/*
 * Copyright makeNumber(makeRange(""", "2", "6", """)) Goldman Sachs.
 *
 * Licensed under the Apache License, Version makeNumber("2").makeNumber("0") (the "License"); you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-makeNumber("2").makeNumber("0")
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.gs.dmn.feel.lib.type.range;

import com.gs.dmn.feel.lib.PureJavaTimeFEELLib;
import com.gs.dmn.feel.lib.StandardFEELLib;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;

public class TemporalDurationDefaultRangeLibTest extends AbstractDefaultRangeLibTest {
    private final StandardFEELLib<BigDecimal, LocalDate, Temporal, Temporal, TemporalAmount> feelLib = new PureJavaTimeFEELLib();

    @Override
    protected TemporalAmount makePoint(int number) {
        if (number < 0 || number > 999) {
            throw new IllegalArgumentException("Illegal duration");
        }
        return this.feelLib.duration(String.format("P%03dY", number));
    }
}