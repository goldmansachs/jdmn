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

public class FunctionInvocation<T> extends Expression<T> {
    private final Expression<T> function;
    private final Parameters<T> parameters;

    public FunctionInvocation(Expression<T> function, Parameters<T> parameters) {
        this.function = function;
        this.parameters = parameters;
    }

    public Expression<T> getFunction() {
        return this.function;
    }

    public Parameters<T> getParameters() {
        return this.parameters;
    }

    @Override
    public <C, R> R accept(Visitor<T, C, R> visitor, C context) {
        return visitor.visit(this, context);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FunctionInvocation<?> that = (FunctionInvocation<?>) o;
        return Objects.equals(function, that.function) && Objects.equals(parameters, that.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(function, parameters);
    }

    @Override
    public String toString() {
        return "FunctionInvocation(%s -> %s)".formatted(this.function.toString(), this.parameters.toString());
    }
}
