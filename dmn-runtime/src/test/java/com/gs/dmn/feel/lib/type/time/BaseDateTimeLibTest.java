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
package com.gs.dmn.feel.lib.type.time;

import org.junit.Test;

import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.format.DateTimeParseException;

import static org.junit.Assert.*;

public class BaseDateTimeLibTest {
    BaseDateTimeLibStub dateTimeLib = new BaseDateTimeLibStub();
    
    @Test
    public void testFixDateTimeFormat() {
        assertEquals("12:00:00", this.dateTimeLib.fixDateTimeFormat("T12:00:00"));
        assertEquals("12:00:00Z", this.dateTimeLib.fixDateTimeFormat("T12:00:00Z"));
        assertEquals("12:00:00+00:00", this.dateTimeLib.fixDateTimeFormat("T12:00:00+00:00"));
        assertEquals("12:00:00-00:00", this.dateTimeLib.fixDateTimeFormat("T12:00:00-00:00"));
        assertEquals("12:00:00-00:00", this.dateTimeLib.fixDateTimeFormat("T12:00:00-0000"));
        assertEquals("12:00:00+00:00", this.dateTimeLib.fixDateTimeFormat("T12:00:00+0000"));

        assertEquals("2016-08-01T12:00:00", this.dateTimeLib.fixDateTimeFormat("2016-08-01T12:00:00"));
        assertEquals("2016-08-01T12:00:00Z", this.dateTimeLib.fixDateTimeFormat("2016-08-01T12:00:00Z"));
        assertEquals("2016-08-01T12:00:00+00:00", this.dateTimeLib.fixDateTimeFormat("2016-08-01T12:00:00+00:00"));
        assertEquals("2016-08-01T12:00:00-00:00", this.dateTimeLib.fixDateTimeFormat("2016-08-01T12:00:00-00:00"));
        assertEquals("2016-08-01T12:00:00-00:00", this.dateTimeLib.fixDateTimeFormat("2016-08-01T12:00:00-0000"));
        assertEquals("2016-08-01T12:00:00+00:00", this.dateTimeLib.fixDateTimeFormat("2016-08-01T12:00:00+0000"));

        assertEquals("+999999999-08-01T12:00:00+00:00", this.dateTimeLib.fixDateTimeFormat("+999999999-08-01T12:00:00+0000"));
        assertEquals("-999999999-08-01T12:00:00+00:00", this.dateTimeLib.fixDateTimeFormat("-999999999-08-01T12:00:00+0000"));
    }

    @Test
    public void testIsTime() {
        assertTrue(this.dateTimeLib.isTime("12:00:00"));
        assertTrue(this.dateTimeLib.isTime("12:00:00Z"));
        assertTrue(this.dateTimeLib.isTime("12:00:00[UTC]"));
        assertTrue(this.dateTimeLib.isTime("12:00:00@Europe/Paris"));
        assertTrue(this.dateTimeLib.isTime("12:00:00Z[UTC]"));
        assertTrue(this.dateTimeLib.isTime("12:00:00Z@Europe/Paris"));
        assertTrue(this.dateTimeLib.isTime("12:00:00+00:00[UTC]"));
        assertTrue(this.dateTimeLib.isTime("12:00:00+01:00@Europe/Paris"));

        assertFalse(this.dateTimeLib.isTime("2017-08-01"));
        assertFalse(this.dateTimeLib.isTime("2017-08-01T12:00:00Z"));
        assertFalse(this.dateTimeLib.isTime("2017-08-01T12:00:00+00:00[UTC]"));
        assertFalse(this.dateTimeLib.isTime("2017-08-01T12:00:00+00:00@Europe/Paris"));
    }

    @Test
    public void testHasTime() {
        assertTrue(this.dateTimeLib.hasTime("2017-06-01T12:00:00Z"));
        assertTrue(this.dateTimeLib.hasTime("9999999999-06-01T12:00:00Z"));
        assertTrue(this.dateTimeLib.hasTime("-9999999999-06-01T12:00:00Z"));
        assertTrue(this.dateTimeLib.hasTime("T12:00:00Z"));

        assertFalse(this.dateTimeLib.hasTime("2017-08-01"));
        assertFalse(this.dateTimeLib.hasTime("12:00:00Z"));
    }

    @Test
    public void testHasZoneId() {
        assertFalse(this.dateTimeLib.hasZoneId("12:00:00"));
        assertFalse(this.dateTimeLib.hasZoneId("12:00:00Z"));
        assertTrue(this.dateTimeLib.hasZoneId("12:00:00[UTC]"));
        assertTrue(this.dateTimeLib.hasZoneId("12:00:00@Europe/Paris"));
        assertFalse(this.dateTimeLib.hasZoneId("12:00:00+00:00"));
        assertFalse(this.dateTimeLib.hasZoneId("12:00:00-00:00"));
        assertFalse(this.dateTimeLib.hasZoneId("12:00:00-0000"));
        assertFalse(this.dateTimeLib.hasZoneId("12:00:00+0000"));

        assertFalse(this.dateTimeLib.hasZoneId("2016-08-01T12:00:00"));
        assertFalse(this.dateTimeLib.hasZoneId("2016-08-01T12:00:00Z"));
        assertTrue(this.dateTimeLib.hasZoneId("2016-08-01T12:00:00[UTC]"));
        assertTrue(this.dateTimeLib.hasZoneId("2016-08-01T12:00:00@Europe/Paris"));
        assertFalse(this.dateTimeLib.hasZoneId("2016-08-01T12:00:00+00:00"));
        assertFalse(this.dateTimeLib.hasZoneId("2016-08-01T12:00:00-00:00"));
        assertFalse(this.dateTimeLib.hasZoneId("2016-08-01T12:00:00-0000"));
        assertFalse(this.dateTimeLib.hasZoneId("2016-08-01T12:00:00+0000"));
    }

    @Test
    public void testHasZoneOffset() {
        assertFalse(this.dateTimeLib.hasZoneOffset("12:00:00"));
        assertTrue(this.dateTimeLib.hasZoneOffset("12:00:00Z"));
        assertFalse(this.dateTimeLib.hasZoneOffset("12:00:00[UTC]"));
        assertTrue(this.dateTimeLib.hasZoneOffset("12:00:00+00:00"));
        assertTrue(this.dateTimeLib.hasZoneOffset("12:00:00-00:00"));
        assertFalse(this.dateTimeLib.hasZoneOffset("12:00:00-0000"));
        assertFalse(this.dateTimeLib.hasZoneOffset("12:00:00+0000"));

        assertFalse(this.dateTimeLib.hasZoneOffset("2016-08-01T12:00:00"));
        assertTrue(this.dateTimeLib.hasZoneOffset("2016-08-01T12:00:00Z"));
        assertFalse(this.dateTimeLib.hasZoneOffset("2016-08-01T12:00:00[UTC]"));
        assertTrue(this.dateTimeLib.hasZoneOffset("2016-08-01T12:00:00+00:00"));
        assertTrue(this.dateTimeLib.hasZoneOffset("2016-08-01T12:00:00-00:00"));
        assertTrue(this.dateTimeLib.hasZoneOffset("-999999999-01-01T12:00:00-00:00"));
        assertTrue(this.dateTimeLib.hasZoneOffset("+999999999-01-01T12:00:00-00:00"));
        assertFalse(this.dateTimeLib.hasZoneOffset("2016-08-01T12:00:00-0000"));
        assertFalse(this.dateTimeLib.hasZoneOffset("2016-08-01T12:00:00+0000"));
    }

    @Test
    public void testTimeHasOffset() {
        assertTrue(this.dateTimeLib.timeHasOffset("12:00:00+00:00"));
        assertTrue(this.dateTimeLib.timeHasOffset("12:00:00-00:00"));
        assertTrue(this.dateTimeLib.timeHasOffset("12:00:00-0000"));
        assertTrue(this.dateTimeLib.timeHasOffset("12:00:00+0000"));

        assertFalse(this.dateTimeLib.timeHasOffset("12:00:00"));
        assertFalse(this.dateTimeLib.timeHasOffset("12:00:00Z"));
        assertFalse(this.dateTimeLib.timeHasOffset("12:00:00[UTC]"));
    }

    @Test
    public void testMakeLocalDate() {
        assertEquals("2017-08-10", this.dateTimeLib.makeLocalDate("2017-08-10").format(BaseDateTimeLib.FEEL_DATE_FORMAT));
        assertEquals("9999-01-01", this.dateTimeLib.makeLocalDate("9999-01-01").format(BaseDateTimeLib.FEEL_DATE_FORMAT));
        assertEquals("999999999-01-01", this.dateTimeLib.makeLocalDate("999999999-01-01").format(BaseDateTimeLib.FEEL_DATE_FORMAT));
        assertEquals("-999999999-01-01", this.dateTimeLib.makeLocalDate("-999999999-01-01").format(BaseDateTimeLib.FEEL_DATE_FORMAT));
    }

    @Test(expected = DateTimeParseException.class)
    public void testMakeLocalDateWhenSign() {
        assertNotNull(this.dateTimeLib.makeLocalDate("+999999999-01-01"));
    }

    @Test
    public void testMakeOffsetTime() {
        assertEquals("10:20:00Z", this.dateTimeLib.makeOffsetTime("10:20:00").format(BaseDateTimeLib.FEEL_TIME_FORMAT));
        assertEquals("10:20:00Z", this.dateTimeLib.makeOffsetTime("10:20:00Z").format(BaseDateTimeLib.FEEL_TIME_FORMAT));
        assertEquals("10:20:00+01:00", this.dateTimeLib.makeOffsetTime("10:20:00+01:00").format(BaseDateTimeLib.FEEL_TIME_FORMAT));
        assertEquals("10:20:00-01:00", this.dateTimeLib.makeOffsetTime("10:20:00-01:00").format(BaseDateTimeLib.FEEL_TIME_FORMAT));
        assertEquals("10:20:00+01:00", this.dateTimeLib.makeOffsetTime("10:20:00@Europe/Paris").format(BaseDateTimeLib.FEEL_TIME_FORMAT));
        assertEquals("10:20:00Z", this.dateTimeLib.makeOffsetTime("10:20:00@UTC").format(BaseDateTimeLib.FEEL_TIME_FORMAT));
    }

    @Test
    public void testMakeDateTime() {
        assertEquals("2017-08-10T00:00:00Z@UTC", this.dateTimeLib.makeZonedDateTime("2017-08-10").format(BaseDateTimeLib.FEEL_DATE_TIME_FORMAT));
        assertEquals("9999-01-01T00:00:00Z@UTC", this.dateTimeLib.makeZonedDateTime("9999-01-01").format(BaseDateTimeLib.FEEL_DATE_TIME_FORMAT));
        assertEquals("999999999-01-01T00:00:00Z@UTC", this.dateTimeLib.makeZonedDateTime("999999999-01-01").format(BaseDateTimeLib.FEEL_DATE_TIME_FORMAT));
        assertEquals("-999999999-01-01T00:00:00Z@UTC", this.dateTimeLib.makeZonedDateTime("-999999999-01-01").format(BaseDateTimeLib.FEEL_DATE_TIME_FORMAT));

        assertEquals("2017-08-10T10:20:00Z", this.dateTimeLib.makeZonedDateTime("2017-08-10T10:20:00").format(BaseDateTimeLib.FEEL_DATE_TIME_FORMAT));
        assertEquals("2017-08-10T10:20:00Z", this.dateTimeLib.makeZonedDateTime("2017-08-10T10:20:00Z").format(BaseDateTimeLib.FEEL_DATE_TIME_FORMAT));
        assertEquals("2017-08-10T10:20:00+01:00", this.dateTimeLib.makeZonedDateTime("2017-08-10T10:20:00+01:00").format(BaseDateTimeLib.FEEL_DATE_TIME_FORMAT));
        assertEquals("2017-08-10T10:20:00-01:00", this.dateTimeLib.makeZonedDateTime("2017-08-10T10:20:00-01:00").format(BaseDateTimeLib.FEEL_DATE_TIME_FORMAT));
        // Summer
        assertEquals("2017-08-10T10:20:00+02:00@Europe/Paris", this.dateTimeLib.makeZonedDateTime("2017-08-10T10:20:00@Europe/Paris").format(BaseDateTimeLib.FEEL_DATE_TIME_FORMAT));
        // Winter
        assertEquals("2017-02-10T10:20:00+01:00@Europe/Paris", this.dateTimeLib.makeZonedDateTime("2017-02-10T10:20:00@Europe/Paris").format(BaseDateTimeLib.FEEL_DATE_TIME_FORMAT));
        assertEquals("2017-08-10T10:20:00Z@UTC", this.dateTimeLib.makeZonedDateTime("2017-08-10T10:20:00@UTC").format(BaseDateTimeLib.FEEL_DATE_TIME_FORMAT));
    }
}

class BaseDateTimeLibStub extends BaseDateTimeLib {
    @Override
    protected boolean isTime(String literal) {
        return super.isTime(literal);
    }

    @Override
    protected boolean hasTime(String literal) {
        return super.hasTime(literal);
    }

    @Override
    protected boolean hasZoneId(String literal) {
        return super.hasZoneId(literal);
    }

    @Override
    protected boolean hasZoneOffset(String literal) {
        return super.hasZoneOffset(literal);
    }

    @Override
    protected boolean timeHasOffset(String literal) {
        return super.timeHasOffset(literal);
    }

    @Override
    protected String fixDateTimeFormat(String literal) {
        return super.fixDateTimeFormat(literal);
    }

    @Override
    protected LocalDate makeLocalDate(String literal) {
        return super.makeLocalDate(literal);
    }

    @Override
    protected OffsetTime makeOffsetTime(String literal) {
        return super.makeOffsetTime(literal);
    }
}