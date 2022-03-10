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
package com.gs.dmn.feel.analysis.semantics;

import com.gs.dmn.context.DMNContext;
import com.gs.dmn.context.environment.Declaration;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.ParameterConversions;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.ParameterTypes;

public class DeclarationMatch {
    private final Declaration declaration;
    private final ParameterTypes<Type, DMNContext> parameterTypes;
    private final ParameterConversions<Type, DMNContext> parameterConversions;

    public DeclarationMatch(Declaration declaration, ParameterTypes<Type, DMNContext> parameterTypes, ParameterConversions<Type, DMNContext> parameterConversions) {
        this.declaration = declaration;
        this.parameterTypes = parameterTypes;
        this.parameterConversions = parameterConversions;
    }

    public Declaration getDeclaration() {
        return this.declaration;
    }

    public ParameterTypes<Type, DMNContext> getParameterTypes() {
        return this.parameterTypes;
    }

    public ParameterConversions<Type, DMNContext> getParameterConversions() {
        return this.parameterConversions;
    }

    public String toString() {
        return String.format("%s(%s, %s, %s)", getClass().getSimpleName(), this.declaration, this.parameterTypes, this.parameterConversions);
    }
}
