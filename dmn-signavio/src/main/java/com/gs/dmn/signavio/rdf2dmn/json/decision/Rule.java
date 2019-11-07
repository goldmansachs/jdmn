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

import com.gs.dmn.signavio.rdf2dmn.json.expression.Expression;

import java.util.ArrayList;
import java.util.List;

public class Rule {
    private String id;
    private final List<Expression> condition = new ArrayList<>();
    private final List<Expression> conclusion = new ArrayList<>();
    private final List<RuleAnnotation> annotation = new ArrayList<>();

    public String getId() {
        return id;
    }

    public List<Expression> getConditions() {
        return condition;
    }

    public List<Expression> getConclusions() {
        return conclusion;
    }

    public List<RuleAnnotation> getAnnotations() {
        return annotation;
    }
}
