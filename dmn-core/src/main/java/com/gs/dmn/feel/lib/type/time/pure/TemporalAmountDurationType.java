/**
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

import com.gs.dmn.feel.lib.type.BaseType;
import com.gs.dmn.feel.lib.type.BooleanType;
import com.gs.dmn.feel.lib.type.DurationType;
import com.gs.dmn.feel.lib.type.logic.DefaultBooleanType;
import com.gs.dmn.runtime.DMNRuntimeException;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Period;
import java.time.temporal.TemporalAmount;

public class TemporalAmountDurationType extends BaseType implements DurationType<TemporalAmount, BigDecimal> {
    private final BooleanType booleanType;

    public TemporalAmountDurationType(Logger logger) {
        super(logger);
        this.booleanType = new DefaultBooleanType(logger);
    }

    //
    // TemporalAmount operators
    //

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

        try {
            return plus(first, second);
        } catch (Throwable e) {
            String message = String.format("durationAdd(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public TemporalAmount durationSubtract(TemporalAmount first, TemporalAmount second) {
        if (first == null || second == null) {
            return null;
        }

        try {
            return minus(first, second);
        } catch (Throwable e) {
            String message = String.format("durationSubtract(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public TemporalAmount durationMultiply(TemporalAmount first, BigDecimal second) {
        if (first == null || second == null) {
            return null;
        }

        try {
            return multiply(first, second.intValue());
        } catch (Throwable e) {
            String message = String.format("durationMultiply(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public TemporalAmount durationDivide(TemporalAmount first, BigDecimal second) {
        if (first == null || second == null) {
            return null;
        }

        try {
            return divide(first, second.intValue());
        } catch (Throwable e) {
            String message = String.format("durationDivide(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    private int compare(TemporalAmount first, TemporalAmount second) {
        if (first instanceof Period && second instanceof Period) {
            return Long.compare(((Period) first).toTotalMonths(), ((Period) second).toTotalMonths());
        } else if (first instanceof Duration && second instanceof Duration) {
            return ((Duration)first).compareTo((Duration)second);
        } else {
            throw new DMNRuntimeException(String.format("Cannot compare '%s' to '%s'", first, second));
        }
    }

    private TemporalAmount plus(TemporalAmount first, TemporalAmount second) {
        if (first instanceof Period && second instanceof Period) {
            return ((Period) first).plus(second);
        } else if (first instanceof Duration && second instanceof Duration) {
            return ((Duration)first).plus((Duration)second);
        } else {
            throw new DMNRuntimeException(String.format("Cannot add '%s' and '%s'", first, second));
        }
    }

    private TemporalAmount minus(TemporalAmount first, TemporalAmount second) {
        if (first instanceof Period && second instanceof Period) {
            return ((Period) first).minus(second);
        } else if (first instanceof Duration && second instanceof Duration) {
            return ((Duration)first).minus((Duration)second);
        } else {
            throw new DMNRuntimeException(String.format("Cannot subtract '%s' and '%s'", first, second));
        }
    }

    private TemporalAmount multiply(TemporalAmount first, int second) {
        if (first instanceof Period) {
            return ((Period) first).multipliedBy(second);
        } else if (first instanceof Duration) {
            return ((Duration)first).multipliedBy(second);
        } else {
            throw new DMNRuntimeException(String.format("Cannot multiply '%s' by '%s'", first, second));
        }
    }

    private TemporalAmount divide(TemporalAmount first, int second) {
        if (first instanceof Period) {
            long months = ((Period)first).toTotalMonths() / second;
            return Period.ofMonths((int)months);
        } else if (first instanceof Duration) {
            return ((Duration)first).dividedBy(second);
        } else {
            throw new DMNRuntimeException(String.format("Cannot divide '%s' by '%s'", first, second));
        }
    }
}
