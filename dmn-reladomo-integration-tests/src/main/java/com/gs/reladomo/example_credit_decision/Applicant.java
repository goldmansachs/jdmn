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

import com.gs.collections.api.list.MutableList;

import java.util.List;
import java.util.stream.Collectors;

public class Applicant extends ApplicantAbstract implements com.gs.dmn.example_credit_decision.type.Applicant {
    public Applicant() {
        super();
        // You must not modify this constructor. Mithra calls this internally.
        // You can call this constructor. You can also add new constructors.
    }

    @Override
    public List<String> getPriorIssues() {
        MutableList<CreditIssueType> creditIssueTypes = this.getCreditIssueTypes().getCreditIssueTypes().asGscList();
        return creditIssueTypes.stream().map(CreditIssueTypeAbstract::getName).collect(Collectors.toList());
    }
}
