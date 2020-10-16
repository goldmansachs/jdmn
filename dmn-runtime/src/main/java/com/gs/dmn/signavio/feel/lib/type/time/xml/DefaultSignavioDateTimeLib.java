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
package com.gs.dmn.signavio.feel.lib.type.time.xml;

import com.gs.dmn.feel.lib.type.time.xml.FEELXMLGregorianCalendar;
import com.gs.dmn.signavio.feel.lib.type.time.SignavioBaseDateTimeLib;
import com.gs.dmn.signavio.feel.lib.type.time.SignavioDateTimeLib;

import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZonedDateTime;
import java.util.Calendar;

import static com.gs.dmn.feel.lib.DefaultFEELLib.DATA_TYPE_FACTORY;

public class DefaultSignavioDateTimeLib extends SignavioBaseDateTimeLib implements SignavioDateTimeLib<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar> {
    @Override
    public XMLGregorianCalendar yearAdd(XMLGregorianCalendar date, BigDecimal yearsToAdd) {
        XMLGregorianCalendar result = (XMLGregorianCalendar) date.clone();
        int months = yearsToAdd.intValue();
        boolean isPositive = months > 0;
        javax.xml.datatype.Duration duration;
        duration = DATA_TYPE_FACTORY.newDurationYearMonth(
                isPositive, yearsToAdd.abs().intValue(), 0);
        result.add(duration);
        return result;
    }

    @Override
    public XMLGregorianCalendar yearAddDateTime(XMLGregorianCalendar dateTime, BigDecimal yearsToAdd) {
        return yearAdd(dateTime, yearsToAdd);
    }

    @Override
    public Long yearDiff(XMLGregorianCalendar date1, XMLGregorianCalendar date2) {
        Period period = periodBetween(date1, date2);
        return (long) period.getYears();
    }

    @Override
    public Long yearDiffDateTime(XMLGregorianCalendar dateTime1, XMLGregorianCalendar dateTime2) {
        return yearDiff(dateTime1, dateTime2);
    }

    @Override
    public XMLGregorianCalendar monthAdd(XMLGregorianCalendar date, BigDecimal monthsToAdd) {
        XMLGregorianCalendar result = (XMLGregorianCalendar) date.clone();
        int months = monthsToAdd.intValue();
        boolean isPositive = months > 0;
        javax.xml.datatype.Duration duration;
        duration = DATA_TYPE_FACTORY.newDurationYearMonth(
                isPositive, 0, monthsToAdd.abs().intValue());
        result.add(duration);
        return result;
    }

    @Override
    public XMLGregorianCalendar monthAddDateTime(XMLGregorianCalendar dateTime, BigDecimal monthsToAdd) {
        return monthAdd(dateTime, monthsToAdd);
    }

    @Override
    public Long monthDiff(XMLGregorianCalendar date1, XMLGregorianCalendar date2) {
        Period period = periodBetween(date1, date2);
        return period.toTotalMonths();
    }

    @Override
    public Long monthDiffDateTime(XMLGregorianCalendar dateTime1, XMLGregorianCalendar dateTime2) {
        return monthDiff(dateTime1, dateTime2);
    }

    @Override
    public XMLGregorianCalendar dayAdd(XMLGregorianCalendar date, BigDecimal daysToAdd) {
        XMLGregorianCalendar result = (XMLGregorianCalendar) date.clone();
        int days = daysToAdd.intValue();
        boolean isPositive = days > 0;
        javax.xml.datatype.Duration duration;
        duration = DATA_TYPE_FACTORY.newDurationDayTime(
                isPositive, daysToAdd.abs().intValue(), 0, 0, 0);
        result.add(duration);
        return result;
    }

    @Override
    public XMLGregorianCalendar dayAddDateTime(XMLGregorianCalendar dateTime, BigDecimal daysToAdd) {
        return dayAdd(dateTime, daysToAdd);
    }

    @Override
    public Long dayDiff(XMLGregorianCalendar date1, XMLGregorianCalendar date2) {
        java.time.Duration duration = durationBetween(date1, date2);
        Long diff = duration == null ? null : duration.getSeconds() / (60 * 60 * 24);
        return diff;
    }

    @Override
    public Long dayDiffDateTime(XMLGregorianCalendar dateTime1, XMLGregorianCalendar dateTime2) {
        return dayDiff(dateTime1, dateTime2);
    }

    @Override
    public Integer weekday(XMLGregorianCalendar date) {
        if (date == null) {
            return null;
        }

        int weekDay = date.toGregorianCalendar().get(Calendar.DAY_OF_WEEK);
        weekDay--;
        if (weekDay == 0) {
            weekDay = 7;
        }
        return weekDay;
    }

    @Override
    public Integer weekdayDateTime(XMLGregorianCalendar dateTime) {
        return weekday(dateTime);
    }

    @Override
    public Long hourDiff(XMLGregorianCalendar time1, XMLGregorianCalendar time2) {
        Duration duration = durationBetween(time1, time2);
        Long diff = duration == null ? null : duration.getSeconds() / (60 * 60);
        return diff;
    }

    @Override
    public Long hourDiffDateTime(XMLGregorianCalendar dateTime1, XMLGregorianCalendar dateTime2) {
        return hourDiff(dateTime1, dateTime2);
    }

    @Override
    public Long minutesDiff(XMLGregorianCalendar time1, XMLGregorianCalendar time2) {
        Duration duration = durationBetween(time1, time2);
        long diff = duration == null ? null : duration.getSeconds() / 60;
        return diff;
    }

    @Override
    public Long minutesDiffDateTime(XMLGregorianCalendar dateTime1, XMLGregorianCalendar dateTime2) {
        return minutesDiff(dateTime1, dateTime2);
    }

    @Override
    public XMLGregorianCalendar today() {
        return FEELXMLGregorianCalendar.makeXMLCalendar(LocalDate.now());
    }

    @Override
    public XMLGregorianCalendar now() {
        return FEELXMLGregorianCalendar.makeXMLCalendar(ZonedDateTime.now());
    }
}
