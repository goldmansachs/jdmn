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

import com.gs.dmn.AbstractTest;
import com.gs.dmn.runtime.DMNRuntimeException;
import org.junit.Test;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestLabReaderTest extends AbstractTest {
    private final TestLabReader reader = new TestLabReader();

    @Test
    public void testRead() throws Exception {
        String path = "rdf/rdf2java/expected/dmn/decision-table/" + "simple-decision-primitive-type-inputs-feel-input-entries-single-output-first-hit-policy.json";
        URI resource = signavioResource(path);
        File inputFile = new File(resource);
        TestLab testLab = reader.read(inputFile);

        List<InputParameterDefinition> inputParameterDefinitions = testLab.getInputParameterDefinitions();
        List<OutputParameterDefinition> outputParameterDefinitions = testLab.getOutputParameterDefinitions();
        List<TestCase> testCases = testLab.getTestCases();
        assertEquals(7, inputParameterDefinitions.size());
        inputParameterDefinitions.sort(Comparator.comparing(p -> (String)p.getId()));
        InputParameterDefinition inputParameterDefinition = inputParameterDefinitions.get(0);
        assertEquals(
                "InputParameterDefinition(e3ea989c6b0b4b05950b7d24ade1b624/sid-3247220B-9F67-48DB-8CB5-33C5FCDCEC20, simple-decision-primitive-type-inputs-feel-input-entries-single-output-first-hit-policy, EnumerationInput)",
                inputParameterDefinition.toString());
        assertEquals(1, outputParameterDefinitions.size());
        outputParameterDefinitions.sort(Comparator.comparing(p -> (String)p.getId()));
        OutputParameterDefinition outputParameterDefinition = outputParameterDefinitions.get(0);
        assertEquals("OutputParameterDefinition(e3ea989c6b0b4b05950b7d24ade1b624/sid-4A7C793A-882C-4867-94B9-AD88D6D6970D, simple-decision-primitive-type-inputs-feel-input-entries-single-output-first-hit-policy, Decision)",
                outputParameterDefinition.toString());
        assertEquals(2, testCases.size());
        testCases.sort(Comparator.comparing(TestCase::toString));
        TestCase testCase = testCases.get(0);
        List<String> inputValues = testCase.getInputValues().stream().map(Object::toString).collect(Collectors.toList());
        assertTrue(inputValues.contains("TimeLiteral(null)"));
        List<String> expectedValues = testCase.getExpectedValues().stream().map(Object::toString).collect(Collectors.toList());
        assertTrue(expectedValues.contains("StringLiteral(r7)"));
    }
}