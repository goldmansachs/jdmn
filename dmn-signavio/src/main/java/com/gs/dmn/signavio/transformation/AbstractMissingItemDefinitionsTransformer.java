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
import org.omg.spec.dmn._20191111.model.TDefinitions;
import org.omg.spec.dmn._20191111.model.TItemDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class AbstractMissingItemDefinitionsTransformer extends SimpleDMNTransformer<TestLab> {
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractMissingItemDefinitionsTransformer.class);

    protected final BuildLogger logger;

    protected boolean transformRepository = true;

    public AbstractMissingItemDefinitionsTransformer() {
        this(new Slf4jBuildLogger(LOGGER));
    }

    protected AbstractMissingItemDefinitionsTransformer(BuildLogger logger) {
        this.logger = logger;
    }

    protected void addNewDefinitions(DMNModelRepository repository, List<TItemDefinition> definitions) {
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
    }

    @Override
    public Pair<DMNModelRepository, List<TestLab>> transform(DMNModelRepository repository, List<TestLab> testCasesList) {
        if (isEmpty(repository, testCasesList)) {
            logger.warn("DMN repository or test list is empty; transformer will not run");
            return new Pair<>(repository, testCasesList);
        }

        // Transform model
        if (this.transformRepository) {
            transform(repository);
        }

        return new Pair<>(repository, testCasesList);
    }

    protected TItemDefinition makeItemDefinition(int definitionsCount, String name, boolean isCollection, String typeRef) {
        TItemDefinition itemDefinition = new TItemDefinition();
        itemDefinition.setId(String.format("generated-definition-%d", definitionsCount));
        itemDefinition.setName(name);
        itemDefinition.setLabel(name);
        itemDefinition.setTypeRef(typeRef);
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
