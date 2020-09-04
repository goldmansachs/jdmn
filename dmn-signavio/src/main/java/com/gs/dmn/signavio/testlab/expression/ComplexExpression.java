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

import java.util.List;
import java.util.stream.Collectors;

@JsonPropertyOrder({ "type", "slots" })
public class ComplexExpression extends Expression {
    private List<Slot> slots;

    public List<Slot> getSlots() {
        return slots;
    }

    public ComplexExpression() {
    }

    public ComplexExpression(List<Slot> slots) {
        this.slots = slots;
    }

    @Override
    public String toFEELExpression() {
        if (slots == null) {
            return "null";
        } else {
            String context = slots.stream().map(Slot::toContextEntry).collect(Collectors.joining(", "));
            return String.format("{%s}", context);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComplexExpression that = (ComplexExpression) o;

        return slots != null ? slots.equals(that.slots) : that.slots == null;
    }

    @Override
    public int hashCode() {
        return slots != null ? slots.hashCode() : 0;
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", this.getClass().getSimpleName(), slots);
    }

    @Override
    public Object accept(TestLabVisitor visitor, Object... params) {
        return visitor.visit(this, params);
    }
}
