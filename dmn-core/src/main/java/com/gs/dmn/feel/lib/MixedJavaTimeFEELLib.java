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
package com.gs.dmn.feel.lib;

import com.gs.dmn.feel.lib.type.context.DefaultContextType;
import com.gs.dmn.feel.lib.type.list.DefaultListType;
import com.gs.dmn.feel.lib.type.logic.DefaultBooleanType;
import com.gs.dmn.feel.lib.type.numeric.DefaultNumericType;
import com.gs.dmn.feel.lib.type.string.DefaultStringType;
import com.gs.dmn.feel.lib.type.time.mixed.LocalDateType;
import com.gs.dmn.feel.lib.type.time.mixed.OffsetTimeType;
import com.gs.dmn.feel.lib.type.time.mixed.ZonedDateTimeType;
import com.gs.dmn.feel.lib.type.time.xml.DefaultDurationType;
import com.gs.dmn.runtime.LambdaExpression;
import org.apache.commons.lang3.StringUtils;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import java.math.BigDecimal;
import java.time.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MixedJavaTimeFEELLib extends BaseFEELLib<BigDecimal, LocalDate, OffsetTime, ZonedDateTime, Duration> implements StandardFEELLib<BigDecimal, LocalDate, OffsetTime, ZonedDateTime, Duration> {
    private static final DatatypeFactory DATA_TYPE_FACTORY = XMLDatataypeFactory.newInstance();

    private final BigDecimalLib numberLib = new BigDecimalLib();
    private final StringLib stringLib = new StringLib();
    private final BooleanLib booleanLib = new BooleanLib();
    private final DateLib dateLib = new DateLib();
    private final TimeLib timeLib = new TimeLib();
    private final DateTimeLib dateTimeLib = new DateTimeLib();
    private final DurationLib durationLib = new DurationLib();
    
    public MixedJavaTimeFEELLib() {
        super(new DefaultNumericType(LOGGER),
                new DefaultBooleanType(LOGGER),
                new DefaultStringType(LOGGER),
                new LocalDateType(LOGGER, DATA_TYPE_FACTORY),
                new OffsetTimeType(LOGGER, DATA_TYPE_FACTORY),
                new ZonedDateTimeType(LOGGER, DATA_TYPE_FACTORY),
                new DefaultDurationType(LOGGER),
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
        return this.dateTimeLib.string(from);
    }

    @Override
    public LocalDate date(String literal) {
        try {
            if (literal == null) {
                return null;
            }

            if (this.dateTimeLib.hasTime(literal) || this.dateTimeLib.hasZone(literal)) {
                String message = String.format("date(%s)", literal);
                logError(message);
                return null;
            } else {
                return this.dateTimeLib.makeLocalDate(literal);
            }
        } catch (Exception e) {
            String message = String.format("date(%s)", literal);
            logError(message, e);
            return null;
        }
    }

    @Override
    public LocalDate date(BigDecimal year, BigDecimal month, BigDecimal day) {
        if (year == null || month == null || day == null) {
            return null;
        }

        return LocalDate.of(year.intValue(), month.intValue(), day.intValue());
    }

    @Override
    public LocalDate date(ZonedDateTime from) {
        if (from == null) {
            return null;
        }

        return from.toLocalDate();
    }
    public LocalDate date(LocalDate from) {
        if (from == null) {
            return null;
        }
        return from;
    }

    @Override
    public OffsetTime time(String literal) {
        if (literal == null) {
            return null;
        }
        try {
            return this.dateTimeLib.makeOffsetTime(literal);
        } catch (Exception e) {
            String message = String.format("time(%s)", literal);
            logError(message, e);
            return null;
        }
    }

    @Override
    public OffsetTime time(BigDecimal hour, BigDecimal minute, BigDecimal second, Duration offset) {
        if (hour == null || minute == null || second == null) {
            return null;
        }

        try {
            if (offset != null) {
                // Make ZoneOffset
                String sign = offset.getSign() < 0 ? "-" : "+";
                String offsetString = String.format("%s%02d:%02d:%02d", sign, (long) offset.getHours(), (long) offset.getMinutes(), offset.getSeconds());
                ZoneOffset zoneOffset = ZoneOffset.of(offsetString);

                // Make OffsetTime and add nanos
                OffsetTime offsetTime = OffsetTime.of(hour.intValue(), minute.intValue(), second.intValue(), 0, zoneOffset);
                BigDecimal secondFraction = second.subtract(BigDecimal.valueOf(second.intValue()));
                double nanos = secondFraction.doubleValue() * 1E9;
                offsetTime = offsetTime.plusNanos((long) nanos);
                return offsetTime;
            } else {
                // Make OffsetTime and add nanos
                OffsetTime offsetTime = OffsetTime.of(hour.intValue(), minute.intValue(), second.intValue(), 0, ZoneOffset.UTC);
                BigDecimal secondFraction = second.subtract(BigDecimal.valueOf(second.intValue()));
                double nanos = secondFraction.doubleValue() * 1E9;
                offsetTime = offsetTime.plusNanos((long) nanos);
                return offsetTime;
            }
        } catch (Exception e) {
            String message = String.format("time(%s, %s, %s, %s)", hour, minute, second, offset);
            logError(message, e);
            return null;
        }
    }

    @Override
    public OffsetTime time(ZonedDateTime from) {
        if (from == null) {
            return null;
        }
        return from.toOffsetDateTime().toOffsetTime();
    }
    public OffsetTime time(LocalDate from) {
        if (from == null) {
            return null;
        }
        return from.atStartOfDay(ZoneOffset.UTC).toOffsetDateTime().toOffsetTime();
    }
    public OffsetTime time(OffsetTime from) {
        if (from == null) {
            return null;
        }
        return from;
    }

    @Override
    public ZonedDateTime dateAndTime(String from) {
        if (from == null) {
            return null;
        }
        if (this.dateTimeLib.hasZone(from) && this.dateTimeLib.hasOffset(from)) {
            return null;
        }
        if (this.dateTimeLib.invalidYear(from)) {
            return null;
        }

        return makeDateTime(from);
    }

    @Override
    public ZonedDateTime dateAndTime(LocalDate date, OffsetTime time) {
        if (date == null || time == null) {
            return null;
        }

        try {
            ZoneOffset offset = time.getOffset();
            LocalDateTime localDateTime = LocalDateTime.of(date, time.toLocalTime());
            return ZonedDateTime.ofInstant(localDateTime, offset, ZoneId.of(offset.getId()));
        } catch (Exception e) {
            String message = String.format("dateAndTime(%s, %s)", date, time);
            logError(message, e);
            return null;
        }
    }
    public ZonedDateTime dateAndTime(Object date, OffsetTime time) {
        if (date == null || time == null) {
            return null;
        }

        if (date instanceof ZonedDateTime) {
            return dateAndTime(((ZonedDateTime) date).toLocalDate(), time);
        } else {
            String message = String.format("dateAndTime(%s, %s)", date, time);
            logError(message);
            return null;
        }
    }

    @Override
    public Duration duration(String from) {
        if (StringUtils.isBlank(from)) {
            return null;
        }

        try {
            return DATA_TYPE_FACTORY.newDuration(from);
        } catch (Exception e) {
            String message = String.format("duration(%s)", from);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Duration yearsAndMonthsDuration(ZonedDateTime from, ZonedDateTime to) {
        if (from == null || to == null) {
            return null;
        }

        try {
            return this.dateTimeLib.toYearsMonthDuration(DATA_TYPE_FACTORY, toDate(to), toDate(from));
        } catch (Exception e) {
            String message = String.format("yearsAndMonthsDuration(%s, %s)", from, to);
            logError(message, e);
            return null;
        }
    }

    public Duration yearsAndMonthsDuration(LocalDate from, LocalDate to) {
        if (from == null || to == null) {
            return null;
        }

        try {
            return this.dateTimeLib.toYearsMonthDuration(DATA_TYPE_FACTORY, to, from);
        } catch (Exception e) {
            String message = String.format("yearsAndMonthsDuration(%s, %s)", from, to);
            logError(message, e);
            return null;
        }
    }

    public Duration yearsAndMonthsDuration(ZonedDateTime from, LocalDate to) {
        if (from == null || to == null) {
            return null;
        }

        try {
            return this.dateTimeLib.toYearsMonthDuration(DATA_TYPE_FACTORY, to, toDate(from));
        } catch (Exception e) {
            String message = String.format("yearsAndMonthsDuration(%s, %s)", from, to);
            logError(message, e);
            return null;
        }
    }

    public Duration yearsAndMonthsDuration(LocalDate from, ZonedDateTime to) {
        if (from == null || to == null) {
            return null;
        }

        try {
            return this.dateTimeLib.toYearsMonthDuration(DATA_TYPE_FACTORY, toDate(to), from);
        } catch (Exception e) {
            String message = String.format("yearsAndMonthsDuration(%s, %s)", from, to);
            logError(message, e);
            return null;
        }
    }

    private ZonedDateTime makeDateTime(String literal) {
        if (StringUtils.isBlank(literal)) {
            return null;
        }

        try {
            return this.dateTimeLib.makeDateTime(literal);
        } catch (Exception e) {
            String message = String.format("makeDateTime(%s)", literal);
            logError(message, e);
            return null;
        }
    }

    @Override
    public LocalDate toDate(Object object) {
        if (object instanceof ZonedDateTime) {
            return date((ZonedDateTime) object);
        }
        return (LocalDate) object;
    }

    @Override
    public OffsetTime toTime(Object object) {
        if (object instanceof ZonedDateTime) {
            return time((ZonedDateTime) object);
        }
        return (OffsetTime) object;
    }

    //
    // Numeric functions
    //
    @Override
    public BigDecimal decimal(BigDecimal n, BigDecimal scale) {
        try {
            return this.numberLib.decimal(n, scale);
        } catch (Exception e) {
            String message = String.format("decimal(%s, %s)", n, scale);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal floor(BigDecimal number) {
        try {
            return this.numberLib.floor(number);
        } catch (Exception e) {
            String message = String.format("fllor(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal ceiling(BigDecimal number) {
        try {
            return this.numberLib.ceiling(number);
        } catch (Exception e) {
            String message = String.format("ceiling(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal abs(BigDecimal number) {
        try {
            return this.numberLib.abs(number);
        } catch (Exception e) {
            String message = String.format("abs(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal intModulo(BigDecimal dividend, BigDecimal divisor) {
        try {
            return this.numberLib.intModulo(dividend, divisor);
        } catch (Exception e) {
            String message = String.format("modulo(%s, %s)", dividend, divisor);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal modulo(BigDecimal dividend, BigDecimal divisor) {
        try {
            return this.numberLib.modulo(dividend, divisor);
        } catch (Exception e) {
            String message = String.format("modulo(%s, %s)", dividend, divisor);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal sqrt(BigDecimal number) {
        try {
            return this.numberLib.sqrt(number);
        } catch (Exception e) {
            String message = String.format("sqrt(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal log(BigDecimal number) {
        try {
            return this.numberLib.log(number);
        } catch (Exception e) {
            String message = String.format("log(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal exp(BigDecimal number) {
        try {
            return this.numberLib.exp(number);
        } catch (Exception e) {
            String message = String.format("exp(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean odd(BigDecimal number) {
        try {
            return this.numberLib.odd(number);
        } catch (Exception e) {
            String message = String.format("odd(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean even(BigDecimal number) {
        try {
            return this.numberLib.even(number);
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
        if (list == null) {
            return null;
        }

        try {
            BigDecimal sum = sum(list);
            return numericDivide(sum, BigDecimal.valueOf(list.size()));
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
        return this.stringLib.contains(string, match);
    }

    @Override
    public Boolean startsWith(String string, String match) {
        return this.stringLib.startsWith(string, match);
    }

    @Override
    public Boolean endsWith(String string, String match) {
        return this.stringLib.endsWith(string, match);
    }

    @Override
    public BigDecimal stringLength(String string) {
        return string == null ? null : BigDecimal.valueOf(this.stringLib.stringLength(string));
    }

    @Override
    public String substring(String string, BigDecimal startPosition) {
        return this.stringLib.substring(string, startPosition);
    }

    @Override
    public String substring(String string, BigDecimal startPosition, BigDecimal length) {
        return this.stringLib.substring(string, startPosition, length);
    }

    @Override
    public String upperCase(String string) {
        return this.stringLib.upperCase(string);
    }

    @Override
    public String lowerCase(String string) {
        return this.stringLib.lowerCase(string);
    }

    @Override
    public String substringBefore(String string, String match) {
        return this.stringLib.substringBefore(string, match);
    }

    @Override
    public String substringAfter(String string, String match) {
        return this.stringLib.substringAfter(string, match);
    }

    @Override
    public String replace(String input, String pattern, String replacement) {
        return replace(input, pattern, replacement, "");
    }

    @Override
    public String replace(String input, String pattern, String replacement, String flags) {
        try {
            return this.stringLib.replace(input, pattern, replacement, flags);
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
            return this.stringLib.matches(input, pattern, flags);
        } catch (Exception e) {
            String message = String.format("matches(%s, %s, %s)", input, pattern, flags);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List split(String string, String delimiter) {
        try {
            return this.stringLib.split(string, delimiter);
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
        try {
            return this.booleanLib.and(list);
        } catch (Exception e) {
            String message = String.format("and(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean and(Object... args) {
        try {
            return this.booleanLib.and(args);
        } catch (Exception e) {
            String message = String.format("and(%s)", args);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean all(List list) {
        try {
            return this.booleanLib.all(list);
        } catch (Exception e) {
            String message = String.format("all(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean all(Object... args) {
        try {
            return this.booleanLib.all(args);
        } catch (Exception e) {
            String message = String.format("all(%s)", args);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean or(List list) {
        try {
            return this.booleanLib.or(list);
        } catch (Exception e) {
            String message = String.format("or(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean or(Object... args) {
        try {
            return this.booleanLib.or(args);
        } catch (Exception e) {
            String message = String.format("or(%s)", args);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean any(List list) {
        try {
            return this.booleanLib.any(list);
        } catch (Exception e) {
            String message = String.format("any(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean any(Object... args) {
        try {
            return this.booleanLib.any(args);
        } catch (Exception e) {
            String message = String.format("any(%s)", args);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean not(Boolean operand) {
        try {
            return this.booleanType.booleanNot(operand);
        } catch (Exception e) {
            String message = String.format("not(%s)", operand);
            logError(message, e);
            return null;
        }
    }

    //
    // Date functions
    //
    public BigDecimal year(LocalDate date) {
        try {
            return BigDecimal.valueOf(date.getYear());
        } catch (Exception e) {
            String message = String.format("year(%s)", date);
            logError(message, e);
            return null;
        }
    }
    public BigDecimal year(ZonedDateTime dateTime) {
        try {
            return BigDecimal.valueOf(dateTime.getYear());
        } catch (Exception e) {
            String message = String.format("year(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    public BigDecimal month(LocalDate date) {
        try {
            return BigDecimal.valueOf(date.getMonth().getValue());
        } catch (Exception e) {
            String message = String.format("month(%s)", date);
            logError(message, e);
            return null;
        }
    }
    public BigDecimal month(ZonedDateTime dateTime) {
        try {
            return BigDecimal.valueOf(dateTime.getMonth().getValue());
        } catch (Exception e) {
            String message = String.format("month(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    public BigDecimal day(LocalDate date) {
        try {
            return BigDecimal.valueOf(date.getDayOfMonth());
        } catch (Exception e) {
            String message = String.format("day(%s)", date);
            logError(message, e);
            return null;
        }
    }
    public BigDecimal day(ZonedDateTime dateTime) {
        try {
            return BigDecimal.valueOf(dateTime.getDayOfMonth());
        } catch (Exception e) {
            String message = String.format("day(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }
    public BigDecimal weekday(LocalDate date) {
        try {
            return BigDecimal.valueOf(date.getDayOfWeek().getValue());
        } catch (Exception e) {
            String message = String.format("day(%s)", date);
            logError(message, e);
            return null;
        }
    }
    public BigDecimal weekday(ZonedDateTime dateTime) {
        try {
            return BigDecimal.valueOf(dateTime.getDayOfWeek().getValue());
        } catch (Exception e) {
            String message = String.format("day(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    //
    // Time functions
    //
    public BigDecimal hour(OffsetTime time) {
        return BigDecimal.valueOf(time.getHour());
    }
    public BigDecimal hour(ZonedDateTime dateTime) {
        return BigDecimal.valueOf(dateTime.getHour());
    }

    public BigDecimal minute(OffsetTime time) {
        return BigDecimal.valueOf(time.getMinute());
    }
    public BigDecimal minute(ZonedDateTime dateTime) {
        return BigDecimal.valueOf(dateTime.getMinute());
    }

    public BigDecimal second(OffsetTime time) {
        return BigDecimal.valueOf(time.getSecond());
    }
    public BigDecimal second(ZonedDateTime dateTime) {
        return BigDecimal.valueOf(dateTime.getSecond());
    }

    public Duration timeOffset(OffsetTime time) {
        // timezone offset in seconds
        int secondsOffset = time.getOffset().getTotalSeconds();
        return computeDuration(secondsOffset);
    }
    public Duration timeOffset(ZonedDateTime dateTime) {
        // timezone offset in seconds
        int secondsOffset = dateTime.getOffset().getTotalSeconds();
        return computeDuration(secondsOffset);
    }

    public String timezone(OffsetTime time) {
        return time.getOffset().getId();
    }
    public String timezone(ZonedDateTime dateTime) {
        return dateTime.getZone().getId();
    }
    private Duration computeDuration(int secondsOffset) {
        return DATA_TYPE_FACTORY.newDuration((long) secondsOffset * 1000);
    }

    //
    // Duration functions
    //
    public BigDecimal years(Duration duration) {
        return BigDecimal.valueOf(duration.getYears());
    }

    public BigDecimal months(Duration duration) {
        return BigDecimal.valueOf(duration.getMonths());
    }

    public BigDecimal days(Duration duration) {
        return BigDecimal.valueOf(duration.getDays());
    }

    public BigDecimal hours(Duration duration) {
        return BigDecimal.valueOf(duration.getHours());
    }

    public BigDecimal minutes(Duration duration) {
        return BigDecimal.valueOf(duration.getMinutes());
    }

    public BigDecimal seconds(Duration duration) {
        return BigDecimal.valueOf(duration.getSeconds());
    }

    private int months(ZonedDateTime calendar) {
        return calendar.getYear() * 12 + calendar.getMonth().getValue();
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
            return this.numberLib.min(list);
        } catch (Exception e) {
            String message = String.format("min(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal max(List list) {
        try {
            return this.numberLib.max(list);
        } catch (Exception e) {
            String message = String.format("max(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal sum(List list) {
        try {
            return this.numberLib.sum(list);
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
                    result.add(BigDecimal.valueOf((long) i + 1));
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
            return this.numberLib.product(list);
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
            return this.numberLib.median(list);
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
            return this.numberLib.stddev(list);
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
            return this.numberLib.mode(list);
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
}
