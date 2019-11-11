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
    @Test
    public void testCompareLists() throws Exception {
        doTest("CompareLists");
    }

    @Test
    public void testDotProduct() throws Exception {
        doTest("DotProduct");
    }

    @Test
    public void testExampleCreditDecision() throws Exception {
        doTest("Example credit decision");
    }

    @Test
    public void testNPEValidation2() throws Exception {
        doTest("NPEValidation2");
    }

    @Test
    public void testNullSafeTests() throws Exception {
        doTest("Null Safe Tests");
    }

    @Test
    public void testChildLinked() throws Exception {
        doTest("ChildLinked");
    }

    @Test
    public void testParentLinked() throws Exception {
        doTest("ParentLinked");
    }

    @Test
    public void testDecisionWithAnnotations() throws Exception {
        doTest("decision-with-annotations");
    }

    @Test
    public void testZip() throws Exception {
        doTest("Test ZIP");
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
    public void testBKMLinkedToDecision() throws Exception {
        doTest("simple-decision-with-bkm");
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
    public void testDecisionWithUserFunction() throws Exception {
        doTest("simple-decision-with-user-function");
    }

    @Test
    public void testDecisionWithExternalFunction() throws Exception {
        doTest("simple-decision-with-external-function");
    }

    @Test
    public void testDecisionWithExternalFunctionWithComplexTypes() throws Exception {
        doTest("ExternalFunctions");
    }

    @Test
    public void testNullComplexTypeAccess() throws Exception {
        doTest("NullComplexTypeAccess");
    }

    @Test
    public void testTest() throws Exception {
        doTest("TestDecision");
    }

    @Override
    protected String getInputPath() {
        return "dmn2java/exported/complex/input";
    }

    @Override
    protected String getExpectedPath() {
        return "dmn2java/exported/complex/expected/dmn";
    }
}
