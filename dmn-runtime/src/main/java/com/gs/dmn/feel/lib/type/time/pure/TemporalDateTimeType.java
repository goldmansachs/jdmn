/*
 * Copyright 2016 Goldman Sachs.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * <p>
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.gs.dmn.feel.lib.type.time.pure;

import com.gs.dmn.feel.lib.type.DateTimeType;
import com.gs.dmn.feel.lib.type.time.JavaTimeType;
import org.slf4j.Logger;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;

public class TemporalDateTimeType extends JavaTimeType implements DateTimeType<Temporal, TemporalAmount> {
    private final TemporalComparator comparator;

    @Deprecated
    public TemporalDateTimeType(Logger logger) {
        super(logger);
        this.comparator = new TemporalComparator(logger);
    }

    public TemporalDateTimeType(Logger logger, TemporalComparator comparator) {
        super(logger);
        this.comparator = comparator;
    }

    //
    // Date and time operators
    //

    @Override
    public Boolean dateTimeIs(Temporal first, Temporal second) {
        if (first == null || second == null) {
            return first == second;
        }

        if (first.getClass() != second.getClass()) {
            // Different kind
            return false;
        } else if (first instanceof LocalDateTime && second instanceof LocalDateTime) {
            LocalDateTime first1 = (LocalDateTime) first;
            LocalDateTime second1 = (LocalDateTime) second;
            return first1.getYear() == second1.getYear()
                    && first1.getMonth() == second1.getMonth()
                    && first1.getDayOfMonth() == second1.getDayOfMonth()
                    && first1.getHour() == second1.getHour()
                    && first1.getMinute() == second1.getMinute()
                    && first1.getSecond() == second1.getSecond();
        } else if (first instanceof OffsetDateTime && second instanceof OffsetDateTime) {
            OffsetDateTime first1 = (OffsetDateTime) first;
            OffsetDateTime second1 = (OffsetDateTime) second;
            return first1.getYear() == second1.getYear()
                    && first1.getMonth() == second1.getMonth()
                    && first1.getDayOfMonth() == second1.getDayOfMonth()
                    && first1.getHour() == second1.getHour()
                    && first1.getMinute() == second1.getMinute()
                    && first1.getSecond() == second1.getSecond()
                    && first1.getOffset().equals(second1.getOffset());
        } else if (first instanceof ZonedDateTime && second instanceof ZonedDateTime) {
            ZonedDateTime first1 = (ZonedDateTime) first;
            ZonedDateTime second1 = (ZonedDateTime) second;
            return first1.getYear() == second1.getYear()
                    && first1.getMonth() == second1.getMonth()
                    && first1.getDayOfMonth() == second1.getDayOfMonth()
                    && first1.getHour() == second1.getHour()
                    && first1.getMinute() == second1.getMinute()
                    && first1.getSecond() == second1.getSecond()
                    && (first1.getOffset() == second1.getOffset() || first1.getOffset().equals(second1.getOffset()));
        } else {
            return false;
        }
    }

    @Override
    public Boolean dateTimeEqual(Temporal first, Temporal second) {
        return this.comparator.equalTo(first, second);
    }

    @Override
    public Boolean dateTimeNotEqual(Temporal first, Temporal second) {
        return this.comparator.notEqualTo(first, second);
    }

    @Override
    public Boolean dateTimeLessThan(Temporal first, Temporal second) {
        return this.comparator.lessThan(first, second);
    }

    @Override
    public Boolean dateTimeGreaterThan(Temporal first, Temporal second) {
        return this.comparator.greaterThan(first, second);
    }

    @Override
    public Boolean dateTimeLessEqualThan(Temporal first, Temporal second) {
        return this.comparator.lessEqualThan(first, second);
    }

    @Override
    public Boolean dateTimeGreaterEqualThan(Temporal first, Temporal second) {
        return this.comparator.greaterEqualThan(first, second);
    }

    @Override
    public TemporalAmount dateTimeSubtract(Temporal first, Temporal second) {
        if (first == null || second == null) {
            return null;
        }

        try {
            return Duration.between(second, first);
        } catch (Exception e) {
            String message = String.format("dateTimeSubtract(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Temporal dateTimeAddDuration(Temporal dateTime, TemporalAmount duration) {
        if (dateTime == null || duration == null) {
            return null;
        }

        try {
            return dateTime.plus(duration);
        } catch (Exception e) {
            String message = String.format("dateTimeAddDuration(%s, %s)", dateTime, duration);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Temporal dateTimeSubtractDuration(Temporal dateTime, TemporalAmount duration) {
        if (dateTime == null || duration == null) {
            return null;
        }

        try {
            return dateTime.minus(duration);
        } catch (Exception e) {
            String message = String.format("dateTimeSubtractDuration(%s, %s)", dateTime, duration);
            logError(message, e);
            return null;
        }
    }
}
