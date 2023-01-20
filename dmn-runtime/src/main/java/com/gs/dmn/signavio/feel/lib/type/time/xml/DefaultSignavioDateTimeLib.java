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

import com.gs.dmn.feel.lib.type.time.xml.DefaultDateTimeLib;
import com.gs.dmn.feel.lib.type.time.xml.FEELXMLGregorianCalendar;
import com.gs.dmn.feel.lib.type.time.xml.XMLDurationFactory;
import com.gs.dmn.signavio.feel.lib.type.time.SignavioBaseDateTimeLib;
import com.gs.dmn.signavio.feel.lib.type.time.SignavioDateTimeLib;

import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZonedDateTime;
import java.util.Calendar;

public class DefaultSignavioDateTimeLib extends SignavioBaseDateTimeLib implements SignavioDateTimeLib<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar> {
    private static final DefaultDateTimeLib DEFAULT_DATE_TIME_LIB = new DefaultDateTimeLib();

    //
    // Common methods
    //
    @Override
    public XMLGregorianCalendar date(String literal) {
        return DEFAULT_DATE_TIME_LIB.date(literal);
    }

    @Override
    public XMLGregorianCalendar time(String literal) {
        return DEFAULT_DATE_TIME_LIB.time(literal);
    }

    @Override
    public XMLGregorianCalendar dateAndTime(String literal) {
        return DEFAULT_DATE_TIME_LIB.dateAndTime(literal);
    }

    @Override
    public Integer year(Object date) {
        return DEFAULT_DATE_TIME_LIB.year((XMLGregorianCalendar) date);
    }

    @Override
    public Integer month(Object date) {
        return DEFAULT_DATE_TIME_LIB.month((XMLGregorianCalendar) date);
    }

    @Override
    public Integer day(Object date) {
        return DEFAULT_DATE_TIME_LIB.day((XMLGregorianCalendar) date);
    }

    @Override
    public Integer hour(Object date) {
        return DEFAULT_DATE_TIME_LIB.hour((XMLGregorianCalendar) date);
    }

    @Override
    public Integer minute(Object date) {
        return DEFAULT_DATE_TIME_LIB.minute((XMLGregorianCalendar) date);
    }

    @Override
    public XMLGregorianCalendar toDate(Object from) {
        return DEFAULT_DATE_TIME_LIB.toDate(from);
    }

    @Override
    public XMLGregorianCalendar toTime(Object from) {
        return DEFAULT_DATE_TIME_LIB.toTime(from);
    }

    @Override
    public XMLGregorianCalendar toDateTime(Object from) {
        return DEFAULT_DATE_TIME_LIB.toDateTime(from);
    }

    //
    // Specific methods
    //
    @Override
    public XMLGregorianCalendar yearAdd(XMLGregorianCalendar date, BigDecimal yearsToAdd) {
        XMLGregorianCalendar result = (XMLGregorianCalendar) date.clone();
        javax.xml.datatype.Duration duration = XMLDurationFactory.INSTANCE.yearMonthWithYears(yearsToAdd.intValue());
        result.add(duration);
        return result;
    }

    @Override
    public XMLGregorianCalendar yearAddDateTime(XMLGregorianCalendar dateTime, BigDecimal yearsToAdd) {
        return yearAdd(dateTime, yearsToAdd);
    }

    @Override
    public Long yearDiff(Object date1, Object date2) {
        Period period = periodBetween((XMLGregorianCalendar) date1, (XMLGregorianCalendar) date2);
        return (long) period.getYears();
    }

    @Override
    public XMLGregorianCalendar monthAdd(XMLGregorianCalendar date, BigDecimal monthsToAdd) {
        XMLGregorianCalendar result = (XMLGregorianCalendar) date.clone();
        javax.xml.datatype.Duration duration = XMLDurationFactory.INSTANCE.yearMonthWithMonths(monthsToAdd.intValue());
        result.add(duration);
        return result;
    }

    @Override
    public XMLGregorianCalendar monthAddDateTime(XMLGregorianCalendar dateTime, BigDecimal monthsToAdd) {
        return monthAdd(dateTime, monthsToAdd);
    }

    @Override
    public Long monthDiff(Object date1, Object date2) {
        Period period = periodBetween((XMLGregorianCalendar) date1, (XMLGregorianCalendar) date2);
        return period.toTotalMonths();
    }

    @Override
    public XMLGregorianCalendar dayAdd(XMLGregorianCalendar date, BigDecimal daysToAdd) {
        XMLGregorianCalendar result = (XMLGregorianCalendar) date.clone();
        javax.xml.datatype.Duration duration = XMLDurationFactory.INSTANCE.dayTimeWithDays(daysToAdd.intValue());
        result.add(duration);
        return result;
    }

    @Override
    public XMLGregorianCalendar dayAddDateTime(XMLGregorianCalendar dateTime, BigDecimal daysToAdd) {
        return dayAdd(dateTime, daysToAdd);
    }

    @Override
    public Long dayDiff(Object date1, Object date2) {
        java.time.Duration duration = durationBetween((XMLGregorianCalendar) date1, (XMLGregorianCalendar) date2);
        return duration == null ? null : duration.getSeconds() / (60 * 60 * 24);
    }

    @Override
    public Integer weekday(Object date) {
        if (date == null) {
            return null;
        }

        XMLGregorianCalendar xmlDate = (XMLGregorianCalendar) date;
        int weekDay = xmlDate.toGregorianCalendar().get(Calendar.DAY_OF_WEEK);
        weekDay--;
        if (weekDay == 0) {
            weekDay = 7;
        }
        return weekDay;
    }

    @Override
    public Long hourDiff(Object time1, Object time2) {
        Duration duration = durationBetween((XMLGregorianCalendar) time1, (XMLGregorianCalendar) time2);
        return duration == null ? null : duration.getSeconds() / (60 * 60);
    }

    @Override
    public Long minutesDiff(Object time1, Object time2) {
        Duration duration = durationBetween((XMLGregorianCalendar) time1, (XMLGregorianCalendar) time2);
        return duration == null ? null : duration.getSeconds() / 60;
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
