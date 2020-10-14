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
package com.gs.dmn.feel.lib.type.time.mixed;

import com.gs.dmn.feel.lib.type.TimeType;
import com.gs.dmn.feel.lib.type.time.xml.DefaultDateTimeLib;
import org.slf4j.Logger;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZonedDateTime;

public class OffsetTimeType extends JavaTimeCalendarType implements TimeType<OffsetTime, Duration> {
    private static final LocalDate EPOCH = LocalDate.of(1970, 1, 1);

    private final OffsetTimeComparator comparator;

    @Deprecated
    public OffsetTimeType(Logger logger, DatatypeFactory datatypeFactory) {
        this(logger, datatypeFactory, new OffsetTimeComparator(logger));
    }

    public OffsetTimeType(Logger logger, DatatypeFactory datatypeFactory, OffsetTimeComparator comparator) {
        super(logger, datatypeFactory);
        this.comparator = comparator;
    }

    //
    // Time operators
    //

    @Override
    public Boolean timeEqual(OffsetTime first, OffsetTime second) {
        return this.comparator.equalTo(first, second);
    }

    @Override
    public Boolean timeNotEqual(OffsetTime first, OffsetTime second) {
        return this.comparator.notEqualTo(first, second);
    }

    @Override
    public Boolean timeLessThan(OffsetTime first, OffsetTime second) {
        return this.comparator.lessThan(first, second);
    }

    @Override
    public Boolean timeGreaterThan(OffsetTime first, OffsetTime second) {
        return this.comparator.greaterThan(first, second);
    }

    @Override
    public Boolean timeLessEqualThan(OffsetTime first, OffsetTime second) {
        return this.comparator.lessEqualThan(first, second);
    }

    @Override
    public Boolean timeGreaterEqualThan(OffsetTime first, OffsetTime second) {
        return this.comparator.greaterEqualThan(first, second);
    }

    @Override
    public Duration timeSubtract(OffsetTime first, OffsetTime second) {
        if (first == null || second == null) {
            return null;
        }

        try {
            return toDuration(first, second);
        } catch (Exception e) {
            String message = String.format("timeSubtract(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public OffsetTime timeAddDuration(OffsetTime time, Duration duration) {
        if (time == null || duration == null) {
            return null;
        }

        try {
            return time.plus(toTemporalDuration(duration));
        } catch (Exception e) {
            String message = String.format("timeAdd(%s, %s)", time, duration);
            logError(message, e);
            return null;
        }
    }

    @Override
    public OffsetTime timeSubtractDuration(OffsetTime time, Duration duration) {
        if (time == null || duration == null) {
            return null;
        }

        try {
            return time.minus(toTemporalDuration(duration));
        } catch (Exception e) {
            String message = String.format("timeSubtract(%s, %s)", time, duration);
            logError(message, e);
            return null;
        }
    }

    protected Duration toDuration(OffsetTime first, OffsetTime second) {
        ZonedDateTime first1 = first.atDate(EPOCH).atZoneSameInstant(DefaultDateTimeLib.UTC);
        ZonedDateTime second1 = second.atDate(EPOCH).atZoneSameInstant(DefaultDateTimeLib.UTC);
        long durationInMilliSeconds = getDurationInMilliSeconds(first1, second1);
        return datatypeFactory.newDuration(durationInMilliSeconds);
    }
}
