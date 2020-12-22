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

import com.gs.dmn.feel.lib.type.*;
import com.gs.dmn.feel.lib.type.bool.BooleanLib;
import com.gs.dmn.feel.lib.type.list.ListLib;
import com.gs.dmn.feel.lib.type.numeric.*;
import com.gs.dmn.feel.lib.type.string.StringLib;
import com.gs.dmn.feel.lib.type.time.DateTimeLib;
import com.gs.dmn.feel.lib.type.time.DurationLib;
import com.gs.dmn.feel.lib.type.time.xml.DoubleDurationType;

import javax.xml.datatype.Duration;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZonedDateTime;

public class DoubleMixedJavaTimeFEELLib extends BaseMixedJavaTimeFEELLib<Double> {
    private static final NumericType<Double> NUMERIC_TYPE = new DoubleNumericType(LOGGER);
    private static final DurationType<Duration, Double> DURATION_TYPE = new DoubleDurationType(LOGGER, DATA_TYPE_FACTORY);
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

    protected DoubleMixedJavaTimeFEELLib(NumericType<Double> numericType, BooleanType booleanType, StringType stringType, DateType<LocalDate, Duration> dateType, TimeType<OffsetTime, Duration> timeType, DateTimeType<ZonedDateTime, Duration> dateTimeType, DurationType<Duration, Double> durationType, ListType listType, ContextType contextType, NumericLib<Double> numericLib, StringLib stringLib, BooleanLib booleanLib, DateTimeLib<Double, LocalDate, OffsetTime, ZonedDateTime, Duration> dateTimeLib, DurationLib<LocalDate, Duration> durationLib, ListLib listLib) {
        super(numericType, booleanType, stringType,
                dateType, timeType, dateTimeType, durationType,
                listType, contextType,
                numericLib, stringLib, booleanLib, dateTimeLib, durationLib, listLib
        );
    }

    @Override
    protected Double valueOf(long number) {
        return Double.valueOf(number);
    }

    @Override
    protected int intValue(Double number) {
        return number.intValue();
    }
}
