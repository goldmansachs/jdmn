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

import com.gs.dmn.feel.lib.type.time.BaseDateTimeLib;
import com.gs.dmn.feel.lib.type.time.DateTimeLib;
import org.apache.commons.lang3.StringUtils;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQueries;
import java.util.Calendar;

public class DefaultDateTimeLib extends BaseDateTimeLib implements DateTimeLib<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar, Duration> {
    public static final ZoneId UTC = ZoneId.of("UTC");

    private final DatatypeFactory dataTypeFactory;

    public DefaultDateTimeLib(DatatypeFactory dataTypeFactory) {
        this.dataTypeFactory = dataTypeFactory;
    }

    @Override
    public XMLGregorianCalendar date(String literal) {
        if (StringUtils.isBlank(literal)) {
            return null;
        }

        XMLGregorianCalendar calendar = FEELXMLGregorianCalendar.makeXMLCalendar(this.dateTemporalAccessor(literal));
        return this.isValidDate(calendar) ? calendar : null;
    }

    @Override
    public XMLGregorianCalendar date(BigDecimal year, BigDecimal month, BigDecimal day) {
        if (year == null || month == null || day == null) {
            return null;
        }

        XMLGregorianCalendar calendar = FEELXMLGregorianCalendar.makeDate(year.toBigInteger(), month.intValue(), day.intValue());
        return this.isValidDate(calendar) ? calendar : null;
    }

    @Override
    public XMLGregorianCalendar date(XMLGregorianCalendar from) {
        if (from == null) {
            return null;
        }

        FEELXMLGregorianCalendar calendar = (FEELXMLGregorianCalendar) from.clone();
        calendar.setTime(DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED);
        calendar.setZoneID(null);
        return this.isValidDate(calendar) ? calendar : null;
    }

    @Override
    public XMLGregorianCalendar time(String literal) {
        if (literal == null) {
            return null;
        }

        literal = this.fixDateTimeFormat(literal);
        XMLGregorianCalendar calendar = FEELXMLGregorianCalendar.makeXMLCalendar(this.timeTemporalAccessor(literal));
        return this.isValidTime(calendar) ? calendar : null;
    }

    @Override
    public XMLGregorianCalendar time(BigDecimal hour, BigDecimal minute, BigDecimal second, Duration offset) {
        if (hour == null || minute == null || second == null) {
            return null;
        }

        XMLGregorianCalendar calendar;
        if (offset != null) {
            BigDecimal secondFraction = second.subtract(BigDecimal.valueOf(second.intValue()));
            String sign = offset.getSign() < 0 ? "-" : "+";
            int seconds = offset.getSeconds();
            String zoneId;
            if (seconds == 0) {
                zoneId = String.format("%s%02d:%02d", sign, offset.getHours(), offset.getMinutes());
            } else {
                zoneId = String.format("%s%02d:%02d:%02d", sign, offset.getHours(), offset.getMinutes(), seconds);
            }
            calendar = FEELXMLGregorianCalendar.makeTime(hour.intValue(), minute.intValue(), second.intValue(), secondFraction, zoneId);
        } else {
            BigDecimal secondFraction = second.subtract(BigDecimal.valueOf(second.intValue()));
            calendar = FEELXMLGregorianCalendar.makeTime(hour.intValue(), minute.intValue(), second.intValue(), secondFraction, null);
        }
        return this.isValidTime(calendar) ? calendar : null;
    }

    @Override
    public XMLGregorianCalendar time(XMLGregorianCalendar from) {
        if (from == null) {
            return null;
        }

        FEELXMLGregorianCalendar calendar = (FEELXMLGregorianCalendar) from.clone();
        if (from.getXMLSchemaType() == DatatypeConstants.DATE) {
            calendar.setYear(DatatypeConstants.FIELD_UNDEFINED);
            calendar.setMonth(DatatypeConstants.FIELD_UNDEFINED);
            calendar.setDay(DatatypeConstants.FIELD_UNDEFINED);
            calendar.setHour(0);
            calendar.setMinute(0);
            calendar.setSecond(0);
            calendar.setZoneID("Z");
        } else if (from.getXMLSchemaType() == DatatypeConstants.DATETIME) {
            calendar.setYear(DatatypeConstants.FIELD_UNDEFINED);
            calendar.setMonth(DatatypeConstants.FIELD_UNDEFINED);
            calendar.setDay(DatatypeConstants.FIELD_UNDEFINED);
        }
        return this.isValidTime(calendar) ? calendar : null;
    }

    @Override
    public XMLGregorianCalendar dateAndTime(String literal) {
        if (literal == null) {
            return null;
        }

        literal = this.fixDateTimeFormat(literal);
        XMLGregorianCalendar calendar = FEELXMLGregorianCalendar.makeXMLCalendar(this.dateTimeTemporalAccessor(literal));
        return this.isValidDateTime(calendar) ? calendar : null;
    }

    @Override
    public XMLGregorianCalendar dateAndTime(XMLGregorianCalendar date, XMLGregorianCalendar time) {
        if (date == null || time == null) {
            return null;
        }

        XMLGregorianCalendar calendar = FEELXMLGregorianCalendar.makeDateTime(
                BigInteger.valueOf(date.getYear()), date.getMonth(), date.getDay(),
                time.getHour(), time.getMinute(), time.getSecond(), time.getFractionalSecond(),
                ((FEELXMLGregorianCalendar) time).getZoneID()
        );
        return this.isValidDateTime(calendar) ? calendar : null;
    }

    @Override
    public Integer year(XMLGregorianCalendar date) {
        if (date == null) {
            return null;
        }

        return date.getYear();
    }
    @Override
    public Integer yearDateTime(XMLGregorianCalendar dateTime) {
        return year(dateTime);
    }

    @Override
    public Integer month(XMLGregorianCalendar date) {
        if (date == null) {
            return null;
        }

        return date.getMonth();
    }
    @Override
    public Integer monthDateTime(XMLGregorianCalendar dateTime) {
        return month(dateTime);
    }

    @Override
    public Integer day(XMLGregorianCalendar date) {
        if (date == null) {
            return null;
        }

        return date.getDay();
    }
    @Override
    public Integer dayDateTime(XMLGregorianCalendar dateTime) {
        return day(dateTime);
    }

    @Override
    public Integer weekday(XMLGregorianCalendar date) {
        if (date == null) {
            return null;
        }

        return date.toGregorianCalendar().get(Calendar.DAY_OF_WEEK) - 1;
    }
    @Override
    public Integer weekdayDateTime(XMLGregorianCalendar dateTime) {
        return weekday(dateTime);
    }

    @Override
    public Integer hour(XMLGregorianCalendar date) {
        if (date == null) {
            return null;
        }

        return date.getHour();
    }
    @Override
    public Integer hourDateTime(XMLGregorianCalendar dateTime) {
        return hour(dateTime);
    }

    @Override
    public Integer minute(XMLGregorianCalendar date) {
        if (date == null) {
            return null;
        }

        return date.getMinute();
    }
    @Override
    public Integer minuteDateTime(XMLGregorianCalendar dateTime) {
        return minute(dateTime);
    }

    @Override
    public Integer second(XMLGregorianCalendar date) {
        if (date == null) {
            return null;
        }

        return date.getSecond();
    }
    @Override
    public Integer secondDateTime(XMLGregorianCalendar dateTime) {
        return second(dateTime);
    }

    @Override
    public Duration timeOffset(XMLGregorianCalendar date) {
        if (date == null) {
            return null;
        }

        int secondsOffset = date.getTimezone();
        if (secondsOffset == DatatypeConstants.FIELD_UNDEFINED) {
            return null;
        } else {
            return this.dataTypeFactory.newDuration((long) secondsOffset * 1000);
        }
    }
    @Override
    public Duration timeOffsetDateTime(XMLGregorianCalendar dateTime) {
        return timeOffset(dateTime);
    }

    @Override
    public String timezone(XMLGregorianCalendar date) {
        if (date == null) {
            return null;
        }

        return ((FEELXMLGregorianCalendar) date).getZoneID();
    }
    @Override
    public String timezoneDateTime(XMLGregorianCalendar dateTime) {
        return timezone(dateTime);
    }

    @Override
    public XMLGregorianCalendar toDate(Object object) {
        if (!(object instanceof XMLGregorianCalendar)) {
            return null;
        }

        XMLGregorianCalendar calendar = (XMLGregorianCalendar) object;
        return date(calendar);
    }

    @Override
    public XMLGregorianCalendar toTime(Object object) {
        if (!(object instanceof XMLGregorianCalendar)) {
            return null;
        }

        return time((XMLGregorianCalendar) object);
    }

    public TemporalAccessor dateTemporalAccessor(String literal) {
        if (literal == null) {
            throw new IllegalArgumentException("Date literal cannot be null");
        }
        if (!BEGIN_YEAR.matcher(literal).find()) {
            throw new IllegalArgumentException("Year not compliant with XML Schema Part 2 Datatypes");
        }

        try {
            return LocalDate.from(FEEL_DATE.parse(literal));
        } catch (DateTimeException e) {
            throw new RuntimeException("Parsing exception in date literal", e);
        }
    }

    public TemporalAccessor timeTemporalAccessor(String literal) {
        if (literal == null) {
            throw new IllegalArgumentException("Time literal cannot be null");
        }

        try {
            TemporalAccessor parsed = FEEL_TIME.parse(literal);

            if (parsed.query(TemporalQueries.offset()) != null) {
                OffsetTime asOffSetTime = parsed.query(OffsetTime::from);
                return asOffSetTime;
            } else if (parsed.query(TemporalQueries.zone()) == null) {
                LocalTime asLocalTime = parsed.query(LocalTime::from);
                return asLocalTime;
            }

            return parsed;
        } catch (DateTimeException e) {
            throw new RuntimeException("Parsing exception in time literal", e);
        }
    }

    public TemporalAccessor dateTimeTemporalAccessor(String literal) {
        if (literal == null) {
            throw new IllegalArgumentException("Date and time literal cannot be null");
        }
        if (!BaseDateTimeLib.BEGIN_YEAR.matcher(literal).find()) {
            throw new IllegalArgumentException("Year is not not compliant with XML Schema Part 2 Datatypes");
        }

        try {
            if (literal.contains("T")) {
                TemporalAccessor value = FEEL_DATE_TIME.parse(literal);

                if (value.query(TemporalQueries.zoneId()) != null) {
                    ZonedDateTime asZonedDateTime = value.query(ZonedDateTime::from);
                    return asZonedDateTime;
                } else if (value.query(TemporalQueries.offset()) != null) {
                    OffsetDateTime asOffSetDateTime = value.query(OffsetDateTime::from);
                    return asOffSetDateTime;
                } else if (value.query(TemporalQueries.zone()) == null) {
                    LocalDateTime asLocalDateTime = value.query(LocalDateTime::from);
                    return asLocalDateTime;
                }

                return value;
            } else {
                LocalDate value = DateTimeFormatter.ISO_DATE.parse(literal, LocalDate::from);
                return LocalDateTime.of(value, LocalTime.of(0, 0));
            }
        } catch (Exception e) {
            throw new RuntimeException("Parsing exception in date and time literal", e);
        }
    }

    private boolean isValidDate(XMLGregorianCalendar calendar) {
        if (calendar == null) {
            return false;
        }

        long year = calendar.getYear();
        BigInteger eonAndYear = calendar.getEonAndYear();
        if (eonAndYear != null) {
            year = eonAndYear.intValue();
        }
        return
                isValidDate(year, calendar.getMonth(), calendar.getDay())
                        && isUndefined(calendar.getHour())
                        && isUndefined(calendar.getMinute())
                        && isUndefined(calendar.getSecond())
                ;
    }

    private boolean isValidTime(XMLGregorianCalendar calendar) {
        if (calendar == null) {
            return false;
        }

        return
                isValidTime(calendar.getHour(), calendar.getMinute(), calendar.getSecond(), calendar.getTimezone())
                        && isUndefined(calendar.getYear())
                        && isUndefined(calendar.getMonth())
                        && isUndefined(calendar.getDay())
                ;
    }

    private boolean isValidDateTime(XMLGregorianCalendar calendar) {
        if (calendar == null) {
            return false;
        }

        return isValidDateTime(
                calendar.getYear(), calendar.getMonth(), calendar.getDay(),
                calendar.getHour(), calendar.getMinute(), calendar.getSecond(), calendar.getTimezone());
    }

}
