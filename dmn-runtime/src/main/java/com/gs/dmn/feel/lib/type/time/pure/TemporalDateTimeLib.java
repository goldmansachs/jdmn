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
import com.gs.dmn.feel.lib.type.time.DateTimeLib;
import com.gs.dmn.feel.lib.type.time.xml.DefaultDateTimeLib;
import org.apache.commons.lang3.StringUtils;

import javax.xml.datatype.DatatypeFactory;
import java.time.*;
import java.time.temporal.*;
import java.util.TimeZone;

public class TemporalDateTimeLib extends BaseDateTimeLib implements DateTimeLib<Number, LocalDate, Temporal, Temporal, TemporalAmount> {
    private final DefaultDateTimeLib dateTimeLib;

    public TemporalDateTimeLib(DatatypeFactory datatypeFactory) {
        this.dateTimeLib = new DefaultDateTimeLib(datatypeFactory);
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

    @Override
    public LocalDate date(LocalDate from) {
        return from;
    }

    public LocalDate dateDateTime(Temporal from) {
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

    @Override
    public Temporal time(String literal) {
        if (StringUtils.isBlank(literal)) {
            return null;
        }

        literal = this.fixDateTimeFormat(literal);
        TemporalAccessor parsed = this.dateTimeLib.timeTemporalAccessor(literal);
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

    @Override
    public Temporal time(Number hour, Number minute, Number second, TemporalAmount offset) {
        if (hour == null || minute == null || second == null || offset == null) {
            return null;
        }

        long hours = offset.get(ChronoUnit.HOURS);
        long minutes = offset.get(ChronoUnit.MINUTES);
        ZoneOffset zoneOffset = ZoneOffset.ofHoursMinutes((int)hours, (int)minutes);
        return OffsetTime.of(hour.intValue(), minute.intValue(), second.intValue(), 0, zoneOffset);
    }

    @Override
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

    @Override
    public Temporal dateAndTime(String literal) {
        if (StringUtils.isBlank(literal)) {
            return null;
        }

        literal = this.fixDateTimeFormat(literal);
        return (Temporal) this.dateTimeLib.dateTimeTemporalAccessor(literal);
    }

    @Override
    public Temporal dateAndTime(LocalDate date, Temporal time) {
        if (date == null || time == null) {
            return null;
        }

        if (time instanceof LocalTime) {
            return LocalDateTime.of(date, (LocalTime) time);
        } else if (time instanceof OffsetTime) {
            return OffsetDateTime.of(date, ((OffsetTime) time).toLocalTime(), ((OffsetTime) time).getOffset());
        }
        throw new IllegalArgumentException(String.format("Cannot convert '%s' and '%s' to date and time", date, time));
    }

    @Override
    public Integer year(LocalDate date) {
        if (date == null) {
            return null;
        }

        return date.getYear();
    }
    @Override
    public Integer yearDateTime(Temporal dateTime) {
        if (dateTime == null) {
            return null;
        }

        return dateTime.get(ChronoField.YEAR);
    }

    @Override
    public Integer month(LocalDate date) {
        if (date == null) {
            return null;
        }

        return date.getMonth().getValue();
    }
    @Override
    public Integer monthDateTime(Temporal dateTime) {
        if (dateTime == null) {
            return null;
        }

        return dateTime.get(ChronoField.MONTH_OF_YEAR);
    }

    @Override
    public Integer day(LocalDate date) {
        if (date == null) {
            return null;
        }

        return date.getDayOfMonth();
    }
    @Override
    public Integer dayDateTime(Temporal dateTime) {
        if (dateTime == null) {
            return null;
        }

        return dateTime.get(ChronoField.DAY_OF_MONTH);
    }

    @Override
    public Integer weekday(LocalDate date) {
        if (date == null) {
            return null;
        }

        return date.getDayOfWeek().getValue();
    }
    @Override
    public Integer weekdayDateTime(Temporal dateTime) {
        if (dateTime == null) {
            return null;
        }

        return dateTime.get(ChronoField.DAY_OF_WEEK);
    }

    @Override
    public Integer hour(Temporal time) {
        return time.get(ChronoField.HOUR_OF_DAY);
    }
    @Override
    public Integer hourDateTime(Temporal dateTime) {
        return hour(dateTime);
    }

    @Override
    public Integer minute(Temporal time) {
        return time.get(ChronoField.MINUTE_OF_HOUR);
    }
    @Override
    public Integer minuteDateTime(Temporal dateTime) {
        return minute(dateTime);
    }

    @Override
    public Integer second(Temporal time) {
        return time.get(ChronoField.SECOND_OF_MINUTE);
    }
    @Override
    public Integer secondDateTime(Temporal dateTime) {
        return second(dateTime);
    }

    @Override
    public TemporalAmount timeOffset(Temporal time) {
        int secondsOffset = time.get(ChronoField.OFFSET_SECONDS);
        return duration((long) secondsOffset * 1000);
    }
    @Override
    public TemporalAmount timeOffsetDateTime(Temporal dateTime) {
        return timeOffset(dateTime);
    }

    @Override
    public String timezone(Temporal time) {
        return time.query(TemporalQueries.zone()).getId();
    }
    @Override
    public String timezoneDateTime(Temporal dateTime) {
        return timezone(dateTime);
    }

    public LocalDate toDate(Object object) {
        if (object instanceof Temporal) {
            return dateDateTime((Temporal) object);
        }
        return null;
    }

    public Temporal toTime(Object object) {
        if (object instanceof Temporal) {
            return time((Temporal) object);
        }
        return null;
    }

    private TemporalAmount duration(long milliseconds) {
        return Duration.ofSeconds(milliseconds / 1000, (milliseconds % 1000) * 1000);
    }
}
