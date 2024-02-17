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
package com.gs.jpa.lazy_input_model;

import com.gs.dmn.generated.lazy_input_model.Decision;
import com.gs.dmn.generated.lazy_input_model.type.Person;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.math.BigDecimal;

public class JPALazyInputModelTest extends com.gs.dmn.runtime.MixedJavaTimeDMNBaseDecision {
    protected static EntityManagerFactory emf;
    protected static EntityManager em;

    @BeforeAll
    public static void init() {
        emf = Persistence.createEntityManagerFactory("lazy-pu-test");
        em = emf.createEntityManager();
    }

    @AfterAll
    public static void tearDown(){
        em.clear();
        em.close();
        emf.close();
    }

    @Test
    public void testCase001() {
        com.gs.dmn.runtime.ExecutionContext context = new com.gs.dmn.runtime.ExecutionContext();
        // Initialize input data
        String creditRisk = "HIGH";
        Person applicant = makeLazyPerson(1);

        // Check generateOutputData
        BigDecimal score = new Decision().apply(applicant, creditRisk, context);
        checkValues(number("1"), score);
    }

    @Test
    public void testCase002() {
        com.gs.dmn.runtime.ExecutionContext context = new com.gs.dmn.runtime.ExecutionContext();
        // Initialize input data
        String creditRisk = "MEDIUM";
        Person applicant = makeLazyPerson(2);

        // Check generateOutputData
        BigDecimal score = new Decision().apply(applicant, creditRisk, context);
        checkValues(number("2"), score);
    }

    @Test
    public void testCase003() {
        com.gs.dmn.runtime.ExecutionContext context = new com.gs.dmn.runtime.ExecutionContext();
        // Initialize input data
        String creditRisk = "MEDIUM";
        Person applicant = makeLazyPerson(3);

        // Check generateOutputData
        BigDecimal score = new Decision().apply(applicant, creditRisk, context);
        checkValues(number("3"), score);
    }

    @Test
    public void testCase004() {
        com.gs.dmn.runtime.ExecutionContext context = new com.gs.dmn.runtime.ExecutionContext();
        // Initialize input data
        String creditRisk = "MEDIUM";
        Person applicant = makeLazyPerson(4);

        // Check generateOutputData
        BigDecimal score = new Decision().apply(applicant, creditRisk, context);
        checkValues(number("4"), score);
    }

    @Test
    public void testCase005() {
        com.gs.dmn.runtime.ExecutionContext context = new com.gs.dmn.runtime.ExecutionContext();
        // Initialize input data
        String creditRisk = "LOW";
        Person applicant = makeLazyPerson(1);

        // Check generateOutputData
        BigDecimal score = new Decision().apply(applicant, creditRisk, context);
        checkValues(number("5"), score);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }

    private LazyPerson makeLazyPerson(int id) {
        return new LazyPerson(em, id);
    }
}
