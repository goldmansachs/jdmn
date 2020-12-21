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
import com.gs.dmn.feel.lib.DefaultFEELLib;
import com.gs.dmn.feel.lib.type.context.DefaultContextType;
import com.gs.dmn.feel.lib.type.list.DefaultListType;
import com.gs.dmn.feel.lib.type.logic.DefaultBooleanType;
import com.gs.dmn.feel.lib.type.numeric.DefaultNumericType;
import com.gs.dmn.signavio.feel.lib.type.list.SignavioListLib;
import com.gs.dmn.signavio.feel.lib.type.numeric.DefaultSignavioNumberLib;
import com.gs.dmn.signavio.feel.lib.type.numeric.DefaultSignavioNumericType;
import com.gs.dmn.signavio.feel.lib.type.string.DefaultSignavioStringLib;
import com.gs.dmn.signavio.feel.lib.type.string.DefaultSignavioStringType;
import com.gs.dmn.signavio.feel.lib.type.time.xml.*;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.util.List;

import static com.gs.dmn.feel.lib.DefaultFEELLib.DATA_TYPE_FACTORY;

public class DefaultSignavioLib extends BaseFEELLib<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar, Duration> implements SignavioLib<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar, Duration> {
    private static final DefaultSignavioNumericType NUMERIC_TYPE = new DefaultSignavioNumericType(LOGGER);
    private static final DefaultBooleanType BOOLEAN_TYPE = new DefaultBooleanType(LOGGER);
    private static final DefaultSignavioStringType STRING_TYPE = new DefaultSignavioStringType(LOGGER);
    private static final DefaultSignavioDateType DATE_TYPE = new DefaultSignavioDateType(LOGGER, DATA_TYPE_FACTORY);
    private static final DefaultSignavioTimeType TIME_TYPE = new DefaultSignavioTimeType(LOGGER, DATA_TYPE_FACTORY);
    private static final DefaultSignavioDateTimeType DATE_TIME_TYPE = new DefaultSignavioDateTimeType(LOGGER, DATA_TYPE_FACTORY);
    private static final DefaultSignavioDurationType DURATION_TYPE = new DefaultSignavioDurationType(LOGGER, DATA_TYPE_FACTORY);
    private static final DefaultListType LIST_TYPE = new DefaultListType(LOGGER);
    private static final DefaultContextType CONTEXT_TYPE = new DefaultContextType(LOGGER);

    private static final DefaultFEELLib FEEL_LIB = DefaultFEELLib.INSTANCE;

    private static final DefaultSignavioNumberLib NUMBER_LIB = new DefaultSignavioNumberLib();
    private static final DefaultSignavioStringLib STRING_LIB = new DefaultSignavioStringLib();
    private static final DefaultSignavioDateLib DATE_LIB = new DefaultSignavioDateLib();
    private static final DefaultSignavioTimeLib TIME_LIB = new DefaultSignavioTimeLib();
    private static final DefaultSignavioDateTimeLib DATE_TIME_LIB = new DefaultSignavioDateTimeLib();
    private static final SignavioListLib LIST_LIB = new SignavioListLib();
    
    public DefaultSignavioLib() {
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
    public BigDecimal abs(BigDecimal number) {
        try {
            return NUMBER_LIB.abs(number);
        } catch (Exception e) {
            String message = String.format("abs(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal count(List list) {
        try {
            return NUMBER_LIB.count(list);
        } catch (Exception e) {
            String message = String.format("count(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal round(BigDecimal number, BigDecimal digits) {
        try {
            return NUMBER_LIB.round(number, digits);
        } catch (Exception e) {
            String message = String.format("round(%s, %s)", number, digits);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal ceiling(BigDecimal number) {
        return FEEL_LIB.ceiling(number);
    }

    @Override
    public BigDecimal floor(BigDecimal number) {
        return FEEL_LIB.floor(number);
    }

    @Override
    public BigDecimal integer(BigDecimal number) {
        try {
            return NUMBER_LIB.integer(number);
        } catch (Exception e) {
            String message = String.format("integer(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal modulo(BigDecimal divident, BigDecimal divisor) {
        try {
            return NUMBER_LIB.modulo(divident, divisor);
        } catch (Exception e) {
            String message = String.format("modulo(%s, %s)", divident, divisor);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal percent(BigDecimal number) {
        try {
            return numericDivide(number, BigDecimal.valueOf(100));
        } catch (Exception e) {
            String message = String.format("percent(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal power(BigDecimal base, BigDecimal exponent) {
        try {
            return ((DefaultNumericType)this.numericType).numericExponentiation(base, exponent.intValue());
        } catch (Exception e) {
            String message = String.format("power(%s, %s)", base, exponent);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal product(List factors) {
        return FEEL_LIB.product(factors);
    }

    @Override
    public BigDecimal roundDown(BigDecimal number, BigDecimal digits) {
        try {
            return NUMBER_LIB.roundDown(number, digits);
        } catch (Exception e) {
            String message = String.format("roundDown(%s, %s)", number, digits);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal roundUp(BigDecimal number, BigDecimal digits) {
        try {
            return NUMBER_LIB.roundUp(number, digits);
        } catch (Exception e) {
            String message = String.format("roundUp(%s, %s)", number, digits);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal sum(List numbers) {
        return FEEL_LIB.sum(numbers);
    }

    @Override
    public BigDecimal day(XMLGregorianCalendar date) {
        return FEEL_LIB.day(date);
    }

    //
    // Date and time operations
    //

    @Override
    public XMLGregorianCalendar dayAdd(XMLGregorianCalendar dateTime, BigDecimal daysToAdd) {
        try {
            return DATE_LIB.dayAdd(dateTime, daysToAdd);
        } catch (Exception e) {
            String message = String.format("dayAdd(%s, %s)", dateTime, daysToAdd);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal dayDiff(XMLGregorianCalendar dateTime1, XMLGregorianCalendar dateTime2) {
        try {
            return BigDecimal.valueOf(DATE_LIB.dayDiff(dateTime1, dateTime2));
        } catch (Exception e) {
            String message = String.format("dayDiff(%s, %s)", dateTime1, dateTime2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public XMLGregorianCalendar date(BigDecimal year, BigDecimal month, BigDecimal day) {
        try {
            if (year == null || month == null || day == null) {
                return null;
            }
            if (year.intValue() < 0 || month.intValue() < 0 || day.intValue() < 0) {
                return null;
            }

            String literal = String.format("%04d-%02d-%02d", year.intValue(), month.intValue(), day.intValue());
            return FEEL_LIB.date(literal);
        } catch (Exception e) {
            String message = String.format("date(%s, %s, %s)", year, month, day);
            logError(message, e);
            return null;
        }
    }

    @Override
    public XMLGregorianCalendar dateTime(BigDecimal day, BigDecimal month, BigDecimal year, BigDecimal hour, BigDecimal minute, BigDecimal second) {
        try {
            if (year == null || month == null || day == null || minute == null || second == null) {
                return null;
            }
            if (year.intValue() < 0 || month.intValue() < 0 || day.intValue() < 0 || minute.intValue() < 0 || second.intValue() < 0) {
                return null;
            }

            String literal = String.format("%04d-%02d-%02dT%02d:%02d:%02dZ",
                    year.intValue(), month.intValue(), day.intValue(), hour.intValue(), minute.intValue(), second.intValue());
            return FEEL_LIB.dateAndTime(literal);
        } catch (Exception e) {
            String message = String.format("dateTime(%s, %s, %s, %s, %s, %s)", day, month, year, hour, minute, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public XMLGregorianCalendar dateTime(BigDecimal day, BigDecimal month, BigDecimal year, BigDecimal hour, BigDecimal minute, BigDecimal second, BigDecimal hourOffset) {
        try {
            if (year == null || month == null || day == null || minute == null || second == null || hourOffset == null) {
                return null;
            }
            if (year.intValue() < 0 || month.intValue() < 0 || day.intValue() < 0 || minute.intValue() < 0 || second.intValue() < 0) {
                return null;
            }

            String literal = String.format("%04d-%02d-%02dT%02d:%02d:%02d%+03d:00",
                    year.intValue(), month.intValue(), day.intValue(), hour.intValue(), minute.intValue(), second.intValue(), hourOffset.intValue());
            return FEEL_LIB.dateAndTime(literal);
        } catch (Exception e) {
            String message = String.format("dateTime(%s, %s, %s, %s, %s, %s, %s)", day, month, year, hour, minute, second, hourOffset);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal hour(XMLGregorianCalendar dateTime) {
        return FEEL_LIB.hour(dateTime);
    }

    @Override
    public BigDecimal hourDiff(XMLGregorianCalendar dateTime1, XMLGregorianCalendar dateTime2) {
        try {
            return BigDecimal.valueOf(TIME_LIB.hourDiff(dateTime1, dateTime2));
        } catch (Exception e) {
            String message = String.format("hourDiff(%s, %s)", dateTime1, dateTime2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal minute(XMLGregorianCalendar time) {
        return FEEL_LIB.minute(time);
    }

    @Override
    public BigDecimal second(XMLGregorianCalendar time) {
        return FEEL_LIB.second(time);
    }

    @Override
    public Duration timeOffset(XMLGregorianCalendar time) {
        return FEEL_LIB.timeOffset(time);
    }

    @Override
    public String timezone(XMLGregorianCalendar time) {
        return FEEL_LIB.timezone(time);
    }

    @Override
    public BigDecimal years(Duration duration) {
        return FEEL_LIB.years(duration);
    }

    @Override
    public BigDecimal months(Duration duration) {
        return FEEL_LIB.months(duration);
    }

    @Override
    public BigDecimal days(Duration duration) {
        return FEEL_LIB.days(duration);
    }

    @Override
    public BigDecimal hours(Duration duration) {
        return FEEL_LIB.hours(duration);
    }

    @Override
    public BigDecimal minutes(Duration duration) {
        return FEEL_LIB.minutes(duration);
    }

    @Override
    public BigDecimal seconds(Duration duration) {
        return FEEL_LIB.seconds(duration);
    }

    @Override
    public String string(Object object) {
        return FEEL_LIB.string(object);
    }

    @Override
    public BigDecimal minutesDiff(XMLGregorianCalendar dateTime1, XMLGregorianCalendar dateTime2) {
        try {
            return BigDecimal.valueOf(TIME_LIB.minutesDiff(dateTime1, dateTime2));
        } catch (Exception e) {
            String message = String.format("minutesDiff(%s, %s)", dateTime1, dateTime2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal month(XMLGregorianCalendar dateTime) {
        return FEEL_LIB.month(dateTime);
    }

    @Override
    public XMLGregorianCalendar monthAdd(XMLGregorianCalendar dateTime, BigDecimal monthsToAdd) {
        try {
            return DATE_LIB.monthAdd(dateTime, monthsToAdd);
        } catch (Exception e) {
            String message = String.format("monthAdd(%s, %s)", dateTime, monthsToAdd);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal monthDiff(XMLGregorianCalendar dateTime1, XMLGregorianCalendar dateTime2) {
        try {
            return BigDecimal.valueOf(DATE_LIB.monthDiff(dateTime1, dateTime2));
        } catch (Exception e) {
            String message = String.format("monthDiff(%s, %s)", dateTime1, dateTime2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public XMLGregorianCalendar now() {
        try {
            return DATE_TIME_LIB.now();
        } catch (Exception e) {
            String message = "now()";
            logError(message, e);
            return null;
        }
    }

    @Override
    public XMLGregorianCalendar today() {
        try {
            return DATE_LIB.today();
        } catch (Exception e) {
            String message = "today()";
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal weekday(XMLGregorianCalendar dateTime) {
        try {
            return BigDecimal.valueOf(DATE_LIB.weekday(dateTime));
        } catch (Exception e) {
            String message = String.format("weekday(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal year(XMLGregorianCalendar dateTime) {
        return FEEL_LIB.year(dateTime);
    }

    @Override
    public XMLGregorianCalendar yearAdd(XMLGregorianCalendar dateTime, BigDecimal yearsToAdd) {
        try {
            return DATE_LIB.yearAdd(dateTime, yearsToAdd);
        } catch (Exception e) {
            String message = String.format("yearAdd(%s, %s)", dateTime, yearsToAdd);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal yearDiff(XMLGregorianCalendar dateTime1, XMLGregorianCalendar dateTime2) {
        try {
            return DATE_LIB.yearDiff(dateTime1, dateTime2);
        } catch (Exception e) {
            String message = String.format("yearDiff(%s, %s)", dateTime1, dateTime2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List append(List list, Object element) {
        return FEEL_LIB.append(list, element);
    }

    @Override
    public BigDecimal number(String text, BigDecimal defaultValue) {
        try {
            return NUMBER_LIB.number(text, defaultValue);
        } catch (Exception e) {
            String message = String.format("number(%s, %s)", text, defaultValue);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String mid(String text, BigDecimal start, BigDecimal numChar) {
        try {
            return STRING_LIB.mid(text, start, numChar);
        } catch (Exception e) {
            String message = String.format("mid(%s, %s, %s)", text, start, numChar);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String left(String text, BigDecimal numChar) {
        try {
            return STRING_LIB.left(text, numChar);
        } catch (Exception e) {
            String message = String.format("left(%s, %s)", text, numChar);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String right(String text, BigDecimal numChar) {
        try {
            return STRING_LIB.right(text, numChar);
        } catch (Exception e) {
            String message = String.format("right(%s, %s)", text, numChar);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String text(BigDecimal num, String formatText) {
        try {
            return STRING_LIB.text(num, formatText);
        } catch (Exception e) {
            String message = String.format("text(%s, %s)", num, formatText);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal textOccurrences(String findText, String withinText) {
        try {
            return BigDecimal.valueOf(STRING_LIB.textOccurrences(findText, withinText));
        } catch (Exception e) {
            String message = String.format("textOccurrences(%s, %s)", findText, withinText);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean contains(String text, String substring) {
        return FEEL_LIB.contains(text, substring);
    }

    @Override
    public Boolean startsWith(String text, String prefix) {
        return FEEL_LIB.startsWith(text, prefix);
    }

    @Override
    public Boolean endsWith(String text, String suffix) {
        return FEEL_LIB.endsWith(text, suffix);
    }

    @Override
    public Boolean not(Boolean bool) {
        return this.booleanType.booleanNot(bool);
    }

    @Override
    public XMLGregorianCalendar date(String literal) {
        return FEEL_LIB.date(literal);
    }

    @Override
    public XMLGregorianCalendar date(XMLGregorianCalendar date) {
        return FEEL_LIB.date(date);
    }

    @Override
    public XMLGregorianCalendar time(String literal) {
        return FEEL_LIB.time(literal);
    }

    @Override
    public XMLGregorianCalendar time(XMLGregorianCalendar dateTime) {
        return FEEL_LIB.time(dateTime);
    }

    @Override
    public XMLGregorianCalendar time(BigDecimal hour, BigDecimal minute, BigDecimal second, Duration offset) {
        return FEEL_LIB.time(hour, minute, second, offset);
    }

    @Override
    public XMLGregorianCalendar dateAndTime(String literal) {
        return FEEL_LIB.dateAndTime(literal);
    }

    @Override
    public XMLGregorianCalendar dateAndTime(XMLGregorianCalendar date, XMLGregorianCalendar time) {
        return FEEL_LIB.dateAndTime(date, time);
    }

    @Override
    public Duration duration(String literal) {
        return FEEL_LIB.duration(literal);
    }

    //
    // List operations
    //

    @Override
    public List appendAll(List list1, List list2) {
        try {
            return LIST_LIB.appendAll(list1, list2);
        } catch (Exception e) {
            String message = String.format("appendAll(%s, %s)", list1, list2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List remove(List list, Object element) {
        try {
            return LIST_LIB.remove(list, element);
        } catch (Exception e) {
            String message = String.format("remove(%s, %s)", list, element);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List removeAll(List list1, List list2) {
        try {
            return LIST_LIB.removeAll(list1, list2);
        } catch (Exception e) {
            String message = String.format("removeAll(%s, %s)", list1, list2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean notContainsAny(List list1, List list2) {
        try {
            return LIST_LIB.notContainsAny(list1, list2);
        } catch (Exception e) {
            String message = String.format("notContainsAny(%s, %s)", list1, list2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean containsOnly(List list1, List list2) {
        try {
            return LIST_LIB.containsOnly(list1, list2);
        } catch (Exception e) {
            String message = String.format("containsOnly(%s, %s)", list1, list2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean areElementsOf(List list1, List list2) {
        try {
            return LIST_LIB.areElementsOf(list1, list2);
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
            return LIST_LIB.zip(attributes, values);
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
    public BigDecimal avg(List list) {
        return FEEL_LIB.mean(list);
    }

    @Override
    public BigDecimal max(List numbers) {
        return FEEL_LIB.max(numbers);
    }

    @Override
    public BigDecimal median(List numbers) {
        return FEEL_LIB.median(numbers);
    }

    @Override
    public BigDecimal min(List numbers) {
        return FEEL_LIB.min(numbers);
    }

    @Override
    public BigDecimal mode(List numbers) {
        try {
            return (BigDecimal) LIST_LIB.mode(numbers);
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
            return STRING_LIB.stringAdd(first, second);
        } catch (Exception e) {
            String message = String.format("+(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String concat(List<String> texts) {
        try {
            return STRING_LIB.concat(texts);
        } catch (Exception e) {
            String message = String.format("concat(%s)", texts);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean isAlpha(String text) {
        try {
            return STRING_LIB.isAlpha(text);
        } catch (Exception e) {
            String message = String.format("isAlpha(%s)", text);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean isAlphanumeric(String text) {
        try {
            return STRING_LIB.isAlphanumeric(text);
        } catch (Exception e) {
            String message = String.format("isAlphanumeric(%s)", text);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean isNumeric(String text) {
        try {
            return STRING_LIB.isNumeric(text);
        } catch (Exception e) {
            String message = String.format("concat(%s)", text);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean isSpaces(String text) {
        try {
            return STRING_LIB.isSpaces(text);
        } catch (Exception e) {
            String message = String.format("isSpaces(%s)", text);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String lower(String text) {
        try {
            return STRING_LIB.lower(text);
        } catch (Exception e) {
            String message = String.format("lower(%s)", text);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String trim(String text) {
        try {
            return STRING_LIB.trim(text);
        } catch (Exception e) {
            String message = String.format("trim(%s)", text);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String upper(String text) {
        try {
            return STRING_LIB.upper(text);
        } catch (Exception e) {
            String message = String.format("upper(%s)", text);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal number(String text) {
        try {
            return NUMBER_LIB.number(text);
        } catch (Exception e) {
            String message = String.format("number(%s)", text);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean and(List list) {
        return FEEL_LIB.and(list);
    }

    @Override
    public Boolean or(List list) {
        return FEEL_LIB.or(list);
    }

    @Override
    public Boolean listContains(List list, Object value) {
        return FEEL_LIB.listContains(list, value);
    }

    @Override
    public XMLGregorianCalendar toDate(Object object) {
        return FEEL_LIB.toDate(object);
    }

    @Override
    public XMLGregorianCalendar toTime(Object object) {
        return FEEL_LIB.toTime(object);
    }

    @Override
    public BigDecimal len(String text) {
        return FEEL_LIB.stringLength(text);
    }
}
