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
package com.gs.dmn.signavio.transformation;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import org.omg.spec.dmn._20191111.model.TItemDefinition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GenerateMissingItemDefinitionsTransformer extends AbstractMissingItemDefinitionsTransformer {
    private static final String ELEMENT_DEFINITIONS = "definitions";
    private static final String ELEMENT_DEFINITION = "definition";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_TYPE = "type";
    private static final String FIELD_COLLECTION = "isCollection";

    private List<TItemDefinition> definitions;

    public GenerateMissingItemDefinitionsTransformer() {
        this(new Slf4jBuildLogger(LOGGER));
    }

    public GenerateMissingItemDefinitionsTransformer(BuildLogger logger) {
        super(logger);
    }

    @Override
    public void configure(Map<String, Object> configuration) {
        this.definitions = parseConfigurationForDefinitions(configuration);
    }

    @Override
    public DMNModelRepository transform(DMNModelRepository repository) {
        if (isEmpty(repository)) {
            logger.warn("Repository is empty; transformer will not run");
            return repository;
        }

        if (this.definitions == null || this.definitions.isEmpty()) {
            logger.warn("No definitions provided; transformer will not run");
            return repository;
        }

        addNewDefinitions(repository, this.definitions);

        this.transformRepository = false;
        return repository;
    }

    private List<TItemDefinition> parseConfigurationForDefinitions(Map<String, Object> configuration) {
        List<TItemDefinition> result = new ArrayList<>();

        List<Object> definitionList = extractDefinitionConfigurations(configuration);
        for (Object definitionObj : definitionList) {
            if (!(definitionObj instanceof Map)) {
                reportInvalidConfig("Definition entry does not have expected structure");
            } else {
                Map<String, Object> def = (Map<String, Object>) definitionObj;
                Object name = def.get(FIELD_NAME);
                Object type = def.get(FIELD_TYPE);
                Object isCollection = def.get(FIELD_COLLECTION);

                if (!(name instanceof String && type instanceof String)) {
                    reportInvalidConfig("Definition entry does not contain mandatory fields");
                } else {
                    // Support both DMN 1.1 and 1.2 syntax; transform all to 1.2
                    String typeRef = ((String) type).replace(':', '.');

                    // Create ItemDefinition and add it to result
                    TItemDefinition itemDefinition = makeItemDefinition(result.size(), (String) name, Boolean.parseBoolean((String) isCollection), typeRef);
                    result.add(itemDefinition);
                }
            }
        }

        return result;
    }

    private List<Object> extractDefinitionConfigurations(Map<String, Object> configuration) {
        List<Object> definitionList = null;
        if (configuration == null) {
            reportInvalidConfig("No configuration provided");
        } else {
            Object definitionsNode = configuration.get(ELEMENT_DEFINITIONS);
            if (!(definitionsNode instanceof Map)) {
                reportInvalidConfig(String.format("Configuration does not have expected structure (expecting \"%s\" node)", ELEMENT_DEFINITIONS));
            } else {
                Object definitionConfig = ((Map<String, Object>) definitionsNode).get(ELEMENT_DEFINITION);
                if (definitionConfig instanceof List) {
                    definitionList = (List<Object>) definitionConfig;
                } else if (definitionConfig instanceof Map) {
                    definitionList = Collections.singletonList(definitionConfig);
                } else {
                    reportInvalidConfig(String.format("Configuration does not have expected structure (expecting list of \"%s\" nodes)", ELEMENT_DEFINITION));
                }
            }
        }
        return definitionList;
    }
}
