/**
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
package com.gs.reladomo.example_credit_decision;

import com.gs.dmn.example_credit_decision.GenerateOutputData;
import com.gs.dmn.example_credit_decision.type.Applicant;
import com.gs.fw.common.mithra.test.ConnectionManagerForTests;
import com.gs.fw.common.mithra.test.MithraTestResource;
import org.junit.After;
import org.junit.Before;

public class ExampleCreditDecision extends com.gs.dmn.runtime.MixedJavaTimeDMNBaseDecision {
    private MithraTestResource mithraTestResource;

    protected String getMithraConfigXmlFilename()
    {
        return "testconfig/TestMithraRuntimeConfig.xml";
    }

    @Before
    public void setUp() {
        this.mithraTestResource = new MithraTestResource(this.getMithraConfigXmlFilename());

        final ConnectionManagerForTests connectionManager = ConnectionManagerForTests.getInstanceForDbName("mithra_db");
        this.mithraTestResource.createSingleDatabase(connectionManager);
        for (String filename : this.getTestDataFilenames()) {
            this.mithraTestResource.addTestDataToDatabase(filename, connectionManager);
        }

        this.mithraTestResource.setUp();
    }

    @After
    public void tearDown() {
        this.mithraTestResource.tearDown();
    }

    private String[] getTestDataFilenames() {
        return new String[] {
                "testdata/data_Applicant.txt",
                "testdata/data_CreditIssueType.txt",
                "testdata/data_ApplicantCreditIssueType.txt"
        };
    }

    @org.junit.Test
    public void testCase001() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        com.gs.dmn.example_credit_decision.type.Applicant applicant = findApplicant("Amy");
        java.math.BigDecimal currentRiskAppetite = number("50");
        java.math.BigDecimal lendingThreshold = number("25");

        // Check generateOutputData
        com.gs.dmn.example_credit_decision.type.GenerateOutputData generateOutputDataOutput = new GenerateOutputData().apply(applicant, currentRiskAppetite, lendingThreshold, annotationSet_, eventListener_, externalExecutor_);
        checkValues(new com.gs.dmn.example_credit_decision.type.GenerateOutputDataImpl(number("27.5"), "Accept", number("-7.5")), generateOutputDataOutput);
    }

    @org.junit.Test
    public void testCase002() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        com.gs.dmn.example_credit_decision.type.Applicant applicant = findApplicant("Bill");
        java.math.BigDecimal currentRiskAppetite = number("50");
        java.math.BigDecimal lendingThreshold = number("25");

        // Check generateOutputData
        com.gs.dmn.example_credit_decision.type.GenerateOutputData generateOutputDataOutput = new GenerateOutputData().apply(applicant, currentRiskAppetite, lendingThreshold, annotationSet_, eventListener_, externalExecutor_);
        checkValues(new com.gs.dmn.example_credit_decision.type.GenerateOutputDataImpl(number("-10"), "Reject", number("-25")), generateOutputDataOutput);
    }

    @org.junit.Test
    public void testCase003() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        com.gs.dmn.example_credit_decision.type.Applicant applicant = findApplicant("Charlie");
        java.math.BigDecimal currentRiskAppetite = number("50");
        java.math.BigDecimal lendingThreshold = number("25");

        // Check generateOutputData
        com.gs.dmn.example_credit_decision.type.GenerateOutputData generateOutputDataOutput = new GenerateOutputData().apply(applicant, currentRiskAppetite, lendingThreshold, annotationSet_, eventListener_, externalExecutor_);
        checkValues(new com.gs.dmn.example_credit_decision.type.GenerateOutputDataImpl(number("-42.5"), "Reject", number("-77.5")), generateOutputDataOutput);
    }

    private Applicant findApplicant(String name) {
        com.gs.reladomo.example_credit_decision.Applicant applicantRel = ApplicantFinder.findOne(ApplicantFinder.name().eq(name));
        return Applicant.toApplicant(applicantRel);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
