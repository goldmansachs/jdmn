/*
 * Copyright 2016 Goldman Sachs.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"; you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.gs.dmn.signavio.feel.lib.type.time.mixed;

import com.gs.dmn.signavio.feel.lib.type.time.SignavioBaseDateTimeLib;

import java.time.OffsetTime;
import java.time.ZonedDateTime;

public class SignavioOffsetTimeLib extends SignavioBaseDateTimeLib {
    public Long hourDiff(ZonedDateTime dateTime1, ZonedDateTime dateTime2) {
        long diff = durationBetween(dateTime1, dateTime2).getSeconds() / (60 * 60);
        return diff;
    }
    public Long hourDiff(OffsetTime time1, OffsetTime time2) {
        long diff = durationBetween(time1, time2).getSeconds() / (60 * 60);
        return diff;
    }

    public Long minutesDiff(ZonedDateTime dateTime1, ZonedDateTime dateTime2) {
        long diff = durationBetween(dateTime1, dateTime2).getSeconds() / 60;
        return diff;
    }
    public Long minutesDiff(OffsetTime time1, OffsetTime time2) {
        long diff = durationBetween(time1, time2).getSeconds() / 60;
        return diff;
    }
}
