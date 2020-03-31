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

import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQueries;

public class DefaultDateTimeLib extends BaseDateTimeLib {
    public static final ZoneId UTC = ZoneId.of("UTC");

    public XMLGregorianCalendar dateAndTime(String literal) {
        if (literal == null) {
            return null;
        }

        literal = this.fixDateTimeFormat(literal);
        XMLGregorianCalendar calendar = FEELXMLGregorianCalendar.makeXMLCalendar(this.temporalAccessor(literal));
        return this.isValidDateTime(calendar) ? calendar : null;
    }

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

    public TemporalAccessor temporalAccessor(String literal) {
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

    private boolean isValidDateTime(XMLGregorianCalendar calendar) {
        if (calendar == null) {
            return false;
        }

        return isValidDateTime(
                calendar.getYear(), calendar.getMonth(), calendar.getDay(),
                calendar.getHour(), calendar.getMinute(), calendar.getSecond(), calendar.getTimezone());
    }

}
