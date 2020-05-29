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

import org.apache.commons.lang3.StringUtils;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZonedDateTime;

public class DefaultDurationLib {
    private final DatatypeFactory dataTypeFactory;
    private final LocalDateLib dateLib = new LocalDateLib();

    public DefaultDurationLib(DatatypeFactory dataTypeFactory) {
        this.dataTypeFactory = dataTypeFactory;
    }

    public javax.xml.datatype.Duration duration(String from) {
        if (StringUtils.isBlank(from)) {
            return null;
        }

        return this.dataTypeFactory.newDuration(from);
    }

    public javax.xml.datatype.Duration yearsAndMonthsDuration(ZonedDateTime from, ZonedDateTime to) {
        if (from == null || to == null) {
            return null;
        }

        return this.toYearsMonthDuration(this.dataTypeFactory, toDate(to), toDate(from));
    }

    public javax.xml.datatype.Duration yearsAndMonthsDuration(LocalDate from, LocalDate to) {
        if (from == null || to == null) {
            return null;
        }

        return this.toYearsMonthDuration(this.dataTypeFactory, to, from);
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
            return this.dateLib.date((ZonedDateTime) object);
        }
        return (LocalDate) object;
    }

    public Integer years(Duration duration) {
        if (duration == null) {
            return null;
        }

        return duration.getYears();
    }

    public Integer months(Duration duration) {
        if (duration == null) {
            return null;
        }

        return duration.getMonths();
    }

    public Integer days(Duration duration) {
        if (duration == null) {
            return null;
        }

        return duration.getDays();
    }

    public Integer hours(Duration duration) {
        if (duration == null) {
            return null;
        }

        return duration.getHours();
    }

    public Integer minutes(Duration duration) {
        if (duration == null) {
            return null;
        }

        return duration.getMinutes();
    }

    public Integer seconds(Duration duration) {
        if (duration == null) {
            return null;
        }

        return duration.getSeconds();
    }
}
