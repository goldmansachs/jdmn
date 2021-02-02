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
import com.gs.dmn.runtime.DMNRuntimeException;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;

public class DoubleDurationType extends BaseDefaultDurationType implements DurationType<Duration, Double> {
    @Deprecated
    public DoubleDurationType() {
        this(DefaultFEELLib.DATA_TYPE_FACTORY, new DefaultDurationComparator());
    }

    @Deprecated
    public DoubleDurationType(DatatypeFactory dataTypeFactory) {
        this(dataTypeFactory, new DefaultDurationComparator());
    }

    public DoubleDurationType(DatatypeFactory dataTypeFactory, RelationalComparator<Duration> durationComparator) {
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
    public Double durationDivide(Duration first, Duration second) {
        if (first == null || second == null) {
            return null;
        }

        if (isYearMonthDuration(first) && isYearMonthDuration(second)) {
            Long firstValue = monthsValue(first);
            Long secondValue = monthsValue(second);
            return secondValue == 0 ? null : firstValue.doubleValue() / secondValue.doubleValue();
        } else if (isDayTimeDuration(first) && isDayTimeDuration(second)) {
            Long firstValue = secondsValue(first);
            Long secondValue = secondsValue(second);
            return secondValue == 0 ? null : firstValue.doubleValue() / secondValue.doubleValue();
        } else {
            throw new DMNRuntimeException(String.format("Cannot divide '%s' by '%s'", first, second));
        }
    }

    @Override
    public Duration durationMultiplyNumber(Duration first, Double second) {
        if (first == null || second == null) {
            return null;
        }

        if (isYearMonthDuration(first)) {
            Double months = monthsValue(first) * second;
            return makeYearsMonthsDuration(months);
        } else if (isDayTimeDuration(first)) {
            Double seconds = secondsValue(first) * second;
            return makeDaysTimeDuration(seconds);
        } else {
            throw new DMNRuntimeException(String.format("Cannot divide '%s' by '%s'", first, second));
        }
    }

    @Override
    public Duration durationDivideNumber(Duration first, Double second) {
        if (first == null || second == null) {
            return null;
        }
        if (second == 0) {
            return null;
        }

        if (isYearMonthDuration(first)) {
            Double months = monthsValue(first).doubleValue() / second;
            return makeYearsMonthsDuration(months);
        } else if (isDayTimeDuration(first)) {
            Double seconds = secondsValue(first).doubleValue() / second;
            return makeDaysTimeDuration(seconds);
        } else {
            throw new DMNRuntimeException(String.format("Cannot divide '%s' by '%s'", first, second));
        }
    }

    @Override
    public boolean isDuration(Object value) {
        return value instanceof Duration;
    }
}
