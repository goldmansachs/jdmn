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
package com.gs.dmn.tck.serialization;

import com.gs.dmn.error.SyntaxErrorException;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.serialization.TCKVersion;
import com.gs.dmn.tck.TCKSerializer;
import com.gs.dmn.tck.ast.InputNode;
import com.gs.dmn.tck.ast.ResultNode;
import com.gs.dmn.tck.ast.TestCase;
import com.gs.dmn.tck.ast.TestCases;
import com.gs.dmn.tck.serialization.xstream.XMLTCKSerializer;
import com.gs.dmn.AbstractFileTransformerTest;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class AbstractTCKSerializationTest extends AbstractFileTransformerTest {
    protected final TCKSerializer serializer = makeSerializer();

    protected Map<String, String> makeInputParametersMap() {
        Map<String, String> inputParams = super.makeInputParametersMap();
        inputParams.put("xsdValidation", "true");
        return inputParams;
    }

    protected void doReadTest(String inputPath) {
        File input = new File(resource(inputPath));

        TestCases testCases = this.serializer.read(input);
        checkTestCases(testCases);
    }

    protected void doWriteTest() throws IOException {
        // Test partial objects
        File outputFile = File.createTempFile("jdmn-", "-tck");
        outputFile.deleteOnExit();

        assertThrows(DMNRuntimeException.class, () -> this.serializer.write(null, outputFile));

        assertDoesNotThrow(() -> {
            TestCases testCases = new TestCases();
            testCases.getElementInfo().getNsContext().putAll(TCKVersion.LATEST.getPrefixToNamespaceMap());
            this.serializer.write(testCases, outputFile);
        });

        assertDoesNotThrow(() -> {
            TestCases testCases = new TestCases();
            testCases.getElementInfo().getNsContext().putAll(TCKVersion.LATEST.getPrefixToNamespaceMap());
            TestCase testCase = new TestCase();
            testCase.getInputNode().add(new InputNode());
            testCase.getResultNode().add(new ResultNode());
            testCases.getTestCase().add(testCase);
            this.serializer.write(testCases, outputFile);
        });
    }

    protected void doRoundTripTest(String inputPath, String expectedPath) throws Exception {
        doRoundTripTest(inputPath, expectedPath, this.serializer);
    }

    protected void doRoundTripTest(String inputPath, String expectedPath, TCKSerializer serializer) throws Exception {
        File inputFile = new File(resource(inputPath));
        TestCases testCases = serializer.read(inputFile);
        String extension = serializer instanceof XMLTCKSerializer ? ".xml" : ".json";
        String outputFileName = String.format("test-" + inputFile.getName() + extension);
        File outputFile = new File("target", outputFileName);
        serializer.write(testCases, outputFile);

        File expectedFile = new File(resource(expectedPath));
        compareFile(expectedFile, outputFile);
    }

    @Test
    public void testReadWhenErrors() {
        // Test null input
        assertThrows(DMNRuntimeException.class, () -> {
            this.serializer.read((File) null);
        });
        assertThrows(DMNRuntimeException.class, () -> {
            this.serializer.read((Reader) null);
        });

        // Test empty input
        assertThrows(SyntaxErrorException.class, () -> {
            this.serializer.read(new StringReader(""));
        });
    }

    @Test
    public void testWriteWhenErrors() {
        // Test write
        assertThrows(DMNRuntimeException.class, () -> {
            this.serializer.write(null, new File("test"));
        });
        assertThrows(DMNRuntimeException.class, () -> {
            this.serializer.write(new TestCases(), (File) null);
        });

        // Test write
        assertThrows(DMNRuntimeException.class, () -> {
            this.serializer.write(null, new StringWriter());
        });
        assertThrows(DMNRuntimeException.class, () -> {
            this.serializer.write(new TestCases(), (Writer) null);
        });
    }

    protected abstract TCKSerializer makeSerializer();

    protected abstract void checkTestCases(TestCases testCases);
}
