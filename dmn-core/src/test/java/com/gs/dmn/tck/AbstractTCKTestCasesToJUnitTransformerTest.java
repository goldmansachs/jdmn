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

import com.gs.dmn.feel.analysis.semantics.environment.StandardEnvironmentFactory;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.serialization.DefaultTypeDeserializationConfigurer;
import com.gs.dmn.serialization.TypeDeserializationConfigurer;
import com.gs.dmn.tck.ast.TestCases;
import com.gs.dmn.transformation.*;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;
import com.gs.dmn.validation.DMNValidator;
import com.gs.dmn.validation.NopDMNValidator;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.util.Map;

public abstract class AbstractTCKTestCasesToJUnitTransformerTest<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends AbstractTestCasesTransformerTest<NUMBER, DATE, TIME, DATE_TIME, DURATION, TestCases> {
    @Override
    protected URI resource(String path) {
        return tckResource(path);
    }

    @SafeVarargs
    public final void doSingleModelTest(String dmnVersion, String dmnFileName, String testFileName, Pair<String, String>... extraInputParameters) throws Exception {
        String dmnPath = completePath(getDMNInputPath(), dmnVersion, dmnFileName) + "/";
        String testCasesPath = completePath(getTestCasesInputPath(), dmnVersion, dmnFileName) + "/";
        String expectedPath = completePath(getExpectedPath(), dmnVersion, dmnFileName);
        String inputTestFilePath = testCasesPath + testFileName + this.inputParameters.getTckFileExtension();
        String inputModelFilePath = dmnPath + dmnFileName + this.inputParameters.getDmnFileExtension();
        String decodedInputTestFilePath = URLDecoder.decode(resource(inputTestFilePath).getPath(), "UTF-8");
        String decodedInputModelFilePath = URLDecoder.decode(resource(inputModelFilePath).getPath(), "UTF-8");
        super.doTest(decodedInputTestFilePath, decodedInputModelFilePath, expectedPath, extraInputParameters);
    }

    @SafeVarargs
    public final void doMultipleModelsTest(String dmnVersion, String dmnFolderName, String testFolderName, Pair<String, String>... extraInputParameters) throws Exception {
        String inputTestFilePath = completePath(getTestCasesInputPath(), dmnVersion, testFolderName) + "/";
        String inputModelFilePath = completePath(getDMNInputPath(), dmnVersion, dmnFolderName) + "/";
        String expectedPath = completePath(getExpectedPath(), dmnVersion, dmnFolderName);
        String decodedInputTestFilePath = URLDecoder.decode(resource(inputTestFilePath).getPath(), "UTF-8");
        String decodedInputModelFilePath = URLDecoder.decode(resource(inputModelFilePath).getPath(), "UTF-8");
        super.doTest(decodedInputTestFilePath, decodedInputModelFilePath, expectedPath, extraInputParameters);
    }

    @Override
    protected DMNValidator makeDMNValidator(BuildLogger logger) {
        return new NopDMNValidator();
    }

    @Override
    protected DMNTransformer<TestCases> makeDMNTransformer(BuildLogger logger) {
        return new ToQuotedNameTransformer(logger);
    }

    @Override
    protected LazyEvaluationDetector makeLazyEvaluationDetector(InputParameters inputParameters, BuildLogger logger) {
        return new NopLazyEvaluationDetector();
    }

    @Override
    protected TypeDeserializationConfigurer makeTypeDeserializationConfigurer(BuildLogger logger) {
        return new DefaultTypeDeserializationConfigurer();
    }

    @Override
    protected Map<String, String> makeInputParametersMap() {
        Map<String, String> inputParams = super.makeInputParametersMap();
        inputParams.put("environmentFactoryClass", StandardEnvironmentFactory.class.getName());
        inputParams.put("decisionBaseClass", makeDialectDefinition().getDecisionBaseClass());
        return inputParams;
    }

    @Override
    protected abstract FileTransformer makeTransformer(Path inputModelPath, InputParameters inputParameters, BuildLogger logger);

    protected abstract String getDMNInputPath();

    protected abstract String getTestCasesInputPath();

    protected abstract String getExpectedPath();
}
