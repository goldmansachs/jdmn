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

import com.gs.dmn.feel.lib.type.time.JavaCalendarType;

import java.time.*;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQueries;

public abstract class BasePureCalendarType extends JavaCalendarType {
    @Override
    public boolean isDate(Object object) {
        return object instanceof LocalDate;
    }

    @Override
    public boolean isTime(Object object) {
        return object instanceof LocalTime
                || object instanceof OffsetTime
                || isTimeWithZone(object);
    }

    @Override
    public boolean isDateTime(Object object) {
        return object instanceof LocalDateTime || object instanceof OffsetDateTime || object instanceof ZonedDateTime;
    }

    private boolean isTimeWithZone(Object obj) {
        if (obj instanceof TemporalAccessor) {
            TemporalAccessor value = (TemporalAccessor) obj;
            return value.isSupported(ChronoField.HOUR_OF_DAY)
                    && value.isSupported(ChronoField.MINUTE_OF_HOUR)
                    && value.isSupported(ChronoField.SECOND_OF_MINUTE)
                    && value.query(TemporalQueries.zone()) != null;

        } else {
            return false;
        }
    }

    protected boolean hasTimezone(TemporalAccessor first) {
        return first.query(TemporalQueries.zone()) != null;
    }
}
