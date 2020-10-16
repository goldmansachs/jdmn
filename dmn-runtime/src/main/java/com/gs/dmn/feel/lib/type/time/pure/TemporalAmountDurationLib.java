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
import org.apache.commons.lang3.StringUtils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;

public class TemporalAmountDurationLib implements DurationLib<LocalDate, TemporalAmount> {
    @Override
   public TemporalAmount duration(String from) {
        if (StringUtils.isBlank(from)) {
            return null;
        }

        TemporalAmount ta;
        try {
            ta = Duration.parse(from);
            return ta;
        } catch (Exception e) {
        }
        ta = Period.parse(from);
        return ta;
    }

    @Override
    public TemporalAmount yearsAndMonthsDuration(LocalDate from, LocalDate to) {
        if (from == null || to == null) {
            return null;
        }

        return Period.between(from, to).withDays(0);
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
           long days = hours / 24;
           return days;
       } else {
           throw new IllegalArgumentException(String.format("Cannot extract days from '%s'", duration));
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
            throw new IllegalArgumentException(String.format("Cannot extract hours from '%s'", duration));
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
            throw new IllegalArgumentException(String.format("Cannot extract minutes from '%s'", duration));
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
            throw new IllegalArgumentException(String.format("Cannot extract seconds from '%s'", duration));
        }
    }
}
