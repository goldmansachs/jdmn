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

import com.gs.dmn.tck.ast.TestCases;
import org.junit.jupiter.api.Test;

import java.util.Collections;

class DRGElementVariableNameTransformerTest extends SimpleDMNTransformerTest {
    @Test
    public void testTransform() throws Exception {
        doTest("1.1", Collections.singletonList("test-dmn-with-incorrect-variable.dmn"));
    }

    @Override
    protected DMNTransformer<TestCases> getTransformer() {
        return new DRGElementVariableNameTransformer<>();
    }

    @Override
    protected String getInputPath() {
        return "dmn/input/";
    }

    @Override
    protected  String getTargetPath() {
        return "target/incorrect-variable/";
    }

    @Override
    protected  String getExpectedPath() {
        return "dmn/expected/incorrect-variable/";
    }
}