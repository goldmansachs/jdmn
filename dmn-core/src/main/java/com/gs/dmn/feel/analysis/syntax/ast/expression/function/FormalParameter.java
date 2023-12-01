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

import com.gs.dmn.feel.analysis.syntax.ast.Element;
import com.gs.dmn.feel.analysis.syntax.ast.Visitor;
import com.gs.dmn.feel.analysis.syntax.ast.expression.type.TypeExpression;

import java.util.Objects;

public class FormalParameter<T> extends Element<T> {
    protected final String name;
    private TypeExpression<T> typeExpression;
    protected T type;

    protected final boolean optional;
    protected final boolean varArg;

    public FormalParameter(String name, TypeExpression<T> typeExpression) {
        this.name = name;
        this.typeExpression = typeExpression;
        this.optional = false;
        this.varArg = false;
    }

    public FormalParameter(String name, T type) {
        this.name = name;
        this.type = type;
        this.optional = false;
        this.varArg = false;
    }

    public FormalParameter(String name, T type, boolean optional, boolean varArg) {
        this.name = name;
        this.type = type;
        this.optional = optional;
        this.varArg = varArg;
        if (optional && varArg) {
            throw new IllegalArgumentException("Parameter cannot be optional and varArg in the same time");
        }
    }

    public String getName() {
        return name;
    }

    public TypeExpression<T> getTypeExpression() {
        return this.typeExpression;
    }

    public T getType() {
        return type;
    }

    public void setType(T type) {
        this.type = type;
    }

    public boolean isOptional() {
        return optional;
    }

    public boolean isVarArg() {
        return varArg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FormalParameter<?> that = (FormalParameter<?>) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(typeExpression, that.typeExpression) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, typeExpression, type);
    }

    @Override
    public <C, R> R accept(Visitor<T, C, R> visitor, C context) {
        return visitor.visit(this, context);
    }

    @Override
    public String toString() {
        return String.format("%s(%s, %s, %s, %s)", this.getClass().getSimpleName(), name, type, optional, varArg);
    }
}
