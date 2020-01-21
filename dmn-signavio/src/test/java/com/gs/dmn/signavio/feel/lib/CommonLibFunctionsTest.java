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

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public abstract class CommonLibFunctionsTest<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends FEELOperatorsTest<NUMBER, DATE, TIME, DATE_TIME, DURATION> {
    //
    // Constructors
    //
    @Test
    public void testNumber() {
        assertNull(getLib().number(null));
        assertNull(getLib().number("1,200.56"));
        assertNull(getLib().number("xxx"));

        assertEquals("123.56", getLib().number("123.56").toString());
        assertEquals("-123.56", getLib().number("-123.56").toString());
    }

    @Test
    public void testDate()  {
        assertNull(getLib().date((String)null));
        assertNull(getLib().date(""));
        assertNull(getLib().date("xxx"));

        assertNull("2017-08-25", getLib().date("2017-08-25T11:00:00"));

        assertEqualsTime("2016-08-01", getLib().date("2016-08-01"));
        assertEqualsTime("2016-08-01", getLib().date("2016-08-01"));

        assertEqualsTime("2016-08-01", getLib().date(makeDateAndTime("2016-08-01T12:00:00Z")));

        assertNull(getLib().date(makeNumber("2016"), null, null));
        assertNull(getLib().date(null, makeNumber("8"), null));
        assertNull(getLib().date(null, null, makeNumber("1")));
        assertNull(getLib().date(makeNumber("-2016"), makeNumber("8"), makeNumber("1")));
        assertNull(getLib().date(makeNumber("2016"), makeNumber("-8"), makeNumber("1")));
        assertNull(getLib().date(makeNumber("2016"), makeNumber("8"), makeNumber("-1")));
        assertEqualsTime("2016-08-01", getLib().date(makeNumber("2016"), makeNumber("8"), makeNumber("1")));

        assertNull(getLib().date((String) null));
    }

    @Test
    public void testTime()  {
        assertNull(getLib().time((String)null));
        assertNull(getLib().time(""));
        assertNull(getLib().time("xxx"));
        assertNull(getLib().time("13:20:00+01:00@Europe/Paris"));
        assertNull(getLib().time("13:20:00+00:00[UTC]"));
        assertNull(getLib().time(
                makeNumber("12"), makeNumber("00"), makeNumber("00"),
                makeDuration("PT25H10M")));

        // Fix input literal
        assertEqualsTime("11:00:00Z", getLib().time("T11:00:00Z"));
        assertEqualsTime("11:00:00+01:00", getLib().time("11:00:00+0100"));

        assertEqualsTime("11:00:00Z", getLib().time("11:00:00Z"));
        assertEqualsTime("11:00:00.001Z", getLib().time("11:00:00.001Z"));

        assertEqualsTime("11:00:00.001+01:00", getLib().time("11:00:00.001+01:00"));
        assertEqualsTime("11:00:00+01:00", getLib().time("11:00:00+01:00"));

        assertEqualsTime("11:00:00Z", getLib().time(makeDateAndTime("2016-08-01T11:00:00Z")));

        assertEqualsTime("12:00:00+01:10", getLib().time(
                makeNumber("12"), makeNumber("00"), makeNumber("00"),
                makeDuration("PT1H10M")));
    }

    @Test
    public void testDateTime()  {
        assertNull(getLib().dateAndTime(null));
        assertNull(getLib().dateAndTime(""));
        assertNull(getLib().dateAndTime("xxx"));
        assertNull(getLib().dateAndTime("11:00:00"));
        assertNull(getLib().dateAndTime("2011-12-03T10:15:30+01:00@Europe/Paris"));

        assertNull(getLib().dateAndTime(null, null));
        assertNull(getLib().dateAndTime(null, makeTime("11:00:00Z")));
        assertNull(getLib().dateAndTime(getLib().date("2016-08-01"), null));

        // Fix input literal
        assertEqualsTime("2016-08-01T11:00:00+01:00", getLib().dateAndTime("2016-08-01T11:00:00+0100"));

        assertEqualsTime("2016-08-01T11:00:00Z", getLib().dateAndTime("2016-08-01T11:00:00Z"));
        assertEqualsTime("2016-08-01T11:00:00.001Z", getLib().dateAndTime("2016-08-01T11:00:00.001Z"));
        assertEqualsTime("2016-08-01T11:00:00.001+01:00", getLib().dateAndTime("2016-08-01T11:00:00.001+01:00"));
        assertEqualsTime("2016-08-01T11:00:00+01:00", getLib().dateAndTime("2016-08-01T11:00:00+01:00"));

        assertEqualsTime("2016-08-01T11:00:00Z", getLib().dateAndTime("2016-08-01T11:00:00Z"));

        assertEqualsTime("2016-08-01T11:00:00Z", getLib().dateAndTime("2016-08-01T11:00:00Z"));
        assertEqualsTime("2016-08-01T11:00:00Z", getLib().dateAndTime(makeDate("2016-08-01"), makeTime("11:00:00Z")));

        assertEqualsTime("2000-01-02T03:04:05Z", getLib().dateTime(makeNumber("2"), makeNumber("1"), makeNumber("2000"), makeNumber("3"), makeNumber("4"), makeNumber("5")));
    }

    @Test
    public void testDuration()  {
        assertEquals("P1Y8M", getLib().duration("P1Y8M").toString());
        assertEquals("P2DT20H", getLib().duration("P2DT20H").toString());
        assertNull(getLib().duration("XXX"));
        assertNull(getLib().duration(null));
    }

    //
    // Numeric functions
    //
    @Test
    public void testFloor() {
        assertNull(getLib().floor(null));

        assertEqualsNumber(makeNumber("1"), getLib().floor(makeNumber(1)));
        assertEqualsNumber(makeNumber("1"), getLib().floor(makeNumber(1.1)));
        assertEqualsNumber(makeNumber("1"), getLib().floor(makeNumber(1.2)));
        assertEqualsNumber(makeNumber("1"), getLib().floor(makeNumber(1.5)));
        assertEqualsNumber(makeNumber("1"), getLib().floor(makeNumber(1.6)));

        assertEqualsNumber(makeNumber("-1"), getLib().floor(makeNumber(-1)));
        assertEqualsNumber(makeNumber("-2"), getLib().floor(makeNumber(-1.1)));
        assertEqualsNumber(makeNumber("-2"), getLib().floor(makeNumber(-1.2)));
        assertEqualsNumber(makeNumber("-2"), getLib().floor(makeNumber(-1.5)));
        assertEqualsNumber(makeNumber("-2"), getLib().floor(makeNumber(-1.6)));
    }

    @Test
    public void testCeiling() {
        assertNull(getLib().ceiling(null));

        assertEqualsNumber(makeNumber("1"), getLib().ceiling(makeNumber(1)));
        assertEqualsNumber(makeNumber("2"), getLib().ceiling(makeNumber(1.1)));
        assertEqualsNumber(makeNumber("2"), getLib().ceiling(makeNumber(1.2)));
        assertEqualsNumber(makeNumber("2"), getLib().ceiling(makeNumber(1.5)));
        assertEqualsNumber(makeNumber("2"), getLib().ceiling(makeNumber(1.6)));

        assertEqualsNumber(makeNumber("-1"), getLib().ceiling(makeNumber(-1)));
        assertEqualsNumber(makeNumber("-1"), getLib().ceiling(makeNumber(-1.1)));
        assertEqualsNumber(makeNumber("-1"), getLib().ceiling(makeNumber(-1.2)));
        assertEqualsNumber(makeNumber("-1"), getLib().ceiling(makeNumber(-1.5)));
        assertEqualsNumber(makeNumber("-1"), getLib().ceiling(makeNumber(-1.6)));
    }

    //
    // String functions
    //
    @Test
    public void testContains() {
        assertNull(getLib().contains(null, null));
        assertNull(getLib().contains(null, "bcg"));
        assertNull(getLib().contains("bcg", null));

        assertTrue(getLib().contains("abc", "a"));
        assertFalse(getLib().contains("aBc1", "bcg"));

        assertFalse(getLib().contains("foobar", "of"));
        assertTrue(getLib().contains("foobar", "fo"));
        assertTrue(getLib().contains("foobar", "r"));
        assertFalse(getLib().contains("foobar", "R"));
    }

    @Test
    public void testStartsWith() {
        assertNull(getLib().startsWith(null, null));
        assertNull(getLib().startsWith(null, "bcg"));
        assertNull(getLib().startsWith("bcg", null));

        assertTrue(getLib().startsWith("abc", "a"));
        assertFalse(getLib().startsWith("aBc1", "abc"));

        assertEquals(true, getLib().startsWith("foobar", "fo"));
    }

    @Test
    public void testEndsWith() {
        assertNull(getLib().endsWith(null, null));
        assertNull(getLib().endsWith(null, "bcg"));
        assertNull(getLib().endsWith("bcg", null));

        assertTrue(getLib().endsWith("abc", "c"));
        assertFalse(getLib().endsWith("aBc1", "bc1"));

        assertEquals(true, getLib().endsWith("foobar", "r"));
    }

    //
    // Test list functions
    //
    @Test
    public void testAppend() {
        assertEquals(Arrays.asList(null), getLib().append(null, null));
        assertEquals(makeNumberList("3"), getLib().append(null, makeNumber("3")));
        assertEquals(Arrays.asList(), getLib().append(makeNumberList(), null));

        assertEquals(makeNumberList("1", "2", "3", "4"), getLib().append(makeNumberList(1, 2, 3), makeNumber(4)));
        assertEquals(makeNumberList("1", null, "3", "4"), getLib().append(makeNumberList(1, null, 3), makeNumber(4)));
    }

    @Test
    public void testCount() {
        assertNull(getLib().count(null));
        assertEqualsNumber(makeNumber("0"), getLib().count(makeNumberList()));

        assertEqualsNumber(makeNumber("3"), getLib().count(makeNumberList(1, 2, 3)));
        assertEqualsNumber(makeNumber("3"), getLib().count(makeNumberList(1, null, 3)));
    }

    @Test
    public void testMin() {
        assertNull(getLib().min((List) null));
        assertNull(getLib().min(Arrays.asList()));

        assertEqualsNumber(makeNumber("1"), getLib().min(makeNumberList(1, 2, 3)));
        assertNull(getLib().min(makeNumberList(1, null, 3)));
    }

    @Test
    public void testMax() {
        assertNull(getLib().max((List) null));
        assertNull(getLib().max(Arrays.asList()));

        assertEqualsNumber(makeNumber("3"), getLib().max(makeNumberList(1, 2, 3)));
        assertNull(getLib().max(makeNumberList(1, null, 3)));
    }

    @Test
    public void testSum() {
        assertNull(getLib().sum((List) null));
        assertNull(getLib().sum((makeNumberList())));

        assertEqualsNumber(makeNumber("6"), getLib().sum(makeNumberList(1, 2, 3)));
        assertEqualsNumber(makeNumber("1"), getLib().sum(makeNumberList(1, 2, -2)));
        assertNull(getLib().sum(makeNumberList(1, null, 3)));
    }

    //
    // Test booleanm functions
    //
    @Test
    public void testNot() {
        assertNull(getLib().not(null));
        assertFalse(getLib().not(Boolean.TRUE));
        assertTrue(getLib().not(Boolean.FALSE));
    }
}
