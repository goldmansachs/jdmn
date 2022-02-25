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

public class BetweenExpression<C> extends Comparison<C> {
    private final Expression<C> value;
    private final Expression<C> leftEndpoint;
    private final Expression<C> rightEndpoint;

    public BetweenExpression(Expression<C> value, Expression<C> leftEndpoint, Expression<C> rightEndpoint) {
        this.value = value;
        this.leftEndpoint = leftEndpoint;
        this.rightEndpoint = rightEndpoint;
    }

    public Expression<C> getValue() {
        return this.value;
    }

    public Expression<C> getLeftEndpoint() {
        return this.leftEndpoint;
    }

    public Expression<C> getRightEndpoint() {
        return this.rightEndpoint;
    }

    @Override
    public Object accept(Visitor<C> visitor, C context) {
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
        return String.format("%s(%s, %s, %s)", getClass().getSimpleName(), this.value.toString(), this.leftEndpoint.toString(), this.rightEndpoint.toString());
    }

}
