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
package com.gs.dmn.feel.lib.type.time.pure;

import com.gs.dmn.feel.lib.type.time.DurationType;
import com.gs.dmn.runtime.DMNRuntimeException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.Period;
import java.time.temporal.TemporalAmount;

public class TemporalAmountDurationType extends BasePureCalendarType implements DurationType<TemporalAmount, BigDecimal> {
    private final TemporalAmountComparator comparator;

    public TemporalAmountDurationType() {
        this.comparator = TemporalAmountComparator.COMPARATOR;
    }

    //
    // TemporalAmount operators
    //
    @Override
    public boolean isDuration(Object value) {
        return value instanceof TemporalAmount;
    }

    @Override
    public boolean isYearsAndMonthsDuration(Object value) {
        return value instanceof Period;
    }

    @Override
    public boolean isDaysAndTimeDuration(Object value) {
        return value instanceof Duration;
    }

    @Override
    public Long durationValue(TemporalAmount duration) {
        if (duration == null) {
            return null;
        }

        if (isYearsAndMonthsDuration(duration)) {
            return monthsValue((Period) duration);
        } else if (isDaysAndTimeDuration(duration)) {
            return secondsValue((Duration) duration);
        } else {
            throw new DMNRuntimeException(String.format("value() not supported yet for '%s'", duration));
        }
    }

    @Override
    public Boolean durationIs(TemporalAmount first, TemporalAmount second) {
        if (first == null || second == null) {
            return first == second;
        }

        if (first instanceof Period && second instanceof Period) {
            return durationEqual(first, second);
        } else if (first instanceof Duration && second instanceof Duration) {
            return durationEqual(first, second);
        } else {
            return false;
        }

    }

    @Override
    public Boolean durationEqual(TemporalAmount first, TemporalAmount second) {
        return this.comparator.equalTo(first, second);
    }

    @Override
    public Boolean durationNotEqual(TemporalAmount first, TemporalAmount second) {
        return this.comparator.notEqualTo(first, second);
    }

    @Override
    public Boolean durationLessThan(TemporalAmount first, TemporalAmount second) {
        return this.comparator.lessThan(first, second);
    }

    @Override
    public Boolean durationGreaterThan(TemporalAmount first, TemporalAmount second) {
        return this.comparator.greaterThan(first, second);
    }

    @Override
    public Boolean durationLessEqualThan(TemporalAmount first, TemporalAmount second) {
        return this.comparator.lessEqualThan(first, second);
    }

    @Override
    public Boolean durationGreaterEqualThan(TemporalAmount first, TemporalAmount second) {
        return this.comparator.greaterEqualThan(first, second);
    }

    @Override
    public TemporalAmount durationAdd(TemporalAmount first, TemporalAmount second) {
        if (first == null || second == null) {
            return null;
        }

        if (first instanceof Period && second instanceof Period) {
            return ((Period) first).plus(second).normalized();
        } else if (first instanceof Duration && second instanceof Duration) {
            return ((Duration) first).plus((Duration) second);
        } else {
            throw new DMNRuntimeException(String.format("Cannot add '%s' and '%s'", first, second));
        }
    }

    @Override
    public TemporalAmount durationSubtract(TemporalAmount first, TemporalAmount second) {
        if (first == null || second == null) {
            return null;
        }

        if (first instanceof Period && second instanceof Period) {
            return ((Period) first).minus(second).normalized();
        } else if (first instanceof Duration && second instanceof Duration) {
            return ((Duration) first).minus((Duration) second);
        } else {
            throw new DMNRuntimeException(String.format("Cannot subtract '%s' and '%s'", first, second));
        }
    }

    @Override
    public BigDecimal durationDivide(TemporalAmount first, TemporalAmount second) {
        if (first == null || second == null) {
            return null;
        }

        if (first instanceof Period && second instanceof Period) {
            Long firstValue = value((Period) first);
            Long secondValue = value((Period) second);
            return secondValue == 0 ? null : BigDecimal.valueOf(firstValue).divide(BigDecimal.valueOf(secondValue), RoundingMode.HALF_DOWN);
        } else if (first instanceof Duration && second instanceof Duration) {
            Long firstValue = value((Duration) first);
            Long secondValue = value((Duration) second);
            return secondValue == 0 ? null : BigDecimal.valueOf(firstValue).divide(BigDecimal.valueOf(secondValue), RoundingMode.HALF_DOWN);
        } else {
            throw new DMNRuntimeException(String.format("Cannot divide '%s' by '%s'", first, second));
        }
    }

    @Override
    public TemporalAmount durationMultiplyNumber(TemporalAmount first, BigDecimal second) {
        if (first == null || second == null) {
            return null;
        }

        if (first instanceof Period) {
            BigDecimal months = BigDecimal.valueOf(value((Period) first)).multiply(second);
            return Period.of((int) (months.longValue() / 12), (int) (months.longValue() % 12), 0).normalized();
        } else if (first instanceof Duration) {
            BigDecimal seconds = BigDecimal.valueOf(value((Duration) first)).multiply(second);
            return Duration.ofSeconds(seconds.longValue());
        } else {
            throw new DMNRuntimeException(String.format("Cannot multiply '%s' by '%s'", first, second));
        }
    }

    @Override
    public TemporalAmount durationDivideNumber(TemporalAmount first, BigDecimal second) {
        if (first == null || second == null) {
            return null;
        }

        if (first instanceof Period) {
            BigDecimal bdMonths = BigDecimal.valueOf(value((Period) first)).divide(second, RoundingMode.HALF_DOWN);
            int years = bdMonths.intValue() / 12;
            int months = bdMonths.intValue() % 12;
            return Period.of(years, months, 0).normalized();
        } else if (first instanceof Duration) {
            BigDecimal seconds = BigDecimal.valueOf(value((Duration) first)).divide(second, RoundingMode.HALF_DOWN);
            return Duration.ofSeconds(seconds.longValue());
        } else {
            throw new DMNRuntimeException(String.format("Cannot divide '%s' by '%s'", first, second));
        }
    }

    private Long value(Period duration) {
        return duration == null ? null : duration.toTotalMonths();
    }

    private Long value(Duration duration) {
        return duration == null ? null : duration.toMillis() / 1000;
    }
}
