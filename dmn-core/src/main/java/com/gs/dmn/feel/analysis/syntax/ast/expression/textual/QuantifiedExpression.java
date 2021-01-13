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
import com.gs.dmn.runtime.DMNContext;

import java.util.List;
import java.util.stream.Collectors;

public class QuantifiedExpression extends Expression {
    private final String predicate;
    private final List<Iterator> iterators;
    private final Expression body;

    public QuantifiedExpression(String predicate, List<Iterator> iterators, Expression body) {
        this.predicate = predicate;
        this.iterators = iterators;
        this.body = body;
    }

    public String getPredicate() {
        return this.predicate;
    }

    public List<Iterator> getIterators() {
        return this.iterators;
    }

    public Expression getBody() {
        return this.body;
    }

    public ForExpression toForExpression() {
        return new ForExpression(this.iterators, this.body);
    }

    @Override
    public void deriveType(DMNContext context) {
        setType(this.body.getType());
    }

    @Override
    public Object accept(Visitor visitor, DMNContext params) {
        return visitor.visit(this, params);
    }

    @Override
    public String toString() {
        String iterators = this.iterators.stream().map(Iterator::toString).collect(Collectors.joining(","));
        return String.format("%s(%s, %s -> %s)", getClass().getSimpleName(), this.predicate, iterators, this.body.toString());
    }
}
