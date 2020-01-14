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
package com.gs.dmn.signavio.feel.lib.type.time.uniform;

import com.gs.dmn.signavio.feel.lib.type.time.SignavioBaseDateTimeLib;

import java.math.BigDecimal;
import java.time.Period;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class SignavioZonedDateLib extends SignavioBaseDateTimeLib {
    public ZonedDateTime yearAdd(ZonedDateTime dateTime, BigDecimal yearsToAdd) {
        return dateTime.plusYears(yearsToAdd.longValue());
    }

    public Integer yearDiff(ZonedDateTime dateTime1, ZonedDateTime dateTime2) {
        Period period = periodBetween(dateTime1, dateTime2);
        return period.getYears();
    }

    public ZonedDateTime monthAdd(ZonedDateTime dateTime, BigDecimal monthsToAdd) {
        return dateTime.plusMonths(monthsToAdd.longValue());
    }

    public Long monthDiff(ZonedDateTime dateTime1, ZonedDateTime dateTime2) {
        Period period = periodBetween(dateTime1, dateTime2);
        return period.toTotalMonths();
    }

    public ZonedDateTime dayAdd(ZonedDateTime dateTime, BigDecimal daysToAdd) {
        return dateTime.plusDays(daysToAdd.intValue());
    }

    public Long dayDiff(ZonedDateTime dateTime1, ZonedDateTime dateTime2) {
        long diff = durationBetween(dateTime1, dateTime2).getSeconds() / (60 * 60 * 24);
        return diff;
    }

    public Integer weekday(ZonedDateTime dateTime) {
        int weekDay = dateTime.getDayOfWeek().getValue();
        return weekDay;
    }

    public ZonedDateTime today() {
        return ZonedDateTime.now().truncatedTo(ChronoUnit.DAYS);
    }
}
