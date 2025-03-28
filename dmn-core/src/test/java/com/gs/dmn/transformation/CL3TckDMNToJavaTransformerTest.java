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
        doFolderTest("1.1", "0004-lending");
        doFolderTest("1.1", "0005-literal-invocation");
        doFolderTest("1.1", "0006-join");
        doFolderTest("1.1", "0013-sort");
        doFolderTest("1.1", "0014-loan-comparison");
        doFolderTest("1.1", "0016-some-every");
        doFolderTest("1.1", "0017-tableTests");
        doFolderTest("1.1", "0020-vacation-days");
        doFolderTest("1.1", "0021-singleton-list");
        doFolderTest("1.1", "0030-user-defined-functions");
        doFolderTest("1.1", "0031-user-defined-functions");
        doFolderTest("1.1", "0031-static-user-defined-functions");

        // DMN 1.2
        doFolderTest("1.2", "0076-feel-external-java");

        // DMN 1.3
        doFolderTest("1.3", "0085-decision-services", new Pair<>("caching", "true"));
        doFolderTest("1.3", "0092-feel-lambda");
        doFolderTest("1.3", "0083-feel-unicode");

        // DMN 1.5
        doFolderTest("1.5","1157-implicit-conversions");

        // extensions
        doFolderTest("1.1", "9001-recursive-function");
    }

    @Test
    public void testCL3Singleton() throws Exception {
        doFolderTest("1.3", "0004-lending", new Pair<>("singletonDecision", "true"));
    }
}
