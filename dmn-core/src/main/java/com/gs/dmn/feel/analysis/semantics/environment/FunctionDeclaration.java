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
package com.gs.dmn.feel.analysis.semantics.environment;

import com.gs.dmn.feel.analysis.semantics.type.FunctionType;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.ParameterConversions;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.ParameterTypes;
import com.gs.dmn.runtime.Pair;

import java.util.List;

public class FunctionDeclaration extends Declaration {
    private final FunctionType type;

    FunctionDeclaration(String name, FunctionType type) {
        super(name);
        this.type = type;
    }

    @Override
    public FunctionType getType() {
        return this.type;
    }

    public boolean match(ParameterTypes parameterTypes) {
        return this.type.match(parameterTypes);
    }

    public List<Pair<ParameterTypes, ParameterConversions>> matchCandidates(ParameterTypes parameterTypes) {
        return this.type.matchCandidates(parameterTypes);
    }

    public String toString() {
        return String.format("%s -> %s ", this.name, this.type);
    }
}
