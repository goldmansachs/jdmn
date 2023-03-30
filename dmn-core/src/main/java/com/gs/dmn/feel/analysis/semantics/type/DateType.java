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
import com.gs.dmn.feel.FEELConstants;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.gs.dmn.feel.analysis.semantics.type.NumberType.NUMBER;

public class DateType extends TemporalType {
    public static final DateType DATE = new DateType();

    private static final Map<String, Type> MEMBERS = new LinkedHashMap<>();
    static {
        MEMBERS.put("year", NumberType.NUMBER);
        MEMBERS.put("month", NumberType.NUMBER);
        MEMBERS.put("day", NumberType.NUMBER);
        MEMBERS.put("weekday", NUMBER);
    }

    public static Type getMemberType(String member) {
        return MEMBERS.get(member);
    }

    public DateType() {
        super("date", FEELConstants.DATE_LITERAL_FUNCTION_NAME);
    }

    @Override
    public boolean equivalentTo(Type other) {
        return other == DATE;
    }

    @Override
    public boolean conformsTo(Type other) {
        return other == DATE || other == TEMPORAL || other == COMPARABLE;
    }
}
