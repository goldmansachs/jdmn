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
package com.gs.dmn.runtime.interpreter;

import org.junit.Test;

public abstract class CL3DMNInterpreterTest extends AbstractDMNInterpreterTest {
    @Override
    protected String getDMNInputPath() {
        return "tck/cl3";
    }

    @Test
    public void testCL3() {
        doTestDiagram("0001-filter");
        doTestDiagram("0002-string-functions");
        doTestDiagram("0003-iteration");
        doTestDiagram("0004-lending");
        doTestDiagram("0005-literal-invocation");
        doTestDiagram("0006-join");
        doTestDiagram("0007-date-time");
        doTestDiagram("0008-listGen");
        doTestDiagram("0009-append-flatten");
        doTestDiagram("0010-concatenate");
        doTestDiagram("0011-insert-remove");
        doTestDiagram("0012-list-functions");
        doTestDiagram("0013-sort");
        doTestDiagram("0014-loan-comparison");
        doTestDiagram("0016-some-every");
        doTestDiagram("0017-tableTests");
        doTestDiagram("0020-vacation-days");
        doTestDiagram("0021-singleton-list");
        doTestDiagram("0030-user-defined-functions");
        doTestDiagram("0031-user-defined-functions");
        doTestDiagram("0032-conditionals");
        doTestDiagram("0033-for-loops");
        doTestDiagram("0034-drg-scopes");
        doTestDiagram("0035-test-structure-output");
        doTestDiagram("0036-dt-variable-input");
        doTestDiagram("0037-dt-on-bkm-implicit-params");
        doTestDiagram("0038-dt-on-bkm-explicit-params");
        doTestDiagram("0039-dt-list-semantics");
        doTestDiagram("0040-singlenestedcontext");
        doTestDiagram("0041-multiple-nestedcontext");
        doTestDiagram("1100-feel-decimal-function");
        doTestDiagram("1101-feel-floor-function");
        doTestDiagram("1102-feel-ceiling-function");
        doTestDiagram("1103-feel-substring-function");
        doTestDiagram("1104-feel-string-length-function");
        doTestDiagram("1105-feel-upper-case-function");
        doTestDiagram("1106-feel-lower-case-function");
        doTestDiagram("1107-feel-substring-before-function");
        doTestDiagram("1108-feel-substring-after-function");
        doTestDiagram("1109-feel-replace-function");
        doTestDiagram("1110-feel-contains-function");
        doTestDiagram("1115-feel-date-function");
        doTestDiagram("1116-feel-time-function");
        doTestDiagram("1117-feel-date-and-time-function");
        doTestDiagram("1120-feel-duration-function");
        doTestDiagram("1121-feel-years-and-months-duration-function");

        doTestDiagram("0030-static-user-defined-functions");
        doTestDiagram("0031-static-user-defined-functions");
        doTestDiagram("9001-recursive-function");
    }
}
