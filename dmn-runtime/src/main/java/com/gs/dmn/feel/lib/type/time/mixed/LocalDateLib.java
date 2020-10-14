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

import com.gs.dmn.feel.lib.type.time.BaseDateTimeLib;
import com.gs.dmn.feel.lib.type.time.DateLib;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.ZonedDateTime;

public class LocalDateLib extends BaseDateTimeLib implements DateLib<Number, LocalDate> {
    @Override
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

    @Override
    public LocalDate date(Number year, Number month, Number day) {
        if (year == null || month == null || day == null) {
            return null;
        }

        return LocalDate.of(year.intValue(), month.intValue(), day.intValue());
    }

    public LocalDate date(ZonedDateTime from) {
        if (from == null) {
            return null;
        }

        return from.toLocalDate();
    }
    @Override
    public LocalDate date(LocalDate from) {
        return from;
    }

    public LocalDate toDate(Object object) {
        if (object instanceof ZonedDateTime) {
            return date((ZonedDateTime) object);
        }
        if (object instanceof LocalDate) {
            return date((LocalDate) object);
        }
        return null;
    }

    @Override
    public Integer year(LocalDate date) {
        if (date == null) {
            return null;
        }

        return date.getYear();
    }
    public Integer year(ZonedDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }

        return dateTime.getYear();
    }

    @Override
    public Integer month(LocalDate date) {
        if (date == null) {
            return null;
        }

        return date.getMonth().getValue();
    }
    public Integer month(ZonedDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }

        return dateTime.getMonth().getValue();
    }

    @Override
    public Integer day(LocalDate date) {
        if (date == null) {
            return null;
        }

        return date.getDayOfMonth();
    }
    public Integer day(ZonedDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }

        return dateTime.getDayOfMonth();
    }
    @Override
    public Integer weekday(LocalDate date) {
        if (date == null) {
            return null;
        }

        return date.getDayOfWeek().getValue();
    }
    public Integer weekday(ZonedDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }

        return dateTime.getDayOfWeek().getValue();
    }
}
