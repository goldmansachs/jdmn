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

public class CL3TestCasesToPythonJUnitTransformerTest extends AbstractTestCasesToPythonJUnitTransformerTest {
    @Override
    protected String getDMNInputPath() {
        return "tck/%s/cl3/%s/translator";
    }

    @Override
    protected String getTestCasesInputPath() {
        return getDMNInputPath() + "/standard";
    }

    @Override
    protected String getExpectedPath() {
        return "tck/%s/cl3/%s/translator/expected/python/test";
    }

    @Test
    public void testCL3() throws Exception {
        // DMN 1.1
        doSingleModelTest("1.1","0020-vacation-days", "0020-vacation-days-test-01");
    }
}
