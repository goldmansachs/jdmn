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
package com.gs.dmn.transformation.basic;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.feel.analysis.semantics.environment.Environment;
import com.gs.dmn.feel.analysis.semantics.environment.EnvironmentFactory;
import com.gs.dmn.feel.analysis.semantics.type.ListType;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.synthesis.FEELTranslator;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.runtime.RuleOutput;
import com.gs.dmn.runtime.RuleOutputList;
import com.gs.dmn.runtime.annotation.HitPolicy;
import com.gs.dmn.runtime.annotation.Rule;
import com.gs.dmn.transformation.DMNToJavaTransformer;
import org.apache.commons.lang3.StringUtils;
import org.omg.spec.dmn._20180521.model.*;

import java.util.*;
import java.util.stream.Collectors;

import static com.gs.dmn.transformation.DMNToJavaTransformer.DECISION_RULE_OUTPUT_CLASS_SUFFIX;

public class DecisionTableToJavaTransformer {
    private final BasicDMN2JavaTransformer dmnTransformer;
    private final DMNModelRepository dmnModelRepository;
    private final FEELTranslator feelTranslator;
    private final EnvironmentFactory environmentFactory;

    DecisionTableToJavaTransformer(BasicDMN2JavaTransformer dmnTransformer) {
        this.dmnTransformer = dmnTransformer;
        this.dmnModelRepository = dmnTransformer.getDMNModelRepository();
        this.feelTranslator = dmnTransformer.getFEELTranslator();
        this.environmentFactory = dmnTransformer.getEnvironmentFactory();
    }

    public String defaultValue(TDRGElement element) {
        if (this.dmnModelRepository.isDecisionTableExpression(element)) {
            TDecisionTable decisionTable = (TDecisionTable) dmnModelRepository.expression(element);
            if (this.dmnModelRepository.hasDefaultValue(decisionTable)) {
                if (this.dmnModelRepository.isCompoundDecisionTable(element)) {
                    List<String> values = new ArrayList<>();
                    List<TOutputClause> output = sortOutputClauses(element, new ArrayList(decisionTable.getOutput()));
                    for(TOutputClause outputClause: output) {
                        values.add(defaultValue(element, outputClause));
                    }
                    String defaultValue = dmnTransformer.constructor(dmnTransformer.itemDefinitionJavaClassName(dmnTransformer.drgElementOutputClassName(element)), values.stream().collect(Collectors.joining(", ")));
                    if (dmnTransformer.isList(element)) {
                        return dmnTransformer.asList(defaultValue);
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
            return dmnTransformer.literalExpressionToJava(defaultOutputEntry.getText(), element);
        }
    }

    //
    // Output Clause
    //
    public String outputClauseClassName(TDRGElement element, TOutputClause outputClause) {
        Type type = toFEELType(element, outputClause);
        return dmnTransformer.toJavaType(type);
    }

    private Type toFEELType(TDRGElement element, TOutputClause outputClause) {
        // Check TOutputClause.typeRef
        QualifiedName outputClauseTypeRef = QualifiedName.toQualifiedName(outputClause.getTypeRef());
        if (outputClauseTypeRef != null) {
            return dmnTransformer.toFEELType(outputClauseTypeRef);
        }
        // Derive from parent typeRef
        QualifiedName parentTypeRef = this.dmnModelRepository.typeRef(element);
        if (this.dmnModelRepository.isCompoundDecisionTable(element)) {
            TItemDefinition itemDefinition = this.dmnModelRepository.lookupItemDefinition(parentTypeRef);
            if (itemDefinition != null) {
                for (TItemDefinition child : itemDefinition.getItemComponent()) {
                    if (child.getName().equals(outputClause.getName())) {
                        return dmnTransformer.toFEELType(child);
                    }
                }
            }
            throw new DMNRuntimeException(String.format("Cannot map typeRef of output clause '%s' in element '%s' to java", outputClause.getId(), element.getName()));
        } else {
            Type parentType = dmnTransformer.toFEELType(parentTypeRef);
            TDecisionTable decisionTable = dmnModelRepository.decisionTable(element);
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
        return dmnTransformer.lowerCaseFirst(name);
    }

    public String outputClausePriorityVariableName(TDRGElement element, TOutputClause outputClause) {
        String name = this.dmnModelRepository.outputClauseName(element, outputClause);
        if (name == null) {
            throw new DMNRuntimeException(String.format("Variable name cannot be null. OutputClause id '%s'", outputClause.getId()));
        }
        return dmnTransformer.lowerCaseFirst(name + DMNToJavaTransformer.PRIORITY_SUFFIX);
    }

    public Integer priority(TDRGElement element, TLiteralExpression literalExpression, int outputIndex) {
        String outputEntryText = literalExpression.getText();
        TExpression tExpression = dmnModelRepository.expression(element);
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
        String name = this.dmnModelRepository.outputClauseName(element, output);
        return dmnTransformer.getter(dmnTransformer.lowerCaseFirst(name));
    }

    public String priorityGetter(TDRGElement element, TOutputClause output) {
        return dmnTransformer.getter(dmnTransformer.lowerCaseFirst(this.dmnModelRepository.outputClauseName(element, output) + DMNToJavaTransformer.PRIORITY_SUFFIX));
    }

    public String setter(TDRGElement element, TOutputClause output) {
        return dmnTransformer.setter(dmnTransformer.lowerCaseFirst(this.dmnModelRepository.outputClauseName(element, output)));
    }

    public String prioritySetter(TDRGElement element, TOutputClause output) {
        return dmnTransformer.setter(dmnTransformer.lowerCaseFirst(this.dmnModelRepository.outputClauseName(element, output)) + DMNToJavaTransformer.PRIORITY_SUFFIX);
    }

    private List<TOutputClause> sortOutputClauses(TDRGElement element, List<TOutputClause> parameters) {
        parameters.sort(Comparator.comparing(o -> dmnModelRepository.outputClauseName(element, o)));
        return parameters;
    }

    //
    // Aggregation and hit policy
    //
    public String aggregator(TDRGElement element, TDecisionTable decisionTable, TOutputClause outputClause, String variableName) {
        TBuiltinAggregator aggregation = decisionTable.getAggregation();
        String decisionRuleOutputClassName = ruleOutputClassName(element);
        String getter = getter(element, outputClause);
        if (aggregation == TBuiltinAggregator.MIN) {
            return String.format("min(%s.stream().map(o -> ((%s)o).%s).collect(Collectors.toList()))",
                    variableName, decisionRuleOutputClassName, getter
            );
        } else if (aggregation == TBuiltinAggregator.MAX) {
            return String.format("max(%s.stream().map(o -> ((%s)o).%s).collect(Collectors.toList()))",
                    variableName, decisionRuleOutputClassName, getter
            );
        } else if (aggregation == TBuiltinAggregator.COUNT) {
            return "number(String.format(\"%d\", " + variableName + ".size()))";
        } else if (aggregation == TBuiltinAggregator.SUM) {
            return String.format("sum(%s.stream().map(o -> ((%s)o).%s).collect(Collectors.toList()))",
                    variableName, decisionRuleOutputClassName, getter
            );
        } else {
            throw new UnsupportedOperationException(String.format("Not supported '%s' aggregation.", aggregation));
        }
    }

    public HitPolicy hitPolicy(TDRGElement element) {
        TExpression expression = dmnModelRepository.expression(element);
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
        return dmnTransformer.upperCaseFirst(element.getName() + DECISION_RULE_OUTPUT_CLASS_SUFFIX);
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
        Set<TDRGElement> elementSet = new LinkedHashSet<>();
        this.dmnModelRepository.collectInputs(decision, elementSet);
        List<TDRGElement> elements = new ArrayList<>(elementSet);
        this.dmnModelRepository.sortNamedElements(elements);

        List<Pair<String, String>> parameters = new ArrayList<>();
        for (TDRGElement element : elements) {
            String parameterName = ruleParameterName(element);
            String parameterJavaType = dmnTransformer.lazyEvaluationType(element, dmnTransformer.parameterJavaType(element));
            parameters.add(new Pair<>(parameterName, parameterJavaType));
        }
        String signature = parameters.stream().map(p -> String.format("%s %s", p.getRight(), p.getLeft())).collect(Collectors.joining(", "));
        return dmnTransformer.augmentSignature(signature);
    }

    public String ruleArgumentList(TDecision decision) {
        Set<TDRGElement> elementSet = new LinkedHashSet<>();
        this.dmnModelRepository.collectInputs(decision, elementSet);
        List<TDRGElement> elements = new ArrayList<>(elementSet);
        this.dmnModelRepository.sortNamedElements(elements);

        List<String> arguments = new ArrayList<>();
        for (TDRGElement element : elements) {
            String argumentName = ruleArgumentName(element);
            arguments.add(argumentName);
        }
        String argumentList = arguments.stream().collect(Collectors.joining(", "));
        return dmnTransformer.augmentArgumentList(argumentList);
    }

    public String ruleSignature(TBusinessKnowledgeModel bkm) {
        List<Pair<String, String>> parameters = new ArrayList<>();
        List<TInformationItem> formalParameters = bkm.getEncapsulatedLogic().getFormalParameter();
        for (TNamedElement element : formalParameters) {
            String parameterName = ruleParameterName(element);
            String parameterJavaType = dmnTransformer.parameterJavaType(element);
            parameters.add(new Pair<>(parameterName, parameterJavaType));
        }
        String signature = parameters.stream().map(p -> String.format("%s %s", p.getRight(), p.getLeft())).collect(Collectors.joining(", "));
        return dmnTransformer.augmentSignature(signature);
    }

    public String ruleArgumentList(TBusinessKnowledgeModel bkm) {
        List<String> arguments = new ArrayList<>();
        List<TInformationItem> formalParameters = bkm.getEncapsulatedLogic().getFormalParameter();
        for (TNamedElement element : formalParameters) {
            String argumentName = ruleArgumentName(element);
            arguments.add(argumentName);
        }
        String argumentList = arguments.stream().collect(Collectors.joining(", "));
        return dmnTransformer.augmentArgumentList(argumentList);
    }

    private String ruleParameterName(TNamedElement element) {
        if (element instanceof TInputData) {
            return dmnTransformer.inputDataVariableName((TInputData) element);
        } else if (element instanceof TDecision) {
            return dmnTransformer.drgElementVariableName((TDecision) element);
        } else if (element instanceof TInformationItem) {
            return dmnTransformer.parameterVariableName(((TInformationItem) element));
        }
        throw new UnsupportedOperationException(String.format("Not supported '%s'", element.getClass().getName()));
    }

    private String ruleArgumentName(TNamedElement element) {
        if (element instanceof TInputData) {
            return dmnTransformer.inputDataVariableName((TInputData) element);
        } else if (element instanceof TDecision) {
            return dmnTransformer.drgElementVariableName(((TDecision) element));
        } else if (element instanceof TInformationItem) {
            return dmnTransformer.parameterVariableName(((TInformationItem) element));
        }
        throw new UnsupportedOperationException(String.format("Not supported '%s'", element.getClass().getName()));
    }

    //
    // Rule condition
    //
    public String condition(TDRGElement element, TDecisionRule rule) {
        TExpression decisionTable = dmnModelRepository.expression(element);
        if (decisionTable instanceof TDecisionTable) {
            List<String> conditionParts = new ArrayList<>();
            for (int i = 0; i < rule.getInputEntry().size(); i++) {
                TUnaryTests inputEntry = rule.getInputEntry().get(i);
                String condition = condition(element, decisionTable, inputEntry, i);
                conditionParts.add(condition);
            }
            if (conditionParts.size() == 1) {
                return String.format("Boolean.TRUE == %s", conditionParts.get(0));
            } else {
                String indent3tabs = "            ";
                String indent2tabs = "        ";
                String operands = conditionParts.stream().collect(Collectors.joining(",\n" + indent3tabs));
                return String.format("Boolean.TRUE == booleanAnd(\n%s%s\n%s)", indent3tabs, operands, indent2tabs);
            }
        }
        throw new DMNRuntimeException("Cannot build condition for " + decisionTable.getClass().getSimpleName());
    }

    private String condition(TDRGElement element, TExpression decisionTable, TUnaryTests inputEntry, int inputEntryIndex) {
        TInputClause tInputClause = ((TDecisionTable) decisionTable).getInput().get(inputEntryIndex);
        String inputExpressionText = tInputClause.getInputExpression().getText();
        String inputEntryText = inputEntry.getText();
        try {
            return inputEntryToJava(element, inputExpressionText, inputEntryText);
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot build condition for input clause '%s' for entry '%s'", inputExpressionText, inputEntryText), e);
        }
    }

    private String inputEntryToJava(TDRGElement element, String inputExpressionText, String inputEntryText) {
        // Analyze input expression
        Environment inputExpressionEnvironment = dmnTransformer.makeEnvironment(element);
        FEELContext inputExpressionContext = FEELContext.makeContext(inputExpressionEnvironment);
        Expression inputExpression = feelTranslator.analyzeSimpleExpressions(inputExpressionText, inputExpressionContext);

        // Generate code for input entry
        Environment inputEntryEnvironment = dmnTransformer.makeInputEntryEnvironment(element, inputExpression);
        FEELContext inputEntryContext = FEELContext.makeContext(inputEntryEnvironment);
        return feelTranslator.unaryTestsToJava(inputEntryText, inputEntryContext);
    }

    public String outputEntryToJava(TDRGElement element, TLiteralExpression outputEntryExpression, int outputIndex) {
        TExpression tExpression = dmnModelRepository.expression(element);
        if (tExpression instanceof TDecisionTable) {
            // Analyze output expression
            String outputEntryText = outputEntryExpression.getText();
            Environment outputEntryEnvironment = dmnTransformer.makeOutputEntryEnvironment(element, environmentFactory);
            if ("-".equals(outputEntryText)) {
                outputEntryText = "null";
            }
            Expression feelOutputEntryExpression = feelTranslator.analyzeSimpleExpressions(outputEntryText, FEELContext.makeContext(outputEntryEnvironment));

            // Generate code
            FEELContext context = FEELContext.makeContext(outputEntryEnvironment);
            return feelTranslator.simpleExpressionsToJava(feelOutputEntryExpression, context);
        } else {
            throw new UnsupportedOperationException(String.format("Not supported '%s'", tExpression.getClass().getSimpleName()));
        }
    }

    //
    // Annotations
    //
    public String annotationEscapedText(TDecisionRule rule) {
        String description = rule.getDescription();
        return description == null ? "" : dmnTransformer.escapeInString(description);
    }

    public String annotation(TDRGElement element, TDecisionRule rule) {
        String description = rule.getDescription();
        return dmnTransformer.annotation(element, description);
    }

    public String ruleAnnotationClassName() {
        return Rule.class.getName();
    }

    public String hitPolicyAnnotationClassName() {
        return HitPolicy.class.getName();
    }
}
