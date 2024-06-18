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

import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.dialect.JavaTimePythonStandardDMNDialectDefinition;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.tck.ast.TestCases;
import com.gs.dmn.transformation.FileTransformer;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.template.PythonTreeTemplateProvider;
import com.gs.dmn.transformation.template.TemplateProvider;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;

public abstract class AbstractTestCasesToPythonJUnitTransformerTest extends AbstractTCKTestCasesToJUnitTransformerTest<BigDecimal, LocalDate, TemporalAccessor, TemporalAccessor, TemporalAmount> {
    @Override
    protected FileTransformer makeTransformer(Path inputModelPath, InputParameters inputParameters, BuildLogger logger) {
        return new TCKTestCasesToPythonJUnitTransformer<>(makeDialectDefinition(), makeDMNValidator(logger), makeDMNTransformer(logger), makeTemplateProvider(), makeLazyEvaluationDetector(inputParameters, LOGGER), makeTypeDeserializationConfigurer(logger), inputModelPath, inputParameters, logger);
    }

    @Override
    protected DMNDialectDefinition<BigDecimal, LocalDate, TemporalAccessor, TemporalAccessor, TemporalAmount, TestCases> makeDialectDefinition() {
        return new JavaTimePythonStandardDMNDialectDefinition();
    }

    @Override
    protected TemplateProvider makeTemplateProvider(){
        return new PythonTreeTemplateProvider();
    }
}
