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
package com.gs.dmn.feel.lib.type.time.xml;

import com.gs.dmn.feel.lib.type.bool.BooleanType;
import com.gs.dmn.feel.lib.type.bool.DefaultBooleanType;
import com.gs.dmn.feel.lib.type.time.DateType;
import com.gs.dmn.runtime.DMNRuntimeException;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneOffset;

import static com.gs.dmn.feel.lib.type.time.xml.DefaultTimeType.hasTimezone;

public class DefaultDateType extends XMLCalendarType implements DateType<XMLGregorianCalendar, Duration> {
    private final DefaultXMLCalendarComparator comparator;
    private final BooleanType booleanType;

    public DefaultDateType() {
        this(new DefaultXMLCalendarComparator());
    }

    public DefaultDateType(DefaultXMLCalendarComparator comparator) {
        this.comparator = comparator;
        this.booleanType = new DefaultBooleanType();
    }

    //
    // Date operators
    //
    @Override
    public Boolean dateIs(XMLGregorianCalendar first, XMLGregorianCalendar second) {
        if (first == null || second == null) {
            return first == second;
        }

        return ((FEELXMLGregorianCalendar) first).same(second);
    }

    @Override
    public Boolean dateEqual(XMLGregorianCalendar first, XMLGregorianCalendar second) {
        return this.comparator.equalTo(first, second);
    }

    @Override
    public Boolean dateNotEqual(XMLGregorianCalendar first, XMLGregorianCalendar second) {
        return this.booleanType.booleanNot(dateEqual(first, second));
    }

    @Override
    public Boolean dateLessThan(XMLGregorianCalendar first, XMLGregorianCalendar second) {
        return this.comparator.lessThan(first, second);
    }

    @Override
    public Boolean dateGreaterThan(XMLGregorianCalendar first, XMLGregorianCalendar second) {
        return this.comparator.greaterThan(first, second);
    }

    @Override
    public Boolean dateLessEqualThan(XMLGregorianCalendar first, XMLGregorianCalendar second) {
        return this.comparator.lessEqualThan(first, second);
    }

    @Override
    public Boolean dateGreaterEqualThan(XMLGregorianCalendar first, XMLGregorianCalendar second) {
        return this.comparator.greaterEqualThan(first, second);
    }

    @Override
    public Duration dateSubtract(XMLGregorianCalendar first, Object secondObj) {
        XMLGregorianCalendar second = (XMLGregorianCalendar) secondObj;
        if (first == null || second == null) {
            return null;
        }
        if (isDate(first)) {
            first = dateToDateTime(first);
        }
        if (isDate(second)) {
            second = dateToDateTime(second);
        }
        // Subtraction is undefined for the case where only one of the values has a timezone
        if (hasTimezone(first) && !hasTimezone(second) || !hasTimezone(first) && hasTimezone(second)) {
            return null;
        }

        long durationInSeconds = getDurationInSeconds(first, second);
        return XMLDurationFactory.INSTANCE.dayTimeFromValue(durationInSeconds);
    }

    @Override
    public XMLGregorianCalendar dateAddDuration(XMLGregorianCalendar date, Duration duration) {
        if (date == null || duration == null) {
            return null;
        }

        if (isYearsAndMonthsDuration(duration)) {
            LocalDate localDate = LocalDate.of(date.getYear(), date.getMonth(), date.getDay());
            Long totalMonths = monthsValue(duration);
            Period period = Period.ofMonths(totalMonths.intValue());
            LocalDate newLocalDate = localDate.plus(period);
            // Convert to XML type
            return FEELXMLGregorianCalendar.makeDate(BigInteger.valueOf(newLocalDate.getYear()), newLocalDate.getMonthValue(), newLocalDate.getDayOfMonth());
        } else if (isDaysAndTimeDuration(duration)) {
            // Calculate with Java 8 types
            Long value1 = value(date);
            Long value2 = secondsValue(duration);
            LocalDate localDate = LocalDateTime.ofEpochSecond(value1 + value2, 0, ZoneOffset.UTC).toLocalDate();
            // Convert to XML type
            return FEELXMLGregorianCalendar.makeDate(BigInteger.valueOf(localDate.getYear()), localDate.getMonthValue(), localDate.getDayOfMonth());
        } else {
            throw new DMNRuntimeException(String.format("Cannot add '%s' with '%s'", date, duration));
        }
    }

    @Override
    public XMLGregorianCalendar dateSubtractDuration(XMLGregorianCalendar date, Duration duration) {
        if (date == null || duration == null) {
            return null;
        }

        return dateAddDuration(date, duration.negate());
    }
}
