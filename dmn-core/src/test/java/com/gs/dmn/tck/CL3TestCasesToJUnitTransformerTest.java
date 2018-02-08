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
package com.gs.dmn.tck;

import org.junit.Test;

/**
 * Created by Octavian Patrascoiu on 09/06/2017.
 */
public class CL3TestCasesToJUnitTransformerTest extends AbstractTCKTestCasesToJUnitTransformerTest {
    @Override
    protected String getDMNInputPath() {
        return "tck/cl3/input";
    }

    @Override
    protected String getTestCasesInputPath() {
        return getDMNInputPath() + "/standard";
    }

    @Override
    protected String getExpectedPath() {
        return "tck/cl3/expected/test";
    }

    @Test
    public void testCL3() throws Exception {
        doTest("0001-filter", "0001-filter-test-01");
        doTest("0002-string-functions", "0002-string-functions-test-01");
        doTest("0003-iteration", "0003-iteration-test-01");
        doTest("0005-literal-invocation", "0005-literal-invocation-test-01");
        doTest("0004-lending", "0004-lending-test-01");
        doTest("0006-join", "0006-join-test-01");
        doTest("0007-date-time", "0007-date-time-test-01");
        doTest("0008-listGen", "0008-listGen-test-01");
        doTest("0009-append-flatten", "0009-append-flatten-test-01");
        doTest("0010-concatenate", "0010-concatenate-test-01");
        doTest("0011-insert-remove", "0011-insert-remove-test-01");
        doTest("0012-list-functions", "0012-list-functions-test-01");
        doTest("0013-sort", "0013-sort-test-01");
        doTest("0014-loan-comparison", "0014-loan-comparison-test-01");
        doTest("0016-some-every", "0016-some-every-test-01");
        doTest("0017-tableTests", "0017-tableTests-test-01");
        doTest("0020-vacation-days", "0020-vacation-days-test-01");
        doTest("0021-singleton-list", "0021-singleton-list-test-01");
        doTest("0030-user-defined-functions", "0030-user-defined-functions-test-01");
        doTest("0031-user-defined-functions", "0031-user-defined-functions-test-01");
        doTest("0032-conditionals", "0032-conditionals-test-01");
        doTest("0033-for-loops", "0033-for-loops-test-01");
        doTest("1100-feel-decimal-function", "1100-feel-decimal-function-test-01");
        doTest("1101-feel-floor-function", "1101-feel-floor-function-test-01");
        doTest("1102-feel-ceiling-function", "1102-feel-ceiling-function-test-01");
        doTest("1103-feel-substring-function", "1103-feel-substring-function-test-01");
        doTest("1104-feel-string-length-function", "1104-feel-string-length-function-test-01");
        doTest("1105-feel-upper-case-function", "1105-feel-upper-case-function-test-01");
        doTest("1106-feel-lower-case-function", "1106-feel-lower-case-function-test-01");
        doTest("1107-feel-substring-before-function", "1107-feel-substring-before-function-test-01");
        doTest("1108-feel-substring-after-function", "1108-feel-substring-after-function-test-01");
        doTest("1109-feel-replace-function", "1109-feel-replace-function-test-01");
        doTest("1110-feel-contains-function", "1110-feel-contains-function-test-01");

        doTest("9001-recursive-function", "9001-recursive-function-test-01");
    }
}
