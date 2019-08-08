/**
 * Copyright 2016 Goldman Sachs.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.gs.dmn.runtime.interpreter;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.feel.analysis.semantics.environment.Environment;
import com.gs.dmn.feel.analysis.semantics.environment.EnvironmentFactory;
import com.gs.dmn.feel.analysis.semantics.type.*;
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter;
import com.gs.dmn.feel.analysis.syntax.ast.test.UnaryTests;
import com.gs.dmn.feel.interpreter.FEELInterpreter;
import com.gs.dmn.feel.interpreter.FEELInterpreterImpl;
import com.gs.dmn.feel.lib.FEELLib;
import com.gs.dmn.runtime.*;
import com.gs.dmn.runtime.annotation.HitPolicy;
import com.gs.dmn.runtime.interpreter.environment.RuntimeEnvironment;
import com.gs.dmn.runtime.interpreter.environment.RuntimeEnvironmentFactory;
import com.gs.dmn.runtime.listener.Arguments;
import com.gs.dmn.runtime.listener.EventListener;
import com.gs.dmn.runtime.listener.*;
import com.gs.dmn.transformation.basic.BasicDMN2JavaTransformer;
import com.gs.dmn.transformation.basic.QualifiedName;
import org.omg.spec.dmn._20180521.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.Duration;
import java.util.*;
import java.util.stream.Collectors;

import static com.gs.dmn.feel.analysis.semantics.type.AnyType.ANY;
import static com.gs.dmn.feel.analysis.semantics.type.BooleanType.BOOLEAN;
import static com.gs.dmn.feel.analysis.semantics.type.NumberType.NUMBER;
import static com.gs.dmn.feel.analysis.semantics.type.StringType.STRING;

public class StandardDMNInterpreter implements DMNInterpreter {
    private static final Logger LOGGER = LoggerFactory.getLogger(StandardDMNInterpreter.class);
    protected static EventListener EVENT_LISTENER = new LoggingEventListener(LOGGER);
    protected final RuntimeEnvironmentFactory runtimeEnvironmentFactory = RuntimeEnvironmentFactory.instance();
    private final DMNModelRepository dmnModelRepository;
    private final EnvironmentFactory environmentFactory;

    public static void setEventListener(EventListener eventListener) {
        EVENT_LISTENER = eventListener;
    }

    private final BasicDMN2JavaTransformer basicDMNTransformer;
    protected final FEELLib feelLib;
    private final FEELInterpreter feelInterpreter;

    public StandardDMNInterpreter(BasicDMN2JavaTransformer basicDMNTransformer, FEELLib feelLib) {
        this.basicDMNTransformer = basicDMNTransformer;
        this.dmnModelRepository = basicDMNTransformer.getDMNModelRepository();
        this.environmentFactory = basicDMNTransformer.getEnvironmentFactory();
        this.feelLib = feelLib;
        this.feelInterpreter = new FEELInterpreterImpl(this);
    }

    @Override
    public BasicDMN2JavaTransformer getBasicDMNTransformer() {
        return basicDMNTransformer;
    }

    @Override
    public FEELLib getFeelLib() {
        return feelLib;
    }

    @Override
    public Result evaluate(String namespacePrefix, String drgElementName, RuntimeEnvironment runtimeEnvironment) {
        TDRGElement drgElement = dmnModelRepository.findDRGElementByName(drgElementName);
        evaluate(namespacePrefix, drgElement, runtimeEnvironment);
        Object value = lookupBinding(runtimeEnvironment, namespacePrefix, drgElementName);
        return new Result(value, basicDMNTransformer.drgElementOutputFEELType(drgElement));
    }

    @Override
    public Result evaluateInvocation(String namespacePrefix, String drgElementName, List<Object> args, RuntimeEnvironment runtimeEnvironment) {
        TDRGElement drgElement = dmnModelRepository.findDRGElementByName(drgElementName);
        Environment environment = basicDMNTransformer.makeEnvironment(drgElement);
        return evaluateInvocation(namespacePrefix, drgElement, args, FEELContext.makeContext(environment, runtimeEnvironment));
    }

    @Override
    public Result evaluateInvocation(String namespacePrefix, TDRGElement drgElement, List<Object> args, FEELContext context) {
        Result actualOutput;
        if (drgElement instanceof TInputData) {
            actualOutput = evaluate(namespacePrefix, drgElement.getName(), context.getRuntimeEnvironment());
        } else if (drgElement instanceof TDecision) {
            actualOutput = evaluate(namespacePrefix, drgElement.getName(), context.getRuntimeEnvironment());
        } else if (drgElement instanceof TDecisionService) {
            actualOutput = evaluateInvocation(namespacePrefix, (TDecisionService) drgElement, args, context);
        } else if (drgElement instanceof TBusinessKnowledgeModel) {
            actualOutput = evaluateInvocation(namespacePrefix, (TBusinessKnowledgeModel) drgElement, args, context);
        } else {
            throw new IllegalArgumentException(String.format("Not supported type '%s'", drgElement.getClass().getSimpleName()));
        }
        return actualOutput;
    }

    private Result evaluateInvocation(String namespacePrefix, TBusinessKnowledgeModel bkm, List<Object> argList, FEELContext context) {
        RuntimeEnvironment bkmRuntimeEnvironment = runtimeEnvironmentFactory.makeEnvironment(context.getRuntimeEnvironment());

        // BKM start
        long startTime_ = System.currentTimeMillis();
        DRGElement drgElementAnnotation = makeDRGElementAnnotation(bkm, bkmRuntimeEnvironment);
        com.gs.dmn.runtime.listener.Arguments decisionArguments = makeArguments(bkm, bkmRuntimeEnvironment);
        EVENT_LISTENER.startDRGElement(drgElementAnnotation, decisionArguments);

        // Bind parameters
        Environment bkmEnvironment = environmentFactory.makeEnvironment(context.getEnvironment());
        List<TInformationItem> formalParameterList = bkm.getEncapsulatedLogic().getFormalParameter();
        for (int i = 0; i < formalParameterList.size(); i++) {
            TInformationItem param = formalParameterList.get(i);
            String name = param.getName();
            Type type = basicDMNTransformer.toFEELType(QualifiedName.toQualifiedName(param.getTypeRef()));
            Object value = argList.get(i);

            // Check value and apply implicit conversions
            Result result = convertValue(value, type);
            value = Result.value(result);

            bkmEnvironment.addDeclaration(environmentFactory.makeVariableDeclaration(name, type));
            bkmRuntimeEnvironment.bind(name, value);
        }

        // Execute function body
        TExpression expression = dmnModelRepository.expression(bkm);
        Result result = evaluateExpression(expression, bkmEnvironment, bkmRuntimeEnvironment, bkm, drgElementAnnotation);
        Object value = Result.value(result);

        // Check value and apply implicit conversions
        Type expectedType = basicDMNTransformer.drgElementOutputFEELType(bkm, bkmEnvironment);
        result = convertResult(result, expectedType);
        value = Result.value(result);

        // Decision end
        EVENT_LISTENER.endDRGElement(drgElementAnnotation, decisionArguments, value, (System.currentTimeMillis() - startTime_));

        return result;
    }

    private Result evaluateInvocation(String namespacePrefix, TDecisionService service, List<Object> argList, FEELContext context) {
        RuntimeEnvironment serviceRuntimeEnvironment = runtimeEnvironmentFactory.makeEnvironment(context.getRuntimeEnvironment());

        // Decision Service start
        long startTime_ = System.currentTimeMillis();
        DRGElement drgElementAnnotation = makeDRGElementAnnotation(service, serviceRuntimeEnvironment);
        com.gs.dmn.runtime.listener.Arguments decisionArguments = makeArguments(service, serviceRuntimeEnvironment);
        EVENT_LISTENER.startDRGElement(drgElementAnnotation, decisionArguments);

        // Bind parameters
        Environment serviceEnvironment = environmentFactory.makeEnvironment(context.getEnvironment());
        List<FormalParameter> formalParameterList = basicDMNTransformer.dsFEELParameters(service);
        for (int i = 0; i < formalParameterList.size(); i++) {
            FormalParameter param = formalParameterList.get(i);
            String name = param.getName();
            Type type = param.getType();
            Object value = argList.get(i);

            // Check value and apply implicit conversions
            Result result = convertValue(value, type);
            value = Result.value(result);

            serviceEnvironment.addDeclaration(environmentFactory.makeVariableDeclaration(name, type));
            serviceRuntimeEnvironment.bind(name, value);
        }

        // Evaluate output decisions
        List<TDecision> outputDecisions = new ArrayList<>();
        List<String> outputDecisionPrefixes = new ArrayList<>();
        for (TDMNElementReference reference: service.getOutputDecision()) {
            String childNamespacePrefix = dmnModelRepository.namespacePrefix(reference);
            TDecision decision = dmnModelRepository.findDecisionById(reference.getHref());
            outputDecisions.add(decision);
            outputDecisionPrefixes.add(childNamespacePrefix);
            evaluateDecision(childNamespacePrefix, decision, serviceRuntimeEnvironment);
        }

        // Make context result
        Object output = null;
        if (outputDecisions.size() == 1) {
            String key = outputDecisions.get(0).getName();
            String prefix = outputDecisionPrefixes.get(0);
            output = lookupBinding(serviceRuntimeEnvironment, prefix, key);
        } else {
            output = new Context();
            for(int i=0; i < outputDecisions.size(); i++) {
                TDecision decision = outputDecisions.get(i);
                String prefix = outputDecisionPrefixes.get(i);
                String key = decision.getName();
                Object value = lookupBinding(serviceRuntimeEnvironment, prefix, key);
                ((Context) output).add(key, value);
            }
        }

        // Check value and apply implicit conversions
        Type expectedType = basicDMNTransformer.drgElementOutputFEELType(service, serviceEnvironment);
        Result result = convertValue(output, expectedType);
        output = Result.value(result);

        // Set variable
        serviceRuntimeEnvironment.bind(service.getName(), output);

        // Decision service end
        EVENT_LISTENER.endDRGElement(drgElementAnnotation, decisionArguments, output, (System.currentTimeMillis() - startTime_));

        return new Result(output, basicDMNTransformer.drgElementOutputFEELType(service));
    }

    @Override
    public Result evaluateInvocation(TFunctionDefinition functionDefinition, List<Object> argList, FEELContext context) {
        // Create new environments and bind parameters
        Environment functionEnvironment = environmentFactory.makeEnvironment(context.getEnvironment());
        RuntimeEnvironment functionRuntimeEnvironment = runtimeEnvironmentFactory.makeEnvironment(context.getRuntimeEnvironment());
        List<TInformationItem> formalParameterList = functionDefinition.getFormalParameter();
        for (int i = 0; i < formalParameterList.size(); i++) {
            TInformationItem param = formalParameterList.get(i);
            String name = param.getName();
            Type type = basicDMNTransformer.toFEELType(QualifiedName.toQualifiedName(param.getTypeRef()));
            Object value = argList.get(i);
            functionEnvironment.addDeclaration(environmentFactory.makeVariableDeclaration(name, type));
            functionRuntimeEnvironment.bind(name, value);
        }

        // Execute function body
        JAXBElement<? extends TExpression> expressionElement = functionDefinition.getExpression();
        Result output;
        if (expressionElement == null) {
            output = null;
        } else {
            TExpression expression = expressionElement.getValue();
            output = evaluateExpression(expression, functionEnvironment, functionRuntimeEnvironment, null, null);
        }

        return output;
    }

    private void evaluate(String namespacePrefix, TDRGElement drgElement, RuntimeEnvironment runtimeEnvironment) {
        if (drgElement instanceof TInputData) {
        } else if (drgElement instanceof TBusinessKnowledgeModel) {
            evaluateBKM(namespacePrefix, (TBusinessKnowledgeModel) drgElement, runtimeEnvironment);
        } else if (drgElement instanceof TDecisionService) {
            evaluateDecisionService(namespacePrefix, (TDecisionService) drgElement, runtimeEnvironment);
        } else if (drgElement instanceof TDecision) {
            evaluateDecision(namespacePrefix, (TDecision) drgElement, runtimeEnvironment);
        } else {
            handleError(String.format("DRG Element '%s' not supported yet", drgElement.getClass()));
        }
    }

    private void evaluateKnowledgeRequirements(List<TKnowledgeRequirement> knowledgeRequirementList, RuntimeEnvironment runtimeEnvironment) {
        for (TKnowledgeRequirement requirement : knowledgeRequirementList) {
            TDMNElementReference requiredKnowledge = requirement.getRequiredKnowledge();
            String href = requiredKnowledge.getHref();
            TInvocable invocable = this.dmnModelRepository.findInvocableById(href);
            String namespacePrefix = dmnModelRepository.namespacePrefix(requiredKnowledge);
            if (invocable instanceof TBusinessKnowledgeModel) {
                evaluateBKM(namespacePrefix, (TBusinessKnowledgeModel) invocable, runtimeEnvironment);
            } else if (invocable instanceof TDecisionService) {
                evaluateDecisionService(namespacePrefix, (TDecisionService) invocable, runtimeEnvironment);
            } else {
                throw new UnsupportedOperationException(String.format("Not supported invocable '%s'", invocable.getClass().getSimpleName()));
            }
        }
    }

    private void evaluateBKM(String namespacePrefix, TBusinessKnowledgeModel bkm, RuntimeEnvironment runtimeEnvironment) {
        // Evaluate knowledge requirements
        List<TKnowledgeRequirement> knowledgeRequirement = bkm.getKnowledgeRequirement();
        evaluateKnowledgeRequirements(knowledgeRequirement, runtimeEnvironment);

        // Bind name to DMN definition
        String bkmName = bkm.getName();
        if (bkmName == null) {
            bkmName = bkm.getVariable().getName();
        }
        bind(runtimeEnvironment, namespacePrefix, bkmName, bkm);
    }

    private void evaluateDecisionService(String namespacePrefix, TDecisionService service, RuntimeEnvironment runtimeEnvironment) {
        // Bind name to DMN definition
        String serviceName = service.getName();
        if (serviceName == null) {
            serviceName = service.getVariable().getName();
        }
        bind(runtimeEnvironment, namespacePrefix, serviceName, service);
    }

    protected void evaluateDecision(String namespacePrefix, TDecision decision, RuntimeEnvironment runtimeEnvironment) {
        // Decision start
        long startTime_ = System.currentTimeMillis();
        DRGElement drgElementAnnotation = makeDRGElementAnnotation(decision, runtimeEnvironment);
        com.gs.dmn.runtime.listener.Arguments decisionArguments = makeArguments(decision, runtimeEnvironment);
        EVENT_LISTENER.startDRGElement(drgElementAnnotation, decisionArguments);

        // Check if has already been evaluated
        String decisionName = decision.getName();
        Object output = null;
        if (dagOptimisation() && runtimeEnvironment.isBound(decisionName)) {
            // Retrieve value from environment
            output = lookupBinding(runtimeEnvironment, namespacePrefix, decisionName);
        } else {
            // Evaluate dependencies
            evaluateInformationRequirementList(decision.getInformationRequirement(), runtimeEnvironment);
            evaluateKnowledgeRequirements(decision.getKnowledgeRequirement(), runtimeEnvironment);

            // Evaluate expression
            TExpression expression = dmnModelRepository.expression(decision);
            Environment environment = basicDMNTransformer.makeEnvironment(decision);
            Result result = evaluateExpression(expression, environment, runtimeEnvironment, decision, drgElementAnnotation);
            output = Result.value(result);

            // Check value and apply implicit conversions
            Type expectedType = basicDMNTransformer.drgElementOutputFEELType(decision, environment);
            result = convertResult(result, expectedType);
            output = Result.value(result);

            // Bind value
            bind(runtimeEnvironment, namespacePrefix, decisionName, output);
        }

        // Decision end
        EVENT_LISTENER.endDRGElement(drgElementAnnotation, decisionArguments, output, (System.currentTimeMillis() - startTime_));
    }

    private void bind(RuntimeEnvironment runtimeEnvironment, String namespacePrefix, String name, Object value) {
        if (namespacePrefix == null) {
            runtimeEnvironment.bind(name, value);
        } else {
            Context context = (Context) runtimeEnvironment.lookupBinding(namespacePrefix);
            if (context == null) {
                context = new Context();
                runtimeEnvironment.bind(namespacePrefix, context);
            }
            context.put(name, value);
        }
    }

    private Object lookupBinding(RuntimeEnvironment runtimeEnvironment, String namespacePrefix, String drgElementName) {
        if (namespacePrefix == null) {
            return runtimeEnvironment.lookupBinding(drgElementName);
        } else {
            Context context = (Context) runtimeEnvironment.lookupBinding(namespacePrefix);
            return context.get(drgElementName);
        }
    }


    protected boolean dagOptimisation() {
        return true;
    }

    private void evaluateInformationRequirementList(List<TInformationRequirement> informationRequirementList, RuntimeEnvironment runtimeEnvironment) {
        for (TInformationRequirement informationRequirement : informationRequirementList) {
            TDMNElementReference requiredInput = informationRequirement.getRequiredInput();
            TDMNElementReference requiredDecision = informationRequirement.getRequiredDecision();
            if (requiredInput != null) {
            } else if (requiredDecision != null) {
                String namespacePrefix = dmnModelRepository.namespacePrefix(requiredDecision);
                TDecision child = dmnModelRepository.findDecisionById(requiredDecision.getHref());
                evaluateDecision(namespacePrefix, child, runtimeEnvironment);
            } else {
                handleError("Incorrect InformationRequirement. Missing required input and decision");
            }
        }
    }

    //
    // Expression evaluation
    //
    @Override
    public Result evaluateLiteralExpression(String text, Environment environment, RuntimeEnvironment runtimeEnvironment) {
        return this.evaluateLiteralExpression(text, environment, runtimeEnvironment, null);
    }

    protected Result evaluateExpression(TExpression expression, Environment environment, RuntimeEnvironment runtimeEnvironment, TDRGElement element, DRGElement elementAnnotation) {
        Result result = null;
        if (expression == null) {
            handleError(String.format("Missing expression for element '%s'", element == null ? null : element.getName()));
        } else if (expression instanceof TContext) {
            result = evaluateContextExpression((TContext) expression, environment, runtimeEnvironment, element, elementAnnotation);
        } else if (expression instanceof TDecisionTable) {
            result = evaluateDecisionTable((TDecisionTable)expression, environment, runtimeEnvironment, element, elementAnnotation);
        } else if (expression instanceof TFunctionDefinition) {
            result = evaluateFunctionDefinitionExpression((TFunctionDefinition)expression, environment, runtimeEnvironment, element, elementAnnotation);
        } else if (expression instanceof TInvocation) {
            result = evaluateInvocationExpression((TInvocation) expression, environment, runtimeEnvironment, element, elementAnnotation);
        } else if (expression instanceof TLiteralExpression) {
            result = evaluateLiteralExpression((TLiteralExpression) expression, environment, runtimeEnvironment, element, elementAnnotation);
        } else if (expression instanceof TList) {
            result = evaluateListExpression((TList) expression, environment, runtimeEnvironment, element, elementAnnotation);
        } else if (expression instanceof TRelation) {
            result = evaluateRelationExpression((TRelation) expression, environment, runtimeEnvironment, element, elementAnnotation);
        } else {
            handleError(String.format("Expression '%s' not supported yet", expression.getClass().getSimpleName()));
        }
        return result;
    }

    private Result evaluateLiteralExpression(TLiteralExpression expression, Environment environment, RuntimeEnvironment runtimeEnvironment, TDRGElement element, DRGElement elementAnnotation) {
        String text = expression.getText();
        Result result = evaluateLiteralExpression(text, environment, runtimeEnvironment, element);
        return result;
    }

    protected Result evaluateLiteralExpression(String text, Environment environment, RuntimeEnvironment runtimeEnvironment, TDRGElement element) {
        FEELContext context = FEELContext.makeContext(environment, runtimeEnvironment);
        Result result = feelInterpreter.evaluateExpression(text, context);
        return result;
    }

    private Result evaluateInvocationExpression(TInvocation invocation, Environment environment, RuntimeEnvironment runtimeEnvironment, TDRGElement element, DRGElement elementAnnotation) {
        // Compute name-java binding for arguments
        Map<String, Object> argBinding = new LinkedHashMap<>();
        for(TBinding binding: invocation.getBinding()) {
            String argName = binding.getParameter().getName();
            TExpression argExpression = binding.getExpression().getValue();
            Result argResult = evaluateExpression(argExpression, environment, runtimeEnvironment, element, elementAnnotation);
            Object argJava = Result.value(argResult);
            argBinding.put(argName, argJava);
        }

        // Evaluate body
        TExpression body = invocation.getExpression().getValue();
        if (body instanceof TLiteralExpression) {
            String bkmName = ((TLiteralExpression) body).getText();
            TBusinessKnowledgeModel bkm = dmnModelRepository.findKnowledgeModelByName(bkmName);
            if (bkm == null) {
                throw new DMNRuntimeException(String.format("Cannot find BKM for '%s'", bkmName));
            }
            List<Object> argList = new ArrayList<>();
            List<String> formalParameterList = basicDMNTransformer.bkmFEELParameterNames(bkm);
            for(String paramName: formalParameterList) {
                if (argBinding.containsKey(paramName)) {
                    Object argValue = argBinding.get(paramName);
                    argList.add(argValue);
                } else {
                    throw new DMNRuntimeException(String.format("Cannot find binding for parameter '%s'", paramName));
                }
            }
            Environment parentEnvironment = basicDMNTransformer.makeEnvironment(element);
            FEELContext context = FEELContext.makeContext(parentEnvironment, runtimeEnvironment);
            return evaluateInvocation(null, bkm, argList, context);
        } else {
            throw new UnsupportedOperationException(String.format("Not supported '%s'", body.getClass().getSimpleName()));
        }
    }

    private Result evaluateContextExpression(TContext context, Environment environment, RuntimeEnvironment runtimeEnvironment, TDRGElement element, DRGElement elementAnnotation) {
        // Make context environment
        Pair<Environment, Map<TContextEntry, Expression>> pair = basicDMNTransformer.makeContextEnvironment(context, environment);
        Environment contextEnvironment = pair.getLeft();
        Map<TContextEntry, Expression> literalExpressionMap = pair.getRight();

        // Evaluate entries
        RuntimeEnvironment contextRuntimeEnvironment = runtimeEnvironmentFactory.makeEnvironment(runtimeEnvironment);
        FEELContext feelContext = FEELContext.makeContext(contextEnvironment, contextRuntimeEnvironment);
        Result returnResult = null;
        for(TContextEntry entry: context.getContextEntry()) {
            // Evaluate entry value
            Result entryResult;
            JAXBElement<? extends TExpression> jaxbElement = entry.getExpression();
            if (jaxbElement != null) {
                TExpression expression = jaxbElement.getValue();
                if (expression instanceof TLiteralExpression) {
                    Expression feelExpression = literalExpressionMap.get(entry);
                    entryResult = this.feelInterpreter.evaluateExpression(feelExpression, feelContext);
                } else {
                    entryResult = evaluateExpression(expression, contextEnvironment, contextRuntimeEnvironment, element, elementAnnotation);
                }
            } else {
                entryResult = null;
            }
            Object entryValue = Result.value(entryResult);

            // Add runtime binding
            TInformationItem variable = entry.getVariable();
            if (variable != null) {
                String entryName = variable.getName();
                contextRuntimeEnvironment.bind(entryName, entryValue);
            } else {
                returnResult = entryResult;
            }
        }

        // Return result
        if (returnResult != null) {
            return returnResult;
        } else {
            // Make complex type value
            Context output = new Context();
            ContextType type = new ContextType();
            // Add entries
            for(TContextEntry entry: context.getContextEntry()) {
                TInformationItem variable = entry.getVariable();
                if (variable != null) {
                    String entryName = variable.getName();
                    output.add(entryName, contextRuntimeEnvironment.lookupBinding(entryName));
                    type.addMember(entryName, new ArrayList<>(), basicDMNTransformer.toFEELType(variable.getTypeRef()));
                }
            }
            // Return value
            return new Result(output, type);
        }
    }

    private Result evaluateListExpression(TList list, Environment environment, RuntimeEnvironment runtimeEnvironment, TDRGElement element, DRGElement elementAnnotation) {
        if (list.getExpression() == null) {
            return null;
        }
        List<Object> resultValue = new ArrayList<>();
        Type elementType = ANY;
        for(JAXBElement<? extends TExpression> expElement: list.getExpression()) {
            Result expResult;
            if (expElement == null) {
                expResult = null;
            } else {
                TExpression exp = expElement.getValue();
                expResult = evaluateExpression(exp, environment, runtimeEnvironment, element, elementAnnotation);
                elementType = basicDMNTransformer.toFEELType(exp.getTypeRef());
            }
            resultValue.add(Result.value(expResult));
        }
        return new Result(resultValue, new ListType(elementType));
    }

    private Result evaluateRelationExpression(TRelation relation, Environment environment, RuntimeEnvironment runtimeEnvironment, TDRGElement element, DRGElement elementAnnotation) {
        if (relation.getRow() == null || relation.getColumn() == null) {
            return null;
        }

        // Make relation environment
        Environment relationEnvironment = basicDMNTransformer.makeRelationEnvironment(relation, environment);

        // Column names
        List<String> columnNameList = relation.getColumn().stream().map(TNamedElement::getName).collect(Collectors.toList());

        // Scan relation and evaluate each row
        List<Object> relationValue = new ArrayList<>();
        ContextType relationType = new ContextType();
        for(TList row: relation.getRow()) {
            Object rowValue = null;
            List<JAXBElement<? extends TExpression>> jaxbElementList = row.getExpression();
            if (jaxbElementList != null) {
                Context contextValue = new Context();
                ContextType contextType = new ContextType();
                for(int i = 0; i < jaxbElementList.size(); i++) {
                    JAXBElement<? extends TExpression> jaxbElement = jaxbElementList.get(i);
                    TExpression expression = jaxbElement == null ? null : jaxbElement.getValue();
                    Result columnResult = expression == null ? null : evaluateExpression(expression, relationEnvironment, runtimeEnvironment, element, elementAnnotation);
                    Object columnValue = Result.value(columnResult);
                    contextValue.add(columnNameList.get(i), columnValue);
                    contextType.addMember(columnNameList.get(i), new ArrayList<>(), columnResult.getType());
                    relationType = contextType;
                }
                rowValue = contextValue;
            }
            relationValue.add(rowValue);
        }
        return new Result(relationValue, new ListType(relationType));
    }

    private Result evaluateFunctionDefinitionExpression(TFunctionDefinition expression, Environment environment, RuntimeEnvironment runtimeEnvironment, TDRGElement element, DRGElement elementAnnotation) {
        return new Result(expression, ANY);
    }

    private Result evaluateDecisionTable(TDecisionTable decisionTable, Environment environment, RuntimeEnvironment runtimeEnvironment, TDRGElement element, DRGElement elementAnnotation) {
        // Evaluate InputClauses
        List<InputClausePair> inputClauseList = new ArrayList<>();
        for (TInputClause inputClause : decisionTable.getInput()) {
            TLiteralExpression inputExpression = inputClause.getInputExpression();
            String inputExpressionText = inputExpression.getText();
            FEELContext feelContext = FEELContext.makeContext(environment, runtimeEnvironment);
            Expression expression = feelInterpreter.analyzeExpression(inputExpressionText, feelContext);
            Result inputExpressionResult = feelInterpreter.evaluateExpression(expression, feelContext);
            Object inputExpressionValue = Result.value(inputExpressionResult);
            inputClauseList.add(new InputClausePair(expression, inputExpressionValue));
        }

        // Evaluate rules
        List<TDecisionRule> ruleList = decisionTable.getRule();
        RuleOutputList ruleOutputList = new RuleOutputList();
        for (int i = 0; i < ruleList.size(); i++) {
            TDecisionRule rule = ruleList.get(i);
            Rule ruleAnnotation = makeRuleAnnotation(rule, i);

            // Rule start
            EVENT_LISTENER.startRule(elementAnnotation, ruleAnnotation);

            InterpretedRuleOutput ruleOutput = evaluateRule(element, decisionTable, rule, inputClauseList, environment, runtimeEnvironment, elementAnnotation, ruleAnnotation);
            ruleOutputList.add(ruleOutput);

            // Rule end
            EVENT_LISTENER.endRule(elementAnnotation, ruleAnnotation, ruleOutput);
        }

        // Return results based on hit policy
        Object value = applyHitPolicy(element, decisionTable, ruleOutputList, environment, runtimeEnvironment, elementAnnotation);
        return new Result(value, basicDMNTransformer.drgElementOutputFEELType(element));
    }

    private InterpretedRuleOutput evaluateRule(TDRGElement element, TDecisionTable decisionTable, TDecisionRule rule, List<InputClausePair> inputClauseList, Environment environment, RuntimeEnvironment runtimeEnvironment, DRGElement elementAnnotation, Rule ruleAnnotation) {
        // Check tests
        List<TUnaryTests> inputEntry = rule.getInputEntry();
        boolean ruleMatched = true;
        for (int index = 0; index < inputEntry.size(); index++) {
            TUnaryTests unaryTest = inputEntry.get(index);
            String text = unaryTest.getText();
            Environment inputEntryEnvironment = basicDMNTransformer.makeInputEntryEnvironment(element, inputClauseList.get(index).getExpression());
            RuntimeEnvironment inputEntryRuntimeEnvironment = runtimeEnvironmentFactory.makeInputEntryEnvironment(inputClauseList, runtimeEnvironment, index);
            FEELContext context = FEELContext.makeContext(inputEntryEnvironment, inputEntryRuntimeEnvironment);
            Expression ast = feelInterpreter.analyzeUnaryTests(text, context);
            Result result = feelInterpreter.evaluateUnaryTests((UnaryTests) ast, context);
            Object testMatched = Result.value(result);
            if (isFalse(testMatched)) {
                ruleMatched = false;
                break;
            }
        }

        // Compute output
        if (ruleMatched) {
            // Rule match
            EVENT_LISTENER.matchRule(elementAnnotation, ruleAnnotation);

            THitPolicy hitPolicy = decisionTable.getHitPolicy();
            if (dmnModelRepository.isCompoundDecisionTable(element)) {
                Context output = new Context();
                List<TLiteralExpression> outputEntry = rule.getOutputEntry();
                for (int i = 0; i < outputEntry.size(); i++) {
                    TLiteralExpression literalExpression = outputEntry.get(i);
                    String key = decisionTable.getOutput().get(i).getName();
                    Result result = evaluateLiteralExpression(literalExpression, environment, runtimeEnvironment, element, elementAnnotation);
                    Object value = Result.value(result);
                    if (dmnModelRepository.isOutputOrderHit(hitPolicy)) {
                        Object priority = basicDMNTransformer.priority(element, rule.getOutputEntry().get(i), i);
                        output.put(key, new Pair<>(value, priority));
                    } else {
                        output.put(key, new Pair<>(value, null));
                    }
                }
                return new InterpretedRuleOutput(ruleMatched, output);
            } else {
                List<TLiteralExpression> outputEntry = rule.getOutputEntry();
                TLiteralExpression literalExpression = outputEntry.get(0);
                Object output = null;
                Result result = evaluateLiteralExpression(literalExpression, environment, runtimeEnvironment, element, elementAnnotation);
                Object value = Result.value(result);
                if (dmnModelRepository.isOutputOrderHit(hitPolicy)) {
                    Object priority = basicDMNTransformer.priority(element, rule.getOutputEntry().get(0), 0);
                    output = new Pair(value, priority);
                } else {
                    output = new Pair(value, null);
                }
                return new InterpretedRuleOutput(ruleMatched, output);
            }
        } else {
            return new InterpretedRuleOutput(ruleMatched, null);
        }

    }

    protected Result convertResult(Result result, Type expectedType) {
        Object value = Result.value(result);
        Type actualType = Result.type(result);
        // Static conversion
        if (expectedType == null || expectedType == ANY) {
            return new Result(value, ANY);
        } else if (actualType != null && actualType.conformsTo(expectedType)) {
            return new Result(value, expectedType);
        }
        // Dynamic conversion
        return convertValue(value, expectedType);
    }

    protected Result convertValue(Object value, Type expectedType) {
        // Dynamic conversion
        if (expectedType == null) {
            expectedType = ANY;
        }
        if (conformsTo(value, expectedType)) {
            return new Result(value, expectedType);
        } else if (value instanceof List && ((List) value).size() == 1 && !(expectedType instanceof ListType)) {
            if (conformsTo(((List) value).get(0), expectedType)) {
                // from-singleton-list conversion
                value = feelLib.asElement((List)value);
                return new Result(value, expectedType);
            } else {
                return new Result(null, expectedType);
            }
        }
        return new Result(null, expectedType);
    }

    private boolean conformsTo(Object value, Type expectedType) {
        if (expectedType == ANY) {
            return true;
        } else if (value instanceof Number && expectedType == NUMBER) {
            return true;
        } else if (value instanceof String && expectedType == STRING) {
            return true;
        } else if (value instanceof Boolean && expectedType == BOOLEAN) {
            return true;
        } else if (value instanceof Duration && expectedType instanceof DurationType) {
            return true;
        } else if (value instanceof Context && (expectedType instanceof ContextType || expectedType instanceof ItemDefinitionType)) {
            Context context = (Context) value;
            CompositeDataType contextType = (CompositeDataType) expectedType;
            for (String member: contextType.getMembers()) {
                if (!conformsTo(context.get(member), contextType.getMemberType(member))) {
                    return false;
                }
            }
            return true;
        } else if (value instanceof List && expectedType instanceof ListType) {
            for (Object obj : (List) value) {
                if (!conformsTo(obj, ((ListType) expectedType).getElementType())) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean isFalse(Object o) {
        if (o instanceof List) {
            if (((List) o).stream().anyMatch(t -> t == null || Boolean.FALSE.equals(o))) {
                return false;
            }
        }
        return o == null || Boolean.FALSE.equals(o);
    }

    private Object applyHitPolicy(TDRGElement element, TDecisionTable decisionTable, RuleOutputList ruleOutputList, Environment environment, RuntimeEnvironment runtimeEnvironment, DRGElement elementAnnotation) {
        if (ruleOutputList.noMatchedRules()) {
            return evaluateDefaultValue(element, decisionTable, basicDMNTransformer, environment, runtimeEnvironment, elementAnnotation);
        } else {
            THitPolicy hitPolicy = decisionTable.getHitPolicy();
            if (dmnModelRepository.isSingleHit(hitPolicy)) {
                InterpretedRuleOutput ruleOutput = (InterpretedRuleOutput) ruleOutputList.applySingle(HitPolicy.fromValue(hitPolicy.value()));
                return toDecisionOutput(element, decisionTable, ruleOutput);
            } else if (dmnModelRepository.isMultipleHit(hitPolicy)) {
                List<? extends RuleOutput> ruleOutputs = ruleOutputList.applyMultiple(HitPolicy.fromValue(hitPolicy.value()));
                if (dmnModelRepository.isCompoundDecisionTable(element)) {
                    if (dmnModelRepository.hasAggregator(decisionTable)) {
                        return null;
                    } else {
                        return ruleOutputs.stream().map(r -> toDecisionOutput(element, decisionTable, (InterpretedRuleOutput) r)).collect(Collectors.toList());
                    }
                } else {
                    List<Object> decisionOutput = ruleOutputs.stream().map(r -> toDecisionOutput(element, decisionTable, (InterpretedRuleOutput) r)).collect(Collectors.toList());
                    if (dmnModelRepository.hasAggregator(decisionTable)) {
                        TBuiltinAggregator aggregation = decisionTable.getAggregation();
                        if (aggregation == TBuiltinAggregator.MIN) {
                            return feelLib.min(decisionOutput);
                        } else if (aggregation == TBuiltinAggregator.MAX) {
                            return feelLib.max(decisionOutput);
                        } else if (aggregation == TBuiltinAggregator.COUNT) {
                            return feelLib.number(String.format("%d", decisionOutput.size()));
                        } else if (aggregation == TBuiltinAggregator.SUM) {
                            return feelLib.sum(decisionOutput);
                        } else {
                            throw new UnsupportedOperationException(String.format("Not supported '%s' aggregation.", aggregation));
                        }
                    } else {
                        return decisionOutput;
                    }
                }
            } else {
                throw new UnsupportedOperationException(String.format("Hit policy '%s' not supported ", hitPolicy));
            }
        }
    }

    private Object evaluateDefaultValue(TDRGElement element, TDecisionTable decisionTable, BasicDMN2JavaTransformer dmnTransformer, Environment environment, RuntimeEnvironment runtimeEnvironment, DRGElement elementAnnotation) {
        if (dmnModelRepository.hasDefaultValue(decisionTable)) {
            // Evaluate and collect default values
            List<TOutputClause> outputClauses = decisionTable.getOutput();
            Context defaultValue = new Context();
            for (TOutputClause output : outputClauses) {
                TLiteralExpression defaultOutputEntry = output.getDefaultOutputEntry();
                String key = dmnModelRepository.outputClauseName(element, output);
                if (defaultOutputEntry == null) {
                    defaultValue.put(key, null);
                } else {
                    Result result = evaluateLiteralExpression(defaultOutputEntry, environment, runtimeEnvironment, element, elementAnnotation);
                    Object value = Result.value(result);
                    defaultValue.put(key, value);
                }
            }
            // Return result
            if (dmnModelRepository.isCompoundDecisionTable(element)) {
                if (dmnTransformer.isList(element)) {
                    return Arrays.asList(defaultValue);
                } else {
                    return defaultValue;
                }
            } else {
                String key = dmnModelRepository.outputClauseName(element, decisionTable.getOutput().get(0));
                return defaultValue.get(key);
            }
        } else {
            return null;
        }
    }

    private Object toDecisionOutput(TDRGElement element, TDecisionTable decisionTable, InterpretedRuleOutput ruleOutput) {
        if (ruleOutput == null) {
            return null;
        }
        Object result = ruleOutput.getResult();
        // Compound decision
        if (result instanceof Context) {
            Context newContext = new Context();
            for (Object key : ((Context) result).keySet()) {
                newContext.put(key, ((Pair) ((Context) result).get(key)).getLeft());
            }
            return newContext;
            // Simple decision
        } else if (result instanceof Pair) {
            return ((Pair) result).getLeft();
        } else {
            return result;
        }
    }

    //
    // Logging
    //
    protected DRGElement makeDRGElementAnnotation(TDRGElement element, RuntimeEnvironment runtimeEnvironment) {
        return new DRGElement(null,
                dmnModelRepository.name(element),
                dmnModelRepository.label(element),
                basicDMNTransformer.elementKind(element),
                basicDMNTransformer.expressionKind(element),
                basicDMNTransformer.hitPolicy(element),
                dmnModelRepository.rulesCount(element)
        );
    }

    private Arguments makeArguments(TDRGElement element, RuntimeEnvironment runtimeEnvironment) {
        Arguments arguments = new Arguments();
        List<String> parameters = basicDMNTransformer.drgElementArgumentNameList(element, false);
        parameters.forEach(p -> arguments.put(p, runtimeEnvironment.lookupBinding(p)));
        return arguments;
    }

    private Rule makeRuleAnnotation(TDecisionRule rule, int ruleIndex) {
        return new Rule(ruleIndex, basicDMNTransformer.annotationEscapedText(rule));
    }

    protected void handleError(String message) {
        LOGGER.error(message);
        throw new DMNRuntimeException(message);
    }

    protected void handleError(String message, Exception e) {
        LOGGER.error(message, e);
        throw new DMNRuntimeException(message, e);
    }
}
