/**
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
package com.gs.dmn.runtime.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.gs.dmn.feel.lib.type.time.xml.FEELXMLGregorianCalendar;
import com.gs.dmn.runtime.DMNRuntimeException;

import javax.xml.datatype.XMLGregorianCalendar;
import java.io.IOException;

public class XMLGregorianCalendarDeserializer extends JsonDeserializer<XMLGregorianCalendar> {
    public XMLGregorianCalendarDeserializer() {
    }

    @Override
    public XMLGregorianCalendar deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);

        try {
            String literal = node.asText();
            return new FEELXMLGregorianCalendar(literal);
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Error deserializing '%s' ", node), e);
        }
    }
}
