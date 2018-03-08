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

import com.gs.dmn.feel.lib.type.BaseType;
import org.slf4j.Logger;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

public abstract class DefaultXMLCalendarType extends BaseType {
    protected final DatatypeFactory datatypeFactory;

    public DefaultXMLCalendarType(Logger logger, DatatypeFactory datatypeFactory) {
        super(logger);
        this.datatypeFactory = datatypeFactory;
    }

    protected Boolean xmlCalendarEqual(XMLGregorianCalendar first, XMLGregorianCalendar second) {
        if (first == null && second == null) {
            return true;
        } else if (first == null) {
            return false;
        } else if (second == null) {
            return false;
        } else {
            int result = compare(first, second);
            return result == 0;
        }
    }

    protected Boolean xmlCalendarLessThan(XMLGregorianCalendar first, XMLGregorianCalendar second) {
        if (first == null && second == null) {
            return false;
        } else if (first == null) {
            return null;
        } else if (second == null) {
            return null;
        } else {
            int result = compare(first, second);
            return result < 0;
        }
    }

    protected Boolean xmlCalendarGreaterThan(XMLGregorianCalendar first, XMLGregorianCalendar second) {
        if (first == null && second == null) {
            return false;
        } else if (first == null) {
            return null;
        } else if (second == null) {
            return null;
        } else {
            int result = compare(first, second);
            return result > 0;
        }
    }

    protected Boolean xmlCalendarLessEqualThan(XMLGregorianCalendar first, XMLGregorianCalendar second) {
        if (first == null && second == null) {
            return true;
        } else if (first == null) {
            return null;
        } else if (second == null) {
            return null;
        } else {
            int result = compare(first, second);
            return result <= 0;
        }
    }

    protected Boolean xmlCalendarGreaterEqualThan(XMLGregorianCalendar first, XMLGregorianCalendar second) {
        if (first == null && second == null) {
            return true;
        } else if (first == null) {
            return null;
        } else if (second == null) {
            return null;
        } else {
            int result = compare(first, second);
            return result >= 0;
        }
    }

    private int compare(XMLGregorianCalendar first, XMLGregorianCalendar second) {
        XMLGregorianCalendar normal1 = normalize(first);
        XMLGregorianCalendar normal2 = normalize(second);
        return normal1.compare(normal2);
    }

    private XMLGregorianCalendar normalize(XMLGregorianCalendar first) {
        XMLGregorianCalendar normal1 = (XMLGregorianCalendar) first.clone();
        if (first.getTimezone() == DatatypeConstants.FIELD_UNDEFINED) {
            normal1 = normalizeToTimezone(first, 0);
        }
        return normal1.normalize();
    }

    protected long getDurationInMilliSeconds(XMLGregorianCalendar first, XMLGregorianCalendar second) {
        return first.toGregorianCalendar().getTimeInMillis() - second.toGregorianCalendar().getTimeInMillis();
    }

    private XMLGregorianCalendar normalizeToTimezone(XMLGregorianCalendar first, int timezone) {
        int minutes = timezone;
        XMLGregorianCalendar result = (XMLGregorianCalendar) first.clone();

        // normalizing to UTC time negates the timezone offset before
        // addition.
        minutes = -minutes;
        Duration d = datatypeFactory.newDuration(minutes >= 0, // isPositive
                0, //years
                0, //months
                0, //days
                0, //hours
                minutes < 0 ? -minutes : minutes, // absolute
                0  //seconds
        );
        result.add(d);

        // set to zulu UTC time.
        result.setTimezone(0);
        return result;
    }

}
