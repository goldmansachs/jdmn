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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.serialization.DMNMarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class JsonDMNMarshaller implements DMNMarshaller {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonDMNMarshaller.class);

    private final ObjectMapper objectMapper;

    JsonDMNMarshaller(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public TDefinitions unmarshal(String input, boolean validateSchema) {
        try {
            checkSchemaValidationFlag(validateSchema);
            return this.objectMapper.readValue(input, TDefinitions.class);
        } catch (IOException e) {
            throw new DMNRuntimeException(String.format("Cannot read DMN from '%s'", input), e);
        }
    }

    @Override
    public TDefinitions unmarshal(Reader input, boolean validateSchema) {
        try {
            checkSchemaValidationFlag(validateSchema);
            return this.objectMapper.readValue(input, TDefinitions.class);
        } catch (IOException e) {
            throw new DMNRuntimeException(String.format("Cannot read DMN from '%s'", input), e);
        }
    }

    @Override
    public String marshal(TDefinitions definitions) {
        try {
            return this.objectMapper.writeValueAsString(definitions);
        } catch (IOException e) {
            throw new DMNRuntimeException("Cannot write DMN as string", e);
        }
    }

    @Override
    public void marshal(TDefinitions definitions, Writer output) {
        try {
            this.objectMapper.writeValue(output, definitions);
        } catch (IOException e) {
            throw new DMNRuntimeException(String.format("Cannot write DMN to '%s'", output), e);
        }
    }

    private void checkSchemaValidationFlag(boolean validateSchema) {
        if (validateSchema) {
            LOGGER.warn("Schema validation is not supported in Json serializers");
        }
    }
}
