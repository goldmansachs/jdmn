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
package com.gs.dmn.tck.validation;

import com.gs.dmn.tck.ast.TestCases;
import com.gs.dmn.tck.error.ErrorFactory;
import com.gs.dmn.tck.error.TCKError;

import java.util.ArrayList;
import java.util.List;

public class CompositeTCKValidator implements TCKValidator {
    private final List<TCKValidator> validators = new ArrayList<>();

    public CompositeTCKValidator(List<TCKValidator> validators) {
        if (validators != null) {
            this.validators.addAll(validators);
        }
    }

    @Override
    public List<ValidationError> validate(TestCases testCases) {
        List<ValidationError> errors = new ArrayList<>();
        if (isEmpty(testCases)) {
            return errors;
        }

        for (TCKValidator validator : this.validators) {
            try {
                List<ValidationError> result = validator.validate(testCases);
                if (result != null) {
                    errors.addAll(result);
                }
            } catch (Exception e) {
                String ruleName = validator.ruleName();
                String errorMessage = String.format("Fatal error in validator '%s' %s", ruleName, e.getMessage());
                TCKError error = ErrorFactory.makeTCKError(null, errorMessage);
                errors.add(new ValidationError(error, ruleName));
            }
        }
        return errors;
    }
}
