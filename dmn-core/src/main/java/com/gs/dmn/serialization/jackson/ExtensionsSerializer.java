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
package com.gs.dmn.serialization.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.gs.dmn.ast.TDMNElement;

import java.io.IOException;

public class ExtensionsSerializer extends JsonSerializer<TDMNElement.ExtensionElements> {
    @Override
    public void serialize(TDMNElement.ExtensionElements element, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("elementInfo", element.getElementInfo());
        if (!element.getAny().isEmpty()) {
            gen.writeObjectField("any", element.getAny());
        }
        gen.writeEndObject();
    }
}
