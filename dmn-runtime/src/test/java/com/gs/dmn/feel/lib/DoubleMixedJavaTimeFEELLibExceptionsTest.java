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

import javax.xml.datatype.Duration;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZonedDateTime;

public class DoubleMixedJavaTimeFEELLibExceptionsTest extends BaseMixedJavaTimeFEELLibExceptionsTest<Double, LocalDate, OffsetTime, ZonedDateTime, Duration> {
    @Override
    protected DoubleMixedJavaTimeFEELLib getLib() {
        NumericType<Double> numericType = new NumericTypeStub<>();
        BooleanType booleanType = new BooleanTypeStub();
        StringType stringType = new StringTypeStub();
        DateType<LocalDate, Duration> dateType = new DateTypeStub<>();
        TimeType<OffsetTime, Duration> timeType = new TimeTypeStub<>();
        DateTimeType<ZonedDateTime, Duration> dateTimeType = new DateTimeTypeStub<>();
        DurationType<Duration, Double> durationType = new DurationTypeStub<>();
        ListType listType = new ListTypeStub();
        ContextType contextType = new ContextTypeStub();
        NumericLib<Double> numericLib = new NumericLibStub<>();
        StringLib stringLib = new StringLibStub();
        BooleanLib booleanLib = new BooleanLibStub();
        DateTimeLib<Double, LocalDate, OffsetTime, ZonedDateTime, Duration> dateTimeLib = new DateTimeLibStub<>();
        DurationLib<LocalDate, Duration> durationLib = new DurationLibStub<>();
        ListLib listLib = new ListLibStub();
        return new DoubleMixedJavaTimeFEELLib(numericType, booleanType, stringType,
                dateType, timeType, dateTimeType, durationType,
                listType, contextType,
                numericLib, stringLib, booleanLib, dateTimeLib, durationLib, listLib);
    }
}