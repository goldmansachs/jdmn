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
package com.gs.dmn.feel.analysis.syntax.ast.expression;

import com.gs.dmn.feel.analysis.syntax.ast.Visitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ExpressionList<C> extends Expression<C> {
    private List<Expression<C>> expressionList = new ArrayList<>();

    public ExpressionList() {
    }

    public ExpressionList(List<Expression<C>> expressionList) {
        this.expressionList = expressionList;
    }

    public List<Expression<C>> getExpressionList() {
        return this.expressionList;
    }

    public void add(Expression<C> ast) {
        this.expressionList.add(ast);
    }

    @Override
    public Object accept(Visitor<C> visitor, C context) {
        return visitor.visit(this, context);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpressionList<?> that = (ExpressionList<?>) o;
        return Objects.equals(expressionList, that.expressionList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expressionList);
    }

    @Override
    public String toString() {
        String expressions = this.expressionList.stream().map(Object::toString).collect(Collectors.joining(","));
        return String.format("%s(%s)", getClass().getSimpleName(), expressions);
    }
}
