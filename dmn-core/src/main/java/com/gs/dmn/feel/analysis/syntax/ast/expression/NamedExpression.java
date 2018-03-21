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
package com.gs.dmn.feel.analysis.syntax.ast.expression;

import com.gs.dmn.feel.analysis.semantics.environment.*;
import com.gs.dmn.feel.analysis.semantics.type.Type;

import java.util.List;

public abstract class NamedExpression extends Expression {
    @Override
    public void deriveType(Environment environment) {
        Type type;
        String name = getName();
        // Loopup for variables
        Declaration declaration = environment.lookupVariableDeclaration(name);
        if (declaration instanceof VariableDeclaration) {
            type = ((VariableDeclaration) declaration).getType();
            setType(type);
            return;
        }
        // Lookup for member (used in filters)
        declaration = environment.lookupMemberDeclaration(name);
        if (declaration instanceof MemberDeclaration) {
            type = ((MemberDeclaration) declaration).getType();
            setType(type);
            return;
        }
        // Lookup for functions
        List<Declaration> declarations = environment.lookupFunctionDeclaration(name);
        if (declarations != null && declarations.size() == 1) {
            declaration = declarations.get(0);
            type = ((FunctionDeclaration) declaration).getType();
            setType(type);
            return;
        }

    }

    protected abstract String getName();
}
