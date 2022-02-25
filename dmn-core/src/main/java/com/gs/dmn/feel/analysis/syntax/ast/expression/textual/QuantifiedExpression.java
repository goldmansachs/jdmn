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

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class QuantifiedExpression<C> extends Expression<C> {
    private final String predicate;
    private final List<Iterator<C>> iterators;
    private final Expression<C> body;

    public QuantifiedExpression(String predicate, List<Iterator<C>> iterators, Expression<C> body) {
        this.predicate = predicate;
        this.iterators = iterators;
        this.body = body;
    }

    public String getPredicate() {
        return this.predicate;
    }

    public List<Iterator<C>> getIterators() {
        return this.iterators;
    }

    public Expression<C> getBody() {
        return this.body;
    }

    public ForExpression<C> toForExpression() {
        return new ForExpression<>(this.iterators, this.body);
    }

    @Override
    public Object accept(Visitor<C> visitor, C context) {
        return visitor.visit(this, context);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuantifiedExpression<?> that = (QuantifiedExpression<?>) o;
        return Objects.equals(predicate, that.predicate) && Objects.equals(iterators, that.iterators) && Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(predicate, iterators, body);
    }

    @Override
    public String toString() {
        String iterators = this.iterators.stream().map(Iterator::toString).collect(Collectors.joining(","));
        return String.format("%s(%s, %s -> %s)", getClass().getSimpleName(), this.predicate, iterators, this.body.toString());
    }
}
