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
package com.gs.dmn.feel.lib.type.time.xml;

import com.gs.dmn.feel.lib.type.time.DurationLib;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.Period;

public class DefaultDurationLib implements DurationLib<XMLGregorianCalendar, Duration> {

    public DefaultDurationLib() {
    }

    @Override
    public Duration duration(String from) {
        return XMLDurationFactory.INSTANCE.parse(from);
    }

    @Override
    public Duration yearsAndMonthsDuration(XMLGregorianCalendar from, XMLGregorianCalendar to) {
        if (from == null || to == null) {
            return null;
        }

        LocalDate fromLocalDate = LocalDate.of(from.getYear(), from.getMonth(), from.getDay());
        LocalDate toLocalDate = LocalDate.of(to.getYear(), to.getMonth(), to.getDay());
        Period period = Period.between(fromLocalDate, toLocalDate);
        return XMLDurationFactory.INSTANCE.yearMonthFrom(period);
    }

    @Override
    public Long years(Duration duration) {
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
    public Long months(Duration duration) {
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
    public Long days(Duration duration) {
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
    public Long hours(Duration duration) {
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
    public Long minutes(Duration duration) {
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
    public Long seconds(Duration duration) {
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
    public Duration abs(Duration duration) {
        if (duration == null) {
            return null;
        }

        return duration.getSign() == -1 ? duration.negate() : duration;
    }

}
