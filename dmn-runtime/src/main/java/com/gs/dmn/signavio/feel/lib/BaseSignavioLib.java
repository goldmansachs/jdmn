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
import com.gs.dmn.feel.lib.StandardFEELLib;
import com.gs.dmn.feel.lib.type.*;
import com.gs.dmn.signavio.feel.lib.type.list.SignavioListLib;
import com.gs.dmn.signavio.feel.lib.type.numeric.SignavioNumberLib;
import com.gs.dmn.signavio.feel.lib.type.string.SignavioStringLib;
import com.gs.dmn.signavio.feel.lib.type.time.SignavioDateTimeLib;

import java.util.List;

public abstract class BaseSignavioLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends BaseFEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> implements SignavioLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> {
    protected final StandardFEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> feelLib;

    private final SignavioNumberLib<NUMBER> numberLib;
    private final SignavioStringLib stringLib;
    protected final SignavioDateTimeLib<NUMBER, DATE, TIME, DATE_TIME> dateTimeLib;
    private final SignavioListLib listLib;

    protected BaseSignavioLib(
            NumericType<NUMBER> numericType, BooleanType booleanType, StringType stringType,
            DateType<DATE, DURATION> dateType, TimeType<TIME, DURATION> timeType, DateTimeType<DATE_TIME, DURATION> dateTimeType, DurationType<DURATION, NUMBER> durationType,
            ListType listType, ContextType contextType,
            StandardFEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> feelLib,
            SignavioNumberLib<NUMBER> numberLib,
            SignavioStringLib stringLib,
            SignavioDateTimeLib<NUMBER, DATE, TIME, DATE_TIME> dateTimeLib,
            SignavioListLib listLib) {
        super(numericType, booleanType, stringType, dateType, timeType, dateTimeType, durationType, listType, contextType);
        this.feelLib = feelLib;
        this.numberLib = numberLib;
        this.stringLib = stringLib;
        this.dateTimeLib = dateTimeLib;
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

    //
    // Arithmetic functions
    //

    @Override
    public NUMBER abs(NUMBER number) {
        return this.feelLib.abs(number);
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
    public NUMBER round(NUMBER number, NUMBER digits) {
        try {
            return this.numberLib.round(number, digits);
        } catch (Exception e) {
            String message = String.format("round(%s, %s)", number, digits);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER ceiling(NUMBER aNumber) {
        return this.feelLib.ceiling(aNumber);
    }

    @Override
    public NUMBER floor(NUMBER aNumber) {
        return this.feelLib.floor(aNumber);
    }

    @Override
    public NUMBER integer(NUMBER number) {
        try {
            return this.numberLib.integer(number);
        } catch (Exception e) {
            String message = String.format("integer(%s)", number);
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
    public NUMBER percent(NUMBER number) {
        try {
            return numericDivide(number, this.numberLib.valueOf(100));
        } catch (Exception e) {
            String message = String.format("percent(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER power(NUMBER base, NUMBER exponent) {
        try {
            return this.numberLib.power(base, exponent);
        } catch (Exception e) {
            String message = String.format("power(%s, %s)", base, exponent);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER product(List factors) {
        return this.feelLib.product(factors);
    }

    @Override
    public NUMBER roundDown(NUMBER number, NUMBER digits) {
        try {
            return this.numberLib.roundDown(number, digits);
        } catch (Exception e) {
            String message = String.format("roundDown(%s, %s)", number, digits);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER roundUp(NUMBER number, NUMBER digits) {
        try {
            return this.numberLib.roundUp(number, digits);
        } catch (Exception e) {
            String message = String.format("roundUp(%s, %s)", number, digits);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER sum(List numbers) {
        return this.feelLib.sum(numbers);
    }

    @Override
    public NUMBER day(DATE date) {
        return this.feelLib.day(date);
    }

    //
    // Date and time operations
    //
    @Override
    public DATE dayAdd(DATE date, NUMBER daysToAdd) {
        try {
            return this.dateTimeLib.dayAdd(date, daysToAdd);
        } catch (Exception e) {
            String message = String.format("dayAdd(%s, %s)", date, daysToAdd);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER dayDiff(DATE date1, DATE date2) {
        try {
            return this.numberLib.valueOf(this.dateTimeLib.dayDiff(date1, date2));
        } catch (Exception e) {
            String message = String.format("dayDiff(%s, %s)", date1, date2);
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

            String literal = String.format("%04d-%02d-%02d", this.numberLib.intValue(year), this.numberLib.intValue(month), this.numberLib.intValue(day));
            return this.feelLib.date(literal);
        } catch (Exception e) {
            String message = String.format("date(%s, %s, %s)", year, month, day);
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

            String literal = String.format("%04d-%02d-%02dT%02d:%02d:%02dZ",
                    this.numberLib.intValue(year), this.numberLib.intValue(month), this.numberLib.intValue(day), this.numberLib.intValue(hour), this.numberLib.intValue(minute), this.numberLib.intValue(second));
            return this.feelLib.dateAndTime(literal);
        } catch (Exception e) {
            String message = String.format("dateTime(%s, %s, %s, %s, %s, %s)", day, month, year, hour, minute, second);
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

            String literal = String.format("%04d-%02d-%02dT%02d:%02d:%02d%+03d:00",
                    this.numberLib.intValue(year), this.numberLib.intValue(month), this.numberLib.intValue(day), this.numberLib.intValue(hour), this.numberLib.intValue(minute), this.numberLib.intValue(second), this.numberLib.intValue(hourOffset));
            return this.feelLib.dateAndTime(literal);
        } catch (Exception e) {
            String message = String.format("dateTime(%s, %s, %s, %s, %s, %s, %s)", day, month, year, hour, minute, second, hourOffset);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER hour(TIME time) {
        return this.feelLib.hour(time);
    }

    @Override
    public NUMBER hourDiff(TIME time1, TIME time2) {
        try {
            return this.numberLib.valueOf(this.dateTimeLib.hourDiff(time1, time2));
        } catch (Exception e) {
            String message = String.format("hourDiff(%s, %s)", time1, time2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER minute(TIME time) {
        return feelLib.minute(time);
    }

    @Override
    public NUMBER second(TIME time) {
        return this.feelLib.second(time);
    }

    @Override
    public DURATION timeOffset(TIME time) {
        return this.feelLib.timeOffset(time);
    }

    @Override
    public String timezone(TIME offsetTime) {
        return this.feelLib.timezone(offsetTime);
    }

    @Override
    public NUMBER years(DURATION duration) {
        return this.feelLib.years(duration);
    }

    @Override
    public NUMBER months(DURATION duration) {
        return this.feelLib.months(duration);
    }

    @Override
    public NUMBER days(DURATION duration) {
        return this.feelLib.days(duration);
    }

    @Override
    public NUMBER hours(DURATION duration) {
        return this.feelLib.hours(duration);
    }

    @Override
    public NUMBER minutes(DURATION duration) {
        return this.feelLib.minutes(duration);
    }

    @Override
    public NUMBER seconds(DURATION duration) {
        return this.feelLib.seconds(duration);
    }

    @Override
    public String string(Object object) {
        return this.feelLib.string(object);
    }

    @Override
    public NUMBER minutesDiff(TIME time1, TIME time2) {
        try {
            return this.numberLib.valueOf(this.dateTimeLib.minutesDiff(time1, time2));
        } catch (Exception e) {
            String message = String.format("minutesDiff(%s, %s)", time1, time2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER month(DATE date) {
        return this.feelLib.month(date);
    }

    @Override
    public DATE monthAdd(DATE date, NUMBER monthsToAdd) {
        try {
            return this.dateTimeLib.monthAdd(date, monthsToAdd);
        } catch (Exception e) {
            String message = String.format("monthAdd(%s, %s)", date, monthsToAdd);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER monthDiff(DATE date1, DATE date2) {
        try {
            return this.numberLib.valueOf(this.dateTimeLib.monthDiff(date1, date2));
        } catch (Exception e) {
            String message = String.format("monthDiff(%s, %s)", date1, date2);
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
    public NUMBER weekday(DATE date) {
        try {
            return this.numberLib.valueOf(this.dateTimeLib.weekday(date));
        } catch (Exception e) {
            String message = String.format("weekday(%s)", date);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER year(DATE date) {
        return this.feelLib.year(date);
    }

    @Override
    public DATE yearAdd(DATE localDate, NUMBER yearsToAdd) {
        try {
            return this.dateTimeLib.yearAdd(localDate, yearsToAdd);
        } catch (Exception e) {
            String message = String.format("yearAdd(%s, %s)", localDate, yearsToAdd);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER yearDiff(DATE dateTime1, DATE dateTime2) {
        try {
            return this.numberLib.valueOf(this.dateTimeLib.yearDiff(dateTime1, dateTime2));
        } catch (Exception e) {
            String message = String.format("yearDiff(%s, %s)", dateTime1, dateTime2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List append(List list, Object element) {
        return this.feelLib.append(list, element);
    }

    @Override
    public NUMBER number(String text, NUMBER defaultValue) {
        try {
            return this.numberLib.number(text, defaultValue);
        } catch (Exception e) {
            String message = String.format("number(%s, %s)", text, defaultValue);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String mid(String text, NUMBER start, NUMBER numChar) {
        try {
            return this.stringLib.mid(text, this.numberLib.toNumber(start), this.numberLib.toNumber(numChar));
        } catch (Exception e) {
            String message = String.format("mid(%s, %s, %s)", text, start, numChar);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String left(String text, NUMBER numChar) {
        try {
            return this.stringLib.left(text, this.numberLib.toNumber(numChar));
        } catch (Exception e) {
            String message = String.format("left(%s, %s)", text, numChar);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String right(String text, NUMBER numChar) {
        try {
            return this.stringLib.right(text, this.numberLib.toNumber(numChar));
        } catch (Exception e) {
            String message = String.format("right(%s, %s)", text, numChar);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String text(NUMBER num, String formatText) {
        try {
            return this.stringLib.text(this.numberLib.toNumber(num), formatText);
        } catch (Exception e) {
            String message = String.format("text(%s, %s)", num, formatText);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER textOccurrences(String findText, String withinText) {
        try {
            return this.numberLib.valueOf(this.stringLib.textOccurrences(findText, withinText));
        } catch (Exception e) {
            String message = String.format("textOccurrences(%s, %s)", findText, withinText);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean contains(String text, String substring) {
        return this.feelLib.contains(text, substring);
    }

    @Override
    public Boolean startsWith(String text, String prefix) {
        return this.feelLib.startsWith(text, prefix);
    }

    @Override
    public Boolean endsWith(String text, String suffix) {
        return this.feelLib.endsWith(text, suffix);
    }

    @Override
    public Boolean not(Boolean bool) {
        try {
            return this.booleanType.booleanNot(bool);
        } catch (Exception e) {
            String message = String.format("not(%s)", bool);
            logError(message, e);
            return null;
        }
    }

    @Override
    public DATE date(String literal) {
        return this.feelLib.date(literal);
    }

    @Override
    public DATE date(DATE dateTime) {
        return this.feelLib.date(dateTime);
    }

    @Override
    public TIME time(String literal) {
        return this.feelLib.time(literal);
    }

    @Override
    public TIME time(TIME dateTime) {
        return this.feelLib.time(dateTime);
    }

    @Override
    public TIME time(NUMBER hour, NUMBER minute, NUMBER second, DURATION offset) {
        return this.feelLib.time(hour, minute, second, offset);
    }

    @Override
    public DATE_TIME dateAndTime(String literal) {
        return this.feelLib.dateAndTime(literal);
    }

    @Override
    public DATE_TIME dateAndTime(DATE localDate, TIME offsetTime) {
        return this.feelLib.dateAndTime(localDate, offsetTime);
    }

    @Override
    public DURATION duration(String literal) {
        return this.feelLib.duration(literal);
    }

    //
    // List operations
    //

    @Override
    public List appendAll(List list1, List list2) {
        try {
            return this.listLib.appendAll(list1, list2);
        } catch (Exception e) {
            String message = String.format("appendAll(%s, %s)", list1, list2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List remove(List list, Object element) {
        try {
            return this.listLib.remove(list, element);
        } catch (Exception e) {
            String message = String.format("remove(%s, %s)", list, element);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List removeAll(List list1, List list2) {
        try {
            return this.listLib.removeAll(list1, list2);
        } catch (Exception e) {
            String message = String.format("removeAll(%s, %s)", list1, list2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean notContainsAny(List list1, List list2) {
        try {
            return this.listLib.notContainsAny(list1, list2);
        } catch (Exception e) {
            String message = String.format("notContainsAny(%s, %s)", list1, list2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean containsOnly(List list1, List list2) {
        try {
            return this.listLib.containsOnly(list1, list2);
        } catch (Exception e) {
            String message = String.format("containsOnly(%s, %s)", list1, list2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean areElementsOf(List list1, List list2) {
        try {
            return this.listLib.areElementsOf(list1, list2);
        } catch (Exception e) {
            String message = String.format("areElementsOf(%s, %s)", list1, list2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean elementOf(List list1, List list2) {
        return areElementsOf(list1, list2);
    }

    @Override
    public List<?> zip(List attributes, List values) {
        try {
            return this.listLib.zip(attributes, values);
        } catch (Exception e) {
            String message = String.format("zip(%s, %s)", attributes, values);
            logError(message, e);
            return null;
        }
    }

    //
    // Statistical operations
    //

    @Override
    public NUMBER avg(List list) {
        return this.feelLib.mean(list);
    }

    @Override
    public NUMBER max(List numbers) {
        return this.feelLib.max(numbers);
    }

    @Override
    public NUMBER median(List numbers) {
        return this.feelLib.median(numbers);
    }

    @Override
    public NUMBER min(List numbers) {
        return this.feelLib.min(numbers);
    }

    @Override
    public NUMBER mode(List numbers) {
        try {
            return (NUMBER) this.listLib.mode(numbers);
        } catch (Exception e) {
            String message = String.format("mode(%s)", numbers);
            logError(message, e);
            return null;
        }
    }

    //
    // String functions
    //
    @Override
    public String stringAdd(String first, String second) {
        try {
            return this.stringLib.stringAdd(first, second);
        } catch (Exception e) {
            String message = String.format("+(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String concat(List<String> texts) {
        try {
            return this.stringLib.concat(texts);
        } catch (Exception e) {
            String message = String.format("concat(%s)", texts);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean isAlpha(String text) {
        try {
            return this.stringLib.isAlpha(text);
        } catch (Exception e) {
            String message = String.format("isAlpha(%s)", text);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean isAlphanumeric(String text) {
        try {
            return this.stringLib.isAlphanumeric(text);
        } catch (Exception e) {
            String message = String.format("isAlphanumeric(%s)", text);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean isNumeric(String text) {
        try {
            return this.stringLib.isNumeric(text);
        } catch (Exception e) {
            String message = String.format("concat(%s)", text);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean isSpaces(String text) {
        try {
            return this.stringLib.isSpaces(text);
        } catch (Exception e) {
            String message = String.format("isSpaces(%s)", text);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String lower(String text) {
        try {
            return this.stringLib.lower(text);
        } catch (Exception e) {
            String message = String.format("lower(%s)", text);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String trim(String text) {
        try {
            return this.stringLib.trim(text);
        } catch (Exception e) {
            String message = String.format("trim(%s)", text);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String upper(String text) {
        try {
            return this.stringLib.upper(text);
        } catch (Exception e) {
            String message = String.format("upper(%s)", text);
            logError(message, e);
            return null;
        }
    }

    @Override
    public NUMBER number(String text) {
        return this.feelLib.number(text);
    }

    @Override
    public Boolean and(List list) {
        return this.feelLib.and(list);
    }

    @Override
    public Boolean or(List list) {
        return this.feelLib.or(list);
    }

    @Override
    public Boolean listContains(List list, Object value) {
        return this.feelLib.listContains(list, value);
    }

    @Override
    public DATE toDate(Object object) {
        return this.feelLib.toDate(object);
    }

    @Override
    public TIME toTime(Object object) {
        return this.feelLib.toTime(object);
    }

    @Override
    public NUMBER len(String text) {
        return this.feelLib.stringLength(text);
    }

    @Override
    protected NUMBER valueOf(long number) {
        return this.numberLib.valueOf(number);
    }

    @Override
    protected int intValue(NUMBER number) {
        return this.numberLib.intValue(number);
    }
}
