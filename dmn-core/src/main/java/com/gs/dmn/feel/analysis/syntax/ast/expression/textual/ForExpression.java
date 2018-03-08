/**
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

import com.gs.dmn.feel.analysis.semantics.environment.Environment;
import com.gs.dmn.feel.analysis.semantics.type.ListType;
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.analysis.syntax.ast.Visitor;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Iterator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ForExpression extends Expression {
    private final List<Iterator> iterators;
    private final Expression body;

    public ForExpression(List<Iterator> iterators, Expression body) {
        this.iterators = iterators;
        this.body = body;
    }

    public List<Iterator> getIterators() {
        return iterators;
    }

    public Expression getBody() {
        return body;
    }

    @Override
    public void deriveType(Environment environment) {
        setType(new ListType(body.getType()));
    }

    @Override
    public Object accept(Visitor visitor, FEELContext params) {
        return visitor.visit(this, params);
    }

    public ForExpression toNestedForExpression() {
        if (iterators.size() == 1) {
            return this;
        } else {
            Expression body = this.body;
            for(int i=iterators.size()-1; i>=0; i--) {
                body = new ForExpression(Arrays.asList(iterators.get(i)), body);
            }
            return (ForExpression) body;
        }
    }

    @Override
    public String toString() {
        String iterators = this.iterators.stream().map(Iterator::toString).collect(Collectors.joining(","));
        return String.format("ForExpression(%s -> %s)", iterators, body.toString());
    }
}
