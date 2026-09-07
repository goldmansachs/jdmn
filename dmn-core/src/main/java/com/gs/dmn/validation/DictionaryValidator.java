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
import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.error.ValidationError;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;

import java.util.ArrayList;
import java.util.List;

public class DictionaryValidator extends SimpleDMNValidator {
    private final DMNValidator childValidator = new CompositeDMNValidator(List.of(
            new DefaultDMNValidator(),
            new CyclicDependenciesValidator(),
            new DMNModellingStyleValidator(),
            new TypeRefValidator()
    ));

    public DictionaryValidator() {
        this(new Slf4jBuildLogger(LOGGER));
    }

    public DictionaryValidator(BuildLogger logger) {
        super(logger);
    }

    @Override
    public List<com.gs.dmn.error.ValidationError> validate(DMNModelRepository repository) {
        if (isEmpty(repository)) {
            logger.warn("DMN repository is empty; validator will not run");
            return new ArrayList<>();
        }

        // ItemDefinitions only, no DRG elements
        ValidationContext context = new ValidationContext(repository);
        for (TDefinitions definitions : repository.getAllDefinitions()) {
            if (definitions != null) {
                if (!definitions.getDrgElement().isEmpty()) {
                    String errorMessage = "Dictionaries must not contain DRG elements.";
                    addValidationError(context, definitions, null, errorMessage);
                }
            }
        }

        // Check other rules
        List<ValidationError> errors = this.childValidator.validate(repository);
        for (ValidationError error : errors) {
            context.addError(error);
        }

        return context.getErrors();
    }
}
