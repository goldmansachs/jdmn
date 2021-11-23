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
import com.gs.dmn.feel.analysis.semantics.environment.FunctionDeclaration;
import com.gs.dmn.feel.analysis.semantics.environment.StandardEnvironmentFactory;
import com.gs.dmn.feel.analysis.semantics.type.FunctionType;
import com.gs.dmn.feel.analysis.semantics.type.ListType;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.Visitor;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Name;
import com.gs.dmn.feel.analysis.syntax.ast.expression.QualifiedName;
import com.gs.dmn.feel.analysis.syntax.ast.expression.literal.ListLiteral;
import com.gs.dmn.runtime.DMNContext;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.gs.dmn.feel.analysis.semantics.type.AnyType.ANY;

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
            String functionName = ((Name) this.function).getName();
            deriveType(context, functionName);
        } else if (this.function instanceof QualifiedName && ((QualifiedName) this.function).getNames().size() == 1) {
            String functionName = ((QualifiedName) this.function).getQualifiedName();
            deriveType(context, functionName);
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

    private void deriveType(DMNContext context, String functionName) {
        DeclarationMatch declarationMatch = functionResolution(context, functionName);
        Declaration functionDeclaration = declarationMatch.getDeclaration();
        Type functionType = refineFunctionType(functionDeclaration);
        this.function.setType(functionType);

        setInvocationType(functionType);
        this.parameters.setParameterConversions(declarationMatch.getParameterConversions());
        this.parameters.setConvertedParameterTypes(declarationMatch.getParameterTypes());
    }

    private Type refineFunctionType(Declaration functionDeclaration) {
        String functionName = functionDeclaration.getName();
        if("sublist".equals(functionName)) {
            Parameters parameters = this.getParameters();
            Type listType = parameters.getParameterType(0, "list");
            return StandardEnvironmentFactory.makeSublistBuiltInFunctionType(listType);
        } else if("append".equals(functionName)) {
            FormalParameter formalParameter = ((FunctionType) functionDeclaration.getType()).getParameters().get(1);
            String name = formalParameter.getName();
            Parameters parameters = this.getParameters();
            if ("item".equals(name)) {
                Type listType = parameters.getParameterType(0, "list");
                Type itemType = parameters.getParameterType(1, "item");
                return StandardEnvironmentFactory.makeAppendBuiltinFunctionType(listType, itemType);
            } else {
                Type listType = parameters.getParameterType(0, "list");
                Type elementType = parameters.getParameterType(1, "element");
                return StandardEnvironmentFactory.makeSignavioAppendBuiltinFunctionType(listType, elementType);
            }
        } else if("concatenate".equals(functionName)) {
            Parameters parameters = this.getParameters();
            Type listType = parameters.getParameterType(0, "list");
            return StandardEnvironmentFactory.makeConcatenateBuiltinFunctionType(listType);
        } else if("insert before".equals(functionName)) {
            Parameters parameters = this.getParameters();
            Type listType = parameters.getParameterType(0, "list");
            Type newItemType = parameters.getParameterType(2, "new item");
            return StandardEnvironmentFactory.makeInsertBeforeBuiltinFunctionType(listType, newItemType);
        } else if("remove".equals(functionName)) {
            FormalParameter formalParameter = ((FunctionType) functionDeclaration.getType()).getParameters().get(1);
            String name = formalParameter.getName();
            if ("position".equals(name)) {
                Parameters parameters = this.getParameters();
                Type listType = parameters.getParameterType(0, "list");
                return StandardEnvironmentFactory.makeRemoveBuiltinFunctionType(listType);
            } else {
                Parameters parameters = this.getParameters();
                Type listType = parameters.getParameterType(0, "list");
                Type elementType = parameters.getParameterType(1, "element");
                return StandardEnvironmentFactory.makeSignavioRemoveBuiltinFunctionType(listType, elementType);
            }
        } else if("reverse".equals(functionName)) {
            Parameters parameters = this.getParameters();
            Type listType = parameters.getParameterType(0, "list");
            return StandardEnvironmentFactory.makeReverseBuiltinFunctionType(listType);
        } else if("index of".equals(functionName)) {
            Parameters parameters = this.getParameters();
            Type listType = parameters.getParameterType(0, "list");
            Type matchType = parameters.getParameterType(1, "match");
            return StandardEnvironmentFactory.makeIndexOfBuiltinFunctionType(listType, matchType);
        } else if("distinct values".equals(functionName)) {
            Parameters parameters = this.getParameters();
            Type listType = parameters.getParameterType(0, "list");
            return StandardEnvironmentFactory.makeDistinctValuesBuiltinFunctionType(listType);
        } else if("union".equals(functionName)) {
            Parameters parameters = this.getParameters();
            Type listType = parameters.getParameterType(0, "list");
            return StandardEnvironmentFactory.makeUnionBuiltinFunctionType(listType);
        } else if("flatten".equals(functionName)) {
            Expression listParameter = parameters.getParameter(0, "list");
            Type elementType = nestedElementType(listParameter);
            return StandardEnvironmentFactory.makeFlattenBuiltinFunctionType(new ListType(elementType));
        } else if ("sort".equals(functionName)) {
            Parameters parameters = this.getParameters();
            Type listType = parameters.getParameterType(0, "list");
            Type functionType = parameters.getParameterType(1, "function");
            return StandardEnvironmentFactory.makeSortBuiltinFunctionType(listType, functionType);

        } else if("appendAll".equals(functionName)) {
            Parameters parameters = this.getParameters();
            Type list1Type = parameters.getParameterType(0, "list1");
            return StandardEnvironmentFactory.makeSignavioAppendAllBuiltinFunctionType(list1Type);
        } else if("removeAll".equals(functionName)) {
            Parameters parameters = this.getParameters();
            Type list1Type = parameters.getParameterType(0, "list1");
            return StandardEnvironmentFactory.makeRemoveBuiltinFunctionType(list1Type);
        } else {
            return functionDeclaration.getType();
        }
    }

    private Type nestedElementType(Expression expression) {
        List<Type> primitiveTypes = new ArrayList<>();
        collectPrimitiveTypes(expression, primitiveTypes);
        Type upperBound = null;
        for (Type type: primitiveTypes) {
            if (upperBound == null) {
                upperBound = type;
            } else {
                if (!Type.conformsTo(type, upperBound)) {
                    if (Type.conformsTo(upperBound, type)) {
                        upperBound = type;
                    } else {
                        upperBound = ANY;
                    }
                }
            }
        }
        if (upperBound == null) {
            upperBound = ANY;
        }
        return upperBound;
    }

    private void collectPrimitiveTypes(Expression expression, List<Type> types) {
        if (expression instanceof ListLiteral) {
            List<Expression> expressionList = ((ListLiteral) expression).getExpressionList();
            for (Expression exp: expressionList) {
                collectPrimitiveTypes(exp, types);
            }
        } else {
            Type type = expression.getType();
            while (type instanceof ListType) {
                type = ((ListType) type).getElementType();
            }
            if (type != null) {
                types.add(type);
            }
        }
    }

    @Override
    public Object accept(Visitor visitor, DMNContext context) {
        return visitor.visit(this, context);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FunctionInvocation that = (FunctionInvocation) o;
        return Objects.equals(function, that.function) && Objects.equals(parameters, that.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(function, parameters);
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
        List<Declaration> declarations = context.lookupFunctionDeclaration(name);
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
