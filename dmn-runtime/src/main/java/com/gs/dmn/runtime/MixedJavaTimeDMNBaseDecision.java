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
package com.gs.dmn.runtime;

import com.gs.dmn.feel.lib.MixedJavaTimeFEELLib;

import javax.xml.datatype.Duration;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.util.Map;

/**
 * @deprecated  As of release 8.3.0, replaced by {@link JavaTimeDMNBaseDecision}
 */
@Deprecated
public abstract class MixedJavaTimeDMNBaseDecision extends MixedJavaTimeFEELLib implements DMNDecision<BigDecimal, LocalDate, OffsetTime, ZonedDateTime, Duration> {
    @Override
    public Object applyMap(Map<String, String> input_, ExecutionContext context_) {
        return null;
    }
}
