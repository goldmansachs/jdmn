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

import java.util.List;

public class MessageType {
    private final String name;
    private final List<Field> fields;

    public MessageType(String name, List<Field> fields) {
        if (StringUtils.isBlank(name)) {
            throw new DMNRuntimeException(String.format("Mandatory proto message name. Found '%s'", name));
        }
        if (fields == null || fields.isEmpty()) {
            throw new DMNRuntimeException(String.format("Mandatory proto message fields. Found '%s'", fields));
        }
        this.name = name;
        this.fields = fields;
    }

    public String getName() {
        return name;
    }

    public List<Field> getFields() {
        return fields;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageType that = (MessageType) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return fields != null ? fields.equals(that.fields) : that.fields == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (fields != null ? fields.hashCode() : 0);
        return result;
    }
}
