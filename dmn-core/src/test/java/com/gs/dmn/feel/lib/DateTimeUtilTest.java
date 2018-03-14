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

import org.junit.Test;

import java.time.format.DateTimeParseException;

import static org.junit.Assert.*;

public class DateTimeUtilTest {
    @Test
    public void testFixDateTimeFormat() {
        assertEquals("12:00:00", DateTimeUtil.fixDateTimeFormat("T12:00:00"));
        assertEquals("12:00:00Z", DateTimeUtil.fixDateTimeFormat("T12:00:00Z"));
        assertEquals("12:00:00+00:00", DateTimeUtil.fixDateTimeFormat("T12:00:00+00:00"));
        assertEquals("12:00:00-00:00", DateTimeUtil.fixDateTimeFormat("T12:00:00-00:00"));
        assertEquals("12:00:00-00:00", DateTimeUtil.fixDateTimeFormat("T12:00:00-0000"));
        assertEquals("12:00:00+00:00", DateTimeUtil.fixDateTimeFormat("T12:00:00+0000"));

        assertEquals("2016-08-01T12:00:00", DateTimeUtil.fixDateTimeFormat("2016-08-01T12:00:00"));
        assertEquals("2016-08-01T12:00:00Z", DateTimeUtil.fixDateTimeFormat("2016-08-01T12:00:00Z"));
        assertEquals("2016-08-01T12:00:00+00:00", DateTimeUtil.fixDateTimeFormat("2016-08-01T12:00:00+00:00"));
        assertEquals("2016-08-01T12:00:00-00:00", DateTimeUtil.fixDateTimeFormat("2016-08-01T12:00:00-00:00"));
        assertEquals("2016-08-01T12:00:00-00:00", DateTimeUtil.fixDateTimeFormat("2016-08-01T12:00:00-0000"));
        assertEquals("2016-08-01T12:00:00+00:00", DateTimeUtil.fixDateTimeFormat("2016-08-01T12:00:00+0000"));

        assertEquals("+999999999-08-01T12:00:00+00:00", DateTimeUtil.fixDateTimeFormat("+999999999-08-01T12:00:00+0000"));
        assertEquals("-999999999-08-01T12:00:00+00:00", DateTimeUtil.fixDateTimeFormat("-999999999-08-01T12:00:00+0000"));
    }

    @Test
    public void testIsTime() {
        assertTrue(DateTimeUtil.isTime("12:00:00"));
        assertTrue(DateTimeUtil.isTime("12:00:00Z"));
        assertTrue(DateTimeUtil.isTime("12:00:00[UTC]"));
        assertTrue(DateTimeUtil.isTime("12:00:00@Europe/Paris"));
        assertTrue(DateTimeUtil.isTime("12:00:00Z[UTC]"));
        assertTrue(DateTimeUtil.isTime("12:00:00Z@Europe/Paris"));
        assertTrue(DateTimeUtil.isTime("12:00:00+00:00[UTC]"));
        assertTrue(DateTimeUtil.isTime("12:00:00+01:00@Europe/Paris"));

        assertFalse(DateTimeUtil.isTime("2017-08-01"));
        assertFalse(DateTimeUtil.isTime("2017-08-01T12:00:00Z"));
        assertFalse(DateTimeUtil.isTime("2017-08-01T12:00:00+00:00[UTC]"));
        assertFalse(DateTimeUtil.isTime("2017-08-01T12:00:00+00:00@Europe/Paris"));
    }

    @Test
    public void testHasTime() {
        assertTrue(DateTimeUtil.hasTime("2017-06-01T12:00:00Z"));
        assertTrue(DateTimeUtil.hasTime("9999999999-06-01T12:00:00Z"));
        assertTrue(DateTimeUtil.hasTime("-9999999999-06-01T12:00:00Z"));
        assertTrue(DateTimeUtil.hasTime("T12:00:00Z"));

        assertFalse(DateTimeUtil.hasTime("2017-08-01"));
        assertFalse(DateTimeUtil.hasTime("12:00:00Z"));
    }

    @Test
    public void testHasZone() {
        assertFalse(DateTimeUtil.hasZone("12:00:00"));
        assertTrue(DateTimeUtil.hasZone("12:00:00Z"));
        assertTrue(DateTimeUtil.hasZone("12:00:00[UTC]"));
        assertTrue(DateTimeUtil.hasZone("12:00:00@Europe/Paris"));
        assertFalse(DateTimeUtil.hasZone("12:00:00+00:00"));
        assertFalse(DateTimeUtil.hasZone("12:00:00-00:00"));
        assertFalse(DateTimeUtil.hasZone("12:00:00-0000"));
        assertFalse(DateTimeUtil.hasZone("12:00:00+0000"));

        assertFalse(DateTimeUtil.hasZone("2016-08-01T12:00:00"));
        assertTrue(DateTimeUtil.hasZone("2016-08-01T12:00:00Z"));
        assertTrue(DateTimeUtil.hasZone("2016-08-01T12:00:00[UTC]"));
        assertTrue(DateTimeUtil.hasZone("2016-08-01T12:00:00@Europe/Paris"));
        assertFalse(DateTimeUtil.hasZone("2016-08-01T12:00:00+00:00"));
        assertFalse(DateTimeUtil.hasZone("2016-08-01T12:00:00-00:00"));
        assertFalse(DateTimeUtil.hasZone("2016-08-01T12:00:00-0000"));
        assertFalse(DateTimeUtil.hasZone("2016-08-01T12:00:00+0000"));
    }

    @Test
    public void testHasOffset() {
        assertFalse(DateTimeUtil.hasOffset("12:00:00"));
        assertFalse(DateTimeUtil.hasOffset("12:00:00Z"));
        assertFalse(DateTimeUtil.hasOffset("12:00:00[UTC]"));
        assertTrue(DateTimeUtil.hasOffset("12:00:00+00:00"));
        assertTrue(DateTimeUtil.hasOffset("12:00:00-00:00"));
        assertFalse(DateTimeUtil.hasOffset("12:00:00-0000"));
        assertFalse(DateTimeUtil.hasOffset("12:00:00+0000"));

        assertFalse(DateTimeUtil.hasOffset("2016-08-01T12:00:00"));
        assertFalse(DateTimeUtil.hasOffset("2016-08-01T12:00:00Z"));
        assertFalse(DateTimeUtil.hasOffset("2016-08-01T12:00:00[UTC]"));
        assertTrue(DateTimeUtil.hasOffset("2016-08-01T12:00:00+00:00"));
        assertTrue(DateTimeUtil.hasOffset("2016-08-01T12:00:00-00:00"));
        assertTrue(DateTimeUtil.hasOffset("-999999999-01-01T12:00:00-00:00"));
        assertTrue(DateTimeUtil.hasOffset("+999999999-01-01T12:00:00-00:00"));
        assertFalse(DateTimeUtil.hasOffset("2016-08-01T12:00:00-0000"));
        assertFalse(DateTimeUtil.hasOffset("2016-08-01T12:00:00+0000"));
    }

    @Test
    public void testTimeHasOffset() {
        assertTrue(DateTimeUtil.timeHasOffset("12:00:00+00:00"));
        assertTrue(DateTimeUtil.timeHasOffset("12:00:00-00:00"));
        assertTrue(DateTimeUtil.timeHasOffset("12:00:00-0000"));
        assertTrue(DateTimeUtil.timeHasOffset("12:00:00+0000"));

        assertFalse(DateTimeUtil.timeHasOffset("12:00:00"));
        assertFalse(DateTimeUtil.timeHasOffset("12:00:00Z"));
        assertFalse(DateTimeUtil.timeHasOffset("12:00:00[UTC]"));
    }

    @Test
    public void testMakeLocalDate() {
        assertEquals("2017-08-10", DateTimeUtil.makeLocalDate("2017-08-10").format(DateTimeUtil.FEEL_DATE_FORMAT));
        assertEquals("9999-01-01", DateTimeUtil.makeLocalDate("9999-01-01").format(DateTimeUtil.FEEL_DATE_FORMAT));
        assertEquals("999999999-01-01", DateTimeUtil.makeLocalDate("999999999-01-01").format(DateTimeUtil.FEEL_DATE_FORMAT));
        assertEquals("-999999999-01-01", DateTimeUtil.makeLocalDate("-999999999-01-01").format(DateTimeUtil.FEEL_DATE_FORMAT));
    }

    @Test(expected = DateTimeParseException.class)
    public void testMakeLocalDateWhenSign() {
        assertNotNull(DateTimeUtil.makeLocalDate("+999999999-01-01"));
    }

    @Test
    public void testMakeOffsetTime() {
        assertEquals("10:20:00Z", DateTimeUtil.makeOffsetTime("10:20:00").format(DateTimeUtil.FEEL_TIME_FORMAT));
        assertEquals("10:20:00Z", DateTimeUtil.makeOffsetTime("10:20:00Z").format(DateTimeUtil.FEEL_TIME_FORMAT));
        assertEquals("10:20:00+01:00", DateTimeUtil.makeOffsetTime("10:20:00+01:00").format(DateTimeUtil.FEEL_TIME_FORMAT));
        assertEquals("10:20:00-01:00", DateTimeUtil.makeOffsetTime("10:20:00-01:00").format(DateTimeUtil.FEEL_TIME_FORMAT));
        assertEquals("10:20:00+01:00", DateTimeUtil.makeOffsetTime("10:20:00@Europe/Paris").format(DateTimeUtil.FEEL_TIME_FORMAT));
        assertEquals("10:20:00Z", DateTimeUtil.makeOffsetTime("10:20:00@UTC").format(DateTimeUtil.FEEL_TIME_FORMAT));
    }

    @Test
    public void testMakeDateTime() {
        assertEquals("2017-08-10T00:00:00Z@UTC", DateTimeUtil.makeDateTime("2017-08-10").format(DateTimeUtil.FEEL_DATE_TIME_FORMAT));
        assertEquals("9999-01-01T00:00:00Z@UTC", DateTimeUtil.makeDateTime("9999-01-01").format(DateTimeUtil.FEEL_DATE_TIME_FORMAT));
        assertEquals("999999999-01-01T00:00:00Z@UTC", DateTimeUtil.makeDateTime("999999999-01-01").format(DateTimeUtil.FEEL_DATE_TIME_FORMAT));
        assertEquals("-999999999-01-01T00:00:00Z@UTC", DateTimeUtil.makeDateTime("-999999999-01-01").format(DateTimeUtil.FEEL_DATE_TIME_FORMAT));

        assertEquals("2017-08-10T10:20:00Z", DateTimeUtil.makeDateTime("2017-08-10T10:20:00").format(DateTimeUtil.FEEL_DATE_TIME_FORMAT));
        assertEquals("2017-08-10T10:20:00Z", DateTimeUtil.makeDateTime("2017-08-10T10:20:00Z").format(DateTimeUtil.FEEL_DATE_TIME_FORMAT));
        assertEquals("2017-08-10T10:20:00+01:00", DateTimeUtil.makeDateTime("2017-08-10T10:20:00+01:00").format(DateTimeUtil.FEEL_DATE_TIME_FORMAT));
        assertEquals("2017-08-10T10:20:00-01:00", DateTimeUtil.makeDateTime("2017-08-10T10:20:00-01:00").format(DateTimeUtil.FEEL_DATE_TIME_FORMAT));
        // Summer
        assertEquals("2017-08-10T10:20:00+02:00@Europe/Paris", DateTimeUtil.makeDateTime("2017-08-10T10:20:00@Europe/Paris").format(DateTimeUtil.FEEL_DATE_TIME_FORMAT));
        // Winter
        assertEquals("2017-02-10T10:20:00+01:00@Europe/Paris", DateTimeUtil.makeDateTime("2017-02-10T10:20:00@Europe/Paris").format(DateTimeUtil.FEEL_DATE_TIME_FORMAT));
        assertEquals("2017-08-10T10:20:00Z@UTC", DateTimeUtil.makeDateTime("2017-08-10T10:20:00@UTC").format(DateTimeUtil.FEEL_DATE_TIME_FORMAT));
    }
}