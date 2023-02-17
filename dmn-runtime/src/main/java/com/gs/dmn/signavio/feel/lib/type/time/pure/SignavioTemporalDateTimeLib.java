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
package com.gs.dmn.signavio.feel.lib.type.time.pure;

import com.gs.dmn.feel.lib.type.time.pure.TemporalDateTimeLib;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.signavio.feel.lib.SignavioUtil;
import com.gs.dmn.signavio.feel.lib.type.time.SignavioBaseDateTimeLib;
import com.gs.dmn.signavio.feel.lib.type.time.SignavioDateTimeLib;

import java.time.Duration;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;

public class SignavioTemporalDateTimeLib extends SignavioBaseDateTimeLib implements SignavioDateTimeLib<Number, LocalDate, TemporalAccessor, TemporalAccessor> {
    private static final TemporalDateTimeLib TEMPORAL_DATE_TIME_LIB = new TemporalDateTimeLib();

    @Override
    public LocalDate date(String literal) {
        return TEMPORAL_DATE_TIME_LIB.date(literal);
    }

    @Override
    public TemporalAccessor time(String literal) {
        return TEMPORAL_DATE_TIME_LIB.time(literal);
    }

    @Override
    public TemporalAccessor dateAndTime(String from) {
        return TEMPORAL_DATE_TIME_LIB.dateAndTime(from);
    }

    @Override
    public Integer year(Object date) {
        return TEMPORAL_DATE_TIME_LIB.year(date);
    }

    @Override
    public Integer month(Object date) {
        return TEMPORAL_DATE_TIME_LIB.month(date);
    }

    @Override
    public Integer day(Object date) {
        return TEMPORAL_DATE_TIME_LIB.day(date);
    }

    @Override
    public Integer hour(Object time) {
        return TEMPORAL_DATE_TIME_LIB.hour(time);
    }

    @Override
    public Integer minute(Object time) {
        return TEMPORAL_DATE_TIME_LIB.minute(time);
    }

    @Override
    public LocalDate toDate(Object from) {
        return TEMPORAL_DATE_TIME_LIB.toDate(from);
    }

    @Override
    public TemporalAccessor toTime(Object from) {
        return TEMPORAL_DATE_TIME_LIB.toTime(from);
    }

    @Override
    public TemporalAccessor toDateTime(Object from) {
        return TEMPORAL_DATE_TIME_LIB.toDateTime(from);
    }

    //
    // Common methods
    //

    //
    // Specific methods
    //
    @Override
    public LocalDate yearAdd(LocalDate date, Number yearsToAdd) {
        if (date == null || yearsToAdd == null) {
            return null;
        }

        return date.plusYears(yearsToAdd.longValue());
    }
    @Override
    public TemporalAccessor yearAddDateTime(TemporalAccessor dateTime, Number yearsToAdd) {
        if (dateTime == null || yearsToAdd == null) {
            return null;
        }

        if (dateTime instanceof Temporal) {
            return ((Temporal) dateTime).plus(yearsToAdd.longValue(), ChronoUnit.YEARS);
        } else {
            throw new DMNRuntimeException(String.format("Cannot add months '%s' to '%s'", yearsToAdd, dateTime));
        }
    }

    @Override
    public Long yearDiff(Object date1, Object date2) {
        if (!SignavioUtil.areNullSafe(date1, date2)) {
            return null;
        }

        return ChronoUnit.YEARS.between(toDate(date1), toDate(date2));
    }

    @Override
    public LocalDate monthAdd(LocalDate date, Number monthsToAdd) {
        if (!SignavioUtil.areNullSafe(date, monthsToAdd)) {
            return null;
        }

        return date.plusMonths(monthsToAdd.longValue());
    }
    @Override
    public TemporalAccessor monthAddDateTime(TemporalAccessor dateTime, Number monthsToAdd) {
        if (!SignavioUtil.areNullSafe(dateTime, monthsToAdd)) {
            return null;
        }

        if (dateTime instanceof Temporal) {
            return ((Temporal) dateTime).plus(monthsToAdd.longValue(), ChronoUnit.MONTHS);
        } else {
            throw new DMNRuntimeException(String.format("Cannot add months '%s' to '%s'", monthsToAdd, dateTime));
        }
    }

    @Override
    public Long monthDiff(Object date1, Object date2) {
        if (!SignavioUtil.areNullSafe(date1, date2)) {
            return null;
        }

        return ChronoUnit.MONTHS.between(toDate(date1), toDate(date2));
    }

    @Override
    public LocalDate dayAdd(LocalDate date, Number daysToAdd) {
        if (!SignavioUtil.areNullSafe(date, daysToAdd)) {
            return null;
        }

        return date.plusDays(daysToAdd.longValue());
    }
    @Override
    public TemporalAccessor dayAddDateTime(TemporalAccessor dateTime, Number daysToAdd) {
        if (!SignavioUtil.areNullSafe(dateTime, daysToAdd)) {
            return null;
        }

        if (dateTime instanceof Temporal) {
            return ((Temporal) dateTime).plus(daysToAdd.longValue(), ChronoUnit.DAYS);
        } else {
            throw new DMNRuntimeException(String.format("Cannot add days '%s' to '%s'", daysToAdd, dateTime));
        }
    }

    @Override
    public Long dayDiff(Object date1, Object date2) {
        if (!SignavioUtil.areNullSafe(date1, date2)) {
            return null;
        }

        return ChronoUnit.DAYS.between(toDate(date1), toDate(date2));
    }

    @Override
    public Long hourDiff(Object time1, Object time2) {
        if (!SignavioUtil.areNullSafe(time1, time2)) {
            return null;
        }

        if (time1 instanceof ZonedDateTime && time2 instanceof OffsetTime) {
            // between() is not symmetric, converts second to first
            return - Duration.between((Temporal) time2, (Temporal) time1).toHours();
        } else {
            return Duration.between((Temporal) time1, (Temporal) time2).toHours();
        }
    }

    @Override
    public Long minutesDiff(Object time1, Object time2) {
        if (!SignavioUtil.areNullSafe(time1, time2)) {
            return null;
        }

        if (time1 instanceof ZonedDateTime && time2 instanceof OffsetTime) {
            // between() is not symmetric, converts second to first
            return - Duration.between((Temporal) time2, (Temporal) time1).toMinutes();
        } else {
            return Duration.between((Temporal) time1, (Temporal) time2).toMinutes();
        }
    }

    @Override
    public Integer weekday(Object date) {
        if (!SignavioUtil.areNullSafe(date)) {
            return null;
        }

        if (date instanceof LocalDate) {
            return ((LocalDate) date).getDayOfWeek().getValue();
        } else if (date instanceof ZonedDateTime) {
            return ((ZonedDateTime) date).getDayOfWeek().getValue();
        }
        throw new RuntimeException(String.format("Cannot extract 'weekday' from '%s'", date));
    }

    @Override
    public LocalDate today() {
        return LocalDate.now();
    }

    @Override
    public TemporalAccessor now() {
        return ZonedDateTime.now();
    }
}
