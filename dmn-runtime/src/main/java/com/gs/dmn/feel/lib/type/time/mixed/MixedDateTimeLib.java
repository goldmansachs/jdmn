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
import com.gs.dmn.feel.lib.type.time.DateTimeLib;
import org.apache.commons.lang3.StringUtils;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import java.math.BigDecimal;
import java.time.*;

public class MixedDateTimeLib extends BaseDateTimeLib implements DateTimeLib<Number, LocalDate, OffsetTime, ZonedDateTime, Duration> {
    private final DatatypeFactory datatypeFactory;

    public MixedDateTimeLib(DatatypeFactory datatypeFactory) {
        this.datatypeFactory = datatypeFactory;
    }

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

    @Override
    public OffsetTime time(String literal) {
        if (StringUtils.isBlank(literal)) {
            return null;
        }

        return this.makeOffsetTime(literal);
    }

    @Override
    public OffsetTime time(Number hour, Number minute, Number second, Duration offset) {
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
            Double secondFraction = second.doubleValue() - second.intValue();
            double nanos = secondFraction * 1E9;
            offsetTime = offsetTime.plusNanos((long) nanos);
            return offsetTime;
        } else {
            // Make OffsetTime and add nanos
            OffsetTime offsetTime = OffsetTime.of(hour.intValue(), minute.intValue(), second.intValue(), 0, ZoneOffset.UTC);
            Double secondFraction = second.doubleValue() - second.intValue();
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
    @Override
    public OffsetTime time(OffsetTime from) {
        if (from == null) {
            return null;
        }
        return from;
    }

    @Override
    public ZonedDateTime dateAndTime(String from) {
        if (from == null) {
            return null;
        }
        if (this.hasZone(from) && this.hasOffset(from)) {
            return null;
        }
        if (this.invalidYear(from)) {
            return null;
        }

        return makeZonedDateTime(from);
    }

    @Override
    public ZonedDateTime dateAndTime(LocalDate date, OffsetTime time) {
        if (date == null || time == null) {
            return null;
        }

        ZoneOffset offset = time.getOffset();
        LocalDateTime localDateTime = LocalDateTime.of(date, time.toLocalTime());
        return ZonedDateTime.ofInstant(localDateTime, offset, ZoneId.of(offset.getId()));
    }
    public ZonedDateTime dateAndTime(Object date, OffsetTime time) {
        if (date == null || time == null) {
            return null;
        }

        if (date instanceof ZonedDateTime) {
            return dateAndTime(((ZonedDateTime) date).toLocalDate(), time);
        } else {
            return null;
        }
    }

    @Override
    public Integer year(LocalDate date) {
        if (date == null) {
            return null;
        }

        return date.getYear();
    }
    @Override
    public Integer yearDateTime(ZonedDateTime dateTime) {
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
    @Override
    public Integer monthDateTime(ZonedDateTime dateTime) {
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
    @Override
    public Integer dayDateTime(ZonedDateTime dateTime) {
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
    @Override
    public Integer weekdayDateTime(ZonedDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }

        return dateTime.getDayOfWeek().getValue();
    }

    @Override
    public Integer hour(OffsetTime time) {
        if (time == null) {
            return null;
        }

        return time.getHour();
    }
    @Override
    public Integer hourDateTime(ZonedDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }

        return dateTime.getHour();
    }

    @Override
    public Integer minute(OffsetTime time) {
        if (time == null) {
            return null;
        }

        return time.getMinute();
    }
    @Override
    public Integer minuteDateTime(ZonedDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }

        return dateTime.getMinute();
    }

    @Override
    public Integer second(OffsetTime time) {
        if (time == null) {
            return null;
        }

        return time.getSecond();
    }
    @Override
    public Integer secondDateTime(ZonedDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }

        return dateTime.getSecond();
    }

    @Override
    public Duration timeOffset(OffsetTime time) {
        if (time == null) {
            return null;
        }

        // timezone offset in seconds
        int secondsOffset = time.getOffset().getTotalSeconds();
        return computeDuration(secondsOffset);
    }
    @Override
    public Duration timeOffsetDateTime(ZonedDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }

        // timezone offset in seconds
        int secondsOffset = dateTime.getOffset().getTotalSeconds();
        return computeDuration(secondsOffset);
    }

    @Override
    public String timezone(OffsetTime time) {
        if (time == null) {
            return null;
        }

        return time.getOffset().getId();
    }
    @Override
    public String timezoneDateTime(ZonedDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }

        return dateTime.getZone().getId();
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

    public OffsetTime toTime(Object object) {
        if (object instanceof ZonedDateTime) {
            return time((ZonedDateTime) object);
        } else if (object instanceof LocalDate) {
            return time((LocalDate) object);
        }
        return (OffsetTime) object;
    }

    private Duration computeDuration(int secondsOffset) {
        return this.datatypeFactory.newDuration((long) secondsOffset * 1000);
    }
}
