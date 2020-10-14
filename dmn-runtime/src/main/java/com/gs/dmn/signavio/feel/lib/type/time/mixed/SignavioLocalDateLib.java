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

import com.gs.dmn.feel.lib.type.time.xml.DefaultDateTimeLib;
import com.gs.dmn.signavio.feel.lib.type.time.SignavioBaseDateTimeLib;
import com.gs.dmn.signavio.feel.lib.type.time.SignavioDateLib;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZonedDateTime;

public class SignavioLocalDateLib extends SignavioBaseDateTimeLib implements SignavioDateLib<Number, LocalDate, ZonedDateTime> {
    public ZonedDateTime yearAdd(ZonedDateTime dateTime, Number yearsToAdd) {
        return dateTime.plusYears(yearsToAdd.longValue());
    }
    @Override
    public ZonedDateTime yearAdd(LocalDate localDate, Number yearsToAdd) {
        return localDate.plusYears(yearsToAdd.longValue()).atStartOfDay(DefaultDateTimeLib.UTC);
    }

    public Integer yearDiff(ZonedDateTime dateTime1, ZonedDateTime dateTime2) {
        Period period = periodBetween(dateTime1, dateTime2);
        return period.getYears();
    }
    @Override
    public Integer yearDiff(LocalDate dateTime1, LocalDate dateTime2) {
        Period period = periodBetween(dateTime1, dateTime2);
        return period.getYears();
    }

    public ZonedDateTime monthAdd(ZonedDateTime dateTime, Number monthsToAdd) {
        return dateTime.plusMonths(monthsToAdd.longValue());
    }
    public ZonedDateTime monthAdd(LocalDate date, Number monthsToAdd) {
        return date.plusMonths(monthsToAdd.longValue()).atStartOfDay(DefaultDateTimeLib.UTC);
    }

    public Long monthDiff(ZonedDateTime dateTime1, ZonedDateTime dateTime2) {
        Period period = periodBetween(dateTime1, dateTime2);
        return period.toTotalMonths();
    }
    @Override
    public Long monthDiff(LocalDate date1, LocalDate date2) {
        Period period = periodBetween(date1, date2);
        return period.toTotalMonths();
    }

    public LocalDate dayAdd(ZonedDateTime dateTime, Number daysToAdd) {
        return dateTime.plusDays(daysToAdd.intValue()).toLocalDate();
    }
    @Override
    public LocalDate dayAdd(LocalDate date, Number daysToAdd) {
        return date.plusDays(daysToAdd.intValue());
    }

    public Long dayDiff(ZonedDateTime dateTime1, ZonedDateTime dateTime2) {
        long diff = durationBetween(dateTime1, dateTime2).getSeconds() / (60 * 60 * 24);
        return diff;
    }
    @Override
    public Long dayDiff(LocalDate date1, LocalDate date2) {
        long diff = durationBetween(date1, date2).getSeconds() / (60 * 60 * 24);
        return diff;
    }

    @Override
    public Integer weekday(LocalDate date) {
        int weekDay = date.getDayOfWeek().getValue();
        return weekDay;
    }
    public Integer weekday(ZonedDateTime dateTime) {
        int weekDay = dateTime.getDayOfWeek().getValue();
        return weekDay;
    }

    @Override
    public LocalDate today() {
        return LocalDate.now();
    }
}
