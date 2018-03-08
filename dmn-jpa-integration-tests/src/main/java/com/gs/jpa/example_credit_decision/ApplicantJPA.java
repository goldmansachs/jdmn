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

import com.gs.dmn.example_credit_decision.type.Applicant;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "APPLICANT")
public class ApplicantJPA implements Applicant {
    @Id
    @Column(name = "ID")
    private long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "AGE")
    private BigDecimal age;

    @Column(name = "CREDIT_SCORE")
    private BigDecimal creditScore;

    @ManyToMany
    @JoinTable(
            name="APPLICANT_ISSUE",
            joinColumns=@JoinColumn(name="APP_ID", referencedColumnName="ID"),
            inverseJoinColumns=@JoinColumn(name="ISSUE_ID", referencedColumnName="ID"))
    private List<CreditIssueTypeJPA> creditIssueTypes;

    public ApplicantJPA() {
    }

    public ApplicantJPA(String name, BigDecimal age, BigDecimal creditScore) {
        this.name = name;
        this.age = age;
        this.creditScore = creditScore;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public BigDecimal getAge() {
        return age;
    }

    @Override
    public BigDecimal getCreditScore() {
        return creditScore;
    }

    @Override
    public List<String> getPriorIssues() {
        if (creditIssueTypes == null) {
            return Arrays.asList();
        }
        return creditIssueTypes.stream().map(CreditIssueTypeJPA::getName).collect(Collectors.toList());
    }
}
