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
package com.gs.dmn.signavio.testlab.expression;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.gs.dmn.signavio.testlab.TestLabElement;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(name="boolean", value = BooleanLiteral.class),
        @JsonSubTypes.Type(name="date", value = DateLiteral.class),
        @JsonSubTypes.Type(name="datetime", value = DatetimeLiteral.class),
        @JsonSubTypes.Type(name="enumeration", value = EnumerationLiteral.class),
        @JsonSubTypes.Type(name="number", value = NumberLiteral.class),
        @JsonSubTypes.Type(name="string", value = StringLiteral.class),
        @JsonSubTypes.Type(name="time", value = TimeLiteral.class),
        @JsonSubTypes.Type(name="list", value = ListExpression.class),
        @JsonSubTypes.Type(name="complex", value = ComplexExpression.class),
})
public abstract class Expression extends TestLabElement {
    @JsonIgnore
    private String type;

    public String getType() {
        return type;
    }

    public abstract String toFEELExpression();
}
