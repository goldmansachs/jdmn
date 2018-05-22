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

import com.gs.dmn.feel.lib.type.list.DefaultListType;
import com.gs.dmn.feel.lib.type.logic.DefaultBooleanType;
import com.gs.dmn.feel.lib.type.numeric.DefaultNumericType;
import com.gs.dmn.feel.lib.type.string.DefaultStringType;
import com.gs.dmn.feel.lib.type.time.xml.*;
import com.gs.dmn.runtime.LambdaExpression;
import com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl;
import net.sf.saxon.xpath.XPathFactoryImpl;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
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
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class DefaultFEELLib extends BaseFEELLib<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar, Duration> implements StandardFEELLib<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar, Duration> {
    public static final DatatypeFactory DATA_TYPE_FACTORY = XMLDatataypeFactory.newInstance();

    public DefaultFEELLib() {
        super(new DefaultNumericType(LOGGER),
                new DefaultBooleanType(LOGGER),
                new DefaultStringType(LOGGER),
                new DefaultDateType(LOGGER, DATA_TYPE_FACTORY),
                new DefaultTimeType(LOGGER, DATA_TYPE_FACTORY),
                new DefaultDateTimeType(LOGGER, DATA_TYPE_FACTORY),
                new DefaultDurationType(LOGGER),
                new DefaultListType(LOGGER)
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
        } catch (Throwable e) {
            String message = String.format("number(%s)", literal);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal number(String from, String groupingSeparator, String decimalSeparator) {
        if (StringUtils.isBlank(from) || groupingSeparator == null || decimalSeparator == null) {
            return null;
        }

        try {
            if (decimalSeparator.equals(".")) {
                decimalSeparator = "\\" + decimalSeparator;
            }
            if (groupingSeparator.equals(".")) {
                groupingSeparator = "\\" + groupingSeparator;
            }
            String[] parts = from.split(decimalSeparator);
            if (parts.length == 1) {
                return number(from.replaceAll(groupingSeparator, ""));
            } else if (parts.length == 2) {
                return number(parts[0].replaceAll(groupingSeparator, "") + "." + parts[1]);
            } else {
                return null;
            }
        } catch (Throwable e) {
            String message = String.format("number(%s, %s, %s)", from, groupingSeparator, decimalSeparator);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String string(Object from) {
        if (from == null) {
            return "null";
        } else if (from instanceof BigDecimal) {
            return ((BigDecimal) from).toPlainString();
        } else if (from instanceof XMLGregorianCalendar) {
            return from.toString();
        }
        return from.toString();
    }

    @Override
    public XMLGregorianCalendar date(String literal) {
        if (StringUtils.isBlank(literal)) {
            return null;
        }
        // Check time
        if (DateTimeUtil.hasTime(literal)) {
            return null;
        }
        // Check year
        if (DateTimeUtil.invalidYear(literal)) {
            return null;
        }

        XMLGregorianCalendar calendar = makeXMLCalendar(literal);
        return DateTimeUtil.isValidDate(calendar) ? calendar : null;
    }

    @Override
    public XMLGregorianCalendar date(BigDecimal year, BigDecimal month, BigDecimal day) {
        if (year == null || month == null || day == null) {
            return null;
        }

        XMLGregorianCalendar calendar = FEELXMLGregorianCalendar.makeDate(year.toBigInteger(), month.intValue(), day.intValue());
        return DateTimeUtil.isValidDate(calendar) ? calendar : null;
    }

    @Override
    public XMLGregorianCalendar date(XMLGregorianCalendar from) {
        if (from == null) {
            return null;
        }

        XMLGregorianCalendar clone = (XMLGregorianCalendar) from.clone();
        clone.setTime(DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED);
        clone.setTimezone(DatatypeConstants.FIELD_UNDEFINED);

        return DateTimeUtil.isValidDate(clone) ? clone : null;
    }

    @Override
    public XMLGregorianCalendar time(String literal) {
        if (literal == null) {
            return null;
        }
        literal = DateTimeUtil.fixDateTimeFormat(literal);
        if (!DateTimeUtil.isTime(literal)) {
            return null;
        }
        if (DateTimeUtil.hasZone(literal) && DateTimeUtil.timeHasOffset(literal)) {
            return null;
        }

        XMLGregorianCalendar clone = makeXMLCalendar(literal);
        if (clone != null) {
            clone.setYear(DatatypeConstants.FIELD_UNDEFINED);
            clone.setMonth(DatatypeConstants.FIELD_UNDEFINED);
            clone.setDay(DatatypeConstants.FIELD_UNDEFINED);
        }
        return DateTimeUtil.isValidTime(clone) ? clone : null;
    }

    @Override
    public XMLGregorianCalendar time(BigDecimal hour, BigDecimal minute, BigDecimal second, Duration offset) {
        if (hour == null || minute == null || second == null) {
            return null;
        }
        if (!DateTimeUtil.isValidTime(hour.intValue(), minute.intValue(), second.intValue())) {
            return null;
        }

        try {
            XMLGregorianCalendar xmlGregorianCalendar = null;
            if (offset != null) {
                BigDecimal secondFraction = second.subtract(BigDecimal.valueOf(second.intValue()));
                int sign = offset.getSign() < 0 ? -1 : +1;
                int timezone = sign * (((offset.getHours() * 60 + offset.getMinutes()) * 60) + offset.getSeconds());
                xmlGregorianCalendar = FEELXMLGregorianCalendar.makeTime(hour.intValue(), minute.intValue(), second.intValue(), secondFraction, timezone, null);
            } else {
                BigDecimal secondFraction = second.subtract(BigDecimal.valueOf(second.intValue()));
                xmlGregorianCalendar = FEELXMLGregorianCalendar.makeTime(hour.intValue(), minute.intValue(), second.intValue(), secondFraction, DatatypeConstants.FIELD_UNDEFINED, null);
            }
            return xmlGregorianCalendar;
        } catch (Throwable e) {
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

        XMLGregorianCalendar clone = (XMLGregorianCalendar) from.clone();
        clone.setYear(DatatypeConstants.FIELD_UNDEFINED);
        clone.setMonth(DatatypeConstants.FIELD_UNDEFINED);
        clone.setDay(DatatypeConstants.FIELD_UNDEFINED);

        clone = midnightIfDate(clone);
        if (from.getXMLSchemaType() == DatatypeConstants.DATE) {
            clone.setTimezone(0);
        }
        return clone;
    }

    @Override
    public XMLGregorianCalendar dateAndTime(String from) {
        if (from == null) {
            return null;
        }
        if (DateTimeUtil.hasZone(from) && DateTimeUtil.hasOffset(from)) {
            return null;
        }
        if (DateTimeUtil.invalidYear(from)) {
            return null;
        }

        XMLGregorianCalendar calendar = makeDateTime(from);
        calendar = midnightIfDate(calendar);
        return DateTimeUtil.isValidDateTime(calendar) ? calendar : null;
    }

    @Override
    public XMLGregorianCalendar dateAndTime(XMLGregorianCalendar date, XMLGregorianCalendar time) {
        if (date == null || time == null) {
            return null;
        }

        try {
            return FEELXMLGregorianCalendar.makeDateTime(
                    BigInteger.valueOf(date.getYear()), date.getMonth(), date.getDay(),
                    time.getHour(), time.getMinute(), time.getSecond(), time.getFractionalSecond(),
                    time.getTimezone(), ((FEELXMLGregorianCalendar)time).getZoneID()
            );
        } catch (Throwable e) {
            return null;
        }
    }

    private XMLGregorianCalendar midnightIfDate(XMLGregorianCalendar clone) {
        if (clone == null) {
            return null;
        }

        if (clone.getHour() == DatatypeConstants.FIELD_UNDEFINED) {
            clone.setHour(0);
            clone.setMinute(0);
            clone.setSecond(0);
        }
        return clone;
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
    public Duration yearsAndMonthsDuration(XMLGregorianCalendar from, XMLGregorianCalendar to) {
        if (from == null || to == null) {
            return null;
        }

        try {
            LocalDate toLocalDate = LocalDate.of(to.getYear(), to.getMonth(), to.getDay());
            LocalDate fromLocalDate = LocalDate.of(from.getYear(), from.getMonth(), from.getDay());
            return DateTimeUtil.toYearsMonthDuration(DATA_TYPE_FACTORY, toLocalDate, fromLocalDate);
        } catch (Throwable e) {
            String message = String.format("yearsAndMonthsDuration(%s, %s)", from, to);
            logError(message, e);
            return null;
        }
    }

    public XMLGregorianCalendar makeXMLCalendar(String literal) {
        if (StringUtils.isBlank(literal)) {
            return null;
        }

        try {
            int zoneIdIndex = literal.indexOf("@");
            if (zoneIdIndex != -1) {
                String zoneID = literal.substring(zoneIdIndex + 1);
                ZoneId zone = ZoneId.of(zoneID);
                XMLGregorianCalendar xmlGregorianCalendar = new FEELXMLGregorianCalendar(literal.substring(0, zoneIdIndex), zoneID);
                return xmlGregorianCalendar;
            } else {
                return new FEELXMLGregorianCalendar(literal);
            }
        } catch (Throwable e) {
            String message = String.format("makeXMLCalendar(%s)", literal);
            logError(message, e);
            return null;
        }
    }

    private XMLGregorianCalendar makeDateTime(String literal) {
        if (StringUtils.isBlank(literal)) {
            return null;
        }

        try {
            literal = DateTimeUtil.fixDateTimeFormat(literal);
            return makeXMLCalendar(literal);
        } catch (Throwable e) {
            String message = String.format("makeDateTime(%s)", literal);
            logError(message, e);
            return null;
        }
    }

    @Override
    public XMLGregorianCalendar toDate(Object object) {
        return (XMLGregorianCalendar)object;
    }

    @Override
    public XMLGregorianCalendar toTime(Object object) {
        return (XMLGregorianCalendar)object;
    }

    //
    // Numeric functions
    //
    @Override
    public BigDecimal decimal(BigDecimal n, BigDecimal scale) {
        try {
            return BigDecimalUtil.decimal(n, scale);
        } catch (Throwable e) {
            String message = String.format("decimal(%s, %s)", n, scale);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal floor(BigDecimal number) {
        try {
            return BigDecimalUtil.floor(number);
        } catch (Throwable e) {
            String message = String.format("fllor(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal ceiling(BigDecimal number) {
        try {
            return BigDecimalUtil.ceiling(number);
        } catch (Throwable e) {
            String message = String.format("ceiling(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal abs(BigDecimal number) {
        try {
            return BigDecimalUtil.abs(number);
        } catch (Throwable e) {
            String message = String.format("abs(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal modulo(BigDecimal divident, BigDecimal divisor) {
        try {
            return BigDecimalUtil.modulo(divident, divisor);
        } catch (Throwable e) {
            String message = String.format("modulo(%s, %s)", divident, divisor);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal sqrt(BigDecimal number) {
        try {
            return BigDecimalUtil.sqrt(number);
        } catch (Throwable e) {
            String message = String.format("sqrt(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal log(BigDecimal number) {
        try {
            return BigDecimalUtil.log(number);
        } catch (Throwable e) {
            String message = String.format("log(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal exp(BigDecimal number) {
        try {
            return BigDecimalUtil.exp(number);
        } catch (Throwable e) {
            String message = String.format("exp(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean odd(BigDecimal number) {
        try {
            return BigDecimalUtil.odd(number);
        } catch (Throwable e) {
            String message = String.format("odd(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean even(BigDecimal number) {
        try {
            return BigDecimalUtil.even(number);
        } catch (Throwable e) {
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
        } catch (Throwable e) {
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
        } catch (Throwable e) {
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
        } catch (Throwable e) {
            String message = String.format("sum(%s)", args);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal mean(List list) {
        try {
            return BigDecimalUtil.mean(list);
        } catch (Throwable e) {
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
    public BigDecimal stringLength(String string) {
        return string == null ? null : BigDecimal.valueOf(string.length());
    }

    @Override
    public String substring(String string, BigDecimal startPosition) {
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
    public String substring(String string, BigDecimal startPosition, BigDecimal length) {
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
    public List split(String string, String delimiter) {
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
    public Boolean and(List list) {
        return all(list);
    }

    @Override
    public Boolean and(Object... args) {
        return all(args);
    }

    @Override
    public Boolean all(List list) {
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
    public Boolean or(List list) {
        return any(list);
    }

    @Override
    public Boolean or(Object... args) {
        return any(args);
    }

    @Override
    public Boolean any(List list) {
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

    //
    // Time functions
    //
    public BigDecimal hour(XMLGregorianCalendar date) {
        return BigDecimal.valueOf(date.getHour());
    }

    public BigDecimal minute(XMLGregorianCalendar date) {
        return BigDecimal.valueOf(date.getMinute());
    }

    public BigDecimal second(XMLGregorianCalendar date) {
        return BigDecimal.valueOf(date.getSecond());
    }

    public Duration timeOffset(XMLGregorianCalendar date) {
        return timezone(date);
    }

    public Duration timezone(XMLGregorianCalendar date) {
        // timezone offset in seconds
        int secondsOffset = date.getTimezone();
        return DATA_TYPE_FACTORY.newDuration(secondsOffset * 1000);
    }

    //
    // Duration functions
    //
    public BigDecimal years(Duration duration) {
        return BigDecimal.valueOf(duration.getYears());
    }

    public BigDecimal months(Duration duration) {
        return BigDecimal.valueOf(duration.getMonths());
    }

    public BigDecimal days(Duration duration) {
        return BigDecimal.valueOf(duration.getDays());
    }

    public BigDecimal hours(Duration duration) {
        return BigDecimal.valueOf(duration.getHours());
    }

    public BigDecimal minutes(Duration duration) {
        return BigDecimal.valueOf(duration.getMinutes());
    }

    public BigDecimal seconds(Duration duration) {
        return BigDecimal.valueOf(duration.getSeconds());
    }

    private int months(XMLGregorianCalendar calendar) {
        return calendar.getYear() * 12 + calendar.getMonth();
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
    public BigDecimal count(List list) {
        return list == null ? BigDecimal.valueOf(0) : BigDecimal.valueOf(list.size());
    }

    @Override
    public BigDecimal min(List list) {
        try {
            return BigDecimalUtil.min(list);
        } catch (Throwable e) {
            String message = String.format("min(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal max(List list) {
        try {
            return BigDecimalUtil.max(list);
        } catch (Throwable e) {
            String message = String.format("max(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal sum(List list) {
        try {
            return BigDecimalUtil.sum(list);
        } catch (Throwable e) {
            String message = String.format("sum(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List sublist(List list, BigDecimal startPosition) {
        return sublist(list, startPosition.intValue());
    }

    @Override
    public List sublist(List list, BigDecimal startPosition, BigDecimal length) {
        return sublist(list, startPosition.intValue(), length.intValue());
    }

    private List sublist(List list, int position) {
        List result = new ArrayList<>();
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

    private List sublist(List list, int position, int length) {
        List result = new ArrayList<>();
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

    private boolean isOutOfBounds(List list, int position) {
        int length = list.size();
        if (position < 0) {
            return !(-length <= position);
        } else {
            return !(1 <= position && position <= length);
        }
    }

    @Override
    public List concatenate(Object... lists) {
        List result = new ArrayList<>();
        if (lists != null) {
            for (Object list : lists) {
                result.addAll((List) list);
            }
        }
        return result;
    }

    @Override
    public List insertBefore(List list, BigDecimal position, Object newItem) {
        return insertBefore(list, position.intValue(), newItem);
    }

    private List insertBefore(List list, int position, Object newItem) {
        List result = new ArrayList<>();
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
    public List remove(List list, Object position) {
        return remove(list, ((BigDecimal) position).intValue());
    }

    private List remove(List list, int position) {
        List result = new ArrayList<>();
        if (list != null) {
            result.addAll(list);
        }
        result.remove(position - 1);
        return result;
    }

    @Override
    public List reverse(List list) {
        List result = new ArrayList<>();
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
                    result.add(BigDecimal.valueOf(i + 1));
                }
            }
        }
        return result;
    }

    @Override
    public List union(Object... lists) {
        List result = new ArrayList<>();
        if (lists != null) {
            for (Object list : lists) {
                result.addAll((List) list);
            }
        }
        return distinctValues(result);
    }

    @Override
    public List distinctValues(List list1) {
        List result = new ArrayList<>();
        if (list1 != null) {
            for (Object element : list1) {
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
    public BigDecimal product(List list) {
        try {
            return BigDecimalUtil.product(list);
        } catch (Throwable e) {
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
        } catch (Throwable e) {
            String message = String.format("sum(%s)", numbers);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal median(List list) {
        try {
            return BigDecimalUtil.median(list);
        } catch (Throwable e){
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
        } catch (Throwable e) {
            String message = String.format("median(%s)", numbers);
            logError(message, e);
            return null;
        }
    }

    @Override
    public BigDecimal stddev(List list) {
        try {
            return BigDecimalUtil.stddev(list);
        } catch (Throwable e) {
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
        } catch (Throwable e) {
            String message = String.format("stddev(%s)", numbers);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List mode(List list) {
        try {
            return BigDecimalUtil.mode(list);
        } catch (Throwable e) {
            String message = String.format("mode(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List mode(Object... numbers) {
        if (numbers == null || numbers.length < 1) {
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
