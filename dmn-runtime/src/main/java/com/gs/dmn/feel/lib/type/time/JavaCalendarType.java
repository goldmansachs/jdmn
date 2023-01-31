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
import com.gs.dmn.runtime.DMNRuntimeException;

import java.time.*;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;

public abstract class JavaCalendarType extends BaseType {
    public Long value(Object object) {
        if (object == null) {
            return null;
        }

        if (isDate(object)) {
            return dateValue((LocalDate) object);
        } else if (isTime(object)) {
            return timeValue((TemporalAccessor) object);
        } else if (isDateTime(object)) {
            return dateTimeValue((TemporalAccessor) object);
        }
        throw new DMNRuntimeException(String.format("Cannot calculate value() for '%s'", object));
    }

    public Long dateValue(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }

        return dateTimeValue(toDateTime(localDate));
    }

    public Long timeValue(TemporalAccessor time) {
        if (time == null) {
            return null;
        }

        long result = 0;
        result += time.get(ChronoField.HOUR_OF_DAY) * 3600L;
        result += time.get(ChronoField.MINUTE_OF_HOUR) * 60L;
        result += time.get(ChronoField.SECOND_OF_MINUTE);

        try {
            ZoneId zoneId = ZoneId.from(time);
            if (zoneId instanceof ZoneOffset) {
                result -= ((ZoneOffset) zoneId).getTotalSeconds();
            } else {
                Instant instant = Instant.now();
                ZoneOffset zoneOffset = zoneId.getRules().getOffset(instant);
                result -= zoneOffset.getTotalSeconds();
            }
        } catch (Exception e) {
        }

        return result;
    }

    public Long timeValue(Temporal time) {
        return timeValue((TemporalAccessor) time);
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

    public Long dateTimeValue(TemporalAccessor dateTime) {
        if (dateTime == null) {
            return null;
        }

        if (dateTime instanceof LocalDateTime) {
            return ((LocalDateTime) dateTime).toEpochSecond(ZoneOffset.of("Z"));
        } else if (dateTime instanceof OffsetDateTime) {
            return ((OffsetDateTime) dateTime).toEpochSecond();
        } else if (dateTime instanceof ZonedDateTime) {
            return ((ZonedDateTime) dateTime).toEpochSecond();
        } else {
            return null;
        }
    }

    protected Long monthsValue(Period duration) {
        return duration.toTotalMonths();
    }

    protected Long secondsValue(Duration duration) {
        return duration.toMillis() / 1000;
    }

    public LocalDate toDate(Object obj) {
        if (obj == null) {
            return null;
        } else if (isDate(obj)) {
            return (LocalDate) obj;
        } else if (obj instanceof ZonedDateTime) {
            return ((ZonedDateTime) obj).toLocalDate();
        }
        throw new DMNRuntimeException(String.format("Cannot convert '%s' to date", obj));
    }

    public Temporal toDateTime(Object obj) {
        if (obj == null) {
            return null;
        } else if (isDate(obj)) {
            return ((LocalDate) obj).atStartOfDay(UTC);
        } else if (obj instanceof LocalDateTime) {
            return (LocalDateTime) obj;
        } else if (obj instanceof OffsetDateTime) {
            return (OffsetDateTime) obj;
        } else if (obj instanceof ZonedDateTime) {
            return (ZonedDateTime) obj;
        }
        throw new DMNRuntimeException(String.format("Cannot convert '%s' to date", obj));
    }

    public abstract boolean isDate(Object object);

    public abstract boolean isTime(Object object);

    public abstract boolean isDateTime(Object object);
}
