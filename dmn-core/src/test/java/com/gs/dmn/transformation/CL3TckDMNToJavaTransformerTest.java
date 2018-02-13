/**
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

public class CL3TckDMNToJavaTransformerTest extends AbstractTckDMNToJavaTransformerTest {
    @Override
    protected String getInputPath() {
        return "tck/cl3/input";
    }

    @Override
    protected String getExpectedPath() {
        return "tck/cl3/expected/dmn";
    }

    @Test
    public void testCL3() throws Exception {
        doTest("0001-filter");
        doTest("0002-string-functions");
        doTest("0003-iteration");
        doTest("0005-literal-invocation");
        doTest("0004-lending");
        doTest("0006-join");
        doTest("0007-date-time");
        doTest("0008-listGen");
        doTest("0009-append-flatten");
        doTest("0010-concatenate");
        doTest("0011-insert-remove");
        doTest("0012-list-functions");
        doTest("0013-sort");
        doTest("0014-loan-comparison");
        doTest("0016-some-every");
        doTest("0017-tableTests");
        doTest("0020-vacation-days");
        doTest("0021-singleton-list");
        doTest("0030-user-defined-functions");
        doTest("0031-user-defined-functions");
        doTest("0032-conditionals");
        doTest("0033-for-loops");
        doTest("1100-feel-decimal-function");
        doTest("1101-feel-floor-function");
        doTest("1102-feel-ceiling-function");
        doTest("1103-feel-substring-function");
        doTest("1104-feel-string-length-function");
        doTest("1105-feel-upper-case-function");
        doTest("1106-feel-lower-case-function");
        doTest("1107-feel-substring-before-function");
        doTest("1108-feel-substring-after-function");
        doTest("1109-feel-replace-function");
        doTest("1110-feel-contains-function");

        doTest("9001-recursive-function");
    }
}
