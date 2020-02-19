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

import com.google.common.base.Function;
import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import org.omg.spec.dmn._20180521.model.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UniqueNameValidator extends SimpleDMNValidator {
    public UniqueNameValidator() {
        super(new Slf4jBuildLogger(LOGGER));
    }

    public UniqueNameValidator(BuildLogger logger) {
        super(logger);
    }

    @Override
    public List<String> validate(DMNModelRepository dmnModelRepository) {
        List<String> errors = new ArrayList<>();

        if (dmnModelRepository == null) {
            throw new IllegalArgumentException("Missing definitions");
        }

        for (TDefinitions definitions: dmnModelRepository.getAllDefinitions()) {
            logger.debug("Validate unique 'DRGElement.name'");
            validateUnique(
                    "DRGElement", "name", false,
                    new ArrayList<>(dmnModelRepository.drgElements(definitions)), TNamedElement::getName, null, errors
            );

            logger.debug("Validate unique 'ItemDefinition.name'");
            validateUnique(
                    "ItemDefinition", "name", false,
                    new ArrayList<>(dmnModelRepository.itemDefinitions(definitions)), TNamedElement::getName, null, errors
            );
        }

        return errors;
    }

    private void validateUnique(String elementType, String property, boolean isOptionalProperty, List<TNamedElement> elements, Function<TNamedElement, String> accessor, String errorMessage, List<String> errors) {
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
        for (Map.Entry<String, List<TDMNElement>> entry : map.entrySet()) {
            String key = entry.getKey();
            if(entry.getValue().size() > 1){
                errors.add(String.format("%s Found %d duplicates for '%s'.", errorMessage, entry.getValue().size(), key));
            }
        }
    }
}
