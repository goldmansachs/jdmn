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
package com.gs.dmn.signavio.feel.lib.type.time;

import com.gs.dmn.feel.lib.type.BaseType;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.*;

public class SignavioBaseDateTimeLib {
    protected Period periodBetween(XMLGregorianCalendar dateTime1, XMLGregorianCalendar dateTime2) {
        LocalDate localDate1 = LocalDate.of(dateTime1.getYear(), dateTime1.getMonth(), dateTime1.getDay());
        LocalDate localDate2 = LocalDate.of(dateTime2.getYear(), dateTime2.getMonth(), dateTime2.getDay());
        return Period.between(localDate1, localDate2);
    }

    protected java.time.Duration durationBetween(XMLGregorianCalendar dateTime1, XMLGregorianCalendar dateTime2) {
        if (isDate(dateTime1) && isDate(dateTime2)) {
            LocalDateTime localDateTime1 = LocalDateTime.of(
                    dateTime1.getYear(), dateTime1.getMonth(), dateTime1.getDay(),
                    0, 0, 0);
            LocalDateTime localDateTime2 = LocalDateTime.of(
                    dateTime2.getYear(), dateTime2.getMonth(), dateTime2.getDay(),
                    0, 0, 0);
            return java.time.Duration.between(localDateTime1, localDateTime2);

        } else if (isTime(dateTime1) && isTime(dateTime2)) {
            LocalDateTime localDateTime1 = LocalDateTime.of(1972, 1, 1, dateTime1.getHour(), dateTime1.getMinute(), dateTime1.getSecond());
            LocalDateTime localDateTime2 = LocalDateTime.of(1972, 1, 1, dateTime2.getHour(), dateTime2.getMinute(), dateTime2.getSecond());
            return java.time.Duration.between(localDateTime1, localDateTime2);
        } else if (isDateTime(dateTime1) && isDateTime(dateTime2)) {
            LocalDateTime localDateTime1 = LocalDateTime.of(
                    dateTime1.getYear(), dateTime1.getMonth(), dateTime1.getDay(),
                    dateTime1.getHour(), dateTime1.getMinute(), dateTime1.getSecond());
            LocalDateTime localDateTime2 = LocalDateTime.of(
                    dateTime2.getYear(), dateTime2.getMonth(), dateTime2.getDay(),
                    dateTime2.getHour(), dateTime2.getMinute(), dateTime2.getSecond());
            return java.time.Duration.between(localDateTime1, localDateTime2);
        } else {
            return null;
        }
    }

    protected Period periodBetween(LocalDate date1, LocalDate date2) {
        return Period.between(date1, date2);
    }

    protected Period periodBetween(ZonedDateTime dateTime1, ZonedDateTime dateTime2) {
        LocalDate localDate1 = LocalDate.of(dateTime1.getYear(), dateTime1.getMonth(), dateTime1.getDayOfMonth());
        LocalDate localDate2 = LocalDate.of(dateTime2.getYear(), dateTime2.getMonth(), dateTime2.getDayOfMonth());
        return Period.between(localDate1, localDate2);
    }

    protected java.time.Duration durationBetween(LocalDate date1, LocalDate date2) {
        return java.time.Duration.between(date1.atStartOfDay(BaseType.UTC), date2.atStartOfDay(BaseType.UTC));
    }

    protected java.time.Duration durationBetween(OffsetTime time1, OffsetTime time2) {
        return java.time.Duration.between(time1, time2);
    }

    protected java.time.Duration durationBetween(ZonedDateTime dateTime1, ZonedDateTime dateTime2) {
        return java.time.Duration.between(dateTime1, dateTime2);
    }

    protected boolean isDate(XMLGregorianCalendar dateTime) {
        return dateTime.getYear() >= 0 && dateTime.getHour() < 0;
    }

    protected boolean isTime(XMLGregorianCalendar dateTime1) {
        return dateTime1.getYear() < 0 && dateTime1.getHour() >= 0;
    }

    protected boolean isDateTime(XMLGregorianCalendar dateTime1) {
        return dateTime1.getYear() >= 0 && dateTime1.getHour() >= 0;
    }
}
