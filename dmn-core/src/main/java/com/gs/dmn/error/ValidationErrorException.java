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
package com.gs.dmn.error;

import java.util.List;

public class ValidationErrorException extends SemanticErrorException {
    private final List<ValidationError> errors;

    public ValidationErrorException(String errorMessage, List<ValidationError> errors) {
        super(errorMessage);
        this.errors = errors;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }

    public String toString() {
        return String.format("%s: %s", getMessage(), errors);
    }
}
