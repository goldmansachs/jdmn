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
import com.gs.dmn.feel.lib.type.time.xml.DefaultDurationComparator;
import com.gs.dmn.feel.lib.type.time.xml.XMLDurationFactory;
import com.gs.dmn.runtime.DMNRuntimeException;
import org.apache.commons.lang3.StringUtils;

import javax.xml.datatype.Duration;
import java.math.BigDecimal;
import java.time.*;
import java.time.format.TextStyle;
import java.time.temporal.IsoFields;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

import static com.gs.dmn.feel.lib.type.BaseType.UTC;

public class MixedDateTimeLib extends BaseDateTimeLib implements DateTimeLib<Number, LocalDate, OffsetTime, ZonedDateTime, Duration> {
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

        if (from instanceof LocalDate date) {
            return date;
        } else if (from instanceof ZonedDateTime time) {
            return time.toLocalDate();
        }
        throw new DMNRuntimeException("Cannot convert '%s' to date".formatted(from.getClass().getSimpleName()));
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
            String offsetString = "%s%02d:%02d:%02d".formatted(sign, (long) offset.getHours(), (long) offset.getMinutes(), offset.getSeconds());
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
            String offsetString = "%s%02d:%02d:%02d".formatted(sign, (long) offset.getHours(), (long) offset.getMinutes(), offset.getSeconds());
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

    @Override
    public OffsetTime time(Object from) {
        if (from == null) {
            return null;
        }

        if (from instanceof LocalDate date) {
            return date.atStartOfDay(ZoneOffset.UTC).toOffsetDateTime().toOffsetTime();
        } else if (from instanceof OffsetTime time) {
            return time;
        } else if (from instanceof ZonedDateTime time) {
            return time.toOffsetDateTime().toOffsetTime();
        }
        throw new DMNRuntimeException("Cannot convert '%s' to time".formatted(from.getClass().getSimpleName()));
    }

    @Override
    public ZonedDateTime dateAndTime(String from) {
        if (from == null) {
            return null;
        }

        if (this.hasZoneId(from) && this.hasZoneOffset(from)) {
            throw new DMNRuntimeException("Time literal '%s' has both a zone offset and zone id".formatted(from));
        }
        if (!BEGIN_YEAR.matcher(from).find()) {
            throw new DMNRuntimeException("Illegal year in '%s'".formatted(from));
        }
        return makeZonedDateTime(from);
    }

    @Override
    public ZonedDateTime dateAndTime(Object dateObj, Object timeObj) {
        if (dateObj == null || timeObj == null) {
            return null;
        }

        if (timeObj instanceof OffsetTime time) {
            if (dateObj instanceof LocalDate date) {
                ZoneOffset offset = time.getOffset();
                LocalDateTime localDateTime = LocalDateTime.of(date, time.toLocalTime());
                return ZonedDateTime.ofInstant(localDateTime, offset, ZoneId.of(offset.getId()));
            } else if (dateObj instanceof ZonedDateTime date) {
                return dateAndTime(date.toLocalDate(), time);
            }
        }
        throw new DMNRuntimeException("Cannot convert '%s' and '%s' to date and time".formatted(dateObj.getClass().getSimpleName(), timeObj.getClass().getSimpleName()));
    }

    //
    // Date properties
    //
    @Override
    public Integer year(Object date) {
        if (date == null) {
            return null;
        }

        if (date instanceof LocalDate localDate) {
            return localDate.getYear();
        } else if (date instanceof ZonedDateTime time) {
            return time.getYear();
        }
        throw new RuntimeException("Cannot extract 'year' from %s".formatted(date));
    }

    @Override
    public Integer month(Object date) {
        if (date == null) {
            return null;
        }

        if (date instanceof LocalDate localDate) {
            return localDate.getMonth().getValue();
        } else if (date instanceof ZonedDateTime time) {
            return time.getMonth().getValue();
        }
        throw new RuntimeException("Cannot extract 'month' from %s".formatted(date));
    }

    @Override
    public Integer day(Object date) {
        if (date == null) {
            return null;
        }

        if (date instanceof LocalDate localDate) {
            return localDate.getDayOfMonth();
        } else if (date instanceof ZonedDateTime time) {
            return time.getDayOfMonth();
        }
        throw new RuntimeException("Cannot extract 'day' from %s".formatted(date));
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

        if (time instanceof OffsetTime offsetTime) {
            return offsetTime.getHour();
        } else if (time instanceof ZonedDateTime dateTime) {
            return dateTime.getHour();
        }
        throw new RuntimeException("Cannot extract 'hour' from %s".formatted(time));
    }

    @Override
    public Integer minute(Object time) {
        if (time == null) {
            return null;
        }

        if (time instanceof OffsetTime offsetTime) {
            return offsetTime.getMinute();
        } else if (time instanceof ZonedDateTime dateTime) {
            return dateTime.getMinute();
        }
        throw new RuntimeException("Cannot extract 'minute' from %s".formatted(time));
    }

    //
    // Time properties
    //
    @Override
    public Integer second(Object time) {
        if (time == null) {
            return null;
        }

        if (time instanceof OffsetTime offsetTime) {
            return offsetTime.getSecond();
        } else if (time instanceof ZonedDateTime dateTime) {
            return dateTime.getSecond();
        }
        throw new RuntimeException("Cannot extract 'second' from %s".formatted(time));
    }

    @Override
    public Duration timeOffset(Object time) {
        if (time == null) {
            return null;
        }

        // timezone offset in seconds
        if (time instanceof OffsetTime offsetTime) {
            int secondsOffset = offsetTime.getOffset().getTotalSeconds();
            return XMLDurationFactory.INSTANCE.dayTimeFromValue(secondsOffset);
        } else if (time instanceof ZonedDateTime dateTime) {
            int secondsOffset = dateTime.getOffset().getTotalSeconds();
            return XMLDurationFactory.INSTANCE.dayTimeFromValue(secondsOffset);
        }
        throw new RuntimeException("Cannot extract 'timeOffset' from %s".formatted(time));
    }

    @Override
    public String timezone(Object time) {
        if (time == null) {
            return null;
        }

        if (time instanceof OffsetTime offsetTime) {
            return offsetTime.getOffset().getId();
        } else if (time instanceof ZonedDateTime dateTime) {
            return dateTime.getZone().getId();
        }
        throw new RuntimeException("Cannot extract 'timeOffset' from %s".formatted(time));
    }

    @Override
    public ZonedDateTime now() {
        return ZonedDateTime.now();
    }

    @Override
    public LocalDate today() {
        return LocalDate.now();
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

        if (from instanceof LocalDate date) {
            return date;
        } else if (from instanceof OffsetTime) {
            return null;
        } else if (from instanceof ZonedDateTime) {
            return date(from);
        }
        throw new DMNRuntimeException("Cannot convert '%s' to date".formatted(from.getClass().getSimpleName()));
    }

    @Override
    public OffsetTime toTime(Object from) {
        if (from == null) {
            return null;
        }

        if (from instanceof LocalDate) {
            return time(from);
        } else if (from instanceof OffsetTime time) {
            return time;
        } else if (from instanceof ZonedDateTime) {
            return time(from);
        }
        throw new DMNRuntimeException("Cannot convert '%s' to time".formatted(from.getClass().getSimpleName()));
    }

    @Override
    public ZonedDateTime toDateTime(Object from) {
        if (from == null) {
            return null;
        }

        if (from instanceof LocalDate date) {
            return date.atStartOfDay(UTC);
        } else if (from instanceof LocalDateTime time) {
            return time.atZone(UTC);
        } else if (from instanceof OffsetDateTime time) {
            return time.atZoneSameInstant(UTC);
        } else if (from instanceof ZonedDateTime time) {
            return time;
        }
        throw new DMNRuntimeException("Cannot convert '%s' to date time".formatted(from.getClass().getSimpleName()));
    }

    @Override
    public <T> T min(List<T> list) {
        return minMax(list, LocalDateComparator.COMPARATOR, OffsetTimeComparator.COMPARATOR, ZonedDateTimeComparator.COMPARATOR, DefaultDurationComparator.COMPARATOR, x -> x > 0);
    }

    @Override
    public <T> T max(List<T> list) {
        return minMax(list, LocalDateComparator.COMPARATOR, OffsetTimeComparator.COMPARATOR, ZonedDateTimeComparator.COMPARATOR, DefaultDurationComparator.COMPARATOR, x -> x < 0);
    }

    private <T> T minMax(List<T> list, LocalDateComparator dateComparator, OffsetTimeComparator timeComparator, ZonedDateTimeComparator dateTimeComparator, DefaultDurationComparator durationComparator, Predicate<Integer> condition) {
        if (list == null || list.isEmpty()) {
            return null;
        }

        T result = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            T x = list.get(i);
            if (dateComparator.isDate(result) && dateComparator.isDate(x)) {
                if (condition.test(dateComparator.compareTo((LocalDate) result, (LocalDate) x))) {
                    result = x;
                }
            } else if (timeComparator.isTime(result) && timeComparator.isTime(x)) {
                if (condition.test(timeComparator.compareTo((OffsetTime) result, (OffsetTime) x))) {
                    result = x;
                }
            } else if (dateTimeComparator.isDateTime(result) && dateTimeComparator.isDateTime(x)) {
                if (condition.test(dateTimeComparator.compareTo((ZonedDateTime) result, (ZonedDateTime) x))) {
                    result = x;
                }
            } else if (durationComparator.isYearsAndMonthsDuration(result) && durationComparator.isYearsAndMonthsDuration(x)) {
                if (condition.test(durationComparator.compareTo((Duration) result, (Duration) x))) {
                    result = x;
                }
            } else if (durationComparator.isDaysAndTimeDuration(result) && durationComparator.isDaysAndTimeDuration(x)) {
                if (condition.test(durationComparator.compareTo((Duration) result, (Duration) x))) {
                    result = x;
                }
            } else {
                throw new DMNRuntimeException("Cannot compare '%s' and '%s'".formatted(result, x));
            }
        }
        return result;
    }
}
