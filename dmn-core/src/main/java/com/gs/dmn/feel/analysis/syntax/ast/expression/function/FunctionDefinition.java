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
import com.gs.dmn.feel.analysis.syntax.ast.expression.type.TypeExpression;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FunctionDefinition<T> extends Expression<T> {
    private final List<FormalParameter<T>> formalParameters;
    private final TypeExpression<T> returnTypeExpression;
    private final Expression<T> body;
    private final boolean external;

    public FunctionDefinition(List<FormalParameter<T>> formalParameters, TypeExpression<T> returnTypeExpression, Expression<T> body, boolean external) {
        this.formalParameters = formalParameters;
        this.returnTypeExpression = returnTypeExpression;
        this.body = body;
        this.external = external;
    }

    public List<FormalParameter<T>> getFormalParameters() {
        return this.formalParameters;
    }

    public boolean isStaticTyped() {
        return this.formalParameters.stream().allMatch(p -> p.getType() != null);
    }

    public TypeExpression<T> getReturnTypeExpression() {
        return this.returnTypeExpression;
    }

    public Expression<T> getBody() {
        return this.body;
    }

    public boolean isExternal() {
        return this.external;
    }

    public T getReturnType() {
        if (this.external) {
            return null;
        } else {
            return this.body.getType();
        }
    }

    @Override
    public <C, R> R accept(Visitor<T, C, R> visitor, C context) {
        return visitor.visit(this, context);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FunctionDefinition<?> that = (FunctionDefinition<?>) o;
        return external == that.external && Objects.equals(formalParameters, that.formalParameters) && Objects.equals(returnTypeExpression, that.returnTypeExpression) && Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(formalParameters, returnTypeExpression, body, external);
    }

    @Override
    public String toString() {
        String parameters = this.formalParameters.stream().map(FormalParameter::toString).collect(Collectors.joining(","));
        return "%s(%s, %s, %s)".formatted(getClass().getSimpleName(), parameters, this.body.toString(), this.external);
    }
}
