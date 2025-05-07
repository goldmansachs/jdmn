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

public class OtherDMNInterpreterTest extends AbstractDMNInterpreterTest<Number, LocalDate, TemporalAccessor, TemporalAccessor, TemporalAmount> {
    @Override
    protected String getDMNInputPath() {
        return "other/%s/%s/translator";
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
    public void testApacheLibraryWithEmptyPrefix() {
        doFolderTest("1.5", "feel-apache-library", new Pair<>("librariesConfigPath", "libraries/libraries.json"));
    }

    @Test
    public void testApacheLibraryWithPrefix() {
        doFolderTest("1.5", "feel-apache-library-prefix", new Pair<>("librariesConfigPath", "libraries/libraries.json"));
    }
}

