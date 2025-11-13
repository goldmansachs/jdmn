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
import com.gs.dmn.ast.DMNBaseElement;
import com.gs.dmn.ast.TDMNElement;
import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.ast.TNamedElement;
import com.gs.dmn.ast.visitor.TraversalVisitor;
import com.gs.dmn.error.ErrorFactory;
import com.gs.dmn.error.ErrorHandler;
import com.gs.dmn.error.SemanticError;
import com.gs.dmn.error.ValidationError;
import com.gs.dmn.feel.ModelLocation;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class UniqueNameValidator extends SimpleDMNValidator {
    public UniqueNameValidator() {
        this(new Slf4jBuildLogger(LOGGER));
    }

    public UniqueNameValidator(BuildLogger logger) {
        super(logger);
    }

    @Override
    public List<ValidationError> validate(DMNModelRepository repository) {
        if (isEmpty(repository)) {
            this.logger.warn("DMN repository is empty; validator will not run");
            return new ArrayList<>();
        }

        ValidationContext context = new ValidationContext(repository);
        UniqueNameValidatorVisitor visitor = new UniqueNameValidatorVisitor(this.logger, this.errorHandler, this.ruleName());
        for (TDefinitions definitions: repository.getAllDefinitions()) {
            definitions.accept(visitor, context);
        }

        return context.getErrors();
    }
}

class UniqueNameValidatorVisitor extends TraversalVisitor<ValidationContext> {
    private final String ruleName;

    public UniqueNameValidatorVisitor(BuildLogger logger, ErrorHandler errorHandler, String ruleName) {
        super(logger, errorHandler);
        this.ruleName = ruleName;
    }

    @Override
    public DMNBaseElement visit(TDefinitions element, ValidationContext context) {
        DMNModelRepository repository = context.getRepository();
        logger.debug("Validate unique 'DRGElement.name'");
        validateUnique(element,
                new ArrayList<>(repository.findDRGElements(element)), "DRGElement", "name",
                false, TNamedElement::getName, null,
                context);

        logger.debug("Validate unique 'ItemDefinition.name'");
        validateUnique(element,
                new ArrayList<>(repository.findTopLevelItemDefinitions(element)), "ItemDefinition", "name",
                false, TNamedElement::getName, null,
                context);

        return element;
    }

    private void validateUnique(TDefinitions definitions, List<TNamedElement> elements, String elementType, String property, boolean isOptionalProperty, Function<TNamedElement, String> accessor, String errorMessage, ValidationContext context) {
        if (errorMessage == null) {
            errorMessage = String.format("The '%s' of a '%s' must be unique.", property, elementType);
        }
        // Create a map
        Map<String, List<TDMNElement>> map = new LinkedHashMap<>();
        for (TNamedElement element : elements) {
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
        for (Map.Entry<String, List<TDMNElement>> entry : map.entrySet()) {
            String key = entry.getKey();
            if(entry.getValue().size() > 1){
                String finalErrorMessage = String.format("%s Found %d duplicates for '%s'.", errorMessage, entry.getValue().size(), key);
                SemanticError error = ErrorFactory.makeDMNError(new ModelLocation(definitions, null), finalErrorMessage);
                context.addError(new ValidationError(error, this.ruleName));
            }
        }
    }
}
