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
package com.gs.dmn.feel.lib.type.time.mixed;

import com.gs.dmn.feel.lib.type.DateTimeType;
import com.gs.dmn.feel.lib.type.time.BaseDateTimeLib;
import com.gs.dmn.feel.lib.type.time.DateTimeLib;

import javax.xml.datatype.Duration;
import java.time.*;

public class ZonedDateTimeLib extends BaseDateTimeLib implements DateTimeLib<LocalDate, OffsetTime, ZonedDateTime> {
    @Override
    public ZonedDateTime dateAndTime(String from) {
        if (from == null) {
            return null;
        }
        if (this.hasZone(from) && this.hasOffset(from)) {
            return null;
        }
        if (this.invalidYear(from)) {
            return null;
        }

        return makeZonedDateTime(from);
    }

    @Override
    public ZonedDateTime dateAndTime(LocalDate date, OffsetTime time) {
        if (date == null || time == null) {
            return null;
        }

        ZoneOffset offset = time.getOffset();
        LocalDateTime localDateTime = LocalDateTime.of(date, time.toLocalTime());
        return ZonedDateTime.ofInstant(localDateTime, offset, ZoneId.of(offset.getId()));
    }
    public ZonedDateTime dateAndTime(Object date, OffsetTime time) {
        if (date == null || time == null) {
            return null;
        }

        if (date instanceof ZonedDateTime) {
            return dateAndTime(((ZonedDateTime) date).toLocalDate(), time);
        } else {
            return null;
        }
    }

}
