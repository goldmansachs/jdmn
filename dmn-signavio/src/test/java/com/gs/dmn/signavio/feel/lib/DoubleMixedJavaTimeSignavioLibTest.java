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

import javax.xml.datatype.Duration;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DoubleMixedJavaTimeSignavioLibTest extends BaseSignavioLibTest<Double, LocalDate, OffsetTime, ZonedDateTime, Duration> {
    @Override
    protected DoubleMixedJavaTimeSignavioLib getLib() {
        return new DoubleMixedJavaTimeSignavioLib();
    }

    //
    // Date & time operations
    //
    @Test
    public void testDay() {
        assertNull(getLib().day((LocalDate) null));

        assertEqualsNumber(makeNumber("10"), getLib().day(makeDate("2015-12-10")));
        assertNull(getLib().day(makeDate("xxx")));
    }

    @Test
    public void testDayAdd() {
        assertNull(getLib().dayAdd((ZonedDateTime) null, null));
        assertNull(getLib().dayAdd((ZonedDateTime) null, makeNumber("360")));
        assertNull(getLib().dayAdd(makeDateAndTime("2015-12-24T12:15:00.000+01:00"), null));

        assertEqualsTime("2015-12-25", getLib().dayAdd(makeDateAndTime("2015-12-24T12:15:00.001+01:00"), makeNumber("1")));
        assertEqualsTime("2016-12-18", getLib().dayAdd(makeDateAndTime("2015-12-24T12:15:00.001+01:00"), makeNumber("360")));
        assertEqualsTime("2015-12-25", getLib().dayAdd(makeDate("2015-12-24"), makeNumber("1")));
        assertEqualsTime("2016-12-18", getLib().dayAdd(makeDate("2015-12-24"), makeNumber("360")));
        assertEqualsTime("2015-12-23", getLib().dayAdd(makeDate("2015-12-24"), makeNumber("-1")));
        assertNull(getLib().dayAdd(makeDateAndTime("x"), makeNumber("360")));
    }

    @Test
    public void testDayDiff() {
        assertNull(getLib().dayDiff((LocalDate)null, null));
        assertNull(getLib().dayDiff(null, makeDate("2015-12-25")));
        assertNull(getLib().dayDiff(makeDate("2015-12-25"), null));

        assertEquals(makeNumber("1"), getLib().dayDiff(makeDate("2015-12-24"), makeDate("2015-12-25")));
        assertEquals(makeNumber("1"), getLib().dayDiff(makeDateAndTime("2015-12-24T12:15:00.000+01:00"), makeDateAndTime("2015-12-25T12:15:00.000+01:00")));
        assertEquals(makeNumber("-1"), getLib().dayDiff(makeDate("2015-12-24"), makeDate("2015-12-23")));
    }

    @Override
    @Test
    public void testDate() {
        super.testDate();

        assertEqualsTime("2016-01-01", getLib().date("2016-01-01"));
    }

    @Override
    @Test
    public void testDateTime() {
        assertNull(getLib().dateAndTime(null));

        assertEqualsTime("2016-08-01T11:00:00Z", getLib().dateAndTime("2016-08-01T11:00:00Z"));
        assertEqualsTime("2016-08-01T11:00:00Z", getLib().dateTime(
                makeNumber("1"), makeNumber("8"), makeNumber("2016"),
                makeNumber("11"), makeNumber("0"), makeNumber("0")
        ));
    }

    @Test
    public void testHour() {
        assertNull(getLib().hour(makeDateAndTime(null)));

        assertEquals(makeNumber("12"), getLib().hour(makeTime("12:15:00.000+01:00")));
        assertEquals(makeNumber("12"), getLib().hour(makeDateAndTime("2015-12-24T12:15:00.000+01:00")));
        assertNull(getLib().hour(makeDateAndTime("xxx")));
    }

    @Test
    public void testHourDiff() {
        assertNull(getLib().hourDiff((OffsetTime) null, null));
        assertNull(getLib().hourDiff(null, makeTime("12:15:00.000+01:00")));
        assertNull(getLib().hourDiff(makeTime("12:15:00.000+01:00"), null));

        assertEquals(makeNumber("0"), getLib().hourDiff(makeTime("12:15:00.000+01:00"), makeTime("12:15:00.000+01:00")));
        assertEquals(makeNumber("3"), getLib().hourDiff(makeDateAndTime("2015-12-24T12:15:00.000+01:00"), makeDateAndTime("2015-12-24T15:15:00.000+01:00")));
        assertEquals(makeNumber("27"), getLib().hourDiff(makeDateAndTime("2015-12-24T12:15:00.000+01:00"), makeDateAndTime("2015-12-25T15:15:00.000+01:00")));
        assertEquals(makeNumber("-24"), getLib().hourDiff(makeDateAndTime("2015-12-25T12:15:00.000+01:00"), makeDateAndTime("2015-12-24T12:15:00.000+01:00")));
        assertEquals(makeNumber("-27"), getLib().hourDiff(makeDateAndTime("2015-12-25T15:15:00.000+01:00"), makeDateAndTime("2015-12-24T12:15:00.000+01:00")));
    }

    @Test
    public void testMinute() {
        assertNull(getLib().minute((OffsetTime) null));

        assertEquals(makeNumber("15"), getLib().minute(makeTime("12:15:00.000+01:00")));
        assertEquals(makeNumber("15"), getLib().minute(makeDateAndTime("2015-12-24T12:15:00.000+01:00")));
    }

    @Test
    public void testMinuteDiff() {
        assertNull(getLib().minutesDiff((OffsetTime) null, null));
        assertNull(getLib().minutesDiff(null, makeTime("12:15:00.000+01:00")));
        assertNull(getLib().minutesDiff(makeTime("12:15:00.000+01:00"), null));

        assertEquals(makeNumber("0"), getLib().minutesDiff(makeTime("12:15:00.000+01:00"), makeTime("12:15:00.000+01:00")));
        assertEquals(makeNumber("5"), getLib().minutesDiff(makeDateAndTime("2015-12-24T12:15:00.000+01:00"), makeDateAndTime("2015-12-24T12:20:00.000+01:00")));
        assertEquals(makeNumber("65"), getLib().minutesDiff(makeDateAndTime("2015-12-24T12:15:00.000+01:00"), makeDateAndTime("2015-12-24T13:20:00.000+01:00")));
        assertEquals(makeNumber("-65"), getLib().minutesDiff(makeDateAndTime("2015-12-24T13:20:00.000+01:00"), makeDateAndTime("2015-12-24T12:15:00.000+01:00")));
    }

    @Test
    public void testMonth() {
        assertNull(getLib().month((LocalDate) null));

        assertEquals(makeNumber("12"), getLib().month(makeDate("2015-12-02")));
        assertEquals(makeNumber("12"), getLib().month(makeDateAndTime("2015-12-24T12:15:00.000+01:00")));
    }

    @Test
    public void testMonthAdd() {
        assertNull(getLib().monthAdd((LocalDate) null, null));
        assertNull(getLib().monthAdd((LocalDate) null, makeNumber("1")));
        assertNull(getLib().monthAdd(makeDate("2015-01-05"), null));

        assertEqualsTime("2015-02-05", getLib().monthAdd(makeDate("2015-01-05"), makeNumber("1")));
        assertEqualsTime("2016-01-05", getLib().monthAdd(makeDate("2015-01-05"), makeNumber("12")));
        assertEqualsTime("2014-11-05", getLib().monthAdd(makeDate("2015-01-05"), makeNumber("-2")));
        assertEqualsTime("2015-02-05T12:15:00.001+01:00", getLib().monthAdd(makeDateAndTime("2015-01-05T12:15:00.001+01:00"), makeNumber("1")));
        assertEqualsTime("2016-01-05T12:15:00.001+01:00", getLib().monthAdd(makeDateAndTime("2015-01-05T12:15:00.001+01:00"), makeNumber("12")));
        assertEqualsTime("2014-11-05T12:15:00.001+01:00", getLib().monthAdd(makeDateAndTime("2015-01-05T12:15:00.001+01:00"), makeNumber("-2")));
        assertNull(getLib().monthAdd(makeDateAndTime("xxx"), makeNumber("12")));
        assertNull(getLib().monthAdd((LocalDate) null, makeNumber("12")));
    }

    @Test
    public void testMonthDiff() {
        assertNull(getLib().monthDiff((LocalDate) null, null));
        assertNull(getLib().monthDiff(null, makeDate("2015-05-05")));
        assertNull(getLib().monthDiff(makeDate("2015-01-05"), null));

        assertEquals(makeNumber("0"), getLib().monthDiff(makeDate("2015-01-05"), makeDate("2015-01-05")));
        assertEquals(makeNumber("4"), getLib().monthDiff(makeDate("2015-01-05"), makeDate("2015-05-05")));
        assertEquals(makeNumber("16"), getLib().monthDiff(makeDate("2015-01-05"), makeDate("2016-05-05")));
        assertEquals(makeNumber("-4"), getLib().monthDiff(makeDate("2015-05-05"), makeDate("2015-01-05")));

        assertEquals(makeNumber("0"), getLib().monthDiff(makeDateAndTime("2015-01-05T12:15:00.000+01:00"), makeDateAndTime("2015-01-05T12:15:00.000+01:00")));
        assertEquals(makeNumber("4"), getLib().monthDiff(makeDateAndTime("2015-01-05T12:15:00.000+01:00"), makeDateAndTime("2015-05-05T12:15:00.000+01:00")));
        assertEquals(makeNumber("16"), getLib().monthDiff(makeDateAndTime("2015-01-05T12:15:00.000+01:00"), makeDateAndTime("2016-05-05T12:15:00.000+01:00")));
        assertEquals(makeNumber("-4"), getLib().monthDiff(makeDateAndTime("2015-05-05T12:15:00.000+01:00"), makeDateAndTime("2015-01-05T12:15:00.000+01:00")));
    }

    @Test
    public void testNow() {
        ZonedDateTime actual = getLib().now();
        Calendar expected = Calendar.getInstance();
        assertEquals(expected.get(Calendar.YEAR), actual.getYear());
        assertEquals(expected.get(Calendar.MONTH) + 1, actual.getMonth().getValue());
        assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.getDayOfMonth());
    }

    @Test
    public void testToday() {
        LocalDate actual = getLib().today();
        Calendar expected = Calendar.getInstance();
        assertEquals(expected.get(Calendar.YEAR), actual.getYear());
        assertEquals(expected.get(Calendar.MONTH) + 1, actual.getMonth().getValue());
        assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.getDayOfMonth());
    }


    @Test
    public void testWeekday() {
        assertNull(getLib().weekday((LocalDate) null));

        assertEquals(makeNumber("7"), getLib().weekday(makeDate("2016-12-25")));
        assertEquals(makeNumber("6"), getLib().weekday(makeDate("0003-02-01")));
        assertEquals(makeNumber("2"), getLib().weekday(makeDate("2016-11-01")));
        assertEquals(makeNumber("7"), getLib().weekday(makeDateAndTime("2016-11-20T12:15:00.000+01:00")));
    }

    @Test
    public void testYear() {
        assertNull(getLib().year((LocalDate) null));

        assertEquals(makeNumber("2016"), getLib().year(makeDate("2016-11-01")));
        assertEquals(makeNumber("2015"), getLib().year(makeDateAndTime("2015-11-20T12:15:00.000+01:00")));
    }

    @Test
    public void testYearAdd() {
        assertNull(getLib().yearAdd((LocalDate) null, null));
        assertNull(getLib().yearAdd((LocalDate) null, makeNumber("1")));
        assertNull(getLib().yearAdd(makeDate("2015-01-05"), null));

        assertEqualsTime("2016-01-05", getLib().yearAdd(makeDate("2015-01-05"), makeNumber("1")));
        assertEqualsTime("2027-01-05", getLib().yearAdd(makeDate("2015-01-05"), makeNumber("12")));
        assertEqualsTime("2016-01-05T12:15:00.001+01:00", getLib().yearAdd(makeDateAndTime("2015-01-05T12:15:00.001+01:00"), makeNumber("1")));
        assertEqualsTime("2027-01-05T12:15:00.001+01:00", getLib().yearAdd(makeDateAndTime("2015-01-05T12:15:00.001+01:00"), makeNumber("12")));
        assertNull(getLib().yearAdd(makeDateAndTime("xxx"), makeNumber("12")));
        assertNull(getLib().yearAdd((LocalDate) null, makeNumber("12")));
    }

    @Test
    public void testYearDiff() {
        assertNull(getLib().yearDiff((LocalDate) null, null));
        assertNull(getLib().yearDiff(null, makeDate("2015-05-05")));
        assertNull(getLib().yearDiff(makeDate("2015-01-05"), null));

        assertEquals(makeNumber("0"), getLib().yearDiff(makeDate("2015-01-05"), makeDate("2015-01-05")));
        assertEquals(makeNumber("1"), getLib().yearDiff(makeDate("2015-01-05"), makeDate("2016-05-05")));
        assertEquals(makeNumber("1"), getLib().yearDiff(makeDate("2015-01-05"), makeDate("2016-01-05")));
        assertEquals(makeNumber("-1"), getLib().yearDiff(makeDate("2016-01-05"), makeDate("2015-01-05")));
        assertEquals(makeNumber("0"), getLib().yearDiff(makeDate("2016-01-05"), makeDate("2015-05-05")));

        assertEquals(makeNumber("0"), getLib().yearDiff(makeDateAndTime("2015-01-01T12:15:00.000+01:00"), makeDateAndTime("2015-01-05T15:15:00.000+01:00")));
        assertEquals(makeNumber("1"), getLib().yearDiff(makeDateAndTime("2015-01-05T12:15:00.000+01:00"), makeDateAndTime("2016-05-05T15:15:00.000+01:00")));
        assertEquals(makeNumber("1"), getLib().yearDiff(makeDateAndTime("2015-01-05T12:15:00.000+01:00"), makeDateAndTime("2016-01-05T15:15:00.000+01:00")));
        assertEquals(makeNumber("-1"), getLib().yearDiff(makeDateAndTime("2016-01-05T12:15:00.000+01:00"), makeDateAndTime("2015-01-05T15:15:00.000+01:00")));
        assertEquals(makeNumber("0"), getLib().yearDiff(makeDateAndTime("2016-01-05T12:15:00.000+01:00"), makeDateAndTime("2015-05-05T15:15:00.000+01:00")));
    }
}

