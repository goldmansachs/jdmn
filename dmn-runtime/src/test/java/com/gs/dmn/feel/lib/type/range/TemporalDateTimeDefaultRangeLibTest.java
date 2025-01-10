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
package com.gs.dmn.feel.lib.type.range;

import com.gs.dmn.feel.lib.JavaTimeFEELLib;
import com.gs.dmn.feel.lib.StandardFEELLib;

import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;

public class TemporalDateTimeDefaultRangeLibTest extends AbstractDefaultRangeLibTest<Number, LocalDate, TemporalAccessor, TemporalAccessor, TemporalAmount> {
    @Override
    protected StandardFEELLib<Number, LocalDate, TemporalAccessor, TemporalAccessor, TemporalAmount> getLib() {
        return new JavaTimeFEELLib();
    }

    @Override
    protected TemporalAccessor makePoint(int number) {
        if (number < 0 || number > 60) {
            throw new IllegalArgumentException("Illegal day");
        }
        return this.getLib().dateAndTime(String.format("2020-01-01T12:00:%02d", number));
    }
}