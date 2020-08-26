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
import com.gs.dmn.runtime.annotation.AnnotationSet;
import com.gs.dmn.runtime.cache.DefaultCache;
import com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor;
import com.gs.dmn.runtime.listener.PostorderTraceEventListener;
import com.gs.dmn.runtime.listener.node.DRGElementNode;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class PostorderTraceListenerTest extends AbstractTraceListenerTest {
    private final GenerateOutputData decision = new GenerateOutputData();

    @Test
    public void testListener() throws Exception {
        AnnotationSet annotationSet = new AnnotationSet();
        PostorderTraceEventListener listener = new PostorderTraceEventListener();

        List<com.gs.dmn.generated.example_credit_decision.type.GenerateOutputData> expectedResult = decision.asList(new com.gs.dmn.generated.example_credit_decision.type.GenerateOutputDataImpl(decision.number("27.5"), "Accept", decision.numericUnaryMinus(decision.number("7.5"))));
        java.math.BigDecimal currentRiskAppetite = decision.number("50");
        java.math.BigDecimal lendingThreshold = decision.number("25");
        Applicant applicant = new ApplicantImpl(decision.number("38"), decision.number("100"), "Amy", decision.asList("Late payment"));
        List<?> actualResult = decision.apply(Applicant.toApplicant(applicant), currentRiskAppetite, lendingThreshold, annotationSet, listener, new DefaultExternalFunctionExecutor(), new DefaultCache());

        Assert.assertEquals(expectedResult, actualResult);

        List<DRGElementNode> elementTraces = listener.postorderNodes();
        File actualOutputFile = writeNodes(elementTraces);
        File expectedOutputFile = new File(resource(getExpectedPath() + "/50-25-postorder.json"));
        checkTrace(expectedOutputFile, actualOutputFile);
    }

    @Test
    public void testListenerWithFilter() throws Exception {
        AnnotationSet annotationSet = new AnnotationSet();
        PostorderTraceEventListener listener = new PostorderTraceEventListener(Arrays.asList("Make credit decision"));

        List<com.gs.dmn.generated.example_credit_decision.type.GenerateOutputData> expectedResult = decision.asList(new com.gs.dmn.generated.example_credit_decision.type.GenerateOutputDataImpl(decision.number("27.5"), "Accept", decision.numericUnaryMinus(decision.number("7.5"))));
        java.math.BigDecimal currentRiskAppetite = decision.number("50");
        java.math.BigDecimal lendingThreshold = decision.number("25");
        Applicant applicant = new ApplicantImpl(decision.number("38"), decision.number("100"), "Amy", decision.asList("Late payment"));
        List<?> actualResult = decision.apply(Applicant.toApplicant(applicant), currentRiskAppetite, lendingThreshold, annotationSet, listener, new DefaultExternalFunctionExecutor(), new DefaultCache());

        Assert.assertEquals(expectedResult, actualResult);

        List<DRGElementNode> elementTraces = listener.postorderNodes();
        File actualOutputFile = writeNodes(elementTraces);
        File expectedOutputFile = new File(resource(getExpectedPath() + "/50-25-postorder-with-filter.json"));
        checkTrace(expectedOutputFile, actualOutputFile);
    }

    private String getExpectedPath() {
        return "traces/example_credit_decision";
    }
}
