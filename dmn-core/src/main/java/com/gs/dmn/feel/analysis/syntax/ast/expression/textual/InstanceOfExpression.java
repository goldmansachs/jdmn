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
import com.gs.dmn.feel.analysis.syntax.ast.expression.type.TypeExpression;

import java.util.Objects;

public class InstanceOfExpression<T> extends Expression<T> {
    private final Expression<T> leftOperand;
    private final TypeExpression<T> rightOperand;

    public InstanceOfExpression(Expression<T> leftOperand, TypeExpression<T> rightOperand) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    public Expression<T> getLeftOperand() {
        return this.leftOperand;
    }

    public TypeExpression<T> getRightOperand() {
        return this.rightOperand;
    }

    @Override
    public <C, R> R accept(Visitor<T, C, R> visitor, C context) {
        return visitor.visit(this, context);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstanceOfExpression<?> that = (InstanceOfExpression<?>) o;
        return Objects.equals(leftOperand, that.leftOperand) && Objects.equals(rightOperand, that.rightOperand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(leftOperand, rightOperand);
    }

    @Override
    public String toString() {
        return "%s(%s, %s)".formatted(getClass().getSimpleName(), this.leftOperand.toString(), this.rightOperand.toString());
    }
}
