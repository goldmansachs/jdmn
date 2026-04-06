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

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.serialization.TypeDeserializationConfigurer;
import com.gs.dmn.tck.ast.TestCases;
import com.gs.dmn.transformation.DMNToPythonTransformer;
import com.gs.dmn.transformation.DMNTransformer;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import com.gs.dmn.transformation.template.TemplateProvider;
import com.gs.dmn.validation.DMNValidator;
import com.gs.dmn.validation.TestValidator;

import java.io.File;

public class TCKTestCasesToPythonJUnitTransformer<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends TCKTestCasesToJavaJUnitTransformer<NUMBER, DATE, TIME, DATE_TIME, DURATION> {
    public TCKTestCasesToPythonJUnitTransformer(DMNDialectDefinition<NUMBER, DATE, TIME, DATE_TIME, DURATION, TestCases> dialectDefinition, DMNValidator dmnValidator, TestValidator<TestCases> testCasesValidator, DMNTransformer<TestCases> dmnTransformer, TemplateProvider templateProvider, LazyEvaluationDetector lazyEvaluationDetector, TypeDeserializationConfigurer typeDeserializationConfigurer, File inputModelFile, InputParameters inputParameters, BuildLogger logger) {
        super(dialectDefinition, dmnValidator, testCasesValidator, dmnTransformer, templateProvider, lazyEvaluationDetector, typeDeserializationConfigurer, inputModelFile, inputParameters, logger);
    }

    @Override
    protected void generateExtra(BasicDMNToNativeTransformer<Type, DMNContext> basicTransformer, DMNModelRepository dmnModelRepository, File outputFolder) {
        if (basicTransformer.isGenerateExtra()) {
            DMNToPythonTransformer.generateInitFiles(basicTransformer, dmnModelRepository, outputFolder, inputParameters.getCharset(), false);
        }
    }

    @Override
    protected String getFileExtension() {
        return ".py";
    }
}
