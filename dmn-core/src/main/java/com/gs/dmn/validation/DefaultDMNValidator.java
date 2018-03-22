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
package com.gs.dmn.validation;

import com.google.common.base.Function;
import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.transformation.DMNToJavaTransformer;
import org.apache.commons.lang3.StringUtils;
import org.omg.spec.dmn._20151101.dmn.*;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DefaultDMNValidator extends SimpleDMNValidator {
    public DefaultDMNValidator() {
        super(new Slf4jBuildLogger(LOGGER));
    }

    public DefaultDMNValidator(BuildLogger logger) {
        super(logger);
    }

    @Override
    public void validate(DMNModelRepository dmnModelRepository) {
        if (dmnModelRepository == null) {
            throw new IllegalArgumentException("Missing definitions");
        }

        logger.debug("Validate unique 'DRGElement.id'");
        validateUnique(
                "DRGElement", "id", false,
                new ArrayList<>(dmnModelRepository.drgElements()), TDMNElement::getId, null
        );

        logger.debug("Validate unique 'DRGElement.name'");
        validateUnique(
                "DRGElement", "name", false,
                new ArrayList<>(dmnModelRepository.drgElements()), TNamedElement::getName, null
        );

        logger.debug("Validate unique 'ItemDefinition.name'");
        validateUnique(
                "ItemDefinition", "name", false,
                new ArrayList<>(dmnModelRepository.itemDefinitions()), TNamedElement::getName, null
        );
        for (TDRGElement element : dmnModelRepository.drgElements()) {
            logger.debug(String.format("Validate element '%s'", element.getName()));
            if (element instanceof TInputData) {
                validateInputData((TInputData) element);
            } else if (element instanceof TBusinessKnowledgeModel) {
                validateBusinessKnowledgeModel((TBusinessKnowledgeModel) element);
            } else if (element instanceof TDecision) {
                validateDecision((TDecision) element, dmnModelRepository);
            }
        }
    }

    private void validateUnique(String elementType, String property, boolean isOptionalProperty, List<TNamedElement> elements, Function<TNamedElement, String> accessor, String errorMessage) {
        if (errorMessage == null) {
            errorMessage = String.format("The '%s' of a '%s' must be unique.", property, elementType);
        }
        // Create a map
        Map<String, List<TDMNElement>> map = new LinkedHashMap<>();
        for (TDMNElement element : elements) {
            String key = accessor.apply((TNamedElement) element);
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
            String message = duplicates.stream().collect(Collectors.joining(", "));
            throw new IllegalArgumentException(String.format("%s Found duplicates for '%s'.", errorMessage, message));
        }
    }

    private void validateInputData(TInputData inputData) {
        validateNamedElement(inputData);
        validateVariable(inputData, inputData.getVariable());
    }

    protected void validateBusinessKnowledgeModel(TBusinessKnowledgeModel knowledgeModel) {
        validateNamedElement(knowledgeModel);
        validateVariable(knowledgeModel, knowledgeModel.getVariable());
    }

    protected void validateDecision(TDecision decision, DMNModelRepository dmnModelRepository) {
        validateNamedElement(decision);
        TInformationItem variable = decision.getVariable();
        validateVariable(decision, variable);
        String decisionName = decision.getName();
        if (variable != null) {
            // decision/@name == decision/variable/@name
            String variableName = variable.getName();
            if (!decisionName.equals(variableName)) {
                throw new IllegalArgumentException(String.format("Decision name and variable name should be the same. Found '%s' and '%s'", decisionName, variableName));
            }
            // decision/variable/@typeRef is not null
            QName typeRef = variable.getTypeRef();
            if (typeRef == null) {
                throw new IllegalArgumentException(String.format("Variable typRef is missing in decision '%s'", decisionName));
            }
        } else {
            throw new IllegalArgumentException(String.format("Missing variable for '%s'", decision.getName()));
        }

        // Validate requirements
        List<TInformationRequirement> informationRequirement = decision.getInformationRequirement();
        List<TNamedElement> references = informationRequirement.stream()
                .map(ir -> ir.getRequiredDecision() != null ? dmnModelRepository.findDecisionById(ir.getRequiredDecision().getHref()) : dmnModelRepository.findInputDataById(ir.getRequiredInput().getHref())).collect(Collectors.toList());
        validateUnique("DRGElement", "name", false,
                references, TNamedElement::getName, decision.getName());

        // Validate expression
        JAXBElement<? extends TExpression> element = decision.getExpression();
        if (element != null && element.getValue() != null) {
            validateExpression(decisionName, element);
        }
    }

    private void validateDecisionTable(String decisionName, TDecisionTable decisionTable) {
        List<TInputClause> input = decisionTable.getInput();
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("No input clauses for decision " + decisionName);
        }
        List<TOutputClause> output = decisionTable.getOutput();
        if (output == null || output.isEmpty()) {
            throw new IllegalArgumentException("No output clauses for decision " + decisionName);
        }
        List<TDecisionRule> ruleList = decisionTable.getRule();
        if (ruleList == null || ruleList.isEmpty()) {
            throw new IllegalArgumentException("No rules for decision " + decisionName);
        }
        validateHitPolicy(decisionTable);
        for (TDecisionRule rule : ruleList) {
            validateRule(rule);
        }
    }

    private void validateHitPolicy(TDecisionTable decisionTable) {
        List<TOutputClause> output = decisionTable.getOutput();
        THitPolicy hitPolicy = decisionTable.getHitPolicy();
        TBuiltinAggregator aggregation = decisionTable.getAggregation();
        if (hitPolicy != THitPolicy.COLLECT && aggregation != null) {
            throw new IllegalArgumentException(String.format("Aggregation '%s' not allowed for hit policy '%s'", aggregation, hitPolicy));
        }
        if (output != null && output.size() > 1
                && hitPolicy == THitPolicy.COLLECT
                && aggregation != null) {
            throw new IllegalArgumentException(String.format("Collect operator is not defined over multiple outputs for decision table '%s'", decisionTable.getId()));
        }
    }

    private void validateNamedElement(TNamedElement element) {
        if (StringUtils.isBlank(element.getName())) {
            throw new IllegalArgumentException(String.format("Missing name for element '%s'", element.getId()));
        }
    }

    private void validateVariable(TNamedElement element, TInformationItem variable) {
        if (variable != null && variable.getName() == null) {
            throw new IllegalArgumentException(String.format("Missing variable name for '%s'", element.getName()));
        }
    }

    private void validateExpression(String name, JAXBElement<? extends TExpression> expressionElement) {
        if (expressionElement == null || expressionElement.getValue() == null) {
            throw new IllegalArgumentException(String.format("Missing expression in element '%s'", name));
        } else {
            TExpression expression = expressionElement.getValue();
            if (expression instanceof TDecisionTable) {
                TDecisionTable decisionTable = (TDecisionTable) expression;
                validateDecisionTable(name, decisionTable);
            } else if (expression instanceof TInvocation) {
                TInvocation invocation = (TInvocation) expression;
                validateExpression(invocation.getLabel(), invocation.getExpression());
            } else if (expression instanceof TLiteralExpression) {
                TLiteralExpression literalExpression = (TLiteralExpression) expression;
                String expressionLanguage = ((TLiteralExpression) expression).getExpressionLanguage();
                if (!isSupported(expressionLanguage)) {
                    throw new UnsupportedOperationException(String.format("Not supported expression language '%s' in decision '%s'", expressionLanguage, name));
                }
                if (StringUtils.isBlank(literalExpression.getText())) {
                    throw new IllegalArgumentException(String.format("Missing text in literalExpressions in element '%s'", name));
                }
            } else if (expression instanceof TContext) {
                List<TContextEntry> contextEntryList = ((TContext) expression).getContextEntry();
                if (contextEntryList.isEmpty()) {
                    throw new IllegalArgumentException(String.format("Missing entries in context '%s'", name));
                }
            } else if (expression instanceof TRelation) {
                if (((TRelation) expression).getColumn() == null && ((TRelation) expression).getRow() == null) {
                    throw new IllegalArgumentException(String.format("Empty relation '%s'", name));
                }
            } else {
                throw new UnsupportedOperationException("Not supported DMN expression type " + expression.getClass().getName());
            }
        }
    }

    private boolean isSupported(String expressionLanguage) {
        return expressionLanguage == null || DMNToJavaTransformer.SUPPORTED_LANGUAGES.contains(expressionLanguage);
    }

    private void validateRule(TDecisionRule rule) {
        List<TUnaryTests> inputEntry = rule.getInputEntry();
        if (inputEntry == null || inputEntry.isEmpty()) {
            throw new IllegalArgumentException("No input entries for rule " + rule.getId());
        }
        List<TLiteralExpression> outputEntry = rule.getOutputEntry();
        if (outputEntry == null || outputEntry.isEmpty()) {
            throw new IllegalArgumentException("No outputEntry entries for rule " + rule.getId());
        }
    }
}
