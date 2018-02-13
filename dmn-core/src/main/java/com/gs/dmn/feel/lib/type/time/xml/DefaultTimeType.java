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
package com.gs.dmn.feel.lib.type.time.xml;

import com.gs.dmn.feel.lib.type.BooleanType;
import com.gs.dmn.feel.lib.type.TimeType;
import com.gs.dmn.feel.lib.type.logic.DefaultBooleanType;
import org.slf4j.Logger;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

public class DefaultTimeType extends DefaultXMLCalendarType implements TimeType<XMLGregorianCalendar, Duration> {
    private final BooleanType booleanType;

    public DefaultTimeType(Logger logger, DatatypeFactory datatypeFactory) {
        super(logger, datatypeFactory);
        this.booleanType = new DefaultBooleanType(logger);
    }

    //
    // Time operators
    //
    @Override
    public Boolean timeEqual(XMLGregorianCalendar first, XMLGregorianCalendar second) {
        return xmlCalendarEqual(first, second);
    }

    @Override
    public Boolean timeNotEqual(XMLGregorianCalendar first, XMLGregorianCalendar second) {
        return booleanType.booleanNot(timeEqual(first, second));
    }

    @Override
    public Boolean timeLessThan(XMLGregorianCalendar first, XMLGregorianCalendar second) {
        return xmlCalendarLessThan(first, second);
    }

    @Override
    public Boolean timeGreaterThan(XMLGregorianCalendar first, XMLGregorianCalendar second) {
        return xmlCalendarGreaterThan(first, second);
    }

    @Override
    public Boolean timeLessEqualThan(XMLGregorianCalendar first, XMLGregorianCalendar second) {
        return xmlCalendarLessEqualThan(first, second);
    }

    @Override
    public Boolean timeGreaterEqualThan(XMLGregorianCalendar first, XMLGregorianCalendar second) {
        return xmlCalendarGreaterEqualThan(first, second);
    }

    @Override
    public Duration timeSubtract(XMLGregorianCalendar first, XMLGregorianCalendar second) {
        if (first == null || second == null) {
            return null;
        }

        try {
            return datatypeFactory.newDuration(getDurationInMilliSeconds(first, second));
        } catch (Throwable e) {
            String message = String.format("timeSubtract(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public XMLGregorianCalendar timeAddDuration(XMLGregorianCalendar time, Duration duration) {
        if (time == null || duration == null) {
            return null;
        }

        try {
            XMLGregorianCalendar clone = (XMLGregorianCalendar) time.clone();
            clone.add(duration);
            return clone;
        } catch (Throwable e) {
            String message = String.format("timeAdd(%s, %s)", time, duration);
            logError(message, e);
            return null;
        }
    }

    @Override
    public XMLGregorianCalendar timeSubtractDuration(XMLGregorianCalendar time, Duration duration) {
        if (time == null || duration == null) {
            return null;
        }

        try {
            XMLGregorianCalendar clone = (XMLGregorianCalendar) time.clone();
            clone.add(duration.negate());
            return clone;
        } catch (Throwable e) {
            String message = String.format("timeSubtract(%s, %s)", time, duration);
            logError(message, e);
            return null;
        }
    }
}
