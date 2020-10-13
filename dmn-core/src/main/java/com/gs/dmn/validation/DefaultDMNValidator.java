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
package com.gs.dmn.validation;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.transformation.AbstractDMNToNativeTransformer;
import com.gs.dmn.transformation.basic.QualifiedName;
import org.apache.commons.lang3.StringUtils;
import org.omg.spec.dmn._20180521.model.*;

import javax.xml.bind.JAXBElement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class DefaultDMNValidator extends SimpleDMNValidator {
    public DefaultDMNValidator() {
        super(new Slf4jBuildLogger(LOGGER));
    }

    public DefaultDMNValidator(BuildLogger logger) {
        super(logger);
    }

    @Override
    public List<String> validate(DMNModelRepository dmnModelRepository) {
        List<String> errors = new ArrayList<>();
        if (isEmpty(dmnModelRepository)) {
            logger.warn("DMN repository is empty; validator will not run");
            return errors;
        }

        for (TDefinitions definitions: dmnModelRepository.getAllDefinitions()) {
            logger.debug("Validate unique 'DRGElement.id'");
            validateUnique(
                    definitions, new ArrayList<>(dmnModelRepository.findDRGElements(definitions)), "DRGElement", "id", false,
                    TDMNElement::getId, null, errors
            );

            logger.debug("Validate unique 'DRGElement.name'");
            validateUnique(
                    definitions, new ArrayList<>(dmnModelRepository.findDRGElements(definitions)), "DRGElement", "name", false,
                    e -> ((TNamedElement)e).getName(), null, errors
            );

            logger.debug("Validate unique 'ItemDefinition.name'");
            validateUnique(
                    definitions, new ArrayList<>(dmnModelRepository.findItemDefinitions(definitions)), "ItemDefinition", "name", false,
                    e -> ((TNamedElement)e).getName(), null, errors
            );

            for (TDRGElement element : dmnModelRepository.findDRGElements(definitions)) {
                logger.debug(String.format("Validate element '%s'", element.getName()));
                if (element instanceof TInputData) {
                    validateInputData(definitions, (TInputData) element, errors);
                } else if (element instanceof TBusinessKnowledgeModel) {
                    validateBusinessKnowledgeModel(definitions, (TBusinessKnowledgeModel) element, errors);
                } else if (element instanceof TDecision) {
                    validateDecision(definitions, (TDecision) element, errors);
                }
            }
        }

        return errors;
    }

    private void validateUnique(TDefinitions definitions, List<? extends TDMNElement> elements, String elementType, String property, boolean isOptionalProperty, Function<TDMNElement, String> accessor, String errorMessage, List<String> errors) {
        if (errorMessage == null) {
            errorMessage = String.format("The '%s' of a '%s' must be unique.", property, elementType);
        }
        // Create a map
        Map<String, List<TDMNElement>> map = new LinkedHashMap<>();
        for (TDMNElement element : elements) {
            String key = accessor.apply(element);
            if (!isOptionalProperty || key != null) {
                List<TDMNElement> list = map.get(key);
                if (list == null) {
                    list = new ArrayList<>();
                    list.add(element);
                    map.put(key, list);
                } else {
                    list.add(element);
                }
            }
        }
        // Find duplicates
        List<String> duplicates = new ArrayList<>();
        for (Map.Entry<String, List<TDMNElement>> entry : map.entrySet()) {
            String key = entry.getKey();
            if(entry.getValue().size() > 1){
                duplicates.add(key);
            }
        }
        // Report error
        if (!duplicates.isEmpty()) {
            String message = String.join(", ", duplicates);
            errors.add(makeError(definitions, null, String.format("%s Found duplicates for '%s'.", errorMessage, message)));
        }
    }

    private void validateInputData(TDefinitions definitions, TInputData inputData, List<String> errors) {
        validateNamedElement(definitions, inputData, errors);
        validateVariable(definitions, inputData, inputData.getVariable(), errors);
    }

    protected void validateBusinessKnowledgeModel(TDefinitions definitions, TBusinessKnowledgeModel knowledgeModel, List<String> errors) {
        validateNamedElement(definitions, knowledgeModel, errors);
        validateVariable(definitions, knowledgeModel, knowledgeModel.getVariable(), errors);
    }

    protected void validateDecision(TDefinitions definitions, TDecision decision, List<String> errors) {
        validateNamedElement(definitions, decision, errors);
        TInformationItem variable = decision.getVariable();
        validateVariable(definitions, decision, variable, errors);
        String decisionName = decision.getName();
        if (variable != null) {
            // decision/@name == decision/variable/@name
            String variableName = variable.getName();
            if (!decisionName.equals(variableName)) {
                String errorMessage = String.format("Decision name and variable name should be the same. Found '%s' and '%s'", decisionName, variableName);
                errors.add(makeError(definitions, decision, errorMessage));
            }
            // decision/variable/@typeRef is not null
            QualifiedName typeRef = QualifiedName.toQualifiedName(definitions, variable.getTypeRef());
            if (typeRef == null) {
                String errorMessage = "Missing typRef in variable";
                errors.add(makeError(definitions, decision, errorMessage));
            }
        } else {
            String errorMessage = "Missing variable";
            errors.add(makeError(definitions, decision, errorMessage));
        }

        // Validate requirements
        List<TInformationRequirement> informationRequirement = decision.getInformationRequirement();
        Function<TDMNElement, String> accessor =
                (TDMNElement e) -> {
                    TInformationRequirement ir = (TInformationRequirement) e;
                    if (ir.getRequiredInput() != null) {
                        return ir.getRequiredInput().getHref();
                    } else {
                        return ir.getRequiredDecision().getHref();
                    }
                };
        validateUnique(definitions, informationRequirement, "TInformationRequirement", "href", false,
                accessor, decision.getName(), errors);

        // Validate expression
        JAXBElement<? extends TExpression> element = decision.getExpression();
        if (element != null && element.getValue() != null) {
            validateExpression(definitions, decision, element, errors);
        }
    }

    private void validateDecisionTable(TDefinitions definitions, TDMNElement element, TDecisionTable decisionTable, List<String> errors) {
        List<TInputClause> input = decisionTable.getInput();
        if (input == null || input.isEmpty()) {
            String errorMessage = "Missing input clauses";
            errors.add(makeError(definitions, element, errorMessage));
        }
        List<TOutputClause> output = decisionTable.getOutput();
        if (output == null || output.isEmpty()) {
            String errorMessage = "Missing output clauses";
            errors.add(makeError(definitions, element, errorMessage));
        }
        validateHitPolicy(definitions, element, decisionTable, errors);
        List<TDecisionRule> ruleList = decisionTable.getRule();
        if (ruleList == null || ruleList.isEmpty()) {
            String errorMessage = "Missing rules in decision table";
            errors.add(makeError(definitions, element, errorMessage));
        } else {
            for (TDecisionRule rule : ruleList) {
                validateRule(definitions, element, rule, errors);
            }
        }
    }

    private void validateHitPolicy(TDefinitions definitions, TDMNElement element, TDecisionTable decisionTable, List<String> errors) {
        List<TOutputClause> output = decisionTable.getOutput();
        THitPolicy hitPolicy = decisionTable.getHitPolicy();
        TBuiltinAggregator aggregation = decisionTable.getAggregation();
        if (hitPolicy != THitPolicy.COLLECT && aggregation != null) {
            String errorMessage = String.format("Aggregation '%s' not allowed for hit policy '%s'", aggregation, hitPolicy);
            errors.add(makeError(definitions, element, errorMessage));
        }
        if (output != null && output.size() > 1
                && hitPolicy == THitPolicy.COLLECT
                && aggregation != null) {
            String errorMessage = String.format("Collect operator is not defined over multiple outputs for decision table '%s'", decisionTable.getId());
            errors.add(makeError(definitions, element, errorMessage));
        }
    }

    private void validateNamedElement(TDefinitions definitions, TNamedElement element, List<String> errors) {
        if (StringUtils.isBlank(element.getName())) {
            String errorMessage = "Missing name";
            errors.add(makeError(definitions, element, errorMessage));
        }
    }

    private void validateVariable(TDefinitions definitions, TNamedElement element, TInformationItem variable, List<String> errors) {
        if (variable != null && variable.getName() == null) {
            String errorMessage = "Missing variable name";
            errors.add(makeError(definitions, element, errorMessage));
        }
    }

    private void validateExpression(TDefinitions definitions, TDRGElement element, JAXBElement<? extends TExpression> expressionElement, List<String> errors) {
        if (expressionElement == null || expressionElement.getValue() == null) {
            String errorMessage = "Missing expression";
            errors.add(makeError(definitions, element, errorMessage));
        } else {
            TExpression expression = expressionElement.getValue();
            if (expression instanceof TDecisionTable) {
                TDecisionTable decisionTable = (TDecisionTable) expression;
                validateDecisionTable(definitions, element, decisionTable, errors);
            } else if (expression instanceof TInvocation) {
                TInvocation invocation = (TInvocation) expression;
                validateExpression(definitions, element, invocation.getExpression(), errors);
            } else if (expression instanceof TLiteralExpression) {
                TLiteralExpression literalExpression = (TLiteralExpression) expression;
                String expressionLanguage = ((TLiteralExpression) expression).getExpressionLanguage();
                if (!isSupported(expressionLanguage)) {
                    String errorMessage = String.format("Not supported expression language '%s'", expressionLanguage);
                    errors.add(makeError(definitions, element, errorMessage));
                }
                if (StringUtils.isBlank(literalExpression.getText())) {
                    String errorMessage = "Missing text in literal expressions";
                    errors.add(makeError(definitions, element, errorMessage));
                }
            } else if (expression instanceof TContext) {
                List<TContextEntry> contextEntryList = ((TContext) expression).getContextEntry();
                if (contextEntryList.isEmpty()) {
                    String errorMessage = "Missing entries in context expression";
                    errors.add(makeError(definitions, element, errorMessage));
                }
            } else if (expression instanceof TRelation) {
                if (((TRelation) expression).getColumn() == null && ((TRelation) expression).getRow() == null) {
                    String errorMessage = "Empty relation";
                    errors.add(makeError(definitions, element, errorMessage));
                }
            } else {
                throw new UnsupportedOperationException("Not supported DMN expression type " + expression.getClass().getName());
            }
        }
    }

    private boolean isSupported(String expressionLanguage) {
        return expressionLanguage == null || AbstractDMNToNativeTransformer.SUPPORTED_LANGUAGES.contains(expressionLanguage);
    }

    private void validateRule(TDefinitions definitions, TDMNElement element, TDecisionRule rule, List<String> errors) {
        List<TUnaryTests> inputEntry = rule.getInputEntry();
        if (inputEntry == null || inputEntry.isEmpty()) {
            String errorMessage = "No input entries for rule " + rule.getId();
            errors.add(makeError(definitions, element, errorMessage));
        }
        List<TLiteralExpression> outputEntry = rule.getOutputEntry();
        if (outputEntry == null || outputEntry.isEmpty()) {
            String errorMessage = "No outputEntry entries for rule " + rule.getId();
            errors.add(makeError(definitions, element, errorMessage));
        }
    }
}
