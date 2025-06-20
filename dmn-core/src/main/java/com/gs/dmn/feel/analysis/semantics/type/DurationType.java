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

import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.error.SemanticError;
import com.gs.dmn.feel.FEELConstants;

public class DurationType extends ComparableDataType {
    public static final DurationType ANY_DURATION = new DurationType("duration");

    public static Type getMemberType(Type sourceType, String member) {
        if (YearsAndMonthsDurationType.YEAR_MONTH_DURATION.equivalentTo(sourceType)) {
            return YearsAndMonthsDurationType.getMemberType(sourceType, member);
        } else if (DaysAndTimeDurationType.DAYS_AND_TIME_DURATION.equivalentTo(sourceType)) {
            return DaysAndTimeDurationType.getMemberType(sourceType, member);
        } else if (DurationType.ANY_DURATION.equivalentTo(sourceType)) {
            Type memberType = null;
            try {
                memberType = YearsAndMonthsDurationType.getMemberType(sourceType, member);
            } catch (Exception ignored) {
            }
            return memberType == null ? DaysAndTimeDurationType.getMemberType(sourceType, member) : memberType;
        } else {
            throw new SemanticError(String.format("Cannot find member '%s' of type '%s'", member, sourceType.toString()));
        }
    }

    public DurationType(String name) {
        super(name, FEELConstants.DURATION_LITERAL_FUNCTION_NAME);
    }

    @Override
    public boolean conformsTo(Type other) {
        return equivalentTo(other) || other == ANY_DURATION || other == COMPARABLE;
    }
}
