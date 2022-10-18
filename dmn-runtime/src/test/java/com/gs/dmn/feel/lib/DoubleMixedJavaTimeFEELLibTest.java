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

import org.junit.Test;

import javax.xml.datatype.Duration;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZonedDateTime;

import static org.junit.Assert.*;

public class DoubleMixedJavaTimeFEELLibTest extends BaseStandardFEELLibTest<Double, LocalDate, OffsetTime, ZonedDateTime, Duration> {
    @Override
    protected DoubleMixedJavaTimeFEELLib getLib() {
        return new DoubleMixedJavaTimeFEELLib();
    }

    //
    // Time operators
    //
    @Override
    @Test
    public void testTimeIs() {
        super.testTimeIs();

        // times with equivalent offset and zone id are not is()
        assertTrue(getLib().timeIs(makeTime("12:00:00"), makeTime("12:00:00Z")));
        assertTrue(getLib().timeIs(makeTime("12:00:00"), makeTime("12:00:00+00:00")));
        assertTrue(getLib().timeIs(makeTime("00:00:00+00:00"), makeTime("00:00:00@Etc/UTC")));
        assertTrue(getLib().timeIs(makeTime("00:00:00Z"), makeTime("00:00:00+00:00")));
        assertTrue(getLib().timeIs(makeTime("00:00:00Z"), makeTime("00:00:00@Etc/UTC")));
    }

    //
    // Date time operators
    //
    @Override
    @Test
    public void testDateTimeIs() {
        super.testDateTimeIs();

        // datetime with equivalent offset and zone id are not is()
        assertTrue(getLib().dateTimeIs(makeDateAndTime("2018-12-08T12:00:00"), makeDateAndTime("2018-12-08T12:00:00+00:00")));
        assertTrue(getLib().dateTimeIs(makeDateAndTime("2018-12-08T00:00:00+00:00"), makeDateAndTime("2018-12-08T00:00:00@Etc/UTC")));
        assertTrue(getLib().dateTimeIs(makeDateAndTime("2018-12-08T12:00:00Z"), makeDateAndTime("2018-12-08T12:00:00+00:00")));
        assertTrue(getLib().dateTimeIs(makeDateAndTime("2018-12-08T00:00:00Z"), makeDateAndTime("2018-12-08T00:00:00@Etc/UTC")));
    }

    //
    // Conversion functions
    //
    @Override
    @Test
    public void testDate() {
        super.testDate();

        assertEqualsDateTime("2016-08-01", getLib().date(makeDateAndTime("2016-08-01T12:00:00Z")));
    }

    @Override
    @Test
    public void testTime() {
        super.testTime();

        //
        // conversion from time, date and date time
        //
        assertEqualsDateTime("12:00:00Z", getLib().time(makeTime("12:00:00Z")));
        assertEqualsDateTime("00:00:00Z", getLib().time(getLib().date("2017-08-10")));
        assertEqualsDateTime("11:00:00Z", getLib().time(makeDateAndTime("2016-08-01T11:00:00Z")));

        //
        // conversion from numbers
        //
        assertEqualsDateTime("12:00:00Z", getLib().time(makeNumber("12"), makeNumber("00"), makeNumber("00"), null));

        //
        // conversion from date, time and date time
        //
        assertEqualsDateTime("00:00:00Z", getLib().time(getLib().date("2017-08-10")));
        assertEqualsDateTime("12:00:00Z", getLib().time(makeTime("12:00:00Z")));
        assertEqualsDateTime("11:00:00Z", getLib().time(makeDateAndTime("2016-08-01T11:00:00Z")));
    }

    @Override
    @Test
    public void testDateAndTime() {
        super.testDateAndTime();

        //
        // conversion from string
        //
        assertEqualsDateTime("2016-08-01T00:00:00Z@UTC", getLib().dateAndTime("2016-08-01"));

        // missing Z
        assertEqualsDateTime("-2016-01-30T09:05:00Z", getLib().dateAndTime("-2016-01-30T09:05:00"));
        assertEqualsDateTime("-2017-02-28T02:02:02Z", getLib().dateAndTime("-2017-02-28T02:02:02"));

        // with zone id
        assertEqualsDateTime("2011-12-03T10:15:30+01:00@Europe/Paris", getLib().dateAndTime("2011-12-03T10:15:30@Europe/Paris"));

        // year must be in the range [-999,999,999..999,999,999]
        assertEqualsDateTime("-999999999-12-31T11:22:33Z", getLib().dateAndTime("-999999999-12-31T11:22:33"));
        assertEqualsDateTime("999999999-12-31T11:22:33Z", getLib().dateAndTime("999999999-12-31T11:22:33"));
        assertNull(getLib().dateAndTime("-9999999991-12-31T11:22:33"));
        assertNull(getLib().dateAndTime("9999999991-12-31T11:22:33"));
    }

    @Override
    @Test
    public void testYearsAndMonthsDuration() {
        super.testYearsAndMonthsDuration();

        assertEqualsDateTime("P0Y0M", getLib().yearsAndMonthsDuration(makeDateAndTime("2015-12-24T12:15:00.000+01:00"), makeDateAndTime("2015-12-24T12:15:00.000+01:00")));
        assertEqualsDateTime("P1Y2M", getLib().yearsAndMonthsDuration(makeDateAndTime("2016-09-30T23:25:00"), makeDateAndTime("2017-12-28T12:12:12")));
        assertEqualsDateTime("P7Y6M", getLib().yearsAndMonthsDuration(makeDateAndTime("2010-05-30T03:55:58"), makeDateAndTime("2017-12-15T00:59:59")));
        assertEqualsDateTime("-P4033Y2M", getLib().yearsAndMonthsDuration(makeDateAndTime("2014-12-31T23:59:59"), makeDateAndTime("-2019-10-01T12:32:59")));
        assertEqualsDateTime("-P4035Y11M", getLib().yearsAndMonthsDuration(makeDateAndTime("2017-09-05T10:20:00-01:00"), makeDateAndTime("-2019-10-01T12:32:59+02:00")));
    }

    @Test
    public void testString() {
        assertEquals("null", getLib().string(null));

        // test number
        assertEquals("123.45", getLib().string(makeNumber("123.45")));

        // test string
        assertEquals("true", getLib().string(true));

        // test date
        assertEquals("2016-08-01", getLib().string(makeDate("2016-08-01")));
        assertEquals("999999999-12-31", getLib().string(getLib().date("999999999-12-31")));
        assertEquals("-999999999-12-31", getLib().string(getLib().date("-999999999-12-31")));
        assertEquals("999999999-12-31", getLib().string(getLib().date(makeNumber(999999999), makeNumber(12), makeNumber(31))));
        assertEquals("-999999999-12-31", getLib().string(getLib().date(makeNumber(-999999999), makeNumber(12), makeNumber(31))));

        // test time
        assertEquals("11:00:01Z", getLib().string(makeTime("11:00:01Z")));
        assertEquals("00:01:00Z", getLib().string(getLib().time("00:01:00@Etc/UTC")));
        assertEquals("00:01:00+01:00", getLib().string(getLib().time("00:01:00@Europe/Paris")));
        assertEquals("10:20:00+02:00", getLib().string(getLib().time(getLib().dateAndTime("2017-08-10T10:20:00@Europe/Paris"))));
        assertEquals("11:20:00+06:00", getLib().string(getLib().time(getLib().dateAndTime("2017-09-04T11:20:00@Asia/Dhaka"))));
        assertEquals("11:59:45+02:45:55", getLib().string(getLib().time(makeNumber(11), makeNumber(59), makeNumber(45), getLib().duration("PT2H45M55S"))));
        assertEquals("11:59:45-02:45:55", getLib().string(getLib().time(makeNumber(11), makeNumber(59), makeNumber(45), getLib().duration("-PT2H45M55S"))));
        assertEquals("00:00:00Z", getLib().string(getLib().time(getLib().date("2017-08-10"))));

        // test date time
        assertEquals("2016-08-01T11:00:01Z", getLib().string(makeDateAndTime("2016-08-01T11:00:01Z")));
        assertEquals("99999-12-31T11:22:33Z", getLib().string(getLib().dateAndTime("99999-12-31T11:22:33")));
        assertEquals("-99999-12-31T11:22:33Z", getLib().string(getLib().dateAndTime("-99999-12-31T11:22:33")));
        assertEquals("2011-12-31T10:15:30+01:00@Europe/Paris", getLib().string(getLib().dateAndTime("2011-12-31T10:15:30@Europe/Paris")));
        assertEquals("2011-12-31T10:15:30Z@Etc/UTC", getLib().string(getLib().dateAndTime("2011-12-31T10:15:30@Etc/UTC")));
        assertEquals("2011-12-31T10:15:30.987+01:00@Europe/Paris", getLib().string(getLib().dateAndTime("2011-12-31T10:15:30.987@Europe/Paris")));
        assertEquals("2011-12-31T10:15:30.123456789+01:00@Europe/Paris", getLib().string(getLib().dateAndTime("2011-12-31T10:15:30.123456789@Europe/Paris")));
        assertEquals("999999999-12-31T23:59:59.999999999+01:00@Europe/Paris", getLib().string(getLib().dateAndTime("999999999-12-31T23:59:59.999999999@Europe/Paris")));
        assertEquals("-999999999-12-31T23:59:59.999999999+02:00", getLib().string(getLib().dateAndTime("-999999999-12-31T23:59:59.999999999+02:00")));
        assertEquals("2017-01-01T23:59:01+01:00", getLib().string(getLib().dateAndTime(getLib().date("2017-01-01"), getLib().time("23:59:01@Europe/Paris"))));
        assertEquals("2017-01-01T23:59:01.123456789+01:00", getLib().string(getLib().dateAndTime(getLib().date("2017-01-01"), getLib().time("23:59:01.123456789@Europe/Paris"))));
        assertEquals("2017-09-05T09:15:30.987654321+01:00", getLib().string(getLib().dateAndTime(getLib().dateAndTime("2017-09-05T10:20:00"), getLib().time("09:15:30.987654321@Europe/Paris"))));
        assertEquals("2017-09-05T09:15:30.987654321+01:00", getLib().string(getLib().dateAndTime(getLib().dateAndTime("2017-09-05T10:20:00-01:00"), getLib().time("09:15:30.987654321@Europe/Paris"))));
        assertEquals("2017-09-05T09:15:30.987654321+01:00", getLib().string(getLib().dateAndTime(getLib().dateAndTime("2017-09-05T10:20:00@Europe/Paris"), getLib().time("09:15:30.987654321@Europe/Paris"))));
    }

    //
    // Extra conversion functions
    //
    @Override
    @Test
    public void testToDateTime() {
        super.testToDateTime();

        assertEqualsDateTime("2016-08-01T00:00:00Z@UTC", getLib().toDateTime(makeDate("2016-08-01")));
        assertEqualsDateTime("2016-08-01T12:00:00Z", getLib().toDateTime(makeDateAndTime("2016-08-01T12:00:00Z")));
    }

    //
    // Time properties
    //
    @Override
    @Test
    public void testTimeProperties() {
        assertNull(getLib().hour((OffsetTime) null));
        assertEqualsNumber(makeNumber("12"), getLib().hour(getLib().time("12:01:02Z")));

        assertNull(getLib().minute((OffsetTime) null));
        assertEqualsNumber(makeNumber("1"), getLib().minute(getLib().time("12:01:02Z")));

        assertNull(getLib().second((OffsetTime) null));
        assertEqualsNumber(makeNumber("2"), getLib().second(getLib().time("12:01:02Z")));

        assertNull(getLib().timeOffset((OffsetTime) null));
        assertEquals(getLib().duration("P0Y0M0DT0H0M0.000S"), getLib().timeOffset(getLib().time("12:01:02")));
        assertEquals(getLib().duration("PT1H"), getLib().timeOffset(getLib().time("12:01:02+01:00")));
        assertEquals(getLib().duration("P0Y0M0DT0H0M0.000S"), getLib().timeOffset(getLib().time("12:01:02Z")));
        assertEquals(getLib().duration("P0Y0M0DT0H0M0.000S"), getLib().timeOffset(getLib().time("12:01:02Z@Etc/UTC")));

        assertNull(getLib().timezone((OffsetTime) null));
        assertEquals("Z", getLib().timezone(getLib().time("12:01:02")));
        assertEquals("+01:00", getLib().timezone(getLib().time("12:01:02+01:00")));
        assertEquals("Z", getLib().timezone(getLib().time("12:01:02Z")));
        assertEquals("Z", getLib().timezone(getLib().time("12:01:02@Etc/UTC")));
        assertEquals("Z", getLib().timezone(getLib().time("12:01:02Z@Etc/UTC")));
    }

    //
    // Date and time properties
    //
    @Override
    @Test
    public void testDateAndTimeProperties() {
        assertNull(getLib().year((ZonedDateTime) null));
        assertEqualsNumber(makeNumber("2018"), getLib().year(getLib().dateAndTime("2018-12-10T12:01:02Z")));

        assertNull(getLib().month((ZonedDateTime) null));
        assertEqualsNumber(makeNumber("12"), getLib().month(getLib().dateAndTime("2018-12-10T12:01:02Z")));

        assertNull(getLib().day((ZonedDateTime) null));
        assertEqualsNumber(makeNumber("10"), getLib().day(getLib().dateAndTime("2018-12-10T12:01:02Z")));

        assertNull(getLib().weekday((ZonedDateTime) null));
        assertEqualsNumber(makeNumber("1"), getLib().weekday(getLib().dateAndTime("2018-12-10T12:01:02Z")));

        assertNull(getLib().hour((ZonedDateTime) null));
        assertEqualsNumber(makeNumber("12"), getLib().hour(getLib().dateAndTime("2018-12-10T12:01:02Z")));

        assertNull(getLib().minute((ZonedDateTime) null));
        assertEqualsNumber(makeNumber("1"), getLib().minute(getLib().dateAndTime("2018-12-10T12:01:02Z")));

        assertNull(getLib().second((ZonedDateTime) null));
        assertEqualsNumber(makeNumber("2"), getLib().second(getLib().dateAndTime("2018-12-10T12:01:02Z")));

        assertNull(getLib().timeOffset((ZonedDateTime) null));
        assertEquals(getLib().duration("P0Y0M0DT0H0M0.000S"), getLib().timeOffset(getLib().dateAndTime("2018-12-10T12:01:02")));
        assertEquals(getLib().duration("PT1H"), getLib().timeOffset(getLib().dateAndTime("2018-12-10T12:01:02+01:00")));
        assertEquals(getLib().duration("P0Y0M0DT0H0M0.000S"), getLib().timeOffset(getLib().dateAndTime("2018-12-10T12:01:02Z")));
        assertEquals(getLib().duration("P0Y0M0DT0H0M0.000S"), getLib().timeOffset(getLib().dateAndTime("2018-12-10T12:01:02Z@Etc/UTC")));

        assertNull(getLib().timezone((ZonedDateTime) null));
        assertEquals("Z", getLib().timezone(getLib().dateAndTime("2018-12-10T12:01:02")));
        assertEquals("+01:00", getLib().timezone(getLib().dateAndTime("2018-12-10T12:01:02+01:00")));
        assertEquals("Z", getLib().timezone(getLib().dateAndTime("2018-12-10T12:01:02Z")));
        assertEquals("Etc/UTC", getLib().timezone(getLib().dateAndTime("2018-12-10T12:01:02@Etc/UTC")));
        assertEquals("Etc/UTC", getLib().timezone(getLib().dateAndTime("2018-12-10T12:01:02Z@Etc/UTC")));
    }
}
