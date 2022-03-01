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

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ForExpression<T, C> extends Expression<T, C> {
    public static final String PARTIAL_PARAMETER_NAME = "partial";

    private final List<Iterator<T, C>> iterators;
    private final Expression<T, C> body;

    public ForExpression(List<Iterator<T, C>> iterators, Expression<T, C> body) {
        this.iterators = iterators;
        this.body = body;
    }

    public List<Iterator<T, C>> getIterators() {
        return this.iterators;
    }

    public Expression<T, C> getBody() {
        return this.body;
    }

    @Override
    public Object accept(Visitor<T, C> visitor, C context) {
        return visitor.visit(this, context);
    }

    public ForExpression<T, C> toNestedForExpression() {
        if (this.iterators.size() == 1) {
            return this;
        } else {
            Expression<T, C> newBody = this.body;
            for(int i=this.iterators.size()-1; i>=0; i--) {
                newBody = new ForExpression<>(Arrays.asList(this.iterators.get(i)), newBody);
            }
            return (ForExpression<T, C>) newBody;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ForExpression<?, ?> that = (ForExpression<?, ?>) o;
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
