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
import com.gs.dmn.feel.lib.type.time.xml.XMLDurationFactory;
import org.apache.commons.lang3.StringUtils;

import javax.xml.datatype.Duration;
import java.math.BigDecimal;
import java.time.*;
import java.time.format.TextStyle;
import java.time.temporal.IsoFields;
import java.util.Locale;

import static com.gs.dmn.feel.lib.type.BaseType.UTC;

public class MixedDateTimeLib extends BaseDateTimeLib implements DateTimeLib<Number, LocalDate, OffsetTime, ZonedDateTime, Duration> {
    //
    // Conversion functions
    //
    @Override
    public LocalDate date(String literal) {
        if (StringUtils.isBlank(literal)) {
            return null;
        }

        if (this.hasTime(literal) || this.hasZoneId(literal)) {
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
            double secondFraction = second.doubleValue() - second.intValue();
            double nanos = secondFraction * 1E9;
            offsetTime = offsetTime.plusNanos((long) nanos);
            return offsetTime;
        } else {
            // Make OffsetTime and add nanos
            OffsetTime offsetTime = OffsetTime.of(hour.intValue(), minute.intValue(), second.intValue(), 0, ZoneOffset.UTC);
            double secondFraction = second.doubleValue() - second.intValue();
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
        if (this.hasZoneId(from) && this.hasZoneOffset(from)) {
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

    //
    // Date properties
    //
    @Override
    public Integer year(Object date) {
        if (date == null) {
            return null;
        }

        if (date instanceof LocalDate) {
            return ((LocalDate) date).getYear();
        } else if (date instanceof ZonedDateTime) {
            return ((ZonedDateTime) date).getYear();
        }
        throw new RuntimeException(String.format("Cannot extract 'year' from %s", date));
    }

    @Override
    public Integer month(Object date) {
        if (date == null) {
            return null;
        }

        if (date instanceof LocalDate) {
            return ((LocalDate) date).getMonth().getValue();
        } else if (date instanceof ZonedDateTime) {
            return ((ZonedDateTime) date).getMonth().getValue();
        }
        throw new RuntimeException(String.format("Cannot extract 'month' from %s", date));
    }

    @Override
    public Integer day(Object date) {
        if (date == null) {
            return null;
        }

        if (date instanceof LocalDate) {
            return ((LocalDate) date).getDayOfMonth();
        } else if (date instanceof ZonedDateTime) {
            return ((ZonedDateTime) date).getDayOfMonth();
        }
        throw new RuntimeException(String.format("Cannot extract 'day' from %s", date));
    }

    @Override
    public Integer weekday(Object date) {
        if (date == null) {
            return null;
        }

        return toDate(date).getDayOfWeek().getValue();
    }

    @Override
    public Integer hour(Object time) {
        if (time == null) {
            return null;
        }

        if (time instanceof OffsetTime) {
            return ((OffsetTime) time).getHour();
        } else if (time instanceof ZonedDateTime) {
            return ((ZonedDateTime) time).getHour();
        }
        throw new RuntimeException(String.format("Cannot extract 'hour' from %s", time));
    }

    @Override
    public Integer minute(Object time) {
        if (time == null) {
            return null;
        }

        if (time instanceof OffsetTime) {
            return ((OffsetTime) time).getMinute();
        } else if (time instanceof ZonedDateTime) {
            return ((ZonedDateTime) time).getMinute();
        }
        throw new RuntimeException(String.format("Cannot extract 'minute' from %s", time));
    }

    //
    // Time properties
    //
    @Override
    public Integer second(Object time) {
        if (time == null) {
            return null;
        }

        if (time instanceof OffsetTime) {
            return ((OffsetTime) time).getSecond();
        } else if (time instanceof ZonedDateTime) {
            return ((ZonedDateTime) time).getSecond();
        }
        throw new RuntimeException(String.format("Cannot extract 'second' from %s", time));
    }

    @Override
    public Duration timeOffset(Object time) {
        if (time == null) {
            return null;
        }

        // timezone offset in seconds
        if (time instanceof OffsetTime) {
            int secondsOffset = ((OffsetTime) time).getOffset().getTotalSeconds();
            return XMLDurationFactory.INSTANCE.dayTimeFromValue(secondsOffset);
        } else if (time instanceof ZonedDateTime) {
            int secondsOffset = ((ZonedDateTime) time).getOffset().getTotalSeconds();
            return XMLDurationFactory.INSTANCE.dayTimeFromValue(secondsOffset);
        }
        throw new RuntimeException(String.format("Cannot extract 'timeOffset' from %s", time));
    }

    @Override
    public String timezone(Object time) {
        if (time == null) {
            return null;
        }

        if (time instanceof OffsetTime) {
            return ((OffsetTime) time).getOffset().getId();
        } else if (time instanceof ZonedDateTime) {
            return ((ZonedDateTime) time).getZone().getId();
        }
        throw new RuntimeException(String.format("Cannot extract 'timeOffset' from %s", time));
    }

    //
    // Temporal functions
    //
    @Override
    public Integer dayOfYear(Object date) {
        if (date == null) {
            return null;
        }

        return toDate(date).getDayOfYear();
    }

    @Override
    public String dayOfWeek(Object date) {
        if (date == null) {
            return null;
        }

        DayOfWeek dayOfWeek = toDate(date).getDayOfWeek();
        return dayOfWeek.getDisplayName(TextStyle.FULL, Locale.US);
    }

    @Override
    public Integer weekOfYear(Object date) {
        if (date == null) {
            return null;
        }

        return toDate(date).get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
    }

    @Override
    public String monthOfYear(Object date) {
        if (date == null) {
            return null;
        }

        Month month = toDate(date).getMonth();
        return MONTH_NAMES[month.getValue() - 1];
    }

    //
    // Extra conversion functions
    //
    @Override
    public LocalDate toDate(Object from) {
        if (from == null) {
            return null;
        }

        if (from instanceof LocalDate) {
            return (LocalDate) from;
        } else if (from instanceof OffsetTime) {
            return null;
        } else if (from instanceof ZonedDateTime) {
            return date((ZonedDateTime) from);
        }
        throw new IllegalArgumentException(String.format("Cannot convert '%s' to date", from.getClass().getSimpleName()));
    }

    @Override
    public OffsetTime toTime(Object from) {
        if (from == null) {
            return null;
        }

        if (from instanceof LocalDate) {
            return time((LocalDate) from);
        } else if (from instanceof OffsetTime) {
            return (OffsetTime) from;
        } else if (from instanceof ZonedDateTime) {
            return time((ZonedDateTime) from);
        }
        throw new IllegalArgumentException(String.format("Cannot convert '%s' to time", from.getClass().getSimpleName()));
    }

    @Override
    public ZonedDateTime toDateTime(Object from) {
        if (from == null) {
            return null;
        }

        if (from instanceof LocalDate) {
            return ((LocalDate) from).atStartOfDay(UTC);
        } else if (from instanceof LocalDateTime) {
            return ((LocalDateTime) from).atZone(UTC);
        } else if (from instanceof OffsetDateTime) {
            return ((OffsetDateTime) from).atZoneSameInstant(UTC);
        } else if (from instanceof ZonedDateTime) {
            return (ZonedDateTime) from;
        }
        throw new IllegalArgumentException(String.format("Cannot convert '%s' to date time", from.getClass().getSimpleName()));
    }
}
