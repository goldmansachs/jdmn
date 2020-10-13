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
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import org.omg.spec.dmn._20180521.model.*;

import java.util.ArrayList;
import java.util.List;

public class UniqueInformationRequirementValidator extends SimpleDMNValidator {
    public UniqueInformationRequirementValidator() {
        super(new Slf4jBuildLogger(LOGGER));
    }

    public UniqueInformationRequirementValidator(BuildLogger logger) {
        super(logger);
    }

    @Override
    public List<String> validate(DMNModelRepository dmnModelRepository) {
        List<String> errors = new ArrayList<>();
        if (isEmpty(dmnModelRepository)) {
            logger.warn("DMN repository is empty; validator will not run");
            return errors;
        }

        for (TDefinitions definitions: dmnModelRepository.getAllDefinitions()) {
            for (TDRGElement element : dmnModelRepository.findDRGElements(definitions)) {
                if (element instanceof TDecision) {
                    List<TInformationRequirement> irList = ((TDecision) element).getInformationRequirement();
                    validate(definitions, element, getReferences(irList), "InformationRequirement", errors);
                } else if (element instanceof TDecisionService) {
                    List<TDMNElementReference> inputData = ((TDecisionService) element).getInputData();
                    validate(definitions, element, inputData, "InputData", errors);
                    List<TDMNElementReference> inputDecision = ((TDecisionService) element).getInputDecision();
                    validate(definitions, element, inputDecision, "InputDecision", errors);
                }
            }
        }

        return errors;
    }

    private void validate(TDefinitions definitions, TDRGElement element, List<TDMNElementReference> references, String property, List<String> errors) {
        List<String> existingIds = new ArrayList<>();
        for (TDMNElementReference ir: references) {
            String id = ir.getHref();
            if (id != null) {
                if (existingIds.contains(id)) {
                    String errorMessage = String.format("Duplicated %s '%s'", property, id);
                    errors.add(makeError(definitions, element, errorMessage));
                } else {
                    existingIds.add(id);
                }
            }
        }
    }

    private List<TDMNElementReference> getReferences(List<TInformationRequirement> irList) {
        List<TDMNElementReference> references = new ArrayList<>();
        for (TInformationRequirement ir: irList) {
            TDMNElementReference requiredInput = ir.getRequiredInput();
            TDMNElementReference requiredDecision = ir.getRequiredDecision();
            if (requiredInput != null) {
                references.add(requiredInput);
            } else if (requiredDecision != null) {
                references.add(requiredDecision);
            }

        }
        return references;
    }
}
