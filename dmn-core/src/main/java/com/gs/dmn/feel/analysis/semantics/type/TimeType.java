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

public class TimeType extends DataType {
    public static final TimeType TIME = new TimeType();

    private static final Map<String, Type> MEMBERS = new LinkedHashMap() {{
        put("minute", NUMBER);
        put("second", NUMBER);
        put("time offset", DurationType.DAYS_AND_TIME_DURATION);
        put("timezone", DurationType.DAYS_AND_TIME_DURATION);
    }};

    public static Type getMemberType(String member) {
        return MEMBERS.get(member);
    }

    public TimeType() {
        super("time", "time");
    }

    @Override
    public boolean equivalentTo(Type other) {
        return other == TIME;
    }
}
