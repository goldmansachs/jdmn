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
package com.gs.dmn.feel.analysis.semantics.environment;

import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter;
import com.gs.dmn.runtime.DMNRuntimeException;

public class Parameter extends FormalParameter {
    private final boolean optional;
    private final boolean varArg;

    public Parameter(String name, Type type) {
        this(name, type, false, false);
    }

    public Parameter(String name, Type type, boolean optional, boolean varArg) {
        super(name, type);
        this.optional = optional;
        this.varArg = varArg;
        if (optional && varArg) {
            throw new DMNRuntimeException("Parameter cannot be optional and varArg in the same time");
        }
    }

    public boolean isOptional() {
        return optional;
    }

    public boolean isVarArg() {
        return varArg;
    }

    @Override
    public String toString() {
        return String.format("%s -> %s, %s, %s)", name, type, optional, varArg);
    }
}
