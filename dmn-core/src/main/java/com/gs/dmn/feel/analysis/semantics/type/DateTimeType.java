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
package com.gs.dmn.feel.analysis.semantics.type;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.gs.dmn.feel.analysis.semantics.type.NumberType.NUMBER;

public class DateTimeType extends DataType {
    // Main type
    public static final DateTimeType DATE_AND_TIME = new DateTimeType("date and time");

    // Aliases
    public static final DateTimeType DATE_TIME_CAMEL = new DateTimeType("dateTime");
    public static final DateTimeType DATE_TIME = new DateTimeType("datetime");

    private static final Map<String, Type> MEMBERS = new LinkedHashMap() {{
        put("year", NUMBER);
        put("month", NUMBER);
        put("day", NUMBER);
        put("hour", NUMBER);
        put("minute", NUMBER);
        put("second", NUMBER);
        put("time offset", DurationType.DAYS_AND_TIME_DURATION);
        put("timezone", DurationType.DAYS_AND_TIME_DURATION);
    }};

    public static Type getMemberType(String member) {
        return MEMBERS.get(member);
    }

    @Override
    public boolean equivalentTo(Type other) {
        return other == DATE_AND_TIME
                || other == DATE_TIME
                || other == DATE_TIME_CAMEL;
    }

    public DateTimeType(String datetime) {
        super(datetime, "date and time");
    }
}
