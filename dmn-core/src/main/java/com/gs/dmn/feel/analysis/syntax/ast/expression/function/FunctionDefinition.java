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
package com.gs.dmn.feel.analysis.syntax.ast.expression.function;

import com.gs.dmn.feel.analysis.semantics.environment.Environment;
import com.gs.dmn.feel.analysis.semantics.type.AnyType;
import com.gs.dmn.feel.analysis.semantics.type.FEELFunctionType;
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.analysis.syntax.ast.Visitor;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;

import java.util.List;
import java.util.stream.Collectors;

public class FunctionDefinition extends Expression {
    private final boolean external;
    private final List<FormalParameter> formalParameters;
    private final Expression body;

    public FunctionDefinition(List<FormalParameter> formalParameters, Expression body, boolean external) {
        this.formalParameters = formalParameters;
        this.body = body;
        this.external = external;
    }

    public List<FormalParameter> getFormalParameters() {
        return formalParameters;
    }

    public boolean isStaticTyped() {
        return formalParameters.stream().allMatch(p -> p.getType() != null && p.getType() != AnyType.ANY);
    }

    public Expression getBody() {
        return body;
    }

    public boolean isExternal() {
        return external;
    }

    @Override
    public void deriveType(Environment environment) {
        FEELFunctionType type = new FEELFunctionType(formalParameters, body.getType(), external, this);
        setType(type);
    }

    @Override
    public Object accept(Visitor visitor, FEELContext params) {
        return visitor.visit(this, params);
    }

    @Override
    public String toString() {
        String parameters = formalParameters.stream().map(FormalParameter::toString).collect(Collectors.joining(","));
        return String.format("FunctionDefinition(%s, %s, %s)", parameters, body.toString(), external);
    }
}
