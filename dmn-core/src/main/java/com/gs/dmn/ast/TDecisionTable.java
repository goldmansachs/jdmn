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
        "id",
        "label",
        "hitPolicy",
        "aggregation",
        "preferredOrientation",
        "outputLabel",
        "otherAttributes",
        "description",
        "input",
        "output",
        "annotation",
        "rule",
        "extensionElements"
})
public class TDecisionTable extends TExpression implements Visitable {
    private List<TInputClause> input;
    private List<TOutputClause> output;
    private List<TRuleAnnotationClause> annotation;
    private List<TDecisionRule> rule;
    private THitPolicy hitPolicy;
    private TBuiltinAggregator aggregation;
    private TDecisionTableOrientation preferredOrientation;
    private String outputLabel;

    public List<TInputClause> getInput() {
        if (input == null) {
            input = new ArrayList<>();
        }
        return this.input;
    }

    public List<TOutputClause> getOutput() {
        if (output == null) {
            output = new ArrayList<>();
        }
        return this.output;
    }

    public List<TRuleAnnotationClause> getAnnotation() {
        if (annotation == null) {
            annotation = new ArrayList<>();
        }
        return this.annotation;
    }

    public List<TDecisionRule> getRule() {
        if (rule == null) {
            rule = new ArrayList<>();
        }
        return this.rule;
    }

    public THitPolicy getHitPolicy() {
        if (hitPolicy == null) {
            return THitPolicy.UNIQUE;
        } else {
            return hitPolicy;
        }
    }

    public void setHitPolicy(THitPolicy value) {
        this.hitPolicy = value;
    }

    public TBuiltinAggregator getAggregation() {
        return aggregation;
    }

    public void setAggregation(TBuiltinAggregator value) {
        this.aggregation = value;
    }

    public TDecisionTableOrientation getPreferredOrientation() {
        if (preferredOrientation == null) {
            return TDecisionTableOrientation.RULE_AS_ROW;
        } else {
            return preferredOrientation;
        }
    }

    public void setPreferredOrientation(TDecisionTableOrientation value) {
        this.preferredOrientation = value;
    }

    public String getOutputLabel() {
        return outputLabel;
    }

    public void setOutputLabel(String value) {
        this.outputLabel = value;
    }

    @Override
    public Object accept(Visitor visitor, DMNContext context) {
        return visitor.visit(this, context);
    }
}
