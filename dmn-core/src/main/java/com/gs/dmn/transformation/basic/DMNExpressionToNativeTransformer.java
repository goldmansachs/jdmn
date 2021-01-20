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
package com.gs.dmn.transformation.basic;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.DRGElementReference;
import com.gs.dmn.feel.analysis.semantics.environment.Environment;
import com.gs.dmn.feel.analysis.semantics.environment.EnvironmentFactory;
import com.gs.dmn.feel.analysis.semantics.type.*;
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.Context;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.ContextEntry;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FunctionDefinition;
import com.gs.dmn.feel.analysis.syntax.ast.expression.literal.StringLiteral;
import com.gs.dmn.feel.lib.StringEscapeUtil;
import com.gs.dmn.feel.synthesis.FEELTranslator;
import com.gs.dmn.feel.synthesis.type.NativeTypeFactory;
import com.gs.dmn.runtime.*;
import com.gs.dmn.runtime.annotation.HitPolicy;
import com.gs.dmn.runtime.annotation.Rule;
import com.gs.dmn.runtime.external.JavaExternalFunction;
import com.gs.dmn.runtime.external.JavaFunctionInfo;
import com.gs.dmn.transformation.DMNToJavaTransformer;
import com.gs.dmn.transformation.native_.NativeFactory;
import com.gs.dmn.transformation.native_.statement.CompoundStatement;
import com.gs.dmn.transformation.native_.statement.ExpressionStatement;
import com.gs.dmn.transformation.native_.statement.Statement;
import org.apache.commons.lang3.StringUtils;
import org.omg.spec.dmn._20180521.model.*;

import javax.xml.bind.JAXBElement;
import java.util.*;
import java.util.stream.Collectors;

import static com.gs.dmn.transformation.DMNToJavaTransformer.DECISION_RULE_OUTPUT_CLASS_SUFFIX;

public class DMNExpressionToNativeTransformer {
    private final String indent = "\t\t\t\t";

    private final BasicDMNToNativeTransformer dmnTransformer;
    private final DMNModelRepository dmnModelRepository;
    private final FEELTranslator feelTranslator;
    private final EnvironmentFactory environmentFactory;
    private final DMNEnvironmentFactory dmnEnvironmentFactory;
    private final NativeTypeFactory nativeTypeFactory;
    private final NativeFactory nativeFactory;

    DMNExpressionToNativeTransformer(BasicDMNToNativeTransformer dmnTransformer) {
        this.dmnTransformer = dmnTransformer;

        this.dmnModelRepository = dmnTransformer.getDMNModelRepository();
        this.environmentFactory = dmnTransformer.getEnvironmentFactory();

        this.feelTranslator = dmnTransformer.getFEELTranslator();
        this.nativeFactory = dmnTransformer.getNativeFactory();
        this.dmnEnvironmentFactory = dmnTransformer.getDMNEnvironmentFactory();
        this.nativeTypeFactory = dmnTransformer.getNativeTypeFactory();
    }

    //
    // TDecisionTable
    //
    String defaultValue(TDRGElement element) {
        if (this.dmnModelRepository.isDecisionTableExpression(element)) {
            Type feelType = this.dmnTransformer.drgElementOutputFEELType(element);
            TDecisionTable decisionTable = (TDecisionTable) this.dmnModelRepository.expression(element);
            if (this.dmnModelRepository.hasDefaultValue(decisionTable)) {
                if (this.dmnModelRepository.isCompoundDecisionTable(element)) {
                    List<String> values = new ArrayList<>();
                    List<TOutputClause> output = sortOutputClauses(element, new ArrayList<>(decisionTable.getOutput()));
                    for(TOutputClause outputClause: output) {
                        values.add(defaultValue(element, outputClause));
                    }
                    String defaultValue = this.dmnTransformer.constructor(this.dmnTransformer.itemDefinitionNativeClassName(this.dmnTransformer.drgElementOutputClassName(element)), String.join(", ", values));
                    if (this.dmnTransformer.hasListType(element)) {
                        return this.nativeFactory.asList(((ListType) feelType).getElementType(), defaultValue);
                    } else {
                        return defaultValue;
                    }
                } else {
                    TOutputClause outputClause = decisionTable.getOutput().get(0);
                    return defaultValue(element, outputClause);
                }
            } else {
                return "null";
            }
        } else {
            throw new DMNRuntimeException(String.format("Cannot compute default value for '%s' '%s'", element.getClass().getSimpleName(), element.getName()));
        }
    }

    String defaultValue(TDRGElement element, TOutputClause output) {
        TLiteralExpression defaultOutputEntry = output.getDefaultOutputEntry();
        if (defaultOutputEntry == null) {
            return "null";
        } else {
            return this.dmnTransformer.literalExpressionToNative(element, defaultOutputEntry.getText());
        }
    }

    String outputClauseClassName(TDRGElement element, TOutputClause outputClause, int index) {
        Type type = this.dmnEnvironmentFactory.toFEELType(element, outputClause, index);
        return this.dmnTransformer.toNativeType(type);
    }

    String outputClauseVariableName(TDRGElement element, TOutputClause outputClause) {
        String name = this.dmnModelRepository.outputClauseName(element, outputClause);
        if (name == null) {
            throw new DMNRuntimeException(String.format("Variable name cannot be null. OutputClause id '%s'", outputClause.getId()));
        }
        return this.dmnTransformer.lowerCaseFirst(name);
    }

    String outputClausePriorityVariableName(TDRGElement element, TOutputClause outputClause) {
        return outputClauseVariableName(element, outputClause) + DMNToJavaTransformer.PRIORITY_SUFFIX;
    }

    Integer priority(TDRGElement element, TLiteralExpression literalExpression, int outputIndex) {
        String outputEntryText = literalExpression.getText();
        TExpression tExpression = this.dmnModelRepository.expression(element);
        if (tExpression instanceof TDecisionTable) {
            TOutputClause tOutputClause = ((TDecisionTable) tExpression).getOutput().get(outputIndex);
            TUnaryTests outputValues = tOutputClause.getOutputValues();
            if (outputValues != null) {
                String text = outputValues.getText();
                String[] parts = text.split(",");
                Integer position = null;
                for (int i = 0; i < parts.length; i++) {
                    if (outputEntryText.equals(parts[i].trim())) {
                        position = i;
                        break;
                    }
                }
                if (position != null) {
                    return parts.length - position;
                }
            }
            return null;
        } else {
            throw new UnsupportedOperationException(String.format("Not supported '%s'", tExpression.getClass().getSimpleName()));
        }
    }

    String getter(TDRGElement element, TOutputClause output) {
        return this.dmnTransformer.getter(this.dmnTransformer.outputClauseVariableName(element, output));
    }

    String priorityGetter(TDRGElement element, TOutputClause output) {
        return this.dmnTransformer.getter(this.outputClausePriorityVariableName(element, output));
    }

    String setter(TDRGElement element, TOutputClause output) {
        return this.dmnTransformer.setter(this.dmnTransformer.outputClauseVariableName(element, output));
    }

    String prioritySetter(TDRGElement element, TOutputClause output) {
        return this.dmnTransformer.setter(this.outputClausePriorityVariableName(element, output));
    }

    private List<TOutputClause> sortOutputClauses(TDRGElement element, List<TOutputClause> parameters) {
        parameters.sort(Comparator.comparing(o -> this.dmnModelRepository.outputClauseName(element, o)));
        return parameters;
    }

    //
    // Aggregation and hit policy
    //
    String aggregator(TDRGElement element, TDecisionTable decisionTable, TOutputClause outputClause, String ruleOutputListVariableName) {
        TBuiltinAggregator aggregation = decisionTable.getAggregation();
        String decisionRuleOutputClassName = ruleOutputClassName(element);
        String outputClauseVariableName = this.dmnTransformer.outputClauseVariableName(element, outputClause);
        if (aggregation == TBuiltinAggregator.MIN) {
            return this.nativeFactory.makeMinAggregator(ruleOutputListVariableName, decisionRuleOutputClassName, outputClauseVariableName);
        } else if (aggregation == TBuiltinAggregator.MAX) {
            return this.nativeFactory.makeMaxAggregator(ruleOutputListVariableName, decisionRuleOutputClassName, outputClauseVariableName);
        } else if (aggregation == TBuiltinAggregator.COUNT) {
            return this.nativeFactory.makeCountAggregator(ruleOutputListVariableName);
        } else if (aggregation == TBuiltinAggregator.SUM) {
            return this.nativeFactory.makeSumAggregator(ruleOutputListVariableName, decisionRuleOutputClassName, outputClauseVariableName);
        } else {
            throw new UnsupportedOperationException(String.format("Not supported '%s' aggregation.", aggregation));
        }
    }

    HitPolicy hitPolicy(TDRGElement element) {
        TExpression expression = this.dmnModelRepository.expression(element);
        if (expression instanceof TDecisionTable) {
            THitPolicy hitPolicy = ((TDecisionTable) expression).getHitPolicy();
            return HitPolicy.fromValue(hitPolicy.value());
        } else {
            return HitPolicy.UNKNOWN;
        }
    }

    String aggregation(TDecisionTable decisionTable) {
        TBuiltinAggregator aggregation = decisionTable.getAggregation();
        return aggregation == null ? null : aggregation.value();
    }

    //
    // Rules
    //
    String ruleOutputClassName(TDRGElement element) {
        return this.dmnTransformer.upperCaseFirst(element.getName() + DECISION_RULE_OUTPUT_CLASS_SUFFIX);
    }

    String abstractRuleOutputClassName() {
        return RuleOutput.class.getName();
    }

    String ruleOutputListClassName() {
        return RuleOutputList.class.getName();
    }

    String ruleId(List<TDecisionRule> rules, TDecisionRule rule) {
        String id = rule.getId();
        return StringUtils.isBlank(id) ? Integer.toString(rules.indexOf(rule)) : id;
    }

    String ruleSignature(TDecision decision) {
        List<DRGElementReference<? extends TDRGElement>> references = this.dmnModelRepository.sortedUniqueInputs(decision, this.dmnTransformer.getDrgElementFilter());

        List<Pair<String, String>> parameters = new ArrayList<>();
        for (DRGElementReference<? extends TDRGElement> reference : references) {
            TDRGElement element = reference.getElement();
            String parameterName = ruleParameterName(reference);
            String parameterNativeType = this.dmnTransformer.lazyEvaluationType(element, this.dmnTransformer.parameterNativeType(element));
            parameters.add(new Pair<>(parameterName, parameterNativeType));
        }
        String signature = parameters.stream().map(p -> this.nativeFactory.nullableParameter(p.getRight(), p.getLeft())).collect(Collectors.joining(", "));
        return this.dmnTransformer.augmentSignature(signature);
    }

    String ruleArgumentList(TDecision decision) {
        List<DRGElementReference<? extends TDRGElement>> references = this.dmnModelRepository.sortedUniqueInputs(decision, this.dmnTransformer.getDrgElementFilter());

        List<String> arguments = new ArrayList<>();
        for (DRGElementReference<? extends TDRGElement> reference : references) {
            String argumentName = ruleArgumentName(reference);
            arguments.add(argumentName);
        }
        String argumentList = String.join(", ", arguments);
        return this.dmnTransformer.augmentArgumentList(argumentList);
    }

    String ruleSignature(TBusinessKnowledgeModel bkm) {
        List<Pair<String, String>> parameters = new ArrayList<>();
        TDefinitions model = this.dmnModelRepository.getModel(bkm);
        List<TInformationItem> formalParameters = bkm.getEncapsulatedLogic().getFormalParameter();
        for (TInformationItem parameter : formalParameters) {
            String parameterName = ruleParameterName(parameter);
            String parameterNativeType = this.dmnTransformer.parameterNativeType(model, parameter);
            parameters.add(new Pair<>(parameterName, parameterNativeType));
        }
        String signature = parameters.stream().map(p -> this.nativeFactory.nullableParameter(p.getRight(), p.getLeft())).collect(Collectors.joining(", "));
        return this.dmnTransformer.augmentSignature(signature);
    }

    String ruleArgumentList(TBusinessKnowledgeModel bkm) {
        List<String> arguments = new ArrayList<>();
        List<TInformationItem> formalParameters = bkm.getEncapsulatedLogic().getFormalParameter();
        for (TInformationItem element : formalParameters) {
            String argumentName = this.dmnTransformer.namedElementVariableName(element);
            arguments.add(argumentName);
        }
        String argumentList = String.join(", ", arguments);
        return this.dmnTransformer.augmentArgumentList(argumentList);
    }

    String ruleParameterName(DRGElementReference<? extends TDRGElement> reference) {
        return this.dmnTransformer.drgElementReferenceVariableName(reference);
    }

    String ruleParameterName(TInformationItem element) {
        return this.dmnTransformer.namedElementVariableName(element);
    }

    String ruleArgumentName(DRGElementReference<? extends TDRGElement> reference) {
        return this.dmnTransformer.drgElementReferenceVariableName(reference);
    }

    //
    // Rule condition
    //
    String condition(TDRGElement element, TDecisionRule rule) {
        TExpression decisionTable = this.dmnModelRepository.expression(element);
        if (decisionTable instanceof TDecisionTable) {
            // Build condition parts
            List<String> conditionParts = new ArrayList<>();
            for (int i = 0; i < rule.getInputEntry().size(); i++) {
                TUnaryTests inputEntry = rule.getInputEntry().get(i);
                String condition = condition(element, decisionTable, inputEntry, i);
                conditionParts.add(condition);
            }
            // Build rule matches call
            String indent3tabs = "            ";
            String indent2tabs = "        ";
            String operands = conditionParts.stream().collect(Collectors.joining(",\n" + indent3tabs));
            String eventListenerVariable = this.dmnTransformer.eventListenerVariableName();
            String ruleMetadataVariable = this.dmnTransformer.drgRuleMetadataFieldName();
            String condition = String.format("%s(%s, %s,\n%s%s\n%s)", ruleMatchesMethodName(), eventListenerVariable, ruleMetadataVariable, indent3tabs, operands, indent2tabs);
            return condition;
        }
        throw new DMNRuntimeException("Cannot build condition for " + decisionTable.getClass().getSimpleName());
    }

    private String ruleMatchesMethodName() {
        return "ruleMatches";
    }

    private String condition(TDRGElement element, TExpression decisionTable, TUnaryTests inputEntry, int inputEntryIndex) {
        TInputClause tInputClause = ((TDecisionTable) decisionTable).getInput().get(inputEntryIndex);
        String inputExpressionText = tInputClause.getInputExpression().getText();
        String inputEntryText = inputEntry.getText();
        try {
            return inputEntryToNative(element, inputExpressionText, inputEntryText);
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot build condition for input clause '%s' for entry '%s' in element '%s'", inputExpressionText, inputEntryText, element.getName()), e);
        }
    }

    private String inputEntryToNative(TDRGElement element, String inputExpressionText, String inputEntryText) {
        // Analyze input expression
        Environment inputExpressionEnvironment = this.dmnTransformer.makeEnvironment(element);
        FEELContext inputExpressionContext = FEELContext.makeContext(element, inputExpressionEnvironment);
        Expression inputExpression = this.feelTranslator.analyzeExpression(inputExpressionText, inputExpressionContext);

        // Generate code for input entry
        Environment inputEntryEnvironment = this.dmnTransformer.makeInputEntryEnvironment(element, inputExpression);
        FEELContext inputEntryContext = FEELContext.makeContext(element, inputEntryEnvironment);
        return this.feelTranslator.unaryTestsToJava(inputEntryText, inputEntryContext);
    }

    String outputEntryToNative(TDRGElement element, TLiteralExpression outputEntryExpression, int outputIndex) {
        TExpression tExpression = this.dmnModelRepository.expression(element);
        if (tExpression instanceof TDecisionTable) {
            // Analyze output expression
            String outputEntryText = outputEntryExpression.getText();
            Environment outputEntryEnvironment = this.dmnTransformer.makeOutputEntryEnvironment(element, this.environmentFactory);
            if ("-".equals(outputEntryText)) {
                outputEntryText = "null";
            }
            Expression feelOutputEntryExpression = this.feelTranslator.analyzeExpression(outputEntryText, FEELContext.makeContext(element, outputEntryEnvironment));

            // Generate code
            FEELContext context = FEELContext.makeContext(element, outputEntryEnvironment);
            return this.feelTranslator.expressionToNative(feelOutputEntryExpression, context);
        } else {
            throw new UnsupportedOperationException(String.format("Not supported '%s'", tExpression.getClass().getSimpleName()));
        }
    }

    //
    // Annotations
    //
    String annotationEscapedText(TDecisionRule rule) {
        String description = rule.getDescription();
        return description == null ? "" : StringEscapeUtil.escapeInString(description);
    }

    String annotation(TDRGElement element, TDecisionRule rule) {
        String description = rule.getDescription();
        return this.dmnTransformer.annotation(element, description);
    }

    String ruleAnnotationClassName() {
        return Rule.class.getName();
    }

    String hitPolicyAnnotationClassName() {
        return HitPolicy.class.getName();
    }

    //
    // TContext
    //
    Statement contextExpressionToNative(TDRGElement element, TContext context) {
        Environment elementEnvironment = this.dmnTransformer.makeEnvironment(element);

        // Make context environment
        return contextExpressionToNative(element, context, elementEnvironment);
    }

    Statement contextExpressionToNative(TDRGElement element, TContext context, Environment elementEnvironment) {
        // Make context environment
        Pair<Environment, Map<TContextEntry, Expression>> pair = this.dmnTransformer.makeContextEnvironment(element, context, elementEnvironment);
        return contextExpressionToNative(element, context, pair.getLeft(), pair.getRight());
    }

    private Statement contextExpressionToNative(TDRGElement element, TContext context, Environment contextEnvironment, Map<TContextEntry, Expression> literalExpressionMap) {
        // Translate to Java
        FEELContext feelContext = FEELContext.makeContext(element, contextEnvironment);
        CompoundStatement statement = this.nativeFactory.makeCompoundStatement();
        ExpressionStatement returnValue = null;
        for(TContextEntry entry: context.getContextEntry()) {
            // Translate value
            ExpressionStatement value;
            Type entryType;
            JAXBElement<? extends TExpression> jaxbElement = entry.getExpression();
            if (jaxbElement != null) {
                TExpression expression = jaxbElement.getValue();
                if (expression instanceof TLiteralExpression) {
                    Expression feelExpression = literalExpressionMap.get(entry);
                    entryType = this.dmnEnvironmentFactory.entryType(element, entry, expression, feelExpression);
                    String stm = this.feelTranslator.expressionToNative(feelExpression, feelContext);
                    value = this.nativeFactory.makeExpressionStatement(stm, entryType);
                } else {
                    entryType = this.dmnEnvironmentFactory.entryType(element, entry, contextEnvironment);
                    value = (ExpressionStatement) this.dmnTransformer.expressionToNative(element, expression, contextEnvironment);
                }
            } else {
                entryType = this.dmnEnvironmentFactory.entryType(element, entry, contextEnvironment);
                value = this.nativeFactory.makeExpressionStatement("null", entryType);
            }

            // Add statement
            TInformationItem variable = entry.getVariable();
            if (variable != null) {
                String name = this.dmnTransformer.lowerCaseFirst(variable.getName());
                String type = this.dmnTransformer.toNativeType(entryType);
                String assignmentText = this.nativeFactory.makeVariableAssignment(type, name, value.getExpression());
                statement.add(this.nativeFactory.makeExpressionStatement(assignmentText, entryType));
            } else {
                returnValue = value;
            }
        }

        // Add return statement
        Type returnType = this.dmnTransformer.drgElementOutputFEELType(element);
        if (returnValue != null) {
            String text = this.nativeFactory.makeReturn(returnValue.getExpression());
            statement.add(this.nativeFactory.makeExpressionStatement(text, returnType));
        } else {
            // Make complex type value
            String complexJavaType = this.dmnTransformer.drgElementOutputType(element);
            String complexTypeVariable = this.dmnTransformer.namedElementVariableName(element);
            String expressionText;
            if (returnType instanceof ItemDefinitionType) {
                expressionText = this.nativeFactory.makeVariableAssignment(this.dmnTransformer.itemDefinitionNativeClassName(complexJavaType), complexTypeVariable, this.dmnTransformer.defaultConstructor(this.dmnTransformer.itemDefinitionNativeClassName(complexJavaType)));
            } else if (returnType instanceof ContextType) {
                expressionText = this.nativeFactory.makeVariableAssignment(this.dmnTransformer.contextClassName(), complexTypeVariable, this.dmnTransformer.defaultConstructor(this.dmnTransformer.contextClassName()));
            } else {
                throw new DMNRuntimeException(String.format("Expected complex type in element '%s', found '%s'", element.getName(), returnType));
            }
            statement.add(this.nativeFactory.makeExpressionStatement(expressionText, returnType));
            // Add entries
            for(TContextEntry entry: context.getContextEntry()) {
                Type entryType;
                JAXBElement<? extends TExpression> jaxbElement = entry.getExpression();
                TExpression expression = jaxbElement == null ? null : jaxbElement.getValue();
                if (expression instanceof TLiteralExpression) {
                    Expression feelExpression = literalExpressionMap.get(entry);
                    entryType = this.dmnEnvironmentFactory.entryType(element, entry, expression, feelExpression);
                } else {
                    entryType = this.dmnEnvironmentFactory.entryType(element, entry, contextEnvironment);
                }

                // Add statement
                TInformationItem variable = entry.getVariable();
                if (variable != null) {
                    String javaContextEntryName = this.dmnTransformer.lowerCaseFirst(variable.getName());
                    String entryText;
                    if (returnType instanceof ItemDefinitionType) {
                        entryText = this.nativeFactory.makeMemberAssignment(complexTypeVariable, javaContextEntryName, javaContextEntryName);
                    } else {
                        entryText = this.nativeFactory.makeContextMemberAssignment(complexTypeVariable, javaContextEntryName, javaContextEntryName);
                    }
                    statement.add(this.nativeFactory.makeExpressionStatement(entryText, entryType));
                }
            }
            // Return value
            String returnText = this.nativeFactory.makeReturn(complexTypeVariable);
            statement.add(this.nativeFactory.makeExpressionStatement(returnText, returnType));
        }
        return statement;
    }

    //
    // TFunctionDefinition
    //
    ExpressionStatement functionDefinitionToNative(TDRGElement element, TFunctionDefinition expression, Environment environment) {
        FunctionType functionType = (FunctionType) this.dmnTransformer.expressionType(element, expression, environment);
        TFunctionKind kind = expression.getKind();
        if (this.dmnTransformer.isFEELFunction(kind)) {
            TExpression bodyExpression = expression.getExpression().getValue();
            Environment functionDefinitionEnvironment = this.dmnTransformer.makeFunctionDefinitionEnvironment(element, expression, environment);
            ExpressionStatement statement = (ExpressionStatement) this.dmnTransformer.expressionToNative(element, bodyExpression, functionDefinitionEnvironment);
            String body = statement.getExpression();

            String expressionText = functionDefinitionToNative(element, functionType, body, false);
            return this.nativeFactory.makeExpressionStatement(expressionText, functionType);
        } else if (this.dmnTransformer.isJavaFunction(kind)) {
            JavaFunctionInfo javaInfo = extractJavaFunctionInfo(element, expression);
            String text = javaFunctionToNative(javaInfo, functionType);
            return this.nativeFactory.makeExpressionStatement(text, functionType);
        }
        throw new DMNRuntimeException(String.format("Kind '%s' is not supported yet in element '%s'", kind, element.getName()));
    }

    private String javaFunctionToNative(JavaFunctionInfo javaInfo, FunctionType functionType) {
        String paramTypesArg = javaInfo.getParamTypes().stream().map(p -> String.format("\"%s\"", p)).collect(Collectors.joining(", "));
        String javaInfoArgs = String.format("\"%s\", \"%s\", Arrays.asList(%s)", javaInfo.getClassName(), javaInfo.getMethodName(), paramTypesArg);
        String javaInfoArg = dmnTransformer.constructor(JavaFunctionInfo.class.getName(), javaInfoArgs);
        String returnType = dmnTransformer.toNativeType(functionType.getReturnType());
        String className = nativeTypeFactory.constructorOfGenericType(JavaExternalFunction.class.getName(), returnType);
        String javaClassOfReturnType = nativeTypeFactory.javaClass(returnType);
        return dmnTransformer.constructor(className, String.format("%s, %s, %s", javaInfoArg, this.dmnTransformer.externalExecutorVariableName(), javaClassOfReturnType));
    }

    String functionDefinitionToNative(TDRGElement element, FunctionDefinition functionDefinition, String body, boolean convertToContext) {
        FunctionType functionType = (FunctionType) functionDefinition.getType();
        return functionDefinitionToNative(element, functionType, body, convertToContext);
    }

    private String functionDefinitionToNative(TDRGElement element, FunctionType functionType, String body, boolean convertToContext) {
        if (functionType instanceof FEELFunctionType) {
            if (((FEELFunctionType) functionType).isExternal()) {
                JavaFunctionInfo javaInfo = extractJavaFunctionInfo(element, ((FEELFunctionType) functionType).getFunctionDefinition());
                return javaFunctionToNative(javaInfo, functionType);
            } else {
                String returnType = this.dmnTransformer.toNativeType(this.dmnTransformer.convertType(functionType.getReturnType(), convertToContext));
                String signature = "Object... args";
                String applyMethod = this.nativeFactory.applyMethod(functionType, signature, convertToContext, body);
                return functionDefinitionToNative(returnType, applyMethod);
            }
        } else if (functionType instanceof DMNFunctionType) {
            String returnType = this.dmnTransformer.toNativeType(this.dmnTransformer.convertType(functionType.getReturnType(), convertToContext));
            String signature = "Object... args";
            String applyMethod = this.nativeFactory.applyMethod(functionType, signature, convertToContext, body);
            return functionDefinitionToNative(returnType, applyMethod);
        }
        throw new DMNRuntimeException(String.format("%s is not supported yet", functionType));
    }

    public JavaFunctionInfo extractJavaFunctionInfo(TDRGElement element, TFunctionDefinition functionDefinition) {
        // Extract class, method and param types names
        String className = null;
        String methodName = null;
        List<String> paramTypes = new ArrayList<>();
        TExpression body = functionDefinition.getExpression().getValue();
        if (body instanceof TContext) {
            for (TContextEntry entry: ((TContext) body).getContextEntry()) {
                String name = entry.getVariable().getName();
                if ("class".equals(name)) {
                    TExpression value = entry.getExpression().getValue();
                    if (value instanceof TLiteralExpression) {
                        className = ((TLiteralExpression) value).getText().replaceAll("\"", "");
                    }
                } else if (isMethodSignature(name)) {
                    TExpression value = entry.getExpression().getValue();
                    if (value instanceof TLiteralExpression) {
                        String signature = ((TLiteralExpression) value).getText().replaceAll("\"", "");
                        int lpIndex = signature.indexOf('(');
                        int rpIndex = signature.indexOf(')');
                        methodName = signature.substring(0, lpIndex);
                        String[] types = signature.substring(lpIndex + 1, rpIndex).split(",");
                        for (String t: types) {
                            paramTypes.add(t.trim());
                        }
                    }
                }
            }
        }
        if (className != null && methodName != null) {
            return new JavaFunctionInfo(className, methodName, paramTypes);
        } else {
            throw new DMNRuntimeException(String.format("Cannot extract Java function info for element '%s'", element.getName()));
        }
    }

    public JavaFunctionInfo extractJavaFunctionInfo(TDRGElement element, FunctionDefinition functionDefinition) {
        // Extract class, method and param types names
        String className = null;
        String methodName = null;
        List<String> paramTypes = new ArrayList<>();
        Expression body = functionDefinition.getBody();
        if (body instanceof Context) {
            body = ((Context) body).getEntries().get(0).getExpression();
        }
        if (body instanceof Context) {
            for (ContextEntry entry: ((Context) body).getEntries()) {
                String name = entry.getKey().getKey();
                if ("class".equals(name)) {
                    Expression value = entry.getExpression();
                    if (value instanceof StringLiteral) {
                        String lexeme = ((StringLiteral) value).getLexeme();
                        className = StringEscapeUtil.stripQuotes(lexeme);
                    }
                } else if (isMethodSignature(name)) {
                    Expression value = entry.getExpression();
                    if (value instanceof StringLiteral) {
                        String lexeme = ((StringLiteral) value).getLexeme();
                        String signature = StringEscapeUtil.stripQuotes(lexeme);
                        int lpIndex = signature.indexOf('(');
                        int rpIndex = signature.indexOf(')');
                        methodName = signature.substring(0, lpIndex);
                        String[] types = signature.substring(lpIndex + 1, rpIndex).split(",");
                        for (String t: types) {
                            paramTypes.add(t.trim());
                        }
                    }
                }
            }
        }
        if (className != null && methodName != null) {
            return new JavaFunctionInfo(className, methodName, paramTypes);
        } else {
            throw new DMNRuntimeException(String.format("Cannot extract Java function info for element '%s'", element.getName()));
        }
    }

    private boolean isMethodSignature(String name) {
        return "method signature".equals(name) || "methodSignature".equals(name) || "'method signature'".equals(name);
    }

    private String functionDefinitionToNative(String returnType, String applyMethod) {
        String functionalInterface = LambdaExpression.class.getName();
        return this.nativeFactory.functionalInterfaceConstructor(functionalInterface, returnType, applyMethod);
    }

    //
    // TInvocation
    //
    Statement invocationExpressionToNative(TDRGElement element, TInvocation invocation) {
        Environment environment = dmnTransformer.makeEnvironment(element);
        return invocationExpressionToNative(element, invocation, environment);
    }

    Statement invocationExpressionToNative(TDRGElement element, TInvocation invocation, Environment environment) {
        // Compute name-java binding for arguments
        Map<String, Statement> argBinding = new LinkedHashMap<>();
        for(TBinding binding: invocation.getBinding()) {
            String argName= binding.getParameter().getName();
            TExpression argExpression = binding.getExpression().getValue();
            Statement argJava = dmnTransformer.expressionToNative(element, argExpression, environment);
            argBinding.put(argName, argJava);
        }
        // Build call
        TExpression body = invocation.getExpression().getValue();
        if (body instanceof TLiteralExpression) {
            String bkmName = ((TLiteralExpression) body).getText();
            TBusinessKnowledgeModel bkm = dmnModelRepository.findKnowledgeModelByName(bkmName);
            if (bkm == null) {
                throw new DMNRuntimeException(String.format("Cannot find BKM for '%s'", bkmName));
            }
            List<Statement> argList = new ArrayList<>();
            List<String> formalParameterList = dmnTransformer.bkmFEELParameterNames(bkm);
            for(String paramName: formalParameterList) {
                if (argBinding.containsKey(paramName)) {
                    Statement argValue = argBinding.get(paramName);
                    argList.add(argValue);
                } else {
                    throw new UnsupportedOperationException(String.format("Cannot find binding for parameter '%s'", paramName));
                }
            }
            String bkmFunctionName = dmnTransformer.bkmQualifiedFunctionName(bkm);
            String argListString = argList.stream().map(s -> ((ExpressionStatement)s).getExpression()).collect(Collectors.joining(", "));
            String expressionText = String.format("%s(%s)", bkmFunctionName, dmnTransformer.drgElementArgumentListExtraCache(dmnTransformer.drgElementArgumentListExtra(dmnTransformer.augmentArgumentList(argListString))));
            Type expressionType = dmnTransformer.drgElementOutputFEELType(bkm);
            return this.nativeFactory.makeExpressionStatement(expressionText, expressionType);
        } else {
            throw new DMNRuntimeException(String.format("Not supported '%s'", body.getClass().getSimpleName()));
        }
    }

    //
    // TLiteralExpression
    //
    Statement literalExpressionToNative(TDRGElement element, String expressionText) {
        Environment environment = this.dmnTransformer.makeEnvironment(element);
        return literalExpressionToNative(element, expressionText, environment);
    }

    Statement literalExpressionToNative(TDRGElement element, String expressionText, Environment environment) {
        FEELContext context = FEELContext.makeContext(element, environment);
        Expression expression = this.feelTranslator.analyzeExpression(expressionText, context);
        Type expressionType = expression.getType();

        String javaExpression = this.feelTranslator.expressionToNative(expression, context);
        return this.nativeFactory.makeExpressionStatement(javaExpression, expressionType);
    }

    //
    // TRelation
    //
    Statement relationExpressionToNative(TDRGElement element, TRelation relation) {
        TDefinitions model = this.dmnModelRepository.getModel(element);
        Environment elementEnvironment = dmnTransformer.makeEnvironment(element);

        // Make relation environment
        Environment relationEnvironment = dmnTransformer.makeRelationEnvironment(model, relation, elementEnvironment);
        return relationExpressionToNative(element, relation, relationEnvironment);
    }

    Statement relationExpressionToNative(TDRGElement element, TRelation relation, Environment relationEnvironment) {
        Type resultType = dmnTransformer.drgElementOutputFEELType(element);
        if (relation.getRow() == null) {
            return this.nativeFactory.makeExpressionStatement("null", resultType);
        }

        // Type name
        String javaType = dmnTransformer.drgElementOutputClassName(element);

        // Constructor argument names
        List<String> argNameList = relation.getColumn().stream().map(c -> dmnTransformer.nativeFriendlyVariableName(c.getName())).collect(Collectors.toList());

        // Scan relation and translate each row to a Java constructor invocation
        List<String> rowValues = new ArrayList<>();
        for(TList row: relation.getRow()) {
            // Translate value
            List<JAXBElement<? extends TExpression>> jaxbElementList = row.getExpression();
            if (jaxbElementList == null) {
                rowValues.add("null");
            } else {
                List<Pair<String, String>> argPairList = new ArrayList<>();
                for(int i = 0; i < jaxbElementList.size(); i++) {
                    JAXBElement<? extends TExpression> jaxbElement = jaxbElementList.get(i);
                    TExpression expression = jaxbElement.getValue();
                    String argValue;
                    if (expression == null) {
                        argValue = "null";
                    } else {
                        Statement statement = dmnTransformer.expressionToNative(element, expression, relationEnvironment);
                        argValue = ((ExpressionStatement)statement).getExpression();
                    }
                    argPairList.add(new Pair<>(argNameList.get(i), argValue));
                }
                argPairList.sort(Comparator.comparing(Pair::getLeft));
                String argList = argPairList.stream().map(Pair::getRight).collect(Collectors.joining(", "));
                rowValues.add(dmnTransformer.constructor(dmnTransformer.itemDefinitionNativeClassName(javaType), argList));
            }
        }

        // Make a list
        Type elementType = ((ListType) resultType).getElementType();
        String result = nativeFactory.asList(elementType, String.join(",\n" + indent, rowValues));
        return this.nativeFactory.makeExpressionStatement(result, resultType);
    }
}
