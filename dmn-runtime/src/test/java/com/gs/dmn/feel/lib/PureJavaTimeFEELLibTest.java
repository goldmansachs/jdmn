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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;

import static org.junit.Assert.*;

public class PureJavaTimeFEELLibTest extends BaseStandardFEELLibTest<BigDecimal, LocalDate, Temporal, Temporal, TemporalAmount> {
    @Override
    protected PureJavaTimeFEELLib getLib() {
        return new PureJavaTimeFEELLib();
    }

    //
    // Time operators
    //
    @Override
    @Test
    public void testTimeIs() {
        super.testTimeIs();

        // times with equivalent offset and zone id are not is()
        assertFalse(getLib().timeIs(makeTime("12:00:00"), makeTime("12:00:00+00:00")));
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
        assertFalse(getLib().dateTimeIs(makeDateAndTime("2018-12-08T12:00:00"), makeDateAndTime("2018-12-08T12:00:00+00:00")));
        assertFalse(getLib().dateTimeIs(makeDateAndTime("2018-12-08T00:00:00+00:00"), makeDateAndTime("2018-12-08T00:00:00@Etc/UTC")));
        assertTrue(getLib().dateTimeIs(makeDateAndTime("2018-12-08T12:00:00Z"), makeDateAndTime("2018-12-08T12:00:00+00:00")));
        assertFalse(getLib().dateTimeIs(makeDateAndTime("2018-12-08T00:00:00Z"), makeDateAndTime("2018-12-08T00:00:00@Etc/UTC")));
    }

    //
    // Conversion functions
    //
    @Override
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
        // conversion from numbers
        //
        assertNull(getLib().time(null, null, null, null));

        assertNull(getLib().time(
                makeNumber("12"), makeNumber("00"), makeNumber("00"),
                makeDuration("PT25H10M")));
        assertNull(getLib().time(
                makeNumber("12"), makeNumber("00"), makeNumber("00"),
                makeDuration("PT1H10M")));
        assertNull(getLib().time(
                makeNumber("12"), makeNumber("00"), makeNumber("00"),
                null));

        //
        // conversion from date, time and date time
        //
        assertEqualsDateTime("00:00:00Z", getLib().time(getLib().date("2017-08-10")));
        assertEqualsDateTime("12:00:00Z", getLib().time(makeTime("12:00:00Z")));
        assertEqualsDateTime("11:00:00Z", getLib().time(makeDateAndTime("2016-08-01T11:00:00Z")));
    }

    @Override
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

    @Override
    @Test
    public void testDuration() {
        assertEqualsDateTime("P1Y8M", getLib().duration("P1Y8M"));
        assertEqualsDateTime("PT68H", getLib().duration("P2DT20H"));
        assertEqualsDateTime("PT-2H", getLib().duration("-PT2H"));

        assertEqualsDateTime("P1Y8M", getLib().duration("P1Y8M"));
        assertEqualsDateTime("PT68H", getLib().duration("P2DT20H"));

        assertEqualsDateTime("P999999999M", getLib().duration("P999999999M"));
        assertEqualsDateTime("P-999999999M",getLib().duration("-P999999999M"));
        assertEqualsDateTime("PT8814H58M59S", getLib().duration("P1Y0M2DT6H58M59.000S"));
        // Overflow in duration(from)
        assertEqualsDateTime(null, getLib().duration("P11999999988M"));
        assertEqualsDateTime("PT51112945032H", getLib().duration("P2129706043D"));
    }

    @Override
    @Test
    public void testYearsAndMonthsDuration() {
        assertNull(getLib().yearsAndMonthsDuration(null, null));

        assertEqualsDateTime("P0D", getLib().yearsAndMonthsDuration(makeDate("2015-12-24"), makeDate("2015-12-24")));
        assertEqualsDateTime("P1Y2M", getLib().yearsAndMonthsDuration(makeDate("2016-09-30"), makeDate("2017-12-28")));
        assertEqualsDateTime("P7Y6M", getLib().yearsAndMonthsDuration(makeDate("2010-05-30"), makeDate("2017-12-15")));
        assertEqualsDateTime("P-4033Y-2M", getLib().yearsAndMonthsDuration(makeDate("2014-12-31"), makeDate("-2019-10-01")));
        assertEqualsDateTime("P-4035Y-11M", getLib().yearsAndMonthsDuration(makeDate("2017-09-05"), makeDate("-2019-10-01")));

        assertEqualsDateTime("P0D", getLib().yearsAndMonthsDuration(getLib().dateAndTime("2015-12-24T12:15:00.000+01:00"), getLib().dateAndTime("2015-12-24T12:15:00.000+01:00")));
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
        assertEquals("null", getLib().string(getLib().time(makeNumber(11), makeNumber(59), makeNumber(45), getLib().duration("PT2H45M55S"))));
        assertEquals("null", getLib().string(getLib().time(makeNumber(11), makeNumber(59), makeNumber(45), getLib().duration("-PT2H45M55S"))));
        assertEquals("00:00:00Z", getLib().string(getLib().time(getLib().date("2017-08-10"))));

        // test date time
        assertEquals("2016-08-01T11:00:01Z", getLib().string(makeDateAndTime("2016-08-01T11:00:01Z")));
        assertEquals("+99999-12-31T11:22:33", getLib().string(getLib().dateAndTime("99999-12-31T11:22:33")));
        assertEquals("-99999-12-31T11:22:33", getLib().string(getLib().dateAndTime("-99999-12-31T11:22:33")));
        assertEquals("2011-12-31T10:15:30+01:00@Europe/Paris", getLib().string(getLib().dateAndTime("2011-12-31T10:15:30@Europe/Paris")));
        assertEquals("2011-12-31T10:15:30Z@Etc/UTC", getLib().string(getLib().dateAndTime("2011-12-31T10:15:30@Etc/UTC")));
        assertEquals("2011-12-31T10:15:30.987+01:00@Europe/Paris", getLib().string(getLib().dateAndTime("2011-12-31T10:15:30.987@Europe/Paris")));
        assertEquals("2011-12-31T10:15:30.123456789+01:00@Europe/Paris", getLib().string(getLib().dateAndTime("2011-12-31T10:15:30.123456789@Europe/Paris")));
        assertEquals("999999999-12-31T23:59:59.999999999+01:00@Europe/Paris", getLib().string(getLib().dateAndTime("999999999-12-31T23:59:59.999999999@Europe/Paris")));
        assertEquals("-999999999-12-31T23:59:59.999999999+02:00", getLib().string(getLib().dateAndTime("-999999999-12-31T23:59:59.999999999+02:00")));
        assertEquals("2017-01-01T23:59:01+01:00", getLib().string(getLib().dateAndTime(getLib().date("2017-01-01"), getLib().time("23:59:01@Europe/Paris"))));
        assertEquals("2017-01-01T23:59:01.123456789+01:00", getLib().string(getLib().dateAndTime(getLib().date("2017-01-01"), getLib().time("23:59:01.123456789@Europe/Paris"))));
        assertEquals("null", getLib().string(getLib().dateAndTime(getLib().date("2017-09-05T10:20:00"), getLib().time("09:15:30.987654321@Europe/Paris"))));
        assertEquals("null", getLib().string(getLib().dateAndTime(getLib().date("2017-09-05T10:20:00-01:00"), getLib().time("09:15:30.987654321@Europe/Paris"))));
        assertEquals("null", getLib().string(getLib().dateAndTime(getLib().date("2017-09-05T10:20:00@Europe/Paris"), getLib().time("09:15:30.987654321@Europe/Paris"))));
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

        assertNull(getLib().second(null));
        assertEqualsNumber(makeNumber("2"), getLib().second(getLib().time("12:01:02Z")));

        assertNull(getLib().timeOffset(null));
        assertNull(getLib().timeOffset(getLib().time("12:01:02")));
        assertEquals(getLib().duration("PT1H"), getLib().timeOffset(getLib().time("12:01:02+01:00")));
        assertEquals(getLib().duration("PT0S"), getLib().timeOffset(getLib().time("12:01:02Z")));
        assertEquals(getLib().duration("PT0S"), getLib().timeOffset(getLib().time("12:01:02@Etc/UTC")));

        assertNull(getLib().timezone(null));
        assertNull(getLib().timezone(getLib().time("12:01:02")));
        assertEquals("+01:00", getLib().timezone(getLib().time("12:01:02+01:00")));
        assertEquals("Z", getLib().timezone(getLib().time("12:01:02Z")));
        assertEquals("Z", getLib().timezone(getLib().time("12:01:02@Etc/UTC")));
        assertEquals("+01:00", getLib().timezone(getLib().time("12:01:02@Europe/Paris")));
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

        assertNull(getLib().hour(null));
        assertEqualsNumber(makeNumber("12"), getLib().hour(getLib().dateAndTime("2018-12-10T12:01:02Z")));

        assertNull(getLib().minute(null));
        assertEqualsNumber(makeNumber("1"), getLib().minute(getLib().dateAndTime("2018-12-10T12:01:02Z")));

        assertNull(getLib().second(null));
        assertEqualsNumber(makeNumber("2"), getLib().second(getLib().dateAndTime("2018-12-10T12:01:02Z")));

        assertNull(getLib().timeOffset(null));
        assertNull(getLib().timeOffset(getLib().dateAndTime("2018-12-10T12:01:02")));
        assertEquals(getLib().duration("PT1H"), getLib().timeOffset(getLib().dateAndTime("2018-12-10T12:01:02+01:00")));
        assertEquals(getLib().duration("PT0S"), getLib().timeOffset(getLib().dateAndTime("2018-12-10T12:01:02Z")));
        assertEquals(getLib().duration("PT0S"), getLib().timeOffset(getLib().dateAndTime("2018-12-10T12:01:02@Etc/UTC")));

        assertNull(getLib().timezone(null));
        assertNull(getLib().timezone(getLib().dateAndTime("2018-12-10T12:01:02")));
        assertEquals("+01:00", getLib().timezone(getLib().dateAndTime("2018-12-10T12:01:02+01:00")));
        assertEquals("Z", getLib().timezone(getLib().dateAndTime("2018-12-10T12:01:02Z")));
        assertEquals("Etc/UTC", getLib().timezone(getLib().dateAndTime("2018-12-10T12:01:02@Etc/UTC")));
        assertEquals("Europe/Paris", getLib().timezone(getLib().dateAndTime("2018-12-10T12:01:02@Europe/Paris")));
    }
}
