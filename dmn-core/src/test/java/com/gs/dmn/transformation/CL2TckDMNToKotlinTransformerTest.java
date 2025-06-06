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

import org.junit.jupiter.api.Test;

public class CL2TckDMNToKotlinTransformerTest extends AbstractTckDMNToKotlinTransformerTest {
    @Override
    protected String getInputPath() {
        return "tck/%s/cl2/%s/translator";
    }

    @Override
    protected String getExpectedPath() {
        return "tck/%s/cl2/%s/translator/expected/kotlin/dmn";
    }

    @Test
    public void testCL2() throws Exception {
        doFolderTest("1.1", "0004-simpletable-U");
        doFolderTest("1.1", "0005-simpletable-A");
        doFolderTest("1.1", "0006-simpletable-P1");
        doFolderTest("1.1", "0007-simpletable-P2");
        doFolderTest("1.1", "0008-LX-arithmetic");
        doFolderTest("1.1", "0009-invocation-arithmetic");
        doFolderTest("1.1", "0010-multi-output-U");
    }
}
