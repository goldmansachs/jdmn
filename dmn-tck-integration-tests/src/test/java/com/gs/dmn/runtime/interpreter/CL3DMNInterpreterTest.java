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

import com.gs.dmn.runtime.Pair;
import org.junit.Test;

import java.util.Arrays;

public abstract class CL3DMNInterpreterTest extends AbstractDMNInterpreterTest {
    @Override
    protected String getDMNInputPath() {
        return "tck/cl3";
    }

    @Test
    public void testCL3() {
        // DMN 1.1 files
        doSingleModelTest("0001-filter");
        doSingleModelTest("0002-string-functions");
        doSingleModelTest("0003-iteration");
        doSingleModelTest("0004-lending");
        doSingleModelTest("0005-literal-invocation");
        doSingleModelTest("0006-join");
        doSingleModelTest("0007-date-time");
        doSingleModelTest("0008-listGen");
        doSingleModelTest("0009-append-flatten");
        doSingleModelTest("0010-concatenate");
        doSingleModelTest("0011-insert-remove");
        doSingleModelTest("0013-sort");
        doSingleModelTest("0014-loan-comparison");
        doSingleModelTest("0016-some-every");
        doSingleModelTest("0017-tableTests");
        doSingleModelTest("0020-vacation-days");
        doSingleModelTest("0021-singleton-list");
        doSingleModelTest("0030-user-defined-functions");
        doSingleModelTest("0031-user-defined-functions");
        doSingleModelTest("0032-conditionals");
        doSingleModelTest("0033-for-loops");
        doSingleModelTest("0034-drg-scopes");
        doSingleModelTest("0035-test-structure-output");
        doSingleModelTest("0036-dt-variable-input");
        doSingleModelTest("0037-dt-on-bkm-implicit-params");
        doSingleModelTest("0038-dt-on-bkm-explicit-params");
        doSingleModelTest("0039-dt-list-semantics");
        doSingleModelTest("0040-singlenestedcontext");
        doSingleModelTest("0041-multiple-nestedcontext");
        doSingleModelTest("1100-feel-decimal-function");
        doSingleModelTest("1101-feel-floor-function");
        doSingleModelTest("1102-feel-ceiling-function");
        doSingleModelTest("1103-feel-substring-function");
        doSingleModelTest("1104-feel-string-length-function");
        doSingleModelTest("1105-feel-upper-case-function");
        doSingleModelTest("1106-feel-lower-case-function");
        doSingleModelTest("1107-feel-substring-before-function");
        doSingleModelTest("1108-feel-substring-after-function");
        doSingleModelTest("1109-feel-replace-function");
        doSingleModelTest("1110-feel-contains-function");
        doSingleModelTest("1115-feel-date-function");
        doSingleModelTest("1116-feel-time-function");
        doSingleModelTest("1117-feel-date-and-time-function");
        doSingleModelTest("1120-feel-duration-function");
        doSingleModelTest("1121-feel-years-and-months-duration-function");

        doSingleModelTest("0030-static-user-defined-functions");
        doSingleModelTest("0031-static-user-defined-functions");
        doSingleModelTest("9001-recursive-function");

        // DMN 1.2 files
        doSingleModelTest("0012-list-functions");
        doSingleModelTest("0034-drg-scopes-2");
        doSingleModelTest("0068-feel-equality");
        doSingleModelTest("0070-feel-instance-of");
        doSingleModelTest("0074-feel-properties");
        doSingleModelTest("0076-feel-external-java");
        doSingleModelTest("0082-feel-coercion");
        doSingleModelTest("0085-decision-services");
        doMultipleModelsTest(Arrays.asList("0086-import", "0086-imported-model"), new Pair<>("singletonInputData", "false"));
        doSingleModelTest("0087-chapter-11-example");
        doMultipleModelsTest(Arrays.asList("0089-nested-inputdata-imports", "0089-model-b", "0089-model-b2", "0089-model-a"), new Pair<>("singletonInputData", "false"));
    }
}
