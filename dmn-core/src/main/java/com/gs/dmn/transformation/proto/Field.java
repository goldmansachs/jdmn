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
package com.gs.dmn.transformation.proto;

import com.gs.dmn.runtime.DMNRuntimeException;

public class Field extends NameElement {
    private final FieldType type;
    private final String defaultValue;

    public Field(String name, FieldType type) {
        this(name, type, null);
    }

    public Field(String name, FieldType type, String defaultValue) {
        super(name);
        if (type == null) {
            throw new DMNRuntimeException(String.format("Mandatory proto field type. Found '%s'", type));
        }
        this.type = type;
        this.defaultValue = defaultValue;
    }

    public String getTypeModifier(String version) {
        if ("proto3".equals(version) && "optional".equals(this.type.getModifier())) {
            // optional by default
            return null;
        } else {
            return this.type.getModifier();
        }
    }

    public String getTypeName() {
        return this.type.getType();
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Field field = (Field) o;

        return this.name.equals(field.name);
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
}
