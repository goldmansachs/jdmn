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
import org.apache.commons.lang3.StringUtils;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class OffsetTimeLib extends BaseDateTimeLib {
    private final DatatypeFactory dataTypeFactory;

    public OffsetTimeLib(DatatypeFactory dataTypeFactory) {
        this.dataTypeFactory = dataTypeFactory;
    }

    public OffsetTime time(String literal) {
        if (StringUtils.isBlank(literal)) {
            return null;
        }

        return this.makeOffsetTime(literal);
    }

    public OffsetTime time(Double hour, Double minute, Double second, Duration offset) {
        if (hour == null || minute == null || second == null) {
            return null;
        }

        if (offset != null) {
            // Make ZoneOffset
            String sign = offset.getSign() < 0 ? "-" : "+";
            String offsetString = String.format("%s%02d:%02d:%02d", sign, (long) offset.getHours(), (long) offset.getMinutes(), offset.getSeconds());
            ZoneOffset zoneOffset = ZoneOffset.of(offsetString);

            // Make OffsetTime and add nanos
            OffsetTime offsetTime = OffsetTime.of(hour.intValue(), minute.intValue(), second.intValue(), 0, zoneOffset);
            Double secondFraction = second - second.intValue();
            double nanos = secondFraction * 1E9;
            offsetTime = offsetTime.plusNanos((long) nanos);
            return offsetTime;
        } else {
            // Make OffsetTime and add nanos
            OffsetTime offsetTime = OffsetTime.of(hour.intValue(), minute.intValue(), second.intValue(), 0, ZoneOffset.UTC);
            Double secondFraction = second - second.intValue();
            double nanos = secondFraction * 1E9;
            offsetTime = offsetTime.plusNanos((long) nanos);
            return offsetTime;
        }
    }

    public OffsetTime time(BigDecimal hour, BigDecimal minute, BigDecimal second, Duration offset) {
        if (offset != null) {
            // Make ZoneOffset
            String sign = offset.getSign() < 0 ? "-" : "+";
            String offsetString = String.format("%s%02d:%02d:%02d", sign, (long) offset.getHours(), (long) offset.getMinutes(), offset.getSeconds());
            ZoneOffset zoneOffset = ZoneOffset.of(offsetString);

            // Make OffsetTime and add nanos
            OffsetTime offsetTime = OffsetTime.of(hour.intValue(), minute.intValue(), second.intValue(), 0, zoneOffset);
            BigDecimal secondFraction = second.subtract(BigDecimal.valueOf(second.intValue()));
            double nanos = secondFraction.doubleValue() * 1E9;
            offsetTime = offsetTime.plusNanos((long) nanos);
            return offsetTime;
        } else {
            // Make OffsetTime and add nanos
            OffsetTime offsetTime = OffsetTime.of(hour.intValue(), minute.intValue(), second.intValue(), 0, ZoneOffset.UTC);
            BigDecimal secondFraction = second.subtract(BigDecimal.valueOf(second.intValue()));
            double nanos = secondFraction.doubleValue() * 1E9;
            offsetTime = offsetTime.plusNanos((long) nanos);
            return offsetTime;
        }
    }

    public OffsetTime time(ZonedDateTime from) {
        if (from == null) {
            return null;
        }
        return from.toOffsetDateTime().toOffsetTime();
    }
    public OffsetTime time(LocalDate from) {
        if (from == null) {
            return null;
        }
        return from.atStartOfDay(ZoneOffset.UTC).toOffsetDateTime().toOffsetTime();
    }
    public OffsetTime time(OffsetTime from) {
        if (from == null) {
            return null;
        }
        return from;
    }

    public OffsetTime toTime(Object object) {
        if (object instanceof ZonedDateTime) {
            return time((ZonedDateTime) object);
        } else if (object instanceof LocalDate) {
            return time((LocalDate) object);
        }
        return (OffsetTime) object;
    }

    public Integer hour(OffsetTime time) {
        if (time == null) {
            return null;
        }

        return time.getHour();
    }
    public Integer hour(ZonedDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }

        return dateTime.getHour();
    }

    public Integer minute(OffsetTime time) {
        if (time == null) {
            return null;
        }

        return time.getMinute();
    }
    public Integer minute(ZonedDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }

        return dateTime.getMinute();
    }

    public Integer second(OffsetTime time) {
        if (time == null) {
            return null;
        }

        return time.getSecond();
    }
    public Integer second(ZonedDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }

        return dateTime.getSecond();
    }

    public Duration timeOffset(OffsetTime time) {
        if (time == null) {
            return null;
        }

        // timezone offset in seconds
        int secondsOffset = time.getOffset().getTotalSeconds();
        return computeDuration(secondsOffset);
    }
    public Duration timeOffset(ZonedDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }

        // timezone offset in seconds
        int secondsOffset = dateTime.getOffset().getTotalSeconds();
        return computeDuration(secondsOffset);
    }

    public String timezone(OffsetTime time) {
        if (time == null) {
            return null;
        }

        return time.getOffset().getId();
    }
    public String timezone(ZonedDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }

        return dateTime.getZone().getId();
    }

    private Duration computeDuration(int secondsOffset) {
        return this.dataTypeFactory.newDuration((long) secondsOffset * 1000);
    }
}
