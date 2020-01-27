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
import org.junit.Ignore;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;

import static org.junit.Assert.assertEquals;

public class PureJavaTimeFEELLibTest extends BaseFEELLibTest<BigDecimal, LocalDate, Temporal, Temporal, TemporalAmount> {
    @Override
    protected PureJavaTimeFEELLib getLib() {
        return new PureJavaTimeFEELLib();
    }

    //
    // Conversion functions
    //
    @Override
    @Test
    public void testDate() {
        super.testDate();
    }

    @Override
    @Test
    @Ignore
    public void testDateSubtract() {
        super.testDateSubtract();
    }

    @Override
    @Test
    @Ignore
    public void testDateAddDuration() {
        super.testDateAddDuration();
    }

    @Override
    @Test
    @Ignore
    public void testDateSubtractDuration() {
        super.testDateSubtractDuration();
    }

    @Override
    @Test
    @Ignore
    public void testTime() {
        super.testTime();

        assertEqualsTime("12:00:00Z", getLib().time(makeTime("12:00:00Z")));
        assertEqualsTime("12:00:00Z", getLib().time(
                makeNumber("12"), makeNumber("00"), makeNumber("00"),
                null));
    }

    @Override
    @Test
    @Ignore
    public void testTimeSubtract() {
        super.testTimeSubtract();
    }

    @Override
    @Test
    @Ignore
    public void testTimeAddDuration() {
        super.testTimeAddDuration();
    }

    @Override
    @Test
    @Ignore
    public void testTimeSubtractDuration() {
        super.testTimeSubtractDuration();
    }

    @Override
    @Test
    @Ignore
    public void testDateTime() {
        super.testDateTime();
    }

    @Override
    @Test
    @Ignore
    public void testDateTimeSubtract() {
        super.testDateTimeSubtract();
    }

    @Override
    @Test
    @Ignore
    public void testDateTimeAddDuration() {
        super.testDateTimeAddDuration();
    }

    @Override
    @Test
    @Ignore
    public void testDateTimeSubtractDuration() {
        super.testDateTimeSubtractDuration();
    }

    @Test
    public void testString() {
        assertEqualsTime("2016-08-01", getLib().string(makeDate("2016-08-01")));
        assertEqualsTime("11:00:01Z", getLib().string(makeTime("11:00:01Z")));
        assertEqualsTime("2016-08-01T11:00:01Z", getLib().string(makeDateAndTime("2016-08-01T11:00:01Z")));
        assertEquals("123.45", getLib().string(makeNumber("123.45")));
        assertEquals("true", getLib().string(true));
    }

    @Override
    @Test
    @Ignore
    public void testDurationEqual() {
        super.testDurationEqual();
    }

    @Override
    @Test
    @Ignore
    public void testDurationNotEqual() {
        super.testDurationNotEqual();
    }

    @Override
    @Test
    @Ignore
    public void testDurationLessThan() {
        super.testDurationLessThan();
    }

    @Override
    @Test
    @Ignore
    public void testDurationGreaterThan() {
        super.testDurationGreaterThan();
    }

    @Override
    @Test
    @Ignore
    public void testDurationLessEqualThan() {
        super.testDurationLessEqualThan();
    }

    @Override
    @Test
    @Ignore
    public void testDurationGreaterEqualThan() {
        super.testDurationGreaterEqualThan();
    }

    @Override
    @Test
    @Ignore
    public void testDuration() {
        assertEqualsTime("P1Y8M", getLib().duration("P1Y8M").toString());
        assertEqualsTime("PT68H", getLib().duration("P2DT20H").toString());
    }

    @Override
    @Test
    public void testYearsAndMonthsDuration() {
        assertEqualsTime("PT0S", getLib().yearsAndMonthsDuration(getLib().dateAndTime("2015-12-24T12:15:00.000+01:00"), getLib().dateAndTime("2015-12-24T12:15:00.000+01:00")).toString());
    }

    //
    // Time properties
    //
    @Override
    @Test
    public void testTimeProperties() {
        assertEqualsNumber(getLib().number("12"), getLib().hour(getLib().time("12:01:02Z")));
        assertEqualsNumber(getLib().number("1"), getLib().minute(getLib().time("12:01:02Z")));
        assertEqualsNumber(getLib().number("2"), getLib().second(getLib().time("12:01:02Z")));

        assertEquals(null, getLib().timeOffset(getLib().time("12:01:02")));
        assertEquals(getLib().duration("PT1H"), getLib().timeOffset(getLib().time("12:01:02+01:00")));
        assertEquals(getLib().duration("PT0S"), getLib().timeOffset(getLib().time("12:01:02Z")));
        assertEquals(getLib().duration("PT0S"), getLib().timeOffset(getLib().time("12:01:02@Etc/UTC")));

        assertEquals(null, getLib().timezone(getLib().time("12:01:02")));
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
        assertEqualsNumber(getLib().number("2018"), getLib().year(getLib().dateAndTime("2018-12-10T12:01:02Z")));
        assertEqualsNumber(getLib().number("12"), getLib().month(getLib().dateAndTime("2018-12-10T12:01:02Z")));
        assertEqualsNumber(getLib().number("10"), getLib().day(getLib().dateAndTime("2018-12-10T12:01:02Z")));
        assertEqualsNumber(getLib().number("1"), getLib().weekday(getLib().dateAndTime("2018-12-10T12:01:02Z")));
        assertEqualsNumber(getLib().number("12"), getLib().hour(getLib().dateAndTime("2018-12-10T12:01:02Z")));
        assertEqualsNumber(getLib().number("1"), getLib().minute(getLib().dateAndTime("2018-12-10T12:01:02Z")));
        assertEqualsNumber(getLib().number("2"), getLib().second(getLib().dateAndTime("2018-12-10T12:01:02Z")));

        assertEquals(null, getLib().timeOffset(getLib().dateAndTime("2018-12-10T12:01:02")));
        assertEquals(getLib().duration("PT1H"), getLib().timeOffset(getLib().dateAndTime("2018-12-10T12:01:02+01:00")));
        assertEquals(getLib().duration("PT0S"), getLib().timeOffset(getLib().dateAndTime("2018-12-10T12:01:02Z")));
        assertEquals(getLib().duration("PT0S"), getLib().timeOffset(getLib().dateAndTime("2018-12-10T12:01:02@Etc/UTC")));

        assertEquals(null, getLib().timezone(getLib().dateAndTime("2018-12-10T12:01:02")));
        assertEquals("+01:00", getLib().timezone(getLib().dateAndTime("2018-12-10T12:01:02+01:00")));
        assertEquals("Z", getLib().timezone(getLib().dateAndTime("2018-12-10T12:01:02Z")));
        assertEquals("Etc/UTC", getLib().timezone(getLib().dateAndTime("2018-12-10T12:01:02@Etc/UTC")));
        assertEquals("Europe/Paris", getLib().timezone(getLib().dateAndTime("2018-12-10T12:01:02@Europe/Paris")));
    }

    @Override
    @Test
    @Ignore
    public void testDurationProperties() {
        super.testDurationProperties();
    }

    @Override
    protected void assertEqualsTime(String expected, Object actual) {
        if (actual instanceof LocalDate) {
            String actualText = ((LocalDate) actual).format(BaseDateTimeLib.FEEL_DATE_FORMAT);
            assertEquals(expected, cleanActualText(actualText));
        } else if (actual instanceof OffsetTime) {
            String actualText = ((OffsetTime) actual).format(BaseDateTimeLib.FEEL_TIME_FORMAT);
            assertEquals(expected, cleanActualText(actualText));
        } else if (actual instanceof ZonedDateTime) {
            assertEquals(normalize(ZonedDateTime.parse(expected)), normalize((ZonedDateTime)actual));
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

