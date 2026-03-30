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
package com.gs.dmn.tck.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestLocationTest {
    @Test
    void testToText() {
        assertEquals("", new TestLocation((String) null, null).toText());
        assertEquals("(testCasesName = 'test case')", new TestLocation("test case", null).toText());
        assertEquals("(testCasesName = 'test case', testCase = '001')", new TestLocation("test case", "001").toText());
    }

    @Test
    void testSerialization() throws Exception {
        TestLocation location = new TestLocation("test case", "001");
        assertEquals("{\"testCasesName\":\"test case\",\"testCasesId\":\"001\"}", new ObjectMapper().writeValueAsString(location));
    }
}