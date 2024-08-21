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
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import javax.xml.namespace.QName;
import java.io.IOException;

public class QNameSerializer extends StdSerializer<QName> {
    public static String toKey(QName value) {
        if (value == null) {
            return null;
        }

        String namespaceURI = value.getNamespaceURI();
        String localPart = value.getLocalPart();
        String prefix = value.getPrefix();
        if (namespaceURI.isEmpty() && prefix.isEmpty()) {
            return localPart;
        } else {
            return String.format("%s|%s|%s", namespaceURI, localPart, prefix);
        }
    }

    public QNameSerializer() {
        super(QName.class);
    }

    @Override
    public void serialize(QName value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(toKey(value));
    }
}
