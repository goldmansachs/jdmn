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
import com.gs.dmn.error.ErrorFactory;
import com.gs.dmn.error.ErrorHandler;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.transformation.AbstractDMNToNativeTransformer;
import org.apache.commons.lang3.StringUtils;

import javax.xml.namespace.QName;
import java.util.*;
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

        DefaultDMNValidatorVisitor visitor = new DefaultDMNValidatorVisitor(this.logger, this.errorHandler, this);
        for (TDefinitions definitions : repository.getAllDefinitions()) {
            definitions.accept(visitor, context);
        }

        return context.getErrors();
    }

    public void validateImport(TDefinitions definitions, TImport element, ValidationContext context) {
        validateNamedElement(definitions, element, context);
        if (StringUtils.isBlank(element.getImportType())) {
            String errorMessage = "Missing importType of import";
            context.addError(ErrorFactory.makeDMNErrorMessage(definitions, element, errorMessage));
        }
        if (StringUtils.isBlank(element.getNamespace())) {
            String errorMessage = "Missing namespace of import";
            context.addError(ErrorFactory.makeDMNErrorMessage(definitions, element, errorMessage));
        }
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
        validateExpression(definitions, element, element.getExpression(), context);
        validateTypeRef(definitions, element.getVariable(), element.getExpression(), context);
    }

    protected void validateBusinessKnowledgeModel(TDefinitions definitions, TBusinessKnowledgeModel element, ValidationContext context) {
        validateNamedElement(definitions, element, context);
        validateVariable(definitions, element, element.getVariable(), false, context);
        List<TDMNElementReference> krs = element.getKnowledgeRequirement().stream().map(TKnowledgeRequirement::getRequiredKnowledge).collect(Collectors.toList());
        validateReferences(definitions, element, krs, context);
        validateExpression(definitions, element, element.getEncapsulatedLogic(), context);
        validateTypeRef(definitions, element.getVariable(), element.getEncapsulatedLogic(), context);
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
            errorMessage = String.format("The %s of a %s must be unique.", property, elementType);
        }
        // Create a map
        Map<String, List<TDMNElement>> map = new LinkedHashMap<>();
        for (TDMNElement element : elements) {
            String key = accessor.apply(element);
            if (key != null && !isOptionalProperty) {
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
            if (entry.getValue().size() > 1) {
                duplicates.add(key);
            }
        }
        // Report error
        if (!duplicates.isEmpty()) {
            String message = String.join(", ", duplicates);
            context.addError(ErrorFactory.makeDMNErrorMessage(definitions, null, String.format("%s Found duplicates for '%s'.", errorMessage, message)));
        }
    }

    private void validateUniqueReferences(TDefinitions definitions, List<? extends TDMNElementReference> elements, String elementType, String property, boolean isOptionalProperty, Function<TDMNElementReference, String> accessor, String errorMessage, ValidationContext context) {
        if (errorMessage == null) {
            errorMessage = String.format("The %s of a %s must be unique.", property, elementType);
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
            if (entry.getValue().size() > 1) {
                duplicates.add(key);
            }
        }
        // Report error
        if (!duplicates.isEmpty()) {
            String message = String.join(", ", duplicates);
            context.addError(ErrorFactory.makeDMNErrorMessage(definitions, null, String.format("%s Found duplicates for '%s'.", errorMessage, message)));
        }
    }

    private void validateNamedElement(TDefinitions definitions, TNamedElement element, ValidationContext context) {
        // ID is mandatory for DRG elements, it is used in references
        if (StringUtils.isBlank(element.getId()) && element instanceof TDRGElement) {
            String errorMessage = String.format("Missing id for element %s", element.getClass().getSimpleName());
            context.addError(ErrorFactory.makeDMNErrorMessage(definitions, element, errorMessage));
        }
        // Name is mandatory in XSD
        if (element.getName() == null) {
            String errorMessage = String.format("Missing name for element %s", element.getClass().getSimpleName());
            context.addError(ErrorFactory.makeDMNErrorMessage(definitions, element, errorMessage));
        }
    }

    private void validateVariable(TDefinitions definitions, TNamedElement element, TInformationItem variable, boolean validateTypeRef, ValidationContext context) {
        DMNModelRepository repository = context.getRepository();

        if (variable == null) {
            String errorMessage = "Missing variable";
            context.addError(ErrorFactory.makeDMNErrorMessage(definitions, element, errorMessage));
            return;
        }

        // validate element/variable/name
        if (variable.getName() == null) {
            String errorMessage = "Missing variable name";
            context.addError(ErrorFactory.makeDMNErrorMessage(definitions, element, errorMessage));
        } else {
            // element/@name == element/variable/@name
            String variableName = variable.getName();
            String elementName = element.getName();
            if (!elementName.equals(variableName)) {
                String errorMessage = String.format("DRGElement name and variable name should be the same. Found '%s' and '%s'", elementName, variableName);
                context.addError(ErrorFactory.makeDMNErrorMessage(definitions, element, errorMessage));
            }
        }
        // validate element/variable/@typeRef
        if (validateTypeRef) {
            QualifiedName typeRef = QualifiedName.toQualifiedName(definitions, variable.getTypeRef());
            if (repository.isNull(typeRef)) {
                String errorMessage = "Missing typRef of variable";
                context.addError(ErrorFactory.makeDMNErrorMessage(definitions, element, errorMessage));
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

    private void validateReferences(TDefinitions definitions, TNamedElement element, List<TDMNElementReference> references, ValidationContext context) {
        // Validate requirements
        Function<TDMNElementReference, String> accessor =
                TDMNElementReference::getHref;
        validateUniqueReferences(definitions, references, "TDMNElementReference", "href", false,
                accessor, element.getName(), context);
    }

    private void validateExpression(TDefinitions definitions, TDRGElement element, TExpression expression, ValidationContext context) {
        if (expression == null) {
            String errorMessage = "Missing expression";
            context.addError(ErrorFactory.makeDMNErrorMessage(definitions, element, errorMessage));
        } else {
            if (expression instanceof TList) {
                TList listExp = (TList) expression;
                for (TExpression childExp : listExp.getExpression()) {
                    validateExpression(definitions, element, childExp, context);
                }
            } else if (expression instanceof TFunctionDefinition) {
                TFunctionDefinition functionDefinitionExp = (TFunctionDefinition) expression;
                validateExpression(definitions, element, functionDefinitionExp.getExpression(), context);
            } else if (expression instanceof TRelation) {
                TRelation relationExp = (TRelation) expression;
                if (((TRelation) expression).getColumn() == null && relationExp.getRow() == null) {
                    String errorMessage = "Empty relation";
                    context.addError(ErrorFactory.makeDMNErrorMessage(definitions, element, errorMessage));
                }
            } else if (expression instanceof TUnaryTests) {
                TUnaryTests unaryTests = (TUnaryTests) expression;
                String expressionLanguage = unaryTests.getExpressionLanguage();
                if (!isSupported(expressionLanguage)) {
                    String errorMessage = String.format("Not supported expression language '%s'", expressionLanguage);
                    context.addError(ErrorFactory.makeDMNErrorMessage(definitions, element, errorMessage));
                }
                if (StringUtils.isBlank(unaryTests.getText())) {
                    String errorMessage = "Missing text of unary tests";
                    context.addError(ErrorFactory.makeDMNErrorMessage(definitions, element, errorMessage));
                }
            } else if (expression instanceof TConditional) {
                TConditional conditionalExp = (TConditional) expression;
                checkChildExpression(definitions, element, conditionalExp.getIf(), "conditional", "if", context);
                checkChildExpression(definitions, element, conditionalExp.getThen(), "conditional", "then", context);
                checkChildExpression(definitions, element, conditionalExp.getElse(), "conditional", "else", context);
            } else if (expression instanceof TDecisionTable) {
                TDecisionTable decisionTable = (TDecisionTable) expression;
                validateDecisionTable(definitions, element, decisionTable, context);
            } else if (expression instanceof TSome) {
                TQuantified quantifiedExp = (TQuantified) expression;
                String parentName = "some";
                checkChildExpression(definitions, element, quantifiedExp.getIn(), parentName, "in", context);
                checkChildExpression(definitions, element, quantifiedExp.getSatisfies(), parentName, "satisfies", context);
            } else if (expression instanceof TEvery) {
                TQuantified quantifiedExp = (TQuantified) expression;
                String parentName = "every";
                checkChildExpression(definitions, element, quantifiedExp.getIn(), parentName, "in", context);
                checkChildExpression(definitions, element, quantifiedExp.getSatisfies(), parentName, "satisfies", context);
            } else if (expression instanceof TFor) {
                TFor forExp = (TFor) expression;
                checkChildExpression(definitions, element, forExp.getIn(), "for", "in", context);
                checkChildExpression(definitions, element, forExp.getReturn(), "for", "return", context);
            } else if (expression instanceof TLiteralExpression) {
                TLiteralExpression literalExpression = (TLiteralExpression) expression;
                String expressionLanguage = ((TLiteralExpression) expression).getExpressionLanguage();
                if (!isSupported(expressionLanguage)) {
                    String errorMessage = String.format("Not supported expression language '%s'", expressionLanguage);
                    context.addError(ErrorFactory.makeDMNErrorMessage(definitions, element, errorMessage));
                }
                if (StringUtils.isBlank(literalExpression.getText())) {
                    String errorMessage = "Missing text of literal expression";
                    context.addError(ErrorFactory.makeDMNErrorMessage(definitions, element, errorMessage));
                }
            } else if (expression instanceof TFilter) {
                TFilter filterExp = (TFilter) expression;
                checkChildExpression(definitions, element, filterExp.getIn(), "filter", "in", context);
                checkChildExpression(definitions, element, filterExp.getMatch(), "filter", "match", context);
            } else if (expression instanceof TContext) {
                TContext contextExp = (TContext) expression;
                List<TContextEntry> contextEntryList = contextExp.getContextEntry();
                if (contextEntryList.isEmpty()) {
                    String errorMessage = "Missing entries in context expression";
                    context.addError(ErrorFactory.makeDMNErrorMessage(definitions, element, errorMessage));
                }
            } else if (expression instanceof TInvocation) {
                TInvocation invocation = (TInvocation) expression;
                validateExpression(definitions, element, invocation.getExpression(), context);
            }
        }
    }

    private void validateDecisionTable(TDefinitions definitions, TDMNElement element, TDecisionTable decisionTable, ValidationContext context) {
        List<TInputClause> input = decisionTable.getInput();
        if (input == null || input.isEmpty()) {
            String errorMessage = "Missing input clauses";
            context.addError(ErrorFactory.makeDMNErrorMessage(definitions, element, errorMessage));
        }
        List<TOutputClause> output = decisionTable.getOutput();
        if (output == null || output.isEmpty()) {
            String errorMessage = "Missing output clauses";
            context.addError(ErrorFactory.makeDMNErrorMessage(definitions, element, errorMessage));
        }
        validateHitPolicy(definitions, element, decisionTable, context);
        List<TDecisionRule> ruleList = decisionTable.getRule();
        if (ruleList == null || ruleList.isEmpty()) {
            String errorMessage = "Missing rules in decision table";
            context.addError(ErrorFactory.makeDMNErrorMessage(definitions, element, errorMessage));
        } else {
            for (TDecisionRule rule : ruleList) {
                validateRule(definitions, element, rule, context);
            }
        }
    }

    private void validateHitPolicy(TDefinitions definitions, TDMNElement element, TDecisionTable decisionTable, ValidationContext context) {
        List<TOutputClause> output = decisionTable.getOutput();
        THitPolicy hitPolicy = decisionTable.getHitPolicy();
        TBuiltinAggregator aggregation = decisionTable.getAggregation();
        if (hitPolicy != THitPolicy.COLLECT && aggregation != null) {
            String errorMessage = String.format("Aggregation '%s' not allowed for hit policy '%s'", aggregation, hitPolicy);
            context.addError(ErrorFactory.makeDMNErrorMessage(definitions, element, errorMessage));
        }
        if (output != null && output.size() > 1
                && hitPolicy == THitPolicy.COLLECT
                && aggregation != null) {
            String errorMessage = String.format("Collect operator is not defined over multiple outputs for decision table '%s'", decisionTable.getId());
            context.addError(ErrorFactory.makeDMNErrorMessage(definitions, element, errorMessage));
        }
    }

    private boolean isSupported(String expressionLanguage) {
        return expressionLanguage == null || AbstractDMNToNativeTransformer.SUPPORTED_LANGUAGES.contains(expressionLanguage);
    }

    private void validateRule(TDefinitions definitions, TDMNElement element, TDecisionRule rule, ValidationContext context) {
        List<TUnaryTests> inputEntry = rule.getInputEntry();
        if (inputEntry == null || inputEntry.isEmpty()) {
            String errorMessage = "No input entries for rule " + rule.getId();
            context.addError(ErrorFactory.makeDMNErrorMessage(definitions, element, errorMessage));
        }
        List<TLiteralExpression> outputEntry = rule.getOutputEntry();
        if (outputEntry == null || outputEntry.isEmpty()) {
            String errorMessage = "No outputEntry entries for rule " + rule.getId();
            context.addError(ErrorFactory.makeDMNErrorMessage(definitions, element, errorMessage));
        }
    }

    private void checkChildExpression(TDefinitions definitions, TDRGElement element, TChildExpression childExpression, String parentName, String childName, ValidationContext context) {
        String errorMessage = String.format("Missing '%s' expression in '%s' boxed expression in element '%s'", childName, parentName, element.getName());
        if (childExpression == null || childExpression.getExpression() == null) {
            context.addError(ErrorFactory.makeDMNErrorMessage(definitions, element, errorMessage));
        }
    }

    private void validateTypeRef(TDefinitions definitions, TInformationItem variable, TExpression expression, ValidationContext context) {
        if (variable != null && expression != null) {
            QName variableTypeRef = variable.getTypeRef();
            QName expressionTypeRef = expression.getTypeRef();
            if (variableTypeRef != null && expressionTypeRef != null) {
                if (!Objects.equals(variableTypeRef, expressionTypeRef)) {
                    String errorMessage = String.format("The variable type '%s' must be the same as the type of the contained expression '%s'", variableTypeRef, expressionTypeRef);
                    context.addError(ErrorFactory.makeDMNErrorMessage(definitions, null, errorMessage));
                }
            }
        }
    }
}

class DefaultDMNValidatorVisitor extends TraversalVisitor<ValidationContext> {
    private final DefaultDMNValidator validator;

    public DefaultDMNValidatorVisitor(BuildLogger logger, ErrorHandler errorHandler, DefaultDMNValidator validator) {
        super(logger, errorHandler);
        this.validator = validator;
    }

    @Override
    public DMNBaseElement visit(TDefinitions element, ValidationContext context) {
        if (element != null) {
            DMNModelRepository repository = context.getRepository();

            logger.debug("Validate unique 'DRGElement.id'");
            List<TDRGElement> drgElements = repository.findDRGElements(element);
            this.validator.validateUnique(
                    element, new ArrayList<>(drgElements), "DRGElement", "id", false,
                    TDMNElement::getId, null, context
            );

            logger.debug("Validate unique 'DRGElement.name' and 'Import.name'");
            List<TNamedElement> namedElements = new ArrayList<>(drgElements);
            namedElements.addAll(element.getImport().stream().filter(i -> StringUtils.isNotBlank(i.getName())).toList());
            this.validator.validateUnique(
                    element, new ArrayList<>(namedElements), "DRGElement", "name", false,
                    e -> ((TNamedElement) e).getName(), null, context
            );

            logger.debug("Validate unique 'ItemDefinition.name'");
            this.validator.validateUnique(
                    element, new ArrayList<>(repository.findTopLevelItemDefinitions(element)), "ItemDefinition", "name", false,
                    e -> ((TNamedElement) e).getName(), null, context
            );

            // Visit children
            super.visit(element, context);
        }

        return element;
    }

    @Override
    public DMNBaseElement visit(TImport element, ValidationContext context) {
        if (element != null) {
            DMNModelRepository repository = context.getRepository();
            TDefinitions definitions = repository.getModel(element);

            this.validator.validateImport(definitions, element, context);
        }

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
