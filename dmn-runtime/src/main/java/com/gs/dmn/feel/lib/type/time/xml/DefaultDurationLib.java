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
package com.gs.dmn.feel.lib.type.time.xml;

import com.gs.dmn.feel.lib.type.time.DurationLib;
import org.apache.commons.lang3.StringUtils;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAmount;
import java.util.Arrays;
import java.util.List;

public class DefaultDurationLib implements DurationLib<XMLGregorianCalendar, javax.xml.datatype.Duration> {
    private final DatatypeFactory dataTypeFactory;

    public DefaultDurationLib(DatatypeFactory dataTypeFactory) {
        this.dataTypeFactory = dataTypeFactory;
    }

    @Override
    public javax.xml.datatype.Duration duration(String from) {
        if (StringUtils.isBlank(from)) {
            return null;
        }

        return this.dataTypeFactory.newDuration(from);
    }

    @Override
    public javax.xml.datatype.Duration yearsAndMonthsDuration(XMLGregorianCalendar from, XMLGregorianCalendar to) {
        if (from == null || to == null) {
            return null;
        }

        LocalDate toLocalDate = LocalDate.of(to.getYear(), to.getMonth(), to.getDay());
        LocalDate fromLocalDate = LocalDate.of(from.getYear(), from.getMonth(), from.getDay());
        return this.toYearsMonthDuration(this.dataTypeFactory, toLocalDate, fromLocalDate);
    }

    @Override
    public Long years(javax.xml.datatype.Duration duration) {
        if (duration == null) {
            return null;
        }

        if (hasYearsOrMonths(duration)) {
            return (long) duration.getYears();
        } else {
            return null;
        }
    }

    @Override
    public Long months(javax.xml.datatype.Duration duration) {
        if (duration == null) {
            return null;
        }

        if (hasYearsOrMonths(duration)) {
            return (long) duration.getMonths();
        } else {
            return null;
        }
    }

    @Override
    public Long days(javax.xml.datatype.Duration duration) {
        if (duration == null) {
            return null;
        }

        if (hasDayOrTime(duration)) {
            return (long) duration.getDays();
        } else {
            return null;
        }
    }

    @Override
    public Long hours(javax.xml.datatype.Duration duration) {
        if (duration == null) {
            return null;
        }

        if (hasDayOrTime(duration)) {
            return (long) duration.getHours();
        } else {
            return null;
        }
    }

    @Override
    public Long minutes(javax.xml.datatype.Duration duration) {
        if (duration == null) {
            return null;
        }

        if (hasDayOrTime(duration)) {
            return (long) duration.getMinutes();
        } else {
            return null;
        }
    }

    @Override
    public Long seconds(javax.xml.datatype.Duration duration) {
        if (duration == null) {
            return null;
        }

        if (hasDayOrTime(duration)) {
            return (long) duration.getSeconds();
        } else {
            return null;
        }
    }

    private boolean hasYearsOrMonths(javax.xml.datatype.Duration duration) {
        return duration.isSet(DatatypeConstants.YEARS)
                || duration.isSet(DatatypeConstants.MONTHS)
                ;
    }

    private boolean hasDayOrTime(javax.xml.datatype.Duration duration) {
        return duration.isSet(DatatypeConstants.DAYS)
                || duration.isSet(DatatypeConstants.HOURS)
                || duration.isSet(DatatypeConstants.MINUTES)
                || duration.isSet(DatatypeConstants.SECONDS)
                ;
    }

    private javax.xml.datatype.Duration toYearsMonthDuration(DatatypeFactory datatypeFactory, LocalDate date1, LocalDate date2) {
        Period between = Period.between(date2, date1);
        int years = between.getYears();
        int months = between.getMonths();
        if (between.isNegative()) {
            years = - years;
            months = - months;
        }
        return datatypeFactory.newDurationYearMonth(!between.isNegative(), years, months);
    }

    public static TemporalAmount temporalAmount(String literal) {
        if (literal == null) {
            throw new IllegalArgumentException("Duration literal cannot be null");
        }

        if (literal.indexOf("-") > 0) {
            throw new IllegalArgumentException("Negative values for units are not allowed.");
        }

        try {
            return Duration.parse(literal);
        } catch (DateTimeParseException e1) {
            try {
                return Period.parse(literal).normalized();
            } catch (DateTimeParseException e2) {
                throw new RuntimeException("Parsing exception in duration literal",
                        new RuntimeException(new Throwable() {
                            public final List<Throwable> causes = Arrays.asList(new Throwable[]{e1, e2});
                        }));
            }
        }
    }
}
