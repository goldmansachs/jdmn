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
package com.gs.dmn.feel.synthesis.expression;

import com.gs.dmn.DRGElementReference;
import com.gs.dmn.feel.analysis.semantics.type.*;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.Conversion;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.ConversionKind;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.serialization.JsonSerializer;
import com.gs.dmn.transformation.basic.BasicDMN2JavaTransformer;
import org.apache.commons.lang3.StringUtils;
import org.omg.spec.dmn._20180521.model.TDecision;
import org.omg.spec.dmn._20180521.model.TItemDefinition;

import java.util.ArrayList;
import java.util.List;

public class KotlinExpressionFactory implements NativeExpressionFactory {
    private final BasicDMN2JavaTransformer dmnTransformer;

    public KotlinExpressionFactory(BasicDMN2JavaTransformer basicDMN2JavaTransformer) {
        this.dmnTransformer = basicDMN2JavaTransformer;
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
        memberName = this.dmnTransformer.lowerCaseFirst(memberName);
        String nullableType = nullableType(javaType);
        return String.format("%s?.let({ it.%s as %s })", source, memberName, nullableType);
    }

    @Override
    public String makeItemDefinitionSelectExpression(String source, String memberName, String memberType) {
        String nullableType = nullableType(memberType);
        return String.format("(%s.%s) as %s", source, memberName, nullableType);
    }

    @Override
    public String makeContextAccessor(String javaType, String source, String memberName) {
        String contextClassName = this.dmnTransformer.contextClassName();
        String nullableType = nullableType(javaType);
        return String.format("((%s as %s).%s as %s)", source, contextClassName, this.dmnTransformer.contextGetter(memberName), nullableType);
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

    public String makeCollectionNumericFilter(String javaElementType, String source, String filter) {
        String nullableType = nullableType(javaElementType);
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
        String nullableType = nullableType(type);
        return String.format("val %s: %s = %s as %s", name, nullableType, expression, nullableType);
    }

    @Override
    public String makeMemberAssignment(String complexTypeVariable, String memberName, String value) {
        return String.format("%s?.%s = %s", complexTypeVariable, memberName, value);
    }

    //
    // Equality
    //
    @Override
    public String makeEquality(String left, String right) {
        return String.format("%s == %s", left, right);
    }

    //
    // Functions
    //
    @Override
    public String makeApplyInvocation(String javaFunctionCode, String argumentsText) {
        return String.format("%s?.apply(%s)", javaFunctionCode, argumentsText);
    }

    @Override
    public String applyMethod(FunctionType functionType, String signature, boolean convertTypeToContext, String body) {
        String returnType = dmnTransformer.toJavaType(dmnTransformer.convertType(functionType.getReturnType(), convertTypeToContext));
        String parametersAssignment = parametersAssignment(functionType.getParameters(), convertTypeToContext);
        return applyMethod(returnType, signature, parametersAssignment, body);
    }

    protected String applyMethod(String returnType, String signature, String parametersAssignment, String body) {
        return String.format(
                "%s" +
                "%s",
                parametersAssignment, body);
    }

    private String parametersAssignment(List<FormalParameter> formalParameters, boolean convertTypeToContext) {
        List<String> parameters = new ArrayList<>();
        for(int i = 0; i< formalParameters.size(); i++) {
            FormalParameter p = formalParameters.get(i);
            String type = dmnTransformer.toJavaType(dmnTransformer.convertType(p.getType(), convertTypeToContext));
            String name = dmnTransformer.javaFriendlyVariableName(p.getName());
            parameters.add(makeLambdaParameterAssignment(type, name, i));
        }
        return String.join(" ", parameters);
    }

    private String makeLambdaParameterAssignment(String type, String name, int i) {
        String nullableType = nullableType(type);
        return String.format("val %s: %s = args[%s] as %s;", name, nullableType, i, nullableType);
    }

    //
    // Parameters
    //
    @Override
    public String nullableParameter(String parameterType, String parameterName) {
        return String.format("%s: %s", parameterName, nullableType(parameterType));
    }

    @Override
    public String parameter(String parameterType, String parameterName) {
        return String.format("%s: %s", parameterName, parameterType);
    }

    @Override
    public String decisionConstructorParameter(DRGElementReference<TDecision> d) {
        return String.format("val %s : %s = %s", this.dmnTransformer.drgElementVariableName(d), this.dmnTransformer.qualifiedName(d), defaultConstructor(this.dmnTransformer.qualifiedName(d)));
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
    // Conversion
    //
    @Override
    public String convertListToElement(String expression, Type type) {
        return String.format("%s", asElement(expression));
    }

    public String asList(Type elementType, String exp) {
        if (StringUtils.isBlank(exp)) {
            String elementJavaType = nullableType(this.dmnTransformer.toJavaType(elementType));
            return String.format("asList<%s>()", elementJavaType);
        } else {
            return String.format("asList(%s)", exp);
        }
    }

    public String asElement(String exp) {
        return String.format("asElement(%s)", exp);
    }

    public String convertElementToList(String expression, Type type) {
        return String.format("%s", asList(type, expression));
    }

    public String makeListConversion(String javaExpression, ItemDefinitionType expectedElementType) {
        String elementConversion = convertToItemDefinitionType("x", expectedElementType);
        return String.format("%s?.map({ x -> %s })", javaExpression, elementConversion);
    }

    public String convertToItemDefinitionType(String expression, ItemDefinitionType type) {
        String convertMethodName = convertMethodName(type);
        String interfaceName = dmnTransformer.toJavaType(type);
        return String.format("%s.%s(%s)", interfaceName, convertMethodName, expression);
    }

    public String convertMethodName(TItemDefinition itemDefinition) {
        String javaInterfaceName = dmnTransformer.upperCaseFirst(itemDefinition.getName());
        return String.format("to%s", javaInterfaceName);
    }

    private String convertMethodName(ItemDefinitionType type) {
        String javaInterfaceName = dmnTransformer.upperCaseFirst(type.getName());
        return String.format("to%s", javaInterfaceName);
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
            String javaType = nullableType(this.dmnTransformer.toJavaType(type));
            return String.format("%s?.let({ %s.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<%s>() {}) })", paramName, objectMapper(), javaType);
        } else {
            String javaType = dmnTransformer.itemDefinitionJavaClassName(dmnTransformer.toJavaType(type));
            return String.format("%s?.let({ %s.readValue(it, %s::class.java) })", paramName, objectMapper(), javaType);
        }
    }

    @Override
    public String conversionFunction(Conversion conversion, String javaType) {
        if (conversion.getKind() == ConversionKind.NONE) {
            return null;
        } else if (conversion.getKind() == ConversionKind.ELEMENT_TO_LIST) {
            return "asList";
        } else if (conversion.getKind() == ConversionKind.LIST_TO_ELEMENT) {
            return String.format("asElement");
        } else {
            throw new DMNRuntimeException(String.format("Conversion '%s' is not supported yet", conversion));
        }
    }

    private String nullableType(String type) {
        return type.endsWith("?") ? type : type + "?";
    }

    private String objectMapper() {
        return JsonSerializer.class.getName() + ".OBJECT_MAPPER";
    }
}
