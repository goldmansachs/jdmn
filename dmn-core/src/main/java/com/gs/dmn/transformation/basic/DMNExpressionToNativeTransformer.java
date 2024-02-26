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
import com.gs.dmn.NameUtils;
import com.gs.dmn.ast.*;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.context.environment.EnvironmentFactory;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.el.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.el.synthesis.ELTranslator;
import com.gs.dmn.feel.analysis.semantics.type.*;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FunctionDefinition;
import com.gs.dmn.feel.analysis.syntax.ast.expression.textual.FilterExpression;
import com.gs.dmn.feel.lib.StringEscapeUtil;
import com.gs.dmn.feel.synthesis.type.NativeTypeFactory;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.runtime.RuleOutput;
import com.gs.dmn.runtime.RuleOutputList;
import com.gs.dmn.runtime.annotation.HitPolicy;
import com.gs.dmn.runtime.annotation.Rule;
import com.gs.dmn.runtime.external.JavaFunctionInfo;
import com.gs.dmn.transformation.DMNToJavaTransformer;
import com.gs.dmn.transformation.native_.NativeFactory;
import com.gs.dmn.transformation.native_.statement.AssignmentStatement;
import com.gs.dmn.transformation.native_.statement.CompoundStatement;
import com.gs.dmn.transformation.native_.statement.ExpressionStatement;
import com.gs.dmn.transformation.native_.statement.Statement;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.gs.dmn.transformation.DMNToJavaTransformer.DECISION_RULE_OUTPUT_CLASS_SUFFIX;

public class DMNExpressionToNativeTransformer {
    private static final String TAB = "    ";
    private static final String RELATION_INDENT = TAB + TAB + TAB + TAB;

    private static final String LINE_SEPARATOR = System.lineSeparator();

    protected final BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer;
    protected final DMNModelRepository dmnModelRepository;
    private final ELTranslator<Type, DMNContext> feelTranslator;
    private final EnvironmentFactory environmentFactory;
    private final DMNEnvironmentFactory dmnEnvironmentFactory;
    private final NativeTypeFactory nativeTypeFactory;
    protected final NativeFactory nativeFactory;
    private final ExternalFunctionExtractor externalFunctionExtractor;

    protected DMNExpressionToNativeTransformer(BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer) {
        this.dmnTransformer = dmnTransformer;

        this.dmnModelRepository = dmnTransformer.getDMNModelRepository();
        this.environmentFactory = dmnTransformer.getEnvironmentFactory();

        this.feelTranslator = dmnTransformer.getFEELTranslator();
        this.nativeFactory = dmnTransformer.getNativeFactory();
        this.dmnEnvironmentFactory = dmnTransformer.getDMNEnvironmentFactory();
        this.nativeTypeFactory = dmnTransformer.getNativeTypeFactory();
        this.externalFunctionExtractor = new ExternalFunctionExtractor();
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
                    String defaultValue = this.dmnTransformer.constructor(this.dmnTransformer.drgElementOutputClassName(element), String.join(", ", values));
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
                return this.nativeFactory.nullLiteral();
            }
        } else {
            throw new DMNRuntimeException("Cannot compute default value for '%s' '%s'".formatted(element.getClass().getSimpleName(), element.getName()));
        }
    }

    String defaultValue(TDRGElement element, TOutputClause output) {
        TLiteralExpression defaultOutputEntry = output.getDefaultOutputEntry();
        if (defaultOutputEntry == null) {
            return this.nativeFactory.nullLiteral();
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
            throw new DMNRuntimeException("Variable name cannot be null. OutputClause id '%s'".formatted(outputClause.getId()));
        }
        return this.dmnTransformer.lowerCaseFirst(name);
    }

    String outputClausePriorityVariableName(TDRGElement element, TOutputClause outputClause) {
        return outputClauseVariableName(element, outputClause) + DMNToJavaTransformer.PRIORITY_SUFFIX;
    }

    Integer outputClausePriority(TDRGElement element, TLiteralExpression literalExpression, int outputIndex) {
        String outputEntryText = literalExpression.getText();
        TExpression tExpression = this.dmnModelRepository.expression(element);
        if (tExpression instanceof TDecisionTable table) {
            TOutputClause tOutputClause = table.getOutput().get(outputIndex);
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
            throw new UnsupportedOperationException("Not supported '%s'".formatted(tExpression.getClass().getSimpleName()));
        }
    }

    String outputClauseGetter(TDRGElement element, TOutputClause output) {
        return this.dmnTransformer.getter(this.dmnTransformer.outputClauseVariableName(element, output));
    }

    String drgElementOutputGetter(TDRGElement element, TOutputClause output) {
        String name = this.dmnTransformer.outputClauseVariableName(element, output);
        Type type = dmnTransformer.drgElementOutputFEELType(element);
        if (type instanceof ContextType) {
            return this.dmnTransformer.contextGetter(name);
        } else {
            return this.dmnTransformer.getter(name);
        }
    }

    String outputClausePriorityGetter(TDRGElement element, TOutputClause output) {
        return this.dmnTransformer.getter(this.outputClausePriorityVariableName(element, output));
    }

    String outputClauseSetter(TDRGElement element, TOutputClause output, String args) {
        return this.dmnTransformer.setter(this.dmnTransformer.outputClauseVariableName(element, output), args);
    }

    String drgElementOutputSetter(TDRGElement element, TOutputClause output, String args) {
        String name = this.dmnTransformer.outputClauseVariableName(element, output);
        Type type = dmnTransformer.drgElementOutputFEELType(element);
        if (type instanceof ContextType) {
            return "%s%s)".formatted(this.dmnTransformer.contextSetter(name), args);
        } else {
            return this.dmnTransformer.setter(name, args);
        }
    }

    String prioritySetter(TDRGElement element, TOutputClause output, String args) {
        return this.dmnTransformer.setter(this.outputClausePriorityVariableName(element, output), args);
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
            throw new UnsupportedOperationException("Not supported '%s' aggregation.".formatted(aggregation));
        }
    }

    HitPolicy hitPolicy(TDRGElement element) {
        TExpression expression = this.dmnModelRepository.expression(element);
        if (expression instanceof TDecisionTable table) {
            THitPolicy hitPolicy = table.getHitPolicy();
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

    String qualifiedRuleOutputClassName(TDRGElement element) {
        String clsName = this.dmnTransformer.upperCaseFirst(element.getName() + DECISION_RULE_OUTPUT_CLASS_SUFFIX);
        String modelName = this.dmnModelRepository.getModelName(element);
        String nativePackage = this.dmnTransformer.nativeModelPackageName(modelName);;
        return this.dmnTransformer.qualifiedName(nativePackage, clsName);
    }

    String abstractRuleOutputClassName() {
        return this.dmnTransformer.qualifiedName(RuleOutput.class);
    }

    String ruleOutputListClassName() {
        return this.dmnTransformer.qualifiedName(RuleOutputList.class);
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
    String condition(TDRGElement element, TDecisionRule rule, int ruleIndex) {
        TExpression decisionTable = this.dmnModelRepository.expression(element);
        if (decisionTable instanceof TDecisionTable) {
            // Build condition parts
            List<String> conditionParts = new ArrayList<>();
            for (int i = 0; i < rule.getInputEntry().size(); i++) {
                TUnaryTests inputEntry = rule.getInputEntry().get(i);
                String condition = condition(element, decisionTable, inputEntry, i, ruleIndex);
                conditionParts.add(condition);
            }
            // Build rule matches call
            String indent3tabs = "            ";
            String indent2tabs = "        ";
            String operands = conditionParts.stream().collect(Collectors.joining("," + LINE_SEPARATOR + indent3tabs));
            String eventListenerVariable = this.dmnTransformer.eventListenerVariableName();
            String ruleMetadataVariable = this.dmnTransformer.drgRuleMetadataFieldName();
            String args = "%s, %s,%s%s%s".formatted(eventListenerVariable, ruleMetadataVariable, LINE_SEPARATOR + indent3tabs, operands, LINE_SEPARATOR + indent2tabs);
            return this.nativeFactory.makeBuiltinFunctionInvocation(ruleMatchesMethodName(), args);
        }
        throw new DMNRuntimeException("Cannot build condition for " + decisionTable.getClass().getSimpleName());
    }

    private String ruleMatchesMethodName() {
        return "ruleMatches";
    }

    private String condition(TDRGElement element, TExpression decisionTable, TUnaryTests inputEntry, int inputEntryIndex, int ruleIndex) {
        TInputClause tInputClause = ((TDecisionTable) decisionTable).getInput().get(inputEntryIndex);
        String inputExpressionText = tInputClause.getInputExpression().getText();
        String inputEntryText = inputEntry.getText();
        try {
            return inputEntryToNative(element, inputExpressionText, inputEntryText);
        } catch (Exception e) {
            throw new DMNRuntimeException("Cannot build condition for input clause '%s' for entry '%s' in element '%s' in rule %d".formatted(inputExpressionText, inputEntryText, element.getName(), ruleIndex + 1), e);
        }
    }

    private String inputEntryToNative(TDRGElement element, String inputExpressionText, String inputEntryText) {
        // Analyze input expression
        DMNContext globalContext = this.dmnTransformer.makeGlobalContext(element);
        Expression<Type> inputExpression = this.feelTranslator.analyzeExpression(inputExpressionText, globalContext);

        // Generate code for input entry
        DMNContext inputEntryContext = this.dmnTransformer.makeUnaryTestContext(inputExpression, globalContext);
        return this.feelTranslator.unaryTestsToJava(inputEntryText, inputEntryContext);
    }

    String outputEntryToNative(TDRGElement element, TLiteralExpression outputEntryExpression, int outputIndex) {
        TExpression tExpression = this.dmnModelRepository.expression(element);
        if (tExpression instanceof TDecisionTable) {
            // Analyze output expression
            String outputEntryText = outputEntryExpression.getText();
            if ("-".equals(outputEntryText)) {
                outputEntryText = this.nativeFactory.nullLiteral();
            }
            DMNContext outputEntryContext = this.dmnTransformer.makeGlobalContext(element);
            Expression<Type> feelOutputEntryExpression = this.feelTranslator.analyzeExpression(outputEntryText, outputEntryContext);

            // Generate code
            return this.feelTranslator.expressionToNative(feelOutputEntryExpression, outputEntryContext);
        } else {
            throw new UnsupportedOperationException("Not supported '%s'".formatted(tExpression.getClass().getSimpleName()));
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
        return this.dmnTransformer.qualifiedName(Rule.class);
    }

    String hitPolicyAnnotationClassName() {
        return this.dmnTransformer.qualifiedName(HitPolicy.class);
    }

    //
    // TContext
    //
    Statement contextExpressionToNative(TDRGElement element, TContext context) {
        DMNContext globalContext = this.dmnTransformer.makeGlobalContext(element);

        // Make context environment
        return contextExpressionToNative(element, context, globalContext);
    }

    Statement contextExpressionToNative(TDRGElement element, TContext context, DMNContext parentContext) {
        // Make entry context
        Pair<DMNContext, Map<TContextEntry, Expression<Type>>> pair = this.dmnTransformer.makeContextEnvironment(element, context, parentContext);
        DMNContext localContext = pair.getLeft();

        // Translate to Java
        Map<TContextEntry, Expression<Type>> literalExpressionMap = pair.getRight();
        CompoundStatement statement = this.nativeFactory.makeCompoundStatement();
        ExpressionStatement returnValue = null;
        for(TContextEntry entry: context.getContextEntry()) {
            // Translate value
            ExpressionStatement value;
            Type entryType;
            TExpression expression = entry.getExpression();
            if (expression == null) {
                entryType = this.dmnEnvironmentFactory.entryType(element, entry, localContext);
                value = this.nativeFactory.makeExpressionStatement(this.nativeFactory.nullLiteral(), entryType);
            } else if (expression instanceof TLiteralExpression) {
                Expression<Type> feelExpression = literalExpressionMap.get(entry);
                entryType = this.dmnEnvironmentFactory.entryType(element, entry, expression, feelExpression);
                String stm = this.feelTranslator.expressionToNative(feelExpression, localContext);
                value = this.nativeFactory.makeExpressionStatement(stm, entryType);
            } else {
                entryType = this.dmnEnvironmentFactory.entryType(element, entry, localContext);
                value = (ExpressionStatement) this.dmnTransformer.expressionToNative(element, expression, localContext);
            }

            // Add statement
            TInformationItem variable = entry.getVariable();
            if (variable != null) {
                String type = this.dmnTransformer.toNativeType(entryType);
                String name = this.dmnTransformer.lowerCaseFirst(variable.getName());
                String assignmentText = this.nativeFactory.makeVariableAssignment(type, name, value.getText());
                AssignmentStatement aStatement = this.nativeFactory.makeAssignmentStatement(type, name, value.getText(), entryType, assignmentText);
                if (!aStatement.getLhs().equals(aStatement.getRhs())) {
                    statement.add(aStatement);
                }
            } else {
                returnValue = value;
            }
        }

        // Add return statement
        Type returnType = this.dmnTransformer.drgElementOutputFEELType(element);
        if (returnValue != null) {
            String text = this.nativeFactory.makeReturn(returnValue.getText());
            statement.add(this.nativeFactory.makeExpressionStatement(text, returnType));
        } else {
            // Make complex type value
            String complexJavaType = this.dmnTransformer.drgElementOutputType(element);
            String complexTypeVariable = this.dmnTransformer.namedElementVariableName(element);
            String expressionText;
            AssignmentStatement expressionStatement;
            if (returnType instanceof ItemDefinitionType) {
                expressionText = this.nativeFactory.makeVariableAssignment(this.dmnTransformer.itemDefinitionNativeClassName(complexJavaType), complexTypeVariable, this.dmnTransformer.defaultConstructor(this.dmnTransformer.itemDefinitionNativeClassName(complexJavaType)));
                expressionStatement = this.nativeFactory.makeAssignmentStatement(this.dmnTransformer.itemDefinitionNativeClassName(complexJavaType), complexTypeVariable, this.dmnTransformer.defaultConstructor(this.dmnTransformer.itemDefinitionNativeClassName(complexJavaType)), returnType, expressionText);
            } else if (returnType instanceof ContextType) {
                expressionText = this.nativeFactory.makeVariableAssignment(this.dmnTransformer.contextClassName(), complexTypeVariable, this.dmnTransformer.defaultConstructor(this.dmnTransformer.contextClassName()));
                expressionStatement = this.nativeFactory.makeAssignmentStatement(this.dmnTransformer.contextClassName(), complexTypeVariable, this.dmnTransformer.defaultConstructor(this.dmnTransformer.contextClassName()), returnType, expressionText);
            } else {
                throw new DMNRuntimeException("Expected complex type in element '%s', found '%s'".formatted(element.getName(), returnType));
            }
            statement.add(expressionStatement);
            // Add entries
            for(TContextEntry entry: context.getContextEntry()) {
                Type entryType;
                TExpression expression = entry.getExpression();
                if (expression instanceof TLiteralExpression) {
                    Expression<Type> feelExpression = literalExpressionMap.get(entry);
                    entryType = this.dmnEnvironmentFactory.entryType(element, entry, expression, feelExpression);
                } else {
                    entryType = this.dmnEnvironmentFactory.entryType(element, entry, localContext);
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
                    Statement entryStatement = this.nativeFactory.makeAssignmentStatement(complexTypeVariable, javaContextEntryName, javaContextEntryName, entryType, entryText);
                    statement.add(entryStatement);
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
    Statement functionDefinitionToNative(TDRGElement element, TFunctionDefinition expression) {
        DMNContext globalContext = this.dmnTransformer.makeGlobalContext(element);
        return functionDefinitionToNative(element, expression, globalContext);
    }

    ExpressionStatement functionDefinitionToNative(TDRGElement element, TFunctionDefinition expression, DMNContext context) {
        FunctionType functionType = (FunctionType) this.dmnTransformer.expressionType(element, expression, context);
        TFunctionKind kind = expression.getKind();
        if (this.dmnTransformer.isFEELFunction(kind)) {
            TExpression bodyExpression = expression.getExpression();
            DMNContext functionContext = this.dmnTransformer.makeFunctionContext(element, expression, context);
            ExpressionStatement statement = (ExpressionStatement) this.dmnTransformer.expressionToNative(element, bodyExpression, functionContext);
            String body = statement.getText();

            String expressionText = functionDefinitionToNative(element, functionType, body, false);
            return this.nativeFactory.makeExpressionStatement(expressionText, functionType);
        } else if (this.dmnTransformer.isJavaFunction(kind)) {
            JavaFunctionInfo javaInfo = this.externalFunctionExtractor.extractJavaFunctionInfo(element, expression);
            String text = javaFunctionToNative(javaInfo, functionType);
            return this.nativeFactory.makeExpressionStatement(text, functionType);
        }
        throw new DMNRuntimeException("Kind '%s' is not supported yet in element '%s'".formatted(kind, element.getName()));
    }

    private String javaFunctionToNative(JavaFunctionInfo javaInfo, FunctionType functionType) {
        String paramTypesArg = javaInfo.getParamTypes().stream().map(p -> "\"%s\"".formatted(p)).collect(Collectors.joining(", "));
        String javaInfoArgs = "\"%s\", \"%s\", Arrays.asList(%s)".formatted(javaInfo.getClassName(), javaInfo.getMethodName(), paramTypesArg);
        String javaInfoArg = this.dmnTransformer.constructor(this.dmnTransformer.javaFunctionInfoClassName(), javaInfoArgs);
        String returnType = this.dmnTransformer.toNativeType(functionType.getReturnType());
        String className = this.nativeTypeFactory.constructorOfGenericType(this.dmnTransformer.javaExternalFunctionClassName(), returnType);
        String javaClassOfReturnType = this.nativeTypeFactory.javaClass(returnType);
        return this.dmnTransformer.constructor(className, "%s, %s, %s".formatted(javaInfoArg, this.dmnTransformer.externalExecutorVariableName(), javaClassOfReturnType));
    }

    String functionDefinitionToNative(TDRGElement element, FunctionDefinition<Type> functionDefinition, String body, boolean convertToContext) {
        FunctionType functionType = (FunctionType) functionDefinition.getType();
        return functionDefinitionToNative(element, functionType, body, convertToContext);
    }

    private String functionDefinitionToNative(TDRGElement element, FunctionType functionType, String body, boolean convertToContext) {
        if (functionType instanceof FEELFunctionType type) {
            if (type.isExternal()) {
                JavaFunctionInfo javaInfo = this.externalFunctionExtractor.extractJavaFunctionInfo(element, type.getFunctionDefinition());
                return javaFunctionToNative(javaInfo, functionType);
            } else {
                String returnType = this.dmnTransformer.toNativeType(this.dmnTransformer.convertType(functionType.getReturnType(), convertToContext));
                String signature = dmnTransformer.lambdaApplySignature();
                String applyMethod = this.nativeFactory.applyMethod(functionType, signature, convertToContext, body);
                return functionDefinitionToNative(returnType, applyMethod);
            }
        } else if (functionType instanceof DMNFunctionType) {
            String returnType = this.dmnTransformer.toNativeType(this.dmnTransformer.convertType(functionType.getReturnType(), convertToContext));
            String signature = this.dmnTransformer.lambdaApplySignature();
            String applyMethod = this.nativeFactory.applyMethod(functionType, signature, convertToContext, body);
            return functionDefinitionToNative(returnType, applyMethod);
        }
        throw new DMNRuntimeException("%s is not supported yet".formatted(functionType));
    }

    private String functionDefinitionToNative(String returnType, String applyMethod) {
        String functionalInterface = this.dmnTransformer.lambdaExpressionClassName();
        return this.nativeFactory.functionalInterfaceConstructor(functionalInterface, returnType, applyMethod);
    }

    //
    // TInvocation
    //
    Statement invocationExpressionToNative(TDRGElement element, TInvocation invocation) {
        DMNContext globalContext = this.dmnTransformer.makeGlobalContext(element);
        return invocationExpressionToNative(element, invocation, globalContext);
    }

    protected Statement invocationExpressionToNative(TDRGElement element, TInvocation invocation, DMNContext parentContext) {
        // Compute name-java binding for arguments
        Map<String, Statement> argBinding = new LinkedHashMap<>();
        for(TBinding binding: invocation.getBinding()) {
            String argName= binding.getParameter().getName();
            TExpression argExpression = binding.getExpression();
            Statement argJava = this.dmnTransformer.expressionToNative(element, argExpression, parentContext);
            argBinding.put(argName, argJava);
        }

        // Build call
        TExpression functionExp = invocation.getExpression();
        Type functionType = this.dmnTransformer.expressionType(element, functionExp, parentContext);
        if (functionType instanceof FunctionType type) {
            // Make args
            List<Statement> argList = new ArrayList<>();
            for (FormalParameter<Type> param: type.getParameters()) {
                String paramName = param.getName();
                if (argBinding.containsKey(paramName)) {
                    Statement argValue = argBinding.get(paramName);
                    argList.add(argValue);
                } else {
                    throw new UnsupportedOperationException("Cannot find binding for parameter '%s'".formatted(paramName));
                }
            }

            if (functionType instanceof DMNFunctionType) {
                // Find Invocable
                String invocableName = NameUtils.invocableName(((TLiteralExpression) functionExp).getText());
                TInvocable invocable = this.dmnModelRepository.findInvocableByName(invocableName);
                if (invocable == null) {
                    throw new DMNRuntimeException("Cannot find BKM or DS for '%s'".formatted(invocableName));
                }
                String invocableInstance = this.dmnTransformer.singletonInvocableInstance(invocable);
                String argListString = argList.stream().map(Statement::getText).collect(Collectors.joining(", "));
                String expressionText = "%s.apply(%s)".formatted(invocableInstance, this.dmnTransformer.augmentArgumentList(argListString));
                Type expressionType = this.dmnTransformer.drgElementOutputFEELType(invocable);
                return this.nativeFactory.makeExpressionStatement(expressionText, expressionType);
            } else {
                throw new DMNRuntimeException("Not supported in TInvocation '%s' yet".formatted(functionExp));
            }
        } else {
            throw new DMNRuntimeException("Expecting function found '%s' in %s".formatted(functionType, invocation));
        }
    }

    //
    // TLiteralExpression
    //
    Statement literalExpressionToNative(TDRGElement element, String expressionText) {
        DMNContext globalContext = this.dmnTransformer.makeGlobalContext(element);
        return literalExpressionToNative(element, expressionText, globalContext);
    }

    Statement literalExpressionToNative(TDRGElement element, String expressionText, DMNContext context) {
        Expression<Type> expression = this.feelTranslator.analyzeExpression(expressionText, context);
        Type expressionType = expression.getType();

        String javaExpression = this.feelTranslator.expressionToNative(expression, context);
        return this.nativeFactory.makeExpressionStatement(javaExpression, expressionType);
    }

    //
    // TRelation
    //
    Statement relationExpressionToNative(TDRGElement element, TRelation relation) {
        DMNContext globalContext = this.dmnTransformer.makeGlobalContext(element);

        return relationExpressionToNative(element, relation, globalContext);
    }

    Statement relationExpressionToNative(TDRGElement element, TRelation relation, DMNContext parentContext) {
        // Make relation context
        DMNContext relationContext = this.dmnTransformer.makeRelationContext(element, relation, parentContext);
        Type resultType = this.dmnTransformer.drgElementOutputFEELType(element);
        if (relation.getRow() == null) {
            return this.nativeFactory.makeExpressionStatement(this.nativeFactory.nullLiteral(), resultType);
        }

        // Type name
        String javaType = this.dmnTransformer.drgElementOutputInterfaceName(element);

        // Constructor argument names
        List<String> argNameList = relation.getColumn().stream().map(c -> this.dmnTransformer.nativeFriendlyVariableName(c.getName())).collect(Collectors.toList());

        // Scan relation and translate each row to a Java constructor invocation
        List<String> rowValues = new ArrayList<>();
        for(TList row: relation.getRow()) {
            // Translate value
            List<TExpression> expList = row.getExpression();
            if (expList == null) {
                rowValues.add(this.nativeFactory.nullLiteral());
            } else {
                List<Pair<String, String>> argPairList = new ArrayList<>();
                for(int i = 0; i < expList.size(); i++) {
                    TExpression expression = expList.get(i);
                    String argValue;
                    if (expression == null) {
                        argValue = this.nativeFactory.nullLiteral();
                    } else {
                        Statement statement = this.dmnTransformer.expressionToNative(element, expression, relationContext);
                        argValue = statement.getText();
                    }
                    argPairList.add(new Pair<>(argNameList.get(i), argValue));
                }
                argPairList.sort(Comparator.comparing(Pair::getLeft));
                String argList = argPairList.stream().map(Pair::getRight).collect(Collectors.joining(", "));
                rowValues.add(this.dmnTransformer.constructor(this.dmnTransformer.itemDefinitionNativeClassName(javaType), argList));
            }
        }

        // Make a list
        Type elementType = ((ListType) resultType).getElementType();
        String result = this.nativeFactory.asList(elementType, String.join("," + LINE_SEPARATOR + RELATION_INDENT, rowValues));
        return this.nativeFactory.makeExpressionStatement(result, resultType);
    }

    //
    // TConditional
    //
    Statement conditionalExpressionToNative(TDRGElement element, TConditional conditional) {
        DMNContext globalContext = this.dmnTransformer.makeGlobalContext(element);

        return conditionalExpressionToNative(element, conditional, globalContext);
    }

    Statement conditionalExpressionToNative(TDRGElement element, TConditional expression, DMNContext parentContext) {
        // Check semantics
        Type type = this.dmnTransformer.expressionType(element, expression, parentContext);

        // Generate code
        Statement condition = this.dmnTransformer.expressionToNative(element, expression.getIf().getExpression(), parentContext);
        Statement thenExp = this.dmnTransformer.expressionToNative(element, expression.getThen().getExpression(), parentContext);
        Statement elseExp = this.dmnTransformer.expressionToNative(element, expression.getElse().getExpression(), parentContext);
        String conditionalText = this.nativeFactory.makeIfExpression(condition.getText(), thenExp.getText(), elseExp.getText());
        return this.nativeFactory.makeExpressionStatement(conditionalText, type);
    }

    //
    // TFilter
    //
    Statement filterExpressionToNative(TDRGElement element, TFilter expression) {
        DMNContext globalContext = this.dmnTransformer.makeGlobalContext(element);

        return filterExpressionToNative(element, expression, globalContext);
    }

    Statement filterExpressionToNative(TDRGElement element, TFilter expression, DMNContext parentContext) {
        // Check semantics
        Type sourceType = this.dmnTransformer.expressionType(element, expression, parentContext);

        // Generate code
        Statement source = this.dmnTransformer.expressionToNative(element, expression.getIn().getExpression(), parentContext);
        String filterParameterName = FilterExpression.FILTER_PARAMETER_NAME;
        DMNContext filterContext = this.dmnTransformer.makeFilterContext(sourceType, filterParameterName, parentContext);
        Statement filter = this.dmnTransformer.expressionToNative(element, expression.getMatch().getExpression(), filterContext);
        String filterExp = this.nativeFactory.makeCollectionLogicFilter(source.getText(), filterParameterName, filter.getText());
        return this.nativeFactory.makeExpressionStatement(filterExp, sourceType);
    }

    //
    // TFor
    //
    Statement forExpressionToNative(TDRGElement element, TFor expression) {
        DMNContext globalContext = this.dmnTransformer.makeGlobalContext(element);

        return forExpressionToNative(element, expression, globalContext);
    }

    Statement forExpressionToNative(TDRGElement element, TFor expression, DMNContext parentContext) {
        // Check semantics
        Type forType = this.dmnTransformer.expressionType(element, expression, parentContext);
        Type sourceType = this.dmnTransformer.expressionType(element, expression.getIn().getExpression(), parentContext);

        // Generate code
        TExpression domainExpression = expression.getIn().getExpression();
        String iteratorVariable = expression.getIteratorVariable();
        TExpression bodyExpression = expression.getReturn().getExpression();

        List<Pair<String, String>> domainIterators = new ArrayList<>();
        DMNContext iteratorContext = this.dmnTransformer.makeIteratorContext(parentContext);
        iteratorContext.addDeclaration(this.environmentFactory.makeVariableDeclaration(iteratorVariable, ((ListType) sourceType).getElementType()));
        Statement domain = this.dmnTransformer.expressionToNative(element, domainExpression, iteratorContext);
        domainIterators.add(new Pair<>(domain.getText(), iteratorVariable));

        Statement body = this.dmnTransformer.expressionToNative(element, bodyExpression, iteratorContext);
        String forText = this.nativeFactory.makeForExpression(domainIterators, body.getText());
        return this.nativeFactory.makeExpressionStatement(forText, forType);
    }

    //
    // TSome
    //
    Statement someExpressionToNative(TDRGElement element, TSome expression) {
        DMNContext globalContext = this.dmnTransformer.makeGlobalContext(element);

        return someExpressionToNative(element, expression, globalContext);
    }

    Statement someExpressionToNative(TDRGElement element, TSome expression, DMNContext parentContext) {
        // Check semantics
        Type type = this.dmnTransformer.expressionType(element, expression, parentContext);

        // Generate code
        TFor forExp = toFor(expression);
        Statement forList = this.dmnTransformer.expressionToNative(element, forExp, parentContext);
        String everyText = quantifiedExpressionToNative(forList.getText(), "some");
        return nativeFactory.makeExpressionStatement(everyText, type);
    }

    //
    // TEvery
    //
    Statement everyExpressionToNative(TDRGElement element, TEvery expression) {
        DMNContext globalContext = this.dmnTransformer.makeGlobalContext(element);

        return everyExpressionToNative(element, expression, globalContext);
    }

    Statement everyExpressionToNative(TDRGElement element, TEvery expression, DMNContext parentContext) {
        // Check semantics
        Type type = this.dmnTransformer.expressionType(element, expression, parentContext);

        // Generate code
        TFor forExp = toFor(expression);
        Statement forList = this.dmnTransformer.expressionToNative(element, forExp, parentContext);
        String everyText = quantifiedExpressionToNative(forList.getText(), "every");
        return nativeFactory.makeExpressionStatement(everyText, type);
    }

    private static TFor toFor(TQuantified expression) {
        TFor for_ = new TFor();
        for_.setIn(expression.getIn());
        for_.setIteratorVariable(expression.getIteratorVariable());
        for_.setReturn(expression.getSatisfies());
        return for_;
    }

    private String quantifiedExpressionToNative(String list, String predicate) {
        // Add boolean predicate
        if ("some".equals(predicate)) {
            return this.nativeFactory.makeSomeExpression(list);
        } else if ("every".equals(predicate)) {
            return this.nativeFactory.makeEveryExpression(list);
        } else {
            throw new UnsupportedOperationException("Predicate '" + predicate + "' is not supported yet");
        }
    }
}
