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
        doSimpleModelTest("CompareLists");
    }

    @Test
    public void testDotProduct() throws Exception {
        doSimpleModelTest("DotProduct");
    }

    @Test
    public void testExampleCreditDecision() throws Exception {
        doSimpleModelTest("Example credit decision");
    }

    @Test
    public void testNPEValidation2() throws Exception {
        doSimpleModelTest("NPEValidation2");
    }

    @Test
    public void testNullSafeTests() throws Exception {
        doSimpleModelTest("Null Safe Tests");
    }

    @Test
    public void testChildLinked() throws Exception {
        doSimpleModelTest("ChildLinked");
    }

    @Test
    public void testParentLinked() throws Exception {
        doSimpleModelTest("ParentLinked");
    }

    @Test
    public void testDecisionWithAnnotations() throws Exception {
        doSimpleModelTest("decision-with-annotations");
    }

    @Test
    public void testZip() throws Exception {
        doSimpleModelTest("Test ZIP");
    }

    @Test
    public void testMultiListOutputTopDecision() throws Exception {
        doSimpleModelTest("Multi-List Output top decision");
    }

    @Test
    public void testNullsWithZipFunction() throws Exception {
        doSimpleModelTest("Nulls with zip function");
    }

    @Test
    public void testComplexMID() throws Exception {
        doSimpleModelTest("Complex MID");
    }

    @Test
    public void testExecutionAnalysisTest() throws Exception {
        doSimpleModelTest("ExecutionAnalysisTestModel");
    }

    @Test
    public void testBKMLinkedToDecision() throws Exception {
        doSimpleModelTest("simple-decision-with-bkm");
    }

    @Test
    public void testBKMfromBKM() throws Exception {
        doSimpleModelTest("BKMfromBKM");
    }

    @Test
    public void testBKMwithLiteralExpression() throws Exception {
        doSimpleModelTest("BKMwithLiteralExpression");
    }

    @Test
    public void testDecisionWithUserFunction() throws Exception {
        doSimpleModelTest("simple-decision-with-user-function");
    }

    @Test
    public void testDecisionWithExternalFunction() throws Exception {
        doSimpleModelTest("simple-decision-with-external-function");
    }

    @Test
    public void testDecisionWithExternalFunctionWithComplexTypes() throws Exception {
        doSimpleModelTest("ExternalFunctions");
    }

    @Test
    public void testNullComplexTypeAccess() throws Exception {
        doSimpleModelTest("NullComplexTypeAccess");
    }

    @Test
    public void testTest() throws Exception {
        doSimpleModelTest("TestDecision");
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
