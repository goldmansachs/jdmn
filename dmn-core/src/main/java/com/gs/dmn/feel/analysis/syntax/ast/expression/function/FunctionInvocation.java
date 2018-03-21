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

import com.gs.dmn.feel.analysis.semantics.environment.*;
import com.gs.dmn.feel.analysis.semantics.type.FunctionType;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.analysis.syntax.ast.Visitor;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Name;
import com.gs.dmn.feel.analysis.syntax.ast.expression.QualifiedName;
import com.gs.dmn.runtime.DMNRuntimeException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FunctionInvocation extends Expression {
    private final Expression function;
    private final Parameters parameters;
    private List<Conversion> parameterConversions;

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

    public List<Conversion> getParameterConversions() {
        return parameterConversions;
    }

    private void setParameterConversions(List<Conversion> parameterConversions) {
        this.parameterConversions = parameterConversions;
    }

    @Override
    public void deriveType(Environment environment) {
        if (function instanceof Name) {
            checkFunction(environment, ((Name) function).getName());
        } else if (function instanceof QualifiedName && ((QualifiedName) function).getNames().size() == 1) {
            checkFunction(environment, ((QualifiedName) function).getQualifiedName());
        } else {
            Type type = function.getType();
            if (type instanceof FunctionType) {
                setType(((FunctionType) type).getReturnType());
            } else {
                throw new DMNRuntimeException(String.format("Expected function type for '%s'. Found '%s'", function, type));
            }
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

    private void checkFunction(Environment environment, String name) {
        Signature signature = parameters.getSignature();
        List<DeclarationMatch> functionMatches = findFunctionMatches(environment, name, signature);
        if (functionMatches.isEmpty()) {
            throw new DMNRuntimeException(String.format("Cannot resolve function '%s(%s)'", name, signature));
        } else if (functionMatches.size() == 1) {
            DeclarationMatch declarationMatch = functionMatches.get(0);
            FunctionDeclaration functionDeclaration = (FunctionDeclaration) declarationMatch.getDeclaration();
            FunctionType functionType = functionDeclaration.getType();
            this.function.setType(functionType);
            setType(functionType.getReturnType());
            setParameterConversions(declarationMatch.getParameterConversions());
        } else {
            throw new DMNRuntimeException(String.format("Multiple matches for function '%s(%s)'", name, signature));
        }
    }

    private List<DeclarationMatch> findFunctionMatches(Environment environment, String name, Signature signature) {
        List<DeclarationMatch> matches = new ArrayList<>();
        Declaration exactMatch = environment.lookupFunctionDeclaration(name, signature);
        if (exactMatch != null) {
            List<Conversion> conversions = new ArrayList<>();
            for(int i=0; i<signature.size(); i++) {
                conversions.add(new Conversion(ConversionKind.NONE, null));
            }
            matches.add(new DeclarationMatch(exactMatch, conversions));
        } else {
            // Try conversion from List
            Signature newSignature = signature.listToSingletonSignature();
            if (newSignature != null) {
                Declaration match = environment.lookupFunctionDeclaration(name, newSignature);
                if (match != null) {
                    String javaElementType = "String";
                    matches.add(new DeclarationMatch(match, Arrays.asList(new Conversion(ConversionKind.LIST_TO_ELEMENT, javaElementType))));
                }
            }
        }
        return matches;
    }
}
