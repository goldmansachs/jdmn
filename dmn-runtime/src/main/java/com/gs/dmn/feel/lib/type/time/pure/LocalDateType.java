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
package com.gs.dmn.feel.lib.type.time.pure;

import com.gs.dmn.feel.lib.type.DateType;
import com.gs.dmn.feel.lib.type.time.JavaTimeType;
import com.gs.dmn.feel.lib.type.time.mixed.LocalDateComparator;
import org.slf4j.Logger;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.TemporalAmount;

public class LocalDateType extends JavaTimeType implements DateType<LocalDate, TemporalAmount> {
    private final LocalDateComparator comparator;

    @Deprecated
    public LocalDateType(Logger logger) {
        this(logger, new LocalDateComparator());
    }

    public LocalDateType(Logger logger, LocalDateComparator comparator) {
        super(logger);
        this.comparator = comparator;
    }

    //
    // Date operators
    //

    @Override
    public Boolean dateEqual(LocalDate first, LocalDate second) {
        return this.comparator.equalTo(first, second);
    }

    @Override
    public Boolean dateNotEqual(LocalDate first, LocalDate second) {
        return this.comparator.notEqualTo(first, second);
    }

    @Override
    public Boolean dateLessThan(LocalDate first, LocalDate second) {
        return this.comparator.lessThan(first, second);
    }

    @Override
    public Boolean dateGreaterThan(LocalDate first, LocalDate second) {
        return this.comparator.greaterThan(first, second);
    }

    @Override
    public Boolean dateLessEqualThan(LocalDate first, LocalDate second) {
        return this.comparator.lessEqualThan(first, second);
    }

    @Override
    public Boolean dateGreaterEqualThan(LocalDate first, LocalDate second) {
        return this.comparator.greaterEqualThan(first, second);
    }

    @Override
    public TemporalAmount dateSubtract(LocalDate first, LocalDate second) {
        if (first == null || second == null) {
            return null;
        }

        try {
            return Period.between(first, second);
        } catch (Exception e) {
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
        } catch (Exception e) {
            String message = String.format("dateAddDuration(%s, %s)", date, duration);
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
        } catch (Exception e) {
            String message = String.format("dateSubtractDuration(%s, %s)", date, duration);
            logError(message, e);
            return null;
        }
    }
}
