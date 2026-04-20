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

import com.gs.dmn.tck.TCKSerializer;
import com.gs.dmn.tck.ast.InputNode;
import com.gs.dmn.tck.ast.ResultNode;
import com.gs.dmn.tck.ast.TestCase;
import com.gs.dmn.tck.ast.TestCases;
import com.gs.dmn.tck.serialization.jackson.JsonTCKSerializer;
import com.gs.dmn.tck.serialization.xstream.XMLTCKSerializer;
import com.gs.dmn.transformation.AbstractFileTransformerTest;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TCKSerializerConversionTest extends AbstractFileTransformerTest {
    private final TCKSerializer xmlSerializer = new XMLTCKSerializer(LOGGER, this.inputParameters);
    private final TCKSerializer jsonSerializer = new JsonTCKSerializer(LOGGER, this.inputParameters);

    @Test
    public void testRoundTripStartingFromXML() throws Exception {
        // Read in XML format
        File inputXmlFile = new File(resource("dmn/input/1.4/0004-lending-test-01.xml"));
        TestCases testCases = this.xmlSerializer.read(inputXmlFile);
        checkTestCases(testCases);

        // Write & read in JSON format
        File parentFolder = new File("target/xml");
        parentFolder.mkdirs();
        File outputJsonFile = new File(parentFolder, "test-0004-lending-test-01.json");
        this.jsonSerializer.write(testCases, outputJsonFile);
        testCases = this.jsonSerializer.read(outputJsonFile);
        checkTestCases(testCases);

        // Write in XML format
        File outputXmlFile = new File(parentFolder, "test-0004-lending-test-01.xml");
        this.xmlSerializer.write(testCases, outputXmlFile);

        // Check files
        File expectedXmlFile = new File(resource("dmn/expected/1.4/0004-lending-test-01.xml"));
        compareFile(expectedXmlFile, outputXmlFile);
    }

    @Test
    public void testRoundTripStartingFromJson() throws Exception {
        // Read in JSON format
        File inputJsonFile = new File(resource("dmn/input/1.4/0004-lending-test-01.json"));
        TestCases testCases = this.jsonSerializer.read(inputJsonFile);
        checkTestCases(testCases);

        // Write & read in XML format
        File parentFolder = new File("target/json");
        parentFolder.mkdirs();
        File outputXmlFile = new File(parentFolder, "test-0004-lending-test-01.xml");
        this.xmlSerializer.write(testCases, outputXmlFile);
        testCases = this.xmlSerializer.read(outputXmlFile);
        checkTestCases(testCases);

        // Write in JSON format
        File outputJsonFile = new File(parentFolder, "test-0004-lending-test-01.json");
        this.jsonSerializer.write(testCases, outputJsonFile);

        // Check files
        File expectedJsonFile = new File(resource("dmn/expected/1.4/0004-lending-test-01.json"));
        compareFile(expectedJsonFile, outputJsonFile);
    }

    private void checkTestCases(TestCases testCases) {
        List<TestCase> testCaseList = testCases.getTestCase();
        assertEquals(1, testCaseList.size());

        TestCase testCase = testCaseList.get(0);
        assertNull(testCase.getName());
        assertEquals("001", testCase.getId());
        List<InputNode> inputNodes = testCase.getInputNode();
        assertEquals(4, inputNodes.size());
        List<ResultNode> resultNodes = testCase.getResultNode();
        assertEquals(11, resultNodes.size());
    }
}
