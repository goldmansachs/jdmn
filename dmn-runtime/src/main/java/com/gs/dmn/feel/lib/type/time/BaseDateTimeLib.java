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
package com.gs.dmn.feel.lib.type.time;

import org.apache.commons.lang3.StringUtils;

import javax.xml.datatype.DatatypeConstants;
import java.text.DateFormatSymbols;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;
import java.util.Locale;
import java.util.regex.Pattern;

import static com.gs.dmn.feel.lib.type.BaseType.UTC;
import static java.time.temporal.ChronoField.*;

public abstract class BaseDateTimeLib {
    protected static final Pattern BEGIN_YEAR = Pattern.compile("^-?(([1-9]\\d\\d\\d+)|(0\\d\\d\\d))-"); // FEEL spec, "specified by XML Schema Part 2 Datatypes", hence: yearFrag ::= '-'? (([1-9] digit digit digit+)) | ('0' digit digit digit))

    public static final DateTimeFormatter FEEL_DATE_FORMAT;
    public static final DateTimeFormatter FEEL_TIME_FORMAT;
    public static final DateTimeFormatter FEEL_DATE_TIME_FORMAT;

    protected static final DateTimeFormatter FEEL_DATE_TIME;
    protected static final DateTimeFormatter FEEL_DATE;
    protected static final DateTimeFormatter FEEL_TIME;

    protected static final DateFormatSymbols DATE_FORMAT_SYMBOLS = new DateFormatSymbols();
    protected static final String[] DAY_NAMES = DATE_FORMAT_SYMBOLS.getWeekdays();
    protected static final String[] MONTH_NAMES = DATE_FORMAT_SYMBOLS.getMonths();

    static {
        FEEL_DATE_FORMAT = new DateTimeFormatterBuilder()
                .appendValue(YEAR, 4, 10, SignStyle.NORMAL)
                .appendLiteral('-')
                .appendValue(MONTH_OF_YEAR, 2)
                .appendLiteral('-')
                .appendValue(DAY_OF_MONTH, 2)
                .toFormatter(Locale.getDefault(Locale.Category.FORMAT));

        FEEL_DATE = new DateTimeFormatterBuilder()
                .appendValue(YEAR, 4, 9, SignStyle.NORMAL)
                .appendLiteral('-')
                .appendValue(MONTH_OF_YEAR, 2)
                .appendLiteral('-')
                .appendValue(DAY_OF_MONTH, 2)
                .toFormatter();

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
                .parseCaseInsensitive()
                .appendZoneRegionId()
                .optionalEnd()
                .toFormatter(Locale.getDefault(Locale.Category.FORMAT));

        FEEL_TIME = new DateTimeFormatterBuilder().parseCaseInsensitive()
                .append(DateTimeFormatter.ISO_LOCAL_TIME)
                .optionalStart()
                .appendLiteral("@")
                .appendZoneRegionId()
                .optionalEnd()
                .optionalStart()
                .appendOffsetId()
                .optionalEnd()
                .toFormatter();

        FEEL_DATE_TIME_FORMAT = new DateTimeFormatterBuilder()
                .append(FEEL_DATE_FORMAT)
                .appendLiteral('T')
                .append(FEEL_TIME_FORMAT)
                .toFormatter(Locale.getDefault(Locale.Category.FORMAT));

        FEEL_DATE_TIME = new DateTimeFormatterBuilder().parseCaseInsensitive()
                .append(FEEL_DATE)
                .appendLiteral('T')
                .append(FEEL_TIME)
                .toFormatter();
    }

    protected boolean isTime(String literal) {
        if (literal == null) {
            return false;
        }
        return literal.length() > 3 && literal.charAt(2) == ':';
    }

    protected boolean hasTime(String literal) {
        if (literal == null) {
            return false;
        }
        return literal.indexOf('T') != -1;
    }

    protected boolean hasZoneId(String literal) {
        if (literal == null) {
            return false;
        }

        return literal.endsWith("]") || literal.contains("@");
    }

    protected boolean hasZoneOffset(String literal) {
        if (literal == null) {
            return false;
        }

        // Check for offset ID Z
        if (literal.endsWith("Z") || literal.endsWith("z")) {
            return true;
        }

        // Check for offset ID +/-HH:MM
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

    protected boolean timeHasOffset(String literal) {
        return literal.length() > 8 && (literal.charAt(8) == '+' || literal.charAt(8) == '-');
    }

    protected boolean invalidYear(String literal) {
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

    protected boolean isValidDate(long year, long month, long day) {
        return isValidYear(year) && isValidMonth(month) && isValidDay(day);
    }

    protected boolean isValidTime(int hour, int minute, int second, Integer secondsOffset) {
        return isValidHour(hour) && isValidMinute(minute) && isValidSecond(second) && isValidOffset(secondsOffset);
    }

    protected boolean isValidDateTime(long year, long month, long day, int hour, int minute, int second, Integer secondsOffset) {
        return isValidDate(year, month, day) && isValidTime(hour, minute, second, secondsOffset);
    }

    private boolean isValidYear(long year) {
        return -999999999L <= year && year <= 999999999L;
    }

    private boolean isValidMonth(long month) {
        return 1 <= month && month <= 12;
    }

    private boolean isValidDay(long day) {
        return 1 <= day && day <= 31;
    }

    private boolean isValidHour(long hour) {
        return 0 <= hour && hour <= 23;
    }

    private boolean isValidMinute(long minute) {
        return 0 <= minute && minute <= 59;
    }

    private boolean isValidSecond(long second) {
        return 0 <= second && second <= 59;
    }

    private boolean isValidOffset(Integer secondsOffset) {
        if (secondsOffset == null || isUndefined(secondsOffset)) {
            return true;
        }
        return -18 * 3600 <= secondsOffset && secondsOffset < 18 * 3600;
    }

    protected boolean isUndefined(long value) {
        return value == DatatypeConstants.FIELD_UNDEFINED;
    }

    // Fix the format 2016-08-01T11:00:00.000+0000 to 2016-08-01T11:00:00.000+00:00
    // and T11:00:00.000+0000 to 11:00:00.000+00:00
    protected String fixDateTimeFormat(String literal) {
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

    protected OffsetTime makeOffsetTime(String literal) {
        literal = fixDateTimeFormat(literal);
        if (!isTime(literal)) {
            return null;
        }
        if (hasZoneId(literal) && timeHasOffset(literal)) {
            return null;
        }

        if (hasZoneId(literal)) {
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
        } else if (hasZoneOffset(literal)) {
            return OffsetTime.parse(literal);
        } else {
            return OffsetTime.parse(literal + "Z");
        }
    }

    protected LocalDate makeLocalDate(String literal) {
        if (StringUtils.isBlank(literal)) {
            return null;
        }
        // Check time
        if (hasTime(literal)) {
            return null;
        }
        // Check year
        if (invalidYear(literal)) {
            return null;
        }

        return LocalDate.parse(literal, FEEL_DATE_FORMAT);
    }

    protected ZonedDateTime makeZonedDateTime(String literal) {
        if (StringUtils.isBlank(literal)) {
            return null;
        }

        literal = fixDateTimeFormat(literal);
        if (hasZoneId(literal)) {
            return ZonedDateTime.parse(literal, FEEL_DATE_TIME_FORMAT);
        } else if (hasZoneOffset(literal)) {
            return ZonedDateTime.parse(literal, FEEL_DATE_TIME_FORMAT);
        } else if (hasTime(literal)) {
            return ZonedDateTime.parse(literal + 'Z', FEEL_DATE_TIME_FORMAT);
        } else {
            LocalDate localDate = LocalDate.parse(literal, FEEL_DATE_FORMAT);
            return localDate.atStartOfDay(UTC);
        }
    }
}
