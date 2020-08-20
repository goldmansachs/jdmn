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

import java.util.List;

public class MessageType extends NameElement {
    private final List<Field> fields;

    public MessageType(String name, List<Field> fields) {
        super(name);
        if (fields == null || fields.isEmpty()) {
            throw new DMNRuntimeException(String.format("Mandatory proto message fields. Found '%s'", fields));
        }
        this.fields = fields;
    }

    public List<Field> getFields() {
        return this.fields;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageType that = (MessageType) o;

        if (!this.name.equals(that.name)) return false;
        return this.fields.equals(that.fields);
    }

    @Override
    public int hashCode() {
        int result = this.name.hashCode();
        result = 31 * result + this.fields.hashCode();
        return result;
    }
}
