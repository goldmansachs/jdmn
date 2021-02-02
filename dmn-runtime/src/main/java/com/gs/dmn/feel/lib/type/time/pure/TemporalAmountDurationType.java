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

import com.gs.dmn.feel.lib.type.bool.BooleanType;
import com.gs.dmn.feel.lib.type.time.DurationType;
import com.gs.dmn.feel.lib.type.bool.DefaultBooleanType;
import com.gs.dmn.feel.lib.type.time.JavaTimeType;
import com.gs.dmn.runtime.DMNRuntimeException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.Period;
import java.time.temporal.TemporalAmount;

public class TemporalAmountDurationType extends JavaTimeType implements DurationType<TemporalAmount, BigDecimal> {
    private final BooleanType booleanType;

    public TemporalAmountDurationType() {
        this.booleanType = new DefaultBooleanType();
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
    public Boolean durationIs(TemporalAmount first, TemporalAmount second) {
        if (first == null || second == null) {
            return first == second;
        }

        if (first instanceof Period && second instanceof Period) {
            Period period1 = (Period) first;
            Period period2 = (Period) second;
            return period1.isNegative() == period2.isNegative()
                    && period1.getYears() == period2.getYears()
                    && period1.getMonths() == period2.getMonths()
                    && period1.getDays() == period2.getDays();
        } else if (first instanceof Duration && second instanceof Duration) {
            Duration duration1 = (Duration) first;
            Duration duration2 = (Duration) second;
            return duration1.isNegative() == duration2.isNegative()
                    && duration1.toDays() == duration2.toDays()
                    && duration1.toHours() == duration2.toHours()
                    && duration1.toMinutes() == duration2.toMinutes()
                    && duration1.getSeconds() == duration2.getSeconds();
        } else {
            throw new DMNRuntimeException("Not supported yet");
        }

    }

    @Override
    public Boolean durationEqual(TemporalAmount first, TemporalAmount second) {
        if (first == null && second == null) {
            return true;
        } else if (first == null) {
            return false;
        } else if (second == null) {
            return false;
        } else {
            return first.equals(second);
        }
    }

    @Override
    public Boolean durationNotEqual(TemporalAmount first, TemporalAmount second) {
        return booleanType.booleanNot(durationEqual(first, second));
    }

    @Override
    public Boolean durationLessThan(TemporalAmount first, TemporalAmount second) {
        if (first == null && second == null) {
            return false;
        } else if (first == null) {
            return null;
        } else if (second == null) {
            return null;
        } else {
            return compare(first, second) < 0;
        }
    }

    @Override
    public Boolean durationGreaterThan(TemporalAmount first, TemporalAmount second) {
        if (first == null && second == null) {
            return false;
        } else if (first == null) {
            return null;
        } else if (second == null) {
            return null;
        } else {
            return compare(first, second) > 0;
        }
    }

    @Override
    public Boolean durationLessEqualThan(TemporalAmount first, TemporalAmount second) {
        if (first == null && second == null) {
            return false;
        } else if (first == null) {
            return null;
        } else if (second == null) {
            return null;
        } else {
            return compare(first, second) <= 0;
        }
    }

    @Override
    public Boolean durationGreaterEqualThan(TemporalAmount first, TemporalAmount second) {
        if (first == null && second == null) {
            return false;
        } else if (first == null) {
            return null;
        } else if (second == null) {
            return null;
        } else {
            return compare(first, second) >= 0;
        }
    }

    @Override
    public TemporalAmount durationAdd(TemporalAmount first, TemporalAmount second) {
        if (first == null || second == null) {
            return null;
        }

        if (first instanceof Period && second instanceof Period) {
            return ((Period) first).plus(second);
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
            return ((Period) first).minus(second);
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
            return secondValue == null ? null : BigDecimal.valueOf(firstValue).divide(BigDecimal.valueOf(secondValue), RoundingMode.HALF_DOWN);
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
            return Period.of((int) (months.longValue() / 12), (int) (months.longValue() % 12), 0);
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
            BigDecimal months = BigDecimal.valueOf(value((Period) first)).divide(second, RoundingMode.HALF_DOWN);
            return Period.ofMonths(months.intValue());
        } else if (first instanceof Duration) {
            BigDecimal seconds = BigDecimal.valueOf(value((Duration) first)).divide(second, RoundingMode.HALF_DOWN);
            return Duration.ofSeconds(seconds.longValue());
        } else {
            throw new DMNRuntimeException(String.format("Cannot divide '%s' by '%s'", first, second));
        }
    }

    private int compare(TemporalAmount first, TemporalAmount second) {
        if (first instanceof Period && second instanceof Period) {
            return Long.compare(value((Period) first), value((Period) second));
        } else if (first instanceof Duration && second instanceof Duration) {
            return ((Duration)first).compareTo((Duration)second);
        } else {
            throw new DMNRuntimeException(String.format("Cannot compare '%s' to '%s'", first, second));
        }
    }

    private Long value(Period duration) {
        return duration == null ? null : duration.toTotalMonths();
    }

    private Long value(Duration duration) {
        return duration == null ? null : duration.toMillis() / 1000;
    }
}
