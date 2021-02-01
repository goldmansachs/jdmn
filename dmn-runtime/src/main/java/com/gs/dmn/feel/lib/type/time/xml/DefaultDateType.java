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

import com.gs.dmn.feel.lib.type.BaseType;
import com.gs.dmn.feel.lib.type.BooleanType;
import com.gs.dmn.feel.lib.type.DateType;
import com.gs.dmn.feel.lib.type.logic.DefaultBooleanType;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

public class DefaultDateType extends BaseType implements DateType<XMLGregorianCalendar, Duration> {
    private final DatatypeFactory datatypeFactory;
    private final DefaultXMLCalendarComparator comparator;
    private final BooleanType booleanType;

    public DefaultDateType(DatatypeFactory datatypeFactory) {
        this(datatypeFactory, new DefaultXMLCalendarComparator());
    }

    public DefaultDateType(DatatypeFactory datatypeFactory, DefaultXMLCalendarComparator comparator) {
        this.datatypeFactory = datatypeFactory;
        this.comparator = comparator;
        this.booleanType = new DefaultBooleanType();
    }

    //
    // Date operators
    //

    @Override
    public Boolean dateIs(XMLGregorianCalendar first, XMLGregorianCalendar second) {
        if (first == null || second == null) {
            return first == second;
        }

        return ((FEELXMLGregorianCalendar) first).same(second);
    }

    @Override
    public Boolean dateEqual(XMLGregorianCalendar first, XMLGregorianCalendar second) {
        return this.comparator.equalTo(first, second);
    }

    @Override
    public Boolean dateNotEqual(XMLGregorianCalendar first, XMLGregorianCalendar second) {
        return this.booleanType.booleanNot(dateEqual(first, second));
    }

    @Override
    public Boolean dateLessThan(XMLGregorianCalendar first, XMLGregorianCalendar second) {
        return this.comparator.lessThan(first, second);
    }

    @Override
    public Boolean dateGreaterThan(XMLGregorianCalendar first, XMLGregorianCalendar second) {
        return this.comparator.greaterThan(first, second);
    }

    @Override
    public Boolean dateLessEqualThan(XMLGregorianCalendar first, XMLGregorianCalendar second) {
        return this.comparator.lessEqualThan(first, second);
    }

    @Override
    public Boolean dateGreaterEqualThan(XMLGregorianCalendar first, XMLGregorianCalendar second) {
        return this.comparator.greaterEqualThan(first, second);
    }

    @Override
    public Duration dateSubtract(XMLGregorianCalendar first, XMLGregorianCalendar second) {
        if (first == null || second == null) {
            return null;
        }

        return this.datatypeFactory.newDuration(this.comparator.getDurationInMilliSeconds(first, second));
    }

    @Override
    public XMLGregorianCalendar dateAddDuration(XMLGregorianCalendar date, Duration duration) {
        if (date == null || duration == null) {
            return null;
        }

        XMLGregorianCalendar clone = (XMLGregorianCalendar) date.clone();
        clone.add(duration);
        return clone;
    }

    @Override
    public XMLGregorianCalendar dateSubtractDuration(XMLGregorianCalendar date, Duration duration) {
        if (date == null || duration == null) {
            return null;
        }

        XMLGregorianCalendar clone = (XMLGregorianCalendar) date.clone();
        clone.add(duration.negate());
        return clone;
    }
}
