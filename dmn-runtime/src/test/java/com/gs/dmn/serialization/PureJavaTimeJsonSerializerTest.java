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
package com.gs.dmn.serialization;

import com.gs.dmn.feel.lib.PureJavaTimeFEELLib;
import com.gs.dmn.feel.lib.StandardFEELLib;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;
import java.util.Collections;
import java.util.List;

import static com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER;

public class PureJavaTimeJsonSerializerTest extends AbstractJavaTimeJsonSerializerTest<BigDecimal, LocalDate, TemporalAccessor, TemporalAccessor, TemporalAmount> {
    @Override
    protected StandardFEELLib<BigDecimal, LocalDate, TemporalAccessor, TemporalAccessor, TemporalAmount> makeFEELLib() {
        return new PureJavaTimeFEELLib();
    }

    @Override
    protected BigDecimal readNumber(String literal) throws Exception {
        return OBJECT_MAPPER.readValue(literal, BigDecimal.class);
    }

    @Override
    protected LocalDate readDate(String literal) throws Exception {
        return OBJECT_MAPPER.readValue(literal, LocalDate.class);
    }

    @Override
    protected Temporal readTime(String literal) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    protected Temporal readDateTime(String literal) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    protected TemporalAmount readDuration(String literal) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    protected List<Pair<String, String>> getTimeTestData() {
        return Collections.emptyList();
    }

    @Override
    protected List<Pair<String, String>> getDateTimeTestData() {
        return Collections.emptyList();
    }

    @Override
    protected List<Pair<String, String>> getDurationTestData() {
        return Collections.emptyList();
    }
}

