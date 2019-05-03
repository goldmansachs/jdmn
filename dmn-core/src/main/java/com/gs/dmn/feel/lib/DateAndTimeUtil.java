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
package com.gs.dmn.feel.lib;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQueries;
import java.util.TimeZone;

public class DateAndTimeUtil {
    public static final DateTimeFormatter FEEL_DATE_TIME;
    public static final DateTimeFormatter REGION_DATETIME_FORMATTER;

    static {
        FEEL_DATE_TIME = new DateTimeFormatterBuilder().parseCaseInsensitive()
                .append(DateUtil.FEEL_DATE)
                .appendLiteral('T')
                .append(TimeUtil.FEEL_TIME)
                .toFormatter();
        REGION_DATETIME_FORMATTER = new DateTimeFormatterBuilder().parseCaseInsensitive()
                .append(DateUtil.FEEL_DATE)
                .appendLiteral('T')
                .append(DateTimeFormatter.ISO_LOCAL_TIME)
                .appendLiteral("@")
                .appendZoneRegionId()
                .toFormatter();
    }

    public static TemporalAccessor dateAndTime(String literal) {
        if (literal == null) {
            throw new IllegalArgumentException("Date and time literal cannot be null");
        }
        if (!DateUtil.BEGIN_YEAR.matcher(literal).find()) {
            throw new IllegalArgumentException("Year is not not compliant with XML Schema Part 2 Datatypes");
        }

        try {
            if (literal.contains("T")) {
                TemporalAccessor value = FEEL_DATE_TIME.parse(literal);

                if (value.query(TemporalQueries.zoneId()) != null) {
                    ZonedDateTime asZonedDateTime = value.query(ZonedDateTime::from);
                    return asZonedDateTime;
                } else if (value.query(TemporalQueries.offset()) != null) {
                    OffsetDateTime asOffSetDateTime = value.query(OffsetDateTime::from);
                    return asOffSetDateTime;
                } else if (value.query(TemporalQueries.zone()) == null) {
                    LocalDateTime asLocalDateTime = value.query(LocalDateTime::from);
                    return asLocalDateTime;
                }

                return value;
            } else {
                TemporalAccessor value = DateTimeFormatter.ISO_DATE.parse(literal, LocalDate::from);
                return LocalDateTime.of((LocalDate) value, LocalTime.of(0, 0));
            }
        } catch (Exception e) {
            throw new RuntimeException("Parsing exception in date and time literal", e);
        }
    }

    public TemporalAccessor dateAndTime(TemporalAccessor date, TemporalAccessor time) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        if (!(date instanceof LocalDate)) {
            date = date.query(TemporalQueries.localDate());
            if (date == null) {
                throw new IllegalArgumentException("Date must be an instance of LocalDate or contain LocalDate");
            }
        }
        if (time == null) {
            throw new IllegalArgumentException("Time cannot be null");
        }
        if (!(time instanceof LocalTime || (time.query(TemporalQueries.localTime()) != null && time.query(TemporalQueries.zone()) != null))) {
            throw new IllegalArgumentException("Time must be an instance of LocalTime or (contain LocalTime and zone)");
        }

        try {
            if (date instanceof LocalDate && time instanceof LocalTime) {
                return LocalDateTime.of((LocalDate) date, (LocalTime) time);
            } else if (date instanceof LocalDate && (time.query(TemporalQueries.localTime()) != null && time.query(TemporalQueries.zone()) != null)) {
                return ZonedDateTime.of((LocalDate) date, LocalTime.from(time), ZoneId.from(time));
            }
            throw new IllegalArgumentException("Illegal date and time arguments");
        } catch (DateTimeException e) {
            throw new RuntimeException("Cannot create date and time from arguments", e);
        }
    }

    public static TemporalAccessor dateAndTime(Number year, Number month, Number day,
                                               Number hour, Number minute, Number second) {
        return dateAndTime(year, month, day, hour, minute, second, (Number) null);
    }

    public static TemporalAccessor dateAndTime(Number year, Number month, Number day,
                                               Number hour, Number minute, Number second,
                                               Number hourOffset) {
        if (year == null) {
            throw new RuntimeException("Year cannot be null");
        }
        if (month == null) {
            throw new RuntimeException("Month cannot be null");
        }
        if (day == null) {
            throw new RuntimeException("Day cannot be null");
        }
        if (hour == null) {
            throw new RuntimeException("Hour cannot be null");
        }
        if (minute == null) {
            throw new RuntimeException("Minute cannot be null");
        }
        if (second == null) {
            throw new RuntimeException("Second cannot be null");
        }

        try {
            if (hourOffset != null) {
                return OffsetDateTime.of(year.intValue(), month.intValue(), day.intValue(),
                        hour.intValue(), minute.intValue(), second.intValue(),
                        0, ZoneOffset.ofHours(hourOffset.intValue()));
            } else {
                return LocalDateTime.of(year.intValue(), month.intValue(), day.intValue(),
                        hour.intValue(), minute.intValue(), second.intValue());
            }
        } catch (DateTimeException e) {
            throw new RuntimeException("Cannot create date and time from arguments", e);
        }
    }

    public static TemporalAccessor dateAndTime(Number year, Number month, Number day,
                                               Number hour, Number minute, Number second,
                                               String timezone) {
        if (year == null) {
            throw new RuntimeException("Year cannot be null");
        }
        if (month == null) {
            throw new RuntimeException("Month cannot be null");
        }
        if (day == null) {
            throw new RuntimeException("Day cannot be null");
        }
        if (hour == null) {
            throw new RuntimeException("Hour cannot be null");
        }
        if (minute == null) {
            throw new RuntimeException("Minute cannot be null");
        }
        if (second == null) {
            throw new RuntimeException("Second cannot be null");
        }

        try {
            return ZonedDateTime.of(year.intValue(), month.intValue(), day.intValue(),
                    hour.intValue(), minute.intValue(), second.intValue(), 0, TimeZone.getTimeZone(timezone).toZoneId());
        } catch (DateTimeException e) {
            throw new RuntimeException("Cannot create date and time from arguments", e);
        }
    }
}
