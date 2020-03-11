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
package com.gs.dmn.runtime.interpreter;

import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.dialect.StandardDMNDialectDefinition;
import com.gs.dmn.runtime.Pair;
import org.junit.Test;

public class ImportDMNInterpreterTest extends AbstractDMNInterpreterTest {
    @Override
    protected String getDMNInputPath() {
        return "composite";
    }

    @Override
    protected String getTestCasesInputPath() {
        return getDMNInputPath();
    }

    @Override
    protected DMNDialectDefinition getDialectDefinition() {
        return new StandardDMNDialectDefinition();
    }

    @Test
    public void testImport() {
        doMultipleModelsTest("0001-no-name-conflicts-one-package", "0001-no-name-conflicts-one-package");
        doMultipleModelsTest("0002-no-name-conflicts", "0002-no-name-conflicts");
        doMultipleModelsTest("0003-name-conflicts", "0003-name-conflicts");
        doMultipleModelsTest("0004-decision-tables", "0004-decision-tables");
        doMultipleModelsTest("0005-decision-tables-name-conflicts", "0005-decision-tables-name-conflicts");
        doMultipleModelsTest("0006-multiple-input-data", "0006-multiple-input-data", new Pair<>("singletonInputData", "false"));
        doMultipleModelsTest("0007-name-conflicts-same-decision-singleton", "0007-name-conflicts-same-decision-singleton");
        doMultipleModelsTest("0008-name-conflicts-same-decision-no-singleton", "0008-name-conflicts-same-decision-no-singleton", new Pair<>("singletonInputData", "false"));
    }
}

