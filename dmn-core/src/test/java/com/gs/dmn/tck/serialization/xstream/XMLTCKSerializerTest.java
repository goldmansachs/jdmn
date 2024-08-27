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
package com.gs.dmn.tck.serialization.xstream;

import com.gs.dmn.tck.TCKSerializer;
import com.gs.dmn.tck.ast.InputNode;
import com.gs.dmn.tck.ast.ResultNode;
import com.gs.dmn.tck.ast.TestCase;
import com.gs.dmn.tck.ast.TestCases;
import com.gs.dmn.tck.serialization.AbstractTCKSerializationTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class XMLTCKSerializerTest extends AbstractTCKSerializationTest {
    @Test
    public void testRead() {
        doReadTest("dmn/input/1.4/0004-lending-test-01.xml");
    }

    @Test
    public void testWrite() throws IOException {
        doWriteTest();
    }

    @Test
    public void testRoundTrip() throws Exception {
        String inputPath = "dmn/input/1.4/0004-lending-test-01.xml";
        String expectedPath = "dmn/expected/1.4/0004-lending-test-01-no-default.xml";
        doRoundTripTest(inputPath, expectedPath);
    }

    @Override
    protected TCKSerializer makeSerializer() {
        return new XMLTCKSerializer(LOGGER, this.inputParameters);
    }

    @Override
    protected void checkTestCases(TestCases testCases) {
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
