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
package com.gs.dmn.generated.example_credit_decision_mixed_proto;

import com.gs.dmn.generated.example_credit_decision_mixed_proto.proto.GenerateOutputDataResponse;
import com.gs.dmn.runtime.Assert;
import com.gs.dmn.runtime.annotation.AnnotationSet;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class HandwrittenGenerateOutputDataTest {
    protected static final Logger LOGGER = LoggerFactory.getLogger(HandwrittenGenerateOutputDataTest.class);

    private final com.gs.dmn.generated.example_credit_decision_mixed_proto.GenerateOutputData decision = new GenerateOutputData();

    @Test
    public void testCase1() {
        AnnotationSet annotationSet_ = new AnnotationSet();

        // Create GenerateOutputDataRequest
        com.gs.dmn.generated.example_credit_decision_mixed_proto.proto.GenerateOutputDataRequest request = makeRequest();

        // Invoke decision
        GenerateOutputDataResponse response = decision.apply(request, annotationSet_);

        // Check result
        List<com.gs.dmn.generated.example_credit_decision_mixed_proto.type.GenerateOutputData> expected = decision.asList(new com.gs.dmn.generated.example_credit_decision_mixed_proto.type.GenerateOutputDataImpl(decision.number("27.5"), "Accept", decision.numericUnaryMinus(decision.number("7.5"))));
        checkValues(expected, response.getGenerateOutputDataList());
    }

    private com.gs.dmn.generated.example_credit_decision_mixed_proto.proto.Applicant makeApplicant() {
        //Applicant applicant = new ApplicantImpl(decision.number("38"), decision.number("100"), "Amy", decision.asList("Late payment"));
        double age = 38;
        double creditScore = 100;
        String name = "Amy";
        List<String> priorIssues = decision.asList("Late payment");
        com.gs.dmn.generated.example_credit_decision_mixed_proto.proto.Applicant.Builder builder = com.gs.dmn.generated.example_credit_decision_mixed_proto.proto.Applicant.newBuilder();
        builder.setAge(age).setCreditScore(creditScore).setName(name);
        builder.addAllPriorIssues(priorIssues);
        return builder.build();
    }

    private com.gs.dmn.generated.example_credit_decision_mixed_proto.proto.GenerateOutputDataRequest makeRequest() {
        // Make members of request
        com.gs.dmn.generated.example_credit_decision_mixed_proto.proto.Applicant applicant = makeApplicant();
        double currentRiskAppetite = 50;
        double lendingThreshold = 25;

        // Create request
        com.gs.dmn.generated.example_credit_decision_mixed_proto.proto.GenerateOutputDataRequest.Builder builder = com.gs.dmn.generated.example_credit_decision_mixed_proto.proto.GenerateOutputDataRequest.newBuilder();
        builder.setCurrentRiskAppetite(currentRiskAppetite);
        builder.setLendingThreshold(lendingThreshold);
        builder.setApplicant(applicant);

        com.gs.dmn.generated.example_credit_decision_mixed_proto.proto.GenerateOutputDataRequest request = builder.build();
        return request;
    }

    private void checkValues(Object expected, Object actual) {
        Assert.assertEquals(expected, actual);
    }
}