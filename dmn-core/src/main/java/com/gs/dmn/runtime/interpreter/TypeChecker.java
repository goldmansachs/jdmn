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
package com.gs.dmn.runtime.interpreter;

import com.gs.dmn.QualifiedName;
import com.gs.dmn.ast.*;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.context.environment.Declaration;
import com.gs.dmn.el.analysis.semantics.type.ConstraintType;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.el.interpreter.ELInterpreter;
import com.gs.dmn.feel.analysis.semantics.type.*;
import com.gs.dmn.feel.analysis.syntax.ConversionKind;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.Conversion;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FunctionDefinition;
import com.gs.dmn.feel.lib.FEELLib;
import com.gs.dmn.feel.lib.type.time.xml.XMLCalendarType;
import com.gs.dmn.runtime.Context;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.DMNType;
import com.gs.dmn.runtime.Range;
import com.gs.dmn.runtime.function.BuiltinFunction;
import com.gs.dmn.runtime.function.DMNFunction;
import com.gs.dmn.runtime.function.DMNInvocable;
import com.gs.dmn.runtime.function.FEELFunction;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;

import javax.xml.datatype.Duration;
import javax.xml.namespace.QName;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

import static com.gs.dmn.el.analysis.semantics.type.AnyType.ANY;
import static com.gs.dmn.el.analysis.semantics.type.NullType.NULL;
import static com.gs.dmn.feel.analysis.semantics.type.BooleanType.BOOLEAN;
import static com.gs.dmn.feel.analysis.semantics.type.DateType.DATE;
import static com.gs.dmn.feel.analysis.semantics.type.NumberType.NUMBER;
import static com.gs.dmn.feel.analysis.semantics.type.StringType.STRING;
import static com.gs.dmn.feel.analysis.semantics.type.TimeType.TIME;
import static com.gs.dmn.feel.analysis.syntax.ConversionKind.*;

public class TypeChecker {
    private static Type valueType(Object value, Type expectedType) {
        Type actualType = null;
        if (value == null) {
            actualType =  NULL;
        } else if (isNumber(value)) {
            actualType = NUMBER;
        } else if (isString(value)) {
            actualType = STRING;
        } else if (value instanceof Boolean) {
            actualType = BOOLEAN;
        } else if (isDate(value)) {
            actualType = DATE;
        } else if (isTime(value)) {
            actualType = TIME;
        } else if (isDateTime(value)) {
            actualType = DateTimeType.DATE_AND_TIME;
        } else if (isDaysTimeDuration(value)) {
            actualType = DaysAndTimeDurationType.DAYS_AND_TIME_DURATION;
        } else if (isYearsAndMonthDuration(value)) {
            actualType = YearsAndMonthsDurationType.YEARS_AND_MONTHS_DURATION;
        } else if (value instanceof DMNType) {
            return valueType(((DMNType) value).toContext(), expectedType);
        } else if (value instanceof Context) {
            Context context = (Context) value;
            ContextType contextType = new ContextType();
            for (Object key : context.keySet()) {
                String member = (String) key;
                Type expectedMemberType = expectedType instanceof CompositeDataType ? ((CompositeDataType) expectedType).getMemberType(member) : NULL;
                contextType.addMember(member, new ArrayList<>(), valueType(context.get(member), expectedMemberType));
            }
            return contextType;
        } else if (value instanceof Range) {
            actualType = RangeType.ANY_RANGE;
        } else if (value instanceof List) {
            if (((List<?>) value).isEmpty()) {
                return new ListType(NULL);
            } else {
                Type expectdElementType = expectedType instanceof ListType ? ((ListType) expectedType).getElementType() : NULL;
                Type elementType = valueType(((List<?>) value).get(0), expectdElementType);
                for (Object obj : (List<?>) value) {
                    Type objType = valueType(obj, expectdElementType);
                    if (Type.equivalentTo(objType, elementType) || Type.conformsTo(objType, elementType)) {
                    } else if (Type.conformsTo(elementType, objType)) {
                        // move to parent type
                        elementType = objType;
                    } else {
                        // could be refined to use other abstract types (e.g. comparable)
                        elementType = ANY;
                    }
                }
                return new ListType(elementType);
            }
        } else if (value instanceof FEELFunction) {
            FunctionDefinition<Type> functionDefinition = ((FEELFunction) value).getFunctionDefinition();
            actualType = functionDefinition.getType();
        } else if (value instanceof DMNInvocable) {
            actualType = ((DMNInvocable) value).getType();
        } else if (value instanceof DMNFunction) {
            actualType = ((DMNFunction) value).getType();
        } else if (value instanceof BuiltinFunction) {
            // Find the one that conforms
            List<Declaration> declarations = ((BuiltinFunction) value).getDeclarations();
            actualType = NULL;
            for (Declaration d: declarations) {
                if (Type.conformsTo(d.getType(), expectedType)) {
                    actualType = d.getType();
                }
            }
        }
        return actualType;
    }

    private static boolean isString(Object value) {
        return value instanceof String;
    }

    private static boolean isNumber(Object value) {
        return value instanceof Number;
    }

    private static boolean isDateTime(Object value) {
        return value instanceof LocalDateTime || value instanceof OffsetDateTime || value instanceof ZonedDateTime;
    }

    private static boolean isTime(Object value) {
        return value instanceof OffsetTime || value instanceof LocalTime;
    }

    private static boolean isDate(Object value) {
        return value instanceof LocalDate;
    }

    private static boolean isComparable(Object value) {
        return isNumber(value) || isString(value) ||
                isDate(value) || isTime(value) || isDateTime(value) || isDuration(value);
    }

    private static boolean isDuration(Object value) {
        return isDaysTimeDuration(value) || isYearsAndMonthDuration(value);
    }

    private static boolean isDaysTimeDuration(Object value) {
        return value instanceof Duration && XMLCalendarType.isDayTimeDuration(value) ||
                value instanceof java.time.Duration;
    }

    private static boolean isYearsAndMonthDuration(Object value) {
        return value instanceof Duration && XMLCalendarType.isYearMonthDuration(value) ||
                value instanceof java.time.Period;
    }

    private final BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer;
    private final ELInterpreter<Type, DMNContext> elInterpreter;
    protected final FEELLib<?, ?, ?, ?, ?> lib;
    private final boolean checkConstraints;
    private final boolean throwError;

    public TypeChecker(BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, ELInterpreter<Type, DMNContext> elInterpreter, FEELLib<?, ?, ?, ?, ?> lib) {
        this.dmnTransformer = dmnTransformer;
        this.elInterpreter = elInterpreter;
        this.lib = lib;
        this.checkConstraints = dmnTransformer.isCheckConstraints();
        this.throwError = true;
    }

    //
    // Convert and check constraints when binding to a variable
    //
    public Result checkBindingResult(Result result, TInformationItem variable, TDefinitions model) {
        if (variable == null || variable.getTypeRef() == null) {
            return result;
        }

        // Check result
        QualifiedName typeRef = QualifiedName.toQualifiedName(model, variable.getTypeRef());
        if (typeRef != null) {
            Type expectedType = dmnTransformer.toFEELType(model, typeRef);
            result = checkBindingResult(result, expectedType);
        }

        return result;
    }

    public Result checkBindingResult(Result result, Type expectedType) {
        // Apply implicit conversions
        Result finalResult = convertValue(Result.value(result), expectedType);

        // Check constraints
        return checkConstraints(finalResult, expectedType);
    }

    public Result checkBindingArgument(Object value, Type parameterType) {
        // Apply conversions
        Result finalResult = convertValue(value, parameterType);

        // Check constraints
        return checkConstraints(finalResult, parameterType);
    }

    public Object checkBindingArgument(Object value, Conversion<Type> conversion) {
        Result finalResult = checkBindingArgument(value, conversion.getTargetType());
        return Result.value(finalResult);
    }

    //
    // Check constraints when evaluating an expression
    //
    public Result checkExpressionResult(Result result, TExpression expression, TDefinitions model) {
        QName typeRef = expression.getTypeRef();
        if (typeRef == null) {
            return result;
        }

        // Check constraints
        Type expressionType = dmnTransformer.toFEELType(model, QualifiedName.toQualifiedName(model, typeRef));
        return checkConstraints(result, expressionType);
    }

    public Result checkExpressionResult(Result result, Type expectedType) {
        // Check constraints
        return checkConstraints(result, expectedType);
    }

    public Result checkListElement(Result elementResult, QName typeRef, TDefinitions model) {
        if (typeRef != null) {
            Type type = dmnTransformer.toFEELType(model, QualifiedName.toQualifiedName(model, typeRef));
            elementResult = checkExpressionResult(elementResult, type);
        }
        return elementResult;
    }

    public Result checkListElement(Result columnResult, TInformationItem tInformationItem, TExpression columnExp, TDefinitions model) {
        QName typeRef = columnExp.getTypeRef();
        if (typeRef == null) {
            typeRef = tInformationItem.getTypeRef();
        }
        return checkListElement(columnResult, typeRef, model);
    }

    public Object checkInputExpression(Result inputExpressionResult, TInputClause inputClause, Type type) {
        TUnaryTests inputValues = inputClause.getInputValues();
        Type inputType = inputValues == null ? type : new ConstraintType(type, inputValues);
        Result finalResult = checkBindingResult(inputExpressionResult, inputType);
        return Result.value(finalResult);
    }

    public Result checkOutputClause(Result result, TOutputClause outputClause, TLiteralExpression outputExpression, TDefinitions model) {
        QName typeRef = outputClauseTypeRef(outputClause, outputExpression);
        Type paramType = typeRef == null ? null : dmnTransformer.toFEELType(model, QualifiedName.toQualifiedName(model, typeRef));
        return checkBindingResult(result, paramType);
    }

    private Result checkConstraints(Result result, Type expectedType) {
        // Check constraints
        if (checkConstraints) {
            Type actualType = Result.type(result);
            if (Type.conformsTo(actualType, expectedType)) {
                Object value = Result.value(result);
                value = AllowedValuesConverter.validateConstraint(value, expectedType, elInterpreter, dmnTransformer);
                result = Result.of(value, expectedType);
            }
        }
        return result;
    }

    private Result convertValue(Object value, Type expectedType) {
        expectedType = Type.extractTypeFromConstraint(expectedType);
        if (value == null) {
            return Result.of(value, expectedType);
        }

        // Dynamic conversion
        if (expectedType == null) {
            expectedType = ANY;
        }
        ConversionKind conversionKind = conversionKind(value, expectedType);
        if (throwError && conversionKind.isError()) {
            throw new DMNRuntimeException(String.format("Value '%s' does not conform to type '%s'", value, expectedType));
        }
        Object newValue = convertValue(value, conversionKind);
        return Result.of(newValue, expectedType);
    }

    private static QName outputClauseTypeRef(TOutputClause outputClause, TExpression expression) {
        QName typeRef = expression.getTypeRef();
        if (typeRef == null) {
            typeRef = outputClause.getTypeRef();
        }
        return typeRef;
    }

    private ConversionKind conversionKind(Object value, Type expectedType) {
        return FEELType.conversionKind(expectedType, valueType(value, expectedType));
    }

    private Object convertValue(Object value, ConversionKind kind) {
        if (kind == NONE) {
            return value;
        } else if (kind == ELEMENT_TO_SINGLETON_LIST) {
            return lib.asList(value);
        } else if (kind == SINGLETON_LIST_TO_ELEMENT) {
            return lib.asElement((List<?>) value);
        } else if (kind == DATE_TO_UTC_MIDNIGHT) {
            return lib.toDateTime(value);
        } else if (kind == TO_ITEM_DEFINITION || kind == TO_LIST_OF_ITEM_DEFINITION) {
            return value;
        } else if (kind == CONFORMS_TO) {
            return null;
        } else {
            throw new DMNRuntimeException(String.format("'%s' is not supported yet", kind));
        }
    }
}
