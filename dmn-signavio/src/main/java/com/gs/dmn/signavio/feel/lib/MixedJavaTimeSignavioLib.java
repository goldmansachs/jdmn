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
import com.gs.dmn.feel.lib.type.time.xml.DefaultDateTimeLib;
import com.gs.dmn.feel.lib.MixedJavaTimeFEELLib;
import com.gs.dmn.feel.lib.type.context.DefaultContextType;
import com.gs.dmn.feel.lib.type.list.DefaultListType;
import com.gs.dmn.feel.lib.type.logic.DefaultBooleanType;
import com.gs.dmn.feel.lib.type.numeric.DefaultNumericType;
import com.gs.dmn.feel.lib.type.string.DefaultStringType;
import com.gs.dmn.feel.lib.type.time.mixed.LocalDateType;
import com.gs.dmn.feel.lib.type.time.mixed.OffsetTimeType;
import com.gs.dmn.feel.lib.type.time.mixed.ZonedDateTimeType;
import com.gs.dmn.feel.lib.type.time.xml.DefaultDurationType;
import org.apache.commons.lang3.StringUtils;

import javax.xml.datatype.Duration;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.Period;
import java.time.ZonedDateTime;
import java.util.List;

import static com.gs.dmn.feel.lib.DefaultFEELLib.DATA_TYPE_FACTORY;

public class MixedJavaTimeSignavioLib extends BaseFEELLib<BigDecimal, LocalDate, OffsetTime, ZonedDateTime, Duration> implements SignavioLib<BigDecimal, LocalDate, OffsetTime, ZonedDateTime, Duration> {
    private final MixedJavaTimeFEELLib feelLib = new MixedJavaTimeFEELLib();

    private final SignavioNumberLib numberLib = new SignavioNumberLib();
    private final SignavioStringLib stringLib = new SignavioStringLib();
    private final SignavioListLib listLib = new SignavioListLib();
    
    public MixedJavaTimeSignavioLib() {
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
    public BigDecimal day(LocalDate date) {
        return feelLib.day(date);
    }
    public BigDecimal day(ZonedDateTime date) {
        return feelLib.day(date);
    }

    //
    // Date and time operations
    //

    @Override
    public LocalDate dayAdd(ZonedDateTime dateTime, BigDecimal daysToAdd) {
        try {
            return dateTime.plusDays(daysToAdd.intValue()).toLocalDate();
        } catch (Exception e) {
            String message = String.format("dayAdd(%s, %s)", dateTime, daysToAdd);
            logError(message, e);
            return null;
        }
    }
    public LocalDate dayAdd(LocalDate date, BigDecimal daysToAdd) {
        try {
            return date.plusDays(daysToAdd.intValue());
        } catch (Exception e) {
            String message = String.format("dayAdd(%s, %s)", date, daysToAdd);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal dayDiff(ZonedDateTime dateTime1, ZonedDateTime dateTime2) {
        try {
            long diff = durationBetween(dateTime1, dateTime2).getSeconds() / (60 * 60 * 24);
            return BigDecimal.valueOf(diff);
        } catch (Exception e) {
            String message = String.format("dayDiff(%s, %s)", dateTime1, dateTime2);
            logError(message, e);
            return null;
        }
    }
    public BigDecimal dayDiff(LocalDate date1, LocalDate date2) {
        try {
            long diff = durationBetween(date1, date2).getSeconds() / (60 * 60 * 24);
            return BigDecimal.valueOf(diff);
        } catch (Exception e) {
            String message = String.format("dayDiff(%s, %s)", date1, date2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public LocalDate date(BigDecimal year, BigDecimal month, BigDecimal day) {
        return feelLib.date(String.format("%04d-%02d-%02d", year.intValue(), month.intValue(), day.intValue()));
    }

    @Override
    public ZonedDateTime dateTime(BigDecimal day, BigDecimal month, BigDecimal year, BigDecimal hour, BigDecimal minute, BigDecimal second) {
        String literal = String.format("%04d-%02d-%02dT%02d:%02d:%02dZ",
                year.intValue(), month.intValue(), day.intValue(), hour.intValue(), minute.intValue(), second.intValue());
        return feelLib.dateAndTime(literal);
    }

    @Override
    public BigDecimal hour(OffsetTime time) {
        try {
            return BigDecimal.valueOf(time.getHour());
        } catch (Exception e) {
            String message = String.format("hour(%s)", time);
            logError(message, e);
            return null;
        }
    }
    public BigDecimal hour(ZonedDateTime dateTime) {
        try {
            return BigDecimal.valueOf(dateTime.getHour());
        } catch (Exception e) {
            String message = String.format("hour(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal hourDiff(ZonedDateTime dateTime1, ZonedDateTime dateTime2) {
        try {
            long diff = durationBetween(dateTime1, dateTime2).getSeconds() / (60 * 60);
            return BigDecimal.valueOf(diff);
        } catch (Exception e) {
            String message = String.format("hourDiff(%s, %s)", dateTime1, dateTime2);
            logError(message, e);
            return null;
        }
    }
    public BigDecimal hourDiff(OffsetTime time1, OffsetTime time2) {
        try {
            long diff = durationBetween(time1, time2).getSeconds() / (60 * 60);
            return BigDecimal.valueOf(diff);
        } catch (Exception e) {
            String message = String.format("hourDiff(%s, %s)", time1, time2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal minute(OffsetTime time) {
        try {
            return BigDecimal.valueOf(time.getMinute());
        } catch (Exception e) {
            String message = String.format("minute(%s)", time);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal second(OffsetTime time) {
        return feelLib.second(time);
    }

    @Override
    public Duration timeOffset(OffsetTime time) {
        return feelLib.timeOffset(time);
    }

    @Override
    public String timezone(OffsetTime time) {
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

    public BigDecimal minute(ZonedDateTime dateTime) {
        try {
            return BigDecimal.valueOf(dateTime.getMinute());
        } catch (Exception e) {
            String message = String.format("minute(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal minutesDiff(ZonedDateTime dateTime1, ZonedDateTime dateTime2) {
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
    public BigDecimal month(LocalDate date) {
        return feelLib.month(date);
    }
    public BigDecimal month(ZonedDateTime dateTime) {
        return feelLib.month(dateTime);
    }

    public BigDecimal minutesDiff(OffsetTime time1, OffsetTime time2) {
        try {
            long diff = durationBetween(time1, time2).getSeconds() / 60;
            return BigDecimal.valueOf(diff);
        } catch (Exception e) {
            String message = String.format("minutesDiff(%s, %s)", time1, time2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public ZonedDateTime monthAdd(ZonedDateTime dateTime, BigDecimal monthsToAdd) {
        try {
            return dateTime.plusMonths(monthsToAdd.longValue());
        } catch (Exception e) {
            String message = String.format("monthAdd(%s, %s)", dateTime, monthsToAdd);
            logError(message, e);
            return null;
        }
    }
    public ZonedDateTime monthAdd(LocalDate date, BigDecimal monthsToAdd) {
        try {
            return date.plusMonths(monthsToAdd.longValue()).atStartOfDay(DefaultDateTimeLib.UTC);
        } catch (Exception e) {
            String message = String.format("monthAdd(%s, %s)", date, monthsToAdd);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal monthDiff(ZonedDateTime dateTime1, ZonedDateTime dateTime2) {
        try {
            Period period = periodBetween(dateTime1, dateTime2);
            return BigDecimal.valueOf(period.toTotalMonths());
        } catch (Exception e) {
            String message = String.format("monthDiff(%s, %s)", dateTime1, dateTime2);
            logError(message, e);
            return null;
        }
    }
    public BigDecimal monthDiff(LocalDate date1, LocalDate date2) {
        try {
            Period period = periodBetween(date1, date2);
            return BigDecimal.valueOf(period.toTotalMonths());
        } catch (Exception e) {
            String message = String.format("monthDiff(%s, %s)", date1, date2);
            logError(message, e);
            return null;
        }
    }

    @Override
    public ZonedDateTime now() {
        try {
            return ZonedDateTime.now();
        } catch (Exception e) {
            String message = "now()";
            logError(message, e);
            return null;
        }
    }

    @Override
    public LocalDate today() {
        try {
            return now().toLocalDate();
        } catch (Exception e) {
            String message = "today()";
            logError(message, e);
            return null;
        }
    }

    public BigDecimal weekday(ZonedDateTime dateTime) {
        try {
            int weekDay = dateTime.getDayOfWeek().getValue();
            return BigDecimal.valueOf(weekDay);
        } catch (Exception e) {
            String message = String.format("weekday(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal year(LocalDate date) {
        return feelLib.year(date);
    }
    public BigDecimal year(ZonedDateTime dateTime) {
        return feelLib.year(dateTime);
    }

    public BigDecimal weekday(LocalDate date) {
        try {
            int weekDay = date.getDayOfWeek().getValue();
            return BigDecimal.valueOf(weekDay);
        } catch (Exception e) {
            String message = String.format("weekday(%s)", date);
            logError(message, e);
            return null;
        }
    }

    @Override
    public ZonedDateTime yearAdd(ZonedDateTime dateTime, BigDecimal yearsToAdd) {
        try {
            return dateTime.plusYears(yearsToAdd.longValue());
        } catch (Exception e) {
            String message = String.format("yearAdd(%s, %s)", dateTime, yearsToAdd);
            logError(message, e);
            return null;
        }
    }
    public ZonedDateTime yearAdd(LocalDate localDate, BigDecimal yearsToAdd) {
        try {
            return localDate.plusYears(yearsToAdd.longValue()).atStartOfDay(DefaultDateTimeLib.UTC);
        } catch (Exception e) {
            String message = String.format("yearAdd(%s, %s)", localDate, yearsToAdd);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal yearDiff(ZonedDateTime dateTime1, ZonedDateTime dateTime2) {
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

    public BigDecimal yearDiff(LocalDate dateTime1, LocalDate dateTime2) {
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
        if (text == null || numChar == null) {
            return null;
        }

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
    public LocalDate date(String literal) {
        return feelLib.date(literal);
    }

    public LocalDate date(LocalDate date) {
        return feelLib.date(date);
    }
    @Override
    public LocalDate date(ZonedDateTime date) {
        return feelLib.date(date);
    }

    @Override
    public OffsetTime time(String literal) {
        return feelLib.time(literal);
    }

    public OffsetTime time(OffsetTime time) {
        return feelLib.time(time);
    }
    @Override
    public OffsetTime time(ZonedDateTime dateTime) {
        return feelLib.time(dateTime);
    }

    @Override
    public OffsetTime time(BigDecimal hour, BigDecimal minute, BigDecimal second, Duration offset) {
        return feelLib.time(hour, minute, second, offset);
    }

    @Override
    public ZonedDateTime dateAndTime(String literal) {
        return feelLib.dateAndTime(literal);
    }

    @Override
    public ZonedDateTime dateAndTime(LocalDate date, OffsetTime time) {
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
    public LocalDate toDate(Object object) {
        return feelLib.toDate(object);
    }

    @Override
    public OffsetTime toTime(Object object) {
        return feelLib.toTime(object);
    }

    @Override
    public BigDecimal len(String text) {
        return text == null ? null : number(String.format("%d", text.length()));
    }

    private Period periodBetween(LocalDate date1, LocalDate date2) {
        return Period.between(date1, date2);
    }

    private Period periodBetween(ZonedDateTime dateTime1, ZonedDateTime dateTime2) {
        LocalDate localDate1 = LocalDate.of(dateTime1.getYear(), dateTime1.getMonth(), dateTime1.getDayOfMonth());
        LocalDate localDate2 = LocalDate.of(dateTime2.getYear(), dateTime2.getMonth(), dateTime2.getDayOfMonth());
        return Period.between(localDate1, localDate2);
    }

    private java.time.Duration durationBetween(LocalDate date1, LocalDate date2) {
        return java.time.Duration.between(date1.atStartOfDay(DefaultDateTimeLib.UTC), date2.atStartOfDay(DefaultDateTimeLib.UTC));
    }

    private java.time.Duration durationBetween(OffsetTime time1, OffsetTime time2) {
        return java.time.Duration.between(time1, time2);
    }

    private java.time.Duration durationBetween(ZonedDateTime dateTime1, ZonedDateTime dateTime2) {
        return java.time.Duration.between(dateTime1, dateTime2);
    }
}
