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
package com.gs.dmn.signavio.rdf2dmn.json.decision;

import com.gs.dmn.signavio.rdf2dmn.json.Context;
import com.gs.dmn.signavio.rdf2dmn.json.Visitor;

import java.util.ArrayList;
import java.util.List;

public class DecisionTable extends DecisionExpression {
    private String version;
    private HitPolicy hitPolicy;
    private String aggregation;
    private boolean isComplete;
    private final List<InputClause> inputClauses = new ArrayList<>();
    private final List<OutputClause> outputClauses = new ArrayList<>();
    private final List<DecisionTableAnnotation> annotations = new ArrayList<>();
    private final List<Rule> rules = new ArrayList<>();

    public String getVersion() {
        return version;
    }

    public HitPolicy getHitPolicy() {
        return hitPolicy;
    }

    public String getAggregation() {
        return aggregation;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public List<InputClause> getInputClauses() {
        return inputClauses;
    }

    public List<OutputClause> getOutputClauses() {
        return outputClauses;
    }

    public List<DecisionTableAnnotation> getAnnotations() {
        return annotations;
    }

    public List<Rule> getRules() {
        return rules;
    }

    @Override
    public String accept(Visitor visitor, Context params) {
        return visitor.visit(this, params);
    }
}
