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
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.Conversion;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.ConversionKind;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FunctionDefinition;
import com.gs.dmn.feel.lib.FEELLib;
import com.gs.dmn.runtime.Context;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Range;
import com.gs.dmn.runtime.function.BuiltinFunction;
import com.gs.dmn.runtime.function.DMNFunction;
import com.gs.dmn.runtime.function.DMNInvocable;
import com.gs.dmn.runtime.function.FEELFunction;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import java.time.*;
import java.time.temporal.TemporalAmount;
import java.util.List;

import static com.gs.dmn.el.analysis.semantics.type.AnyType.ANY;
import static com.gs.dmn.feel.analysis.semantics.type.BooleanType.BOOLEAN;
import static com.gs.dmn.feel.analysis.semantics.type.ComparableDataType.COMPARABLE;
import static com.gs.dmn.feel.analysis.semantics.type.DateType.DATE;
import static com.gs.dmn.feel.analysis.semantics.type.NumberType.NUMBER;
import static com.gs.dmn.feel.analysis.semantics.type.StringType.STRING;
import static com.gs.dmn.feel.analysis.semantics.type.TimeType.TIME;
import static com.gs.dmn.feel.analysis.syntax.ast.expression.function.ConversionKind.*;

public class TypeChecker {
    private final BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer;
    private final ELInterpreter<Type, DMNContext> elInterpreter;
    protected final FEELLib<?, ?, ?, ?, ?> lib;
    private final boolean checkConstraints;
    private final boolean throwError = true;

    public TypeChecker(BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, ELInterpreter<Type, DMNContext> elInterpreter, FEELLib<?, ?, ?, ?, ?> lib) {
        this.dmnTransformer = dmnTransformer;
        this.elInterpreter = elInterpreter;
        this.lib = lib;
        this.checkConstraints = dmnTransformer.isCheckConstraints();
    }

    /*
        A value conforms to a type when the value is in the semantic domain of type (in varies from one dialect to another)
    */
    private static boolean conformsTo(Object value, Type type) {
        if (value == null) {
            return true;
        }

        type = Type.extractTypeFromConstraint(type);
        if (type == ANY) {
            return true;
        } else if (type == NUMBER && isNumber(value)) {
            return true;
        } else if (type == STRING && isString(value)) {
            return true;
        } else if (type == BOOLEAN && value instanceof Boolean) {
            return true;
        } else if (type == DATE && isDate(value)) {
            return true;
        } else if (type == TIME && isTime(value)) {
            return true;
        } else if (type instanceof DateTimeType && isDateTime(value)) {
            return true;
        } else if (type instanceof DurationType && isDuration(value)) {
            return true;
        } else if (type == COMPARABLE && isComparable(value)) {
            return true;
        } else if ((type instanceof ContextType || type instanceof ItemDefinitionType) && value instanceof Context context) {
            CompositeDataType contextType = (CompositeDataType) type;
            for (String member : contextType.getMembers()) {
                if (!context.getBindings().containsKey(member) || !conformsTo(context.get(member), contextType.getMemberType(member))) {
                    return false;
                }
            }
            return true;
        } else if (type instanceof RangeType && value instanceof Range) {
            return true;
        } else if (type instanceof ListType listType && value instanceof List list) {
            for (Object obj : list) {
                if (!conformsTo(obj, listType.getElementType())) {
                    return false;
                }
            }
            return true;
        } else if (value instanceof FEELFunction function && type instanceof FunctionType) {
            FunctionDefinition<Type> functionDefinition = function.getFunctionDefinition();
            return com.gs.dmn.el.analysis.semantics.type.Type.conformsTo(functionDefinition.getType(), type);
        } else if (value instanceof DMNInvocable invocable && type instanceof FunctionType) {
            Type valueType = invocable.getType();
            return com.gs.dmn.el.analysis.semantics.type.Type.conformsTo(valueType, type);
        } else if (value instanceof DMNFunction function && type instanceof FunctionType) {
            Type valueType = function.getType();
            return com.gs.dmn.el.analysis.semantics.type.Type.conformsTo(valueType, type);
        } else if (value instanceof BuiltinFunction function && type instanceof FunctionType) {
            // At least one conforms
            List<Declaration> declarations = function.getDeclarations();
            for (Declaration d: declarations) {
                if (com.gs.dmn.el.analysis.semantics.type.Type.conformsTo(d.getType(), type)) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    private static boolean isString(Object value) {
        return value instanceof String;
    }

    private static boolean isNumber(Object value) {
        return value instanceof Number;
    }

    private static boolean isDuration(Object value) {
        return value instanceof Duration || value instanceof TemporalAmount;
    }

    private static boolean isDateTime(Object value) {
        return value instanceof XMLGregorianCalendar || value instanceof LocalDateTime || value instanceof OffsetDateTime || value instanceof ZonedDateTime;
    }

    private static boolean isTime(Object value) {
        return value instanceof XMLGregorianCalendar || value instanceof OffsetTime || value instanceof LocalTime;
    }

    private static boolean isDate(Object value) {
        return value instanceof XMLGregorianCalendar || value instanceof LocalDate;
    }

    private static boolean isComparable(Object value) {
        return isNumber(value) || isString(value) ||
                isDate(value) || isTime(value) || isDateTime(value) || isDuration(value);
    }

    private static boolean isSingletonList(Object value) {
        return value instanceof List l && l.size() == 1;
    }

    public Result checkResult(Result result, Type expectedType) {
        // Apply conversions
        Result finalResult = convertValue(Result.value(result), expectedType);

        // Check constraints
        return checkConstraints(finalResult, expectedType);
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

    public Result checkArgument(Result argResult, TInformationItem parameter, TDefinitions model) {
        QName typeRef = parameter.getTypeRef();
        if (typeRef == null) {
            return argResult;
        }

        // Apply conversions
        Type parameterType = dmnTransformer.toFEELType(model, QualifiedName.toQualifiedName(model, typeRef));
        Result finalResult = convertValue(Result.value(argResult), parameterType);

        // Check constraints
        return checkConstraints(finalResult, parameterType);
    }

    public Result checkArgument(Object value, Type parameterType) {
        // Apply conversions
        Result finalResult = convertValue(value, parameterType);

        // Check constraints
        return checkConstraints(finalResult, parameterType);
    }

    public Object checkArgument(Object value, Conversion<Type> conversion) {
        ConversionKind kind = conversion.getKind();
        return convertValue(value, kind);
    }

    public Object checkEntry(Object entryValue, Type entryType) {
        // Apply conversions
        Result finalResult = convertValue(entryValue, entryType);

        // Check constraints
        Result result = checkConstraints(finalResult, entryType);
        return Result.value(result);
    }

    public Result checkListElement(Result elementResult, QName typeRef, TDefinitions model) {
        if (typeRef != null) {
            Type type = dmnTransformer.toFEELType(model, QualifiedName.toQualifiedName(model, typeRef));
            elementResult = checkResult(elementResult, type);
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
        Result finalResult = checkResult(inputExpressionResult, inputType);
        return Result.value(finalResult);
    }

    public Result checkOutputClause(Result result, TOutputClause outputClause, TLiteralExpression outputExpression, TDefinitions model) {
        QName typeRef = outputClauseTypeRef(outputClause, outputExpression);
        Type paramType = typeRef == null ? null : dmnTransformer.toFEELType(model, QualifiedName.toQualifiedName(model, typeRef));
        return checkResult(result, paramType);
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
            throw new DMNRuntimeException("Value '%s' does not conform to type '%s'".formatted(value, expectedType));
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
        expectedType = Type.extractTypeFromConstraint(expectedType);
        if (conformsTo(value, expectedType)) {
            return NONE;
        } else if (isSingletonList(value) && conformsTo(((List) value).get(0), expectedType)) {
            return SINGLETON_LIST_TO_ELEMENT;
        } else if (expectedType instanceof ListType type && conformsTo(value, type.getElementType())) {
            return ELEMENT_TO_SINGLETON_LIST;
        } else if (lib.isDate(value) && expectedType instanceof DateTimeType) {
            return DATE_TO_UTC_MIDNIGHT;
        } else {
            return CONFORMS_TO;
        }
    }

    private Object convertValue(Object value, ConversionKind kind) {
        if (kind == NONE) {
            return value;
        } else if (kind == ELEMENT_TO_SINGLETON_LIST) {
            return lib.asList(value);
        } else if (kind == SINGLETON_LIST_TO_ELEMENT) {
            return lib.asElement((List) value);
        } else if (kind == CONFORMS_TO) {
            return null;
        } else if (kind == DATE_TO_UTC_MIDNIGHT) {
            return lib.toDate(value);
        } else {
            throw new DMNRuntimeException("'%s' is not supported yet".formatted(kind));
        }
    }
}
