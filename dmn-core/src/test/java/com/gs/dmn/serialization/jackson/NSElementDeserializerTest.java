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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.DeserializerFactoryConfig;
import com.fasterxml.jackson.databind.deser.BeanDeserializerFactory;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
import com.fasterxml.jackson.databind.deser.DeserializerFactory;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import com.gs.dmn.serialization.xstream.dom.NSElement;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class NSElementDeserializerTest {
    private final NSElementDeserializer deserializer = new NSElementDeserializer();

    @Test
    void testDeserialize() throws IOException {
        JsonNode node = new TextNode("text");
        JsonParser parser = new TreeTraversingParser(node);
        parser.setCodec(new ObjectMapper());
        DeserializerFactoryConfig config = new DeserializerFactoryConfig();
        DeserializerFactory df = new BeanDeserializerFactory(config);
        DeserializationContext context = new DefaultDeserializationContext.Impl(df);
        NSElement deserialize = deserializer.deserialize(parser, context);
        assertNotNull(deserialize);
    }
}