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
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.signavio.SignavioDMNModelRepository;
import com.gs.dmn.signavio.testlab.TestLab;
import com.gs.dmn.transformation.SimpleDMNTransformer;
import org.omg.spec.dmn._20180521.model.TDefinitions;
import org.omg.spec.dmn._20180521.model.TItemDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GenerateMissingItemDefinitionsTransformer extends SimpleDMNTransformer<TestLab> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MergeInputDataTransformer.class);

    private static final String ELEMENT_DEFINITIONS = "definitions";
    private static final String ELEMENT_DEFINITION = "definition";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_TYPE = "type";
    private static final String FIELD_COLLECTION = "isCollection";

    private final BuildLogger logger;

    private List<TItemDefinition> definitions;

    private boolean transformRepository = true;

    public GenerateMissingItemDefinitionsTransformer() {
        this(new Slf4jBuildLogger(LOGGER));
    }

    public GenerateMissingItemDefinitionsTransformer(BuildLogger logger) {
        this.logger = logger;
    }

    @Override
    public DMNModelRepository transform(DMNModelRepository repository) {
        if (definitions == null || definitions.isEmpty()) {
            logger.warn("No definitions provided; transformer will not run");
            return repository;
        }

        TDefinitions model = repository.getRootDefinitions();
        for (TItemDefinition definition : definitions) {
            TItemDefinition existingDefinition = repository.lookupItemDefinition(definition.getName());
            if (existingDefinition != null) {
                if (definitionsAreEquivalent(definition, existingDefinition)) {
                    logger.warn(String.format("Matching definition already exists for \"%s\"; no definition will be generated", definition.getName()));
                    continue;
                }
                else {
                    throw new DMNRuntimeException(String.format("Cannot generate item definition for \"%s\"; conflicting item definition already exists", definition.getName()));
                }
            }

            ((SignavioDMNModelRepository) repository).addItemDefinition(model, definition);
            logger.info(String.format("Generated new item definition for \"%s\"", definition.getName()));
        }

        this.transformRepository = false;
        return repository;
    }

    @Override
    public Pair<DMNModelRepository, List<TestLab>> transform(DMNModelRepository repository, List<TestLab> testCasesList) {
        if (this.transformRepository) {
            transform(repository);
        }

        return new Pair<>(repository, testCasesList);
    }

    @Override
    public void configure(Map<String, Object> configuration) {
        this.definitions = parseConfigurationForDefinitions(configuration);
    }

    private List<TItemDefinition> parseConfigurationForDefinitions(Map<String, Object> configuration) {
        List<TItemDefinition> definitions = new ArrayList<>();

        if (configuration == null) {
            reportInvalidConfig("No configuration provided");
        }

        Object definitionsNode = configuration.get(ELEMENT_DEFINITIONS);
        if (!(definitionsNode instanceof Map)) {
            reportInvalidConfig(String.format("Configuration does not have expected structure (expecting \"%s\" node)", ELEMENT_DEFINITIONS));
        }

        List<Object> definitionList = null;
        Object definitionConfig = ((Map<String, Object>) definitionsNode).get(ELEMENT_DEFINITION);
        if (definitionConfig instanceof List) {
            definitionList = (List<Object>) definitionConfig;
        }
        else if (definitionConfig instanceof Map) {
            definitionList = Collections.singletonList(definitionConfig);
        }
        else {
            reportInvalidConfig(String.format("Configuration does not have expected structure (expecting list of \"%s\" nodes)", ELEMENT_DEFINITION));
        }

        for (Object definitionObj : definitionList)
        {
            if (!(definitionObj instanceof Map)) {
                reportInvalidConfig("Definition entry does not have expected structure");
            }

            Map<String, Object> def = (Map<String, Object>) definitionObj;
            Object name = def.get(FIELD_NAME);
            Object type = def.get(FIELD_TYPE);
            Object isCollection = def.get(FIELD_COLLECTION);

            if (!(name instanceof String && type instanceof String)) {
                reportInvalidConfig("Definition entry does not contain mandatory fields");
            }

            // Support both DMN 1.1 and 1.2 syntax; transform all to 1.2
            String typeRef = ((String)type).replace(':', '.');

            TItemDefinition itemDefinition = new TItemDefinition();
            itemDefinition.setId(String.format("generated-definition-%d", definitions.size()));
            itemDefinition.setName((String)name);
            itemDefinition.setLabel((String)name);
            itemDefinition.setTypeRef(typeRef);
            itemDefinition.setIsCollection(Boolean.parseBoolean((String)isCollection));

            definitions.add(itemDefinition);
        }

        return definitions;
    }

    // Definitions are considered to be equivalent if all of { name, typeref, collection state } are
    // equal.  Other fields are not inputs to the transformer and therefore are not considered
    private boolean definitionsAreEquivalent(TItemDefinition definition1, TItemDefinition definition2) {
        if (definition1 == null) {
            return (definition2 == null);
        }

        if (definition2 == null) {
            return false;
        }

        return (definition1.getName().equals(definition2.getName()) &&
                definition1.getTypeRef().equals(definition2.getTypeRef()) &&
                definition1.isIsCollection() == definition2.isIsCollection());
    }

    private void reportInvalidConfig(String message) {
        throw new DMNRuntimeException(String.format("Invalid transformer configuration: %s", message));
    }

}
