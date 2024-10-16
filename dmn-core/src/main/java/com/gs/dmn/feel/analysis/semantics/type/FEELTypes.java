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
import com.gs.dmn.el.analysis.semantics.type.Type;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FEELTypes {
    public static final Map<String, Type> FEEL_NAME_TO_FEEL_TYPE = new LinkedHashMap<>();
    static {
        FEEL_NAME_TO_FEEL_TYPE.put(AnyType.ANY.getName(), AnyType.ANY);
        FEEL_NAME_TO_FEEL_TYPE.put(NumberType.NUMBER.getName(), NumberType.NUMBER);
        FEEL_NAME_TO_FEEL_TYPE.put(BooleanType.BOOLEAN.getName(), BooleanType.BOOLEAN);
        FEEL_NAME_TO_FEEL_TYPE.put(StringType.STRING.getName(), StringType.STRING);
        FEEL_NAME_TO_FEEL_TYPE.put(EnumerationType.ENUMERATION.getName(), StringType.STRING);
        FEEL_NAME_TO_FEEL_TYPE.put(DateType.DATE.getName(), DateType.DATE);
        FEEL_NAME_TO_FEEL_TYPE.put(TimeType.TIME.getName(), TimeType.TIME);
        FEEL_NAME_TO_FEEL_TYPE.put(DateTimeType.DATE_TIME_CAMEL.getName(), DateTimeType.DATE_AND_TIME);
        FEEL_NAME_TO_FEEL_TYPE.put(DateTimeType.DATE_TIME.getName(), DateTimeType.DATE_AND_TIME);
        FEEL_NAME_TO_FEEL_TYPE.put(DateTimeType.DATE_AND_TIME.getName(), DateTimeType.DATE_AND_TIME);
        FEEL_NAME_TO_FEEL_TYPE.put(DaysAndTimeDurationType.DAYS_AND_TIME_DURATION.getName(), DaysAndTimeDurationType.DAYS_AND_TIME_DURATION);
        FEEL_NAME_TO_FEEL_TYPE.put(DaysAndTimeDurationType.DAY_TIME_DURATION.getName(), DaysAndTimeDurationType.DAYS_AND_TIME_DURATION);
        FEEL_NAME_TO_FEEL_TYPE.put(YearsAndMonthsDurationType.YEARS_AND_MONTHS_DURATION.getName(), YearsAndMonthsDurationType.YEARS_AND_MONTHS_DURATION);
        FEEL_NAME_TO_FEEL_TYPE.put(YearsAndMonthsDurationType.YEAR_MONTH_DURATION.getName(), YearsAndMonthsDurationType.YEARS_AND_MONTHS_DURATION);
    }

    public static final List<String> FEEL_TYPE_NAMES = Arrays.asList(
            AnyType.ANY.getName(), NumberType.NUMBER.getName(), BooleanType.BOOLEAN.getName(), StringType.STRING.getName(),
            DateType.DATE.getName(), TimeType.TIME.getName(), DateTimeType.DATE_TIME_CAMEL.getName(), DateTimeType.DATE_TIME.getName(), DateTimeType.DATE_AND_TIME.getName(),
            DaysAndTimeDurationType.DAYS_AND_TIME_DURATION.getName(), YearsAndMonthsDurationType.YEARS_AND_MONTHS_DURATION.getName(), EnumerationType.ENUMERATION.getName());

    public static final List<Type> FEEL_PRIMITIVE_TYPES = Arrays.asList(new Type[]{
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

    public static final Map<Type, String> FEEL_PRIMITIVE_TYPE_TO_JAVA_CONVERSION_FUNCTION = new LinkedHashMap<>();
    static {
        FEEL_PRIMITIVE_TYPE_TO_JAVA_CONVERSION_FUNCTION.put(NumberType.NUMBER, "number");
        FEEL_PRIMITIVE_TYPE_TO_JAVA_CONVERSION_FUNCTION.put(BooleanType.BOOLEAN, null);
        FEEL_PRIMITIVE_TYPE_TO_JAVA_CONVERSION_FUNCTION.put(StringType.STRING, null);
        FEEL_PRIMITIVE_TYPE_TO_JAVA_CONVERSION_FUNCTION.put(DateType.DATE, "date");
        FEEL_PRIMITIVE_TYPE_TO_JAVA_CONVERSION_FUNCTION.put(TimeType.TIME, "time");
        FEEL_PRIMITIVE_TYPE_TO_JAVA_CONVERSION_FUNCTION.put(DateTimeType.DATE_TIME, "dateAndTime");
        FEEL_PRIMITIVE_TYPE_TO_JAVA_CONVERSION_FUNCTION.put(DateTimeType.DATE_AND_TIME, "dateAndTime");
        FEEL_PRIMITIVE_TYPE_TO_JAVA_CONVERSION_FUNCTION.put(DateTimeType.DATE_TIME_CAMEL, "dateAndTime");
        FEEL_PRIMITIVE_TYPE_TO_JAVA_CONVERSION_FUNCTION.put(DaysAndTimeDurationType.DAYS_AND_TIME_DURATION, "duration");
        FEEL_PRIMITIVE_TYPE_TO_JAVA_CONVERSION_FUNCTION.put(YearsAndMonthsDurationType.YEARS_AND_MONTHS_DURATION, "duration");
    }
}
