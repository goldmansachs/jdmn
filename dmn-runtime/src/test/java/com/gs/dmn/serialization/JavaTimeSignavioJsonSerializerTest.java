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

import com.gs.dmn.feel.lib.FEELLib;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.signavio.feel.lib.JavaTimeSignavioLib;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;
import java.util.List;

import static com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER;

public class JavaTimeSignavioJsonSerializerTest extends AbstractJsonSerializerTest<Number, LocalDate, TemporalAccessor, TemporalAccessor, TemporalAmount> {
    @Override
    protected FEELLib<Number, LocalDate, TemporalAccessor, TemporalAccessor, TemporalAmount> makeFEELLib() {
        return new JavaTimeSignavioLib();
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
    protected TemporalAccessor readTime(String literal) throws Exception {
        return OBJECT_MAPPER.readValue(literal, TemporalAccessor.class);
    }

    @Override
    protected TemporalAccessor readDateTime(String literal) throws Exception {
        return OBJECT_MAPPER.readValue(literal, TemporalAccessor.class);
    }

    @Override
    protected TemporalAmount readDuration(String literal) throws Exception {
        return OBJECT_MAPPER.readValue(literal, TemporalAmount.class);
    }

    @Override
    protected List<Pair<String, String>> getTimeTestData() {
        return JavaTimeJsonSerializerTest.TIME_TEST_DATA;
    }

    @Override
    protected List<Pair<String, String>> getDateTimeTestData() {
        return JavaTimeJsonSerializerTest.DATE_TIME_TEST_DATA;
    }
}
