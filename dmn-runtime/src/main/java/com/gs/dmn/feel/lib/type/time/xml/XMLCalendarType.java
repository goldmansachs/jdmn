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

import com.gs.dmn.feel.lib.type.BaseType;
import com.gs.dmn.runtime.DMNRuntimeException;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import java.util.Calendar;
import java.util.GregorianCalendar;

public abstract class XMLCalendarType extends BaseType {
    protected static final ThreadLocal<GregorianCalendar> GREGORIAN = ThreadLocal.withInitial(() -> new GregorianCalendar(
            1970,
            Calendar.JANUARY,
            1,
            0,
            0,
            0));

    protected final DatatypeFactory datatypeFactory;

    public XMLCalendarType(DatatypeFactory datatypeFactory) {
        this.datatypeFactory = datatypeFactory;
    }

    protected static QName getXMLSchemaType(Duration duration) {
        if (duration == null) {
            return DatatypeConstants.DURATION;
        }

        boolean yearSet = duration.isSet(DatatypeConstants.YEARS);
        boolean monthSet = duration.isSet(DatatypeConstants.MONTHS);
        boolean daySet = duration.isSet(DatatypeConstants.DAYS);
        boolean hourSet = duration.isSet(DatatypeConstants.HOURS);
        boolean minuteSet = duration.isSet(DatatypeConstants.MINUTES);
        boolean secondSet = duration.isSet(DatatypeConstants.SECONDS);

        if ((yearSet || monthSet) && !(daySet || hourSet || minuteSet || secondSet)) {
            return DatatypeConstants.DURATION_YEARMONTH;
        } else if (!(yearSet || monthSet) && (daySet || hourSet || minuteSet || secondSet)) {
            return DatatypeConstants.DURATION_DAYTIME;
        } else {
            return DatatypeConstants.DURATION;
        }
    }

    public boolean isDate(Object value) {
        return value instanceof XMLGregorianCalendar
                && ((XMLGregorianCalendar) value).getXMLSchemaType() == DatatypeConstants.DATE;
    }

    public boolean isTime(Object value) {
        return value instanceof XMLGregorianCalendar
                && ((XMLGregorianCalendar) value).getXMLSchemaType() == DatatypeConstants.TIME;
    }

    public boolean isDateTime(Object value) {
        return value instanceof XMLGregorianCalendar
                && ((XMLGregorianCalendar) value).getXMLSchemaType() == DatatypeConstants.DATETIME;
    }

    public boolean isDuration(Object value) {
        return value instanceof Duration;
    }

    public boolean isYearsAndMonthsDuration(Object value) {
        return value instanceof Duration
                && getXMLSchemaType((Duration) value) == DatatypeConstants.DURATION_YEARMONTH;
    }

    public boolean isDaysAndTimeDuration(Object value) {
        return value instanceof Duration
                && getXMLSchemaType((Duration) value) == DatatypeConstants.DURATION_DAYTIME;
    }

    public Long dateValue(XMLGregorianCalendar date) {
        return calendarValue(dateToDateTime(date));
    }

    public Long timeValue(XMLGregorianCalendar time) {
        return calendarValue(time);
    }

    public Long dateTimeValue(XMLGregorianCalendar dateTime) {
        return calendarValue(dateTime);
    }

    protected Long calendarValue(XMLGregorianCalendar calendar) {
        return calendar == null ? null : Math.floorDiv(calendar.toGregorianCalendar().getTimeInMillis(), 1000L);
    }

    public Long durationValue(Duration duration) {
        if (duration == null) {
            return null;
        }

        if (isYearsAndMonthsDuration(duration)) {
            return monthsValue(duration);
        } else if (isDaysAndTimeDuration(duration)) {
            return secondsValue(duration);
        } else {
            throw new DMNRuntimeException(String.format("value() not supported yet for '%s'", duration));
        }
    }

    protected Long monthsValue(Duration duration) {
        if (duration == null) {
            return null;
        }

        boolean isNegative = duration.getSign() < 0;
        long months = 12L * duration.getYears() + duration.getMonths();
        return isNegative ? - months : months;
    }

    protected Long secondsValue(Duration duration) {
        if (duration == null) {
            return null;
        }

        boolean isNegative = duration.getSign() < 0;
        long hours = 24L * duration.getDays() + duration.getHours();
        long minutes = 60L * hours + duration.getMinutes();
        long seconds = 60L * minutes + duration.getSeconds();
        return isNegative ? - seconds : seconds;
    }

    protected long getDurationInSeconds(XMLGregorianCalendar first, XMLGregorianCalendar second) {
        return calendarValue(first) - calendarValue(second);
    }

    protected XMLGregorianCalendar dateToDateTime(XMLGregorianCalendar date) {
        if (date == null) {
            return null;
        }

        FEELXMLGregorianCalendar clone = (FEELXMLGregorianCalendar) date.clone();
        clone.setHour(0);
        clone.setMinute(0);
        clone.setSecond(0);
        clone.setZoneID("Z");
        return clone;
    }

    protected Duration makeDuration(long durationInSeconds) {
        return this.datatypeFactory.newDuration(durationInSeconds * 1000);
    }

    protected Duration makeYearsMonthsDuration(Number months) {
        long lMonths = months.longValue();
        if (lMonths < 0) {
            String literal = String.format("P%dM", -months.intValue());
            return this.datatypeFactory.newDurationYearMonth(literal).negate();
        } else {
            String literal = String.format("P%dM", months.intValue());
            return this.datatypeFactory.newDurationYearMonth(literal);
        }
    }

    protected Duration makeDaysTimeDuration(Number seconds) {
        long millis = seconds.longValue() * 1000L;
        if (millis < 0) {
            return this.datatypeFactory.newDurationDayTime(-millis).negate();
        } else {
            return this.datatypeFactory.newDurationDayTime(millis);
        }
    }

    protected Duration makeDuration(Number months, Number seconds) {
        long lMonths = months.longValue();
        long lSeconds = seconds.longValue();
        boolean isNegative = lMonths < 0;
        if (isNegative) {
            String literal = String.format("P%dMT%dS", -lMonths, -lSeconds);
            return this.datatypeFactory.newDurationYearMonth(literal).negate();
        } else {
            String literal = String.format("P%dMT%dS", lMonths, lSeconds);
            return this.datatypeFactory.newDuration(literal);
        }
    }
}
