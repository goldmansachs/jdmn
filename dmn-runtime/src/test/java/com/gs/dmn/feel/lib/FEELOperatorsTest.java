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

import com.gs.dmn.feel.lib.type.time.xml.XMLDurationFactory;
import com.gs.dmn.runtime.Assert;
import com.gs.dmn.runtime.Context;
import com.gs.dmn.runtime.Range;
import org.junit.Test;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public abstract class FEELOperatorsTest<NUMBER, DATE, TIME, DATE_TIME, DURATION> {
    protected abstract StandardFEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> getLib();

    //
    // Numeric operators
    //
    @Test
    public void testIsNumeric() {
        assertFalse(getLib().isNumber(null));
        assertTrue(getLib().isNumber(getLib().number("1")));
        assertFalse(getLib().isNumber("abc"));
        assertFalse(getLib().isNumber(true));
        assertFalse(getLib().isNumber(getLib().date("2020-01-01")));
        assertFalse(getLib().isNumber(getLib().time("12:00:00")));
        assertFalse(getLib().isNumber(getLib().dateAndTime("2020-01-01T12:00:00")));
        assertFalse(getLib().isNumber(getLib().duration("P1Y1M")));
        assertFalse(getLib().isNumber(getLib().asList("a")));
        assertFalse(getLib().isNumber(new Context()));
        assertFalse(getLib().isNumber(new Range(true, BigDecimal.ZERO, true, BigDecimal.ONE)));
    }

    @Test
    public void testNumericValue() {
        assertNull(getLib().numericValue(null));

        assertEquals(makeNumber("1"), getLib().numericValue(makeNumber("1")));
    }

    @Test
    public void testNumericIs() {
        assertTrue(getLib().numericIs(null, null));
        assertFalse(getLib().numericIs(null, makeNumber("1")));
        assertFalse(getLib().numericIs(makeNumber("1"), null));

        assertTrue(getLib().numericIs(makeNumber("1"), makeNumber("1")));
        assertFalse(getLib().numericIs(makeNumber("1"), makeNumber("2")));
    }

    @Test
    public void testNumericEqual() {
        assertTrue(getLib().numericEqual(null, null));
        assertFalse(getLib().numericEqual(null, makeNumber("1")));
        assertFalse(getLib().numericEqual(makeNumber("1"), null));

        assertTrue(getLib().numericEqual(makeNumber("1"), makeNumber("1")));
        assertFalse(getLib().numericEqual(makeNumber("1"), makeNumber("2")));
    }

    @Test
    public void testNumericNotEqual() {
        assertFalse(getLib().numericNotEqual(null, null));
        assertTrue(getLib().numericNotEqual(null, makeNumber("1")));
        assertTrue(getLib().numericNotEqual(makeNumber("1"), null));

        assertFalse(getLib().numericNotEqual(makeNumber("1"), makeNumber("1")));
        assertTrue(getLib().numericNotEqual(makeNumber("1"), makeNumber("2")));
    }

    @Test
    public void testNumericLessThan() {
        assertNull(getLib().numericLessThan(null, null));
        assertNull(getLib().numericLessThan(null, makeNumber("1")));
        assertNull(getLib().numericLessThan(makeNumber("1"), null));

        assertFalse(getLib().numericLessThan(makeNumber("1"), makeNumber("1")));
        assertTrue(getLib().numericLessThan(makeNumber("1"), makeNumber("2")));
    }

    @Test
    public void testNumericGreaterThan() {
        assertNull(getLib().numericGreaterThan(null, null));
        assertNull(getLib().numericGreaterThan(null, makeNumber("1")));
        assertNull(getLib().numericGreaterThan(makeNumber("1"), null));

        assertFalse(getLib().numericGreaterThan(makeNumber("1"), makeNumber("1")));
        assertTrue(getLib().numericGreaterThan(makeNumber("2"), makeNumber("1")));
    }

    @Test
    public void testNumericLessEqualThan() {
        assertTrue(getLib().numericLessEqualThan(null, null));
        assertNull(getLib().numericLessEqualThan(null, makeNumber("1")));
        assertNull(getLib().numericLessEqualThan(makeNumber("1"), null));

        assertTrue(getLib().numericLessEqualThan(makeNumber("1"), makeNumber("1")));
        assertFalse(getLib().numericLessEqualThan(makeNumber("2"), makeNumber("1")));
    }

    @Test
    public void testNumericGreaterEqualThan() {
        assertTrue(getLib().numericGreaterEqualThan(null, null));
        assertNull(getLib().numericGreaterEqualThan(null, makeNumber("1")));
        assertNull(getLib().numericGreaterEqualThan(makeNumber("1"), null));

        assertTrue(getLib().numericGreaterEqualThan(makeNumber("1"), makeNumber("1")));
        assertFalse(getLib().numericGreaterEqualThan(makeNumber("1"), makeNumber("2")));
    }

    @Test
    public void testNumericAdd() {
        assertNull(getLib().numericAdd(null, null));
        assertNull(getLib().numericAdd(null, makeNumber("1")));
        assertNull(getLib().numericAdd(makeNumber("1"), null));

        assertEqualsNumber(makeNumber("3"), getLib().numericAdd(makeNumber("1"), makeNumber("2")));
    }

    @Test
    public void testNumericSubtract() {
        assertNull(getLib().numericSubtract(null, null));
        assertNull(getLib().numericSubtract(null, makeNumber("2")));
        assertNull(getLib().numericSubtract(makeNumber("2"), null));

        assertEqualsNumber(makeNumber("-1"), getLib().numericSubtract(makeNumber("1"), makeNumber("2")));
    }

    @Test
    public void testNumericMultiply() {
        assertNull(getLib().numericMultiply(null, null));
        assertNull(getLib().numericMultiply(null, makeNumber("2")));
        assertNull(getLib().numericMultiply(makeNumber("2"), null));

        assertEqualsNumber(makeNumber("2"), getLib().numericMultiply(makeNumber("1"), makeNumber("2")));
    }

    @Test
    public void testNumericDivide() {
        assertNull(getLib().numericDivide(null, null));
        assertNull(getLib().numericDivide(null, makeNumber("2")));
        assertNull(getLib().numericDivide(makeNumber("2"), null));

        assertEqualsNumber("0.5", getLib().numericDivide(makeNumber("1"), makeNumber("2")));
    }

    @Test
    public void testNumericUnaryMinus() {
        assertNull(getLib().numericUnaryMinus(null));

        assertEqualsNumber(makeNumber("-1"), getLib().numericUnaryMinus(makeNumber("1")));
    }

    @Test
    public void testNumericExponentiation() {
        assertNull(getLib().numericExponentiation(null, null));
        assertNull(getLib().numericExponentiation(null, makeNumber("10")));
        assertNull(getLib().numericExponentiation(makeNumber("10"), null));

        assertEqualsNumber(makeNumber("1"), getLib().numericExponentiation(makeNumber("2"), makeNumber("0")));
        assertEqualsNumber(makeNumber("0.5"), getLib().numericExponentiation(makeNumber("2"), makeNumber("-1")));
        assertEqualsNumber(makeNumber("1024"), getLib().numericExponentiation(makeNumber("2"), makeNumber("10")));

        assertEqualsNumber(makeNumber("1"), getLib().numericExponentiation(makeNumber("3"), makeNumber("0")));

        assertEqualsNumber(makeNumber("1.41421356"), getLib().numericExponentiation(makeNumber("2"), makeNumber("0.5")));
        assertEqualsNumber(makeNumber("8"), getLib().numericExponentiation(makeNumber("2"), makeNumber("3")));
        assertEqualsNumber(makeNumber("11.31370849"), getLib().numericExponentiation(makeNumber("2"), makeNumber("3.5")));
        assertEqualsNumber(makeNumber("11.84466611"), getLib().numericExponentiation(makeNumber("3"), makeNumber("2.25")));
        assertEqualsNumber(makeNumber("15.58845726"), getLib().numericExponentiation(makeNumber("3"), makeNumber("2.5")));
        assertEqualsNumber(makeNumber("20.51556351"), getLib().numericExponentiation(makeNumber("3"), makeNumber("2.75")));
        assertEqualsNumber(makeNumber("1.73205080"), getLib().numericExponentiation(makeNumber("3"), makeNumber("0.5")));
        assertEqualsNumber(makeNumber("0.11111111"), getLib().numericExponentiation(makeNumber("3"), makeNumber("-2")));
        assertEqualsNumber(makeNumber("0.04874348"), getLib().numericExponentiation(makeNumber("3"), makeNumber("-2.75")));
    }

    //
    // Boolean operators
    //
    @Test
    public void testIsBoolean() {
        assertFalse(getLib().isBoolean(null));
        assertFalse(getLib().isBoolean(getLib().number("1")));
        assertFalse(getLib().isBoolean("abc"));
        assertTrue(getLib().isBoolean(true));
        assertFalse(getLib().isBoolean(getLib().date("2020-01-01")));
        assertFalse(getLib().isBoolean(getLib().time("12:00:00")));
        assertFalse(getLib().isBoolean(getLib().dateAndTime("2020-01-01T12:00:00")));
        assertFalse(getLib().isBoolean(getLib().duration("P1Y1M")));
        assertFalse(getLib().isBoolean(getLib().asList("a")));
        assertFalse(getLib().isBoolean(new Context()));
        assertFalse(getLib().isBoolean(new Range(true, BigDecimal.ZERO, true, BigDecimal.ONE)));
    }

    @Test
    public void testBooleanValue() {
        assertNull(getLib().booleanValue(null));
        assertTrue(getLib().booleanValue(true));
        assertFalse(getLib().booleanValue(false));
    }

    @Test
    public void testBooleanIs() {
        assertTrue(getLib().booleanIs(null, null));
        assertFalse(getLib().booleanIs(Boolean.TRUE, null));
        assertFalse(getLib().booleanIs(null, Boolean.TRUE));

        assertFalse(getLib().booleanIs(Boolean.FALSE, Boolean.TRUE));
        assertTrue(getLib().booleanIs(Boolean.TRUE, Boolean.TRUE));
    }

    @Test
    public void testBooleanEqual() {
        assertTrue(getLib().booleanEqual(null, null));
        assertFalse(getLib().booleanEqual(Boolean.TRUE, null));
        assertFalse(getLib().booleanEqual(null, Boolean.TRUE));

        assertFalse(getLib().booleanEqual(Boolean.FALSE, Boolean.TRUE));
        assertTrue(getLib().booleanEqual(Boolean.TRUE, Boolean.TRUE));
    }

    @Test
    public void testBooleanNotEqual() {
        assertFalse(getLib().booleanNotEqual(null, null));
        assertTrue(getLib().booleanNotEqual(Boolean.TRUE, null));
        assertTrue(getLib().booleanNotEqual(null, Boolean.TRUE));

        assertTrue(getLib().booleanNotEqual(Boolean.FALSE, Boolean.TRUE));
        assertFalse(getLib().booleanNotEqual(Boolean.TRUE, Boolean.TRUE));
    }

    @Test
    public void testBooleanNot() {
        assertTrue(getLib().booleanNot(Boolean.FALSE));
        assertFalse(getLib().booleanNot(Boolean.TRUE));
        assertNull(getLib().booleanNot(null));

        assertNull(getLib().booleanNot("abc"));
        assertNull(getLib().booleanNot(makeNumber("123")));
    }

    @Test
    public void testBooleanOr() {
        assertFalse(getLib().booleanOr(Boolean.FALSE, Boolean.FALSE));
        assertTrue(getLib().booleanOr(Boolean.FALSE, Boolean.TRUE));
        assertTrue(getLib().booleanOr(Boolean.TRUE, Boolean.FALSE));
        assertTrue(getLib().booleanOr(Boolean.TRUE, Boolean.TRUE));
        assertNull(getLib().booleanOr(Boolean.FALSE, null));
        assertNull(getLib().booleanOr(null, Boolean.FALSE));
        assertTrue(getLib().booleanOr(Boolean.TRUE, null));
        assertTrue(getLib().booleanOr(null, Boolean.TRUE));
        assertTrue(getLib().booleanOr(Boolean.TRUE, getLib().number("123")));
        assertTrue(getLib().booleanOr(getLib().number("123"), Boolean.TRUE));
        assertTrue(getLib().booleanOr(Boolean.TRUE, "123"));
        assertTrue(getLib().booleanOr(Boolean.TRUE, null));
        assertTrue(getLib().booleanOr(null, Boolean.TRUE));
        assertTrue(getLib().booleanOr("123", Boolean.TRUE));
        assertNull(getLib().booleanOr(null, null));
        assertNull(getLib().booleanOr("123", "123"));

        assertNull(getLib().booleanOr(true));
        assertTrue(getLib().booleanOr(false, false, true));
        assertTrue(getLib().booleanOr(Arrays.asList(false, false, true)));
        assertNull(getLib().booleanOr(Arrays.asList(Arrays.asList(false, false, true))));
    }

    @Test
    public void testBinaryBooleanOr() {
        assertFalse(getLib().binaryBooleanOr(Boolean.FALSE, Boolean.FALSE));
        assertTrue(getLib().binaryBooleanOr(Boolean.FALSE, Boolean.TRUE));
        assertTrue(getLib().binaryBooleanOr(Boolean.TRUE, Boolean.FALSE));
        assertTrue(getLib().binaryBooleanOr(Boolean.TRUE, Boolean.TRUE));
        assertNull(getLib().binaryBooleanOr(Boolean.FALSE, null));
        assertNull(getLib().binaryBooleanOr(null, Boolean.FALSE));
        assertTrue(getLib().binaryBooleanOr(Boolean.TRUE, null));
        assertTrue(getLib().binaryBooleanOr(null, Boolean.TRUE));
        assertTrue(getLib().binaryBooleanOr(Boolean.TRUE, getLib().number("123")));
        assertTrue(getLib().binaryBooleanOr(getLib().number("123"), Boolean.TRUE));
        assertTrue(getLib().binaryBooleanOr(Boolean.TRUE, "123"));
        assertTrue(getLib().binaryBooleanOr(Boolean.TRUE, null));
        assertTrue(getLib().binaryBooleanOr(null, Boolean.TRUE));
        assertTrue(getLib().binaryBooleanOr("123", Boolean.TRUE));
        assertNull(getLib().binaryBooleanOr(null, null));
        assertNull(getLib().binaryBooleanOr("123", "123"));
    }

    @Test
    public void testBooleanAnd() {
        assertFalse(getLib().booleanAnd(Boolean.FALSE, Boolean.FALSE));
        assertFalse(getLib().booleanAnd(Boolean.FALSE, Boolean.TRUE));
        assertFalse(getLib().booleanAnd(Boolean.TRUE, Boolean.FALSE));
        assertTrue(getLib().booleanAnd(Boolean.TRUE, Boolean.TRUE));
        assertFalse(getLib().booleanAnd(Boolean.FALSE, null));
        assertFalse(getLib().booleanAnd(null, Boolean.FALSE));
        assertFalse(getLib().booleanAnd(Boolean.FALSE, getLib().number("123")));
        assertFalse(getLib().booleanAnd(getLib().number("123"), Boolean.FALSE));
        assertFalse(getLib().booleanAnd(Boolean.FALSE, "123"));
        assertFalse(getLib().booleanAnd(Boolean.FALSE, null));
        assertFalse(getLib().booleanAnd(null, Boolean.FALSE));
        assertFalse(getLib().booleanAnd("123", Boolean.FALSE));
        assertNull(getLib().booleanAnd(Boolean.TRUE, null));
        assertNull(getLib().booleanAnd(null, Boolean.TRUE));
        assertNull(getLib().booleanAnd(null, null));
        assertNull(getLib().booleanAnd("123", "123"));

        assertNull(getLib().booleanAnd(true));
        assertFalse(getLib().booleanAnd(true, true, false));
        assertFalse(getLib().booleanAnd(Arrays.asList(false, false, true)));
        assertNull(getLib().booleanAnd(Arrays.asList(Arrays.asList(false, false, true))));
    }

    @Test
    public void testBinaryBooleanAnd() {
        assertFalse(getLib().binaryBooleanAnd(Boolean.FALSE, Boolean.FALSE));
        assertFalse(getLib().binaryBooleanAnd(Boolean.FALSE, Boolean.TRUE));
        assertFalse(getLib().binaryBooleanAnd(Boolean.TRUE, Boolean.FALSE));
        assertTrue(getLib().binaryBooleanAnd(Boolean.TRUE, Boolean.TRUE));
        assertFalse(getLib().binaryBooleanAnd(Boolean.FALSE, null));
        assertFalse(getLib().binaryBooleanAnd(null, Boolean.FALSE));
        assertFalse(getLib().binaryBooleanAnd(Boolean.FALSE, getLib().number("123")));
        assertFalse(getLib().binaryBooleanAnd(getLib().number("123"), Boolean.FALSE));
        assertFalse(getLib().binaryBooleanAnd(Boolean.FALSE, "123"));
        assertFalse(getLib().binaryBooleanAnd(Boolean.FALSE, null));
        assertFalse(getLib().binaryBooleanAnd(null, Boolean.FALSE));
        assertFalse(getLib().binaryBooleanAnd("123", Boolean.FALSE));
        assertNull(getLib().binaryBooleanAnd(Boolean.TRUE, null));
        assertNull(getLib().binaryBooleanAnd(null, Boolean.TRUE));
        assertNull(getLib().binaryBooleanAnd(null, null));
        assertNull(getLib().binaryBooleanAnd("123", "123"));
    }

    //
    // String operators
    //
    @Test
    public void testIsString() {
        assertFalse(getLib().isString(null));
        assertFalse(getLib().isString(getLib().number("1")));
        assertTrue(getLib().isString("abc"));
        assertFalse(getLib().isString(true));
        assertFalse(getLib().isString(getLib().date("2020-01-01")));
        assertFalse(getLib().isString(getLib().time("12:00:00")));
        assertFalse(getLib().isString(getLib().dateAndTime("2020-01-01T12:00:00")));
        assertFalse(getLib().isString(getLib().duration("P1Y1M")));
        assertFalse(getLib().isString(getLib().asList("a")));
        assertFalse(getLib().isString(new Context()));
        assertFalse(getLib().isString(new Range(true, BigDecimal.ZERO, true, BigDecimal.ONE)));
    }

    @Test
    public void testStringValue() {
        assertNull(getLib().stringValue(null));
        assertEquals("a", getLib().stringValue("a"));
    }

    @Test
    public void testStringIs() {
        assertTrue(getLib().stringIs(null, null));
        assertFalse(getLib().stringIs("a", null));
        assertFalse(getLib().stringIs(null, "b"));

        assertFalse(getLib().stringIs("a", "b"));
        assertTrue(getLib().stringIs("b", "b"));
    }

    @Test
    public void testStringEqual() {
        assertTrue(getLib().stringEqual(null, null));
        assertFalse(getLib().stringEqual("a", null));
        assertFalse(getLib().stringEqual(null, "b"));

        assertFalse(getLib().stringEqual("a", "b"));
        assertTrue(getLib().stringEqual("b", "b"));
    }

    @Test
    public void testStringNotEqual() {
        assertFalse(getLib().stringNotEqual(null, null));
        assertTrue(getLib().stringNotEqual("a", null));
        assertTrue(getLib().stringNotEqual(null, "b"));

        assertTrue(getLib().stringNotEqual("a", "b"));
        assertFalse(getLib().stringNotEqual("b", "b"));
    }

    @Test
    public void testStringAdd() {
        assertNull(getLib().stringAdd(null, null));
        assertNull(getLib().stringAdd("a", null));
        assertNull(getLib().stringAdd(null, "b"));

        assertEquals("ab", getLib().stringAdd("a", "b"));
        assertEquals("ba", getLib().stringAdd("b", "a"));
    }

    //
    // Date operators
    //
    @Test
    public void testIsDate() {
        assertFalse(getLib().isDate(null));
        assertFalse(getLib().isDate(getLib().number("1")));
        assertFalse(getLib().isDate("abc"));
        assertFalse(getLib().isDate(true));
        assertTrue(getLib().isDate(getLib().date("2020-01-01")));
        assertFalse(getLib().isDate(getLib().time("12:00:00")));
        assertFalse(getLib().isDate(getLib().dateAndTime("2020-01-01T12:00:00")));
        assertFalse(getLib().isDate(getLib().duration("P1Y1M")));
        assertFalse(getLib().isDate(getLib().asList("a")));
        assertFalse(getLib().isDate(new Context()));
        assertFalse(getLib().isDate(new Range(true, BigDecimal.ZERO, true, BigDecimal.ONE)));
    }

    @Test
    public void testDateValue() {
        assertNull(getLib().dateValue(null));

        assertEquals(Long.valueOf(0L), getLib().dateValue(makeDate("1970-01-01")));
        assertEquals(Long.valueOf(86400L), getLib().dateValue(makeDate("1970-01-02")));
        assertEquals(Long.valueOf(2678400L), getLib().dateValue(makeDate("1970-02-01")));
        assertEquals(Long.valueOf(946684800L), getLib().dateValue(makeDate("2000-01-01")));

        assertEquals(Long.valueOf(-124334265600L), getLib().dateValue(makeDate("-1970-01-02")));
        assertEquals(Long.valueOf(-124331673600L), getLib().dateValue(makeDate("-1970-02-01")));
        assertEquals(Long.valueOf(-125281123200L), getLib().dateValue(makeDate("-2000-01-01")));
    }

    @Test
    public void testDateIs() {
        assertTrue(getLib().dateIs(null, null));
        assertFalse(getLib().dateIs(null, makeDate("2016-08-01")));
        assertFalse(getLib().dateIs(makeDate("2016-08-01"), null));

        assertTrue(getLib().dateIs(makeDate("2016-08-01"), makeDate("2016-08-01")));
        assertFalse(getLib().dateIs(makeDate("2016-08-01"), makeDate("2016-08-02")));
    }

    @Test
    public void testDateEqual() {
        assertTrue(getLib().dateEqual(null, null));
        assertFalse(getLib().dateEqual(null, makeDate("2016-08-01")));
        assertFalse(getLib().dateEqual(makeDate("2016-08-01"), null));

        assertTrue(getLib().dateEqual(makeDate("2016-08-01"), makeDate("2016-08-01")));
        assertFalse(getLib().dateEqual(makeDate("2016-08-01"), makeDate("2016-08-02")));
    }

    @Test
    public void testDateNotEqual() {
        assertFalse(getLib().dateNotEqual(null, null));
        assertTrue(getLib().dateNotEqual(null, makeDate("2016-08-01")));
        assertTrue(getLib().dateNotEqual(makeDate("2016-08-01"), null));

        assertFalse(getLib().dateNotEqual(makeDate("2016-08-01"), makeDate("2016-08-01")));
        assertTrue(getLib().dateNotEqual(makeDate("2016-08-01"), makeDate("2016-08-02")));
    }

    @Test
    public void testDateLessThan() {
        assertNull(getLib().dateLessThan(null, null));
        assertNull(getLib().dateLessThan(null, makeDate("2016-08-01")));
        assertNull(getLib().dateLessThan(makeDate("2016-08-01"), null));

        assertFalse(getLib().dateLessThan(makeDate("2016-08-01"), makeDate("2016-08-01")));
        assertTrue(getLib().dateLessThan(makeDate("2016-08-01"), makeDate("2016-08-02")));
    }

    @Test
    public void testDateGreaterThan() {
        assertNull(getLib().dateGreaterThan(null, null));
        assertNull(getLib().dateGreaterThan(null, makeDate("2016-08-01")));
        assertNull(getLib().dateGreaterThan(makeDate("2016-08-01"), null));

        assertFalse(getLib().dateGreaterThan(makeDate("2016-08-01"), makeDate("2016-08-01")));
        assertTrue(getLib().dateGreaterThan(makeDate("2016-08-02"), makeDate("2016-08-01")));
    }

    @Test
    public void testDateLessEqualThan() {
        assertTrue(getLib().dateLessEqualThan(null, null));
        assertNull(getLib().dateLessEqualThan(null, makeDate("2016-08-01")));
        assertNull(getLib().dateLessEqualThan(makeDate("2016-08-01"), null));

        assertTrue(getLib().dateLessEqualThan(makeDate("2016-08-01"), makeDate("2016-08-01")));
        assertFalse(getLib().dateLessEqualThan(makeDate("2016-08-03"), makeDate("2016-08-02")));
    }

    @Test
    public void testDateGreaterEqualThan() {
        assertTrue(getLib().dateGreaterEqualThan(null, null));
        assertNull(getLib().dateGreaterEqualThan(null, makeDate("2016-08-01")));
        assertNull(getLib().dateGreaterEqualThan(makeDate("2016-08-01"), null));

        assertTrue(getLib().dateGreaterEqualThan(makeDate("2016-08-01"), makeDate("2016-08-01")));
        assertFalse(getLib().dateGreaterEqualThan(makeDate("2016-08-01"), makeDate("2016-08-03")));
    }

    @Test
    public void testDateSubtract() {
        assertNull(getLib().dateSubtract(null, null));
        assertNull(getLib().dateSubtract(null, makeDate("2016-08-01")));
        assertNull(getLib().dateSubtract(makeDate("2016-08-01"), null));

        assertEqualsDateTime("PT0S", getLib().dateSubtract(makeDate("2016-08-01"), makeDate("2016-08-01")));
        assertEqualsDateTime("-P2D", getLib().dateSubtract(makeDate("2016-08-01"), makeDate("2016-08-03")));
    }

    @Test
    public void testDateAddDuration() {
        assertNull(getLib().dateAddDuration(null, null));
        assertNull(getLib().dateAddDuration(null, makeDuration("P0Y2M")));
        assertNull(getLib().dateAddDuration(makeDate("2016-08-01"), null));

        assertEqualsDateTime("2016-10-01", getLib().dateAddDuration(makeDate("2016-08-01"), makeDuration("P0Y2M")));
        assertEqualsDateTime("2016-06-01", getLib().dateAddDuration(makeDate("2016-08-01"), makeDuration("-P0Y2M")));
    }

    @Test
    public void testDateSubtractDuration() {
        assertNull(getLib().dateSubtractDuration(null, null));
        assertNull(getLib().dateSubtractDuration(null, makeDuration("P0Y2M")));
        assertNull(getLib().dateSubtractDuration(makeDate("2016-08-01"), null));

        assertEqualsDateTime("2016-06-01", getLib().dateSubtractDuration(makeDate("2016-08-01"), makeDuration("P0Y2M")));
        assertEqualsDateTime("2016-10-01", getLib().dateSubtractDuration(makeDate("2016-08-01"), makeDuration("-P0Y2M")));
    }

    //
    // Time operators
    //
    @Test
    public void testIsTime() {
        assertFalse(getLib().isTime(null));
        assertFalse(getLib().isTime(getLib().number("1")));
        assertFalse(getLib().isTime("abc"));
        assertFalse(getLib().isTime(true));
        assertFalse(getLib().isTime(getLib().date("2020-01-01")));
        assertTrue(getLib().isTime(getLib().time("12:00:00")));
        assertFalse(getLib().isTime(getLib().dateAndTime("2020-01-01T12:00:00")));
        assertFalse(getLib().isTime(getLib().duration("P1Y1M")));
        assertFalse(getLib().isTime(getLib().asList("a")));
        assertFalse(getLib().isTime(new Context()));
        assertFalse(getLib().isTime(new Range(true, BigDecimal.ZERO, true, BigDecimal.ONE)));
    }

    @Test
    public void testTimeValue() {
        assertNull(getLib().timeValue(null));

        // local time
        assertEquals(Long.valueOf(3723L), getLib().timeValue(makeTime("01:02:03")));
        assertEquals(Long.valueOf(3723L), getLib().timeValue(makeTime("01:02:03.0004")));

        // offset time
        assertEquals(Long.valueOf(3723L), getLib().timeValue(makeTime("01:02:03Z")));
        assertEquals(Long.valueOf(3723L), getLib().timeValue(makeTime("01:02:03Z")));
        assertEquals(Long.valueOf(3723L), getLib().timeValue(makeTime("01:02:03+00:00")));
        assertEquals(Long.valueOf(63L), getLib().timeValue(makeTime("01:02:03+01:01")));

        // zoneid time
        assertEquals(Long.valueOf(123L), getLib().timeValue(makeTime("01:02:03@Europe/Paris")));
        assertEquals(Long.valueOf(3723L), getLib().timeValue(makeTime("01:02:03@Etc/UTC")));
    }

    @Test
    public void testTimeIs() {
        assertTrue(getLib().timeIs(null, null));
        assertFalse(getLib().timeIs(null, makeTime("12:00:00Z")));
        assertFalse(getLib().timeIs(makeTime("12:00:00Z"), null));

        // same times are is()
        assertTrue(getLib().timeIs(makeTime("10:30:00"), makeTime("10:30:00")));
        // different times are not is()
        assertFalse(getLib().timeIs(makeTime("10:30:00"), makeTime("10:30:01")));
        // different times with zero milliseconds are is()
        assertTrue(getLib().timeIs(makeTime("10:30:00.0000"), makeTime("10:30:00")));
        // different times with same milliseconds are is()
        assertTrue(getLib().timeIs(makeTime("10:30:00.0001"), makeTime("10:30:00.0001")));
        // different times with different milliseconds are is()
        assertTrue(getLib().timeIs(makeTime("10:30:00.0001"), makeTime("10:30:00.0002")));
        // same times in same zone are is()
        assertTrue(getLib().timeIs(makeTime("10:30:00@Europe/Paris"), makeTime("10:30:00@Europe/Paris")));
        // same times - one with zone one without are not is()
        assertFalse(getLib().timeIs(makeTime("10:30:00@Europe/Paris"), makeTime("10:30:00")));
        // same times with different zones are not is()
        assertFalse(getLib().timeIs(makeTime("10:30:00@Europe/Paris"), makeTime("10:30:00@Asia/Dhaka")));
        // same times = one with offset, the other with zone are not equal
        assertFalse(getLib().timeIs(makeTime("10:30:00+02:00"), makeTime("10:30:00@Europe/Paris")));
        // same times = one with Z zone, the other with UTC are is()
        assertTrue(getLib().timeIs(makeTime("10:30:00Z"), makeTime("10:30:00+00:00")));
    }

    @Test
    public void testTimeEqual() {
        assertTrue(getLib().timeEqual(null, null));
        assertFalse(getLib().timeEqual(null, makeTime("12:00:00Z")));
        assertFalse(getLib().timeEqual(makeTime("12:00:00Z"), null));

        // same times are equal
        assertTrue(getLib().timeEqual(makeTime("10:30:00"), makeTime("10:30:00")));
        // different times are not equal
        assertFalse(getLib().timeEqual(makeTime("10:30:00"), makeTime("10:30:01")));
        // same times with zero milliseconds are equal
        assertTrue(getLib().timeEqual(makeTime("10:30:00.0000"), makeTime("10:30:00")));
        // same times with same milliseconds are equal
        assertTrue(getLib().timeEqual(makeTime("10:30:00.0001"), makeTime("10:30:00.0001")));
        // same times with different milliseconds are equal
        assertTrue(getLib().timeEqual(makeTime("10:30:00.0001"), makeTime("10:30:00.0002")));
        // same times in same zone are equal
        assertTrue(getLib().timeEqual(makeTime("10:30:00@Europe/Paris"), makeTime("10:30:00@Europe/Paris")));
        // same times - one with zone one without are not equal
        assertFalse(getLib().timeEqual(makeTime("10:30:00@Europe/Paris"), makeTime("10:30:00")));
        // same times with different zones are not equal
        assertFalse(getLib().timeEqual(makeTime("10:30:00@Europe/Paris"), makeTime("10:30:00@Asia/Dhaka")));
        // same times = one with offset, the other with zone are not equal
        assertFalse(getLib().timeEqual(makeTime("10:30:00+02:00"), makeTime("10:30:00@Europe/Paris")));
        // same times = one with Z zone, the other with UTC are equal
        assertTrue(getLib().timeEqual(makeTime("10:30:00Z"), makeTime("10:30:00+00:00")));

        // times with equivalent offset and zone id are equal
        assertTrue(getLib().timeEqual(makeTime("12:00:00"), makeTime("12:00:00+00:00")));
        assertTrue(getLib().timeEqual(makeTime("00:00:00+00:00"), makeTime("00:00:00@Etc/UTC")));
        assertTrue(getLib().timeEqual(makeTime("00:00:00Z"), makeTime("00:00:00+00:00")));
        assertTrue(getLib().timeEqual(makeTime("00:00:00Z"), makeTime("00:00:00@Etc/UTC")));
        assertTrue(getLib().timeEqual(makeTime("10:30:00+01:00"), makeTime("10:30:00@Europe/Paris")));
    }

    @Test
    public void testTimeNotEqual() {
        assertFalse(getLib().timeNotEqual(null, null));
        assertTrue(getLib().timeNotEqual(null, makeTime("12:00:00Z")));
        assertTrue(getLib().timeNotEqual(makeTime("12:00:00Z"), null));

        assertFalse(getLib().timeNotEqual(makeTime("12:00:00Z"), makeTime("12:00:00Z")));
        assertTrue(getLib().timeNotEqual(makeTime("12:00:00Z"), makeTime("12:00:01Z")));
    }

    @Test
    public void testTimeLessThan() {
        assertNull(getLib().timeLessThan(null, null));
        assertNull(getLib().timeLessThan(null, makeTime("12:00:00Z")));
        assertNull(getLib().timeLessThan(makeTime("12:00:00Z"), null));

        assertFalse(getLib().timeLessThan(makeTime("12:00:00Z"), makeTime("12:00:00Z")));
        assertTrue(getLib().timeLessThan(makeTime("11:00:00Z"), makeTime("12:00:01Z")));
    }

    @Test
    public void testTimeGreaterThan() {
        assertNull(getLib().timeGreaterThan(null, null));
        assertNull(getLib().timeGreaterThan(null, makeTime("12:00:00Z")));
        assertNull(getLib().timeGreaterThan(makeTime("12:00:00Z"), null));

        assertFalse(getLib().timeGreaterThan(makeTime("12:00:00Z"), makeTime("12:00:00Z")));
        assertTrue(getLib().timeGreaterThan(makeTime("13:00:00Z"), makeTime("12:00:01Z")));
    }

    @Test
    public void testTimeLessEqualThan() {
        assertTrue(getLib().timeLessEqualThan(null, null));
        assertNull(getLib().timeLessEqualThan(null, makeTime("12:00:00Z")));
        assertNull(getLib().timeLessEqualThan(makeTime("12:00:00Z"), null));

        assertTrue(getLib().timeLessEqualThan(makeTime("12:00:00Z"), makeTime("12:00:00Z")));
        assertFalse(getLib().timeLessEqualThan(makeTime("13:00:00Z"), makeTime("12:00:01Z")));
    }

    @Test
    public void testTimeGreaterEqualThan() {
        assertTrue(getLib().timeGreaterEqualThan(null, null));
        assertNull(getLib().timeGreaterEqualThan(null, makeTime("12:00:00Z")));
        assertNull(getLib().timeGreaterEqualThan(makeTime("12:00:00Z"), null));

        assertTrue(getLib().timeGreaterEqualThan(makeTime("12:00:00Z"), makeTime("12:00:00Z")));
        assertFalse(getLib().timeGreaterEqualThan(makeTime("11:00:00Z"), makeTime("12:00:01Z")));
    }

    @Test
    public void testTimeSubtract() {
        assertNull(getLib().timeSubtract(null, null));
        assertNull(getLib().timeSubtract(null, makeTime("12:00:00Z")));
        assertNull(getLib().timeSubtract(makeTime("12:00:00Z"), null));

        assertEquals(makeDuration("PT1H"), getLib().timeSubtract(makeTime("13:00:00Z"), makeTime("12:00:00Z")));
        assertEquals(makeDuration("P0D"), getLib().timeSubtract(makeTime("12:00:00Z"), makeTime("12:00:00Z")));
        assertEquals(makeDuration("-PT1H"), getLib().timeSubtract(makeTime("12:00:00Z"), makeTime("13:00:00Z")));
    }

    @Test
    public void testTimeAddDuration() {
        assertNull(getLib().timeAddDuration(null, null));
        assertNull(getLib().timeAddDuration(null, makeDuration("P0DT1H")));
        assertNull(getLib().timeAddDuration(makeTime("12:00:00Z"), null));

        assertEqualsDateTime("13:00:01Z", getLib().timeAddDuration(makeTime("12:00:01Z"), makeDuration("P0DT1H")));
        assertEqualsDateTime("12:00:01Z", getLib().timeAddDuration(makeTime("12:00:01Z"), makeDuration("P1DT0H")));
    }

    @Test
    public void testTimeSubtractDuration() {
        assertNull(getLib().timeSubtractDuration(null, null));
        assertNull(getLib().timeSubtractDuration(null, makeDuration("P0DT1H")));
        assertNull(getLib().timeSubtractDuration(makeTime("12:00:01Z"), null));

        assertEqualsDateTime("11:00:01Z", getLib().timeSubtractDuration(makeTime("12:00:01Z"), makeDuration("P0DT1H")));
        assertEqualsDateTime("12:00:01Z", getLib().timeSubtractDuration(makeTime("12:00:01Z"), makeDuration("P1DT0H")));
    }

    //
    // Date and time operators
    //
    @Test
    public void testIsDateTime() {
        assertFalse(getLib().isDateTime(null));
        assertFalse(getLib().isDateTime(getLib().number("1")));
        assertFalse(getLib().isDateTime("abc"));
        assertFalse(getLib().isDateTime(true));
        assertFalse(getLib().isDateTime(getLib().date("2020-01-01")));
        assertFalse(getLib().isDateTime(getLib().time("12:00:00")));
        assertTrue(getLib().isDateTime(getLib().dateAndTime("2020-01-01T12:00:00")));
        assertFalse(getLib().isDateTime(getLib().duration("P1Y1M")));
        assertFalse(getLib().isDateTime(getLib().asList("a")));
        assertFalse(getLib().isDateTime(new Context()));
        assertFalse(getLib().isDateTime(new Range(true, BigDecimal.ZERO, true, BigDecimal.ONE)));
    }

    @Test
    public void testDateTimeValue() {
        assertNull(getLib().dateTimeValue(null));

        // local date time
        assertEquals(Long.valueOf(3723L), getLib().dateTimeValue(makeDateAndTime("1970-01-01T01:02:03")));
        assertEquals(Long.valueOf(3723L), getLib().dateTimeValue(makeDateAndTime("1970-01-01T01:02:03.0004")));
        assertEquals(Long.valueOf(315536523L), getLib().dateTimeValue(makeDateAndTime("1980-01-01T01:02:03.0004")));
        assertEquals(Long.valueOf(-124649967477L), getLib().dateTimeValue(makeDateAndTime("-1980-01-01T01:02:03.0004")));

        // offset date time
        assertEquals(Long.valueOf(3723L), getLib().dateTimeValue(makeDateAndTime("1970-01-01T01:02:03Z")));
        assertEquals(Long.valueOf(3723L), getLib().dateTimeValue(makeDateAndTime("1970-01-01T01:02:03Z")));
        assertEquals(Long.valueOf(3723L), getLib().dateTimeValue(makeDateAndTime("1970-01-01T01:02:03+00:00")));
        assertEquals(Long.valueOf(63L), getLib().dateTimeValue(makeDateAndTime("1970-01-01T01:02:03+01:01")));
        assertEquals(Long.valueOf(315532863L), getLib().dateTimeValue(makeDateAndTime("1980-01-01T01:02:03+01:01")));
        assertEquals(Long.valueOf(-124649971137L), getLib().dateTimeValue(makeDateAndTime("-1980-01-01T01:02:03+01:01")));

        // zoneid date time
        assertEquals(Long.valueOf(123L), getLib().dateTimeValue(makeDateAndTime("1970-01-01T01:02:03@Europe/Paris")));
        assertEquals(Long.valueOf(3723L), getLib().dateTimeValue(makeDateAndTime("1970-01-01T01:02:03@Etc/UTC")));
        assertEquals(Long.valueOf(315536523L), getLib().dateTimeValue(makeDateAndTime("1980-01-01T01:02:03@Etc/UTC")));
        assertEquals(Long.valueOf(-124649967477L), getLib().dateTimeValue(makeDateAndTime("-1980-01-01T01:02:03@Etc/UTC")));
    }

    @Test
    public void testDateTimeIs() {
        // datetime equals null
        assertTrue(getLib().dateTimeIs(null, null));
        assertFalse(getLib().dateTimeIs(makeDateAndTime("2018-12-08T00:00:00"), null));
        assertFalse(getLib().dateTimeIs(null, makeDateAndTime("2018-12-08T00:00:00")));

        assertTrue(getLib().dateTimeIs(makeDateAndTime("2016-08-01T11:00:00Z"), makeDateAndTime("2016-08-01T11:00:00Z")));
        assertFalse(getLib().dateTimeIs(makeDateAndTime("2016-08-01T11:00:00Z"), makeDateAndTime("2016-08-01T11:00:01Z")));

        // same datetimes are is()
        assertTrue(getLib().dateTimeIs(makeDateAndTime("2018-12-08T10:30:00"), makeDateAndTime("2018-12-08T10:30:00")));
        // datetimes with no time is is() to datetime with zero time
        assertTrue(getLib().dateTimeIs(makeDateAndTime("2018-12-08"), makeDateAndTime("2018-12-08T00:00:00")));
        // datetimes with same milliseconds are is()
        assertTrue(getLib().dateTimeIs(makeDateAndTime("2018-12-08T00:00:00.0001"), makeDateAndTime("2018-12-08T00:00:00.0001")));
        // datetimes with different milliseconds are is()
        assertTrue(getLib().dateTimeIs(makeDateAndTime("2018-12-08T00:00:00.0001"), makeDateAndTime("2018-12-08T00:00:00.0002")));
        // different datetimes are not is()
        assertFalse(getLib().dateTimeIs(makeDateAndTime("2018-12-08T00:00:00"), makeDateAndTime("2018-12-07T00:00:00")));
        // same datetimes in same zone are is()
        assertTrue(getLib().dateTimeIs(makeDateAndTime("2018-12-08T00:00:00@Europe/Paris"), makeDateAndTime("2018-12-08T00:00:00@Europe/Paris")));
        // same datetimes in different zones are not is()
        assertFalse(getLib().dateTimeIs(makeDateAndTime("2018-12-08T00:00:00@Europe/Paris"), makeDateAndTime("2018-12-08T00:00:00@Asia/Dhaka")));
        // same datetimes, one with zone one without are not is()
        assertFalse(getLib().dateTimeIs(makeDateAndTime("2018-12-08T00:00:00"), makeDateAndTime("2018-12-08T00:00:00@Asia/Dhaka")));
        // same datetimes, one with offset and the other with zone are not is()
        assertFalse(getLib().dateTimeIs(makeDateAndTime("2018-12-08T00:00:00+02:00"), makeDateAndTime("2018-12-08T00:00:00@Europe/Paris")));
    }

    @Test
    public void testDateTimeEqual() {
        // datetime equals null
        assertTrue(getLib().dateTimeEqual(null, null));
        assertFalse(getLib().dateTimeEqual(makeDateAndTime("2018-12-08T00:00:00"), null));
        assertFalse(getLib().dateTimeEqual(null, makeDateAndTime("2018-12-08T00:00:00")));

        assertTrue(getLib().dateTimeEqual(makeDateAndTime("2016-08-01T11:00:00Z"), makeDateAndTime("2016-08-01T11:00:00Z")));
        assertFalse(getLib().dateTimeEqual(makeDateAndTime("2016-08-01T11:00:00Z"), makeDateAndTime("2016-08-01T11:00:01Z")));

        // same datetimes are equal
        assertTrue(getLib().dateTimeEqual(makeDateAndTime("2018-12-08T10:30:00"), makeDateAndTime("2018-12-08T10:30:00")));
        // datetimes with no time is equal to datetime with zero time
        assertTrue(getLib().dateTimeEqual(makeDateAndTime("2018-12-08"), makeDateAndTime("2018-12-08T00:00:00")));
        // datetimes with same milliseconds are equal
        assertTrue(getLib().dateTimeEqual(makeDateAndTime("2018-12-08T00:00:00.0001"), makeDateAndTime("2018-12-08T00:00:00.0001")));
        // datetimes with different milliseconds are equal
        assertTrue(getLib().dateTimeEqual(makeDateAndTime("2018-12-08T00:00:00.0001"), makeDateAndTime("2018-12-08T00:00:00.0002")));
        // different datetimes are not equal
        assertFalse(getLib().dateTimeEqual(makeDateAndTime("2018-12-08T00:00:00"), makeDateAndTime("2018-12-07T00:00:00")));
        // same datetimes in same zone are equal
        assertTrue(getLib().dateTimeEqual(makeDateAndTime("2018-12-08T00:00:00@Europe/Paris"), makeDateAndTime("2018-12-08T00:00:00@Europe/Paris")));
        // same datetimes in different zones are not equal
        assertFalse(getLib().dateTimeEqual(makeDateAndTime("2018-12-08T00:00:00@Europe/Paris"), makeDateAndTime("2018-12-08T00:00:00@Asia/Dhaka")));
        // same datetimes, one with zone one without are not equal
        assertFalse(getLib().dateTimeEqual(makeDateAndTime("2018-12-08T00:00:00"), makeDateAndTime("2018-12-08T00:00:00@Asia/Dhaka")));
        // same datetimes, one with offset and the other with zone are not equal
        assertFalse(getLib().dateTimeEqual(makeDateAndTime("2018-12-08T00:00:00+02:00"), makeDateAndTime("2018-12-08T00:00:00@Europe/Paris")));

        // datetime with equivalent offset and zone id are equal
        assertTrue(getLib().dateTimeEqual(makeDateAndTime("2018-12-08T00:00:00+00:00"), makeDateAndTime("2018-12-08T00:00:00@Etc/UTC")));
        assertTrue(getLib().dateTimeEqual(makeDateAndTime("2018-12-08T12:00:00Z"), makeDateAndTime("2018-12-08T12:00:00+00:00")));
        assertTrue(getLib().dateTimeEqual(makeDateAndTime("2018-12-08T00:00:00Z"), makeDateAndTime("2018-12-08T00:00:00@Etc/UTC")));
    }

    @Test
    public void testDateTimeNotEqual() {
        assertFalse(getLib().dateTimeNotEqual(null, null));
        assertTrue(getLib().dateTimeNotEqual(null, makeDateAndTime("2016-08-01T11:00:00Z")));
        assertTrue(getLib().dateTimeNotEqual(makeDateAndTime("2016-08-01T11:00:00Z"), null));

        assertFalse(getLib().dateTimeNotEqual(makeDateAndTime("2016-08-01T11:00:00Z"), makeDateAndTime("2016-08-01T11:00:00Z")));
        assertTrue(getLib().dateTimeNotEqual(makeDateAndTime("2016-08-01T11:00:00Z"), makeDateAndTime("2016-08-01T11:00:01Z")));
    }

    @Test
    public void testDateTimeLessThan() {
        assertNull(getLib().dateTimeLessThan(null, null));
        assertNull(getLib().dateTimeLessThan(null, makeDateAndTime("2016-08-01T11:00:00Z")));
        assertNull(getLib().dateTimeLessThan(makeDateAndTime("2016-08-01T11:00:00Z"), null));

        assertFalse(getLib().dateTimeLessThan(makeDateAndTime("2016-08-01T11:00:00Z"), makeDateAndTime("2016-08-01T11:00:00Z")));
        assertTrue(getLib().dateTimeLessThan(makeDateAndTime("2016-08-01T11:00:00Z"), makeDateAndTime("2017-08-01T11:00:01Z")));
    }

    @Test
    public void testDateTimeGreaterThan() {
        assertNull(getLib().dateTimeGreaterThan(null, null));
        assertNull(getLib().dateTimeGreaterThan(null, makeDateAndTime("2016-08-01T11:00:00Z")));
        assertNull(getLib().dateTimeGreaterThan(makeDateAndTime("2016-08-01T11:00:00Z"), null));

        assertTrue(getLib().dateTimeGreaterThan(makeDateAndTime("2017-08-01T11:00:00Z"), makeDateAndTime("2016-08-01T11:00:00Z")));
        assertFalse(getLib().dateTimeGreaterThan(makeDateAndTime("2016-08-01T11:00:00Z"), makeDateAndTime("2016-08-01T11:00:01Z")));
    }

    @Test
    public void testDateTimeLessEqualThan() {
        assertTrue(getLib().dateTimeLessEqualThan(null, null));
        assertNull(getLib().dateTimeLessEqualThan(null, makeDateAndTime("2016-08-01T11:00:00Z")));
        assertNull(getLib().dateTimeLessEqualThan(makeDateAndTime("2016-08-01T11:00:00Z"), null));

        assertTrue(getLib().dateTimeLessEqualThan(makeDateAndTime("2016-08-01T11:00:00Z"), makeDateAndTime("2016-08-01T11:00:00Z")));
        assertFalse(getLib().dateTimeLessEqualThan(makeDateAndTime("2016-08-01T11:00:01Z"), makeDateAndTime("2016-08-01T11:00:00Z")));
    }

    @Test
    public void testDateTimeGreaterEqualThan() {
        assertTrue(getLib().dateTimeGreaterEqualThan(null, null));
        assertNull(getLib().dateTimeGreaterEqualThan(null, makeDateAndTime("2016-08-01T11:00:00Z")));
        assertNull(getLib().dateTimeGreaterEqualThan(makeDateAndTime("2016-08-01T11:00:00Z"), null));

        assertTrue(getLib().dateTimeGreaterEqualThan(makeDateAndTime("2016-08-01T11:00:00Z"), makeDateAndTime("2016-08-01T11:00:00Z")));
        assertFalse(getLib().dateTimeGreaterEqualThan(makeDateAndTime("2015-08-01T11:00:00Z"), makeDateAndTime("2016-08-01T11:00:01Z")));
    }

    @Test
    public void testDateTimeSubtract() {
        assertNull(getLib().dateTimeSubtract(null, null));
        assertNull(getLib().dateTimeSubtract(null, makeDateAndTime("2016-08-01T12:00:00Z")));
        assertNull(getLib().dateTimeSubtract(makeDateAndTime("2016-08-01T12:00:00Z"), null));

        assertEquals(makeDuration("PT1H"), getLib().dateTimeSubtract(makeDateAndTime("2016-08-01T13:00:00Z"), makeDateAndTime("2016-08-01T12:00:00Z")));
        assertEquals(makeDuration("PT0S"), getLib().dateTimeSubtract(makeDateAndTime("2016-08-01T12:00:00Z"), makeDateAndTime("2016-08-01T12:00:00Z")));
        assertEquals(makeDuration("-P2DT1H"), getLib().dateTimeSubtract(makeDateAndTime("2016-08-01T12:00:00Z"), makeDateAndTime("2016-08-03T13:00:00Z")));

        assertEquals(makeDuration("P367DT6H58M59S"), getLib().dateTimeSubtract(makeDateAndTime("2016-12-24T23:59:00-08:00"), makeDateAndTime("2015-12-24T00:00:01-01:00")));
    }

    @Test
    public void testDateTimeAddDuration() {
        assertNull(getLib().dateTimeAddDuration(null, null));
        assertNull(getLib().dateTimeAddDuration(null, makeDuration("P1Y1M")));
        assertNull(getLib().dateTimeAddDuration(makeDateAndTime("2016-08-01T12:00:00Z"), null));

        assertEqualsDateTime("2017-03-01T12:00:01Z", getLib().dateTimeAddDuration(makeDateAndTime("2016-02-01T12:00:01Z"), makeDuration("P1Y1M")));
        assertEqualsDateTime("2015-01-01T12:00:01Z", getLib().dateTimeAddDuration(makeDateAndTime("2016-02-01T12:00:01Z"), makeDuration("-P1Y1M")));

        assertEqualsDateTime("2016-02-02T13:00:01Z", getLib().dateTimeAddDuration(makeDateAndTime("2016-02-01T12:00:01Z"), makeDuration("P1DT1H")));
        assertEqualsDateTime("2016-01-31T11:00:01Z", getLib().dateTimeAddDuration(makeDateAndTime("2016-02-01T12:00:01Z"), makeDuration("-P1DT1H")));
    }

    @Test
    public void testDateTimeSubtractDuration() {
        assertNull(getLib().dateTimeSubtractDuration(null, null));
        assertNull(getLib().dateTimeSubtractDuration(null, makeDuration("P1Y1M")));
        assertNull(getLib().dateTimeSubtractDuration(makeDateAndTime("2016-08-01T12:00:00Z"), null));

        assertEqualsDateTime("2015-01-01T12:00:01Z", getLib().dateTimeSubtractDuration(makeDateAndTime("2016-02-01T12:00:01Z"), makeDuration("P1Y1M")));
        assertEqualsDateTime("2017-03-01T12:00:01Z", getLib().dateTimeSubtractDuration(makeDateAndTime("2016-02-01T12:00:01Z"), makeDuration("-P1Y1M")));

        assertEqualsDateTime("2016-01-31T11:00:01Z", getLib().dateTimeSubtractDuration(makeDateAndTime("2016-02-01T12:00:01Z"), makeDuration("P1DT1H")));
        assertEqualsDateTime("2016-02-02T13:00:01Z", getLib().dateTimeSubtractDuration(makeDateAndTime("2016-02-01T12:00:01Z"), makeDuration("-P1DT1H")));
    }

    //
    // Duration operators
    //
    @Test
    public void testIsDuration() {
        assertFalse(getLib().isDuration(null));

        // years and months
        assertTrue(getLib().isYearsAndMonthsDuration(makeDuration("P1Y2M")));
        assertTrue(getLib().isYearsAndMonthsDuration(makeDuration("-P1Y2M")));
        assertFalse(getLib().isYearsAndMonthsDuration(makeDuration("P1DT2H3M4S")));
        assertFalse(getLib().isYearsAndMonthsDuration(makeDuration("-P1DT2H3M4S")));
        assertFalse(getLib().isYearsAndMonthsDuration(makeDuration("P1Y2M1DT2H3M4S")));
        assertFalse(getLib().isYearsAndMonthsDuration(makeDuration("-P1Y2M1DT2H3M4S")));

        // days and time
        assertFalse(getLib().isDaysAndTimeDuration(makeDuration("P1Y2M")));
        assertFalse(getLib().isDaysAndTimeDuration(makeDuration("-P1Y2M")));
        assertTrue(getLib().isDaysAndTimeDuration(makeDuration("P1DT2H3M4S")));
        assertTrue(getLib().isDaysAndTimeDuration(makeDuration("-P1DT2H3M4S")));
//      Fail in Pure lib
//        assertFalse(getLib().isDaysAndTimeDuration(makeDuration("P1Y2M1DT2H3M4S")));
//        assertFalse(getLib().isDaysAndTimeDuration(makeDuration("-P1Y2M1DT2H3M4S")));

        // mixture
        assertTrue(getLib().isDuration(makeDuration("P1Y2M")));
        assertTrue(getLib().isDuration(makeDuration("-P1Y2M")));
        assertTrue(getLib().isDuration(makeDuration("P1DT2H3M4S")));
        assertTrue(getLib().isDuration(makeDuration("-P1DT2H3M4S")));
        assertFalse(getLib().isDuration(makeDuration("P1Y2M1DT2H3M4S")));
        assertFalse(getLib().isDuration(makeDuration("-P1Y2M1DT2H3M4S")));
    }

    @Test
    public void testDurationValue() {
        assertNull(getLib().durationValue(null));

        // years and months
        assertEquals(Long.valueOf(12L + 2L), getLib().durationValue(makeDuration("P1Y2M")));
        assertEquals(Long.valueOf(-(12L + 2L)), getLib().durationValue(makeDuration("-P1Y2M")));

        // days and time
        assertEquals(Long.valueOf((24 + 2) * 3600L + 3 * 60L + 4), getLib().durationValue(makeDuration("P1DT2H3M4S")));
        assertEquals(Long.valueOf(- ((24 + 2) * 3600L + 3 * 60L + 4)), getLib().durationValue(makeDuration("-P1DT2H3M4S")));

        // mixture
        assertNull(getLib().durationValue(makeDuration("P1Y2M1DT2H3M4S")));
        assertNull(getLib().durationValue(makeDuration("-P1Y2M1DT2H3M4S")));
    }

    @Test
    public void testDurationIs() {
        assertTrue(getLib().durationIs(null, null));
        assertFalse(getLib().durationIs(null, makeDuration("P1Y1M")));
        assertFalse(getLib().durationIs(makeDuration("P1Y1M"), null));

        assertTrue(getLib().durationIs(makeDuration("P1Y1M"), makeDuration("P1Y1M")));
        assertTrue(getLib().durationIs(makeDuration("P1Y1M"), makeDuration("P0Y13M")));
        assertFalse(getLib().durationIs(makeDuration("P1Y1M"), makeDuration("P1Y2M")));

        assertTrue(getLib().durationIs(makeDuration("P1DT1H"), makeDuration("P1DT1H")));
        assertTrue(getLib().durationIs(makeDuration("P1DT1H"), makeDuration("P0DT25H")));
        assertFalse(getLib().durationIs(makeDuration("P1DT1H"), makeDuration("P1DT2H")));

        // different semantic domains
        assertFalse(getLib().durationIs(makeDuration("P0Y"), makeDuration("P0D")));
    }

    @Test
    public void testDurationEqual() {
        assertTrue(getLib().durationEqual(null, null));
        assertFalse(getLib().durationEqual(null, makeDuration("P1Y1M")));
        assertFalse(getLib().durationEqual(makeDuration("P1Y1M"), null));

        assertTrue(getLib().durationEqual(makeDuration("P1Y1M"), makeDuration("P1Y1M")));
        assertTrue(getLib().durationEqual(makeDuration("P1Y1M"), makeDuration("P0Y13M")));
        assertFalse(getLib().durationEqual(makeDuration("P1Y1M"), makeDuration("P1Y2M")));

        assertTrue(getLib().durationEqual(makeDuration("P1DT1H"), makeDuration("P1DT1H")));
        assertTrue(getLib().durationEqual(makeDuration("P1DT1H"), makeDuration("P0DT25H")));
        assertFalse(getLib().durationEqual(makeDuration("P1DT1H"), makeDuration("P1DT2H")));
    }

    @Test
    public void testDurationNotEqual() {
        assertFalse(getLib().durationNotEqual(null, null));
        assertTrue(getLib().durationNotEqual(null, makeDuration("P1Y1M")));
        assertTrue(getLib().durationNotEqual(makeDuration("P1Y1M"), null));

        assertFalse(getLib().durationNotEqual(makeDuration("P1Y1M"), makeDuration("P1Y1M")));
        assertFalse(getLib().durationNotEqual(makeDuration("P1Y1M"), makeDuration("P0Y13M")));
        assertTrue(getLib().durationNotEqual(makeDuration("P1Y1M"), makeDuration("P1Y2M")));

        assertFalse(getLib().durationNotEqual(makeDuration("P1DT1H"), makeDuration("P1DT1H")));
        assertFalse(getLib().durationNotEqual(makeDuration("P1DT1H"), makeDuration("P0DT25H")));
        assertTrue(getLib().durationNotEqual(makeDuration("P1DT1H"), makeDuration("P1DT2H")));
    }

    @Test
    public void testDurationLessThan() {
        assertNull(getLib().durationLessThan(null, null));
        assertNull(getLib().durationLessThan(null, makeDuration("P1Y1M")));
        assertNull(getLib().durationLessThan(makeDuration("P1Y1M"), null));

        assertFalse(getLib().durationLessThan(makeDuration("P1Y1M"), makeDuration("P1Y1M")));
        assertFalse(getLib().durationLessThan(makeDuration("P1Y1M"), makeDuration("P0Y13M")));
        assertTrue(getLib().durationLessThan(makeDuration("P1Y1M"), makeDuration("P1Y2M")));

        assertFalse(getLib().durationLessThan(makeDuration("P1DT1H"), makeDuration("P1DT1H")));
        assertFalse(getLib().durationLessThan(makeDuration("P1DT1H"), makeDuration("P0DT25H")));
        assertTrue(getLib().durationLessThan(makeDuration("P1DT1H"), makeDuration("P1DT2H")));
    }

    @Test
    public void testDurationGreaterThan() {
        assertNull(getLib().durationGreaterThan(null, null));
        assertNull(getLib().durationGreaterThan(null, makeDuration("P1Y1M")));
        assertNull(getLib().durationGreaterThan(makeDuration("P1Y1M"), null));

        assertFalse(getLib().durationGreaterThan(makeDuration("P1Y1M"), makeDuration("P1Y1M")));
        assertFalse(getLib().durationGreaterThan(makeDuration("P1Y1M"), makeDuration("P0Y13M")));
        assertFalse(getLib().durationGreaterThan(makeDuration("P1Y1M"), makeDuration("P1Y2M")));

        assertFalse(getLib().durationGreaterThan(makeDuration("P1DT1H"), makeDuration("P1DT1H")));
        assertFalse(getLib().durationGreaterThan(makeDuration("P1DT1H"), makeDuration("P0DT25H")));
        assertFalse(getLib().durationGreaterThan(makeDuration("P1DT1H"), makeDuration("P1DT2H")));
    }

    @Test
    public void testDurationLessEqualThan() {
        assertTrue(getLib().durationLessEqualThan(null, null));
        assertNull(getLib().durationLessEqualThan(null, makeDuration("P1Y1M")));
        assertNull(getLib().durationLessEqualThan(makeDuration("P1Y1M"), null));

        assertTrue(getLib().durationLessEqualThan(makeDuration("P1Y1M"), makeDuration("P1Y1M")));
        assertTrue(getLib().durationLessEqualThan(makeDuration("P1Y1M"), makeDuration("P0Y13M")));
        assertTrue(getLib().durationLessEqualThan(makeDuration("P1Y1M"), makeDuration("P1Y2M")));

        assertTrue(getLib().durationLessEqualThan(makeDuration("P1DT1H"), makeDuration("P1DT1H")));
        assertTrue(getLib().durationLessEqualThan(makeDuration("P1DT1H"), makeDuration("P0DT25H")));
        assertTrue(getLib().durationLessEqualThan(makeDuration("P1DT1H"), makeDuration("P1DT2H")));
    }

    @Test
    public void testDurationGreaterEqualThan() {
        assertTrue(getLib().durationGreaterEqualThan(null, null));
        assertNull(getLib().durationGreaterEqualThan(null, makeDuration("P1Y1M")));
        assertNull(getLib().durationGreaterEqualThan(makeDuration("P1Y1M"), null));

        assertTrue(getLib().durationGreaterEqualThan(makeDuration("P1Y1M"), makeDuration("P1Y1M")));
        assertTrue(getLib().durationGreaterEqualThan(makeDuration("P1Y1M"), makeDuration("P0Y13M")));
        assertFalse(getLib().durationGreaterEqualThan(makeDuration("P1Y1M"), makeDuration("P1Y2M")));

        assertTrue(getLib().durationGreaterEqualThan(makeDuration("P1DT1H"), makeDuration("P1DT1H")));
        assertTrue(getLib().durationGreaterEqualThan(makeDuration("P1DT1H"), makeDuration("P0DT25H")));
        assertFalse(getLib().durationGreaterEqualThan(makeDuration("P1DT1H"), makeDuration("P1DT2H")));
    }

    @Test
    public void testDurationAdd() {
        assertNull(getLib().durationAdd(null, null));
        assertNull(getLib().durationAdd(null, makeDuration("P1Y1M")));
        assertNull(getLib().durationAdd(makeDuration("P1Y1M"), null));

        assertEqualsDateTime("P2Y2M", getLib().durationAdd(makeDuration("P1Y1M"), makeDuration("P1Y1M")));
        assertEqualsDateTime("P2Y3M", getLib().durationAdd(makeDuration("P1Y1M"), makeDuration("P1Y2M")));
        assertEqualsDateTime("P1Y2M", getLib().durationAdd(makeDuration("P1Y"), makeDuration("P2M")));
        assertEqualsDateTime("-P10M", getLib().durationAdd(makeDuration("-P1Y"), makeDuration("P2M")));
        assertEqualsDateTime("P10M", getLib().durationAdd(makeDuration("P1Y"), makeDuration("-P2M")));
        assertEqualsDateTime("-P1Y2M", getLib().durationAdd(makeDuration("-P1Y"), makeDuration("-P2M")));

        assertEqualsDateTime("P2DT2H", getLib().durationAdd(makeDuration("P1DT1H"), makeDuration("P1DT1H")));
        assertEqualsDateTime("P2DT3H", getLib().durationAdd(makeDuration("P1DT1H"), makeDuration("P1DT2H")));

        assertEqualsDateTime("P380DT8H59M13S", getLib().durationAdd(makeDuration("P13DT2H14S"), getLib().dateTimeSubtract(makeDateAndTime("2016-12-24T23:59:00-08:00"), makeDateAndTime("2015-12-24T00:00:01-01:00"))));

    }

    @Test
    public void testDurationSubtract() {
        assertNull(getLib().durationSubtract(null, null));
        assertNull(getLib().durationSubtract(null, makeDuration("P1Y1M")));
        assertNull(getLib().durationSubtract(makeDuration("P1Y1M"), null));

        assertEqualsDateTime("P0M", getLib().durationSubtract(makeDuration("P1Y1M"), makeDuration("P1Y1M")));
        assertEqualsDateTime("-P1M", getLib().durationSubtract(makeDuration("P1Y1M"), makeDuration("P1Y2M")));
        assertEqualsDateTime("P1Y2M", getLib().durationSubtract(makeDuration("P1Y"), makeDuration("-P2M")));
        assertEqualsDateTime("P10M", getLib().durationSubtract(makeDuration("P1Y"), makeDuration("P2M")));
        assertEqualsDateTime("-P1Y2M", getLib().durationSubtract(makeDuration("-P1Y"), makeDuration("P2M")));
        assertEqualsDateTime("P1Y2M", getLib().durationSubtract(makeDuration("P1Y"), makeDuration("-P2M")));
        assertEqualsDateTime("-P10M", getLib().durationSubtract(makeDuration("-P1Y"), makeDuration("-P2M")));

        assertEqualsDateTime("PT0S", getLib().durationSubtract(makeDuration("P1DT1H"), makeDuration("P1DT1H")));
        assertEqualsDateTime("-PT1H", getLib().durationSubtract(makeDuration("P1DT1H"), makeDuration("P1DT2H")));
    }

    @Test
    public void testDurationMultiply() {
        assertNull(getLib().durationMultiplyNumber(null, null));
        assertNull(getLib().durationMultiplyNumber(null, makeNumber("2")));
        assertNull(getLib().durationMultiplyNumber(makeDuration("P1Y1M"), null));

        assertEquals(makeDuration("P2Y2M"), getLib().durationMultiplyNumber(makeDuration("P1Y1M"),  makeNumber("2")));
        assertEquals(makeDuration("-P2Y2M"), getLib().durationMultiplyNumber(makeDuration("P1Y1M"),  makeNumber("-2")));

        assertEquals(makeDuration("P2DT2H"), getLib().durationMultiplyNumber(makeDuration("P1DT1H"),  makeNumber("2")));
        assertEquals(makeDuration("-P2DT2H"), getLib().durationMultiplyNumber(makeDuration("P1DT1H"),  makeNumber("-2")));
    }

    @Test
    public void testDurationDivide() {
        assertNull(getLib().durationDivideNumber(null, null));
        assertNull(getLib().durationDivideNumber(null, makeNumber("2")));
        assertNull(getLib().durationDivideNumber(makeDuration("P1Y1M"), null));

        assertEquals(makeDuration("P0Y6M"), getLib().durationDivideNumber(makeDuration("P1Y1M"),  makeNumber("2")));
        assertEquals(makeDuration("P1Y1M"), getLib().durationDivideNumber(makeDuration("P2Y2M"),  makeNumber("2")));

        assertEquals(makeDuration("P0DT12H30M"), getLib().durationDivideNumber(makeDuration("P1DT1H"),  makeNumber("2")));
        assertEquals(makeDuration("P1DT1H"), getLib().durationDivideNumber(makeDuration("P2DT2H"),  makeNumber("2")));
    }

    //
    // List operators
    //
    @Test
    public void testIsList() {
        assertFalse(getLib().isList(null));
        assertFalse(getLib().isList(getLib().number("1")));
        assertFalse(getLib().isList("abc"));
        assertFalse(getLib().isList(true));
        assertFalse(getLib().isList(getLib().date("2020-01-01")));
        assertFalse(getLib().isList(getLib().time("12:00:00")));
        assertFalse(getLib().isList(getLib().dateAndTime("2020-01-01T12:00:00")));
        assertFalse(getLib().isList(getLib().duration("P1Y1M")));
        assertTrue(getLib().isList(getLib().asList("a")));
        assertFalse(getLib().isList(new Context()));
        assertFalse(getLib().isList(new Range(true, BigDecimal.ZERO, true, BigDecimal.ONE)));
    }

    @Test
    public void testListIs() {
        assertTrue(getLib().listIs(null, null));
        assertFalse(getLib().listIs(Arrays.asList("a"), null));
        assertFalse(getLib().listIs(null, Arrays.asList("a")));

        assertFalse(getLib().listIs(Arrays.asList("a"), Arrays.asList("b")));
        assertTrue(getLib().listIs(Arrays.asList("a"), Arrays.asList("a")));
    }

    @Test
    public void testListEqual() {
        assertTrue(getLib().listEqual(null, null));
        assertFalse(getLib().listEqual(Arrays.asList("a"), null));
        assertFalse(getLib().listEqual(null, Arrays.asList("a")));

        assertFalse(getLib().listEqual(Arrays.asList("a"), Arrays.asList("b")));
        assertTrue(getLib().listEqual(Arrays.asList("a"), Arrays.asList("a")));
    }

    @Test
    public void testListNotEqual() {
        assertFalse(getLib().listNotEqual(null, null));
        assertTrue(getLib().listNotEqual(Arrays.asList("a"), null));
        assertTrue(getLib().listNotEqual(null, Arrays.asList("a")));

        assertTrue(getLib().listNotEqual(Arrays.asList("a"), Arrays.asList("b")));
        assertFalse(getLib().listNotEqual(Arrays.asList("a"), Arrays.asList("a")));
    }

    //
    // Context operators
    //
    @Test
    public void testIsContext() {
        assertFalse(getLib().isContext(null));
        assertFalse(getLib().isContext(getLib().number("1")));
        assertFalse(getLib().isContext("abc"));
        assertFalse(getLib().isContext(true));
        assertFalse(getLib().isContext(getLib().date("2020-01-01")));
        assertFalse(getLib().isContext(getLib().time("12:00:00")));
        assertFalse(getLib().isContext(getLib().dateAndTime("2020-01-01T12:00:00")));
        assertFalse(getLib().isContext(getLib().dateAndTime("2020-01-01T12:00:00")));
        assertFalse(getLib().isContext(getLib().duration("P1Y1M")));
        assertFalse(getLib().isContext(getLib().asList("a")));
        assertTrue(getLib().isContext(new Context()));
        assertFalse(getLib().isContext(new Range(true, BigDecimal.ZERO, true, BigDecimal.ONE)));
    }

    @Test
    public void testContextValue() {
        Context c1 = new Context();

        assertNull(getLib().contextValue(null));
        assertEquals(c1, getLib().contextValue(c1));
    }

    @Test
    public void testContextIs() {
        Context c1 = new Context().add("m", "a");
        Context c2 = new Context().add("m", "b");
        Context c3 = new Context().add("m", "a");

        assertTrue(getLib().contextIs(null, null));
        assertFalse(getLib().contextIs(c1, null));
        assertFalse(getLib().contextIs(null, c1));

        assertTrue(getLib().contextIs(c1, c1));
        assertFalse(getLib().contextIs(c1, c2));
        assertTrue(getLib().contextIs(c1, c3));
    }

    @Test
    public void testContextEqual() {
        Context c1 = new Context().add("m", "a");
        Context c2 = new Context().add("m", "b");
        Context c3 = new Context().add("m", "a");

        assertTrue(getLib().contextEqual(null, null));
        assertFalse(getLib().contextEqual(c1, null));
        assertFalse(getLib().contextEqual(null, c1));

        assertFalse(getLib().contextEqual(c1, c2));
        assertTrue(getLib().contextEqual(c1, c3));
    }

    @Test
    public void testContextNotEqual() {
        Context c1 = new Context().add("m", "a");
        Context c2 = new Context().add("m", "b");
        Context c3 = new Context().add("m", "a");

        assertFalse(getLib().contextNotEqual(null, null));
        assertTrue(getLib().contextNotEqual(c1, null));
        assertTrue(getLib().contextNotEqual(null, c1));

        assertTrue(getLib().contextNotEqual(c1, c2));
        assertFalse(getLib().contextNotEqual(c1, c3));
    }

    //
    // Range operators
    //
    @Test
    public void testIsRange() {
        assertFalse(getLib().isRange(null));
        assertFalse(getLib().isRange(getLib().number("1")));
        assertFalse(getLib().isRange("abc"));
        assertFalse(getLib().isRange(true));
        assertFalse(getLib().isRange(getLib().date("2020-01-01")));
        assertFalse(getLib().isRange(getLib().time("12:00:00")));
        assertFalse(getLib().isRange(getLib().dateAndTime("2020-01-01T12:00:00")));
        assertFalse(getLib().isRange(getLib().dateAndTime("2020-01-01T12:00:00")));
        assertFalse(getLib().isRange(getLib().duration("P1Y1M")));
        assertFalse(getLib().isRange(getLib().asList("a")));
        assertFalse(getLib().isRange(new Context()));
        assertTrue(getLib().isRange(new Range(true, BigDecimal.ZERO, true, BigDecimal.ONE)));
    }

    @Test
    public void testRangeValue() {
        Range r1 = new Range(true, getLib().number("1"), true, getLib().number("2"));

        assertNull(getLib().rangeValue(null));
        assertEquals(r1, getLib().rangeValue(r1));
    }

    @Test
    public void testRangeIs() {
        Range r1 = new Range(true, getLib().number("1"), true, getLib().number("2"));
        Range r2 = new Range(true, getLib().number("1"), true, getLib().number("3"));
        Range r3 = new Range(true, getLib().number("1"), true, getLib().number("2"));

        assertTrue(getLib().rangeIs(null, null));
        assertFalse(getLib().rangeIs(r1, null));
        assertFalse(getLib().rangeIs(null, r1));

        assertTrue(getLib().rangeIs(r1, r1));
        assertFalse(getLib().rangeIs(r1, r2));
        assertTrue(getLib().rangeIs(r1, r3));
    }

    @Test
    public void testRangeEqual() {
        Range r1 = new Range(true, getLib().number("1"), true, getLib().number("2"));
        Range r2 = new Range(true, getLib().number("1"), true, getLib().number("3"));
        Range r3 = new Range(true, getLib().number("1"), true, getLib().number("2"));

        assertTrue(getLib().rangeEqual(null, null));
        assertFalse(getLib().rangeEqual(r1, null));
        assertFalse(getLib().rangeEqual(null, r1));

        assertFalse(getLib().rangeEqual(r1, r2));
        assertTrue(getLib().rangeEqual(r1, r3));
    }

    @Test
    public void testRangeNotEqual() {
        Range r1 = new Range(true, getLib().number("1"), true, getLib().number("2"));
        Range r2 = new Range(true, getLib().number("1"), true, getLib().number("3"));
        Range r3 = new Range(true, getLib().number("1"), true, getLib().number("2"));

        assertFalse(getLib().rangeNotEqual(null, null));
        assertTrue(getLib().rangeNotEqual(r1, null));
        assertTrue(getLib().rangeNotEqual(null, r1));

        assertTrue(getLib().rangeNotEqual(r1, r2));
        assertFalse(getLib().rangeNotEqual(r1, r3));
    }

    //
    // Function operators
    //
    @Test
    public void testIsFunction() {
        assertTrue(getLib().isFunction(null));
        assertFalse(getLib().isFunction(getLib().number("1")));
        assertFalse(getLib().isFunction("abc"));
        assertFalse(getLib().isFunction(true));
        assertFalse(getLib().isFunction(getLib().date("2020-01-01")));
        assertFalse(getLib().isFunction(getLib().time("12:00:00")));
        assertFalse(getLib().isFunction(getLib().dateAndTime("2020-01-01T12:00:00")));
        assertFalse(getLib().isFunction(getLib().dateAndTime("2020-01-01T12:00:00")));
        assertFalse(getLib().isFunction(getLib().duration("P1Y1M")));
        assertFalse(getLib().isFunction(getLib().asList("a")));
        assertFalse(getLib().isFunction(new Context()));
        assertFalse(getLib().isFunction(new Range(true, BigDecimal.ZERO, true, BigDecimal.ONE)));
    }

    @Test
    public void testFunctionValue() {
        assertNull(getLib().functionValue(null));
        assertNull(getLib().functionValue("a"));
    }

    @Test
    public void testFunctionIs() {
        assertTrue(getLib().functionIs(null, null));
        assertNull(getLib().functionIs("a", null));
        assertNull(getLib().functionIs(null, "b"));

        assertNull(getLib().functionIs("a", "b"));
        assertNull(getLib().functionIs("b", "b"));
    }

    @Test
    public void testFunctionEqual() {
        assertTrue(getLib().functionEqual(null, null));
        assertNull(getLib().functionEqual("a", null));
        assertNull(getLib().functionEqual(null, "b"));

        assertNull(getLib().functionEqual("a", "b"));
        assertNull(getLib().functionEqual("b", "b"));
    }

    @Test
    public void testFunctionNotEqual() {
        assertFalse(getLib().functionNotEqual(null, null));
        assertNull(getLib().functionNotEqual("a", null));
        assertNull(getLib().functionNotEqual(null, "b"));

        assertNull(getLib().functionNotEqual("a", "b"));
        assertNull(getLib().functionNotEqual("b", "b"));
    }

    protected NUMBER makeNumber(String literal) {
        return getLib().number(literal);
    }

    protected NUMBER makeNumber(long number) {
        return makeNumber(Long.valueOf(number));
    }

    protected NUMBER makeNumber(Long number) {
        return number == null ? null : getLib().number(number.toString());
    }

    protected NUMBER makeNumber(double number) {
        return makeNumber(Double.valueOf(number));
    }

    protected NUMBER makeNumber(Double number) {
        return number == null ? null : getLib().number(number.toString());
    }

    protected List<NUMBER> makeNumberList(Object... numbers) {
        List result = new ArrayList();
        for(Object num: numbers) {
            result.add(makeNumber(String.format("%s", num)));
        }
        return result;
    }

    protected DATE makeDate(String literal) {
        return getLib().date(literal);
    }

    protected TIME makeTime(String literal) {
        return getLib().time(literal);
    }

    protected DATE_TIME makeDateAndTime(String literal) {
        return getLib().dateAndTime(literal);
    }

    protected DURATION makeDuration(String literal) {
        return getLib().duration(literal);
    }

    protected void assertEqualsNumber(String expected, Object actual) {
        if (actual instanceof Number) {
            assertEquals(expected, actual.toString());
        }
    }

    protected void assertEqualsNumber(NUMBER expected, Object actual) {
        Assert.assertEquals(expected, actual);
    }

    protected void assertEqualsNumber(double expected, Object actual, double precision) {
        if (actual instanceof BigDecimal) {
            assertEquals(expected, ((BigDecimal) actual).doubleValue(), precision);
        } else if (actual instanceof Double) {
            assertEquals(expected, (Double) actual, precision);
        } else {
            assertEquals(expected, actual);
        }
    }

    protected void assertEqualsDateTime(String expected, Object actual) {
        if (actual instanceof XMLGregorianCalendar) {
            assertEquals(expected, actual.toString());
        } else if (actual instanceof TemporalAccessor) {
            String actualText = FormatUtils.formatTemporal(actual);
            assertEquals(expected, actualText);
        } else if (actual instanceof Duration) {
            Duration expectedDuration = null;
            try {
                expectedDuration = XMLDurationFactory.INSTANCE.parse(expected);
            } catch (Exception e) {
                fail("Cannot parse expected Duration");
            }
            assertEquals(expectedDuration, actual);
        } else if (actual instanceof java.time.Duration) {
            String actualText = FormatUtils.formatTemporal(actual);
            assertEquals(expected, actualText);
        } else if (actual instanceof java.time.Period) {
            String actualText = FormatUtils.formatTemporal(actual);
            assertEquals(expected, actualText);
        } else {
            assertEquals(expected, actual);
        }
    }

    protected void assertEqualsList(String expected, Object actual) {
        Assert.assertEquals(expected, actual.toString());
    }
}