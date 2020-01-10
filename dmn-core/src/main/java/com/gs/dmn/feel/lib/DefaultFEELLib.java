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
import com.gs.dmn.feel.lib.type.time.xml.*;
import com.gs.dmn.runtime.LambdaExpression;
import org.apache.commons.lang3.StringUtils;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class DefaultFEELLib extends BaseFEELLib<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar, Duration> implements StandardFEELLib<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar, Duration> {
    public static final DatatypeFactory DATA_TYPE_FACTORY = XMLDatataypeFactory.newInstance();

    private final BigDecimalLib numberLib = new BigDecimalLib();
    private final StringLib stringLib = new StringLib();
    private final BooleanLib booleanLib = new BooleanLib();
    private final DateLib dateLib = new DateLib();
    private final TimeLib timeLib = new TimeLib();
    private final DateTimeLib dateTimeLib = new DateTimeLib();
    private final DurationLib durationLib = new DurationLib();
    private final ListLib listLib = new ListLib();

    public DefaultFEELLib() {
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
    public XMLGregorianCalendar date(String literal) {
        if (StringUtils.isBlank(literal)) {
            return null;
        }

        try {
            XMLGregorianCalendar calendar = FEELXMLGregorianCalendar.makeXMLCalendar(this.dateLib.date(literal));
            return this.dateTimeLib.isValidDate(calendar) ? calendar : null;
        } catch (Exception e) {
            String message = String.format("date(%s)", literal);
            logError(message, e);
            return null;
        }
    }

    @Override
    public XMLGregorianCalendar date(BigDecimal year, BigDecimal month, BigDecimal day) {
        if (year == null || month == null || day == null) {
            return null;
        }

        XMLGregorianCalendar calendar = FEELXMLGregorianCalendar.makeDate(year.toBigInteger(), month.intValue(), day.intValue());
        return this.dateTimeLib.isValidDate(calendar) ? calendar : null;
    }

    @Override
    public XMLGregorianCalendar date(XMLGregorianCalendar from) {
        if (from == null) {
            return null;
        }

        FEELXMLGregorianCalendar calendar = (FEELXMLGregorianCalendar) from.clone();
        calendar.setTime(DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED);
        calendar.setZoneID(null);
        return this.dateTimeLib.isValidDate(calendar) ? calendar : null;
    }

    @Override
    public XMLGregorianCalendar time(String literal) {
        if (literal == null) {
            return null;
        }
        try {
            literal = this.dateTimeLib.fixDateTimeFormat(literal);
            XMLGregorianCalendar calendar = FEELXMLGregorianCalendar.makeXMLCalendar(this.timeLib.time(literal));
            return this.dateTimeLib.isValidTime(calendar) ? calendar : null;
        } catch (Exception e) {
            String message = String.format("time(%s)", literal);
            logError(message, e);
            return null;
        }
    }

    @Override
    public XMLGregorianCalendar time(BigDecimal hour, BigDecimal minute, BigDecimal second, Duration offset) {
        if (hour == null || minute == null || second == null) {
            return null;
        }

        try {
            XMLGregorianCalendar calendar = null;
            if (offset != null) {
                BigDecimal secondFraction = second.subtract(BigDecimal.valueOf(second.intValue()));
                String sign = offset.getSign() < 0 ? "-" : "+";
                int seconds = offset.getSeconds();
                String zoneId;
                if (seconds == 0) {
                    zoneId = String.format("%s%02d:%02d", sign, offset.getHours(), offset.getMinutes());
                } else {
                    zoneId = String.format("%s%02d:%02d:%02d", sign, offset.getHours(), offset.getMinutes(), seconds);
                }
                calendar = FEELXMLGregorianCalendar.makeTime(hour.intValue(), minute.intValue(), second.intValue(), secondFraction, zoneId);
            } else {
                BigDecimal secondFraction = second.subtract(BigDecimal.valueOf(second.intValue()));
                calendar = FEELXMLGregorianCalendar.makeTime(hour.intValue(), minute.intValue(), second.intValue(), secondFraction, null);
            }
            return this.dateTimeLib.isValidTime(calendar) ? calendar : null;
        } catch (Exception e) {
            String message = String.format("time(%s, %s, %s, %s)", hour, minute, second, offset);
            logError(message, e);
            return null;
        }
    }

    @Override
    public XMLGregorianCalendar time(XMLGregorianCalendar from) {
        if (from == null) {
            return null;
        }

        FEELXMLGregorianCalendar calendar = (FEELXMLGregorianCalendar) from.clone();
        if (from.getXMLSchemaType() == DatatypeConstants.DATE) {
            calendar.setYear(DatatypeConstants.FIELD_UNDEFINED);
            calendar.setMonth(DatatypeConstants.FIELD_UNDEFINED);
            calendar.setDay(DatatypeConstants.FIELD_UNDEFINED);
            calendar.setHour(0);
            calendar.setMinute(0);
            calendar.setSecond(0);
            calendar.setZoneID("Z");
        } else if (from.getXMLSchemaType() == DatatypeConstants.DATETIME) {
            calendar.setYear(DatatypeConstants.FIELD_UNDEFINED);
            calendar.setMonth(DatatypeConstants.FIELD_UNDEFINED);
            calendar.setDay(DatatypeConstants.FIELD_UNDEFINED);
        }
        return this.dateTimeLib.isValidTime(calendar) ? calendar : null;
    }

    @Override
    public XMLGregorianCalendar dateAndTime(String literal) {
        if (literal == null) {
            return null;
        }

        try {
            literal = this.dateTimeLib.fixDateTimeFormat(literal);
            XMLGregorianCalendar calendar = FEELXMLGregorianCalendar.makeXMLCalendar(this.dateTimeLib.dateAndTime(literal));
            return this.dateTimeLib.isValidDateTime(calendar) ? calendar : null;
        } catch (Exception e) {
            String message = String.format("dateAndTime(%s)", literal);
            logError(message, e);
            return null;
        }
    }

    @Override
    public XMLGregorianCalendar dateAndTime(XMLGregorianCalendar date, XMLGregorianCalendar time) {
        if (date == null || time == null) {
            return null;
        }

        try {
            XMLGregorianCalendar calendar = FEELXMLGregorianCalendar.makeDateTime(
                    BigInteger.valueOf(date.getYear()), date.getMonth(), date.getDay(),
                    time.getHour(), time.getMinute(), time.getSecond(), time.getFractionalSecond(),
                    ((FEELXMLGregorianCalendar) time).getZoneID()
            );
            return this.dateTimeLib.isValidDateTime(calendar) ? calendar : null;
        } catch (Exception e) {
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
    public Duration yearsAndMonthsDuration(XMLGregorianCalendar from, XMLGregorianCalendar to) {
        if (from == null || to == null) {
            return null;
        }

        try {
            LocalDate toLocalDate = LocalDate.of(to.getYear(), to.getMonth(), to.getDay());
            LocalDate fromLocalDate = LocalDate.of(from.getYear(), from.getMonth(), from.getDay());
            return this.dateTimeLib.toYearsMonthDuration(DATA_TYPE_FACTORY, toLocalDate, fromLocalDate);
        } catch (Exception e) {
            String message = String.format("yearsAndMonthsDuration(%s, %s)", from, to);
            logError(message, e);
            return null;
        }
    }

    @Override
    public XMLGregorianCalendar toDate(Object object) {
        return (XMLGregorianCalendar) object;
    }

    @Override
    public XMLGregorianCalendar toTime(Object object) {
        return (XMLGregorianCalendar) object;
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
        try {
            return this.numberLib.mean(list);
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
    public BigDecimal year(XMLGregorianCalendar date) {
        if (date == null) {
            return null;
        }

        try {
            return BigDecimal.valueOf(date.getYear());
        } catch (Exception e) {
            String message = String.format("year(%s)", date);
            logError(message, e);
            return null;
        }
    }

    public BigDecimal month(XMLGregorianCalendar date) {
        if (date == null) {
            return null;
        }

        try {
            return BigDecimal.valueOf(date.getMonth());
        } catch (Exception e) {
            String message = String.format("month(%s)", date);
            logError(message, e);
            return null;
        }
    }

    public BigDecimal day(XMLGregorianCalendar date) {
        if (date == null) {
            return null;
        }

        try {
            return BigDecimal.valueOf(date.getDay());
        } catch (Exception e) {
            String message = String.format("day(%s)", date);
            logError(message, e);
            return null;
        }
    }

    public BigDecimal weekday(XMLGregorianCalendar date) {
        if (date == null) {
            return null;
        }

        try {
            return BigDecimal.valueOf((long) date.toGregorianCalendar().get(Calendar.DAY_OF_WEEK) - 1);
        } catch (Exception e) {
            String message = String.format("day(%s)", date);
            logError(message, e);
            return null;
        }
    }

    //
    // Time functions
    //
    public BigDecimal hour(XMLGregorianCalendar date) {
        if (date == null) {
            return null;
        }

        return BigDecimal.valueOf(date.getHour());
    }

    public BigDecimal minute(XMLGregorianCalendar date) {
        if (date == null) {
            return null;
        }

        return BigDecimal.valueOf(date.getMinute());
    }

    public BigDecimal second(XMLGregorianCalendar date) {
        if (date == null) {
            return null;
        }

        return BigDecimal.valueOf(date.getSecond());
    }

    public Duration timeOffset(XMLGregorianCalendar date) {
        if (date == null) {
            return null;
        }

        int secondsOffset = date.getTimezone();
        if (secondsOffset == DatatypeConstants.FIELD_UNDEFINED) {
            return null;
        } else {
            return DATA_TYPE_FACTORY.newDuration((long) secondsOffset * 1000);
        }
    }

    public String timezone(XMLGregorianCalendar date) {
        if (date == null) {
            return null;
        }

        return ((FEELXMLGregorianCalendar) date).getZoneID();
    }

    //
    // Duration functions
    //
    public BigDecimal years(Duration duration) {
        if (duration == null) {
            return null;
        }

        if (isYearMonthsTime(duration)) {
            return BigDecimal.valueOf(duration.getYears());
        } else {
            return null;
        }
    }

    public BigDecimal months(Duration duration) {
        if (duration == null) {
            return null;
        }

        if (isYearMonthsTime(duration)) {
            return BigDecimal.valueOf(duration.getMonths());
        } else {
            return null;
        }
    }

    public BigDecimal days(Duration duration) {
        if (duration == null) {
            return null;
        }

        if (isDayTime(duration)) {
            return BigDecimal.valueOf(duration.getDays());
        } else {
            return null;
        }
    }

    public BigDecimal hours(Duration duration) {
        if (duration == null) {
            return null;
        }

        if (isDayTime(duration)) {
            return BigDecimal.valueOf(duration.getHours());
        } else {
            return null;
        }
    }

    public BigDecimal minutes(Duration duration) {
        if (duration == null) {
            return null;
        }

        if (isDayTime(duration)) {
            return BigDecimal.valueOf(duration.getMinutes());
        } else {
            return null;
        }
    }

    public BigDecimal seconds(Duration duration) {
        if (duration == null) {
            return null;
        }

        if (isDayTime(duration)) {
            return BigDecimal.valueOf(duration.getSeconds());
        } else {
            return null;
        }
    }

    private boolean isYearMonthsTime(Duration duration) {
        return duration.isSet(DatatypeConstants.YEARS)
                || duration.isSet(DatatypeConstants.MONTHS)
                ;
    }

    private boolean isDayTime(Duration duration) {
        return duration.isSet(DatatypeConstants.DAYS)
                || duration.isSet(DatatypeConstants.HOURS)
                || duration.isSet(DatatypeConstants.MINUTES)
                || duration.isSet(DatatypeConstants.SECONDS)
                ;
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
    public BigDecimal count(List list) {
        try {
            return this.numberLib.count(list);
        } catch (Exception e) {
            String message = String.format("count(%s)", list);
            logError(message, e);
            return null;
        }
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
        try {
            return this.listLib.sublist(list, startPosition.intValue());
        } catch (Exception e) {
            String message = String.format("min(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List sublist(List list, BigDecimal startPosition, BigDecimal length) {
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
    public List insertBefore(List list, BigDecimal position, Object newItem) {
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
                if (o == null && match == null || o != null && o.equals(match)) {
                    result.add(BigDecimal.valueOf((long) i + 1));
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
        } catch (Exception e) {
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
