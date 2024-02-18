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
import com.gs.jpa.example_credit_decision.CreditIssueTypeJPA;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "APPLICANT")
public class PersonJPA implements Person {
    @Id
    @Column(name = "ID")
    private long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "AGE")
    private BigDecimal age;

    public PersonJPA() {
    }

    public PersonJPA(String name, BigDecimal age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public BigDecimal getAge() {
        return age;
    }
}
