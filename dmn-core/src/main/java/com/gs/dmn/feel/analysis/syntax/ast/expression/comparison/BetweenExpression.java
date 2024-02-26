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
package com.gs.dmn.feel.analysis.syntax.ast.expression.comparison;

import com.gs.dmn.feel.analysis.syntax.ast.Visitor;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;

import java.util.Objects;

public class BetweenExpression<T> extends Comparison<T> {
    private final Expression<T> value;
    private final Expression<T> leftEndpoint;
    private final Expression<T> rightEndpoint;

    public BetweenExpression(Expression<T> value, Expression<T> leftEndpoint, Expression<T> rightEndpoint) {
        this.value = value;
        this.leftEndpoint = leftEndpoint;
        this.rightEndpoint = rightEndpoint;
    }

    public Expression<T> getValue() {
        return this.value;
    }

    public Expression<T> getLeftEndpoint() {
        return this.leftEndpoint;
    }

    public Expression<T> getRightEndpoint() {
        return this.rightEndpoint;
    }

    @Override
    public <C, R> R accept(Visitor<T, C, R> visitor, C context) {
        return visitor.visit(this, context);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BetweenExpression<?> that = (BetweenExpression<?>) o;
        return Objects.equals(value, that.value) && Objects.equals(leftEndpoint, that.leftEndpoint) && Objects.equals(rightEndpoint, that.rightEndpoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, leftEndpoint, rightEndpoint);
    }

    @Override
    public String toString() {
        return "%s(%s, %s, %s)".formatted(getClass().getSimpleName(), this.value.toString(), this.leftEndpoint.toString(), this.rightEndpoint.toString());
    }

}
