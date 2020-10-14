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

import com.gs.dmn.feel.lib.type.time.BaseDateTimeLib;
import com.gs.dmn.feel.lib.type.time.DateTimeLib;
import com.gs.dmn.feel.lib.type.time.xml.DefaultDateTimeLib;
import org.apache.commons.lang3.StringUtils;

import java.time.*;
import java.time.temporal.Temporal;

public class TemporalDateTimeLib extends BaseDateTimeLib implements DateTimeLib<LocalDate, Temporal, Temporal> {
    private final DefaultDateTimeLib dateTimeLib = new DefaultDateTimeLib();

    @Override
    public Temporal dateAndTime(String literal) {
        if (StringUtils.isBlank(literal)) {
            return null;
        }

        literal = this.fixDateTimeFormat(literal);
        return (Temporal) this.dateTimeLib.temporalAccessor(literal);
    }

    @Override
    public Temporal dateAndTime(LocalDate date, Temporal time) {
        if (date == null || time == null) {
            return null;
        }

        if (time instanceof LocalTime) {
            return LocalDateTime.of(date, (LocalTime) time);
        } else if (time instanceof OffsetTime) {
            return OffsetDateTime.of(date, ((OffsetTime) time).toLocalTime(), ((OffsetTime) time).getOffset());
        }
        throw new IllegalArgumentException(String.format("Cannot convert '%s' and '%s' to date and time", date, time));
    }

}
