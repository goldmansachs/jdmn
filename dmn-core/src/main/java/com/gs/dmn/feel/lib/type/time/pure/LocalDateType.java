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
package com.gs.dmn.feel.lib.type.time.pure;

import com.gs.dmn.feel.lib.type.BooleanType;
import com.gs.dmn.feel.lib.type.DateType;
import com.gs.dmn.feel.lib.type.logic.DefaultBooleanType;
import com.gs.dmn.feel.lib.type.time.JavaTimeType;
import org.slf4j.Logger;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.TemporalAmount;

public class LocalDateType extends JavaTimeType implements DateType<LocalDate, TemporalAmount> {
    private final BooleanType booleanType;

    public LocalDateType(Logger logger) {
        super(logger);
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
    public TemporalAmount dateSubtract(LocalDate first, LocalDate second) {
        if (first == null || second == null) {
            return null;
        }

        try {
            return Period.between(first, second);
        } catch (Throwable e) {
            String message = String.format("dateSubtract(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public LocalDate dateAddDuration(LocalDate date, TemporalAmount duration) {
        if (date == null || duration == null) {
            return null;
        }

        try {
            return date.plus(duration);
        } catch (Throwable e) {
            String message = String.format("dateAdd(%s, %s)", date, duration);
            logError(message, e);
            return null;
        }
    }

    @Override
    public LocalDate dateSubtractDuration(LocalDate date, TemporalAmount duration) {
        if (date == null || duration == null) {
            return null;
        }

        try {
            return date.minus(duration);
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
}
