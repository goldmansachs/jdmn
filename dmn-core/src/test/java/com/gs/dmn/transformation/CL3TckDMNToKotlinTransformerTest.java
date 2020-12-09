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

import org.junit.Test;

public class CL3TckDMNToKotlinTransformerTest extends AbstractTckDMNToKotlinTransformerTest {
    @Override
    protected String getInputPath() {
        return "tck/%s/cl3/%s/translator";
    }

    @Override
    protected String getExpectedPath() {
        return "tck/%s/cl3/%s/translator/expected/kotlin/dmn";
    }

    @Test
    public void testCL3() throws Exception {
        doSingleModelTest("1.1","0004-lending");
        doSingleModelTest("1.1","0005-literal-invocation");
        doSingleModelTest("1.1","0006-join");
        doSingleModelTest("1.1","0013-sort");
        doSingleModelTest("1.1","0014-loan-comparison");
        doSingleModelTest("1.1","0016-some-every");
        doSingleModelTest("1.1","0017-tableTests");
        doSingleModelTest("1.1","0020-vacation-days");
        doSingleModelTest("1.1","0021-singleton-list");
        doSingleModelTest("1.1","0030-user-defined-functions");
        doSingleModelTest("1.1","0031-static-user-defined-functions");

        doSingleModelTest("1.1","9001-recursive-function");
    }
}
