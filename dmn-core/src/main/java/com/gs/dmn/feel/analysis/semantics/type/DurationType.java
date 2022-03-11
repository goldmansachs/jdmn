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

import com.gs.dmn.feel.FEELConstants;
import com.gs.dmn.feel.analysis.semantics.SemanticError;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.gs.dmn.feel.analysis.semantics.type.NumberType.NUMBER;

public class DurationType extends ComparableDataType {
    // Main types
    public static final DurationType ANY_DURATION = new DurationType("duration");
    public static final DurationType DAYS_AND_TIME_DURATION = new DurationType("days and time duration");
    public static final DurationType YEARS_AND_MONTHS_DURATION = new DurationType("years and months duration");

    // Aliases
    public static final DurationType DAY_TIME_DURATION = new DurationType("dayTimeDuration");
    public static final DurationType YEAR_MONTH_DURATION = new DurationType("yearMonthDuration");

    public DurationType(String name) {
        super(name, FEELConstants.DURATION_LITERAL_FUNCTION_NAME);
    }

    @Override
    protected boolean equivalentTo(Type other) {
        return (this == DAYS_AND_TIME_DURATION || this == DAY_TIME_DURATION) && (other == DAYS_AND_TIME_DURATION || other == DAY_TIME_DURATION)
                ||
                (this == YEARS_AND_MONTHS_DURATION || this == YEAR_MONTH_DURATION) && (other == YEARS_AND_MONTHS_DURATION || other == YEAR_MONTH_DURATION)
                ||
                (this == ANY_DURATION && other == ANY_DURATION)
                ;
    }

    @Override
    protected boolean conformsTo(Type other) {
        return equivalentTo(other) || other == ANY_DURATION || other == COMPARABLE;
    }

    private static final Map<String, Type> YEARS_AND_MONTHS_DURATION_MEMBERS = new LinkedHashMap<>();
    static {
        YEARS_AND_MONTHS_DURATION_MEMBERS.put("years", NUMBER);
        YEARS_AND_MONTHS_DURATION_MEMBERS.put("months", NUMBER);
    }

    private static final Map<String, Type> DAYS_AND_TIME_DURATION_MEMBERS = new LinkedHashMap<>();
    static {
        DAYS_AND_TIME_DURATION_MEMBERS.put("seconds", NUMBER);
        DAYS_AND_TIME_DURATION_MEMBERS.put("minutes", NUMBER);
        DAYS_AND_TIME_DURATION_MEMBERS.put("hours", NUMBER);
        DAYS_AND_TIME_DURATION_MEMBERS.put("days", NUMBER);
    }

    public static Type getMemberType(Type sourceType, String member) {
        if (YEAR_MONTH_DURATION.equivalentTo(sourceType)) {
            Type type = YEARS_AND_MONTHS_DURATION_MEMBERS.get(member);
            if (Type.isNull(type)) {
                throw new SemanticError(String.format("Cannot find member '%s' of type '%s'", member, sourceType.toString()));
            }
            return type;
        } else if (DAYS_AND_TIME_DURATION.equivalentTo(sourceType)) {
            Type type = DAYS_AND_TIME_DURATION_MEMBERS.get(member);
            if (Type.isNull(type)) {
                throw new SemanticError(String.format("Cannot find member '%s' of type '%s'", member, sourceType.toString()));
            }
            return type;
        } else {
            throw new SemanticError(String.format("Cannot find member '%s' of type '%s'", member, sourceType.toString()));
        }
    }
}
