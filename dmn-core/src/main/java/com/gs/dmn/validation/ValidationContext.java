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
import com.gs.dmn.error.SemanticError;

import java.util.ArrayList;
import java.util.List;

public class ValidationContext {
    protected final DMNModelRepository repository;
    protected final List<SemanticError> errors;

    public ValidationContext(DMNModelRepository repository) {
        this.repository = repository;
        this.errors = new ArrayList<>();
    }

    public DMNModelRepository getRepository() {
        return repository;
    }

    public List<SemanticError> getErrors() {
        return errors;
    }

    public void addError(SemanticError error) {
        if (error != null) {
            this.errors.add(error);
        }
    }
}
