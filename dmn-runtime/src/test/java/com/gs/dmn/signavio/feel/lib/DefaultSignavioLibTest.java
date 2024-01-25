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

import org.junit.jupiter.api.Test;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DefaultSignavioLibTest extends BaseSignavioLibTest<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar, Duration> {
    @Override
    protected DefaultSignavioLib getLib() {
        return new DefaultSignavioLib();
    }

    //
    // Date & time functions
    //
    @Test
    public void testDay() {
        assertNull(getLib().day(null));

        assertEqualsNumber(makeNumber("10"), getLib().day(makeDate("2015-12-10")));
        assertNull(getLib().day(makeDate("xxx")));
    }

    @Test
    public void testDayAdd() {
        assertNull(getLib().dayAdd(null, null));
        assertNull(getLib().dayAdd(null, makeNumber("360")));
        assertNull(getLib().dayAdd(makeDateAndTime("2015-12-24T12:15:00.000+01:00"), null));

        assertEqualsDateTime("2015-12-25T12:15:00.001+01:00", getLib().dayAdd(makeDateAndTime("2015-12-24T12:15:00.001+01:00"), makeNumber("1")));
        assertEqualsDateTime("2016-12-18T12:15:00.001+01:00", getLib().dayAdd(makeDateAndTime("2015-12-24T12:15:00.001+01:00"), makeNumber("360")));
        assertEqualsDateTime("2015-12-25", getLib().dayAdd(makeDate("2015-12-24"), makeNumber("1")));
        assertEqualsDateTime("2016-12-18", getLib().dayAdd(makeDate("2015-12-24"), makeNumber("360")));
        assertEqualsDateTime("2015-12-23", getLib().dayAdd(makeDate("2015-12-24"), makeNumber("-1")));
        assertNull(getLib().dayAdd(makeDateAndTime("x"), makeNumber("360")));
    }

    @Test
    public void testDayDiff() {
        assertNull(getLib().dayDiff(null, null));
        assertNull(getLib().dayDiff(null, makeDate("2015-12-25")));
        assertNull(getLib().dayDiff(makeDate("2015-12-25"), null));
        assertNull(getLib().dayDiff(null, makeDateAndTime("2015-12-25T00:00:00")));
        assertNull(getLib().dayDiff(makeDateAndTime("2015-12-24T00:00:00"), null));

        assertEquals(makeNumber("1"), getLib().dayDiff(makeDate("2015-12-24"), makeDate("2015-12-25")));
        assertEquals(makeNumber("0"), getLib().dayDiff(makeDate("2015-12-24"), makeDate("2015-12-24")));
        assertEquals(makeNumber("-1"), getLib().dayDiff(makeDate("2015-12-24"), makeDate("2015-12-23")));

        assertEquals(makeNumber("1"), getLib().dayDiff(makeDateAndTime("2015-12-24T00:00:00"), makeDateAndTime("2015-12-25T00:00:00")));
        assertEquals(makeNumber("0"), getLib().dayDiff(makeDateAndTime("2015-12-24T12:25:00"), makeDateAndTime("2015-12-24T00:00:00")));
        assertEquals(makeNumber("-1"), getLib().dayDiff(makeDateAndTime("2015-12-24T00:00:00"), makeDateAndTime("2015-12-23T00:00:00")));
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
        assertNull(getLib().hourDiff(null, null));
        assertNull(getLib().hourDiff(null, makeTime("12:15:00.000+01:00")));
        assertNull(getLib().hourDiff(makeTime("12:15:00.000+01:00"), null));

        assertEquals(makeNumber("0"), getLib().hourDiff(makeTime("12:15:00.000+01:00"), makeTime("12:15:00.000+01:00")));
        assertEquals(makeNumber("3"), getLib().hourDiff(makeDateAndTime("2015-12-24T12:15:00.000+01:00"), makeDateAndTime("2015-12-24T15:15:00.000+01:00")));
        assertEquals(makeNumber("27"), getLib().hourDiff(makeDateAndTime("2015-12-24T12:15:00.000+01:00"), makeDateAndTime("2015-12-25T15:15:00.000+01:00")));
        assertEquals(makeNumber("-24"), getLib().hourDiff(makeDateAndTime("2015-12-25T12:15:00.000+01:00"), makeDateAndTime("2015-12-24T12:15:00.000+01:00")));
        assertEquals(makeNumber("-27"), getLib().hourDiff(makeDateAndTime("2015-12-25T15:15:00.000+01:00"), makeDateAndTime("2015-12-24T12:15:00.000+01:00")));
        assertEquals(makeNumber("8757"), getLib().hourDiff(makeDateAndTime("2015-12-25T15:15:00.000+01:00"), makeDateAndTime("2016-12-24T12:15:00.000+01:00")));
    }

    @Test
    public void testMinute() {
        assertNull(getLib().minute(null));

        assertEquals(makeNumber("15"), getLib().minute(makeTime("12:15:00.000+01:00")));
        assertEquals(makeNumber("15"), getLib().minute(makeDateAndTime("2015-12-24T12:15:00.000+01:00")));
    }

    @Test
    public void testMinuteDiff() {
        assertNull(getLib().minutesDiff(null, null));
        assertNull(getLib().minutesDiff(null, makeTime("12:15:00.000+01:00")));
        assertNull(getLib().minutesDiff(makeTime("12:15:00.000+01:00"), null));

        assertEquals(makeNumber("0"), getLib().minutesDiff(makeTime("12:15:00.000+01:00"), makeTime("12:15:00.000+01:00")));
        assertEquals(makeNumber("5"), getLib().minutesDiff(makeDateAndTime("2015-12-24T12:15:00.000+01:00"), makeDateAndTime("2015-12-24T12:20:00.000+01:00")));
        assertEquals(makeNumber("65"), getLib().minutesDiff(makeDateAndTime("2015-12-24T12:15:00.000+01:00"), makeDateAndTime("2015-12-24T13:20:00.000+01:00")));
        assertEquals(makeNumber("-65"), getLib().minutesDiff(makeDateAndTime("2015-12-24T13:20:00.000+01:00"), makeDateAndTime("2015-12-24T12:15:00.000+01:00")));
        assertEquals(makeNumber("525420"), getLib().minutesDiff(makeDateAndTime("2015-12-25T15:15:00.000+01:00"), makeDateAndTime("2016-12-24T12:15:00.000+01:00")));
    }

    @Test
    public void testMonth() {
        assertNull(getLib().month(null));

        assertEquals(makeNumber("12"), getLib().month(makeDate("2015-12-02")));
        assertEquals(makeNumber("12"), getLib().month(makeDateAndTime("2015-12-24T12:15:00.000+01:00")));
    }

    @Test
    public void testMonthAdd() {
        assertNull(getLib().monthAdd(null, null));
        assertNull(getLib().monthAdd(null, makeNumber("1")));
        assertNull(getLib().monthAdd(makeDate("2015-01-05"), null));

        assertEqualsDateTime("2015-02-05", getLib().monthAdd(makeDate("2015-01-05"), makeNumber("1")));
        assertEqualsDateTime("2016-01-05", getLib().monthAdd(makeDate("2015-01-05"), makeNumber("12")));
        assertEqualsDateTime("2014-11-05", getLib().monthAdd(makeDate("2015-01-05"), makeNumber("-2")));
        assertEqualsDateTime("2015-02-05T12:15:00.001+01:00", getLib().monthAdd(makeDateAndTime("2015-01-05T12:15:00.001+01:00"), makeNumber("1")));
        assertEqualsDateTime("2016-01-05T12:15:00.001+01:00", getLib().monthAdd(makeDateAndTime("2015-01-05T12:15:00.001+01:00"), makeNumber("12")));
        assertEqualsDateTime("2014-11-05T12:15:00.001+01:00", getLib().monthAdd(makeDateAndTime("2015-01-05T12:15:00.001+01:00"), makeNumber("-2")));
        assertNull(getLib().monthAdd(makeDateAndTime("xxx"), makeNumber("12")));
        assertNull(getLib().monthAdd(null, makeNumber("12")));
    }

    @Test
    public void testMonthDiff() {
        assertNull(getLib().monthDiff(null, null));
        assertNull(getLib().monthDiff(null, makeDate("2015-01-05")));
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
        XMLGregorianCalendar actual = getLib().now();
        Calendar expected = Calendar.getInstance();
        assertEquals(expected.get(Calendar.YEAR), actual.getYear());
        assertEquals(expected.get(Calendar.MONTH) + 1, actual.getMonth());
        assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.getDay());
    }

    @Test
    public void testToday() {
        XMLGregorianCalendar actual = getLib().today();
        LocalDate expected = LocalDate.now();
        assertEquals(expected.getYear(), actual.getYear());
        assertEquals(expected.getMonthValue(), actual.getMonth());
        assertEquals(expected.getDayOfMonth(), actual.getDay());
    }


    @Test
    public void testWeekday() {
        assertNull(getLib().weekday(null));

        assertEquals(makeNumber("7"), getLib().weekday(makeDate("2016-12-25")));
        assertEquals(makeNumber("6"), getLib().weekday(makeDate("0003-02-01")));
        assertEquals(makeNumber("2"), getLib().weekday(makeDate("2016-11-01")));
        assertEquals(makeNumber("7"), getLib().weekday(makeDateAndTime("2016-11-20T12:15:00.000+01:00")));
    }

    @Test
    public void testYear() {
        assertNull(getLib().year(null));

        assertEquals(makeNumber("2016"), getLib().year(makeDate("2016-11-01")));
        assertEquals(makeNumber("2015"), getLib().year(makeDateAndTime("2015-11-20T12:15:00.000+01:00")));
    }

    @Test
    public void testYearAdd() {
        assertNull(getLib().yearAdd(null, null));
        assertNull(getLib().yearAdd(null, makeNumber("1")));
        assertNull(getLib().yearAdd(makeDate("2015-01-05"), null));

        assertEqualsDateTime("2016-01-05", getLib().yearAdd(makeDate("2015-01-05"), makeNumber("1")));
        assertEqualsDateTime("2027-01-05", getLib().yearAdd(makeDate("2015-01-05"), makeNumber("12")));
        assertEqualsDateTime("2016-01-05T12:15:00.001+01:00", getLib().yearAdd(makeDateAndTime("2015-01-05T12:15:00.001+01:00"), makeNumber("1")));
        assertEqualsDateTime("2027-01-05T12:15:00.001+01:00", getLib().yearAdd(makeDateAndTime("2015-01-05T12:15:00.001+01:00"), makeNumber("12")));
        assertNull(getLib().yearAdd(makeDateAndTime("xxx"), makeNumber("12")));
        assertNull(getLib().yearAdd(null, makeNumber("12")));
    }

    @Test
    public void testYearDiff() {
        assertNull(getLib().yearDiff(null, null));
        assertNull(getLib().yearDiff(null, makeDate("2015-01-05")));
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

    //
    // Time properties
    //
    @Override
    @Test
    public void testTimeProperties() {
        assertNull(getLib().hour(null));
        assertEqualsNumber(makeNumber("12"), getLib().hour(getLib().time("12:01:02Z")));

        assertNull(getLib().minute(null));
        assertEqualsNumber(makeNumber("1"), getLib().minute(getLib().time("12:01:02Z")));
    }

    //
    // Date and time properties
    //
    @Override
    @Test
    public void testDateAndTimeProperties() {
        assertNull(getLib().year(null));
        assertEqualsNumber(makeNumber("2018"), getLib().year(getLib().dateAndTime("2018-12-10T12:01:02Z")));

        assertNull(getLib().month(null));
        assertEqualsNumber(makeNumber("12"), getLib().month(getLib().dateAndTime("2018-12-10T12:01:02Z")));

        assertNull(getLib().day(null));
        assertEqualsNumber(makeNumber("10"), getLib().day(getLib().dateAndTime("2018-12-10T12:01:02Z")));

        assertNull(getLib().weekday(null));
        assertEqualsNumber(makeNumber("1"), getLib().weekday(getLib().dateAndTime("2018-12-10T12:01:02Z")));

        assertNull(getLib().hour(null));
        assertEqualsNumber(makeNumber("12"), getLib().hour(getLib().dateAndTime("2018-12-10T12:01:02Z")));

        assertNull(getLib().minute(null));
        assertEqualsNumber(makeNumber("1"), getLib().minute(getLib().dateAndTime("2018-12-10T12:01:02Z")));
    }
}
