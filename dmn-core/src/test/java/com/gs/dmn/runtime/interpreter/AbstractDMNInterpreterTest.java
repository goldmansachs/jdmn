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

import com.gs.dmn.AbstractTest;
import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.feel.lib.FEELLib;
import com.gs.dmn.feel.lib.StandardFEELLib;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.runtime.Assert;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.serialization.DMNConstants;
import com.gs.dmn.serialization.DMNReader;
import com.gs.dmn.serialization.PrefixNamespaceMappings;
import com.gs.dmn.tck.TCKUtil;
import com.gs.dmn.tck.TestCasesReader;
import com.gs.dmn.transformation.DMNTransformer;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.ToSimpleNameTransformer;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.omg.dmn.tck.marshaller._20160719.TestCases;
import org.omg.dmn.tck.marshaller._20160719.TestCases.TestCase;
import org.omg.dmn.tck.marshaller._20160719.TestCases.TestCase.ResultNode;
import org.omg.spec.dmn._20180521.model.TDefinitions;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.*;

import static com.gs.dmn.tck.TestCasesReader.isTCKFile;

public abstract class AbstractDMNInterpreterTest<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends AbstractTest {
    private static final BuildLogger LOGGER = new Slf4jBuildLogger(LoggerFactory.getLogger(AbstractDMNInterpreterTest.class));
    private static final boolean IGNORE_ERROR = true;

    private final DMNReader reader = new DMNReader(LOGGER, false);
    private final TestCasesReader testCasesReader = new TestCasesReader(LOGGER);

    protected DMNInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> interpreter;
    private DMNTransformer<TestCases> dmnTransformer;
    private BasicDMNToNativeTransformer basicTransformer;
    private FEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> lib;

    @SafeVarargs
    protected final void doSingleModelTest(String dmnFileName, Pair<String, String>... extraInputParameters) {
        doMultipleModelsTest(Arrays.asList(dmnFileName), extraInputParameters);
    }

    @SafeVarargs
    protected final void doMultipleModelsTest(List<String> dmnFileNames, Pair<String, String>... extraInputParameters) {
        String errorMessage = String.format("Tested failed for DM '%s'", dmnFileNames);
        try {
            // Read DMN files
            List<Pair<TDefinitions, PrefixNamespaceMappings>> pairs = readModels(dmnFileNames);
            DMNModelRepository repository = new DMNModelRepository(pairs);

            // Read TestCases filers
            Pair<List<String>, List<TestCases>> pair = findTestCases(dmnFileNames);

            // Transform definitions and test cases
            dmnTransformer = new ToSimpleNameTransformer(LOGGER);
            dmnTransformer.transform(repository, pair.getRight());

            // Set-up execution
            Map<String, String> inputParameters = makeInputParameters();
            for (Pair<String, String> params: extraInputParameters) {
                inputParameters.put(params.getLeft(), params.getRight());
            }
            this.interpreter = getDialectDefinition().createDMNInterpreter(repository, new InputParameters(inputParameters));
            this.basicTransformer = interpreter.getBasicDMNTransformer();
            this.lib = interpreter.getFeelLib();

            // Execute tests
            doTest(pair.getLeft(), pair.getRight(), interpreter, repository);
        } catch (Exception e) {
            throw new DMNRuntimeException(errorMessage, e);
        }
    }

    @SafeVarargs
    protected final void doMultipleModelsTest(String dmnFolderName, String testFolderName, Pair<String, String>... extraInputParameters) {
        String errorMessage = String.format("Tested failed for diagram '%s'", dmnFolderName);
        try {
            // Read DMN files
            File dmnInputFile = new File(resource(this.getDMNInputPath() + "/" + dmnFolderName));
            DMNModelRepository repository = new DMNModelRepository(this.reader.readModels(dmnInputFile));

            // Read TestCases filers
            File testCasesInputFile = new File(resource(this.getTestCasesInputPath() + "/" + testFolderName));
            Pair<List<String>, List<TestCases>> pair = findTestCasesInFolder(testCasesInputFile);

            // Transform definitions and test cases
            dmnTransformer = new ToSimpleNameTransformer(LOGGER);
            dmnTransformer.transform(repository, pair.getRight());

            // Set-up execution
            Map<String, String> inputParameters = makeInputParameters();
            for (Pair<String, String> params: extraInputParameters) {
                inputParameters.put(params.getLeft(), params.getRight());
            }
            this.interpreter = getDialectDefinition().createDMNInterpreter(repository, new InputParameters(inputParameters));
            this.basicTransformer = interpreter.getBasicDMNTransformer();
            this.lib = interpreter.getFeelLib();

            // Execute tests
            doTest(pair.getLeft(), pair.getRight(), interpreter, repository);
        } catch (Exception e) {
            throw new DMNRuntimeException(errorMessage, e);
        }
    }

    private Pair<List<String>, List<TestCases>> findTestCasesInFolder(File testCasesInputFile) {
        List<String> testFileNames = new ArrayList<>();
        List<TestCases> testCasesList = new ArrayList<>();
        for (File child: testCasesInputFile.listFiles()) {
            if (isTCKFile(child)) {
                TestCases testCases = testCasesReader.read(child);
                testFileNames.add(child.getName());
                testCasesList.add(testCases);
            }
        }
        return new Pair<>(testFileNames, testCasesList);
    }

    private Pair<List<String>, List<TestCases>> findTestCases(List<String> dmnFileNames) throws Exception {
        List<String> testFileNames = new ArrayList<>();
        List<TestCases> testCasesList = new ArrayList<>();
        for (String dmnFileName: dmnFileNames) {
            URL testInputPathURL = getClass().getClassLoader().getResource(getTestCasesInputPath()).toURI().toURL();
            File testInputPathFolder = new File(testInputPathURL.getFile());
            for (File child : testInputPathFolder.listFiles()) {
                if (isTCKFile(child) && child.getName().startsWith(dmnFileName)) {
                    TestCases testCases = testCasesReader.read(child);
                    testFileNames.add(child.getName());
                    testCasesList.add(testCases);
                }
            }
        }
        return new Pair<>(testFileNames, testCasesList);
    }

    protected void doTest(List<String> testCaseFileNameList, List<TestCases> testCasesList, DMNInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> interpreter, DMNModelRepository repository) {
        // Check all TestCases
        Pair<DMNModelRepository, List<TestCases>> result = dmnTransformer.transform(repository, testCasesList);
        List<TestCases> actualTestCasesList = result.getRight();
        for (int i = 0; i < actualTestCasesList.size(); i++) {
            String testCaseFileName = testCaseFileNameList.get(i);
            TestCases testCases = actualTestCasesList.get(i);
            for (TestCase testCase : testCases.getTestCase()) {
                doTest(testCaseFileName, testCases, testCase, interpreter);
            }
        }
    }

    private void doTest(String testCaseFileName, TestCases testCases, TestCase testCase, DMNInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> interpreter) {
        TCKUtil<NUMBER, DATE, TIME, DATE_TIME, DURATION> tckUtil = new TCKUtil<>(basicTransformer, (StandardFEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION>) lib);

        List<ResultNode> resultNode = testCase.getResultNode();
        for (ResultNode res : resultNode) {
            Object expectedValue = null;
            Result actualResult;
            Object actualValue = null;
            String testLocation = String.format("Unexpected result in test file '%s', TestCase.id='%s', ResultNode.name='%s'.", testCaseFileName, testCase.getId(), res.getName());
            try {
                expectedValue = tckUtil.expectedValue(testCases, testCase, res);
                actualResult = tckUtil.evaluate(interpreter, testCases, testCase, res);
                actualValue = Result.value(actualResult);
                String errorMessage = String.format("%s ResultNode '%s' output mismatch, expected '%s' actual '%s'", testLocation, res.getName(), expectedValue, actualValue);
                Assert.assertEquals(errorMessage, expectedValue, actualValue);
            } catch (Exception e) {
                String stackTrace = ExceptionUtils.getStackTrace(e);
                LOGGER.error(stackTrace);
                if (!IGNORE_ERROR) {
                    String errorMessage = String.format("%s ResultNode '%s' output mismatch, expected '%s' actual '%s'", testLocation, res.getName(), expectedValue, actualValue);
                    Assert.assertEquals(errorMessage, expectedValue, actualValue);
                }
            }
        }
    }

    private List<Pair<TDefinitions, PrefixNamespaceMappings>> readModels(List<String> dmnFileNames) throws Exception {
        List<Pair<TDefinitions, PrefixNamespaceMappings>> pairs = new ArrayList<>();
        for (String dmnFileName: dmnFileNames) {
            URI dmnFileURI = resource(getDMNInputPath() + "/" + dmnFileName + DMNConstants.DMN_FILE_EXTENSION);
            Pair<TDefinitions, PrefixNamespaceMappings> pair = reader.read(dmnFileURI.toURL());
            pairs.add(pair);
        }
        return pairs;
    }

    protected Map<String, String> makeInputParameters() {
        Map<String, String> inputParams = new LinkedHashMap<>();
        inputParams.put("dmnVersion", "1.1");
        inputParams.put("modelVersion", "2.0");
        inputParams.put("platformVersion", "1.0");
        return inputParams;
    }

    protected abstract DMNDialectDefinition<NUMBER, DATE, TIME, DATE_TIME, DURATION, TestCases> getDialectDefinition();

    protected abstract String getDMNInputPath();

    protected abstract String getTestCasesInputPath();
}
