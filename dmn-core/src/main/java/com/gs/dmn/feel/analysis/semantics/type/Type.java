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
package com.gs.dmn.feel.analysis.semantics.type;

import com.gs.dmn.feel.analysis.semantics.environment.Declaration;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FunctionDefinition;
import com.gs.dmn.runtime.Context;
import com.gs.dmn.runtime.DMNContext;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Range;
import com.gs.dmn.runtime.function.BuiltinFunction;
import com.gs.dmn.runtime.function.DMNFunction;
import com.gs.dmn.runtime.function.DMNInvocable;
import com.gs.dmn.runtime.function.FEELFunction;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.*;
import java.time.temporal.TemporalAmount;
import java.util.List;

import static com.gs.dmn.feel.analysis.semantics.type.AnyType.ANY;
import static com.gs.dmn.feel.analysis.semantics.type.BooleanType.BOOLEAN;
import static com.gs.dmn.feel.analysis.semantics.type.ComparableDataType.COMPARABLE;
import static com.gs.dmn.feel.analysis.semantics.type.DateType.DATE;
import static com.gs.dmn.feel.analysis.semantics.type.NumberType.NUMBER;
import static com.gs.dmn.feel.analysis.semantics.type.StringType.STRING;
import static com.gs.dmn.feel.analysis.semantics.type.TimeType.TIME;

public abstract class Type {
    //
    // Undefined types
    //
    public static boolean isNull(Type type) {
        return type == null;
    }

    public static boolean isNullType(Type type) {
        return type == NullType.NULL;
    }

    public static boolean isAny(Type type) {
        return type == AnyType.ANY;
    }

    public static boolean isNullOrAny(Type type) {
        return isNull(type) || isAny(type);
    }

    public static boolean isNullOrAnyType(Type type) {
        return isNull(type) || isNullType(type) || isAny(type);
    }

    public static boolean isNullOrAny(String typeRef) {
        return typeRef == null || "Any".equals(typeRef);
    }

    /*
        A type type1 is equivalent to type type2 when the types are either structurally or name equivalent. The types are compatible without coercion
    */
    public static boolean equivalentTo(Type type1, Type type2) {
        if (type1 == null) {
            return type2 == null;
        } else {
            return type1.equivalentTo(type2);
        }
    }

    /*
        A type type1 conforms to type type2 when an instance of type1 can be substituted at each place where an instance of type2 is expected
    */
    public static boolean conformsTo(Type type1, Type type2) {
        if (type2 == null || type2 == ANY) {
            return true;
        } else if (type1 == null) {
            return false;
        } else {
            return type1.conformsTo(type2);
        }
    }


    /*
        A value conforms to a type when the value is in the semantic domain of type (in varies from one dialect to another)
    */
    public static boolean conformsTo(Object value, Type type) {
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
        } else if ((type instanceof ContextType || type instanceof ItemDefinitionType) && value instanceof Context) {
            Context context = (Context) value;
            CompositeDataType contextType = (CompositeDataType) type;
            for (String member : contextType.getMembers()) {
                if (!conformsTo(context.get(member), contextType.getMemberType(member))) {
                    return false;
                }
            }
            return true;
        } else if (type instanceof RangeType && value instanceof Range) {
            return true;
        } else if (type instanceof ListType && value instanceof List) {
            for (Object obj : (List) value) {
                if (!conformsTo(obj, ((ListType) type).getElementType())) {
                    return false;
                }
            }
            return true;
        } else if (value instanceof FEELFunction && type instanceof FunctionType) {
            FunctionDefinition<Type, DMNContext> functionDefinition = ((FEELFunction) value).getFunctionDefinition();
            return conformsTo(functionDefinition.getType(), type);
        } else if (value instanceof DMNInvocable && type instanceof FunctionType) {
            Type valueType = ((DMNInvocable) value).getType();
            return conformsTo(valueType, type);
        } else if (value instanceof DMNFunction && type instanceof FunctionType) {
            Type valueType = ((DMNFunction) value).getType();
            return conformsTo(valueType, type);
        } else if (value instanceof BuiltinFunction && type instanceof FunctionType) {
            // At least one conforms
            List<Declaration> declarations = ((BuiltinFunction) value).getDeclarations();
            for (Declaration d: declarations) {
                if (conformsTo(d.getType(), type)) {
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

    protected static boolean equivalentTo(List<Type> list1, List<Type> list2) {
        if (list1.size() != list2.size()) {
            return false;
        }
        for (int i = 0; i < list1.size(); i++) {
            if (!Type.equivalentTo(list1.get(i), list2.get(i))) {
                return false;
            }
        }
        return true;
    }

    protected static boolean conformsTo(List<Type> list1, List<Type> list2) {
        if (list1.size() != list2.size()) {
            return false;
        }
        for (int i = 0; i < list1.size(); i++) {
            if (!Type.conformsTo(list1.get(i), list2.get(i))) {
                return false;
            }
        }
        return true;
    }

    // Check only types that share the same class
    protected abstract boolean equivalentTo(Type other);

    // Check only types that share the same class
    protected abstract boolean conformsTo(Type other);

    public void validate() {
        if (!isFullySpecified()) {
            throw new DMNRuntimeException(String.format("Type '%s' is partially specified", this));
        }
    }

    public abstract boolean isFullySpecified();

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
