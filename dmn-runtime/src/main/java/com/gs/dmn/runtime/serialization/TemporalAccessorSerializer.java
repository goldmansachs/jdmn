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

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.gs.dmn.feel.lib.JavaTimeFEELLib;

import java.io.IOException;
import java.time.temporal.TemporalAccessor;

public class TemporalAccessorSerializer extends JsonSerializer<TemporalAccessor> {
    private final JavaTimeFEELLib feelLib = new JavaTimeFEELLib();
    @Override
    public void serialize(TemporalAccessor value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(feelLib.string(value));
    }
}
