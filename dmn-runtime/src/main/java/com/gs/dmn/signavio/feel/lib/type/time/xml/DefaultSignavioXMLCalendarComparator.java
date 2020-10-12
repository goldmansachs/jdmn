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

import com.gs.dmn.feel.lib.type.time.xml.DefaultXMLCalendarComparator;
import org.slf4j.Logger;

import javax.xml.datatype.XMLGregorianCalendar;

import static javax.xml.datatype.DatatypeConstants.*;

public class DefaultSignavioXMLCalendarComparator extends DefaultXMLCalendarComparator {
    public DefaultSignavioXMLCalendarComparator(Logger logger) {
        super(logger);
    }

    @Override
    public Boolean equal(XMLGregorianCalendar first, XMLGregorianCalendar second) {
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

    @Override
    public Boolean lessThan(XMLGregorianCalendar first, XMLGregorianCalendar second) {
        if (first == null && second == null) {
            return null;
        } else if (first == null) {
            return null;
        } else if (second == null) {
            return null;
        } else {
            int result = first.compare(second);
            return result == LESSER;
        }
    }

    @Override
    public Boolean greaterThan(XMLGregorianCalendar first, XMLGregorianCalendar second) {
        if (first == null && second == null) {
            return null;
        } else if (first == null) {
            return null;
        } else if (second == null) {
            return null;
        } else {
            int result = first.compare(second);
            return result == GREATER;
        }
    }

    @Override
    public Boolean lessEqualThan(XMLGregorianCalendar first, XMLGregorianCalendar second) {
        if (first == null && second == null) {
            return null;
        } else if (first == null) {
            return null;
        } else if (second == null) {
            return null;
        } else {
            int result = first.compare(second);
            return result == LESSER || result == EQUAL;
        }
    }

    @Override
    public Boolean greaterEqualThan(XMLGregorianCalendar first, XMLGregorianCalendar second) {
        if (first == null && second == null) {
            return null;
        } else if (first == null) {
            return null;
        } else if (second == null) {
            return null;
        } else {
            int result = first.compare(second);
            return result == GREATER || result == EQUAL;
        }
    }

    @Override
    public long getDurationInMilliSeconds(XMLGregorianCalendar first, XMLGregorianCalendar second) {
        return first.toGregorianCalendar().getTimeInMillis() - second.toGregorianCalendar().getTimeInMillis();
    }

}
