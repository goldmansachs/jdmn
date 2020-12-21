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
import com.gs.dmn.feel.lib.type.list.DefaultListLib;
import com.gs.dmn.feel.lib.type.list.DefaultListType;
import com.gs.dmn.feel.lib.type.logic.DefaultBooleanType;
import com.gs.dmn.feel.lib.type.numeric.DoubleNumericLib;
import com.gs.dmn.feel.lib.type.numeric.DoubleNumericType;
import com.gs.dmn.feel.lib.type.string.DefaultStringLib;
import com.gs.dmn.feel.lib.type.string.DefaultStringType;
import com.gs.dmn.feel.lib.type.time.mixed.*;
import com.gs.dmn.feel.lib.type.time.xml.DoubleDurationType;
import com.gs.dmn.runtime.LambdaExpression;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class DoubleMixedJavaTimeFEELLib extends BaseFEELLib<Double, LocalDate, OffsetTime, ZonedDateTime, Duration> implements StandardFEELLib<Double, LocalDate, OffsetTime, ZonedDateTime, Duration> {
    private static final DatatypeFactory DATA_TYPE_FACTORY = XMLDatataypeFactory.newInstance();

    private static final DoubleNumericType NUMERIC_TYPE = new DoubleNumericType(LOGGER);
    private static final DefaultBooleanType BOOLEAN_TYPE = new DefaultBooleanType(LOGGER);
    private static final DefaultStringType STRING_TYPE = new DefaultStringType(LOGGER);
    private static final LocalDateType DATE_TYPE = new LocalDateType(LOGGER, DATA_TYPE_FACTORY);
    private static final OffsetTimeType TIME_TYPE = new OffsetTimeType(LOGGER, DATA_TYPE_FACTORY);
    private static final ZonedDateTimeType DATE_TIME_TYPE = new ZonedDateTimeType(LOGGER, DATA_TYPE_FACTORY);
    private static final DoubleDurationType DURATION_TYPE = new DoubleDurationType(LOGGER, DATA_TYPE_FACTORY);
    private static final DefaultListType LIST_TYPE = new DefaultListType(LOGGER);
    private static final DefaultContextType CONTEXT_TYPE = new DefaultContextType(LOGGER);

    private static final DoubleNumericLib NUMERIC_LIB = new DoubleNumericLib();
    private static final DefaultStringLib STRING_LIB = new DefaultStringLib();
    private static final DefaultBooleanLib BOOLEAN_LIB = new DefaultBooleanLib();
    private static final LocalDateLib DATE_LIB = new LocalDateLib();
    private static final OffsetTimeLib TIME_LIB = new OffsetTimeLib(DATA_TYPE_FACTORY);
    private static final ZonedDateTimeLib DATE_TIME_LIB = new ZonedDateTimeLib();
    private static final DefaultDurationLib DURATION_LIB = new DefaultDurationLib(DATA_TYPE_FACTORY);
    private static final DefaultListLib LIST_LIB = new DefaultListLib();

    public static final DoubleMixedJavaTimeFEELLib INSTANCE = new DoubleMixedJavaTimeFEELLib();

    public DoubleMixedJavaTimeFEELLib() {
        super(NUMERIC_TYPE,
                BOOLEAN_TYPE,
                STRING_TYPE,
                DATE_TYPE,
                TIME_TYPE,
                DATE_TIME_TYPE,
                DURATION_TYPE,
                LIST_TYPE,
                CONTEXT_TYPE
        );
    }

    //
    // Constructors
    //

    @Override
    public Double number(String literal) {
        try {
            return NUMERIC_LIB.number(literal);
        } catch (Exception e) {
            String message = String.format("number(%s)", literal);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double number(String from, String groupingSeparator, String decimalSeparator) {
        try {
            return NUMERIC_LIB.number(from, groupingSeparator, decimalSeparator);
        } catch (Exception e) {
            String message = String.format("number(%s, %s, %s)", from, groupingSeparator, decimalSeparator);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String string(Object from) {
        try {
            return STRING_LIB.string(from);
        } catch (Exception e) {
            String message = String.format("string(%s)", from);
            logError(message, e);
            return null;
        }
    }

    @Override
    public LocalDate date(String literal) {
        try {
            return DATE_LIB.date(literal);
        } catch (Exception e) {
            String message = String.format("date(%s)", literal);
            logError(message, e);
            return null;
        }
    }

    @Override
    public LocalDate date(Double year, Double month, Double day) {
        try {
            return DATE_LIB.date(year, month, day);
        } catch (Exception e) {
            String message = String.format("date(%s, %s, %s)", year, month, day);
            logError(message, e);
            return null;
        }
    }

    @Override
    public LocalDate date(ZonedDateTime from) {
        try {
            return DATE_LIB.date(from);
        } catch (Exception e) {
            String message = String.format("date(%s)", from);
            logError(message, e);
            return null;
        }
    }
    public LocalDate date(LocalDate from) {
        try {
            return DATE_LIB.date(from);
        } catch (Exception e) {
            String message = String.format("date(%s)", from);
            logError(message, e);
            return null;
        }
    }

    @Override
    public OffsetTime time(String literal) {
        try {
            return TIME_LIB.time(literal);
        } catch (Exception e) {
            String message = String.format("time(%s)", literal);
            logError(message, e);
            return null;
        }
    }

    @Override
    public OffsetTime time(Double hour, Double minute, Double second, Duration offset) {
        try {
            return TIME_LIB.time(hour, minute, second, offset);
        } catch (Exception e) {
            String message = String.format("time(%s, %s, %s, %s)", hour, minute, second, offset);
            logError(message, e);
            return null;
        }
    }

    @Override
    public OffsetTime time(ZonedDateTime from) {
        try {
            return TIME_LIB.time(from);
        } catch (Exception e) {
            String message = String.format("time(%s)", from);
            logError(message, e);
            return null;
        }
    }
    public OffsetTime time(LocalDate from) {
        try {
            return TIME_LIB.time(from);
        } catch (Exception e) {
            String message = String.format("time(%s)", from);
            logError(message, e);
            return null;
        }
    }
    public OffsetTime time(OffsetTime from) {
        try {
            return TIME_LIB.time(from);
        } catch (Exception e) {
            String message = String.format("time(%s)", from);
            logError(message, e);
            return null;
        }
    }

    @Override
    public ZonedDateTime dateAndTime(String from) {
        try {
            return DATE_TIME_LIB.dateAndTime(from);
        } catch (Exception e) {
            String message = String.format("dateAndTime(%s)", from);
            logError(message, e);
            return null;
        }
    }

    @Override
    public ZonedDateTime dateAndTime(LocalDate date, OffsetTime time) {
        try {
            return DATE_TIME_LIB.dateAndTime(date, time);
        } catch (Exception e) {
            String message = String.format("dateAndTime(%s, %s)", date, time);
            logError(message, e);
            return null;
        }
    }
    public ZonedDateTime dateAndTime(Object date, OffsetTime time) {
        try {
            return DATE_TIME_LIB.dateAndTime(date, time);
        } catch (Exception e) {
            String message = String.format("dateAndTime(%s, %s)", date, time);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Duration duration(String from) {
        try {
            return DURATION_LIB.duration(from);
        } catch (Exception e) {
            String message = String.format("duration(%s)", from);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Duration yearsAndMonthsDuration(ZonedDateTime from, ZonedDateTime to) {
        try {
            return DURATION_LIB.yearsAndMonthsDuration(from, to);
        } catch (Exception e) {
            String message = String.format("yearsAndMonthsDuration(%s, %s)", from, to);
            logError(message, e);
            return null;
        }
    }

    public Duration yearsAndMonthsDuration(LocalDate from, LocalDate to) {
        try {
            return DURATION_LIB.yearsAndMonthsDuration(from, to);
        } catch (Exception e) {
            String message = String.format("yearsAndMonthsDuration(%s, %s)", from, to);
            logError(message, e);
            return null;
        }
    }

    public Duration yearsAndMonthsDuration(ZonedDateTime from, LocalDate to) {
        try {
            return DURATION_LIB.yearsAndMonthsDuration(from, to);
        } catch (Exception e) {
            String message = String.format("yearsAndMonthsDuration(%s, %s)", from, to);
            logError(message, e);
            return null;
        }
    }

    public Duration yearsAndMonthsDuration(LocalDate from, ZonedDateTime to) {
        try {
            return DURATION_LIB.yearsAndMonthsDuration(from, to);
        } catch (Exception e) {
            String message = String.format("yearsAndMonthsDuration(%s, %s)", from, to);
            logError(message, e);
            return null;
        }
    }

    @Override
    public LocalDate toDate(Object object) {
        try {
            return DATE_LIB.toDate(object);
        } catch (Exception e) {
            String message = String.format("toDate(%s)", object);
            logError(message, e);
            return null;
        }
    }

    @Override
    public OffsetTime toTime(Object object) {
        try {
            return TIME_LIB.toTime(object);
        } catch (Exception e) {
            String message = String.format("toTime(%s)", object);
            logError(message, e);
            return null;
        }
    }

    //
    // Numeric functions
    //
    @Override
    public Double decimal(Double n, Double scale) {
        try {
            return NUMERIC_LIB.decimal(n, scale);
        } catch (Exception e) {
            String message = String.format("decimal(%s, %s)", n, scale);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double floor(Double number) {
        try {
            return NUMERIC_LIB.floor(number);
        } catch (Exception e) {
            String message = String.format("floor(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double ceiling(Double number) {
        try {
            return NUMERIC_LIB.ceiling(number);
        } catch (Exception e) {
            String message = String.format("ceiling(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double abs(Double number) {
        try {
            return NUMERIC_LIB.abs(number);
        } catch (Exception e) {
            String message = String.format("abs(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double intModulo(Double dividend, Double divisor) {
        try {
            return NUMERIC_LIB.intModulo(dividend, divisor);
        } catch (Exception e) {
            String message = String.format("modulo(%s, %s)", dividend, divisor);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double modulo(Double dividend, Double divisor) {
        try {
            return NUMERIC_LIB.modulo(dividend, divisor);
        } catch (Exception e) {
            String message = String.format("modulo(%s, %s)", dividend, divisor);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double sqrt(Double number) {
        try {
            return NUMERIC_LIB.sqrt(number);
        } catch (Exception e) {
            String message = String.format("sqrt(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double log(Double number) {
        try {
            return NUMERIC_LIB.log(number);
        } catch (Exception e) {
            String message = String.format("log(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double exp(Double number) {
        try {
            return NUMERIC_LIB.exp(number);
        } catch (Exception e) {
            String message = String.format("exp(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean odd(Double number) {
        try {
            return NUMERIC_LIB.odd(number);
        } catch (Exception e) {
            String message = String.format("odd(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean even(Double number) {
        try {
            return NUMERIC_LIB.even(number);
        } catch (Exception e) {
            String message = String.format("even(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double mean(List list) {
        try {
            return NUMERIC_LIB.mean(list);
        } catch (Exception e) {
            String message = String.format("mean(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double mean(Object... args) {
        try {
            return NUMERIC_LIB.mean(args);
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
        try {
            return STRING_LIB.contains(string, match);
        } catch (Exception e) {
            String message = String.format("contains(%s, %s)", string, match);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean startsWith(String string, String match) {
        try {
            return STRING_LIB.startsWith(string, match);
        } catch (Exception e) {
            String message = String.format("startsWith(%s, %s)", string, match);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean endsWith(String string, String match) {
        try {
            return STRING_LIB.endsWith(string, match);
        } catch (Exception e) {
            String message = String.format("endsWith(%s, %s)", string, match);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double stringLength(String string) {
        try {
            return string == null ? null : Double.valueOf(STRING_LIB.stringLength(string));
        } catch (Exception e) {
            String message = String.format("stringLength(%s)", string);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String substring(String string, Double startPosition) {
        try {
            return STRING_LIB.substring(string, startPosition);
        } catch (Exception e) {
            String message = String.format("substring(%s, %s)", string, startPosition);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String substring(String string, Double startPosition, Double length) {
        try {
            return STRING_LIB.substring(string, startPosition, length);
        } catch (Exception e) {
            String message = String.format("substring(%s, %s, %s)", string, startPosition, length);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String upperCase(String string) {
        try {
            return STRING_LIB.upperCase(string);
        } catch (Exception e) {
            String message = String.format("upperCase(%s)", string);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String lowerCase(String string) {
        try {
            return STRING_LIB.lowerCase(string);
        } catch (Exception e) {
            String message = String.format("lowerCase(%s)", string);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String substringBefore(String string, String match) {
        try {
            return STRING_LIB.substringBefore(string, match);
        } catch (Exception e) {
            String message = String.format("substringBefore(%s, %s)", string, match);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String substringAfter(String string, String match) {
        try {
            return STRING_LIB.substringAfter(string, match);
        } catch (Exception e) {
            String message = String.format("substringAfter(%s, %s)", string, match);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String replace(String input, String pattern, String replacement) {
        return replace(input, pattern, replacement, "");
    }

    @Override
    public String replace(String input, String pattern, String replacement, String flags) {
        try {
            return STRING_LIB.replace(input, pattern, replacement, flags);
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
            return STRING_LIB.matches(input, pattern, flags);
        } catch (Exception e) {
            String message = String.format("matches(%s, %s, %s)", input, pattern, flags);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List split(String string, String delimiter) {
        try {
            return STRING_LIB.split(string, delimiter);
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
            return BOOLEAN_LIB.and(list);
        } catch (Exception e) {
            String message = String.format("and(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean and(Object... args) {
        try {
            return BOOLEAN_LIB.and(args);
        } catch (Exception e) {
            String message = String.format("and(%s)", args);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean all(List list) {
        try {
            return BOOLEAN_LIB.all(list);
        } catch (Exception e) {
            String message = String.format("all(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean all(Object... args) {
        try {
            return BOOLEAN_LIB.all(args);
        } catch (Exception e) {
            String message = String.format("all(%s)", args);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean or(List list) {
        try {
            return BOOLEAN_LIB.or(list);
        } catch (Exception e) {
            String message = String.format("or(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean or(Object... args) {
        try {
            return BOOLEAN_LIB.or(args);
        } catch (Exception e) {
            String message = String.format("or(%s)", args);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean any(List list) {
        try {
            return BOOLEAN_LIB.any(list);
        } catch (Exception e) {
            String message = String.format("any(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean any(Object... args) {
        try {
            return BOOLEAN_LIB.any(args);
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
    @Override
    public Double year(LocalDate date) {
        try {
            return Double.valueOf(DATE_LIB.year(date));
        } catch (Exception e) {
            String message = String.format("year(%s)", date);
            logError(message, e);
            return null;
        }
    }
    public Double year(ZonedDateTime dateTime) {
        try {
            return Double.valueOf(DATE_LIB.year(dateTime));
        } catch (Exception e) {
            String message = String.format("year(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double month(LocalDate date) {
        try {
            return Double.valueOf(DATE_LIB.month(date));
        } catch (Exception e) {
            String message = String.format("month(%s)", date);
            logError(message, e);
            return null;
        }
    }
    public Double month(ZonedDateTime dateTime) {
        try {
            return Double.valueOf(DATE_LIB.month(dateTime));
        } catch (Exception e) {
            String message = String.format("month(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double day(LocalDate date) {
        try {
            return Double.valueOf(DATE_LIB.day(date));
        } catch (Exception e) {
            String message = String.format("day(%s)", date);
            logError(message, e);
            return null;
        }
    }
    public Double day(ZonedDateTime dateTime) {
        try {
            return Double.valueOf(DATE_LIB.day(dateTime));
        } catch (Exception e) {
            String message = String.format("day(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }
    @Override
    public Double weekday(LocalDate date) {
        try {
            return Double.valueOf(DATE_LIB.weekday(date));
        } catch (Exception e) {
            String message = String.format("weekday(%s)", date);
            logError(message, e);
            return null;
        }
    }
    public Double weekday(ZonedDateTime dateTime) {
        try {
            return Double.valueOf(DATE_LIB.weekday(dateTime));
        } catch (Exception e) {
            String message = String.format("weekday(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    //
    // Time functions
    //
    @Override
    public Double hour(OffsetTime time) {
        try {
            return Double.valueOf(TIME_LIB.hour(time));
        } catch (Exception e) {
            String message = String.format("hour(%s)", time);
            logError(message, e);
            return null;
        }
    }
    public Double hour(ZonedDateTime dateTime) {
        try {
            return Double.valueOf(TIME_LIB.hour(dateTime));
        } catch (Exception e) {
            String message = String.format("hour(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double minute(OffsetTime time) {
        try {
            return Double.valueOf(TIME_LIB.minute(time));
        } catch (Exception e) {
            String message = String.format("minute(%s)", time);
            logError(message, e);
            return null;
        }
    }
    public Double minute(ZonedDateTime dateTime) {
        try {
            return Double.valueOf(TIME_LIB.minute(dateTime));
        } catch (Exception e) {
            String message = String.format("minute(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double second(OffsetTime time) {
        try {
            return Double.valueOf(TIME_LIB.second(time));
        } catch (Exception e) {
            String message = String.format("second(%s)", time);
            logError(message, e);
            return null;
        }
    }
    public Double second(ZonedDateTime dateTime) {
        try {
            return Double.valueOf(TIME_LIB.second(dateTime));
        } catch (Exception e) {
            String message = String.format("second(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Duration timeOffset(OffsetTime time) {
        try {
            return TIME_LIB.timeOffset(time);
        } catch (Exception e) {
            String message = String.format("timeOffset(%s)", time);
            logError(message, e);
            return null;
        }
    }
    public Duration timeOffset(ZonedDateTime dateTime) {
        try {
            return TIME_LIB.timeOffset(dateTime);
        } catch (Exception e) {
            String message = String.format("timeOffset(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String timezone(OffsetTime time) {
        try {
            return TIME_LIB.timezone(time);
        } catch (Exception e) {
            String message = String.format("timezone(%s)", time);
            logError(message, e);
            return null;
        }
    }
    public String timezone(ZonedDateTime dateTime) {
        try {
            return TIME_LIB.timezone(dateTime);
        } catch (Exception e) {
            String message = String.format("timezone(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    //
    // Duration functions
    //
    @Override
    public Double years(Duration duration) {
        try {
            return Double.valueOf(DURATION_LIB.years(duration));
        } catch (Exception e) {
            String message = String.format("years(%s)", duration);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double months(Duration duration) {
        try {
            return Double.valueOf(DURATION_LIB.months(duration));
        } catch (Exception e) {
            String message = String.format("months(%s)", duration);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double days(Duration duration) {
        try {
            return Double.valueOf(DURATION_LIB.days(duration));
        } catch (Exception e) {
            String message = String.format("days(%s)", duration);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double hours(Duration duration) {
        try {
            return Double.valueOf(DURATION_LIB.hours(duration));
        } catch (Exception e) {
            String message = String.format("hours(%s)", duration);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double minutes(Duration duration) {
        try {
            return Double.valueOf(DURATION_LIB.minutes(duration));
        } catch (Exception e) {
            String message = String.format("minutes(%s)", duration);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double seconds(Duration duration) {
        try {
            return Double.valueOf(DURATION_LIB.seconds(duration));
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
        try {
            return LIST_LIB.listContains(list, element);
        } catch (Exception e) {
            String message = String.format("listContains(%s, %s)", list, element);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List append(List list, Object... items) {
        try {
            return LIST_LIB.append(list, items);
        } catch (Exception e) {
            String message = String.format("append(%s, %s)", list, items);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double count(List list) {
        try {
            return NUMERIC_LIB.count(list);
        } catch (Exception e) {
            String message = String.format("count(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double min(List list) {
        try {
            return NUMERIC_LIB.min(list);
        } catch (Exception e) {
            String message = String.format("min(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double min(Object... args) {
        try {
            return NUMERIC_LIB.min(args);
        } catch (Exception e) {
            String message = String.format("min(%s)", args);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double max(List list) {
        try {
            return NUMERIC_LIB.max(list);
        } catch (Exception e) {
            String message = String.format("max(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double max(Object... args) {
        try {
            return NUMERIC_LIB.max(args);
        } catch (Exception e) {
            String message = String.format("max(%s)", args);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double sum(List list) {
        try {
            return NUMERIC_LIB.sum(list);
        } catch (Exception e) {
            String message = String.format("sum(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double sum(Object... args) {
        try {
            return NUMERIC_LIB.sum(args);
        } catch (Exception e) {
            String message = String.format("sum(%s)", args);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List sublist(List list, Double startPosition) {
        try {
            return LIST_LIB.sublist(list, startPosition.intValue());
        } catch (Exception e) {
            String message = String.format("sublist(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List sublist(List list, Double startPosition, Double length) {
        try {
            return LIST_LIB.sublist(list, startPosition.intValue(), length.intValue());
        } catch (Exception e) {
            String message = String.format("sublist(%s, %s, %s)", list, startPosition, length);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List concatenate(Object... lists) {
        try {
            return LIST_LIB.concatenate(lists);
        } catch (Exception e) {
            String message = String.format("concatenate(%s)", lists);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List insertBefore(List list, Double position, Object newItem) {
        try {
            return LIST_LIB.insertBefore(list, position.intValue(), newItem);
        } catch (Exception e) {
            String message = String.format("insertBefore(%s, %s, %s)", list, position, newItem);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List remove(List list, Object position) {
        try {
            return LIST_LIB.remove(list, ((Number)position).intValue());
        } catch (Exception e) {
            String message = String.format("remove(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List reverse(List list) {
        try {
            return LIST_LIB.reverse(list);
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
            return LIST_LIB.union(lists);
        } catch (Exception e) {
            String message = String.format("union(%s)", lists);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List distinctValues(List list) {
        try {
            return LIST_LIB.distinctValues(list);
        } catch (Exception e) {
            String message = String.format("distinctValues(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List flatten(List list) {
        try {
            return LIST_LIB.flatten(list);
        } catch (Exception e) {
            String message = String.format("flatten(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double product(List list) {
        try {
            return NUMERIC_LIB.product(list);
        } catch (Exception e) {
            String message = String.format("product(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double product(Object... args) {
        try {
            return NUMERIC_LIB.product(args);
        } catch (Exception e) {
            String message = String.format("product(%s)", args);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double median(List list) {
        try {
            return NUMERIC_LIB.median(list);
        } catch (Exception e){
            String message = String.format("median(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double median(Object... args) {
        try {
            return NUMERIC_LIB.median(args);
        } catch (Exception e) {
            String message = String.format("median(%s)", args);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double stddev(List list) {
        try {
            return NUMERIC_LIB.stddev(list);
        } catch (Exception e) {
            String message = String.format("stddev(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double stddev(Object... args) {
        try {
            return NUMERIC_LIB.stddev(args);
        } catch (Exception e) {
            String message = String.format("stddev(%s)", args);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List mode(List list) {
        try {
            return NUMERIC_LIB.mode(list);
        } catch (Exception e) {
            String message = String.format("mode(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List mode(Object... args) {
        try {
            return NUMERIC_LIB.mode(args);
        } catch (Exception e) {
            String message = String.format("mode(%s)", args);
            logError(message, e);
            return null;
        }
    }

    @Override
    public void collect(List result, List list) {
        try {
            LIST_LIB.collect(result, list);
        } catch (Exception e) {
            String message = String.format("collect(%s, %s)", result, list);
            logError(message, e);
        }
    }

    @Override
    public <T> List<T> sort(List<T> list, LambdaExpression<Boolean> comparator) {
        try {
            return LIST_LIB.sort(list, comparator);
        } catch (Exception e) {
            String message = String.format("sort(%s)", list);
            logError(message, e);
            return null;
        }
    }
}
