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

import com.gs.dmn.feel.lib.type.bool.BooleanLib;
import com.gs.dmn.feel.lib.type.bool.BooleanType;
import com.gs.dmn.feel.lib.type.context.ContextType;
import com.gs.dmn.feel.lib.type.list.ListLib;
import com.gs.dmn.feel.lib.type.list.ListType;
import com.gs.dmn.feel.lib.type.numeric.DoubleNumericLib;
import com.gs.dmn.feel.lib.type.numeric.DoubleNumericType;
import com.gs.dmn.feel.lib.type.numeric.NumericLib;
import com.gs.dmn.feel.lib.type.numeric.NumericType;
import com.gs.dmn.feel.lib.type.range.RangeLib;
import com.gs.dmn.feel.lib.type.range.RangeType;
import com.gs.dmn.feel.lib.type.string.StringLib;
import com.gs.dmn.feel.lib.type.string.StringType;
import com.gs.dmn.feel.lib.type.time.*;
import com.gs.dmn.feel.lib.type.time.xml.DoubleDurationType;

import javax.xml.datatype.Duration;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZonedDateTime;

public class DoubleMixedJavaTimeFEELLib extends BaseMixedJavaTimeFEELLib<Double> {
    private static final NumericType<Double> NUMERIC_TYPE = new DoubleNumericType();
    private static final DurationType<Duration, Double> DURATION_TYPE = new DoubleDurationType(DATA_TYPE_FACTORY);
    private static final NumericLib<Double> NUMERIC_LIB = new DoubleNumericLib();

    public static final DoubleMixedJavaTimeFEELLib INSTANCE = new DoubleMixedJavaTimeFEELLib();

    public DoubleMixedJavaTimeFEELLib() {
        this(NUMERIC_TYPE,
                DURATION_TYPE,
                NUMERIC_LIB
        );
    }

    protected DoubleMixedJavaTimeFEELLib(
            NumericType<Double> numericType,
            DurationType<Duration, Double> durationType,
            NumericLib<Double> numericLib) {
        super(numericType, durationType, numericLib);
    }

    protected DoubleMixedJavaTimeFEELLib(NumericType<Double> numericType, BooleanType booleanType, StringType stringType, DateType<LocalDate, Duration> dateType, TimeType<OffsetTime, Duration> timeType, DateTimeType<ZonedDateTime, Duration> dateTimeType, DurationType<Duration, Double> durationType, ListType listType, ContextType contextType, RangeType rangeType, NumericLib<Double> numericLib, StringLib stringLib, BooleanLib booleanLib, DateTimeLib<Double, LocalDate, OffsetTime, ZonedDateTime, Duration> dateTimeLib, DurationLib<LocalDate, Duration> durationLib, ListLib listLib, RangeLib rangeLib) {
        super(numericType, booleanType, stringType,
                dateType, timeType, dateTimeType, durationType,
                listType, contextType, rangeType,
                numericLib, stringLib, booleanLib, dateTimeLib, durationLib, listLib, rangeLib
        );
    }

    //
    // Extra conversion functions
    //
    @Override
    protected Double valueOf(long number) {
        return Double.valueOf(number);
    }

    @Override
    protected int intValue(Double number) {
        return number.intValue();
    }
}
