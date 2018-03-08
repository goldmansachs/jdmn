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

import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.analysis.syntax.ast.Visitor;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PositionalParameters extends Parameters {
    private final List<Expression> parameters = new ArrayList<>();

    public PositionalParameters(List<Expression> parameters) {
        if (parameters != null) {
            this.parameters.addAll(parameters);
        }
    }

    @Override
    public boolean isEmpty() {
        return this.parameters.isEmpty();
    }

    @Override
    public PositionalSignature getSignature() {
        return new PositionalSignature(parameters.stream().map(Expression::getType).collect(Collectors.toList()));
    }

    public List<Expression> getParameters() {
        return parameters;
    }

    @Override
    public Object accept(Visitor visitor, FEELContext params) {
        return visitor.visit(this, params);
    }

    @Override
    public String toString() {
        String opd = parameters.stream().map(Object::toString).collect(Collectors.joining(", "));
        return String.format("PositionalParameters(%s)", opd);
    }
}
