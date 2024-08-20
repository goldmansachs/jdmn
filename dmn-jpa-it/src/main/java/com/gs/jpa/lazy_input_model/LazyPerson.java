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

import com.gs.dmn.generated.lazy_input_model.type.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;

public class LazyPerson implements Person {
    private static final Logger LOGGER = LoggerFactory.getLogger(LazyPerson.class);

    private final EntityManager em;
    private final int id;

    private Person personJPA;

    public LazyPerson(EntityManager em, int id) {
        this.em = em;
        this.id = id;
    }

    @Override
    public String getName() {
        if (personJPA == null) {
            this.personJPA = findApplicant(id);
        }
        return personJPA.getName();
    }

    @Override
    public BigDecimal getAge() {
        if (personJPA == null) {
            this.personJPA = findApplicant(id);
        }
        return personJPA.getAge();
    }

    private Person findApplicant(int id) {
        LOGGER.error("Load Person {}", id);

        TypedQuery<Person> query = em.createQuery("SELECT a FROM PersonJPA a WHERE a.id = :id", Person.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }
}
