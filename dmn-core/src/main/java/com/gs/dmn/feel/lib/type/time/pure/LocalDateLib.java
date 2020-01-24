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

import com.gs.dmn.feel.lib.type.time.BaseDateTimeLib;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;

public class LocalDateLib extends BaseDateTimeLib {
    public LocalDate date(String literal) {
        if (StringUtils.isBlank(literal)) {
            return null;
        }

        if (this.hasTime(literal) || this.hasZone(literal)) {
            return null;
        } else {
            return this.makeLocalDate(literal);
        }
    }

    public LocalDate date(Number year, Number month, Number day) {
        if (year == null || month == null || day == null) {
            return null;
        }

        return LocalDate.of(year.intValue(), month.intValue(), day.intValue());
    }

    public LocalDate date(Temporal from) {
        if (from == null) {
            return null;
        }

        if (from instanceof LocalDate) {
            return (LocalDate) from;
        } else if (from instanceof LocalDateTime) {
            return ((LocalDateTime) from).toLocalDate();
        } else if (from instanceof OffsetDateTime) {
            return ((OffsetDateTime) from).toLocalDate();
        } else if (from instanceof ZonedDateTime) {
            return ((ZonedDateTime) from).toLocalDate();
        }
        throw new IllegalArgumentException(String.format("Cannot convert '%s' to date", from.getClass().getSimpleName()));
    }

    public LocalDate toDate(Object object) {
        if (object instanceof Temporal) {
            return date((Temporal) object);
        }
        return null;
    }

    public Integer year(LocalDate date) {
        if (date == null) {
            return null;
        }

        return date.getYear();
    }
    public Integer year(Temporal dateTime) {
        if (dateTime == null) {
            return null;
        }

        return dateTime.get(ChronoField.YEAR);
    }

    public Integer month(LocalDate date) {
        if (date == null) {
            return null;
        }

        return date.getMonth().getValue();
    }
    public Integer month(Temporal dateTime) {
        if (dateTime == null) {
            return null;
        }

        return dateTime.get(ChronoField.MONTH_OF_YEAR);
    }

    public Integer day(LocalDate date) {
        if (date == null) {
            return null;
        }

        return date.getDayOfMonth();
    }
    public Integer day(Temporal dateTime) {
        if (dateTime == null) {
            return null;
        }

        return dateTime.get(ChronoField.DAY_OF_MONTH);
    }
    public Integer weekday(LocalDate date) {
        if (date == null) {
            return null;
        }

        return date.getDayOfWeek().getValue();
    }
    public Integer weekday(Temporal dateTime) {
        if (dateTime == null) {
            return null;
        }

        return dateTime.get(ChronoField.DAY_OF_WEEK);
    }
}
