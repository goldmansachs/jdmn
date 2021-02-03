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

import com.gs.dmn.feel.lib.type.RelationalComparator;
import com.gs.dmn.feel.lib.type.time.DurationType;
import com.gs.dmn.runtime.DMNRuntimeException;

import javax.xml.datatype.Duration;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class DefaultDurationType extends BaseDefaultDurationType implements DurationType<Duration, BigDecimal> {
    public DefaultDurationType() {
        this(new DefaultDurationComparator());
    }

    public DefaultDurationType(RelationalComparator<Duration> durationComparator) {
        super(durationComparator);
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
    public BigDecimal durationDivide(Duration first, Duration second) {
        if (first == null || second == null) {
            return null;
        }

        if (isYearsAndMonthsDuration(first) && isYearsAndMonthsDuration(second)) {
            Long firstValue = monthsValue(first);
            Long secondValue = monthsValue(second);
            return secondValue == 0 ? null : BigDecimal.valueOf(firstValue).divide(BigDecimal.valueOf(secondValue), RoundingMode.HALF_DOWN);
        } else if (isDaysAndTimeDuration(first) && isDaysAndTimeDuration(second)) {
            Long firstValue = secondsValue(first);
            Long secondValue = secondsValue(second);
            return secondValue == 0 ? null : BigDecimal.valueOf(firstValue).divide(BigDecimal.valueOf(secondValue), RoundingMode.HALF_DOWN);
        } else {
            throw new DMNRuntimeException(String.format("Cannot divide '%s' by '%s'", first, second));
        }
    }

    @Override
    public Duration durationMultiplyNumber(Duration first, BigDecimal second) {
        if (first == null || second == null) {
            return null;
        }

        if (isYearsAndMonthsDuration(first)) {
            BigDecimal months = BigDecimal.valueOf(monthsValue(first)).multiply(second);
            return XMLDurationFactory.INSTANCE.yearMonthOf(months.longValue());
        } else if (isDaysAndTimeDuration(first)) {
            BigDecimal seconds = BigDecimal.valueOf(secondsValue(first)).multiply(second);
            return XMLDurationFactory.INSTANCE.dayTimeOf(seconds);
        } else {
            throw new DMNRuntimeException(String.format("Cannot divide '%s' by '%s'", first, second));
        }
    }

    @Override
    public Duration durationDivideNumber(Duration first, BigDecimal second) {
        if (first == null || second == null) {
            return null;
        }
        if (second.signum() == 0) {
            return null;
        }

        if (isYearsAndMonthsDuration(first)) {
            BigDecimal months = BigDecimal.valueOf(monthsValue(first)).divide(second, RoundingMode.HALF_DOWN);
            return XMLDurationFactory.INSTANCE.yearMonthOf(months.longValue());
        } else if (isDaysAndTimeDuration(first)) {
            BigDecimal seconds = BigDecimal.valueOf(secondsValue(first)).divide(second, RoundingMode.HALF_DOWN);
            return XMLDurationFactory.INSTANCE.dayTimeOf(seconds);
        } else {
            throw new DMNRuntimeException(String.format("Cannot divide '%s' by '%s'", first, second));
        }
    }
}
