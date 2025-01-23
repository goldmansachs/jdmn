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

public class CL3TckDMNToPythonTransformerTest extends AbstractTckDMNToPythonTransformerTest {
    @Override
    protected String getInputPath() {
        return "tck/%s/cl3/%s/translator";
    }

    @Override
    protected String getExpectedPath() {
        return "tck/%s/cl3/%s/translator/expected/python/dmn";
    }

    @Test
    public void testCL3() throws Exception {
        // DMN 1.1
        doFolderTest("1.1", "0004-lending");
        doFolderTest("1.1", "0020-vacation-days", new Pair<>("javaRootPackage", "org.gs"), new Pair<>("generateExtra", "true"));

        // DMN 1.3
        doFolderTest("1.3", "0085-decision-services", new Pair<>("caching", "true"));
    }
}
