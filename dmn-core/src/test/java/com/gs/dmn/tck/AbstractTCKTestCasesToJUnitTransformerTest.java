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
import com.gs.dmn.runtime.DefaultDMNBaseDecision;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.serialization.DMNConstants;
import com.gs.dmn.serialization.DefaultTypeDeserializationConfigurer;
import com.gs.dmn.serialization.TypeDeserializationConfigurer;
import com.gs.dmn.transformation.AbstractTestCasesTransformerTest;
import com.gs.dmn.transformation.DMNTransformer;
import com.gs.dmn.transformation.FileTransformer;
import com.gs.dmn.transformation.ToSimpleNameTransformer;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;
import com.gs.dmn.validation.DMNValidator;
import com.gs.dmn.validation.DefaultDMNValidator;
import org.omg.dmn.tck.marshaller._20160719.TestCases;

import java.net.URLDecoder;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AbstractTCKTestCasesToJUnitTransformerTest<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends AbstractTestCasesTransformerTest<NUMBER, DATE, TIME, DATE_TIME, DURATION, TestCases> {
    public void doSingleModelTest(String dmnFileName, String testFileName, Pair<String, String>... extraInputParameters) throws Exception {
        String dmnPath = getDMNInputPath() + "/";
        String testCasesPath = getTestCasesInputPath() + "/";
        String expectedPath = getExpectedPath() + "/" + friendlyFolderName(dmnFileName);
        String inputTestFilePath = testCasesPath + testFileName + TestCasesReader.DEFAULT_TEST_CASE_FILE_EXTENSION;
        String inputModelFilePath = dmnPath + dmnFileName + DMNConstants.DMN_FILE_EXTENSION;
        String decodedInputTestFilePath = URLDecoder.decode(resource(inputTestFilePath).getPath(), "UTF-8");
        String decodedInputModelFilePath = URLDecoder.decode(resource(inputModelFilePath).getPath(), "UTF-8");
        super.doTest(decodedInputTestFilePath, decodedInputModelFilePath, expectedPath, extraInputParameters);
    }

    public void doMultipleModelsTest(String dmnFolderName, String testFolderName, Pair<String, String>... extraInputParameters) throws Exception {
        String dmnPath = getDMNInputPath() + "/";
        String testCasesPath = getTestCasesInputPath() + "/";
        String expectedPath = getExpectedPath() + "/" + friendlyFolderName(dmnFolderName);
        String inputTestFilePath = testCasesPath + testFolderName;
        String inputModelFilePath = dmnPath + dmnFolderName;
        String decodedInputTestFilePath = URLDecoder.decode(resource(inputTestFilePath).getPath(), "UTF-8");
        String decodedInputModelFilePath = URLDecoder.decode(resource(inputModelFilePath).getPath(), "UTF-8");
        super.doTest(decodedInputTestFilePath, decodedInputModelFilePath, expectedPath, extraInputParameters);
    }

    @Override
    protected DMNValidator makeDMNValidator(BuildLogger logger) {
        return new DefaultDMNValidator(logger);
    }

    @Override
    protected DMNTransformer<TestCases> makeDMNTransformer(BuildLogger logger) {
        return new ToSimpleNameTransformer(logger);
    }

    @Override
    protected LazyEvaluationDetector makeLazyEvaluationDetector(Map<String, String> inputParameters, BuildLogger logger) {
        return new NopLazyEvaluationDetector();
    }

    @Override
    protected TypeDeserializationConfigurer makeTypeDeserializationConfigurer(BuildLogger logger) {
        return new DefaultTypeDeserializationConfigurer();
    }

    @Override
    protected Map<String, String> makeInputParameters() {
        LinkedHashMap<String, String> inputParams = new LinkedHashMap<>();
        inputParams.put("environmentFactoryClass", StandardEnvironmentFactory.class.getName());
        inputParams.put("decisionBaseClass", DefaultDMNBaseDecision.class.getName());
        return inputParams;
    }

    @Override
    protected abstract FileTransformer makeTransformer(Path inputModelPath, Map<String, String> inputParameters, BuildLogger logger);

    protected abstract String getDMNInputPath();

    protected abstract String getTestCasesInputPath();

    protected abstract String getExpectedPath();
}
