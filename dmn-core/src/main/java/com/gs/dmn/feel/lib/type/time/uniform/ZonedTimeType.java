/**
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
package com.gs.dmn.feel.lib.type.time.uniform;

import com.gs.dmn.feel.lib.type.BooleanType;
import com.gs.dmn.feel.lib.type.TimeType;
import com.gs.dmn.feel.lib.type.logic.DefaultBooleanType;
import org.slf4j.Logger;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import java.time.ZonedDateTime;

public class ZonedTimeType extends JavaTimeCalendarType implements TimeType<ZonedDateTime, Duration> {
    private final BooleanType booleanType;

    public ZonedTimeType(Logger logger, DatatypeFactory datatypeFactory) {
        super(logger, datatypeFactory);
        this.booleanType = new DefaultBooleanType(logger);
    }

    //
    // Time operators
    //

    @Override
    public Boolean timeEqual(ZonedDateTime first, ZonedDateTime second) {
        return zonedDateTimeEqual(first, second);
    }

    @Override
    public Boolean timeNotEqual(ZonedDateTime first, ZonedDateTime second) {
        return booleanType.booleanNot(timeEqual(first, second));
    }

    @Override
    public Boolean timeLessThan(ZonedDateTime first, ZonedDateTime second) {
        return zonedDateTimeLessThan(first, second);
    }

    @Override
    public Boolean timeGreaterThan(ZonedDateTime first, ZonedDateTime second) {
        return zonedDateTimeGreaterThan(first, second);
    }

    @Override
    public Boolean timeLessEqualThan(ZonedDateTime first, ZonedDateTime second) {
        return zonedDateTimeLessEqualThan(first, second);
    }

    @Override
    public Boolean timeGreaterEqualThan(ZonedDateTime first, ZonedDateTime second) {
        return zonedDateTimeGreaterEqualThan(first, second);
    }

    @Override
    public Duration timeSubtract(ZonedDateTime first, ZonedDateTime second) {
        if (first == null || second == null) {
            return null;
        }

        try {
            java.time.Duration between = java.time.Duration.between(first, second);
            long durationInMilliSeconds = between.toMillis();
            if (!between.isNegative()) {
                durationInMilliSeconds = - durationInMilliSeconds;
            }
            return datatypeFactory.newDuration(durationInMilliSeconds);
        } catch (Throwable e) {
            String message = String.format("timeSubtract(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public ZonedDateTime timeAddDuration(ZonedDateTime time, Duration duration) {
        if (time == null || duration == null) {
            return null;
        }

        try {
            int hours = duration.getHours();
            int minutes = duration.getMinutes();
            int seconds = duration.getSeconds();
            if (duration.getSign() == -1) {
                return time
                        .minusHours(hours)
                        .minusMinutes(minutes)
                        .minusSeconds(seconds);
            } else {
                return time
                        .plusHours(hours)
                        .plusMinutes(minutes)
                        .plusSeconds(seconds);
            }
        } catch (Throwable e) {
            String message = String.format("timeAdd(%s, %s)", time, duration);
            logError(message, e);
            return null;
        }
    }

    @Override
    public ZonedDateTime timeSubtractDuration(ZonedDateTime time, Duration duration) {
        if (time == null || duration == null) {
            return null;
        }

        try {
            int hours = duration.getHours();
            int minutes = duration.getMinutes();
            int seconds = duration.getSeconds();
            if (duration.getSign() == -1) {
                return time
                        .plusHours(hours)
                        .plusMinutes(minutes)
                        .plusSeconds(seconds);
            } else {
                return time
                        .minusHours(hours)
                        .minusMinutes(minutes)
                        .minusSeconds(seconds);
            }
        } catch (Throwable e) {
            String message = String.format("timeSubtract(%s, %s)", time, duration);
            logError(message, e);
            return null;
        }
    }
}
