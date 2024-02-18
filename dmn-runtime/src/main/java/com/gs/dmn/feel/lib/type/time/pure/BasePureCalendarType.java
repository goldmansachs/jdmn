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
package com.gs.dmn.feel.lib.type.time.pure;

import com.gs.dmn.feel.lib.type.time.JavaCalendarType;

import java.time.*;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalQueries;
import java.util.Optional;

public abstract class BasePureCalendarType extends JavaCalendarType {
    @Override
    public boolean isDate(Object object) {
        return object instanceof LocalDate;
    }

    @Override
    public boolean isTime(Object object) {
        return object instanceof LocalTime
                || object instanceof OffsetTime
                || isTimeWithZone(object);
    }

    @Override
    public boolean isDateTime(Object object) {
        return object instanceof LocalDateTime || object instanceof OffsetDateTime || object instanceof ZonedDateTime;
    }

    @Override
    public boolean isYearsAndMonthsDuration(Object value) {
        return value instanceof Period;
    }

    @Override
    public boolean isDaysAndTimeDuration(Object value) {
        return value instanceof Duration;
    }

    private boolean isTimeWithZone(Object obj) {
        if (obj instanceof TemporalAccessor value) {
            return value.isSupported(ChronoField.HOUR_OF_DAY)
                    && value.isSupported(ChronoField.MINUTE_OF_HOUR)
                    && value.isSupported(ChronoField.SECOND_OF_MINUTE)
                    && hasTimezone(value);

        } else {
            return false;
        }
    }

    protected boolean hasTimezone(TemporalAccessor first) {
        return first.query(TemporalQueries.zone()) != null;
    }

    protected boolean sameDateProperties(TemporalAccessor first, TemporalAccessor second) {
        // Check if year, month and day are the same
        Optional<Integer> year1 = Optional.ofNullable(first.isSupported(ChronoField.YEAR) ? first.get(ChronoField.YEAR) : null);
        Optional<Integer> year2 = Optional.ofNullable(second.isSupported(ChronoField.YEAR) ? second.get(ChronoField.YEAR) : null);
        boolean result = year1.equals(year2);
        Optional<Integer> month1 = Optional.ofNullable(first.isSupported(ChronoField.MONTH_OF_YEAR) ? first.get(ChronoField.MONTH_OF_YEAR) : null);
        Optional<Integer> month2 = Optional.ofNullable(second.isSupported(ChronoField.MONTH_OF_YEAR) ? second.get(ChronoField.MONTH_OF_YEAR) : null);
        result &= month1.equals(month2);
        Optional<Integer> day1 = Optional.ofNullable(first.isSupported(ChronoField.DAY_OF_MONTH) ? first.get(ChronoField.DAY_OF_MONTH) : null);
        Optional<Integer> day2 = Optional.ofNullable(second.isSupported(ChronoField.DAY_OF_MONTH) ? second.get(ChronoField.DAY_OF_MONTH) : null);
        result &= day1.equals(day2);
        return result;
    }

    protected boolean sameTimeProperties(TemporalAccessor first, TemporalAccessor second) {
        // Check if hour, minute, second and timezone are the same
        Optional<Integer> hour1 = Optional.ofNullable(first.isSupported(ChronoField.HOUR_OF_DAY) ? first.get(ChronoField.HOUR_OF_DAY) : null);
        Optional<Integer> hour2 = Optional.ofNullable(second.isSupported(ChronoField.HOUR_OF_DAY) ? second.get(ChronoField.HOUR_OF_DAY) : null);
        boolean result = hour1.equals(hour2);
        Optional<Integer> minute1 = Optional.ofNullable(first.isSupported(ChronoField.MINUTE_OF_HOUR) ? first.get(ChronoField.MINUTE_OF_HOUR) : null);
        Optional<Integer> minute2 = Optional.ofNullable(second.isSupported(ChronoField.MINUTE_OF_HOUR) ? second.get(ChronoField.MINUTE_OF_HOUR) : null);
        result &= minute1.equals(minute2);
        Optional<Integer> second1 = Optional.ofNullable(first.isSupported(ChronoField.SECOND_OF_MINUTE) ? first.get(ChronoField.SECOND_OF_MINUTE) : null);
        Optional<Integer> second2 = Optional.ofNullable(second.isSupported(ChronoField.SECOND_OF_MINUTE) ? second.get(ChronoField.SECOND_OF_MINUTE) : null);
        result &= second1.equals(second2);
        Optional<ZoneId> tz1 = Optional.ofNullable(first.query(TemporalQueries.zone()));
        Optional<ZoneId> tz2 = Optional.ofNullable(second.query(TemporalQueries.zone()));
        result &= tz1.equals(tz2);
        return result;
    }

    protected boolean sameDateTimeProperties(TemporalAccessor first, TemporalAccessor second) {
        return sameDateProperties(first, second) && sameTimeProperties(first, second);
    }
}
