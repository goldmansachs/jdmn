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
package com.gs.dmn.tck;

import org.junit.Test;

public class CL2TestCasesToKotlinJUnitTransformerTest extends AbstractTestCasesToKotlinJUnitTransformerTest {
    @Override
    protected String getDMNInputPath() {
        return "tck/%s/cl2/%s/translator";
    }

    @Override
    protected String getTestCasesInputPath() {
        return getDMNInputPath();
    }

    @Override
    protected String getExpectedPath() {
        return "tck/%s/cl2/%s/translator/expected/kotlin/test";
    }

    @Test
    public void testCL2() throws Exception {
        doSingleModelTest("1.1","0004-simpletable-U", "0004-simpletable-U-test-01");
        doSingleModelTest("1.1","0005-simpletable-A", "0005-simpletable-A-test-01");
        doSingleModelTest("1.1","0006-simpletable-P1", "0006-simpletable-P1-test-01");
        doSingleModelTest("1.1","0007-simpletable-P2", "0007-simpletable-P2-test-01");
        doSingleModelTest("1.1","0008-LX-arithmetic", "0008-LX-arithmetic-test-01");
        doSingleModelTest("1.1","0009-invocation-arithmetic", "0009-invocation-arithmetic-test-01");
        doSingleModelTest("1.1","0010-multi-output-U", "0010-multi-output-U-test-01");
    }
}
