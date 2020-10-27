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

import java.time.LocalDate;
import java.time.ZonedDateTime;

import static org.junit.Assert.assertNull;

public abstract class BaseMixedJavaTimeFEELLibExceptionsTest<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends BaseStandardFEELLibExceptionsTest<NUMBER, DATE, TIME, DATE_TIME, DURATION> {
    @Test
    public void testDate() {
        super.testDate();

        assertNull(getBaseMixedJavaTimeLib().date((ZonedDateTime) null));
    }

    @Test
    public void testTime() {
        super.testTime();

        assertNull(getBaseMixedJavaTimeLib().time((ZonedDateTime) null));
        assertNull(getBaseMixedJavaTimeLib().time((LocalDate) null));
    }

    @Test
    public void testDateAndTime() {
        super.testDateAndTime();

        assertNull(getBaseMixedJavaTimeLib().dateAndTime((Object) null, null));
    }

    @Test
    public void testYearsAndMonthsDuration() {
        super.testYearsAndMonthsDuration();

        assertNull(getBaseMixedJavaTimeLib().yearsAndMonthsDuration((ZonedDateTime) null, (ZonedDateTime) null));
        assertNull(getBaseMixedJavaTimeLib().yearsAndMonthsDuration((ZonedDateTime) null, (LocalDate) null));
        assertNull(getBaseMixedJavaTimeLib().yearsAndMonthsDuration((LocalDate) null, (ZonedDateTime) null));
    }

    @Test
    public void testYear() {
        super.testYear();

        assertNull(getBaseMixedJavaTimeLib().year((ZonedDateTime) null));
    }

    @Test
    public void testMonth() {
        super.testMonth();

        assertNull(getBaseMixedJavaTimeLib().month((ZonedDateTime) null));
    }

    @Test
    public void testDay() {
        super.testDay();

        assertNull(getBaseMixedJavaTimeLib().day((ZonedDateTime) null));
    }

    @Test
    public void testWeekday() {
        super.testWeekday();

        assertNull(getBaseMixedJavaTimeLib().weekday((ZonedDateTime) null));
    }

    @Test
    public void testHour() {
        super.testHour();

        assertNull(getBaseMixedJavaTimeLib().hour((ZonedDateTime) null));
    }

    @Test
    public void testMinute() {
        super.testMinute();

        assertNull(getBaseMixedJavaTimeLib().minute((ZonedDateTime) null));
    }

    @Test
    public void testSecond() {
        super.testSecond();

        assertNull(getBaseMixedJavaTimeLib().second((ZonedDateTime) null));
    }

    @Test
    public void testTimeOffset() {
        super.testTimeOffset();

        assertNull(getBaseMixedJavaTimeLib().timeOffset((ZonedDateTime) null));
    }

    @Test
    public void testTimezone() {
        super.testTimezone();

        assertNull(getBaseMixedJavaTimeLib().timezone((ZonedDateTime) null));
    }

    private BaseMixedJavaTimeFEELLib<NUMBER> getBaseMixedJavaTimeLib() {
        return (BaseMixedJavaTimeFEELLib) getLib();
    }
}