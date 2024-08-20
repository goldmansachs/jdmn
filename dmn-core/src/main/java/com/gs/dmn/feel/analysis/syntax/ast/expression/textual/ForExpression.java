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
import com.gs.dmn.feel.analysis.syntax.ast.expression.Iterator;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ForExpression<T> extends Expression<T> {
    public static final String PARTIAL_PARAMETER_NAME = "partial";

    private final List<Iterator<T>> iterators;
    private final Expression<T> body;

    public ForExpression(List<Iterator<T>> iterators, Expression<T> body) {
        this.iterators = iterators;
        this.body = body;
    }

    public List<Iterator<T>> getIterators() {
        return this.iterators;
    }

    public Expression<T> getBody() {
        return this.body;
    }

    @Override
    public <C, R> R accept(Visitor<T, C, R> visitor, C context) {
        return visitor.visit(this, context);
    }

    public ForExpression<T> toNestedForExpression() {
        if (this.iterators.size() == 1) {
            return this;
        } else {
            Expression<T> newBody = this.body;
            for(int i=this.iterators.size()-1; i>=0; i--) {
                newBody = new ForExpression<>(Collections.singletonList(this.iterators.get(i)), newBody);
            }
            return (ForExpression<T>) newBody;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ForExpression<?> that = (ForExpression<?>) o;
        return Objects.equals(iterators, that.iterators) && Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iterators, body);
    }

    @Override
    public String toString() {
        String iterators = this.iterators.stream().map(Iterator::toString).collect(Collectors.joining(","));
        return String.format("%s(%s -> %s)", getClass().getSimpleName(), iterators, this.body.toString());
    }
}
