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
import com.gs.dmn.feel.lib.type.time.DateLib;
import org.apache.commons.lang3.StringUtils;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;


public class DefaultDateLib extends BaseDateTimeLib implements DateLib<BigDecimal, XMLGregorianCalendar> {
    @Override
    public XMLGregorianCalendar date(String literal) {
        if (StringUtils.isBlank(literal)) {
            return null;
        }

        XMLGregorianCalendar calendar = FEELXMLGregorianCalendar.makeXMLCalendar(this.temporalAccessor(literal));
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

    public XMLGregorianCalendar toDate(Object object) {
        if (!(object instanceof XMLGregorianCalendar)) {
            return null;
        }

        XMLGregorianCalendar calendar = (XMLGregorianCalendar) object;
        return date(calendar);
    }

    @Override
    public Integer year(XMLGregorianCalendar date) {
        if (date == null) {
            return null;
        }

        return date.getYear();
    }

    @Override
    public Integer month(XMLGregorianCalendar date) {
        if (date == null) {
            return null;
        }

        return date.getMonth();
    }

    @Override
    public Integer day(XMLGregorianCalendar date) {
        if (date == null) {
            return null;
        }

        return date.getDay();
    }

    @Override
    public Integer weekday(XMLGregorianCalendar date) {
        if (date == null) {
            return null;
        }

        return date.toGregorianCalendar().get(Calendar.DAY_OF_WEEK) - 1;
    }

    public TemporalAccessor temporalAccessor(String literal) {
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
}
