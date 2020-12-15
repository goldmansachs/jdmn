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

public class ImportDMNToJavaTransformerTest extends AbstractTckDMNToJavaTransformerTest {
    @Override
    protected String getInputPath() {
        return "composite/%s/%s/translator";
    }

    @Override
    protected String getExpectedPath() {
        return "composite/%s/%s/translator/expected/java/dmn";
    }

    @Test
    public void testImport() throws Exception {
        doMultipleModelsTest("1.2","0001-no-name-conflicts-one-package", new Pair<>("onePackage", "true"));
        doMultipleModelsTest("1.2","0002-no-name-conflicts");
        doMultipleModelsTest("1.2","0003-name-conflicts");
        doMultipleModelsTest("1.2","0004-decision-tables");
        doMultipleModelsTest("1.2","0005-decision-tables-name-conflicts");
        doMultipleModelsTest("1.2","0006-multiple-input-data", new Pair<>("singletonInputData", "false"));
        doMultipleModelsTest("1.2","0007-name-conflicts-same-decision-singleton");
        doMultipleModelsTest("1.2","0008-name-conflicts-same-decision-no-singleton", new Pair<>("singletonInputData", "false"));
        doMultipleModelsTest("1.2","0009-type-name-conflicts", new Pair<>("onePackage", "true"));
        doMultipleModelsTest("1.2","0010-bkm-name-conflicts");
    }
}
