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
import com.gs.dmn.feel.analysis.semantics.type.*;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import org.apache.commons.lang3.StringUtils;
import org.omg.spec.dmn._20191111.model.TDecision;
import org.omg.spec.dmn._20191111.model.TItemDefinition;

import java.util.List;

public class KotlinFactory extends JavaFactory implements NativeFactory {
    public KotlinFactory(BasicDMNToNativeTransformer transformer) {
        super(transformer);
    }

    //
    // Constructor
    //
    @Override
    public String constructor(String className, String arguments) {
        return String.format("%s(%s)", className.replace("?", ""), arguments);
    }

    @Override
    public String fluentConstructor(String className, String addMethods) {
        return String.format("%s()%s", className, addMethods);
    }

    @Override
    public String functionalInterfaceConstructor(String functionalInterface, String returnType, String applyMethod) {
        return String.format("%s<%s> { args -> %s }", functionalInterface, returnType, applyMethod);
    }

    //
    // Selection
    //
    @Override
    public String makeItemDefinitionAccessor(String javaType, String source, String memberName) {
        memberName = this.transformer.lowerCaseFirst(memberName);
        String nullableType = this.typeFactory.nullableType(javaType);
        return String.format("%s?.let({ it.%s as %s })", source, memberName, nullableType);
    }

    @Override
    public String makeItemDefinitionSelectExpression(String source, String memberName, String memberType) {
        String nullableType = this.typeFactory.nullableType(memberType);
        return String.format("(%s.%s) as %s", source, memberName, nullableType);
    }

    @Override
    public String makeContextAccessor(String javaType, String source, String memberName) {
        String contextClassName = this.transformer.contextClassName();
        String nullableType = this.typeFactory.nullableType(javaType);
        return String.format("((%s as %s).%s as %s)", source, contextClassName, this.transformer.contextGetter(memberName), nullableType);
    }

    @Override
    public String makeCollectionMap(String source, String filter) {
        return String.format("%s?.map({ x -> %s })", source, filter);
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
    public String makeCollectionNumericFilter(String javaElementType, String source, String filter) {
        String nullableType = this.typeFactory.nullableType(javaElementType);
        return String.format("(elementAt(%s, %s) as %s)", source, filter, nullableType);
    }

    @Override
    public String makeIfExpression(String condition, String thenExp, String elseExp) {
        return String.format("(if (booleanEqual(%s, %s)) %s else %s)", condition, trueConstant(), thenExp, elseExp);
    }

    @Override
    public String makeForExpression(List<Pair<String, String>> domainIterators, String body) {
        // Add code for each iterator
        StringBuilder result = new StringBuilder();
        for (Pair<String, String> pair: domainIterators) {
            result.append(String.format("%s?.stream()?.map({ %s -> ", pair.getLeft(), pair.getRight()));
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
        return String.format("booleanOr(%s?.toList())", list);
    }

    @Override
    public String makeEveryExpression(String list) {
        return String.format("booleanAnd(%s?.toList())", list);
    }

    //
    // Decision Table aggregators
    //
    @Override
    public String makeMinAggregator(String ruleOutputListVariableName, String decisionRuleOutputClassName, String outputClauseVariableName) {
        return String.format("min(%s?.map({ o -> (o as %s)?.%s }))",
                ruleOutputListVariableName, decisionRuleOutputClassName, outputClauseVariableName
        );
    }

    @Override
    public String makeMaxAggregator(String ruleOutputListVariableName, String decisionRuleOutputClassName, String outputClauseVariableName) {
        return String.format("max(%s?.map({ o -> (o as %s)?.%s }))",
                ruleOutputListVariableName, decisionRuleOutputClassName, outputClauseVariableName
        );
    }

    @Override
    public String makeCountAggregator(String ruleOutputListVariableName) {
        return "number(String.format(\"%d\", " + ruleOutputListVariableName + "?.size))";
    }

    @Override
    public String makeSumAggregator(String ruleOutputListVariableName, String decisionRuleOutputClassName, String outputClauseVariableName) {
        return String.format("sum(%s?.map({ o -> (o as %s)?.%s }))",
                ruleOutputListVariableName, decisionRuleOutputClassName, outputClauseVariableName
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
    public String makeApplyInvocation(String javaFunctionCode, String argumentsText) {
        return String.format("%s?.apply(%s)", javaFunctionCode, argumentsText);
    }

    @Override
    protected String applyMethod(String returnType, String signature, String parametersAssignment, String body) {
        return String.format(
                "%s" +
                        "%s",
                parametersAssignment, body);
    }

    @Override
    protected String makeLambdaParameterAssignment(String type, String name, int i) {
        String nullableType = this.typeFactory.nullableType(type);
        return String.format("val %s: %s = args[%s] as %s;", name, nullableType, i, nullableType);
    }

    @Override
    public String makeExternalExecutorCall(String externalExecutorVariableName, String className, String methodName, String arguments, String returnNativeType) {
        return String.format("%s.execute(\"%s\", \"%s\", arrayOf(%s)) as %s", externalExecutorVariableName, className, methodName, arguments, returnNativeType);
    }

    //
    // Parameters
    //
    @Override
    public String nullableParameter(String parameterType, String parameterName) {
        return String.format("%s: %s", parameterName, this.typeFactory.nullableType(parameterType));
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
    public String convertListToElement(String expression, Type type) {
        return String.format("%s", asElement(expression));
    }

    @Override
    public String asList(Type elementType, String exp) {
        if (StringUtils.isBlank(exp)) {
            String elementJavaType = this.typeFactory.nullableType(this.transformer.toNativeType(elementType));
            return String.format("asList<%s>()", elementJavaType);
        } else {
            return String.format("asList(%s)", exp);
        }
    }

    @Override
    public String makeListConversion(String javaExpression, ItemDefinitionType expectedElementType) {
        String elementConversion = convertToItemDefinitionType("x", expectedElementType);
        return String.format("%s?.map({ x -> %s })", javaExpression, elementConversion);
    }

    @Override
    public String convertDecisionArgumentFromString(String paramName, Type type) {
        if (FEELTypes.FEEL_PRIMITIVE_TYPES.contains(type)) {
            String conversionMethod = FEELTypes.FEEL_PRIMITIVE_TYPE_TO_JAVA_CONVERSION_FUNCTION.get(type);
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
            String javaType = this.typeFactory.nullableType(this.transformer.toNativeType(type));
            return String.format("%s?.let({ %s.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<%s>() {}) })", paramName, objectMapper(), javaType);
        } else {
            // Complex types
            String javaType = transformer.itemDefinitionNativeClassName(transformer.toNativeType(type));
            return String.format("%s?.let({ %s.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<%s>() {}) })", paramName, objectMapper(), javaType);
        }
    }

    @Override
    protected String listToElementConversionFunction(String javaType) {
        return String.format("asElement");
    }

    @Override
    public String convertMemberToProto(String source, String sourceType, TItemDefinition member, boolean staticContext) {
        Type memberType = this.transformer.toFEELType(member);
        String value = String.format("%s.%s", cast(sourceType, source), this.transformer.protoFieldName(member));
        return convertValueToProtoNativeType(value, memberType, staticContext);
    }

    @Override
    protected String itemDefinitionConversionLambda(String qNativeType, String convertFunction) {
        return String.format("e -> %s.%s(e)", qNativeType, convertFunction);
    }

    @Override
    protected String extractListMemberFromProto(String protoSource, String mapFunction, String qNativeType) {
        return cast(qNativeType, String.format("%s?.stream()?.map({%s})?.collect(java.util.stream.Collectors.toList())", protoSource, mapFunction));
    }

    @Override
    protected String convertListMemberToProto(String protoSource, String mapFunction) {
        return String.format("%s?.stream()?.map({%s})?.collect(java.util.stream.Collectors.toList())", protoSource, mapFunction);
    }

    @Override
    protected String toProtoNumber(String value) {
        return String.format("(if (%s == null) %s else %s!!.toDouble())", value, DEFAULT_PROTO_NUMBER, value);
    }

    @Override
    protected String toProtoBoolean(String value) {
        return String.format("(if (%s == null) %s else %s!!)", value, DEFAULT_PROTO_BOOLEAN, value);
    }

    @Override
    protected String toProtoString(String value) {
        return String.format("(if (%s == null) %s else %s!!)", value, DEFAULT_PROTO_STRING, value);
    }

    @Override
    protected String cast(String type, String value) {
        return StringUtils.isBlank(type) ? value : String.format("(%s as %s)", value, type);
    }
}
