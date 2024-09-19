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
package com.gs.dmn.serialization.xstream;

import com.gs.dmn.serialization.DMNDialectTransformerTest;
import com.gs.dmn.serialization.DMNVersion;
import com.gs.dmn.serialization.SimpleDMNDialectTransformer;
import org.junit.jupiter.api.Test;

public class DMN14ToLatestDialectTransformerTest extends DMNDialectTransformerTest {
    @Test
    public void testTransform() throws Exception {
        doTest("0004-lending.dmn");
        doTest("0014-loan-comparison.dmn");
        doTest("0087-chapter-11-example.dmn");
    }

    @Override
    protected SimpleDMNDialectTransformer getTransformer() {
        return new DMN14ToLatestDialectTransformer(LOGGER);
    }

    @Override
    protected String getSourceVersion() {
        return DMNVersion.DMN_14.getVersion();
    }

    @Override
    protected String getTargetVersion() {
        return LATEST_VERSION;
    }
}