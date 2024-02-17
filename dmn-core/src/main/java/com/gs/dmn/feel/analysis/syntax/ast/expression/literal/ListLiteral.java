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
package com.gs.dmn.feel.analysis.syntax.ast.expression.literal;

import com.gs.dmn.feel.analysis.syntax.ast.Visitor;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.test.OperatorRange;
import com.gs.dmn.feel.analysis.syntax.ast.test.SimplePositiveUnaryTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ListLiteral<T> extends Expression<T> {
    private final List<Expression<T>> expressionList = new ArrayList<>();

    public ListLiteral(List<Expression<T>> expressionList) {
        if (expressionList != null) {
            this.expressionList.addAll(expressionList);
        }
    }

    public List<Expression<T>> getExpressionList() {
        return this.expressionList;
    }

    public void add(Expression<T> ast) {
        this.expressionList.add(ast);
    }

    @Override
    public <C, R> R accept(Visitor<T, C, R> visitor, C context) {
        return visitor.visit(this, context);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListLiteral<?> that = (ListLiteral<?>) o;
        return Objects.equals(expressionList, that.expressionList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expressionList);
    }

    @Override
    public String toString() {
        String expressions = this.expressionList.stream().map(Object::toString).collect(Collectors.joining(","));
        return "%s(%s)".formatted(getClass().getSimpleName(), expressions);
    }

    public boolean allTestsAreEqualityTest() {
        return this.expressionList.stream().allMatch(this::isEqualityTest);
    }

    private boolean isEqualityTest(Expression<T> expression) {
        if (expression instanceof SimplePositiveUnaryTest) {
            return expression instanceof OperatorRange && ((OperatorRange<T>) expression).getOperator() == null;
        } else {
            return true;
        }
    }

}
