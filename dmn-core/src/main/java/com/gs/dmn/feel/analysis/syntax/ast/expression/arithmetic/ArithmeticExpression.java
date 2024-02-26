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
package com.gs.dmn.feel.analysis.syntax.ast.expression.arithmetic;

import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;

import java.util.Objects;

public abstract class ArithmeticExpression<T> extends Expression<T> {
    private final String operator;
    private final Expression<T> leftOperand;
    private final Expression<T> rightOperand;

    protected ArithmeticExpression(String operator, Expression<T> leftOperand, Expression<T> rightOperand) {
        this.operator = operator;
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    public Expression<T> getLeftOperand() {
        return this.leftOperand;
    }

    public Expression<T> getRightOperand() {
        return this.rightOperand;
    }

    public String getOperator() {
        return this.operator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArithmeticExpression<?> that = (ArithmeticExpression<?>) o;
        return Objects.equals(operator, that.operator) && Objects.equals(leftOperand, that.leftOperand) && Objects.equals(rightOperand, that.rightOperand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operator, leftOperand, rightOperand);
    }

    @Override
    public String toString() {
        return "%s(%s,%s)".formatted(getClass().getSimpleName(), getLeftOperand(), getRightOperand());
    }
}
 
