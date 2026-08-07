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
import com.gs.dmn.error.ValidationError;
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
    public List<ValidationError> validate(DMNModelRepository repository) {
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
            addValidationError(context, definitions, element, errorMessage);
        }
        if (StringUtils.isBlank(element.getNamespace())) {
            String errorMessage = "Missing namespace of import";
            addValidationError(context, definitions, element, errorMessage);
        }
    }

    public void validateItemDefinition(TDefinitions definitions, TItemDefinition element, ValidationContext context) {
        validateNamedElement(definitions, element, context);
        QName typeRef = element.getTypeRef();

        if (!hasTypeRef(typeRef) && element.getItemComponent().isEmpty() && element.getFunctionItem() == null) {
            String errorMessage = "Incorrect definition of type";
            addValidationError(context, definitions, element, errorMessage);
        }
    }

    private static boolean hasTypeRef(QName typeRef) {
        return typeRef != null && !StringUtils.isBlank(typeRef.getLocalPart());
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
            errorMessage = "The %s of a %s must be unique.".formatted(property, elementType);
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
            String finalErrorMessage = "%s Found duplicates for '%s'.".formatted(errorMessage, message);
            addValidationError(context, definitions, null, finalErrorMessage);
        }
    }

    private void validateUniqueReferences(TDefinitions definitions, List<? extends TDMNElementReference> elements, String elementType, String property, boolean isOptionalProperty, Function<TDMNElementReference, String> accessor, String errorMessage, ValidationContext context) {
        if (errorMessage == null) {
            errorMessage = "The %s of a %s must be unique.".formatted(property, elementType);
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
            String finalErrorMessage = "%s Found duplicates for '%s'.".formatted(errorMessage, message);
            addValidationError(context, definitions, null, finalErrorMessage);
        }
    }

    private void validateNamedElement(TDefinitions definitions, TNamedElement element, ValidationContext context) {
        // ID is mandatory for DRG elements, it is used in references
        if (StringUtils.isBlank(element.getId()) && element instanceof TDRGElement) {
            String errorMessage = "Missing id for element %s".formatted(element.getClass().getSimpleName());
            addValidationError(context, definitions, element, errorMessage);
        }
        // Name is mandatory in XSD
        if (element.getName() == null) {
            String errorMessage = "Missing name for element %s".formatted(element.getClass().getSimpleName());
            addValidationError(context, definitions, element, errorMessage);
        }
    }

    private void validateVariable(TDefinitions definitions, TNamedElement element, TInformationItem variable, boolean validateTypeRef, ValidationContext context) {
        DMNModelRepository repository = context.getRepository();

        if (variable == null) {
            String errorMessage = "Missing variable";
            addValidationError(context, definitions, element, errorMessage);
            return;
        }

        // validate element/variable/name
        if (variable.getName() == null) {
            String errorMessage = "Missing variable name";
            addValidationError(context, definitions, element, errorMessage);
        } else {
            // element/@name == element/variable/@name
            String variableName = variable.getName();
            String elementName = element.getName();
            if (!elementName.equals(variableName)) {
                String errorMessage = "DRGElement name and variable name should be the same. Found '%s' and '%s'".formatted(elementName, variableName);
                addValidationError(context, definitions, element, errorMessage);
            }
        }
        // validate element/variable/@typeRef
        if (validateTypeRef) {
            QualifiedName typeRef = QualifiedName.toQualifiedName(definitions, variable.getTypeRef());
            if (repository.isNull(typeRef)) {
                String errorMessage = "Missing typRef of variable";
                addValidationError(context, definitions, element, errorMessage);
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
            addValidationError(context, definitions, element, errorMessage);
        } else if (expression instanceof TConditional conditionalExp) {
            checkChildExpression(definitions, element, conditionalExp.getIf(), "conditional", "if", context);
            checkChildExpression(definitions, element, conditionalExp.getThen(), "conditional", "then", context);
            checkChildExpression(definitions, element, conditionalExp.getElse(), "conditional", "else", context);
        } else if (expression instanceof TContext contextExp) {
            List<TContextEntry> contextEntryList = contextExp.getContextEntry();
            if (contextEntryList.isEmpty()) {
                String errorMessage = "Missing entries in context expression";
                addValidationError(context, definitions, element, errorMessage);
            } else {
                validateUnique(
                        definitions, contextEntryList, "TContextEntry", "name", false,
                        entryAccessor, null, context
                );
            }
        } else if (expression instanceof TDecisionTable decisionTable) {
            validateDecisionTable(definitions, element, decisionTable, context);
        } else if (expression instanceof TFilter filterExp) {
            checkChildExpression(definitions, element, filterExp.getIn(), "filter", "in", context);
            checkChildExpression(definitions, element, filterExp.getMatch(), "filter", "match", context);
        } else if (expression instanceof TFunctionDefinition functionDefinitionExp) {
            validateFormalParameters(definitions, element, functionDefinitionExp.getFormalParameter(), context);
            validateExpression(definitions, element, functionDefinitionExp.getExpression(), context);
        } else if (expression instanceof TInvocation invocation) {
            validateBinding(definitions, element, invocation.getBinding(), context);
            validateExpression(definitions, element, invocation.getExpression(), context);
        } else if (expression instanceof TFor forExp) {
            checkChildExpression(definitions, element, forExp.getIn(), "for", "in", context);
            checkChildExpression(definitions, element, forExp.getReturn(), "for", "return", context);
        } else if (expression instanceof TSome quantifiedExp) {
            String parentName = "some";
            checkChildExpression(definitions, element, quantifiedExp.getIn(), parentName, "in", context);
            checkChildExpression(definitions, element, quantifiedExp.getSatisfies(), parentName, "satisfies", context);
        } else if (expression instanceof TEvery quantifiedExp) {
            String parentName = "every";
            checkChildExpression(definitions, element, quantifiedExp.getIn(), parentName, "in", context);
            checkChildExpression(definitions, element, quantifiedExp.getSatisfies(), parentName, "satisfies", context);
        } else if (expression instanceof TList listExp) {
            for (TExpression childExp : listExp.getExpression()) {
                validateExpression(definitions, element, childExp, context);
            }
        } else if (expression instanceof TLiteralExpression literalExpression) {
            String expressionLanguage = literalExpression.getExpressionLanguage();
            if (!isSupported(expressionLanguage)) {
                String errorMessage = "Not supported expression language '%s'".formatted(expressionLanguage);
                addValidationError(context, definitions, element, errorMessage);
            }
            if (StringUtils.isBlank(literalExpression.getText())) {
                String errorMessage = "Missing text of literal expression";
                addValidationError(context, definitions, element, errorMessage);
            }
        } else if (expression instanceof TRelation relationExp) {
            if (relationExp.getColumn().isEmpty() && relationExp.getRow().isEmpty()) {
                String errorMessage = "Empty relation";
                addValidationError(context, definitions, element, errorMessage);
            }
        } else if (expression instanceof TUnaryTests unaryTests) {
            String expressionLanguage = unaryTests.getExpressionLanguage();
            if (!isSupported(expressionLanguage)) {
                String errorMessage = "Not supported expression language '%s'".formatted(expressionLanguage);
                addValidationError(context, definitions, element, errorMessage);
            }
            if (StringUtils.isBlank(unaryTests.getText())) {
                String errorMessage = "Missing text of unary tests";
                addValidationError(context, definitions, element, errorMessage);
            }
        }
    }

    private void validateBinding(TDefinitions definitions, TDRGElement element, List<TBinding> binding, ValidationContext context) {
        for (int i=0; i<binding.size(); i++) {
            TBinding b = binding.get(i);
            if (b.getParameter() == null) {
                String errorMessage = "Missing parameter in binding %d".formatted(i + 1);
                addValidationError(context, definitions, element, errorMessage);
            } else {
                if (StringUtils.isBlank(b.getParameter().getName())) {
                    String errorMessage = "Missing parameter name in binding %d".formatted(i + 1);
                    addValidationError(context, definitions, element, errorMessage);
                }
            }
            if (b.getExpression() == null) {
                String errorMessage = "Missing expression in binding %d".formatted(i + 1);
                addValidationError(context, definitions, element, errorMessage);
            }
        }
    }

    private void validateFormalParameters(TDefinitions definitions, TDRGElement element, List<TInformationItem> formalParameter, ValidationContext context) {
        // Check name and typeRef
        for (int i=0; i<formalParameter.size(); i++) {
            TInformationItem parameter = formalParameter.get(i);
            if (parameter.getName() == null) {
                String errorMessage = "Missing name in formal parameter %d".formatted(i + 1);
                addValidationError(context, definitions, element, errorMessage);
            }
            if (parameter.getTypeRef() == null) {
                String errorMessage = "Missing typeRef in formal parameter %d".formatted(i + 1);
                addValidationError(context, definitions, element, errorMessage);
            }
        }
        // Names are unique within the formal parameters of a function definition.
        validateUnique(
                definitions, formalParameter, "TInformationItem", "name", false,
                e -> ((TInformationItem) e).getName(), null, context
        );
    }

    private final Function<TDMNElement, String> entryAccessor = (TDMNElement e) -> {
        TContextEntry entry = (TContextEntry) e;
        if (entry.getVariable() != null) {
            return entry.getVariable().getName();
        } else {
            return null;
        }
    };

    private void validateDecisionTable(TDefinitions definitions, TDMNElement element, TDecisionTable decisionTable, ValidationContext context) {
        validateInputClauses(definitions, element, decisionTable, context);
        validateOutputClauses(definitions, element, decisionTable, context);
        validateRuleAnnotationClauses(definitions, element, decisionTable, context);
        validateHitPolicy(definitions, element, decisionTable, context);
        validateRules(definitions, element, decisionTable, context);
    }

    private void validateRules(TDefinitions definitions, TDMNElement element, TDecisionTable decisionTable, ValidationContext context) {
        List<TDecisionRule> ruleList = decisionTable.getRule();
        if (ruleList == null || ruleList.isEmpty()) {
            String errorMessage = "Missing rules in decision table";
            addValidationError(context, definitions, element, errorMessage);
        } else {
            for (int i=0; i<ruleList.size(); i++) {
                validateRule(definitions, element, decisionTable, i, context);
            }
        }
    }

    private void validateInputClauses(TDefinitions definitions, TDMNElement element, TDecisionTable decisionTable, ValidationContext context) {
//        A list of input clauses (zero or more). Each input clause is made of an input expression and optional allowed
//        values for the input entries that correspond to the clause. The input entries are contained in the rules, and the ith
//        input entry corresponds to the ith input clause.
        List<TInputClause> input = decisionTable.getInput();
        for (int i=0; i<input.size(); i++) {
                TInputClause inputClause = input.get(i);
                if (inputClause.getInputExpression() == null) {
                    String errorMessage = "Missing input expression in input clause %d".formatted(i+1);
                    addValidationError(context, definitions, element, errorMessage);
                }
            }
    }

    private void validateOutputClauses(TDefinitions definitions, TDMNElement element, TDecisionTable decisionTable, ValidationContext context) {
//        A list of output clauses (one or more). Each output clause is made of a name and optional allowed values for the
//        output entries that correspond to the clause. The output entries are contained in the rules, and the ith output entry
//        corresponds to the ith output clause. A single output clause has no name. Two or more output clauses describe a
//        decision table that returns a context for each hit with an entry for each output clause. Each of the multiple output
//        clauses SHALL be named.
        List<TOutputClause> output = decisionTable.getOutput();
        if (output == null || output.isEmpty()) {
            String errorMessage = "Missing output clauses";
            addValidationError(context, definitions, element, errorMessage);
        } else {
            // Check names
            if (output.size() > 1) {
                for (int i=0; i<output.size(); i++) {
                    TOutputClause outputClause = output.get(i);
                    if (outputClause.getName() == null) {
                        String errorMessage = "Missing name in output clause %d".formatted(i + 1);
                        addValidationError(context, definitions, element, errorMessage);
                    }
                }
            }
        }
    }

    private void validateRuleAnnotationClauses(TDefinitions definitions, TDMNElement element, TDecisionTable decisionTable, ValidationContext context) {
//        A list of annotation clauses (zero or more). Each annotation clause is made of a name. Each annotation SHALL
//        be named as part of a rule annotation clause. The annotation entries are contained in the rules, and the ith
//        annotation entry corresponds to the ith annotation clause.
        List<TRuleAnnotationClause> annotationClauses = decisionTable.getAnnotation();
        for (int i=0; i<annotationClauses.size(); i++) {
            TRuleAnnotationClause annotationClause = annotationClauses.get(i);
            if (annotationClause.getName() == null) {
                String errorMessage = "Missing name in annotation clause %d".formatted(i + 1);
                addValidationError(context, definitions, element, errorMessage);
            }
        }
    }

    private void validateHitPolicy(TDefinitions definitions, TDMNElement element, TDecisionTable decisionTable, ValidationContext context) {
        List<TOutputClause> output = decisionTable.getOutput();
        THitPolicy hitPolicy = decisionTable.getHitPolicy();
        TBuiltinAggregator aggregation = decisionTable.getAggregation();
        if (hitPolicy != THitPolicy.COLLECT && aggregation != null) {
            String errorMessage = "Aggregation '%s' not allowed for hit policy '%s'".formatted(aggregation, hitPolicy);
            addValidationError(context, definitions, element, errorMessage);
        }
        if (output != null && output.size() > 1
                && hitPolicy == THitPolicy.COLLECT
                && aggregation != null) {
            String errorMessage = "Collect operator is not defined over multiple outputs for decision table '%s'".formatted(decisionTable.getId());
            addValidationError(context, definitions, element, errorMessage);
        }
    }

    private void validateRule(TDefinitions definitions, TDMNElement element, TDecisionTable decisionTable, int index, ValidationContext context) {
        TDecisionRule rule = decisionTable.getRule().get(index);
        // Validate inputEntries
        List<TUnaryTests> inputEntries = rule.getInputEntry();
        if (inputEntries == null || inputEntries.isEmpty()) {
            String errorMessage = "No input entries for rule %s".formatted(index + 1);
            addValidationError(context, definitions, element, errorMessage);
        }
        // Validate outputEntries
        List<TLiteralExpression> outputEntries = rule.getOutputEntry();
        if (outputEntries == null || outputEntries.isEmpty()) {
            String errorMessage = "No outputEntry entries for rule %s".formatted(index + 1);
            addValidationError(context, definitions, element, errorMessage);
        }
        // Validate annotations
        List<TRuleAnnotation> annotationEntries = rule.getAnnotationEntry();
        for (TRuleAnnotation annotationEntry : annotationEntries) {
            if (annotationEntry.getText() == null) {
                String errorMessage = "Missing text in annotation entry for rule %s".formatted(index + 1);
                addValidationError(context, definitions, element, errorMessage);
            }
        }
        // Validate cardinality with clauses
        if (inputEntries.size() != decisionTable.getInput().size()) {
            String errorMessage = "The number of input entries in rule %s does not match the number of input clauses in decision table %s".formatted(index + 1, decisionTable.getId());
            addValidationError(context, definitions, element, errorMessage);
        }
        if (outputEntries.size() != decisionTable.getOutput().size()) {
            String errorMessage = "The number of output entries in rule %s does not match the number of output clauses in decision table %s".formatted(index + 1, decisionTable.getId());
            addValidationError(context, definitions, element, errorMessage);
        }
        if (annotationEntries.size() != decisionTable.getAnnotation().size()) {
            String errorMessage = "The number of annotation entries in rule %s does not match the number of annotation clauses in decision table %s".formatted(index + 1, decisionTable.getId());
            addValidationError(context, definitions, element, errorMessage);
        }
    }

    private boolean isSupported(String expressionLanguage) {
        return expressionLanguage == null || AbstractDMNToNativeTransformer.SUPPORTED_LANGUAGES.contains(expressionLanguage);
    }

    private void checkChildExpression(TDefinitions definitions, TDRGElement element, TChildExpression childExpression, String parentName, String childName, ValidationContext context) {
        String errorMessage = "Missing '%s' expression in '%s' boxed expression in element '%s'".formatted(childName, parentName, element.getName());
        if (childExpression == null || childExpression.getExpression() == null) {
            addValidationError(context, definitions, element, errorMessage);
        }
    }

    private void validateTypeRef(TDefinitions definitions, TInformationItem variable, TExpression expression, ValidationContext context) {
        if (variable != null && expression != null) {
            QName variableTypeRef = variable.getTypeRef();
            QName expressionTypeRef = expression.getTypeRef();
            if (variableTypeRef != null && expressionTypeRef != null) {
                if (!Objects.equals(variableTypeRef, expressionTypeRef)) {
                    String errorMessage = "The variable type '%s' must be the same as the type of the contained expression '%s'".formatted(variableTypeRef, expressionTypeRef);
                    addValidationError(context, definitions, null, errorMessage);
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
    public DMNBaseElement visit(TItemDefinition element, ValidationContext context) {
        if (element != null) {
            DMNModelRepository repository = context.getRepository();
            TDefinitions definitions = repository.getModel(element);

            this.validator.validateItemDefinition(definitions, element, context);
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
