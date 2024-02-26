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
package com.gs.dmn.feel.analysis.syntax.ast.expression.context;

import com.gs.dmn.feel.analysis.syntax.ast.Element;
import com.gs.dmn.feel.analysis.syntax.ast.Visitor;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;

public class ContextEntry<T> extends Element<T> {
    private final ContextEntryKey<T> key;
    private final Expression<T> expression;

    public ContextEntry(ContextEntryKey<T> key, Expression<T> expression) {
        this.key = key;
        this.expression = expression;
    }

    public ContextEntryKey<T> getKey() {
        return this.key;
    }

    public Expression<T> getExpression() {
        return this.expression;
    }

    @Override
    public <C, R> R accept(Visitor<T, C, R> visitor, C context) {
        return visitor.visit(this, context);
    }

    @Override
    public String toString() {
        return "%s(%s = %s)".formatted(getClass().getSimpleName(), this.key.toString(), this.expression.toString());
    }
}
