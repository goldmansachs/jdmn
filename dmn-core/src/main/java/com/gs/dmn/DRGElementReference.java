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
package com.gs.dmn;

import com.gs.dmn.runtime.interpreter.ImportPath;
import org.apache.commons.lang3.StringUtils;
import org.omg.spec.dmn._20180521.model.TDRGElement;
import org.omg.spec.dmn._20180521.model.TDefinitions;

import java.util.List;
import java.util.Objects;

public class DRGElementReference<T extends TDRGElement> {
    private final TDefinitions model;
    private final T element;
    private final ImportPath importPath = new ImportPath();

    public DRGElementReference(TDefinitions model, T element, String importName) {
        this.model = model;
        this.element = element;
        if (!StringUtils.isBlank(importName)) {
            this.importPath.addPathElement(importName);
        }
    }

    public TDefinitions getModel() {
        return model;
    }

    public T getElement() {
        return this.element;
    }

    public List<String> getImportPathElements() {
        return this.importPath.getPathElements();
    }

    public String getModelName() {
        return this.model.getName();
    }

    public String getElementName() {
        return this.element.getName();
    }

    public String getQualifiedName() {
        if (this.importPath.isEmpty()) {
            return this.getElementName();
        } else {
            return String.format("%s.%s", this.getModelName(), this.getElementName());
        }
    }

    public void push(String prefix) {
        if (!StringUtils.isBlank(prefix)) {
            this.importPath.add(0, prefix);
        }
    }

    @Override
    public String toString() {
        return String.format("DMNReference(name='%s', model='%s', import='%s')", getElementName(), getModelName(), getImportPathElements());
    }
}
