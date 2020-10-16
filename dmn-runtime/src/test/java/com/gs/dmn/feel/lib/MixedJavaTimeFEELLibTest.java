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

import com.gs.dmn.feel.lib.type.time.BaseDateTimeLib;
import com.gs.dmn.feel.lib.type.time.xml.DefaultDateTimeLib;
import org.junit.Test;

import javax.xml.datatype.Duration;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZonedDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class MixedJavaTimeFEELLibTest extends BaseStandardFEELLibTest<BigDecimal, LocalDate, OffsetTime, ZonedDateTime, Duration> {
    @Override
    protected MixedJavaTimeFEELLib getLib() {
        return new MixedJavaTimeFEELLib();
    }

    //
    // Date time operators
    //
    @Override
    @Test
    public void testDateSubtract() {
        super.testDateSubtract();

        assertEqualsDateTime("P0Y0M", getLib().dateSubtract(makeDate("2016-08-01"), makeDate("2016-08-01")));
        assertEqualsDateTime("P0Y0M", getLib().dateSubtract(makeDate("2016-08-01"), makeDate("2016-08-03")));
    }

    //
    // Constructors
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

        assertEqualsDateTime("12:00:00Z", getLib().time(makeTime("12:00:00Z")));
        assertEqualsDateTime("00:00:00Z", getLib().time(getLib().date("2017-08-10")));
        assertEqualsDateTime("11:00:00Z", getLib().time(makeDateAndTime("2016-08-01T11:00:00Z")));

        assertEqualsDateTime("12:00:00Z", getLib().time(
                makeNumber("12"), makeNumber("00"), makeNumber("00"),
                null));
    }

    @Override
    @Test
    public void testDateTime() {
        super.testDateTime();

        // Test date
        assertEqualsDateTime("2016-08-01T00:00:00Z", getLib().dateAndTime("2016-08-01"));

        // Missing Z
        assertEqualsDateTime("-2016-01-30T09:05:00Z", getLib().dateAndTime("-2016-01-30T09:05:00"));
        assertEqualsDateTime("-2017-02-28T02:02:02Z", getLib().dateAndTime("-2017-02-28T02:02:02"));

        assertEqualsDateTime("2016-08-01T11:00:00Z", getLib().dateAndTime("2016-08-01T11:00:00"));
        assertEqualsDateTime("2011-12-03T10:15:30+01:00@Europe/Paris", getLib().dateAndTime("2011-12-03T10:15:30@Europe/Paris"));

        // Test minimum and maximum
        assertEqualsDateTime("-99999-12-31T11:22:33Z@UTC", getLib().dateAndTime("-99999-12-31T11:22:33"));
        assertEqualsDateTime("99999-12-31T11:22:33Z@UTC", getLib().dateAndTime("99999-12-31T11:22:33"));
    }

   @Override
   @Test
    public void testDuration() {
        super.testDuration();
    }

    @Override
    @Test
    public void testYearsAndMonthsDuration() {
        super.testYearsAndMonthsDuration();

        assertEquals("P0Y0M", getLib().yearsAndMonthsDuration(makeDateAndTime("2015-12-24T12:15:00.000+01:00"), makeDateAndTime("2015-12-24T12:15:00.000+01:00")).toString());
        assertEquals("P1Y2M", getLib().yearsAndMonthsDuration(makeDateAndTime("2016-09-30T23:25:00"), makeDateAndTime("2017-12-28T12:12:12")).toString());
        assertEquals("P7Y6M", getLib().yearsAndMonthsDuration(makeDateAndTime("2010-05-30T03:55:58"), makeDateAndTime("2017-12-15T00:59:59")).toString());
        assertEquals("-P4033Y2M", getLib().yearsAndMonthsDuration(makeDateAndTime("2014-12-31T23:59:59"), makeDateAndTime("-2019-10-01T12:32:59")).toString());
        assertEquals("-P4035Y11M", getLib().yearsAndMonthsDuration(makeDateAndTime("2017-09-05T10:20:00-01:00"), makeDateAndTime("-2019-10-01T12:32:59+02:00")).toString());
    }

    @Test
    public void testString() {
        assertEquals("null", getLib().string(null));

        assertEqualsDateTime("2016-08-01", getLib().string(makeDate("2016-08-01")));
        assertEqualsDateTime("11:00:01Z", getLib().string(makeTime("11:00:01Z")));
        assertEqualsDateTime("2016-08-01T11:00:01Z", getLib().string(makeDateAndTime("2016-08-01T11:00:01Z")));
        assertEquals("123.45", getLib().string(makeNumber("123.45")));
        assertEquals("true", getLib().string(true));

        assertEquals("999999999-12-31", getLib().string(getLib().date("999999999-12-31")));
        assertEquals("-999999999-12-31", getLib().string(getLib().date("-999999999-12-31")));
        assertEquals("999999999-12-31", getLib().string(getLib().date(makeNumber(999999999), makeNumber(12),makeNumber(31))));
        assertEquals("-999999999-12-31", getLib().string(getLib().date(makeNumber(-999999999), makeNumber(12),makeNumber(31))));

//        assertEquals("00:01:00@Etc/UTC", getLib().string(getLib().time("00:01:00@Etc/UTC")));
//        assertEquals("00:01:00@Europe/Paris", getLib().string(getLib().time("00:01:00@Europe/Paris")));
//        assertEquals("10:20:00@Europe/Paris", getLib().string(getLib().time(getLib().dateAndTime("2017-08-10T10:20:00@Europe/Paris"))));
//        assertEquals("11:20:00@Asia/Dhaka", getLib().string(getLib().time(getLib().dateAndTime("2017-09-04T11:20:00@Asia/Dhaka"))));
        assertEquals("11:59:45+02:45:55", getLib().string(getLib().time(makeNumber(11), makeNumber(59), makeNumber(45), getLib().duration("PT2H45M55S"))));
        assertEquals("11:59:45-02:45:55", getLib().string(getLib().time(makeNumber(11), makeNumber(59), makeNumber(45), getLib().duration("-PT2H45M55S"))));
        assertEquals("00:00:00Z", getLib().string(getLib().time(getLib().date("2017-08-10"))));

//        assertEquals("99999-12-31T11:22:33", getLib().string(getLib().dateAndTime("99999-12-31T11:22:33")));
//        assertEquals("-99999-12-31T11:22:33", getLib().string(getLib().dateAndTime("-99999-12-31T11:22:33")));
//        assertEquals("2011-12-31T10:15:30@Europe/Paris", getLib().string(getLib().dateAndTime("2011-12-31T10:15:30@Europe/Paris")));
//        assertEquals("2011-12-31T10:15:30@Etc/UTC", getLib().string(getLib().dateAndTime("2011-12-31T10:15:30@Etc/UTC")));
//        assertEquals("2011-12-31T10:15:30.987@Europe/Paris", getLib().string(getLib().dateAndTime("2011-12-31T10:15:30.987@Europe/Paris")));
//        assertEquals("2011-12-31T10:15:30.123456789@Europe/Paris", getLib().string(getLib().dateAndTime("2011-12-31T10:15:30.123456789@Europe/Paris")));
//        assertEquals("999999999-12-31T23:59:59.999999999@Europe/Paris", getLib().string(getLib().dateAndTime("999999999-12-31T23:59:59.999999999@Europe/Paris")));
        assertEquals("-999999999-12-31T23:59:59.999999999+02:00", getLib().string(getLib().dateAndTime("-999999999-12-31T23:59:59.999999999+02:00")));
//        assertEquals("2017-01-01T23:59:01@Europe/Paris", getLib().string(getLib().dateAndTime(getLib().date("2017-01-01"), getLib().time("23:59:01@Europe/Paris"))));
//        assertEquals("2017-01-01T23:59:01.123456789@Europe/Paris", getLib().string(getLib().dateAndTime(getLib().date("2017-01-01"), getLib().time("23:59:01.123456789@Europe/Paris"))));
//        assertEquals("2017-09-05T09:15:30.987654321@Europe/Paris", getLib().string(getLib().dateAndTime(getLib().dateAndTime("2017-09-05T10:20:00"), getLib().time("09:15:30.987654321@Europe/Paris"))));
//        assertEquals("2017-09-05T09:15:30.987654321@Europe/Paris", getLib().string(getLib().dateAndTime(getLib().dateAndTime("2017-09-05T10:20:00-01:00"), getLib().time("09:15:30.987654321@Europe/Paris"))));
//        assertEquals("2017-09-05T09:15:30.987654321@Europe/Paris", getLib().string(getLib().dateAndTime(getLib().dateAndTime("2017-09-05T10:20:00@Europe/Paris"), getLib().time("09:15:30.987654321@Europe/Paris"))));
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
        assertEquals(getLib().duration("P0Y0M0DT0H0M0.000S"), getLib().timeOffset(getLib().time("12:01:02Z@Etc/UTC")));
        assertEquals(getLib().duration("P0Y0M0DT0H0M0.000S"), getLib().timeOffset(getLib().time("12:01:02Z")));
        assertEquals(getLib().duration("P0Y0M0DT0H0M0.000S"), getLib().timeOffset(getLib().time("12:01:02")));

        assertNull(getLib().timezone((OffsetTime) null));
        assertEquals("Z", getLib().timezone(getLib().time("12:01:02Z@Etc/UTC")));
        assertEquals("Z", getLib().timezone(getLib().time("12:01:02@Etc/UTC")));
        assertEquals("Z", getLib().timezone(getLib().time("12:01:02")));
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
        assertEquals(getLib().duration("P0Y0M0DT0H0M0.000S"), getLib().timeOffset(getLib().dateAndTime("2018-12-10T12:01:02Z@Etc/UTC")));
        assertEquals(getLib().duration("P0Y0M0DT0H0M0.000S"), getLib().timeOffset(getLib().dateAndTime("2018-12-10T12:01:02Z")));
        assertEquals(getLib().duration("P0Y0M0DT0H0M0.000S"), getLib().timeOffset(getLib().dateAndTime("2018-12-10T12:01:02")));

        assertNull(getLib().timezone((ZonedDateTime) null));
        assertEquals("Etc/UTC", getLib().timezone(getLib().dateAndTime("2018-12-10T12:01:02Z@Etc/UTC")));
        assertEquals("Etc/UTC", getLib().timezone(getLib().dateAndTime("2018-12-10T12:01:02@Etc/UTC")));
        assertEquals("Z", getLib().timezone(getLib().dateAndTime("2018-12-10T12:01:02")));
    }

    @Override
    protected void assertEqualsDateTime(String expected, Object actual) {
        if (actual instanceof LocalDate) {
            String actualText = ((LocalDate) actual).format(BaseDateTimeLib.FEEL_DATE_FORMAT);
            assertEquals(expected, cleanActualText(actualText));
        } else if (actual instanceof OffsetTime) {
            String actualText = ((OffsetTime) actual).format(BaseDateTimeLib.FEEL_TIME_FORMAT);
            assertEquals(expected, cleanActualText(actualText));
        } else if (actual instanceof ZonedDateTime) {
            ZonedDateTime expectedDateTime = normalize(ZonedDateTime.parse(expected, BaseDateTimeLib.FEEL_DATE_TIME_FORMAT));
            ZonedDateTime actualDateTime = normalize((ZonedDateTime) actual);
            assertEquals(expectedDateTime, actualDateTime);
        } else if (actual instanceof Duration) {
            String actualText = actual.toString();
            assertEquals(expected, cleanActualText(actualText));
        } else if (actual instanceof String) {
            String actualText = cleanActualText((String) actual);
            assertEquals(expected, cleanActualText(actualText));
        } else {
            assertEquals(expected, actual);
        }
    }

    private ZonedDateTime normalize(ZonedDateTime dateTime) {
        return dateTime.withZoneSameInstant(DefaultDateTimeLib.UTC);
    }
}

