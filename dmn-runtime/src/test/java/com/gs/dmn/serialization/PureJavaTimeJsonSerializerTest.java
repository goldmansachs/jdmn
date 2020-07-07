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

import com.fasterxml.jackson.core.type.TypeReference;
import com.gs.dmn.feel.lib.FEELLib;
import com.gs.dmn.feel.lib.PureJavaTimeFEELLib;
import com.gs.dmn.runtime.Pair;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER;

public class PureJavaTimeJsonSerializerTest extends AbstractJavaTimeJsonSerializerTest<BigDecimal, LocalDate, Temporal, Temporal, TemporalAmount> {
    @Override
    protected FEELLib<BigDecimal, LocalDate, Temporal, Temporal, TemporalAmount> makeFEELLib() {
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
    protected Temporal readTime(String literal) throws Exception {
        return OBJECT_MAPPER.readValue(literal, OffsetTime.class);
    }

    @Override
    protected Temporal readDateTime(String literal) throws Exception {
        return OBJECT_MAPPER.readValue(literal, ZonedDateTime.class);
    }

    @Override
    protected TemporalAmount readDuration(String literal) throws Exception {
        return OBJECT_MAPPER.readValue(literal, new TypeReference<TemporalAmount>() {});
    }

    @Override
    protected List<Pair<String, String>> getTimeTestData() {
        return Arrays.asList(
//                new Pair<>("04:20:20", "04:20:20"),
                new Pair<>("04:20:20Z", "04:20:20Z"),
//                new Pair<>("04:20:20.004", "04:20:20.004"),
                new Pair<>("04:20:20.004Z", "04:20:20.004Z"),
//                new Pair<>("04:20:20.00421", "04:20:20.00421"),
                new Pair<>("04:20:20.00421Z", "04:20:20.004210Z"),
                new Pair<>("04:20:20.00421+01:00", "04:20:20.004210+01:00"),
                new Pair<>("04:20:20.004@UTC", "04:20:20.004Z"),
                new Pair<>("04:20:20.00421@Europe/Paris", "04:20:20.004210+01:00")
        );
    }

    @Override
    protected List<Pair<String, String>> getDateTimeTestData() {
        return new ArrayList<>();
    }

    @Override
    protected List<Pair<String, String>> getDurationTestData() {
        return new ArrayList<>();
    }
}

