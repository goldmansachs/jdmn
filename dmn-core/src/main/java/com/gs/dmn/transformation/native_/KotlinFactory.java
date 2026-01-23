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
import com.gs.dmn.ast.TDecision;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.el.analysis.semantics.type.ItemDefinitionType;
import com.gs.dmn.el.analysis.semantics.type.ListType;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.semantics.type.BooleanType;
import com.gs.dmn.feel.analysis.semantics.type.FEELType;
import com.gs.dmn.feel.analysis.semantics.type.StringType;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class KotlinFactory extends JavaFactory implements NativeFactory {
    public KotlinFactory(BasicDMNToNativeTransformer<Type, DMNContext> transformer) {
        super(transformer);
    }

    //
    // Constructor
    //
    @Override
    public String constructor(String className, String arguments, boolean isGeneric) {
        return String.format("%s(%s)", className.replace("?", ""), arguments);
    }

    @Override
    public String fluentConstructor(String className, String addMethods) {
        return String.format("%s()%s", className, addMethods);
    }

    @Override
    public String functionalInterfaceConstructor(String functionalInterface, String returnType, String applyMethod) {
        return String.format("%s<%s> { %s -> %s }", functionalInterface, returnType, transformer.lambdaArgsVariableName(), applyMethod);
    }

    //
    // Selection
    //
    @Override
    public String makeItemDefinitionAccessor(String nativeType, String source, String memberName) {
        memberName = this.transformer.lowerCaseFirst(memberName);
        String nullableType = this.typeFactory.nullableType(nativeType);
        return String.format("%s?.let({ it.%s as %s })", source, memberName, nullableType);
    }

    @Override
    public String makeContextAccessor(String nativeType, String source, String memberName) {
        String contextClassName = this.transformer.contextClassName();
        String nullableType = this.typeFactory.nullableType(nativeType);
        return String.format("((%s as %s).%s as %s)", source, contextClassName, this.transformer.contextGetter(memberName), nullableType);
    }

    @Override
    public String makeCollectionMap(String source, String filter) {
        return String.format("%s?.map({ %s -> %s })", source, MAP_ITERATOR, filter);
    }

    @Override
    public String makeContextSelectExpression(String contextClassName, String source, String memberName) {
        return String.format("((%s) as %s).get(\"%s\", asList())", source, contextClassName, memberName);
    }

    //
    // Expressions
    //
    @Override
    public String makeCollectionLogicFilter(String source, String parameterName, String filter) {
        return String.format("%s?.filter({ %s -> %s })", source, parameterName, filter);
    }

    @Override
    public String makeCollectionNumericFilter(String nativeElementType, String source, String filter) {
        String nullableType = this.typeFactory.nullableType(nativeElementType);
        String args = String.format("%s, %s", source, filter);
        String call = this.makeBuiltinFunctionInvocation("elementAt", args);
        return String.format("(%s as %s)", call, nullableType);
    }

    @Override
    public String makeIfExpression(String condition, String thenExp, String elseExp) {
        String args = String.format("%s, %s", condition, trueConstant());
        String call = this.makeBuiltinFunctionInvocation("booleanEqual", args);
        return String.format("(if (%s) %s else %s)", call, thenExp, elseExp);
    }

    @Override
    public String makeForExpression(List<Pair<String, String>> domainIterators, String body) {
        // Add code for each iterator
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < domainIterators.size(); i++) {
            Pair<String, String> pair = domainIterators.get(i);
            if (i != domainIterators.size() - 1) {
                // Flatten nested streams
                result.append(String.format("%s?.stream()?.flatMap({ %s -> ", pair.getLeft(), pair.getRight()));
            } else {
                result.append(String.format("%s?.stream()?.map({ %s -> ", pair.getLeft(), pair.getRight()));
            }
        }
        // Add body
        result.append(body);
        // Close parenthesis
        result.append(" })".repeat(domainIterators.size()));
        // Collect result
        result.append("?.collect(Collectors.toList())");
        return result.toString();
    }

    @Override
    public String makeSomeExpression(String list) {
        String args = String.format("%s?.toList()", list);
        return makeBuiltinFunctionInvocation("booleanOr", args);
    }

    @Override
    public String makeEveryExpression(String list) {
        String args = String.format("%s?.toList()", list);
        return makeBuiltinFunctionInvocation("booleanAnd", args);
    }

    //
    // Decision Table aggregators
    //
    @Override
    public String makeMinAggregator(String ruleOutputListVariableName, String decisionRuleOutputClassName, String outputClauseVariableName) {
        return String.format("min(%s?.map({ %s -> (%s as %s)?.%s }))",
                ruleOutputListVariableName, MAP_ITERATOR, MAP_ITERATOR, decisionRuleOutputClassName, outputClauseVariableName
        );
    }

    @Override
    public String makeMaxAggregator(String ruleOutputListVariableName, String decisionRuleOutputClassName, String outputClauseVariableName) {
        return String.format("max(%s?.map({ %s -> (%s as %s)?.%s }))",
                ruleOutputListVariableName, MAP_ITERATOR, MAP_ITERATOR, decisionRuleOutputClassName, outputClauseVariableName
        );
    }

    @Override
    public String makeCountAggregator(String ruleOutputListVariableName) {
        return "number(String.format(\"%d\", " + ruleOutputListVariableName + "?.size))";
    }

    @Override
    public String makeSumAggregator(String ruleOutputListVariableName, String decisionRuleOutputClassName, String outputClauseVariableName) {
        return String.format("sum(%s?.map({ %s -> (%s as %s)?.%s }))",
                ruleOutputListVariableName, MAP_ITERATOR, MAP_ITERATOR, decisionRuleOutputClassName, outputClauseVariableName
        );
    }

    //
    // Return
    //
    @Override
    public String makeReturn(String expression) {
        return String.format("return %s", expression);
    }

    //
    // Assignment
    //
    @Override
    public String makeVariableAssignment(String type, String name, String expression) {
        String nullableType = this.typeFactory.nullableType(type);
        return String.format("val %s: %s = %s as %s", name, nullableType, expression, nullableType);
    }

    @Override
    public String makeMemberAssignment(String complexTypeVariable, String memberName, String value) {
        return String.format("%s?.%s = %s", complexTypeVariable, memberName, value);
    }

    @Override
    public String makeContextMemberAssignment(String complexTypeVariable, String memberName, String value) {
        return String.format("%s?.%s %s);", complexTypeVariable, this.transformer.contextSetter(memberName), value);
    }

    //
    // Functions
    //
    @Override
    public String makeApplyInvocation(String nativeFunctionCode, String argumentsText) {
        return String.format("%s?.apply(%s)", nativeFunctionCode, argumentsText);
    }

    @Override
    protected String applyMethod(String returnType, String signature, String parametersAssignment, String body) {
        if (StringUtils.isEmpty(parametersAssignment)) {
            return body;
        } else {
            return String.format("%s %s", parametersAssignment, body);
        }
    }

    @Override
    protected String makeLambdaParameterAssignment(String type, String name, int i) {
        String nullableType = this.typeFactory.nullableType(type);
        return String.format("val %s: %s = %s[%s] as %s;", name, nullableType, transformer.lambdaArgsVariableName(), i, nullableType);
    }

    @Override
    public String makeExternalExecutorCall(String externalExecutorVariableName, String className, String methodName, String arguments, String returnNativeType) {
        return String.format("%s.execute(\"%s\", \"%s\", arrayOf(%s)) as %s", externalExecutorVariableName, className, methodName, arguments, returnNativeType);
    }

    //
    // Parameters
    //
    @Override
    public String nullableParameterType(String parameterType) {
        return String.format("%s", this.typeFactory.nullableType(parameterType));
    }

    @Override
    public String nullableParameter(String parameterType, String parameterName) {
        return String.format("%s: %s", parameterName, this.typeFactory.nullableType(parameterType));
    }

    @Override
    public String parameterType(String parameterType) {
        return String.format("%s", parameterType);
    }

    @Override
    public String parameter(String parameterType, String parameterName) {
        return String.format("%s: %s", parameterName, parameterType);
    }

    @Override
    public String decisionConstructorParameter(DRGElementReference<TDecision> d) {
        return String.format("val %s : %s = %s", this.transformer.drgElementReferenceVariableName(d), this.transformer.qualifiedName(d), defaultConstructor(this.transformer.qualifiedName(d)));
    }

    //
    // Literal
    //
    @Override
    public String trueConstant() {
        return "true";
    }

    @Override
    public String falseConstant() {
        return "false";
    }

    //
    // Conversions
    //
    @Override
    public String asList(Type elementType, String exp) {
        if (StringUtils.isBlank(exp)) {
            String elementNativeType = this.typeFactory.nullableType(this.transformer.toNativeType(elementType));
            return String.format("asList<%s>()", elementNativeType);
        } else {
            return String.format("asList(%s)", exp);
        }
    }

    @Override
    public String convertListToElement(String expression, Type type) {
        return String.format("%s", asElement(expression));
    }

    @Override
    public String convertToListOfItemDefinitionType(String nativeExpression, ItemDefinitionType elementType) {
        String elementConversion = convertToItemDefinitionType(MAP_ITERATOR, elementType);
        return String.format("%s?.map({ %s -> %s })", nativeExpression, MAP_ITERATOR, elementConversion);
    }

    @Override
    public String convertArgumentFromString(String paramName, Type type) {
        type = Type.extractTypeFromConstraint(type);
        if (com.gs.dmn.el.analysis.semantics.type.Type.isNull(type)) {
            if (transformer.isStrongTyping()) {
                throw new DMNRuntimeException(String.format("Cannot convert String to type '%s'", type));
            } else {
                return paramName;
            }
        }

        if (FEELType.isPrimitiveType(type)) {
            String conversionMethod = FEELType.FEEL_PRIMITIVE_TYPE_TO_NATIVE_CONVERSION_FUNCTION.get(type);
            if (conversionMethod != null) {
                return String.format("%s?.let({ %s(it) })", paramName, conversionMethod);
            } else if (type == StringType.STRING) {
                return paramName;
            } else if (type == BooleanType.BOOLEAN) {
                return String.format("%s?.let({ it.toBoolean() })", paramName);
            } else {
                throw new DMNRuntimeException(String.format("Cannot convert String to type '%s'", type));
            }
        } else if (type instanceof ListType) {
            String nativeType = this.typeFactory.nullableType(this.transformer.toNativeType(type));
            return String.format("%s?.let({ %s.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<%s>() {}) })", paramName, objectMapper(), nativeType);
        } else {
            // Complex types
            String nativeType = transformer.itemDefinitionNativeClassName(transformer.toNativeType(type));
            return String.format("%s?.let({ %s.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<%s>() {}) })", paramName, objectMapper(), nativeType);
        }
    }

    @Override
    protected String itemDefinitionConversionLambda(String qNativeType, String convertFunction) {
        return String.format("e -> %s.%s(e)", qNativeType, convertFunction);
    }

    @Override
    protected String cast(String type, String value) {
        return StringUtils.isBlank(type) ? value : String.format("(%s as %s)", value, type);
    }
}
