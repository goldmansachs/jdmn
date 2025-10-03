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
import com.gs.dmn.ast.*;
import com.gs.dmn.ast.visitor.TraversalVisitor;
import com.gs.dmn.error.ErrorFactory;
import com.gs.dmn.error.ErrorHandler;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UniqueRequirementValidator extends SimpleDMNValidator {
    public UniqueRequirementValidator() {
        this(new Slf4jBuildLogger(LOGGER));
    }

    public UniqueRequirementValidator(BuildLogger logger) {
        super(logger);
    }

    @Override
    public List<String> validate(DMNModelRepository repository) {
        if (isEmpty(repository)) {
            logger.warn("DMN repository is empty; validator will not run");
            return new ArrayList<>();
        }

        ValidationContext context = new ValidationContext(repository);
        UniqueRequirementValidatorVisitor visitor = new UniqueRequirementValidatorVisitor(this.logger, this.errorHandler);
        for (TDefinitions definitions: repository.getAllDefinitions()) {
            definitions.accept(visitor, context);
        }

        return context.getErrors();
    }
}

class UniqueRequirementValidatorVisitor extends TraversalVisitor<ValidationContext> {
    public UniqueRequirementValidatorVisitor(BuildLogger logger, ErrorHandler errorHandler) {
        super(logger, errorHandler);
    }

    @Override
    public DMNBaseElement visit(TDecision  element, ValidationContext context) {
        if (element != null) {
            logger.debug("Validate unique requirement references for " + element.getName());
            List<TInformationRequirement> irList = element.getInformationRequirement();
            List<TDMNElementReference> inputDataReferences = new ArrayList<>();
            List<TDMNElementReference> decisionReferences = new ArrayList<>();
            collectReferences(irList, inputDataReferences, decisionReferences);
            validate(element, inputDataReferences, "informationRequirement.requiredInput", context);
            validate(element, decisionReferences, "informationRequirement.requiredDecision", context);

            List<TKnowledgeRequirement> krList = element.getKnowledgeRequirement();
            List<TDMNElementReference> krReferences = krList.stream().map(TKnowledgeRequirement::getRequiredKnowledge).collect(Collectors.toList());
            validate(element, krReferences, "knowledgeRequirements", context);
        }

        return element;
    }

    @Override
    public DMNBaseElement visit(TBusinessKnowledgeModel  element, ValidationContext context) {
        if (element != null) {
            logger.debug("Validate unique requirement references for " + element.getName());

            List<TKnowledgeRequirement> krList = element.getKnowledgeRequirement();
            List<TDMNElementReference> krReferences = krList.stream().map(TKnowledgeRequirement::getRequiredKnowledge).collect(Collectors.toList());
            validate(element, krReferences, "knowledgeRequirements", context);
        }

        return element;
    }

    @Override
    public DMNBaseElement visit(TDecisionService  element, ValidationContext context) {
        if (element != null) {
            logger.debug("Validate unique requirement references for " + element.getName());

            List<TDMNElementReference> inputDataReferences = element.getInputData();
            validate(element, inputDataReferences, "inputData", context);
            List<TDMNElementReference> decisionReferences = element.getInputDecision();
            validate(element, decisionReferences, "inputDecision", context);
        }

        return element;
    }

    private void validate(TDRGElement element, List<TDMNElementReference> references, String propertyPath, ValidationContext context) {
        DMNModelRepository repository = context.getRepository();
        TDefinitions definitions = repository.getModel(element);
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
                        errorMessage = String.format("Duplicated %s %s", propertyPath, ErrorFactory.makeLocation(definitions, referredElement));
                    }
                    context.addError(ErrorFactory.makeDMNErrorMessage(definitions, element, errorMessage));
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