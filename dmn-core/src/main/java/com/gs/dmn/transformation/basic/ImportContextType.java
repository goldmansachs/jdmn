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

import com.gs.dmn.feel.analysis.semantics.type.*;

import java.util.*;
import java.util.stream.Collectors;

import static com.gs.dmn.feel.analysis.semantics.type.AnyType.ANY;

public class ImportContextType extends ContextType implements CompositeDataType {
    private final String modelName;
    private final String importName;

    public ImportContextType(String modelName, String importName) {
        this.modelName = modelName;
        this.importName = importName;
    }

    public String getModelName() {
        return modelName;
    }

    public String getImportName() {
        return importName;
    }

    @Override
    public String toString() {
        return String.format("ImportContextType(%s, model='%s', import='%s')", super.toString(), modelName, importName);
    }
}
