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

public class TestLabToKotlinProtoTransformerTest extends AbstractTestLabToKotlinJUnitTransformerTest {
    @Test
    public void testExampleCreditDecision() throws Exception {
        doTest("Example credit decision");
    }

    @Test
    public void testDateTime() throws Exception {
        doTest("date-time-proto");
    }

    @Override
    protected String getInputPath() {
        return "dmn2java/exported/proto/input";
    }

    @Override
    protected String getExpectedPath() {
        return "dmn2java/exported/proto/expected/proto3/kotlin/test-lab";
    }

    @Override
    protected Map<String, String> makeInputParameters() {
        Map<String, String> inputParams = super.makeInputParameters();
        inputParams.put("generateProtoMessages", "true");
        inputParams.put("generateProtoServices", "true");
        inputParams.put("protoVersion", "proto3");
        return inputParams;
    }
}