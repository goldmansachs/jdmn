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

import org.junit.Test;

import java.util.Map;

public class TestLabToJavaProtoTransformerTest extends AbstractTestLabToJavaJUnitTransformerTest {
    @Override
    protected String getInputPath() {
        return "dmn/proto";
    }

    @Override
    protected String getExpectedPath() {
        return "dmn/dmn2java/expected/proto/proto3/java/test-lab";
    }

    @Test
    public void testExampleCreditDecision() throws Exception {
        doTest("Example credit decision");
    }

    @Test
    public void testDateTime() throws Exception {
        doTest("date-time-proto");
    }

    @Override
    protected Map<String, String> makeInputParametersMap() {
        Map<String, String> inputParams = super.makeInputParametersMap();
        inputParams.put("generateProtoMessages", "true");
        inputParams.put("generateProtoServices", "true");
        inputParams.put("protoVersion", "proto3");
        return inputParams;
    }
}