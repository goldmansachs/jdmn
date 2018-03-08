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
import com.gs.dmn.feel.lib.type.TimeType;
import com.gs.dmn.feel.lib.type.logic.DefaultBooleanType;
import org.slf4j.Logger;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import java.time.OffsetTime;
import java.time.ZonedDateTime;

public class OffsetTimeType extends JavaTimeCalendarType implements TimeType<OffsetTime, Duration> {
    private final BooleanType booleanType;

    public OffsetTimeType(Logger logger, DatatypeFactory datatypeFactory) {
        super(logger, datatypeFactory);
        this.booleanType = new DefaultBooleanType(logger);
    }

    //
    // Time operators
    //

    @Override
    public Boolean timeEqual(OffsetTime first, OffsetTime second) {
        return offsetTimeEqual(first, second);
    }

    @Override
    public Boolean timeNotEqual(OffsetTime first, OffsetTime second) {
        return booleanType.booleanNot(timeEqual(first, second));
    }

    @Override
    public Boolean timeLessThan(OffsetTime first, OffsetTime second) {
        return offsetTimeLessThan(first, second);
    }

    @Override
    public Boolean timeGreaterThan(OffsetTime first, OffsetTime second) {
        return offsetTimeGreaterThan(first, second);
    }

    @Override
    public Boolean timeLessEqualThan(OffsetTime first, OffsetTime second) {
        return offsetTimeLessEqualThan(first, second);
    }

    @Override
    public Boolean timeGreaterEqualThan(OffsetTime first, OffsetTime second) {
        return offsetTimeGreaterEqualThan(first, second);
    }

    @Override
    public Duration timeSubtract(OffsetTime first, OffsetTime second) {
        if (first == null || second == null) {
            return null;
        }

        try {
            return toDuration(first, second);
        } catch (Throwable e) {
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
        } catch (Throwable e) {
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
        } catch (Throwable e) {
            String message = String.format("timeSubtract(%s, %s)", time, duration);
            logError(message, e);
            return null;
        }
    }

    protected Boolean offsetTimeEqual(OffsetTime first, OffsetTime second) {
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

    protected Boolean offsetTimeLessThan(OffsetTime first, OffsetTime second) {
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

    protected Boolean offsetTimeGreaterThan(OffsetTime first, OffsetTime second) {
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

    protected Boolean offsetTimeLessEqualThan(OffsetTime first, OffsetTime second) {
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

    protected Boolean offsetTimeGreaterEqualThan(OffsetTime first, OffsetTime second) {
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

    private int compare(OffsetTime first, OffsetTime second) {
        return first.compareTo(second);
    }

    private Duration toDuration(OffsetTime first, OffsetTime second) {
        ZonedDateTime first1 = first.atDate(DateTimeUtil.EPOCH).atZoneSameInstant(DateTimeUtil.UTC);
        ZonedDateTime second1 = second.atDate(DateTimeUtil.EPOCH).atZoneSameInstant(DateTimeUtil.UTC);
        long durationInMilliSeconds = getDurationInMilliSeconds(first1, second1);
        return datatypeFactory.newDuration(durationInMilliSeconds);
    }
}
