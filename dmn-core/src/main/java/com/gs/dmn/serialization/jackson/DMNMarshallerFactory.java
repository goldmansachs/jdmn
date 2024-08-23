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

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gs.dmn.ast.TDMNElement;
import com.gs.dmn.serialization.DMNMarshaller;
import com.gs.dmn.serialization.xstream.dom.NSElement;

import javax.xml.namespace.QName;
import java.util.LinkedHashMap;
import java.util.Map;

public final class DMNMarshallerFactory {
    private final static ObjectMapper OBJECT_MAPPER = makeJsonMapper(new LinkedHashMap<>());

    private static ObjectMapper makeJsonMapper(Map<String, Class<?>> mapper) {
        ObjectMapper objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .visibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
                .visibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE)
                .visibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE)
                .visibility(PropertyAccessor.CREATOR, JsonAutoDetect.Visibility.NONE)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .enable(SerializationFeature.INDENT_OUTPUT)
                .build();

        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        // Add serializers & deserializers for QName - used for otherAttributes
        SimpleModule module = new SimpleModule();
        module.addSerializer(QName.class, new QNameSerializer());
        module.addDeserializer(QName.class, new QNameDeserializer());
        module.addKeySerializer(QName.class, new QNameKeySerializer());
        module.addKeyDeserializer(QName.class, new QNameKeyDeserializer());

        // Add serializers & deserializers for TDMNElement.ExtensionElements, used for <extensionElements>
        module.addSerializer(NSElement.class, new NSElementSerializer());
        module.addDeserializer(NSElement.class, new NSElementDeserializer());

        // Add serializers & deserializers for TDMNElement.ExtensionElements, used for <extensionElements>
        module.addSerializer(TDMNElement.ExtensionElements.class, new ExtensionsSerializer());
        module.addDeserializer(TDMNElement.ExtensionElements.class, new ExtensionsDeserializer(mapper));

        objectMapper.registerModule(module);

        return objectMapper;
    }

    public static DMNMarshaller newDefaultMarshaller() {
        return new JsonDMNMarshaller(OBJECT_MAPPER);
    }

    public static DMNMarshaller newMarshallerWithExtensions(Map<String, Class<?>> mapper) {
        return new JsonDMNMarshaller(makeJsonMapper(mapper));
    }

    private DMNMarshallerFactory() {
    }

}
