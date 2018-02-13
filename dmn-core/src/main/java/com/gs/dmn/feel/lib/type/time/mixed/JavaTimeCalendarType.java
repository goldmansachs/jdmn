/**
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

import com.gs.dmn.feel.lib.type.time.JavaTimeType;
import org.slf4j.Logger;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import java.time.Period;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAmount;


public abstract class JavaTimeCalendarType extends JavaTimeType {
    protected final DatatypeFactory datatypeFactory;

    public JavaTimeCalendarType(Logger logger, DatatypeFactory datatypeFactory) {
        super(logger);
        this.datatypeFactory = datatypeFactory;
    }

    protected TemporalAmount toTemporalPeriod(Duration duration) {
        Period period = Period.of(duration.getYears(), duration.getMonths(), duration.getDays());
        return duration.getSign() == -1 ? period.negated() : period;
    }

    protected TemporalAmount toTemporalDuration(Duration duration) {
        int days = normalize(duration.getDays());
        int hours = normalize(duration.getHours());
        int minutes = normalize(duration.getMinutes());
        int seconds = normalize(duration.getSeconds());

        java.time.Duration timeDuration = java.time.Duration.ofDays(days).plusHours(hours).plusMinutes(minutes).plusSeconds(seconds);
        return duration.getSign() == -1 ? timeDuration.negated() : timeDuration;
    }

    private int normalize(int number) {
        return number == DatatypeConstants.FIELD_UNDEFINED ? 0 : number;
    }

    protected long getDurationInMilliSeconds(ZonedDateTime first, ZonedDateTime second) {
        return first.toInstant().toEpochMilli() - second.toInstant().toEpochMilli();
    }
}
