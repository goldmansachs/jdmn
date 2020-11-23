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
    public void test_11_cl2_0001_input_data_string() {
        doSingleModelTest("0001-input-data-string");
    }

    @Test
    public void test_11_cl2_0002_input_data_number() {
        doSingleModelTest("0002-input-data-number");
    }

    @Test
    public void test_11_cl2_0003_input_data_string_allowed_values() {
        doSingleModelTest("0003-input-data-string-allowed-values");
    }

    @Test
    public void test_11_cl2_0004_simpletable_U() {
        doSingleModelTest("0004-simpletable-U");
    }

    @Test
    public void test_11_cl2_0005_simpletable_A() {
        doSingleModelTest("0005-simpletable-A");
    }

    @Test
    public void test_11_cl2_0006_simpletable_P1() {
        doSingleModelTest("0006-simpletable-P1");
    }

    @Test
    public void test_11_cl2_0007_simpletable_P2() {
        doSingleModelTest("0007-simpletable-P2");
    }

    @Test
    public void test_11_cl2_0008_LX_arithmetic() {
        doSingleModelTest("0008-LX-arithmetic");
    }

    @Test
    public void test_11_cl2_0009_invocation_arithmetic() {
        doSingleModelTest("0009-invocation-arithmetic");
    }

    @Test
    public void test_11_cl2_0010_multi_output_U() {
        doSingleModelTest("0010-multi-output-U");
    }

    @Test
    public void test_11_cl2_0100_feel_constants() {
        doSingleModelTest("0100-feel-constants");
    }

    @Test
    public void test_11_cl2_0101_feel_constants() {
        doSingleModelTest("0101-feel-constants");
    }

    @Test
    public void test_11_cl2_0102_feel_constants() {
        doSingleModelTest("0102-feel-constants");
    }

    @Test
    public void test_11_cl2_0105_feel_math() {
        doSingleModelTest("0105-feel-math");
    }

    @Test
    public void test_11_cl2_0106_feel_ternary_logic() {
        doSingleModelTest("0106-feel-ternary-logic");
    }

    @Test
    public void test_11_cl2_0107_feel_ternary_logic_not() {
        doSingleModelTest("0107-feel-ternary-logic-not");
    }

    @Test
    public void test_11_cl2_0108_first_hitpolicy() {
        doSingleModelTest("0108-first-hitpolicy");
    }

    @Test
    public void test_11_cl2_0109_ruleOrder_hitpolicy() {
        doSingleModelTest("0109-ruleOrder-hitpolicy");
    }

    @Test
    public void test_11_cl2_0110_outputOrder_hitpolicy() {
        doSingleModelTest("0110-outputOrder-hitpolicy");
    }

    @Test
    public void test_11_cl2_0111_first_hitpolicy_singleoutputcol() {
        doSingleModelTest("0111-first-hitpolicy-singleoutputcol");
    }

    @Test
    public void test_11_cl2_0112_ruleOrder_hitpolicy_singleinoutcol() {
        doSingleModelTest("0112-ruleOrder-hitpolicy-singleinoutcol");
    }

    @Test
    public void test_11_cl2_0113_outputOrder_hitpolicy_singleinoutcol() {
        doSingleModelTest("0113-outputOrder-hitpolicy-singleinoutcol");
    }

    @Test
    public void test_11_cl2_0114_min_collect_hitpolicy() {
        doSingleModelTest("0114-min-collect-hitpolicy");
    }

    @Test
    public void test_11_cl2_0115_sum_collect_hitpolicy() {
        doSingleModelTest("0115-sum-collect-hitpolicy");
    }

    @Test
    public void test_11_cl2_0116_count_collect_hitpolicy() {
        doSingleModelTest("0116-count-collect-hitpolicy");
    }

    @Test
    public void test_11_cl2_0117_multi_any_hitpolicy() {
        doSingleModelTest("0117-multi-any-hitpolicy");
    }

    @Test
    public void test_11_cl2_0118_multi_priority_hitpolicy() {
        doSingleModelTest("0118-multi-priority-hitpolicy");
    }

    @Test
    public void test_11_cl2_0119_multi_collect_hitpolicy() {
        doSingleModelTest("0119-multi-collect-hitpolicy");
    }
}
