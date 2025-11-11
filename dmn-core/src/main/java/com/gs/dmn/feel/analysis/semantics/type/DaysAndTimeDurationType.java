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
import com.gs.dmn.error.SemanticErrorException;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.gs.dmn.feel.analysis.semantics.type.NumberType.NUMBER;

public class DaysAndTimeDurationType extends DurationType {
    public static final DurationType DAYS_AND_TIME_DURATION = new DaysAndTimeDurationType("days and time duration");
    // Aliases
    public static final DurationType DAY_TIME_DURATION = new DaysAndTimeDurationType("dayTimeDuration");

    private static final Map<String, Type> MEMBERS = new LinkedHashMap<>();
    static {
        MEMBERS.put("seconds", NUMBER);
        MEMBERS.put("minutes", NUMBER);
        MEMBERS.put("hours", NUMBER);
        MEMBERS.put("days", NUMBER);
    }

    public static Type getMemberType(Type sourceType, String member) {
        Type type = MEMBERS.get(member);
        if (Type.isNull(type)) {
            throw new SemanticErrorException(String.format("Cannot find member '%s' of type '%s'", member, sourceType.toString()));
        }
        return type;
    }

    public DaysAndTimeDurationType(String name) {
        super(name);
    }

    @Override
    public boolean equivalentTo(Type other) {
        return other == DAYS_AND_TIME_DURATION
                || other == DAY_TIME_DURATION
                ;
    }
}
