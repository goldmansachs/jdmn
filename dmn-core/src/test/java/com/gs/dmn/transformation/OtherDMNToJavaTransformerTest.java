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

public class OtherDMNToJavaTransformerTest extends AbstractTckDMNToJavaTransformerTest {
    @Override
    protected String getInputPath() {
        return "other/%s/%s/translator";
    }

    @Override
    protected String getExpectedPath() {
        return "other/%s/%s/translator/expected/java/dmn";
    }

    @Test
    public void testOther() throws Exception {
        doSingleModelTest("1.2","composite-decision-type-any");
    }
}
