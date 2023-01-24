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
import com.gs.dmn.runtime.DMNRuntimeException;
import org.apache.commons.lang3.StringUtils;

import java.time.*;
import java.time.format.TextStyle;
import java.time.temporal.*;
import java.util.Locale;
import java.util.TimeZone;

import static com.gs.dmn.feel.lib.type.BaseType.UTC;

public class TemporalDateTimeLib extends BaseDateTimeLib implements DateTimeLib<Number, LocalDate, Temporal, Temporal, TemporalAmount> {
    private final DefaultDateTimeLib dateTimeLib;

    public TemporalDateTimeLib() {
        this.dateTimeLib = new DefaultDateTimeLib();
    }

    //
    // Conversion functions
    //
    @Override
    public LocalDate date(String literal) {
         return this.makeLocalDate(literal);
    }

    @Override
    public LocalDate date(Number year, Number month, Number day) {
        if (year == null || month == null || day == null) {
            return null;
        }

        return LocalDate.of(year.intValue(), month.intValue(), day.intValue());
    }

    @Override
    public LocalDate date(Object from) {
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
        throw new DMNRuntimeException(String.format("Cannot convert '%s' to date", from.getClass().getSimpleName()));
    }

    public Temporal dateDateTime(Temporal from) {
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
            return from;
        }
        throw new DMNRuntimeException(String.format("Cannot convert '%s' to date", from.getClass().getSimpleName()));
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
            return OffsetTime.of(localTime, zoneOffset);
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
    public Temporal time(Object from) {
        if (from == null) {
            return null;
        }

        if (from instanceof LocalDate) {
            return ((LocalDate) from).atStartOfDay(ZoneOffset.UTC).toOffsetDateTime().toOffsetTime();
        } else if (from instanceof LocalTime) {
            return (LocalTime) from;
        } else if (from instanceof OffsetTime) {
            return (OffsetTime) from;
        } else if (from instanceof LocalDateTime) {
            return ((LocalDateTime) from).toLocalTime();
        } else if (from instanceof OffsetDateTime) {
            return ((OffsetDateTime) from).toOffsetTime();
        } else if (from instanceof ZonedDateTime) {
            return ((ZonedDateTime) from).toOffsetDateTime().toOffsetTime();
        }
        throw new DMNRuntimeException(String.format("Cannot convert '%s' to time", from.getClass().getSimpleName()));
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
    public Temporal dateAndTime(Object date, Object time) {
        if (date == null || time == null) {
            return null;
        }

        if (date instanceof LocalDate) {
            if (time instanceof LocalTime) {
                return LocalDateTime.of((LocalDate) date, (LocalTime) time);
            } else if (time instanceof OffsetTime) {
                return OffsetDateTime.of((LocalDate) date, ((OffsetTime) time).toLocalTime(), ((OffsetTime) time).getOffset());
            }
        }
        throw new DMNRuntimeException(String.format("Cannot convert '%s' and '%s' to date and time", date, time));
    }

    //
    // Date properties
    //
    @Override
    public Integer year(Object date) {
        if (date == null) {
            return null;
        }

        return ((Temporal) date).get(ChronoField.YEAR);
    }

    @Override
    public Integer month(Object date) {
        if (date == null) {
            return null;
        }

        return ((Temporal) date).get(ChronoField.MONTH_OF_YEAR);
    }

    @Override
    public Integer day(Object date) {
        if (date == null) {
            return null;
        }

        return ((Temporal) date).get(ChronoField.DAY_OF_MONTH);
    }

    @Override
    public Integer weekday(Object date) {
        if (date == null) {
            return null;
        }

        return ((Temporal) date).get(ChronoField.DAY_OF_WEEK);
    }

    //
    // Time properties
    //
    @Override
    public Integer hour(Object time) {
        if (time == null) {
            return null;
        }

        return ((Temporal) time).get(ChronoField.HOUR_OF_DAY);
    }

    @Override
    public Integer minute(Object time) {
        if (time == null) {
            return null;
        }

        return ((Temporal) time).get(ChronoField.MINUTE_OF_HOUR);
    }

    @Override
    public Integer second(Object time) {
        if (time == null) {
            return null;
        }

        return ((Temporal) time).get(ChronoField.SECOND_OF_MINUTE);
    }

    @Override
    public TemporalAmount timeOffset(Object time) {
        if (time == null) {
            return null;
        }

        int secondsOffset = ((Temporal) time).get(ChronoField.OFFSET_SECONDS);
        return duration((long) secondsOffset * 1000);
    }

    @Override
    public String timezone(Object time) {
        if (time == null) {
            return null;
        }

        return ((Temporal) time).query(TemporalQueries.zone()).getId();
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

        DayOfWeek dayOfWeek =  toDate(date).getDayOfWeek();
        return dayOfWeek.getDisplayName(TextStyle.FULL, Locale.US);
    }

    @Override
    public Integer weekOfYear(Object date) {
        if (date == null) {
            return null;
        }

        return  toDate(date).get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
    }

    @Override
    public String monthOfYear(Object date) {
        if (date == null) {
            return null;
        }

        Month month =  toDate(date).getMonth();
        return MONTH_NAMES[month.getValue() - 1];
    }

    //
    // Extra conversion functions
    //
    @Override
    public LocalDate toDate(Object from) {
        if (from instanceof Temporal) {
            return date(from);
        }
        return null;
    }

    @Override
    public Temporal toTime(Object from) {
        if (from instanceof Temporal) {
            return time(from);
        }
        return null;
    }

    @Override
    public Temporal toDateTime(Object from) {
        if (from instanceof Temporal) {
            return dateDateTime((Temporal) from);
        }
        return null;
    }

    private TemporalAmount duration(long milliseconds) {
        return Duration.ofSeconds(milliseconds / 1000, (milliseconds % 1000) * 1000);
    }
}
