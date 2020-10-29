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
import com.gs.dmn.runtime.DMNRuntimeException;
import org.slf4j.Logger;

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

    public static BigDecimal normalize(Duration duration) {
        if (isDuration(duration)) {
            return BigDecimal.valueOf(duration.getTimeInMillis(GREGORIAN.get()));
        } else if (isYearMonthDuration(duration)) {
            BigDecimal years = BigDecimal.valueOf(duration.getYears());
            BigDecimal months = BigDecimal.valueOf(duration.getMonths());
            BigDecimal totalMonths = years.multiply(BigDecimal.valueOf(12)).add(months);
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

    private static boolean isYearMonthDuration(Duration duration) {
        return getXMLSchemaType(duration) == DatatypeConstants.DURATION_YEARMONTH;
    }

    private static boolean isDayTimeDuration(Duration duration) {
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
    protected BaseDefaultDurationType(Logger logger) {
        this(logger, DefaultFEELLib.DATA_TYPE_FACTORY, new DefaultDurationComparator());
    }

    protected BaseDefaultDurationType(Logger logger, DatatypeFactory dataTypeFactory, RelationalComparator<Duration> comparator) {
        super(logger);
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

        try {
            return first.add(second);
        } catch (Exception e) {
            String message = String.format("durationAdd(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    public Duration durationSubtract(Duration first, Duration second) {
        if (first == null || second == null) {
            return null;
        }

        try {
            return first.subtract(second);
        } catch (Exception e) {
            String message = String.format("durationSubtract(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    protected Duration durationMultiply(Duration first, Number second) {
        if (first == null || second == null) {
            return null;
        }

        try {
            return first.multiply(second.intValue());
        } catch (Exception e) {
            String message = String.format("durationMultiply(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    protected Duration durationDivide(Duration first, Number second) {
        if (first == null || second == null) {
            return null;
        }

        try {
            if (isYearMonthDuration(first)) {
                long months = (first.getYears() * 12 + first.getMonths()) / second.intValue();
                return this.dataTypeFactory.newDurationYearMonth(String.format("P%dM", months));
            } else if (isDayTimeDuration(first)) {
                long hours = 24L * first.getDays() + first.getHours();
                long minutes = 60L * hours + first.getMinutes();
                long seconds = 60L * minutes + first.getSeconds();
                seconds = seconds / second.intValue();
                return this.dataTypeFactory.newDurationDayTime(seconds * 1000L);
            } else {
                throw new DMNRuntimeException(String.format("Cannot divide '%s' by '%s'", first, second));
            }
        } catch (Exception e) {
            String message = String.format("durationDivide(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }
}
