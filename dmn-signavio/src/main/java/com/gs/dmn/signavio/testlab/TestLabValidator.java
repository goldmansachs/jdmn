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

import java.util.ArrayList;
import java.util.List;

public class TestLabValidator {
    public List<SemanticError> validate(TestLab testLab) {
        List<SemanticError> errors = new ArrayList<>();
        if (testLab == null) {
            errors.add(ErrorFactory.makeDMNError(null, "Missing or empty TestLab"));
        }  else {
            List<OutputParameterDefinition> outputParameterDefinitions = testLab.getOutputParameterDefinitions();
            if (outputParameterDefinitions == null || outputParameterDefinitions.isEmpty()) {
                errors.add(ErrorFactory.makeDMNError(null, String.format("Missing or empty OutputParameterDefinitions for TestLab '%s'", testLab.getSource())));
            }
        }
        return errors;
    }
}
