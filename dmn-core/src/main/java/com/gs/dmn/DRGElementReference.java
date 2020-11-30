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
import org.omg.spec.dmn._20191111.model.TDRGElement;

public class DRGElementReference<T extends TDRGElement> {
    // Import path from the root node
    private final ImportPath importPath;
    // Namespace of DM containing that contains the definition of the element
    private final String namespace;
    // Name of DM that contains the definition of the element
    private final String modelName;
    // The DRG element
    private final T element;

    public DRGElementReference(ImportPath importPath, String namespace, String modelName, T element) {
        this.importPath = importPath;
        this.namespace = namespace;
        this.modelName = modelName;
        this.element = element;
    }

    public ImportPath getImportPath() {
        return this.importPath;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public String getModelName() {
        return this.modelName;
    }

    public T getElement() {
        return this.element;
    }

    public String getElementName() {
        return this.element.getName();
    }

    public String getQualifiedHref() {
        String id = this.element.getId();
        return String.format("%s#%s", this.namespace, id);
    }

    public String getQualifiedImportName() {
        if (ImportPath.isEmpty(this.importPath)) {
            return getElementName();
        } else {
            return String.format("%s.%s", String.join(".", this.importPath.getPathElements()), getElementName());
        }
    }

    @Override
    public String toString() {
        return String.format("DMNReference(import='%s', namespace='%s', model='%s', element='%s')", this.importPath, this.namespace, this.modelName, this.element.getName());
    }
}
