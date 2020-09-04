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

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.gs.dmn.signavio.testlab.TestLabVisitor;

import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({ "type", "value" })
public class ListExpression extends Expression {
    private List<Expression> value;

    public ListExpression() {
    }

    public ListExpression(List<Expression> value) {
        this.value = value;
    }

    public List<Expression> getElements() {
        return value;
    }

    @Override
    public String toFEELExpression() {
        if (value == null) {
            return "null";
        } else {
            List<String> elementsList = new ArrayList<>();
            for (Expression e: value) {
                if (e != null) {
                    elementsList.add(e.toFEELExpression());
                } else {
                    elementsList.add("null");
                }
            }
            String elements = String.join(", ", elementsList);
            return String.format("[%s]", elements);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListExpression that = (ListExpression) o;

        return value != null ? value.equals(that.value) : that.value == null;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", this.getClass().getSimpleName(), value);
    }

    @Override
    public Object accept(TestLabVisitor visitor, Object... params) {
        return visitor.visit(this, params);
    }
}
