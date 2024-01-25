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

import com.gs.dmn.runtime.Pair;
import org.junit.jupiter.api.Test;

public class CL3TckDMNToJavaTransformerTest extends AbstractTckDMNToJavaTransformerTest {
    @Override
    protected String getInputPath() {
        return "tck/%s/cl3/%s/translator";
    }

    @Override
    protected String getExpectedPath() {
        return "tck/%s/cl3/%s/translator/expected/java/dmn";
    }

    @Test
    public void testCL3() throws Exception {
        // DMN 1.1
        doSingleModelTest("1.1", "0004-lending");
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

        // DMN 1.2
        doSingleModelTest("1.2","0076-feel-external-java");

        // DMN 1.3
        doSingleModelTest("1.3","0085-decision-services", new Pair<>("caching", "true"));
        doSingleModelTest("1.3","0092-feel-lambda");

        // extensions
        doSingleModelTest("1.1","9001-recursive-function");
    }

    @Test
    public void testCL3Singleton() throws Exception {
        doSingleModelTest("1.3", "0004-lending", new Pair<>("singletonDecision", "true"));
    }
}
