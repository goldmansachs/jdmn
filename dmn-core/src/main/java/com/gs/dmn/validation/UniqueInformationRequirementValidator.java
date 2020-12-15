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
import org.omg.spec.dmn._20191111.model.*;

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
    public List<String> validate(DMNModelRepository repository) {
        List<String> errors = new ArrayList<>();
        if (isEmpty(repository)) {
            logger.warn("DMN repository is empty; validator will not run");
            return errors;
        }

        for (TDefinitions definitions: repository.getAllDefinitions()) {
            for (TDRGElement element : repository.findDRGElements(definitions)) {
                if (element instanceof TDecision) {
                    List<TInformationRequirement> irList = ((TDecision) element).getInformationRequirement();
                    List<TDMNElementReference> inputDataReferences = new ArrayList<>();
                    List<TDMNElementReference> decisionReferences = new ArrayList<>();
                    collectReferences(irList, inputDataReferences, decisionReferences);
                    validate(repository, definitions, element, inputDataReferences, "informationRequirement.requiredInput", errors);
                    validate(repository, definitions, element, decisionReferences, "informationRequirement.requiredDecision", errors);
                } else if (element instanceof TDecisionService) {
                    List<TDMNElementReference> inputDataReferences = ((TDecisionService) element).getInputData();
                    validate(repository, definitions, element, inputDataReferences, "inputData", errors);
                    List<TDMNElementReference> decisionReferences = ((TDecisionService) element).getInputDecision();
                    validate(repository, definitions, element, decisionReferences, "inputDecision", errors);
                }
            }
        }

        return errors;
    }

    private void validate(DMNModelRepository repository, TDefinitions definitions, TDRGElement element, List<TDMNElementReference> references, String propertyPath, List<String> errors) {
        List<String> existingIds = new ArrayList<>();
        for (TDMNElementReference ir: references) {
            String id = ir.getHref();
            if (id != null) {
                if (existingIds.contains(id)) {
                    TDRGElement referredElement = findElementByRef(repository, element, ir);
                    String errorMessage;
                    if (referredElement == null) {
                        errorMessage = String.format("Duplicated %s '%s'", propertyPath, id);
                    } else {
                        errorMessage = String.format("Duplicated %s %s", propertyPath, repository.makeLocation(definitions, referredElement));
                    }
                    errors.add(makeError(repository, definitions, element, errorMessage));
                } else {
                    existingIds.add(id);
                }
            }
        }
    }

    private TDRGElement findElementByRef(DMNModelRepository repository, TDRGElement element, TDMNElementReference ir) {
        try {
            return repository.findDRGElementByRef(element, ir.getHref());
        } catch (Exception e) {
            return null;
        }
    }

    private void collectReferences(List<TInformationRequirement> irList, List<TDMNElementReference> inputDataReferences, List<TDMNElementReference> decisionReferences) {
        for (TInformationRequirement ir: irList) {
            TDMNElementReference requiredInput = ir.getRequiredInput();
            TDMNElementReference requiredDecision = ir.getRequiredDecision();
            if (requiredInput != null) {
                inputDataReferences.add(requiredInput);
            } else if (requiredDecision != null) {
                decisionReferences.add(requiredDecision);
            }
        }
    }
}
