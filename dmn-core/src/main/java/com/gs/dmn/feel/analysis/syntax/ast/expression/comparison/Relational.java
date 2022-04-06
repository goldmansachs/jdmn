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

public class Relational<T, C> extends Comparison<T, C> {
    private final String operator;
    private final Expression<T, C> leftOperand;
    private final Expression<T, C> rightOperand;

    public Relational(String operator, Expression<T, C> leftOperand, Expression<T, C> rightOperand) {
        this.operator = operator;
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    public String getOperator() {
        return this.operator;
    }

    public Expression<T, C> getLeftOperand() {
        return this.leftOperand;
    }

    public Expression<T, C> getRightOperand() {
        return this.rightOperand;
    }

    @Override
    public Object accept(Visitor<T, C> visitor, C context) {
        return visitor.visit(this, context);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Relational<?, ?> that = (Relational<?, ?>) o;
        return Objects.equals(operator, that.operator) && Objects.equals(leftOperand, that.leftOperand) && Objects.equals(rightOperand, that.rightOperand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operator, leftOperand, rightOperand);
    }

    @Override
    public String toString() {
        return String.format("%s(%s,%s,%s)", getClass().getSimpleName(), getOperator(), getLeftOperand(), getRightOperand());
    }

}
 
