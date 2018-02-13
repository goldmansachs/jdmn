/**
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

import java.util.HashMap;
import java.util.Map;

import static com.gs.dmn.feel.analysis.semantics.type.BooleanType.BOOLEAN;
import static com.gs.dmn.feel.analysis.semantics.type.DateTimeType.DATE_AND_TIME;
import static com.gs.dmn.feel.analysis.semantics.type.DateType.DATE;
import static com.gs.dmn.feel.analysis.semantics.type.DurationType.DAYS_AND_TIME_DURATION;
import static com.gs.dmn.feel.analysis.semantics.type.DurationType.YEARS_AND_MONTHS_DURATION;
import static com.gs.dmn.feel.analysis.semantics.type.EnumerationType.ENUMERATION;
import static com.gs.dmn.feel.analysis.semantics.type.NumberType.NUMBER;
import static com.gs.dmn.feel.analysis.semantics.type.StringType.STRING;
import static com.gs.dmn.feel.analysis.semantics.type.TimeType.TIME;

public class UniformJavaTimeFEELTypeTranslator implements FEELTypeTranslator {
    private static final Map<String, String> TIME_FEEL_TO_JAVA_TYPE = new HashMap() {{
        put(NUMBER.getName(), java.math.BigDecimal.class.getName());
        put(BOOLEAN.getName(), java.lang.Boolean.class.getSimpleName());
        put(STRING.getName(), java.lang.String.class.getSimpleName());
        put(DATE.getName(), java.time.ZonedDateTime.class.getName());
        put(TIME.getName(), java.time.ZonedDateTime.class.getName());
        put(DATE_AND_TIME.getName(), java.time.ZonedDateTime.class.getName());
        put(DAYS_AND_TIME_DURATION.getName(), javax.xml.datatype.Duration.class.getName());
        put(YEARS_AND_MONTHS_DURATION.getName(), javax.xml.datatype.Duration.class.getName());
        put(ENUMERATION.getName(), java.lang.String.class.getSimpleName());
    }};

    private static final Map<String, String> TIME_FEEL_TO_QUALIFIED_JAVA_TYPE = new HashMap() {{
        put(NUMBER.getName(), java.math.BigDecimal.class.getName());
        put(BOOLEAN.getName(), java.lang.Boolean.class.getName());
        put(STRING.getName(), java.lang.String.class.getName());
        put(DATE.getName(), java.time.ZonedDateTime.class.getName());
        put(TIME.getName(), java.time.ZonedDateTime.class.getName());
        put(DATE_AND_TIME.getName(), java.time.ZonedDateTime.class.getName());
        put(DAYS_AND_TIME_DURATION.getName(), javax.xml.datatype.Duration.class.getName());
        put(YEARS_AND_MONTHS_DURATION.getName(), javax.xml.datatype.Duration.class.getName());
        put(ENUMERATION.getName(), java.lang.String.class.getName());
    }};

    @Override
    public String toJavaType(String feelType) {
        return TIME_FEEL_TO_JAVA_TYPE.get(feelType);
    }

    @Override
    public String toQualifiedJavaType(String feelType) {
        return TIME_FEEL_TO_QUALIFIED_JAVA_TYPE.get(feelType);
    }
}
