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

import com.gs.dmn.feel.lib.type.time.DateTimeType;
import com.gs.dmn.runtime.DMNRuntimeException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;

public class TemporalDateTimeType extends BaseJavaTimeCalendarType implements DateTimeType<TemporalAccessor, TemporalAmount> {
    private final TemporalComparator comparator;

    public TemporalDateTimeType() {
        this(TemporalComparator.COMPARATOR);
    }

    public TemporalDateTimeType(TemporalComparator comparator) {
        this.comparator = comparator;
    }

    //
    // Date and time operators
    //
    @Override
    public Boolean dateTimeIs(TemporalAccessor first, TemporalAccessor second) {
        if (first == null || second == null) {
            return first == second;
        }

        return sameDateTimeProperties(first, second);
    }

    @Override
    public Boolean dateTimeEqual(TemporalAccessor first, TemporalAccessor second) {
        return this.comparator.equalTo(first, second);
    }

    @Override
    public Boolean dateTimeNotEqual(TemporalAccessor first, TemporalAccessor second) {
        return this.comparator.notEqualTo(first, second);
    }

    @Override
    public Boolean dateTimeLessThan(TemporalAccessor first, TemporalAccessor second) {
        return this.comparator.lessThan(first, second);
    }

    @Override
    public Boolean dateTimeGreaterThan(TemporalAccessor first, TemporalAccessor second) {
        return this.comparator.greaterThan(first, second);
    }

    @Override
    public Boolean dateTimeLessEqualThan(TemporalAccessor first, TemporalAccessor second) {
        return this.comparator.lessEqualThan(first, second);
    }

    @Override
    public Boolean dateTimeGreaterEqualThan(TemporalAccessor first, TemporalAccessor second) {
        return this.comparator.greaterEqualThan(first, second);
    }

    @Override
    public TemporalAmount dateTimeSubtract(TemporalAccessor first, Object secondObj) {
        TemporalAccessor second = (TemporalAccessor) secondObj;
        if (first == null || second == null) {
            return null;
        }

        // Subtraction is undefined for the case where only one of the values has a timezone
        if (hasTimezone(first) && !hasTimezone(second) || !hasTimezone(first) && hasTimezone(second)) {
            return null;
        }

        return Duration.between((Temporal) second, (Temporal) first);
    }

    @Override
    public TemporalAccessor dateTimeAddDuration(TemporalAccessor dateTime, TemporalAmount duration) {
        if (dateTime == null || duration == null) {
            return null;
        }
        if (!(isYearsAndMonthsDuration(duration) || isDaysAndTimeDuration(duration))) {
            throw new DMNRuntimeException(String.format("Cannot add '%s' and '%s'", dateTime, duration));
        }

        if (dateTime instanceof ZonedDateTime) {
            return ((ZonedDateTime) dateTime).plus(duration);
        } else if (dateTime instanceof OffsetDateTime) {
            return ((OffsetDateTime) dateTime).plus(duration);
        } else if (dateTime instanceof LocalDateTime) {
            return ((LocalDateTime) dateTime).plus(duration);
        }
        throw new DMNRuntimeException(String.format("Cannot add '%s' and '%s'", dateTime, duration));
    }

    @Override
    public TemporalAccessor dateTimeSubtractDuration(TemporalAccessor dateTime, TemporalAmount duration) {
        if (dateTime == null || duration == null) {
            return null;
        }

        if (dateTime instanceof LocalDateTime) {
            return ((LocalDateTime) dateTime).minus(duration);
        } else if (dateTime instanceof OffsetDateTime) {
            return ((OffsetDateTime) dateTime).minus(duration);
        } else if (dateTime instanceof ZonedDateTime) {
            return ((ZonedDateTime) dateTime).minus(duration);
        }
        throw new DMNRuntimeException(String.format("Cannot subtract '%s' and '%s'", dateTime, duration));
    }
}
