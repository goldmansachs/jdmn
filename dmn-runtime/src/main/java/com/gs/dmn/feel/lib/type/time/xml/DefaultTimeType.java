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

import com.gs.dmn.feel.lib.type.bool.BooleanType;
import com.gs.dmn.feel.lib.type.bool.DefaultBooleanType;
import com.gs.dmn.feel.lib.type.time.TimeType;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

public class DefaultTimeType extends XMLCalendarType implements TimeType<XMLGregorianCalendar, Duration> {
    public static boolean hasTimezone(XMLGregorianCalendar calendar) {
        return calendar.getTimezone() != DatatypeConstants.FIELD_UNDEFINED;
    }

    private final BooleanType booleanType;
    protected final DefaultXMLCalendarComparator comparator;

    public DefaultTimeType() {
        this(DefaultXMLCalendarComparator.COMPARATOR);
    }

    public DefaultTimeType(DefaultXMLCalendarComparator comparator) {
        this.comparator = comparator;
        this.booleanType = new DefaultBooleanType();
    }

    @Override
    public Boolean timeIs(XMLGregorianCalendar first, XMLGregorianCalendar second) {
        if (first == null || second == null) {
            return first == second;
        }

        return ((FEELXMLGregorianCalendar) first).same(second);
    }

    //
    // Time operators
    //
    @Override
    public Boolean timeEqual(XMLGregorianCalendar first, XMLGregorianCalendar second) {
        return this.comparator.equalTo(first, second);
    }

    @Override
    public Boolean timeNotEqual(XMLGregorianCalendar first, XMLGregorianCalendar second) {
        return this.booleanType.booleanNot(timeEqual(first, second));
    }

    @Override
    public Boolean timeLessThan(XMLGregorianCalendar first, XMLGregorianCalendar second) {
        return this.comparator.lessThan(first, second);
    }

    @Override
    public Boolean timeGreaterThan(XMLGregorianCalendar first, XMLGregorianCalendar second) {
        return this.comparator.greaterThan(first, second);
    }

    @Override
    public Boolean timeLessEqualThan(XMLGregorianCalendar first, XMLGregorianCalendar second) {
        return this.comparator.lessEqualThan(first, second);
    }

    @Override
    public Boolean timeGreaterEqualThan(XMLGregorianCalendar first, XMLGregorianCalendar second) {
        return this.comparator.greaterEqualThan(first, second);
    }

    @Override
    public Duration timeSubtract(XMLGregorianCalendar first, XMLGregorianCalendar second) {
        if (first == null || second == null) {
            return null;
        }

        long durationInSeconds = getDurationInSeconds(first, second);
        return XMLDurationFactory.INSTANCE.dayTimeFromValue(durationInSeconds);
    }

    @Override
    public XMLGregorianCalendar timeAddDuration(XMLGregorianCalendar time, Duration duration) {
        if (time == null || duration == null) {
            return null;
        }

        XMLGregorianCalendar clone = (XMLGregorianCalendar) time.clone();
        clone.add(duration);
        return clone;
    }

    @Override
    public XMLGregorianCalendar timeSubtractDuration(XMLGregorianCalendar time, Duration duration) {
        if (time == null || duration == null) {
            return null;
        }

        return timeAddDuration(time, duration.negate());
    }
}
