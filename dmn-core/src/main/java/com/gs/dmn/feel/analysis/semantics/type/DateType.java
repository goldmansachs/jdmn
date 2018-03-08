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

public class DateType extends DataType {
    public static final DateType DATE = new DateType();

    private static final Map<String, Type> MEMBERS = new LinkedHashMap() {{
        put("year", NumberType.NUMBER);
        put("month", NumberType.NUMBER);
        put("day", NumberType.NUMBER);
    }};

    public static Type getMemberType(String member) {
        return MEMBERS.get(member);
    }

    public DateType() {
        super("date", "date");
    }

    @Override
    public boolean equivalentTo(Type other) {
        return other == DATE;
    }

    @Override
    public boolean conformsTo(Type other) {
        return equivalentTo(other) || other == AnyType.ANY || other == DateTimeType.DATE_AND_TIME;
    }

}
