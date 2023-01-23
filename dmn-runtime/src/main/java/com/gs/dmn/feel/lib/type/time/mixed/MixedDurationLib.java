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

import com.gs.dmn.feel.lib.type.time.DurationLib;
import com.gs.dmn.feel.lib.type.time.xml.XMLCalendarType;
import com.gs.dmn.feel.lib.type.time.xml.XMLDurationFactory;

import javax.xml.datatype.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZonedDateTime;

public class MixedDurationLib implements DurationLib<LocalDate, Duration> {
    private final MixedDateTimeLib dateTimeLib;

    public MixedDurationLib() {
        this.dateTimeLib = new MixedDateTimeLib();
    }

    @Override
    public javax.xml.datatype.Duration duration(String from) {
        return XMLDurationFactory.INSTANCE.parse(from);
    }

    @Override
    public javax.xml.datatype.Duration yearsAndMonthsDuration(Object from, Object to) {
        if (from == null || to == null) {
            return null;
        }

        Period period = Period.between(toDate(from), toDate(to));
        return XMLDurationFactory.INSTANCE.yearMonthFrom(period);
    }

    @Override
    public Long years(javax.xml.datatype.Duration duration) {
        if (duration == null) {
            return null;
        }

        if (XMLCalendarType.isYearMonthDuration(duration)) {
            return (long) duration.getYears();
        } else {
            return null;
        }
    }

    @Override
    public Long months(javax.xml.datatype.Duration duration) {
        if (duration == null) {
            return null;
        }

        if (XMLCalendarType.isYearMonthDuration(duration)) {
            return (long) duration.getMonths();
        } else {
            return null;
        }
    }

    @Override
    public Long days(javax.xml.datatype.Duration duration) {
        if (duration == null) {
            return null;
        }

        if (XMLCalendarType.isDayTimeDuration(duration)) {
            return (long) duration.getDays();
        } else {
            return null;
        }
    }

    @Override
    public Long hours(javax.xml.datatype.Duration duration) {
        if (duration == null) {
            return null;
        }

        if (XMLCalendarType.isDayTimeDuration(duration)) {
            return (long) duration.getHours();
        } else {
            return null;
        }
    }

    @Override
    public Long minutes(javax.xml.datatype.Duration duration) {
        if (duration == null) {
            return null;
        }

        if (XMLCalendarType.isDayTimeDuration(duration)) {
            return (long) duration.getMinutes();
        } else {
            return null;
        }
    }

    @Override
    public Long seconds(javax.xml.datatype.Duration duration) {
        if (duration == null) {
            return null;
        }

        if (XMLCalendarType.isDayTimeDuration(duration)) {
            return (long) duration.getSeconds();
        } else {
            return null;
        }
    }

    @Override
    public javax.xml.datatype.Duration abs(javax.xml.datatype.Duration duration) {
        if (duration == null) {
            return null;
        }

        return duration.getSign() == -1 ? duration.negate() : duration;
    }

    private LocalDate toDate(Object object) {
        return this.dateTimeLib.toDate(object);
    }
}
