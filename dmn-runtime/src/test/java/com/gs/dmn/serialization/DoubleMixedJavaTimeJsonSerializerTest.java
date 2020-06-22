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

import com.gs.dmn.feel.lib.DoubleMixedJavaTimeFEELLib;
import com.gs.dmn.feel.lib.FEELLib;
import com.gs.dmn.runtime.Pair;

import javax.xml.datatype.Duration;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import static com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER;

public class DoubleMixedJavaTimeJsonSerializerTest extends AbstractJavaTimeJsonSerializerTest<Double, LocalDate, OffsetTime, ZonedDateTime, Duration> {
    @Override
    protected FEELLib<Double, LocalDate, OffsetTime, ZonedDateTime, Duration> makeFEELLib() {
        return new DoubleMixedJavaTimeFEELLib();
    }

    @Override
    protected Double readNumber(String literal) throws Exception {
        return OBJECT_MAPPER.readValue(literal, Double.class);
    }

    @Override
    protected LocalDate readDate(String literal) throws Exception {
        return OBJECT_MAPPER.readValue(literal, LocalDate.class);
    }

    @Override
    protected OffsetTime readTime(String literal) throws Exception {
        return OBJECT_MAPPER.readValue(literal, OffsetTime.class);
    }

    @Override
    protected ZonedDateTime readDateTime(String literal) throws Exception {
        return OBJECT_MAPPER.readValue(literal, ZonedDateTime.class);
    }

    @Override
    protected Duration readDuration(String literal) throws Exception {
        return OBJECT_MAPPER.readValue(literal, Duration.class);
    }

    @Override
    protected List<Pair<String, String>> getNumberTestData() {
        return Arrays.asList(
                new Pair<>("123", "123.0"),
                new Pair<>("-123", "-123.0"),
                new Pair<>("123.45", "123.45"),
                new Pair<>("-123.45", "-123.45")
        );
    }
}

