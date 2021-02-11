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

import com.gs.dmn.feel.lib.type.time.TimeType;

import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;

public class TemporalTimeType extends BasePureCalendarType implements TimeType<Temporal, TemporalAmount> {
    private final TemporalComparator comparator;

    public TemporalTimeType() {
        this(new TemporalComparator());
    }

    public TemporalTimeType(TemporalComparator comparator) {
        this.comparator = comparator;
    }

    //
    // Time operators
    //

    @Override
    public boolean isTime(Object value) {
        return value instanceof LocalTime
                || value instanceof OffsetTime;
    }

    @Override
    public Boolean timeIs(Temporal first, Temporal second) {
        if (first == null || second == null) {
            return first == second;
        }

        return first.get(ChronoField.HOUR_OF_DAY) == second.get(ChronoField.HOUR_OF_DAY)
                && first.get(ChronoField.MINUTE_OF_DAY) == second.get(ChronoField.MINUTE_OF_DAY)
                && first.get(ChronoField.SECOND_OF_DAY) == second.get(ChronoField.SECOND_OF_DAY)
                && first.get(ChronoField.OFFSET_SECONDS) == second.get(ChronoField.OFFSET_SECONDS);
    }

    @Override
    public Boolean timeEqual(Temporal first, Temporal second) {
        return this.comparator.equalTo(first, second);
    }

    @Override
    public Boolean timeNotEqual(Temporal first, Temporal second) {
        return this.comparator.notEqualTo(first, second);
    }

    @Override
    public Boolean timeLessThan(Temporal first, Temporal second) {
        return this.comparator.lessThan(first, second);
    }

    @Override
    public Boolean timeGreaterThan(Temporal first, Temporal second) {
        return this.comparator.greaterThan(first, second);
    }

    @Override
    public Boolean timeLessEqualThan(Temporal first, Temporal second) {
        return this.comparator.lessEqualThan(first, second);
    }

    @Override
    public Boolean timeGreaterEqualThan(Temporal first, Temporal second) {
        return this.comparator.greaterEqualThan(first, second);
    }

    @Override
    public TemporalAmount timeSubtract(Temporal first, Temporal second) {
        if (first == null || second == null) {
            return null;
        }

        return java.time.Duration.between(second, first);
    }

    @Override
    public Temporal timeAddDuration(Temporal time, TemporalAmount duration) {
        if (time == null || duration == null) {
            return null;
        }

        return time.plus(duration);
    }

    @Override
    public Temporal timeSubtractDuration(Temporal time, TemporalAmount duration) {
        if (time == null || duration == null) {
            return null;
        }

        return time.minus(duration);
    }

    protected Integer compare(Temporal first, Temporal second) {
        if (first instanceof LocalTime && second instanceof LocalTime) {
            return ((LocalTime) first).compareTo((LocalTime) second);
        } else if (first instanceof OffsetTime && second instanceof OffsetTime) {
            return ((OffsetTime) first).compareTo((OffsetTime) second);
        }
        return  null;
    }
}
