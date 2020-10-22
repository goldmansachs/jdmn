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

import com.gs.dmn.feel.lib.DefaultFEELLib;
import com.gs.dmn.runtime.DMNRuntimeException;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQueries;
import java.util.*;

public class FEELXMLGregorianCalendar extends XMLGregorianCalendar implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;

    private static final String[] FIELD_NAME = {
            "Year",
            "Month",
            "Day",
            "Hour",
            "Minute",
            "Second",
            "Millisecond",
            "Timezone"
    };
    private static final int YEAR = 0;
    private static final int MONTH = 1;
    private static final int DAY = 2;
    private static final int HOUR = 3;
    private static final int MINUTE = 4;
    private static final int SECOND = 5;
    private static final int MILLISECOND = 6;
    private static final int TIMEZONE = 7;

    private static final BigInteger FOUR = BigInteger.valueOf(4);
    private static final BigInteger HUNDRED = BigInteger.valueOf(100);
    private static final BigInteger FOUR_HUNDRED = BigInteger.valueOf(400);
    private static final BigInteger SIXTY = BigInteger.valueOf(60);
    private static final BigInteger TWENTY_FOUR = BigInteger.valueOf(24);
    private static final BigInteger TWELVE = BigInteger.valueOf(12);
    private static final BigDecimal DECIMAL_ZERO = BigDecimal.ZERO;
    private static final BigDecimal DECIMAL_ONE = BigDecimal.ONE;
    private static final BigDecimal DECIMAL_SIXTY = BigDecimal.valueOf(60);
    public static final String MIN_OFFSET = "+14:00";
    public static final String MAX_OFFSET = "-14:00";

    private static final int[] daysInMonth = {0,  // XML Schema months start at 1.
            31, 28, 31, 30, 31, 30,
            31, 31, 30, 31, 30, 31};

    private static int maximumDayInMonthFor(BigInteger year, int month) {
        if (month != DatatypeConstants.FEBRUARY) {
            return daysInMonth[month];
        } else {
            if (year.mod(FOUR_HUNDRED).equals(BigInteger.ZERO) ||
                    (!year.mod(HUNDRED).equals(BigInteger.ZERO) &&
                            year.mod(FOUR).equals(BigInteger.ZERO))) {
                // is a leap year.
                return 29;
            } else {
                return daysInMonth[month];
            }
        }
    }

    private static int maximumDayInMonthFor(int year, int month) {
        if (month != DatatypeConstants.FEBRUARY) {
            return daysInMonth[month];
        } else {
            if (((year % 400) == 0) ||
                    (((year % 100) != 0) && ((year % 4) == 0))) {
                // is a leap year.
                return 29;
            } else {
                return daysInMonth[DatatypeConstants.FEBRUARY];
            }
        }
    }

    /**
     * Implement Step B from
     * http://www.w3.org/TR/xmlschema-2/#dateTime-order.</p>
     */
    private static int compareField(int field1, int field2) {
        if (field1 == field2) {
            //fields are either equal in value or both undefined.
            // Step B. 1.1 AND optimized result of performing 1.1-1.4.
            return DatatypeConstants.EQUAL;
        } else {
            if (field1 == DatatypeConstants.FIELD_UNDEFINED || field2 == DatatypeConstants.FIELD_UNDEFINED) {
                // Step B. 1.2
                return DatatypeConstants.INDETERMINATE;
            } else {
                // Step B. 1.3-4.
                return field1 < field2 ? DatatypeConstants.LESSER : DatatypeConstants.GREATER;
            }
        }
    }

    private static int compareField(BigInteger field1, BigInteger field2) {
        if (field1 == null) {
            return field2 == null ? DatatypeConstants.EQUAL : DatatypeConstants.INDETERMINATE;
        }
        if (field2 == null) {
            return DatatypeConstants.INDETERMINATE;
        }
        return field1.compareTo(field2);
    }

    private static int compareField(BigDecimal field1, BigDecimal field2) {
        // optimization. especially when both arguments are null.
        if (field1 == field2) {
            return DatatypeConstants.EQUAL;
        }
        if (field1 == null) {
            field1 = DECIMAL_ZERO;
        }
        if (field2 == null) {
            field2 = DECIMAL_ZERO;
        }
        return field1.compareTo(field2);
    }

    public static XMLGregorianCalendar makeDate(BigInteger year, int month, int day) {
        return new FEELXMLGregorianCalendar(
                year, month, day,
                DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED, null,
                null);
    }

    public static XMLGregorianCalendar makeTime(int hour, int minute, int second, BigDecimal fractionalSecond, String zoneID) {
        return new FEELXMLGregorianCalendar(
                null, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED,
                hour, minute, second, fractionalSecond,
                zoneID);
    }

    public static XMLGregorianCalendar makeDateTime(BigInteger year, int month, int day, int hour, int minute, int second, BigDecimal fractionalSecond, String zoneID) {
        return new FEELXMLGregorianCalendar(
                year, month, day,
                hour, minute, second, fractionalSecond,
                zoneID);
    }

    public static XMLGregorianCalendar makeXMLCalendar(TemporalAccessor accessor) {
        return new FEELXMLGregorianCalendar(accessor);
    }

    private BigInteger eon = null;
    private int year = DatatypeConstants.FIELD_UNDEFINED;
    private int month = DatatypeConstants.FIELD_UNDEFINED;
    private int day = DatatypeConstants.FIELD_UNDEFINED;
    private int hour = DatatypeConstants.FIELD_UNDEFINED;
    private int minute = DatatypeConstants.FIELD_UNDEFINED;
    private int second = DatatypeConstants.FIELD_UNDEFINED;
    private BigDecimal fractionalSecond = null;
    private int secondsOffset = DatatypeConstants.FIELD_UNDEFINED;
    private String zoneID;

    private static final BigInteger BILLION = new BigInteger("1000000000");
    private static final Date PURE_GREGORIAN_CHANGE = new Date(Long.MIN_VALUE);

    public FEELXMLGregorianCalendar() {
    }

    private FEELXMLGregorianCalendar(TemporalAccessor accessor) throws IllegalArgumentException {
        setEon(null);
        try {
            setYear(accessor.get(ChronoField.YEAR));
        } catch (Exception e) {
            setYear(DatatypeConstants.FIELD_UNDEFINED);
        }
        try {
            setMonth(accessor.get(ChronoField.MONTH_OF_YEAR));
        } catch (Exception e) {
            setMonth(DatatypeConstants.FIELD_UNDEFINED);
        }
        try {
            setDay(accessor.get(ChronoField.DAY_OF_MONTH));
        } catch (Exception e) {
            setDay(DatatypeConstants.FIELD_UNDEFINED);
        }
        try {
            setHour(accessor.get(ChronoField.HOUR_OF_DAY));
        } catch (Exception e) {
            setHour(DatatypeConstants.FIELD_UNDEFINED);
        }
        try {
            setMinute(accessor.get(ChronoField.MINUTE_OF_HOUR));
        } catch (Exception e) {
            setMinute(DatatypeConstants.FIELD_UNDEFINED);
        }
        try {
            setSecond(accessor.get(ChronoField.SECOND_OF_MINUTE));
        } catch (Exception e) {
            setSecond(DatatypeConstants.FIELD_UNDEFINED);
        }
        try {
            BigDecimal fractional = BigDecimal.valueOf(accessor.get(ChronoField.NANO_OF_SECOND) / (double) 1000000000);
            setFractionalSecond(fractional);
        } catch (Exception e) {
            setFractionalSecond(null);
        }

        ZoneId zone = accessor.query(TemporalQueries.zone());
        setZoneID(zone == null ? null : zone.getId());
    }

    private FEELXMLGregorianCalendar(BigInteger year, int month, int day, int hour, int minute, int second, BigDecimal fractionalSecond, String zoneID) {
        setYear(year);
        setMonth(month);
        setDay(day);
        setTime(hour, minute, second, fractionalSecond);
        setZoneID(zoneID);
        // check for validity
        if (!isValid()) {
            Object[] arguments = {year, month, day, hour, minute, second, fractionalSecond, zoneID};
            throw new IllegalArgumentException(errorMessage("InvalidXGCValue-fractional", arguments));
        }
    }

    @Override
    public BigInteger getEon() {
        return eon;
    }

    @Override
    public int getYear() {
        return year;
    }

    @Override
    public BigInteger getEonAndYear() {
        // both are defined
        if (year != DatatypeConstants.FIELD_UNDEFINED && eon != null) {
            return eon.add(BigInteger.valueOf(year));
        }
        // only year is defined
        if (year != DatatypeConstants.FIELD_UNDEFINED && eon == null) {
            return BigInteger.valueOf(year);
        }
        // neither are defined
        // or only eon is defined which is not valid without a year
        return null;
    }

    @Override
    public void setYear(BigInteger year) {
        if (year == null) {
            this.eon = null;
            this.year = DatatypeConstants.FIELD_UNDEFINED;
        } else {
            BigInteger temp = year.remainder(BILLION);
            this.year = temp.intValue();
            setEon(year.subtract(temp));
        }
    }

    @Override
    public void setYear(int year) {
        if (year == DatatypeConstants.FIELD_UNDEFINED) {
            this.year = DatatypeConstants.FIELD_UNDEFINED;
            this.eon = null;
        } else if (Math.abs(year) < BILLION.intValue()) {
            this.year = year;
            this.eon = null;
        } else {
            BigInteger theYear = BigInteger.valueOf(year);
            BigInteger remainder = theYear.remainder(BILLION);
            this.year = remainder.intValue();
            setEon(theYear.subtract(remainder));
        }
    }

    void setEon(BigInteger eon) {
        if (eon != null && eon.compareTo(BigInteger.ZERO) == 0) {
            // Treat ZERO as field being undefined.
            this.eon = null;
        } else {
            this.eon = eon;
        }
    }

    @Override
    public int getMonth() {
        return month;
    }

    @Override
    public void setMonth(int month) {
        if (month < DatatypeConstants.JANUARY || DatatypeConstants.DECEMBER < month)
            if (month != DatatypeConstants.FIELD_UNDEFINED)
                invalidFieldValue(MONTH, month);
        this.month = month;
    }

    @Override
    public int getDay() {
        return day;
    }

    @Override
    public void setDay(int day) {
        if (day < 1 || 31 < day)
            if (day != DatatypeConstants.FIELD_UNDEFINED)
                invalidFieldValue(DAY, day);
        this.day = day;
    }

    @Override
    public int getHour() {
        return hour;
    }

    @Override
    public void setHour(int hour) {
        setHour(hour, true);
    }

    void setHour(int hour, boolean validate) {
        if (hour < 0 || hour > 24) {
            if (hour != DatatypeConstants.FIELD_UNDEFINED) {
                invalidFieldValue(HOUR, hour);
            }
        }
        this.hour = hour;
        if (validate) {
            fixHour();
        }
    }

    void fixHour() {
        if (getHour() == 24) {
            if (getMinute() != 0 || getSecond() != 0) {
                invalidFieldValue(HOUR, getHour());
            }
            setHour(0, false);
            add(DefaultFEELLib.DATA_TYPE_FACTORY.newDuration(true, 0, 0, 1, 0, 0, 0));
        }
    }

    @Override
    public int getMinute() {
        return minute;
    }

    @Override
    public void setMinute(int minute) {
        if (minute < 0 || 59 < minute)
            if (minute != DatatypeConstants.FIELD_UNDEFINED)
                invalidFieldValue(MINUTE, minute);
        this.minute = minute;
    }

    @Override
    public int getSecond() {
        return second;
    }

    @Override
    public BigDecimal getFractionalSecond() {
        return fractionalSecond;
    }

    /**
     * @return result of adding second and fractional second field
     */
    private BigDecimal getSeconds() {
        if (second == DatatypeConstants.FIELD_UNDEFINED) {
            return DECIMAL_ZERO;
        }
        BigDecimal result = BigDecimal.valueOf(second);
        if (fractionalSecond != null) {
            return result.add(fractionalSecond);
        } else {
            return result;
        }
    }

    @Override
    public int getMillisecond() {
        if (fractionalSecond == null) {
            return DatatypeConstants.FIELD_UNDEFINED;
        } else {
            return fractionalSecond.movePointRight(3).intValue();
        }
    }

    @Override
    public void setSecond(int second) {
        if (second < 0 || 60 < second)   // leap second allows for 60
            if (second != DatatypeConstants.FIELD_UNDEFINED)
                invalidFieldValue(SECOND, second);
        this.second = second;
    }

    @Override
    public void setMillisecond(int millisecond) {
        if (millisecond == DatatypeConstants.FIELD_UNDEFINED) {
            fractionalSecond = null;
        } else {
            if (millisecond < 0 || 999 < millisecond)
                if (millisecond != DatatypeConstants.FIELD_UNDEFINED)
                    invalidFieldValue(MILLISECOND, millisecond);
            fractionalSecond = new BigDecimal((long) millisecond).movePointLeft(3);
        }
    }

    @Override
    public void setFractionalSecond(BigDecimal fractional) {
        if (fractional != null && fractional.compareTo(BigDecimal.ZERO) == 0) {
            fractional = null;
        }
        if (fractional != null) {
            if ((fractional.compareTo(DECIMAL_ZERO) < 0) ||
                    (fractional.compareTo(DECIMAL_ONE) > 0)) {
                throw new IllegalArgumentException(errorMessage("InvalidFractional", new Object[]{fractional}));
            }
        }
        this.fractionalSecond = fractional;
    }

    @Override
    public int getTimezone() {
        return this.secondsOffset;
    }

    @Override
    public void setTimezone(int offset) {
        throw new DMNRuntimeException("Not supported");
    }

    private void setSecondsOffset(String zoneID) {
        int offset = secondsOffset(zoneID);
        if (offset < -18 * 3600 || 18 * 3600 < offset) {
            if (offset != DatatypeConstants.FIELD_UNDEFINED) {
                invalidFieldValue(TIMEZONE, offset);
            }
        }
        this.secondsOffset = offset;
    }

    public String getZoneID() {
        return zoneID;
    }

    public void setZoneID(String zoneID) {
        this.zoneID = zoneID;
        // Derive offset from zoneId
        this.setSecondsOffset(zoneID);
    }

    private int secondsOffset(String zoneID) {
        if (zoneID != null) {
            if (year != DatatypeConstants.FIELD_UNDEFINED) {
                LocalDateTime ldt = LocalDateTime.of(year, month, day, hour, minute, second);
                ZoneOffset offset = ldt.atZone(ZoneId.of(zoneID)).getOffset();
                return offset.get(ChronoField.OFFSET_SECONDS);
            } else {
                // milliseconds to seconds
                return TimeZone.getTimeZone(ZoneId.of(zoneID)).getRawOffset() / 1000;
            }
        }
        return DatatypeConstants.FIELD_UNDEFINED;
    }

    @Override
    public void setTime(int hour, int minute, int second) {
        setTime(hour, minute, second, null);
    }

    @Override
    public void setTime(int hour, int minute, int second, BigDecimal fractional) {
        setHour(hour, false);
        setMinute(minute);
        if (second != 60) {
            setSecond(second);
        } else if ((hour == 23 && minute == 59) || (hour == 0 && minute == 0)) {
            setSecond(second);
        } else {
            invalidFieldValue(SECOND, second);
        }
        setFractionalSecond(fractional);
        fixHour();
    }

    @Override
    public void setTime(int hour, int minute, int second, int millisecond) {
        setHour(hour, false);
        setMinute(minute);
        if (second != 60) {
            setSecond(second);
        } else if ((hour == 23 && minute == 59) || (hour == 0 && minute == 0)) {
            setSecond(second);
        } else {
            invalidFieldValue(SECOND, second);
        }
        setMillisecond(millisecond);
        // must test hour after setting seconds
        fixHour();
    }

    private void invalidFieldValue(int field, int value) {
        throw new IllegalArgumentException(errorMessage("InvalidFieldValue", new Object[]{value, FIELD_NAME[field]}));
    }

    @Override
    public int compare(XMLGregorianCalendar other) {
        FEELXMLGregorianCalendar lhs = this;
        FEELXMLGregorianCalendar rhs = (FEELXMLGregorianCalendar) other;

        int result;
        if (lhs.getTimezone() == rhs.getTimezone()) {
            // Optimization:
            // both instances are in same timezone or
            // both are FIELD_UNDEFINED.
            // Avoid costly normalization of timezone to 'Z' time.
            return internalCompare(lhs, rhs);
        } else if (lhs.getTimezone() != DatatypeConstants.FIELD_UNDEFINED &&
                rhs.getTimezone() != DatatypeConstants.FIELD_UNDEFINED) {
            // Both instances have different timezones.
            // Normalize to UTC time and compare.
            lhs = (FEELXMLGregorianCalendar) lhs.normalize();
            rhs = (FEELXMLGregorianCalendar) rhs.normalize();
            return internalCompare(lhs, rhs);
        } else if (lhs.getTimezone() != DatatypeConstants.FIELD_UNDEFINED) {
            if (lhs.getTimezone() != 0) {
                lhs = (FEELXMLGregorianCalendar) lhs.normalize();
            }
            // C. step 1
            XMLGregorianCalendar minQ = rhs.normalizeToTimezone(MIN_OFFSET);
            result = internalCompare(lhs, minQ);
            if (result == DatatypeConstants.LESSER) {
                return result;
            }
            // C. step 2
            XMLGregorianCalendar maxQ = rhs.normalizeToTimezone(MAX_OFFSET);
            result = internalCompare(lhs, maxQ);
            if (result == DatatypeConstants.GREATER) {
                return result;
            } else {
                // C. step 3
                return DatatypeConstants.INDETERMINATE;
            }
        } else {
            // rhs.getTimezone() != DatatypeConstants.FIELD_UNDEFINED
            // lhs has no timezone and rhs does.
            if (rhs.getTimezone() != 0) {
                rhs = (FEELXMLGregorianCalendar) rhs.normalizeToTimezone(rhs.zoneID);
            }
            // D. step 1
            XMLGregorianCalendar maxP = lhs.normalizeToTimezone(MAX_OFFSET);
            result = internalCompare(maxP, rhs);
            if (result == DatatypeConstants.LESSER) {
                return result;
            }
            // D. step 2
            XMLGregorianCalendar minP = lhs.normalizeToTimezone(MIN_OFFSET);
            result = internalCompare(minP, rhs);
            if (result == DatatypeConstants.GREATER) {
                return result;
            } else {
                // D. step 3
                return DatatypeConstants.INDETERMINATE;
            }
        }
    }

    @Override
    public XMLGregorianCalendar normalize() {
        XMLGregorianCalendar normalized = normalizeToTimezone(zoneID);
        // if timezone was undefined, leave it undefined
        if (getTimezone() == DatatypeConstants.FIELD_UNDEFINED) {
            normalized.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
        }
        // if milliseconds was undefined, leave it undefined
        if (getMillisecond() == DatatypeConstants.FIELD_UNDEFINED) {
            normalized.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);
        }
        return normalized;
    }

    public XMLGregorianCalendar normalizeToTimezone(String zoneID) {
        int seconds = secondsOffset(zoneID);
        FEELXMLGregorianCalendar result = (FEELXMLGregorianCalendar) this.clone();
        // normalizing to UTC time negates the timezone offset before addition.
        seconds = -seconds;
        Duration d = DefaultFEELLib.DATA_TYPE_FACTORY.newDuration(
                seconds >= 0,
                0, 0, 0,
                0, 0, seconds < 0 ? -seconds : seconds);
        result.add(d);
        // set to zulu UTC time.
        result.setZoneID("Z");
        return result;
    }

    private static int internalCompare(XMLGregorianCalendar lhs, XMLGregorianCalendar rhs) {
        int result;
        // compare Year.
        if (lhs.getEon() == rhs.getEon()) {
            // Eon field is only equal when null.
            // optimized case for comparing year not requiring eon field.
            result = compareField(lhs.getYear(), rhs.getYear());
            if (result != DatatypeConstants.EQUAL) {
                return result;
            }
        } else {
            result = compareField(lhs.getEonAndYear(), rhs.getEonAndYear());
            if (result != DatatypeConstants.EQUAL) {
                return result;
            }
        }
        // compare month
        result = compareField(lhs.getMonth(), rhs.getMonth());
        if (result != DatatypeConstants.EQUAL) {
            return result;
        }
        // compare day
        result = compareField(lhs.getDay(), rhs.getDay());
        if (result != DatatypeConstants.EQUAL) {
            return result;
        }
        // compare hour
        result = compareField(lhs.getHour(), rhs.getHour());
        if (result != DatatypeConstants.EQUAL) {
            return result;
        }
        // compare minute
        result = compareField(lhs.getMinute(), rhs.getMinute());
        if (result != DatatypeConstants.EQUAL) {
            return result;
        }
        // compare rhs
        result = compareField(lhs.getSecond(), rhs.getSecond());
        if (result != DatatypeConstants.EQUAL) {
            return result;
        }
        // compare fractional rhs
        result = compareField(lhs.getFractionalSecond(), rhs.getFractionalSecond());
        return result;
    }

    public boolean same(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        FEELXMLGregorianCalendar calendar = (FEELXMLGregorianCalendar) o;
        return year == calendar.year &&
                month == calendar.month &&
                day == calendar.day &&
                hour == calendar.hour &&
                minute == calendar.minute &&
                second == calendar.second &&
                Objects.equals(eon, calendar.eon) &&
                Objects.equals(fractionalSecond, calendar.fractionalSecond) &&
                Objects.equals(zoneID, calendar.zoneID);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof XMLGregorianCalendar)) {
            return false;
        }
        return compare((XMLGregorianCalendar) obj) == DatatypeConstants.EQUAL;
    }

    @Override
    public int hashCode() {
        // Following two dates compare to EQUALS since in different timezones.
        // 2000-01-15T12:00:00-05:00 == 2000-01-15T13:00:00-04:00
        //
        // Must ensure both instances generate same hashcode by normalizing this to UTC timezone.
        int timezone = getTimezone();
        if (timezone == DatatypeConstants.FIELD_UNDEFINED) {
            timezone = 0;
        }
        XMLGregorianCalendar gc = this;
        if (timezone != 0) {
            gc = this.normalizeToTimezone(getZoneID());
        }
        return gc.getYear() + gc.getMonth() + gc.getDay() + gc.getHour() + gc.getMinute() + gc.getSecond();
    }

    @Override
    public String toXMLFormat() {
        QName kind = getXMLSchemaType();
        String formatString = null;
        // Fix 4971612: invalid SCCS macro substitution in data string
        //   no %{alpha}% to avoid SCCS macro substitution
        if (kind == DatatypeConstants.DATETIME) {
            formatString = "%Y-%M-%DT%h:%m:%s" + "%z";
        } else if (kind == DatatypeConstants.DATE) {
            formatString = "%Y-%M-%D" + "%z";
        } else if (kind == DatatypeConstants.TIME) {
            formatString = "%h:%m:%s" + "%z";
        }
        return format(formatString);
    }

    @Override
    public QName getXMLSchemaType() {
        int mask =
                (year != DatatypeConstants.FIELD_UNDEFINED ? 0x20 : 0) |
                        (month != DatatypeConstants.FIELD_UNDEFINED ? 0x10 : 0) |
                        (day != DatatypeConstants.FIELD_UNDEFINED ? 0x08 : 0) |
                        (hour != DatatypeConstants.FIELD_UNDEFINED ? 0x04 : 0) |
                        (minute != DatatypeConstants.FIELD_UNDEFINED ? 0x02 : 0) |
                        (second != DatatypeConstants.FIELD_UNDEFINED ? 0x01 : 0);
        switch (mask) {
            case 0x3F:
                return DatatypeConstants.DATETIME;
            case 0x38:
                return DatatypeConstants.DATE;
            case 0x07:
                return DatatypeConstants.TIME;
            default:
                throw new IllegalStateException(
                        errorMessage("InvalidXGCFields", new Object[] {this.getClass().getName() + "#getXMLSchemaType() :"})
                );
        }
    }

    @Override
    public boolean isValid() {
        // check if days in month is valid. Can be dependent on leap year.
        if (getMonth() == DatatypeConstants.FEBRUARY) {
            // years could not be set
            int maxDays = 29;
            if (eon == null) {
                if (year != DatatypeConstants.FIELD_UNDEFINED) {
                    maxDays = maximumDayInMonthFor(year, getMonth());
                }
            } else {
                BigInteger years = getEonAndYear();
                if (years != null) {
                    maxDays = maximumDayInMonthFor(getEonAndYear(), DatatypeConstants.FEBRUARY);
                }
            }
            if (getDay() > maxDays) {
                return false;
            }
        }

        // http://www.w3.org/2001/05/xmlschema-errata#e2-45
        if (getHour() == 24) {
            if (getMinute() != 0 || getSecond() != 0) {
                return false;
            }
        }

        // XML Schema 1.0 specification defines year value of zero as
        // invalid. Allow this class to set year field to zero
        // since XML Schema 1.0 errata states that lexical zero will
        // be allowed in next version and treated as 1 B.C.E.
        if (eon == null) {
            // optimize check.
            return year != 0;
        } else {
            BigInteger yearField = getEonAndYear();
            if (yearField != null) {
                int result = compareField(yearField, BigInteger.ZERO);
                return result != DatatypeConstants.EQUAL;
            }
        }
        return true;
    }

    @Override
    public void add(Duration duration) {
        /*
         * Extracted from
         * http://www.w3.org/TR/xmlschema-2/#adding-durations-to-dateTimes
         * to ensure implemented properly. See spec for definitions of methods
         * used in algorithm.
         *
         * Given a dateTime S and a duration D, specifies how to compute a
         * dateTime E where E is the end of the time period with start S and
         * duration D i.e. E = S + D.
         *
         * The following is the precise specification.
         * These steps must be followed in the same order.
         * If a field in D is not specified, it is treated as if it were zero.
         * If a field in S is not specified, it is treated in the calculation
         * as if it were the minimum allowed value in that field, however,
         * after the calculation is concluded, the corresponding field in
         * E is removed (set to unspecified).
         *
         * Months (may be modified additionally below)
         *  temp := S[month] + D[month]
         *  E[month] := modulo(temp, 1, 13)
         *  carry := fQuotient(temp, 1, 13)
         */

        boolean[] fieldUndefined = {
                false,
                false,
                false,
                false,
                false,
                false
        };

        int signum = duration.getSign();

        int startMonth = getMonth();
        if (startMonth == DatatypeConstants.FIELD_UNDEFINED) {
            startMonth = DatatypeConstants.JANUARY;
            fieldUndefined[MONTH] = true;
        }

        BigInteger dMonths = sanitize(duration.getField(DatatypeConstants.MONTHS), signum);
        BigInteger temp = BigInteger.valueOf(startMonth).add(dMonths);
        setMonth(temp.subtract(BigInteger.ONE).mod(TWELVE).intValue() + 1);
        BigInteger carry =
                new BigDecimal(temp.subtract(BigInteger.ONE)).divide(new BigDecimal(TWELVE), BigDecimal.ROUND_FLOOR).toBigInteger();


        // Years (may be modified additionally below)
        //     E[year] := S[year] + D[year] + carry
        BigInteger startYear = getEonAndYear();
        if (startYear == null) {
            fieldUndefined[YEAR] = true;
            startYear = BigInteger.ZERO;
        }
        BigInteger dYears = sanitize(duration.getField(DatatypeConstants.YEARS), signum);
        BigInteger endYear = startYear.add(dYears).add(carry);
        setYear(endYear);

        // Zone
        //    E[zone] := S[zone]
        //    no-op since adding to this, not to a new end point.
        // Seconds
        //    temp := S[second] + D[second]
        //    E[second] := modulo(temp, 60)
        //    carry := fQuotient(temp, 60)
        BigDecimal startSeconds;
        if (getSecond() == DatatypeConstants.FIELD_UNDEFINED) {
            fieldUndefined[SECOND] = true;
            startSeconds = DECIMAL_ZERO;
        } else {
            // seconds + fractionalSeconds
            startSeconds = getSeconds();
        }

        // Duration seconds is SECONDS + FRACTIONALSECONDS.
        BigDecimal dSeconds = sanitize((BigDecimal) duration.getField(DatatypeConstants.SECONDS), signum);
        BigDecimal tempBD = startSeconds.add(dSeconds);
        BigDecimal fQuotient = new BigDecimal(new BigDecimal(tempBD.toBigInteger()).divide(DECIMAL_SIXTY, BigDecimal.ROUND_FLOOR).toBigInteger());
        BigDecimal endSeconds = tempBD.subtract(fQuotient.multiply(DECIMAL_SIXTY));

        carry = fQuotient.toBigInteger();
        setSecond(endSeconds.intValue());
        BigDecimal tempFracSeconds = endSeconds.subtract(new BigDecimal(BigInteger.valueOf(getSecond())));
        if (tempFracSeconds.compareTo(DECIMAL_ZERO) < 0) {
            setFractionalSecond(DECIMAL_ONE.add(tempFracSeconds));
            if (getSecond() == 0) {
                setSecond(59);
                carry = carry.subtract(BigInteger.ONE);
            } else {
                setSecond(getSecond() - 1);
            }
        } else {
            setFractionalSecond(tempFracSeconds);
        }

        //  Minutes
        //      temp := S[minute] + D[minute] + carry
        //      E[minute] := modulo(temp, 60)
        //      carry := fQuotient(temp, 60)
        int startMinutes = getMinute();
        if (startMinutes == DatatypeConstants.FIELD_UNDEFINED) {
            fieldUndefined[MINUTE] = true;
            startMinutes = 0;
        }
        BigInteger dMinutes = sanitize(duration.getField(DatatypeConstants.MINUTES), signum);

        temp = BigInteger.valueOf(startMinutes).add(dMinutes).add(carry);
        setMinute(temp.mod(SIXTY).intValue());
        carry = new BigDecimal(temp).divide(DECIMAL_SIXTY, BigDecimal.ROUND_FLOOR).toBigInteger();

        //  Hours
        //      temp := S[hour] + D[hour] + carry
        //      E[hour] := modulo(temp, 24)
        //      carry := fQuotient(temp, 24)
        int startHours = getHour();
        if (startHours == DatatypeConstants.FIELD_UNDEFINED) {
            fieldUndefined[HOUR] = true;
            startHours = 0;
        }
        BigInteger dHours = sanitize(duration.getField(DatatypeConstants.HOURS), signum);

        temp = BigInteger.valueOf(startHours).add(dHours).add(carry);
        setHour(temp.mod(TWENTY_FOUR).intValue(), false);
        carry = new BigDecimal(temp).divide(new BigDecimal(TWENTY_FOUR), BigDecimal.ROUND_FLOOR).toBigInteger();

        //  Days
        //      if S[day] > maximumDayInMonthFor(E[year], E[month])
        //             + tempDays := maximumDayInMonthFor(E[year], E[month])
        //      else if S[day] < 1
        //             + tempDays := 1
        //      else
        //             + tempDays := S[day]
        //        E[day] := tempDays + D[day] + carry
        //        START LOOP
        //             + IF E[day] < 1
        //                  # E[day] := E[day] +
        //                       maximumDayInMonthFor(E[year], E[month] - 1)
        //                   # carry := -1
        //             + ELSE IF E[day] > maximumDayInMonthFor(E[year], E[month])
        //                   # E[day] :=
        //                          E[day] - maximumDayInMonthFor(E[year], E[month])
        //                   # carry := 1
        //             + ELSE EXIT LOOP
        //             + temp := E[month] + carry
        //             + E[month] := modulo(temp, 1, 13)
        //             + E[year] := E[year] + fQuotient(temp, 1, 13)
        //             + GOTO START LOOP
        BigInteger tempDays;
        int startDay = getDay();
        if (startDay == DatatypeConstants.FIELD_UNDEFINED) {
            fieldUndefined[DAY] = true;
            startDay = 1;
        }
        BigInteger dDays = sanitize(duration.getField(DatatypeConstants.DAYS), signum);
        int maxDayInMonth = maximumDayInMonthFor(getEonAndYear(), getMonth());
        if (startDay > maxDayInMonth) {
            tempDays = BigInteger.valueOf(maxDayInMonth);
        } else if (startDay < 1) {
            tempDays = BigInteger.ONE;
        } else {
            tempDays = BigInteger.valueOf(startDay);
        }
        BigInteger endDays = tempDays.add(dDays).add(carry);
        int monthCarry;
        int intTemp;
        while (true) {
            if (endDays.compareTo(BigInteger.ONE) < 0) {
                // calculate days in previous month, watch for month roll over
                BigInteger mdimf;
                if (month >= 2) {
                    mdimf = BigInteger.valueOf(maximumDayInMonthFor(getEonAndYear(), getMonth() - 1));
                } else {
                    // roll over to December of previous year
                    mdimf = BigInteger.valueOf(maximumDayInMonthFor(getEonAndYear().subtract(BigInteger.valueOf(1)), 12));
                }
                endDays = endDays.add(mdimf);
                monthCarry = -1;
            } else if (endDays.compareTo(BigInteger.valueOf(maximumDayInMonthFor(getEonAndYear(), getMonth()))) > 0) {
                endDays = endDays.add(BigInteger.valueOf(-maximumDayInMonthFor(getEonAndYear(), getMonth())));
                monthCarry = 1;
            } else {
                break;
            }

            intTemp = getMonth() + monthCarry;
            int endMonth = (intTemp - 1) % (13 - 1);
            int quotient;
            if (endMonth < 0) {
                endMonth = (13 - 1) + endMonth + 1;
                quotient = new BigDecimal(intTemp - 1).divide(new BigDecimal(TWELVE), BigDecimal.ROUND_UP).intValue();
            } else {
                quotient = (intTemp - 1) / (13 - 1);
                endMonth += 1;
            }
            setMonth(endMonth);
            if (quotient != 0) {
                setYear(getEonAndYear().add(BigInteger.valueOf(quotient)));
            }
        }
        setDay(endDays.intValue());

        // set fields that where undefined before this addition, back to undefined.
        for (int i = YEAR; i <= SECOND; i++) {
            if (fieldUndefined[i]) {
                switch (i) {
                    case YEAR:
                        setYear(DatatypeConstants.FIELD_UNDEFINED);
                        break;
                    case MONTH:
                        setMonth(DatatypeConstants.FIELD_UNDEFINED);
                        break;
                    case DAY:
                        setDay(DatatypeConstants.FIELD_UNDEFINED);
                        break;
                    case HOUR:
                        setHour(DatatypeConstants.FIELD_UNDEFINED, false);
                        break;
                    case MINUTE:
                        setMinute(DatatypeConstants.FIELD_UNDEFINED);
                        break;
                    case SECOND:
                        setSecond(DatatypeConstants.FIELD_UNDEFINED);
                        setFractionalSecond(null);
                        break;
                }
            }
        }
    }

    @Override
    public java.util.GregorianCalendar toGregorianCalendar() {
        GregorianCalendar result;
        final int DEFAULT_TIMEZONE_OFFSET = DatatypeConstants.FIELD_UNDEFINED;
        TimeZone tz = getTimeZone(DEFAULT_TIMEZONE_OFFSET);
        // Use the following instead for JDK7 only:
        // Locale locale = Locale.getDefault(Locale.Category.FORMAT);
        Locale locale = getDefaultLocale();

        result = new GregorianCalendar(tz, locale);
        result.clear();
        result.setGregorianChange(PURE_GREGORIAN_CHANGE);

        // if year( and eon) are undefined, leave default Calendar values
        BigInteger year = getEonAndYear();
        if (year != null) {
            result.set(Calendar.ERA, year.signum() == -1 ? GregorianCalendar.BC : GregorianCalendar.AD);
            result.set(Calendar.YEAR, year.abs().intValue());
        }

        // only set month if it is set
        if (month != DatatypeConstants.FIELD_UNDEFINED) {
            // Calendar.MONTH is zero based while XMLGregorianCalendar month field is not.
            result.set(Calendar.MONTH, month - 1);
        }

        // only set day if it is set
        if (day != DatatypeConstants.FIELD_UNDEFINED) {
            result.set(Calendar.DAY_OF_MONTH, day);
        }

        // only set hour if it is set
        if (hour != DatatypeConstants.FIELD_UNDEFINED) {
            result.set(Calendar.HOUR_OF_DAY, hour);
        }

        // only set minute if it is set
        if (minute != DatatypeConstants.FIELD_UNDEFINED) {
            result.set(Calendar.MINUTE, minute);
        }

        // only set second if it is set
        if (second != DatatypeConstants.FIELD_UNDEFINED) {
            result.set(Calendar.SECOND, second);
        }

        // only set millisend if it is set
        if (fractionalSecond != null) {
            result.set(Calendar.MILLISECOND, getMillisecond());
        }

        return result;
    }

    private Locale getDefaultLocale() {
        return Locale.getDefault();
    }

    @Override
    public GregorianCalendar toGregorianCalendar(TimeZone timezone, Locale aLocale, XMLGregorianCalendar defaults) {
        GregorianCalendar result;
        TimeZone tz = timezone;
        if (tz == null) {
            int defaultZoneoffset = DatatypeConstants.FIELD_UNDEFINED;
            if (defaults != null) {
                defaultZoneoffset = defaults.getTimezone();
            }
            tz = getTimeZone(defaultZoneoffset);
        }
        if (aLocale == null) {
            aLocale = Locale.getDefault();
        }
        result = new GregorianCalendar(tz, aLocale);
        result.clear();
        result.setGregorianChange(PURE_GREGORIAN_CHANGE);

        // if year( and eon) are undefined, leave default Calendar values
        BigInteger year = getEonAndYear();
        if (year != null) {
            result.set(Calendar.ERA, year.signum() == -1 ? GregorianCalendar.BC : GregorianCalendar.AD);
            result.set(Calendar.YEAR, year.abs().intValue());
        } else {
            // use default if set
            BigInteger defaultYear = (defaults != null) ? defaults.getEonAndYear() : null;
            if (defaultYear != null) {
                result.set(Calendar.ERA, defaultYear.signum() == -1 ? GregorianCalendar.BC : GregorianCalendar.AD);
                result.set(Calendar.YEAR, defaultYear.abs().intValue());
            }
        }

        // only set month if it is set
        if (month != DatatypeConstants.FIELD_UNDEFINED) {
            // Calendar.MONTH is zero based while XMLGregorianCalendar month field is not.
            result.set(Calendar.MONTH, month - 1);
        } else {
            // use default if set
            int defaultMonth = (defaults != null) ? defaults.getMonth() : DatatypeConstants.FIELD_UNDEFINED;
            if (defaultMonth != DatatypeConstants.FIELD_UNDEFINED) {
                // Calendar.MONTH is zero based while XMLGregorianCalendar month field is not.
                result.set(Calendar.MONTH, defaultMonth - 1);
            }
        }

        // only set day if it is set
        if (day != DatatypeConstants.FIELD_UNDEFINED) {
            result.set(Calendar.DAY_OF_MONTH, day);
        } else {
            // use default if set
            int defaultDay = (defaults != null) ? defaults.getDay() : DatatypeConstants.FIELD_UNDEFINED;
            if (defaultDay != DatatypeConstants.FIELD_UNDEFINED) {
                result.set(Calendar.DAY_OF_MONTH, defaultDay);
            }
        }

        // only set hour if it is set
        if (hour != DatatypeConstants.FIELD_UNDEFINED) {
            result.set(Calendar.HOUR_OF_DAY, hour);
        } else {
            // use default if set
            int defaultHour = (defaults != null) ? defaults.getHour() : DatatypeConstants.FIELD_UNDEFINED;
            if (defaultHour != DatatypeConstants.FIELD_UNDEFINED) {
                result.set(Calendar.HOUR_OF_DAY, defaultHour);
            }
        }

        // only set minute if it is set
        if (minute != DatatypeConstants.FIELD_UNDEFINED) {
            result.set(Calendar.MINUTE, minute);
        } else {
            // use default if set
            int defaultMinute = (defaults != null) ? defaults.getMinute() : DatatypeConstants.FIELD_UNDEFINED;
            if (defaultMinute != DatatypeConstants.FIELD_UNDEFINED) {
                result.set(Calendar.MINUTE, defaultMinute);
            }
        }

        // only set second if it is set
        if (second != DatatypeConstants.FIELD_UNDEFINED) {
            result.set(Calendar.SECOND, second);
        } else {
            // use default if set
            int defaultSecond = (defaults != null) ? defaults.getSecond() : DatatypeConstants.FIELD_UNDEFINED;
            if (defaultSecond != DatatypeConstants.FIELD_UNDEFINED) {
                result.set(Calendar.SECOND, defaultSecond);
            }
        }

        // only set millisend if it is set
        if (fractionalSecond != null) {
            result.set(Calendar.MILLISECOND, getMillisecond());
        } else {
            // use default if set
            BigDecimal defaultFractionalSecond = (defaults != null) ? defaults.getFractionalSecond() : null;
            if (defaultFractionalSecond != null) {
                result.set(Calendar.MILLISECOND, defaults.getMillisecond());
            }
        }

        return result;
    }

    @Override
    public TimeZone getTimeZone(int defaultZoneoffset) {
        TimeZone result;
        int zoneoffset = getTimezone();

        if (zoneoffset == DatatypeConstants.FIELD_UNDEFINED) {
            zoneoffset = defaultZoneoffset;
        }
        if (zoneoffset == DatatypeConstants.FIELD_UNDEFINED) {
            result = TimeZone.getDefault();
        } else {
            // zoneoffset is in seconds. Convert to custom timezone id format.
            char sign = zoneoffset < 0 ? '-' : '+';
            if (sign == '-') {
                zoneoffset = -zoneoffset;
            }
            int hour = zoneoffset / 3600;
            int seconds = zoneoffset % 3600;
            int minutes = seconds / 60;

            // Javadoc for java.util.TimeZone documents max length
            // for customTimezoneId is 8 when optional ':' is not used.
            // Format is
            // "GMT" ('-'|'+') (digit digit?) (digit digit)?
            //                 hour          minutes
            StringBuffer customTimezoneId = new StringBuffer(8);
            customTimezoneId.append("GMT");
            customTimezoneId.append(sign);
            customTimezoneId.append(hour);
            if (minutes != 0) {
                customTimezoneId.append(minutes);
            }
            result = TimeZone.getTimeZone(customTimezoneId.toString());
        }
        return result;
    }

    @Override
    public Object clone() {
        // Both this.eon and this.fractionalSecond are instances
        // of immutable classes, so they do not need to be cloned.
        return new FEELXMLGregorianCalendar(
                getEonAndYear(), this.month, this.day,
                this.hour, this.minute, this.second, this.fractionalSecond,
                this.zoneID);
    }

    @Override
    public void clear() {
        eon = null;
        year = DatatypeConstants.FIELD_UNDEFINED;
        month = DatatypeConstants.FIELD_UNDEFINED;
        day = DatatypeConstants.FIELD_UNDEFINED;
        hour = DatatypeConstants.FIELD_UNDEFINED;
        minute = DatatypeConstants.FIELD_UNDEFINED;
        second = DatatypeConstants.FIELD_UNDEFINED;
        fractionalSecond = null;
        secondsOffset = DatatypeConstants.FIELD_UNDEFINED;
        zoneID = null;
    }

    /**
     * Prints this object according to the format specification.
     * <p>
     * <p>
     * I wrote a custom format method for a particular format string to
     * see if it improves the performance, but it didn't. So this interpreting
     * approach isn't too bad.
     * <p>
     * <p>
     * StringBuffer -> StringBuilder change had a very visible impact.
     * It almost cut the execution time to half, but unfortunately we can't use it
     * because we need to run on JDK 1.3
     */
    private String format(String format) {
        char[] buf = new char[32];
        int bufPtr = 0;

        int fidx = 0, flen = format.length();

        while (fidx < flen) {
            char fch = format.charAt(fidx++);
            if (fch != '%') {// not a meta char
                buf[bufPtr++] = fch;
                continue;
            }

            switch (format.charAt(fidx++)) {
                case 'Y':
                    if (eon == null) {
                        // optimized path
                        int y = getYear();
                        if (y < 0) {
                            buf[bufPtr++] = '-';
                            y = -y;
                        }
                        bufPtr = print4Number(buf, bufPtr, y);
                    } else {
                        String s = getEonAndYear().toString();
                        // reallocate the buffer now so that it has enough space
                        char[] n = new char[buf.length + s.length()];
                        System.arraycopy(buf, 0, n, 0, bufPtr);
                        buf = n;
                        for (int i = s.length(); i < 4; i++)
                            buf[bufPtr++] = '0';
                        s.getChars(0, s.length(), buf, bufPtr);
                        bufPtr += s.length();
                    }
                    break;
                case 'M':
                    bufPtr = print2Number(buf, bufPtr, getMonth());
                    break;
                case 'D':
                    bufPtr = print2Number(buf, bufPtr, getDay());
                    break;
                case 'h':
                    bufPtr = print2Number(buf, bufPtr, getHour());
                    break;
                case 'm':
                    bufPtr = print2Number(buf, bufPtr, getMinute());
                    break;
                case 's':
                    bufPtr = print2Number(buf, bufPtr, getSecond());
                    if (getFractionalSecond() != null) {
                        // Note: toPlainString() isn't available before Java 1.5
                        String frac = getFractionalSecond().toString();

                        int pos = frac.indexOf("E-");
                        if (pos >= 0) {
                            String zeros = frac.substring(pos + 2);
                            frac = frac.substring(0, pos);
                            pos = frac.indexOf(".");
                            if (pos >= 0) {
                                frac = frac.substring(0, pos) + frac.substring(pos + 1);
                            }
                            int count = Integer.parseInt(zeros);
                            if (count < 40) {
                                frac = "00000000000000000000000000000000000000000".substring(0, count - 1) + frac;
                            } else {
                                // do it the hard way
                                while (count > 1) {
                                    frac = "0" + frac;
                                    count--;
                                }
                            }
                            frac = "0." + frac;
                        }

                        // reallocate the buffer now so that it has enough space
                        char[] n = new char[buf.length + frac.length()];
                        System.arraycopy(buf, 0, n, 0, bufPtr);
                        buf = n;
                        //skip leading zero.
                        frac.getChars(1, frac.length(), buf, bufPtr);
                        bufPtr += frac.length() - 1;
                    }
                    break;
                case 'z':
                    int offset = getTimezone();
                    if (offset == 0) {
                        buf[bufPtr++] = 'Z';
                    } else if (offset != DatatypeConstants.FIELD_UNDEFINED) {
                        if (offset < 0) {
                            buf[bufPtr++] = '-';
                            offset *= -1;
                        } else {
                            buf[bufPtr++] = '+';
                        }
                        int min = offset / 60;
                        bufPtr = print2Number(buf, bufPtr, min / 60);
                        buf[bufPtr++] = ':';
                        bufPtr = print2Number(buf, bufPtr, min % 60);
                    }
                    break;
                default:
                    throw new InternalError();  // impossible
            }
        }

        return new String(buf, 0, bufPtr);
    }

    /**
     * Prints an int as two digits into the buffer.
     *
     * @param number Number to be printed. Must be positive.
     */
    private int print2Number(char[] out, int bufptr, int number) {
        out[bufptr++] = (char) ('0' + (number / 10));
        out[bufptr++] = (char) ('0' + (number % 10));
        return bufptr;
    }

    /**
     * Prints an int as four digits into the buffer.
     *
     * @param number Number to be printed. Must be positive.
     */
    private int print4Number(char[] out, int bufptr, int number) {
        out[bufptr + 3] = (char) ('0' + (number % 10));
        number /= 10;
        out[bufptr + 2] = (char) ('0' + (number % 10));
        number /= 10;
        out[bufptr + 1] = (char) ('0' + (number % 10));
        number /= 10;
        out[bufptr] = (char) ('0' + (number % 10));
        return bufptr + 4;
    }

    /**
     * Compute <code>value*signum</code> where value==null is treated as
     * value==0.
     *
     * @return non-null {@link BigInteger}.
     */
    static BigInteger sanitize(Number value, int signum) {
        if (signum == 0 || value == null) {
            return BigInteger.ZERO;
        }
        return (signum < 0) ? ((BigInteger) value).negate() : (BigInteger) value;
    }

    static BigDecimal sanitize(BigDecimal value, int signum) {
        if (signum == 0 || value == null) {
            return DECIMAL_ZERO;
        }
        if (signum > 0) {
            return value;
        }
        return value.negate();
    }

    /**
     * <p><code>reset()</code> is designed to allow the reuse of existing
     * <code>XMLGregorianCalendar</code>s thus saving resources associated
     * with the creation of new <code>XMLGregorianCalendar</code>s.</p>
     */
    @Override
    public void reset() {
        //PENDING : Implementation of reset method
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
            String result;
            int hour = this.getHour();
            int minute = this.getMinute();
            int second = this.getSecond();
            String nanoseconds = getNanoSeconds(this.getFractionalSecond());
            if (nanoseconds == null) {
                result = String.format("%02d:%02d:%02d", hour, minute, second);
            } else {
                result = String.format("%02d:%02d:%02d%s", hour, minute, second, nanoseconds);
            }
            if (zoneID != null) {
                if (isOffset(zoneID)) {
                    result += zoneID;
                } else {
                    result += "@" + zoneID;
                }
            }
            return result;

        } else if (kind == DatatypeConstants.DATETIME) {
            String result;
            int year = this.getYear();
            int month = this.getMonth();
            int day = this.getDay();
            int hour = this.getHour();
            int minute = this.getMinute();
            int second = this.getSecond();
            String nanoseconds = getNanoSeconds(this.getFractionalSecond());
            if (nanoseconds == null) {
                result = String.format("%d-%02d-%02dT%02d:%02d:%02d", year, month, day, hour, minute, second);
            } else {
                result = String.format("%d-%02d-%02dT%02d:%02d:%02d%s", year, month, day, hour, minute, second, nanoseconds);
            }
            if (zoneID != null) {
                if (isOffset(zoneID)) {
                    result += zoneID;
                } else {
                    result += "@" + zoneID;
                }
            }
            return result;
        } else {
            return super.toString();
        }
    }

    private boolean isOffset(String zoneID) {
        return zoneID != null &&
                ( zoneID.equals("Z") || zoneID.startsWith("+") || zoneID.startsWith("-") )
                ;
    }

    private String getNanoSeconds(BigDecimal fractionalSeconds) {
        if (fractionalSeconds == null) {
            return null;
        }
        return fractionalSeconds.stripTrailingZeros().toPlainString().substring(1);
    }

    private String errorMessage(String text, Object[] arguments) {
        StringBuilder builder = new StringBuilder();
        builder.append(text);
        for(Object arg: arguments) {
            builder.append(arg.toString());
        }
        return builder.toString();
    }
}
