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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
    public void testTimeSubtract() {
        assertNull(getLib().timeSubtract(null, null));
        assertNull(getLib().timeSubtract(null, makeTime("12:00:00Z")));
        assertNull(getLib().timeSubtract(makeTime("12:00:00Z"), null));
        assertEqualsDateTime("PT1H", getLib().timeSubtract(makeTime("12:00:00Z"), makeTime("11:00:00Z")));
        assertEqualsDateTime("PT0S", getLib().timeSubtract(makeTime("12:00:00Z"), makeTime("12:00:00Z")));
        assertEqualsDateTime("PT-1H", getLib().timeSubtract(makeTime("12:00:00Z"), makeTime("13:00:00Z")));
    }

    //
    // Date time operators
    //
    @Override
    @Test
    public void testDateTimeSubtract() {
        assertNull(getLib().dateTimeSubtract(null, null));
        assertNull(getLib().dateTimeSubtract(null, makeDateAndTime("2016-08-01T12:00:00Z")));
        assertNull(getLib().dateTimeSubtract(makeDateAndTime("2016-08-01T12:00:00Z"), null));
        assertEqualsDateTime("PT1H", getLib().dateTimeSubtract(makeDateAndTime("2016-08-01T13:00:00Z"), makeDateAndTime("2016-08-01T12:00:00Z")).toString());
        assertEqualsDateTime("PT0S", getLib().dateTimeSubtract(makeDateAndTime("2016-08-01T12:00:00Z"), makeDateAndTime("2016-08-01T12:00:00Z")).toString());
        assertEqualsDateTime("PT-49H", getLib().dateTimeSubtract(makeDateAndTime("2016-08-01T12:00:00Z"), makeDateAndTime("2016-08-03T13:00:00Z")).toString());
    }

    //
    // Duration operators
    //
    @Override
    @Test
    public void testDurationDivide() {
        assertNull(getLib().durationDivide(null, null));
        assertNull(getLib().durationDivide(null, makeNumber("2")));
        assertNull(getLib().durationDivide(makeDuration("P1Y1M"), null));

        assertEquals(makeDuration("P6M"), getLib().durationDivide(makeDuration("P1Y1M"),  makeNumber("2")));
        assertEquals(makeDuration("P13M"), getLib().durationDivide(makeDuration("P2Y2M"),  makeNumber("2")));

        assertEquals(makeDuration("P0DT12H30M"), getLib().durationDivide(makeDuration("P1DT1H"),  makeNumber("2")));
        assertEquals(makeDuration("P1DT1H"), getLib().durationDivide(makeDuration("P2DT2H"),  makeNumber("2")));
    }

    //
    // Constructors
    //
    @Override
    @Test
    public void testTime() {
        assertNull(getLib().time((String) null));
        assertNull(getLib().time(""));
        assertNull(getLib().time("xxx"));
        assertNull(getLib().time("13:20:00+01:00@Europe/Paris"));
        assertNull(getLib().time("13:20:00+00:00[UTC]"));
        assertNull(getLib().time(
                makeNumber("12"), makeNumber("00"), makeNumber("00"),
                makeDuration("PT25H10M")));

        // Fix input literal
        assertEqualsDateTime("11:00:00Z", getLib().time("T11:00:00Z"));
        assertEqualsDateTime("11:00:00+01:00", getLib().time("11:00:00+0100"));

        assertEqualsDateTime("11:00:00Z", getLib().time("11:00:00Z"));
        assertEqualsDateTime("11:00:00.001Z", getLib().time("11:00:00.001Z"));

        assertEqualsDateTime("11:00:00.001+01:00", getLib().time("11:00:00.001+01:00"));
        assertEqualsDateTime("11:00:00+01:00", getLib().time("11:00:00+01:00"));

        assertEqualsDateTime("00:00:00Z", getLib().time(getLib().date("2017-08-10")));
        assertEqualsDateTime("11:00:00Z", getLib().time(makeDateAndTime("2016-08-01T11:00:00Z")));

//        assertEqualsTime("12:00:00+01:10", getLib().time(
//                makeNumber("12"), makeNumber("00"), makeNumber("00"),
//                makeDuration("PT1H10M")));

        assertEqualsDateTime("12:00:00Z", getLib().time(makeTime("12:00:00Z")));
//        assertEqualsTime("12:00:00Z", getLib().time(
//                makeNumber("12"), makeNumber("00"), makeNumber("00"),
//                null));

        assertEqualsDateTime("12:00:00Z", getLib().time(makeTime("12:00:00Z")));
        assertEqualsDateTime("00:00:00Z", getLib().time(getLib().date("2017-08-10")));
        assertEqualsDateTime("11:00:00Z", getLib().time(makeDateAndTime("2016-08-01T11:00:00Z")));
    }

    @Override
    @Test
    public void testDateTime() {
        assertNull(getLib().dateAndTime(null));
        assertNull(getLib().dateAndTime(""));
        assertNull(getLib().dateAndTime("xxx"));
        assertNull(getLib().dateAndTime("11:00:00"));
        assertNull(getLib().dateAndTime("2011-12-03T10:15:30+01:00@Europe/Paris"));
        assertNull(getLib().dateAndTime("2017-12-31T12:20:00+19:00"));

        assertNull(getLib().dateAndTime(null, null));
        assertNull(getLib().dateAndTime(null, makeTime("11:00:00Z")));
        assertNull(getLib().dateAndTime(getLib().date("2016-08-01"), null));

        // Fix input literal
        assertEqualsDateTime("2016-08-01T11:00:00+01:00", getLib().dateAndTime("2016-08-01T11:00:00+0100"));

        assertEqualsDateTime("2016-08-01T11:00:00Z", getLib().dateAndTime("2016-08-01T11:00:00Z"));
        assertEqualsDateTime("2016-08-01T11:00:00.001Z", getLib().dateAndTime("2016-08-01T11:00:00.001Z"));
        assertEqualsDateTime("2016-08-01T11:00:00.001+01:00", getLib().dateAndTime("2016-08-01T11:00:00.001+01:00"));
        assertEqualsDateTime("2016-08-01T11:00:00+01:00", getLib().dateAndTime("2016-08-01T11:00:00+01:00"));

        assertEqualsDateTime("2016-08-01T11:00:00Z", getLib().dateAndTime("2016-08-01T11:00:00Z"));

        assertEqualsDateTime("2016-08-01T11:00:00Z", getLib().dateAndTime("2016-08-01T11:00:00Z"));
        assertEqualsDateTime("2016-08-01T11:00:00Z", getLib().dateAndTime(makeDate("2016-08-01"), makeTime("11:00:00Z")));
    }

    @Override
    @Test
    public void testDuration() {
        assertEqualsDateTime("P1Y8M", getLib().duration("P1Y8M").toString());
        assertEqualsDateTime("PT68H", getLib().duration("P2DT20H").toString());
    }

    @Override
    @Test
    public void testYearsAndMonthsDuration() {
        assertNull(getLib().yearsAndMonthsDuration(null, null));

        assertEquals("P0D", getLib().yearsAndMonthsDuration(makeDate("2015-12-24"), makeDate("2015-12-24")).toString());
        assertEquals("P1Y2M", getLib().yearsAndMonthsDuration(makeDate("2016-09-30"), makeDate("2017-12-28")).toString());
        assertEquals("P7Y6M", getLib().yearsAndMonthsDuration(makeDate("2010-05-30"), makeDate("2017-12-15")).toString());
        assertEquals("P-4033Y-2M", getLib().yearsAndMonthsDuration(makeDate("2014-12-31"), makeDate("-2019-10-01")).toString());
        assertEquals("P-4035Y-11M", getLib().yearsAndMonthsDuration(makeDate("2017-09-05"), makeDate("-2019-10-01")).toString());

        assertEqualsDateTime("P0D", getLib().yearsAndMonthsDuration(getLib().dateAndTime("2015-12-24T12:15:00.000+01:00"), getLib().dateAndTime("2015-12-24T12:15:00.000+01:00")).toString());
    }

    @Test
    public void testString() {
        assertEquals("null", getLib().string(null));

        assertEqualsDateTime("2016-08-01", getLib().string(makeDate("2016-08-01")));
        assertEqualsDateTime("11:00:01Z", getLib().string(makeTime("11:00:01Z")));
        assertEqualsDateTime("2016-08-01T11:00:01Z", getLib().string(makeDateAndTime("2016-08-01T11:00:01Z")));
        assertEquals("123.45", getLib().string(makeNumber("123.45")));
        assertEquals("true", getLib().string(true));
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

