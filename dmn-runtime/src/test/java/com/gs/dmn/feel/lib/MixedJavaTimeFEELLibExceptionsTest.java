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
import com.gs.dmn.feel.lib.type.bool.BooleanLib;
import com.gs.dmn.feel.lib.type.bool.BooleanType;
import com.gs.dmn.feel.lib.type.context.ContextType;
import com.gs.dmn.feel.lib.type.function.FunctionType;
import com.gs.dmn.feel.lib.type.list.ListLib;
import com.gs.dmn.feel.lib.type.list.ListType;
import com.gs.dmn.feel.lib.type.numeric.NumericLib;
import com.gs.dmn.feel.lib.type.numeric.NumericType;
import com.gs.dmn.feel.lib.type.range.RangeLib;
import com.gs.dmn.feel.lib.type.range.RangeType;
import com.gs.dmn.feel.lib.type.string.StringLib;
import com.gs.dmn.feel.lib.type.string.StringType;
import com.gs.dmn.feel.lib.type.time.*;

import javax.xml.datatype.Duration;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZonedDateTime;

public class MixedJavaTimeFEELLibExceptionsTest extends BaseMixedJavaTimeFEELLibExceptionsTest<BigDecimal, LocalDate, OffsetTime, ZonedDateTime, Duration> {
    @Override
    protected MixedJavaTimeFEELLib getLib() {
        NumericType<BigDecimal> numericType = new NumericTypeStub<>();
        BooleanType booleanType = new BooleanTypeStub();
        StringType stringType = new StringTypeStub();
        DateType<LocalDate, Duration> dateType = new DateTypeStub<>();
        TimeType<OffsetTime, Duration> timeType = new TimeTypeStub<>();
        DateTimeType<ZonedDateTime, Duration> dateTimeType = new DateTimeTypeStub<>();
        DurationType<Duration, BigDecimal> durationType = new DurationTypeStub<>();
        ListType listType = new ListTypeStub();
        ContextType contextType = new ContextTypeStub();
        RangeType rangeType = new RangeTypeStub();
        FunctionType functionType = new FunctionTypeStub();
        NumericLib<BigDecimal> numericLib = new NumericLibStub<>();
        StringLib stringLib = new StringLibStub();
        BooleanLib booleanLib = new BooleanLibStub();
        DateTimeLib<BigDecimal, LocalDate, OffsetTime, ZonedDateTime, Duration> dateTimeLib = new DateTimeLibStub<>();
        DurationLib<LocalDate, Duration> durationLib = new DurationLibStub<>();
        ListLib listLib = new ListLibStub();
        RangeLib rangeLib = new RangeLibStub();
        return new MixedJavaTimeFEELLib(
                numericType, booleanType, stringType,
                dateType, timeType, dateTimeType, durationType,
                listType, contextType, rangeType, functionType,
                numericLib, stringLib, booleanLib, dateTimeLib, durationLib, listLib, rangeLib
        );
    }
}