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

import com.gs.dmn.feel.analysis.semantics.type.*;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.gs.dmn.el.analysis.semantics.type.AnyType.ANY;
import static com.gs.dmn.el.analysis.semantics.type.NullType.NULL;
import static com.gs.dmn.feel.analysis.semantics.type.BooleanType.BOOLEAN;
import static com.gs.dmn.feel.analysis.semantics.type.DateTimeType.*;
import static com.gs.dmn.feel.analysis.semantics.type.DateType.DATE;
import static com.gs.dmn.feel.analysis.semantics.type.DaysAndTimeDurationType.DAYS_AND_TIME_DURATION;
import static com.gs.dmn.feel.analysis.semantics.type.DaysAndTimeDurationType.DAY_TIME_DURATION;
import static com.gs.dmn.feel.analysis.semantics.type.EnumerationType.ENUMERATION;
import static com.gs.dmn.feel.analysis.semantics.type.NumberType.NUMBER;
import static com.gs.dmn.feel.analysis.semantics.type.StringType.STRING;
import static com.gs.dmn.feel.analysis.semantics.type.TimeType.TIME;
import static com.gs.dmn.feel.analysis.semantics.type.YearsAndMonthsDurationType.YEARS_AND_MONTHS_DURATION;
import static com.gs.dmn.feel.analysis.semantics.type.YearsAndMonthsDurationType.YEAR_MONTH_DURATION;

public class StandardNativeTypeToPythonFactory extends PythonTypeFactory {
    static final Map<String, String> FEEL_TYPE_TO_QUALIFIED_JAVA_TYPE = new LinkedHashMap<>();
    static {
        FEEL_TYPE_TO_QUALIFIED_JAVA_TYPE.put(NUMBER.getName(), "decimal.Decimal");
        FEEL_TYPE_TO_QUALIFIED_JAVA_TYPE.put(STRING.getName(), "str");
        FEEL_TYPE_TO_QUALIFIED_JAVA_TYPE.put(BOOLEAN.getName(), "bool");
        FEEL_TYPE_TO_QUALIFIED_JAVA_TYPE.put(DATE.getName(), "datetime.date");
        FEEL_TYPE_TO_QUALIFIED_JAVA_TYPE.put(TIME.getName(), "datetime.time");
        FEEL_TYPE_TO_QUALIFIED_JAVA_TYPE.put(DATE_AND_TIME.getName(), "datetime.datetime");
        FEEL_TYPE_TO_QUALIFIED_JAVA_TYPE.put(DATE_TIME_CAMEL.getName(), "datetime.datetime");
        FEEL_TYPE_TO_QUALIFIED_JAVA_TYPE.put(DATE_TIME.getName(), "datetime.datetime");
        FEEL_TYPE_TO_QUALIFIED_JAVA_TYPE.put(YEARS_AND_MONTHS_DURATION.getName(), "isodate.Duration");
        FEEL_TYPE_TO_QUALIFIED_JAVA_TYPE.put(YEAR_MONTH_DURATION.getName(), "isodate.Duration");
        FEEL_TYPE_TO_QUALIFIED_JAVA_TYPE.put(DAYS_AND_TIME_DURATION.getName(), "datetime.timedelta");
        FEEL_TYPE_TO_QUALIFIED_JAVA_TYPE.put(DAY_TIME_DURATION.getName(), "datetime.timedelta");
        FEEL_TYPE_TO_QUALIFIED_JAVA_TYPE.put(ENUMERATION.getName(), "str");

        FEEL_TYPE_TO_QUALIFIED_JAVA_TYPE.put(ANY.getName(), "typing.Any");
        FEEL_TYPE_TO_QUALIFIED_JAVA_TYPE.put(NULL.getName(), "typing.Any");
        FEEL_TYPE_TO_QUALIFIED_JAVA_TYPE.put(TemporalType.TEMPORAL.getName(), "typing.Any");
        FEEL_TYPE_TO_QUALIFIED_JAVA_TYPE.put(DurationType.DURATION.getName(), "isodate.Duration");
        FEEL_TYPE_TO_QUALIFIED_JAVA_TYPE.put(ComparableDataType.COMPARABLE.getName(), "typing.Any");
        FEEL_TYPE_TO_QUALIFIED_JAVA_TYPE.put(FEELType.typeName(ContextType.CONTEXT), "jdmn.runtime.Context");
    }

    static final Map<String, String> FEEL_TYPE_TO_JAVA_TYPE = FEEL_TYPE_TO_QUALIFIED_JAVA_TYPE;

    @Override
    public String toNativeType(String feelType) {
        return FEEL_TYPE_TO_JAVA_TYPE.get(feelType);
    }

    @Override
    public String toQualifiedNativeType(String feelType) {
        return FEEL_TYPE_TO_QUALIFIED_JAVA_TYPE.get(feelType);
    }
}
