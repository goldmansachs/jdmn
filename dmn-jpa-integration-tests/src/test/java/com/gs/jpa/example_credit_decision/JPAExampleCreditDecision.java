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
package com.gs.jpa.example_credit_decision;

import com.gs.dmn.example_credit_decision.GenerateOutputData;
import com.gs.dmn.example_credit_decision.type.Applicant;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class JPAExampleCreditDecision extends com.gs.dmn.runtime.MixedJavaTimeDMNBaseDecision {
    protected static EntityManagerFactory emf;
    protected static EntityManager em;

    @BeforeClass
    public static void init() {
        emf = Persistence.createEntityManagerFactory("mnf-pu-test");
        em = emf.createEntityManager();
    }

    @AfterClass
    public static void tearDown(){
        em.clear();
        em.close();
        emf.close();
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
        TypedQuery<Applicant> query = em.createQuery("SELECT a FROM ApplicantJPA a WHERE a.name = :name", Applicant.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
