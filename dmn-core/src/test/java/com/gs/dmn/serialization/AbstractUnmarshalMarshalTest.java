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
package com.gs.dmn.serialization;

import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.transformation.AbstractFileTransformerTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public abstract class AbstractUnmarshalMarshalTest extends AbstractFileTransformerTest {
    protected static final Logger LOG = LoggerFactory.getLogger(AbstractUnmarshalMarshalTest.class);

    protected void testRoundTrip(String subDir, String dmnFile) throws Exception {
        DMNMarshaller marshaller = getMarshaller();
        testRoundTrip(subDir, dmnFile, marshaller);
    }

    protected void testRoundTrip(String subDir, String dmnFile, DMNMarshaller marshaller) throws Exception {
        File baseInputDir = new File("target/test-classes/");
        File baseOutputDir = new File("target/");

        // Validate input XML
        File inputDMNFile = new File(baseInputDir, subDir + dmnFile);
        validateXSDSchema(inputDMNFile);

        // Read definitions
        FileInputStream fis = new FileInputStream(inputDMNFile);
        TDefinitions definitions = readModel(marshaller, fis);

        // Write definitions
        LOG.debug("{}", marshaller.marshal(definitions));
        File outputDMNFile = new File(baseOutputDir, subDir + dmnFile);
        outputDMNFile.getParentFile().mkdirs();
        writeModel(marshaller, definitions, outputDMNFile);

        // Validate output XML:
        validateXSDSchema(outputDMNFile);

        // Compare input and output
        compareDMNFile(inputDMNFile, outputDMNFile);
    }

    protected TDefinitions readModel(DMNMarshaller marshaller, FileInputStream fis) {
        TDefinitions definitions = marshaller.unmarshal(new InputStreamReader(fis), true);
        return definitions;
    }

    protected void writeModel(DMNMarshaller marshaller, TDefinitions definitions, File outputDMNFile) throws IOException {
        try (FileWriter targetFos = new FileWriter(outputDMNFile)) {
            marshaller.marshal(definitions, targetFos);
        }
    }

    protected abstract DMNMarshaller getMarshaller();

    protected abstract void validateXSDSchema(File inputDMNFile);

    protected abstract void compareDMNFile(File inputDMNFile, File outputDMNFile) throws Exception;
}
