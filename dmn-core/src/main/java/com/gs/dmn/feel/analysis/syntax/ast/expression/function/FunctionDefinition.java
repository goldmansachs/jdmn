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

import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.analysis.syntax.ast.Visitor;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.type.TypeExpression;

import java.util.List;
import java.util.stream.Collectors;

public class FunctionDefinition extends Expression {
    private final List<FormalParameter> formalParameters;
    private final TypeExpression returnTypeExpression;
    private final Expression body;
    private final boolean external;

    public FunctionDefinition(List<FormalParameter> formalParameters, TypeExpression returnTypeExpression, Expression body, boolean external) {
        this.formalParameters = formalParameters;
        this.returnTypeExpression = returnTypeExpression;
        this.body = body;
        this.external = external;
    }

    public List<FormalParameter> getFormalParameters() {
        return this.formalParameters;
    }

    public boolean isStaticTyped() {
        return this.formalParameters.stream().allMatch(p -> p.getType() != null);
    }

    public TypeExpression getReturnTypeExpression() {
        return this.returnTypeExpression;
    }

    public Expression getBody() {
        return this.body;
    }

    public boolean isExternal() {
        return this.external;
    }

    @Override
    public void deriveType(FEELContext context) {
    }

    public Type getReturnType() {
        if (this.external) {
            return null;
        } else {
            return this.body.getType();
        }
    }

    @Override
    public Object accept(Visitor visitor, FEELContext params) {
        return visitor.visit(this, params);
    }

    @Override
    public String toString() {
        String parameters = this.formalParameters.stream().map(FormalParameter::toString).collect(Collectors.joining(","));
        return String.format("FunctionDefinition(%s, %s, %s)", parameters, this.body.toString(), this.external);
    }
}
