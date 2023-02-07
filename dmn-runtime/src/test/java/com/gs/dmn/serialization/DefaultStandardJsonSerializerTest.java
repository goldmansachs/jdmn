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

import com.gs.dmn.feel.lib.DefaultFEELLib;
import com.gs.dmn.feel.lib.FEELLib;
import com.gs.dmn.runtime.Pair;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER;

public class DefaultStandardJsonSerializerTest extends AbstractJsonSerializerTest<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar, Duration> {
    static final List<Pair<String, String>> TIME_TEST_DATA = Arrays.asList(
            new Pair<>("04:20:20", "04:20:20"),
            new Pair<>("04:20:20Z", "04:20:20Z"),
            new Pair<>("04:20:20.004", "04:20:20.004"),
            new Pair<>("04:20:20.004Z", "04:20:20.004Z"),
            new Pair<>("04:20:20.00421", "04:20:20.00421"),
            new Pair<>("04:20:20.00421Z", "04:20:20.00421Z"),
            new Pair<>("04:20:20.00421+01:00", "04:20:20.00421+01:00"),
            new Pair<>("04:20:20.004Z", "04:20:20.004Z"),
            new Pair<>("04:20:20.00421@Europe/Paris", "04:20:20.00421+01:00")
    );
    static final List<Pair<String, String>> DATE_TIME_TEST_DATA = Arrays.asList(
            new Pair<>("2019-03-11T04:20:20", "2019-03-11T04:20:20"),
            new Pair<>("2019-03-11T04:20:20Z", "2019-03-11T04:20:20Z"),
            new Pair<>("2019-03-11T04:20:20.004", "2019-03-11T04:20:20.004"),
            new Pair<>("2019-03-11T04:20:20.004Z", "2019-03-11T04:20:20.004Z"),
            new Pair<>("2019-03-11T04:20:20.00421", "2019-03-11T04:20:20.00421"),
            new Pair<>("2019-03-11T04:20:20.00421Z", "2019-03-11T04:20:20.00421Z"),
            new Pair<>("2019-03-11T04:20:20.00421+01:00", "2019-03-11T04:20:20.00421+01:00"),
            new Pair<>("2019-03-11T04:20:20.004@UTC", "2019-03-11T04:20:20.004Z"),
            new Pair<>("2019-03-11T04:20:20.00421@Europe/Paris", "2019-03-11T04:20:20.00421+01:00"),

            new Pair<>("9999-03-11T04:20:20", "9999-03-11T04:20:20")
    );

    @Override
    protected FEELLib<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar, Duration> makeFEELLib() {
        return new DefaultFEELLib();
    }

    @Override
    protected BigDecimal readNumber(String literal) throws Exception {
        return OBJECT_MAPPER.readValue(literal, BigDecimal.class);
    }

    @Override
    protected XMLGregorianCalendar readDate(String literal) throws Exception {
        return OBJECT_MAPPER.readValue(literal, XMLGregorianCalendar.class);
    }

    @Override
    protected XMLGregorianCalendar readTime(String literal) throws Exception {
        return OBJECT_MAPPER.readValue(literal, XMLGregorianCalendar.class);
    }

    @Override
    protected XMLGregorianCalendar readDateTime(String literal) throws Exception {
        return OBJECT_MAPPER.readValue(literal, XMLGregorianCalendar.class);
    }

    @Override
    protected Duration readDuration(String literal) throws Exception {
        return OBJECT_MAPPER.readValue(literal, Duration.class);
    }

    @Override
    protected List<Pair<String, String>> getTimeTestData() {
        return TIME_TEST_DATA;
    }

    @Override
    protected List<Pair<String, String>> getDateTimeTestData() {
        return DATE_TIME_TEST_DATA;
    }
}

