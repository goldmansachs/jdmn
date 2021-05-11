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
import com.gs.dmn.feel.analysis.semantics.type.ListType;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.analysis.syntax.ast.Visitor;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Name;
import com.gs.dmn.feel.analysis.syntax.ast.expression.QualifiedName;
import com.gs.dmn.feel.analysis.syntax.ast.expression.literal.ListLiteral;
import com.gs.dmn.runtime.DMNRuntimeException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.gs.dmn.feel.analysis.semantics.type.AnyType.ANY;

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
            Type functionType = refineFunctionType(functionDeclaration);
            this.function.setType(functionType);

            setInvocationType(functionType);
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

    private void setInvocationType(Type functionType) {
        if (functionType instanceof FunctionType) {
            setType(((FunctionType) functionType).getReturnType());
        } else {
            throw new DMNRuntimeException(String.format("Expected function type for '%s'. Found '%s'", this.function, functionType));
        }
    }

    private Type refineFunctionType(Declaration functionDeclaration) {
        String functionName = functionDeclaration.getName();
        if("sublist".equals(functionName)) {
            Parameters parameters = this.getParameters();
            Type listType = parameters.getParameterType(0, "list");
            return DefaultDMNEnvironmentFactory.makeSublistBuiltInFunctionType(listType);
        } else if("append".equals(functionName)) {
            FormalParameter formalParameter = ((FunctionDeclaration) functionDeclaration).getType().getParameters().get(1);
            String name = formalParameter.getName();
            Parameters parameters = this.getParameters();
            if ("item".equals(name)) {
                Type listType = parameters.getParameterType(0, "list");
                Type itemType = parameters.getParameterType(1, "item");
                return DefaultDMNEnvironmentFactory.makeAppendBuiltinFunctionType(listType, itemType);
            } else {
                Type listType = parameters.getParameterType(0, "list");
                Type elementType = parameters.getParameterType(1, "element");
                return DefaultDMNEnvironmentFactory.makeSignavioAppendBuiltinFunctionType(listType, elementType);
            }
        } else if("concatenate".equals(functionName)) {
            Parameters parameters = this.getParameters();
            Type listType = parameters.getParameterType(0, "list");
            return DefaultDMNEnvironmentFactory.makeConcatenateBuiltinFunctionType(listType);
        } else if("insert before".equals(functionName)) {
            Parameters parameters = this.getParameters();
            Type listType = parameters.getParameterType(0, "list");
            Type newItemType = parameters.getParameterType(2, "new item");
            return DefaultDMNEnvironmentFactory.makeInsertBeforeBuiltinFunctionType(listType, newItemType);
        } else if("remove".equals(functionName)) {
            FormalParameter formalParameter = ((FunctionDeclaration) functionDeclaration).getType().getParameters().get(1);
            String name = formalParameter.getName();
            if ("position".equals(name)) {
                Parameters parameters = this.getParameters();
                Type listType = parameters.getParameterType(0, "list");
                return DefaultDMNEnvironmentFactory.makeRemoveBuiltinFunctionType(listType);
            } else {
                Parameters parameters = this.getParameters();
                Type listType = parameters.getParameterType(0, "list");
                Type elementType = parameters.getParameterType(1, "element");
                return DefaultDMNEnvironmentFactory.makeSignavioRemoveBuiltinFunctionType(listType, elementType);
            }
        } else if("reverse".equals(functionName)) {
            Parameters parameters = this.getParameters();
            Type listType = parameters.getParameterType(0, "list");
            return DefaultDMNEnvironmentFactory.makeReverseBuiltinFunctionType(listType);
        } else if("index of".equals(functionName)) {
            Parameters parameters = this.getParameters();
            Type listType = parameters.getParameterType(0, "list");
            Type matchType = parameters.getParameterType(1, "match");
            return DefaultDMNEnvironmentFactory.makeIndexOfBuiltinFunctionType(listType, matchType);
        } else if("distinct values".equals(functionName)) {
            Parameters parameters = this.getParameters();
            Type listType = parameters.getParameterType(0, "list");
            return DefaultDMNEnvironmentFactory.makeDistinctValuesBuiltinFunctionType(listType);
        } else if("union".equals(functionName)) {
            Parameters parameters = this.getParameters();
            Type listType = parameters.getParameterType(0, "list");
            return DefaultDMNEnvironmentFactory.makeUnionBuiltinFunctionType(listType);
        } else if("flatten".equals(functionName)) {
            Expression listParameter = parameters.getParameter(0, "list");
            Type elementType = nestedElementType(listParameter);
            return DefaultDMNEnvironmentFactory.makeFlattenBuiltinFunctionType(new ListType(elementType));
        } else if ("sort".equals(functionName)) {
            Parameters parameters = this.getParameters();
            Type listType = parameters.getParameterType(0, "list");
            Type functionType = parameters.getParameterType(1, "function");
            return DefaultDMNEnvironmentFactory.makeSortBuiltinFunctionType(listType, functionType);

        } else if("appendAll".equals(functionName)) {
            Parameters parameters = this.getParameters();
            Type list1Type = parameters.getParameterType(0, "list1");
            return DefaultDMNEnvironmentFactory.makeSignavioAppendAllBuiltinFunctionType(list1Type);
        } else if("removeAll".equals(functionName)) {
            Parameters parameters = this.getParameters();
            Type list1Type = parameters.getParameterType(0, "list1");
            return DefaultDMNEnvironmentFactory.makeRemoveBuiltinFunctionType(list1Type);
        } else {
            return ((FunctionDeclaration) functionDeclaration).getType();
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
                if (!type.conformsTo(upperBound)) {
                    if (upperBound.conformsTo(type)) {
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
        if (expression == null) {
            return;
        } else if (expression instanceof ListLiteral) {
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
}
