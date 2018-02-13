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

import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.analysis.syntax.ast.Visitor;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class NamedParameters extends Parameters {
    private final Map<String, Expression> parameters;

    public NamedParameters(Map<String, Expression> params) {
        this.parameters = params;
    }

    public Map<String, Expression> getParameters() {
        return parameters;
    }

    @Override
    public boolean isEmpty() {
        return parameters.isEmpty();
    }

    @Override
    public Signature getSignature() {
        Map<String, Type> signature = new LinkedHashMap<>();
        parameters.keySet().forEach(k -> signature.put(k, parameters.get(k).getType()));
        return new NamedSignature(signature);
    }

    @Override
    public Object accept(Visitor visitor, FEELContext params) {
        return visitor.visit(this, params);
    }

    @Override
    public String toString() {
        String opd = parameters.keySet().stream().map(k -> String.format("%s : %s", k, parameters.get(k).toString())).collect(Collectors.joining(", "));
        return String.format("NamedParameters(%s)", opd);
    }

}
