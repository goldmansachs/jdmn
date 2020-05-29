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
package com.gs.dmn.runtime.interpreter;

import org.junit.Test;

public abstract class CL2SDMNInterpreterTest<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends AbstractDMNInterpreterTest<NUMBER, DATE, TIME, DATE_TIME, DURATION> {
    @Override
    protected String getDMNInputPath() {
        return "tck/cl2";
    }

    @Override
    protected String getTestCasesInputPath() {
        return getDMNInputPath();
    }

    @Test
    public void testCL2() {
        doSingleModelTest("0001-input-data-string");
        doSingleModelTest("0002-input-data-number");
        doSingleModelTest("0003-input-data-string-allowed-values");
        doSingleModelTest("0004-simpletable-U");
        doSingleModelTest("0005-simpletable-A");
        doSingleModelTest("0006-simpletable-P1");
        doSingleModelTest("0007-simpletable-P2");
        doSingleModelTest("0008-LX-arithmetic");
        doSingleModelTest("0009-invocation-arithmetic");
        doSingleModelTest("0010-multi-output-U");
        doSingleModelTest("0100-feel-constants");
        doSingleModelTest("0101-feel-constants");
        doSingleModelTest("0102-feel-constants");
        doSingleModelTest("0105-feel-math");
        doSingleModelTest("0106-feel-ternary-logic");
        doSingleModelTest("0107-feel-ternary-logic-not");
        doSingleModelTest("0108-first-hitpolicy");
        doSingleModelTest("0109-ruleOrder-hitpolicy");
        doSingleModelTest("0110-outputOrder-hitpolicy");
        doSingleModelTest("0111-first-hitpolicy-singleoutputcol");
        doSingleModelTest("0112-ruleOrder-hitpolicy-singleinoutcol");
        doSingleModelTest("0113-outputOrder-hitpolicy-singleinoutcol");
        doSingleModelTest("0114-min-collect-hitpolicy");
        doSingleModelTest("0115-sum-collect-hitpolicy");
        doSingleModelTest("0116-count-collect-hitpolicy");
        doSingleModelTest("0117-multi-any-hitpolicy");
        doSingleModelTest("0118-multi-priority-hitpolicy");
        doSingleModelTest("0119-multi-collect-hitpolicy");
    }
}
