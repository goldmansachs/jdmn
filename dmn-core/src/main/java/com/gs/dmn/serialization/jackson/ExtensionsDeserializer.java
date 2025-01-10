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
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.gs.dmn.ast.TDMNElement;
import com.gs.dmn.serialization.xstream.dom.ElementInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ExtensionsDeserializer extends JsonDeserializer<TDMNElement.ExtensionElements> {
    private final Map<String, Class<?>> mapper;

    public ExtensionsDeserializer(Map<String, Class<?>> mapper) {
        this.mapper = mapper;
    }

    @Override
    public TDMNElement.ExtensionElements deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
        ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);

        JsonNode elementInfoNode = node.get("elementInfo");
        ElementInfo elementInfo = codec.treeToValue(elementInfoNode, ElementInfo.class);
        JsonNode anyNode = node.get("any");
        List<Object> any = new ArrayList<>();
        if (anyNode instanceof ArrayNode) {
            Iterator<JsonNode> elements = anyNode.elements();
            while (elements.hasNext()) {
                JsonNode next = elements.next();
                Object extension = toExtension(next, codec);
                any.add(extension);
            }
        }
        TDMNElement.ExtensionElements extensionElements = new TDMNElement.ExtensionElements();
        extensionElements.setElementInfo(elementInfo);
        extensionElements.getAny().addAll(any);
        return extensionElements;
    }

    private Object toExtension(JsonNode node, ObjectCodec codec) {
        try {
            JsonNode jsonNode = node.get("@type");
            if (jsonNode != null) {
                Class<?> cls = this.mapper.get(jsonNode.asText());
                if (cls != null) {
                    return codec.treeToValue(node, cls);
                } else {
                    return null;
                }
            } else {
                return node.asText();
            }
        } catch (Exception e) {
            return null;
        }
    }
}
