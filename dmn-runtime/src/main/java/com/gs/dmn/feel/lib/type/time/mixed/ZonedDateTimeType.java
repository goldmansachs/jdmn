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

import com.gs.dmn.feel.lib.type.DateTimeType;
import org.slf4j.Logger;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import java.time.ZonedDateTime;

public class ZonedDateTimeType extends JavaTimeCalendarType implements DateTimeType<ZonedDateTime, Duration> {
    private final ZonedDateTimeComparator comparator;

    @Deprecated
    public ZonedDateTimeType(Logger logger, DatatypeFactory datatypeFactory) {
        this(logger, datatypeFactory, new ZonedDateTimeComparator());
    }

    public ZonedDateTimeType(Logger logger, DatatypeFactory datatypeFactory, ZonedDateTimeComparator comparator) {
        super(logger, datatypeFactory);
        this.comparator = comparator;
    }

    //
    // Date and time operators
    //

    @Override
    public Boolean dateTimeEqual(ZonedDateTime first, ZonedDateTime second) {
        return this.comparator.equalTo(first, second);
    }

    @Override
    public Boolean dateTimeNotEqual(ZonedDateTime first, ZonedDateTime second) {
        return this.comparator.notEqualTo(first, second);
    }

    @Override
    public Boolean dateTimeLessThan(ZonedDateTime first, ZonedDateTime second) {
        return this.comparator.lessThan(first, second);
    }

    @Override
    public Boolean dateTimeGreaterThan(ZonedDateTime first, ZonedDateTime second) {
        return this.comparator.greaterThan(first, second);
    }

    @Override
    public Boolean dateTimeLessEqualThan(ZonedDateTime first, ZonedDateTime second) {
        return this.comparator.lessEqualThan(first, second);
    }

    @Override
    public Boolean dateTimeGreaterEqualThan(ZonedDateTime first, ZonedDateTime second) {
        return this.comparator.greaterEqualThan(first, second);
    }

    @Override
    public Duration dateTimeSubtract(ZonedDateTime first, ZonedDateTime second) {
        if (first == null || second == null) {
            return null;
        }

        try {
            return toDuration(first, second);
        } catch (Exception e) {
            String message = String.format("dateTimeSubtract(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public ZonedDateTime dateTimeAddDuration(ZonedDateTime dateTime, Duration duration) {
        if (dateTime == null || duration == null) {
            return null;
        }

        try {
            return dateTime
                    .plus(toTemporalPeriod(duration))
                    .plus(toTemporalDuration(duration))
                    ;
        } catch (Exception e) {
            String message = String.format("dateTimeSubtract(%s, %s)", dateTime, duration);
            logError(message, e);
            return null;
        }
    }

    @Override
    public ZonedDateTime dateTimeSubtractDuration(ZonedDateTime dateTime, Duration duration) {
        if (dateTime == null || duration == null) {
            return null;
        }

        try {
            return dateTime
                    .minus(toTemporalPeriod(duration))
                    .minus(toTemporalDuration(duration))
                    ;
        } catch (Exception e) {
            String message = String.format("dateTimeSubtract(%s, %s)", dateTime, duration);
            logError(message, e);
            return null;
        }
    }

    protected Duration toDuration(ZonedDateTime first, ZonedDateTime second) {
        long durationInMilliSeconds = getDurationInMilliSeconds(first, second);
        return datatypeFactory.newDuration(durationInMilliSeconds);
    }
}
