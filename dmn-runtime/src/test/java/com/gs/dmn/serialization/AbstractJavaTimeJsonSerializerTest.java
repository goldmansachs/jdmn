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

import com.gs.dmn.runtime.Pair;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractJavaTimeJsonSerializerTest<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends AbstractJsonSerializerTest<NUMBER, DATE, TIME, DATE_TIME, DURATION> {
    @Override
    protected List<Pair<String, String>> getDateTestData() {
        return Arrays.asList(
                new Pair<>("2019-03-12", "2019-03-12"),
                new Pair<>("9999-12-31", "9999-12-31")
        );
    }

    @Override
    protected List<Pair<String, String>> getTimeTestData() {
        return Arrays.asList(
                new Pair<>("04:20:20", "04:20:20Z"),
                new Pair<>("04:20:20Z", "04:20:20Z"),
                new Pair<>("04:20:20.004", "04:20:20.004Z"),
                new Pair<>("04:20:20.004Z", "04:20:20.004Z"),
                new Pair<>("04:20:20.00421", "04:20:20.004210Z"),
                new Pair<>("04:20:20.00421Z", "04:20:20.004210Z"),
                new Pair<>("04:20:20.00421+01:00", "04:20:20.004210+01:00"),
                new Pair<>("04:20:20.004@UTC", "04:20:20.004Z"),
                new Pair<>("04:20:20.00421@Europe/Paris", "04:20:20.004210+01:00")
        );
    }

    @Override
    protected List<Pair<String, String>> getDateTimeTestData() {
        return Arrays.asList(
                new Pair<>("2019-03-11T04:20:20", "2019-03-11T04:20:20Z"),
                new Pair<>("2019-03-11T04:20:20Z", "2019-03-11T04:20:20Z"),
                new Pair<>("2019-03-11T04:20:20.004", "2019-03-11T04:20:20.004Z"),
                new Pair<>("2019-03-11T04:20:20.004Z", "2019-03-11T04:20:20.004Z"),
                new Pair<>("2019-03-11T04:20:20.00421", "2019-03-11T04:20:20.00421Z"),
                new Pair<>("2019-03-11T04:20:20.00421Z", "2019-03-11T04:20:20.00421Z"),
//                new Pair<>("2019-03-11T04:20:20.00421+01:00", "2019-03-11T04:20:20.00421+01:00"),
                new Pair<>("2019-03-11T04:20:20.004@UTC", "2019-03-11T04:20:20.004Z"),
//                new Pair<>("2019-03-11T04:20:20.00421@Europe/Paris", "2019-03-11T04:20:20.00421+01:00"),

                new Pair<>("9999-03-11T04:20:20", "9999-03-11T04:20:20Z")
        );
    }

    @Override
    protected List<Pair<String, String>> getDurationTestData() {
        return Arrays.asList(
                new Pair<>("P1Y2M", "P1Y2M"),
                new Pair<>("P1DT2H3M", "P1DT2H3M"),
                new Pair<>("P1Y1M3DT4H5M", "P1Y1M3DT4H5M")
        );
    }
}