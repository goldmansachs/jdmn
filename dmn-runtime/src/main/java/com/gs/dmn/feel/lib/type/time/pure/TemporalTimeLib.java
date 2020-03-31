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

import com.gs.dmn.feel.lib.DefaultFEELLib;
import com.gs.dmn.feel.lib.type.time.BaseDateTimeLib;
import com.gs.dmn.feel.lib.type.time.xml.DefaultTimeLib;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.*;
import java.time.temporal.*;
import java.util.TimeZone;

public class TemporalTimeLib extends BaseDateTimeLib {
    private DefaultTimeLib timeLib = new DefaultTimeLib(DefaultFEELLib.DATA_TYPE_FACTORY);

    public Temporal time(String literal) {
        if (StringUtils.isBlank(literal)) {
            return null;
        }

        literal = this.fixDateTimeFormat(literal);
        TemporalAccessor parsed = this.timeLib.temporalAccessor(literal);
        if (parsed.query(TemporalQueries.zoneId()) != null) {
            LocalTime localTime = parsed.query(LocalTime::from);
            ZoneId zoneId = parsed.query(TemporalQueries.zoneId());
            int millisOffset = TimeZone.getTimeZone(zoneId).getRawOffset();
            ZoneOffset zoneOffset = ZoneOffset.ofTotalSeconds(millisOffset / 1000);
            OffsetTime asOffSetTime = OffsetTime.of(localTime, zoneOffset);
            return asOffSetTime;
        } else {
            return (Temporal) parsed;
        }
    }

    public OffsetTime time(BigDecimal hour, BigDecimal minute, BigDecimal second, TemporalAmount offset) {
        if (hour == null || minute == null || second == null || offset == null) {
            return null;
        }

        long hours = offset.get(ChronoUnit.HOURS);
        long minutes = offset.get(ChronoUnit.MINUTES);
        ZoneOffset zoneOffset = ZoneOffset.ofHoursMinutes((int)hours, (int)minutes);
        return OffsetTime.of(hour.intValue(), minute.intValue(), second.intValue(), 0, zoneOffset);
    }

    public Temporal time(Temporal from) {
        if (from == null) {
            return null;
        }

        if (from instanceof LocalDate) {
            return ((LocalDate) from).atStartOfDay(ZoneOffset.UTC).toOffsetDateTime().toOffsetTime();
        } else if (from instanceof LocalTime) {
            return from;
        } else if (from instanceof OffsetTime) {
            return from;
        } else if (from instanceof LocalDateTime) {
            return ((LocalDateTime) from).toLocalTime();
        } else if (from instanceof OffsetDateTime) {
            return ((OffsetDateTime) from).toOffsetTime();
        } else if (from instanceof ZonedDateTime) {
            return ((ZonedDateTime) from).toOffsetDateTime().toOffsetTime();
        }
        throw new IllegalArgumentException(String.format("Cannot convert '%s' to time", from.getClass().getSimpleName()));
    }

    public Temporal toTime(Object object) {
        if (object instanceof Temporal) {
            return time((Temporal) object);
        }
        return null;
    }

    public Integer hour(Temporal time) {
        return time.get(ChronoField.HOUR_OF_DAY);
    }

    public Integer minute(Temporal time) {
        return time.get(ChronoField.MINUTE_OF_HOUR);
    }

    public Integer second(Temporal time) {
        return time.get(ChronoField.SECOND_OF_MINUTE);
    }

    public TemporalAmount timeOffset(Temporal time) {
        int secondsOffset = time.get(ChronoField.OFFSET_SECONDS);
        return duration((long) secondsOffset * 1000);
    }

    public String timezone(Temporal time) {
        return time.query(TemporalQueries.zone()).getId();
    }

    private TemporalAmount duration(long milliseconds) {
        return Duration.ofSeconds(milliseconds / 1000, (milliseconds % 1000) * 1000);
    }
}
