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
import java.time.temporal.TemporalAccessor;

public class TemporalComparator extends BasePureCalendarType implements DateTimeComparator<TemporalAccessor> {
    @Override
    public Integer compareTo(TemporalAccessor first, TemporalAccessor second) {
        if (isDate(first) && isDate(second)) {
            // Date
            return dateValue((LocalDate) first).compareTo(dateValue((LocalDate) second));
        } else if (isDateTime(first) && isDateTime(second)) {
            // Date and time
            // valuedt(e1) < valuedt(e2). valuedt is defined in 10.3.2.3.5. If one input has a null timezone offset, that input uses the timezone offset of the other input.
            return dateTimeValue(first).compareTo(dateTimeValue(second));
        } else if (isTime(first) && isTime(second)) {
            // Time
            // valuet(e1) < valuet(e2). valuet is defined in 10.3.2.3.4. If one input has a null timezone offset, that input uses the timezone offset of the other input.
            return timeValue(first).compareTo(timeValue(second));
        }

        return  null;
    }
}
