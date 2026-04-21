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
package com.gs.dmn.runtime.compiler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gs.dmn.runtime.DMNRuntimeException;
import org.junit.jupiter.api.Test;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestFailureTest {
    @Test
    void testFromMethod() {
        // Test when null
        assertThrows(DMNRuntimeException.class, () -> TestFailure.from(null));

        // Test when empty
        TestFailure from = TestFailure.from(new TestExecutionSummary.Failure() {
            @Override
            public TestIdentifier getTestIdentifier() {
                return null;
            }

            @Override
            public Throwable getException() {
                return null;
            }
        });
        assertEquals("", from.getClassName());
        assertEquals("", from.getMethodName());
        assertEquals("", from.getMessage());
        assertEquals("", from.getTestCasesName());
        assertEquals("", from.getTestCaseId());
    }

    @Test
    void testSerialization() throws Exception {
        TestFailure failure = new TestFailure("className", "methodName", "message", "testCaseName", "testCaseId");
        String expectedJson = "{\"testCasesName\":\"testCaseName\",\"testCaseId\":\"testCaseId\",\"className\":\"className\",\"methodName\":\"methodName\",\"message\":\"message\"}";
        assertEquals(expectedJson, new ObjectMapper().writeValueAsString(failure));
    }
}