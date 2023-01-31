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
import com.gs.dmn.feel.lib.type.time.xml.XMLDurationFactory;
import com.gs.dmn.runtime.DMNRuntimeException;

import javax.xml.datatype.Duration;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;

public class ZonedDateTimeType extends BaseMixedCalendarType implements DateTimeType<ZonedDateTime, Duration> {
    private final ZonedDateTimeComparator comparator;

    public ZonedDateTimeType() {
        this(new ZonedDateTimeComparator());
    }

    public ZonedDateTimeType(ZonedDateTimeComparator comparator) {
        this.comparator = comparator;
    }

    @Override
    public Long dateTimeValue(ZonedDateTime dateTime) {
        return super.dateTimeValue(dateTime);
    }

    //
    // Date and time operators
    //
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
    public Duration dateTimeSubtract(ZonedDateTime first, Object second) {
        if (first == null || second == null) {
            return null;
        }

        if (second instanceof Temporal) {
            long durationInSeconds = dateTimeValue(first) - (long) value(second);
            return XMLDurationFactory.INSTANCE.dayTimeFromValue(durationInSeconds);
        }
        throw new DMNRuntimeException(String.format("Cannot subtract '%s' and '%s'", first, second));
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
}
