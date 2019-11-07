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
package com.gs.dmn.signavio.rdf2dmn.json.expression;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.gs.dmn.signavio.rdf2dmn.json.Visitable;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "expressionType", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(name="logical", value = Logical.class),
        @JsonSubTypes.Type(name="comparison", value = Comparison.class),
        @JsonSubTypes.Type(name="arithmetic", value = Arithmetic.class),
        @JsonSubTypes.Type(name="arithmeticNegation", value = ArithmeticNegation.class),
        @JsonSubTypes.Type(name="functionCall", value = FunctionCall.class),
        @JsonSubTypes.Type(name="reference", value = Reference.class),
        @JsonSubTypes.Type(name="simpleLiteral", value = SimpleLiteral.class),
        @JsonSubTypes.Type(name="list", value = List.class),

        @JsonSubTypes.Type(name="simpleLiteral", value = SimpleLiteral.class),
        @JsonSubTypes.Type(name="enumeration", value = Enumeration.class),
        @JsonSubTypes.Type(name="list", value = List.class),
        @JsonSubTypes.Type(name="hierarchy", value = Hierarchy.class),
        @JsonSubTypes.Type(name="negation", value = Negation.class),
        @JsonSubTypes.Type(name="unaryComparison", value = UnaryComparison.class),
        @JsonSubTypes.Type(name="disjunction", value = Disjunction.class),
        @JsonSubTypes.Type(name="unaryTestFunctionCall", value = UnaryTestFunctionCall.class),
        @JsonSubTypes.Type(name="interval", value = Interval.class),
        @JsonSubTypes.Type(name="any", value = Any.class),
        @JsonSubTypes.Type(name="functionCall", value = FunctionCall.class),
        @JsonSubTypes.Type(name="dataAcceptance", value = DataAcceptance.class),
        @JsonSubTypes.Type(name="reference", value = Reference.class),
})
public abstract class Expression implements Visitable {
    private String expressionType;

    public String getExpressionType() {
        return expressionType;
    }
}
