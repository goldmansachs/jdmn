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
import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.ast.TNamedElement;
import com.gs.dmn.error.ValidationError;
import com.gs.dmn.runtime.Pair;

import java.util.List;
import java.util.stream.Collectors;

public class TypeRefValidationContext extends ValidationContext {
    private final TDefinitions model;
    private final List<Pair<TNamedElement, ValidationError>> errorReport;

    private TNamedElement element;

    public TypeRefValidationContext(TDefinitions model, DMNModelRepository repository, List<Pair<TNamedElement, ValidationError>> errorReport) {
        super(repository);
        this.model = model;
        this.errorReport = errorReport;
    }

    public TDefinitions getModel() {
        return this.model;
    }

    public TNamedElement getElement() {
        return element;
    }

    public void setElement(TNamedElement element) {
        this.element = element;
    }

    @Override
    public List<ValidationError> getErrors() {
        return this.errorReport.stream().map(Pair::getRight).collect(Collectors.toList());
    }

    public List<Pair<TNamedElement, ValidationError>> getErrorReport() {
        return errorReport;
    }
}
