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

import com.gs.dmn.feel.lib.type.time.DurationLib;
import org.apache.commons.lang3.StringUtils;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZonedDateTime;

public class MixedDurationLib implements DurationLib<LocalDate, Duration> {
    private final DatatypeFactory dataTypeFactory;
    private final MixedDateTimeLib dateTimeLib;

    public MixedDurationLib(DatatypeFactory dataTypeFactory) {
        this.dataTypeFactory = dataTypeFactory;
        this.dateTimeLib = new MixedDateTimeLib(dataTypeFactory);
    }

    @Override
    public javax.xml.datatype.Duration duration(String from) {
        if (StringUtils.isBlank(from)) {
            return null;
        }

        return this.dataTypeFactory.newDuration(from);
    }

    @Override
    public javax.xml.datatype.Duration yearsAndMonthsDuration(LocalDate from, LocalDate to) {
        if (from == null || to == null) {
            return null;
        }

        return this.toYearsMonthDuration(this.dataTypeFactory, to, from);
    }
    public javax.xml.datatype.Duration yearsAndMonthsDuration(ZonedDateTime from, ZonedDateTime to) {
        if (from == null || to == null) {
            return null;
        }

        return this.toYearsMonthDuration(this.dataTypeFactory, toDate(to), toDate(from));
    }
    public javax.xml.datatype.Duration yearsAndMonthsDuration(ZonedDateTime from, LocalDate to) {
        if (from == null || to == null) {
            return null;
        }

        return this.toYearsMonthDuration(this.dataTypeFactory, to, toDate(from));
    }
    public Duration yearsAndMonthsDuration(LocalDate from, ZonedDateTime to) {
        if (from == null || to == null) {
            return null;
        }

        return this.toYearsMonthDuration(this.dataTypeFactory, toDate(to), from);
    }

    private Duration toYearsMonthDuration(DatatypeFactory datatypeFactory, LocalDate date1, LocalDate date2) {
        Period between = Period.between(date2, date1);
        int years = between.getYears();
        int months = between.getMonths();
        if (between.isNegative()) {
            years = - years;
            months = - months;
        }
        return datatypeFactory.newDurationYearMonth(!between.isNegative(), years, months);
    }

    private LocalDate toDate(Object object) {
        if (object instanceof ZonedDateTime) {
            return this.dateTimeLib.date((ZonedDateTime) object);
        }
        return (LocalDate) object;
    }

    @Override
    public Long years(Duration duration) {
        if (duration == null) {
            return null;
        }

        return (long) duration.getYears();
    }

    @Override
    public Long months(Duration duration) {
        if (duration == null) {
            return null;
        }

        return (long) duration.getMonths();
    }

    @Override
    public Long days(Duration duration) {
        if (duration == null) {
            return null;
        }

        return (long) duration.getDays();
    }

    @Override
    public Long hours(Duration duration) {
        if (duration == null) {
            return null;
        }

        return (long) duration.getHours();
    }

    @Override
    public Long minutes(Duration duration) {
        if (duration == null) {
            return null;
        }

        return (long) duration.getMinutes();
    }

    @Override
    public Long seconds(Duration duration) {
        if (duration == null) {
            return null;
        }

        return (long) duration.getSeconds();
    }
}
