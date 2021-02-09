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
        if (first instanceof LocalTime && second instanceof LocalTime) {
            return ((LocalTime) first).compareTo((LocalTime) second);
        } else if (first instanceof OffsetTime && second instanceof OffsetTime) {
            return ((OffsetTime) first).compareTo((OffsetTime) second);

        // Date time
        } else if (first instanceof LocalDateTime && second instanceof LocalDateTime) {
            return ((LocalDateTime) first).compareTo((LocalDateTime) second);
        } else if (first instanceof OffsetDateTime && second instanceof OffsetDateTime) {
            return ((OffsetDateTime) first).compareTo((OffsetDateTime) second);
        } else if (first instanceof ZonedDateTime && second instanceof ZonedDateTime) {
            return ((ZonedDateTime) first).compareTo((ZonedDateTime) second);
        }
        return  null;
    }
}
