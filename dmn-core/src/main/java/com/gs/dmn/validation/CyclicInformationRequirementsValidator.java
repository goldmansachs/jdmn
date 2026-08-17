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
import com.gs.dmn.DRGElementReference;
import com.gs.dmn.Graph;
import com.gs.dmn.ast.TDecision;
import com.gs.dmn.error.SemanticError;
import com.gs.dmn.error.SeverityLevel;
import com.gs.dmn.error.ValidationError;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;

import java.util.List;
import java.util.stream.Collectors;

public class CyclicInformationRequirementsValidator extends SimpleDMNValidator {
    public CyclicInformationRequirementsValidator() {
        this(new Slf4jBuildLogger(LOGGER));
    }

    public CyclicInformationRequirementsValidator(BuildLogger logger) {
        super(logger);
    }

    @Override
    public List<ValidationError> validate(DMNModelRepository repository) {
        ValidationContext context = new ValidationContext(repository);
        if (isEmpty(repository)) {
            this.logger.warn("DMN repository is empty; validator will not run");
            return context.getErrors();
        }

        // Build the information requirements graph for Decisions only.
        // The Inputs, BKMs and DSs do not have information requirements
        Graph<TDecision> requirementsGraph = new Graph<>();
        List<TDecision> allDecisions = repository.findAllDecisions();

        // Add all decisions as nodes
        requirementsGraph.addNodes(allDecisions);

        // Add edges for each required decision
        for (TDecision decision : allDecisions) {
            List<DRGElementReference<TDecision>> directSubDecisions = repository.directSubDecisions(decision);
            for (DRGElementReference<TDecision> subDecisionRef : directSubDecisions) {
                TDecision subDecision = subDecisionRef.getElement();
                if (subDecision != null) {
                    requirementsGraph.addEdge(decision, subDecision);
                }
            }
        }

        // Find cycles
        requirementsGraph.findCycles().forEach(cycle -> {
            String cycleText = cycle.stream().map(repository::qualifiedName).collect(Collectors.joining(" --> "));
            String errorMessage = String.format("Cyclic information requirement detected: %s", cycleText);
            SemanticError error = new SemanticError(SeverityLevel.ERROR, errorMessage);
            context.addError(new ValidationError(error, ruleName()));
        });

        return context.getErrors();
    }
}
