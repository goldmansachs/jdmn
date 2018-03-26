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
package com.gs.dmn.feel.lib;

import org.apache.commons.lang3.StringUtils;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;
import java.util.Locale;

import static java.time.temporal.ChronoField.*;

public class DateTimeUtil {
    public static final LocalDate EPOCH = LocalDate.of(1970, 1, 1);
    public static final ZoneId UTC = ZoneId.of("UTC");
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.######");

    public static final DateTimeFormatter FEEL_DATE_FORMAT;
    public static final DateTimeFormatter FEEL_TIME_FORMAT;
    public static final DateTimeFormatter FEEL_DATE_TIME_FORMAT;

    static {
        FEEL_DATE_FORMAT = new DateTimeFormatterBuilder()
                .appendValue(YEAR, 4, 10, SignStyle.NORMAL)
                .appendLiteral('-')
                .appendValue(MONTH_OF_YEAR, 2)
                .appendLiteral('-')
                .appendValue(DAY_OF_MONTH, 2)
                .toFormatter(Locale.getDefault(Locale.Category.FORMAT));

        FEEL_TIME_FORMAT = new DateTimeFormatterBuilder()
                .appendValue(HOUR_OF_DAY, 2)
                .appendLiteral(':')
                .appendValue(MINUTE_OF_HOUR, 2)
                .optionalStart()
                .appendLiteral(':')
                .appendValue(SECOND_OF_MINUTE, 2)
                .optionalStart()
                .appendFraction(NANO_OF_SECOND, 0, 9, true)
                .optionalEnd()
                .optionalEnd()
                .optionalStart()
                .appendOffsetId()
                .optionalEnd()
                .optionalStart()
                .appendLiteral('@')
                .parseCaseSensitive()
                .appendZoneRegionId()
                .optionalEnd()
                .toFormatter(Locale.getDefault(Locale.Category.FORMAT));

        FEEL_DATE_TIME_FORMAT = new DateTimeFormatterBuilder()
                .append(FEEL_DATE_FORMAT)
                .appendLiteral('T')
                .append(FEEL_TIME_FORMAT)
                .toFormatter(Locale.getDefault(Locale.Category.FORMAT));
    }

    // Fix the format 2016-08-01T11:00:00.000+0000 to 2016-08-01T11:00:00.000+00:00
    // and T11:00:00.000+0000 to 11:00:00.000+00:00
    public static String fixDateTimeFormat(String literal) {
        if (literal == null) {
            return null;
        }
        if (literal.startsWith("T")) {
            literal = literal.substring(1);
        }
        int timeZoneStartIndex = literal.length() - 5;
        if (0 <= timeZoneStartIndex && timeZoneStartIndex < literal.length()) {
            char timeZoneStart = literal.charAt(timeZoneStartIndex);
            if (timeZoneStart == '+' || timeZoneStart == '-') {
                String timeZoneOffset = literal.substring(timeZoneStartIndex + 1);
                literal = literal.substring(0, timeZoneStartIndex + 1) + timeZoneOffset.substring(0, 2) + ":" + timeZoneOffset.substring(2);
            }
        }
        return literal;
    }

    public static boolean isTime(String literal) {
        if (literal == null) {
            return false;
        }
        return literal.length() > 3 && literal.charAt(2) == ':';
    }

    public static boolean hasTime(String literal) {
        if (literal == null) {
            return false;
        }
        return literal.indexOf('T') != -1;
    }

    public static boolean hasZone(String literal) {
        if (literal == null) {
            return false;
        }

        return literal.endsWith("Z") || literal.endsWith("]") || literal.contains("@");
    }

    public static boolean hasOffset(String literal) {
        if (literal == null) {
            return false;
        }

        // Remove sign
        if (literal.startsWith("-") || literal.startsWith("+")) {
            literal = literal.substring(1);
        }
        // Last index before zoneId
        int n = literal.length();
        int index = literal.indexOf('@');
        if (index != -1) {
            n = index;
        }
        index = literal.indexOf('[');
        if (index != -1) {
            n = index;
        }
        int offsetStartIndex = n - 6;
        if (0 <= offsetStartIndex && offsetStartIndex < literal.length()) {
            char offsetStart = literal.charAt(offsetStartIndex);
            if (offsetStart == '+' || offsetStart == '-') {
                String timeZoneOffset = literal.substring(offsetStartIndex + 1);
                return Character.isDigit(timeZoneOffset.charAt(0))
                        && Character.isDigit(timeZoneOffset.charAt(1))
                        && timeZoneOffset.charAt(2) == ':'
                        && Character.isDigit(timeZoneOffset.charAt(3))
                        && Character.isDigit(timeZoneOffset.charAt(4));
            }
        }
        return false;
    }

    public static boolean timeHasOffset(String literal) {
        return literal.length() > 8 && (literal.charAt(8) == '+' || literal.charAt(8) == '-');
    }

    public static LocalDate makeLocalDate(String literal) {
        if (StringUtils.isBlank(literal)) {
            return null;
        }
        // Check time
        if (DateTimeUtil.hasTime(literal)) {
            return null;
        }
        // Check year
        if (DateTimeUtil.invalidYear(literal)) {
            return null;
        }
        return LocalDate.parse(literal, FEEL_DATE_FORMAT);
    }

    public static OffsetTime makeOffsetTime(String literal) {
        literal = DateTimeUtil.fixDateTimeFormat(literal);
        if (!DateTimeUtil.isTime(literal)) {
            return null;
        }
        if (DateTimeUtil.hasZone(literal) && DateTimeUtil.timeHasOffset(literal)) {
            return null;
        }
        if (DateTimeUtil.hasZone(literal)) {
            if (literal.contains("@")) {
                int zoneIndex = literal.indexOf("@");
                String zoneId = literal.substring(literal.indexOf('@') + 1);
                ZoneId zone = ZoneId.of(zoneId);
                LocalTime localTime = LocalTime.parse(literal.substring(0, zoneIndex), FEEL_TIME_FORMAT);
                ZonedDateTime zdt = ZonedDateTime.of(LocalDate.now(zone), localTime, zone);
                ZoneOffset offset = zone.getRules().getStandardOffset(zdt.toInstant());
                return localTime.atOffset(offset);
            } else {
                return OffsetTime.parse(literal);
            }
        } else if (DateTimeUtil.hasOffset(literal)) {
            return OffsetTime.parse(literal);
        } else {
            return OffsetTime.parse(literal + "Z");
        }
    }

    public static ZonedDateTime makeDateTime(String literal) {
        if (StringUtils.isBlank(literal)) {
            return null;
        }
        literal = fixDateTimeFormat(literal);
        if (DateTimeUtil.hasZone(literal)) {
            return ZonedDateTime.parse(literal, DateTimeUtil.FEEL_DATE_TIME_FORMAT);
        } else if (DateTimeUtil.hasOffset(literal)) {
            return ZonedDateTime.parse(literal, DateTimeUtil.FEEL_DATE_TIME_FORMAT);
        } else if (DateTimeUtil.hasTime(literal)) {
            return ZonedDateTime.parse(literal + 'Z', DateTimeUtil.FEEL_DATE_TIME_FORMAT);
        } else {
            LocalDate localDate = LocalDate.parse(literal, DateTimeUtil.FEEL_DATE_FORMAT);
            return localDate.atStartOfDay(DateTimeUtil.UTC);
        }
    }

    public static Duration toYearsMonthDuration(DatatypeFactory datatypeFactory, LocalDate date1, LocalDate date2) {
        Period between = Period.between(date2, date1);
        int years = between.getYears();
        int months = between.getMonths();
        if (between.isNegative()) {
            years = - years;
            months = - months;
        }
        return datatypeFactory.newDurationYearMonth(!between.isNegative(), years, months);
    }

    public static String string(Object from) {
        if (from == null) {
            return "null";
        } else if (from instanceof Double) {
            return DECIMAL_FORMAT.format(from);
        } else if (from instanceof BigDecimal) {
            return ((BigDecimal) from).toPlainString();
        } else if (from instanceof LocalDate) {
            return ((LocalDate) from).format(DateTimeUtil.FEEL_DATE_FORMAT);
        } else if (from instanceof OffsetTime) {
            return ((OffsetTime) from).format(DateTimeUtil.FEEL_TIME_FORMAT);
        } else if (from instanceof ZonedDateTime) {
            return ((ZonedDateTime) from).format(DateTimeUtil.FEEL_DATE_TIME_FORMAT);
        } else {
            return from.toString();
        }
    }

    public static boolean invalidYear(String literal) {
        if (StringUtils.isBlank(literal)) {
            return true;
        }
        boolean hasSign = literal.charAt(0) == '-';
        int i = hasSign ? 1 : 0;
        boolean startsWithZero = literal.charAt(i) == '0';
        while (Character.isDigit(literal.charAt(i))) {
            i++;
        }
        return i > 4 && startsWithZero;
    }

    public static boolean isValidDate(long year, long month, long day) {
        return isValidYear(year) && isValidMonth(month) && isValidDay(day);
    }

    public static boolean isValidTime(int hour, int minute, int second) {
        return isValidHour(hour) && isValidMinute(minute) && isValidSecond(second);
    }

    public static boolean isValidDate(XMLGregorianCalendar calendar) {
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

    public static boolean isValidTime(XMLGregorianCalendar calendar) {
        if (calendar == null) {
            return false;
        }

        return
                isValidTime(calendar.getHour(), calendar.getMinute(), calendar.getSecond())
                        && isUndefined(calendar.getYear())
                        && isUndefined(calendar.getMonth())
                        && isUndefined(calendar.getDay())
                ;
    }

    public static boolean isValidDateTime(XMLGregorianCalendar calendar) {
        if (calendar == null) {
            return false;
        }

        long year = calendar.getYear();
        BigInteger eonAndYear = calendar.getEonAndYear();
        if (eonAndYear != null) {
            year = eonAndYear.intValue();
        }
        return isValidDate(year, calendar.getMonth(), calendar.getDay())
                || isValidDate(year, calendar.getMonth(), calendar.getDay()) && isValidTime(calendar.getHour(), calendar.getMinute(), calendar.getSecond())
                ;
    }

    private static boolean isValidYear(long year) {
        return -999999999L <= year && year <= 999999999L;
    }

    private static boolean isValidMonth(long month) {
        return 1 <= month && month <= 12;
    }

    private static boolean isValidDay(long day) {
        return 1 <= day && day <= 31;
    }

    private static boolean isValidHour(long hour) {
        return 0 <= hour && hour <= 23;
    }

    private static boolean isValidMinute(long minute) {
        return 0 <= minute && minute <= 59;
    }

    private static boolean isValidSecond(long second) {
        return 0 <= second && second <= 59;
    }

    private static boolean isUndefined(long value) {
        return value == DatatypeConstants.FIELD_UNDEFINED;
    }
}
