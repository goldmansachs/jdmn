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
package com.gs.dmn.feel.analysis.syntax.ast.expression.textual;

import com.gs.dmn.feel.analysis.syntax.ast.Visitor;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;

import java.util.Objects;

public class IfExpression<T> extends Expression<T> {
    private final Expression<T> condition;
    private final Expression<T> thenExpression;
    private final Expression<T> elseExpression;

    public IfExpression(Expression<T> condition, Expression<T> thenExpression, Expression<T> elseExpression) {
        this.condition = condition;
        this.thenExpression = thenExpression;
        this.elseExpression = elseExpression;
    }

    public Expression<T> getCondition() {
        return this.condition;
    }

    public Expression<T> getThenExpression() {
        return this.thenExpression;
    }

    public Expression<T> getElseExpression() {
        return this.elseExpression;
    }

    @Override
    public <C, R> R accept(Visitor<T, C, R> visitor, C context) {
        return visitor.visit(this, context);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IfExpression<?> that = (IfExpression<?>) o;
        return Objects.equals(condition, that.condition) && Objects.equals(thenExpression, that.thenExpression) && Objects.equals(elseExpression, that.elseExpression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(condition, thenExpression, elseExpression);
    }

    @Override
    public String toString() {
        return "%s(%s, %s, %s)".formatted(getClass().getSimpleName(), this.condition.toString(), this.thenExpression.toString(), this.elseExpression.toString());
    }
}
