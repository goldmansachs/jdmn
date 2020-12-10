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
package com.gs.dmn.signavio.transformation;

import org.junit.Test;

public class ExportedComplexDMNToJavaTransformerTest extends AbstractSignavioDMNToJavaTest {
    @Override
    protected String getInputPath() {
        return "dmn/complex";
    }

    @Override
    protected String getExpectedPath() {
        return "dmn/dmn2java/expected/complex/dmn";
    }

    @Test
    public void testCompareLists() throws Exception {
        doSingleModelTest("CompareLists");
    }

    @Test
    public void testDotProduct() throws Exception {
        doSingleModelTest("DotProduct");
    }

    @Test
    public void testExampleCreditDecision() throws Exception {
        doSingleModelTest("Example credit decision");
    }

    @Test
    public void testNPEValidation2() throws Exception {
        doSingleModelTest("NPEValidation2");
    }

    @Test
    public void testNullSafeTests() throws Exception {
        doSingleModelTest("Null Safe Tests");
    }

    @Test
    public void testChildLinked() throws Exception {
        doSingleModelTest("ChildLinked");
    }

    @Test
    public void testParentLinked() throws Exception {
        doSingleModelTest("ParentLinked");
    }

    @Test
    public void testDecisionWithAnnotations() throws Exception {
        doSingleModelTest("decision-with-annotations");
    }

    @Test
    public void testZip() throws Exception {
        doSingleModelTest("Test ZIP");
    }

    @Test
    public void testMultiListOutputTopDecision() throws Exception {
        doSingleModelTest("Multi-List Output top decision");
    }

    @Test
    public void testNullsWithZipFunction() throws Exception {
        doSingleModelTest("Nulls with zip function");
    }

    @Test
    public void testComplexMID() throws Exception {
        doSingleModelTest("Complex MID");
    }

    @Test
    public void testExecutionAnalysisTest() throws Exception {
        doSingleModelTest("ExecutionAnalysisTestModel");
    }

    @Test
    public void testBKMLinkedToDecision() throws Exception {
        doSingleModelTest("simple-decision-with-bkm");
    }

    @Test
    public void testBKMfromBKM() throws Exception {
        doSingleModelTest("BKMfromBKM");
    }

    @Test
    public void testBKMwithLiteralExpression() throws Exception {
        doSingleModelTest("BKMwithLiteralExpression");
    }

    @Test
    public void testDecisionWithUserFunction() throws Exception {
        doSingleModelTest("simple-decision-with-user-function");
    }

    @Test
    public void testDecisionWithExternalFunction() throws Exception {
        doSingleModelTest("simple-decision-with-external-function");
    }

    @Test
    public void testDecisionWithExternalFunctionWithComplexTypes() throws Exception {
        doSingleModelTest("ExternalFunctions");
    }

    @Test
    public void testNullComplexTypeAccess() throws Exception {
        doSingleModelTest("NullComplexTypeAccess");
    }

    @Test
    public void testTest() throws Exception {
        doSingleModelTest("TestDecision");
    }
}
