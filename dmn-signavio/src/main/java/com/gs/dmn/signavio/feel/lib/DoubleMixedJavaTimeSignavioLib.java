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
import com.gs.dmn.feel.lib.DoubleMixedJavaTimeFEELLib;
import com.gs.dmn.feel.lib.type.context.DefaultContextType;
import com.gs.dmn.feel.lib.type.list.DefaultListType;
import com.gs.dmn.feel.lib.type.logic.DefaultBooleanType;
import com.gs.dmn.signavio.feel.lib.type.list.SignavioListLib;
import com.gs.dmn.signavio.feel.lib.type.numeric.DoubleSignavioNumberLib;
import com.gs.dmn.signavio.feel.lib.type.numeric.DoubleSignavioNumericType;
import com.gs.dmn.signavio.feel.lib.type.string.DefaultSignavioStringLib;
import com.gs.dmn.signavio.feel.lib.type.string.DefaultSignavioStringType;
import com.gs.dmn.signavio.feel.lib.type.time.mixed.*;
import com.gs.dmn.signavio.feel.lib.type.time.xml.DoubleSignavioDurationType;

import javax.xml.datatype.Duration;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.util.List;

import static com.gs.dmn.feel.lib.DefaultFEELLib.DATA_TYPE_FACTORY;

public class DoubleMixedJavaTimeSignavioLib extends BaseFEELLib<Double, LocalDate, OffsetTime, ZonedDateTime, Duration> implements SignavioLib<Double, LocalDate, OffsetTime, ZonedDateTime, Duration> {
    private final DoubleMixedJavaTimeFEELLib feelLib = new DoubleMixedJavaTimeFEELLib();

    private final DoubleSignavioNumberLib numberLib = new DoubleSignavioNumberLib();
    private final DefaultSignavioStringLib stringLib = new DefaultSignavioStringLib();
    private final SignavioLocalDateLib dateLib = new SignavioLocalDateLib();
    private final SignavioOffsetTimeLib timeLib = new SignavioOffsetTimeLib();
    private final SignavioZonedDateTimeLib dateTimeLib = new SignavioZonedDateTimeLib();
    private final SignavioListLib listLib = new SignavioListLib();
    
    public DoubleMixedJavaTimeSignavioLib() {
        super(new DoubleSignavioNumericType(LOGGER),
                new DefaultBooleanType(LOGGER),
                new DefaultSignavioStringType(LOGGER),
                new SignavioLocalDateType(LOGGER, DATA_TYPE_FACTORY),
                new SignavioOffsetTimeType(LOGGER, DATA_TYPE_FACTORY),
                new SignavioZonedDateTimeType(LOGGER, DATA_TYPE_FACTORY),
                new DoubleSignavioDurationType(LOGGER),
                new DefaultListType(LOGGER),
                new DefaultContextType(LOGGER)
        );
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
    public Double round(Double number, Double digits) {
        try {
            return this.numberLib.round(number, digits);
        } catch (Exception e) {
            String message = String.format("round(%s, %s)", number, digits);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double ceiling(Double aDouble) {
        return this.feelLib.ceiling(aDouble);
    }

    @Override
    public Double floor(Double aDouble) {
        return this.feelLib.floor(aDouble);
    }

    @Override
    public Double integer(Double number) {
        try {
            return this.numberLib.integer(number);
        } catch (Exception e) {
            String message = String.format("integer(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double modulo(Double divident, Double divisor) {
        try {
            return this.numberLib.modulo(divident, divisor);
        } catch (Exception e) {
            String message = String.format("modulo(%s, %s)", divident, divisor);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double percent(Double number) {
        try {
            return numericDivide(number, Double.valueOf(100));
        } catch (Exception e) {
            String message = String.format("percent(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double power(Double base, Double exponent) {
        try {
            return Math.pow(base, exponent.intValue());
        } catch (Exception e) {
            String message = String.format("power(%s, %s)", base, exponent);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double product(List factors) {
        return this.feelLib.product(factors);
    }

    @Override
    public Double roundDown(Double number, Double digits) {
        try {
            return this.numberLib.roundDown(number, digits);
        } catch (Exception e) {
            String message = String.format("roundDown(%s, %s)", number, digits);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double roundUp(Double number, Double digits) {
        try {
            return this.numberLib.roundUp(number, digits);
        } catch (Exception e) {
            String message = String.format("roundUp(%s, %s)", number, digits);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double sum(List numbers) {
        return this.feelLib.sum(numbers);
    }

    @Override
    public Double day(LocalDate date) {
        return this.feelLib.day(date);
    }
    public Double day(ZonedDateTime date) {
        return this.feelLib.day(date);
    }

    //
    // Date and time operations
    //

    @Override
    public LocalDate dayAdd(ZonedDateTime dateTime, Double daysToAdd) {
        try {
            return this.dateLib.dayAdd(dateTime, daysToAdd);
        } catch (Exception e) {
            String message = String.format("dayAdd(%s, %s)", dateTime, daysToAdd);
            logError(message, e);
            return null;
        }
    }
    public LocalDate dayAdd(LocalDate date, Double daysToAdd) {
        try {
            return this.dateLib.dayAdd(date, daysToAdd);
        } catch (Exception e) {
            String message = String.format("dayAdd(%s, %s)", date, daysToAdd);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double dayDiff(ZonedDateTime dateTime1, ZonedDateTime dateTime2) {
        try {
            return Double.valueOf(this.dateLib.dayDiff(dateTime1, dateTime2));
        } catch (Exception e) {
            String message = String.format("dayDiff(%s, %s)", dateTime1, dateTime2);
            logError(message, e);
            return null;
        }
    }
    public Double dayDiff(LocalDate date1, LocalDate date2) {
        try {
            return Double.valueOf(this.dateLib.dayDiff(date1, date2));
        } catch (Exception e) {
            String message = String.format("dayDiff(%s, %s)", date1, date2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public LocalDate date(Double year, Double month, Double day) {
        try {
            if (year == null || month == null || day == null) {
                return null;
            }
            if (year.intValue() < 0 || month.intValue() < 0 || day.intValue() < 0) {
                return null;
            }

            String literal = String.format("%04d-%02d-%02d", year.intValue(), month.intValue(), day.intValue());
            return this.feelLib.date(literal);
        } catch (Exception e) {
            String message = String.format("date(%s, %s, %s)", year, month, day);
            logError(message, e);
            return null;
        }
    }

    @Override
    public ZonedDateTime dateTime(Double day, Double month, Double year, Double hour, Double minute, Double second) {
        try {
            if (year == null || month == null || day == null || minute == null || second == null) {
                return null;
            }
            if (year.intValue() < 0 || month.intValue() < 0 || day.intValue() < 0 || minute.intValue() < 0 || second.intValue() < 0) {
                return null;
            }

            String literal = String.format("%04d-%02d-%02dT%02d:%02d:%02dZ",
                    year.intValue(), month.intValue(), day.intValue(), hour.intValue(), minute.intValue(), second.intValue());
            return this.feelLib.dateAndTime(literal);
        } catch (Exception e) {
            String message = String.format("dateTime(%s, %s, %s, %s, %s, %s)", day, month, year, hour, minute, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double hour(OffsetTime time) {
        return this.feelLib.hour(time);
    }
    public Double hour(ZonedDateTime dateTime) {
        return this.feelLib.hour(dateTime);
    }

    @Override
    public Double hourDiff(ZonedDateTime dateTime1, ZonedDateTime dateTime2) {
        try {
            return Double.valueOf(this.timeLib.hourDiff(dateTime1, dateTime2));
        } catch (Exception e) {
            String message = String.format("hourDiff(%s, %s)", dateTime1, dateTime2);
            logError(message, e);
            return null;
        }
    }
    public Double hourDiff(OffsetTime time1, OffsetTime time2) {
        try {
            return Double.valueOf(this.timeLib.hourDiff(time1, time2));
        } catch (Exception e) {
            String message = String.format("hourDiff(%s, %s)", time1, time2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double minute(OffsetTime time) {
        return feelLib.minute(time);
    }
    public Double minute(ZonedDateTime dateTime) {
        return this.feelLib.minute(dateTime);
    }

    @Override
    public Double second(OffsetTime time) {
        return this.feelLib.second(time);
    }
    public Double second(ZonedDateTime time) {
        return this.feelLib.second(time);
    }

    @Override
    public Duration timeOffset(OffsetTime time) {
        return this.feelLib.timeOffset(time);
    }
    public Duration timeOffset(ZonedDateTime time) {
        return this.feelLib.timeOffset(time);
    }

    @Override
    public String timezone(OffsetTime offsetTime) {
        return this.feelLib.timezone(offsetTime);
    }
    public String timezone(ZonedDateTime time) {
        return this.feelLib.timezone(time);
    }

    @Override
    public Double years(Duration duration) {
        return this.feelLib.years(duration);
    }

    @Override
    public Double months(Duration duration) {
        return this.feelLib.months(duration);
    }

    @Override
    public Double days(Duration duration) {
        return this.feelLib.days(duration);
    }

    @Override
    public Double hours(Duration duration) {
        return this.feelLib.hours(duration);
    }

    @Override
    public Double minutes(Duration duration) {
        return this.feelLib.minutes(duration);
    }

    @Override
    public Double seconds(Duration duration) {
        return this.feelLib.seconds(duration);
    }

    @Override
    public String string(Object object) {
        return this.feelLib.string(object);
    }

    @Override
    public Double minutesDiff(ZonedDateTime dateTime1, ZonedDateTime dateTime2) {
        try {
            return Double.valueOf(this.timeLib.minutesDiff(dateTime1, dateTime2));
        } catch (Exception e) {
            String message = String.format("minutesDiff(%s, %s)", dateTime1, dateTime2);
            logError(message, e);
            return null;
        }
    }
    public Double minutesDiff(OffsetTime time1, OffsetTime time2) {
        try {
            return Double.valueOf(this.timeLib.minutesDiff(time1, time2));
        } catch (Exception e) {
            String message = String.format("minutesDiff(%s, %s)", time1, time2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double month(LocalDate date) {
        return this.feelLib.month(date);
    }
    public Double month(ZonedDateTime dateTime) {
        return this.feelLib.month(dateTime);
    }


    @Override
    public ZonedDateTime monthAdd(ZonedDateTime dateTime, Double monthsToAdd) {
        try {
            return this.dateLib.monthAdd(dateTime, monthsToAdd);
        } catch (Exception e) {
            String message = String.format("monthAdd(%s, %s)", dateTime, monthsToAdd);
            logError(message, e);
            return null;
        }
    }
    public ZonedDateTime monthAdd(LocalDate date, Double monthsToAdd) {
        try {
            return this.dateLib.monthAdd(date, monthsToAdd);
        } catch (Exception e) {
            String message = String.format("monthAdd(%s, %s)", date, monthsToAdd);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double monthDiff(ZonedDateTime dateTime1, ZonedDateTime dateTime2) {
        try {
            return Double.valueOf(this.dateLib.monthDiff(dateTime1, dateTime2));
        } catch (Exception e) {
            String message = String.format("monthDiff(%s, %s)", dateTime1, dateTime2);
            logError(message, e);
            return null;
        }
    }
    public Double monthDiff(LocalDate date1, LocalDate date2) {
        try {
            return Double.valueOf(this.dateLib.monthDiff(date1, date2));
        } catch (Exception e) {
            String message = String.format("monthDiff(%s, %s)", date1, date2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public ZonedDateTime now() {
        try {
            return this.dateTimeLib.now();
        } catch (Exception e) {
            String message = "now()";
            logError(message, e);
            return null;
        }
    }

    @Override
    public LocalDate today() {
        try {
            return this.dateLib.today();
        } catch (Exception e) {
            String message = "today()";
            logError(message, e);
            return null;
        }
    }

    public Double weekday(LocalDate date) {
        try {
            return Double.valueOf(this.dateLib.weekday(date));
        } catch (Exception e) {
            String message = String.format("weekday(%s)", date);
            logError(message, e);
            return null;
        }
    }
    public Double weekday(ZonedDateTime dateTime) {
        try {
            return Double.valueOf(this.dateLib.weekday(dateTime));
        } catch (Exception e) {
            String message = String.format("weekday(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double year(LocalDate date) {
        return this.feelLib.year(date);
    }
    public Double year(ZonedDateTime dateTime) {
        return this.feelLib.year(dateTime);
    }

    @Override
    public ZonedDateTime yearAdd(ZonedDateTime dateTime, Double yearsToAdd) {
        try {
            return this.dateLib.yearAdd(dateTime, yearsToAdd);
        } catch (Exception e) {
            String message = String.format("yearAdd(%s, %s)", dateTime, yearsToAdd);
            logError(message, e);
            return null;
        }
    }
    public ZonedDateTime yearAdd(LocalDate localDate, Double yearsToAdd) {
        try {
            return this.dateLib.yearAdd(localDate, yearsToAdd);
        } catch (Exception e) {
            String message = String.format("yearAdd(%s, %s)", localDate, yearsToAdd);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double yearDiff(ZonedDateTime dateTime1, ZonedDateTime dateTime2) {
        try {
            return Double.valueOf(this.dateLib.yearDiff(dateTime1, dateTime2));
        } catch (Exception e) {
            String message = String.format("yearDiff(%s, %s)", dateTime1, dateTime2);
            logError(message, e);
            return null;
        }
    }

    public Double yearDiff(LocalDate dateTime1, LocalDate dateTime2) {
        try {
            return Double.valueOf(this.dateLib.yearDiff(dateTime1, dateTime2));
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
    public Double number(String text, String defaultValue) {
        try {
            return this.numberLib.number(text, defaultValue);
        } catch (Exception e) {
            String message = String.format("number(%s, %s)", text, defaultValue);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String mid(String text, Double start, Double numChar) {
        try {
            return this.stringLib.mid(text, start, numChar);
        } catch (Exception e) {
            String message = String.format("mid(%s, %s, %s)", text, start, numChar);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String left(String text, Double numChar) {
        try {
            return this.stringLib.left(text, numChar);
        } catch (Exception e) {
            String message = String.format("left(%s, %s)", text, numChar);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String right(String text, Double numChar) {
        try {
            return this.stringLib.right(text, numChar);
        } catch (Exception e) {
            String message = String.format("right(%s, %s)", text, numChar);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String text(Double num, String formatText) {
        try {
            return this.stringLib.text(num, formatText);
        } catch (Exception e) {
            String message = String.format("text(%s, %s)", num, formatText);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double textOccurrences(String findText, String withinText) {
        try {
            return Double.valueOf(this.stringLib.textOccurrences(findText, withinText));
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
    public LocalDate date(String literal) {
        return this.feelLib.date(literal);
    }

    @Override
    public LocalDate date(ZonedDateTime dateTime) {
        return this.feelLib.date(dateTime);
    }
    public LocalDate date(LocalDate date) {
        return this.feelLib.date(date);
    }

    @Override
    public OffsetTime time(String literal) {
        return this.feelLib.time(literal);
    }

    @Override
    public OffsetTime time(ZonedDateTime dateTime) {
        return this.feelLib.time(dateTime);
    }
    public OffsetTime time(OffsetTime time) {
        return this.feelLib.time(time);
    }

    @Override
    public OffsetTime time(Double hour, Double minute, Double second, Duration offset) {
        return this.feelLib.time(hour, minute, second, offset);
    }

    @Override
    public ZonedDateTime dateAndTime(String literal) {
        return this.feelLib.dateAndTime(literal);
    }

    @Override
    public ZonedDateTime dateAndTime(LocalDate localDate, OffsetTime offsetTime) {
        return this.feelLib.dateAndTime(localDate, offsetTime);
    }

    @Override
    public Duration duration(String literal) {
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
    public Double avg(List list) {
        return this.feelLib.mean(list);
    }

    @Override
    public Double max(List numbers) {
        return this.feelLib.max(numbers);
    }

    @Override
    public Double median(List numbers) {
        return this.feelLib.median(numbers);
    }

    @Override
    public Double min(List numbers) {
        return this.feelLib.min(numbers);
    }

    @Override
    public Double mode(List numbers) {
        try {
            return (Double) this.listLib.mode(numbers);
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
    public Double number(String text) {
        try {
            return this.numberLib.number(text);
        } catch (Exception e) {
            String message = String.format("number(%s)", text);
            logError(message, e);
            return null;
        }
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
    public LocalDate toDate(Object object) {
        return this.feelLib.toDate(object);
    }

    @Override
    public OffsetTime toTime(Object object) {
        return this.feelLib.toTime(object);
    }

    @Override
    public Double len(String text) {
        return this.feelLib.stringLength(text);
    }
}
