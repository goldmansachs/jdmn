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
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.runtime.DMNRuntimeException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Environment {
    final Map<String, List<Declaration>> variablesTable = new LinkedHashMap<>();

    // For unary test context (input)
    private final Expression inputExpression;

    Environment() {
        this(null);
    }

    Environment(Expression inputExpression) {
        this.inputExpression = inputExpression;
    }

    public Expression getInputExpression() {
        return this.inputExpression;
    }

    public Type getInputExpressionType() {
        return this.inputExpression == null ? null : this.inputExpression.getType();
    }

    public Map<String, List<Declaration>> getVariablesTable() {
        return this.variablesTable;
    }

    public void addDeclaration(Declaration declaration) {
        String name = declaration.getName();
        if (name != null) {
            List<Declaration> existingDeclarations = this.variablesTable.get(name);
            if (existingDeclarations == null) {
                existingDeclarations = new ArrayList<>();
            }
            if (declaration.getType() instanceof FunctionType) {
                existingDeclarations.add(declaration);
                this.variablesTable.put(name, existingDeclarations);
            } else {
                // the name must not clash with names already existing in this scope
                if (existingDeclarations.isEmpty()) {
                    existingDeclarations.add(declaration);
                    this.variablesTable.put(name, existingDeclarations);
                } else {
                    throw new DMNRuntimeException(String.format("%s '%s' already exists", declaration.getClass().getSimpleName(), name));
                }
            }
        } else {
            throw new DMNRuntimeException(String.format("Could not add declaration with missing name %s", declaration));
        }
    }

    public Declaration lookupLocalVariableDeclaration(String name) {
        List<Declaration> declarations = this.variablesTable.get(name);
        if (declarations == null || declarations.isEmpty()) {
            return null;
        } else if (declarations.size() == 1) {
            return declarations.get(0);
        } else if (declarations.stream().allMatch(d -> d.getType() instanceof FunctionType)) {
            return null;
        } else {
            throw new DMNRuntimeException(String.format("Multiple variables for 'name' in the same context %s", declarations));
        }
    }

    public List<Declaration> lookupLocalFunctionDeclaration(String name) {
        List<Declaration> declarations = this.variablesTable.get(name);
        return declarations == null ? null : declarations.stream().filter(d -> d.getType() instanceof FunctionType).collect(Collectors.toList());
    }
}
