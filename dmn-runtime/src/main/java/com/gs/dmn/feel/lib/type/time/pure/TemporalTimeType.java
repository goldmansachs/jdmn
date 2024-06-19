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

import java.time.*;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalQueries;

import static com.gs.dmn.feel.lib.type.time.BaseDateTimeLib.FEEL_TIME;

public class TemporalTimeType extends BaseJavaTimeCalendarType implements TimeType<TemporalAccessor, TemporalAmount> {
    private final TemporalComparator comparator;

    public TemporalTimeType() {
        this(TemporalComparator.COMPARATOR);
    }

    public TemporalTimeType(TemporalComparator comparator) {
        this.comparator = comparator;
    }

    //
    // Time operators
    //

    @Override
    public Boolean timeIs(TemporalAccessor first, TemporalAccessor second) {
        if (first == null || second == null) {
            return first == second;
        }

        return sameTimeProperties(first, second);
    }

    @Override
    public Boolean timeEqual(TemporalAccessor first, TemporalAccessor second) {
        return this.comparator.equalTo(first, second);
    }

    @Override
    public Boolean timeNotEqual(TemporalAccessor first, TemporalAccessor second) {
        return this.comparator.notEqualTo(first, second);
    }

    @Override
    public Boolean timeLessThan(TemporalAccessor first, TemporalAccessor second) {
        return this.comparator.lessThan(first, second);
    }

    @Override
    public Boolean timeGreaterThan(TemporalAccessor first, TemporalAccessor second) {
        return this.comparator.greaterThan(first, second);
    }

    @Override
    public Boolean timeLessEqualThan(TemporalAccessor first, TemporalAccessor second) {
        return this.comparator.lessEqualThan(first, second);
    }

    @Override
    public Boolean timeGreaterEqualThan(TemporalAccessor first, TemporalAccessor second) {
        return this.comparator.greaterEqualThan(first, second);
    }

    @Override
    public TemporalAmount timeSubtract(TemporalAccessor first, TemporalAccessor second) {
        if (first == null || second == null) {
            return null;
        }

        // Subtraction is undefined for the case where only one of the values has a timezone
        if (hasTimezone(first) && !hasTimezone(second) || !hasTimezone(first) && hasTimezone(second)) {
            return null;
        }
        if (first instanceof Temporal && second instanceof Temporal) {
            return Duration.between((Temporal) second, (Temporal) first);
        } else {
            Long value1 = timeValue(first);
            Long value2 = timeValue(second);
            return Duration.ofSeconds(value1 - value2);
        }
    }

    @Override
    public TemporalAccessor timeAddDuration(TemporalAccessor time, TemporalAmount duration) {
        if (time == null || duration == null) {
            return null;
        }

        if (time instanceof LocalTime && duration instanceof Duration) {
            return ((LocalTime) time).plus(duration);
        } else if (time instanceof OffsetTime && duration instanceof Duration) {
            return ((OffsetTime) time).plus(duration);
        } else {
            // Has zone
            ZoneId zone = time.query(TemporalQueries.zoneId());
            if (!(zone instanceof ZoneOffset)) {
                LocalTime localTime = time.query(TemporalQueries.localTime());
                localTime = localTime.plus(duration);
                String str = localTime + "@" + zone;
                return FEEL_TIME.parse(str);
            } else {
                return OffsetTime.from(time);
            }
        }
    }

    @Override
    public TemporalAccessor timeSubtractDuration(TemporalAccessor time, TemporalAmount duration) {
        if (time == null || duration == null) {
            return null;
        }

        if (time instanceof LocalTime && duration instanceof Duration) {
            return ((LocalTime) time).minus(duration);
        } else if (time instanceof OffsetTime && duration instanceof Duration) {
            return ((OffsetTime) time).minus(duration);
        }
        // Has zone
        ZoneId zone = time.query(TemporalQueries.zoneId());
        if (!(zone instanceof ZoneOffset)) {
            LocalTime localTime = time.query(TemporalQueries.localTime());
            localTime = localTime.minus(duration);
            String str = localTime + "@" + zone;
            return FEEL_TIME.parse(str);
        } else {
            return OffsetTime.from(time);
        }
    }

    protected Integer compare(TemporalAccessor first, TemporalAccessor second) {
        if (first instanceof LocalTime && second instanceof LocalTime) {
            return ((LocalTime) first).compareTo((LocalTime) second);
        } else if (first instanceof OffsetTime && second instanceof OffsetTime) {
            return ((OffsetTime) first).compareTo((OffsetTime) second);
        }
        return  null;
    }
}
