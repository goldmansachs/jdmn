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
package com.gs.dmn.signavio.feel.lib.type.time.mixed;

import com.gs.dmn.signavio.feel.lib.type.time.SignavioBaseDateTimeLib;
import com.gs.dmn.signavio.feel.lib.type.time.SignavioDateTimeLib;

import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.Period;
import java.time.ZonedDateTime;

public class MixedSignavioDateTimeLib extends SignavioBaseDateTimeLib implements SignavioDateTimeLib<Number, LocalDate, OffsetTime, ZonedDateTime> {
    @Override
    public LocalDate yearAdd(LocalDate date, Number yearsToAdd) {
        return date.plusYears(yearsToAdd.longValue());
    }
    @Override
    public ZonedDateTime yearAddDateTime(ZonedDateTime dateTime, Number yearsToAdd) {
        return dateTime.plusYears(yearsToAdd.longValue());
    }

    @Override
    public Long yearDiff(LocalDate date1, LocalDate date2) {
        Period period = periodBetween(date1, date2);
        return (long) period.getYears();
    }
    @Override
    public Long yearDiffDateTime(ZonedDateTime dateTime1, ZonedDateTime dateTime2) {
        Period period = periodBetween(dateTime1, dateTime2);
        return (long) period.getYears();
    }

    @Override
    public LocalDate monthAdd(LocalDate date, Number monthsToAdd) {
        return date.plusMonths(monthsToAdd.longValue());
    }
    @Override
    public ZonedDateTime monthAddDateTime(ZonedDateTime dateTime, Number monthsToAdd) {
        return dateTime.plusMonths(monthsToAdd.longValue());
    }

    @Override
    public Long monthDiff(LocalDate date1, LocalDate date2) {
        Period period = periodBetween(date1, date2);
        return period.toTotalMonths();
    }
    @Override
    public Long monthDiffDateTime(ZonedDateTime dateTime1, ZonedDateTime dateTime2) {
        Period period = periodBetween(dateTime1, dateTime2);
        return period.toTotalMonths();
    }

    @Override
    public LocalDate dayAdd(LocalDate date, Number daysToAdd) {
        return date.plusDays(daysToAdd.intValue());
    }
    @Override
    public LocalDate dayAddDateTime(ZonedDateTime dateTime, Number daysToAdd) {
        return dateTime.plusDays(daysToAdd.intValue()).toLocalDate();
    }

    @Override
    public Long dayDiff(LocalDate date1, LocalDate date2) {
        long diff = durationBetween(date1, date2).getSeconds() / (60 * 60 * 24);
        return diff;
    }
    @Override
    public Long dayDiffDateTime(ZonedDateTime dateTime1, ZonedDateTime dateTime2) {
        long diff = durationBetween(dateTime1, dateTime2).getSeconds() / (60 * 60 * 24);
        return diff;
    }

    @Override
    public Long hourDiff(OffsetTime time1, OffsetTime time2) {
        long diff = durationBetween(time1, time2).getSeconds() / (60 * 60);
        return diff;
    }
    @Override
    public Long hourDiffDateTime(ZonedDateTime dateTime1, ZonedDateTime dateTime2) {
        long diff = durationBetween(dateTime1, dateTime2).getSeconds() / (60 * 60);
        return diff;
    }

    @Override
    public Long minutesDiff(OffsetTime time1, OffsetTime time2) {
        long diff = durationBetween(time1, time2).getSeconds() / 60;
        return diff;
    }
    @Override
    public Long minutesDiffDateTime(ZonedDateTime dateTime1, ZonedDateTime dateTime2) {
        long diff = durationBetween(dateTime1, dateTime2).getSeconds() / 60;
        return diff;
    }

    @Override
    public Integer weekday(LocalDate date) {
        int weekDay = date.getDayOfWeek().getValue();
        return weekDay;
    }
    @Override
    public Integer weekdayDateTime(ZonedDateTime dateTime) {
        int weekDay = dateTime.getDayOfWeek().getValue();
        return weekDay;
    }

    @Override
    public LocalDate today() {
        return LocalDate.now();
    }

    @Override
    public ZonedDateTime now() {
        return ZonedDateTime.now();
    }
}
