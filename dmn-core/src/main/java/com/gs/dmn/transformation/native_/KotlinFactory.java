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
import com.gs.dmn.ast.TItemDefinition;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.el.analysis.semantics.type.ItemDefinitionType;
import com.gs.dmn.el.analysis.semantics.type.ListType;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.semantics.type.BooleanType;
import com.gs.dmn.feel.analysis.semantics.type.FEELTypes;
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
    public String constructor(String className, String arguments) {
        return "%s(%s)".formatted(className.replace("?", ""), arguments);
    }

    @Override
    public String fluentConstructor(String className, String addMethods) {
        return "%s()%s".formatted(className, addMethods);
    }

    @Override
    public String functionalInterfaceConstructor(String functionalInterface, String returnType, String applyMethod) {
        return "%s<%s> { %s -> %s }".formatted(functionalInterface, returnType, transformer.lambdaArgsVariableName(), applyMethod);
    }

    //
    // Selection
    //
    @Override
    public String makeItemDefinitionAccessor(String javaType, String source, String memberName) {
        memberName = this.transformer.lowerCaseFirst(memberName);
        String nullableType = this.typeFactory.nullableType(javaType);
        return "%s?.let({ it.%s as %s })".formatted(source, memberName, nullableType);
    }

    @Override
    public String makeItemDefinitionSelectExpression(String source, String memberName, String memberType) {
        String nullableType = this.typeFactory.nullableType(memberType);
        return "(%s.%s) as %s".formatted(source, memberName, nullableType);
    }

    @Override
    public String makeContextAccessor(String javaType, String source, String memberName) {
        String contextClassName = this.transformer.contextClassName();
        String nullableType = this.typeFactory.nullableType(javaType);
        return "((%s as %s).%s as %s)".formatted(source, contextClassName, this.transformer.contextGetter(memberName), nullableType);
    }

    @Override
    public String makeCollectionMap(String source, String filter) {
        return "%s?.map({ x -> %s })".formatted(source, filter);
    }

    @Override
    public String makeContextSelectExpression(String contextClassName, String source, String memberName) {
        return "((%s) as %s).get(\"%s\", asList())".formatted(source, contextClassName, memberName);
    }

    //
    // Expressions
    //
    @Override
    public String makeCollectionLogicFilter(String source, String parameterName, String filter) {
        return "%s?.filter({ %s -> %s })".formatted(source, parameterName, filter);
    }

    @Override
    public String makeCollectionNumericFilter(String javaElementType, String source, String filter) {
        String nullableType = this.typeFactory.nullableType(javaElementType);
        String args = "%s, %s".formatted(source, filter);
        String call = this.makeBuiltinFunctionInvocation("elementAt", args);
        return "(%s as %s)".formatted(call, nullableType);
    }

    @Override
    public String makeIfExpression(String condition, String thenExp, String elseExp) {
        String args = "%s, %s".formatted(condition, trueConstant());
        String call = this.makeBuiltinFunctionInvocation("booleanEqual", args);
        return "(if (%s) %s else %s)".formatted(call, thenExp, elseExp);
    }

    @Override
    public String makeForExpression(List<Pair<String, String>> domainIterators, String body) {
        // Add code for each iterator
        StringBuilder result = new StringBuilder();
        for (Pair<String, String> pair: domainIterators) {
            result.append("%s?.stream()?.map({ %s -> ".formatted(pair.getLeft(), pair.getRight()));
        }
        // Add body
        result.append(body);
        // Close parenthesis
        for (int i = 0; i < domainIterators.size(); i++) {
            result.append(" })");
        }
        // Flatten nested streams
        for (int i = 0; i < domainIterators.size() - 1; i++) {
            result.append("?.flatMap({ x -> x })");
        }
        // Collect result
        result.append("?.collect(Collectors.toList())");
        return result.toString();
    }

    @Override
    public String makeSomeExpression(String list) {
        String args = "%s?.toList()".formatted(list);
        return makeBuiltinFunctionInvocation("booleanOr", args);
    }

    @Override
    public String makeEveryExpression(String list) {
        String args = "%s?.toList()".formatted(list);
        return makeBuiltinFunctionInvocation("booleanAnd", args);
    }

    //
    // Decision Table aggregators
    //
    @Override
    public String makeMinAggregator(String ruleOutputListVariableName, String decisionRuleOutputClassName, String outputClauseVariableName) {
        return "min(%s?.map({ o -> (o as %s)?.%s }))".formatted(
            ruleOutputListVariableName, decisionRuleOutputClassName, outputClauseVariableName
        );
    }

    @Override
    public String makeMaxAggregator(String ruleOutputListVariableName, String decisionRuleOutputClassName, String outputClauseVariableName) {
        return "max(%s?.map({ o -> (o as %s)?.%s }))".formatted(
            ruleOutputListVariableName, decisionRuleOutputClassName, outputClauseVariableName
        );
    }

    @Override
    public String makeCountAggregator(String ruleOutputListVariableName) {
        return "number(String.format(\"%d\", " + ruleOutputListVariableName + "?.size))";
    }

    @Override
    public String makeSumAggregator(String ruleOutputListVariableName, String decisionRuleOutputClassName, String outputClauseVariableName) {
        return "sum(%s?.map({ o -> (o as %s)?.%s }))".formatted(
            ruleOutputListVariableName, decisionRuleOutputClassName, outputClauseVariableName
        );
    }

    //
    // Return
    //
    @Override
    public String makeReturn(String expression) {
        return "return %s".formatted(expression);
    }

    //
    // Assignment
    //
    @Override
    public String makeVariableAssignment(String type, String name, String expression) {
        String nullableType = this.typeFactory.nullableType(type);
        return "val %s: %s = %s as %s".formatted(name, nullableType, expression, nullableType);
    }

    @Override
    public String makeMemberAssignment(String complexTypeVariable, String memberName, String value) {
        return "%s?.%s = %s".formatted(complexTypeVariable, memberName, value);
    }

    @Override
    public String makeContextMemberAssignment(String complexTypeVariable, String memberName, String value) {
        return "%s?.%s %s);".formatted(complexTypeVariable, this.transformer.contextSetter(memberName), value);
    }

    //
    // Functions
    //
    @Override
    public String makeApplyInvocation(String javaFunctionCode, String argumentsText) {
        return "%s?.apply(%s)".formatted(javaFunctionCode, argumentsText);
    }

    @Override
    protected String applyMethod(String returnType, String signature, String parametersAssignment, String body) {
        if (StringUtils.isEmpty(parametersAssignment)) {
            return body;
        } else {
            return "%s %s".formatted(parametersAssignment, body);
        }
    }

    @Override
    protected String makeLambdaParameterAssignment(String type, String name, int i) {
        String nullableType = this.typeFactory.nullableType(type);
        return "val %s: %s = %s[%s] as %s;".formatted(name, nullableType, transformer.lambdaArgsVariableName(), i, nullableType);
    }

    @Override
    public String makeExternalExecutorCall(String externalExecutorVariableName, String className, String methodName, String arguments, String returnNativeType) {
        return "%s.execute(\"%s\", \"%s\", arrayOf(%s)) as %s".formatted(externalExecutorVariableName, className, methodName, arguments, returnNativeType);
    }

    //
    // Parameters
    //
    @Override
    public String nullableParameterType(String parameterType) {
        return "%s".formatted(this.typeFactory.nullableType(parameterType));
    }

    @Override
    public String nullableParameter(String parameterType, String parameterName) {
        return "%s: %s".formatted(parameterName, this.typeFactory.nullableType(parameterType));
    }

    @Override
    public String parameterType(String parameterType) {
        return "%s".formatted(parameterType);
    }

    @Override
    public String parameter(String parameterType, String parameterName) {
        return "%s: %s".formatted(parameterName, parameterType);
    }

    @Override
    public String decisionConstructorParameter(DRGElementReference<TDecision> d) {
        return "val %s : %s = %s".formatted(this.transformer.drgElementReferenceVariableName(d), this.transformer.qualifiedName(d), defaultConstructor(this.transformer.qualifiedName(d)));
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
            String elementJavaType = this.typeFactory.nullableType(this.transformer.toNativeType(elementType));
            return "asList<%s>()".formatted(elementJavaType);
        } else {
            return "asList(%s)".formatted(exp);
        }
    }

    @Override
    public String convertListToElement(String expression, Type type) {
        return "%s".formatted(asElement(expression));
    }

    @Override
    public String makeListConversion(String javaExpression, ItemDefinitionType expectedElementType) {
        String elementConversion = convertToItemDefinitionType("x", expectedElementType);
        return "%s?.map({ x -> %s })".formatted(javaExpression, elementConversion);
    }

    @Override
    public String convertArgumentFromString(String paramName, Type type) {
        type = Type.extractTypeFromConstraint(type);
        if (com.gs.dmn.el.analysis.semantics.type.Type.isNull(type)) {
            if (transformer.isStrongTyping()) {
                throw new DMNRuntimeException("Cannot convert String to type '%s'".formatted(type));
            } else {
                return paramName;
            }
        }

        if (FEELTypes.FEEL_PRIMITIVE_TYPES.contains(type)) {
            String conversionMethod = FEELTypes.FEEL_PRIMITIVE_TYPE_TO_JAVA_CONVERSION_FUNCTION.get(type);
            if (conversionMethod != null) {
                return "%s?.let({ %s(it) })".formatted(paramName, conversionMethod);
            } else if (type == StringType.STRING) {
                return paramName;
            } else if (type == BooleanType.BOOLEAN) {
                return "%s?.let({ it.toBoolean() })".formatted(paramName);
            } else {
                throw new DMNRuntimeException("Cannot convert String to type '%s'".formatted(type));
            }
        } else if (type instanceof ListType) {
            String javaType = this.typeFactory.nullableType(this.transformer.toNativeType(type));
            return "%s?.let({ %s.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<%s>() {}) })".formatted(paramName, objectMapper(), javaType);
        } else {
            // Complex types
            String javaType = transformer.itemDefinitionNativeClassName(transformer.toNativeType(type));
            return "%s?.let({ %s.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<%s>() {}) })".formatted(paramName, objectMapper(), javaType);
        }
    }

    @Override
    protected String listToElementConversionFunction(String javaType) {
        return "asElement";
    }

    @Override
    public String convertMemberToProto(String source, String sourceType, TItemDefinition member, boolean staticContext) {
        Type memberType = this.transformer.toFEELType(member);
        String value = "%s.%s".formatted(cast(sourceType, source), this.transformer.protoFieldName(member));
        return convertValueToProtoNativeType(value, memberType, staticContext);
    }

    @Override
    protected String itemDefinitionConversionLambda(String qNativeType, String convertFunction) {
        return "e -> %s.%s(e)".formatted(qNativeType, convertFunction);
    }

    @Override
    protected String extractListMemberFromProto(String protoSource, String mapFunction, String qNativeType) {
        return cast(qNativeType, "%s?.stream()?.map({%s})?.collect(java.util.stream.Collectors.toList())".formatted(protoSource, mapFunction));
    }

    @Override
    protected String convertListMemberToProto(String protoSource, String mapFunction) {
        return "%s?.stream()?.map({%s})?.collect(java.util.stream.Collectors.toList())".formatted(protoSource, mapFunction);
    }

    @Override
    protected String toProtoNumber(String value) {
        return "(if (%s == null) %s else %s!!.toDouble())".formatted(value, DEFAULT_PROTO_NUMBER, value);
    }

    @Override
    protected String toProtoBoolean(String value) {
        return "(if (%s == null) %s else %s!!)".formatted(value, DEFAULT_PROTO_BOOLEAN, value);
    }

    @Override
    protected String toProtoString(String value) {
        return "(if (%s == null) %s else %s!!)".formatted(value, DEFAULT_PROTO_STRING, value);
    }

    @Override
    protected String cast(String type, String value) {
        return StringUtils.isBlank(type) ? value : "(%s as %s)".formatted(value, type);
    }
}
