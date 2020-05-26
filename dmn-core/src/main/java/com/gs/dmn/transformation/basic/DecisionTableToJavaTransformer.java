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
import com.gs.dmn.feel.analysis.semantics.type.ListType;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.lib.StringEscapeUtil;
import com.gs.dmn.feel.synthesis.FEELTranslator;
import com.gs.dmn.feel.synthesis.expression.NativeExpressionFactory;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.runtime.RuleOutput;
import com.gs.dmn.runtime.RuleOutputList;
import com.gs.dmn.runtime.annotation.HitPolicy;
import com.gs.dmn.runtime.annotation.Rule;
import com.gs.dmn.transformation.DMNToJavaTransformer;
import org.apache.commons.lang3.StringUtils;
import org.omg.spec.dmn._20180521.model.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.gs.dmn.transformation.DMNToJavaTransformer.DECISION_RULE_OUTPUT_CLASS_SUFFIX;

public class DecisionTableToJavaTransformer {
    private final BasicDMNToNativeTransformer dmnTransformer;
    private final DMNModelRepository dmnModelRepository;
    private final FEELTranslator feelTranslator;
    private final EnvironmentFactory environmentFactory;
    private final NativeExpressionFactory expressionFactory;

    DecisionTableToJavaTransformer(BasicDMNToNativeTransformer dmnTransformer) {
        this.dmnTransformer = dmnTransformer;
        this.dmnModelRepository = dmnTransformer.getDMNModelRepository();
        this.feelTranslator = dmnTransformer.getFEELTranslator();
        this.environmentFactory = dmnTransformer.getEnvironmentFactory();
        this.expressionFactory = dmnTransformer.getExpressionFactory();
    }

    public String defaultValue(TDRGElement element) {
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
                        return this.expressionFactory.asList(((ListType) feelType).getElementType(), defaultValue);
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

    public String defaultValue(TDRGElement element, TOutputClause output) {
        TLiteralExpression defaultOutputEntry = output.getDefaultOutputEntry();
        if (defaultOutputEntry == null) {
            return "null";
        } else {
            return this.dmnTransformer.literalExpressionToNative(element, defaultOutputEntry.getText());
        }
    }

    //
    // Output Clause
    //
    public String outputClauseClassName(TDRGElement element, TOutputClause outputClause) {
        Type type = toFEELType(element, outputClause);
        return this.dmnTransformer.toNativeType(type);
    }

    private Type toFEELType(TDRGElement element, TOutputClause outputClause) {
        TDefinitions model = this.dmnModelRepository.getModel(element);

        // Check TOutputClause.typeRef
        QualifiedName outputClauseTypeRef = QualifiedName.toQualifiedName(model, outputClause.getTypeRef());
        if (outputClauseTypeRef != null) {
            return this.dmnTransformer.toFEELType(model, outputClauseTypeRef);
        }
        // Derive from parent typeRef
        QualifiedName parentTypeRef = this.dmnModelRepository.typeRef(model, element);
        if (this.dmnModelRepository.isCompoundDecisionTable(element)) {
            TItemDefinition itemDefinition = this.dmnModelRepository.lookupItemDefinition(model, parentTypeRef);
            if (itemDefinition != null) {
                for (TItemDefinition child : itemDefinition.getItemComponent()) {
                    if (child.getName().equals(outputClause.getName())) {
                        return this.dmnTransformer.toFEELType(child);
                    }
                }
            }
            throw new DMNRuntimeException(String.format("Cannot map typeRef of output clause '%s' in element '%s' to java", outputClause.getId(), element.getName()));
        } else {
            Type parentType = this.dmnTransformer.toFEELType(model, parentTypeRef);
            TDecisionTable decisionTable = this.dmnModelRepository.decisionTable(element);
            if (decisionTable.getHitPolicy() == THitPolicy.COLLECT) {
                if (decisionTable.getAggregation() == null) {
                    if (parentType instanceof ListType) {
                        return ((ListType)parentType).getElementType();
                    } else {
                        throw new DMNRuntimeException(String.format("Cannot map typeRef of output clause '%s' in element '%s' to java", outputClause.getId(), element.getName()));
                    }
                }
            }
            return parentType;
        }
    }

    public String outputClauseVariableName(TDRGElement element, TOutputClause outputClause) {
        String name = this.dmnModelRepository.outputClauseName(element, outputClause);
        if (name == null) {
            throw new DMNRuntimeException(String.format("Variable name cannot be null. OutputClause id '%s'", outputClause.getId()));
        }
        return this.dmnTransformer.lowerCaseFirst(name);
    }

    public String outputClausePriorityVariableName(TDRGElement element, TOutputClause outputClause) {
        return outputClauseVariableName(element, outputClause) + DMNToJavaTransformer.PRIORITY_SUFFIX;
    }

    public Integer priority(TDRGElement element, TLiteralExpression literalExpression, int outputIndex) {
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

    public String getter(TDRGElement element, TOutputClause output) {
        return this.dmnTransformer.getter(this.dmnTransformer.outputClauseVariableName(element, output));
    }

    public String priorityGetter(TDRGElement element, TOutputClause output) {
        return this.dmnTransformer.getter(this.outputClausePriorityVariableName(element, output));
    }

    public String setter(TDRGElement element, TOutputClause output) {
        return this.dmnTransformer.setter(this.dmnTransformer.outputClauseVariableName(element, output));
    }

    public String prioritySetter(TDRGElement element, TOutputClause output) {
        return this.dmnTransformer.setter(this.outputClausePriorityVariableName(element, output));
    }

    private List<TOutputClause> sortOutputClauses(TDRGElement element, List<TOutputClause> parameters) {
        parameters.sort(Comparator.comparing(o -> this.dmnModelRepository.outputClauseName(element, o)));
        return parameters;
    }

    //
    // Aggregation and hit policy
    //
    public String aggregator(TDRGElement element, TDecisionTable decisionTable, TOutputClause outputClause, String ruleOutputListVariableName) {
        TBuiltinAggregator aggregation = decisionTable.getAggregation();
        String decisionRuleOutputClassName = ruleOutputClassName(element);
        String outputClauseVariableName = this.dmnTransformer.outputClauseVariableName(element, outputClause);
        if (aggregation == TBuiltinAggregator.MIN) {
            return this.expressionFactory.makeMinAggregator(ruleOutputListVariableName, decisionRuleOutputClassName, outputClauseVariableName);
        } else if (aggregation == TBuiltinAggregator.MAX) {
            return this.expressionFactory.makeMaxAggregator(ruleOutputListVariableName, decisionRuleOutputClassName, outputClauseVariableName);
        } else if (aggregation == TBuiltinAggregator.COUNT) {
            return this.expressionFactory.makeCountAggregator(ruleOutputListVariableName);
        } else if (aggregation == TBuiltinAggregator.SUM) {
            return this.expressionFactory.makeSumAggregator(ruleOutputListVariableName, decisionRuleOutputClassName, outputClauseVariableName);
        } else {
            throw new UnsupportedOperationException(String.format("Not supported '%s' aggregation.", aggregation));
        }
    }

    public HitPolicy hitPolicy(TDRGElement element) {
        TExpression expression = this.dmnModelRepository.expression(element);
        if (expression instanceof TDecisionTable) {
            THitPolicy hitPolicy = ((TDecisionTable) expression).getHitPolicy();
            return HitPolicy.fromValue(hitPolicy.value());
        } else {
            return HitPolicy.UNKNOWN;
        }
    }

    public String aggregation(TDecisionTable decisionTable) {
        TBuiltinAggregator aggregation = decisionTable.getAggregation();
        return aggregation == null ? null : aggregation.value();
    }

    //
    // Rules
    //
    public String ruleOutputClassName(TDRGElement element) {
        return this.dmnTransformer.upperCaseFirst(element.getName() + DECISION_RULE_OUTPUT_CLASS_SUFFIX);
    }

    public String abstractRuleOutputClassName() {
        return RuleOutput.class.getName();
    }

    public String ruleOutputListClassName() {
        return RuleOutputList.class.getName();
    }

    public String ruleId(List<TDecisionRule> rules, TDecisionRule rule) {
        String id = rule.getId();
        return StringUtils.isBlank(id) ? Integer.toString(rules.indexOf(rule)) : id;
    }

    public String ruleSignature(TDecision decision) {
        List<DRGElementReference<? extends TDRGElement>> references = this.dmnModelRepository.sortedUniqueInputs(decision, this.dmnTransformer.getDrgElementFilter());

        List<Pair<String, String>> parameters = new ArrayList<>();
        for (DRGElementReference<? extends TDRGElement> reference : references) {
            TDRGElement element = reference.getElement();
            String parameterName = ruleParameterName(reference);
            String parameterNativeType = this.dmnTransformer.lazyEvaluationType(element, this.dmnTransformer.parameterNativeType(element));
            parameters.add(new Pair<>(parameterName, parameterNativeType));
        }
        String signature = parameters.stream().map(p -> this.expressionFactory.nullableParameter(p.getRight(), p.getLeft())).collect(Collectors.joining(", "));
        return this.dmnTransformer.augmentSignature(signature);
    }

    public String ruleArgumentList(TDecision decision) {
        List<DRGElementReference<? extends TDRGElement>> references = this.dmnModelRepository.sortedUniqueInputs(decision, this.dmnTransformer.getDrgElementFilter());

        List<String> arguments = new ArrayList<>();
        for (DRGElementReference<? extends TDRGElement> reference : references) {
            String argumentName = ruleArgumentName(reference);
            arguments.add(argumentName);
        }
        String argumentList = String.join(", ", arguments);
        return this.dmnTransformer.augmentArgumentList(argumentList);
    }

    public String ruleSignature(TBusinessKnowledgeModel bkm) {
        List<Pair<String, String>> parameters = new ArrayList<>();
        TDefinitions model = this.dmnModelRepository.getModel(bkm);
        List<TInformationItem> formalParameters = bkm.getEncapsulatedLogic().getFormalParameter();
        for (TInformationItem parameter : formalParameters) {
            String parameterName = ruleParameterName(parameter);
            String parameterNativeType = this.dmnTransformer.parameterNativeType(model, parameter);
            parameters.add(new Pair<>(parameterName, parameterNativeType));
        }
        String signature = parameters.stream().map(p -> this.expressionFactory.nullableParameter(p.getRight(), p.getLeft())).collect(Collectors.joining(", "));
        return this.dmnTransformer.augmentSignature(signature);
    }

    public String ruleArgumentList(TBusinessKnowledgeModel bkm) {
        List<String> arguments = new ArrayList<>();
        List<TInformationItem> formalParameters = bkm.getEncapsulatedLogic().getFormalParameter();
        for (TNamedElement element : formalParameters) {
            String argumentName = ruleArgumentName(element);
            arguments.add(argumentName);
        }
        String argumentList = String.join(", ", arguments);
        return this.dmnTransformer.augmentArgumentList(argumentList);
    }

    private String ruleParameterName(DRGElementReference<? extends TDRGElement> reference) {
        TDRGElement element = reference.getElement();
        if (element instanceof TInputData) {
            return this.dmnTransformer.inputDataVariableName(reference);
        } else if (element instanceof TDecision) {
            return this.dmnTransformer.drgElementVariableName(reference);
        }
        throw new UnsupportedOperationException(String.format("Not supported '%s'", element.getClass().getName()));
    }

    private String ruleParameterName(TInformationItem element) {
        return this.dmnTransformer.parameterVariableName(element);
    }

    private String ruleArgumentName(DRGElementReference<? extends TDRGElement> reference) {
        TDRGElement element = reference.getElement();
        if (element instanceof TInputData) {
            return this.dmnTransformer.inputDataVariableName(reference);
        } else if (element instanceof TDecision) {
            return this.dmnTransformer.drgElementVariableName(reference);
        }
        throw new UnsupportedOperationException(String.format("Not supported '%s'", element.getClass().getName()));
    }

    private String ruleArgumentName(TNamedElement element) {
        if (element instanceof TInputData) {
            return this.dmnTransformer.inputDataVariableName((TInputData) element);
        } else if (element instanceof TDecision) {
            return this.dmnTransformer.drgElementVariableName(((TDecision) element));
        } else if (element instanceof TInformationItem) {
            return this.dmnTransformer.parameterVariableName(((TInformationItem) element));
        }
        throw new UnsupportedOperationException(String.format("Not supported '%s'", element.getClass().getName()));
    }

    //
    // Rule condition
    //
    public String condition(TDRGElement element, TDecisionRule rule) {
        TExpression decisionTable = this.dmnModelRepository.expression(element);
        if (decisionTable instanceof TDecisionTable) {
            List<String> conditionParts = new ArrayList<>();
            for (int i = 0; i < rule.getInputEntry().size(); i++) {
                TUnaryTests inputEntry = rule.getInputEntry().get(i);
                String condition = condition(element, decisionTable, inputEntry, i);
                conditionParts.add(condition);
            }
            // Optimize AND operator
            List<String> optimizedConditionParts = this.expressionFactory.optimizeAndArguments(conditionParts);
            String left = this.expressionFactory.trueConstant();
            String right;
            if (optimizedConditionParts.size() == 1) {
                right = optimizedConditionParts.get(0);
            } else {
                String indent3tabs = "            ";
                String indent2tabs = "        ";
                String operands = optimizedConditionParts.stream().collect(Collectors.joining(",\n" + indent3tabs));
                right = String.format("booleanAnd(\n%s%s\n%s)", indent3tabs, operands, indent2tabs);
            }
            return this.expressionFactory.makeEquality(left, right);
        }
        throw new DMNRuntimeException("Cannot build condition for " + decisionTable.getClass().getSimpleName());
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
        Expression inputExpression = this.feelTranslator.analyzeSimpleExpressions(inputExpressionText, inputExpressionContext);

        // Generate code for input entry
        Environment inputEntryEnvironment = this.dmnTransformer.makeInputEntryEnvironment(element, inputExpression);
        FEELContext inputEntryContext = FEELContext.makeContext(element, inputEntryEnvironment);
        return this.feelTranslator.unaryTestsToJava(inputEntryText, inputEntryContext);
    }

    public String outputEntryToNative(TDRGElement element, TLiteralExpression outputEntryExpression, int outputIndex) {
        TExpression tExpression = this.dmnModelRepository.expression(element);
        if (tExpression instanceof TDecisionTable) {
            // Analyze output expression
            String outputEntryText = outputEntryExpression.getText();
            Environment outputEntryEnvironment = this.dmnTransformer.makeOutputEntryEnvironment(element, this.environmentFactory);
            if ("-".equals(outputEntryText)) {
                outputEntryText = "null";
            }
            Expression feelOutputEntryExpression = this.feelTranslator.analyzeSimpleExpressions(outputEntryText, FEELContext.makeContext(element, outputEntryEnvironment));

            // Generate code
            FEELContext context = FEELContext.makeContext(element, outputEntryEnvironment);
            return this.feelTranslator.simpleExpressionsToNative(feelOutputEntryExpression, context);
        } else {
            throw new UnsupportedOperationException(String.format("Not supported '%s'", tExpression.getClass().getSimpleName()));
        }
    }

    //
    // Annotations
    //
    public String annotationEscapedText(TDecisionRule rule) {
        String description = rule.getDescription();
        return description == null ? "" : StringEscapeUtil.escapeInString(description);
    }

    public String annotation(TDRGElement element, TDecisionRule rule) {
        String description = rule.getDescription();
        return this.dmnTransformer.annotation(element, description);
    }

    public String ruleAnnotationClassName() {
        return Rule.class.getName();
    }

    public String hitPolicyAnnotationClassName() {
        return HitPolicy.class.getName();
    }
}
