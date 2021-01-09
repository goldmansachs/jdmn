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
import com.gs.dmn.runtime.DMNContext;

import java.util.List;
import java.util.stream.Collectors;

public class FunctionTypeExpression extends TypeExpression {
    private final List<TypeExpression> parameters;
    private final TypeExpression returnType;

    public FunctionTypeExpression(List<TypeExpression> parameters, TypeExpression returnType) {
        this.parameters = parameters;
        this.returnType = returnType;
    }

    public List<TypeExpression> getParameters() {
        return parameters;
    }

    public TypeExpression getReturnType() {
        return returnType;
    }

    @Override
    public Object accept(Visitor visitor, DMNContext params) {
        return visitor.visit(this, params);
    }

    @Override
    public String toString() {
        String parametersStr = parameters.stream().map(e -> String.format("%s", e.toString())).collect(Collectors.joining(", "));
        return String.format("FunctionTypeExpression(%s -> %s)", parametersStr, returnType.toString());
    }
}
