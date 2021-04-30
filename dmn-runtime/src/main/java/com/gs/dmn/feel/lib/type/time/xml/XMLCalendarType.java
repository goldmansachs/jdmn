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

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

public abstract class XMLCalendarType extends BaseType {
    protected static final ThreadLocal<GregorianCalendar> GREGORIAN = ThreadLocal.withInitial(() -> new GregorianCalendar(
            1970,
            Calendar.JANUARY,
            1,
            0,
            0,
            0));

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

    public static boolean isYearMonthDuration(Duration duration) {
        return getXMLSchemaType(duration) == DatatypeConstants.DURATION_YEARMONTH;
    }

    public static boolean isDayTimeDuration(Duration duration) {
        return getXMLSchemaType(duration) == DatatypeConstants.DURATION_DAYTIME;
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
                && isYearMonthDuration((Duration) value);
    }

    public boolean isDaysAndTimeDuration(Object value) {
        return value instanceof Duration
                && isDayTimeDuration((Duration) value);
    }

    public Long dateValue(XMLGregorianCalendar date) {
        if (date == null) {
            return null;
        }

        XMLGregorianCalendar dateTime = dateToDateTime(date);
        return dateTimeValue(dateTime);
    }

    public Long timeValue(XMLGregorianCalendar time) {
        if (time == null) {
            return null;
        }

        long value = time.getHour() * 3600L + time.getMinute() * 60L + time.getSecond();
        if (time.getTimezone() != DatatypeConstants.FIELD_UNDEFINED) {
            value -= time.getTimezone();
        }
        return value;
    }

    public Long dateTimeValue(XMLGregorianCalendar dateTime) {
        if (dateTime == null) {
            return null;
        }

        int nanoSeconds = dateTime.getMillisecond() * 1000_000;
        LocalDateTime localDateTime = LocalDateTime.of(
                dateTime.getYear(), dateTime.getMonth(), dateTime.getDay(),
                dateTime.getHour(), dateTime.getMinute(), dateTime.getSecond(), nanoSeconds);
        int timezone = dateTime.getTimezone();
        if (timezone == DatatypeConstants.FIELD_UNDEFINED) {
            timezone = 0;
        }
        ZoneOffset offset = ZoneOffset.ofTotalSeconds(timezone);
        OffsetDateTime offsetDateTime = OffsetDateTime.of(localDateTime, offset);
        return offsetDateTime.toEpochSecond();
    }

    public Long value(XMLGregorianCalendar calendar) {
        if (isDate(calendar)) {
            return dateValue(calendar);
        } else if (isTime(calendar)) {
            return timeValue(calendar);
        } else if (isDateTime(calendar)) {
            return dateTimeValue(calendar);
        } else {
            return null;
        }
    }

    protected Long toEpochSeconds(XMLGregorianCalendar calendar) {
        Objects.requireNonNull(calendar, "calendar");
        return Math.floorDiv(calendar.toGregorianCalendar().getTimeInMillis(), 1000L);
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
            return duration.getTimeInMillis(GREGORIAN.get()) / 1000L;
        }
    }

    protected Long monthsValue(Duration duration) {
        if (duration == null) {
            return null;
        }

        boolean isNegative = duration.getSign() < 0;
        int years = duration.isSet(DatatypeConstants.YEARS) ? duration.getYears() : 0;
        int months = duration.isSet(DatatypeConstants.MONTHS) ? duration.getMonths() : 0;
        long totalMonths = 12L * years + months;
        return isNegative ? - totalMonths : totalMonths;
    }

    protected Long secondsValue(Duration duration) {
        if (duration == null) {
            return null;
        }

        // Initial values
        boolean isNegative = duration.getSign() < 0;
        int days = duration.isSet(DatatypeConstants.DAYS) ? duration.getDays() : 0;
        int hours = duration.isSet(DatatypeConstants.HOURS) ? duration.getHours() : 0;
        int minutes = duration.isSet(DatatypeConstants.MINUTES) ? duration.getMinutes() : 0;
        int seconds = duration.isSet(DatatypeConstants.SECONDS) ? duration.getSeconds() : 0;

        long totalHours = 24L * days + hours;
        long totalMinutes = 60L * totalHours + minutes;
        long totalSeconds = 60L * totalMinutes + seconds;
        return isNegative ? - totalSeconds : totalSeconds;
    }

    protected long getDurationInSeconds(XMLGregorianCalendar first, XMLGregorianCalendar second) {
        return toEpochSeconds(first) - toEpochSeconds(second);
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
}
