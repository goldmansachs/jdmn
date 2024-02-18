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
import com.gs.dmn.ast.TDRGElement;
import com.gs.dmn.ast.TDecision;
import com.gs.dmn.ast.TItemDefinition;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.el.analysis.semantics.type.FunctionType;
import com.gs.dmn.el.analysis.semantics.type.ItemDefinitionType;
import com.gs.dmn.el.analysis.semantics.type.ListType;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.el.analysis.syntax.ast.expression.function.Conversion;
import com.gs.dmn.feel.analysis.semantics.type.*;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.ConversionKind;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter;
import com.gs.dmn.feel.analysis.syntax.ast.expression.literal.DateTimeLiteral;
import com.gs.dmn.feel.synthesis.type.NativeTypeFactory;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.serialization.JsonSerializer;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import com.gs.dmn.transformation.native_.statement.AssignmentStatement;
import com.gs.dmn.transformation.native_.statement.CompoundStatement;
import com.gs.dmn.transformation.native_.statement.ExpressionStatement;
import com.gs.dmn.transformation.proto.ProtoBufferFactory;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.gs.dmn.feel.analysis.semantics.type.DateTimeType.DATE_AND_TIME;
import static com.gs.dmn.feel.analysis.semantics.type.DateType.DATE;
import static com.gs.dmn.feel.analysis.semantics.type.DurationType.DAYS_AND_TIME_DURATION;
import static com.gs.dmn.feel.analysis.semantics.type.DurationType.YEARS_AND_MONTHS_DURATION;
import static com.gs.dmn.feel.analysis.semantics.type.TimeType.TIME;

public class JavaFactory implements NativeFactory {
    protected static final Object DEFAULT_PROTO_NUMBER = "0.0";
    protected static final Object DEFAULT_PROTO_BOOLEAN = "false";
    protected static final Object DEFAULT_PROTO_STRING = "\"\"";

    protected final BasicDMNToNativeTransformer<Type, DMNContext> transformer;
    protected final ProtoBufferFactory protoFactory;
    protected final NativeTypeFactory typeFactory;
    protected final DMNModelRepository repository;

    public JavaFactory(BasicDMNToNativeTransformer<Type, DMNContext> transformer) {
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
        return "new %s(%s)".formatted(className, arguments);
    }

    @Override
    public String fluentConstructor(String className, String addMethods) {
        return "new %s()%s".formatted(className, addMethods);
    }

    @Override
    public String functionalInterfaceConstructor(String functionalInterface, String returnType, String applyMethod) {
        return "new %s<%s>() {%s}".formatted(functionalInterface, returnType, applyMethod);
    }

    //
    // Selection
    //
    @Override
    public String makeItemDefinitionAccessor(String javaType, String source, String memberName) {
        String accessorMethod = this.transformer.getter(memberName);
        return "((%s)(%s != null ? %s.%s : null))".formatted(javaType, source, source, accessorMethod);
    }

    @Override
    public String makeItemDefinitionSelectExpression(String source, String memberName, String memberType) {
        return "((%s)%s.%s)".formatted(memberType, source, this.transformer.contextGetter(memberName));
    }

    @Override
    public String makeContextAccessor(String javaType, String source, String memberName) {
        return "((%s)((%s)%s).%s)".formatted(javaType, this.transformer.contextClassName(), source, this.transformer.contextGetter(memberName));
    }

    @Override
    public String makeCollectionMap(String source, String filter) {
        return "%s.stream().map(x -> %s).collect(Collectors.toList())".formatted(source, filter);
    }

    @Override
    public String makeContextSelectExpression(String contextClassName, String source, String memberName) {
        return "((%s)(%s)).get(\"%s\", asList())".formatted(contextClassName, source, memberName);
    }

    @Override
    public String prefixWithSelf(String text) {
        return text;
    }

    //
    // Expressions
    //
    @Override
    public String makeCollectionLogicFilter(String source, String parameterName, String filter) {
        return "%s.stream().filter(%s -> %s == Boolean.TRUE).collect(Collectors.toList())".formatted(source, parameterName, filter);
    }

    @Override
    public String makeCollectionNumericFilter(String javaElementType, String source, String filter) {
        String args = "%s, %s".formatted(source, filter);
        String call = this.makeBuiltinFunctionInvocation("elementAt", args);
        return "(%s)(%s)".formatted(javaElementType, call);
    }

    @Override
    public String makeIfExpression(String condition, String thenExp, String elseExp) {
        String args = "%s, %s".formatted(condition, trueConstant());
        String call = this.makeBuiltinFunctionInvocation("booleanEqual", args);
        return "(%s) ? %s : %s".formatted(call, thenExp, elseExp);
    }

    @Override
    public String makeNullCheck(String exp, String type) {
        return "%s == null ? null : ((%s) %s)".formatted(exp, type, exp);
    }

    @Override
    public String makeForExpression(List<Pair<String, String>> domainIterators, String body) {
        // Add code for each iterator
        StringBuilder result = new StringBuilder();
        for (Pair<String, String> pair: domainIterators) {
            result.append("%s.stream().map(%s -> ".formatted(pair.getLeft(), pair.getRight()));
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
        String args = "(List)%s".formatted(list);
        return makeBuiltinFunctionInvocation("booleanOr", args);
    }

    @Override
    public String makeEveryExpression(String list) {
        String args = "(List)%s".formatted(list);
        return makeBuiltinFunctionInvocation("booleanAnd", args);
    }

    @Override
    public String makeInstanceOf(String value, Type type) {
        return "isInstanceOf(%s, \"%s\")".formatted(value, type.serialize());
    }

    //
    // Decision Table aggregators
    //
    @Override
    public String makeMinAggregator(String ruleOutputListVariableName, String decisionRuleOutputClassName, String outputClauseVariableName) {
        return "min(%s.stream().map(o -> ((%s)o).%s).collect(Collectors.toList()))".formatted(
            ruleOutputListVariableName, decisionRuleOutputClassName, this.transformer.getter(outputClauseVariableName)
        );
    }

    @Override
    public String makeMaxAggregator(String ruleOutputListVariableName, String decisionRuleOutputClassName, String outputClauseVariableName) {
        return "max(%s.stream().map(o -> ((%s)o).%s).collect(Collectors.toList()))".formatted(
            ruleOutputListVariableName, decisionRuleOutputClassName, this.transformer.getter(outputClauseVariableName)
        );
    }

    @Override
    public String makeCountAggregator(String ruleOutputListVariableName) {
        return "number(String.format(\"%d\", " + ruleOutputListVariableName + ".size()))";
    }

    @Override
    public String makeSumAggregator(String ruleOutputListVariableName, String decisionRuleOutputClassName, String outputClauseVariableName) {
        return "sum(%s.stream().map(o -> ((%s)o).%s).collect(Collectors.toList()))".formatted(
            ruleOutputListVariableName, decisionRuleOutputClassName, this.transformer.getter(outputClauseVariableName)
        );
    }

    //
    // Return
    //
    @Override
    public String makeReturn(String expression) {
        return "return %s;".formatted(expression);
    }

    //
    // Assignment
    //
    @Override
    public String makeVariableAssignment(String type, String name, String expression) {
        return "%s %s = %s;".formatted(type, name, expression);
    }

    @Override
    public String makeMemberAssignment(String complexTypeVariable, String memberName, String value) {
        return "%s.%s;".formatted(complexTypeVariable, this.transformer.setter(memberName, value));
    }

    @Override
    public String makeContextMemberAssignment(String complexTypeVariable, String memberName, String value) {
        return "%s.%s %s);".formatted(complexTypeVariable, this.transformer.contextSetter(memberName), value);
    }

    //
    // Equality
    //
    @Override
    public String isNull(String exp) {
        return "%s == %s".formatted(exp, this.nullLiteral());
    }

    @Override
    public String makeEquality(String left, String right) {
        return "%s == %s".formatted(left, right);
    }

    //
    // Functions
    //
    @Override
    public String makeBuiltinFunctionInvocation(String javaFunctionCode, String argumentsText) {
        return "%s(%s)".formatted(javaFunctionCode, argumentsText);
    }

    @Override
    public String makeApplyInvocation(String javaFunctionCode, String argumentsText) {
        return "%s.apply(%s)".formatted(javaFunctionCode, argumentsText);
    }

    @Override
    public String applyMethod(FunctionType functionType, String signature, boolean convertTypeToContext, String body) {
        String returnType = transformer.toNativeType(transformer.convertType(functionType.getReturnType(), convertTypeToContext));
        String parametersAssignment = parametersAssignment(((com.gs.dmn.feel.analysis.semantics.type.FunctionType) functionType).getParameters(), convertTypeToContext);
        return applyMethod(returnType, signature, parametersAssignment, body);
    }

    protected String applyMethod(String returnType, String signature, String parametersAssignment, String body) {
        return (
            """
            public %s apply(%s) {\
            %s\
            return %s;\
            }\
            """).formatted(
            returnType, signature, parametersAssignment, body);
    }

    protected String parametersAssignment(List<FormalParameter<Type>> formalParameters, boolean convertTypeToContext) {
        List<String> parameters = new ArrayList<>();
        for(int i=0; i<formalParameters.size(); i++) {
            FormalParameter<Type> p = formalParameters.get(i);
            String type = transformer.toNativeType(transformer.convertType(p.getType(), convertTypeToContext));
            String name = transformer.nativeFriendlyVariableName(p.getName());
            parameters.add(makeLambdaParameterAssignment(type, name, i));
        }
        return String.join(" ", parameters);
    }

    protected String makeLambdaParameterAssignment(String type, String name, int i) {
        return "%s %s = (%s)%s[%s];".formatted(type, name, type, transformer.lambdaArgsVariableName(), i);
    }

    @Override
    public String makeExternalExecutorCall(String externalExecutorVariableName, String className, String methodName, String arguments, String returnNativeType) {
        return "(%s)%s.execute(\"%s\", \"%s\", new Object[] {%s})".formatted(returnNativeType, externalExecutorVariableName, className, methodName, arguments);
    }

    //
    // Parameters
    //
    @Override
    public String nullableParameterType(String parameterType) {
        return "%s".formatted(parameterType);
    }

    @Override
    public String nullableParameter(String parameterType, String parameterName) {
        return "%s %s".formatted(parameterType, parameterName);
    }

    @Override
    public String parameterType(String parameterType) {
        return "%s".formatted(parameterType);
    }

    @Override
    public String parameter(String parameterType, String parameterName) {
        return "%s %s".formatted(parameterType, parameterName);
    }

    @Override
    public String decisionConstructorParameter(DRGElementReference<TDecision> d) {
        return "%s %s".formatted(this.transformer.qualifiedName(d), this.transformer.drgElementReferenceVariableName(d));
    }

    //
    // Literal
    //
    @Override
    public String numericLiteral(String lexeme) {
        return "number(\"%s\")".formatted(lexeme);
    }

    @Override
    public String booleanLiteral(String lexeme) {
        lexeme = lexeme.trim();
        return "true".equals(lexeme) ? trueConstant() : falseConstant();
    }

    @Override
    public String trueConstant() {
        return "Boolean.TRUE";
    }

    @Override
    public String falseConstant() {
        return "Boolean.FALSE";
    }

    @Override
    public String booleanValueLiteral(String lexeme) {
        lexeme = lexeme.trim();
        return "true".equals(lexeme) ? trueValueConstant() : falseValueConstant();
    }

    @Override
    public String trueValueConstant() {
        return "true";
    }

    @Override
    public String falseValueConstant() {
        return "false";
    }

    @Override
    public String nullLiteral() {
        return "null";
    }

    @Override
    public String dateTimeLiteral(DateTimeLiteral<Type> element) {
        Type type = element.getType();
        String value = element.getLexeme();
        String functionName;
        if (type == DATE) {
            functionName = "date";
        } else if (type == TIME) {
            functionName = "time";
        } else if (type == DATE_AND_TIME) {
            functionName = "dateAndTime";
        } else if (type == DAYS_AND_TIME_DURATION || type == YEARS_AND_MONTHS_DURATION) {
            functionName = "duration";
        } else {
            throw new DMNRuntimeException("Illegal date literal kind '" + type + "'. Expected 'date', 'time', 'date and time' or 'duration'.");
        }
        return this.makeBuiltinFunctionInvocation(functionName, value);
    }

    //
    // Conversions
    //
    @Override
    public String asList(Type elementType, String exp) {
        return this.makeBuiltinFunctionInvocation("asList", exp);
    }

    @Override
    public String asElement(String exp) {
        return this.makeBuiltinFunctionInvocation("asElement", exp);
    }

    @Override
    public String convertListToElement(String expression, Type type) {
        return asElement(expression);
    }

    @Override
    public String convertElementToList(String expression, Type type) {
        return asList(type, expression);
    }

    @Override
    public String makeListConversion(String javaExpression, ItemDefinitionType expectedElementType) {
        String elementConversion = convertToItemDefinitionType("x", expectedElementType);
        return "%s.stream().map(x -> %s).collect(Collectors.toList())".formatted(javaExpression, elementConversion);
    }

    @Override
    public String convertToItemDefinitionType(String expression, ItemDefinitionType type) {
        String convertMethodName = convertMethodName(type);
        String interfaceName = transformer.toNativeType(type);
        return "%s.%s(%s)".formatted(interfaceName, convertMethodName, expression);
    }

    @Override
    public String convertMethodName(TItemDefinition itemDefinition) {
        String javaInterfaceName = transformer.upperCaseFirst(itemDefinition.getName());
        return "to%s".formatted(javaInterfaceName);
    }

    @Override
    public String convertMethodName(ItemDefinitionType type) {
        String javaInterfaceName = transformer.upperCaseFirst(type.getName());
        return "to%s".formatted(javaInterfaceName);
    }

    @Override
    public boolean isSerializable(Type type) {
        type = Type.extractTypeFromConstraint(type);
        return FEELTypes.FEEL_PRIMITIVE_TYPES.contains(type) ||
                (type instanceof ListType lt && isSerializable(lt.getElementType())) ||
                type instanceof ItemDefinitionType ||
                type instanceof ContextType;
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
                return "(%s != null ? %s(%s) : null)".formatted(paramName, conversionMethod, paramName);
            } else if (type == StringType.STRING) {
                return paramName;
            } else if (type == BooleanType.BOOLEAN) {
                return "(%s != null ? Boolean.valueOf(%s) : null)".formatted(paramName, paramName);
            } else if (com.gs.dmn.el.analysis.semantics.type.Type.isAny(type)) {
                return paramName;
            } else {
                throw new DMNRuntimeException("Cannot convert String to type '%s'".formatted(type));
            }
        } else if (type instanceof ListType) {
            String javaType = this.transformer.toNativeType(type);
            return "(%s != null ? %s.readValue(%s, new com.fasterxml.jackson.core.type.TypeReference<%s>() {}) : null)".formatted(paramName, objectMapper(), paramName, javaType);
        } else if (type instanceof ItemDefinitionType || type instanceof ContextType) {
            String javaType = transformer.itemDefinitionNativeClassName(transformer.toNativeType(type));
            return "(%s != null ? %s.readValue(%s, new com.fasterxml.jackson.core.type.TypeReference<%s>() {}) : null)".formatted(paramName, objectMapper(), paramName, javaType);
        } else {
            throw new DMNRuntimeException("Conversion not supported for '%s' yet".formatted(type.getClass().getSimpleName()));
        }
    }

    @Override
    public String conversionFunction(Conversion<Type, ConversionKind> conversion, String javaType) {
        if (conversion.getKind() == ConversionKind.NONE) {
            return null;
        } else if (conversion.getKind() == ConversionKind.ELEMENT_TO_SINGLETON_LIST) {
            return elementToListConversionFunction();
        } else if (conversion.getKind() == ConversionKind.SINGLETON_LIST_TO_ELEMENT) {
            return listToElementConversionFunction(javaType);
        } else if (conversion.getKind() == ConversionKind.DATE_TO_UTC_MIDNIGHT) {
            return dateToUTCMidnight(javaType);
        } else {
            return toNull(javaType);
        }
    }

    protected String elementToListConversionFunction() {
        return "asList";
    }

    protected String listToElementConversionFunction(String javaType) {
        return "asElement";
    }

    protected String dateToUTCMidnight(String javaType) {
        return "toDateTime";
    }

    protected String toNull(String javaType) {
        return "toNull";
    }

    @Override
    public String convertProtoMember(String source, TItemDefinition parent, TItemDefinition member, boolean staticContext) {
        Type memberType = this.transformer.toFEELType(member);
        String memberName = this.transformer.protoFieldName(member);
        ProtoBufferFactory protoFactory = this.transformer.getProtoFactory();
        String protoType = protoFactory.qualifiedProtoMessageName(parent);
        String value = "%s.%s".formatted(cast(protoType, source), protoFactory.protoGetter(memberName, memberType));
        return extractMemberFromProtoValue(value, memberType, staticContext);
    }

    @Override
    public String convertMemberToProto(String source, String sourceType, TItemDefinition member, boolean staticContext) {
        Type memberType = this.transformer.toFEELType(member);
        String value = "%s.%s".formatted(cast(sourceType, source), this.transformer.getter(member.getName()));
        return convertValueToProtoNativeType(value, memberType, staticContext);
    }

    @Override
    public String extractParameterFromRequestMessage(TDRGElement element, Pair<String, Type> parameter, boolean staticContext) {
        String name = parameter.getLeft();
        Type type = parameter.getRight();
        String protoValue = "%s.%s".formatted(this.protoFactory.requestVariableName(element), this.protoFactory.protoGetter(name, type));
        return extractMemberFromProtoValue(protoValue, type, staticContext);
    }

    @Override
    public String extractMemberFromProtoValue(String protoValue, Type type, boolean staticContext) {
        type = Type.extractTypeFromConstraint(type);
        if (FEELTypes.FEEL_PRIMITIVE_TYPES.contains(type)) {
            if (type == NumberType.NUMBER) {
                String qNativeType = this.transformer.getNativeTypeFactory().toQualifiedNativeType(((DataType) type).getName());
                return "%s.valueOf(%s)".formatted(qNativeType, protoValue);
            } else if (type == BooleanType.BOOLEAN) {
                return protoValue;
            } else if (type == StringType.STRING) {
                return protoValue;
            } else {
                // Date time types
                String stringValue = "%s".formatted(protoValue);
                String conversionMethod = getConversionMethod(type, staticContext);
                if (conversionMethod != null) {
                    return "%s(%s)".formatted(conversionMethod, stringValue);
                }
                throw new DMNRuntimeException("Cannot convert type '%s' to proto type".formatted(type));
            }
        } else if (type instanceof ListType listType) {
            Type elementType = listType.getElementType();
            elementType = Type.extractTypeFromConstraint(elementType);
            String mapFunction;
            if (FEELTypes.FEEL_PRIMITIVE_TYPES.contains(elementType)) {
                if (elementType == NumberType.NUMBER) {
                    String qNativeType = this.transformer.getNativeTypeFactory().toQualifiedNativeType(((DataType) elementType).getName());
                    mapFunction = "e -> %s.valueOf(e)".formatted(qNativeType);
                } else if (elementType == BooleanType.BOOLEAN) {
                    mapFunction =  "e -> e";
                } else if (elementType == StringType.STRING) {
                    mapFunction =  "e -> e";
                } else {
                    // Date time types
                    String conversionMethod = getConversionMethod(type, staticContext);
                    if (conversionMethod != null) {
                        mapFunction = "e -> %s(e)".formatted(conversionMethod);
                    } else {
                        throw new DMNRuntimeException("Cannot convert type '%s' to proto type".formatted(type));
                    }
                }
            } else if (elementType instanceof ItemDefinitionType definitionType) {
                String qNativeType = this.transformer.toNativeType(elementType);
                String convertFunction = convertMethodName(definitionType);
                mapFunction = itemDefinitionConversionLambda(qNativeType, convertFunction);
            } else {
                throw new DMNRuntimeException("Cannot convert type '%s' to proto type".formatted(type));
            }
            String qNativeType = this.transformer.toNativeType(type);
            return extractListMemberFromProto(protoValue, mapFunction, qNativeType);
        } else if (type instanceof ItemDefinitionType definitionType) {
            String qNativeType = this.transformer.toNativeType(type);
            String convertFunction = convertMethodName(definitionType);
            return "%s.%s(%s)".formatted(qNativeType, convertFunction, protoValue);
        }
        throw new DMNRuntimeException("Cannot convert type '%s' to proto type".formatted(type));
    }

    protected String itemDefinitionConversionLambda(String qNativeType, String convertFunction) {
        return "%s::%s".formatted(qNativeType, convertFunction);
    }

    protected String extractListMemberFromProto(String protoSource, String mapFunction, String qNativeType) {
        return cast(qNativeType, "(%s == null ? null : %s.stream().map(%s).collect(java.util.stream.Collectors.toList()))".formatted(protoSource, protoSource, mapFunction));
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
            conversionMethod = "%s.INSTANCE.%s".formatted(feelLibSingleton, conversionMethod);
        }
        return conversionMethod;
    }

    @Override
    public String convertValueToProtoNativeType(String value, Type type, boolean staticContext) {
        type = Type.extractTypeFromConstraint(type);
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
                return "%s(%s)".formatted(conversionMethod, value);
            }
        } else if (type instanceof ListType listType) {
            Type elementType = listType.getElementType();
            elementType = Type.extractTypeFromConstraint(elementType);
            String mapFunction;
            if (FEELTypes.FEEL_PRIMITIVE_TYPES.contains(elementType)) {
                if (elementType == NumberType.NUMBER) {
                    mapFunction = "e -> %s".formatted(toProtoNumber("e"));
                } else if (elementType == BooleanType.BOOLEAN) {
                    mapFunction = "e -> %s".formatted(toProtoBoolean("e"));
                } else if (elementType == StringType.STRING) {
                    mapFunction = "e -> %s".formatted(toProtoString("e"));
                } else {
                    // DATE TIME: Return string
                    String conversionMethod = getConversionMethod("string", staticContext);
                    mapFunction = "e -> %s(e)".formatted(conversionMethod);
                }
            } else if (elementType instanceof ItemDefinitionType) {
                String nativeType = this.transformer.toNativeType(elementType);
                mapFunction = itemDefinitionConversionLambda(nativeType, TO_PROTO_CONVERSION_METHOD);
            } else {
                throw new DMNRuntimeException("Cannot convert type '%s' to proto type".formatted(type));
            }
            return convertListMemberToProto(value, mapFunction);
        } else if (type instanceof ItemDefinitionType) {
            String nativeType = this.transformer.toNativeType(type);
            return "%s.%s(%s)".formatted(nativeType, TO_PROTO_CONVERSION_METHOD, value);
        }
        throw new DMNRuntimeException("Conversion from '%s' to proto types is not supported yet".formatted(type));
    }

    protected String convertListMemberToProto(String protoSource, String mapFunction) {
        return cast("List", "(%s == null ? null : %s.stream().map(%s).collect(java.util.stream.Collectors.toList()))".formatted(protoSource, protoSource, mapFunction));
    }

    protected String toProtoNumber(String value) {
        return "(%s == null ? %s : %s.doubleValue())".formatted(value, DEFAULT_PROTO_NUMBER, value);
    }

    protected String toProtoBoolean(String value) {
        return "(%s == null ? %s : %s)".formatted(value, DEFAULT_PROTO_BOOLEAN, value);
    }

    protected String toProtoString(String value) {
        return "(%s == null ? %s : %s)".formatted(value, DEFAULT_PROTO_STRING, value);
    }

    protected String cast(String type, String value) {
        return StringUtils.isBlank(type) ? value : "((%s) %s)".formatted(type, value);
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

    @Override
    public AssignmentStatement makeAssignmentStatement(String lhsNativeType, String lhs, String rhs, Type lhsType, String text) {
        return new AssignmentStatement(lhsNativeType, lhs, rhs, lhsType, text);
    }

    //
    // Compound statement
    //
    @Override
    public CompoundStatement makeCompoundStatement() {
        return new CompoundStatement();
    }
}
