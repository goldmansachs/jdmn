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
import com.gs.dmn.feel.lib.type.DateType;
import com.gs.dmn.feel.lib.type.logic.DefaultBooleanType;
import org.slf4j.Logger;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import java.time.LocalDate;

public class LocalDateType extends JavaTimeCalendarType implements DateType<LocalDate, Duration> {
    private final BooleanType booleanType;

    public LocalDateType(Logger logger, DatatypeFactory datatypeFactory) {
        super(logger, datatypeFactory);
        this.booleanType = new DefaultBooleanType(logger);
    }

    //
    // Date operators
    //

    @Override
    public Boolean dateEqual(LocalDate first, LocalDate second) {
        return localDateEqual(first, second);
    }

    @Override
    public Boolean dateNotEqual(LocalDate first, LocalDate second) {
        return booleanType.booleanNot(dateEqual(first, second));
    }

    @Override
    public Boolean dateLessThan(LocalDate first, LocalDate second) {
        return localDateLessThan(first, second);
    }

    @Override
    public Boolean dateGreaterThan(LocalDate first, LocalDate second) {
        return localDateGreaterThan(first, second);
    }

    @Override
    public Boolean dateLessEqualThan(LocalDate first, LocalDate second) {
        return localDateLessEqualThan(first, second);
    }

    @Override
    public Boolean dateGreaterEqualThan(LocalDate first, LocalDate second) {
        return localDateGreaterEqualThan(first, second);
    }

    @Override
    public Duration dateSubtract(LocalDate first, LocalDate second) {
        if (first == null || second == null) {
            return null;
        }

        try {
            return toDuration(first, second);
        } catch (Throwable e) {
            String message = String.format("dateSubtract(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public LocalDate dateAddDuration(LocalDate date, Duration duration) {
        if (date == null || duration == null) {
            return null;
        }

        try {
            return date.plus(toTemporalPeriod(duration));
        } catch (Throwable e) {
            String message = String.format("dateAdd(%s, %s)", date, duration);
            logError(message, e);
            return null;
        }
    }

    @Override
    public LocalDate dateSubtractDuration(LocalDate date, Duration duration) {
        if (date == null || duration == null) {
            return null;
        }

        try {
            return date.minus(toTemporalPeriod(duration));
        } catch (Throwable e) {
            String message = String.format("dateSubtract(%s, %s)", date, duration);
            logError(message, e);
            return null;
        }
    }

    protected Boolean localDateEqual(LocalDate first, LocalDate second) {
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

    protected Boolean localDateLessThan(LocalDate first, LocalDate second) {
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

    protected Boolean localDateGreaterThan(LocalDate first, LocalDate second) {
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

    protected Boolean localDateLessEqualThan(LocalDate first, LocalDate second) {
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

    protected Boolean localDateGreaterEqualThan(LocalDate first, LocalDate second) {
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

    private int compare(LocalDate first, LocalDate second) {
        return first.compareTo(second);
    }

    public Duration toDuration(LocalDate date1, LocalDate date2) {
        long durationInMilliSeconds = getDurationInMilliSeconds(date1.atStartOfDay(DateTimeUtil.UTC), date2.atStartOfDay(DateTimeUtil.UTC));
        return datatypeFactory.newDurationYearMonth(durationInMilliSeconds);
    }
}
