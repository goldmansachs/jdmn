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
import com.gs.dmn.feel.lib.type.time.TimeLib;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQueries;

public class DefaultTimeLib extends BaseDateTimeLib implements TimeLib<BigDecimal, XMLGregorianCalendar, Duration> {
    private final DatatypeFactory dataTypeFactory;

    public DefaultTimeLib(DatatypeFactory dataTypeFactory) {
        this.dataTypeFactory = dataTypeFactory;
    }

    @Override
    public XMLGregorianCalendar time(String literal) {
        if (literal == null) {
            return null;
        }

        literal = this.fixDateTimeFormat(literal);
        XMLGregorianCalendar calendar = FEELXMLGregorianCalendar.makeXMLCalendar(this.temporalAccessor(literal));
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

    public XMLGregorianCalendar toTime(Object object) {
        if (!(object instanceof XMLGregorianCalendar)) {
            return null;
        }

        return time((XMLGregorianCalendar) object);
    }

    @Override
    public Integer hour(XMLGregorianCalendar date) {
        if (date == null) {
            return null;
        }

        return date.getHour();
    }

    @Override
    public Integer minute(XMLGregorianCalendar date) {
        if (date == null) {
            return null;
        }

        return date.getMinute();
    }

    @Override
    public Integer second(XMLGregorianCalendar date) {
        if (date == null) {
            return null;
        }

        return date.getSecond();
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
    public String timezone(XMLGregorianCalendar date) {
        if (date == null) {
            return null;
        }

        return ((FEELXMLGregorianCalendar) date).getZoneID();
    }

    public TemporalAccessor temporalAccessor(String literal) {
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
}
