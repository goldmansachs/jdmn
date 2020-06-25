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

    @Override
    public void deriveType(FEELContext context) {
        if (function instanceof Name) {
            DeclarationMatch declarationMatch = functionResolution(context, ((Name) function).getName());
            FunctionDeclaration functionDeclaration = (FunctionDeclaration) declarationMatch.getDeclaration();
            FunctionType functionType = functionDeclaration.getType();
            this.function.setType(functionType);

            setInvocationType(functionType);
            parameters.setParameterConversions(declarationMatch.getParameterConversions());
            parameters.setConvertedParameterTypes(declarationMatch.getParameterTypes());
        } else if (function instanceof QualifiedName && ((QualifiedName) function).getNames().size() == 1) {
            DeclarationMatch declarationMatch = functionResolution(context, ((QualifiedName) function).getQualifiedName());
            FunctionDeclaration functionDeclaration = (FunctionDeclaration) declarationMatch.getDeclaration();
            FunctionType functionType = functionDeclaration.getType();
            this.function.setType(functionType);

            setInvocationType(functionType);
            parameters.setParameterConversions(declarationMatch.getParameterConversions());
            parameters.setConvertedParameterTypes(declarationMatch.getParameterTypes());
        } else {
            FunctionType functionType = (FunctionType) function.getType();

            setInvocationType(functionType);
            if (parameters instanceof NamedParameters) {
                ParameterConversions parameterConversions = new NamedParameterConversions(functionType.getParameters());
                parameters.setParameterConversions(parameterConversions);
            } else {
                ParameterConversions parameterConversions = new PositionalParameterConversions(functionType.getParameterTypes());
                parameters.setParameterConversions(parameterConversions);
            }
            parameters.setConvertedParameterTypes(parameters.getSignature());
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

    private DeclarationMatch functionResolution(FEELContext context, String name) {
        ParameterTypes parameterTypes = parameters.getSignature();
        List<DeclarationMatch> functionMatches = findFunctionMatches(context, name, parameterTypes);
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

    private List<DeclarationMatch> findFunctionMatches(FEELContext context, String name, ParameterTypes parameterTypes) {
        Environment environment = context.getEnvironment();
        List<DeclarationMatch> matches = new ArrayList<>();
        List<Declaration> declarations = environment.lookupFunctionDeclaration(name);
        for (Declaration declaration : declarations) {
            FunctionDeclaration functionDeclaration = (FunctionDeclaration) declaration;
            if (functionDeclaration.match(parameterTypes)) {
                // Exact match. no conversion required
                DeclarationMatch declarationMatch = makeDeclarationMatch(functionDeclaration);
                matches.add(declarationMatch);
                break;
            } else {
                List<Pair<ParameterTypes, ParameterConversions>> candidates = parameterTypes.candidates();
                for (Pair<ParameterTypes, ParameterConversions> candidate : candidates) {
                    ParameterTypes newParameterTypes = candidate.getLeft();
                    ParameterConversions parameterConversions = candidate.getRight();
                    if (functionDeclaration.match(newParameterTypes)) {
                        matches.add(makeDeclarationMatch(declaration, newParameterTypes, parameterConversions));
                    }
                }
            }
        }
        return matches;
    }

    private DeclarationMatch makeDeclarationMatch(FunctionDeclaration functionDeclaration) {
        FunctionType functionType = functionDeclaration.getType();
        if (parameters instanceof NamedParameters) {
            NamedParameterConversions parameterConversions = new NamedParameterConversions(functionType.getParameters());
            ParameterTypes newParameterTypes = parameters.getSignature();
            return makeDeclarationMatch(functionDeclaration, newParameterTypes, parameterConversions);
        } else {
            PositionalParameterConversions parameterConversions = new PositionalParameterConversions(functionType.getParameterTypes());
            ParameterTypes newParameterTypes = parameters.getSignature();
            return makeDeclarationMatch(functionDeclaration, newParameterTypes, parameterConversions);
        }
    }

    private DeclarationMatch makeDeclarationMatch(Declaration declaration, ParameterTypes newParameterTypes, ParameterConversions parameterConversions) {
        return new DeclarationMatch(declaration, newParameterTypes, parameterConversions);
    }
}
