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
package com.gs.dmn.feel.lib.type.time.pure;

import com.gs.dmn.feel.lib.type.time.DateTimeComparator;

import java.time.*;
import java.time.temporal.Temporal;

public class TemporalComparator extends DateTimeComparator<Temporal> {
    @Override
    protected Integer compareTo(Temporal first, Temporal second) {
        // Time
        if (isTime(first) && isTime(second)) {
            return timeValue(first).compareTo(timeValue(second));

        // Date time
        } else if (isDateTime(first) && isDateTime(second)) {
            return dateTimeValue(first).compareTo(dateTimeValue(second));
        }

        return  null;
    }

    private boolean isTime(Temporal dateTime) {
        return dateTime instanceof LocalTime || dateTime instanceof OffsetTime;
    }

    private boolean isDateTime(Temporal time) {
        return time instanceof LocalDateTime
                || time instanceof OffsetDateTime
                || time instanceof ZonedDateTime;
    }
}
