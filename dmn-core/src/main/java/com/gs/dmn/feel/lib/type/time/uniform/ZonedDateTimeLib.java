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
package com.gs.dmn.feel.lib.type.time.uniform;

import com.gs.dmn.feel.lib.type.time.BaseDateTimeLib;

import java.time.ZonedDateTime;

public class ZonedDateTimeLib extends BaseDateTimeLib {
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

    public ZonedDateTime dateAndTime(ZonedDateTime date, ZonedDateTime time) {
        if (date == null || time == null) {
            return null;
        }

        return ZonedDateTime.of(
                date.getYear(), date.getMonth().getValue(), date.getDayOfMonth(),
                time.getHour(), time.getMinute(), time.getSecond(), time.getNano(), time.getZone()
        );
    }
}
