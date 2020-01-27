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

import org.apache.commons.lang3.StringUtils;

import java.time.Duration;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;

public class TemporalAmountDurationLib {
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

    public TemporalAmount yearsAndMonthsDuration(Temporal from, Temporal to) {
        if (from == null || to == null) {
            return null;
        }

        return Duration.between(from, to);
    }

    public Long years(TemporalAmount duration) {
        return duration.get(ChronoUnit.YEARS);
    }

    public Long months(TemporalAmount duration) {
        return duration.get(ChronoUnit.MONTHS);
    }

    public Long days(TemporalAmount duration) {
        return duration.get(ChronoUnit.DAYS);
    }

    public Long hours(TemporalAmount duration) {
        return duration.get(ChronoUnit.HOURS);
    }

    public Long minutes(TemporalAmount duration) {
        return duration.get(ChronoUnit.MINUTES);
    }

    public Long seconds(TemporalAmount duration) {
        return duration.get(ChronoUnit.SECONDS);
    }
}
