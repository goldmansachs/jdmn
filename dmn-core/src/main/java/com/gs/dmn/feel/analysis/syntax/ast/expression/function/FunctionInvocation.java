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
package com.gs.dmn.feel.analysis.syntax.ast.expression.function;

import com.gs.dmn.feel.analysis.semantics.environment.Declaration;
import com.gs.dmn.feel.analysis.semantics.environment.Environment;
import com.gs.dmn.feel.analysis.semantics.environment.FunctionDeclaration;
import com.gs.dmn.feel.analysis.semantics.type.FunctionType;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.analysis.syntax.ast.Visitor;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Name;
import com.gs.dmn.feel.analysis.syntax.ast.expression.QualifiedName;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;

import java.util.ArrayList;
import java.util.List;

public class FunctionInvocation extends Expression {
    private final Expression function;
    private final Parameters parameters;

    private ParameterTypes parameterTypes;
    private ParameterConversions parameterConversions;

    public FunctionInvocation(Expression function, Parameters parameters) {
        this.function = function;
        this.parameters = parameters;
    }

    public Expression getFunction() {
        return function;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public ParameterTypes getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(ParameterTypes parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public ParameterConversions getParameterConversions() {
        return parameterConversions;
    }

    private void setParameterConversions(ParameterConversions parameterConversions) {
        this.parameterConversions = parameterConversions;
    }

    @Override
    public void deriveType(Environment environment) {
        if (function instanceof Name) {
            DeclarationMatch declarationMatch = functionResolution(environment, ((Name) function).getName());
            FunctionDeclaration functionDeclaration = (FunctionDeclaration) declarationMatch.getDeclaration();
            FunctionType functionType = functionDeclaration.getType();
            this.function.setType(functionType);

            setInvocationType(functionType);
            setParameterTypes(declarationMatch.getParameterTypes());
            setParameterConversions(declarationMatch.getParameterConversions());
        } else if (function instanceof QualifiedName && ((QualifiedName) function).getNames().size() == 1) {
            DeclarationMatch declarationMatch = functionResolution(environment, ((QualifiedName) function).getQualifiedName());
            FunctionDeclaration functionDeclaration = (FunctionDeclaration) declarationMatch.getDeclaration();
            FunctionType functionType = functionDeclaration.getType();
            this.function.setType(functionType);

            setInvocationType(functionType);
            setParameterTypes(declarationMatch.getParameterTypes());
            setParameterConversions(declarationMatch.getParameterConversions());
        } else {
            Type functionType = function.getType();
            List<Type> parameterTypes = ((FunctionType) functionType).getParameterTypes();

            setInvocationType(functionType);
            setParameterTypes(new PositionalParameterTypes(parameterTypes));
            setParameterConversions(new PositionalParameterConversions(parameterTypes));
        }
    }

    @Override
    public Object accept(Visitor visitor, FEELContext params) {
        return visitor.visit(this, params);
    }

    @Override
    public String toString() {
        return String.format("FunctionInvocation(%s -> %s)", function.toString(), parameters.toString());
    }

    private DeclarationMatch functionResolution(Environment environment, String name) {
        ParameterTypes parameterTypes = parameters.getSignature();
        List<DeclarationMatch> functionMatches = findFunctionMatches(environment, name, parameterTypes);
        if (functionMatches.isEmpty()) {
            throw new DMNRuntimeException(String.format("Cannot resolve function '%s(%s)'", name, parameterTypes));
        } else if (functionMatches.size() == 1) {
            return functionMatches.get(0);
        } else {
            throw new DMNRuntimeException(String.format("Multiple matches for function '%s(%s)'", name, parameterTypes));
        }
    }

    private void setInvocationType(Type functionType) {
        if (functionType instanceof FunctionType) {
            setType(((FunctionType) functionType).getReturnType());
        } else {
            throw new DMNRuntimeException(String.format("Expected function type for '%s'. Found '%s'", function, functionType));
        }
    }

    private List<DeclarationMatch> findFunctionMatches(Environment environment, String name, ParameterTypes parameterTypes) {
        List<DeclarationMatch> matches = new ArrayList<>();
        List<Declaration> declarations = environment.lookupFunctionDeclaration(name);
        for (Declaration declaration : declarations) {
            FunctionDeclaration functionDeclaration = (FunctionDeclaration) declaration;
            if (functionDeclaration.match(parameterTypes)) {
                // Exact match. no conversion required
                PositionalParameterConversions conversions = new PositionalParameterConversions(functionDeclaration.getType().getParameterTypes());
                matches.add(new DeclarationMatch(declaration, parameterTypes, conversions));
                break;
            } else {
                List<Pair<ParameterTypes, ParameterConversions>> candidates = parameterTypes.candidates();
                for (Pair<ParameterTypes, ParameterConversions> candidate : candidates) {
                    ParameterTypes newParameterTypes = candidate.getLeft();
                    ParameterConversions parameterConversions = candidate.getRight();
                    if (functionDeclaration.match(newParameterTypes)) {
                        matches.add(new DeclarationMatch(declaration, newParameterTypes, parameterConversions));
                    }
                }
            }
        }
        return matches;
    }
}
