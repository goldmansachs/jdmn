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

import org.junit.Ignore;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAmount;

import static org.junit.Assert.assertEquals;

public class PureJavaTimeFEELLibTest extends BaseFEELLibTest<BigDecimal, LocalDate, OffsetTime, ZonedDateTime, TemporalAmount> {
    @Override
    protected PureJavaTimeFEELLib getLib() {
        return new PureJavaTimeFEELLib();
    }

    //
    // Conversion functions
    //
    @Test
    public void testDate() throws Exception {
        super.testDate();
    }

    @Test
    @Ignore
    public void testDateSubtract() throws Exception {
        super.testDateSubtract();
    }

    @Test
    @Ignore
    public void testDateAddDuration() throws Exception {
        super.testDateAddDuration();
    }

    @Test
    @Ignore
    public void testDateSubtractDuration() throws Exception {
        super.testDateSubtractDuration();
    }

    @Test
    @Ignore
    public void testTime() throws Exception {
        super.testTime();

        assertEqualsTime("12:00:00Z", getLib().time(makeTime("12:00:00Z")));
        assertEqualsTime("12:00:00Z", getLib().time(
                makeNumber("12"), makeNumber("00"), makeNumber("00"),
                null));
    }

    @Test
    @Ignore
    public void testTimeSubtract() throws Exception {
        super.testTimeSubtract();
    }

    @Test
    @Ignore
    public void testTimeAddDuration() throws Exception {
        super.testTimeAddDuration();
    }

    @Test
    @Ignore
    public void testTimeSubtractDuration() throws Exception {
        super.testTimeSubtractDuration();
    }

    @Test
    @Ignore
    public void testDateTime() throws Exception {
        super.testDateTime();
    }

    @Test
    @Ignore
    public void testDateTimeSubtract() throws Exception {
        super.testDateTimeSubtract();
    }

    @Test
    @Ignore
    public void testDateTimeAddDuration() throws Exception {
        super.testDateTimeAddDuration();
    }

    @Test
    @Ignore
    public void testDateTimeSubtractDuration() throws Exception {
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

    @Test
    @Ignore
    public void testMakeDuration() throws Exception {
        super.testMakeDuration();
    }

    @Test
    @Ignore
    public void testDurationEqual() throws Exception {
        super.testDurationEqual();
    }

    @Test
    @Ignore
    public void testDurationNotEqual() throws Exception {
        super.testDurationNotEqual();
    }

    @Test
    @Ignore
    public void testDurationLessThan() throws Exception {
        super.testDurationLessThan();
    }

    @Test
    @Ignore
    public void testDurationGreaterThan() throws Exception {
        super.testDurationGreaterThan();
    }

    @Test
    @Ignore
    public void testDurationLessEqualThan() throws Exception {
        super.testDurationLessEqualThan();
    }

    @Test
    @Ignore
    public void testDurationGreaterEqualThan() throws Exception {
        super.testDurationGreaterEqualThan();
    }

    @Test
    @Ignore
    public void testDuration() {
        assertEqualsTime("P1Y8M", getLib().duration("P1Y8M").toString());
        assertEqualsTime("PT68H", getLib().duration("P2DT20H").toString());
    }

    @Test
    public void testYearsAndMonthsDuration() {
        assertEqualsTime("PT0S", getLib().yearsAndMonthsDuration(getLib().dateAndTime("2015-12-24T12:15:00.000+01:00"), getLib().dateAndTime("2015-12-24T12:15:00.000+01:00")).toString());
    }

    @Override
    protected void assertEqualsTime(String expected, Object actual) {
        if (actual instanceof LocalDate) {
            String actualText = ((LocalDate) actual).format(DateTimeFormatter.ISO_DATE);
            assertEquals(expected, cleanActualText(actualText));
        } else if (actual instanceof OffsetTime) {
            String actualText = ((OffsetTime) actual).format(DateTimeFormatter.ISO_OFFSET_TIME);
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
        return dateTime.withZoneSameInstant(DateTimeUtil.UTC);
    }
}

