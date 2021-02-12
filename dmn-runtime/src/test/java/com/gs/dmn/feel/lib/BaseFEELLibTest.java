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

import com.gs.dmn.runtime.Context;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public abstract class BaseFEELLibTest<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends FEELOperatorsTest<NUMBER, DATE, TIME, DATE_TIME, DURATION> {
    //
    // Conversion functions
    //
    @Test
    public void testNumber() {
        assertNull(getLib().number(null));
        assertNull(getLib().number("1,200.56"));
        assertNull(getLib().number("xxx"));

        assertEqualsNumber("123.56", getLib().number("123.56"));
        assertEqualsNumber("-123.56", getLib().number("-123.56"));
    }

    @Test
    public void testDate() {
        //
        // conversion from string
        //
        assertNull(getLib().date((String) null));
        assertNull(getLib().date(""));
        assertNull(getLib().date("xxx"));
        assertNull(getLib().date("01211-12-31"));
        assertNull(getLib().date("2017-08-25T11:00:00"));

        // year must be in the range [-999,999,999..999,999,999].
        assertEqualsDateTime("999999999-10-11", getLib().date(getLib().date("999999999-10-11")));
        assertEqualsDateTime("-999999999-10-11", getLib().date(getLib().date("-999999999-10-11")));
        assertNull(getLib().date(getLib().date("9999999991-10-11")));
        assertNull(getLib().date(getLib().date("-9999999991-10-11")));

        assertEqualsDateTime("2016-08-01", getLib().date("2016-08-01"));

        //
        // conversion from date
        //
        assertNull(getLib().date((DATE) null));
        assertEqualsDateTime("2016-08-01", getLib().date(makeDate("2016-08-01")));

        //
        // conversion from numbers
        //
        assertNull(getLib().date(null, null, null));
        assertNull(getLib().date(makeNumber("2016"), null, null));
        assertNull(getLib().date(null, makeNumber("8"), null));
        assertNull(getLib().date(null, null, makeNumber("1")));
        assertEqualsDateTime("2016-08-01", getLib().date(makeNumber("2016"), makeNumber("8"), makeNumber("1")));
    }

    @Test
    public void testTime() {
        //
        // conversion from string
        //
        assertNull(getLib().time((String) null));
        assertNull(getLib().time(""));
        assertNull(getLib().time("xxx"));
        assertNull(getLib().time("13:20:00+01:00@Europe/Paris"));
        assertNull(getLib().time("13:20:00+00:00[UTC]"));

        // fix input literal
        assertEqualsDateTime("11:00:00Z", getLib().time("T11:00:00Z"));
        assertEqualsDateTime("11:00:00+01:00", getLib().time("11:00:00+0100"));

        assertEqualsDateTime("11:00:00Z", getLib().time("11:00:00Z"));
        assertEqualsDateTime("11:00:00.001Z", getLib().time("11:00:00.001Z"));

        assertEqualsDateTime("11:00:00.001+01:00", getLib().time("11:00:00.001+01:00"));
        assertEqualsDateTime("11:00:00+01:00", getLib().time("11:00:00+01:00"));

        //
        // conversion from number
        //
        assertNull(getLib().time(null, null, null, null));

        assertNull(getLib().time(
                makeNumber("12"), makeNumber("00"), makeNumber("00"),
                makeDuration("PT25H10M")));
        assertEqualsDateTime("12:00:00+01:10", getLib().time(
                makeNumber("12"), makeNumber("00"), makeNumber("00"),
                makeDuration("PT1H10M")));
    }

    @Test
    public void testDateTime() {
        //
        // conversion from string
        //
        assertNull(getLib().dateAndTime(null));
        assertNull(getLib().dateAndTime(""));
        assertNull(getLib().dateAndTime("xxx"));
        assertNull(getLib().dateAndTime("11:00:00"));
        assertNull(getLib().dateAndTime("2011-12-03T10:15:30+01:00@Europe/Paris"));
        assertNull(getLib().dateAndTime("2017-12-31T12:20:00+19:00"));

        // fix input literal
        assertEqualsDateTime("2016-08-01T11:00:00+01:00", getLib().dateAndTime("2016-08-01T11:00:00+0100"));

        assertEqualsDateTime("2016-08-01T11:00:00Z", getLib().dateAndTime("2016-08-01T11:00:00Z"));
        assertEqualsDateTime("2016-08-01T11:00:00.001Z", getLib().dateAndTime("2016-08-01T11:00:00.001Z"));
        assertEqualsDateTime("2016-08-01T11:00:00.001+01:00", getLib().dateAndTime("2016-08-01T11:00:00.001+01:00"));
        assertEqualsDateTime("2016-08-01T11:00:00+01:00", getLib().dateAndTime("2016-08-01T11:00:00+01:00"));

        //
        // conversion from date and time
        //
        assertNull(getLib().dateAndTime(null, null));
        assertNull(getLib().dateAndTime(null, makeTime("11:00:00Z")));
        assertNull(getLib().dateAndTime(getLib().date("2016-08-01"), null));

        assertEqualsDateTime("2016-08-01T11:00:00Z", getLib().dateAndTime(makeDate("2016-08-01"), makeTime("11:00:00Z")));
    }

    @Test
    public void testDuration() {
        assertNull(getLib().duration("XXX"));
        assertNull(getLib().duration(null));

        assertEqualsDateTime("P1Y8M", getLib().duration("P1Y8M"));
        assertEqualsDateTime("P2DT20H", getLib().duration("P2DT20H"));
    }

    //
    // Conversion functions
    //
    @Test
    public void testAsList() {
        assertEqualsList("[null]", getLib().asList(null));
        assertEquals(Arrays.asList(), getLib().asList());
        assertEquals(Arrays.asList(null, "a"), getLib().asList(null, "a"));
    }

    @Test
    public void testAsElement() {
        assertNull(getLib().asElement(null));
        assertNull(getLib().asElement(Arrays.asList()));
        assertNull(null, getLib().asElement(Arrays.asList("1", "2")));

        assertEquals("1", getLib().asElement(Arrays.asList("1")));
    }

    @Test
    public void testRangeToList() {
        assertEquals(makeNumberList(), getLib().rangeToList(false, null, false, null));
        assertEquals(makeNumberList(), getLib().rangeToList(false, null, false, makeNumber("3")));
        assertEquals(makeNumberList(), getLib().rangeToList(false, makeNumber("1"), false, null));

        assertEquals(makeNumberList("2"), getLib().rangeToList(true, makeNumber("1"), true, makeNumber("3")));
        assertEquals(makeNumberList("1", "2"), getLib().rangeToList(false, makeNumber("1"), true, makeNumber("3")));
        assertEquals(makeNumberList("2", "3"), getLib().rangeToList(true, makeNumber("1"), false, makeNumber("3")));
        assertEquals(makeNumberList("1", "2", "3"), getLib().rangeToList(false, makeNumber("1"), false, makeNumber("3")));
    }

    @Test
    public void testRangeToListNoFlags() {
        assertEquals(Arrays.asList(), getLib().rangeToList(null, null));
        assertEquals(Arrays.asList(), getLib().rangeToList(makeNumber("0"), null));
        assertEquals(Arrays.asList(), getLib().rangeToList(null, makeNumber("1")));

        assertEquals(makeNumberList(1, 2, 3), getLib().rangeToList(makeNumber("1"), makeNumber("3")));
        assertEquals(makeNumberList(3, 2, 1), getLib().rangeToList(makeNumber("3"), makeNumber("1")));
    }

    @Test
    public void testToDate() {
        assertNull(getLib().toDate(null));
        assertNull(getLib().toDate("1"));
        assertNull(getLib().toDate(makeNumber("1")));
        assertNull(getLib().toDate(makeTime("12:00:00Z")));

        assertEqualsDateTime("2016-08-01", getLib().toDate(makeDate("2016-08-01")));
        assertEqualsDateTime("2016-08-01", getLib().toDate(makeDateAndTime("2016-08-01T12:00:00Z")));
    }

    @Test
    public void testToTime() {
        assertNull(getLib().toTime(null));
        assertNull(getLib().toTime("1"));
        assertNull(getLib().toTime(makeNumber("1")));

        assertEqualsDateTime("00:00:00Z", getLib().toTime(makeDate("2016-08-01")));
        assertEqualsDateTime("12:00:00Z", getLib().toTime(makeTime("12:00:00Z")));
        assertEqualsDateTime("12:00:00Z", getLib().toTime(makeDateAndTime("2016-08-01T12:00:00Z")));
    }

    //
    // Boolean functions
    //
    @Test
    public void testAnd() {
        assertFalse(getLib().and(true, true, false));
        assertFalse(getLib().and(null, false));
        assertFalse(getLib().and(Arrays.asList(true, true, false)));
        assertFalse(getLib().and(Arrays.asList(null, false)));

        assertTrue(getLib().and());
        assertTrue(getLib().and(new Object[] {}));
        assertTrue(getLib().and(Arrays.asList()));
        assertTrue(getLib().and(true, true));
        assertTrue(getLib().and(Arrays.asList(true, true)));

        assertNull(getLib().and((Object) null));
        assertNull(getLib().and(null, null));
        assertNull(getLib().and(null, true));
        assertNull(getLib().and((List) null));
        assertNull(getLib().and(Arrays.asList(null, null)));
        assertNull(getLib().and(Arrays.asList(null, true)));
    }

    @Test
    public void testOr() {
        assertTrue(getLib().or(true, true));
        assertTrue(getLib().or(false, true));
        assertTrue(getLib().or(null, true));
        assertTrue(getLib().or(Arrays.asList(true, true)));
        assertTrue(getLib().or(Arrays.asList(false, true)));
        assertTrue(getLib().or(Arrays.asList(null, true)));

        assertFalse(getLib().or());
        assertFalse(getLib().or(new Object[] {}));
        assertFalse(getLib().or(Arrays.asList()));
        assertFalse(getLib().or(false, false, false));
        assertFalse(getLib().or(Arrays.asList(false, false, false)));

        assertNull(getLib().or((Object) null));
        assertNull(getLib().or(null, null));
        assertNull(getLib().or(null, false));
        assertNull(getLib().or((List) null));
        assertNull(getLib().or(Arrays.asList(null, null)));
        assertNull(getLib().or(Arrays.asList(null, false)));
    }

    //
    // List functions
    //
    @Test
    public void testElementAt() {
        assertNull(getLib().elementAt(null, makeNumber("1")));
        assertNull(getLib().elementAt(Arrays.asList("1", "2", "3"), makeNumber("4")));
        assertNull(getLib().elementAt(Arrays.asList("1", "2", "3"), makeNumber("-4")));
        assertNull(getLib().elementAt(Arrays.asList("1", "2", "3"), makeNumber("0")));

        assertEquals("1", getLib().elementAt(Arrays.asList("1", "2", "3"), makeNumber("1")));
        assertEquals("2", getLib().elementAt(Arrays.asList("1", "2", "3"), makeNumber("2")));
        assertEquals("3", getLib().elementAt(Arrays.asList("1", "2", "3"), makeNumber("3")));
        assertEquals("3", getLib().elementAt(Arrays.asList("1", "2", "3"), makeNumber("-1")));
        assertEquals("2", getLib().elementAt(Arrays.asList("1", "2", "3"), makeNumber("-2")));
        assertEquals("1", getLib().elementAt(Arrays.asList("1", "2", "3"), makeNumber("-3")));
    }

    @Test
    public void testFlattenFirstLevel() {
        assertNull(getLib().flattenFirstLevel(null));

        assertEqualsList("[]", getLib().flattenFirstLevel(Arrays.asList()));
        assertEqualsList("[l11, l12, l13]", getLib().flattenFirstLevel(Arrays.asList("l11", "l12", "l13")));
        assertEqualsList("[l11, l21, l22, l13]", getLib().flattenFirstLevel(Arrays.asList("l11", Arrays.asList("l21", "l22"), "l13")));
        assertEqualsList("[l11, l21, [l31, l32], l13]", getLib().flattenFirstLevel(Arrays.asList("l11", Arrays.asList("l21", Arrays.asList("l31", "l32")), "l13")));

        assertEqualsList("[l11, null, [null, l32], l13]", getLib().flattenFirstLevel(Arrays.asList("l11", Arrays.asList(null, Arrays.asList(null, "l32")), "l13")));
    }

    @Test
    public void testCount() {
        assertEqualsNumber(makeNumber("0"), getLib().count(null));

        assertEqualsNumber(makeNumber("3"), getLib().count(makeNumberList(1, 2, 3)));
        assertEqualsNumber(makeNumber("3"), getLib().count(makeNumberList(1, null, 3)));
    }

    @Test
    public void testMin() {
        assertNull(getLib().min());
        assertNull(getLib().min((Object) null));
        assertNull(getLib().min(new Object[] {}));
        assertNull(getLib().min((List) null));
        assertNull(getLib().min(makeNumberList()));

        assertNull(getLib().min(makeNumber(1), null, makeNumber(3)));
        assertNull(getLib().min(makeNumberList(1, null, 3)));

        assertEqualsNumber(makeNumber("1"), getLib().min(makeNumber(1), makeNumber(2), makeNumber(3)));
        assertEqualsNumber(makeNumber("1"), getLib().min(makeNumberList(1, 2, 3)));
    }

    @Test
    public void testMax() {
        assertNull(getLib().max());
        assertNull(getLib().max((Object) null));
        assertNull(getLib().max(new Object[] {}));
        assertNull(getLib().max((List) null));
        assertNull(getLib().max(makeNumberList()));

        assertNull(getLib().max(makeNumber(1), null));
        assertNull(getLib().max(makeNumberList(1, null, 3)));

        assertEqualsNumber(makeNumber("3"), getLib().max(makeNumber(1), makeNumber(3)));
        assertEqualsNumber(makeNumber("3"), getLib().max(makeNumberList(1, 2, 3)));
    }

    @Test
    public void testSum() {
        assertNull(getLib().sum());
        assertNull(getLib().sum((Object) null));
        assertNull(getLib().sum(new Object[] {}));
        assertNull(getLib().sum((List) null));
        assertNull(getLib().sum((makeNumberList())));

        assertNull(getLib().sum(makeNumber(1), null, makeNumber(3)));
        assertNull(getLib().sum(makeNumberList(1, null, 3)));

        assertEqualsNumber(makeNumber("6"), getLib().sum(makeNumber(1), makeNumber(2), makeNumber(3)));
        assertEqualsNumber(makeNumber("6"), getLib().sum(makeNumberList(1, 2, 3)));
    }

    //
    // Context functions
    //
    @Test
    public void testGetEntries() {
        assertNull(getLib().getEntries(null));
        assertNull(getLib().getEntries(makeNumber("1")));

        assertEquals(Arrays.asList(), getLib().getEntries(new Context()));
        assertEquals(Arrays.asList(new Context().add("key", "a").add("value", makeNumber("1"))), getLib().getEntries(new Context().add("a", makeNumber("1"))));
    }

    @Test
    public void testGetValue() {
        assertNull(getLib().getValue(null, null));
        assertNull(getLib().getValue(new Context(), null));
        assertNull(getLib().getValue(new Context(), "a"));

        assertEquals(makeNumber("1"), getLib().getValue(new Context().add("a", makeNumber("1")), "a"));
    }
}

