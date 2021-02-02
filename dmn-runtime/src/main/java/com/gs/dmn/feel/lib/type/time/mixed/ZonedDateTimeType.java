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

import com.gs.dmn.feel.lib.type.time.DateTimeType;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import java.time.ZonedDateTime;

public class ZonedDateTimeType extends JavaTimeCalendarType implements DateTimeType<ZonedDateTime, Duration> {
    private final ZonedDateTimeComparator comparator;

    @Deprecated
    public ZonedDateTimeType(DatatypeFactory datatypeFactory) {
        this(datatypeFactory, new ZonedDateTimeComparator());
    }

    public ZonedDateTimeType(DatatypeFactory datatypeFactory, ZonedDateTimeComparator comparator) {
        super(datatypeFactory);
        this.comparator = comparator;
    }

    //
    // Date and time operators
    //
    @Override
    public boolean isDateTime(Object value) {
        return value instanceof ZonedDateTime;
    }

    @Override
    public Boolean dateTimeIs(ZonedDateTime first, ZonedDateTime second) {
        if (first == null || second == null) {
            return first == second;
        }

        return first.getYear() == second.getYear()
                && first.getMonth() == second.getMonth()
                && first.getDayOfMonth() == second.getDayOfMonth()

                && first.getHour() == second.getHour()
                && first.getMinute() == second.getMinute()
                && first.getSecond() == second.getSecond()
                && first.getOffset() == second.getOffset();
    }

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

        return toDuration(first, second);
    }

    @Override
    public ZonedDateTime dateTimeAddDuration(ZonedDateTime dateTime, Duration duration) {
        if (dateTime == null || duration == null) {
            return null;
        }

        return dateTime
                .plus(toTemporalPeriod(duration))
                .plus(toTemporalDuration(duration));
    }

    @Override
    public ZonedDateTime dateTimeSubtractDuration(ZonedDateTime dateTime, Duration duration) {
        if (dateTime == null || duration == null) {
            return null;
        }

        return dateTime
                .minus(toTemporalPeriod(duration))
                .minus(toTemporalDuration(duration))
                ;
    }

    protected Duration toDuration(ZonedDateTime first, ZonedDateTime second) {
        long durationInMilliSeconds = getDurationInMilliSeconds(first, second);
        return datatypeFactory.newDuration(durationInMilliSeconds);
    }
}
