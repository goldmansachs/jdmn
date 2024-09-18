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

import com.gs.dmn.runtime.DMNRuntimeException;
import org.apache.commons.lang3.StringUtils;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import java.time.Period;

public class XMLDurationFactory {
    public static final XMLDurationFactory INSTANCE = new XMLDurationFactory();

    private final DatatypeFactory datatypeFactory;

    private XMLDurationFactory() {
        try {
            datatypeFactory = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            throw new DMNRuntimeException("Cannot build DatatypeFactory", e);
        }
    }

    public Duration parse(String from) {
        if (StringUtils.isBlank(from)) {
            return null;
        }

        return this.datatypeFactory.newDuration(from);
    }

    public Duration yearMonthFrom(Period period) {
        int years = period.getYears();
        int months = period.getMonths();
        if (period.isNegative()) {
            years = - years;
            months = - months;
        }
        return this.datatypeFactory.newDurationYearMonth(!period.isNegative(), years, months);
    }

    public Duration yearMonthFromValue(long totalMonths) {
        if (totalMonths < 0) {
            String literal = String.format("P%dM", -totalMonths);
            return this.datatypeFactory.newDurationYearMonth(literal).negate();
        } else {
            String literal = String.format("P%dM", totalMonths);
            return this.datatypeFactory.newDurationYearMonth(literal);
        }
    }

    public Duration yearMonthWithYears(int years) {
        boolean isNegative = years < 0;
        return this.datatypeFactory.newDurationYearMonth(!isNegative, isNegative ? - years : years, 0);
    }

    public Duration yearMonthWithMonths(int months) {
        boolean isNegative = months < 0;
        return this.datatypeFactory.newDurationYearMonth(!isNegative, 0, isNegative ? - months : months);
    }

    public Duration dayTimeFromValue(long seconds) {
        boolean isNegative = seconds < 0;
        return this.datatypeFactory.newDurationDayTime(!isNegative, 0, 0, 0, (int) (isNegative ? -seconds : seconds));
    }

    public Duration dayTimeWithDays(int days) {
        boolean isNegative = days < 0;
        return this.datatypeFactory.newDurationDayTime(!isNegative, isNegative ? -days : days, 0, 0, 0);
    }
}
