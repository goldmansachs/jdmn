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

public class RangeIteratorDomain<T, C> extends IteratorDomain<T, C> {
    private final Expression<T, C> start;
    private final Expression<T, C> end;

    public RangeIteratorDomain(Expression<T, C> start, Expression<T, C> end) {
        this.start = start;
        this.end = end;
    }

    public Expression<T, C> getStart() {
        return this.start;
    }

    public Expression<T, C> getEnd() {
        return this.end;
    }

    @Override
    public Object accept(Visitor<T, C> visitor, C context) {
        return visitor.visit(this, context);
    }

    @Override
    public String toString() {
        return String.format("%s(%s, %s)", getClass().getSimpleName(), this.start, this.end);
    }
}
