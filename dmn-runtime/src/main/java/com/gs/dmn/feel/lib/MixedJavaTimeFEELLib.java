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
import com.gs.dmn.feel.lib.type.numeric.DefaultNumericLib;
import com.gs.dmn.feel.lib.type.numeric.DefaultNumericType;
import com.gs.dmn.feel.lib.type.numeric.NumericLib;
import com.gs.dmn.feel.lib.type.string.StringLib;
import com.gs.dmn.feel.lib.type.time.DateTimeLib;
import com.gs.dmn.feel.lib.type.time.DurationLib;
import com.gs.dmn.feel.lib.type.time.xml.DefaultDurationType;

import javax.xml.datatype.Duration;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZonedDateTime;

public class MixedJavaTimeFEELLib extends BaseMixedJavaTimeFEELLib<BigDecimal> implements StandardFEELLib<BigDecimal, LocalDate, OffsetTime, ZonedDateTime, Duration> {
    private static final NumericType<BigDecimal> NUMERIC_TYPE = new DefaultNumericType(LOGGER);
    private static final DurationType<Duration, BigDecimal> DURATION_TYPE = new DefaultDurationType(LOGGER, DATA_TYPE_FACTORY);
    private static final NumericLib<BigDecimal> NUMERIC_LIB = new DefaultNumericLib();

    public static final MixedJavaTimeFEELLib INSTANCE = new MixedJavaTimeFEELLib();

    public MixedJavaTimeFEELLib() {
        this(NUMERIC_TYPE,
                DURATION_TYPE,
                NUMERIC_LIB
        );
    }

    protected MixedJavaTimeFEELLib(
            NumericType<BigDecimal> numericType,
            DurationType<Duration, BigDecimal> durationType,
            NumericLib<BigDecimal> numericLib) {
        super(numericType, durationType, numericLib);
    }

    public MixedJavaTimeFEELLib(
            NumericType<BigDecimal> numericType,
            BooleanType booleanType,
            StringType stringType,
            DateType<LocalDate, Duration> dateType,
            TimeType<OffsetTime, Duration> timeType,
            DateTimeType<ZonedDateTime, Duration> dateTimeType,
            DurationType<Duration, BigDecimal> durationType,
            ListType listType,
            ContextType contextType,
            NumericLib<BigDecimal> numericLib,
            StringLib stringLib,
            BooleanLib booleanLib,
            DateTimeLib<BigDecimal, LocalDate, OffsetTime, ZonedDateTime, Duration> dateTimeLib,
            DurationLib<LocalDate, Duration> durationLib,
            ListLib listLib) {
        super(numericType, booleanType, stringType,
                dateType, timeType, dateTimeType, durationType,
                listType, contextType,
                numericLib, stringLib, booleanLib, dateTimeLib, durationLib, listLib
        );
    }

    @Override
    protected BigDecimal valueOf(long number) {
        return BigDecimal.valueOf(number);
    }

    @Override
    protected int intValue(BigDecimal number) {
        return number.intValue();
    }
}
