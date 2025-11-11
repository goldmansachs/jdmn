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
package com.gs.dmn.context;

import com.gs.dmn.ast.TNamedElement;
import com.gs.dmn.context.environment.Declaration;
import com.gs.dmn.context.environment.Environment;
import com.gs.dmn.context.environment.RuntimeEnvironment;
import com.gs.dmn.context.environment.VariableDeclaration;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.el.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.error.ErrorFactory;
import com.gs.dmn.error.SemanticErrorException;
import com.gs.dmn.runtime.DMNRuntimeException;

import java.util.ArrayList;
import java.util.List;

public class DMNContext {
    public static final String INPUT_ENTRY_PLACE_HOLDER = "?";

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

    public String getElementName() {
        return this.element == null ? null : this.getElement().getName();
    }

    public Environment getEnvironment() {
        return this.environment;
    }

    public Expression<Type> getInputExpression() {
        return this.environment.getInputExpression();
    }

    public Type getInputExpressionType() {
        return this.environment.getInputExpressionType();
    }

    public boolean isTestContext() {
        return getInputExpression() != null;
    }

    public boolean isExpressionContext() {
        return !isTestContext();
    }

    public void addDeclaration(Declaration declaration) {
        try {
            this.environment.addDeclaration(declaration);
        } catch (Exception e) {
            throw new SemanticErrorException(ErrorFactory.makeDMNErrorMessage(null, element, e.getMessage()), e);
        }
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
                for (Declaration d: parentDeclarations) {
                    if (!declarations.contains(d)) {
                        declarations.add(d);
                    }
                }
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

    public Object lookupFunctionBinding(String key) {
        if (this.runtimeEnvironment.isLocalFunctionBound(key)) {
            return this.runtimeEnvironment.lookupLocalFunctionBinding(key);
        } else {
            if (this.parent != null) {
                return this.parent.lookupFunctionBinding(key);
            } else {
                return null;
            }
        }
    }

    public boolean isBound(String key) {
        // Ignore built-in functions, should be invoked for decisions only
        if (parent == null) {
            return false;
        }

        if (this.runtimeEnvironment.isLocalBound(key)) {
            return true;
        } else {
            return this.parent.isBound(key);
        }
    }

    public void bind(String variableName, Object value) {
        if (this.parent == null || this.kind == DMNContextKind.BUILT_IN) {
            throw new DMNRuntimeException(String.format("Cannot bind variable '%s' in built-in context", variableName));
        }
        this.runtimeEnvironment.bind(variableName, value);
    }
}
