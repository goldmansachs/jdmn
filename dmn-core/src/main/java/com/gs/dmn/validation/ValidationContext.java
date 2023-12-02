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
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class ValidationContext {
    private final DMNModelRepository repository;
    private final List<String> errors;

    public ValidationContext(DMNModelRepository repository, List<String> errors) {
        this.repository = repository;
        this.errors = errors;
    }

    public DMNModelRepository getRepository() {
        return repository;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void addError(String error) {
        if (StringUtils.isNotBlank(error)) {
            this.errors.add(error);
        }
    }
}
