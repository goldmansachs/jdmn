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

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

public abstract class XMLTimeType extends BaseType {
    protected static QName getXMLSchemaType(Duration duration) {
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

    public boolean isDate(Object value) {
        return value instanceof XMLGregorianCalendar
                && ((XMLGregorianCalendar) value).getXMLSchemaType() == DatatypeConstants.DATE;
    }

    public boolean isTime(Object value) {
        return value instanceof XMLGregorianCalendar
                && ((XMLGregorianCalendar) value).getXMLSchemaType() == DatatypeConstants.TIME;
    }

    public boolean isDateTime(Object value) {
        return value instanceof XMLGregorianCalendar
                && ((XMLGregorianCalendar) value).getXMLSchemaType() == DatatypeConstants.DATETIME;
    }

    public boolean isDuration(Object value) {
        return value instanceof Duration;
    }

    public boolean isYearsAndMonthsDuration(Object value) {
        return value instanceof Duration
                && getXMLSchemaType((Duration) value) == DatatypeConstants.DURATION_YEARMONTH;
    }

    public boolean isDaysAndTimeDuration(Object value) {
        return value instanceof Duration
                && getXMLSchemaType((Duration) value) == DatatypeConstants.DURATION_DAYTIME;
    }

    public Long dateTimeValue(XMLGregorianCalendar dateTime) {
        return dateTime == null ? null : Math.floorDiv(dateTime.toGregorianCalendar().getTimeInMillis(), 1000L);
    }

}
