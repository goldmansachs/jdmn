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
import com.gs.dmn.dialect.JavaTimeDMNDialectDefinition;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.tck.ast.TestCases;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;

public class ImportDMNInterpreterTest extends AbstractDMNInterpreterTest<Number, LocalDate, TemporalAccessor, TemporalAccessor, TemporalAmount> {
    @Override
    protected String getDMNInputPath() {
        return "composite/%s/%s/translator";
    }

    @Override
    protected String getTestCasesInputPath() {
        return getDMNInputPath();
    }

    @Override
    protected DMNDialectDefinition<Number, LocalDate, TemporalAccessor, TemporalAccessor, TemporalAmount, TestCases> getDialectDefinition() {
        return new JavaTimeDMNDialectDefinition();
    }

    @Test
    public void testImport() {
        doFolderTest("1.2", "0001-no-name-conflicts-one-package");
        doFolderTest("1.2", "0002-no-name-conflicts");
        doFolderTest("1.2", "0003-name-conflicts");
        doFolderTest("1.2", "0004-decision-tables");
        doFolderTest("1.2", "0005-decision-tables-name-conflicts");
        doFolderTest("1.2", "0006-multiple-input-data", new Pair<>("singletonInputData", "false"));
        doFolderTest("1.2", "0007-name-conflicts-same-decision-singleton");
        doFolderTest("1.2", "0008-name-conflicts-same-decision-no-singleton", new Pair<>("singletonInputData", "false"));
        doFolderTest("1.2", "0009-type-name-conflicts", new Pair<>("onePackage", "true"));
        doFolderTest("1.2", "0009-type-name-conflicts");
    }
}

