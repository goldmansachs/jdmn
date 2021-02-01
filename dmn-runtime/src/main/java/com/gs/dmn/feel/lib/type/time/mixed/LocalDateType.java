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

import com.gs.dmn.feel.lib.type.DateType;
import com.gs.dmn.feel.lib.type.time.xml.DefaultDateTimeLib;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import java.time.LocalDate;

public class LocalDateType extends JavaTimeCalendarType implements DateType<LocalDate, Duration> {
    protected final LocalDateComparator comparator;

    @Deprecated
    public LocalDateType(DatatypeFactory datatypeFactory) {
        this(datatypeFactory, new LocalDateComparator());
    }

    public LocalDateType(DatatypeFactory datatypeFactory, LocalDateComparator comparator) {
        super(datatypeFactory);
        this.comparator = comparator;
    }

    //
    // Date operators
    //

    @Override
    public Boolean dateIs(LocalDate first, LocalDate second) {
        if (first == null || second == null) {
            return first == second;
        }

        return first.getYear() == second.getYear()
                && first.getMonth() == second.getMonth()
                && first.getDayOfMonth() == second.getDayOfMonth();
    }

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
    public Duration dateSubtract(LocalDate first, LocalDate second) {
        if (first == null || second == null) {
            return null;
        }

        return toDuration(first, second);
    }

    @Override
    public LocalDate dateAddDuration(LocalDate date, Duration duration) {
        if (date == null || duration == null) {
            return null;
        }

        return date.plus(toTemporalPeriod(duration));
    }

    @Override
    public LocalDate dateSubtractDuration(LocalDate date, Duration duration) {
        if (date == null || duration == null) {
            return null;
        }

        return date.minus(toTemporalPeriod(duration));
    }

    protected Duration toDuration(LocalDate date1, LocalDate date2) {
        long durationInMilliSeconds = getDurationInMilliSeconds(date1.atStartOfDay(DefaultDateTimeLib.UTC), date2.atStartOfDay(DefaultDateTimeLib.UTC));
        return datatypeFactory.newDurationYearMonth(durationInMilliSeconds);
    }
}
