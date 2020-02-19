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
package com.gs.dmn.tck;

import com.gs.dmn.runtime.Pair;
import org.junit.Ignore;
import org.junit.Test;

public class ImportTestCasesToJUnitTransformerTest extends AbstractTCKTestCasesToJUnitTransformerTest {
    @Override
    protected String getDMNInputPath() {
        return "composite/input";
    }

    @Override
    protected String getTestCasesInputPath() {
        return "composite/input";
    }

    @Override
    protected String getExpectedPath() {
        return "composite/expected/test";
    }

    @Test
    @Ignore
    public void testImport() throws Exception {
        doMultipleModelsTest("0001-no-name-conflicts-one-package", "0001-no-name-conflicts-one-package");
        doMultipleModelsTest("0002-no-name-conflicts", "0002-no-name-conflicts", new Pair<>("onePackage", "false"));
        doMultipleModelsTest("0003-name-conflicts", "0003-name-conflicts", new Pair<>("onePackage", "false"));
    }
}
