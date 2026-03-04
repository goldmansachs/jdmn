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
package com.gs.dmn.transformation;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.log.NopBuildLogger;
import com.gs.dmn.tck.ast.*;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TestCaseTransformerTest {
    private final TestCaseTransformer transformer = new TestCaseTransformer(new NopBuildLogger());

    @Test
    void testTransformRepository() {
         assertNotNull(transformer.transform(new DMNModelRepository()));
    }

    @Test
    void testTransformRepositoryAndTestCases() {
        TestCases testCases = new TestCases();
        testCases.getTestCase().add(new TestCase());
        assertNotNull(transformer.transform(makeRepository(), Arrays.asList(testCases)));
    }

    @Test
    void testTransformTestCaseWhenDecision() {
        TestCases testCases = new TestCases();
        TestCase testCase = makeTestCaseForDecision("001", "input1", "result1");
        testCases.getTestCase().add(testCase);
        assertNotNull(transformer.transform(makeRepository(), Arrays.asList(testCases)));

        assertEquals(TestCaseType.DECISION, testCase.getType());
        assertNull(testCase.getInvocableName());
        assertEquals("001", testCase.getId());
        assertEquals(1, testCase.getInputNode().size());
        assertEquals("input1", testCase.getInputNode().get(0).getName());
        assertEquals(1, testCase.getResultNode().size());
        assertEquals("result1", testCase.getResultNode().get(0).getName());
    }

    @Test
    void testTransformTestCaseWhenBKM() {
        TestCases testCases = new TestCases();
        TestCase testCase = makeTestCaseForBKM("001", "bkm","input1", "result1");
        testCases.getTestCase().add(testCase);
        assertNotNull(transformer.transform(makeRepository(), Arrays.asList(testCases)));

        assertEquals(TestCaseType.DECISION, testCase.getType());
        assertNull(testCase.getInvocableName());
        assertEquals("001", testCase.getId());
        assertEquals(1, testCase.getInputNode().size());
        assertEquals("input1", testCase.getInputNode().get(0).getName());
        assertEquals(1, testCase.getResultNode().size());
        assertEquals("bkm", testCase.getResultNode().get(0).getName());
    }

    @Test
    void testTransformTestCaseWhenDSWithOneOutput() {
        TestCases testCases = new TestCases();
        TestCase testCase = makeTestCaseForDS("001", "ds","input1", "result1");
        testCases.getTestCase().add(testCase);
        assertNotNull(transformer.transform(makeRepository(), Arrays.asList(testCases)));

        assertEquals(TestCaseType.DECISION, testCase.getType());
        assertNull(testCase.getInvocableName());
        assertEquals("001", testCase.getId());
        assertEquals(1, testCase.getInputNode().size());
        assertEquals("input1", testCase.getInputNode().get(0).getName());
        assertEquals(1, testCase.getResultNode().size());
        assertEquals("ds", testCase.getResultNode().get(0).getName());
    }

    @Test
    void testTransformTestCaseWhenDSWithTwoOutputs() {
        TestCases testCases = new TestCases();
        TestCase testCase = makeTestCaseForDS("001", "ds","input1", "result1", "result2");
        testCases.getTestCase().add(testCase);
        assertNotNull(transformer.transform(makeRepository(), Arrays.asList(testCases)));

        assertEquals(TestCaseType.DECISION, testCase.getType());
        assertNull(testCase.getInvocableName());
        assertEquals("001", testCase.getId());
        assertEquals(1, testCase.getInputNode().size());
        assertEquals("input1", testCase.getInputNode().get(0).getName());
        assertEquals(1, testCase.getResultNode().size());
        assertEquals("ds", testCase.getResultNode().get(0).getName());
    }

    private static @NonNull DMNModelRepository makeRepository() {
        return new DMNModelRepository(new TDefinitions());
    }

    private TestCase makeTestCaseForDecision(String id, String inputNodeName, String resultNodeName) {
        TestCase testCase = new TestCase();
        testCase.setId(id);
        testCase.setType(TestCaseType.DECISION);
        testCase.getInputNode().add(makeInputNode(inputNodeName));
        testCase.getResultNode().add(makeResultNode(resultNodeName));
        return testCase;
    }

    private TestCase makeTestCaseForBKM(String id, String invocableName, String inputNodeName, String resultNodeName) {
        TestCase testCase = new TestCase();
        testCase.setId(id);
        testCase.setType(TestCaseType.BKM);
        testCase.setInvocableName(invocableName);
        testCase.getInputNode().add(makeInputNode(inputNodeName));
        testCase.getResultNode().add(makeResultNode(resultNodeName));
        return testCase;
    }

    private TestCase makeTestCaseForDS(String id, String invocableName, String inputNodeName, String resultNodeName) {
        TestCase testCase = new TestCase();
        testCase.setId(id);
        testCase.setType(TestCaseType.DECISION_SERVICE);
        testCase.setInvocableName(invocableName);
        testCase.getInputNode().add(makeInputNode(inputNodeName));
        testCase.getResultNode().add(makeResultNode(resultNodeName));
        return testCase;
    }

    private TestCase makeTestCaseForDS(String id, String invocableName, String inputNodeName, String resultNodeName1, String resultNodeName2) {
        TestCase testCase = new TestCase();
        testCase.setId(id);
        testCase.setType(TestCaseType.DECISION_SERVICE);
        testCase.setInvocableName(invocableName);
        testCase.getInputNode().add(makeInputNode(inputNodeName));
        testCase.getResultNode().add(makeResultNode(resultNodeName1));
        testCase.getResultNode().add(makeResultNode(resultNodeName2));
        return testCase;
    }

    private static InputNode makeInputNode(String name) {
        InputNode node = new InputNode();
        node.setName(name);
        return node;
    }

    private static ResultNode makeResultNode(String name) {
        ResultNode node = new ResultNode();
        node.setName(name);
        return node;
    }
}