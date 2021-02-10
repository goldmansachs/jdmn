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

import com.gs.dmn.feel.lib.type.RelationalComparator;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.Duration;
import java.math.BigDecimal;

public abstract class BaseDefaultDurationType extends XMLCalendarType {
    public static BigDecimal normalize(Duration duration) {
        if (isDuration(duration)) {
            return BigDecimal.valueOf(duration.getTimeInMillis(GREGORIAN.get()));
        } else if (isYearMonthDuration(duration)) {
            long years = duration.getYears();
            long months = duration.getMonths();
            long totalMonths = years * 12L + months;
            if (duration.getSign() < 0) {
                totalMonths = -totalMonths;
            }
            return BigDecimal.valueOf(totalMonths);
        } else if (isDayTimeDuration(duration)) {
            return BigDecimal.valueOf(duration.getTimeInMillis(GREGORIAN.get()));
        } else {
            return BigDecimal.valueOf(duration.getTimeInMillis(GREGORIAN.get()));
        }
    }

    private static boolean isDuration(Duration duration) {
        return getXMLSchemaType(duration) == DatatypeConstants.DURATION;
    }

    private static boolean isYearMonthDuration(Duration duration) {
        return getXMLSchemaType(duration) == DatatypeConstants.DURATION_YEARMONTH;
    }

    private static boolean isDayTimeDuration(Duration duration) {
        return getXMLSchemaType(duration) == DatatypeConstants.DURATION_DAYTIME;
    }

    private final RelationalComparator<Duration> comparator;

    protected BaseDefaultDurationType(RelationalComparator<Duration> comparator) {
        this.comparator = comparator;
    }

    //
    // Duration operators
    //
    public Boolean durationEqual(Duration first, Duration second) {
        return this.comparator.equalTo(first, second);
    }

    public Boolean durationNotEqual(Duration first, Duration second) {
        return this.comparator.notEqualTo(first, second);
    }

    public Boolean durationLessThan(Duration first, Duration second) {
        return this.comparator.lessThan(first, second);
    }

    public Boolean durationGreaterThan(Duration first, Duration second) {
        return this.comparator.greaterThan(first, second);
    }

    public Boolean durationLessEqualThan(Duration first, Duration second) {
        return this.comparator.lessEqualThan(first, second);
    }

    public Boolean durationGreaterEqualThan(Duration first, Duration second) {
        return this.comparator.greaterEqualThan(first, second);
    }

    public Duration durationAdd(Duration first, Duration second) {
        if (first == null || second == null) {
            return null;
        }

        if (isYearsAndMonthsDuration(first) && isYearsAndMonthsDuration(second)) {
            long totalMonths = monthsValue(first) + (long) monthsValue(second);
            return XMLDurationFactory.INSTANCE.yearMonthFromValue(totalMonths);
        } else if (isDaysAndTimeDuration(first) && isDaysAndTimeDuration(second)) {
            long seconds = secondsValue(first) + (long) secondsValue(second);
            return XMLDurationFactory.INSTANCE.dayTimeFromValue(seconds);
        } else {
            long months = monthsValue(first) + monthsValue(second);
            long seconds = secondsValue(first) + secondsValue(second);
            return XMLDurationFactory.INSTANCE.fromValue(months, seconds);
        }
    }

    public Duration durationSubtract(Duration first, Duration second) {
        if (first == null || second == null) {
            return null;
        }

        return durationAdd(first, second.negate());
    }
}
