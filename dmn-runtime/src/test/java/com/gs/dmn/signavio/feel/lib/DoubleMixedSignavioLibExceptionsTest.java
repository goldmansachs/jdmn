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

import com.gs.dmn.feel.lib.stub.*;
import com.gs.dmn.feel.lib.type.bool.BooleanLib;
import com.gs.dmn.feel.lib.type.bool.BooleanType;
import com.gs.dmn.feel.lib.type.context.ContextType;
import com.gs.dmn.feel.lib.type.function.FunctionType;
import com.gs.dmn.feel.lib.type.list.ListType;
import com.gs.dmn.feel.lib.type.numeric.NumericType;
import com.gs.dmn.feel.lib.type.range.RangeType;
import com.gs.dmn.feel.lib.type.string.StringType;
import com.gs.dmn.feel.lib.type.time.*;
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
import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZonedDateTime;

import static org.junit.Assert.assertNull;

public class DoubleMixedSignavioLibExceptionsTest extends BaseSignavioLibExceptionsTest<Double, LocalDate, OffsetTime, ZonedDateTime, Duration> {
    @Override
    protected DoubleMixedJavaTimeSignavioLib getLib() {
        NumericType<Double> numericType = new NumericTypeStub<>();
        BooleanType booleanType = new BooleanTypeStub();
        StringType stringType = new StringTypeStub();
        DateType<LocalDate, Duration> dateType = new DateTypeStub<>();
        TimeType<OffsetTime, Duration> timeType = new TimeTypeStub<>();
        DateTimeType<ZonedDateTime, Duration> dateTimeType = new DateTimeTypeStub<>();
        DurationType<Duration, Double> durationType = new DurationTypeStub<>();
        ListType listType = new ListTypeStub();
        ContextType contextType = new ContextTypeStub();
        RangeType rangeType = new RangeTypeStub();
        FunctionType functionType = new FunctionTypeStub();
        SignavioNumberLib<Double> numberLib = new SignavioNumberLibStub<>();
        SignavioStringLib stringLib = new SignavioStringLibStub();
        BooleanLib booleanLib = new BooleanLibStub();
        SignavioDateTimeLib<Double, LocalDate, OffsetTime, ZonedDateTime> dateTimeLib = new SignavioDateTimeLibStub<>();
        DurationLib<LocalDate, Duration> durationLib = new DurationLibStub<>();
        SignavioListLib listLib = new SignavioListLibStub();
        return new DoubleMixedJavaTimeSignavioLib(
                numericType, booleanType, stringType,
                dateType, timeType, dateTimeType, durationType,
                listType, contextType, rangeType, functionType,
                numberLib, stringLib, booleanLib, dateTimeLib, durationLib, listLib
        );
    }

    //
    // Date and time operations
    //
    @Override
    @Test
    public void testDay() {
        super.testDay();

        assertNull(getLib().day((ZonedDateTime) null));
    }

    @Override
    @Test
    public void testDayAdd() {
        super.testDayAdd();

        assertNull(getLib().dayAdd((ZonedDateTime) null, null));
    }

    @Override
    @Test
    public void testHour() {
        super.testHour();

        assertNull(getLib().hour((ZonedDateTime) null));
    }

    @Override
    @Test
    public void testMinute() {
        super.testMinute();

        assertNull(getLib().minute((ZonedDateTime) null));
    }

    @Override
    @Test
    public void testMonth() {
        super.testMonth();

        assertNull(getLib().month((ZonedDateTime) null));
    }

    @Override
    @Test
    public void testMonthAdd() {
        super.testMonthAdd();

        assertNull(getLib().monthAdd((ZonedDateTime) null, null));
    }

    @Override
    @Test
    public void testWeekday() {
        super.testWeekday();

        assertNull(getLib().weekday((ZonedDateTime) null));
    }

    @Override
    @Test
    public void testYear() {
        super.testYear();

        assertNull(getLib().year((ZonedDateTime) null));
    }

    @Override
    @Test
    public void testYearAdd() {
        super.testYearAdd();

        assertNull(getLib().yearAdd((ZonedDateTime) null, null));
    }
}