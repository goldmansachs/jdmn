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
        this(DefaultDurationComparator.COMPARATOR);
    }

    public DefaultDurationType(RelationalComparator<Duration> durationComparator) {
        super(durationComparator);
    }

    @Override
    public Boolean durationIs(Duration first, Duration second) {
        if (first == null || second == null) {
            return first == second;
        }

        if (isYearsAndMonthsDuration(first) && isYearsAndMonthsDuration(second)) {
            return durationEqual(first, second);
        } else if(isDayTimeDuration(first) && isDayTimeDuration(second)) {
            return durationEqual(first, second);
        } else {
            return false;
        }
    }

    @Override
    public BigDecimal durationDivide(Duration first, Duration second) {
        if (first == null || second == null) {
            return null;
        }

        if (isYearsAndMonthsDuration(first) && isYearsAndMonthsDuration(second)) {
            Long firstValue = monthsValue(first);
            Long secondValue = monthsValue(second);
            return divideNumbers(secondValue, firstValue);
        } else if (isDaysAndTimeDuration(first) && isDaysAndTimeDuration(second)) {
            Long firstValue = secondsValue(first);
            Long secondValue = secondsValue(second);
            return divideNumbers(secondValue, firstValue);
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
            Long firstValue = monthsValue(first);
            BigDecimal months = multiplyNumbers(firstValue, second);
            return XMLDurationFactory.INSTANCE.yearMonthFromValue(months.longValue());
        } else if (isDaysAndTimeDuration(first)) {
            Long firstValue = secondsValue(first);
            BigDecimal seconds = multiplyNumbers(firstValue, second);
            return XMLDurationFactory.INSTANCE.dayTimeFromValue(seconds.longValue());
        } else {
            throw new DMNRuntimeException(String.format("Cannot multiply '%s' by '%s'", first, second));
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
            Long firstValue = monthsValue(first);
            BigDecimal months = divideNumbers(firstValue, second);
            return XMLDurationFactory.INSTANCE.yearMonthFromValue(months.longValue());
        } else if (isDaysAndTimeDuration(first)) {
            Long firstValue = secondsValue(first);
            BigDecimal seconds = divideNumbers(firstValue, second);
            return XMLDurationFactory.INSTANCE.dayTimeFromValue(seconds.longValue());
        } else {
            throw new DMNRuntimeException(String.format("Cannot divide '%s' by '%s'", first, second));
        }
    }

    private BigDecimal multiplyNumbers(Long firstValue, BigDecimal second) {
        return BigDecimal.valueOf(firstValue).multiply(second);
    }

    private BigDecimal divideNumbers(Long secondValue, Long firstValue) {
        return secondValue == 0 ? null : BigDecimal.valueOf(firstValue).divide(BigDecimal.valueOf(secondValue), RoundingMode.HALF_DOWN);
    }

    private BigDecimal divideNumbers(Long firstValue, BigDecimal second) {
        return BigDecimal.ZERO.equals(second) ? null : BigDecimal.valueOf(firstValue).divide(second, RoundingMode.HALF_DOWN);
    }
}
