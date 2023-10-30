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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.time.format.TextStyle;
import java.time.temporal.*;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.function.Predicate;

import static com.gs.dmn.feel.lib.type.BaseType.UTC;

public class TemporalDateTimeLib extends BaseDateTimeLib implements DateTimeLib<Number, LocalDate, TemporalAccessor, TemporalAccessor, TemporalAmount> {
    private static final BigDecimal E9 = BigDecimal.valueOf(1000000000);

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

        try {
            return LocalDate.from((TemporalAccessor) from);
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot convert '%s' to date", from));
        }
    }

    @Override
    public TemporalAccessor time(String literal) {
        if (StringUtils.isBlank(literal)) {
            return null;
        }

        literal = this.fixDateTimeFormat(literal);
        return this.dateTimeLib.timeTemporalAccessor(literal);
    }

    @Override
    public TemporalAccessor time(Number hour, Number minute, Number second, TemporalAmount offset) {
        if (hour == null || minute == null || second == null) {
            return null;
        }

        try {
            int nanosecs = 0;
            if (second instanceof BigDecimal) {
                BigDecimal secs = (BigDecimal) second;
                nanosecs = secs.subtract(secs.setScale(0, RoundingMode.DOWN)).multiply(E9).intValue();
            }

            if (offset == null) {
                return LocalTime.of(hour.intValue(), minute.intValue(), second.intValue(), nanosecs);
            } else {
                return OffsetTime.of(hour.intValue(), minute.intValue(), second.intValue(), nanosecs, ZoneOffset.ofTotalSeconds((int) offset.get(ChronoUnit.SECONDS)));
            }
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Error in time('%s', '%s', '%s', '%s'", hour, minute, second, offset));
        }
    }

    @Override
    public TemporalAccessor time(Object from) {
        if (from == null) {
            return null;
        }

        if (from instanceof TemporalAccessor) {
            TemporalAccessor date = (TemporalAccessor) from;
            if (!date.isSupported(ChronoField.HOUR_OF_DAY)) {
                // is LocalDate
                return ((LocalDate) from).atStartOfDay(ZoneOffset.UTC).toOffsetDateTime().toOffsetTime();
            }
            ZoneOffset zoneOffset = date.query(TemporalQueries.offset());
            ZoneId zoneId = date.query(TemporalQueries.zoneId());
            if (zoneOffset == null) {
                // Has no offset
                return LocalTime.from(date);
            }
            if (zoneId == null) {
                return OffsetTime.from(date);
            } else {
                if (!(zoneId instanceof ZoneOffset)) {
                    return time(date.query(TemporalQueries.localTime()) + "@" + zoneId);
                } else {
                    return OffsetTime.from(date);
                }
            }
        }
        throw new DMNRuntimeException(String.format("Cannot convert '%s' to time", from.getClass().getSimpleName()));
    }

    @Override
    public TemporalAccessor dateAndTime(String literal) {
        if (StringUtils.isBlank(literal)) {
            return null;
        }

        literal = this.fixDateTimeFormat(literal);
        return this.dateTimeLib.dateTimeTemporalAccessor(literal);
    }

    @Override
    public TemporalAccessor dateAndTime(Object date, Object time) {
        return dateAndTime((TemporalAccessor) date, (TemporalAccessor) time);
    }

    public TemporalAccessor dateAndTime(TemporalAccessor date, TemporalAccessor time) {
        if (date == null || time == null) {
            return null;
        }

        // Convert date to LocalDate
        if (!(date instanceof LocalDate)) {
            date = date.query(TemporalQueries.localDate());
        }
        if (date == null) {
            throw new DMNRuntimeException(String.format("Error in dateAndTime('%s', '%s')", date, time));
        }
        // Check time
        if (!(time instanceof LocalTime || ((time.query(TemporalQueries.localTime()) != null && time.query(TemporalQueries.zone()) != null)))) {
            throw new DMNRuntimeException(String.format("Error in dateAndTime('%s', '%s')", date, time));
        }

        try {
            if (time instanceof LocalTime) {
                return LocalDateTime.of((LocalDate) date, (LocalTime) time);
            } else if (time.query(TemporalQueries.localTime()) != null && time.query(TemporalQueries.zone()) != null) {
                return ZonedDateTime.of((LocalDate) date, LocalTime.from(time), ZoneId.from(time));
            }
            throw new DMNRuntimeException(String.format("Error in dateAndTime('%s', '%s')", date, time));
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Error in dateAndTime('%s', '%s')", date, time));
        }
    }

    public TemporalAccessor dateTime(Object from) {
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
        throw new DMNRuntimeException(String.format("Cannot convert '%s' to date and time", from.getClass().getSimpleName()));
    }

    //
    // Date properties
    //
    @Override
    public Integer year(Object date) {
        if (date == null) {
            return null;
        }

        return ((TemporalAccessor) date).get(ChronoField.YEAR);
    }

    @Override
    public Integer month(Object date) {
        if (date == null) {
            return null;
        }

        return ((TemporalAccessor) date).get(ChronoField.MONTH_OF_YEAR);
    }

    @Override
    public Integer day(Object date) {
        if (date == null) {
            return null;
        }

        return ((TemporalAccessor) date).get(ChronoField.DAY_OF_MONTH);
    }

    @Override
    public Integer weekday(Object date) {
        if (date == null) {
            return null;
        }

        return ((TemporalAccessor) date).get(ChronoField.DAY_OF_WEEK);
    }

    //
    // Time properties
    //
    @Override
    public Integer hour(Object time) {
        if (time == null) {
            return null;
        }

        return ((TemporalAccessor) time).get(ChronoField.HOUR_OF_DAY);
    }

    @Override
    public Integer minute(Object time) {
        if (time == null) {
            return null;
        }

        return ((TemporalAccessor) time).get(ChronoField.MINUTE_OF_HOUR);
    }

    @Override
    public Integer second(Object time) {
        if (time == null) {
            return null;
        }

        return ((TemporalAccessor) time).get(ChronoField.SECOND_OF_MINUTE);
    }

    @Override
    public TemporalAmount timeOffset(Object time) {
        if (time == null) {
            return null;
        }

        if (((TemporalAccessor) time).isSupported(ChronoField.OFFSET_SECONDS)) {
            return Duration.ofSeconds(((TemporalAccessor) time).get(ChronoField.OFFSET_SECONDS));
        } else {
            return null;
        }
    }

    @Override
    public String timezone(Object time) {
        if (time == null) {
            return null;
        }

        ZoneId zoneId = ((TemporalAccessor) time).query(TemporalQueries.zoneId());
        if (zoneId != null) {
            // Normalization
            return TimeZone.getTimeZone(zoneId).getID();
        } else {
            return null;
        }
    }

    @Override
    public TemporalAccessor now() {
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
        return date(from);
    }

    @Override
    public TemporalAccessor toTime(Object from) {
        return time(from);
    }

    @Override
    public TemporalAccessor toDateTime(Object from) {
        return dateTime(from);
    }

    //
    // List functions
    //
    @Override
    public <T> T min(List<T> list) {
        return minMax(list, TemporalComparator.COMPARATOR, TemporalAmountComparator.COMPARATOR, x -> x > 0);
    }

    @Override
    public <T> T max(List<T> list) {
        return minMax(list, TemporalComparator.COMPARATOR, TemporalAmountComparator.COMPARATOR, x -> x < 0);
    }

    private <T> T minMax(List<T> list, TemporalComparator temporalComparator, TemporalAmountComparator durationComparator, Predicate<Integer> condition) {
        if (list == null || list.isEmpty()) {
            return null;
        }

        T result = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            T x = list.get(i);
            if (temporalComparator.isDate(result) && temporalComparator.isDate(x)) {
                if (condition.test(temporalComparator.compareTo((TemporalAccessor) result, (TemporalAccessor) x))) {
                    result = x;
                }
            } else if (temporalComparator.isTime(result) && temporalComparator.isTime(x)) {
                if (condition.test(temporalComparator.compareTo((TemporalAccessor) result, (TemporalAccessor) x))) {
                    result = x;
                }
            } else if (temporalComparator.isDateTime(result) && temporalComparator.isDateTime(x)) {
                if (condition.test(temporalComparator.compareTo((TemporalAccessor) result, (TemporalAccessor) x))) {
                    result = x;
                }
            } else if (durationComparator.isYearsAndMonthsDuration(result) && durationComparator.isYearsAndMonthsDuration(x)) {
                if (condition.test(durationComparator.compareTo((TemporalAmount) result, (TemporalAmount) x))) {
                    result = x;
                }
            } else if (durationComparator.isDaysAndTimeDuration(result) && durationComparator.isDaysAndTimeDuration(x)) {
                if (condition.test(durationComparator.compareTo((TemporalAmount) result, (TemporalAmount) x))) {
                    result = x;
                }
            } else {
                throw new DMNRuntimeException(String.format("Cannot compare '%s' and '%s'", result, x));
            }
        }
        return result;
    }
}
