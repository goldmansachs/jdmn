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

import com.gs.dmn.feel.lib.type.numeric.DefaultNumericLib;
import com.gs.dmn.feel.lib.type.numeric.DefaultNumericType;
import com.gs.dmn.feel.lib.type.time.xml.DefaultDurationType;

import javax.xml.datatype.Duration;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZonedDateTime;

public class MixedJavaTimeFEELLib extends BaseMixedJavaTimeFEELLib<BigDecimal> implements StandardFEELLib<BigDecimal, LocalDate, OffsetTime, ZonedDateTime, Duration> {
    public static MixedJavaTimeFEELLib INSTANCE = new MixedJavaTimeFEELLib();

    public MixedJavaTimeFEELLib() {
        super(new DefaultNumericType(LOGGER),
                new DefaultDurationType(LOGGER, DATA_TYPE_FACTORY),
                new DefaultNumericLib()
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
