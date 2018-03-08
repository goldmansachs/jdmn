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
package com.gs.dmn.feel.lib.type.time.mixed;

import com.gs.dmn.feel.lib.DateTimeUtil;
import com.gs.dmn.feel.lib.type.BooleanType;
import com.gs.dmn.feel.lib.type.DateTimeType;
import com.gs.dmn.feel.lib.type.logic.DefaultBooleanType;
import org.slf4j.Logger;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import java.time.ZonedDateTime;

public class ZonedDateTimeType extends JavaTimeCalendarType implements DateTimeType<ZonedDateTime, Duration> {
    private final BooleanType booleanType;

    public ZonedDateTimeType(Logger logger, DatatypeFactory datatypeFactory) {
        super(logger, datatypeFactory);
        this.booleanType = new DefaultBooleanType(logger);
    }

    //
    // Date and time operators
    //

    @Override
    public Boolean dateTimeEqual(ZonedDateTime first, ZonedDateTime second) {
        return zonedDateTimeEqual(first, second);
    }

    @Override
    public Boolean dateTimeNotEqual(ZonedDateTime first, ZonedDateTime second) {
        return booleanType.booleanNot(dateTimeEqual(first, second));
    }

    @Override
    public Boolean dateTimeLessThan(ZonedDateTime first, ZonedDateTime second) {
        return zonedDateTimeLessThan(first, second);
    }

    @Override
    public Boolean dateTimeGreaterThan(ZonedDateTime first, ZonedDateTime second) {
        return zonedDateTimeGreaterThan(first, second);
    }

    @Override
    public Boolean dateTimeLessEqualThan(ZonedDateTime first, ZonedDateTime second) {
        return zonedDateTimeLessEqualThan(first, second);
    }

    @Override
    public Boolean dateTimeGreaterEqualThan(ZonedDateTime first, ZonedDateTime second) {
        return zonedDateTimeGreaterEqualThan(first, second);
    }

    @Override
    public Duration dateTimeSubtract(ZonedDateTime first, ZonedDateTime second) {
        if (first == null || second == null) {
            return null;
        }

        try {
            return toDuration(first, second);
        } catch (Throwable e) {
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
        } catch (Throwable e) {
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
        } catch (Throwable e) {
            String message = String.format("dateTimeSubtract(%s, %s)", dateTime, duration);
            logError(message, e);
            return null;
        }
    }

    protected Boolean zonedDateTimeEqual(ZonedDateTime first, ZonedDateTime second) {
        if (first == null && second == null) {
            return true;
        } else if (first == null) {
            return false;
        } else if (second == null) {
            return false;
        } else {
            int result = compare(first, second);
            return result == 0;
        }
    }

    protected Boolean zonedDateTimeLessThan(ZonedDateTime first, ZonedDateTime second) {
        if (first == null && second == null) {
            return false;
        } else if (first == null) {
            return null;
        } else if (second == null) {
            return null;
        } else {
            int result = compare(first, second);
            return result < 0;
        }
    }

    protected Boolean zonedDateTimeGreaterThan(ZonedDateTime first, ZonedDateTime second) {
        if (first == null && second == null) {
            return false;
        } else if (first == null) {
            return null;
        } else if (second == null) {
            return null;
        } else {
            int result = compare(first, second);
            return result > 0;
        }
    }

    protected Boolean zonedDateTimeLessEqualThan(ZonedDateTime first, ZonedDateTime second) {
        if (first == null && second == null) {
            return true;
        } else if (first == null) {
            return null;
        } else if (second == null) {
            return null;
        } else {
            int result = compare(first, second);
            return result <= 0;
        }
    }

    protected Boolean zonedDateTimeGreaterEqualThan(ZonedDateTime first, ZonedDateTime second) {
        if (first == null && second == null) {
            return true;
        } else if (first == null) {
            return null;
        } else if (second == null) {
            return null;
        } else {
            int result = compare(first, second);
            return result >= 0;
        }
    }

    private int compare(ZonedDateTime first, ZonedDateTime second) {
        return first.withZoneSameInstant(DateTimeUtil.UTC).compareTo(second.withZoneSameInstant(DateTimeUtil.UTC));
    }

    private Duration toDuration(ZonedDateTime first, ZonedDateTime second) {
        long durationInMilliSeconds = getDurationInMilliSeconds(first, second);
        return datatypeFactory.newDuration(durationInMilliSeconds);
    }
}
