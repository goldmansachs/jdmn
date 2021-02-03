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
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.GregorianCalendar;

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

    public Duration of(String from) {
        if (StringUtils.isBlank(from)) {
            return null;
        }

        return this.datatypeFactory.newDuration(from);
    }

    public Duration of(Number months, Number seconds) {
        long lMonths = months.longValue();
        long lSeconds = seconds.longValue();
        boolean isNegative = lMonths < 0;
        if (isNegative) {
            String literal = String.format("-P%dMT%dS", -lMonths, -lSeconds);
            return this.datatypeFactory.newDuration(literal).negate();
        } else {
            String literal = String.format("P%dMT%dS", lMonths, lSeconds);
            return this.datatypeFactory.newDuration(literal);
        }
    }

    public Duration yearMonthOf(boolean isPositive, int years, int months) {
        return this.datatypeFactory.newDurationYearMonth(isPositive, years, months);
    }

    public Duration yearMonthOf(long months) {
        if (months < 0) {
            String literal = String.format("P%dM", -months);
            return this.datatypeFactory.newDurationYearMonth(literal).negate();
        } else {
            String literal = String.format("P%dM", months);
            return this.datatypeFactory.newDurationYearMonth(literal);
        }
    }

    public Duration dayTimeOfDays(int days) {
        boolean isNegative = days < 0;
        return this.datatypeFactory.newDurationDayTime(!isNegative, isNegative ? -days : days, 0, 0, 0);
    }

    public Duration dayTimeOfDays(boolean isPositive, int days) {
        return this.datatypeFactory.newDurationDayTime(isPositive, days, 0, 0, 0);
    }

    public Duration dayTimeOfMillis(long durationInMilliSeconds) {
        return this.datatypeFactory.newDurationDayTime(durationInMilliSeconds);
    }

    public Duration dayTimeOfSeconds(long durationInSeconds) {
        return this.datatypeFactory.newDuration(durationInSeconds * 1000);
    }

    public Duration offset(int seconds) {
        boolean isNegative = seconds < 0;
        return this.datatypeFactory.newDurationDayTime(!isNegative, 0, 0, 0, isNegative ? -seconds : seconds);
    }

    public Duration dayTimeOf(Number seconds) {
        long millis = seconds.longValue() * 1000L;
        if (millis < 0) {
            return this.datatypeFactory.newDurationDayTime(-millis).negate();
        } else {
            return this.datatypeFactory.newDurationDayTime(millis);
        }
    }

    public XMLGregorianCalendar newXMLGregorianCalendar(GregorianCalendar gc) {
        return this.datatypeFactory.newXMLGregorianCalendar(gc);
    }
}
