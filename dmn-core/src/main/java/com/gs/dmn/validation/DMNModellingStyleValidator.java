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

import com.gs.dmn.*;
import com.gs.dmn.ast.*;
import com.gs.dmn.ast.visitor.TraversalVisitor;
import com.gs.dmn.error.ErrorHandler;
import com.gs.dmn.error.SemanticError;
import com.gs.dmn.error.SemanticErrorException;
import com.gs.dmn.error.ValidationError;
import com.gs.dmn.feel.analysis.semantics.type.FEELType;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.serialization.DMNVersion;
import org.apache.commons.lang3.StringUtils;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

//
// Check the following modelling style rules:
// Imports:
//  - All imported models should exist in the repository
//  - All imported models should be used in the current model
//  - All used models should be imported in the current model
//  - A model should not be imported more than once in the current model
//
// Item definitions:
//  - Nested item definitions are not allowed. Complex types should be modelled separately.
//
// Decision services:
//  - A decision service should expose exactly one output decision.
//
// Context expressions:
//  - Nested expressions in a context are not allowed. All context entries should be literal expressions
//
// Names:
//  - Names should contain only alphanumeric characters, underscores, dashes and spaces, , and should start with an alphanumeric character.
//  - Names should not end with whitespace, including tabs, newlines, and carriage returns
//  - Item definition names should not be FEEL type names.
//  - Import names should contain only alphanumeric characters, underscores and spaces, and should start with a letter.
//  - Labels are deprecated and should not be used.
//
public class DMNModellingStyleValidator extends SimpleDMNValidator {
    public DMNModellingStyleValidator() {
        this(new Slf4jBuildLogger(LOGGER));
    }

    public DMNModellingStyleValidator(BuildLogger logger) {
        super(logger);
    }

    @Override
    public List<ValidationError> validate(DMNModelRepository repository) {
        if (isEmpty(repository)) {
            this.logger.warn("DMN repository is empty; validator will not run");
            return new ArrayList<>();
        }

        ValidationContext context = new ValidationContext(repository);
        for (TDefinitions definitions : repository.getAllDefinitions()) {
            // Visit model
            context.setDefinitions(definitions);
            DMNModellingStyleValidatorVisitor visitor = new DMNModellingStyleValidatorVisitor(this.logger, this.errorHandler, ruleName());
            definitions.accept(visitor, context);

            // Validate imported and used models
            validateImportedModelsExist(definitions, visitor.importedModels, context);
            validateImportedModelsAreUsed(definitions, visitor.importedModels, visitor.usedModels, context);
            validateUsedModelsAreImported(definitions, visitor.importedModels, visitor.usedModels, context);
            validateModelIsImportedOnlyOnce(definitions, context);
        }

        return context.getErrors();
    }

    // Check that all imported models exist in the repository
    private void validateImportedModelsExist(TDefinitions definitions, Set<String> importedModels, ValidationContext context) {
        DMNModelRepository repository = context.getRepository();
        for (String importedModel : importedModels) {
            try {
                repository.findModelByNamespace(importedModel);
            } catch (SemanticErrorException e) {
                String errorMessage = String.format("Cannot find model for namespace '%s'.", importedModel);
                SemanticError error = ErrorFactory.makeDMNWarning(new ModelCoordinates(definitions, null), errorMessage);
                context.addError(new ValidationError(error, ruleName()));
            }
        }
    }

    // Check that all imported models are used in the current model
    private void validateImportedModelsAreUsed(TDefinitions definitions, Set<String> importedModels, Set<String> usedModels, ValidationContext context) {
        for (String importedModel : importedModels) {
            if (!usedModels.contains(importedModel)) {
                String errorMessage = String.format("Model '%s' is imported but not used in model '%s'.", importedModel, definitions.getNamespace());
                SemanticError error = ErrorFactory.makeDMNWarning(new ModelCoordinates(definitions, null), errorMessage);
                context.addError(new ValidationError(error, ruleName()));
            }
        }
    }

    // Check that all used models are imported in the current model
    private void validateUsedModelsAreImported(TDefinitions definitions, Set<String> importedModels, Set<String> usedModels, ValidationContext context) {
        for (String usedModel : usedModels) {
            if (!importedModels.contains(usedModel)) {
                String errorMessage = String.format("Model '%s' is used but not imported in model '%s'.", usedModel, definitions.getNamespace());
                SemanticError error = ErrorFactory.makeDMNWarning(new ModelCoordinates(definitions, null), errorMessage);
                context.addError(new ValidationError(error, ruleName()));
            }
        }
    }

    // Check that a model is not imported more than once in the current model
    private void validateModelIsImportedOnlyOnce(TDefinitions definitions, ValidationContext context) {
        Set<String> importedNamespaces = new LinkedHashSet<>();
        for (TImport import_: definitions.getImport()) {
            String namespace = import_.getNamespace();
            if (importedNamespaces.contains(namespace)) {
                String errorMessage = String.format("Model '%s' is imported more than once in model '%s'.", namespace, definitions.getNamespace());
                SemanticError error = ErrorFactory.makeDMNWarning(new ModelCoordinates(definitions, import_), errorMessage);
                context.addError(new ValidationError(error, ruleName()));
            } else {
                importedNamespaces.add(namespace);
            }
        }
    }
}

class DMNModellingStyleValidatorVisitor extends TraversalVisitor<ValidationContext> {
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z0-9][A-Za-z0-9-_ ]*$");
    private static final Pattern IMPORT_NAME_PATTERN = Pattern.compile("^[A-Za-z][A-Za-z0-9_]*$");

    private final String ruleName;
    final Set<String> importedModels = new LinkedHashSet<>();
    final Set<String> usedModels = new LinkedHashSet<>();

    public DMNModellingStyleValidatorVisitor(BuildLogger logger, ErrorHandler errorHandler, String ruleName) {
        super(logger, errorHandler);
        this.ruleName = ruleName;
    }

    @Override
    public DMNBaseElement visit(TDefinitions element, ValidationContext context) {
        if (element != null) {
            validateName(element, context);
        }

        return super.visit(element, context);
    }

    @Override
    public DMNBaseElement visit(TImport element, ValidationContext context) {
        // Collect imported models
        if (element != null) {
            collectImportedModel(element, context);
            validateImportName(element, context);
        }

        return super.visit(element, context);
    }

    @Override
    public DMNBaseElement visit(TItemDefinition element, ValidationContext context) {
        if (element != null) {
            validateNestedItemDefinitions(element, context);
            validateItemDefinitionName(element, context);
        }

        return super.visit(element, context);
    }

    @Override
    public DMNBaseElement visit(TInputData element, ValidationContext context) {
        if (element != null) {
            validateName(element, context);
        }

        return super.visit(element, context);
    }

    @Override
    public DMNBaseElement visit(TDecision element, ValidationContext context) {
        if (element != null) {
            validateName(element, context);
        }

        return super.visit(element, context);
    }

    @Override
    public DMNBaseElement visit(TBusinessKnowledgeModel element, ValidationContext context) {
        if (element != null) {
            validateName(element, context);
        }

        return super.visit(element, context);
    }

    @Override
    public DMNBaseElement visit(TDecisionService element, ValidationContext context) {
        if (element != null) {
            validateName(element, context);
            validateOutputDecisionCount(element, context);
        }

        return super.visit(element, context);
    }

    @Override
    public DMNBaseElement visit(TInformationItem element, ValidationContext context) {
        if (element != null) {
            validateName(element, context);
        }

        return super.visit(element, context);
    }

    @Override
    public DMNBaseElement visit(TContext element, ValidationContext context) {
        if (element != null) {
            validateNestedExpressions(element, context);
        }

        return super.visit(element, context);
    }

    @Override
    protected QName visitTypeRef(QName typeRef, ValidationContext context) {
        if (typeRef != null) {
            collectUsedModel(typeRef, context);
        }

        return typeRef;
    }

    @Override
    public DMNBaseElement visit(TDMNElementReference element, ValidationContext context) {
        if (element != null) {
            collectUsedModel(element, context);
        }

        return super.visit(element, context);
    }

    // Nested item definitions are not allowed. Complex types should be modelled separately.
    private void validateNestedItemDefinitions(TItemDefinition element, ValidationContext context) {
        List<TItemDefinition> itemComponents = element.getItemComponent();
        if (!itemComponents.isEmpty()) {
            // Check if child components have components
            for (TItemDefinition child : itemComponents) {
                if (!child.getItemComponent().isEmpty()) {
                    String errorMessage = String.format("Complex type '%s' contains nested complex type '%s' which is not allowed. Complex types should be modelled separately.", element.getName(), child.getName());
                    TDefinitions definitions = context.getDefinitions();
                    SemanticError error = ErrorFactory.makeDMNWarning(new ModelCoordinates(definitions, element), errorMessage);
                    context.addError(new ValidationError(error, this.ruleName));
                }
            }
        }
    }

    // Nested expressions in a context are not allowed. All context entries should be literal expressions.
    private void validateNestedExpressions(TContext element, ValidationContext context) {
        List<TContextEntry> contextEntryList = element.getContextEntry();
        for (TContextEntry entry : contextEntryList) {
            if (entry.getExpression() != null && !(entry.getExpression() instanceof TLiteralExpression)) {
                String entryName = entry.getVariable() != null ? entry.getVariable().getName() : "unnamed";
                String errorMessage = String.format("All context entries should be literal expressions. Context entry '%s' is a not a literal expression", entryName);
                TDefinitions definitions = context.getDefinitions();
                SemanticError error = ErrorFactory.makeDMNWarning(new ModelCoordinates(definitions, element), errorMessage);
                context.addError(new ValidationError(error, this.ruleName));
            }
        }
    }

    // Collect imported model
    private void collectImportedModel(TImport element, ValidationContext context) {
        DMNModelRepository repository = context.getRepository();
        if (repository.isDMNImport(element)) {
            this.importedModels.add(element.getNamespace());
        }
    }

    // Collect used model from typeRef if the prefix is empty
    private void collectUsedModel(QName typeRef, ValidationContext context) {
        DMNModelRepository repository = context.getRepository();
        TDefinitions definitions = context.getDefinitions();

        TypeReference typeReference = TypeReference.toTypeReference(definitions, typeRef);
        String prefix = typeReference.getPrefix();
        String namespace = repository.findNamespace(definitions, prefix);
        // If the prefix is not empty and the namespace is empty, collect the prefix as used model to report the error later.
        if (!StringUtils.isBlank(prefix) && StringUtils.isBlank(namespace)) {
            collectUsedNamespace(prefix);
        } else {
            collectUsedNamespace(namespace);
        }
    }

    // Collect used model from TDMNElementReference
    private void collectUsedModel(TDMNElementReference element, ValidationContext context) {
        DMNModelRepository repository = context.getRepository();
        String namespace = repository.extractNamespaceURI(element.getHref());
        collectUsedNamespace(namespace);
    }

    // Collect used model
    private void collectUsedNamespace(String namespace) {
        if (!StringUtils.isBlank(namespace) && !DMNVersion.LATEST.getFeelNamespace().equals(namespace)) {
            this.usedModels.add(namespace);
        }
    }

    // Names should contain only alphanumeric characters, underscores, dashes and spaces, and should start with an alphanumeric character.
    // Labels are deprecated and should not be used.
    private void validateName(TNamedElement element, ValidationContext context) {
        // Check name
        String name = element.getName();
        if (!StringUtils.isBlank(name)) {
            // Check if name contains invalid characters
            if (!NAME_PATTERN.matcher(name).matches()) {
                String errorMessage = String.format("Name '%s' contains invalid characters. Names should contain only alphanumeric characters, underscores, dashes and spaces, and should start with an alphanumeric character.", name);
                TDefinitions definitions = context.getDefinitions();
                SemanticError error = ErrorFactory.makeDMNWarning(new ModelCoordinates(definitions, element), errorMessage);
                context.addError(new ValidationError(error, this.ruleName));
            }
            // Check if name ends with whitespace, including tabs, newlines, and carriage returns
            if (Character.isWhitespace(name.charAt(name.length() - 1))) {
                String errorMessage = String.format("Name '%s' ends with a whitespace.", name);
                TDefinitions definitions = context.getDefinitions();
                SemanticError error = ErrorFactory.makeDMNWarning(new ModelCoordinates(definitions, element), errorMessage);
                context.addError(new ValidationError(error, this.ruleName));
            }
        }
        // Check label
        String label = element.getLabel();
        if (!StringUtils.isBlank(label)) {
            String errorMessage = String.format("Label '%s' is deprecated and should not be used.", label);
            TDefinitions definitions = context.getDefinitions();
            SemanticError error = ErrorFactory.makeDMNWarning(new ModelCoordinates(definitions, element), errorMessage);
            context.addError(new ValidationError(error, this.ruleName));
        }
    }

    // Item definition names should not be FEEL type names.
    private void validateItemDefinitionName(TItemDefinition element, ValidationContext context) {
        validateName(element, context);
        // Check if name is not a FEEL type name
        if (FEELType.FEEL_TYPE_NAMES.contains(element.getName())) {
            String errorMessage = String.format("Item definition name '%s' is a FEEL type name which is not allowed.", element.getName());
            TDefinitions definitions = context.getDefinitions();
            SemanticError error = ErrorFactory.makeDMNWarning(new ModelCoordinates(definitions, element), errorMessage);
            context.addError(new ValidationError(error, this.ruleName));
        }
    }

    // Import names should contain only alphanumeric characters, underscores and spaces, and should start with a letter.
    private void validateImportName(TNamedElement element, ValidationContext context) {
        String name = element.getName();
        if (!StringUtils.isBlank(name)) {
            // Check if name contains invalid characters
            if (!IMPORT_NAME_PATTERN.matcher(name).matches()) {
                String errorMessage = String.format("Import name '%s' contains invalid characters. Import names should contain only alphanumeric characters and underscores, and should start with a letter.", name);
                TDefinitions definitions = context.getDefinitions();
                SemanticError error = ErrorFactory.makeDMNWarning(new ModelCoordinates(definitions, element), errorMessage);
                context.addError(new ValidationError(error, this.ruleName));
            }
        }
    }

    // A decision service should expose exactly one output decision.
    private void validateOutputDecisionCount(TDecisionService element, ValidationContext context) {
        List<TDMNElementReference> outputDecision = element.getOutputDecision();
        if (outputDecision != null && outputDecision.size() > 1) {
            String errorMessage = String.format("DecisionService '%s' should have only one output decision.", element.getName());
            TDefinitions definitions = context.getDefinitions();
            SemanticError error = ErrorFactory.makeDMNWarning(new ModelCoordinates(definitions, element), errorMessage);
            context.addError(new ValidationError(error, this.ruleName));
        }
    }
}
