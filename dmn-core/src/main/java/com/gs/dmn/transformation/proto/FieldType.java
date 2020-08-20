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
import org.apache.commons.lang3.StringUtils;

public class FieldType {
    private final String modifier;
    private final String type;

    public FieldType(String modifier, String type) {
        if (StringUtils.isBlank(modifier)) {
            throw new DMNRuntimeException(String.format("Mandatory proto field type modifier. Found '%s'", modifier));
        }
        if (StringUtils.isBlank(type)) {
            throw new DMNRuntimeException(String.format("Mandatory proto field type. Found '%s'", type));
        }

        this.modifier = modifier;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getModifier() {
        return modifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FieldType fieldType = (FieldType) o;

        if (modifier != null ? !modifier.equals(fieldType.modifier) : fieldType.modifier != null) return false;
        return type != null ? type.equals(fieldType.type) : fieldType.type == null;
    }

    @Override
    public int hashCode() {
        int result = modifier != null ? modifier.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
