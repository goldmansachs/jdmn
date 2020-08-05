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
package com.gs.dmn.transformation.native_;

import com.gs.dmn.DRGElementReference;
import com.gs.dmn.feel.analysis.semantics.type.FunctionType;
import com.gs.dmn.feel.analysis.semantics.type.ItemDefinitionType;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.Conversion;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.transformation.native_.expression.NativeExpressionFactory;
import com.gs.dmn.transformation.native_.statement.*;
import org.omg.spec.dmn._20180521.model.TDecision;
import org.omg.spec.dmn._20180521.model.TItemDefinition;

import java.util.List;

public abstract class NativeFactory implements NativeExpressionFactory, NativeStatementFactory {
    private final NativeExpressionFactory expressionFactory;
    private final NativeStatementFactory statementFactory;

    public NativeFactory(NativeExpressionFactory expressionFactory, NativeStatementFactory statementFactory) {
        this.expressionFactory = expressionFactory;
        this.statementFactory = statementFactory;
    }

    //
    // Constructor
    //
    @Override
    public String constructor(String className, String arguments) {
        return expressionFactory.constructor(className, arguments);
    }

    @Override
    public String defaultConstructor(String className) {
        return expressionFactory.defaultConstructor(className);
    }

    @Override
    public String fluentConstructor(String className, String addMethods) {
        return expressionFactory.fluentConstructor(className, addMethods);
    }

    @Override
    public String functionalInterfaceConstructor(String functionalInterface, String returnType, String applyMethod) {
        return expressionFactory.functionalInterfaceConstructor(functionalInterface, returnType, applyMethod);
    }

    //
    // Selection
    //
    @Override
    public String makeItemDefinitionAccessor(String javaType, String source, String memberName) {
        return expressionFactory.makeItemDefinitionAccessor(javaType, source, memberName);
    }

    @Override
    public String makeItemDefinitionSelectExpression(String source, String memberName, String memberType) {
        return expressionFactory.makeItemDefinitionSelectExpression(source, memberName, memberType);
    }

    @Override
    public String makeContextAccessor(String javaType, String source, String memberName) {
        return expressionFactory.makeContextAccessor(javaType, source, memberName);
    }

    @Override
    public String makeCollectionMap(String source, String filter) {
        return expressionFactory.makeCollectionMap(source, filter);
    }

    @Override
    public String makeContextSelectExpression(String contextClassName, String source, String memberName) {
        return expressionFactory.makeContextSelectExpression(contextClassName, source, memberName);
    }


    //
    // Expressions
    //
    @Override
    public String makeCollectionLogicFilter(String source, String parameterName, String filter) {
        return expressionFactory.makeCollectionLogicFilter(source, parameterName, filter);
    }

    @Override
    public String makeCollectionNumericFilter(String javaElementType, String source, String filter) {
        return expressionFactory.makeCollectionNumericFilter(javaElementType, source, filter);
    }

    @Override
    public String makeIfExpression(String condition, String thenExp, String elseExp) {
        return expressionFactory.makeIfExpression(condition, thenExp, elseExp);
    }

    @Override
    public String makeForExpression(List<Pair<String, String>> domainIterators, String body) {
        return expressionFactory.makeForExpression(domainIterators, body);
    }

    @Override
    public String makeSomeExpression(String list) {
        return expressionFactory.makeSomeExpression(list);
    }

    @Override
    public String makeEveryExpression(String list) {
        return expressionFactory.makeEveryExpression(list);
    }

    //
    // Decision Table aggregators
    //
    @Override
    public String makeMinAggregator(String ruleOutputListVariableName, String decisionRuleOutputClassName, String outputClauseVariableName) {
        return expressionFactory.makeMinAggregator(ruleOutputListVariableName, decisionRuleOutputClassName, outputClauseVariableName);
    }

    @Override
    public String makeMaxAggregator(String ruleOutputListVariableName, String decisionRuleOutputClassName, String outputClauseVariableName) {
        return expressionFactory.makeMaxAggregator(ruleOutputListVariableName, decisionRuleOutputClassName, outputClauseVariableName);
    }

    @Override
    public String makeCountAggregator(String ruleOutputListVariableName) {
        return expressionFactory.makeCountAggregator(ruleOutputListVariableName);
    }

    @Override
    public String makeSumAggregator(String ruleOutputListVariableName, String decisionRuleOutputClassName, String outputClauseVariableName) {
        return expressionFactory.makeSumAggregator(ruleOutputListVariableName, decisionRuleOutputClassName, outputClauseVariableName);
    }

    //
    // Return
    //
    @Override
    public String makeReturn(String expression) {
        return expressionFactory.makeReturn(expression);
    }

    //
    // Assignment
    //
    @Override
    public String makeVariableAssignment(String type, String name, String expression) {
        return expressionFactory.makeVariableAssignment(type, name, expression);
    }

    @Override
    public String makeMemberAssignment(String complexTypeVariable, String memberName, String value) {
        return expressionFactory.makeMemberAssignment(complexTypeVariable, memberName, value);
    }

    @Override
    public String makeContextMemberAssignment(String complexTypeVariable, String memberName, String value) {
        return expressionFactory.makeContextMemberAssignment(complexTypeVariable, memberName, value);
    }

    //
    // Equality
    //
    @Override
    public String makeEquality(String left, String right) {
        return expressionFactory.makeEquality(left, right);
    }

    //
    // Functions
    //
    @Override
    public String makeApplyInvocation(String javaFunctionCode, String argumentsText) {
        return expressionFactory.makeApplyInvocation(javaFunctionCode, argumentsText);
    }

    @Override
    public String applyMethod(FunctionType functionType, String signature, boolean convertTypeToContext, String body) {
        return expressionFactory.applyMethod(functionType, signature, convertTypeToContext, body);
    }

    @Override
    public String makeExternalExecutorCall(String externalExecutorVariableName, String className, String methodName, String arguments, String returnJavaType) {
        return expressionFactory.makeExternalExecutorCall(externalExecutorVariableName, className, methodName, arguments, returnJavaType);
    }

    //
    // Parameters
    //
    @Override
    public String nullableParameter(String parameterType, String parameterName) {
        return expressionFactory.nullableParameter(parameterType, parameterName);
    }

    @Override
    public String parameter(String parameterType, String parameterName) {
        return expressionFactory.parameter(parameterType, parameterName);
    }

    @Override
    public String decisionConstructorParameter(DRGElementReference<TDecision> d) {
        return expressionFactory.decisionConstructorParameter(d);
    }

    //
    // Literal
    //
    @Override
    public String trueConstant() {
        return expressionFactory.trueConstant();
    }

    @Override
    public String falseConstant() {
        return expressionFactory.falseConstant();
    }

    //
    // Conversions
    //
    @Override
    public String convertListToElement(String expression, Type type) {
        return expressionFactory.convertListToElement(expression, type);
    }

    @Override
    public String asList(Type elementType, String exp) {
        return expressionFactory.asList(elementType, exp);
    }

    @Override
    public String asElement(String exp) {
        return expressionFactory.asElement(exp);
    }

    @Override
    public String convertElementToList(String expression, Type type) {
        return expressionFactory.convertElementToList(expression, type);
    }

    @Override
    public String makeListConversion(String javaExpression, ItemDefinitionType expectedElementType) {
        return expressionFactory.makeListConversion(javaExpression, expectedElementType);
    }

    @Override
    public String convertToItemDefinitionType(String expression, ItemDefinitionType type) {
        return expressionFactory.convertToItemDefinitionType(expression, type);
    }

    @Override
    public String convertMethodName(TItemDefinition itemDefinition) {
        return expressionFactory.convertMethodName(itemDefinition);
    }

    @Override
    public String convertMethodName(ItemDefinitionType type) {
        return expressionFactory.convertMethodName(type);
    }

    @Override
    public String convertDecisionArgumentFromString(String paramName, Type type) {
        return expressionFactory.convertDecisionArgumentFromString(paramName, type);
    }

    @Override
    public String conversionFunction(Conversion conversion, String javaType) {
        return expressionFactory.conversionFunction(conversion, javaType);
    }

    //
    // Simple statements
    //
    @Override
    public NopStatement makeNopStatement() {
        return statementFactory.makeNopStatement();
    }

    @Override
    public ExpressionStatement makeExpressionStatement(String text, Type type) {
        return statementFactory.makeExpressionStatement(text, type);
    }

    @Override
    public CommentStatement makeCommentStatement(String message) {
        return statementFactory.makeCommentStatement(message);
    }

    @Override
    public ExpressionStatement makeAssignmentStatement(String nativeType, String variableName, String expression, Type type) {
        return statementFactory.makeAssignmentStatement(nativeType, variableName, expression, type);
    }

    @Override
    public Statement makeReturnStatement(String expression, Type type) {
        return statementFactory.makeReturnStatement(expression, type);
    }

    //
    // Compound statements
    //
    @Override
    public CompoundStatement makeCompoundStatement() {
        return statementFactory.makeCompoundStatement();
    }
}
