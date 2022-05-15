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
package com.gs.dmn.tck.serialization.xstream.v1;

import com.gs.dmn.tck.TCKSerializer;
import com.gs.dmn.tck.ast.TestCases;
import com.gs.dmn.tck.serialization.AbstractTCKUnmarshalMarshalTest;
import com.gs.dmn.tck.serialization.TCKMarshaller;
import com.gs.dmn.tck.serialization.xstream.TCKMarshallerFactory;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.Objects;

public class UnmarshalMarshalTest extends AbstractTCKUnmarshalMarshalTest {
    @Test
    public void testTCKTests() throws Exception {
        testRoundTrip(STANDARD_FOLDER);
    }

    private void testRoundTrip(File file) throws Exception {
        if (TCKSerializer.isTCKFile(file)) {
            LOGGER.warn(String.format("Testing '%s'", file.getPath()));
            TCKMarshaller marshaller = TCKMarshallerFactory.newDefaultMarshaller();
            testRoundTrip(file, marshaller);
        } else if (file.isDirectory() && !file.getName().equals("expected")) {
            for (File child: file.listFiles()) {
                testRoundTrip(child);
            }
        }
    }

    private void testRoundTrip(File inputTCKFile, TCKMarshaller marshaller) throws Exception {
        LOGGER.debug(String.format("Testing '%s'", inputTCKFile.getPath()));

        File baseOutputDir = new File("target/tck/xstream/");
        baseOutputDir.mkdirs();

        // Validate input XML
        validateXSDSchema(inputTCKFile);

        // Read definitions
        FileInputStream fis = new FileInputStream(inputTCKFile);
        TestCases testCases = readModel(marshaller, fis);
        Objects.requireNonNull(testCases, "Missing testCases");

        // Write definitions
        LOGGER.debug(marshaller.marshal(testCases));
        File outputTCKFile = new File(baseOutputDir, inputTCKFile.getName());
        outputTCKFile.getParentFile().mkdirs();
        writeModel(marshaller, testCases, outputTCKFile);

        // Validate output XML:
        validateXSDSchema(outputTCKFile);

        // Compare input and output
        compareFile(inputTCKFile, outputTCKFile);
    }
}
