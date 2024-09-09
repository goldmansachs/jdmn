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
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import com.gs.dmn.serialization.xstream.dom.NSElement;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class NSElementSerializerTest {
    private final NSElementSerializer serializer = new NSElementSerializer();

    @Test
    public void testSerialize() throws IOException {
        NSElement element = new NSElement(null, "prefix", "namespace");
        ObjectCodec codec = new ObjectMapper();
        JsonGenerator generator = new TokenBuffer(codec, false);
        SerializerProvider serializers = new DefaultSerializerProvider.Impl();
        serializer.serialize(element, generator, serializers);
        assertTrue(true);
    }
}