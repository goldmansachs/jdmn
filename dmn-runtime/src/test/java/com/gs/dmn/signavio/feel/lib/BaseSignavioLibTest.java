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

import com.gs.dmn.runtime.Context;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public abstract class BaseSignavioLibTest<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends BaseFEELLibTest<NUMBER, DATE, TIME, DATE_TIME, DURATION> {
    @Override
    protected abstract SignavioLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> getLib();

    //
    // Data acceptance functions
    //
    @Test
    public void testIsPredicates() {
        assertFalse(getLib().isDefined(null));
        assertTrue(getLib().isUndefined(null));

        assertFalse(getLib().isValid(null));
        assertTrue(getLib().isInvalid(null));
    }

    //
    // Arithmetic operations
    //
    @Test
    public void testAbs() {
        assertNull(getLib().abs(null));

        assertEqualsNumber(makeNumber("123"), getLib().abs(makeNumber("123")));
        assertEqualsNumber(123.45, getLib().abs(makeNumber("123.45")), 0.001);
        assertEqualsNumber(makeNumber("123"), getLib().abs(makeNumber("-123")));
        assertEqualsNumber(123.45, getLib().abs(makeNumber("-123.45")), 0.001);
    }

    @Test
    public void testRound() {
        assertNull(getLib().round(null, null));
        assertNull(getLib().round(makeNumber("3.44"), null));
        assertNull(getLib().round(null, makeNumber("1")));

        assertEqualsNumber(2.23, getLib().round(makeNumber("2.234"), makeNumber("2")), 0.0001);
        assertEqualsNumber(2.24, getLib().round(makeNumber("2.235"), makeNumber("2")), 0.0001);
        assertEqualsNumber(2.24, getLib().round(makeNumber("2.236"), makeNumber("2")), 0.0001);

        assertEqualsNumber(99.4, getLib().round(makeNumber("99.444"), makeNumber("1")), 0.0001);
        assertEqualsNumber(99, getLib().round(makeNumber("99.444"), makeNumber("0")), 0.0001);
        assertEqualsNumber(100, getLib().round(makeNumber("99.444"), makeNumber("-1")), 0.0001);
        assertEqualsNumber(100, getLib().round(makeNumber("99.444"), makeNumber("-2")), 0.0001);
        assertEqualsNumber(0, getLib().round(makeNumber("99.444"), makeNumber("-3")), 0.0001);
        assertEqualsNumber(0, getLib().round(makeNumber("99.444"), makeNumber("-4")), 0.0001);
    }

    @Test
    public void testInteger() {
        assertNull(getLib().integer(null));

        assertEqualsNumber(makeNumber("1"), getLib().integer(makeNumber("1")));
        assertEqualsNumber(makeNumber("1"), getLib().integer(makeNumber("1.2")));
        assertEqualsNumber(makeNumber("1"), getLib().integer(makeNumber("1.4")));
        assertEqualsNumber(makeNumber("1"), getLib().integer(makeNumber("1.5")));
        assertEqualsNumber(makeNumber("1"), getLib().integer(makeNumber("1.6")));

        assertEqualsNumber(makeNumber("-1"), getLib().integer(makeNumber("-1")));
        assertEqualsNumber(makeNumber("-1"), getLib().integer(makeNumber("-1.2")));
        assertEqualsNumber(makeNumber("-1"), getLib().integer(makeNumber("-1.4")));
        assertEqualsNumber(makeNumber("-1"), getLib().integer(makeNumber("-1.5")));
        assertEqualsNumber(makeNumber("-1"), getLib().integer(makeNumber("-1.6")));
    }

    @Test
    public void testModulo() {
        assertNull(getLib().modulo(null, null));
        assertNull(getLib().modulo(null, makeNumber("1")));
        assertNull(getLib().modulo(makeNumber("1"), null));

        assertEqualsNumber(makeNumber("0"), getLib().modulo(makeNumber("4"), makeNumber("2")));
        assertEqualsNumber(makeNumber("1"), getLib().modulo(makeNumber("4"), makeNumber("3")));
        assertEqualsNumber(makeNumber("1"), getLib().modulo(makeNumber("4"), makeNumber("-3")));
        assertEqualsNumber(makeNumber("-1"), getLib().modulo(makeNumber("-4"), makeNumber("3")));
        assertEqualsNumber(makeNumber("-1"), getLib().modulo(makeNumber("-4"), makeNumber("-3")));

        assertEqualsNumber(makeNumber("0.895"), getLib().modulo(makeNumber("4.125"), makeNumber("3.23")));
        assertEqualsNumber(makeNumber("0.895"), getLib().modulo(makeNumber("4.125"), makeNumber("-3.23")));
        assertEqualsNumber(makeNumber("-0.895"), getLib().modulo(makeNumber("-4.125"), makeNumber("3.23")));
        assertEqualsNumber(makeNumber("-0.895"), getLib().modulo(makeNumber("-4.125"), makeNumber("-3.23")));
    }

    @Test
    public void testPercent() {
        assertNull(getLib().percent(null));

        assertEqualsNumber(0.1, getLib().percent(makeNumber("10")), 0.01);
        assertEqualsNumber(0.101, getLib().percent(makeNumber("10.1")), 0.01);
        assertEqualsNumber(1, getLib().percent(makeNumber("100")), 0.01);
        assertEqualsNumber(0.001, getLib().percent(makeNumber("0.1")), 0.01);
        assertEqualsNumber(-0.03, getLib().percent(makeNumber("-3")), 0.01);
    }

    @Test
    public void testPower() {
        assertNull(getLib().power(null, null));
        assertNull(getLib().power(null, makeNumber("3")));
        assertNull(getLib().power(makeNumber("3"), null));

        assertEqualsNumber(makeNumber("1"), getLib().numericExponentiation(makeNumber("3"), makeNumber("0")));

        assertEqualsNumber(makeNumber("8"), getLib().power(makeNumber("2"), makeNumber("3")));
        assertEqualsNumber(makeNumber("4"), getLib().power(makeNumber("2"), makeNumber("2.5")));
        assertEqualsNumber(makeNumber("6.25"), getLib().power(makeNumber("2.5"), makeNumber("2")));
        assertEqualsNumber(makeNumber("0.5"), getLib().power(makeNumber("2"), makeNumber("-1")));
        assertEqualsNumber(makeNumber("0.25"), getLib().power(makeNumber("2"), makeNumber("-2.5")));
        assertEqualsNumber(makeNumber("0.25"), getLib().power(makeNumber("2"), makeNumber("-2.3")));

        assertEqualsNumber(makeNumber("4"), getLib().power(makeNumber("-2"), makeNumber("2.3")));
    }

    @Test
    public void testProduct() {
        assertNull(getLib().product(null));
        assertNull(getLib().product(makeNumberList()));

        assertEqualsNumber(makeNumber("2"), getLib().product(makeNumberList(2)));
        assertEqualsNumber(makeNumber("24"), getLib().product(makeNumberList(2, 3, 4)));
        assertNull(getLib().product(makeNumberList(1, null, 3)));
    }

    @Test
    public void testRoundDown() {
        assertNull(getLib().roundDown(null, null));
        assertNull(getLib().roundDown(null, makeNumber("2")));
        assertNull(getLib().roundDown(makeNumber("2"), null));

        assertEqualsNumber(1.34, getLib().roundDown(makeNumber("1.344"), makeNumber("2")), 0.001);
        assertEqualsNumber(1.33, getLib().roundDown(makeNumber("1.333"), makeNumber("2")), 0.001);
        assertEqualsNumber(0, getLib().roundDown(makeNumber("1.344"), makeNumber("-1")), 0.001);
        assertEqualsNumber(0, getLib().roundDown(makeNumber("1.344"), makeNumber("-2")), 0.001);
    }

    @Test
    public void testRoundUp() {
        assertNull(getLib().roundUp(null, null));
        assertNull(getLib().roundUp(null, makeNumber("2")));
        assertNull(getLib().roundUp(makeNumber("2"), null));

        assertEqualsNumber(1.35, getLib().roundUp(makeNumber("1.344"), makeNumber("2")), 0.001);
        assertEqualsNumber(1.34, getLib().roundUp(makeNumber("1.333"), makeNumber("2")), 0.001);
        assertEqualsNumber(10, getLib().roundUp(makeNumber("1.344"), makeNumber("-1")), 0.001);
        assertEqualsNumber(100, getLib().roundUp(makeNumber("1.344"), makeNumber("-2")), 0.001);
    }

    //
    // Date and time
    //
    @Override
    @Test
    public void testDate() {
        assertNull(getLib().date(makeNumber("2016"), null, null));
        assertNull(getLib().date(null, makeNumber("8"), null));
        assertNull(getLib().date(null, null, makeNumber("1")));
        assertNull(getLib().date(makeNumber("-2016"), makeNumber("8"), makeNumber("1")));
        assertNull(getLib().date(makeNumber("2016"), makeNumber("-8"), makeNumber("1")));
        assertNull(getLib().date(makeNumber("2016"), makeNumber("8"), makeNumber("-1")));

        assertEqualsDateTime("2016-08-01", getLib().date(makeNumber("2016"), makeNumber("8"), makeNumber("1")));
    }

    @Test
    public void testDateTime() {
        assertNull(getLib().dateTime(null, null, null, null, null, null));
        assertNull(getLib().dateTime(null, null, null, null, null, null, null));

        assertNull(getLib().dateTime(makeNumber(-1), makeNumber(-1), makeNumber(-1), makeNumber(-1), makeNumber(-1), makeNumber(-1)));
        assertNull(getLib().dateTime(makeNumber(-1), makeNumber(-1), makeNumber(-1), makeNumber(-1), makeNumber(-1), makeNumber(-1), makeNumber(-1)));

        assertEqualsDateTime("2000-01-02T03:04:05Z", getLib().dateTime(makeNumber("2"), makeNumber("1"), makeNumber("2000"), makeNumber("3"), makeNumber("4"), makeNumber("5")));
        assertEqualsDateTime("2000-01-02T03:04:05+06:00", getLib().dateTime(makeNumber("2"), makeNumber("1"), makeNumber("2000"), makeNumber("3"), makeNumber("4"), makeNumber("5"), makeNumber("6")));
    }


    //
    // Date properties
    //
    @Test
    public void testDateProperties() {
        assertEqualsNumber(getLib().number("2018"), getLib().year(getLib().date("2018-12-10")));
        assertEqualsNumber(getLib().number("12"), getLib().month(getLib().date("2018-12-10")));
        assertEqualsNumber(getLib().number("10"), getLib().day(getLib().date("2018-12-10")));
        assertEqualsNumber(getLib().number("1"), getLib().weekday(getLib().date("2018-12-10")));
    }

    //
    // Time properties
    //
    @Test
    public void testTimeProperties() {
        // See each implementation
    }

    //
    // Date and time properties
    //
    @Test
    public void testDateAndTimeProperties() {
        // See each implementation
    }

    //
    // Duration properties
    //
    @Test
    public void testDurationProperties() {
        assertEqualsNumber(getLib().number("1"), getLib().years(getLib().duration("P1Y2M")));
        assertEqualsNumber(getLib().number("2"), getLib().months(getLib().duration("P1Y2M")));

        assertEqualsNumber(getLib().number("3"), getLib().days(getLib().duration("P1Y2M3DT4H5M6.700S")));
        assertEqualsNumber(getLib().number("4"), getLib().hours(getLib().duration("P1Y2M3DT4H5M6.700S")));
        assertEqualsNumber(getLib().number("5"), getLib().minutes(getLib().duration("P1Y2M3DT4H5M6.700S")));
        assertEqualsNumber(getLib().number("6"), getLib().seconds(getLib().duration("P1Y2M3DT4H5M6.700S")));
    }

    //
    // List operations
    //
    @Test
    public void testAppend() {
        assertEquals(Arrays.asList(new Object[] {null}), getLib().append(null, null));
        assertEquals(Arrays.asList("1", "2", null), getLib().append(Arrays.asList("1", "2"), null));
        assertEquals(Arrays.asList("1", "2", "3"), getLib().append(Arrays.asList("1", "2"), "3"));
    }

    @Test
    public void testAppendAll() {
        assertEquals(Arrays.asList(), getLib().appendAll(null, null));
        assertEquals(Arrays.asList(), getLib().appendAll(null, Arrays.asList()));
        assertEquals(Arrays.asList(), getLib().appendAll(Arrays.asList(), null));
        assertEquals(Arrays.asList(), getLib().appendAll(Arrays.asList(), Arrays.asList()));

        assertEquals(Arrays.asList("1", "2"), getLib().appendAll(Arrays.asList("1", "2"), null));
        assertEquals(Arrays.asList("1", "2"), getLib().appendAll(null, Arrays.asList("1", "2")));

        assertEquals(Arrays.asList("1", "2", "3", "4"), getLib().appendAll(Arrays.asList("1", "2"), Arrays.asList("3", "4")));
    }

    @Test
    public void testZip() {
        assertEquals(Arrays.asList(), getLib().zip(null, null));
        assertEquals(Arrays.asList(), getLib().zip(null, Arrays.asList()));
        assertEquals(Arrays.asList(), getLib().zip(Arrays.asList(), Arrays.asList()));
        assertEquals(Arrays.asList(), getLib().zip(Arrays.asList(), Arrays.asList(null, null)));
        assertEquals(Arrays.asList(), getLib().zip(Arrays.asList(), Arrays.asList(null, Arrays.asList())));
        assertEquals(Arrays.asList(), getLib().zip(Arrays.asList(), Arrays.asList(Arrays.asList(), null)));

        assertEquals(Arrays.asList(),
                getLib().zip(Arrays.asList(), Arrays.asList(Arrays.asList(), Arrays.asList())));

        List<String> attributes = Arrays.asList("k1", "k2");
        assertEquals(Arrays.asList(
                    new Context().add("k1", null).add("k2", "2"),
                    new Context().add("k1", null).add("k2", "4")
                ),
                getLib().zip(attributes, Arrays.asList(Arrays.asList(), Arrays.asList("2", "4"))));
        assertEquals(Arrays.asList(
                    new Context().add("k1", "1").add("k2", null),
                    new Context().add("k1", "3").add("k2", null)
                ),
                getLib().zip(attributes, Arrays.asList(Arrays.asList("1", "3"), Arrays.asList())));
        assertEquals(Arrays.asList(
                    new Context().add("k1", "1").add("k2", "2"),
                    new Context().add("k1", "3").add("k2", "4")
                ),
                getLib().zip(attributes, Arrays.asList(Arrays.asList("1", "3"), Arrays.asList("2", "4"))));
        assertEquals(Arrays.asList(
                    new Context().add("k1", "1").add("k2", "2"),
                    new Context().add("k1", "3").add("k2", null)
                ),
                getLib().zip(attributes, Arrays.asList(Arrays.asList("1", "3"), Arrays.asList("2"))));
        assertEquals(Arrays.asList(
                    new Context().add("k1", "1").add("k2", "2"),
                    new Context().add("k1", null).add("k2", "4")
                    ),
                getLib().zip(attributes, Arrays.asList(Arrays.asList("1"), Arrays.asList("2", "4"))));
    }

    @Test
    public void testRemove() {
        assertEquals(Arrays.asList(), getLib().remove(null, null));
        assertEquals(Arrays.asList(), getLib().remove(null, "1"));

        assertEquals(Arrays.asList(), getLib().remove(Arrays.asList(), null));
        assertEquals(Arrays.asList(), getLib().remove(Arrays.asList(), "1"));

        assertEquals(Arrays.asList("1", "2"), getLib().remove(Arrays.asList("1", "2"), null));
        assertEquals(Arrays.asList("2"), getLib().remove(Arrays.asList("1", "2"), "1"));
        assertEquals(Arrays.asList("1"), getLib().remove(Arrays.asList("2", "1", "2", "2"), "2"));
        assertEquals(Arrays.asList("1", "2"), getLib().remove(Arrays.asList("1", "2"), "3"));
    }

    @Test
    public void testRemoveAll() {
        assertEquals(Arrays.asList(), getLib().removeAll(null, null));
        assertEquals(Arrays.asList(), getLib().removeAll(null, Arrays.asList()));
        assertEquals(Arrays.asList(), getLib().removeAll(null, Arrays.asList("1", "2")));

        assertEquals(Arrays.asList(), getLib().removeAll(Arrays.asList(), null));
        assertEquals(Arrays.asList(), getLib().removeAll(Arrays.asList(), Arrays.asList()));
        assertEquals(Arrays.asList(), getLib().removeAll(Arrays.asList(), Arrays.asList("1", "2")));

        assertEquals(Arrays.asList(), getLib().removeAll(Arrays.asList("1", "2"), null));
        assertEquals(Arrays.asList("1", "2"), getLib().removeAll(Arrays.asList("1", "2"), Arrays.asList()));
        assertEquals(Arrays.asList("1", "2"), getLib().removeAll(Arrays.asList("1", "2"), Arrays.asList("3")));
    }

    @Test
    public void testNotContainsAny() {
        assertNull(getLib().notContainsAny(null, null));
        assertNull(getLib().notContainsAny(Arrays.asList("1", "2"), null));
        assertNull(getLib().notContainsAny(null, Arrays.asList("1", "2")));

        assertTrue(getLib().notContainsAny(Arrays.asList(), Arrays.asList()));
        assertTrue(getLib().notContainsAny(Arrays.asList("1", "2"), Arrays.asList()));
        assertTrue(getLib().notContainsAny(Arrays.asList(), Arrays.asList("1", "2")));

        assertFalse(getLib().notContainsAny(Arrays.asList("1", "2"), Arrays.asList("1", "4")));
        assertTrue(getLib().notContainsAny(Arrays.asList("1", "2"), Arrays.asList("3", "4")));
    }

    @Test
    public void testContainsOnly() {
        assertNull(getLib().containsOnly(null, null));
        assertNull(getLib().containsOnly(null, Arrays.asList()));
        assertNull(getLib().containsOnly(null, Arrays.asList("1", "2")));

        assertNull(getLib().containsOnly(Arrays.asList(), null));
        assertTrue(getLib().containsOnly(Arrays.asList(), Arrays.asList()));
        assertTrue(getLib().containsOnly(Arrays.asList(), Arrays.asList("1", "2")));

        assertNull(getLib().containsOnly(Arrays.asList("1", "2"), null));
        assertFalse(getLib().containsOnly(Arrays.asList("1", "2"), Arrays.asList()));
        assertTrue(getLib().containsOnly(Arrays.asList("1", "2"), Arrays.asList("1", "2")));
        assertFalse(getLib().containsOnly(Arrays.asList("1", "2"), Arrays.asList("3", "4")));
    }

    @Test
    public void testAreElementsOf() {
        assertNull(getLib().areElementsOf(null, null));
        assertNull(getLib().areElementsOf(null, Arrays.asList()));
        assertNull(getLib().areElementsOf(null, Arrays.asList("1", "2")));

        assertNull(getLib().areElementsOf(Arrays.asList(), null));
        assertTrue(getLib().areElementsOf(Arrays.asList(), Arrays.asList()));
        assertTrue(getLib().areElementsOf(Arrays.asList(), Arrays.asList("1", "2")));

        assertNull(getLib().areElementsOf(Arrays.asList("1", "2"), null));
        assertFalse(getLib().areElementsOf(Arrays.asList("1", "2"), Arrays.asList()));
        assertTrue(getLib().areElementsOf(Arrays.asList("1", "2"), Arrays.asList("1", "2")));
        assertFalse(getLib().areElementsOf(Arrays.asList("1", "2"), Arrays.asList("1", "3")));
    }

    @Test
    public void testElementOf() {
        assertNull(getLib().elementOf(null, null));
        assertNull(getLib().elementOf(null, Arrays.asList()));
        assertNull(getLib().elementOf(null, Arrays.asList("1", "2")));

        assertNull(getLib().elementOf(Arrays.asList(), null));
        assertTrue(getLib().elementOf(Arrays.asList(), Arrays.asList()));
        assertTrue(getLib().elementOf(Arrays.asList(), Arrays.asList("1", "2")));

        assertNull(getLib().elementOf(Arrays.asList("1", "2"), null));
        assertFalse(getLib().elementOf(Arrays.asList("1", "2"), Arrays.asList()));
        assertTrue(getLib().elementOf(Arrays.asList("1", "2"), Arrays.asList("1", "2")));
        assertFalse(getLib().elementOf(Arrays.asList("1", "2"), Arrays.asList("1", "3")));
    }

    //
    // Statistical operations
    //
    @Test
    public void testAvg() {
        assertNull(getLib().avg(null));
        assertNull(getLib().avg(Arrays.asList()));

        assertEqualsNumber(makeNumber("2"), getLib().avg(Arrays.asList(makeNumber("1"), makeNumber("3"))));
        assertNull(getLib().avg(Arrays.asList(makeNumber("1"), null)));
    }

    @Test
    public void testMedian() {
        assertNull(getLib().median(null));
        assertNull(getLib().median(Arrays.asList()));

        assertEqualsNumber(makeNumber("2"), getLib().median(Arrays.asList(makeNumber("1"), makeNumber("3"))));
        assertEqualsNumber(makeNumber("3"), getLib().median(Arrays.asList(makeNumber("1"), makeNumber("5"), makeNumber("3"))));
        assertNull(getLib().median(Arrays.asList(makeNumber("1"), null)));
    }

    @Test
    public void testMode() {
        assertNull(getLib().mode(null));
        assertNull(getLib().mode(Arrays.asList()));

        assertEqualsNumber(makeNumber("1"), getLib().mode(Arrays.asList(makeNumber("1"), makeNumber("3"))));
        assertEqualsNumber(makeNumber("3"), getLib().mode(Arrays.asList(makeNumber("1"), makeNumber("3"), makeNumber("3"))));
        assertNull(getLib().mode(Arrays.asList(makeNumber("1"), null)));
    }

    //
    // String functions
    //
    @Override
    @Test
    public void testStringAdd() {
        assertEquals("", getLib().stringAdd(null, null));
        assertEquals("a", getLib().stringAdd("a", null));
        assertEquals("b", getLib().stringAdd(null, "b"));
        assertEquals("ab", getLib().stringAdd("a", "b"));
        assertEquals("ba", getLib().stringAdd("b", "a"));
    }

    @Test
    public void testConcat() {
        assertNull(getLib().concat(null));
        assertNull(getLib().concat(Arrays.asList()));

        assertEquals("123", getLib().concat(Arrays.asList("1", "2", "3")));
        assertNull(getLib().concat(Arrays.asList("1", null, "3")));
    }

    @Test
    public void testIsAlpha() {
        assertNull(getLib().isAlpha(null));

        assertFalse(getLib().isAlpha(""));
        assertTrue(getLib().isAlpha("abc"));
        assertFalse(getLib().isAlpha("abc1"));
        assertFalse(getLib().isAlpha("+-"));
    }

    @Test
    public void testIsAlphaNumeric() {
        assertNull(getLib().isAlphanumeric(null));

        assertFalse(getLib().isAlphanumeric(""));
        assertTrue(getLib().isAlphanumeric("abc"));
        assertTrue(getLib().isAlphanumeric("abc1"));
        assertTrue(getLib().isAlphanumeric("123"));
        assertFalse(getLib().isAlphanumeric("+-"));
    }

    @Test
    public void testIsNumeric() {
        assertNull(getLib().isNumeric(null));

        assertFalse(getLib().isNumeric(""));
        assertFalse(getLib().isNumeric("abc"));
        assertFalse(getLib().isNumeric("abc1"));
        assertTrue(getLib().isNumeric("123"));
        assertFalse(getLib().isNumeric("+-"));
    }

    @Test
    public void testIsSpaces() {
        assertNull(getLib().isSpaces(null));

        assertFalse(getLib().isSpaces(""));
        assertTrue(getLib().isSpaces("  \t\n\f"));
        assertFalse(getLib().isSpaces("abc1"));
        assertFalse(getLib().isSpaces("123"));
        assertFalse(getLib().isSpaces("+-"));
    }

    @Test
    public void testLen() {
        assertNull(getLib().len(null));

        assertEqualsNumber(makeNumber("0"), getLib().len(""));
        assertEqualsNumber(makeNumber("4"), getLib().len("abc1"));
    }

    @Test
    public void testLower() {
        assertNull(getLib().lower(null));

        assertEquals("", getLib().lower(""));
        assertEquals("abc1", getLib().lower("aBc1"));
    }

    @Test
    public void testTrim() {
        assertNull(getLib().trim(null));

        assertEquals("", getLib().trim(""));
        assertEquals("abc", getLib().trim(" abc "));
        assertEquals("ab c1", getLib().trim("ab c1"));
    }

    @Test
    public void testUpper() {
        assertNull(getLib().upper(null));

        assertEquals("", getLib().upper(""));
        assertEquals("ABC1", getLib().upper("aBc1"));
    }

    @Test
    public void testNumberWithDefault() {
        assertNull(getLib().number(null, null));
        assertNull(getLib().number(null, makeNumber("123.56")));
        assertNull(getLib().number("123.56", null));

        assertEqualsNumber(123, getLib().number("123", makeNumber("123.56")), 0.001);
        assertEqualsNumber(123.56, getLib().number("1,200", makeNumber("123.56")), 0.001);
        assertEqualsNumber(123.56, getLib().number("xxx", makeNumber("123.56")), 0.001);
    }

    @Test
    public void testMid() {
        assertNull(getLib().mid(null, null, null));
        assertNull(getLib().mid("123", null, null));
        assertNull(getLib().mid(null, null, makeNumber("1")));
        assertNull(getLib().mid(null, makeNumber("1"), null));
        assertNull(getLib().mid("123", makeNumber("-1"), makeNumber("1")));
        assertNull(getLib().mid("123", makeNumber("1"), makeNumber("-1")));

        assertEquals("2", getLib().mid("123", makeNumber("1"), makeNumber("1")));
        assertEquals("23", getLib().mid("123", makeNumber("1"), makeNumber("5")));
        assertEquals("3", getLib().mid("123", makeNumber("2"), makeNumber("1")));
        assertNull(getLib().mid("123", makeNumber("3"), makeNumber("1")));
    }

    @Test
    public void testLeft() {
        assertNull(getLib().left(null, null));
        assertNull(getLib().left(null, makeNumber("1")));
        assertNull(getLib().left("123", null));
        assertNull(getLib().left("123", makeNumber("-1")));

        assertEquals("1", getLib().left("123", makeNumber("1")));
        assertEquals("12", getLib().left("123", makeNumber("2")));
        assertEquals("123", getLib().left("123", makeNumber("3")));
        assertEquals("123", getLib().left("123", makeNumber("100")));
    }

    @Test
    public void testRight() {
        assertNull(getLib().right(null, null));
        assertNull(getLib().right(null, makeNumber("1")));
        assertNull(getLib().right("123", null));
        assertNull(getLib().right("123", makeNumber("-1")));

        assertEquals("3", getLib().right("123", makeNumber("1")));
        assertEquals("23", getLib().right("123", makeNumber("2")));
        assertEquals("123", getLib().right("123", makeNumber("7")));
    }

    @Test
    public void testText() {
        assertNull(getLib().text(null, null));
        assertNull(getLib().text(null, "#.000"));
        assertNull(getLib().text(makeNumber("1"), null));

        assertEquals("1.000", getLib().text(makeNumber("1"), "#.000"));
        assertEquals("001", getLib().text(makeNumber("1"), "#,000"));
        assertEquals("1,999.00", getLib().text(makeNumber("1999"), "#0,000.00"));
        assertEquals("X1", getLib().text(makeNumber("1"), "X"));
    }

    @Test
    public void testTextOccurrences() {
        assertNull(getLib().textOccurrences(null, null));
        assertNull(getLib().textOccurrences(null, ""));
        assertNull(getLib().textOccurrences("", null));

        assertEqualsNumber(makeNumber("0"), getLib().textOccurrences("can", "abc "));
        assertEqualsNumber(makeNumber("6"), getLib().textOccurrences("can", "Can you can a can as a canner can can a can?"));
    }

    //
    // Test boolean functions
    //
    @Test
    public void testNot() {
        assertNull(getLib().not(null));
        assertFalse(getLib().not(Boolean.TRUE));
        assertTrue(getLib().not(Boolean.FALSE));
    }
}
