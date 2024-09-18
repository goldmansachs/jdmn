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
package com.gs.dmn.runtime.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.gs.dmn.feel.lib.type.time.pure.TemporalAmountDurationLib;
import com.gs.dmn.runtime.DMNRuntimeException;

import java.io.IOException;
import java.time.temporal.TemporalAmount;

public class TemporalAmountDeserializer extends JsonDeserializer<TemporalAmount> {
    private final TemporalAmountDurationLib durationLib = new TemporalAmountDurationLib();

    public TemporalAmountDeserializer() {
    }

    @Override
    public TemporalAmount deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);

        try {
            String literal = node.asText();
            if (literal == null) {
                return null;
            } else {
                return durationLib.duration(literal);
            }
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Error deserializing '%s' ", node), e);
        }
    }
}
