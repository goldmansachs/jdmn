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
package com.gs.dmn.feel.analysis.semantics.type;

import java.util.Objects;

public abstract class NamedType implements com.gs.dmn.el.analysis.semantics.type.NamedType, FEELType {
    protected final String name;

    protected NamedType(String name) {
        this.name = name;
        Objects.requireNonNull(name, "Missing mandatory type name");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isFullySpecified() {
        return name != null && !name.isEmpty();
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
