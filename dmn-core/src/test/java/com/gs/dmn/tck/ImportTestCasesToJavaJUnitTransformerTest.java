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
import org.junit.Test;

public class ImportTestCasesToJavaJUnitTransformerTest extends AbstractTCKTestCasesToJavaJUnitTransformerTest {
    @Override
    protected String getDMNInputPath() {
        return "composite/%s/%s/translator";
    }

    @Override
    protected String getTestCasesInputPath() {
        return getDMNInputPath();
    }

    @Override
    protected String getExpectedPath() {
        return "composite/%s/%s/translator/expected/java/test";
    }

    @Test
    public void testImport() throws Exception {
        doMultipleModelsTest("1.2","0001-no-name-conflicts-one-package", "0001-no-name-conflicts-one-package", new Pair<>("onePackage", "true"));
        doMultipleModelsTest("1.2","0002-no-name-conflicts", "0002-no-name-conflicts");
        doMultipleModelsTest("1.2","0003-name-conflicts", "0003-name-conflicts");
        doMultipleModelsTest("1.2","0004-decision-tables", "0004-decision-tables");
        doMultipleModelsTest("1.2","0005-decision-tables-name-conflicts", "0005-decision-tables-name-conflicts");
        doMultipleModelsTest("1.2","0006-multiple-input-data", "0006-multiple-input-data", new Pair<>("singletonInputData", "false"));
        doMultipleModelsTest("1.2","0007-name-conflicts-same-decision-singleton", "0007-name-conflicts-same-decision-singleton");
        doMultipleModelsTest("1.2","0008-name-conflicts-same-decision-no-singleton", "0008-name-conflicts-same-decision-no-singleton", new Pair<>("singletonInputData", "false"));
        doMultipleModelsTest("1.2","0009-type-name-conflicts", "0009-type-name-conflicts", new Pair<>("onePackage", "true"));
        doMultipleModelsTest("1.2","0010-bkm-name-conflicts", "0010-bkm-name-conflicts");
    }
}
