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
package com.gs.dmn.feel.lib;

import java.math.BigDecimal;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQueries;

public class TimeUtil {
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

    public static TemporalAccessor time(String literal) {
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

    private static final BigDecimal NANO_MULT = BigDecimal.valueOf(1000000000);

    public static TemporalAccessor time(Number hour, Number minute, Number seconds, Duration offset) {
        if (hour == null) {
            throw new RuntimeException("Hour cannot be null");
        }
        if (minute == null) {
            throw new RuntimeException("Minute cannot be null");
        }
        if (seconds == null) {
            throw new RuntimeException("Seconds cannot be null");
        }

        try {
            int nanosecs = 0;
            if (seconds instanceof BigDecimal) {
                BigDecimal secs = (BigDecimal) seconds;
                nanosecs = secs.subtract(secs.setScale(0, BigDecimal.ROUND_DOWN)).multiply(NANO_MULT).intValue();
            }

            if (offset == null) {
                return LocalTime.of(hour.intValue(), minute.intValue(), seconds.intValue(),
                        nanosecs);
            } else {
                return OffsetTime.of(hour.intValue(), minute.intValue(), seconds.intValue(),
                        nanosecs,
                        ZoneOffset.ofTotalSeconds((int) offset.getSeconds()));
            }
        } catch (DateTimeException e) {
            throw new RuntimeException("Cannot create time from arguments", e);
        }
    }

    public static TemporalAccessor time(TemporalAccessor date) {
        if (date == null) {
            throw new RuntimeException("Date cannot be null");
        }

        try {
            if (!date.isSupported(ChronoField.HOUR_OF_DAY)) {
                return new DateTimeUtil().dateAndTime(date, OffsetTime.of(0, 0, 0, 0, ZoneOffset.UTC));
            } else if (date.query(TemporalQueries.offset()) == null) {
                return LocalTime.from(date);
            } else {
                ZoneId zone = date.query(TemporalQueries.zoneId());
                if (!(zone instanceof ZoneOffset)) {
                    return time(date.query(TemporalQueries.localTime()) + "@" + zone);
                } else {
                    return OffsetTime.from(date);
                }
            }
        } catch (DateTimeException e) {
            throw new RuntimeException("Cannot create time from arguments", e);
        }
    }
}
