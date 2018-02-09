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

import org.junit.Test;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * Created by Octavian Patrascoiu on 23/11/2016.
 */
public class DefaultFEELLibTest extends BaseFEELLibTest<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar, Duration> {
    @Override
    protected FEELLib<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar, Duration> getLib() {
        return new DefaultFEELLib();
    }

    //
    // Conversion functions
    //
    @Test
    public void testDate() throws Exception {
        super.testDate();

        assertEqualsTime("2016-08-01", getLib().date(makeDate("2016-08-01")));
        assertEqualsTime("2017-10-11", getLib().date(getLib().date("2017-10-11")));
    }

    @Test
    public void testTime() throws Exception {
        super.testTime();

        assertEqualsTime("12:00:00Z", getLib().time(makeTime("12:00:00Z")));
        assertEqualsTime("12:00:00", getLib().time(
                makeNumber("12"), makeNumber("00"), makeNumber("00"),
                null));
        assertEqualsTime("00:00:00", getLib().time(getLib().date("2017-08-10")));
    }

    @Test
    public void testDateTime() throws Exception {
        super.testDateTime();

        // Test date
        assertEqualsTime("2016-08-01T00:00:00", getLib().dateAndTime("2016-08-01"));

        // Missing Z
        assertEqualsTime("-2016-01-30T09:05:00", getLib().dateAndTime("-2016-01-30T09:05:00"));
        assertEqualsTime("-2017-02-28T02:02:02", getLib().dateAndTime("-2017-02-28T02:02:02"));

        assertEqualsTime("2016-08-01T11:00:00", getLib().dateAndTime("2016-08-01T11:00:00"));
        assertEqualsTime("2011-12-03T10:15:30+01:00", getLib().dateAndTime("2011-12-03T10:15:30@Europe/Paris"));
    }

    @Test
    public void testString() {
        assertEquals("2016-08-01", getLib().string(makeDate("2016-08-01")));
        assertEquals("11:00:00Z", getLib().string(makeTime("11:00:00Z")));
        assertEquals("2016-08-01T11:00:00Z", getLib().string(makeDateAndTime("2016-08-01T11:00:00Z")));
        assertEquals("123.45", getLib().string(makeNumber("123.45")));
        assertEquals("true", getLib().string(true));
    }

    @Test
    public void testDuration() {
        super.testDuration();
    }

    @Test
    public void testYearsAndMonthsDuration() throws Exception {
        super.testYearsAndMonthsDuration();
    }

    //
    // Date time operators
    //
    @Test
    public void testDateSubtract() throws Exception {
        super.testDateSubtract();
        assertEqualsTime("P0Y0M0DT0H0M0.000S", getLib().dateSubtract(makeDate("2016-08-01"), makeDate("2016-08-01")).toString());
        assertEqualsTime("-P0Y0M2DT0H0M0.000S", getLib().dateSubtract(makeDate("2016-08-01"), makeDate("2016-08-03")).toString());
    }
}

