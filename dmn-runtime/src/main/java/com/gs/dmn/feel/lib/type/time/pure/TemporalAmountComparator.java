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

import com.gs.dmn.feel.lib.type.time.DateTimeComparator;

import java.time.Duration;
import java.time.Period;
import java.time.temporal.TemporalAmount;

public class TemporalAmountComparator extends BasePureCalendarType implements DateTimeComparator<TemporalAmount> {
    public static TemporalAmountComparator COMPARATOR = new TemporalAmountComparator();

    protected TemporalAmountComparator() {
    }

    @Override
    public Integer compareTo(TemporalAmount first, TemporalAmount second) {
        if (first instanceof Period && second instanceof Period) {
            Long firstValue = monthsValue((Period) first);
            Long secondValue = monthsValue((Period) second);
            return firstValue.compareTo(secondValue);
        } else if (first instanceof Duration && second instanceof Duration) {
            Long firstValue = secondsValue((Duration) first);
            Long secondValue = secondsValue((Duration) second);
            return firstValue.compareTo(secondValue);
        }
        return  null;
    }
}
