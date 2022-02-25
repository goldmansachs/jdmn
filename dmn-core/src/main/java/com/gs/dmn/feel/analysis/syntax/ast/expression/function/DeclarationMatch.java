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

import com.gs.dmn.feel.analysis.semantics.environment.Declaration;

public class DeclarationMatch<C> {
    private final Declaration declaration;
    private final ParameterTypes<C> parameterTypes;
    private final ParameterConversions<C> parameterConversions;

    public DeclarationMatch(Declaration declaration, ParameterTypes<C> parameterTypes, ParameterConversions<C> parameterConversions) {
        this.declaration = declaration;
        this.parameterTypes = parameterTypes;
        this.parameterConversions = parameterConversions;
    }

    public Declaration getDeclaration() {
        return this.declaration;
    }

    public ParameterTypes<C> getParameterTypes() {
        return this.parameterTypes;
    }

    public ParameterConversions<C> getParameterConversions() {
        return this.parameterConversions;
    }

    public String toString() {
        return String.format("%s(%s, %s, %s)", getClass().getSimpleName(), this.declaration, this.parameterTypes, this.parameterConversions);
    }
}
