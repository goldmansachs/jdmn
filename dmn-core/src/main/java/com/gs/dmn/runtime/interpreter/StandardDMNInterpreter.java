/*
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
import com.gs.dmn.DRGElementReference;
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
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import com.gs.dmn.transformation.basic.QualifiedName;
import org.omg.spec.dmn._20191111.model.*;
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

public class StandardDMNInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> implements DMNInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> {
    private static final Logger LOGGER = LoggerFactory.getLogger(StandardDMNInterpreter.class);
    protected static EventListener EVENT_LISTENER = new LoggingEventListener(LOGGER);
    protected final RuntimeEnvironmentFactory runtimeEnvironmentFactory = RuntimeEnvironmentFactory.instance();
    private final DMNModelRepository dmnModelRepository;
    private final EnvironmentFactory environmentFactory;

    public static void setEventListener(EventListener eventListener) {
        EVENT_LISTENER = eventListener;
    }

    private final BasicDMNToNativeTransformer basicDMNTransformer;
    protected final FEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> feelLib;
    private final FEELInterpreter feelInterpreter;

    public StandardDMNInterpreter(BasicDMNToNativeTransformer basicDMNTransformer, FEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> feelLib) {
        this.basicDMNTransformer = basicDMNTransformer;
        this.dmnModelRepository = basicDMNTransformer.getDMNModelRepository();
        this.environmentFactory = basicDMNTransformer.getEnvironmentFactory();
        this.feelLib = feelLib;
        this.feelInterpreter = new FEELInterpreterImpl<>(this);
    }

    @Override
    public BasicDMNToNativeTransformer getBasicDMNTransformer() {
        return this.basicDMNTransformer;
    }

    @Override
    public FEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> getFeelLib() {
        return this.feelLib;
    }

    @Override
    public Result evaluate(DRGElementReference<? extends TDRGElement> reference, List<Object> args, RuntimeEnvironment runtimeEnvironment) {
        Environment environment = this.basicDMNTransformer.makeEnvironment(reference.getElement());
        return evaluate(reference, args, FEELContext.makeContext(reference.getElement(), environment, runtimeEnvironment));
    }

    @Override
    public Result evaluate(DRGElementReference<? extends TDRGElement> reference, List<Object> args, FEELContext context) {
        TDRGElement drgElement = reference.getElement();
        Result actualOutput;
        if (drgElement instanceof TInputData) {
            actualOutput = evaluateInputData((DRGElementReference<TInputData>) reference, context.getRuntimeEnvironment());
        } else if (drgElement instanceof TDecision) {
            actualOutput = evaluateDecision((DRGElementReference<TDecision>) reference, context.getRuntimeEnvironment());
        } else if (drgElement instanceof TDecisionService) {
            actualOutput = evaluateDecisionService((DRGElementReference<TDecisionService>) reference, args, context);
        } else if (drgElement instanceof TBusinessKnowledgeModel) {
            actualOutput = evaluateBKM((DRGElementReference<TBusinessKnowledgeModel>) reference, args, context);
        } else {
            throw new IllegalArgumentException(String.format("Not supported type '%s'", drgElement.getClass().getSimpleName()));
        }
        return actualOutput;
    }

    @Override
    public Result evaluate(TFunctionDefinition functionDefinition, List<Object> argList, FEELContext context) {
        // Create new environments and bind parameters
        Environment functionEnvironment = this.environmentFactory.makeEnvironment(context.getEnvironment());
        RuntimeEnvironment functionRuntimeEnvironment = this.runtimeEnvironmentFactory.makeEnvironment(context.getRuntimeEnvironment());
        List<TInformationItem> formalParameterList = functionDefinition.getFormalParameter();
        TDefinitions model = this.dmnModelRepository.getModel(context.getElement());
        for (int i = 0; i < formalParameterList.size(); i++) {
            TInformationItem param = formalParameterList.get(i);
            String name = param.getName();
            Type type = this.basicDMNTransformer.toFEELType(null, QualifiedName.toQualifiedName(model, param.getTypeRef()));
            Object value = argList.get(i);
            functionEnvironment.addDeclaration(this.environmentFactory.makeVariableDeclaration(name, type));
            functionRuntimeEnvironment.bind(name, value);
        }

        // Execute function body
        JAXBElement<? extends TExpression> expressionElement = functionDefinition.getExpression();
        Result output;
        if (expressionElement == null) {
            output = null;
        } else {
            TExpression expression = expressionElement.getValue();
            output = evaluateExpression(null, expression, functionEnvironment, functionRuntimeEnvironment, null);
        }

        return output;
    }

    @Override
    public Result evaluate(TInvocable invocable, List<Object> argList, FEELContext context) {
        Environment environment = this.basicDMNTransformer.makeEnvironment(invocable, context.getEnvironment());
        FEELContext invocationContext = FEELContext.makeContext(invocable, environment, context.getRuntimeEnvironment());
        Result result;
        if (invocable instanceof TDecisionService) {
            result = evaluate(this.dmnModelRepository.makeDRGElementReference(invocable), argList, invocationContext);
        } else if (invocable instanceof TBusinessKnowledgeModel) {
            result = evaluate(this.dmnModelRepository.makeDRGElementReference(invocable), argList, invocationContext);
        } else {
            throw new IllegalArgumentException(String.format("Not supported type '%s'", invocable.getClass().getSimpleName()));
        }
        return result;
    }

    private Result evaluateInputData(DRGElementReference<TInputData> reference, RuntimeEnvironment runtimeEnvironment) {
        TInputData inputData = reference.getElement();
        Object value = lookupBinding(runtimeEnvironment, reference);
        return new Result(value, this.basicDMNTransformer.drgElementOutputFEELType(inputData));
    }

    private Result evaluateDecision(DRGElementReference<TDecision> reference, RuntimeEnvironment runtimeEnvironment) {
        applyDecision(reference, runtimeEnvironment);

        TDecision decision = reference.getElement();
        Object value = lookupBinding(runtimeEnvironment, reference);
        return new Result(value, this.basicDMNTransformer.drgElementOutputFEELType(decision));
    }

    private Result evaluateBKM(DRGElementReference<TBusinessKnowledgeModel> reference, List<Object> argList, FEELContext context) {
        TBusinessKnowledgeModel bkm = reference.getElement();
        TDefinitions model = this.dmnModelRepository.getModel(bkm);
        RuntimeEnvironment bkmRuntimeEnvironment = this.runtimeEnvironmentFactory.makeEnvironment(context.getRuntimeEnvironment());

        // BKM start
        long startTime_ = System.currentTimeMillis();
        DRGElement drgElementAnnotation = makeDRGElementAnnotation(bkm, bkmRuntimeEnvironment);
        com.gs.dmn.runtime.listener.Arguments decisionArguments = makeArguments(bkm, bkmRuntimeEnvironment);
        EVENT_LISTENER.startDRGElement(drgElementAnnotation, decisionArguments);

        // Bind parameters
        Environment bkmEnvironment = this.basicDMNTransformer.makeEnvironment(bkm, context.getEnvironment());
        List<TInformationItem> formalParameterList = bkm.getEncapsulatedLogic().getFormalParameter();
        for (int i = 0; i < formalParameterList.size(); i++) {
            TInformationItem param = formalParameterList.get(i);
            String name = param.getName();
            Type type = this.basicDMNTransformer.toFEELType(model, QualifiedName.toQualifiedName(model, param.getTypeRef()));
            Object value = argList.get(i);

            // Check value and apply implicit conversions
            Result result = convertValue(value, type);
            value = Result.value(result);

            bkmEnvironment.addDeclaration(this.environmentFactory.makeVariableDeclaration(name, type));
            bkmRuntimeEnvironment.bind(name, value);
        }

        // Execute function body
        TExpression expression = this.dmnModelRepository.expression(bkm);
        Result result = evaluateExpression(bkm, expression, bkmEnvironment, bkmRuntimeEnvironment, drgElementAnnotation);
        Object value = Result.value(result);

        // Check value and apply implicit conversions
        Type expectedType = this.basicDMNTransformer.drgElementOutputFEELType(bkm, bkmEnvironment);
        result = convertResult(result, expectedType);
        value = Result.value(result);

        // Decision end
        EVENT_LISTENER.endDRGElement(drgElementAnnotation, decisionArguments, value, (System.currentTimeMillis() - startTime_));

        return result;
    }

    private Result evaluateDecisionService(DRGElementReference<TDecisionService> serviceReference, List<Object> argList, FEELContext context) {
        TDecisionService service = serviceReference.getElement();
        RuntimeEnvironment serviceRuntimeEnvironment = this.runtimeEnvironmentFactory.makeEnvironment(context.getRuntimeEnvironment());

        // Decision Service start
        long startTime_ = System.currentTimeMillis();
        DRGElement drgElementAnnotation = makeDRGElementAnnotation(service, serviceRuntimeEnvironment);
        com.gs.dmn.runtime.listener.Arguments decisionArguments = makeArguments(service, serviceRuntimeEnvironment);
        EVENT_LISTENER.startDRGElement(drgElementAnnotation, decisionArguments);

        // Bind parameters
        Environment serviceEnvironment = this.environmentFactory.makeEnvironment(context.getEnvironment());
        List<FormalParameter> formalParameterList = this.basicDMNTransformer.dsFEELParameters(service);
        for (int i = 0; i < formalParameterList.size(); i++) {
            FormalParameter param = formalParameterList.get(i);
            String name = param.getName();
            Type type = param.getType();
            Object value = argList.get(i);

            // Check value and apply implicit conversions
            Result result = convertValue(value, type);
            value = Result.value(result);

            serviceEnvironment.addDeclaration(this.environmentFactory.makeVariableDeclaration(name, type));
            serviceRuntimeEnvironment.bind(name, value);
        }

        // Evaluate output decisions
        List<TDecision> outputDecisions = new ArrayList<>();
        List<ImportPath> outputDecisionImportPaths = new ArrayList<>();
        for (TDMNElementReference outputDecisionReference: service.getOutputDecision()) {
            TDecision decision = this.dmnModelRepository.findDecisionByRef(service, outputDecisionReference.getHref());
            outputDecisions.add(decision);

            String importName = this.dmnModelRepository.findImportName(service, outputDecisionReference);
            ImportPath decisionImportPath = new ImportPath(serviceReference.getImportPath(), importName);
            outputDecisionImportPaths.add(decisionImportPath);

            applyDecision(this.dmnModelRepository.makeDRGElementReference(decisionImportPath, decision), serviceRuntimeEnvironment);
        }

        // Make context result
        Object output;
        if (outputDecisions.size() == 1) {
            TDecision decision = outputDecisions.get(0);
            ImportPath childImportPath = outputDecisionImportPaths.get(0);
            output = lookupBinding(serviceRuntimeEnvironment, this.dmnModelRepository.makeDRGElementReference(childImportPath, decision));
        } else {
            output = new Context();
            for(int i=0; i < outputDecisions.size(); i++) {
                TDecision decision = outputDecisions.get(i);
                ImportPath decisionImportPath = outputDecisionImportPaths.get(i);
                Object value = lookupBinding(serviceRuntimeEnvironment, this.dmnModelRepository.makeDRGElementReference(decisionImportPath, decision));
                ((Context) output).add(decision.getName(), value);
            }
        }

        // Check value and apply implicit conversions
        Type expectedType = this.basicDMNTransformer.drgElementOutputFEELType(service, serviceEnvironment);
        Result result = convertValue(output, expectedType);
        output = Result.value(result);

        // Set variable
        serviceRuntimeEnvironment.bind(service.getName(), output);

        // Decision service end
        EVENT_LISTENER.endDRGElement(drgElementAnnotation, decisionArguments, output, (System.currentTimeMillis() - startTime_));

        return new Result(output, this.basicDMNTransformer.drgElementOutputFEELType(service));
    }

    private void evaluateKnowledgeRequirements(ImportPath importPath, TDRGElement parent, List<TKnowledgeRequirement> knowledgeRequirementList, RuntimeEnvironment runtimeEnvironment) {
        for (TKnowledgeRequirement requirement : knowledgeRequirementList) {
            // Find invocable
            TDMNElementReference requiredKnowledge = requirement.getRequiredKnowledge();
            String href = requiredKnowledge.getHref();
            TInvocable invocable = this.dmnModelRepository.findInvocableByRef(parent, href);

            // Calculate import path
            String importName = this.dmnModelRepository.findImportName(parent, requiredKnowledge);
            ImportPath invocableImportPath = new ImportPath(importPath, importName);

            // Evaluate invocable
            if (invocable instanceof TBusinessKnowledgeModel) {
                applyBKM(this.dmnModelRepository.makeDRGElementReference(invocableImportPath, (TBusinessKnowledgeModel) invocable), runtimeEnvironment);
            } else if (invocable instanceof TDecisionService) {
                applyDecisionService(this.dmnModelRepository.makeDRGElementReference(invocableImportPath, (TDecisionService) invocable), runtimeEnvironment);
            } else {
                throw new UnsupportedOperationException(String.format("Not supported invocable '%s'", invocable.getClass().getSimpleName()));
            }
        }
    }

    private void applyBKM(DRGElementReference<TBusinessKnowledgeModel> reference, RuntimeEnvironment runtimeEnvironment) {
        ImportPath importPath = reference.getImportPath();
        TBusinessKnowledgeModel bkm = reference.getElement();

        // Evaluate knowledge requirements
        List<TKnowledgeRequirement> knowledgeRequirement = bkm.getKnowledgeRequirement();
        evaluateKnowledgeRequirements(importPath, bkm, knowledgeRequirement, runtimeEnvironment);

        // Bind name to DMN definition
        bind(runtimeEnvironment, reference, bkm);
    }

    private void applyDecisionService(DRGElementReference<TDecisionService> reference, RuntimeEnvironment runtimeEnvironment) {
        ImportPath importPath = reference.getImportPath();
        TDecisionService service = reference.getElement();

        // Bind name to DMN definition
        bind(runtimeEnvironment, reference, service);
    }

    protected void applyDecision(DRGElementReference<TDecision> reference, RuntimeEnvironment runtimeEnvironment) {
        TDecision decision = reference.getElement();
        ImportPath importPath = reference.getImportPath();

        // Decision start
        long startTime_ = System.currentTimeMillis();
        DRGElement drgElementAnnotation = makeDRGElementAnnotation(decision, runtimeEnvironment);
        com.gs.dmn.runtime.listener.Arguments decisionArguments = makeArguments(decision, runtimeEnvironment);
        EVENT_LISTENER.startDRGElement(drgElementAnnotation, decisionArguments);

        // Check if has already been evaluated
        String decisionName = decision.getName();
        Object output;
        if (dagOptimisation() && runtimeEnvironment.isBound(decisionName)) {
            // Retrieve value from environment
            output = lookupBinding(runtimeEnvironment, reference);
        } else {
            // Evaluate dependencies
            evaluateInformationRequirementList(importPath, decision, decision.getInformationRequirement(), runtimeEnvironment);
            evaluateKnowledgeRequirements(importPath, decision, decision.getKnowledgeRequirement(), runtimeEnvironment);

            // Evaluate expression
            TExpression expression = this.dmnModelRepository.expression(decision);
            Environment environment = this.basicDMNTransformer.makeEnvironment(decision);
            Result result = evaluateExpression(decision, expression, environment, runtimeEnvironment, drgElementAnnotation);
            output = Result.value(result);

            // Check value and apply implicit conversions
            Type expectedType = this.basicDMNTransformer.drgElementOutputFEELType(decision, environment);
            result = convertResult(result, expectedType);
            output = Result.value(result);

            // Bind value
            bind(runtimeEnvironment, reference, output);
        }

        // Decision end
        EVENT_LISTENER.endDRGElement(drgElementAnnotation, decisionArguments, output, (System.currentTimeMillis() - startTime_));
    }

    protected boolean dagOptimisation() {
        return true;
    }

    private void evaluateInformationRequirementList(ImportPath importPath, TDRGElement parent, List<TInformationRequirement> informationRequirementList, RuntimeEnvironment runtimeEnvironment) {
        for (TInformationRequirement informationRequirement : informationRequirementList) {
            TDMNElementReference requiredInput = informationRequirement.getRequiredInput();
            TDMNElementReference requiredDecision = informationRequirement.getRequiredDecision();
            if (requiredInput != null) {
                TInputData child = this.dmnModelRepository.findInputDataByRef(parent, requiredInput.getHref());
                String importName = this.dmnModelRepository.findImportName(parent, requiredInput);
                ImportPath childImportPath = new ImportPath(importPath, importName);

                // Add new binding to match path in parent
                addBinding(runtimeEnvironment, this.dmnModelRepository.makeDRGElementReference(childImportPath, child), importName);
            } else if (requiredDecision != null) {
                TDecision child = this.dmnModelRepository.findDecisionByRef(parent, requiredDecision.getHref());
                String importName = this.dmnModelRepository.findImportName(parent, requiredDecision);
                ImportPath childImportPath = new ImportPath(importPath, importName);
                evaluateDecision(this.dmnModelRepository.makeDRGElementReference(childImportPath, child), runtimeEnvironment);

                // Add new binding to match path in parent
                addBinding(runtimeEnvironment, this.dmnModelRepository.makeDRGElementReference(childImportPath, child), importName);
            } else {
                handleError("Incorrect InformationRequirement. Missing required input and decision");
            }
        }
    }

    //
    // Expression evaluation
    //
    protected Result evaluateExpression(TDRGElement element, TExpression expression, Environment environment, RuntimeEnvironment runtimeEnvironment, DRGElement elementAnnotation) {
        Result result = null;
        if (expression == null) {
            handleError(String.format("Missing expression for element '%s'", element == null ? null : element.getName()));
        } else if (expression instanceof TContext) {
            result = evaluateContextExpression(element, (TContext) expression, environment, runtimeEnvironment, elementAnnotation);
        } else if (expression instanceof TDecisionTable) {
            result = evaluateDecisionTable(element, (TDecisionTable)expression, environment, runtimeEnvironment, elementAnnotation);
        } else if (expression instanceof TFunctionDefinition) {
            result = evaluateFunctionDefinitionExpression((TFunctionDefinition)expression, environment, runtimeEnvironment, element, elementAnnotation);
        } else if (expression instanceof TInvocation) {
            result = evaluateInvocationExpression(element, (TInvocation) expression, environment, runtimeEnvironment, elementAnnotation);
        } else if (expression instanceof TLiteralExpression) {
            result = evaluateLiteralExpression(element, (TLiteralExpression) expression, environment, runtimeEnvironment, elementAnnotation);
        } else if (expression instanceof TList) {
            result = evaluateListExpression(element, (TList) expression, environment, runtimeEnvironment, elementAnnotation);
        } else if (expression instanceof TRelation) {
            result = evaluateRelationExpression(element, (TRelation) expression, environment, runtimeEnvironment, elementAnnotation);
        } else {
            handleError(String.format("Expression '%s' not supported yet", expression.getClass().getSimpleName()));
        }
        return result;
    }

    private Result evaluateLiteralExpression(TDRGElement element, TLiteralExpression expression, Environment environment, RuntimeEnvironment runtimeEnvironment, DRGElement elementAnnotation) {
        String text = expression.getText();
        Result result = evaluateLiteralExpression(element, text, environment, runtimeEnvironment);
        return result;
    }

    protected Result evaluateLiteralExpression(TDRGElement element, String text, Environment environment, RuntimeEnvironment runtimeEnvironment) {
        FEELContext context = FEELContext.makeContext(element, environment, runtimeEnvironment);
        Result result = this.feelInterpreter.evaluateExpression(text, context);
        return result;
    }

    private Result evaluateInvocationExpression(TDRGElement element, TInvocation invocation, Environment environment, RuntimeEnvironment runtimeEnvironment, DRGElement elementAnnotation) {
        // Compute name-java binding for arguments
        Map<String, Object> argBinding = new LinkedHashMap<>();
        for(TBinding binding: invocation.getBinding()) {
            String argName = binding.getParameter().getName();
            TExpression argExpression = binding.getExpression().getValue();
            Result argResult = evaluateExpression(element, argExpression, environment, runtimeEnvironment, elementAnnotation);
            Object argJava = Result.value(argResult);
            argBinding.put(argName, argJava);
        }

        // Evaluate body
        TExpression body = invocation.getExpression().getValue();
        if (body instanceof TLiteralExpression) {
            String bkmName = ((TLiteralExpression) body).getText();
            TBusinessKnowledgeModel bkm = this.dmnModelRepository.findKnowledgeModelByName(bkmName);
            if (bkm == null) {
                throw new DMNRuntimeException(String.format("Cannot find BKM for '%s'", bkmName));
            }
            List<Object> argList = new ArrayList<>();
            List<String> formalParameterList = this.basicDMNTransformer.bkmFEELParameterNames(bkm);
            for(String paramName: formalParameterList) {
                if (argBinding.containsKey(paramName)) {
                    Object argValue = argBinding.get(paramName);
                    argList.add(argValue);
                } else {
                    throw new DMNRuntimeException(String.format("Cannot find binding for parameter '%s'", paramName));
                }
            }
            Environment parentEnvironment = this.basicDMNTransformer.makeEnvironment(element);
            FEELContext context = FEELContext.makeContext(element, parentEnvironment, runtimeEnvironment);
            return evaluate(this.dmnModelRepository.makeDRGElementReference(bkm), argList, context);
        } else {
            throw new UnsupportedOperationException(String.format("Not supported '%s'", body.getClass().getSimpleName()));
        }
    }

    private Result evaluateContextExpression(TDRGElement element, TContext context, Environment environment, RuntimeEnvironment runtimeEnvironment, DRGElement elementAnnotation) {
        TDefinitions model = this.dmnModelRepository.getModel(element);
        // Make context environment
        Pair<Environment, Map<TContextEntry, Expression>> pair = this.basicDMNTransformer.makeContextEnvironment(element, context, environment);
        Environment contextEnvironment = pair.getLeft();
        Map<TContextEntry, Expression> literalExpressionMap = pair.getRight();

        // Evaluate entries
        RuntimeEnvironment contextRuntimeEnvironment = this.runtimeEnvironmentFactory.makeEnvironment(runtimeEnvironment);
        FEELContext feelContext = FEELContext.makeContext(element, contextEnvironment, contextRuntimeEnvironment);
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
                    entryResult = evaluateExpression(element, expression, contextEnvironment, contextRuntimeEnvironment, elementAnnotation);
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
                    type.addMember(entryName, new ArrayList<>(), this.basicDMNTransformer.toFEELType(model, variable.getTypeRef()));
                }
            }
            // Return value
            return new Result(output, type);
        }
    }

    private Result evaluateListExpression(TDRGElement element, TList list, Environment environment, RuntimeEnvironment runtimeEnvironment, DRGElement elementAnnotation) {
        TDefinitions model = this.dmnModelRepository.getModel(element);
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
                expResult = evaluateExpression(element, exp, environment, runtimeEnvironment, elementAnnotation);
                elementType = this.basicDMNTransformer.toFEELType(model, exp.getTypeRef());
            }
            resultValue.add(Result.value(expResult));
        }
        return new Result(resultValue, new ListType(elementType));
    }

    private Result evaluateRelationExpression(TDRGElement element, TRelation relation, Environment environment, RuntimeEnvironment runtimeEnvironment, DRGElement elementAnnotation) {
        if (relation.getRow() == null || relation.getColumn() == null) {
            return null;
        }

        // Make relation environment
        TDefinitions model = this.dmnModelRepository.getModel(element);
        Environment relationEnvironment = this.basicDMNTransformer.makeRelationEnvironment(model, relation, environment);

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
                    Result columnResult = expression == null ? null : evaluateExpression(element, expression, relationEnvironment, runtimeEnvironment, elementAnnotation);
                    Object columnValue = Result.value(columnResult);
                    contextValue.add(columnNameList.get(i), columnValue);
                    Type type = columnResult == null ? ANY : columnResult.getType();
                    contextType.addMember(columnNameList.get(i), new ArrayList<>(), type);
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

    private Result evaluateDecisionTable(TDRGElement element, TDecisionTable decisionTable, Environment environment, RuntimeEnvironment runtimeEnvironment, DRGElement elementAnnotation) {
        // Evaluate InputClauses
        List<InputClausePair> inputClauseList = new ArrayList<>();
        for (TInputClause inputClause : decisionTable.getInput()) {
            TLiteralExpression inputExpression = inputClause.getInputExpression();
            String inputExpressionText = inputExpression.getText();
            FEELContext feelContext = FEELContext.makeContext(element, environment, runtimeEnvironment);
            Expression expression = this.feelInterpreter.analyzeExpression(inputExpressionText, feelContext);
            Result inputExpressionResult = this.feelInterpreter.evaluateExpression(expression, feelContext);
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
        return new Result(value, this.basicDMNTransformer.drgElementOutputFEELType(element));
    }

    private InterpretedRuleOutput evaluateRule(TDRGElement element, TDecisionTable decisionTable, TDecisionRule rule, List<InputClausePair> inputClauseList, Environment environment, RuntimeEnvironment runtimeEnvironment, DRGElement elementAnnotation, Rule ruleAnnotation) {
        // Check tests
        List<TUnaryTests> inputEntry = rule.getInputEntry();
        boolean ruleMatched = true;
        for (int index = 0; index < inputEntry.size(); index++) {
            TUnaryTests unaryTest = inputEntry.get(index);
            String text = unaryTest.getText();
            Environment inputEntryEnvironment = this.basicDMNTransformer.makeInputEntryEnvironment(element, inputClauseList.get(index).getExpression());
            RuntimeEnvironment inputEntryRuntimeEnvironment = this.runtimeEnvironmentFactory.makeInputEntryEnvironment(inputClauseList, runtimeEnvironment, index);
            FEELContext context = FEELContext.makeContext(element, inputEntryEnvironment, inputEntryRuntimeEnvironment);
            Expression ast = this.feelInterpreter.analyzeUnaryTests(text, context);
            Result result = this.feelInterpreter.evaluateUnaryTests((UnaryTests) ast, context);
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
            if (this.dmnModelRepository.isCompoundDecisionTable(element)) {
                Context output = new Context();
                List<TLiteralExpression> outputEntry = rule.getOutputEntry();
                for (int i = 0; i < outputEntry.size(); i++) {
                    TLiteralExpression literalExpression = outputEntry.get(i);
                    String key = decisionTable.getOutput().get(i).getName();
                    Result result = evaluateLiteralExpression(element, literalExpression, environment, runtimeEnvironment, elementAnnotation);
                    Object value = Result.value(result);
                    if (this.dmnModelRepository.isOutputOrderHit(hitPolicy)) {
                        Object priority = this.basicDMNTransformer.priority(element, rule.getOutputEntry().get(i), i);
                        output.put(key, new Pair<>(value, priority));
                    } else {
                        output.put(key, new Pair<>(value, null));
                    }
                }
                return new InterpretedRuleOutput(ruleMatched, output);
            } else {
                List<TLiteralExpression> outputEntry = rule.getOutputEntry();
                TLiteralExpression literalExpression = outputEntry.get(0);
                Object output;
                Result result = evaluateLiteralExpression(element, literalExpression, environment, runtimeEnvironment, elementAnnotation);
                Object value = Result.value(result);
                if (this.dmnModelRepository.isOutputOrderHit(hitPolicy)) {
                    Object priority = this.basicDMNTransformer.priority(element, rule.getOutputEntry().get(0), 0);
                    output = new Pair<>(value, priority);
                } else {
                    output = new Pair<>(value, null);
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
                value = this.feelLib.asElement((List)value);
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
            if (((List<Object>) o).stream().anyMatch(t -> t == null || Boolean.FALSE.equals(o))) {
                return false;
            }
        }
        return o == null || Boolean.FALSE.equals(o);
    }

    private Object applyHitPolicy(TDRGElement element, TDecisionTable decisionTable, RuleOutputList ruleOutputList, Environment environment, RuntimeEnvironment runtimeEnvironment, DRGElement elementAnnotation) {
        if (ruleOutputList.noMatchedRules()) {
            return evaluateDefaultValue(element, decisionTable, this.basicDMNTransformer, environment, runtimeEnvironment, elementAnnotation);
        } else {
            THitPolicy hitPolicy = decisionTable.getHitPolicy();
            if (this.dmnModelRepository.isSingleHit(hitPolicy)) {
                InterpretedRuleOutput ruleOutput = (InterpretedRuleOutput) ruleOutputList.applySingle(HitPolicy.fromValue(hitPolicy.value()));
                return toDecisionOutput(element, decisionTable, ruleOutput);
            } else if (this.dmnModelRepository.isMultipleHit(hitPolicy)) {
                List<? extends RuleOutput> ruleOutputs = ruleOutputList.applyMultiple(HitPolicy.fromValue(hitPolicy.value()));
                if (this.dmnModelRepository.isCompoundDecisionTable(element)) {
                    if (this.dmnModelRepository.hasAggregator(decisionTable)) {
                        return null;
                    } else {
                        return ruleOutputs.stream().map(r -> toDecisionOutput(element, decisionTable, (InterpretedRuleOutput) r)).collect(Collectors.toList());
                    }
                } else {
                    List decisionOutput = ruleOutputs.stream().map(r -> toDecisionOutput(element, decisionTable, (InterpretedRuleOutput) r)).collect(Collectors.toList());
                    if (this.dmnModelRepository.hasAggregator(decisionTable)) {
                        TBuiltinAggregator aggregation = decisionTable.getAggregation();
                        if (aggregation == TBuiltinAggregator.MIN) {
                            return this.feelLib.min(decisionOutput);
                        } else if (aggregation == TBuiltinAggregator.MAX) {
                            return this.feelLib.max(decisionOutput);
                        } else if (aggregation == TBuiltinAggregator.COUNT) {
                            return this.feelLib.number(String.format("%d", decisionOutput.size()));
                        } else if (aggregation == TBuiltinAggregator.SUM) {
                            return this.feelLib.sum(decisionOutput);
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

    private Object evaluateDefaultValue(TDRGElement element, TDecisionTable decisionTable, BasicDMNToNativeTransformer dmnTransformer, Environment environment, RuntimeEnvironment runtimeEnvironment, DRGElement elementAnnotation) {
        if (this.dmnModelRepository.hasDefaultValue(decisionTable)) {
            // Evaluate and collect default values
            List<TOutputClause> outputClauses = decisionTable.getOutput();
            Context defaultValue = new Context();
            for (TOutputClause output : outputClauses) {
                TLiteralExpression defaultOutputEntry = output.getDefaultOutputEntry();
                String key = this.dmnModelRepository.outputClauseName(element, output);
                if (defaultOutputEntry == null) {
                    defaultValue.put(key, null);
                } else {
                    Result result = evaluateLiteralExpression(element, defaultOutputEntry, environment, runtimeEnvironment, elementAnnotation);
                    Object value = Result.value(result);
                    defaultValue.put(key, value);
                }
            }
            // Return result
            if (this.dmnModelRepository.isCompoundDecisionTable(element)) {
                if (dmnTransformer.hasListType(element)) {
                    return Arrays.asList(defaultValue);
                } else {
                    return defaultValue;
                }
            } else {
                String key = this.dmnModelRepository.outputClauseName(element, decisionTable.getOutput().get(0));
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
    // Binding
    //
    private Object lookupBinding(RuntimeEnvironment runtimeEnvironment, DRGElementReference<? extends TDRGElement> reference) {
        if (this.basicDMNTransformer.isSingletonInputData()) {
            Object value = runtimeEnvironment.lookupBinding(this.basicDMNTransformer.bindingName(reference));
            return value;
        } else {
            ImportPath importPath = reference.getImportPath();
            String name = reference.getElementName();

            if (ImportPath.isEmpty(importPath)) {
                return runtimeEnvironment.lookupBinding(name);
            } else {
                List<String> pathElements = importPath.getPathElements();
                // Lookup root context
                String rootName = pathElements.get(0);
                Object obj = runtimeEnvironment.lookupBinding(rootName);
                if (obj instanceof Context) {
                    // Lookup inner contexts
                    Context parentContext = (Context) obj;
                    for (int i = 1; i < pathElements.size(); i++) {
                        String childName = pathElements.get(i);
                        Context childContext = (Context) parentContext.get(childName);
                        if (childContext == null) {
                            childContext = new Context();
                            parentContext.put(childName, childContext);
                        }
                        parentContext = childContext;
                    }
                    // lookup name
                    return parentContext.get(name);
                } else {
                    throw new DMNRuntimeException(String.format("Context value expected, found '%s'", obj.getClass().getSimpleName()));
                }
            }
        }
    }

    private void bind(RuntimeEnvironment runtimeEnvironment, DRGElementReference<? extends TDRGElement> reference, Object value) {
        if (this.basicDMNTransformer.isSingletonInputData()) {
            runtimeEnvironment.bind(this.basicDMNTransformer.bindingName(reference), value);
        } else {
            ImportPath importPath = reference.getImportPath();
            String name = reference.getElementName();

            if (ImportPath.isEmpty(importPath)) {
                runtimeEnvironment.bind(name, value);
            } else {
                try {
                    List<String> pathElements = importPath.getPathElements();
                    // lookup or bind root context
                    String rootName = pathElements.get(0);
                    Context parentContext = (Context) runtimeEnvironment.lookupBinding(rootName);
                    if (parentContext == null) {
                        parentContext = new Context();
                        runtimeEnvironment.bind(rootName, parentContext);
                    }
                    // lookup or bind inner contexts
                    for (int i = 1; i < pathElements.size(); i++) {
                        String childName = pathElements.get(i);
                        Context childContext = (Context) parentContext.get(childName);
                        if (childContext == null) {
                            childContext = new Context();
                            parentContext.put(childName, childContext);
                        }
                        parentContext = childContext;
                    }
                    // bind name -> value
                    parentContext.put(name, value);
                } catch (Exception e) {
                    throw new DMNRuntimeException(String.format("cannot bind value to '%s.%s'", importPath.asString(), name), e);
                }
            }
        }
    }

    // Add binding required by parent: importName.elementName
    private void addBinding(RuntimeEnvironment runtimeEnvironment, DRGElementReference<? extends TDRGElement> reference, String importName) {
        if (this.basicDMNTransformer.isSingletonInputData()) {
            String name = reference.getElementName();
            Object value = lookupBinding(runtimeEnvironment, reference);
            if (ImportPath.isEmpty(importName)) {
                runtimeEnvironment.bind(name, value);
            } else {
                // Lookup / bind import name
                Context parentContext = (Context) runtimeEnvironment.lookupBinding(importName);
                if (parentContext == null) {
                    parentContext = new Context();
                    runtimeEnvironment.bind(importName, parentContext);
                }
                // bind name -> value
                parentContext.put(name, value);
            }
        } else {
            ImportPath importPath = reference.getImportPath();
            if (!ImportPath.isEmpty(importPath)) {
                Object value = lookupBinding(runtimeEnvironment, reference);
                if (ImportPath.isEmpty(importName)) {
                    runtimeEnvironment.bind(reference.getElementName(), value);
                } else {
                    bind(runtimeEnvironment, this.dmnModelRepository.makeDRGElementReference(new ImportPath(importName), reference.getElement()), value);
                }
            }
        }
    }

    //
    // Logging
    //
    protected DRGElement makeDRGElementAnnotation(TDRGElement element, RuntimeEnvironment runtimeEnvironment) {
        return new DRGElement(null,
                this.dmnModelRepository.name(element),
                this.dmnModelRepository.label(element),
                this.basicDMNTransformer.elementKind(element),
                this.basicDMNTransformer.expressionKind(element),
                this.basicDMNTransformer.hitPolicy(element),
                this.dmnModelRepository.rulesCount(element)
        );
    }

    private Arguments makeArguments(TDRGElement element, RuntimeEnvironment runtimeEnvironment) {
        Arguments arguments = new Arguments();
        DRGElementReference<? extends TDRGElement> reference = this.dmnModelRepository.makeDRGElementReference(element);
        List<String> parameters = this.basicDMNTransformer.drgElementArgumentDisplayNameList(reference);
        parameters.forEach(p -> arguments.put(p, runtimeEnvironment.lookupBinding(p)));
        return arguments;
    }

    private Rule makeRuleAnnotation(TDecisionRule rule, int ruleIndex) {
        return new Rule(ruleIndex, this.basicDMNTransformer.annotationEscapedText(rule));
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
