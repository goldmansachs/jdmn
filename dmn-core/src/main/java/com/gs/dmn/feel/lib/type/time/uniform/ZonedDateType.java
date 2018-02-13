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

import com.gs.dmn.feel.lib.DateTimeUtil;
import com.gs.dmn.feel.lib.type.BooleanType;
import com.gs.dmn.feel.lib.type.DateType;
import com.gs.dmn.feel.lib.type.logic.DefaultBooleanType;
import org.slf4j.Logger;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import java.time.LocalDate;
import java.time.ZonedDateTime;

public class ZonedDateType extends JavaTimeCalendarType implements DateType<ZonedDateTime, Duration> {
    private final BooleanType booleanType;

    public ZonedDateType(Logger logger, DatatypeFactory datatypeFactory) {
        super(logger, datatypeFactory);
        this.booleanType = new DefaultBooleanType(logger);
    }

    //
    // Date operators
    //

    @Override
    public Boolean dateEqual(ZonedDateTime first, ZonedDateTime second) {
        return zonedDateTimeEqual(first, second);
    }

    @Override
    public Boolean dateNotEqual(ZonedDateTime first, ZonedDateTime second) {
        return booleanType.booleanNot(dateEqual(first, second));
    }

    @Override
    public Boolean dateLessThan(ZonedDateTime first, ZonedDateTime second) {
        return zonedDateTimeLessThan(first, second);
    }

    @Override
    public Boolean dateGreaterThan(ZonedDateTime first, ZonedDateTime second) {
        return zonedDateTimeGreaterThan(first, second);
    }

    @Override
    public Boolean dateLessEqualThan(ZonedDateTime first, ZonedDateTime second) {
        return zonedDateTimeLessEqualThan(first, second);
    }

    @Override
    public Boolean dateGreaterEqualThan(ZonedDateTime first, ZonedDateTime second) {
        return zonedDateTimeGreaterEqualThan(first, second);
    }

    @Override
    public Duration dateSubtract(ZonedDateTime first, ZonedDateTime second) {
        if (first == null || second == null) {
            return null;
        }

        try {
            long durationInMilliSeconds = getDurationInMilliSeconds(first, second);
            return datatypeFactory.newDuration(durationInMilliSeconds);
        } catch (Throwable e) {
            String message = String.format("dateSubtract(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public ZonedDateTime dateAddDuration(ZonedDateTime date, Duration duration) {
        if (date == null || duration == null) {
            return null;
        }

        try {
            int years = duration.getYears();
            int months = duration.getMonths();
            int days = duration.getDays();
            if (duration.getSign() == -1) {
                return date
                        .minusYears(years)
                        .minusMonths(months)
                        .minusDays(days);
            } else {
                return date
                        .plusYears(years)
                        .plusMonths(months)
                        .plusDays(days);
            }
        } catch (Throwable e) {
            String message = String.format("dateAdd(%s, %s)", date, duration);
            logError(message, e);
            return null;
        }
    }

    @Override
    public ZonedDateTime dateSubtractDuration(ZonedDateTime date, Duration duration) {
        if (date == null || duration == null) {
            return null;
        }

        try {
            int years = duration.getYears();
            int months = duration.getMonths();
            int days = duration.getDays();
            if (duration.getSign() == -1) {
                return date
                        .plusYears(years)
                        .plusMonths(months)
                        .plusDays(days);
            } else {
                return date
                        .minusYears(years)
                        .minusMonths(months)
                        .minusDays(days);
            }
        } catch (Throwable e) {
            String message = String.format("dateSubtract(%s, %s)", date, duration);
            logError(message, e);
            return null;
        }
    }

    public Duration toDuration(ZonedDateTime first, ZonedDateTime second) {
        LocalDate firstLocalDate = first.toLocalDate();
        LocalDate secondLocalDate = second.toLocalDate();
        long durationInMilliSeconds = getDurationInMilliSeconds(firstLocalDate.atStartOfDay(DateTimeUtil.UTC), secondLocalDate.atStartOfDay(DateTimeUtil.UTC));
        return datatypeFactory.newDurationYearMonth(durationInMilliSeconds);
    }
}
