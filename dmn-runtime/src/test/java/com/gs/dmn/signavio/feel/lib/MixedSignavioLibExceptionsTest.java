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

import com.gs.dmn.feel.lib.MixedJavaTimeFEELLib;
import com.gs.dmn.feel.lib.StandardFEELLib;
import com.gs.dmn.feel.lib.stub.*;
import com.gs.dmn.feel.lib.type.*;
import com.gs.dmn.signavio.feel.lib.stub.SignavioDateTimeLibStub;
import com.gs.dmn.signavio.feel.lib.stub.SignavioListLibStub;
import com.gs.dmn.signavio.feel.lib.stub.SignavioNumberLibStub;
import com.gs.dmn.signavio.feel.lib.stub.SignavioStringLibStub;
import com.gs.dmn.signavio.feel.lib.type.list.SignavioListLib;
import com.gs.dmn.signavio.feel.lib.type.numeric.SignavioNumberLib;
import com.gs.dmn.signavio.feel.lib.type.string.SignavioStringLib;
import com.gs.dmn.signavio.feel.lib.type.time.SignavioDateTimeLib;
import org.junit.Test;

import javax.xml.datatype.Duration;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZonedDateTime;

import static org.junit.Assert.assertNull;

public class MixedSignavioLibExceptionsTest extends BaseSignavioLibExceptionsTest<BigDecimal, LocalDate, OffsetTime, ZonedDateTime, Duration> {
    @Override
    protected MixedJavaTimeSignavioLib getLib() {
        NumericType<BigDecimal> numericType = new NumericTypeStub<>();
        BooleanType booleanType = new BooleanTypeStub();
        StringType stringType = new StringTypeStub();
        DateType<LocalDate, Duration> dateType = new DateTypeStub<>();
        TimeType<OffsetTime, Duration> timeType = new TimeTypeStub<>();
        DateTimeType<ZonedDateTime, Duration> dateTimeType = new DateTimeTypeStub<>();
        DurationType<Duration, BigDecimal> durationType = new DurationTypeStub<>();
        ListType listType = new ListTypeStub();
        ContextType contextType = new ContextTypeStub();
        StandardFEELLib<BigDecimal, LocalDate, OffsetTime, ZonedDateTime, Duration> feelLib = new MixedJavaTimeFEELLib();
        SignavioNumberLib<BigDecimal> numberLib = new SignavioNumberLibStub<>();
        SignavioStringLib stringLib = new SignavioStringLibStub();
        SignavioDateTimeLib<BigDecimal, LocalDate, OffsetTime, ZonedDateTime> dateTimeLib = new SignavioDateTimeLibStub<>();
        SignavioListLib listLib = new SignavioListLibStub();
        return new MixedJavaTimeSignavioLib(
                numericType, booleanType, stringType,
                dateType, timeType, dateTimeType, durationType,
                listType, contextType,
                feelLib, numberLib, stringLib, dateTimeLib, listLib
        );
    }

    //
    // Date and time operations
    //
    @Test
    public void testDay() {
        super.testDay();

        assertNull(getLib().day((ZonedDateTime) null));
    }

    @Test
    public void testDayAdd() {
        super.testDayAdd();

        assertNull(getLib().dayAdd((ZonedDateTime) null, null));
    }

    @Test
    public void testDayDiff() {
        super.testDayDiff();

        assertNull(getLib().dayDiff(null, (ZonedDateTime) null));
    }

    @Test
    public void testHour() {
        super.testHour();

        assertNull(getLib().hour((ZonedDateTime) null));
    }

    @Test
    public void testHourDiff() {
        super.testHourDiff();

        assertNull(getLib().hourDiff(null, (ZonedDateTime) null));
    }

    @Test
    public void testMinute() {
        super.testMinute();

        assertNull(getLib().minute((ZonedDateTime) null));
    }

    @Test
    public void testSecond() {
        super.testSecond();

        assertNull(getLib().second((ZonedDateTime) null));
    }

    @Test
    public void testTimeOffset() {
        super.testTimeOffset();

        assertNull(getLib().timeOffset((ZonedDateTime) null));
    }

    @Test
    public void testTimezone() {
        super.testTimezone();

        assertNull(getLib().timezone((ZonedDateTime) null));
    }

    @Test
    public void testMinutesDiff() {
        super.testMinutesDiff();

        assertNull(getLib().minutesDiff(null, (ZonedDateTime) null));
    }

    @Test
    public void testMonth() {
        super.testMonth();

        assertNull(getLib().month((ZonedDateTime) null));
    }

    @Test
    public void testMonthAdd() {
        super.testMonthAdd();

        assertNull(getLib().monthAdd((ZonedDateTime) null, null));
    }

    @Test
    public void testMonthDiff() {
        super.testMonthDiff();

        assertNull(getLib().monthDiff(null, (ZonedDateTime) null));
    }

    @Test
    public void testWeekday() {
        super.testWeekday();

        assertNull(getLib().weekday((ZonedDateTime) null));
    }

    @Test
    public void testYear() {
        super.testYear();

        assertNull(getLib().year((ZonedDateTime) null));
    }

    @Test
    public void testYearAdd() {
        super.testYearAdd();

        assertNull(getLib().yearAdd((ZonedDateTime) null, null));
    }

    @Test
    public void testYearDiff() {
        super.testYearDiff();

        assertNull(getLib().yearDiff((ZonedDateTime) null, null));
    }

    @Test
    public void testDate() {
        super.testDate();

        assertNull(getLib().date((LocalDate) null));
    }

    @Test
    public void testTime() {
        super.testTime();

        assertNull(getLib().time((OffsetTime) null));
    }
}