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
package com.gs.dmn.runtime;

import com.gs.dmn.feel.analysis.semantics.environment.Declaration;
import com.gs.dmn.feel.analysis.semantics.environment.Environment;
import com.gs.dmn.feel.analysis.semantics.environment.VariableDeclaration;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.runtime.interpreter.environment.RuntimeEnvironment;
import org.omg.spec.dmn._20191111.model.TNamedElement;

import java.util.ArrayList;
import java.util.List;

public class DMNContext {
    private final DMNContext parent;

    private final DMNContextKind kind;
    private final TNamedElement element;
    private final Environment environment;
    private final RuntimeEnvironment runtimeEnvironment;

    public static DMNContext of(DMNContext parent, DMNContextKind kind, TNamedElement element, Environment environment, RuntimeEnvironment runtimeEnvironment) {
        return new DMNContext(parent, kind, element, environment, runtimeEnvironment);
    }

    private DMNContext(DMNContext parent, DMNContextKind kind, TNamedElement element, Environment environment, RuntimeEnvironment runtimeEnvironment) {
        this.parent = parent;
        this.kind = kind;
        this.element = element;
        this.environment = environment;
        this.runtimeEnvironment = runtimeEnvironment;
    }

    public TNamedElement getElement() {
        return this.element;
    }

    public Environment getEnvironment() {
        return this.environment;
    }

    public Expression getInputExpression() {
        return this.environment.getInputExpression();
    }

    public Type getInputExpressionType() {
        return this.environment.getInputExpressionType();
    }

    public void addDeclaration(Declaration declaration) {
        this.environment.addDeclaration(declaration);
    }

    public Declaration lookupVariableDeclaration(String name) {
        Declaration declaration = this.environment.lookupLocalVariableDeclaration(name);
        if (declaration != null) {
            return declaration;
        } else {
            if (this.parent != null) {
                return this.parent.lookupVariableDeclaration(name);
            } else {
                return null;
            }
        }
    }

    public List<Declaration> lookupFunctionDeclaration(String name) {
        List<Declaration> declarations = this.environment.lookupLocalFunctionDeclaration(name);
        if (declarations == null) {
            declarations = new ArrayList<>();
        }
        DMNContext context = this.parent;
        while (context != null) {
            List<Declaration> parentDeclarations = context.getEnvironment().lookupLocalFunctionDeclaration(name);
            if (parentDeclarations != null) {
                declarations.addAll(parentDeclarations);
            }
            context = context.parent;
        }
        return declarations;
    }

    public void updateVariableDeclaration(String name, Type type) {
        VariableDeclaration declaration = (VariableDeclaration) this.lookupVariableDeclaration(name);
        if (declaration != null) {
            declaration.setType(type);
        }
    }

    public RuntimeEnvironment getRuntimeEnvironment() {
        return this.runtimeEnvironment;
    }

    public Object lookupBinding(String key) {
        if (this.runtimeEnvironment.isLocalBound(key)) {
            return this.runtimeEnvironment.lookupLocalBinding(key);
        } else {
            if (this.parent != null) {
                return this.parent.lookupBinding(key);
            } else {
                return null;
            }
        }
    }

    public boolean isBound(String key) {
        if (this.runtimeEnvironment.isLocalBound(key)) {
            return true;
        } else {
            if (this.parent != null) {
                return this.parent.isBound(key);
            } else {
                return false;
            }
        }
    }

    public void bind(String variableName, Object value) {
        if (this.parent == null || this.kind == DMNContextKind.BUILT_IN) {
            throw new DMNRuntimeException(String.format("Cannot bind variable '%s' in built-in context", variableName));
        }
        this.runtimeEnvironment.bind(variableName, value);
    }
}
