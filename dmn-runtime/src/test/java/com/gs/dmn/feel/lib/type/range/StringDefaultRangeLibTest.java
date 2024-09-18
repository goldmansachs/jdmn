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

import com.gs.dmn.feel.lib.MixedJavaTimeFEELLib;
import com.gs.dmn.feel.lib.StandardFEELLib;

import javax.xml.datatype.Duration;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZonedDateTime;

public class StringDefaultRangeLibTest extends AbstractDefaultRangeLibTest<BigDecimal, LocalDate, OffsetTime, ZonedDateTime, Duration> {
    @Override
    protected final StandardFEELLib<BigDecimal, LocalDate, OffsetTime, ZonedDateTime, Duration> getLib() {
        return new MixedJavaTimeFEELLib();
    }
    @Override
    protected String makePoint(int number) {
        return String.format("%03d", number);
    }
}