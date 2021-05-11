/**
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
import com.gs.dmn.feel.lib.type.numeric.DoubleNumericType;
import com.gs.dmn.feel.lib.type.numeric.NumericRoundingMode;
import com.gs.dmn.feel.lib.type.string.DefaultStringType;
import com.gs.dmn.feel.lib.type.time.mixed.LocalDateType;
import com.gs.dmn.feel.lib.type.time.mixed.OffsetTimeType;
import com.gs.dmn.feel.lib.type.time.mixed.ZonedDateTimeType;
import com.gs.dmn.feel.lib.type.time.xml.DoubleDefaultDurationType;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.LambdaExpression;
import com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl;
import net.sf.saxon.xpath.XPathFactoryImpl;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class DoubleMixedJavaTimeFEELLib extends BaseFEELLib<Double, LocalDate, OffsetTime, ZonedDateTime, Duration> implements StandardFEELLib<Double, LocalDate, OffsetTime, ZonedDateTime, Duration> {
    private static final DatatypeFactory DATA_TYPE_FACTORY = XMLDatataypeFactory.newInstance();

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
        } catch (Throwable e) {
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
        } catch (Throwable e) {
            String message = String.format("number(%s, %s, %s)", from, groupingSeparator, decimalSeparator);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String string(Object from) {
        return DateTimeUtil.string(from);
    }

    @Override
    public LocalDate date(String literal) {
        try {
            if (literal == null) {
                return null;
            }

            if (DateTimeUtil.hasTime(literal) || DateTimeUtil.hasZone(literal)) {
                String message = String.format("date(%s)", literal);
                logError(message);
                return null;
            } else {
                return DateTimeUtil.makeLocalDate(literal);
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
            return DateTimeUtil.makeOffsetTime(literal);
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
        } catch (Throwable e) {
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
        if (DateTimeUtil.hasZone(from) && DateTimeUtil.hasOffset(from)) {
            return null;
        }
        if (DateTimeUtil.invalidYear(from)) {
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
        } catch (Throwable e) {
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
        } catch (Throwable e) {
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
            return DateTimeUtil.toYearsMonthDuration(DATA_TYPE_FACTORY, toDate(to), toDate(from));
        } catch (Throwable e) {
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
            return DateTimeUtil.toYearsMonthDuration(DATA_TYPE_FACTORY, to, from);
        } catch (Throwable e) {
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
            return DateTimeUtil.toYearsMonthDuration(DATA_TYPE_FACTORY, to, toDate(from));
        } catch (Throwable e) {
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
            return DateTimeUtil.toYearsMonthDuration(DATA_TYPE_FACTORY, toDate(to), from);
        } catch (Throwable e) {
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
            return DateTimeUtil.makeDateTime(literal);
        } catch (Throwable e) {
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
            return DoubleUtil.decimal(n, scale);
        } catch (Throwable e) {
            String message = String.format("decimal(%s, %s)", n, scale);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double round(Double n, Double scale, String mode) {
        try {
            if (n == null || scale == null || mode == null) {
                return null;
            }

            RoundingMode roundingMode = NumericRoundingMode.fromValue(mode);
            if (roundingMode == null) {
                throw new DMNRuntimeException(String.format("Unknown rounding mode '%s'. Expected one of '%s'", mode, NumericRoundingMode.ALLOWED_VALUES));
            } else {
                return BigDecimal.valueOf(n).setScale(scale.intValue(), roundingMode).doubleValue();
            }
        } catch (Exception e) {
            String message = String.format("round(%s, %s, %s)", n, scale, mode);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double floor(Double number) {
        try {
            return DoubleUtil.floor(number);
        } catch (Throwable e) {
            String message = String.format("fllor(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double ceiling(Double number) {
        try {
            return DoubleUtil.ceiling(number);
        } catch (Throwable e) {
            String message = String.format("ceiling(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double abs(Double number) {
        try {
            return DoubleUtil.abs(number);
        } catch (Throwable e) {
            String message = String.format("abs(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double modulo(Double divident, Double divisor) {
        try {
            return DoubleUtil.modulo(divident, divisor);
        } catch (Throwable e) {
            String message = String.format("modulo(%s, %s)", divident, divisor);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double sqrt(Double number) {
        try {
            return DoubleUtil.sqrt(number);
        } catch (Throwable e) {
            String message = String.format("sqrt(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double log(Double number) {
        try {
            return DoubleUtil.log(number);
        } catch (Throwable e) {
            String message = String.format("log(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double exp(Double number) {
        try {
            return DoubleUtil.exp(number);
        } catch (Throwable e) {
            String message = String.format("exp(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean odd(Double number) {
        try {
            return DoubleUtil.odd(number);
        } catch (Throwable e) {
            String message = String.format("odd(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean even(Double number) {
        try {
            return DoubleUtil.even(number);
        } catch (Throwable e) {
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
        } catch (Throwable e) {
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
        } catch (Throwable e) {
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
        } catch (Throwable e) {
            String message = String.format("sum(%s)", args);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double mean(List list) {
        if (list == null) {
            return null;
        }

        try {
            return DoubleUtil.mean(list);
        } catch (Throwable e) {
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
        } catch (Throwable e) {
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
        return (string == null || match == null) ? null : string.contains(match);
    }

    @Override
    public Boolean startsWith(String string, String match) {
        return (string == null || match == null) ? null : string.startsWith(match);
    }

    @Override
    public Boolean endsWith(String string, String match) {
        return (string == null || match == null) ? null : string.endsWith(match);
    }

    @Override
    public Double stringLength(String string) {
        return string == null ? null : Double.valueOf(string.length());
    }

    @Override
    public String substring(String string, Double startPosition) {
        return substring(string, startPosition.intValue());
    }

    private String substring(String string, int startPosition) {
        if (startPosition < 0) {
            startPosition = string.length() + startPosition;
        } else {
            --startPosition;
        }
        return string.substring(startPosition);
    }

    @Override
    public String substring(String string, Double startPosition, Double length) {
        return substring(string, startPosition.intValue(), length.intValue());
    }

    private String substring(String string, int startPosition, int length) {
        if (startPosition < 0) {
            startPosition = string.length() + startPosition;
        } else {
            --startPosition;
        }
        return string.substring(startPosition, startPosition + length);
    }

    @Override
    public String upperCase(String string) {
        return string == null ? null : string.toUpperCase();
    }

    @Override
    public String lowerCase(String string) {
        return string == null ? null : string.toLowerCase();
    }

    @Override
    public String substringBefore(String string, String match) {
        if (string != null && match != null) {
            int i = string.indexOf(match);
            return i == -1 ? "" : string.substring(0, i);
        } else {
            return null;
        }
    }

    @Override
    public String substringAfter(String string, String match) {
        if (string != null && match != null) {
            int i = string.indexOf(match);
            return i == -1 ? "" : string.substring(i + match.length());
        } else {
            return null;
        }
    }

    @Override
    public String replace(String input, String pattern, String replacement) {
        return replace(input, pattern, replacement, "");
    }

    @Override
    public String replace(String input, String pattern, String replacement, String flags) {
        if (input == null || pattern == null || replacement == null) {
            return null;
        }
        if (flags == null) {
            flags = "";
        }

        try {
            String expression = String.format("replace(/root, '%s', '%s', '%s')", pattern, replacement, flags);
            return evaluateXPath(input, expression);
        } catch (Throwable e) {
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
        if (input == null || pattern == null) {
            return false;
        }
        if (flags == null) {
            flags = "";
        }

        try {
            String expression = String.format("/root[matches(., '%s', '%s')]", pattern, flags);
            String value = evaluateXPath(input, expression);
            return input.equals(value);
        } catch (Throwable e) {
            String message = String.format("matches(%s, %s, %s)", input, pattern, flags);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List<String> split(String string, String delimiter) {
        try {
            return StringUtil.split(string, delimiter);
        } catch (Throwable e) {
            String message = String.format("split(%s, %s)", string, delimiter);
            logError(message, e);
            return null;
        }
    }

    private String evaluateXPath(String input, String expression) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
        // Read document
        String xml = "<root>" + input + "</root>";
        DocumentBuilderFactory documentBuilderFactory = new DocumentBuilderFactoryImpl();
        DocumentBuilder docBuilder = documentBuilderFactory.newDocumentBuilder();
        InputStream inputStream = new ByteArrayInputStream(xml.getBytes());
        Document document = docBuilder.parse(inputStream);

        // Evaluate xpath
        XPathFactory xPathFactory = new XPathFactoryImpl();
        XPath xPath = xPathFactory.newXPath();
        return xPath.evaluate(expression, document.getDocumentElement());
    }

    //
    // Boolean functions
    //
    @Override
    public Boolean and(List<?> list) {
        return all(list);
    }

    @Override
    public Boolean and(Object... args) {
        return all(args);
    }

    @Override
    public Boolean all(List<?> list) {
        if (list == null) {
            return null;
        }

        try {
            if (list.stream().anyMatch(b -> b == Boolean.FALSE)) {
                return false;
            } else if (list.stream().allMatch(b -> b == Boolean.TRUE)) {
                return true;
            } else {
                return null;
            }
        } catch (Throwable e) {
            String message = String.format("and(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean all(Object... args) {
        if (args == null || args.length < 1) {
            return null;
        }

        try {
            return all(Arrays.asList(args));
        } catch (Throwable e) {
            String message = String.format("and(%s)", args);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean or(List<?> list) {
        return any(list);
    }

    @Override
    public Boolean or(Object... args) {
        return any(args);
    }

    @Override
    public Boolean any(List<?> list) {
        if (list == null) {
            return null;
        }

        try {
            if (list.stream().anyMatch(b -> b == Boolean.TRUE)) {
                return true;
            } else if (list.stream().allMatch(b -> b == Boolean.FALSE)) {
                return false;
            } else {
                return null;
            }
        } catch (Throwable e) {
            String message = String.format("or(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean any(Object... args) {
        if (args == null || args.length < 1) {
            return null;
        }

        try {
            return any(Arrays.asList(args));
        } catch (Throwable e) {
            String message = String.format("or(%s)", args);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean not(Boolean operand) {
        return booleanNot(operand);
    }

    //
    // Date functions
    //
    public Double year(LocalDate date) {
        try {
            return Double.valueOf(date.getYear());
        } catch (Exception e) {
            String message = String.format("year(%s)", date);
            logError(message, e);
            return null;
        }
    }
    public Double year(ZonedDateTime dateTime) {
        try {
            return Double.valueOf(dateTime.getYear());
        } catch (Exception e) {
            String message = String.format("year(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    public Double month(LocalDate date) {
        try {
            return Double.valueOf(date.getMonth().getValue());
        } catch (Exception e) {
            String message = String.format("month(%s)", date);
            logError(message, e);
            return null;
        }
    }
    public Double month(ZonedDateTime dateTime) {
        try {
            return Double.valueOf(dateTime.getMonth().getValue());
        } catch (Exception e) {
            String message = String.format("month(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    public Double day(LocalDate date) {
        try {
            return Double.valueOf(date.getDayOfMonth());
        } catch (Exception e) {
            String message = String.format("day(%s)", date);
            logError(message, e);
            return null;
        }
    }
    public Double day(ZonedDateTime dateTime) {
        try {
            return Double.valueOf(dateTime.getDayOfMonth());
        } catch (Exception e) {
            String message = String.format("day(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }
    public Double weekday(LocalDate date) {
        try {
            return Double.valueOf(date.getDayOfWeek().getValue());
        } catch (Exception e) {
            String message = String.format("day(%s)", date);
            logError(message, e);
            return null;
        }
    }
    public Double weekday(ZonedDateTime dateTime) {
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
        return DATA_TYPE_FACTORY.newDuration(secondsOffset * 1000);
    }

    //
    // Duration functions
    //
    public Double years(Duration duration) {
        return Double.valueOf(duration.getYears());
    }

    public Double months(Duration duration) {
        return Double.valueOf(duration.getMonths());
    }

    public Double days(Duration duration) {
        return Double.valueOf(duration.getDays());
    }

    public Double hours(Duration duration) {
        return Double.valueOf(duration.getHours());
    }

    public Double minutes(Duration duration) {
        return Double.valueOf(duration.getMinutes());
    }

    public Double seconds(Duration duration) {
        return Double.valueOf(duration.getSeconds());
    }

    private int months(ZonedDateTime calendar) {
        return calendar.getYear() * 12 + calendar.getMonth().getValue();
    }

    //
    // List functions
    //
    @Override
    public Boolean listContains(List list, Object element) {
        return list == null ? null : list.contains(element);
    }

    @Override
    public List append(List list, Object... items) {
        List result = new ArrayList<>();
        if (list != null) {
            result.addAll(list);
        }
        if (items != null) {
            for (Object item : items) {
                result.add(item);
            }
        } else {
            result.add(null);
        }
        return result;
    }

    @Override
    public Double count(List list) {
        return list == null ? Double.valueOf(0) : Double.valueOf(list.size());
    }

    @Override
    public Double min(List list) {
        try {
            return DoubleUtil.min(list);
        } catch (Throwable e) {
            String message = String.format("min(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double max(List list) {
        if (list == null || list.isEmpty()) {
            return null;
        }

        try {
            return DoubleUtil.max(list);
        } catch (Throwable e) {
            String message = String.format("max(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double sum(List list) {
        try {
            return DoubleUtil.sum(list);
        } catch (Throwable e) {
            String message = String.format("sum(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public <T> List<T> sublist(List<T> list, Double startPosition) {
        return sublist(list, startPosition.intValue());
    }

    @Override
    public <T> List<T> sublist(List<T> list, Double startPosition, Double length) {
        return sublist(list, startPosition.intValue(), length.intValue());
    }

    private <T> List<T> sublist(List<T> list, int position) {
        List<T> result = new ArrayList<>();
        if (list == null || isOutOfBounds(list, position)) {
            return result;
        }
        int javaStartPosition;
        // up to, not included
        int javaEndPosition = list.size();
        if (position < 0) {
            javaStartPosition = list.size() + position;
        } else {
            javaStartPosition = position - 1;
        }
        for (int i = javaStartPosition; i < javaEndPosition; i++) {
            result.add(list.get(i));
        }
        return result;
    }

    private <T> List<T> sublist(List<T> list, int position, int length) {
        List<T> result = new ArrayList<>();
        if (list == null || isOutOfBounds(list, position)) {
            return result;
        }
        int javaStartPosition;
        int javaEndPosition;
        if (position < 0) {
            javaStartPosition = list.size() + position;
            javaEndPosition = javaStartPosition + length;
        } else {
            javaStartPosition = position - 1;
            javaEndPosition = javaStartPosition + length;
        }
        for (int i = javaStartPosition; i < javaEndPosition; i++) {
            result.add(list.get(i));
        }
        return result;
    }

    private <T> boolean isOutOfBounds(List<T> list, int position) {
        int length = list.size();
        if (position < 0) {
            return !(-length <= position);
        } else {
            return !(1 <= position && position <= length);
        }
    }

    @Override
    public <T> List<T> concatenate(List<T>... lists) {
        List<T> result = new ArrayList<>();
        if (lists != null) {
            for (List<T> list : lists) {
                result.addAll(list);
            }
        }
        return result;
    }

    @Override
    public <T> List<T> insertBefore(List<T> list, Double position, T newItem) {
        return insertBefore(list, position.intValue(), newItem);
    }

    private <T> List<T> insertBefore(List<T> list, int position, T newItem) {
        List<T> result = new ArrayList<>();
        if (list != null) {
            result.addAll(list);
        }
        if (isOutOfBounds(result, position)) {
            return result;
        }
        if (position < 0) {
            position = result.size() + position;
        } else {
            position = position - 1;
        }
        result.add(position, newItem);
        return result;
    }

    @Override
    public <T> List<T> remove(List<T> list, Double position) {
        return remove(list, position.intValue());
    }

    private <T> List<T> remove(List<T> list, int position) {
        List<T> result = new ArrayList<>();
        if (list != null) {
            result.addAll(list);
        }
        result.remove(position - 1);
        return result;
    }

    @Override
    public <T> List<T> reverse(List<T> list) {
        List<T> result = new ArrayList<>();
        if (list != null) {
            for (int i = list.size() - 1; i >= 0; i--) {
                result.add(list.get(i));
            }
        }
        return result;
    }

    @Override
    public List indexOf(List list, Object match) {
        List result = new ArrayList<>();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                Object o = list.get(i);
                if (o == null && match == null || o!= null && o.equals(match)) {
                    result.add(Double.valueOf(i + 1));
                }
            }
        }
        return result;
    }

    @Override
    public <T> List<T> union(List<T>... lists) {
        List<T> result = new ArrayList<>();
        if (lists != null) {
            for (List<T> list : lists) {
                result.addAll(list);
            }
        }
        return distinctValues(result);
    }

    @Override
    public <T> List<T> distinctValues(List<T> list1) {
        List<T> result = new ArrayList<>();
        if (list1 != null) {
            for (T element : list1) {
                if (!result.contains(element)) {
                    result.add(element);
                }
            }
        }
        return result;
    }

    @Override
    public List flatten(List list1) {
        if (list1 == null) {
            return null;
        }
        List result = new ArrayList<>();
        collect(result, list1);
        return result;
    }

    @Override
    public Double product(List list) {
        try {
            return DoubleUtil.product(list);
        } catch (Throwable e) {
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
        } catch (Throwable e) {
            String message = String.format("sum(%s)", numbers);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double median(List list) {
        try {
            return DoubleUtil.median(list);
        } catch (Throwable e){
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
        } catch (Throwable e) {
            String message = String.format("median(%s)", numbers);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double stddev(List list) {
        try {
            return DoubleUtil.stddev(list);
        } catch (Throwable e) {
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
        } catch (Throwable e) {
            String message = String.format("stddev(%s)", numbers);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List mode(List list) {
        try {
            return DoubleUtil.mode(list);
        } catch (Throwable e) {
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
        } catch (Throwable e) {
            String message = String.format("mode(%s)", numbers);
            logError(message, e);
            return null;
        }
    }

    @Override
    public void collect(List result, List list) {
        if (list != null) {
            for (Object object : list) {
                if (object instanceof List) {
                    collect(result, (List) object);
                } else {
                    result.add(object);
                }
            }
        }
    }

    @Override
    public <T> List<T> sort(List<T> list, LambdaExpression<Boolean> comparator) {
        List<T> clone = new ArrayList<>(list);
        Comparator<? super T> comp = (Comparator<T>) (o1, o2) -> {
            if (comparator.apply(o1, o2)) {
                return -1;
            } else if (o1 != null && o1.equals(o2)) {
                return 0;
            } else {
                return 1;
            }
        };
        clone.sort(comp);
        return clone;
    }
}
