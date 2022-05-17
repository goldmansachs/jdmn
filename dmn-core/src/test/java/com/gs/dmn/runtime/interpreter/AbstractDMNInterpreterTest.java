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
import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.feel.lib.FEELLib;
import com.gs.dmn.feel.lib.StandardFEELLib;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.runtime.Assert;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.serialization.DMNConstants;
import com.gs.dmn.serialization.DMNSerializer;
import com.gs.dmn.tck.TCKSerializer;
import com.gs.dmn.tck.TCKUtil;
import com.gs.dmn.tck.ast.ResultNode;
import com.gs.dmn.tck.ast.TestCase;
import com.gs.dmn.tck.ast.TestCases;
import com.gs.dmn.tck.serialization.xstream.XMLTCKSerializer;
import com.gs.dmn.transformation.DMNTransformer;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.ToQuotedNameTransformer;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.gs.dmn.tck.TCKSerializer.isTCKFile;

public abstract class AbstractDMNInterpreterTest<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends AbstractTest {
    private static final BuildLogger LOGGER = new Slf4jBuildLogger(LoggerFactory.getLogger(AbstractDMNInterpreterTest.class));
    private static final boolean IGNORE_ERROR_FLAG = true;

    private final DMNSerializer dmnSerializer = this.getDialectDefinition().createDMNSerializer(LOGGER, makeInputParameters());
    private final TCKSerializer tckSerializer = new XMLTCKSerializer(LOGGER, true);

    protected DMNInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> interpreter;
    private DMNTransformer<TestCases> dmnTransformer;
    private BasicDMNToNativeTransformer<Type, DMNContext> basicTransformer;
    private FEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> lib;

    @SafeVarargs
    protected final void doSingleModelTest(String dmnVersion, String dmnFileName, Pair<String, String>... extraInputParameters) {
        doMultipleModelsTest(dmnVersion, Arrays.asList(dmnFileName), extraInputParameters);
    }

    @SafeVarargs
    protected final void doMultipleModelsTest(String dmnVersion, List<String> dmnFileNames, Pair<String, String>... extraInputParameters) {
        String errorMessage = String.format("Tested failed for DM '%s'", dmnFileNames);
        try {
            // Read DMN files
            List<TDefinitions> definitionsList = readModels(dmnVersion, dmnFileNames);
            DMNModelRepository repository = new DMNModelRepository(definitionsList);

            // Read TestCases filers
            Pair<List<String>, List<TestCases>> pair = findTestCases(dmnVersion, dmnFileNames);

            // Transform definitions and test cases
            this.dmnTransformer = new ToQuotedNameTransformer(LOGGER);
            this.dmnTransformer.transform(repository, pair.getRight());

            // Set-up execution
            Map<String, String> inputParameters = makeInputParametersMap(extraInputParameters);
            this.interpreter = getDialectDefinition().createDMNInterpreter(repository, makeInputParameters(inputParameters));
            this.basicTransformer = this.interpreter.getBasicDMNTransformer();
            this.lib = this.interpreter.getFeelLib();

            // Execute tests
            doTest(pair.getLeft(), pair.getRight(), this.interpreter, repository);
        } catch (Exception e) {
            throw new DMNRuntimeException(errorMessage, e);
        }
    }

    @SafeVarargs
    protected final void doMultipleModelsTest(String dmnVersion, String dmnFolderName, String testFolderName, Pair<String, String>... extraInputParameters) {
        String errorMessage = String.format("Tested failed for diagram '%s'", dmnFolderName);
        try {
            // Read DMN files
            File dmnInputFile = new File(tckResource(completePath(getDMNInputPath(), dmnVersion, dmnFolderName) + "/"));
            DMNModelRepository repository = new DMNModelRepository(this.dmnSerializer.readModels(dmnInputFile));

            // Read TestCases filers
            File testCasesInputFile = new File(tckResource(completePath(getTestCasesInputPath(), dmnVersion, testFolderName) + "/"));
            Pair<List<String>, List<TestCases>> pair = findTestCasesInFolder(testCasesInputFile);

            // Transform definitions and test cases
            this.dmnTransformer = new ToQuotedNameTransformer(LOGGER);
            this.dmnTransformer.transform(repository, pair.getRight());

            // Set-up execution
            Map<String, String> inputParameters = makeInputParametersMap(extraInputParameters);
            this.interpreter = getDialectDefinition().createDMNInterpreter(repository, makeInputParameters(inputParameters));
            this.basicTransformer = this.interpreter.getBasicDMNTransformer();
            this.lib = this.interpreter.getFeelLib();

            // Execute tests
            doTest(pair.getLeft(), pair.getRight(), this.interpreter, repository);
        } catch (Exception e) {
            throw new DMNRuntimeException(errorMessage, e);
        }
    }

    private Pair<List<String>, List<TestCases>> findTestCasesInFolder(File testCasesInputFile) {
        List<String> testFileNames = new ArrayList<>();
        List<TestCases> testCasesList = new ArrayList<>();
        for (File child: testCasesInputFile.listFiles()) {
            if (isTCKFile(child)) {
                TestCases testCases = this.tckSerializer.read(child);
                testFileNames.add(child.getName());
                testCasesList.add(testCases);
            }
        }
        return new Pair<>(testFileNames, testCasesList);
    }

    private Pair<List<String>, List<TestCases>> findTestCases(String dmnVersion, List<String> dmnFileNames) throws Exception {
        String testName = dmnFileNames.get(0);
        List<String> testFileNames = new ArrayList<>();
        List<TestCases> testCasesList = new ArrayList<>();
        for (String dmnFileName: dmnFileNames) {
            URL testInputPathURL = tckResource(completePath(getTestCasesInputPath(), dmnVersion, testName)).toURL();
            File testInputPathFolder = new File(testInputPathURL.getFile());
            for (File child: testInputPathFolder.listFiles()) {
                if (isTCKFile(child) && child.getName().startsWith(dmnFileName)) {
                    TestCases testCases = this.tckSerializer.read(child);
                    testFileNames.add(child.getName());
                    testCasesList.add(testCases);
                }
            }
        }
        return new Pair<>(testFileNames, testCasesList);
    }

    protected void doTest(List<String> testCaseFileNameList, List<TestCases> testCasesList, DMNInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> interpreter, DMNModelRepository repository) {
        // Check all TestCases
        Pair<DMNModelRepository, List<TestCases>> result = this.dmnTransformer.transform(repository, testCasesList);
        List<TestCases> actualTestCasesList = result.getRight();
        for (int i = 0; i < actualTestCasesList.size(); i++) {
            String testCaseFileName = testCaseFileNameList.get(i);
            TestCases testCases = actualTestCasesList.get(i);
            for (TestCase testCase: testCases.getTestCase()) {
                doTest(testCaseFileName, testCases, testCase, interpreter);
            }
        }
    }

    private void doTest(String testCaseFileName, TestCases testCases, TestCase testCase, DMNInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> interpreter) {
        TCKUtil<NUMBER, DATE, TIME, DATE_TIME, DURATION> tckUtil = new TCKUtil<>(this.basicTransformer, (StandardFEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION>) this.lib);

        List<ResultNode> resultNode = testCase.getResultNode();
        for (ResultNode res: resultNode) {
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
                if (!IGNORE_ERROR_FLAG) {
                    String errorFlagMessage = String.format("%s ResultNode '%s' error flag mismatch", testLocation, res.getName());
                    Assert.assertEquals(errorFlagMessage, actualResult.hasErrors(), res.isErrorResult());
                }
            } catch (Exception e) {
                String stackTrace = ExceptionUtils.getStackTrace(e);
                LOGGER.error(stackTrace);
                String errorMessage = String.format("%s ResultNode '%s' output mismatch, expected '%s' actual '%s'", testLocation, res.getName(), expectedValue, actualValue);
                org.junit.Assert.fail(errorMessage + ". Exception thrown while testing");
            }
        }
    }

    private List<TDefinitions> readModels(String dmnVersion, List<String> dmnFileNames) throws Exception {
        List<TDefinitions> definitionsList = new ArrayList<>();
        String testName = dmnFileNames.get(0);
        for (String dmnFileName: dmnFileNames) {
            URI dmnFileURI = tckResource(completePath(getDMNInputPath(), dmnVersion, testName) + "/" + dmnFileName + DMNConstants.DMN_FILE_EXTENSION);
            TDefinitions definitions = this.dmnSerializer.readModel(dmnFileURI.toURL());
            definitionsList.add(definitions);
        }
        return definitionsList;
    }

    private InputParameters makeInputParameters(Map<String, String> inputParameters) {
        return new InputParameters(inputParameters);
    }

    private Map<String, String> makeInputParametersMap(Pair<String, String>[] extraInputParameters) {
        Map<String, String> inputParameters = makeInputParametersMap();
        for (Pair<String, String> params: extraInputParameters) {
            inputParameters.put(params.getLeft(), params.getRight());
        }
        return inputParameters;
    }

    protected abstract DMNDialectDefinition<NUMBER, DATE, TIME, DATE_TIME, DURATION, TestCases> getDialectDefinition();

    protected abstract String getDMNInputPath();

    protected abstract String getTestCasesInputPath();
}
