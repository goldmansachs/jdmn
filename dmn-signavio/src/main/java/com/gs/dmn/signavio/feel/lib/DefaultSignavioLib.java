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

import com.gs.dmn.feel.lib.*;
import com.gs.dmn.feel.lib.type.context.DefaultContextType;
import com.gs.dmn.feel.lib.type.list.DefaultListType;
import com.gs.dmn.feel.lib.type.logic.DefaultBooleanType;
import com.gs.dmn.feel.lib.type.numeric.DefaultNumericType;
import com.gs.dmn.feel.lib.type.string.DefaultStringType;
import com.gs.dmn.feel.lib.type.time.xml.DefaultDurationType;
import com.gs.dmn.feel.lib.type.time.xml.FEELXMLGregorianCalendar;
import com.gs.dmn.signavio.feel.lib.type.time.xml.DefaultDateTimeType;
import com.gs.dmn.signavio.feel.lib.type.time.xml.DefaultDateType;
import com.gs.dmn.signavio.feel.lib.type.time.xml.DefaultTimeType;
import org.apache.commons.lang3.StringUtils;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Calendar;
import java.util.List;

import static com.gs.dmn.feel.lib.DefaultFEELLib.DATA_TYPE_FACTORY;

public class DefaultSignavioLib extends BaseFEELLib<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar, Duration> implements SignavioLib<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar, Duration> {
    private final DefaultFEELLib feelLib = new DefaultFEELLib();

    private final SignavioNumberLib numberLib = new SignavioNumberLib();
    private final SignavioStringLib stringLib = new SignavioStringLib();
    private final DateLib dateLib = new DateLib();
    private final TimeLib timeLib = new TimeLib();
    private final DateTimeLib dateTimeLib = new DateTimeLib();
    private final SignavioListLib listLib = new SignavioListLib();
    
    public DefaultSignavioLib() {
        super(new DefaultNumericType(LOGGER),
                new DefaultBooleanType(LOGGER),
                new DefaultStringType(LOGGER),
                new DefaultDateType(LOGGER, DATA_TYPE_FACTORY),
                new DefaultTimeType(LOGGER, DATA_TYPE_FACTORY),
                new DefaultDateTimeType(LOGGER, DATA_TYPE_FACTORY),
                new DefaultDurationType(LOGGER),
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
    public BigDecimal abs(BigDecimal number) {
        if (number == null) {
            return null;
        }

        try {
            return number.abs();
        } catch (Exception e) {
            String message = String.format("abs(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal count(List list) {
        return feelLib.count(list);
    }

    @Override
    public BigDecimal round(BigDecimal number, BigDecimal digits) {
        try {
            return this.numberLib.round(number, digits);
        } catch (Exception e) {
            String message = String.format("round(%s, %s)", number, digits);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal ceiling(BigDecimal number) {
        return feelLib.ceiling(number);
    }

    @Override
    public BigDecimal floor(BigDecimal number) {
        return feelLib.floor(number);
    }

    @Override
    public BigDecimal integer(BigDecimal number) {
        try {
            return BigDecimal.valueOf(number.intValue());
        } catch (Exception e) {
            String message = String.format("integer(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal modulo(BigDecimal divident, BigDecimal divisor) {
        if (divident == null || divisor == null) {
            return null;
        }

        try {
            return BigDecimal.valueOf(divident.intValue() % divisor.intValue());
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
            return ((DefaultNumericType)this.numericType).numericExponentiation(base, (int)exponent.intValue());
        } catch (Exception e) {
            String message = String.format("power(%s, %s)", base, exponent);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal product(List factors) {
        try {
            BigDecimal product = BigDecimal.ONE;
            for (Object o : factors) {
                product = product.multiply((BigDecimal) o);
            }
            return product;
        } catch (Exception e) {
            String message = String.format("product(%s)", factors);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal roundDown(BigDecimal number, BigDecimal digits) {
        try {
            return this.numberLib.roundDown(number, digits);
        } catch (Exception e) {
            String message = String.format("roundDown(%s, %s)", number, digits);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal roundUp(BigDecimal number, BigDecimal digits) {
        try {
            return this.numberLib.roundUp(number, digits);
        } catch (Exception e) {
            String message = String.format("roundUp(%s, %s)", number, digits);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal sum(List numbers) {
        return feelLib.sum(numbers);
    }

    @Override
    public BigDecimal day(XMLGregorianCalendar date) {
        return feelLib.day(date);
    }

    //
    // Date and time operations
    //

    @Override
    public XMLGregorianCalendar dayAdd(XMLGregorianCalendar dateTime, BigDecimal daysToAdd) {
        try {
            XMLGregorianCalendar result = (XMLGregorianCalendar) dateTime.clone();
            int days = daysToAdd.intValue();
            boolean isPositive = days > 0;
            Duration duration = null;
            duration = DATA_TYPE_FACTORY.newDurationDayTime(
                    isPositive, daysToAdd.abs().intValue(), 0, 0, 0);
            result.add(duration);
            return result;
        } catch (Exception e) {
            String message = String.format("dayAdd(%s, %s)", dateTime, daysToAdd);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal dayDiff(XMLGregorianCalendar dateTime1, XMLGregorianCalendar dateTime2) {
        try {
            long diff = durationBetween(dateTime1, dateTime2).getSeconds() / (60 * 60 * 24);
            return BigDecimal.valueOf(diff);
        } catch (Exception e) {
            String message = String.format("dayDiff(%s, %s)", dateTime1, dateTime2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public XMLGregorianCalendar date(BigDecimal year, BigDecimal month, BigDecimal day) {
        return FEELXMLGregorianCalendar.makeXMLCalendar(this.dateLib.date(String.format("%04d-%02d-%02d", year.intValue(), month.intValue(), day.intValue())));
    }

    @Override
    public XMLGregorianCalendar dateTime(BigDecimal day, BigDecimal month, BigDecimal year, BigDecimal hour, BigDecimal minute, BigDecimal second) {
        String literal = String.format("%04d-%02d-%02dT%02d:%02d:%02dZ",
                year.intValue(), month.intValue(), day.intValue(), hour.intValue(), minute.intValue(), second.intValue());
        return FEELXMLGregorianCalendar.makeXMLCalendar(this.dateTimeLib.dateAndTime(literal));
    }

    @Override
    public BigDecimal hour(XMLGregorianCalendar dateTime) {
        try {
            return BigDecimal.valueOf(dateTime.getHour());
        } catch (Exception e) {
            String message = String.format("hour(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal hourDiff(XMLGregorianCalendar dateTime1, XMLGregorianCalendar dateTime2) {
        try {
            long diff = durationBetween(dateTime1, dateTime2).getSeconds() / (60 * 60);
            return BigDecimal.valueOf(diff);
        } catch (Exception e) {
            String message = String.format("hourDiff(%s, %s)", dateTime1, dateTime2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal minute(XMLGregorianCalendar time) {
        if (time == null) {
            return null;
        }

        try {
            return BigDecimal.valueOf(time.getMinute());
        } catch (Exception e) {
            String message = String.format("minute(%s)", time);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal second(XMLGregorianCalendar time) {
        return feelLib.second(time);
    }

    @Override
    public Duration timeOffset(XMLGregorianCalendar time) {
        return feelLib.timeOffset(time);
    }

    @Override
    public String timezone(XMLGregorianCalendar time) {
        return feelLib.timezone(time);
    }

    @Override
    public BigDecimal years(Duration duration) {
        return feelLib.years(duration);
    }

    @Override
    public BigDecimal months(Duration duration) {
        return feelLib.months(duration);
    }

    @Override
    public BigDecimal days(Duration duration) {
        return feelLib.days(duration);
    }

    @Override
    public BigDecimal hours(Duration duration) {
        return feelLib.hours(duration);
    }

    @Override
    public BigDecimal minutes(Duration duration) {
        return feelLib.minutes(duration);
    }

    @Override
    public BigDecimal seconds(Duration duration) {
        return feelLib.seconds(duration);
    }

    @Override
    public String string(Object object) {
        return feelLib.string(object);
    }

    @Override
    public BigDecimal minutesDiff(XMLGregorianCalendar dateTime1, XMLGregorianCalendar dateTime2) {
        try {
            long diff = durationBetween(dateTime1, dateTime2).getSeconds() / 60;
            return BigDecimal.valueOf(diff);
        } catch (Exception e) {
            String message = String.format("minutesDiff(%s, %s)", dateTime1, dateTime2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal month(XMLGregorianCalendar dateTime) {
        return feelLib.month(dateTime);
    }

    @Override
    public XMLGregorianCalendar monthAdd(XMLGregorianCalendar dateTime, BigDecimal monthsToAdd) {
        try {
            XMLGregorianCalendar result = (XMLGregorianCalendar) dateTime.clone();
            int months = monthsToAdd.intValue();
            boolean isPositive = months > 0;
            Duration duration = null;
            duration = DATA_TYPE_FACTORY.newDurationYearMonth(
                    isPositive, 0, monthsToAdd.abs().intValue());
            result.add(duration);
            return result;
        } catch (Exception e) {
            String message = String.format("monthAdd(%s, %s)", dateTime, monthsToAdd);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal monthDiff(XMLGregorianCalendar dateTime1, XMLGregorianCalendar dateTime2) {
        try {
            Period period = periodBetween(dateTime1, dateTime2);
            return BigDecimal.valueOf(period.toTotalMonths());
        } catch (Exception e) {
            String message = String.format("monthDiff(%s, %s)", dateTime1, dateTime2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public XMLGregorianCalendar now() {
        try {
            return FEELXMLGregorianCalendar.makeXMLCalendar(LocalDate.now());
        } catch (Exception e) {
            String message = "now()";
            logError(message, e);
            return null;
        }
    }

    @Override
    public XMLGregorianCalendar today() {
        try {
            LocalDate ld = LocalDate.now();
            XMLGregorianCalendar xmlGregorianCalendar = FEELXMLGregorianCalendar.makeXMLCalendar(ld);
            return xmlGregorianCalendar;
        } catch (Exception e) {
            String message = "today()";
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal weekday(XMLGregorianCalendar dateTime) {
        if (dateTime == null) {
            return null;
        }

        try {
            int weekDay = dateTime.toGregorianCalendar().get(Calendar.DAY_OF_WEEK);
            weekDay--;
            if (weekDay == 0) {
                weekDay = 7;
            }
            return BigDecimal.valueOf(weekDay);
        } catch (Exception e) {
            String message = String.format("weekday(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal year(XMLGregorianCalendar dateTime) {
        return feelLib.year(dateTime);
    }

    @Override
    public XMLGregorianCalendar yearAdd(XMLGregorianCalendar dateTime, BigDecimal yearsToAdd) {
        try {
            XMLGregorianCalendar result = (XMLGregorianCalendar) dateTime.clone();
            int months = yearsToAdd.intValue();
            boolean isPositive = months > 0;
            Duration duration = null;
            duration = DATA_TYPE_FACTORY.newDurationYearMonth(
                    isPositive, yearsToAdd.abs().intValue(), 0);
            result.add(duration);
            return result;
        } catch (Exception e) {
            String message = String.format("yearAdd(%s, %s)", dateTime, yearsToAdd);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal yearDiff(XMLGregorianCalendar dateTime1, XMLGregorianCalendar dateTime2) {
        try {
            Period period = periodBetween(dateTime1, dateTime2);
            return BigDecimal.valueOf(period.getYears());
        } catch (Exception e) {
            String message = String.format("yearDiff(%s, %s)", dateTime1, dateTime2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List append(List list, Object element) {
        return feelLib.append(list, element);
    }

    @Override
    public BigDecimal number(String text, String defaultValue) {
        return text != null ? number(text) : number(defaultValue);
    }

    @Override
    public String mid(String text, BigDecimal start, BigDecimal numChar) {
        try {
            int endIndex = start.intValue() + numChar.intValue();
            if (endIndex > text.length()) {
                endIndex = text.length();
            }
            return text.substring(start.intValue(), endIndex);
        } catch (Exception e) {
            String message = String.format("mid(%s, %s, %s)", text, start, numChar);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String left(String text, BigDecimal numChar) {
        if (text == null) {
            return null;
        }

        try {
            return text.substring(0, numChar.intValue());
        } catch (Exception e) {
            String message = String.format("left(%s, %s)", text, numChar);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String right(String text, BigDecimal numChar) {
        try {
            return text.substring(text.length() - numChar.intValue());
        } catch (Exception e) {
            String message = String.format("right(%s, %s)", text, numChar);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String text(BigDecimal num, String formatText) {
        try {
            DecimalFormat df = new DecimalFormat(formatText);
            return df.format(num);
        } catch (Exception e) {
            String message = String.format("text(%s, %s)", num, formatText);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal textOccurrences(String findText, String withinText) {
        if (findText == null || withinText == null) {
            return null;
        }
        int count = 0;
        int i = 0;
        while (i < withinText.length()) {
            if (withinText.substring(i).startsWith(findText)) {
                count++;
                i += findText.length();
            } else {
                i++;
            }
        }
        return BigDecimal.valueOf(count);
    }

    @Override
    public Boolean contains(String text, String substring) {
        return feelLib.contains(text, substring);
    }

    @Override
    public Boolean startsWith(String text, String prefix) {
        return feelLib.startsWith(text, prefix);
    }

    @Override
    public Boolean endsWith(String text, String suffix) {
        return feelLib.endsWith(text, suffix);
    }

    @Override
    public XMLGregorianCalendar date(String literal) {
        return feelLib.date(literal);
    }

    @Override
    public XMLGregorianCalendar date(XMLGregorianCalendar date) {
        return feelLib.date(date);
    }

    @Override
    public XMLGregorianCalendar time(String literal) {
        return feelLib.time(literal);
    }

    @Override
    public XMLGregorianCalendar time(XMLGregorianCalendar dateTime) {
        return feelLib.time(dateTime);
    }

    @Override
    public XMLGregorianCalendar time(BigDecimal hour, BigDecimal minute, BigDecimal second, Duration offset) {
        return feelLib.time(hour, minute, second, offset);
    }

    @Override
    public XMLGregorianCalendar dateAndTime(String literal) {
        return feelLib.dateAndTime(literal);
    }

    @Override
    public XMLGregorianCalendar dateAndTime(XMLGregorianCalendar date, XMLGregorianCalendar time) {
        return feelLib.dateAndTime(date, time);
    }

    @Override
    public Duration duration(String literal) {
        return feelLib.duration(literal);
    }

    //
    // List operations
    //

    @Override
    public List appendAll(List list1, List list2) {
        return this.listLib.appendAll(list1, list2);
    }

    @Override
    public List remove(List list, Object element) {
        return this.listLib.remove(list, element);
    }

    @Override
    public List removeAll(List list1, List list2) {
        return this.listLib.removeAll(list1, list2);
    }

    @Override
    public Boolean notContainsAny(List list1, List list2) {
        return this.listLib.notContainsAny(list1, list2);
    }

    @Override
    public Boolean containsOnly(List list1, List list2) {
        return this.listLib.containsOnly(list1, list2);
    }

    @Override
    public Boolean areElementsOf(List list1, List list2) {
        return this.listLib.areElementsOf(list1, list2);
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
    public BigDecimal avg(List list) {
        try {
            return feelLib.mean(list);
        } catch (Exception e) {
            String message = String.format("avg(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal max(List numbers) {
        return feelLib.max(numbers);
    }

    @Override
    public BigDecimal median(List numbers) {
        try {
            return feelLib.median(numbers);
        } catch (Exception e) {
            String message = String.format("median(%s)", numbers);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal min(List numbers) {
        return feelLib.min(numbers);
    }

    @Override
    public BigDecimal mode(List numbers) {
        try {
            return (BigDecimal) this.listLib.mode(numbers);
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
            return null;
        }
    }

    @Override
    public Boolean isAlpha(String text) {
        return StringUtils.isAlpha(text);
    }

    @Override
    public Boolean isAlphanumeric(String text) {
        return StringUtils.isAlphanumeric(text);
    }

    @Override
    public Boolean isNumeric(String text) {
        return StringUtils.isNumeric(text);
    }

    @Override
    public Boolean isSpaces(String text) {
        return StringUtils.isBlank(text);
    }

    @Override
    public String lower(String text) {
        return text == null ? null : text.toLowerCase();
    }

    @Override
    public String trim(String text) {
        return text == null ? null : text.trim();
    }

    @Override
    public String upper(String text) {
        return text == null ? null : text.toUpperCase();
    }

    @Override
    public BigDecimal number(String text) {
        return feelLib.number(text);
    }

    @Override
    public Boolean and(List list) {
        return feelLib.and(list);
    }

    @Override
    public Boolean or(List list) {
        return feelLib.or(list);
    }

    @Override
    public Boolean listContains(List list, Object value) {
        return feelLib.listContains(list, value);
    }

    @Override
    public XMLGregorianCalendar toDate(Object object) {
        return feelLib.toDate(object);
    }

    @Override
    public XMLGregorianCalendar toTime(Object object) {
        return feelLib.toTime(object);
    }

    @Override
    public BigDecimal len(String text) {
        return text == null ? null : number(String.format("%d", text.length()));
    }

    private Period periodBetween(XMLGregorianCalendar dateTime1, XMLGregorianCalendar dateTime2) {
        LocalDate localDate1 = LocalDate.of(dateTime1.getYear(), dateTime1.getMonth(), dateTime1.getDay());
        LocalDate localDate2 = LocalDate.of(dateTime2.getYear(), dateTime2.getMonth(), dateTime2.getDay());
        return Period.between(localDate1, localDate2);
    }

    private java.time.Duration durationBetween(XMLGregorianCalendar dateTime1, XMLGregorianCalendar dateTime2) {
        if (isDate(dateTime1) && isDate(dateTime2)) {
            LocalDateTime localDateTime1 = LocalDateTime.of(
                    dateTime1.getYear(), dateTime1.getMonth(), dateTime1.getDay(),
                    0, 0, 0);
            LocalDateTime localDateTime2 = LocalDateTime.of(
                    dateTime2.getYear(), dateTime2.getMonth(), dateTime2.getDay(),
                    0, 0, 0);
            return java.time.Duration.between(localDateTime1, localDateTime2);

        } else if (isTime(dateTime1) && isTime(dateTime2)) {
            LocalDateTime localDateTime1 = LocalDateTime.of(1972, 1, 1, dateTime1.getHour(), dateTime1.getMinute(), dateTime1.getSecond());
            LocalDateTime localDateTime2 = LocalDateTime.of(1972, 1, 1, dateTime2.getHour(), dateTime2.getMinute(), dateTime2.getSecond());
            return java.time.Duration.between(localDateTime1, localDateTime2);
        } else if (isDateTime(dateTime1) && isDateTime(dateTime2)) {
            LocalDateTime localDateTime1 = LocalDateTime.of(
                    dateTime1.getYear(), dateTime1.getMonth(), dateTime1.getDay(),
                    dateTime1.getHour(), dateTime1.getMinute(), dateTime1.getSecond());
            LocalDateTime localDateTime2 = LocalDateTime.of(
                    dateTime2.getYear(), dateTime2.getMonth(), dateTime2.getDay(),
                    dateTime2.getHour(), dateTime2.getMinute(), dateTime2.getSecond());
            return java.time.Duration.between(localDateTime1, localDateTime2);
        } else {
            String message = String.format("durationBetween(%s, %s)", dateTime1, dateTime2);
            logError(message, null);
            return null;
        }
    }

    private boolean isDate(XMLGregorianCalendar dateTime) {
        return dateTime.getYear() > 0 && dateTime.getHour() < 0;
    }

    private boolean isTime(XMLGregorianCalendar dateTime1) {
        return dateTime1.getYear() < 0 && dateTime1.getHour() > 0;
    }

    private boolean isDateTime(XMLGregorianCalendar dateTime1) {
        return dateTime1.getYear() > 0 && dateTime1.getHour() > 0;
    }
}
