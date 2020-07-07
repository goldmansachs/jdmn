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

import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.ParameterTypes;
import com.gs.dmn.runtime.DMNRuntimeException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Environment {
    private final Environment parent;
    private final Map<String, Declaration> variablesTable = new LinkedHashMap<>();
    private final Map<String, List<Declaration>> functionsTable = new LinkedHashMap<>();

    // For unary test context (input)
    private final Expression inputExpression;

    Environment() {
        this(null, null);
    }

    Environment(Environment parent) {
        this(parent, null);
    }

    Environment(Environment parent, Expression inputExpression) {
        this.parent = parent;
        this.inputExpression = inputExpression;
    }

    public Environment getParent() {
        return parent;
    }

    public Expression getInputExpression() {
        return inputExpression;
    }

    public Type getInputExpressionType() {
        return inputExpression == null ? null : inputExpression.getType();
    }

    public void addDeclaration(Declaration declaration) {
        String name = declaration.getName();
        addDeclaration(name, declaration);
    }

    public void addDeclaration(String name, Declaration declaration) {
        if (name != null) {
            // the name must not clash with names already existing in this scope
            if (declaration instanceof VariableDeclaration) {
                Declaration existingVariable = variablesTable.get(name);
                if (existingVariable != null) {
                    Type existingType = existingVariable.getType();
                    Type type = declaration.getType();
                    if (!existingType.conformsTo(type)) {
                        throw new DMNRuntimeException(String.format("%s '%s' already exists", declaration.getClass().getSimpleName(), name));
                    }
                } else {
                    variablesTable.put(name, declaration);
                }
            } else if (declaration instanceof FunctionDeclaration) {
                List<Declaration> existingFunctions = functionsTable.get(name);
                if (existingFunctions == null) {
                    existingFunctions = new ArrayList<>();
                }
                existingFunctions.add(declaration);
                functionsTable.put(name, existingFunctions);
            } else {
                throw new UnsupportedOperationException(String.format("%s declaration type is not supported", declaration.getClass().getSimpleName()));
            }
        } else {
            throw new DMNRuntimeException(String.format("Could not add declaration with missing name %s", declaration));
        }
    }

    private Declaration lookupLocalVariableDeclaration(String name) {
        return variablesTable.get(name);
    }

    private List<Declaration> lookupLocalFunctionDeclaration(String name) {
        return functionsTable.get(name);
    }

    public Declaration lookupVariableDeclaration(String name) {
        Declaration declaration = lookupLocalVariableDeclaration(name);
        if (declaration != null) {
            return declaration;
        } else {
            if (getParent() != null) {
                return getParent().lookupVariableDeclaration(name);
            } else {
                return null;
            }
        }
    }

    public List<Declaration> lookupFunctionDeclaration(String name) {
        List<Declaration> declarations = lookupLocalFunctionDeclaration(name);
        if (declarations == null) {
            declarations = new ArrayList<>();
        }
        Environment environment = this.parent;
        while (environment != null) {
            List<Declaration> parentDeclarations = environment.lookupLocalFunctionDeclaration(name);
            if (parentDeclarations != null) {
                declarations.addAll(parentDeclarations);
            }
            environment = environment.parent;
        }
        return declarations;
    }

    public Declaration lookupFunctionDeclaration(String name, ParameterTypes parameterTypes) {
        List<Declaration> declarations = lookupLocalFunctionDeclaration(name);
        if (declarations != null) {
            for (Declaration declaration : declarations) {
                if (((FunctionDeclaration) declaration).match(parameterTypes)) {
                    return declaration;
                }
            }
            return null;
        } else {
            if (getParent() != null) {
                return getParent().lookupFunctionDeclaration(name, parameterTypes);
            } else {
                return null;
            }
        }
    }

    public void updateVariableDeclaration(String name, Type type) {
        VariableDeclaration declaration = (VariableDeclaration) this.lookupVariableDeclaration(name);
        if (declaration != null) {
            declaration.setType(type);
        }
    }
}
