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
package com.gs.dmn.feel.analysis.syntax.ast.expression.type;

import com.gs.dmn.feel.analysis.syntax.ast.Visitor;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FunctionTypeExpression<T> extends TypeExpression<T> {
    private final List<TypeExpression<T>> parameters;
    private final TypeExpression<T> returnType;

    public FunctionTypeExpression(List<TypeExpression<T>> parameters, TypeExpression<T> returnType) {
        this.parameters = parameters;
        this.returnType = returnType;
    }

    public List<TypeExpression<T>> getParameters() {
        return this.parameters;
    }

    public TypeExpression<T> getReturnType() {
        return this.returnType;
    }

    @Override
    public <C, R> R accept(Visitor<T, C, R> visitor, C context) {
        return visitor.visit(this, context);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FunctionTypeExpression<?> that = (FunctionTypeExpression<?>) o;
        return Objects.equals(parameters, that.parameters) && Objects.equals(returnType, that.returnType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parameters, returnType);
    }

    @Override
    public String toString() {
        String parametersStr = this.parameters.stream().map(e -> "%s".formatted(e.toString())).collect(Collectors.joining(", "));
        return "%s(%s -> %s)".formatted(getClass().getSimpleName(), parametersStr, this.returnType.toString());
    }
}
