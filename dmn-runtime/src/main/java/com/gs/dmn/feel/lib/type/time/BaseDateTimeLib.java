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

import com.gs.dmn.runtime.DMNRuntimeException;
import org.apache.commons.lang3.StringUtils;


import javax.xml.datatype.DatatypeConstants;
import java.text.DateFormatSymbols;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.time.format.SignStyle;
import java.util.Locale;
import java.util.regex.Pattern;

import static com.gs.dmn.feel.lib.type.BaseType.UTC;
import static java.time.temporal.ChronoField.*;

public abstract class BaseDateTimeLib {
    protected static final Pattern BEGIN_YEAR = Pattern.compile("^-?(([1-9]\\d\\d\\d+)|(0\\d\\d\\d))-"); // FEEL spec, "specified by XML Schema Part 2 Datatypes", hence: yearFrag ::= '-'? (([1-9] digit digit digit+)) | ('0' digit digit digit))
    protected static final Pattern ZONE_OFFSET = Pattern.compile("[+-]\\d\\d:\\d\\d");

    public static final DateTimeFormatter FEEL_DATE;
    public static final DateTimeFormatter FEEL_TIME;
    public static final DateTimeFormatter FEEL_DATE_TIME;
    public static final DateTimeFormatter REGION_DATETIME_FORMATTER;

    protected static final DateFormatSymbols DATE_FORMAT_SYMBOLS = new DateFormatSymbols();
    protected static final String[] DAY_NAMES = DATE_FORMAT_SYMBOLS.getWeekdays();
    protected static final String[] MONTH_NAMES = DATE_FORMAT_SYMBOLS.getMonths();

    static {
        FEEL_DATE = new DateTimeFormatterBuilder()
                .appendValue(YEAR, 4, 10, SignStyle.NORMAL)
                .appendLiteral('-')
                .appendValue(MONTH_OF_YEAR, 2)
                .appendLiteral('-')
                .appendValue(DAY_OF_MONTH, 2)
                .toFormatter()
                .withResolverStyle(ResolverStyle.STRICT);

        FEEL_TIME = new DateTimeFormatterBuilder().parseCaseInsensitive()
                .append(DateTimeFormatter.ISO_LOCAL_TIME)
                .optionalStart()
                .appendOffsetId()
                .optionalEnd()
                .optionalStart()
                .appendLiteral("@")
                .appendZoneRegionId()
                .optionalEnd()
                .toFormatter()
                .withResolverStyle(ResolverStyle.STRICT);


        FEEL_DATE_TIME = new DateTimeFormatterBuilder().parseCaseInsensitive()
                .append(FEEL_DATE)
                .appendLiteral('T')
                .append(FEEL_TIME)
                .toFormatter();

        REGION_DATETIME_FORMATTER = new DateTimeFormatterBuilder().parseCaseInsensitive()
                .append(FEEL_DATE)
                .appendLiteral('T')
                .append(DateTimeFormatter.ISO_LOCAL_TIME)
                .appendLiteral("@")
                .appendZoneRegionId()
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
        if (literal.contains("Z") || literal.contains("z")) {
            return true;
        }

        // Check for offset ID +/-HH:MM
        return ZONE_OFFSET.matcher(literal).find();
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
        if (0 <= timeZoneStartIndex) {
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
        if (this.hasZoneOffset(literal) && this.hasZoneId(literal)) {
            throw new DMNRuntimeException("Time literal '%s' has both a zone offset and zone id".formatted(literal));
        }

        if (hasZoneId(literal)) {
            if (literal.contains("@")) {
                int zoneIndex = literal.indexOf("@");
                String zoneId = literal.substring(literal.indexOf('@') + 1);
                ZoneId zone = ZoneId.of(zoneId);
                LocalTime localTime = LocalTime.parse(literal.substring(0, zoneIndex), FEEL_TIME);
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
            throw new DMNRuntimeException("Illegal date literal '%s'".formatted(literal));
        }
        if (!BEGIN_YEAR.matcher(literal).find()) {
            throw new DMNRuntimeException("Illegal year in '%s'".formatted(literal));
        }
        try {
            return LocalDate.from(FEEL_DATE.parse(literal));
        } catch (Exception e) {
            throw new DMNRuntimeException("Cannot convert '%s' to date".formatted(literal));
        }
    }

    protected ZonedDateTime makeZonedDateTime(String literal) {
        if (StringUtils.isBlank(literal)) {
            return null;
        }
        if (this.hasZoneOffset(literal) && this.hasZoneId(literal)) {
            throw new DMNRuntimeException("Time literal '%s' has both a zone offset and zone id".formatted(literal));
        }

        literal = fixDateTimeFormat(literal);
        if (hasZoneId(literal)) {
            return ZonedDateTime.parse(literal, FEEL_DATE_TIME);
        } else if (hasZoneOffset(literal)) {
            return ZonedDateTime.parse(literal, FEEL_DATE_TIME);
        } else if (hasTime(literal)) {
            return ZonedDateTime.parse(literal + 'Z', FEEL_DATE_TIME);
        } else {
            LocalDate localDate = LocalDate.parse(literal, FEEL_DATE);
            return localDate.atStartOfDay(UTC);
        }
    }
}
