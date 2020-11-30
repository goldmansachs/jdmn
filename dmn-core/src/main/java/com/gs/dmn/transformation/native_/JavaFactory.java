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

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.DRGElementReference;
import com.gs.dmn.feel.analysis.semantics.type.*;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.Conversion;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.ConversionKind;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter;
import com.gs.dmn.feel.synthesis.type.NativeTypeFactory;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.serialization.JsonSerializer;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import com.gs.dmn.transformation.native_.statement.CompoundStatement;
import com.gs.dmn.transformation.native_.statement.ExpressionStatement;
import com.gs.dmn.transformation.proto.ProtoBufferFactory;
import org.apache.commons.lang3.StringUtils;
import org.omg.spec.dmn._20191111.model.TDRGElement;
import org.omg.spec.dmn._20191111.model.TDecision;
import org.omg.spec.dmn._20191111.model.TItemDefinition;

import java.util.ArrayList;
import java.util.List;

public class JavaFactory implements NativeFactory {
    protected static final Object DEFAULT_PROTO_NUMBER = "0.0";
    protected static final Object DEFAULT_PROTO_BOOLEAN = "false";
    protected static final Object DEFAULT_PROTO_STRING = "\"\"";

    protected final BasicDMNToNativeTransformer transformer;
    protected final ProtoBufferFactory protoFactory;
    protected final NativeTypeFactory typeFactory;
    protected final DMNModelRepository repository;

    public JavaFactory(BasicDMNToNativeTransformer transformer) {
        this.transformer = transformer;
        this.repository = transformer.getDMNModelRepository();
        this.protoFactory = transformer.getProtoFactory();
        this.typeFactory = transformer.getNativeTypeFactory();
    }

    //
    // Constructor
    //
    @Override
    public String constructor(String className, String arguments) {
        return String.format("new %s(%s)", className, arguments);
    }

    @Override
    public String fluentConstructor(String className, String addMethods) {
        return String.format("new %s()%s", className, addMethods);
    }

    @Override
    public String functionalInterfaceConstructor(String functionalInterface, String returnType, String applyMethod) {
        return String.format("new %s<%s>() {%s}", functionalInterface, returnType, applyMethod);
    }

    //
    // Selection
    //
    @Override
    public String makeItemDefinitionAccessor(String javaType, String source, String memberName) {
        String accessorMethod = this.transformer.getter(memberName);
        return String.format("((%s)(%s != null ? %s.%s : null))", javaType, source, source, accessorMethod);
    }

    @Override
    public String makeItemDefinitionSelectExpression(String source, String memberName, String memberType) {
        return String.format("((%s)%s.%s)", memberType, source, this.transformer.contextGetter(memberName));
    }

    @Override
    public String makeContextAccessor(String javaType, String source, String memberName) {
        return String.format("((%s)((%s)%s).%s)", javaType, this.transformer.contextClassName(), source, this.transformer.contextGetter(memberName));
    }

    @Override
    public String makeCollectionMap(String source, String filter) {
        return String.format("%s.stream().map(x -> %s).collect(Collectors.toList())", source, filter);
    }

    @Override
    public String makeContextSelectExpression(String contextClassName, String source, String memberName) {
        return String.format("((%s)(%s)).get(\"%s\", asList())", contextClassName, source, memberName);
    }

    //
    // Expressions
    //
    @Override
    public String makeCollectionLogicFilter(String source, String parameterName, String filter) {
        return String.format("%s.stream().filter(%s -> %s).collect(Collectors.toList())", source, parameterName, filter);
    }

    @Override
    public String makeCollectionNumericFilter(String javaElementType, String source, String filter) {
        return String.format("(%s)(elementAt(%s, %s))", javaElementType, source, filter);
    }

    @Override
    public String makeIfExpression(String condition, String thenExp, String elseExp) {
        return String.format("(booleanEqual(%s, %s)) ? %s : %s", condition, trueConstant(), thenExp, elseExp);
    }

    @Override
    public String makeForExpression(List<Pair<String, String>> domainIterators, String body) {
        // Add code for each iterator
        StringBuilder result = new StringBuilder();
        for (Pair<String, String> pair: domainIterators) {
            result.append(String.format("%s.stream().map(%s -> ", pair.getLeft(), pair.getRight()));
        }
        // Add body
        result.append(body);
        // Close parenthesis
        for (int i = 0; i < domainIterators.size(); i++) {
            result.append(")");
        }
        // Flatten nested streams
        for (int i = 0; i < domainIterators.size() - 1; i++) {
            result.append(".flatMap(x -> x)");
        }
        // Collect result
        result.append(".collect(Collectors.toList())");
        return result.toString();
    }

    @Override
    public String makeSomeExpression(String list) {
        return String.format("booleanOr((List)%s)", list);
    }

    @Override
    public String makeEveryExpression(String list) {
        return String.format("booleanAnd((List)%s)", list);
    }

    //
    // Decision Table aggregators
    //
    @Override
    public String makeMinAggregator(String ruleOutputListVariableName, String decisionRuleOutputClassName, String outputClauseVariableName) {
        return String.format("min(%s.stream().map(o -> ((%s)o).%s).collect(Collectors.toList()))",
                ruleOutputListVariableName, decisionRuleOutputClassName, this.transformer.getter(outputClauseVariableName)
        );
    }

    @Override
    public String makeMaxAggregator(String ruleOutputListVariableName, String decisionRuleOutputClassName, String outputClauseVariableName) {
        return String.format("max(%s.stream().map(o -> ((%s)o).%s).collect(Collectors.toList()))",
                ruleOutputListVariableName, decisionRuleOutputClassName, this.transformer.getter(outputClauseVariableName)
        );
    }

    @Override
    public String makeCountAggregator(String ruleOutputListVariableName) {
        return "number(String.format(\"%d\", " + ruleOutputListVariableName + ".size()))";
    }

    @Override
    public String makeSumAggregator(String ruleOutputListVariableName, String decisionRuleOutputClassName, String outputClauseVariableName) {
        return String.format("sum(%s.stream().map(o -> ((%s)o).%s).collect(Collectors.toList()))",
                ruleOutputListVariableName, decisionRuleOutputClassName, this.transformer.getter(outputClauseVariableName)
        );
    }

    //
    // Return
    //
    @Override
    public String makeReturn(String expression) {
        return String.format("return %s;", expression);
    }

    //
    // Assignment
    //
    @Override
    public String makeVariableAssignment(String type, String name, String expression) {
        return String.format("%s %s = %s;", type, name, expression);
    }

    @Override
    public String makeMemberAssignment(String complexTypeVariable, String memberName, String value) {
        return String.format("%s.%s(%s);", complexTypeVariable, this.transformer.setter(memberName), value);
    }

    @Override
    public String makeContextMemberAssignment(String complexTypeVariable, String memberName, String value) {
        return String.format("%s.%s %s);", complexTypeVariable, this.transformer.contextSetter(memberName), value);
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
        return String.format("%s.apply(%s)", javaFunctionCode, argumentsText);
    }

    @Override
    public String applyMethod(FunctionType functionType, String signature, boolean convertTypeToContext, String body) {
        String returnType = transformer.toNativeType(transformer.convertType(functionType.getReturnType(), convertTypeToContext));
        String parametersAssignment = parametersAssignment(functionType.getParameters(), convertTypeToContext);
        return applyMethod(returnType, signature, parametersAssignment, body);
    }

    protected String applyMethod(String returnType, String signature, String parametersAssignment, String body) {
        return String.format(
                "public %s apply(%s) {" +
                        "%s" +
                        "return %s;" +
                        "}",
                returnType, signature, parametersAssignment, body);
    }

    protected String parametersAssignment(List<FormalParameter> formalParameters, boolean convertTypeToContext) {
        List<String> parameters = new ArrayList<>();
        for(int i = 0; i< formalParameters.size(); i++) {
            FormalParameter p = formalParameters.get(i);
            String type = transformer.toNativeType(transformer.convertType(p.getType(), convertTypeToContext));
            String name = transformer.nativeFriendlyVariableName(p.getName());
            parameters.add(makeLambdaParameterAssignment(type, name, i));
        }
        return String.join(" ", parameters);
    }

    protected String makeLambdaParameterAssignment(String type, String name, int i) {
        return String.format("%s %s = (%s)args[%s];", type, name, type, i);
    }

    @Override
    public String makeExternalExecutorCall(String externalExecutorVariableName, String className, String methodName, String arguments, String returnNativeType) {
        return String.format("(%s)%s.execute(\"%s\", \"%s\", new Object[] {%s})", returnNativeType, externalExecutorVariableName, className, methodName, arguments);
    }

    //
    // Parameters
    //
    @Override
    public String nullableParameter(String parameterType, String parameterName) {
        return String.format("%s %s", parameterType, parameterName);
    }

    @Override
    public String parameter(String parameterType, String parameterName) {
        return String.format("%s %s", parameterType, parameterName);
    }

    @Override
    public String decisionConstructorParameter(DRGElementReference<TDecision> d) {
        return String.format("%s %s", this.transformer.qualifiedName(d), this.transformer.drgElementReferenceVariableName(d));
    }

    //
    // Literal
    //
    @Override
    public String trueConstant() {
        return "Boolean.TRUE";
    }

    @Override
    public String falseConstant() {
        return "Boolean.FALSE";
    }

    //
    // Conversions
    //
    @Override
    public String convertListToElement(String expression, Type type) {
        return String.format("this.<%s>%s", transformer.toNativeType(type), asElement(expression));
    }

    @Override
    public String asList(Type elementType, String exp) {
        return String.format("asList(%s)", exp);
    }

    @Override
    public String asElement(String exp) {
        return String.format("asElement(%s)", exp);
    }

    @Override
    public String convertElementToList(String expression, Type type) {
        return String.format("%s", asList(type, expression));
    }

    @Override
    public String makeListConversion(String javaExpression, ItemDefinitionType expectedElementType) {
        String elementConversion = convertToItemDefinitionType("x", expectedElementType);
        return String.format("%s.stream().map(x -> %s).collect(Collectors.toList())", javaExpression, elementConversion);
    }

    @Override
    public String convertToItemDefinitionType(String expression, ItemDefinitionType type) {
        String convertMethodName = convertMethodName(type);
        String interfaceName = transformer.toNativeType(type);
        return String.format("%s.%s(%s)", interfaceName, convertMethodName, expression);
    }

    @Override
    public String convertMethodName(TItemDefinition itemDefinition) {
        String javaInterfaceName = transformer.upperCaseFirst(itemDefinition.getName());
        return String.format("to%s", javaInterfaceName);
    }

    @Override
    public String convertMethodName(ItemDefinitionType type) {
        String javaInterfaceName = transformer.upperCaseFirst(type.getName());
        return String.format("to%s", javaInterfaceName);
    }

    @Override
    public String convertDecisionArgumentFromString(String paramName, Type type) {
        if (FEELTypes.FEEL_PRIMITIVE_TYPES.contains(type)) {
            String conversionMethod = FEELTypes.FEEL_PRIMITIVE_TYPE_TO_JAVA_CONVERSION_FUNCTION.get(type);
            if (conversionMethod != null) {
                return String.format("(%s != null ? %s(%s) : null)", paramName, conversionMethod, paramName);
            } else if (type == StringType.STRING) {
                return paramName;
            } else if (type == BooleanType.BOOLEAN) {
                return String.format("(%s != null ? Boolean.valueOf(%s) : null)", paramName, paramName);
            } else {
                throw new DMNRuntimeException(String.format("Cannot convert String to type '%s'", type));
            }
        } else if (type instanceof ListType) {
            String javaType = this.transformer.toNativeType(type);
            return String.format("(%s != null ? %s.readValue(%s, new com.fasterxml.jackson.core.type.TypeReference<%s>() {}) : null)", paramName, objectMapper(), paramName, javaType);
        } else {
            // Complex types
            String javaType = transformer.itemDefinitionNativeClassName(transformer.toNativeType(type));
            return String.format("(%s != null ? %s.readValue(%s, new com.fasterxml.jackson.core.type.TypeReference<%s>() {}) : null)", paramName, objectMapper(), paramName, javaType);
        }
    }

    @Override
    public String conversionFunction(Conversion conversion, String javaType) {
        if (conversion.getKind() == ConversionKind.NONE) {
            return null;
        } else if (conversion.getKind() == ConversionKind.ELEMENT_TO_LIST) {
            return elementToListConversionFunction();
        } else if (conversion.getKind() == ConversionKind.LIST_TO_ELEMENT) {
            return listToElementConversionFunction(javaType);
        } else {
            throw new DMNRuntimeException(String.format("Conversion '%s' is not supported yet", conversion));
        }
    }

    protected String elementToListConversionFunction() {
        return "asList";
    }

    protected String listToElementConversionFunction(String javaType) {
        return String.format("this.<%s>asElement", javaType);
    }

    @Override
    public String convertProtoMember(String source, TItemDefinition parent, TItemDefinition member, boolean staticContext) {
        Type memberType = this.transformer.toFEELType(member);
        String memberName = this.transformer.protoFieldName(member);
        ProtoBufferFactory protoFactory = this.transformer.getProtoFactory();
        String protoType = protoFactory.qualifiedProtoMessageName(parent);
        String value = String.format("%s.%s", cast(protoType, source), protoFactory.protoGetter(memberName, memberType));
        return extractMemberFromProtoValue(value, memberType, staticContext);
    }

    @Override
    public String convertMemberToProto(String source, String sourceType, TItemDefinition member, boolean staticContext) {
        Type memberType = this.transformer.toFEELType(member);
        String value = String.format("%s.%s", cast(sourceType, source), this.transformer.getter(member.getName()));
        return convertValueToProtoNativeType(value, memberType, staticContext);
    }

    @Override
    public String extractParameterFromRequestMessage(TDRGElement element, Pair<String, Type> parameter, boolean staticContext) {
        String name = parameter.getLeft();
        Type type = parameter.getRight();
        String protoValue = String.format("%s.%s", this.protoFactory.requestVariableName(element), this.protoFactory.protoGetter(name, type));
        return extractMemberFromProtoValue(protoValue, type, staticContext);
    }

    @Override
    public String extractMemberFromProtoValue(String protoValue, Type type, boolean staticContext) {
        if (FEELTypes.FEEL_PRIMITIVE_TYPES.contains(type)) {
            if (type == NumberType.NUMBER) {
                String qNativeType = this.transformer.getNativeTypeFactory().toQualifiedNativeType(((DataType) type).getName());
                return String.format("%s.valueOf(%s)", qNativeType, protoValue);
            } else if (type == BooleanType.BOOLEAN) {
                return protoValue;
            } else if (type == StringType.STRING) {
                return protoValue;
            } else {
                // Date time types
                String stringValue = String.format("%s", protoValue);
                String conversionMethod = getConversionMethod(type, staticContext);
                if (conversionMethod != null) {
                    return String.format("%s(%s)", conversionMethod, stringValue);
                }
                throw new DMNRuntimeException(String.format("Cannot convert type '%s' to proto type", type));
            }
        } else if (type instanceof ListType) {
            Type elementType = ((ListType) type).getElementType();
            String mapFunction;
            if (FEELTypes.FEEL_PRIMITIVE_TYPES.contains(elementType)) {
                if (elementType == NumberType.NUMBER) {
                    String qNativeType = this.transformer.getNativeTypeFactory().toQualifiedNativeType(((DataType) elementType).getName());
                    mapFunction = String.format("e -> %s.valueOf(e)", qNativeType);
                } else if (elementType == BooleanType.BOOLEAN) {
                    mapFunction =  "e -> e";
                } else if (elementType == StringType.STRING) {
                    mapFunction =  "e -> e";
                } else {
                    // Date time types
                    String conversionMethod = getConversionMethod(type, staticContext);
                    if (conversionMethod != null) {
                        mapFunction = String.format("e -> %s(e)", conversionMethod);
                    } else {
                        throw new DMNRuntimeException(String.format("Cannot convert type '%s' to proto type", type));
                    }
                }
            } else if (elementType instanceof ItemDefinitionType) {
                String qNativeType = this.transformer.toNativeType(elementType);
                String convertFunction = convertMethodName((ItemDefinitionType) elementType);
                mapFunction = itemDefinitionConversionLambda(qNativeType, convertFunction);
            } else {
                throw new DMNRuntimeException(String.format("Cannot convert type '%s' to proto type", type));
            }
            String qNativeType = this.transformer.toNativeType(type);
            return extractListMemberFromProto(protoValue, mapFunction, qNativeType);
        } else if (type instanceof ItemDefinitionType) {
            String qNativeType = this.transformer.toNativeType(type);
            String convertFunction = convertMethodName((ItemDefinitionType) type);
            return String.format("%s.%s(%s)", qNativeType, convertFunction, protoValue);
        }
        throw new DMNRuntimeException(String.format("Cannot convert type '%s' to proto type", type));
    }

    protected String itemDefinitionConversionLambda(String qNativeType, String convertFunction) {
        return String.format("%s::%s", qNativeType, convertFunction);
    }

    protected String extractListMemberFromProto(String protoSource, String mapFunction, String qNativeType) {
        return cast(qNativeType, String.format("(%s == null ? null : %s.stream().map(%s).collect(java.util.stream.Collectors.toList()))", protoSource, protoSource, mapFunction));
    }

    protected String getConversionMethod(Type type, boolean staticContext) {
        String conversionMethod = FEELTypes.FEEL_PRIMITIVE_TYPE_TO_JAVA_CONVERSION_FUNCTION.get(type);
        if (conversionMethod == null) {
            return null;
        }
        return getConversionMethod(conversionMethod, staticContext);
    }

    protected String getConversionMethod(String conversionMethod, boolean staticContext) {
        if (staticContext) {
            String feelLibSingleton = this.transformer.getDialect().createFEELLib().getClass().getName();
            conversionMethod = String.format("%s.INSTANCE.%s", feelLibSingleton, conversionMethod);
        }
        return conversionMethod;
    }

    @Override
    public String convertValueToProtoNativeType(String value, Type type, boolean staticContext) {
        if (FEELTypes.FEEL_PRIMITIVE_TYPES.contains(type)) {
            if (type == NumberType.NUMBER) {
                return toProtoNumber(value);
            } else if (type == BooleanType.BOOLEAN) {
                return toProtoBoolean(value);
            } else if (type == StringType.STRING) {
                return toProtoString(value);
            } else {
                // DATE TIME: Return string
                String conversionMethod = getConversionMethod("string", staticContext);
                return String.format("%s(%s)", conversionMethod, value);
            }
        } else if (type instanceof ListType) {
            Type elementType = ((ListType) type).getElementType();
            String mapFunction;
            if (FEELTypes.FEEL_PRIMITIVE_TYPES.contains(elementType)) {
                if (elementType == NumberType.NUMBER) {
                    mapFunction = String.format("e -> %s", toProtoNumber("e"));
                } else if (elementType == BooleanType.BOOLEAN) {
                    mapFunction = String.format("e -> %s", toProtoBoolean("e"));
                } else if (elementType == StringType.STRING) {
                    mapFunction = String.format("e -> %s", toProtoString("e"));
                } else {
                    // DATE TIME: Return string
                    String conversionMethod = getConversionMethod("string", staticContext);
                    mapFunction = String.format("e -> %s(e)", conversionMethod);
                }
            } else if (elementType instanceof ItemDefinitionType) {
                String nativeType = this.transformer.toNativeType(elementType);
                mapFunction = itemDefinitionConversionLambda(nativeType, TO_PROTO_CONVERSION_METHOD);
            } else {
                throw new DMNRuntimeException(String.format("Cannot convert type '%s' to proto type", type));
            }
            return convertListMemberToProto(value, mapFunction);
        } else if (type instanceof ItemDefinitionType) {
            String nativeType = this.transformer.toNativeType(type);
            return String.format("%s.%s(%s)", nativeType, TO_PROTO_CONVERSION_METHOD, value);
        }
        throw new DMNRuntimeException(String.format("Conversion from '%s' to proto types is not supported yet", type));
    }

    protected String convertListMemberToProto(String protoSource, String mapFunction) {
        return cast("List", String.format("(%s == null ? null : %s.stream().map(%s).collect(java.util.stream.Collectors.toList()))", protoSource, protoSource, mapFunction));
    }

    protected String toProtoNumber(String value) {
        return String.format("(%s == null ? %s : %s.doubleValue())", value, DEFAULT_PROTO_NUMBER, value);
    }

    protected String toProtoBoolean(String value) {
        return String.format("(%s == null ? %s : %s)", value, DEFAULT_PROTO_BOOLEAN, value);
    }

    protected String toProtoString(String value) {
        return String.format("(%s == null ? %s : %s)", value, DEFAULT_PROTO_STRING, value);
    }

    protected String cast(String type, String value) {
        return StringUtils.isBlank(type) ? value : String.format("((%s) %s)", type, value);
    }

    protected String objectMapper() {
        return JsonSerializer.class.getName() + ".OBJECT_MAPPER";
    }

    //
    // Simple statements
    //
    @Override
    public ExpressionStatement makeExpressionStatement(String text, Type type) {
        return new ExpressionStatement(text, type);
    }

    //
    // Compound statement
    //
    @Override
    public CompoundStatement makeCompoundStatement() {
        return new CompoundStatement();
    }
}
