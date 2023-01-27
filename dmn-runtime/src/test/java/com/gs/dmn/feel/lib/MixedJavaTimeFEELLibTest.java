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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZonedDateTime;

import static org.junit.Assert.*;

public class MixedJavaTimeFEELLibTest extends BaseStandardFEELLibTest<BigDecimal, LocalDate, OffsetTime, ZonedDateTime, Duration> {
    @Override
    protected MixedJavaTimeFEELLib getLib() {
        return new MixedJavaTimeFEELLib();
    }

    //
    // Date operators
    //
    @Test
    public void testDateSubtract() {
        super.testDateSubtract();

        // Subtraction is undefined for the case where only one of the values has a timezone.
        assertEqualsDateTime("P1DT0H0M0S", getLib().dateSubtract(makeDate("2021-01-02"), makeDateAndTime("2021-01-01T00:00:00")));
    }

    @Test
    public void testDateAddDuration() {
        super.testDateAddDuration();

        assertEqualsDateTime("2016-08-02", getLib().dateAddDuration(makeDate("2016-08-01"), makeDuration("P1D")));
        assertEqualsDateTime("2016-09-01", getLib().dateAddDuration(makeDate("2016-08-31"), makeDuration("P1D")));

        assertEqualsDateTime("2021-01-02", getLib().dateAddDuration(makeDate("2021-01-01"), makeDuration("PT36H")));
        assertEqualsDateTime("2021-01-01", getLib().dateAddDuration(makeDate("2021-01-01"), makeDuration("PT1H")));
        assertEqualsDateTime("2021-01-01", getLib().dateAddDuration(makeDate("2021-01-01"), makeDuration("PT1M")));
        assertEqualsDateTime("2021-01-01", getLib().dateAddDuration(makeDate("2021-01-01"), makeDuration("PT1S")));
    }

    @Test
    public void testDateSubtractDuration() {
        super.testDateSubtractDuration();

        assertEqualsDateTime("2016-08-01", getLib().dateSubtractDuration(makeDate("2016-08-02"), makeDuration("P1D")));
        assertEqualsDateTime("2016-07-31", getLib().dateSubtractDuration(makeDate("2016-08-01"), makeDuration("P1D")));

        assertEqualsDateTime("2020-12-30", getLib().dateSubtractDuration(makeDate("2021-01-01"), makeDuration("PT36H")));
        assertEqualsDateTime("2021-01-01", getLib().dateSubtractDuration(makeDate("2021-01-02"), makeDuration("PT1H")));
        assertEqualsDateTime("2021-01-01", getLib().dateSubtractDuration(makeDate("2021-01-02"), makeDuration("PT1M")));
        assertEqualsDateTime("2021-01-01", getLib().dateSubtractDuration(makeDate("2021-01-02"), makeDuration("PT1S")));
        assertEqualsDateTime("2020-12-31", getLib().dateSubtractDuration(makeDate("2021-01-02"), makeDuration("PT25H")));
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
        assertFalse(getLib().timeIs(makeTime("00:00:00Etc/UTC"), makeTime("00:00:00@Europe/Paris")));
    }

    @Test
    public void testTimeSubtract() {
        super.testTimeSubtract();

        assertEquals(makeDuration("-PT1H"), getLib().timeSubtract(makeTime("10:10:10@Australia/Melbourne"), makeTime("11:10:10@Australia/Melbourne")));
        assertEquals(makeDuration("PT1H"), getLib().timeSubtract(makeTime("10:10:10@Australia/Melbourne"), makeTime("09:10:10@Australia/Melbourne")));
    }

    @Test
    public void testTimeAddDuration() {
        super.testTimeAddDuration();

        assertEqualsDateTime("10:15:00+10:00", getLib().timeAddDuration(makeTime("10:15:00@Australia/Melbourne"), makeDuration("P1D")));
        assertEqualsDateTime("11:15:00+10:00", getLib().timeAddDuration(makeTime("10:15:00@Australia/Melbourne"), makeDuration("PT1H")));
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

    @Test
    public void testDateTimeSubtract() {
        super.testDateTimeSubtract();

        // Subtraction is undefined for the case where only one of the values has a timezone.
        assertEqualsDateTime("P0DT23H0M0S", getLib().dateTimeSubtract(makeDateAndTime("2021-01-02T10:10:10@Europe/Paris"), makeDateAndTime("2021-01-01T10:10:10")));
        assertEqualsDateTime("-P0DT23H0M0S", getLib().dateTimeSubtract(makeDateAndTime("2021-01-01T10:10:10"), makeDateAndTime("2021-01-02T10:10:10@Europe/Paris")));
        assertEqualsDateTime("P0DT22H0M0S", getLib().dateTimeSubtract(makeDateAndTime("2021-01-02T10:10:10+02:00"), makeDateAndTime("2021-01-01T10:10:10")));
        assertEqualsDateTime("-P0DT22H0M0S", getLib().dateTimeSubtract(makeDateAndTime("2021-01-01T10:10:10"), makeDateAndTime("2021-01-02T10:10:10+02:00")));
        assertEqualsDateTime("P0DT23H0M0S", getLib().dateTimeSubtract(makeDateAndTime("2021-01-02T10:10:10+01:00"), makeDateAndTime("2021-01-01T10:10:10")));

        // Subtraction is undefined for the case where only one of the values has a timezone.
        assertEqualsDateTime("-P1DT0H0M0S", getLib().dateTimeSubtract(makeDateAndTime("2021-01-01T00:00:00"), makeDate("2021-01-02")));
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
        assertEqualsDateTime("00:00:00Z", getLib().time(makeDate("2017-08-10")));
        assertEqualsDateTime("11:00:00Z", getLib().time(makeDateAndTime("2016-08-01T11:00:00Z")));

        //
        // conversion from numbers
        //
        assertEqualsDateTime("12:00:00Z", getLib().time(makeNumber("12"), makeNumber("00"), makeNumber("00"), null));

        //
        // conversion from date, time and date time
        //
        assertEqualsDateTime("00:00:00Z", getLib().time(makeDate("2017-08-10")));
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
    public void testDuration() {
        super.testDuration();

        assertEqualsDateTime("P1Y8M", getLib().duration("P1Y8M"));
        assertEqualsDateTime("P999999999M", getLib().duration("P999999999M"));
        assertEqualsDateTime("-P999999999M", getLib().duration("-P999999999M"));

        assertEqualsDateTime("P2DT20H", getLib().duration("P2DT20H"));
        assertEqualsDateTime("-PT2H", getLib().duration("-PT2H"));
        assertEqualsDateTime("PT0S", getLib().duration("PT0.S"));

        assertNull(getLib().duration("P1Y0M2DT6H58M59.000S"));

        // Overflow in duration(from)
        assertEquals("P11999999988M", getLib().duration("P11999999988M").toString());
        assertEquals("P2129706043D", getLib().duration("P2129706043D").toString());
    }

    @Override
    @Test
    public void testYearsAndMonthsDuration() {
        super.testYearsAndMonthsDuration();

        assertEqualsDateTime("P0Y0M", getLib().yearsAndMonthsDuration(makeDateAndTime("2015-12-24T12:15:00.000+01:00"), makeDateAndTime("2015-12-24T12:15:00.000+01:00")));
        assertEqualsDateTime("P1Y0M", getLib().yearsAndMonthsDuration(makeDateAndTime("2015-12-24T00:00:01-01:00"), makeDateAndTime("2016-12-24T23:59:00-08:00")));
        assertEqualsDateTime("P1Y2M", getLib().yearsAndMonthsDuration(makeDateAndTime("2016-09-30T23:25:00"), makeDateAndTime("2017-12-28T12:12:12")));
        assertEqualsDateTime("P7Y6M", getLib().yearsAndMonthsDuration(makeDateAndTime("2010-05-30T03:55:58"), makeDateAndTime("2017-12-15T00:59:59")));
        assertEqualsDateTime("-P4033Y2M", getLib().yearsAndMonthsDuration(makeDateAndTime("2014-12-31T23:59:59"), makeDateAndTime("-2019-10-01T12:32:59")));
        assertEqualsDateTime("-P4035Y11M", getLib().yearsAndMonthsDuration(makeDateAndTime("2017-09-05T10:20:00-01:00"), makeDateAndTime("-2019-10-01T12:32:59+02:00")));

        assertEqualsDateTime("-P0Y11M", getLib().yearsAndMonthsDuration(makeDateAndTime("-2016-01-30T09:05:00"), makeDateAndTime("-2017-02-28T02:02:02")));
        assertEqualsDateTime("-P4033Y2M", getLib().yearsAndMonthsDuration(makeDateAndTime("2014-12-31T23:59:59"), makeDateAndTime("-2019-10-01T12:32:59")));
        assertEqualsDateTime("-P4035Y11M", getLib().yearsAndMonthsDuration(makeDateAndTime("2017-09-05T10:20:00-01:00"), makeDateAndTime("-2019-10-01T12:32:59+02:00")));
        assertEqualsDateTime("P2Y0M", getLib().yearsAndMonthsDuration(makeDateAndTime("2017-09-05T10:20:00+05:00"), makeDateAndTime("2019-10-01T12:32:59")));
        assertEqualsDateTime("P1Y0M", getLib().yearsAndMonthsDuration(makeDateAndTime("2017-09-05T10:20:00@Etc/UTC"), makeDateAndTime("2018-10-01T23:59:59")));
        assertEqualsDateTime("P4Y0M", getLib().yearsAndMonthsDuration(makeDateAndTime("2011-08-25T15:59:59@Europe/Paris"), makeDateAndTime("2015-08-25T15:20:59+02:00")));
        assertEqualsDateTime("P2Y9M", getLib().yearsAndMonthsDuration(makeDateAndTime("2015-12-31T23:59:59.9999999"), makeDateAndTime("2018-10-01T12:32:59.111111")));
        assertEqualsDateTime("P3Y0M", getLib().yearsAndMonthsDuration(makeDateAndTime("2016-09-05T22:20:55.123456+05:00"), makeDateAndTime("2019-10-01T12:32:59.32415645")));
        assertEqualsDateTime("P2Y0M", getLib().yearsAndMonthsDuration(makeDateAndTime("2014-12-31T23:59:59"), makeDateAndTime("2016-12-31T00:00:01")));
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
        assertEquals("999999999-12-31", getLib().string(makeDate("999999999-12-31")));
        assertEquals("-999999999-12-31", getLib().string(makeDate("-999999999-12-31")));
        assertEquals("999999999-12-31", getLib().string(getLib().date(makeNumber(999999999), makeNumber(12), makeNumber(31))));
        assertEquals("-999999999-12-31", getLib().string(getLib().date(makeNumber(-999999999), makeNumber(12), makeNumber(31))));

        // test time
        assertEquals("11:00:01Z", getLib().string(makeTime("11:00:01")));
        assertEquals("11:00:01Z", getLib().string(makeTime("11:00:01Z")));
        assertEquals("00:01:00Z", getLib().string(makeTime("00:01:00@Etc/UTC")));
        assertEquals("00:01:00+01:00", getLib().string(makeTime("00:01:00@Europe/Paris")));
        assertEquals("10:20:00+02:00", getLib().string(getLib().time(makeDateAndTime("2017-08-10T10:20:00@Europe/Paris"))));
        assertEquals("11:20:00+06:00", getLib().string(getLib().time(makeDateAndTime("2017-09-04T11:20:00@Asia/Dhaka"))));
        assertEquals("11:59:45+02:45:55", getLib().string(getLib().time(makeNumber(11), makeNumber(59), makeNumber(45), getLib().duration("PT2H45M55S"))));
        assertEquals("11:59:45-02:45:55", getLib().string(getLib().time(makeNumber(11), makeNumber(59), makeNumber(45), getLib().duration("-PT2H45M55S"))));
        assertEquals("00:00:00Z", getLib().string(getLib().time(makeDate("2017-08-10"))));

        // test date time
        assertEquals("2016-08-01T11:00:01Z", getLib().string(makeDateAndTime("2016-08-01T11:00:01")));
        assertEquals("2016-08-01T11:00:01Z", getLib().string(makeDateAndTime("2016-08-01T11:00:01Z")));
        assertEquals("99999-12-31T11:22:33Z", getLib().string(makeDateAndTime("99999-12-31T11:22:33")));
        assertEquals("-99999-12-31T11:22:33Z", getLib().string(makeDateAndTime("-99999-12-31T11:22:33")));
        assertEquals("2011-12-31T10:15:30@Europe/Paris", getLib().string(makeDateAndTime("2011-12-31T10:15:30@Europe/Paris")));
        assertEquals("2011-12-31T10:15:30@Etc/UTC", getLib().string(makeDateAndTime("2011-12-31T10:15:30@Etc/UTC")));
        assertEquals("2011-12-31T10:15:30.987@Europe/Paris", getLib().string(makeDateAndTime("2011-12-31T10:15:30.987@Europe/Paris")));
        assertEquals("2011-12-31T10:15:30.123456789@Europe/Paris", getLib().string(makeDateAndTime("2011-12-31T10:15:30.123456789@Europe/Paris")));
        assertEquals("999999999-12-31T23:59:59.999999999@Europe/Paris", getLib().string(makeDateAndTime("999999999-12-31T23:59:59.999999999@Europe/Paris")));
        assertEquals("-999999999-12-31T23:59:59.999999999+02:00", getLib().string(makeDateAndTime("-999999999-12-31T23:59:59.999999999+02:00")));
        assertEquals("2017-01-01T23:59:01+01:00", getLib().string(getLib().dateAndTime(makeDate("2017-01-01"), makeTime("23:59:01@Europe/Paris"))));
        assertEquals("2017-01-01T23:59:01.123456789+01:00", getLib().string(getLib().dateAndTime(makeDate("2017-01-01"), makeTime("23:59:01.123456789@Europe/Paris"))));
        assertEquals("2017-09-05T09:15:30.987654321+01:00", getLib().string(getLib().dateAndTime(makeDateAndTime("2017-09-05T10:20:00"), makeTime("09:15:30.987654321@Europe/Paris"))));
        assertEquals("2017-09-05T09:15:30.987654321+01:00", getLib().string(getLib().dateAndTime(makeDateAndTime("2017-09-05T10:20:00-01:00"), makeTime("09:15:30.987654321@Europe/Paris"))));
        assertEquals("2017-09-05T09:15:30.987654321+01:00", getLib().string(getLib().dateAndTime(makeDateAndTime("2017-09-05T10:20:00@Europe/Paris"), makeTime("09:15:30.987654321@Europe/Paris"))));
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
        assertNull(getLib().hour(null));
        assertEqualsNumber(makeNumber("12"), getLib().hour(makeTime("12:01:02Z")));

        assertNull(getLib().minute(null));
        assertEqualsNumber(makeNumber("1"), getLib().minute(makeTime("12:01:02Z")));

        assertNull(getLib().second(null));
        assertEqualsNumber(makeNumber("2"), getLib().second(makeTime("12:01:02Z")));

        assertNull(getLib().timeOffset(null));
        assertEquals(makeDuration("P0DT0H0M0.000S"), getLib().timeOffset(makeTime("12:01:02")));
        assertEquals(makeDuration("PT1H"), getLib().timeOffset(makeTime("12:01:02+01:00")));
        assertEquals(makeDuration("P0DT0H0M0.000S"), getLib().timeOffset(makeTime("12:01:02Z")));
        assertEquals(makeDuration("P0DT0H0M0.000S"), getLib().timeOffset(makeTime("12:01:02@Etc/UTC")));
        assertEquals(makeDuration("P0DT1H0M0S"), getLib().timeOffset(makeTime("12:01:02@Europe/Paris")));

        assertNull(getLib().timezone(null));
        assertEquals("Z", getLib().timezone(makeTime("12:01:02")));
        assertEquals("+01:00", getLib().timezone(makeTime("12:01:02+01:00")));
        assertEquals("Z", getLib().timezone(makeTime("12:01:02Z")));
        assertEquals("Z", getLib().timezone(makeTime("12:01:02@Etc/UTC")));
        assertEquals("+01:00", getLib().timezone(makeTime("12:01:02@Europe/Paris")));
    }

    //
    // Date and time properties
    //
    @Override
    @Test
    public void testDateAndTimeProperties() {
        assertNull(getLib().year(null));
        assertEqualsNumber(makeNumber("2018"), getLib().year(makeDateAndTime("2018-12-10T12:01:02Z")));

        assertNull(getLib().month(null));
        assertEqualsNumber(makeNumber("12"), getLib().month(makeDateAndTime("2018-12-10T12:01:02Z")));

        assertNull(getLib().day(null));
        assertEqualsNumber(makeNumber("10"), getLib().day(makeDateAndTime("2018-12-10T12:01:02Z")));

        assertNull(getLib().weekday(null));
        assertEqualsNumber(makeNumber("1"), getLib().weekday(makeDateAndTime("2018-12-10T12:01:02Z")));

        assertNull(getLib().hour(null));
        assertEqualsNumber(makeNumber("12"), getLib().hour(makeDateAndTime("2018-12-10T12:01:02Z")));

        assertNull(getLib().minute(null));
        assertEqualsNumber(makeNumber("1"), getLib().minute(makeDateAndTime("2018-12-10T12:01:02Z")));

        assertNull(getLib().second(null));
        assertEqualsNumber(makeNumber("2"), getLib().second(makeDateAndTime("2018-12-10T12:01:02Z")));

        assertNull(getLib().timeOffset(null));
        assertEquals(makeDuration("P0DT0H0M0.000S"), getLib().timeOffset(makeDateAndTime("2018-12-10T12:01:02")));
        assertEquals(makeDuration("PT1H"), getLib().timeOffset(makeDateAndTime("2018-12-10T12:01:02+01:00")));
        assertEquals(makeDuration("P0DT0H0M0.000S"), getLib().timeOffset(makeDateAndTime("2018-12-10T12:01:02Z")));
        assertEquals(makeDuration("P0DT0H0M0.000S"), getLib().timeOffset(makeDateAndTime("2018-12-10T12:01:02@Etc/UTC")));
        assertEquals(makeDuration("PT1H"), getLib().timeOffset(makeDateAndTime("2018-12-10T12:01:02@Europe/Paris")));

        assertNull(getLib().timezone(null));
        assertEquals("Z", getLib().timezone(makeDateAndTime("2018-12-10T12:01:02")));
        assertEquals("+01:00", getLib().timezone(makeDateAndTime("2018-12-10T12:01:02+01:00")));
        assertEquals("Z", getLib().timezone(makeDateAndTime("2018-12-10T12:01:02Z")));
        assertEquals("Etc/UTC", getLib().timezone(makeDateAndTime("2018-12-10T12:01:02@Etc/UTC")));
        assertEquals("Europe/Paris", getLib().timezone(makeDateAndTime("2018-12-10T12:01:02@Europe/Paris")));
    }
}
