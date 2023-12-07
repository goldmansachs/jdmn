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
import com.gs.dmn.ast.*;
import com.gs.dmn.validation.DefaultDMNValidator;
import com.gs.dmn.validation.ValidationContext;
import org.w3c.dom.Element;

import java.util.List;

public class SignavioDMNValidator extends DefaultDMNValidator {
    public SignavioDMNValidator() {
    }

    @Override
    protected void validateBusinessKnowledgeModel(TDefinitions definitions, TBusinessKnowledgeModel knowledgeModel, ValidationContext context) {
        super.validateBusinessKnowledgeModel(definitions, knowledgeModel, context);

        // Validate encapsulated logic
        TFunctionDefinition encapsulatedLogic = knowledgeModel.getEncapsulatedLogic();
        TDMNElement.ExtensionElements extensionElements = knowledgeModel.getExtensionElements();
        if (encapsulatedLogic == null && extensionElements == null) {
            String errorMessage = "Missing encapsulatedLogic";
            context.addError(makeError(context.getRepository(), definitions, knowledgeModel, errorMessage));
        }
    }

    @Override
    protected void validateDecision(TDefinitions definitions, TDecision decision, ValidationContext context) {
        super.validateDecision(definitions, decision, context);
        // Validate extensions
        validateExtensionElements(definitions, decision, context);
    }

    private void validateExtensionElements(TDefinitions definitions, TDecision decision, ValidationContext context) {
        TDMNElement.ExtensionElements extensionElements = decision.getExtensionElements();
        if (extensionElements != null) {
            List<Object> any = extensionElements.getAny();
            if (any != null) {
                for (Object obj : any) {
                    DMNModelRepository repository = context.getRepository();
                    if (obj instanceof Element) {
                        String nodeName = ((Element) obj).getNodeName();
                        if (!"MultiInstanceDecisionLogic".equals(nodeName)) {
                            String errorMessage = String.format("Extension '%s' not supported", obj);
                            context.addError(makeError(repository, definitions, decision, errorMessage));
                        }
                    } else {
                        String errorMessage = String.format("Extension '%s' not supported", obj);
                        context.addError(makeError(repository, definitions, decision, errorMessage));
                    }
                }
            }
        }
    }

}
