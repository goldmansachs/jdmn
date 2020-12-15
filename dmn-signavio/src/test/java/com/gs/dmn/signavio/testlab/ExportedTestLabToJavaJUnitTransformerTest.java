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
package com.gs.dmn.signavio.testlab;

import org.junit.Test;

public class ExportedTestLabToJavaJUnitTransformerTest extends AbstractTestLabToJavaJUnitTransformerTest {
    @Override
    protected String getInputPath() {
        return "dmn/complex";
    }

    @Override
    protected String getExpectedPath() {
        return "dmn/dmn2java/expected/complex/test-lab";
    }

    @Test
    public void testNullSafeTests() throws Exception {
        doTest("Null Safe Tests");
    }

    @Test
    public void testCompareLists() throws Exception {
        doTest("CompareLists");
    }

    @Test
    public void testNPEValidation2() throws Exception {
        doTest("NPEValidation2");
    }

    @Test
    public void testExampleCreditDecision() throws Exception {
        doTest("Example credit decision");
    }

    @Test
    public void testSimpleModel() throws Exception {
        doTest("Simple model");
    }

    @Test
    public void testSimpleMID() throws Exception {
        doTest("Simple MID");
    }

    @Test
    public void testSimpleMIDWithProperIDs() throws Exception {
        doTest("Simple MID Legacy");
    }

    @Test
    public void testMultiListOutputTopDecision() throws Exception {
        doTest("Multi-List Output top decision");
    }

    @Test
    public void testNullsWithZipFunction() throws Exception {
        doTest("Nulls with zip function");
    }

    @Test
    public void testComplexMID() throws Exception {
        doTest("Complex MID");
    }

    @Test
    public void testExecutionAnalysisTest() throws Exception {
        doTest("ExecutionAnalysisTestModel");
    }

    @Test
    public void testBKMfromBKM() throws Exception {
        doTest("BKMfromBKM");
    }

    @Test
    public void testBKMwithLiteralExpression() throws Exception {
        doTest("BKMwithLiteralExpression");
    }

    @Test
    public void testTest() throws Exception {
        doTest("TestDecision");
    }
}