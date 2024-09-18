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

import com.gs.dmn.runtime.Pair;
import org.junit.jupiter.api.Test;

public class CL3TestCasesToPythonJUnitTransformerTest extends AbstractTestCasesToPythonJUnitTransformerTest {
    @Override
    protected String getDMNInputPath() {
        return "tck/%s/cl3/%s/translator";
    }

    @Override
    protected String getTestCasesInputPath() {
        return getDMNInputPath() + "/pure";
    }

    @Override
    protected String getExpectedPath() {
        return "tck/%s/cl3/%s/translator/expected/python/test";
    }

    @Test
    public void testCL3() throws Exception {
        // DMN 1.1
        doSingleModelTest("1.1", "0004-lending", "0004-lending-test-01");
        doSingleModelTest("1.1","0020-vacation-days", "0020-vacation-days-test-01", new Pair<>("generateExtra", "true"), new Pair<>("javaRootPackage", "org.gs"));

        // DMN 1.3
        doSingleModelTest("1.3","0085-decision-services", "0085-decision-services-test-01", new Pair<>("caching", "true"));
    }
}
