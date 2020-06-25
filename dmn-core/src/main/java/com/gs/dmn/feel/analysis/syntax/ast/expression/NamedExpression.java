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
package com.gs.dmn.feel.analysis.syntax.ast.expression;

import com.gs.dmn.feel.analysis.semantics.environment.Declaration;
import com.gs.dmn.feel.analysis.semantics.environment.Environment;
import com.gs.dmn.feel.analysis.semantics.environment.FunctionDeclaration;
import com.gs.dmn.feel.analysis.semantics.environment.VariableDeclaration;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;

import java.util.List;

public abstract class NamedExpression extends Expression {
    @Override
    public void deriveType(FEELContext context) {
        Environment environment = context.getEnvironment();
        Type type;
        String name = getName();
        // Lookup for variables
        Declaration declaration = environment.lookupVariableDeclaration(name);
        if (declaration instanceof VariableDeclaration) {
            type = declaration.getType();
            setType(type);
            return;
        }
        // Lookup for functions
        List<Declaration> declarations = environment.lookupFunctionDeclaration(name);
        if (declarations != null && declarations.size() == 1) {
            declaration = declarations.get(0);
            type = ((FunctionDeclaration) declaration).getType();
            setType(type);
        }
    }

    protected abstract String getName();
}
