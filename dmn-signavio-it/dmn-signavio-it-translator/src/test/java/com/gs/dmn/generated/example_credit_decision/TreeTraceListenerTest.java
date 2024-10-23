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
package com.gs.dmn.generated.example_credit_decision;

import com.gs.dmn.generated.example_credit_decision.type.Applicant;
import com.gs.dmn.generated.example_credit_decision.type.ApplicantImpl;
import com.gs.dmn.runtime.Assert;
import com.gs.dmn.runtime.ExecutionContextBuilder;
import com.gs.dmn.runtime.listener.TreeTraceEventListener;
import com.gs.dmn.runtime.listener.node.DRGElementNode;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

public class TreeTraceListenerTest extends AbstractTraceListenerTest {
    private final GenerateOutputData decision = new GenerateOutputData();

    @Test
    public void testTree() throws Exception {
        TreeTraceEventListener listener = new TreeTraceEventListener();

        List<com.gs.dmn.generated.example_credit_decision.type.GenerateOutputData> expectedResult = decision.asList(new com.gs.dmn.generated.example_credit_decision.type.GenerateOutputDataImpl(decision.number("27.5"), "Accept", decision.numericUnaryMinus(decision.number("7.5"))));
        Number currentRiskAppetite = decision.number("50");
        Number lendingThreshold = decision.number("25");
        Applicant applicant = new ApplicantImpl(decision.number("38"), decision.number("100"), "Amy", decision.asList("Late payment"));
        List<?> actualResult = applyDecision(Applicant.toApplicant(applicant), currentRiskAppetite, lendingThreshold, listener);

        Assert.assertEquals(expectedResult, actualResult);

        DRGElementNode root = listener.getRoot();
        File actualOutputFile = writeNodes(root);
        File expectedOutputFile = new File(resource(getExpectedPath() + "/50-25-tree.json"));
        checkTrace(expectedOutputFile, actualOutputFile);
    }

    @Test
    public void testPreorder() throws Exception {
        TreeTraceEventListener listener = new TreeTraceEventListener();

        List<com.gs.dmn.generated.example_credit_decision.type.GenerateOutputData> expectedResult = decision.asList(new com.gs.dmn.generated.example_credit_decision.type.GenerateOutputDataImpl(decision.number("27.5"), "Accept", decision.numericUnaryMinus(decision.number("7.5"))));
        Number currentRiskAppetite = decision.number("50");
        Number lendingThreshold = decision.number("25");
        Applicant applicant = new ApplicantImpl(decision.number("38"), decision.number("100"), "Amy", decision.asList("Late payment"));
        List<?> actualResult = applyDecision(Applicant.toApplicant(applicant), currentRiskAppetite, lendingThreshold, listener);

        Assert.assertEquals(expectedResult, actualResult);

        List<DRGElementNode> nodes = listener.preorderNodes();
        File actualOutputFile = writeNodes(nodes);
        File expectedOutputFile = new File(resource(getExpectedPath() + "/50-25-tree-preorder.json"));
        checkTrace(expectedOutputFile, actualOutputFile);
    }

    @Test
    public void testPostorder() throws Exception {
        TreeTraceEventListener listener = new TreeTraceEventListener();

        List<com.gs.dmn.generated.example_credit_decision.type.GenerateOutputData> expectedResult = decision.asList(new com.gs.dmn.generated.example_credit_decision.type.GenerateOutputDataImpl(decision.number("27.5"), "Accept", decision.numericUnaryMinus(decision.number("7.5"))));
        Number currentRiskAppetite = decision.number("50");
        Number lendingThreshold = decision.number("25");
        Applicant applicant = new ApplicantImpl(decision.number("38"), decision.number("100"), "Amy", decision.asList("Late payment"));
        List<?> actualResult = applyDecision(Applicant.toApplicant(applicant), currentRiskAppetite, lendingThreshold, listener);

        Assert.assertEquals(expectedResult, actualResult);

        List<DRGElementNode> nodes = listener.postorderNodes();
        File actualOutputFile = writeNodes(nodes);
        File expectedOutputFile = new File(resource(getExpectedPath() + "/50-25-tree-postorder.json"));
        checkTrace(expectedOutputFile, actualOutputFile);
    }

    private List<?> applyDecision(Applicant applicant, Number currentRiskAppetite, Number lendingThreshold, TreeTraceEventListener listener) {
        return decision.apply(applicant, currentRiskAppetite, lendingThreshold, ExecutionContextBuilder.executionContext().withEventListener(listener).build());
    }

    private String getExpectedPath() {
        return "traces/example_credit_decision";
    }
}
