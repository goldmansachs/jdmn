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

import com.gs.dmn.feel.lib.type.bool.DefaultBooleanLib;
import com.gs.dmn.feel.lib.type.context.DefaultContextType;
import com.gs.dmn.feel.lib.type.list.DefaultListType;
import com.gs.dmn.feel.lib.type.list.DefaultListLib;
import com.gs.dmn.feel.lib.type.logic.DefaultBooleanType;
import com.gs.dmn.feel.lib.type.numeric.DoubleNumericLib;
import com.gs.dmn.feel.lib.type.numeric.DoubleNumericType;
import com.gs.dmn.feel.lib.type.string.DefaultStringType;
import com.gs.dmn.feel.lib.type.string.DefaultStringLib;
import com.gs.dmn.feel.lib.type.time.mixed.LocalDateType;
import com.gs.dmn.feel.lib.type.time.mixed.OffsetTimeType;
import com.gs.dmn.feel.lib.type.time.mixed.ZonedDateTimeType;
import com.gs.dmn.feel.lib.type.time.xml.*;
import com.gs.dmn.runtime.LambdaExpression;
import org.apache.commons.lang3.StringUtils;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import java.time.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DoubleMixedJavaTimeFEELLib extends BaseFEELLib<Double, LocalDate, OffsetTime, ZonedDateTime, Duration> implements StandardFEELLib<Double, LocalDate, OffsetTime, ZonedDateTime, Duration> {
    private static final DatatypeFactory DATA_TYPE_FACTORY = XMLDatataypeFactory.newInstance();

    private final DoubleNumericLib numberLib = new DoubleNumericLib();
    private final DefaultStringLib stringLib = new DefaultStringLib();
    private final DefaultBooleanLib booleanLib = new DefaultBooleanLib();
    private final DefaultDateLib dateLib = new DefaultDateLib();
    private final DefaultTimeLib timeLib = new DefaultTimeLib();
    private final DefaultDateTimeLib dateTimeLib = new DefaultDateTimeLib();
    private final DefaultDurationLib durationLib = new DefaultDurationLib();
    private final DefaultListLib listLib = new DefaultListLib();

    public DoubleMixedJavaTimeFEELLib() {
        super(new DoubleNumericType(LOGGER),
                new DefaultBooleanType(LOGGER),
                new DefaultStringType(LOGGER),
                new LocalDateType(LOGGER, DATA_TYPE_FACTORY),
                new OffsetTimeType(LOGGER, DATA_TYPE_FACTORY),
                new ZonedDateTimeType(LOGGER, DATA_TYPE_FACTORY),
                new DoubleDefaultDurationType(LOGGER),
                new DefaultListType(LOGGER),
                new DefaultContextType(LOGGER)
        );
    }

    //
    // Conversion functions
    //

    @Override
    public Double number(String literal) {
        if (StringUtils.isBlank(literal)) {
            return null;
        }

        try {
            return Double.parseDouble(literal);
        } catch (Exception e) {
            String message = String.format("number(%s)", literal);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double number(String from, String groupingSeparator, String decimalSeparator) {
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
        return this.stringLib.string(from);
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
    public LocalDate date(Double year, Double month, Double day) {
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
    public OffsetTime time(Double hour, Double minute, Double second, Duration offset) {
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
                Double secondFraction = second - second.intValue();
                double nanos = secondFraction * 1E9;
                offsetTime = offsetTime.plusNanos((long) nanos);
                return offsetTime;
            } else {
                // Make OffsetTime and add nanos
                OffsetTime offsetTime = OffsetTime.of(hour.intValue(), minute.intValue(), second.intValue(), 0, ZoneOffset.UTC);
                Double secondFraction = second - second.intValue();
                double nanos = secondFraction * 1E9;
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
    public Double decimal(Double n, Double scale) {
        try {
            return this.numberLib.decimal(n, scale);
        } catch (Exception e) {
            String message = String.format("decimal(%s, %s)", n, scale);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double floor(Double number) {
        try {
            return this.numberLib.floor(number);
        } catch (Exception e) {
            String message = String.format("fllor(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double ceiling(Double number) {
        try {
            return this.numberLib.ceiling(number);
        } catch (Exception e) {
            String message = String.format("ceiling(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double abs(Double number) {
        try {
            return this.numberLib.abs(number);
        } catch (Exception e) {
            String message = String.format("abs(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double intModulo(Double dividend, Double divisor) {
        try {
            return this.numberLib.intModulo(dividend, divisor);
        } catch (Exception e) {
            String message = String.format("modulo(%s, %s)", dividend, divisor);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double modulo(Double dividend, Double divisor) {
        try {
            return this.numberLib.modulo(dividend, divisor);
        } catch (Exception e) {
            String message = String.format("modulo(%s, %s)", dividend, divisor);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double sqrt(Double number) {
        try {
            return this.numberLib.sqrt(number);
        } catch (Exception e) {
            String message = String.format("sqrt(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double log(Double number) {
        try {
            return this.numberLib.log(number);
        } catch (Exception e) {
            String message = String.format("log(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double exp(Double number) {
        try {
            return this.numberLib.exp(number);
        } catch (Exception e) {
            String message = String.format("exp(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean odd(Double number) {
        try {
            return this.numberLib.odd(number);
        } catch (Exception e) {
            String message = String.format("odd(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean even(Double number) {
        try {
            return this.numberLib.even(number);
        } catch (Exception e) {
            String message = String.format("odd(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double min(Object... args) {
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
    public Double max(Object... args) {
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
    public Double sum(Object... args) {
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
    public Double mean(List list) {
        try {
            return this.numberLib.mean(list);
        } catch (Exception e) {
            String message = String.format("mean(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double mean(Object... args) {
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
    public Double stringLength(String string) {
        return string == null ? null : Double.valueOf(this.stringLib.stringLength(string));
    }

    @Override
    public String substring(String string, Double startPosition) {
        return this.stringLib.substring(string, startPosition);
    }

    @Override
    public String substring(String string, Double startPosition, Double length) {
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
    public Double year(LocalDate date) {
        if (date == null) {
            return null;
        }

        try {
            return Double.valueOf(date.getYear());
        } catch (Exception e) {
            String message = String.format("year(%s)", date);
            logError(message, e);
            return null;
        }
    }
    public Double year(ZonedDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }

        try {
            return Double.valueOf(dateTime.getYear());
        } catch (Exception e) {
            String message = String.format("year(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    public Double month(LocalDate date) {
        if (date == null) {
            return null;
        }

        try {
            return Double.valueOf(date.getMonth().getValue());
        } catch (Exception e) {
            String message = String.format("month(%s)", date);
            logError(message, e);
            return null;
        }
    }
    public Double month(ZonedDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }

        try {
            return Double.valueOf(dateTime.getMonth().getValue());
        } catch (Exception e) {
            String message = String.format("month(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    public Double day(LocalDate date) {
        if (date == null) {
            return null;
        }

        try {
            return Double.valueOf(date.getDayOfMonth());
        } catch (Exception e) {
            String message = String.format("day(%s)", date);
            logError(message, e);
            return null;
        }
    }
    public Double day(ZonedDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }

        try {
            return Double.valueOf(dateTime.getDayOfMonth());
        } catch (Exception e) {
            String message = String.format("day(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }
    public Double weekday(LocalDate date) {
        if (date == null) {
            return null;
        }

        try {
            return Double.valueOf(date.getDayOfWeek().getValue());
        } catch (Exception e) {
            String message = String.format("day(%s)", date);
            logError(message, e);
            return null;
        }
    }
    public Double weekday(ZonedDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }

        try {
            return Double.valueOf(dateTime.getDayOfWeek().getValue());
        } catch (Exception e) {
            String message = String.format("day(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    //
    // Time functions
    //
    public Double hour(OffsetTime time) {
        return Double.valueOf(time.getHour());
    }
    public Double hour(ZonedDateTime dateTime) {
        return Double.valueOf(dateTime.getHour());
    }

    public Double minute(OffsetTime time) {
        return Double.valueOf(time.getMinute());
    }
    public Double minute(ZonedDateTime dateTime) {
        return Double.valueOf(dateTime.getMinute());
    }

    public Double second(OffsetTime time) {
        return Double.valueOf(time.getSecond());
    }
    public Double second(ZonedDateTime dateTime) {
        return Double.valueOf(dateTime.getSecond());
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
    public Double years(Duration duration) {
        if (duration == null) {
            return null;
        }

        return Double.valueOf(duration.getYears());
    }

    public Double months(Duration duration) {
        if (duration == null) {
            return null;
        }

        return Double.valueOf(duration.getMonths());
    }

    public Double days(Duration duration) {
        if (duration == null) {
            return null;
        }

        return Double.valueOf(duration.getDays());
    }

    public Double hours(Duration duration) {
        if (duration == null) {
            return null;
        }

        return Double.valueOf(duration.getHours());
    }

    public Double minutes(Duration duration) {
        if (duration == null) {
            return null;
        }

        return Double.valueOf(duration.getMinutes());
    }

    public Double seconds(Duration duration) {
        if (duration == null) {
            return null;
        }

        return Double.valueOf(duration.getSeconds());
    }

    //
    // List functions
    //
    @Override
    public Boolean listContains(List list, Object element) {
        try {
            return this.listLib.listContains(list, element);
        } catch (Exception e) {
            String message = String.format("listContains(%s, %s)", list, element);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List append(List list, Object... items) {
        try {
            return this.listLib.append(list, items);
        } catch (Exception e) {
            String message = String.format("append(%s, %s)", list, items);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double count(List list) {
        try {
            return this.numberLib.count(list);
        } catch (Exception e) {
            String message = String.format("count(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double min(List list) {
        try {
            return this.numberLib.min(list);
        } catch (Exception e) {
            String message = String.format("min(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double max(List list) {
        try {
            return this.numberLib.max(list);
        } catch (Exception e) {
            String message = String.format("max(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double sum(List list) {
        try {
            return this.numberLib.sum(list);
        } catch (Exception e) {
            String message = String.format("sum(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List sublist(List list, Double startPosition) {
        try {
            return this.listLib.sublist(list, startPosition.intValue());
        } catch (Exception e) {
            String message = String.format("min(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List sublist(List list, Double startPosition, Double length) {
        try {
            return this.listLib.sublist(list, startPosition.intValue(), length.intValue());
        } catch (Exception e) {
            String message = String.format("min(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List concatenate(Object... lists) {
        try {
            return this.listLib.concatenate(lists);
        } catch (Exception e) {
            String message = String.format("concatenate(%s)", lists);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List insertBefore(List list, Double position, Object newItem) {
        try {
            return this.listLib.insertBefore(list, position.intValue(), newItem);
        } catch (Exception e) {
            String message = String.format("insertBefore(%s, %s, %s)", list, position, newItem);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List remove(List list, Object position) {
        try {
            return this.listLib.remove(list, ((Number)position).intValue());
        } catch (Exception e) {
            String message = String.format("min(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List reverse(List list) {
        try {
            return this.listLib.reverse(list);
        } catch (Exception e) {
            String message = String.format("reverse(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List indexOf(List list, Object match) {
        List result = new ArrayList<>();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                Object o = list.get(i);
                if (o == null && match == null || o!= null && o.equals(match)) {
                    result.add(Double.valueOf((double) i + 1));
                }
            }
        }
        return result;
    }

    @Override
    public List union(Object... lists) {
        try {
            return this.listLib.union(lists);
        } catch (Exception e) {
            String message = String.format("union(%s)", lists);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List distinctValues(List list) {
        try {
            return this.listLib.distinctValues(list);
        } catch (Exception e) {
            String message = String.format("distinctValues(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List flatten(List list) {
        try {
            return this.listLib.flatten(list);
        } catch (Exception e) {
            String message = String.format("flatten(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double product(List list) {
        try {
            return this.numberLib.product(list);
        } catch (Exception e) {
            String message = String.format("product(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double product(Object... numbers) {
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
    public Double median(List list) {
        try {
            return this.numberLib.median(list);
        } catch (Exception e){
            String message = String.format("median(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double median(Object... numbers) {
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
    public Double stddev(List list) {
        try {
            return this.numberLib.stddev(list);
        } catch (Exception e) {
            String message = String.format("stddev(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double stddev(Object... numbers) {
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
        try {
            this.listLib.collect(result, list);
        } catch (Exception e) {
            String message = String.format("collect(%s, %s)", result, list);
            logError(message, e);
        }
    }

    @Override
    public <T> List<T> sort(List<T> list, LambdaExpression<Boolean> comparator) {
        try {
            return this.listLib.sort(list, comparator);
        } catch (Exception e) {
            String message = String.format("min(%s)", list);
            logError(message, e);
            return null;
        }
    }
}
