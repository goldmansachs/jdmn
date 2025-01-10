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
/*
 Copyright 2016.
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
   http://www.apache.org/licenses/LICENSE-2.0
 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an
 "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 KIND, either express or implied.  See the License for the
 specific language governing permissions and limitations
 under the License.
*/
package com.gs.dmn.transformation.native_.expression;

import com.gs.dmn.DRGElementReference;
import com.gs.dmn.ast.TDecision;
import com.gs.dmn.ast.TItemDefinition;
import com.gs.dmn.el.analysis.semantics.type.FunctionType;
import com.gs.dmn.el.analysis.semantics.type.ItemDefinitionType;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.el.analysis.syntax.ast.expression.function.Conversion;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.ConversionKind;
import com.gs.dmn.feel.analysis.syntax.ast.expression.literal.DateTimeLiteral;
import com.gs.dmn.runtime.Pair;

import java.util.List;

public interface NativeExpressionFactory {
    //
    // Constructor
    //
    String constructor(String className, String arguments, boolean isGeneric);

    default String defaultConstructor(String className) {
        return constructor(className, "", false);
    }

    String fluentConstructor(String className, String addMethods);

    String functionalInterfaceConstructor(String functionalInterface, String returnType, String applyMethod);

    //
    // Selection
    //
    String makeItemDefinitionAccessor(String javaType, String source, String memberName);

    String makeItemDefinitionSelectExpression(String source, String memberName, String memberType);

    String makeContextAccessor(String javaType, String source, String memberName);

    String makeCollectionMap(String source, String filter);

    String makeContextSelectExpression(String contextClassName, String source, String memberName);

    String prefixWithSelf(String text);

    //
    // Expressions
    //
    String makeCollectionLogicFilter(String source, String parameterName, String filter);

    String makeCollectionNumericFilter(String javaElementType, String source, String filter);

    String makeIfExpression(String condition, String thenExp, String elseExp);

    String makeNullCheck(String exp, String type);

    String makeForExpression(List<Pair<String, String>> domainIterators, String body);

    String makeSomeExpression(String list);

    String makeEveryExpression(String list);

    String makeInstanceOf(String value, Type type);

    //
    // Decision Table aggregators
    //
    String makeMinAggregator(String ruleOutputListVariableName, String decisionRuleOutputClassName, String outputClauseVariableName);

    String makeMaxAggregator(String ruleOutputListVariableName, String decisionRuleOutputClassName, String outputClauseVariableName);

    String makeCountAggregator(String ruleOutputListVariableName);

    String makeSumAggregator(String ruleOutputListVariableName, String decisionRuleOutputClassName, String outputClauseVariableName);

    //
    // Return
    //
    String makeReturn(String expression);

    //
    // Assignment
    //
    String makeVariableAssignment(String type, String name, String expression);

    String makeMemberAssignment(String complexTypeVariable, String memberName, String value);

    String makeContextMemberAssignment(String complexTypeVariable, String memberName, String value);

    //
    // Equality
    //
    String isNull(String exp);
    String makeEquality(String left, String right);

    //
    // Functions
    //
    String makeBuiltinFunctionInvocation(String javaFunctionCode, String argumentsText);

    String makeApplyInvocation(String javaFunctionCode, String argumentsText);

    String applyMethod(FunctionType functionType, String signature, boolean convertTypeToContext, String body);

    String makeExternalExecutorCall(String externalExecutorVariableName, String className, String methodName, String arguments, String returnJavaType);

    //
    // Parameters
    //
    String nullableParameterType(String parameterType);
    String nullableParameter(String parameterType, String parameterName);

    String parameterType(String parameterType);
    String parameter(String parameterType, String parameterName);

    String decisionConstructorParameter(DRGElementReference<TDecision> d);

    //
    // Literal
    //
    String numericLiteral(String lexeme);

    String stringLiteral(String lexeme);

    String booleanLiteral(String lexeme);

    String trueConstant();

    String falseConstant();

    String booleanValueLiteral(String lexeme);

    String trueValueConstant();

    String falseValueConstant();

    String dateTimeLiteral(DateTimeLiteral<Type> element);

    String nullLiteral();

    //
    // Conversions
    //
    String asList(Type elementType, String exp);

    String asElement(String exp);

    String convertListToElement(String expression, Type type);

    String convertElementToList(String expression, Type type);

    String makeListConversion(String javaExpression, ItemDefinitionType expectedElementType);

    String convertToItemDefinitionType(String expression, ItemDefinitionType type);

    String convertMethodName(TItemDefinition itemDefinition);

    String convertMethodName(ItemDefinitionType type);

    boolean isSerializable(Type type);

    String convertArgumentFromString(String paramName, Type type);

    String conversionFunction(Conversion<Type, ConversionKind> conversion, String javaType);

    //
    // Proto conversions
    //
    String convertProtoMember(String source, TItemDefinition parent, TItemDefinition member, boolean staticContext);

    String convertMemberToProto(String source, String sourceType, TItemDefinition member, boolean staticContext);

    String convertValueToProtoNativeType(String value, Type type, boolean staticContext);
}