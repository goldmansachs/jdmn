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
package com.gs.dmn.signavio.testlab;

import com.gs.dmn.error.ErrorFactory;
import com.gs.dmn.error.SemanticError;
import com.gs.dmn.error.ValidationError;

import java.util.ArrayList;
import java.util.List;

public class TestLabValidator {
    private static final String RULE_NAME = "test-lab-validator";

    public List<ValidationError> validate(TestLab testLab) {
        List<ValidationError> errors = new ArrayList<>();
        if (testLab == null) {
            String errorMessage = "Missing or empty TestLab";
            addValidationError(errorMessage, errors);
        }  else {
            List<OutputParameterDefinition> outputParameterDefinitions = testLab.getOutputParameterDefinitions();
            if (outputParameterDefinitions == null || outputParameterDefinitions.isEmpty()) {
                String errorMessage = String.format("Missing or empty OutputParameterDefinitions for TestLab '%s'", testLab.getSource());
                SemanticError error = ErrorFactory.makeDMNError(null, errorMessage);
                errors.add(new ValidationError(error, RULE_NAME));
            }
        }
        return errors;
    }

    private static void addValidationError(String errorMessage, List<ValidationError> errors) {
        SemanticError error = ErrorFactory.makeDMNError(null, errorMessage);
        errors.add(new ValidationError(error, RULE_NAME));
    }
}
