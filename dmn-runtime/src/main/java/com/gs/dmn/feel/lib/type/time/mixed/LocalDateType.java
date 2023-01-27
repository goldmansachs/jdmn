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

import com.gs.dmn.feel.lib.type.time.DateType;
import com.gs.dmn.feel.lib.type.time.xml.FEELXMLGregorianCalendar;
import com.gs.dmn.feel.lib.type.time.xml.XMLCalendarType;
import com.gs.dmn.feel.lib.type.time.xml.XMLDurationFactory;
import com.gs.dmn.runtime.DMNRuntimeException;

import javax.xml.datatype.Duration;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;

public class LocalDateType extends BaseMixedCalendarType implements DateType<LocalDate, Duration> {
    protected final LocalDateComparator comparator;

    public LocalDateType() {
        this(new LocalDateComparator());
    }

    public LocalDateType(LocalDateComparator comparator) {
        this.comparator = comparator;
    }

    //
    // Date operators
    //
    @Override
    public boolean isDate(Object value) {
        return value instanceof LocalDate;
    }

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
    public Duration dateSubtract(LocalDate first, Object second) {
        if (first == null || second == null) {
            return null;
        }

        if (second instanceof Temporal) {
            long durationInSeconds = dateValue(first) - (long) value(second);
            return XMLDurationFactory.INSTANCE.dayTimeFromValue(durationInSeconds);
        } else {
            throw new DMNRuntimeException(String.format("Cannot subtract '%s' and '%s'", first, second));
        }
    }

    @Override
    public LocalDate dateAddDuration(LocalDate date, Duration duration) {
        if (date == null || duration == null) {
            return null;
        }

        TemporalAmount yearMonth = toTemporalPeriod(duration);
        TemporalAmount dayTime = toTemporalDuration(duration);
        return toDateTime(date).plus(yearMonth).plus(dayTime).toLocalDate();
    }

    @Override
    public LocalDate dateSubtractDuration(LocalDate date, Duration duration) {
        if (date == null || duration == null) {
            return null;
        }

        return dateAddDuration(date, duration.negate());
    }
}
