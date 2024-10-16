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
package com.gs.dmn.feel.synthesis.type;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.gs.dmn.el.analysis.semantics.type.AnyType.ANY;
import static com.gs.dmn.el.analysis.semantics.type.NullType.NULL;
import static com.gs.dmn.feel.analysis.semantics.type.BooleanType.BOOLEAN;
import static com.gs.dmn.feel.analysis.semantics.type.DateTimeType.DATE_AND_TIME;
import static com.gs.dmn.feel.analysis.semantics.type.DateType.DATE;
import static com.gs.dmn.feel.analysis.semantics.type.DaysAndTimeDurationType.DAYS_AND_TIME_DURATION;
import static com.gs.dmn.feel.analysis.semantics.type.YearsAndMonthsDurationType.YEARS_AND_MONTHS_DURATION;
import static com.gs.dmn.feel.analysis.semantics.type.EnumerationType.ENUMERATION;
import static com.gs.dmn.feel.analysis.semantics.type.NumberType.NUMBER;
import static com.gs.dmn.feel.analysis.semantics.type.StringType.STRING;
import static com.gs.dmn.feel.analysis.semantics.type.TimeType.TIME;

public class MixedJavaTimeNativeTypeFactory extends JavaTypeFactory {
    private static final Map<String, String> FEEL_TYPE_TO_JAVA_TYPE = new LinkedHashMap<>();
    static {
        FEEL_TYPE_TO_JAVA_TYPE.put(ENUMERATION.getName(), String.class.getSimpleName());
        FEEL_TYPE_TO_JAVA_TYPE.put(YEARS_AND_MONTHS_DURATION.getName(), javax.xml.datatype.Duration.class.getName());
        FEEL_TYPE_TO_JAVA_TYPE.put(DAYS_AND_TIME_DURATION.getName(), javax.xml.datatype.Duration.class.getName());
        FEEL_TYPE_TO_JAVA_TYPE.put(DATE_AND_TIME.getName(), java.time.ZonedDateTime.class.getName());
        FEEL_TYPE_TO_JAVA_TYPE.put(TIME.getName(), java.time.OffsetTime.class.getName());
        FEEL_TYPE_TO_JAVA_TYPE.put(DATE.getName(), java.time.LocalDate.class.getName());
        FEEL_TYPE_TO_JAVA_TYPE.put(STRING.getName(), String.class.getSimpleName());
        FEEL_TYPE_TO_JAVA_TYPE.put(BOOLEAN.getName(), Boolean.class.getSimpleName());
        FEEL_TYPE_TO_JAVA_TYPE.put(NUMBER.getName(), java.math.BigDecimal.class.getName());
        FEEL_TYPE_TO_JAVA_TYPE.put(ANY.getName(), Object.class.getSimpleName());
        FEEL_TYPE_TO_JAVA_TYPE.put(NULL.getName(), Object.class.getSimpleName());
    }

    private static final Map<String, String> FEEL_TYPE_TO_QUALIFIED_JAVA_TYPE = new LinkedHashMap<>();
    static {
        FEEL_TYPE_TO_QUALIFIED_JAVA_TYPE.put(ENUMERATION.getName(), String.class.getName());
        FEEL_TYPE_TO_QUALIFIED_JAVA_TYPE.put(YEARS_AND_MONTHS_DURATION.getName(), javax.xml.datatype.Duration.class.getName());
        FEEL_TYPE_TO_QUALIFIED_JAVA_TYPE.put(DAYS_AND_TIME_DURATION.getName(), javax.xml.datatype.Duration.class.getName());
        FEEL_TYPE_TO_QUALIFIED_JAVA_TYPE.put(DATE_AND_TIME.getName(), java.time.ZonedDateTime.class.getName());
        FEEL_TYPE_TO_QUALIFIED_JAVA_TYPE.put(TIME.getName(), java.time.OffsetTime.class.getName());
        FEEL_TYPE_TO_QUALIFIED_JAVA_TYPE.put(DATE.getName(), java.time.LocalDate.class.getName());
        FEEL_TYPE_TO_QUALIFIED_JAVA_TYPE.put(STRING.getName(), String.class.getName());
        FEEL_TYPE_TO_QUALIFIED_JAVA_TYPE.put(BOOLEAN.getName(), Boolean.class.getName());
        FEEL_TYPE_TO_QUALIFIED_JAVA_TYPE.put(NUMBER.getName(), java.math.BigDecimal.class.getName());
        FEEL_TYPE_TO_QUALIFIED_JAVA_TYPE.put(ANY.getName(), Object.class.getName());
        FEEL_TYPE_TO_QUALIFIED_JAVA_TYPE.put(NULL.getName(), Object.class.getName());
    }

    @Override
    public String toNativeType(String feelType) {
        return FEEL_TYPE_TO_JAVA_TYPE.get(feelType);
    }

    @Override
    public String toQualifiedNativeType(String feelType) {
        return FEEL_TYPE_TO_QUALIFIED_JAVA_TYPE.get(feelType);
    }
}
