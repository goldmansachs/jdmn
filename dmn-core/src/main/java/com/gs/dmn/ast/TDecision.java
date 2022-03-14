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
package com.gs.dmn.ast;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({
        "name",
        "id",
        "label",
        "otherAttributes",
        "description",
        "question",
        "allowedAnswers",
        "variable",
        "informationRequirement",
        "knowledgeRequirement",
        "authorityRequirement",
        "supportedObjective",
        "impactedPerformanceIndicator",
        "decisionMaker",
        "decisionOwner",
        "usingProcess",
        "usingTask",
        "expression",
        "extensionElements"
})
public class TDecision<C> extends TDRGElement<C> implements Visitable<C> {
    private String question;
    private String allowedAnswers;
    private TInformationItem<C> variable;
    private List<TInformationRequirement<C>> informationRequirement;
    private List<TKnowledgeRequirement<C>> knowledgeRequirement;
    private List<TAuthorityRequirement<C>> authorityRequirement;
    private List<TDMNElementReference<C>> supportedObjective;
    private List<TDMNElementReference<C>> impactedPerformanceIndicator;
    private List<TDMNElementReference<C>> decisionMaker;
    private List<TDMNElementReference<C>> decisionOwner;
    private List<TDMNElementReference<C>> usingProcess;
    private List<TDMNElementReference<C>> usingTask;
    private TExpression<C> expression;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String value) {
        this.question = value;
    }

    public String getAllowedAnswers() {
        return allowedAnswers;
    }

    public void setAllowedAnswers(String value) {
        this.allowedAnswers = value;
    }

    public TInformationItem<C> getVariable() {
        return variable;
    }

    public void setVariable(TInformationItem<C> value) {
        this.variable = value;
    }

    public List<TInformationRequirement<C>> getInformationRequirement() {
        if (informationRequirement == null) {
            informationRequirement = new ArrayList<>();
        }
        return this.informationRequirement;
    }

    public List<TKnowledgeRequirement<C>> getKnowledgeRequirement() {
        if (knowledgeRequirement == null) {
            knowledgeRequirement = new ArrayList<>();
        }
        return this.knowledgeRequirement;
    }

    public List<TAuthorityRequirement<C>> getAuthorityRequirement() {
        if (authorityRequirement == null) {
            authorityRequirement = new ArrayList<>();
        }
        return this.authorityRequirement;
    }

    public List<TDMNElementReference<C>> getSupportedObjective() {
        if (supportedObjective == null) {
            supportedObjective = new ArrayList<>();
        }
        return this.supportedObjective;
    }

    public List<TDMNElementReference<C>> getImpactedPerformanceIndicator() {
        if (impactedPerformanceIndicator == null) {
            impactedPerformanceIndicator = new ArrayList<>();
        }
        return this.impactedPerformanceIndicator;
    }

    public List<TDMNElementReference<C>> getDecisionMaker() {
        if (decisionMaker == null) {
            decisionMaker = new ArrayList<>();
        }
        return this.decisionMaker;
    }

    public List<TDMNElementReference<C>> getDecisionOwner() {
        if (decisionOwner == null) {
            decisionOwner = new ArrayList<>();
        }
        return this.decisionOwner;
    }

    public List<TDMNElementReference<C>> getUsingProcess() {
        if (usingProcess == null) {
            usingProcess = new ArrayList<>();
        }
        return this.usingProcess;
    }

    public List<TDMNElementReference<C>> getUsingTask() {
        if (usingTask == null) {
            usingTask = new ArrayList<>();
        }
        return this.usingTask;
    }

    public TExpression<C> getExpression() {
        return expression;
    }

    public void setExpression(TExpression<C> value) {
        this.expression = value;
    }

    @Override
    public Object accept(Visitor<C> visitor, C context) {
        return visitor.visit(this, context);
    }
}
