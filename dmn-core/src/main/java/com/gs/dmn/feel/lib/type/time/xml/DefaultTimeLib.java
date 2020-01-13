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
package com.gs.dmn.feel.lib.type.time.xml;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQueries;

public class DefaultTimeLib {
    public static final DateTimeFormatter FEEL_TIME;

    static {
        FEEL_TIME = new DateTimeFormatterBuilder().parseCaseInsensitive()
                .append(DateTimeFormatter.ISO_LOCAL_TIME)
                .optionalStart()
                .appendLiteral("@")
                .appendZoneRegionId()
                .optionalEnd()
                .optionalStart()
                .appendOffsetId()
                .optionalEnd()
                .toFormatter();
    }

    public TemporalAccessor time(String literal) {
        if (literal == null) {
            throw new IllegalArgumentException("Time literal cannot be null");
        }

        try {
            TemporalAccessor parsed = FEEL_TIME.parse(literal);

            if (parsed.query(TemporalQueries.offset()) != null) {
                OffsetTime asOffSetTime = parsed.query(OffsetTime::from);
                return asOffSetTime;
            } else if (parsed.query(TemporalQueries.zone()) == null) {
                LocalTime asLocalTime = parsed.query(LocalTime::from);
                return asLocalTime;
            }

            return parsed;
        } catch (DateTimeException e) {
            throw new RuntimeException("Parsing exception in time literal", e);
        }
    }
}
