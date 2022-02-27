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
package com.gs.dmn.feel.analysis.syntax.ast.expression.function;

import com.gs.dmn.feel.analysis.syntax.ast.Visitor;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;

import java.util.Objects;

public class FunctionInvocation<T, C> extends Expression<T, C> {
    private final Expression<T, C> function;
    private final Parameters<T, C> parameters;

    public FunctionInvocation(Expression<T, C> function, Parameters<T, C> parameters) {
        this.function = function;
        this.parameters = parameters;
    }

    public Expression<T, C> getFunction() {
        return this.function;
    }

    public Parameters<T, C> getParameters() {
        return this.parameters;
    }

    @Override
    public Object accept(Visitor<T, C> visitor, C context) {
        return visitor.visit(this, context);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FunctionInvocation<?, ?> that = (FunctionInvocation<?, ?>) o;
        return Objects.equals(function, that.function) && Objects.equals(parameters, that.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(function, parameters);
    }

    @Override
    public String toString() {
        return String.format("FunctionInvocation(%s -> %s)", this.function.toString(), this.parameters.toString());
    }
}
