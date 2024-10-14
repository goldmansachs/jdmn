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

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;

public class TemporalAmountDurationLib implements DurationLib<LocalDate, TemporalAmount> {
    private static TemporalAmount parse(String text) {
        if (text == null) {
            return null;
        }

        if (text.indexOf("-") > 0) {
            throw new DMNRuntimeException(String.format("Negative values for units are not allowed in duration '%s'", text));
        }
        try {
            // try to parse as days/hours/minute/seconds
            return Duration.parse(text);
        } catch (DateTimeParseException e1) {
            // try to parse as years/months
            try {
                if (!text.contains("D")) {
                    return Period.parse(text).normalized();
                }
            } catch (DateTimeParseException e2) {
            }
        }
        throw new DMNRuntimeException(String.format("Cannot parse duration '%s'", text));
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

        return Period.between(toDate(from), toDate(to)).withDays(0).normalized();
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
            // Remove ms fraction
            long seconds = ((Duration) duration).toMillis() / 1000;
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
            return (period.isNegative() ? period.negated() : period).normalized();
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
