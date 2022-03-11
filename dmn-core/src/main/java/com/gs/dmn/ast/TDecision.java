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
import com.gs.dmn.context.DMNContext;

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
public class TDecision extends TDRGElement implements Visitable {
    private String question;
    private String allowedAnswers;
    private TInformationItem variable;
    private List<TInformationRequirement> informationRequirement;
    private List<TKnowledgeRequirement> knowledgeRequirement;
    private List<TAuthorityRequirement> authorityRequirement;
    private List<TDMNElementReference> supportedObjective;
    private List<TDMNElementReference> impactedPerformanceIndicator;
    private List<TDMNElementReference> decisionMaker;
    private List<TDMNElementReference> decisionOwner;
    private List<TDMNElementReference> usingProcess;
    private List<TDMNElementReference> usingTask;
    private TExpression expression;

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

    public TInformationItem getVariable() {
        return variable;
    }

    public void setVariable(TInformationItem value) {
        this.variable = value;
    }

    public List<TInformationRequirement> getInformationRequirement() {
        if (informationRequirement == null) {
            informationRequirement = new ArrayList<>();
        }
        return this.informationRequirement;
    }

    public List<TKnowledgeRequirement> getKnowledgeRequirement() {
        if (knowledgeRequirement == null) {
            knowledgeRequirement = new ArrayList<>();
        }
        return this.knowledgeRequirement;
    }

    public List<TAuthorityRequirement> getAuthorityRequirement() {
        if (authorityRequirement == null) {
            authorityRequirement = new ArrayList<>();
        }
        return this.authorityRequirement;
    }

    public List<TDMNElementReference> getSupportedObjective() {
        if (supportedObjective == null) {
            supportedObjective = new ArrayList<>();
        }
        return this.supportedObjective;
    }

    public List<TDMNElementReference> getImpactedPerformanceIndicator() {
        if (impactedPerformanceIndicator == null) {
            impactedPerformanceIndicator = new ArrayList<>();
        }
        return this.impactedPerformanceIndicator;
    }

    public List<TDMNElementReference> getDecisionMaker() {
        if (decisionMaker == null) {
            decisionMaker = new ArrayList<>();
        }
        return this.decisionMaker;
    }

    public List<TDMNElementReference> getDecisionOwner() {
        if (decisionOwner == null) {
            decisionOwner = new ArrayList<>();
        }
        return this.decisionOwner;
    }

    public List<TDMNElementReference> getUsingProcess() {
        if (usingProcess == null) {
            usingProcess = new ArrayList<>();
        }
        return this.usingProcess;
    }

    public List<TDMNElementReference> getUsingTask() {
        if (usingTask == null) {
            usingTask = new ArrayList<>();
        }
        return this.usingTask;
    }

    public TExpression getExpression() {
        return expression;
    }

    public void setExpression(TExpression value) {
        this.expression = value;
    }

    @Override
    public Object accept(Visitor visitor, DMNContext context) {
        return visitor.visit(this, context);
    }
}
