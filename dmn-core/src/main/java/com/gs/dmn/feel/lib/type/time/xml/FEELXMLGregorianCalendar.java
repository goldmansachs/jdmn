/**
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

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import java.math.BigDecimal;
import java.math.BigInteger;

public class FEELXMLGregorianCalendar extends XMLGregorianCalendarImpl {
    private final String zoneID;

    public static XMLGregorianCalendar makeDate(BigInteger year, int month, int day) {
        return new FEELXMLGregorianCalendar(year, month, day);
    }

    public static XMLGregorianCalendar makeTime(int hour, int minute, int second, BigDecimal fractionalSecond, int timezone, String zoneID) {
        return new FEELXMLGregorianCalendar(hour, minute, second, fractionalSecond, timezone, zoneID);
    }

    public static XMLGregorianCalendar makeDateTime(BigInteger year, int month, int day, int hour, int minute, int second, BigDecimal fractionalSecond, int timezone, String zoneID) {
        return new FEELXMLGregorianCalendar(year, month, day, hour, minute, second, fractionalSecond, timezone, zoneID);
    }

    public FEELXMLGregorianCalendar() {
        super();
        this.zoneID = null;
    }

    public FEELXMLGregorianCalendar(String lexicalRepresentation) {
        super(lexicalRepresentation);
        this.zoneID = null;
    }

    public FEELXMLGregorianCalendar(String lexicalRepresentation, String zoneID) {
        super(lexicalRepresentation);
        this.zoneID = zoneID;
    }

    private FEELXMLGregorianCalendar(
            BigInteger year,
            int month,
            int day) {
        super(year, month, day, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED, null, DatatypeConstants.FIELD_UNDEFINED);
        this.zoneID = null;
    }

    private FEELXMLGregorianCalendar(
            int hour,
            int minute,
            int second,
            BigDecimal fractionalSecond,
            int timezone,
            String zoneID) {
        super(null, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED, hour, minute, second, fractionalSecond, timezone);
        this.zoneID = zoneID;
    }

    private FEELXMLGregorianCalendar(
            BigInteger year,
            int month,
            int day,
            int hour,
            int minute,
            int second,
            BigDecimal fractionalSecond,
            int timezone,
            String zoneID) {
        super(year, month, day, hour, minute, second, fractionalSecond, timezone);
        this.zoneID = zoneID;
    }

    @Override
    public String toString() {
        QName kind = this.getXMLSchemaType();
        if (kind == DatatypeConstants.DATE) {
            int year = this.getYear();
            int month = this.getMonth();
            int day = this.getDay();
            return String.format("%d-%02d-%02d", year, month, day);
        } else if (kind == DatatypeConstants.TIME) {
            int hour = this.getHour();
            int minute = this.getMinute();
            int second = this.getSecond();
            String nanoseconds = getNanoSeconds(this.getFractionalSecond());

            if (zoneID == null) {
                String offset = makeOffsetString(this.getTimezone());
                if (nanoseconds == null) {
                    return String.format("%02d:%02d:%02d%s", hour, minute, second, offset);
                } else {
                    return String.format("%02d:%02d:%02d%s%s", hour, minute, second, nanoseconds, offset);
                }
            } else {
                if (nanoseconds == null) {
                    return String.format("%02d:%02d:%02d@%s", hour, minute, second, zoneID);
                } else {
                    return String.format("%02d:%02d:%02d%s@%s", hour, minute, second, nanoseconds, zoneID);
                }
            }

        } else if (kind == DatatypeConstants.DATETIME) {
            int year = this.getYear();
            int month = this.getMonth();
            int day = this.getDay();
            int hour = this.getHour();
            int minute = this.getMinute();
            int second = this.getSecond();
            String nanoseconds = getNanoSeconds(this.getFractionalSecond());

            if (zoneID == null) {
                String offset = makeOffsetString(this.getTimezone());
                if (nanoseconds == null) {
                    return String.format("%d-%02d-%02dT%02d:%02d:%02d%s", year, month, day, hour, minute, second, offset);
                } else {
                    return String.format("%d-%02d-%02dT%02d:%02d:%02d%s%s", year, month, day, hour, minute, second, nanoseconds, offset);
                }
            } else {
                if (nanoseconds == null) {
                    return String.format("%d-%02d-%02dT%02d:%02d:%02d@%s", year, month, day, hour, minute, second, zoneID);
                } else {
                    return String.format("%d-%02d-%02dT%02d:%02d:%02d%s@%s", year, month, day, hour, minute, second, nanoseconds, zoneID);
                }
            }
        } else {
            return super.toString();
        }
    }

    private String getNanoSeconds(BigDecimal fractionalSeconds) {
        if (fractionalSeconds == null) {
            return null;
        }
        return fractionalSeconds.stripTrailingZeros().toPlainString().substring(1);
    }

    private String makeOffsetString(int offset) {
        StringBuilder offsetBuilder = new StringBuilder();
        if (offset == 0) {
            offsetBuilder.append('Z');
        } else if (offset != DatatypeConstants.FIELD_UNDEFINED) {
            if (offset < 0) {
                offsetBuilder.append('-');
                offset *= -1;
            } else {
                offsetBuilder.append('+');
            }
            offsetBuilder.append(String.format("%02d", offset / 60));
            offsetBuilder.append(':');
            offsetBuilder.append(String.format("%02d", offset % 60));
        }
        return offsetBuilder.toString();
    }

    public String getZoneID() {
        return zoneID;
    }

    @Override
    public Object clone() {
        // eon, fractionalSecond and zoneID are instances of immutable classes, so they do not need to be cloned.
        return new FEELXMLGregorianCalendar(getEonAndYear(),
                this.getMonth(), this.getDay(),
                this.getHour(), this.getMinute(), this.getSecond(),
                this.getFractionalSecond(),
                this.getTimezone(), getZoneID());
    }
}