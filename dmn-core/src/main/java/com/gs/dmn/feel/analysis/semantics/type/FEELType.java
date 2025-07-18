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

import com.gs.dmn.el.analysis.semantics.type.AnyType;
import com.gs.dmn.el.analysis.semantics.type.ListType;
import com.gs.dmn.el.analysis.semantics.type.NullType;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ConversionKind;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.gs.dmn.feel.analysis.syntax.ConversionKind.*;

public interface FEELType {
    static ConversionKind conversionKind(Type expectedType, Type actualType) {
        expectedType = Type.extractTypeFromConstraint(expectedType);
        actualType = Type.extractTypeFromConstraint(actualType);
        // Check when conversion is not needed
        if (Type.equivalentTo(actualType, expectedType)) {
            // Same type, no conversion
            return NONE;
        } else if (Type.isAny(expectedType) || Type.isNullType(actualType)) {
            // Upper bound or lower bound, no conversion
            return NONE;
        }

        // Check data types
        if (expectedType instanceof DataType && actualType instanceof DataType) {
            if (Type.conformsTo(actualType, expectedType)) {
                return NONE;
            } else if (expectedType instanceof DurationType && actualType instanceof DurationType) {
                // Ignore duration variants
                return NONE;
            }
        } else if (expectedType instanceof EnumerationType && actualType instanceof EnumerationType) {
            if (Type.conformsTo(actualType, expectedType)) {
                return NONE;
            }
        } else if (expectedType instanceof RangeType && actualType instanceof RangeType) {
            if (Type.conformsTo(actualType, expectedType)) {
                return NONE;
            }
        } else if (expectedType instanceof CompositeDataType && actualType instanceof CompositeDataType) {
            if (Type.conformsTo(actualType, expectedType)) {
                // Convert to ItemDefinition
                if (expectedType instanceof ItemDefinitionType) {
                    // Convert to Item Definitions
                    return TO_ITEM_DEFINITION;
                }
                return NONE;
            } else {
                // Actual type needs refinement, convert to ItemDefinition
                if (expectedType instanceof ItemDefinitionType && Type.isAny(actualType)) {
                    // Convert to Item Definitions
                    return TO_ITEM_DEFINITION;
                }
            }
        } else if (expectedType instanceof ListType && actualType instanceof ListType) {
            if (Type.conformsTo(actualType, expectedType)) {
                // Not equivalent types, convert to list of Item Definitions
                Type expectedElementType = ((ListType) expectedType).getElementType();
                if (expectedElementType instanceof ItemDefinitionType) {
                    return TO_LIST_OF_ITEM_DEFINITION;
                }
                return NONE;
            } else {
                // Actual type needs refinement, convert to list of Item Definitions
                Type expectedElementType = ((ListType) expectedType).getElementType();
                Type actualElementType = ((ListType) actualType).getElementType();
                if (expectedElementType instanceof ItemDefinitionType && Type.isAny(actualElementType)) {
                    return TO_LIST_OF_ITEM_DEFINITION;
                }
            }
        } else if (expectedType instanceof FunctionType && actualType instanceof FunctionType) {
            if (Type.conformsTo(actualType, expectedType)) {
                return NONE;
            }
        }

        // Check implicit conversions
        if (expectedType instanceof ListType && Type.conformsTo(actualType, ((ListType) expectedType).getElementType())) {
            // actual type conforms to expected list element, convert from element to singleton list
            return ELEMENT_TO_SINGLETON_LIST;
        } else if (actualType instanceof ListType && !Type.equivalentTo(actualType, com.gs.dmn.feel.analysis.semantics.type.ListType.EMPTY_LIST) && Type.conformsTo(((ListType) actualType).getElementType(), expectedType)) {
            // actual element conforms to expected type and not empty list, convert from singleton list to element
            return SINGLETON_LIST_TO_ELEMENT;
        } else if (actualType instanceof DateType && expectedType instanceof DateTimeType) {
            // actual type is Date and expected type is date and time, convert from date to date and time midnight
            return DATE_TO_UTC_MIDNIGHT;
        }

        return CONFORMS_TO;
    }

    Map<String, Type> FEEL_NAME_TO_FEEL_TYPE = new LinkedHashMap<>() {{
        // Primitive types
        put(NumberType.NUMBER.getName(), NumberType.NUMBER);
        put(BooleanType.BOOLEAN.getName(), BooleanType.BOOLEAN);
        put(StringType.STRING.getName(), StringType.STRING);
        put(EnumerationType.ENUMERATION.getName(), StringType.STRING);
        put(DateType.DATE.getName(), DateType.DATE);
        put(TimeType.TIME.getName(), TimeType.TIME);
        put(DateTimeType.DATE_TIME_CAMEL.getName(), DateTimeType.DATE_AND_TIME);
        put(DateTimeType.DATE_TIME.getName(), DateTimeType.DATE_AND_TIME);
        put(DateTimeType.DATE_AND_TIME.getName(), DateTimeType.DATE_AND_TIME);
        put(DaysAndTimeDurationType.DAYS_AND_TIME_DURATION.getName(), DaysAndTimeDurationType.DAYS_AND_TIME_DURATION);
        put(DaysAndTimeDurationType.DAY_TIME_DURATION.getName(), DaysAndTimeDurationType.DAYS_AND_TIME_DURATION);
        put(YearsAndMonthsDurationType.YEARS_AND_MONTHS_DURATION.getName(), YearsAndMonthsDurationType.YEARS_AND_MONTHS_DURATION);
        put(YearsAndMonthsDurationType.YEAR_MONTH_DURATION.getName(), YearsAndMonthsDurationType.YEARS_AND_MONTHS_DURATION);

        // Abstract types
        put(AnyType.ANY.getName(), AnyType.ANY);
        put(NullType.NULL.getName(), NullType.NULL);
        put(TemporalType.TEMPORAL.getName(), TemporalType.TEMPORAL);
        put(DurationType.DURATION.getName(), DurationType.DURATION);
        put(ComparableDataType.COMPARABLE.getName(), ComparableDataType.COMPARABLE);
        // context is equivalent to context<>
        put("context", ContextType.CONTEXT);
    }};

    List<String> FEEL_TYPE_NAMES = Arrays.asList(
            AnyType.ANY.getName(), NumberType.NUMBER.getName(), BooleanType.BOOLEAN.getName(), StringType.STRING.getName(),
            DateType.DATE.getName(), TimeType.TIME.getName(), DateTimeType.DATE_TIME_CAMEL.getName(), DateTimeType.DATE_TIME.getName(), DateTimeType.DATE_AND_TIME.getName(),
            DaysAndTimeDurationType.DAYS_AND_TIME_DURATION.getName(), YearsAndMonthsDurationType.YEARS_AND_MONTHS_DURATION.getName(), EnumerationType.ENUMERATION.getName());

    List<Type> FEEL_PRIMITIVE_TYPES = Arrays.asList(new Type[]{
            AnyType.ANY,
            NumberType.NUMBER,
            BooleanType.BOOLEAN,
            StringType.STRING,
            StringType.STRING,
            DateType.DATE,
            TimeType.TIME,
            DateTimeType.DATE_AND_TIME,
            DaysAndTimeDurationType.DAYS_AND_TIME_DURATION,
            YearsAndMonthsDurationType.YEARS_AND_MONTHS_DURATION
    });

    Map<Type, String> FEEL_PRIMITIVE_TYPE_TO_NATIVE_CONVERSION_FUNCTION = new LinkedHashMap<>() {{
        put(NumberType.NUMBER, "number");
        put(BooleanType.BOOLEAN, null);
        put(StringType.STRING, null);
        put(DateType.DATE, "date");
        put(TimeType.TIME, "time");
        put(DateTimeType.DATE_TIME, "dateAndTime");
        put(DateTimeType.DATE_AND_TIME, "dateAndTime");
        put(DateTimeType.DATE_TIME_CAMEL, "dateAndTime");
        put(DaysAndTimeDurationType.DAYS_AND_TIME_DURATION, "duration");
        put(YearsAndMonthsDurationType.YEARS_AND_MONTHS_DURATION, "duration");
    }};
}
