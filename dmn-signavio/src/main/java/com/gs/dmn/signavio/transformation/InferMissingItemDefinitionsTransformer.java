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
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.feel.analysis.semantics.type.DataType;
import com.gs.dmn.feel.analysis.semantics.type.ListType;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.signavio.dialect.SignavioDMNDialectDefinition;
import com.gs.dmn.validation.TypeRefValidator;
import org.omg.spec.dmn._20191111.model.TDRGElement;
import org.omg.spec.dmn._20191111.model.TItemDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InferMissingItemDefinitionsTransformer extends AbstractMissingItemDefinitionsTransformer {
    static final String DMN_DIALECT_NAME = "dmnDialect";

    private TypeRefValidator typeRefValidator;
    private DMNDialectDefinition<?, ?, ?, ?, ?, ?> dmnDialect;

    public InferMissingItemDefinitionsTransformer() {
        this(new Slf4jBuildLogger(LOGGER));
    }

    public InferMissingItemDefinitionsTransformer(BuildLogger logger) {
        super(logger);
    }

    @Override
    public void configure(Map<String, Object> configuration) {
        parseConfigurationForDialect(configuration);
    }

    @Override
    public DMNModelRepository transform(DMNModelRepository repository) {
        if (isEmpty(repository)) {
            logger.warn("DMN repository is empty; transformer will not run");
            return repository;
        }

        inferAndAddMissingDefinitions(repository);
        return repository;
    }

    private void inferAndAddMissingDefinitions(DMNModelRepository repository) {
        List<TDRGElement> resolvedElements = new ArrayList<>();
        int idSequence = 0;

        int iteration = 0;
        List<TItemDefinition> itemDefinitionsToAdd = new ArrayList<>();
        do {
            logger.debug(String.format("Iteration: %d", iteration));

            itemDefinitionsToAdd.clear();
            List<Pair<TDRGElement, Type>> errorReport = typeRefValidator.makeErrorReport(repository);
            for (Pair<TDRGElement, Type> pair: errorReport) {
                TDRGElement element = pair.getLeft();
                Type type = pair.getRight();
                if (type == null) {
                } else if (isPrimitive(type) || isListOfPrimitive(type)) {
                    if (!resolvedElements.contains(element)) {
                        // Create ItemDefinition and add it
                        String name = repository.variable(element).getTypeRef();
                        boolean isCollection = type instanceof ListType;
                        String typeRef = getTypeRef(type);
                        TItemDefinition itemDefinition = makeItemDefinition(idSequence, name, isCollection, typeRef);
                        idSequence++;
                        itemDefinitionsToAdd.add(itemDefinition);

                        // Mark element as resolved
                        resolvedElements.add(element);
                    }
                } else {
                    throw new DMNRuntimeException(String.format("Cannot infer type for '%s'. '%s' is not supported yet", element.getName(), type));
                }
            }
            // Add new ItemDefinitions and try again
            this.addNewDefinitions(repository, itemDefinitionsToAdd);

            iteration++;
        } while (!itemDefinitionsToAdd.isEmpty());
    }

    private boolean isPrimitive(Type type) {
        return type instanceof DataType;
    }

    private boolean isListOfPrimitive(Type type) {
        return type instanceof ListType && isPrimitive(((ListType) type).getElementType());
    }

    private String getTypeRef(Type type) {
        if (isPrimitive(type)) {
            return ((DataType)type).getName();
        } else if (isListOfPrimitive(type)) {
            return getTypeRef(((ListType) type).getElementType());
        } else {
            throw new DMNRuntimeException(String.format("'%s' is not supported yet", type));
        }
    }

    private void parseConfigurationForDialect(Map<String, Object> configuration) {
        String dialectClassName = SignavioDMNDialectDefinition.class.getName();
        if (configuration != null && configuration.size() != 0) {
            Object dialectNode = configuration.get(DMN_DIALECT_NAME);
            if (dialectNode == null || configuration.values().size() != 1) {
                reportInvalidConfig(String.format("Configuration does not have expected structure (expecting only '%s' node)", DMN_DIALECT_NAME));
            } else if (dialectNode instanceof String) {
                dialectClassName = (String) dialectNode;
            } else {
                reportInvalidConfig(String.format("'%s' should be a string", DMN_DIALECT_NAME));
            }
        }
        try {
            Object object = Class.forName(dialectClassName).getDeclaredConstructor().newInstance();
            if (object instanceof DMNDialectDefinition) {
                DMNDialectDefinition dmnDialect = (DMNDialectDefinition) object;
                this.dmnDialect = dmnDialect;
                this.typeRefValidator = new TypeRefValidator(this.dmnDialect);
            } else {
                reportInvalidConfig(String.format("Incorrect DMN dialect name '%s'", dialectClassName));
            }
        } catch (Exception e) {
            reportInvalidConfig(String.format("Incorrect DMN dialect name '%s'", dialectClassName));
        }
    }

}
