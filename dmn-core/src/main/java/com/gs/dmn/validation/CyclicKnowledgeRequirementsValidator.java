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
import com.gs.dmn.ast.*;
import com.gs.dmn.error.SemanticError;
import com.gs.dmn.error.SeverityLevel;
import com.gs.dmn.error.ValidationError;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;

import java.util.List;
import java.util.stream.Collectors;

public class CyclicKnowledgeRequirementsValidator extends SimpleDMNValidator {
    public CyclicKnowledgeRequirementsValidator() {
        this(new Slf4jBuildLogger(LOGGER));
    }

    public CyclicKnowledgeRequirementsValidator(BuildLogger logger) {
        super(logger);
    }

    @Override
    public List<ValidationError> validate(DMNModelRepository repository) {
        ValidationContext context = new ValidationContext(repository);
        if (isEmpty(repository)) {
            this.logger.warn("DMN repository is empty; validator will not run");
            return context.getErrors();
        }

        // Build the knowledge requirements graph for BKMs only
        // The Inputs, Decisions and DSs do not have KnowledgeRequirements
        Graph<TBusinessKnowledgeModel> requirementsGraph = new Graph<>();
        List<TDefinitions> allDefinitions = repository.getAllDefinitions();
        // Add BKMs as nodes
        for (TDefinitions definitions : allDefinitions) {
            List<TBusinessKnowledgeModel> bkms = repository.findBKMs(definitions);
            requirementsGraph.addNodes(bkms);
        }

        // Add edges for each BKM
        for (TDefinitions definitions : allDefinitions) {
            List<TBusinessKnowledgeModel> bkms = repository.findBKMs(definitions);
            for (TBusinessKnowledgeModel bkm : bkms) {
                List<TKnowledgeRequirement> krs = bkm.getKnowledgeRequirement();
                for (TKnowledgeRequirement kr : krs) {
                    TDMNElementReference reference = kr.getRequiredKnowledge();
                    TDRGElement drgElenent = repository.findDRGElementByRef(bkm, reference.getHref());
                    if (drgElenent instanceof TBusinessKnowledgeModel child) {
                        requirementsGraph.addEdge(bkm, child);
                    }
                }
            }
        }

        // Find cycles
        requirementsGraph.findCycles().forEach(cycle -> {
            String cycleText = cycle.stream().map(repository::qualifiedName).collect(Collectors.joining(" --> "));
            String errorMessage = String.format("Cyclic knowledge requirement detected: %s", cycleText);
            SemanticError error = new SemanticError(SeverityLevel.ERROR, errorMessage);
            context.addError(new ValidationError(error, ruleName()));
        });

        return context.getErrors();
    }
}
