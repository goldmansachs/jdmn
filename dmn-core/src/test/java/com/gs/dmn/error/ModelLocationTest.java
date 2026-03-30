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
package com.gs.dmn.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ModelLocationTest {
    @Test
    void testToText() {
        assertEquals("", new ModelLocation(null, null, null, null, null).toText());
        assertEquals("(modelName = 'modelName', modelId = 'modelId')", new ModelLocation(null, "modelName", "modelId", null, null).toText());
        assertEquals("(namespace = 'ns', modelName = 'modelName', modelId = 'modelId', elementName = 'elementName', elementId = 'elementName')", new ModelLocation("ns", "modelName", "modelId", "elementName", "elementName").toText());
    }

    @Test
    void testSerialization() throws Exception {
        ModelLocation modelLocation = new ModelLocation("ns", "modelName", "modelId", "elementName", "elementName");
        assertEquals("{\"namespace\":\"ns\",\"modelName\":\"modelName\",\"modelId\":\"modelId\",\"elementName\":\"elementName\",\"elementId\":\"elementName\"}", new ObjectMapper().writeValueAsString(modelLocation));
    }
}