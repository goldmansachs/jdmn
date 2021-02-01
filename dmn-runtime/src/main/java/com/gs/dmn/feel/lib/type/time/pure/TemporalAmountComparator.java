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

import com.gs.dmn.feel.lib.type.RelationalComparator;

import java.time.Duration;
import java.time.Period;
import java.time.temporal.TemporalAmount;
import java.util.function.Supplier;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class TemporalAmountComparator implements RelationalComparator<TemporalAmount> {
    public TemporalAmountComparator() {
    }

    @Override
    public Integer compare(TemporalAmount first, TemporalAmount second) {
        // Time
        if (first instanceof Period && second instanceof Period) {
            Period diff = ((Period) first).minus(second);
            if (diff.isNegative()) {
                return -1;
            } else if (diff.isZero()) {
                return 0;
            } else {
                return +1;
            }
        } else if (first instanceof Duration && second instanceof Duration) {
            return ((Duration) first).compareTo((Duration) second);
        }
        return  null;
    }

    @Override
    public Boolean equalTo(TemporalAmount first, TemporalAmount second) {
        return applyOperator(first, second, new Supplier[] {
                () -> TRUE,
                () -> FALSE,
                () -> FALSE,
                () -> { Integer result = compare(first, second); return result != null && result == 0; }
        });
    }

    @Override
    public Boolean lessThan(TemporalAmount first, TemporalAmount second) {
        return applyOperator(first, second, new Supplier[] {
                () -> FALSE,
                () -> null,
                () -> null,
                () -> { Integer result = compare(first, second); return result != null && result < 0; }
        });
    }

    @Override
    public Boolean greaterThan(TemporalAmount first, TemporalAmount second) {
        return applyOperator(first, second, new Supplier[] {
                () -> FALSE,
                () -> null,
                () -> null,
                () -> { Integer result = compare(first, second); return result != null && result > 0; }
        });
    }

    @Override
    public Boolean lessEqualThan(TemporalAmount first, TemporalAmount second) {
        return applyOperator(first, second, new Supplier[] {
                () -> TRUE,
                () -> null,
                () -> null,
                () -> { Integer result = compare(first, second); return result != null && result <= 0; }
        });
    }

    @Override
    public Boolean greaterEqualThan(TemporalAmount first, TemporalAmount second) {
        return applyOperator(first, second, new Supplier[] {
                () -> TRUE,
                () -> null,
                () -> null,
                () -> { Integer result = compare(first, second); return result != null && result >= 0; }
        });
    }
}
