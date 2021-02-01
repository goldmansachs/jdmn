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
package com.gs.dmn.feel.lib.type.time.xml;

import com.gs.dmn.feel.lib.DefaultFEELLib;
import com.gs.dmn.feel.lib.type.DurationType;
import com.gs.dmn.feel.lib.type.RelationalComparator;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import java.math.BigDecimal;

public class DefaultDurationType extends BaseDefaultDurationType implements DurationType<Duration, BigDecimal> {
    @Deprecated
    public DefaultDurationType() {
        this(DefaultFEELLib.DATA_TYPE_FACTORY, new DefaultDurationComparator());
    }

    @Deprecated
    public DefaultDurationType(DatatypeFactory dataTypeFactory) {
        this(dataTypeFactory, new DefaultDurationComparator());
    }

    public DefaultDurationType(DatatypeFactory dataTypeFactory, RelationalComparator<Duration> durationComparator) {
        super(dataTypeFactory, durationComparator);
    }

    @Override
    public Boolean durationIs(Duration first, Duration second) {
        if (first == null || second == null) {
            return first == second;
        }

        return first.getSign() == second.getSign()
                && first.getYears() == second.getYears()
                && first.getMonths() == second.getMonths()
                && first.getDays() == second.getDays()
                && first.getHours() == second.getHours()
                && first.getMinutes() == second.getHours()
                && first.getSeconds() == second.getSeconds();
    }

    //
    // Duration operators
    //
    @Override
    public Duration durationMultiply(Duration first, BigDecimal second) {
        return this.durationMultiply(first, (Number) second);
    }

    @Override
    public Duration durationDivide(Duration first, BigDecimal second) {
        return this.durationDivide(first, (Number) second);
    }
}
