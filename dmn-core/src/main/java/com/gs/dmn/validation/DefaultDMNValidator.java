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
import com.gs.dmn.QualifiedName;
import com.gs.dmn.ast.*;
import com.gs.dmn.ast.visitor.TraversalVisitor;
import com.gs.dmn.error.ErrorHandler;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.transformation.AbstractDMNToNativeTransformer;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DefaultDMNValidator extends SimpleDMNValidator {
    public DefaultDMNValidator() {
        this(new Slf4jBuildLogger(LOGGER));
    }

    public DefaultDMNValidator(BuildLogger logger) {
        super(logger);
    }

    @Override
    public List<String> validate(DMNModelRepository repository) {
        ValidationContext context = new ValidationContext(repository);
        if (isEmpty(repository)) {
            this.logger.warn("DMN repository is empty; validator will not run");
            return context.getErrors();
        }

        DefaultDMNValidatorVisitor visitor = new DefaultDMNValidatorVisitor(this.errorHandler, this.logger, this);
        for (TDefinitions definitions: repository.getAllDefinitions()) {
            definitions.accept(visitor, context);
        }

        return context.getErrors();
    }

    protected void validateInputData(TDefinitions definitions, TInputData element, ValidationContext context) {
        validateNamedElement(definitions, element, context);
        validateVariable(definitions, element, element.getVariable(), true, context);
    }

    protected void validateDecision(TDefinitions definitions, TDecision element, ValidationContext context) {
        validateNamedElement(definitions, element, context);
        TInformationItem variable = element.getVariable();
        validateVariable(definitions, element, variable, true, context);
        validateInformationRequirements(definitions, element, element.getInformationRequirement(), context);
        List<TDMNElementReference> krs = element.getKnowledgeRequirement().stream().map(TKnowledgeRequirement::getRequiredKnowledge).collect(Collectors.toList());
        validateReferences(definitions, element, krs, context);
        validateExpression(definitions, element, context);
    }

    protected void validateBusinessKnowledgeModel(TDefinitions definitions, TBusinessKnowledgeModel element, ValidationContext context) {
        validateNamedElement(definitions, element, context);
        validateVariable(definitions, element, element.getVariable(), false, context);
        List<TDMNElementReference> krs = element.getKnowledgeRequirement().stream().map(TKnowledgeRequirement::getRequiredKnowledge).collect(Collectors.toList());
        validateReferences(definitions, element, krs, context);
    }

    protected void validateDecisionService(TDefinitions definitions, TDecisionService element, ValidationContext context) {
        validateNamedElement(definitions, element, context);
        validateVariable(definitions, element, element.getVariable(), false, context);
        validateReferences(definitions, element, element.getInputData(), context);
        validateReferences(definitions, element, element.getInputDecision(), context);
        validateReferences(definitions, element, element.getOutputDecision(), context);
        validateReferences(definitions, element, element.getEncapsulatedDecision(), context);
    }

    protected void validateUnique(TDefinitions definitions, List<? extends TDMNElement> elements, String elementType, String property, boolean isOptionalProperty, Function<TDMNElement, String> accessor, String errorMessage, ValidationContext context) {
        if (errorMessage == null) {
            errorMessage = "The '%s' of a '%s' must be unique.".formatted(property, elementType);
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
            context.addError(makeError(context.getRepository(), definitions, null, "%s Found duplicates for '%s'.".formatted(errorMessage, message)));
        }
    }

    private void validateUniqueReferences(TDefinitions definitions, List<? extends TDMNElementReference> elements, String elementType, String property, boolean isOptionalProperty, Function<TDMNElementReference, String> accessor, String errorMessage, ValidationContext context) {
        if (errorMessage == null) {
            errorMessage = "The '%s' of a '%s' must be unique.".formatted(property, elementType);
        }
        // Create a map
        Map<String, List<TDMNElementReference>> map = new LinkedHashMap<>();
        for (TDMNElementReference element : elements) {
            String key = accessor.apply(element);
            if (!isOptionalProperty || key != null) {
                List<TDMNElementReference> list = map.get(key);
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
        for (Map.Entry<String, List<TDMNElementReference>> entry : map.entrySet()) {
            String key = entry.getKey();
            if(entry.getValue().size() > 1){
                duplicates.add(key);
            }
        }
        // Report error
        if (!duplicates.isEmpty()) {
            String message = String.join(", ", duplicates);
            context.addError(makeError(context.getRepository(), definitions, null, "%s Found duplicates for '%s'.".formatted(errorMessage, message)));
        }
    }

    private void validateNamedElement(TDefinitions definitions, TNamedElement element, ValidationContext context) {
        if (StringUtils.isBlank(element.getName())) {
            String errorMessage = "Missing name";
            context.addError(makeError(context.getRepository(), definitions, element, errorMessage));
        }
    }

    private void validateVariable(TDefinitions definitions, TNamedElement element, TInformationItem variable, boolean validateTypeRef, ValidationContext context) {
        DMNModelRepository repository = context.getRepository();

        if (variable == null) {
            String errorMessage = "Missing variable";
            context.addError(makeError(repository, definitions, element, errorMessage));
        } else {
            if (variable.getName() == null) {
                String errorMessage = "Missing variable name";
                context.addError(makeError(context.getRepository(), definitions, element, errorMessage));
            } else {
                // element/@name == element/variable/@name
                String variableName = variable.getName();
                String elementName = element.getName();
                if (!elementName.equals(variableName)) {
                    String errorMessage = "DRGElement name and variable name should be the same. Found '%s' and '%s'".formatted(elementName, variableName);
                    context.addError(makeError(repository, definitions, element, errorMessage));
                }
                // decision/variable/@typeRef is not null
                if (validateTypeRef) {
                    QualifiedName typeRef = QualifiedName.toQualifiedName(definitions, variable.getTypeRef());
                    if (repository.isNull(typeRef)) {
                        String errorMessage = "Missing typRef in variable";
                        context.addError(makeError(repository, definitions, element, errorMessage));
                    }
                }
            }
        }
    }

    private void validateInformationRequirements(TDefinitions definitions, TNamedElement decision, List<TInformationRequirement> informationRequirements, ValidationContext context) {
        // Validate requirements
        Function<TDMNElement, String> accessor =
                (TDMNElement e) -> {
                    TInformationRequirement ir = (TInformationRequirement) e;
                    if (ir.getRequiredInput() != null) {
                        return ir.getRequiredInput().getHref();
                    } else {
                        return ir.getRequiredDecision().getHref();
                    }
                };
        validateUnique(definitions, informationRequirements, "TInformationRequirement", "href", false,
                accessor, decision.getName(), context);
    }

    private void validateReferences(TDefinitions definitions, TNamedElement decision, List<TDMNElementReference> references, ValidationContext context) {
        // Validate requirements
        Function<TDMNElementReference, String> accessor =
                TDMNElementReference::getHref;
        validateUniqueReferences(definitions, references, "TDMNElementReference", "href", false,
                accessor, decision.getName(), context);
    }

    private void validateExpression(TDefinitions definitions, TDecision decision, ValidationContext context) {
        // Validate expression
        TExpression expression = decision.getExpression();
        if (expression != null) {
            validateExpression(definitions, decision, expression, context);
        }
    }

    private void validateExpression(TDefinitions definitions, TDRGElement element, TExpression expression, ValidationContext context) {
        DMNModelRepository repository = context.getRepository();
        if (expression == null) {
            String errorMessage = "Missing expression";
            context.addError(makeError(repository, definitions, element, errorMessage));
        } else {
            if (expression instanceof TDecisionTable decisionTable) {
                validateDecisionTable(definitions, element, decisionTable, context);
            } else if (expression instanceof TInvocation invocation) {
                validateExpression(definitions, element, invocation.getExpression(), context);
            } else if (expression instanceof TLiteralExpression literalExpression) {
                String expressionLanguage = literalExpression.getExpressionLanguage();
                if (!isSupported(expressionLanguage)) {
                    String errorMessage = "Not supported expression language '%s'".formatted(expressionLanguage);
                    context.addError(makeError(repository, definitions, element, errorMessage));
                }
                if (StringUtils.isBlank(literalExpression.getText())) {
                    String errorMessage = "Missing text in literal expressions";
                    context.addError(makeError(repository, definitions, element, errorMessage));
                }
            } else if (expression instanceof TContext tContext) {
                List<TContextEntry> contextEntryList = tContext.getContextEntry();
                if (contextEntryList.isEmpty()) {
                    String errorMessage = "Missing entries in context expression";
                    context.addError(makeError(repository, definitions, element, errorMessage));
                }
            } else if (expression instanceof TRelation relation) {
                if (relation.getColumn() == null && relation.getRow() == null) {
                    String errorMessage = "Empty relation";
                    context.addError(makeError(repository, definitions, element, errorMessage));
                }
            } else if (expression instanceof TConditional conditional) {
                checkChildExpression(definitions, element, conditional.getIf(), "conditional", "if", context);
                checkChildExpression(definitions, element, conditional.getThen(), "conditional", "then", context);
                checkChildExpression(definitions, element, conditional.getElse(), "conditional", "else", context);
            } else if (expression instanceof TFilter filter) {
                checkChildExpression(definitions, element, filter.getIn(), "filter", "in", context);
                checkChildExpression(definitions, element, filter.getMatch(), "filter", "match", context);
            } else if (expression instanceof TFor for1) {
                checkChildExpression(definitions, element, for1.getIn(), "for", "in", context);
                checkChildExpression(definitions, element, for1.getReturn(), "for", "return", context);
            } else if (expression instanceof TSome some) {
                String parentName = "some";
                checkChildExpression(definitions, element, some.getIn(), parentName, "in", context);
                checkChildExpression(definitions, element, some.getSatisfies(), parentName, "satisfies", context);
            } else if (expression instanceof TEvery every) {
                String parentName = "every";
                checkChildExpression(definitions, element, every.getIn(), parentName, "in", context);
                checkChildExpression(definitions, element, every.getSatisfies(), parentName, "satisfies", context);
            } else {
                throw new UnsupportedOperationException("Not supported DMN expression type " + expression.getClass().getName());
            }
        }
    }


    private void validateDecisionTable(TDefinitions definitions, TDMNElement element, TDecisionTable decisionTable, ValidationContext context) {
        DMNModelRepository repository = context.getRepository();
        List<TInputClause> input = decisionTable.getInput();
        if (input == null || input.isEmpty()) {
            String errorMessage = "Missing input clauses";
            context.addError(makeError(repository, definitions, element, errorMessage));
        }
        List<TOutputClause> output = decisionTable.getOutput();
        if (output == null || output.isEmpty()) {
            String errorMessage = "Missing output clauses";
            context.addError(makeError(repository, definitions, element, errorMessage));
        }
        validateHitPolicy(definitions, element, decisionTable, context);
        List<TDecisionRule> ruleList = decisionTable.getRule();
        if (ruleList == null || ruleList.isEmpty()) {
            String errorMessage = "Missing rules in decision table";
            context.addError(makeError(repository, definitions, element, errorMessage));
        } else {
            for (TDecisionRule rule : ruleList) {
                validateRule(definitions, element, rule, context);
            }
        }
    }

    private void validateHitPolicy(TDefinitions definitions, TDMNElement element, TDecisionTable decisionTable, ValidationContext context) {
        DMNModelRepository repository = context.getRepository();

        List<TOutputClause> output = decisionTable.getOutput();
        THitPolicy hitPolicy = decisionTable.getHitPolicy();
        TBuiltinAggregator aggregation = decisionTable.getAggregation();
        if (hitPolicy != THitPolicy.COLLECT && aggregation != null) {
            String errorMessage = "Aggregation '%s' not allowed for hit policy '%s'".formatted(aggregation, hitPolicy);
            context.addError(makeError(repository, definitions, element, errorMessage));
        }
        if (output != null && output.size() > 1
                && hitPolicy == THitPolicy.COLLECT
                && aggregation != null) {
            String errorMessage = "Collect operator is not defined over multiple outputs for decision table '%s'".formatted(decisionTable.getId());
            context.addError(makeError(repository, definitions, element, errorMessage));
        }
    }

    private boolean isSupported(String expressionLanguage) {
        return expressionLanguage == null || AbstractDMNToNativeTransformer.SUPPORTED_LANGUAGES.contains(expressionLanguage);
    }

    private void validateRule(TDefinitions definitions, TDMNElement element, TDecisionRule rule, ValidationContext context) {
        DMNModelRepository repository = context.getRepository();
        List<TUnaryTests> inputEntry = rule.getInputEntry();
        if (inputEntry == null || inputEntry.isEmpty()) {
            String errorMessage = "No input entries for rule " + rule.getId();
            context.addError(makeError(repository, definitions, element, errorMessage));
        }
        List<TLiteralExpression> outputEntry = rule.getOutputEntry();
        if (outputEntry == null || outputEntry.isEmpty()) {
            String errorMessage = "No outputEntry entries for rule " + rule.getId();
            context.addError(makeError(repository, definitions, element, errorMessage));
        }
    }

    private void checkChildExpression(TDefinitions definitions, TDRGElement element, TChildExpression childExpression, String parentName, String childName, ValidationContext context) {
        String errorMessage = "Missing '%s' expression in '%s' boxed expression in element '%s'".formatted(childName, parentName, element.getName());
        if (childExpression == null || childExpression.getExpression() == null) {
            context.addError(makeError(context.getRepository(), definitions, element, errorMessage));
        }
    }
}

class DefaultDMNValidatorVisitor extends TraversalVisitor<ValidationContext> {
    private final BuildLogger logger;
    private final DefaultDMNValidator validator;

    public DefaultDMNValidatorVisitor(ErrorHandler errorHandler, BuildLogger logger, DefaultDMNValidator validator) {
        super(errorHandler);
        this.logger = logger;
        this.validator = validator;
    }

    @Override
    public DMNBaseElement visit(TDefinitions element, ValidationContext context) {
        if (element != null)
        {
            DMNModelRepository repository = context.getRepository();

            logger.debug("Validate unique 'DRGElement.id'");
            this.validator.validateUnique(
                    element, new ArrayList<>(repository.findDRGElements(element)), "DRGElement", "id", false,
                    TDMNElement::getId, null, context
            );

            logger.debug("Validate unique 'DRGElement.name'");
            this.validator.validateUnique(
                    element, new ArrayList<>(repository.findDRGElements(element)), "DRGElement", "name", false,
                    e -> ((TNamedElement)e).getName(), null, context
            );

            logger.debug("Validate unique 'ItemDefinition.name'");
            this.validator.validateUnique(
                    element, new ArrayList<>(repository.findItemDefinitions(element)), "ItemDefinition", "name", false,
                    e -> ((TNamedElement)e).getName(), null, context
            );
        }

        // Visit children
        super.visit(element, context);

        return element;
    }

    @Override
    public DMNBaseElement visit(TInputData element, ValidationContext context) {
        if (element != null) {
            DMNModelRepository repository = context.getRepository();
            TDefinitions definitions = repository.getModel(element);

            this.validator.validateInputData(definitions, element, context);
        }

        return element;
    }

    @Override
    public DMNBaseElement visit(TDecision element, ValidationContext context) {
        if (element != null) {
            DMNModelRepository repository = context.getRepository();
            TDefinitions definitions = repository.getModel(element);

            this.validator.validateDecision(definitions, element, context);
        }

        return element;
    }

    @Override
    public DMNBaseElement visit(TBusinessKnowledgeModel element, ValidationContext context) {
        if (element != null) {
            DMNModelRepository repository = context.getRepository();
            TDefinitions definitions = repository.getModel(element);

            this.validator.validateBusinessKnowledgeModel(definitions, element, context);
        }

        return element;
    }

    @Override
    public DMNBaseElement visit(TDecisionService element, ValidationContext context) {
        if (element != null) {
            DMNModelRepository repository = context.getRepository();
            TDefinitions definitions = repository.getModel(element);
            this.validator.validateDecisionService(definitions, element, context);
        }

        return element;
    }
}
