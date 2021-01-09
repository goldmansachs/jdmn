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
import com.gs.dmn.feel.analysis.syntax.ast.Visitor;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Name;
import com.gs.dmn.feel.analysis.syntax.ast.expression.QualifiedName;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.DMNContext;
import com.gs.dmn.runtime.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FunctionInvocation extends Expression {
    private final Expression function;
    private final Parameters parameters;

    public FunctionInvocation(Expression function, Parameters parameters) {
        this.function = function;
        this.parameters = parameters;
    }

    public Expression getFunction() {
        return this.function;
    }

    public Parameters getParameters() {
        return this.parameters;
    }

    @Override
    public void deriveType(DMNContext context) {
        if (this.function instanceof Name) {
            DeclarationMatch declarationMatch = functionResolution(context, ((Name) this.function).getName());
            Declaration functionDeclaration = declarationMatch.getDeclaration();
            Type functionType = functionDeclaration.getType();
            this.function.setType(functionType);

            setInvocationType(functionType);
            this.parameters.setParameterConversions(declarationMatch.getParameterConversions());
            this.parameters.setConvertedParameterTypes(declarationMatch.getParameterTypes());
        } else if (this.function instanceof QualifiedName && ((QualifiedName) this.function).getNames().size() == 1) {
            DeclarationMatch declarationMatch = functionResolution(context, ((QualifiedName) this.function).getQualifiedName());
            Declaration functionDeclaration = declarationMatch.getDeclaration();
            Type functionType = functionDeclaration.getType();
            this.function.setType(functionType);

            setInvocationType(functionType);
            this.parameters.setParameterConversions(declarationMatch.getParameterConversions());
            this.parameters.setConvertedParameterTypes(declarationMatch.getParameterTypes());
        } else {
            FunctionType functionType = (FunctionType) this.function.getType();

            setInvocationType(functionType);
            if (this.parameters instanceof NamedParameters) {
                ParameterConversions parameterConversions = new NamedParameterConversions(functionType.getParameters());
                this.parameters.setParameterConversions(parameterConversions);
            } else {
                ParameterConversions parameterConversions = new PositionalParameterConversions(functionType.getParameterTypes());
                this.parameters.setParameterConversions(parameterConversions);
            }
            this.parameters.setConvertedParameterTypes(this.parameters.getSignature());
        }
    }

    @Override
    public Object accept(Visitor visitor, DMNContext params) {
        return visitor.visit(this, params);
    }

    @Override
    public String toString() {
        return String.format("FunctionInvocation(%s -> %s)", this.function.toString(), this.parameters.toString());
    }

    private DeclarationMatch functionResolution(DMNContext context, String name) {
        ParameterTypes parameterTypes = this.parameters.getSignature();
        List<DeclarationMatch> functionMatches = findFunctionMatches(context, name, parameterTypes);
        if (functionMatches.isEmpty()) {
            throw new DMNRuntimeException(String.format("Cannot resolve function '%s(%s)'", name, parameterTypes));
        } else if (functionMatches.size() == 1) {
            return functionMatches.get(0);
        } else {
            throw new DMNRuntimeException(String.format("Found %d matches for function '%s(%s)' %s", functionMatches.size(), name, parameterTypes, functionMatches));
        }
    }

    private void setInvocationType(Type functionType) {
        if (functionType instanceof FunctionType) {
            setType(((FunctionType) functionType).getReturnType());
        } else {
            throw new DMNRuntimeException(String.format("Expected function type for '%s'. Found '%s'", this.function, functionType));
        }
    }

    private List<DeclarationMatch> findFunctionMatches(DMNContext context, String name, ParameterTypes parameterTypes) {
        Environment environment = context.getEnvironment();
        List<Declaration> declarations = environment.lookupFunctionDeclaration(name);
        // Phase 1: Look for candidates without conversions
        List<DeclarationMatch> matches = new ArrayList<>();
        for (Declaration declaration : declarations) {
            FunctionDeclaration functionDeclaration = (FunctionDeclaration) declaration;
            if (functionDeclaration.match(parameterTypes)) {
                // Exact match. no conversion required
                DeclarationMatch declarationMatch = makeDeclarationMatch(functionDeclaration);
                matches.add(declarationMatch);
                return matches;
            }
        }
        // Phase 2: Check for candidates after applying conversions when types do not conform
        for (Declaration declaration : declarations) {
            FunctionDeclaration functionDeclaration = (FunctionDeclaration) declaration;
            List<Pair<ParameterTypes, ParameterConversions>> candidates = functionDeclaration.matchCandidates(parameterTypes);
            for (Pair<ParameterTypes, ParameterConversions> candidate: candidates) {
                matches.add(makeDeclarationMatch(declaration, candidate.getLeft(), candidate.getRight()));
            }
        }
        // Phase 3: Filter candidates without conformTo conversion
        List<DeclarationMatch> noConformTo = matches.stream().filter(m -> !m.getParameterConversions().hasConversion(ConversionKind.CONFORMS_TO)).collect(Collectors.toList());
        if (noConformTo.size() != 0) {
            return noConformTo;
        }
        // Phase 3: Return candidates with conformsTo
        return matches;
    }

    private DeclarationMatch makeDeclarationMatch(FunctionDeclaration functionDeclaration) {
        FunctionType functionType = functionDeclaration.getType();
        if (this.parameters instanceof NamedParameters) {
            NamedParameterConversions parameterConversions = new NamedParameterConversions(functionType.getParameters());
            ParameterTypes newParameterTypes = this.parameters.getSignature();
            return makeDeclarationMatch(functionDeclaration, newParameterTypes, parameterConversions);
        } else {
            PositionalParameterConversions parameterConversions = new PositionalParameterConversions(functionType.getParameterTypes());
            ParameterTypes newParameterTypes = this.parameters.getSignature();
            return makeDeclarationMatch(functionDeclaration, newParameterTypes, parameterConversions);
        }
    }

    private DeclarationMatch makeDeclarationMatch(Declaration declaration, ParameterTypes newParameterTypes, ParameterConversions parameterConversions) {
        return new DeclarationMatch(declaration, newParameterTypes, parameterConversions);
    }
}
