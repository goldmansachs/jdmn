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

import com.gs.dmn.feel.lib.stub.*;
import com.gs.dmn.feel.lib.type.*;
import com.gs.dmn.feel.lib.type.bool.BooleanLib;
import com.gs.dmn.feel.lib.type.list.ListLib;
import com.gs.dmn.feel.lib.type.numeric.NumericLib;
import com.gs.dmn.feel.lib.type.string.StringLib;
import com.gs.dmn.feel.lib.type.time.DateTimeLib;
import com.gs.dmn.feel.lib.type.time.DurationLib;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;

import static org.junit.Assert.assertNull;

public class PureJavaTimeFEELLibExceptionsTest extends BaseStandardFEELLibExceptionsTest<BigDecimal, LocalDate, Temporal, Temporal, TemporalAmount> {
    @Override
    protected PureJavaTimeFEELLib getLib() {
        NumericType<BigDecimal> numericType = new NumericTypeStub<>();
        BooleanType booleanType = new BooleanTypeStub();
        StringType stringType = new StringTypeStub();
        DateType<LocalDate, TemporalAmount> dateType = new DateTypeStub<>();
        TimeType<Temporal, TemporalAmount> timeType = new TimeTypeStub<>();
        DateTimeType<Temporal, TemporalAmount> dateTimeType = new DateTimeTypeStub<>();
        DurationType<TemporalAmount, BigDecimal> durationType = new DurationTypeStub<>();
        ListType listType = new ListTypeStub();
        ContextType contextType = new ContextTypeStub();
        NumericLib<BigDecimal> numericLib = new NumericLibStub<>();
        StringLib stringLib = new StringLibStub();
        BooleanLib booleanLib = new BooleanLibStub();
        DateTimeLib<BigDecimal, LocalDate, Temporal, Temporal, TemporalAmount> dateTimeLib = new DateTimeLibStub<>();
        DurationLib<LocalDate, TemporalAmount> durationLib = new DurationLibStub<>();
        ListLib listLib = new ListLibStub();
        return new PureJavaTimeFEELLib(numericType, booleanType, stringType,
                dateType, timeType, dateTimeType, durationType,
                listType, contextType,
                numericLib, stringLib, booleanLib, dateTimeLib, durationLib, listLib
        );
    }

    @Test
    public void testYearsAndMonthsDuration() {
        super.testYearsAndMonthsDuration();

        assertNull(getLib().yearsAndMonthsDuration((Temporal) null, null));
    }

    @Test
    public void testYear() {
        super.testYear();

        assertNull(getLib().year((Temporal) null));
    }

    @Test
    public void testMonth() {
        super.testMonth();

        assertNull(getLib().month((Temporal) null));
    }

    @Test
    public void testDay() {
        super.testDay();

        assertNull(getLib().day((Temporal) null));
    }

    @Test
    public void testWeekday() {
        assertNull(getLib().weekday((Temporal) null));
    }

}