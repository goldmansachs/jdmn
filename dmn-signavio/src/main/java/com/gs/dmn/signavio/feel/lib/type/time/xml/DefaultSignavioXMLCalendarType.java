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
package com.gs.dmn.signavio.feel.lib.type.time.xml;

import com.gs.dmn.feel.lib.type.BaseType;
import org.slf4j.Logger;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import static javax.xml.datatype.DatatypeConstants.*;

public abstract class DefaultSignavioXMLCalendarType extends BaseType {
    protected final DatatypeFactory datatypeFactory;

    public DefaultSignavioXMLCalendarType(Logger logger, DatatypeFactory datatypeFactory) {
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
            int result = first.compare(second);
            return result == EQUAL;
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
            int result = first.compare(second);
            return result == LESSER;
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
            int result = first.compare(second);
            return result == GREATER;
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
            int result = first.compare(second);
            return result == LESSER || result == EQUAL;
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
            int result = first.compare(second);
            return result == GREATER || result == EQUAL;
        }
    }

    protected long getDurationInMilliSeconds(XMLGregorianCalendar first, XMLGregorianCalendar second) {
        return first.toGregorianCalendar().getTimeInMillis() - second.toGregorianCalendar().getTimeInMillis();
    }

}
