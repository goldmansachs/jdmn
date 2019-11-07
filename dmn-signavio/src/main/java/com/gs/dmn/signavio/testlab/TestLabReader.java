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
package com.gs.dmn.signavio.testlab;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.Reader;

public class TestLabReader {
    public static final String TEST_LAB_FILE_EXTENSION = ".json";
    public static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.setVisibility(MAPPER.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
    }

    public TestLab read(File inputFile) throws IOException {
        TestLab testLab = MAPPER.readValue(inputFile, TestLab.class);
        testLab.setSource(inputFile.getName());
        return testLab;
    }

    public TestLab read(Reader reader) throws IOException {
        TestLab testLab = MAPPER.readValue(reader, TestLab.class);
        testLab.setSource(reader.getClass().getSimpleName());
        return testLab;
    }

    public void write(TestLab testLab, File file) throws IOException {
        MAPPER.writerWithDefaultPrettyPrinter().writeValue(file, testLab);
    }
}
