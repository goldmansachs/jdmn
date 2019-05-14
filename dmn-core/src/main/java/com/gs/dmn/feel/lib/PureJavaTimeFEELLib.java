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

import com.gs.dmn.feel.lib.type.context.DefaultContextType;
import com.gs.dmn.feel.lib.type.list.DefaultListType;
import com.gs.dmn.feel.lib.type.logic.DefaultBooleanType;
import com.gs.dmn.feel.lib.type.numeric.DefaultNumericType;
import com.gs.dmn.feel.lib.type.string.DefaultStringType;
import com.gs.dmn.feel.lib.type.time.pure.*;
import com.gs.dmn.runtime.LambdaExpression;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.*;
import java.time.temporal.*;
import java.util.*;

public class PureJavaTimeFEELLib extends BaseFEELLib<BigDecimal, LocalDate, Temporal, Temporal, TemporalAmount> implements StandardFEELLib<BigDecimal, LocalDate, Temporal, Temporal, TemporalAmount> {
    public PureJavaTimeFEELLib() {
        super(new DefaultNumericType(LOGGER),
                new DefaultBooleanType(LOGGER),
                new DefaultStringType(LOGGER),
                new LocalDateType(LOGGER),
                new TemporalTimeType(LOGGER),
                new TemporalDateTimeType(LOGGER),
                new TemporalAmountDurationType(LOGGER),
                new DefaultListType(LOGGER),
                new DefaultContextType(LOGGER)
        );
    }

    //
    // Conversion functions
    //

    @Override
    public BigDecimal number(String literal) {
        if (StringUtils.isBlank(literal)) {
            return null;
        }

        try {
            return new BigDecimal(literal, DefaultNumericType.MATH_CONTEXT);
        } catch (Exception e) {
            String message = String.format("number(%s)", literal);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal number(String from, String groupingSeparator, String decimalSeparator) {
        if (StringUtils.isBlank(from)) {
            return null;
        }
        if (! (" ".equals(groupingSeparator) || ".".equals(groupingSeparator) || ",".equals(groupingSeparator) || null == groupingSeparator)) {
            return null;
        }
        if (! (".".equals(decimalSeparator) || ",".equals(decimalSeparator) || null == decimalSeparator)) {
            return null;
        }
        if (groupingSeparator != null && groupingSeparator.equals(decimalSeparator)) {
            return null;
        }

        try {
            if (groupingSeparator != null) {
                if (groupingSeparator.equals(".")) {
                    groupingSeparator = "\\" + groupingSeparator;
                }
                from = from.replaceAll(groupingSeparator, "");
            }
            if (decimalSeparator != null && !decimalSeparator.equals(".")) {
                from = from.replaceAll(decimalSeparator, ".");
            }
            return number(from);
        } catch (Exception e) {
            String message = String.format("number(%s, %s, %s)", from, groupingSeparator, decimalSeparator);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String string(Object from) {
        return DateTimeUtil.string(from);
    }

    @Override
    public LocalDate date(String literal) {
        try {
            if (literal == null) {
                return null;
            }
            return LocalDate.parse(literal, DateTimeUtil.FEEL_DATE_FORMAT);
        } catch (Exception e) {
            String message = String.format("date(%s)", literal);
            logError(message, e);
            return null;
        }
    }

    @Override
    public LocalDate date(BigDecimal year, BigDecimal month, BigDecimal day) {
        return LocalDate.of(year.intValue(), month.intValue(), day.intValue());
    }

    @Override
    public LocalDate date(Temporal from) {
        if (from == null) {
            return null;
        }

        try {
            if (from instanceof LocalDate) {
                return (LocalDate) from;
            } else if (from instanceof LocalDateTime) {
                return ((LocalDateTime) from).toLocalDate();
            } else if (from instanceof OffsetDateTime) {
                return ((OffsetDateTime) from).toLocalDate();
            } else if (from instanceof ZonedDateTime) {
                return ((ZonedDateTime) from).toLocalDate();
            }
            throw new IllegalArgumentException(String.format("Cannot convert '%s' to date", from.getClass().getSimpleName()));
        } catch (Exception e) {
            String message = String.format("date(%s)", from);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Temporal time(String literal) {
        if (literal == null) {
            return null;
        }
        try {
            literal = DateTimeUtil.fixDateTimeFormat(literal);
            TemporalAccessor parsed = TimeUtil.time(literal);
            if (parsed.query(TemporalQueries.zoneId()) != null) {
                LocalTime localTime = parsed.query(LocalTime::from);
                ZoneId zoneId = parsed.query(TemporalQueries.zoneId());
                int millisOffset = TimeZone.getTimeZone(zoneId).getRawOffset();
                ZoneOffset zoneOffset = ZoneOffset.ofTotalSeconds(millisOffset / 1000);
                OffsetTime asOffSetTime = OffsetTime.of(localTime, zoneOffset);
                return asOffSetTime;
            } else {
                return (Temporal) parsed;
            }
        } catch (Exception e) {
            String message = String.format("time(%s)", literal);
            logError(message, e);
            return null;
        }
    }

    @Override
    public OffsetTime time(BigDecimal hour, BigDecimal minute, BigDecimal second, TemporalAmount offset) {
        if (hour == null || minute == null || second == null || offset == null) {
            return null;
        }

        try {
            long hours = offset.get(ChronoUnit.HOURS);
            long minutes = offset.get(ChronoUnit.MINUTES);
            ZoneOffset zoneOffset = ZoneOffset.ofHoursMinutes((int)hours, (int)minutes);
            return OffsetTime.of(hour.intValue(), minute.intValue(), second.intValue(), 0, zoneOffset);
        } catch (Exception e) {
            String message = String.format("time(%s, %s, %s, %s)", hour, minute, second, offset);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Temporal time(Temporal from) {
        if (from == null) {
            return null;
        }

        try {
            if (from instanceof LocalTime) {
                return from;
            } else if (from instanceof OffsetTime) {
                return from;
            } else if (from instanceof LocalDateTime) {
                return ((LocalDateTime) from).toLocalTime();
            } else if (from instanceof OffsetDateTime) {
                return ((OffsetDateTime) from).toOffsetTime();
            } else if (from instanceof ZonedDateTime) {
                return ((ZonedDateTime) from).toOffsetDateTime().toOffsetTime();
            }
            throw new IllegalArgumentException(String.format("Cannot convert '%s' to time", from.getClass().getSimpleName()));
        } catch (Exception e) {
            String message = String.format("time(%s)", from);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Temporal dateAndTime(String literal) {
        if (StringUtils.isBlank(literal)) {
            return null;
        }

        try {
            literal = DateTimeUtil.fixDateTimeFormat(literal);
            return (Temporal) DateAndTimeUtil.dateAndTime(literal);
        } catch (Exception e) {
            String message = String.format("dateAndTime(%s)", literal);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Temporal dateAndTime(LocalDate date, Temporal time) {
        if (date == null || time == null) {
            return null;
        }

        try {
            if (time instanceof LocalTime) {
                return LocalDateTime.of(date, (LocalTime) time);
            } else if (time instanceof OffsetTime) {
                return OffsetDateTime.of(date, ((OffsetTime) time).toLocalTime(), ((OffsetTime) time).getOffset());
            }
            throw new IllegalArgumentException(String.format("Cannot convert '%s' and '%s' to date and time", date, time));
        } catch (Exception e) {
            String message = String.format("dateAndTime(%s, %s)", date, time);
            logError(message, e);
            return null;
        }
    }

    @Override
    public TemporalAmount duration(String from) {
        if (StringUtils.isBlank(from)) {
            return null;
        }

        try {
            TemporalAmount ta = null;
            try {
                ta = Duration.parse(from);
                return ta;
            } catch (Exception e) {
            }
            ta = Period.parse(from);
            return ta;
        } catch (Exception e) {
            String message = String.format("duration(%s)", from);
            logError(message, e);
            return null;
        }
    }

    private TemporalAmount duration(long milliseconds) {
        try {
            return Duration.ofSeconds(milliseconds / 1000, (milliseconds % 1000) * 1000);
        } catch (Exception e) {
            String message = String.format("duration(%d)", milliseconds);
            logError(message, e);
            return null;
        }
    }

    @Override
    public TemporalAmount yearsAndMonthsDuration(Temporal from, Temporal to) {
        if (from == null || to == null) {
            return null;
        }

        try {
            return Duration.between(from, to);
        } catch (Exception e) {
            String message = String.format("yearsAndMonthsDuration(%s, %s)", from, to);
            logError(message, e);
            return null;
        }
    }

    @Override
    public LocalDate toDate(Object object) {
        if (object instanceof Temporal) {
            return date((Temporal) object);
        }
        return null;
    }

    @Override
    public Temporal toTime(Object object) {
        if (object instanceof Temporal) {
            return time((Temporal) object);
        }
        return null;
    }

    //
    // Numeric functions
    //
    @Override
    public BigDecimal decimal(BigDecimal n, BigDecimal scale) {
        try {
            return BigDecimalUtil.decimal(n, scale);
        } catch (Exception e) {
            String message = String.format("decimal(%s, %s)", n, scale);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal floor(BigDecimal number) {
        try {
            return BigDecimalUtil.floor(number);
        } catch (Exception e) {
            String message = String.format("fllor(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal ceiling(BigDecimal number) {
        try {
            return BigDecimalUtil.ceiling(number);
        } catch (Exception e) {
            String message = String.format("ceiling(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal abs(BigDecimal number) {
        try {
            return BigDecimalUtil.abs(number);
        } catch (Exception e) {
            String message = String.format("abs(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal intModulo(BigDecimal dividend, BigDecimal divisor) {
        try {
            return BigDecimalUtil.intModulo(dividend, divisor);
        } catch (Exception e) {
            String message = String.format("modulo(%s, %s)", dividend, divisor);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal modulo(BigDecimal dividend, BigDecimal divisor) {
        try {
            return BigDecimalUtil.modulo(dividend, divisor);
        } catch (Exception e) {
            String message = String.format("modulo(%s, %s)", dividend, divisor);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal sqrt(BigDecimal number) {
        try {
            return BigDecimalUtil.sqrt(number);
        } catch (Exception e) {
            String message = String.format("sqrt(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal log(BigDecimal number) {
        try {
            return BigDecimalUtil.log(number);
        } catch (Exception e) {
            String message = String.format("log(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal exp(BigDecimal number) {
        try {
            return BigDecimalUtil.exp(number);
        } catch (Exception e) {
            String message = String.format("exp(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean odd(BigDecimal number) {
        try {
            return BigDecimalUtil.odd(number);
        } catch (Exception e) {
            String message = String.format("odd(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean even(BigDecimal number) {
        try {
            return BigDecimalUtil.even(number);
        } catch (Exception e) {
            String message = String.format("odd(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal min(Object... args) {
        if (args == null || args.length < 1) {
            return null;
        }

        try {
            return min(Arrays.asList(args));
        } catch (Exception e) {
            String message = String.format("min(%s)", args);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal max(Object... args) {
        if (args == null || args.length < 1) {
            return null;
        }

        try {
            return max(Arrays.asList(args));
        } catch (Exception e) {
            String message = String.format("max(%s)", args);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal sum(Object... args) {
        if (args == null || args.length < 1) {
            return null;
        }

        try {
            return sum(Arrays.asList(args));
        } catch (Exception e) {
            String message = String.format("sum(%s)", args);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal mean(List list) {
        try {
            return BigDecimalUtil.mean(list);
        } catch (Exception e) {
            String message = String.format("mean(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal mean(Object... args) {
        if (args == null || args.length < 1) {
            return null;
        }

        try {
            return mean(Arrays.asList(args));
        } catch (Exception e) {
            String message = String.format("mean(%s)", args);
            logError(message, e);
            return null;
        }
    }

    //
    // String functions
    //
    @Override
    public Boolean contains(String string, String match) {
        return StringUtil.contains(string, match);
    }

    @Override
    public Boolean startsWith(String string, String match) {
        return StringUtil.startsWith(string, match);
    }

    @Override
    public Boolean endsWith(String string, String match) {
        return StringUtil.endsWith(string, match);
    }

    @Override
    public BigDecimal stringLength(String string) {
        return string == null ? null : BigDecimal.valueOf(StringUtil.stringLength(string));
    }

    @Override
    public String substring(String string, BigDecimal startPosition) {
        return StringUtil.substring(string, startPosition);
    }

    @Override
    public String substring(String string, BigDecimal startPosition, BigDecimal length) {
        return StringUtil.substring(string, startPosition, length);
    }

    @Override
    public String upperCase(String string) {
        return StringUtil.upperCase(string);
    }

    @Override
    public String lowerCase(String string) {
        return StringUtil.lowerCase(string);
    }

    @Override
    public String substringBefore(String string, String match) {
        return StringUtil.substringBefore(string, match);
    }

    @Override
    public String substringAfter(String string, String match) {
        return StringUtil.substringAfter(string, match);
    }

    @Override
    public String replace(String input, String pattern, String replacement) {
        return replace(input, pattern, replacement, "");
    }

    @Override
    public String replace(String input, String pattern, String replacement, String flags) {
        try {
            return StringUtil.replace(input, pattern, replacement, flags);
        } catch (Exception e) {
            String message = String.format("replace(%s, %s, %s, %s)", input, pattern, replacement, flags);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean matches(String input, String pattern) {
        return matches(input, pattern, "");
    }

    @Override
    public Boolean matches(String input, String pattern, String flags) {
        try {
            return StringUtil.matches(input, pattern, flags);
        } catch (Exception e) {
            String message = String.format("matches(%s, %s, %s)", input, pattern, flags);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List split(String string, String delimiter) {
        try {
            return StringUtil.split(string, delimiter);
        } catch (Exception e) {
            String message = String.format("split(%s, %s)", string, delimiter);
            logError(message, e);
            return null;
        }
    }

    //
    // Boolean functions
    //
    @Override
    public Boolean and(List list) {
        return all(list);
    }

    @Override
    public Boolean and(Object... args) {
        return all(args);
    }

    @Override
    public Boolean all(List list) {
        if (list == null) {
            return null;
        }

        try {
            if (list.stream().anyMatch(b -> b == Boolean.FALSE)) {
                return false;
            } else if (list.stream().allMatch(b -> b == Boolean.TRUE)) {
                return true;
            } else {
                return null;
            }
        } catch (Exception e) {
            String message = String.format("and(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean all(Object... args) {
        if (args == null || args.length < 1) {
            return null;
        }

        try {
            return all(Arrays.asList(args));
        } catch (Exception e) {
            String message = String.format("and(%s)", args);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean or(List list) {
        return any(list);
    }

    @Override
    public Boolean or(Object... args) {
        return any(args);
    }

    @Override
    public Boolean any(List list) {
        if (list == null) {
            return null;
        }

        try {
            if (list.stream().anyMatch(b -> b == Boolean.TRUE)) {
                return true;
            } else if (list.stream().allMatch(b -> b == Boolean.FALSE)) {
                return false;
            } else {
                return null;
            }
        } catch (Exception e) {
            String message = String.format("or(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean any(Object... args) {
        if (args == null || args.length < 1) {
            return null;
        }

        try {
            return any(Arrays.asList(args));
        } catch (Exception e) {
            String message = String.format("or(%s)", args);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean not(Boolean operand) {
        return booleanNot(operand);
    }

    //
    // Date functions
    //
    @Override
    public BigDecimal year(LocalDate date) {
        try {
            return BigDecimal.valueOf(date.getYear());
        } catch (Exception e) {
            String message = String.format("year(%s)", date);
            logError(message, e);
            return null;
        }
    }
    public BigDecimal year(Temporal dateTime) {
        try {
            return BigDecimal.valueOf(dateTime.get(ChronoField.YEAR));
        } catch (Exception e) {
            String message = String.format("year(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal month(LocalDate date) {
        try {
            return BigDecimal.valueOf(date.getMonth().getValue());
        } catch (Exception e) {
            String message = String.format("month(%s)", date);
            logError(message, e);
            return null;
        }
    }
    public BigDecimal month(Temporal dateTime) {
        try {
            return BigDecimal.valueOf(dateTime.get(ChronoField.MONTH_OF_YEAR));
        } catch (Exception e) {
            String message = String.format("month(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal day(LocalDate date) {
        try {
            return BigDecimal.valueOf(date.getDayOfMonth());
        } catch (Exception e) {
            String message = String.format("day(%s)", date);
            logError(message, e);
            return null;
        }
    }
    public BigDecimal day(Temporal dateTime) {
        try {
            return BigDecimal.valueOf(dateTime.get(ChronoField.DAY_OF_MONTH));
        } catch (Exception e) {
            String message = String.format("day(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal weekday(LocalDate date) {
        try {
            return BigDecimal.valueOf(date.getDayOfWeek().getValue());
        } catch (Exception e) {
            String message = String.format("day(%s)", date);
            logError(message, e);
            return null;
        }
    }
    public BigDecimal weekday(Temporal dateTime) {
        try {
            return BigDecimal.valueOf(dateTime.get(ChronoField.DAY_OF_WEEK));
        } catch (Exception e) {
            String message = String.format("day(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    //
    // Time functions
    //
    @Override
    public BigDecimal hour(Temporal time) {
        try {
            return BigDecimal.valueOf(time.get(ChronoField.HOUR_OF_DAY));
        } catch (Exception e) {
            String message = String.format("hour(%s)", time);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal minute(Temporal time) {
        try {
            return BigDecimal.valueOf(time.get(ChronoField.MINUTE_OF_HOUR));
        } catch (Exception e) {
            String message = String.format("minute(%s)", time);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal second(Temporal time) {
        try {
            return BigDecimal.valueOf(time.get(ChronoField.SECOND_OF_MINUTE));
        } catch (Exception e) {
            String message = String.format("second(%s)", time);
            logError(message, e);
            return null;
        }
    }

    @Override
    public TemporalAmount timeOffset(Temporal time) {
        try {
            int secondsOffset = time.get(ChronoField.OFFSET_SECONDS);
            return duration(secondsOffset * 1000);
        } catch (Exception e) {
            String message = String.format("offset(%s)", time);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String timezone(Temporal time) {
        try {
            return time.query(TemporalQueries.zone()).getId();
        } catch (Exception e) {
            String message = String.format("timezone(%s)", time);
            logError(message, e);
            return null;
        }
    }

    //
    // Duration functions
    //
    @Override
    public BigDecimal years(TemporalAmount duration) {
        try {
            return BigDecimal.valueOf(duration.get(ChronoUnit.YEARS));
        } catch (Exception e) {
            String message = String.format("years(%s)", duration);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal months(TemporalAmount duration) {
        try {
            return BigDecimal.valueOf(duration.get(ChronoUnit.MONTHS));
        } catch (Exception e) {
            String message = String.format("months(%s)", duration);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal days(TemporalAmount duration) {
        try {
            return BigDecimal.valueOf(duration.get(ChronoUnit.DAYS));
        } catch (Exception e) {
            String message = String.format("days(%s)", duration);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal hours(TemporalAmount duration) {
        try {
            return BigDecimal.valueOf(duration.get(ChronoUnit.HOURS));
        } catch (Exception e) {
            String message = String.format("hours(%s)", duration);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal minutes(TemporalAmount duration) {
        try {
            return BigDecimal.valueOf(duration.get(ChronoUnit.MINUTES));
        } catch (Exception e) {
            String message = String.format("minutes(%s)", duration);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal seconds(TemporalAmount duration) {
        try {
            return BigDecimal.valueOf(duration.get(ChronoUnit.SECONDS));
        } catch (Exception e) {
            String message = String.format("seconds(%s)", duration);
            logError(message, e);
            return null;
        }
    }

    //
    // List functions
    //
    @Override
    public Boolean listContains(List list, Object element) {
        return list == null ? null : list.contains(element);
    }

    @Override
    public List append(List list, Object... items) {
        List result = new ArrayList<>();
        if (list != null) {
            result.addAll(list);
        }
        if (items != null) {
            for (Object item : items) {
                result.add(item);
            }
        } else {
            result.add(null);
        }
        return result;
    }

    @Override
    public BigDecimal count(List list) {
        return list == null ? BigDecimal.valueOf(0) : BigDecimal.valueOf(list.size());
    }

    @Override
    public BigDecimal min(List list) {
        try {
            return BigDecimalUtil.min(list);
        } catch (Exception e) {
            String message = String.format("min(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal max(List list) {
        try {
            return BigDecimalUtil.max(list);
        } catch (Exception e) {
            String message = String.format("max(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal sum(List list) {
        try {
            return BigDecimalUtil.sum(list);
        } catch (Exception e) {
            String message = String.format("sum(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List sublist(List list, BigDecimal startPosition) {
        return sublist(list, startPosition.intValue());
    }

    @Override
    public List sublist(List list, BigDecimal startPosition, BigDecimal length) {
        return sublist(list, startPosition.intValue(), length.intValue());
    }

    private List sublist(List list, int position) {
        List result = new ArrayList<>();
        if (list == null || isOutOfBounds(list, position)) {
            return result;
        }
        int javaStartPosition;
        // up to, not included
        int javaEndPosition = list.size();
        if (position < 0) {
            javaStartPosition = list.size() + position;
        } else {
            javaStartPosition = position - 1;
        }
        for (int i = javaStartPosition; i < javaEndPosition; i++) {
            result.add(list.get(i));
        }
        return result;
    }

    private List sublist(List list, int position, int length) {
        List result = new ArrayList<>();
        if (list == null || isOutOfBounds(list, position)) {
            return result;
        }
        int javaStartPosition;
        int javaEndPosition;
        if (position < 0) {
            javaStartPosition = list.size() + position;
            javaEndPosition = javaStartPosition + length;
        } else {
            javaStartPosition = position - 1;
            javaEndPosition = javaStartPosition + length;
        }
        for (int i = javaStartPosition; i < javaEndPosition; i++) {
            result.add(list.get(i));
        }
        return result;
    }

    private boolean isOutOfBounds(List list, int position) {
        int length = list.size();
        if (position < 0) {
            return !(-length <= position);
        } else {
            return !(1 <= position && position <= length);
        }
    }

    @Override
    public List concatenate(Object... lists) {
        List result = new ArrayList<>();
        if (lists != null) {
            for (Object list : lists) {
                result.addAll((List) list);
            }
        }
        return result;
    }

    @Override
    public List insertBefore(List list, BigDecimal position, Object newItem) {
        return insertBefore(list, position.intValue(), newItem);
    }

    private List insertBefore(List list, int position, Object newItem) {
        List result = new ArrayList<>();
        if (list != null) {
            result.addAll(list);
        }
        if (isOutOfBounds(result, position)) {
            return result;
        }
        if (position < 0) {
            position = result.size() + position;
        } else {
            position = position - 1;
        }
        result.add(position, newItem);
        return result;
    }

    @Override
    public List remove(List list, Object position) {
        return remove(list, ((BigDecimal) position).intValue());
    }

    private List remove(List list, int position) {
        List result = new ArrayList<>();
        if (list != null) {
            result.addAll(list);
        }
        result.remove(position - 1);
        return result;
    }

    @Override
    public List reverse(List list) {
        List result = new ArrayList<>();
        if (list != null) {
            for (int i = list.size() - 1; i >= 0; i--) {
                result.add(list.get(i));
            }
        }
        return result;
    }

    @Override
    public List indexOf(List list, Object match) {
        List result = new ArrayList<>();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                Object o = list.get(i);
                if (o == null && match == null || o!= null && o.equals(match)) {
                    result.add(BigDecimal.valueOf(i + 1));
                }
            }
        }
        return result;
    }

    @Override
    public List union(Object... lists) {
        List result = new ArrayList<>();
        if (lists != null) {
            for (Object list : lists) {
                result.addAll((List) list);
            }
        }
        return distinctValues(result);
    }

    @Override
    public List distinctValues(List list1) {
        List result = new ArrayList<>();
        if (list1 != null) {
            for (Object element : list1) {
                if (!result.contains(element)) {
                    result.add(element);
                }
            }
        }
        return result;
    }

    @Override
    public List flatten(List list1) {
        if (list1 == null) {
            return null;
        }
        List result = new ArrayList<>();
        collect(result, list1);
        return result;
    }

    @Override
    public BigDecimal product(List list) {
        try {
            return BigDecimalUtil.product(list);
        } catch (Exception e) {
            String message = String.format("product(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal product(Object... numbers) {
        if (numbers == null || numbers.length < 1) {
            return null;
        }

        try {
            return product(Arrays.asList(numbers));
        } catch (Exception e) {
            String message = String.format("sum(%s)", numbers);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal median(List list) {
        try {
            return BigDecimalUtil.median(list);
        } catch (Exception e){
            String message = String.format("median(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal median(Object... numbers) {
        if (numbers == null || numbers.length < 1) {
            return null;
        }

        try {
            return median(Arrays.asList(numbers));
        } catch (Exception e) {
            String message = String.format("median(%s)", numbers);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal stddev(List list) {
        try {
            return BigDecimalUtil.stddev(list);
        } catch (Exception e) {
            String message = String.format("stddev(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal stddev(Object... numbers) {
        if (numbers == null || numbers.length < 1) {
            return null;
        }

        try {
            return stddev(Arrays.asList(numbers));
        } catch (Exception e) {
            String message = String.format("stddev(%s)", numbers);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List mode(List list) {
        try {
            return BigDecimalUtil.mode(list);
        } catch (Exception e) {
            String message = String.format("mode(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List mode(Object... numbers) {
        if (numbers == null) {
            return null;
        }

        try {
            return mode(Arrays.asList(numbers));
        } catch (Exception e) {
            String message = String.format("mode(%s)", numbers);
            logError(message, e);
            return null;
        }
    }

    @Override
    public void collect(List result, List list) {
        if (list != null) {
            for (Object object : list) {
                if (object instanceof List) {
                    collect(result, (List) object);
                } else {
                    result.add(object);
                }
            }
        }
    }

    @Override
    public <T> List<T> sort(List<T> list, LambdaExpression<Boolean> comparator) {
        List<T> clone = new ArrayList<>(list);
        Comparator<? super T> comp = (Comparator<T>) (o1, o2) -> {
            if (comparator.apply(o1, o2)) {
                return -1;
            } else if (o1 != null && o1.equals(o2)) {
                return 0;
            } else {
                return 1;
            }
        };
        clone.sort(comp);
        return clone;
    }

    private String dateTimeISOFormat(BigDecimal year, BigDecimal month, BigDecimal day, int hours, int minutes, int seconds) {
        return String.format("%04d-%02d-%02dT%02d:%02d:%02dZ", year.intValue(), month.intValue(), day.intValue(), hours, minutes, seconds);
    }

    private String dateTimeISOFormat(int year, int month, int day, int hours, int minutes, int seconds, String offset) {
        return String.format("%04d-%02d-%02dT%02d:%02d:%02d%s", year, month, day, hours, minutes, seconds, offset);
    }
}
