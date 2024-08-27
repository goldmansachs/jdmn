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
package com.gs.dmn.tck.serialization.jackson;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.serialization.jackson.QNameDeserializer;
import com.gs.dmn.serialization.jackson.QNameKeyDeserializer;
import com.gs.dmn.serialization.jackson.QNameKeySerializer;
import com.gs.dmn.serialization.jackson.QNameSerializer;
import com.gs.dmn.tck.ast.TestCases;
import com.gs.dmn.tck.serialization.TCKMarshaller;

import javax.xml.namespace.QName;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class JsonTCKMarshaller implements TCKMarshaller {
    public static final ObjectMapper JSON_MAPPER = makeJsonMapper();
    private final BuildLogger logger;

    private static ObjectMapper makeJsonMapper() {
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

        // Add serializers & deserializers for QName
        SimpleModule module = new SimpleModule();
        module.addSerializer(QName.class, new QNameSerializer());
        module.addDeserializer(QName.class, new QNameDeserializer());
        module.addKeySerializer(QName.class, new QNameKeySerializer());
        module.addKeyDeserializer(QName.class, new QNameKeyDeserializer());
        objectMapper.registerModule(module);

        return objectMapper;
    }

    public JsonTCKMarshaller(BuildLogger logger) {
        this.logger = logger;
    }

    public TestCases unmarshal(String input, boolean validateSchema) {
        try {
            checkSchemaValidationFlag(validateSchema);
            return JSON_MAPPER.readValue(input, TestCases.class);
        } catch (IOException e) {
            throw new DMNRuntimeException(String.format("Cannot read TCK from '%s'", input), e);
        }
    }

    @Override
    public TestCases unmarshal(Reader input, boolean validateSchema) {
        try {
            checkSchemaValidationFlag(validateSchema);
            return JSON_MAPPER.readValue(input, TestCases.class);
        } catch (IOException e) {
            throw new DMNRuntimeException(String.format("Cannot read TCK from '%s'", input), e);
        }
    }

    @Override
    public String marshal(TestCases testCases) {
        try {
            return JSON_MAPPER.writeValueAsString(testCases);
        } catch (IOException e) {
            throw new DMNRuntimeException("Cannot write TCK as string", e);
        }
    }

    @Override
    public void marshal(TestCases testCases, Writer output) {
        try {
            JSON_MAPPER.writeValue(output, testCases);
        } catch (IOException e) {
            throw new DMNRuntimeException(String.format("Cannot write TCK to '%s'", output), e);
        }
    }

    private void checkSchemaValidationFlag(boolean validateSchema) {
        if (validateSchema) {
            logger.warn("Schema validation is not supported in Json serializers");
        }
    }
}
