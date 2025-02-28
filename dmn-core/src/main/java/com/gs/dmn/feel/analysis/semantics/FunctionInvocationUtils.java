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
package com.gs.dmn.feel.analysis.semantics;

import com.gs.dmn.context.DMNContext;
import com.gs.dmn.context.environment.Declaration;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.semantics.environment.StandardEnvironmentFactory;
import com.gs.dmn.feel.analysis.semantics.type.*;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.*;
import com.gs.dmn.feel.analysis.syntax.ast.expression.literal.ListLiteral;
import com.gs.dmn.feel.analysis.syntax.ast.expression.literal.StringLiteral;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.gs.dmn.el.analysis.semantics.type.AnyType.ANY;
import static com.gs.dmn.feel.analysis.semantics.type.ListType.ANY_LIST;
import static com.gs.dmn.feel.lib.type.range.DefaultRangeLib.*;

public class FunctionInvocationUtils {
    static void deriveType(FunctionInvocation<Type> element, DMNContext context, String functionName) {
        DeclarationMatch declarationMatch = functionResolution(element, context, functionName);
        Declaration functionDeclaration = declarationMatch.getDeclaration();
        Type functionType = refineFunctionType(element, functionDeclaration);
        element.getFunction().setType(functionType);

        setInvocationType(element, functionType);
        element.getParameters().setParameterConversions(declarationMatch.getParameterConversions());
        element.getParameters().setConvertedParameterTypes(declarationMatch.getParameterTypes());
    }

    private static Type refineFunctionType(FunctionInvocation<Type> element, Declaration functionDeclaration) {
        // Refine type for built-in functions
        if (!(functionDeclaration.getType() instanceof BuiltinFunctionType)) {
            return functionDeclaration.getType();
        }

        String functionName = functionDeclaration.getName();
        Parameters<Type> parameters = element.getParameters();
        List<FormalParameter<Type>> formalParameters = ((FunctionType) functionDeclaration.getType()).getParameters();
        if ("max".equals(functionName) || "min".equals(functionName)) {
            if (!formalParameters.isEmpty()) {
                FormalParameter<Type> formalParameter = formalParameters.get(0);
                String name = formalParameter.getName();
                if ("list".equals(name)) {
                    Type inputType = parameters.getParameterType(0, "list");
                    if (inputType instanceof ListType) {
                        Type returnType = ((ListType) inputType).getElementType();
                        return StandardEnvironmentFactory.makeMaxMinBuiltInFunctionTypeForList(inputType, returnType);
                    }
                } else {
                    Type inputType = parameters.getParameterType(0, "c1");
                    if (inputType instanceof ComparableDataType) {
                        return StandardEnvironmentFactory.makeMaxMinBuiltInFunctionTypeForSequence(inputType, inputType);
                    }
                }
            }
            return functionDeclaration.getType();
        } else if("sublist".equals(functionName)) {
            Type listType = parameters.getParameterType(0, "list");
            return StandardEnvironmentFactory.makeSublistBuiltInFunctionType(listType);
        } else if("append".equals(functionName)) {
            FormalParameter<Type> formalParameter = formalParameters.get(1);
            String name = formalParameter.getName();
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
            Type listType = parameters.getParameterType(0, "list");
            return StandardEnvironmentFactory.makeConcatenateBuiltinFunctionType(listType);
        } else if("insert before".equals(functionName)) {
            Type listType = parameters.getParameterType(0, "list");
            Type newItemType = parameters.getParameterType(2, "newItem");
            return StandardEnvironmentFactory.makeInsertBeforeBuiltinFunctionType(listType, newItemType);
        } else if("remove".equals(functionName)) {
            FormalParameter<Type> formalParameter = formalParameters.get(1);
            String name = formalParameter.getName();
            Type listType = parameters.getParameterType(0, "list");
            if ("position".equals(name)) {
                return StandardEnvironmentFactory.makeRemoveBuiltinFunctionType(listType);
            } else {
                Type elementType = parameters.getParameterType(1, "element");
                return StandardEnvironmentFactory.makeSignavioRemoveBuiltinFunctionType(listType, elementType);
            }
        } else if("list replace".equals(functionName)) {
            Type listType = parameters.getParameterType(0, "list");
            if (!(listType instanceof ListType)) {
                // Implicit conversion
                listType = new ListType(listType);
            }
            Expression<Type> secondParam = parameters.getParameter(1, "position");
            if (secondParam != null && parameters.getParameterType(1, "position") instanceof NumberType) {
                return StandardEnvironmentFactory.makeListReplacePositionBuiltinFunctionType(listType);
            } else {
                return StandardEnvironmentFactory.makeListReplaceMatchBuiltinFunctionType(listType);
            }
        } else if("reverse".equals(functionName)) {
            Type listType = parameters.getParameterType(0, "list");
            return StandardEnvironmentFactory.makeReverseBuiltinFunctionType(listType);
        } else if("index of".equals(functionName)) {
            Type listType = parameters.getParameterType(0, "list");
            Type matchType = parameters.getParameterType(1, "match");
            return StandardEnvironmentFactory.makeIndexOfBuiltinFunctionType(listType, matchType);
        } else if("distinct values".equals(functionName)) {
            Type listType = parameters.getParameterType(0, "list");
            return StandardEnvironmentFactory.makeDistinctValuesBuiltinFunctionType(listType);
        } else if("union".equals(functionName)) {
            if (parameters.isEmpty()) {
                return StandardEnvironmentFactory.makeUnionBuiltinFunctionType(ANY_LIST);
            } else {
                Type listType = parameters.getParameterType(0, "list");
                return StandardEnvironmentFactory.makeUnionBuiltinFunctionType(listType);
            }
        } else if("flatten".equals(functionName)) {
            Expression<Type> inputListParameter = parameters.getParameter(0, "list");
            Type elementType = nestedElementType(inputListParameter);
            return StandardEnvironmentFactory.makeFlattenBuiltinFunctionType(new ListType(elementType));
        } else if ("sort".equals(functionName)) {
            Type listType = parameters.getParameterType(0, "list");
            Type functionType = parameters.getParameterType(1, "function");
            return StandardEnvironmentFactory.makeSortBuiltinFunctionType(listType, functionType);
        } else if ("range".equals(functionName)) {
            Expression<Type> from = parameters.getParameter(0, "from");
            Type returnType = ANY;
            if (from instanceof StringLiteral) {
                String lexeme = ((StringLiteral<Type>) from).getLexeme();
                String fromStr = lexeme.substring(1, lexeme.length() - 1);
                returnType = inferEndpointType(fromStr);
            }
            return StandardEnvironmentFactory.makeRangeBuiltinFunctionType(returnType);
        } else if("appendAll".equals(functionName)) {
            Type list1Type = parameters.getParameterType(0, "list1");
            return StandardEnvironmentFactory.makeSignavioAppendAllBuiltinFunctionType(list1Type);
        } else if("removeAll".equals(functionName)) {
            Type list1Type = parameters.getParameterType(0, "list1");
            return StandardEnvironmentFactory.makeSignavioRemoveAllBuiltinFunctionType(list1Type);
        } else {
            return functionDeclaration.getType();
        }
    }

    private static Type inferEndpointType(String fromStr) {
        Type returnType = ANY;
        String[] rangeParts = extractRangeParts(fromStr);
        String endpoint = rangeParts[1];
        if (StringUtils.isBlank(endpoint)) {
            endpoint = rangeParts[2];
        }
        endpoint = endpoint == null ? null : endpoint.trim().replace("\\\"", "\"");
        if (isStringRange(endpoint)) {
            returnType = StringType.STRING;
        } else if (isNumberRange(endpoint)) {
            returnType = NumberType.NUMBER;
        } else if (isDateRange(endpoint)) {
            returnType = DateType.DATE;
        } else if (isTimeRange(endpoint)) {
            returnType = TimeType.TIME;
        } else if (isDateTimeRange(endpoint)) {
            returnType = DateTimeType.DATE_AND_TIME;
        } else if (isYearsMonthsRange(endpoint)) {
            returnType = YearsAndMonthsDurationType.YEARS_AND_MONTHS_DURATION;
        } else if (isDaysTimeRange(endpoint)) {
            returnType = DaysAndTimeDurationType.DAYS_AND_TIME_DURATION;
        }
        return returnType;
    }


    private static Type nestedElementType(Expression<Type> expression) {
        List<Type> primitiveTypes = new ArrayList<>();
        collectPrimitiveTypes(expression, primitiveTypes);
        Type upperBound = null;
        for (Type type: primitiveTypes) {
            if (upperBound == null) {
                upperBound = type;
            } else {
                if (!com.gs.dmn.el.analysis.semantics.type.Type.conformsTo(type, upperBound)) {
                    if (com.gs.dmn.el.analysis.semantics.type.Type.conformsTo(upperBound, type)) {
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

    private static void collectPrimitiveTypes(Expression<Type> expression, List<Type> types) {
        if (expression instanceof ListLiteral) {
            List<Expression<Type>> expressionList = ((ListLiteral<Type>) expression).getExpressionList();
            for (Expression<Type> exp: expressionList) {
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

    private static DeclarationMatch functionResolution(FunctionInvocation<Type> element, DMNContext context, String name) {
        ParameterTypes<Type> parameterTypes = element.getParameters().getSignature();
        List<DeclarationMatch> functionMatches = findFunctionMatches(element, context, name, parameterTypes);
        if (functionMatches.isEmpty()) {
            throw new DMNRuntimeException(String.format("Cannot resolve function '%s(%s)'. No match found in element '%s'", name, parameterTypes, context.getElementName()));
        } else if (functionMatches.size() == 1) {
            return functionMatches.get(0);
        } else {
            throw new DMNRuntimeException(String.format("Cannot resolve function '%s(%s)'. Found %d matches %s in element '%s'", name, parameterTypes, functionMatches.size(), functionMatches, context.getElementName()));
        }
    }

    static void setInvocationType(FunctionInvocation<Type> element, Type functionType) {
        if (functionType instanceof FunctionType) {
            element.setType(((FunctionType) functionType).getReturnType());
        } else {
            throw new DMNRuntimeException(String.format("Expected function type for '%s'. Found '%s'", element.getFunction(), functionType));
        }
    }

    private static List<DeclarationMatch> findFunctionMatches(FunctionInvocation<Type> element, DMNContext context, String name, ParameterTypes<Type> parameterTypes) {
        List<Declaration> declarations = context.lookupFunctionDeclaration(name);
        // Phase 1: Look for candidates without conversions
        List<DeclarationMatch> matches = new ArrayList<>();
        for (Declaration declaration : declarations) {
            FunctionType functionType = (FunctionType) declaration.getType();
            if (functionType.match(parameterTypes)) {
                // Exact match. no conversion required
                DeclarationMatch declarationMatch = makeDeclarationMatch(element, declaration);
                matches.add(declarationMatch);
                return matches;
            }
        }
        // Phase 2: Check for candidates after applying implicit conversions when types do not conform
        for (Declaration declaration : declarations) {
            FunctionType functionType = (FunctionType) declaration.getType();
            List<Pair<ParameterTypes<Type>, ParameterConversions<Type>>> candidates = functionType.matchCandidates(parameterTypes);
            for (Pair<ParameterTypes<Type>, ParameterConversions<Type>> candidate: candidates) {
                ParameterTypes<Type> candidateParameterTypes = candidate.getLeft();
                ParameterConversions<Type> candidateParameterConversions = candidate.getRight();
                if (element.getParameters() instanceof NamedParameters) {
                    // candidates are always positional
                    List<FormalParameter<Type>> formalParameters = functionType.getParameters();
                    ParameterTypes<Type> matchParameterTypes = NamedParameterTypes.toNamedParameterTypes((PositionalParameterTypes<Type>) candidateParameterTypes, formalParameters);
                    NamedParameterConversions<Type> matchParameterConversions = NamedParameterConversions.toNamedParameterConversions((PositionalParameterConversions<Type>) candidateParameterConversions, formalParameters);
                    matches.add(makeDeclarationMatch(declaration, matchParameterTypes, matchParameterConversions));
                } else {
                    matches.add(makeDeclarationMatch(declaration, candidateParameterTypes, candidateParameterConversions));
                }
            }
        }
        return matches;
    }

    private static DeclarationMatch makeDeclarationMatch(FunctionInvocation<Type> element, Declaration functionDeclaration) {
        FunctionType functionType = (FunctionType) functionDeclaration.getType();
        Parameters<Type> parameters = element.getParameters();
        ParameterTypes<Type> newParameterTypes = parameters.getSignature();
        if (parameters instanceof NamedParameters) {
            NamedParameterConversions<Type> parameterConversions = new NamedParameterConversions<>(functionType.getParameters());
            return makeDeclarationMatch(functionDeclaration, newParameterTypes, parameterConversions);
        } else {
            PositionalParameterConversions<Type> parameterConversions = new PositionalParameterConversions<>(functionType.getParameterTypes());
            return makeDeclarationMatch(functionDeclaration, newParameterTypes, parameterConversions);
        }
    }

    private static DeclarationMatch makeDeclarationMatch(Declaration declaration, ParameterTypes<Type> newParameterTypes, ParameterConversions<Type> parameterConversions) {
        return new DeclarationMatch(declaration, newParameterTypes, parameterConversions);
    }
}
