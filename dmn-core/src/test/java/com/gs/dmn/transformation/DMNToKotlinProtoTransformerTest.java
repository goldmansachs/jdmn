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
package com.gs.dmn.transformation;

import com.gs.dmn.runtime.Pair;
import org.junit.Test;

public class DMNToKotlinProtoTransformerTest extends AbstractTckDMNToKotlinTransformerTest {
    @Override
    protected String getInputPath() {
        return "proto/%s/%s/translator";
    }

    @Override
    protected String getExpectedPath() {
        return "proto/%s/%s/translator/expected/proto3/kotlin/dmn";
    }

    @Test
    public void testProto() throws Exception {
        doSingleModelTest("1.1","0004-lending", new Pair<>("generateProtoMessages", "true"), new Pair<>("generateProtoServices", "true"), new Pair<>("caching", "true"));
    }

    @Test
    public void testProtoDateTime() throws Exception {
        doSingleModelTest("1.1","date-time-proto", new Pair<>("generateProtoMessages", "true"), new Pair<>("generateProtoServices", "true"), new Pair<>("caching", "true"));
    }
}
