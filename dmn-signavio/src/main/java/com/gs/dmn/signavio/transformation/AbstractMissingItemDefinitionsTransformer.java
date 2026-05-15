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
import com.gs.dmn.QualifiedName;
import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.ast.TItemDefinition;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.signavio.SignavioDMNModelRepository;
import com.gs.dmn.signavio.testlab.TestLab;
import com.gs.dmn.transformation.SimpleDMNTransformer;

import javax.xml.namespace.QName;
import java.util.List;

public abstract class AbstractMissingItemDefinitionsTransformer extends SimpleDMNTransformer<TestLab> {
    // Pre-condition: one single model
    private static TDefinitions getDefinitions(DMNModelRepository repository) {
        List<TDefinitions> models = repository.getAllDefinitions();
        TDefinitions model;
        if (models.size() == 1) {
            model = models.get(0);
        } else {
            throw new DMNRuntimeException(String.format("Not supported for %s models", models.size()));
        }
        return model;
    }

    protected AbstractMissingItemDefinitionsTransformer() {
        this(new Slf4jBuildLogger(LOGGER));
    }

    protected AbstractMissingItemDefinitionsTransformer(BuildLogger logger) {
        super(logger);
    }

    protected DMNModelRepository addNewDefinitions(DMNModelRepository repository, List<TItemDefinition> itemDefinitionList) {
        TDefinitions model = getDefinitions(repository);
        for (TItemDefinition definition : itemDefinitionList) {
            TItemDefinition existingDefinition = repository.lookupItemDefinition(model, QualifiedName.toQualifiedName(model, definition.getName()));
            if (existingDefinition != null) {
                if (definitionsAreEquivalent(definition, existingDefinition)) {
                    logger.warn(String.format("Matching definition already exists for \"%s\"; no definition will be generated", definition.getName()));
                    continue;
                } else {
                    throw new DMNRuntimeException(String.format("Cannot generate item definition for \"%s\"; conflicting item definition already exists", definition.getName()));
                }
            }

            ((SignavioDMNModelRepository) repository).addItemDefinition(model, definition);
            logger.info(String.format("Generated new item definition for \"%s\"", definition.getName()));
        }
        return repository;
    }

    @Override
    public Pair<DMNModelRepository, List<TestLab>> transform(DMNModelRepository repository, List<TestLab> testCasesList) {
        // Transform models
        repository = transform(repository);

        // Transform test cases
        if (isEmpty(testCasesList)) {
            logger.warn("List of test cases is empty");
            return new Pair<>(repository, testCasesList);
        }

        return new Pair<>(repository, testCasesList);
    }

    protected TItemDefinition makeItemDefinition(int definitionsCount, String name, boolean isCollection, String typeRef) {
        TItemDefinition itemDefinition = new TItemDefinition();
        itemDefinition.setId(String.format("generated-definition-%d", definitionsCount));
        itemDefinition.setName(name);
        itemDefinition.setLabel(name);
        itemDefinition.setTypeRef(new QName(typeRef));
        itemDefinition.setIsCollection(isCollection);
        return itemDefinition;
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

    protected void reportInvalidConfig(String message) {
        throw new DMNRuntimeException(String.format("Invalid transformer configuration: %s", message));
    }
}
