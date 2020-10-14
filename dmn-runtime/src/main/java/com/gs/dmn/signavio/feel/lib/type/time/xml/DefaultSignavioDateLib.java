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
import com.gs.dmn.signavio.feel.lib.type.time.SignavioDateLib;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;

import static com.gs.dmn.feel.lib.DefaultFEELLib.DATA_TYPE_FACTORY;

public class DefaultSignavioDateLib extends SignavioBaseDateTimeLib implements SignavioDateLib<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar> {
    @Override
    public XMLGregorianCalendar yearAdd(XMLGregorianCalendar dateTime, BigDecimal yearsToAdd) {
        XMLGregorianCalendar result = (XMLGregorianCalendar) dateTime.clone();
        int months = yearsToAdd.intValue();
        boolean isPositive = months > 0;
        Duration duration;
        duration = DATA_TYPE_FACTORY.newDurationYearMonth(
                isPositive, yearsToAdd.abs().intValue(), 0);
        result.add(duration);
        return result;
    }

    @Override
    public BigDecimal yearDiff(XMLGregorianCalendar dateTime1, XMLGregorianCalendar dateTime2) {
        Period period = periodBetween(dateTime1, dateTime2);
        return BigDecimal.valueOf(period.getYears());
    }

    @Override
    public XMLGregorianCalendar monthAdd(XMLGregorianCalendar dateTime, BigDecimal monthsToAdd) {
        XMLGregorianCalendar result = (XMLGregorianCalendar) dateTime.clone();
        int months = monthsToAdd.intValue();
        boolean isPositive = months > 0;
        Duration duration;
        duration = DATA_TYPE_FACTORY.newDurationYearMonth(
                isPositive, 0, monthsToAdd.abs().intValue());
        result.add(duration);
        return result;
    }

    @Override
    public Long monthDiff(XMLGregorianCalendar dateTime1, XMLGregorianCalendar dateTime2) {
        Period period = periodBetween(dateTime1, dateTime2);
        return period.toTotalMonths();
    }

    @Override
    public XMLGregorianCalendar dayAdd(XMLGregorianCalendar dateTime, BigDecimal daysToAdd) {
        XMLGregorianCalendar result = (XMLGregorianCalendar) dateTime.clone();
        int days = daysToAdd.intValue();
        boolean isPositive = days > 0;
        Duration duration;
        duration = DATA_TYPE_FACTORY.newDurationDayTime(
                isPositive, daysToAdd.abs().intValue(), 0, 0, 0);
        result.add(duration);
        return result;
    }

    @Override
    public Long dayDiff(XMLGregorianCalendar dateTime1, XMLGregorianCalendar dateTime2) {
        java.time.Duration duration = durationBetween(dateTime1, dateTime2);
        Long diff = duration == null ? null : duration.getSeconds() / (60 * 60 * 24);
        return diff;
    }

    @Override
    public Integer weekday(XMLGregorianCalendar dateTime) {
        if (dateTime == null) {
            return null;
        }

        int weekDay = dateTime.toGregorianCalendar().get(Calendar.DAY_OF_WEEK);
        weekDay--;
        if (weekDay == 0) {
            weekDay = 7;
        }
        return weekDay;
    }

    @Override
    public XMLGregorianCalendar today() {
        return FEELXMLGregorianCalendar.makeXMLCalendar(LocalDate.now());
    }
}
