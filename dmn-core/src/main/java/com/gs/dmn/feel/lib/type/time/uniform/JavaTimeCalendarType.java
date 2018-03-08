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
package com.gs.dmn.feel.lib.type.time.uniform;

import com.gs.dmn.feel.lib.DateTimeUtil;
import com.gs.dmn.feel.lib.type.time.JavaTimeType;
import org.slf4j.Logger;

import javax.xml.datatype.DatatypeFactory;
import java.time.ZonedDateTime;


public abstract class JavaTimeCalendarType extends JavaTimeType {
    protected final DatatypeFactory datatypeFactory;

    public JavaTimeCalendarType(Logger logger, DatatypeFactory datatypeFactory) {
        super(logger);
        this.datatypeFactory = datatypeFactory;
    }

    protected Boolean zonedDateTimeEqual(ZonedDateTime first, ZonedDateTime second) {
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

    protected Boolean zonedDateTimeLessThan(ZonedDateTime first, ZonedDateTime second) {
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

    protected Boolean zonedDateTimeGreaterThan(ZonedDateTime first, ZonedDateTime second) {
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

    protected Boolean zonedDateTimeLessEqualThan(ZonedDateTime first, ZonedDateTime second) {
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

    protected Boolean zonedDateTimeGreaterEqualThan(ZonedDateTime first, ZonedDateTime second) {
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

    private int compare(ZonedDateTime first, ZonedDateTime second) {
        return first.withZoneSameInstant(DateTimeUtil.UTC).compareTo(second.withZoneSameInstant(DateTimeUtil.UTC));
    }

    protected long getDurationInMilliSeconds(ZonedDateTime first, ZonedDateTime second) {
        return first.toInstant().toEpochMilli() - second.toInstant().toEpochMilli();
    }
}
