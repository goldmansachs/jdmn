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
package com.gs.dmn.feel.analysis.syntax.ast.library;

import com.gs.dmn.feel.analysis.syntax.ast.Element;
import com.gs.dmn.feel.analysis.syntax.ast.Visitor;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter;
import com.gs.dmn.feel.analysis.syntax.ast.expression.type.TypeExpression;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FunctionDeclaration<T> extends Element<T> {
    private final String name;
    private final List<FormalParameter<T>> formalParameters;
    private final TypeExpression<T> returnTypeExpression;
    private T type;

    public FunctionDeclaration(String name, List<FormalParameter<T>> formalParameters, TypeExpression<T> returnTypeExpression) {
        this.name = name;
        this.formalParameters = formalParameters;
        this.returnTypeExpression = returnTypeExpression;
    }

    public String getName() {
        return name;
    }

    public List<FormalParameter<T>> getFormalParameters() {
        return this.formalParameters;
    }

    public TypeExpression<T> getReturnTypeExpression() {
        return this.returnTypeExpression;
    }

    public T getType() {
        return type;
    }

    public void setType(T type) {
        this.type = type;
    }

    @Override
    public <C, R> R accept(Visitor<T, C, R> visitor, C context) {
        return visitor.visit(this, context);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FunctionDeclaration<?> that = (FunctionDeclaration<?>) o;
        return Objects.equals(name, that.name) && Objects.equals(formalParameters, that.formalParameters) && Objects.equals(returnTypeExpression, that.returnTypeExpression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, formalParameters, returnTypeExpression);
    }

    @Override
    public String toString() {
        String parameters = this.formalParameters.stream().map(FormalParameter::toString).collect(Collectors.joining(","));
        return String.format("%s(%s, %s)", name, parameters, returnTypeExpression);
    }
}
