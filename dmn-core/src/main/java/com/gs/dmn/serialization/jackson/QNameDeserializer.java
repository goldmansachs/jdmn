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

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import javax.xml.namespace.QName;
import java.io.IOException;

public class QNameDeserializer extends StdDeserializer<QName> {
    public static QName fromKey(String key) {
        if (key == null) {
            return null;
        }

        String[] parts = key.split("\\|");
        if (parts.length == 3) {
            return new QName(parts[0], parts[1], parts[2]);
        } else if (parts.length == 2) {
            return new QName(parts[0], parts[1]);
        } else if (parts.length == 1) {
            return new QName(parts[0]);
        } else {
            return null;
        }
    }

    public QNameDeserializer() {
        super(QName.class);
    }

    @Override
    public QName deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        return fromKey(node.asText());
    }
}
