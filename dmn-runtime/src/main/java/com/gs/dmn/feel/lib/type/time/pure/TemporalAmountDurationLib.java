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

import com.gs.dmn.feel.lib.type.time.DurationLib;
import com.gs.dmn.runtime.DMNRuntimeException;
import org.apache.commons.lang3.StringUtils;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemporalAmountDurationLib implements DurationLib<LocalDate, TemporalAmount> {
    private static final int HOURS_PER_DAY = 24;
    private static final int MINUTES_PER_HOUR = 60;
    private static final int SECONDS_PER_MINUTE = 60;
    private static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;
    private static final int SECONDS_PER_DAY = SECONDS_PER_HOUR * HOURS_PER_DAY;

    private static final Pattern FULL_DURATION_PATTERN =
            Pattern.compile("([-+]?)P(?:([-+]?[0-9]+)Y)?(?:([-+]?[0-9]+)M)?(?:([-+]?[0-9]+)D)?" +
                            "(T(?:([-+]?[0-9]+)H)?(?:([-+]?[0-9]+)M)?(?:([-+]?[0-9]+)(?:[.,]([0-9]{0,9}))?S)?)?",
                    Pattern.CASE_INSENSITIVE);

    private static TemporalAmount parse(CharSequence text) {
        if (text == null) {
            return null;
        }

        Matcher matcher = FULL_DURATION_PATTERN.matcher(text);
        if (matcher.matches()) {
            String tGroup = matcher.group(5);
            // check for letter T but no time sections
            if ("T".equals(tGroup)) {
                throw new DMNRuntimeException(String.format("Text '%s' cannot be parsed to a Duration", text));
            }

            // Extract e temporal amount parts
            int negate = ("-".equals(matcher.group(1)) ? -1 : 1);
            Period yearMonthPart = extractYearMonthPart(matcher, text);
            Duration dayTimePart = extractDayTimePart(matcher, text);

            if (yearMonthPart != null && dayTimePart == null) {
                return negate == -1 ? yearMonthPart.negated() : yearMonthPart;
            } else if (yearMonthPart == null && dayTimePart != null) {
                return negate == -1 ? dayTimePart.negated() : dayTimePart;
            }
        }
        throw new DMNRuntimeException(String.format("Text '%s' cannot be parsed to a Duration", text));
    }

    private static Duration extractDayTimePart(Matcher matcher, CharSequence text) {
        String dayMatch = matcher.group(4);
        String tGroup = matcher.group(5);
        // check for letter T but no time sections
        if ("T".equals(tGroup)) {
            throw new DMNRuntimeException(String.format("Text '%s' cannot be parsed to a Duration", text));
        }
        String hourMatch = matcher.group(6);
        String minuteMatch = matcher.group(7);
        String secondMatch = matcher.group(8);
        String fractionMatch = matcher.group(9);

        if (dayMatch != null || hourMatch != null || minuteMatch != null || secondMatch != null) {
            try {
                long daysAsSecs = parseNumber(text, dayMatch, SECONDS_PER_DAY, "days");
                long hoursAsSecs = parseNumber(text, hourMatch, SECONDS_PER_HOUR, "hours");
                long minsAsSecs = parseNumber(text, minuteMatch, SECONDS_PER_MINUTE, "minutes");
                long seconds = parseNumber(text, secondMatch, 1, "seconds");
                int nanos = parseFraction(text, fractionMatch, seconds < 0 ? -1 : 1);
                return create(daysAsSecs, hoursAsSecs, minsAsSecs, seconds, nanos);
            } catch (ArithmeticException ex) {
                throw new DMNRuntimeException(String.format("Text '%s' cannot be parsed to a Duration", text));
            }
        }
        return null;
    }

    private static Period extractYearMonthPart(Matcher matcher, CharSequence text) {
        String yearMatch = matcher.group(2);
        String monthMatch = matcher.group(3);

        Period period = null;
        if (yearMatch != null || monthMatch != null) {
            try {
                int years = parseNumber(yearMatch);
                int months = parseNumber(monthMatch);
                period = Period.of(years, months, 0);
            } catch (NumberFormatException ex) {
                throw new DMNRuntimeException(String.format("Text '%s' cannot be parsed to a Duration", text));
            }
        }
        return period;
    }

    private static int parseNumber(String str) {
        if (str == null) {
            return 0;
        }
        return Integer.parseInt(str);
    }

    private static long parseNumber(CharSequence text, String parsed, int multiplier, String errorText) {
        // regex limits to [-+]?[0-9]+
        if (parsed == null) {
            return 0;
        }
        try {
            long val = Long.parseLong(parsed);
            return Math.multiplyExact(val, (long) multiplier);
        } catch (NumberFormatException | ArithmeticException ex) {
            throw new DMNRuntimeException(String.format("Cannot extract '%s' from text '%s'", errorText, text));
        }
    }

    private static int parseFraction(CharSequence text, String parsed, int negate) {
        // regex limits to [0-9]{0,9}
        if (parsed == null || parsed.length() == 0) {
            return 0;
        }
        try {
            parsed = (parsed + "000000000").substring(0, 9);
            return Integer.parseInt(parsed) * negate;
        } catch (NumberFormatException | ArithmeticException ex) {
            throw new DMNRuntimeException(String.format("Text '%s' cannot be parsed to a Duration", text));
        }
    }

    private static Duration create(long daysAsSecs, long hoursAsSecs, long minsAsSecs, long secs, int nanos) {
        long seconds = Math.addExact(daysAsSecs, Math.addExact(hoursAsSecs, Math.addExact(minsAsSecs, secs)));
        return Duration.ofSeconds(seconds, nanos);
    }

    private final TemporalDateTimeLib dateTimeLib;

    public TemporalAmountDurationLib() {
        this.dateTimeLib = new TemporalDateTimeLib();
    }

    @Override
    public TemporalAmount duration(String from) {
        if (StringUtils.isBlank(from)) {
            return null;
        }

        return parse(from);
    }

    @Override
    public TemporalAmount yearsAndMonthsDuration(Object from, Object to) {
        if (from == null || to == null) {
            return null;
        }

        return Period.between(toDate(from), toDate(to)).withDays(0);
    }

    @Override
    public Long years(TemporalAmount duration) {
        return duration.get(ChronoUnit.YEARS);
    }

    @Override
    public Long months(TemporalAmount duration) {
        return duration.get(ChronoUnit.MONTHS);
    }

    @Override
    public Long days(TemporalAmount duration) {
       if (duration instanceof Period) {
           return duration.get(ChronoUnit.DAYS);
       } else if (duration instanceof Duration) {
           long seconds = ((Duration) duration).getSeconds();
           long minutes = seconds / 60;
           long hours = minutes / 60;
           return hours / 24;
       } else {
           throw new DMNRuntimeException(String.format("Cannot extract days from '%s'", duration));
       }
    }

    @Override
    public Long hours(TemporalAmount duration) {
        if (duration instanceof Period) {
            return duration.get(ChronoUnit.HOURS);
        } else if (duration instanceof Duration) {
            long seconds = ((Duration) duration).getSeconds();
            long minutes = seconds / 60;
            long hours = minutes / 60;
            return hours % 24;
        } else {
            throw new DMNRuntimeException(String.format("Cannot extract hours from '%s'", duration));
        }
    }

    @Override
    public Long minutes(TemporalAmount duration) {
        if (duration instanceof Period) {
            return duration.get(ChronoUnit.MINUTES);
        } else if (duration instanceof Duration) {
            long seconds = ((Duration) duration).getSeconds();
            long minutes = seconds / 60;
            return minutes % 60;
        } else {
            throw new DMNRuntimeException(String.format("Cannot extract minutes from '%s'", duration));
        }
    }

    @Override
    public Long seconds(TemporalAmount duration) {
        if (duration instanceof Period) {
            return duration.get(ChronoUnit.SECONDS);
        } else if (duration instanceof Duration) {
            long seconds = ((Duration) duration).getSeconds();
            return seconds % 60;
        } else {
            throw new DMNRuntimeException(String.format("Cannot extract seconds from '%s'", duration));
        }
    }

    @Override
    public TemporalAmount abs(TemporalAmount temporalAmount) {
        if (temporalAmount == null) {
            return null;
        }

        if (temporalAmount instanceof Period) {
            Period period = (Period) temporalAmount;
            return period.isNegative() ? period.negated() : period;
        } else if (temporalAmount instanceof Duration) {
            Duration duration = (Duration) temporalAmount;
            return duration.isNegative() ? duration.negated() : duration;
        } else {
            return null;
        }
    }

    private LocalDate toDate(Object object) {
        return this.dateTimeLib.toDate(object);
    }
}
