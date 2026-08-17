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
import com.gs.dmn.Graph;
import com.gs.dmn.QualifiedName;
import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.ast.TItemDefinition;
import com.gs.dmn.error.SemanticError;
import com.gs.dmn.error.SeverityLevel;
import com.gs.dmn.error.ValidationError;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;

import javax.xml.namespace.QName;
import java.util.List;
import java.util.stream.Collectors;

public class CyclicItemDefinitionsValidator extends SimpleDMNValidator {
    public CyclicItemDefinitionsValidator() {
        this(new Slf4jBuildLogger(LOGGER));
    }

    public CyclicItemDefinitionsValidator(BuildLogger logger) {
        super(logger);
    }

    @Override
    public List<ValidationError> validate(DMNModelRepository repository) {
        ValidationContext context = new ValidationContext(repository);
        if (isEmpty(repository)) {
            this.logger.warn("DMN repository is empty; validator will not run");
            return context.getErrors();
        }

        // Build the item definition type reference graph
        Graph<TItemDefinition> typeRefGraph = buildItemDefinitionGraph(repository);

        // Find cycles in the graph
        typeRefGraph.findCycles().forEach(cycle -> {
            String cycleText = cycle.stream()
                    .map(repository::qualifiedName)
                    .collect(Collectors.joining(" -> "));
            String errorMessage = String.format("Cyclic item definition detected: %s", cycleText);
            SemanticError error = new SemanticError(SeverityLevel.ERROR, errorMessage);
            context.addError(new ValidationError(error, ruleName()));
        });

        return context.getErrors();
    }

    private Graph<TItemDefinition> buildItemDefinitionGraph(DMNModelRepository repository) {
        Graph<TItemDefinition> graph = new Graph<>();

        // Add all item definitions as nodes
        for (TDefinitions definitions : repository.getAllDefinitions()) {
            List<TItemDefinition> itemDefinitions = repository.findTopLevelItemDefinitions(definitions);
            for (TItemDefinition itemDef : itemDefinitions) {
                graph.addNode(itemDef);
            }
        }
        // Add edges based on typeRef relationships
        for (TDefinitions definitions : repository.getAllDefinitions()) {
            List<TItemDefinition> itemDefinitions = repository.findTopLevelItemDefinitions(definitions);
            for (TItemDefinition itemDef : itemDefinitions) {
                QName typeRef = itemDef.getTypeRef();
                QualifiedName qualifiedName = QualifiedName.toQualifiedName(definitions, typeRef);
                TItemDefinition referencedItemDef = repository.lookupItemDefinition(definitions, qualifiedName);
                if (referencedItemDef != null) {
                    graph.addEdge(itemDef, referencedItemDef);
                }
            }
        }

        return graph;
    }

}
