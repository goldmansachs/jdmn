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
package com.gs.dmn.feel.lib.type.time.uniform;

import com.gs.dmn.feel.lib.type.time.BaseDateTimeLib;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static com.gs.dmn.feel.lib.type.time.xml.DefaultDateTimeLib.UTC;

public class ZonedDateLib extends BaseDateTimeLib {
    public ZonedDateTime date(String literal) {
        if (literal == null) {
            return null;
        }

        if (this.hasTime(literal) || this.hasZone(literal)) {
            return null;
        } else {
            return this.makeLocalDate(literal).atStartOfDay(UTC);
        }
    }

    public ZonedDateTime date(BigDecimal year, BigDecimal month, BigDecimal day) {
        if (year == null || month == null || day == null) {
            return null;
        }

        return ZonedDateTime.of(year.intValue(), month.intValue(), day.intValue(), 0, 0, 0, 0, UTC);
    }

    public ZonedDateTime date(ZonedDateTime from) {
        if (!isDate(from)) {
            return null;
        }
        return from.toLocalDate().atStartOfDay(UTC);
    }

    public ZonedDateTime toDate(Object object) {
        if (!(object instanceof ZonedDateTime)) {
            return null;
        }
        return date((ZonedDateTime) object);
    }

    public BigDecimal year(ZonedDateTime date) {
        return BigDecimal.valueOf(date.getYear());
    }

    public BigDecimal month(ZonedDateTime date) {
        return BigDecimal.valueOf(date.getMonth().getValue());
    }

    public BigDecimal day(ZonedDateTime date) {
        return BigDecimal.valueOf(date.getDayOfMonth());
    }

    public BigDecimal weekday(ZonedDateTime date) {
        return BigDecimal.valueOf(date.getDayOfWeek().getValue());
    }

    private boolean isDate(ZonedDateTime from) {
        return from != null && from.getYear() >=0;
    }
}
