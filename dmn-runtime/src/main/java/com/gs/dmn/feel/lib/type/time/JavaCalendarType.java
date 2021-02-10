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
package com.gs.dmn.feel.lib.type.time;

import com.gs.dmn.feel.lib.type.BaseType;

import java.time.*;
import java.time.temporal.Temporal;

public class JavaCalendarType extends BaseType {
    public Long dateValue(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }

        return dateTimeValue(toDateTime(localDate));
    }

    public Long timeValue(Temporal time) {
        if (time == null) {
            return null;
        }

        if (time instanceof LocalTime) {
            return timeValue((LocalTime) time);
        } else if (time instanceof OffsetTime) {
            return timeValue((OffsetTime) time);
        } else {
            return null;
        }
    }

    public Long timeValue(LocalTime time) {
        if (time == null) {
            return null;
        }

        return (long) time.toSecondOfDay();
    }

    public Long timeValue(OffsetTime time) {
        if (time == null) {
            return null;
        }

        return (long) time.toLocalTime().toSecondOfDay() - time.getOffset().getTotalSeconds();
    }

    public Long dateTimeValue(Temporal dateTime) {
        if (dateTime == null) {
            return null;
        }

        if (dateTime instanceof LocalDateTime) {
            return dateTimeValue((LocalDateTime) dateTime);
        } else if (dateTime instanceof OffsetDateTime) {
            return dateTimeValue((OffsetDateTime) dateTime);
        } else if (dateTime instanceof ZonedDateTime) {
            return dateTimeValue((ZonedDateTime) dateTime);
        } else {
            return null;
        }
    }

    public Long dateTimeValue(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }

        return dateTime.toEpochSecond(ZoneOffset.of("Z"));
    }

    public Long dateTimeValue(OffsetDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }

        return dateTime.toEpochSecond();
    }

    public Long dateTimeValue(ZonedDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }

        return dateTime.toEpochSecond();
    }

    protected Long monthsValue(Period duration) {
        return duration.toTotalMonths();
    }

    protected Long secondsValue(Duration duration) {
        return duration.toMillis() / 1000;
    }

    public ZonedDateTime toDateTime(LocalDate localDate) {
        return localDate.atStartOfDay(UTC);
    }
}
