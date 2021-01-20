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

import com.gs.dmn.feel.lib.type.*;
import com.gs.dmn.feel.lib.type.bool.BooleanLib;
import com.gs.dmn.feel.lib.type.list.ListLib;
import com.gs.dmn.feel.lib.type.numeric.NumericLib;
import com.gs.dmn.feel.lib.type.string.StringLib;
import com.gs.dmn.feel.lib.type.time.DateTimeLib;
import com.gs.dmn.feel.lib.type.time.DurationLib;
import com.gs.dmn.runtime.LambdaExpression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class BaseStandardFEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends BaseFEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> implements StandardFEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> {
    private final NumericLib<NUMBER> numberLib;
    private final StringLib stringLib;
    private final BooleanLib booleanLib;
    protected final DateTimeLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> dateTimeLib;
    protected final DurationLib<DATE, DURATION> durationLib;
    private final ListLib listLib;

    protected BaseStandardFEELLib(
            NumericType<NUMBER> numericType, BooleanType booleanType, StringType stringType,
            DateType<DATE, DURATION> dateType, TimeType<TIME, DURATION> timeType, DateTimeType<DATE_TIME, DURATION> dateTimeType, DurationType<DURATION, NUMBER> durationType,
            ListType listType, ContextType contextType,
            NumericLib<NUMBER> numberLib,
            StringLib stringLib,
            BooleanLib booleanLib,
            DateTimeLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> dateTimeLib,
            DurationLib<DATE, DURATION> durationLib,
            ListLib listLib) {
        super(numericType, booleanType, stringType, dateType, timeType, dateTimeType, durationType, listType, contextType);
        this.numberLib = numberLib;
        this.stringLib = stringLib;
        this.booleanLib = booleanLib;
        this.dateTimeLib = dateTimeLib;
        this.durationLib = durationLib;
        this.listLib = listLib;
    }

    //
    // Constructors
    //

    @Override
    public NUMBER number(String literal) {
        try {
            return this.numberLib.number(literal);
        } catch (Exception e) {
            String message = String.format("number(%s)", literal);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER number(String from, String groupingSeparator, String decimalSeparator) {
        try {
            return this.numberLib.number(from, groupingSeparator, decimalSeparator);
        } catch (Exception e) {
            String message = String.format("number(%s, %s, %s)", from, groupingSeparator, decimalSeparator);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String string(Object from) {
        try {
            return this.stringLib.string(from);
        } catch (Exception e) {
            String message = String.format("string(%s)", from);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DATE date(String literal) {
        try {
            return this.dateTimeLib.date(literal);
        } catch (Exception e) {
            String message = String.format("date(%s)", literal);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DATE date(NUMBER year, NUMBER month, NUMBER day) {
        try {
            return this.dateTimeLib.date(year, month, day);
        } catch (Exception e) {
            String message = String.format("date(%s, %s, %s)", year, month, day);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DATE date(DATE from) {
        try {
            return this.dateTimeLib.date(from);
        } catch (Exception e) {
            String message = String.format("date(%s)", from);
            logError(message, e);
            return null;
        }
    }

    @Override
    public TIME time(String literal) {
        try {
            return this.dateTimeLib.time(literal);
        } catch (Exception e) {
            String message = String.format("time(%s)", literal);
            logError(message, e);
            return null;
        }
    }

    @Override
    public TIME time(NUMBER hour, NUMBER minute, NUMBER second, DURATION offset) {
        try {
            return this.dateTimeLib.time(hour, minute, second, offset);
        } catch (Exception e) {
            String message = String.format("time(%s, %s, %s, %s)", hour, minute, second, offset);
            logError(message, e);
            return null;
        }
    }

    @Override
    public TIME time(TIME from) {
        try {
            return this.dateTimeLib.time(from);
        } catch (Exception e) {
            String message = String.format("time(%s)", from);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DATE_TIME dateAndTime(String from) {
        try {
            return this.dateTimeLib.dateAndTime(from);
        } catch (Exception e) {
            String message = String.format("dateAndTime(%s)", from);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DATE_TIME dateAndTime(DATE date, TIME time) {
        try {
            return this.dateTimeLib.dateAndTime(date, time);
        } catch (Exception e) {
            String message = String.format("dateAndTime(%s, %s)", date, time);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DURATION duration(String from) {
        try {
            return this.durationLib.duration(from);
        } catch (Exception e) {
            String message = String.format("duration(%s)", from);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DURATION yearsAndMonthsDuration(DATE from, DATE to) {
        try {
            return this.durationLib.yearsAndMonthsDuration(from, to);
        } catch (Exception e) {
            String message = String.format("yearsAndMonthsDURATION(%s, %s)", from, to);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DATE toDate(Object object) {
        try {
            return this.dateTimeLib.toDate(object);
        } catch (Exception e) {
            String message = String.format("toDate(%s)", object);
            logError(message, e);
            return null;
        }
    }

    @Override
    public TIME toTime(Object object) {
        try {
            return this.dateTimeLib.toTime(object);
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
    public NUMBER decimal(NUMBER n, NUMBER scale) {
        try {
            return this.numberLib.decimal(n, scale);
        } catch (Exception e) {
            String message = String.format("decimal(%s, %s)", n, scale);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER round(NUMBER n, NUMBER scale, String mode) {
        try {
            return this.numberLib.round(n, scale, mode);
        } catch (Exception e) {
            String message = String.format("round(%s, %s, %s)", n, scale, mode);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER floor(NUMBER number) {
        try {
            return this.numberLib.floor(number);
        } catch (Exception e) {
            String message = String.format("floor(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER ceiling(NUMBER number) {
        try {
            return this.numberLib.ceiling(number);
        } catch (Exception e) {
            String message = String.format("ceiling(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER abs(NUMBER number) {
        try {
            return this.numberLib.abs(number);
        } catch (Exception e) {
            String message = String.format("abs(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER intModulo(NUMBER dividend, NUMBER divisor) {
        try {
            return this.numberLib.intModulo(dividend, divisor);
        } catch (Exception e) {
            String message = String.format("modulo(%s, %s)", dividend, divisor);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER modulo(NUMBER dividend, NUMBER divisor) {
        try {
            return this.numberLib.modulo(dividend, divisor);
        } catch (Exception e) {
            String message = String.format("modulo(%s, %s)", dividend, divisor);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER sqrt(NUMBER number) {
        try {
            return this.numberLib.sqrt(number);
        } catch (Exception e) {
            String message = String.format("sqrt(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER log(NUMBER number) {
        try {
            return this.numberLib.log(number);
        } catch (Exception e) {
            String message = String.format("log(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER exp(NUMBER number) {
        try {
            return this.numberLib.exp(number);
        } catch (Exception e) {
            String message = String.format("exp(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean odd(NUMBER number) {
        try {
            return this.numberLib.odd(number);
        } catch (Exception e) {
            String message = String.format("odd(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean even(NUMBER number) {
        try {
            return this.numberLib.even(number);
        } catch (Exception e) {
            String message = String.format("even(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER mean(List list) {
        try {
            return this.numberLib.mean(list);
        } catch (Exception e) {
            String message = String.format("mean(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER mean(Object... args) {
        try {
            return this.numberLib.mean(args);
        } catch (Exception e) {
            String message = String.format("mean(%s)", Arrays.toString(args));
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
            return this.stringLib.contains(string, match);
        } catch (Exception e) {
            String message = String.format("contains(%s, %s)", string, match);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean startsWith(String string, String match) {
        try {
            return this.stringLib.startsWith(string, match);
        } catch (Exception e) {
            String message = String.format("startsWith(%s, %s)", string, match);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean endsWith(String string, String match) {
        try {
            return this.stringLib.endsWith(string, match);
        } catch (Exception e) {
            String message = String.format("endsWith(%s, %s)", string, match);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER stringLength(String string) {
        try {
            return string == null ? null : valueOf(this.stringLib.stringLength(string));
        } catch (Exception e) {
            String message = String.format("stringLength(%s)", string);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String substring(String string, NUMBER startPosition) {
        try {
            return this.stringLib.substring(string, this.numberLib.toNumber(startPosition));
        } catch (Exception e) {
            String message = String.format("substring(%s, %s)", string, startPosition);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String substring(String string, NUMBER startPosition, NUMBER length) {
        try {
            return this.stringLib.substring(string, this.numberLib.toNumber(startPosition), this.numberLib.toNumber(length));
        } catch (Exception e) {
            String message = String.format("substring(%s, %s, %s)", string, startPosition, length);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String upperCase(String string) {
        try {
            return this.stringLib.upperCase(string);
        } catch (Exception e) {
            String message = String.format("upperCase(%s)", string);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String lowerCase(String string) {
        try {
            return this.stringLib.lowerCase(string);
        } catch (Exception e) {
            String message = String.format("lowerCase(%s)", string);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String substringBefore(String string, String match) {
        try {
            return this.stringLib.substringBefore(string, match);
        } catch (Exception e) {
            String message = String.format("substringBefore(%s, %s)", string, match);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String substringAfter(String string, String match) {
        try {
            return this.stringLib.substringAfter(string, match);
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
            String message = String.format("and(%s)", Arrays.toString(args));
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
            String message = String.format("all(%s)", Arrays.toString(args));
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
            String message = String.format("or(%s)", Arrays.toString(args));
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
            String message = String.format("any(%s)", Arrays.toString(args));
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
    public NUMBER year(DATE date) {
        try {
            return valueOf(this.dateTimeLib.year(date));
        } catch (Exception e) {
            String message = String.format("year(%s)", date);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER month(DATE date) {
        try {
            return valueOf(this.dateTimeLib.month(date));
        } catch (Exception e) {
            String message = String.format("month(%s)", date);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER day(DATE date) {
        try {
            return valueOf(this.dateTimeLib.day(date));
        } catch (Exception e) {
            String message = String.format("day(%s)", date);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER weekday(DATE date) {
        try {
            return valueOf(this.dateTimeLib.weekday(date));
        } catch (Exception e) {
            String message = String.format("weekday(%s)", date);
            logError(message, e);
            return null;
        }
    }

    //
    // Time functions
    //
    @Override
    public NUMBER hour(TIME time) {
        try {
            return valueOf(this.dateTimeLib.hour(time));
        } catch (Exception e) {
            String message = String.format("hour(%s)", time);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER minute(TIME time) {
        try {
            return valueOf(this.dateTimeLib.minute(time));
        } catch (Exception e) {
            String message = String.format("minute(%s)", time);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER second(TIME time) {
        try {
            return valueOf(this.dateTimeLib.second(time));
        } catch (Exception e) {
            String message = String.format("second(%s)", time);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DURATION timeOffset(TIME time) {
        try {
            return this.dateTimeLib.timeOffset(time);
        } catch (Exception e) {
            String message = String.format("timeOffset(%s)", time);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String timezone(TIME time) {
        try {
            return this.dateTimeLib.timezone(time);
        } catch (Exception e) {
            String message = String.format("timezone(%s)", time);
            logError(message, e);
            return null;
        }
    }

    //
    // DURATION functions
    //
    @Override
    public NUMBER years(DURATION duration) {
        try {
            return valueOf(this.durationLib.years(duration));
        } catch (Exception e) {
            String message = String.format("years(%s)", duration);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER months(DURATION duration) {
        try {
            return valueOf(this.durationLib.months(duration));
        } catch (Exception e) {
            String message = String.format("months(%s)", duration);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER days(DURATION duration) {
        try {
            return valueOf(this.durationLib.days(duration));
        } catch (Exception e) {
            String message = String.format("days(%s)", duration);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER hours(DURATION duration) {
        try {
            return valueOf(this.durationLib.hours(duration));
        } catch (Exception e) {
            String message = String.format("hours(%s)", duration);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER minutes(DURATION duration) {
        try {
            return valueOf(this.durationLib.minutes(duration));
        } catch (Exception e) {
            String message = String.format("minutes(%s)", duration);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER seconds(DURATION duration) {
        try {
            return valueOf(this.durationLib.seconds(duration));
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
            String message = String.format("append(%s, %s)", list, Arrays.toString(items));
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER count(List list) {
        try {
            return this.numberLib.count(list);
        } catch (Exception e) {
            String message = String.format("count(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER min(List list) {
        try {
            return this.numberLib.min(list);
        } catch (Exception e) {
            String message = String.format("min(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER min(Object... args) {
        try {
            return this.numberLib.min(args);
        } catch (Exception e) {
            String message = String.format("min(%s)", Arrays.toString(args));
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER max(List list) {
        try {
            return this.numberLib.max(list);
        } catch (Exception e) {
            String message = String.format("max(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER max(Object... args) {
        try {
            return this.numberLib.max(args);
        } catch (Exception e) {
            String message = String.format("max(%s)", Arrays.toString(args));
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER sum(List list) {
        try {
            return this.numberLib.sum(list);
        } catch (Exception e) {
            String message = String.format("sum(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER sum(Object... args) {
        try {
            return this.numberLib.sum(args);
        } catch (Exception e) {
            String message = String.format("sum(%s)", Arrays.toString(args));
            logError(message, e);
            return null;
        }
    }

    @Override
    public List sublist(List list, NUMBER startPosition) {
        try {
            return this.listLib.sublist(list, intValue(startPosition));
        } catch (Exception e) {
            String message = String.format("sublist(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List sublist(List list, NUMBER startPosition, NUMBER length) {
        try {
            return this.listLib.sublist(list, intValue(startPosition), intValue(length));
        } catch (Exception e) {
            String message = String.format("sublist(%s, %s, %s)", list, startPosition, length);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List concatenate(Object... lists) {
        try {
            return this.listLib.concatenate(lists);
        } catch (Exception e) {
            String message = String.format("concatenate(%s)", Arrays.toString(lists));
            logError(message, e);
            return null;
        }
    }

    @Override
    public List insertBefore(List list, NUMBER position, Object newItem) {
        try {
            return this.listLib.insertBefore(list, intValue(position), newItem);
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
            String message = String.format("remove(%s)", list);
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
                    result.add(valueOf((long) i + 1));
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
            String message = String.format("union(%s)", Arrays.toString(lists));
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
    public NUMBER product(List list) {
        try {
            return this.numberLib.product(list);
        } catch (Exception e) {
            String message = String.format("product(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER product(Object... args) {
        try {
            return this.numberLib.product(args);
        } catch (Exception e) {
            String message = String.format("product(%s)", Arrays.toString(args));
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER median(List list) {
        try {
            return this.numberLib.median(list);
        } catch (Exception e){
            String message = String.format("median(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER median(Object... args) {
        try {
            return this.numberLib.median(args);
        } catch (Exception e) {
            String message = String.format("median(%s)", Arrays.toString(args));
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER stddev(List list) {
        try {
            return this.numberLib.stddev(list);
        } catch (Exception e) {
            String message = String.format("stddev(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER stddev(Object... args) {
        try {
            return this.numberLib.stddev(args);
        } catch (Exception e) {
            String message = String.format("stddev(%s)", Arrays.toString(args));
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
    public List mode(Object... args) {
        try {
            return this.numberLib.mode(args);
        } catch (Exception e) {
            String message = String.format("mode(%s)", Arrays.toString(args));
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
            String message = String.format("sort(%s)", list);
            logError(message, e);
            return null;
        }
    }
}
