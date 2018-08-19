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
import com.gs.dmn.feel.analysis.semantics.type.ListType;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.test.UnaryTests;
import com.gs.dmn.feel.interpreter.FEELInterpreter;
import com.gs.dmn.feel.interpreter.FEELInterpreterImpl;
import com.gs.dmn.feel.lib.FEELLib;
import com.gs.dmn.runtime.*;
import com.gs.dmn.runtime.annotation.HitPolicy;
import com.gs.dmn.runtime.interpreter.environment.RuntimeEnvironment;
import com.gs.dmn.runtime.interpreter.environment.RuntimeEnvironmentFactory;
import com.gs.dmn.runtime.listener.Arguments;
import com.gs.dmn.runtime.listener.*;
import com.gs.dmn.runtime.listener.EventListener;
import com.gs.dmn.transformation.basic.BasicDMN2JavaTransformer;
import com.gs.dmn.transformation.basic.QualifiedName;
import org.omg.spec.dmn._20180521.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBElement;
import java.util.*;
import java.util.stream.Collectors;

public class DMNInterpreter {
    private static final Logger LOGGER = LoggerFactory.getLogger(DMNInterpreter.class);
    protected static EventListener EVENT_LISTENER = new LoggingEventListener(LOGGER);
    protected final RuntimeEnvironmentFactory runtimeEnvironmentFactory = RuntimeEnvironmentFactory.instance();
    private final DMNModelRepository dmnModelRepository;
    private final EnvironmentFactory environmentFactory;

    public static void setEventListener(EventListener eventListener) {
        EVENT_LISTENER = eventListener;
    }

    private final BasicDMN2JavaTransformer basicDMNTransformer;
    private final FEELLib feelLib;
    private final FEELInterpreter feelInterpreter;

    public DMNInterpreter(BasicDMN2JavaTransformer basicDMNTransformer, FEELLib feelLib) {
        this.basicDMNTransformer = basicDMNTransformer;
        this.dmnModelRepository = basicDMNTransformer.getDMNModelRepository();
        this.environmentFactory = basicDMNTransformer.getEnvironmentFactory();
        this.feelLib = feelLib;
        this.feelInterpreter = new FEELInterpreterImpl(this);
    }

    public BasicDMN2JavaTransformer getBasicDMNTransformer() {
        return basicDMNTransformer;
    }

    public FEELLib getFeelLib() {
        return feelLib;
    }

    public Object evaluate(String drgElementName, RuntimeEnvironment runtimeEnvironment) {
        TDRGElement drgElement = dmnModelRepository.findDRGElementByName(drgElementName);
        evaluate(drgElement, runtimeEnvironment);
        return runtimeEnvironment.lookupBinding(drgElementName);
    }

    private void evaluate(TDRGElement drgElement, RuntimeEnvironment runtimeEnvironment) {
        if (drgElement instanceof TInputData) {
        } else if (drgElement instanceof TBusinessKnowledgeModel) {
        } else if (drgElement instanceof TDecision) {
            evaluateDecision((TDecision) drgElement, runtimeEnvironment);
        } else {
            handleError(String.format("DRG Element '%s' not supported yet", drgElement.getClass()));
        }
    }

    public Object evaluateBKM(TBusinessKnowledgeModel bkm, List<Object> argList, FEELContext context) {
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
            bkmEnvironment.addDeclaration(environmentFactory.makeVariableDeclaration(name, type));
            bkmRuntimeEnvironment.bind(name, value);
        }

        // Execute function body
        TExpression expression = dmnModelRepository.expression(bkm);
        Object output = evaluateExpression(expression, bkmEnvironment, bkmRuntimeEnvironment, bkm, drgElementAnnotation);

        // Decision end
        EVENT_LISTENER.endDRGElement(drgElementAnnotation, decisionArguments, output, (System.currentTimeMillis() - startTime_));

        return output;
    }

    public Object evaluateFunctionDefinition(TFunctionDefinition functionDefinition, List<Object> argList, FEELContext context) {
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
        Object output;
        if (expressionElement == null) {
            output = null;
        } else {
            TExpression expression = expressionElement.getValue();
            output = evaluateExpression(expression, functionEnvironment, functionRuntimeEnvironment, null, null);
        }

        return output;
    }

    private void evaluateBKMRequirements(List<TKnowledgeRequirement> knowledgeRequirementList, RuntimeEnvironment runtimeEnvironment) {
        for (TKnowledgeRequirement requirement : knowledgeRequirementList) {
            TDMNElementReference requiredKnowledge = requirement.getRequiredKnowledge();
            String href = requiredKnowledge.getHref();
            TBusinessKnowledgeModel childBKM = this.dmnModelRepository.findKnowledgeModelById(href);
            evaluateBKM(childBKM, runtimeEnvironment);
        }
    }

    private void evaluateBKM(TBusinessKnowledgeModel bkm, RuntimeEnvironment runtimeEnvironment) {
        // Evaluate knowledge requirements
        List<TKnowledgeRequirement> knowledgeRequirement = bkm.getKnowledgeRequirement();
        evaluateBKMRequirements(knowledgeRequirement, runtimeEnvironment);

        // Bind name to
        String bkmName = bkm.getName();
        if (bkmName == null) {
            bkmName = bkm.getVariable().getName();
        }
        runtimeEnvironment.bind(bkmName, bkm);
    }

    protected void evaluateDecision(TDecision decision, RuntimeEnvironment runtimeEnvironment) {
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
            output = runtimeEnvironment.lookupBinding(decisionName);
        } else {
            // Evaluate dependencies
            evaluateInformationRequirementList(decision.getInformationRequirement(), runtimeEnvironment);
            evaluateBKMRequirements(decision.getKnowledgeRequirement(), runtimeEnvironment);

            // Evaluate expression
            TExpression expression = dmnModelRepository.expression(decision);
            Environment environment = basicDMNTransformer.makeEnvironment(decision);
            output = evaluateExpression(expression, environment, runtimeEnvironment, decision, drgElementAnnotation);

            // Set variable
            runtimeEnvironment.bind(decisionName, output);
        }

        // Decision end
        EVENT_LISTENER.endDRGElement(drgElementAnnotation, decisionArguments, output, (System.currentTimeMillis() - startTime_));
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
                TDecision child = dmnModelRepository.findDecisionById(requiredDecision.getHref());
                evaluateDecision(child, runtimeEnvironment);
            } else {
                handleError("Incorrect InformationRequirement. Missing required input and decision");
            }
        }
    }

    //
    // Expression evaluation
    //
    public Object evaluateLiteralExpression(String text, Environment environment, RuntimeEnvironment runtimeEnvironment) {
        return this.evaluateLiteralExpression(text, environment, runtimeEnvironment, null);
    }

    protected Object evaluateExpression(TExpression expression, Environment environment, RuntimeEnvironment runtimeEnvironment, TDRGElement element, DRGElement elementAnnotation) {
        Object output = null;
        if (expression == null) {
            handleError(String.format("Missing expression for element '%s'", element == null ? null : element.getName()));
        } else if (expression instanceof TContext) {
            output = evaluateContextExpression((TContext) expression, environment, runtimeEnvironment, element, elementAnnotation);
        } else if (expression instanceof TDecisionTable) {
            output = evaluateDecisionTable((TDecisionTable)expression, environment, runtimeEnvironment, element, elementAnnotation);
        } else if (expression instanceof TFunctionDefinition) {
            output = evaluateFunctionDefinitionExpression((TFunctionDefinition)expression, environment, runtimeEnvironment, element, elementAnnotation);
        } else if (expression instanceof TInvocation) {
            output = evaluateInvocationExpression((TInvocation) expression, environment, runtimeEnvironment, element, elementAnnotation);
        } else if (expression instanceof TLiteralExpression) {
            output = evaluateLiteralExpression((TLiteralExpression) expression, environment, runtimeEnvironment, element, elementAnnotation);
        } else if (expression instanceof TList) {
            output = evaluateListExpression((TList) expression, environment, runtimeEnvironment, element, elementAnnotation);
        } else if (expression instanceof TRelation) {
            output = evaluateRelationExpression((TRelation) expression, environment, runtimeEnvironment, element, elementAnnotation);
        } else {
            handleError(String.format("Expression '%s' not supported yet", expression.getClass().getSimpleName()));
        }
        return output;
    }

    private Object evaluateLiteralExpression(TLiteralExpression expression, Environment environment, RuntimeEnvironment runtimeEnvironment, TDRGElement element, DRGElement elementAnnotation) {
        String text = expression.getText();
        Object value = evaluateLiteralExpression(text, environment, runtimeEnvironment, element);
        if (element == null) {
            return value;
        } else {
            Type expectedType = basicDMNTransformer.drgElementOutputFEELType(element, environment);
            if (expectedType != null) {
                return convertExpression(value, expectedType);
            } else {
                return null;
            }
        }
    }

    protected Object evaluateLiteralExpression(String text, Environment environment, RuntimeEnvironment runtimeEnvironment, TDRGElement element) {
        FEELContext context = FEELContext.makeContext(environment, runtimeEnvironment);
        return feelInterpreter.evaluateExpression(text, context);
    }

    private Object evaluateInvocationExpression(TInvocation invocation, Environment environment, RuntimeEnvironment runtimeEnvironment, TDRGElement element, DRGElement elementAnnotation) {
        // Compute name-java binding for arguments
        Map<String, Object> argBinding = new LinkedHashMap<>();
        for(TBinding binding: invocation.getBinding()) {
            String argName= binding.getParameter().getName();
            TExpression argExpression = binding.getExpression().getValue();
            Object argJava = evaluateExpression(argExpression, environment, runtimeEnvironment, element, elementAnnotation);
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
            return evaluateBKM(bkm, argList, context);
        } else {
            throw new UnsupportedOperationException(String.format("Not supported '%s'", body.getClass().getSimpleName()));
        }
    }

    private Object evaluateContextExpression(TContext context, Environment environment, RuntimeEnvironment runtimeEnvironment, TDRGElement element, DRGElement elementAnnotation) {
        // Make context environment
        Pair<Environment, Map<TContextEntry, Expression>> pair = basicDMNTransformer.makeContextEnvironment(context, environment);
        Environment contextEnvironment = pair.getLeft();
        Map<TContextEntry, Expression> literalExpressionMap = pair.getRight();

        // Evaluate entries
        RuntimeEnvironment contextRuntimeEnvironment = runtimeEnvironmentFactory.makeEnvironment(runtimeEnvironment);
        FEELContext feelContext = FEELContext.makeContext(contextEnvironment, contextRuntimeEnvironment);
        Object returnValue = null;
        for(TContextEntry entry: context.getContextEntry()) {
            // Evaluate entry value
            Object entryValue;
            JAXBElement<? extends TExpression> jaxbElement = entry.getExpression();
            if (jaxbElement != null) {
                TExpression expression = jaxbElement.getValue();
                if (expression instanceof TLiteralExpression) {
                    Expression feelExpression = literalExpressionMap.get(entry);
                    entryValue = this.feelInterpreter.evaluateExpression(feelExpression, feelContext);
                } else {
                    entryValue = evaluateExpression(expression, contextEnvironment, contextRuntimeEnvironment, element, elementAnnotation);
                }
            } else {
                entryValue = null;
            }

            // Add runtime binding
            TInformationItem variable = entry.getVariable();
            if (variable != null) {
                String entryName = variable.getName();
                contextRuntimeEnvironment.bind(entryName, entryValue);
            } else {
                returnValue = entryValue;
            }
        }

        // Return result
        if (returnValue != null) {
            return returnValue;
        } else {
            // Make complex type value
            Context output = new Context();
            // Add entries
            for(TContextEntry entry: context.getContextEntry()) {
                TInformationItem variable = entry.getVariable();
                if (variable != null) {
                    String entryName = variable.getName();
                    output.add(entryName, contextRuntimeEnvironment.lookupBinding(entryName));
                }
            }
            // Return value
            return output;
        }
    }

    private Object evaluateListExpression(TList list, Environment environment, RuntimeEnvironment runtimeEnvironment, TDRGElement element, DRGElement elementAnnotation) {
        if (list.getExpression() == null) {
            return null;
        }
        List<Object> result = new ArrayList<>();
        for(JAXBElement<? extends TExpression> expElement: list.getExpression()) {
            Object expValue;
            if (expElement == null) {
                expValue = null;
            } else {
                expValue = evaluateExpression(expElement.getValue(), environment, runtimeEnvironment, element, elementAnnotation);
            }
            result.add(expValue);
        }
        return result;
    }

    private Object evaluateRelationExpression(TRelation relation, Environment environment, RuntimeEnvironment runtimeEnvironment, TDRGElement element, DRGElement elementAnnotation) {
        if (relation.getRow() == null || relation.getColumn() == null) {
            return null;
        }

        // Make relation environment
        Environment relationEnvironment = basicDMNTransformer.makeRelationEnvironment(relation, environment);

        // Column names
        List<String> columnNameList = relation.getColumn().stream().map(TNamedElement::getName).collect(Collectors.toList());

        // Scan relation and evaluate each row
        List<Object> relationValue = new ArrayList<>();
        for(TList row: relation.getRow()) {
            Object rowValue = null;
            List<JAXBElement<? extends TExpression>> jaxbElementList = row.getExpression();
            if (jaxbElementList != null) {
                Context context = new Context();
                for(int i = 0; i < jaxbElementList.size(); i++) {
                    JAXBElement<? extends TExpression> jaxbElement = jaxbElementList.get(i);
                    TExpression expression = jaxbElement == null ? null : jaxbElement.getValue();
                    Object columnValue = expression == null ? null : evaluateExpression(expression, relationEnvironment, runtimeEnvironment, element, elementAnnotation);
                    context.add(columnNameList.get(i), columnValue);
                }
                rowValue = context;
            }
            relationValue.add(rowValue);
        }
        return relationValue;
    }

    private Object evaluateFunctionDefinitionExpression(TFunctionDefinition expression, Environment environment, RuntimeEnvironment runtimeEnvironment, TDRGElement element, DRGElement elementAnnotation) {
        return expression;
    }

    private Object evaluateDecisionTable(TDecisionTable decisionTable, Environment environment, RuntimeEnvironment runtimeEnvironment, TDRGElement element, DRGElement elementAnnotation) {
        // Evaluate InputClauses
        List<InputClausePair> inputClauseList = new ArrayList<>();
        for (TInputClause inputClause : decisionTable.getInput()) {
            TLiteralExpression inputExpression = inputClause.getInputExpression();
            String inputExpressionText = inputExpression.getText();
            FEELContext feelContext = FEELContext.makeContext(environment, runtimeEnvironment);
            Expression expression = feelInterpreter.analyzeExpression(inputExpressionText, feelContext);
            Object inputExpressionValue = feelInterpreter.evaluateExpression(expression, feelContext);
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
        return applyHitPolicy(element, decisionTable, ruleOutputList, environment, runtimeEnvironment, elementAnnotation);
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
            Object testMatched = feelInterpreter.evaluateUnaryTests((UnaryTests) ast, context);
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
                    Object value = evaluateLiteralExpression(literalExpression, environment, runtimeEnvironment, element, elementAnnotation);
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
                Object value = evaluateLiteralExpression(literalExpression, environment, runtimeEnvironment, element, elementAnnotation);
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

    private Object convertExpression(Object value, Type expectedType) {
        if (value == null) {
            return null;
        }
        if (value instanceof List && ((List) value).size() == 1 && !(expectedType instanceof ListType)) {
            return feelLib.asElement((List)value);
        }
        return value;
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
                    Object value = evaluateLiteralExpression(defaultOutputEntry, environment, runtimeEnvironment, element, elementAnnotation);
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
