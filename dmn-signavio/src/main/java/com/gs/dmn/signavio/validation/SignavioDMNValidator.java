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
import org.omg.spec.dmn._20180521.model.TBusinessKnowledgeModel;
import org.omg.spec.dmn._20180521.model.TDMNElement;
import org.omg.spec.dmn._20180521.model.TDecision;
import org.omg.spec.dmn._20180521.model.TFunctionDefinition;
import org.w3c.dom.Element;

import java.util.List;

public class SignavioDMNValidator extends DefaultDMNValidator {
    public SignavioDMNValidator() {
    }

    @Override
    protected void validateBusinessKnowledgeModel(TBusinessKnowledgeModel knowledgeModel, List<String> errors) {
        super.validateBusinessKnowledgeModel(knowledgeModel, errors);

        // Validate encapsulated logic
        TFunctionDefinition encapsulatedLogic = knowledgeModel.getEncapsulatedLogic();
        TDMNElement.ExtensionElements extensionElements = knowledgeModel.getExtensionElements();
        if (encapsulatedLogic == null && extensionElements == null) {
            errors.add(String.format("Missing encapsulatedLogic for knowledgeModel '%s'", knowledgeModel.getName()));
        }
    }

    @Override
    protected void validateDecision(TDecision decision, DMNModelRepository dmnModelRepository, List<String> errors) {
        super.validateDecision(decision, dmnModelRepository, errors);
        // Validate extensions
        validateExtensionElements(decision, errors);
    }

    private void validateExtensionElements(TDecision decision, List<String> errors) {
        TDMNElement.ExtensionElements extensionElements = decision.getExtensionElements();
        if (extensionElements != null) {
            List<Object> any = extensionElements.getAny();
            if (any != null) {
                for (Object obj : any) {
                    if (obj instanceof Element) {
                        String localName = ((Element) obj).getLocalName();
                        if (!"MultiInstanceDecisionLogic".equals(localName)) {
                            errors.add(String.format("Extension '%s' not supported for decision '%s'", obj, decision.getName()));
                        }
                    } else {
                        errors.add(String.format("Extension '%s' not supported for decision '%s'", obj, decision.getName()));
                    }
                }
            }
        }
    }

}
