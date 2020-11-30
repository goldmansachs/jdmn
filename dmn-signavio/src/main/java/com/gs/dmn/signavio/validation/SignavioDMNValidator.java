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
package com.gs.dmn.signavio.validation;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.validation.DefaultDMNValidator;
import org.omg.spec.dmn._20191111.model.*;
import org.w3c.dom.Element;

import java.util.List;

public class SignavioDMNValidator extends DefaultDMNValidator {
    public SignavioDMNValidator() {
    }

    @Override
    protected void validateBusinessKnowledgeModel(DMNModelRepository repository, TDefinitions definitions, TBusinessKnowledgeModel knowledgeModel, List<String> errors) {
        super.validateBusinessKnowledgeModel(repository, definitions, knowledgeModel, errors);

        // Validate encapsulated logic
        TFunctionDefinition encapsulatedLogic = knowledgeModel.getEncapsulatedLogic();
        TDMNElement.ExtensionElements extensionElements = knowledgeModel.getExtensionElements();
        if (encapsulatedLogic == null && extensionElements == null) {
            String errorMessage = "Missing encapsulatedLogic";
            errors.add(makeError(repository, definitions, knowledgeModel, errorMessage));
        }
    }

    @Override
    protected void validateDecision(DMNModelRepository repository, TDefinitions definitions, TDecision decision, List<String> errors) {
        super.validateDecision(repository, definitions, decision, errors);
        // Validate extensions
        validateExtensionElements(repository, definitions, decision, errors);
    }

    private void validateExtensionElements(DMNModelRepository repository, TDefinitions definitions, TDecision decision, List<String> errors) {
        TDMNElement.ExtensionElements extensionElements = decision.getExtensionElements();
        if (extensionElements != null) {
            List<Object> any = extensionElements.getAny();
            if (any != null) {
                for (Object obj : any) {
                    if (obj instanceof Element) {
                        String localName = ((Element) obj).getLocalName();
                        if (!"MultiInstanceDecisionLogic".equals(localName)) {
                            String errorMessage = String.format("Extension '%s' not supported", obj);
                            errors.add(makeError(repository, definitions, decision, errorMessage));
                        }
                    } else {
                        String errorMessage = String.format("Extension '%s' not supported", obj);
                        errors.add(makeError(repository, definitions, decision, errorMessage));
                    }
                }
            }
        }
    }

}
