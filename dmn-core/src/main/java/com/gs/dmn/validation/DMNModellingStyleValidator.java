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
import com.gs.dmn.ErrorFactory;
import com.gs.dmn.ModelCoordinates;
import com.gs.dmn.ast.*;
import com.gs.dmn.ast.visitor.TraversalVisitor;
import com.gs.dmn.error.ErrorHandler;
import com.gs.dmn.error.SemanticError;
import com.gs.dmn.error.ValidationError;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;

import java.util.ArrayList;
import java.util.List;

//
// Check the following modelling style rules:
// Item definitions:
//  - Nested item definitions are not allowed. Complex types should be modelled separately.
// Context expressions:
//  - Nested expressions in a context are not allowed. All context entries should be literal expressions
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
        DMNModellingStyleValidatorVisitor visitor = new DMNModellingStyleValidatorVisitor(this.logger, this.errorHandler, this.ruleName());
        for (TDefinitions definitions : repository.getAllDefinitions()) {
            context.setDefinitions(definitions);
            definitions.accept(visitor, context);
        }

        return context.getErrors();
    }
}

class DMNModellingStyleValidatorVisitor extends TraversalVisitor<ValidationContext> {
    private final String ruleName;

    public DMNModellingStyleValidatorVisitor(BuildLogger logger, ErrorHandler errorHandler, String ruleName) {
        super(logger, errorHandler);
        this.ruleName = ruleName;
    }

    @Override
    public DMNBaseElement visit(TItemDefinition element, ValidationContext context) {
        if (element != null) {
            validateNestedItemDefinitions(element, context);
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

}
