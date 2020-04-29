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
import org.slf4j.Logger;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.Duration;
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

    public BaseDefaultDurationType(Logger logger) {
        super(logger);
    }

    protected int compare(javax.xml.datatype.Duration duration1, javax.xml.datatype.Duration duration2) {
        javax.xml.datatype.Duration lhs = normalize(duration1);
        javax.xml.datatype.Duration rhs = normalize(duration2);
        return lhs.compare(rhs);
    }

    public static javax.xml.datatype.Duration normalize(javax.xml.datatype.Duration duration) {
        return duration.normalizeWith(GREGORIAN.get());
    }

    protected boolean isYearsAndMonths(Duration duration) {
        if (duration == null) {
            return false;
        }
        return duration.isSet(DatatypeConstants.YEARS) || duration.isSet(DatatypeConstants.MONTHS);
    }

    protected boolean isDaysAndTime(Duration duration) {
        if (duration == null) {
            return false;
        }
        return duration.isSet(DatatypeConstants.DAYS) || duration.isSet(DatatypeConstants.HOURS) ||
                duration.isSet(DatatypeConstants.MINUTES) || duration.isSet(DatatypeConstants.SECONDS);
    }
}
