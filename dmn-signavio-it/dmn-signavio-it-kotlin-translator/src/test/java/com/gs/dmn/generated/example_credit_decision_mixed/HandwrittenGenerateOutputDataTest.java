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
package com.gs.dmn.generated.example_credit_decision_mixed;

import com.gs.dmn.generated.AbstractHandwrittenDecisionTest;
import com.gs.dmn.generated.example_credit_decision_mixed.type.Applicant;
import com.gs.dmn.generated.example_credit_decision_mixed.type.ApplicantImpl;
import com.gs.dmn.runtime.Assert;
import com.gs.dmn.runtime.annotation.AnnotationSet;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class HandwrittenGenerateOutputDataTest extends AbstractHandwrittenDecisionTest {
    private final GenerateOutputData decision = new GenerateOutputData();

    @Test
    public void testCase1() {
        AnnotationSet annotationSet_ = new AnnotationSet();
        BigDecimal currentRiskAppetite = decision.number("50");
        BigDecimal lendingThreshold = decision.number("25");
        Applicant applicant = new ApplicantImpl(decision.number("38"), decision.number("100"), "Amy", decision.asList("Late payment"));
        List<?> generateOutputDataOutput = decision.apply(Applicant.toApplicant(applicant), currentRiskAppetite, lendingThreshold, annotationSet_);

        List<com.gs.dmn.generated.example_credit_decision_mixed.type.GenerateOutputData> expected = decision.asList(new com.gs.dmn.generated.example_credit_decision_mixed.type.GenerateOutputDataImpl(decision.number("27.5"), "Accept", decision.numericUnaryMinus(decision.number("7.5"))));
        checkValues(expected, generateOutputDataOutput);
    }

    @Test
    public void testCase2() {
        AnnotationSet annotationSet_ = new AnnotationSet();
        BigDecimal currentRiskAppetite = decision.number("50");
        BigDecimal lendingThreshold = decision.number("25");
        Applicant applicant = new ApplicantImpl(decision.number("18"), decision.number("65"), "Bill", decision.asList("Card rejection", "Default on obligations"));
        List<?> output = decision.apply(Applicant.toApplicant(applicant), currentRiskAppetite, lendingThreshold, annotationSet_);

        List<com.gs.dmn.generated.example_credit_decision_mixed.type.GenerateOutputData> expected = decision.asList(new com.gs.dmn.generated.example_credit_decision_mixed.type.GenerateOutputDataImpl(decision.numericUnaryMinus(decision.number("10")), "Reject", decision.numericUnaryMinus(decision.number("25"))));
        checkValues(expected, output);
    }

    @Test
    public void testCase3() {
        AnnotationSet annotationSet_ = new AnnotationSet();
        BigDecimal currentRiskAppetite = decision.number("50");
        BigDecimal lendingThreshold = decision.number("25");
        Applicant applicant = new ApplicantImpl(decision.number("65"), decision.number("80"), "Charlie", decision.asList("Late payment", "Default on obligations", "Bankruptcy"));
        List<?> generateOutputDataOutput = decision.apply(Applicant.toApplicant(applicant), currentRiskAppetite, lendingThreshold, annotationSet_);

        List<com.gs.dmn.generated.example_credit_decision_mixed.type.GenerateOutputData> expected = decision.asList(new com.gs.dmn.generated.example_credit_decision_mixed.type.GenerateOutputDataImpl(decision.numericUnaryMinus(decision.number("42.5")), "Reject", decision.numericUnaryMinus(decision.number("77.5"))));
        checkValues(expected, generateOutputDataOutput);
    }

    private void checkValues(Object expected, Object actual) {
        Assert.assertEquals(expected, actual);
    }

    @Override
    protected void applyDecision() {
        AnnotationSet annotationSet_ = new AnnotationSet();
        ApplicantImpl applicant = new ApplicantImpl();
        applicant.setName("Charlie");
        applicant.setAge(decision.number("65"));
        applicant.setCreditScore(decision.number("80"));
        applicant.setPriorIssues(decision.asList("Late payment", "Default on obligations", "Bankruptcy"));

        BigDecimal currentRiskAppetite = decision.number("50");
        BigDecimal lendingThreshold = decision.number("25");

        decision.apply(applicant, currentRiskAppetite, lendingThreshold, annotationSet_);
    }
}