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
package com.gs.dmn.signavio.feel.lib;

import com.gs.dmn.feel.lib.BaseFEELLib;
import com.gs.dmn.feel.lib.type.bool.BooleanLib;
import com.gs.dmn.feel.lib.type.bool.BooleanType;
import com.gs.dmn.feel.lib.type.context.ContextType;
import com.gs.dmn.feel.lib.type.function.FunctionType;
import com.gs.dmn.feel.lib.type.list.ListType;
import com.gs.dmn.feel.lib.type.numeric.NumericType;
import com.gs.dmn.feel.lib.type.range.RangeType;
import com.gs.dmn.feel.lib.type.string.StringType;
import com.gs.dmn.feel.lib.type.time.*;
import com.gs.dmn.signavio.feel.lib.type.list.SignavioListLib;
import com.gs.dmn.signavio.feel.lib.type.numeric.SignavioNumberLib;
import com.gs.dmn.signavio.feel.lib.type.string.SignavioStringLib;
import com.gs.dmn.signavio.feel.lib.type.time.SignavioDateTimeLib;

import java.util.List;

public abstract class BaseSignavioLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends BaseFEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> implements SignavioLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> {
    private final SignavioNumberLib<NUMBER> numberLib;
    private final SignavioStringLib stringLib;
    private final BooleanLib booleanLib;
    protected final SignavioDateTimeLib<NUMBER, DATE, TIME, DATE_TIME> dateTimeLib;
    protected final DurationLib<DATE, DURATION> durationLib;
    private final SignavioListLib<NUMBER> listLib;

    protected BaseSignavioLib(
            NumericType<NUMBER> numericType, BooleanType booleanType, StringType stringType,
            DateType<DATE, DURATION> dateType, TimeType<TIME, DURATION> timeType, DateTimeType<DATE_TIME, DURATION> dateTimeType, DurationType<DURATION, NUMBER> durationType,
            ListType listType, ContextType contextType, RangeType rangeType, FunctionType functionType,
            SignavioNumberLib<NUMBER> numberLib,
            SignavioStringLib stringLib,
            BooleanLib booleanLib,
            SignavioDateTimeLib<NUMBER, DATE, TIME, DATE_TIME> dateTimeLib,
            DurationLib<DATE, DURATION> durationLib,
            SignavioListLib<NUMBER> listLib) {
        super(numericType, booleanType, stringType, dateType, timeType, dateTimeType, durationType, listType, contextType, rangeType, functionType);
        this.numberLib = numberLib;
        this.stringLib = stringLib;
        this.booleanLib = booleanLib;
        this.dateTimeLib = dateTimeLib;
        this.durationLib = durationLib;
        this.listLib = listLib;
    }

    //
    // Data acceptance functions
    //
    @Override
    public Boolean isDefined(Object object) {
        return object != null;
    }

    @Override
    public Boolean isUndefined(Object object) {
        return object == null;
    }

    @Override
    public Boolean isValid(Object object) {
        return object != null;
    }

    @Override
    public Boolean isInvalid(Object object) {
        return object == null;
    }

    @Override
    public String string(Object from) {
        try {
            return this.stringLib.string(from);
        } catch (Exception e) {
            String message = "string(%s)".formatted(from);
            logError(message, e);
            return null;
        }
    }

    //
    // Arithmetic functions
    //
    @Override
    public NUMBER number(String text) {
        try {
            return this.numberLib.number(text);
        } catch (Exception e) {
            String message = "number(%s)".formatted(text);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER number(String text, NUMBER defaultValue) {
        try {
            return this.numberLib.number(text, defaultValue);
        } catch (Exception e) {
            String message = "number(%s, %s)".formatted(text, defaultValue);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER abs(NUMBER number) {
        try {
            return this.numberLib.abs(number);
        } catch (Exception e) {
            String message = "abs(%s)".formatted(number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER count(List<?> list) {
        try {
            return this.numberLib.count(list);
        } catch (Exception e) {
            String message = "count(%s)".formatted(list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER round(NUMBER number, NUMBER digits) {
        try {
            return this.numberLib.round(number, digits);
        } catch (Exception e) {
            String message = "round(%s, %s)".formatted(number, digits);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER ceiling(NUMBER number) {
        try {
            return this.numberLib.ceiling(number);
        } catch (Exception e) {
            String message = "integer(%s)".formatted(number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER floor(NUMBER number) {
        try {
            return this.numberLib.floor(number);
        } catch (Exception e) {
            String message = "floor(%s)".formatted(number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER integer(NUMBER number) {
        try {
            return this.numberLib.integer(number);
        } catch (Exception e) {
            String message = "integer(%s)".formatted(number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER modulo(NUMBER dividend, NUMBER divisor) {
        try {
            return this.numberLib.modulo(dividend, divisor);
        } catch (Exception e) {
            String message = "modulo(%s, %s)".formatted(dividend, divisor);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER percent(NUMBER number) {
        try {
            return this.numberLib.percent(number);
        } catch (Exception e) {
            String message = "percent(%s)".formatted(number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER power(NUMBER base, NUMBER exponent) {
        try {
            return this.numberLib.power(base, exponent);
        } catch (Exception e) {
            String message = "power(%s, %s)".formatted(base, exponent);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER product(List<?> numbers) {
        try {
            return this.numberLib.product(numbers);
        } catch (Exception e) {
            String message = "product(%s)".formatted(numbers);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER roundDown(NUMBER number, NUMBER digits) {
        try {
            return this.numberLib.roundDown(number, digits);
        } catch (Exception e) {
            String message = "roundDown(%s, %s)".formatted(number, digits);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER roundUp(NUMBER number, NUMBER digits) {
        try {
            return this.numberLib.roundUp(number, digits);
        } catch (Exception e) {
            String message = "roundUp(%s, %s)".formatted(number, digits);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER sum(List<?> numbers) {
        try {
            return this.numberLib.sum(numbers);
        } catch (Exception e) {
            String message = "sum(%s)".formatted(numbers);
            logError(message, e);
            return null;
        }
    }

    //
    // Date and time operations
    //
    @Override
    public DATE date(String literal) {
        try {
            return this.dateTimeLib.date(literal);
        } catch (Exception e) {
            String message = "date(%s)".formatted(literal);
            logError(message, e);
            return null;
        }
    }

    @Override
    public TIME time(String literal) {
        try {
            return this.dateTimeLib.time(literal);
        } catch (Exception e) {
            String message = "time(%s)".formatted(literal);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DATE_TIME dateAndTime(String literal) {
        try {
            return this.dateTimeLib.dateAndTime(literal);
        } catch (Exception e) {
            String message = "dateAndTime(%s)".formatted(literal);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER day(Object date) {
        try {
            return valueOf(this.dateTimeLib.day(date));
        } catch (Exception e) {
            String message = "day(%s)".formatted(date);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DATE dayAdd(DATE date, NUMBER daysToAdd) {
        try {
            return this.dateTimeLib.dayAdd(date, daysToAdd);
        } catch (Exception e) {
            String message = "dayAdd(%s, %s)".formatted(date, daysToAdd);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER dayDiff(Object date1, Object date2) {
        try {
            return this.numberLib.valueOf(this.dateTimeLib.dayDiff(date1, date2));
        } catch (Exception e) {
            String message = "dayDiff(%s, %s)".formatted(date1, date2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DATE date(NUMBER year, NUMBER month, NUMBER day) {
        try {
            if (year == null || month == null || day == null) {
                return null;
            }
            if (this.numberLib.intValue(year) < 0 || this.numberLib.intValue(month) < 0 || this.numberLib.intValue(day) < 0) {
                return null;
            }

            String literal = "%04d-%02d-%02d".formatted(this.numberLib.intValue(year), this.numberLib.intValue(month), this.numberLib.intValue(day));
            return this.dateTimeLib.date(literal);
        } catch (Exception e) {
            String message = "date(%s, %s, %s)".formatted(year, month, day);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DATE_TIME dateTime(NUMBER day, NUMBER month, NUMBER year, NUMBER hour, NUMBER minute, NUMBER second) {
        try {
            if (year == null || month == null || day == null || minute == null || second == null) {
                return null;
            }
            if (this.numberLib.intValue(year) < 0 || this.numberLib.intValue(month) < 0 || this.numberLib.intValue(day) < 0 || this.numberLib.intValue(minute) < 0 || this.numberLib.intValue(second) < 0) {
                return null;
            }

            String literal = "%04d-%02d-%02dT%02d:%02d:%02dZ".formatted(
                this.numberLib.intValue(year), this.numberLib.intValue(month), this.numberLib.intValue(day), this.numberLib.intValue(hour), this.numberLib.intValue(minute), this.numberLib.intValue(second));
            return this.dateTimeLib.dateAndTime(literal);
        } catch (Exception e) {
            String message = "dateTime(%s, %s, %s, %s, %s, %s)".formatted(day, month, year, hour, minute, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DATE_TIME dateTime(NUMBER day, NUMBER month, NUMBER year, NUMBER hour, NUMBER minute, NUMBER second, NUMBER hourOffset) {
        try {
            if (year == null || month == null || day == null || minute == null || second == null || hourOffset == null) {
                return null;
            }
            if (this.numberLib.intValue(year) < 0 || this.numberLib.intValue(month) < 0 || this.numberLib.intValue(day) < 0 || this.numberLib.intValue(minute) < 0 || this.numberLib.intValue(second) < 0) {
                return null;
            }

            String literal = "%04d-%02d-%02dT%02d:%02d:%02d%+03d:00".formatted(
                this.numberLib.intValue(year), this.numberLib.intValue(month), this.numberLib.intValue(day), this.numberLib.intValue(hour), this.numberLib.intValue(minute), this.numberLib.intValue(second), this.numberLib.intValue(hourOffset));
            return this.dateTimeLib.dateAndTime(literal);
        } catch (Exception e) {
            String message = "dateTime(%s, %s, %s, %s, %s, %s, %s)".formatted(day, month, year, hour, minute, second, hourOffset);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER hour(Object time) {
        try {
            return this.numberLib.valueOf(this.dateTimeLib.hour(time));
        } catch (Exception e) {
            String message = "hour(%s)".formatted(time);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER hourDiff(Object time1, Object time2) {
        try {
            return this.numberLib.valueOf(this.dateTimeLib.hourDiff(time1, time2));
        } catch (Exception e) {
            String message = "hourDiff(%s, %s)".formatted(time1, time2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER minute(Object time) {
        try {
            return this.numberLib.valueOf(this.dateTimeLib.minute(time));
        } catch (Exception e) {
            String message = "minute(%s)".formatted(time);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER minutesDiff(Object time1, Object time2) {
        try {
            return this.numberLib.valueOf(this.dateTimeLib.minutesDiff(time1, time2));
        } catch (Exception e) {
            String message = "minutesDiff(%s, %s)".formatted(time1, time2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER month(Object date) {
        try {
            return this.numberLib.valueOf(this.dateTimeLib.month(date));
        } catch (Exception e) {
            String message = "month(%s)".formatted(date);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DATE monthAdd(DATE date, NUMBER monthsToAdd) {
        try {
            return this.dateTimeLib.monthAdd(date, monthsToAdd);
        } catch (Exception e) {
            String message = "monthAdd(%s, %s)".formatted(date, monthsToAdd);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER monthDiff(Object date1, Object date2) {
        try {
            return this.numberLib.valueOf(this.dateTimeLib.monthDiff(date1, date2));
        } catch (Exception e) {
            String message = "monthDiff(%s, %s)".formatted(date1, date2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DATE_TIME now() {
        try {
            return this.dateTimeLib.now();
        } catch (Exception e) {
            String message = "now()";
            logError(message, e);
            return null;
        }
    }

    @Override
    public DATE today() {
        try {
            return this.dateTimeLib.today();
        } catch (Exception e) {
            String message = "today()";
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER weekday(Object date) {
        try {
            return this.numberLib.valueOf(this.dateTimeLib.weekday(date));
        } catch (Exception e) {
            String message = "weekday(%s)".formatted(date);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER year(Object date) {
        try {
            return this.numberLib.valueOf(this.dateTimeLib.year(date));
        } catch (Exception e) {
            String message = "year(%s)".formatted(date);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DATE yearAdd(DATE localDate, NUMBER yearsToAdd) {
        try {
            return this.dateTimeLib.yearAdd(localDate, yearsToAdd);
        } catch (Exception e) {
            String message = "yearAdd(%s, %s)".formatted(localDate, yearsToAdd);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER yearDiff(Object dateTime1, Object dateTime2) {
        try {
            return this.numberLib.valueOf(this.dateTimeLib.yearDiff(dateTime1, dateTime2));
        } catch (Exception e) {
            String message = "yearDiff(%s, %s)".formatted(dateTime1, dateTime2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DURATION duration(String from) {
        try {
            return this.durationLib.duration(from);
        } catch (Exception e) {
            String message = "duration(%s)".formatted(from);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String mid(String text, NUMBER start, NUMBER numChar) {
        try {
            return this.stringLib.mid(text, this.numberLib.toNumber(start), this.numberLib.toNumber(numChar));
        } catch (Exception e) {
            String message = "mid(%s, %s, %s)".formatted(text, start, numChar);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String left(String text, NUMBER numChar) {
        try {
            return this.stringLib.left(text, this.numberLib.toNumber(numChar));
        } catch (Exception e) {
            String message = "left(%s, %s)".formatted(text, numChar);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String right(String text, NUMBER numChar) {
        try {
            return this.stringLib.right(text, this.numberLib.toNumber(numChar));
        } catch (Exception e) {
            String message = "right(%s, %s)".formatted(text, numChar);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String text(NUMBER num, String formatText) {
        try {
            return this.stringLib.text(this.numberLib.toNumber(num), formatText);
        } catch (Exception e) {
            String message = "text(%s, %s)".formatted(num, formatText);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER textOccurrences(String findText, String withinText) {
        try {
            return this.numberLib.valueOf(this.stringLib.textOccurrences(findText, withinText));
        } catch (Exception e) {
            String message = "textOccurrences(%s, %s)".formatted(findText, withinText);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean contains(String text, String substring) {
        try {
            return this.stringLib.contains(text, substring);
        } catch (Exception e) {
            String message = "contains(%s, %s)".formatted(text, substring);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean startsWith(String text, String prefix) {
        try {
            return this.stringLib.startsWith(text, prefix);
        } catch (Exception e) {
            String message = "startsWith(%s, %s)".formatted(text, prefix);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean endsWith(String text, String suffix) {
        try {
            return this.stringLib.endsWith(text, suffix);
        } catch (Exception e) {
            String message = "endsWith(%s, %s)".formatted(text, suffix);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean not(Boolean bool) {
        try {
            return this.booleanType.booleanNot(bool);
        } catch (Exception e) {
            String message = "not(%s)".formatted(bool);
            logError(message, e);
            return null;
        }
    }

    //
    // List operations
    //

    @Override
    public <T> List<T> append(List<T> list, T element) {
        try {
            return this.listLib.append(list, element);
        } catch (Exception e) {
            String message = "append(%s, %s)".formatted(list, element);
            logError(message, e);
            return null;
        }
    }

    @Override
    public <T> List<T> appendAll(List<T> list1, List<T> list2) {
        try {
            return this.listLib.appendAll(list1, list2);
        } catch (Exception e) {
            String message = "appendAll(%s, %s)".formatted(list1, list2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public <T> List<T> remove(List<T> list, T element) {
        try {
            return this.listLib.remove(list, element);
        } catch (Exception e) {
            String message = "remove(%s, %s)".formatted(list, element);
            logError(message, e);
            return null;
        }
    }

    @Override
    public <T> List<T> removeAll(List<T> list1, List<T> list2) {
        try {
            return this.listLib.removeAll(list1, list2);
        } catch (Exception e) {
            String message = "removeAll(%s, %s)".formatted(list1, list2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean notContainsAny(List<?> list1, List<?> list2) {
        try {
            return this.listLib.notContainsAny(list1, list2);
        } catch (Exception e) {
            String message = "notContainsAny(%s, %s)".formatted(list1, list2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean containsOnly(List<?> list1, List<?> list2) {
        try {
            return this.listLib.containsOnly(list1, list2);
        } catch (Exception e) {
            String message = "containsOnly(%s, %s)".formatted(list1, list2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean areElementsOf(List<?> list1, List<?> list2) {
        try {
            return this.listLib.areElementsOf(list1, list2);
        } catch (Exception e) {
            String message = "areElementsOf(%s, %s)".formatted(list1, list2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean elementOf(List<?> list1, List<?> list2) {
        try {
            return this.listLib.elementOf(list1, list2);
        } catch (Exception e) {
            String message = "elementOf(%s, %s)".formatted(list1, list2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List<?> zip(List<?> attributes, List<?> values) {
        try {
            return this.listLib.zip(attributes, values);
        } catch (Exception e) {
            String message = "zip(%s, %s)".formatted(attributes, values);
            logError(message, e);
            return null;
        }
    }

    //
    // Statistical operations
    //

    @Override
    public NUMBER avg(List<?> numbers) {
        try {
            return this.numberLib.avg(numbers);
        } catch (Exception e) {
            String message = "avg(%s)".formatted(numbers);
            logError(message, e);
            return null;
        }
    }

    @Override
    public <T> T max(List<T> numbers) {
        try {
            return (T) this.numberLib.max(numbers);
        } catch (Exception e) {
            String message = "max(%s)".formatted(numbers);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER median(List<?> numbers) {
        try {
            return this.numberLib.median(numbers);
        } catch (Exception e) {
            String message = "median(%s)".formatted(numbers);
            logError(message, e);
            return null;
        }
    }

    @Override
    public <T> T min(List<T> numbers) {
        try {
            return (T) this.numberLib.min(numbers);
        } catch (Exception e) {
            String message = "min(%s)".formatted(numbers);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER mode(List<?> numbers) {
        try {
            return this.numberLib.mode(numbers);
        } catch (Exception e) {
            String message = "mode(%s)".formatted(numbers);
            logError(message, e);
            return null;
        }
    }

    //
    // String functions
    //
    @Override
    public NUMBER len(String text) {
        try {
            return valueOf(this.stringLib.len(text));
        } catch (Exception e) {
            String message = "len(%s)".formatted(text);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String stringAdd(String first, String second) {
        try {
            return this.stringLib.stringAdd(first, second);
        } catch (Exception e) {
            String message = "+(%s, %s)".formatted(first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String concat(List<?> texts) {
        try {
            return this.stringLib.concat(texts);
        } catch (Exception e) {
            String message = "concat(%s)".formatted(texts);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean isAlpha(String text) {
        try {
            return this.stringLib.isAlpha(text);
        } catch (Exception e) {
            String message = "isAlpha(%s)".formatted(text);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean isAlphanumeric(String text) {
        try {
            return this.stringLib.isAlphanumeric(text);
        } catch (Exception e) {
            String message = "isAlphanumeric(%s)".formatted(text);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean isNumeric(String text) {
        try {
            return this.stringLib.isNumeric(text);
        } catch (Exception e) {
            String message = "concat(%s)".formatted(text);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean isSpaces(String text) {
        try {
            return this.stringLib.isSpaces(text);
        } catch (Exception e) {
            String message = "isSpaces(%s)".formatted(text);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String lower(String text) {
        try {
            return this.stringLib.lower(text);
        } catch (Exception e) {
            String message = "lower(%s)".formatted(text);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String trim(String text) {
        try {
            return this.stringLib.trim(text);
        } catch (Exception e) {
            String message = "trim(%s)".formatted(text);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String upper(String text) {
        try {
            return this.stringLib.upper(text);
        } catch (Exception e) {
            String message = "upper(%s)".formatted(text);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean and(List<?> list) {
        try {
            return this.booleanLib.and(list);
        } catch (Exception e) {
            String message = "and(%s)".formatted(list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean or(List<?> list) {
        try {
            return this.booleanLib.or(list);
        } catch (Exception e) {
            String message = "or(%s)".formatted(list);
            logError(message, e);
            return null;
        }
    }

    //
    // Extra conversion functions
    //
    @Override
    public DATE toDate(Object from) {
        try {
            return this.dateTimeLib.toDate(from);
        } catch (Exception e) {
            String message = "toDate(%s)".formatted(from);
            logError(message, e);
            return null;
        }
    }

    @Override
    public TIME toTime(Object from) {
        try {
            return this.dateTimeLib.toTime(from);
        } catch (Exception e) {
            String message = "toTime(%s)".formatted(from);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DATE_TIME toDateTime(Object from) {
        try {
            return this.dateTimeLib.toDateTime(from);
        } catch (Exception e) {
            String message = "toTime(%s)".formatted(from);
            logError(message, e);
            return null;
        }
    }
}
