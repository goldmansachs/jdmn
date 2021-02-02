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

import com.gs.dmn.feel.lib.DefaultFEELLib;
import com.gs.dmn.feel.lib.type.BaseType;
import com.gs.dmn.feel.lib.type.RelationalComparator;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.namespace.QName;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;

public abstract class BaseDefaultDurationType extends BaseType {
    static final ThreadLocal<GregorianCalendar> GREGORIAN = ThreadLocal.withInitial(() -> new GregorianCalendar(
            1970,
            Calendar.JANUARY,
            1,
            0,
            0,
            0));

    public static final BigDecimal THOUSAND = BigDecimal.valueOf(1000);
    public static final BigDecimal SIXTY = BigDecimal.valueOf(60);
    public static final BigDecimal TWENTY_FOUR = BigDecimal.valueOf(24);
    public static final BigDecimal TWELVE = BigDecimal.valueOf(12);

    public static BigDecimal normalize(Duration duration) {
        if (isDuration(duration)) {
            return BigDecimal.valueOf(duration.getTimeInMillis(GREGORIAN.get()));
        } else if (isYearMonthDuration(duration)) {
            BigDecimal years = BigDecimal.valueOf(duration.getYears());
            BigDecimal months = BigDecimal.valueOf(duration.getMonths());
            BigDecimal totalMonths = years.multiply(TWELVE).add(months);
            if (duration.getSign() < 0) {
                totalMonths = totalMonths.negate();
            }
            return totalMonths;
        } else if (isDayTimeDuration(duration)) {
            return BigDecimal.valueOf(duration.getTimeInMillis(GREGORIAN.get()));
        } else {
            return BigDecimal.valueOf(duration.getTimeInMillis(GREGORIAN.get()));
        }
    }

    private static boolean isDuration(Duration duration) {
        return getXMLSchemaType(duration) == DatatypeConstants.DURATION;
    }

    public static boolean isYearMonthDuration(Duration duration) {
        return getXMLSchemaType(duration) == DatatypeConstants.DURATION_YEARMONTH;
    }

    public static boolean isDayTimeDuration(Duration duration) {
        return getXMLSchemaType(duration) == DatatypeConstants.DURATION_DAYTIME;
    }

    private static QName getXMLSchemaType(Duration duration) {
        if (duration == null) {
            return DatatypeConstants.DURATION;
        }

        boolean yearSet = duration.isSet(DatatypeConstants.YEARS);
        boolean monthSet = duration.isSet(DatatypeConstants.MONTHS);
        boolean daySet = duration.isSet(DatatypeConstants.DAYS);
        boolean hourSet = duration.isSet(DatatypeConstants.HOURS);
        boolean minuteSet = duration.isSet(DatatypeConstants.MINUTES);
        boolean secondSet = duration.isSet(DatatypeConstants.SECONDS);

        if ((yearSet || monthSet) && !(daySet || hourSet || minuteSet || secondSet)) {
            return DatatypeConstants.DURATION_YEARMONTH;
        } else if (!(yearSet || monthSet) && (daySet || hourSet || minuteSet || secondSet)) {
            return DatatypeConstants.DURATION_DAYTIME;
        } else {
            return DatatypeConstants.DURATION;
        }
    }

    protected final DatatypeFactory dataTypeFactory;
    private final RelationalComparator<Duration> comparator;

    @Deprecated
    protected BaseDefaultDurationType() {
        this(DefaultFEELLib.DATA_TYPE_FACTORY, new DefaultDurationComparator());
    }

    protected BaseDefaultDurationType(DatatypeFactory dataTypeFactory, RelationalComparator<Duration> comparator) {
        this.dataTypeFactory = dataTypeFactory;
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

        if (isYearMonthDuration(first) && isYearMonthDuration(second)) {
            long firstValue = monthsValue(first);
            long secondValue = monthsValue(second);
            return makeYearsMonthsDuration(firstValue + secondValue);
        } else if (isDayTimeDuration(first) && isDayTimeDuration(second)) {
            long firstValue = secondsValue(first);
            long secondValue = secondsValue(second);
            return makeDaysTimeDuration(firstValue + secondValue);
        } else {
            long months = monthsValue(first) + monthsValue(second);
            long seconds = secondsValue(first) + secondsValue(second);
            return makeDuration(months, seconds);
        }
    }

    public Duration durationSubtract(Duration first, Duration second) {
        if (first == null || second == null) {
            return null;
        }

        return durationAdd(first, second.negate());
    }

    public static Long monthsValue(Duration duration) {
        if (duration == null) {
            return null;
        }

        boolean isNegative = duration.getSign() < 0;
        long months = 12L * duration.getYears() + duration.getMonths();
        return isNegative ? - months : months;
    }

    public static Long secondsValue(Duration duration) {
        if (duration == null) {
            return null;
        }

        boolean isNegative = duration.getSign() < 0;
        long hours = 24L * duration.getDays() + duration.getHours();
        long minutes = 60L * hours + duration.getMinutes();
        long seconds = 60L * minutes + duration.getSeconds();
        return isNegative ? - seconds : seconds;
    }

    protected Duration makeYearsMonthsDuration(Number months) {
        long lMonths = months.longValue();
        if (lMonths < 0) {
            String literal = String.format("P%dM", -months.intValue());
            return this.dataTypeFactory.newDurationYearMonth(literal).negate();
        } else {
            String literal = String.format("P%dM", months.intValue());
            return this.dataTypeFactory.newDurationYearMonth(literal);
        }
    }

    protected Duration makeDaysTimeDuration(Number seconds) {
        long millis = seconds.longValue() * 1000L;
        if (millis < 0) {
            return this.dataTypeFactory.newDurationDayTime(-millis).negate();
        } else {
            return this.dataTypeFactory.newDurationDayTime(millis);
        }
    }

    protected Duration makeDuration(Number months, Number seconds) {
        long lMonths = months.longValue();
        long lSeconds = seconds.longValue();
        boolean isNegative = lMonths < 0;
        if (isNegative) {
            String literal = String.format("P%dMT%dS", -lMonths, -lSeconds);
            return this.dataTypeFactory.newDurationYearMonth(literal).negate();
        } else {
            String literal = String.format("P%dMT%dS", lMonths, lSeconds);
            return this.dataTypeFactory.newDuration(literal);
        }
    }
}
