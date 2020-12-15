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
package com.gs.dmn.transformation.basic;

import com.gs.dmn.DRGElementReference;
import com.gs.dmn.feel.analysis.semantics.type.CompositeDataType;
import com.gs.dmn.feel.analysis.semantics.type.ContextType;
import org.omg.spec.dmn._20191111.model.TDRGElement;

import java.util.LinkedHashMap;
import java.util.Map;

public class ImportContextType extends ContextType implements CompositeDataType {
    private final String importName;
    private final Map<String, DRGElementReference<? extends TDRGElement>> referenceMap = new LinkedHashMap<>();

    public ImportContextType(String importName) {
        this.importName = importName;
    }

    public String getImportName() {
        return importName;
    }

    public DRGElementReference<? extends TDRGElement> getMemberReference(String name) {
        DRGElementReference<? extends TDRGElement> reference = this.referenceMap.get(name);
        return reference;
    }

    public void addMemberReference(String name, DRGElementReference<? extends TDRGElement> reference) {
        this.referenceMap.put(name, reference);
    }

    @Override
    public String toString() {
        return String.format("ImportContextType(importName='%s' %s)", this.importName, super.toString());
    }
}
