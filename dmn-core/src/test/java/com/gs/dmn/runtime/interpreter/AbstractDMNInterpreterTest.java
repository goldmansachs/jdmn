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
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.runtime.Assert;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
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
import org.junit.jupiter.api.Assertions;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.gs.dmn.serialization.DMNConstants.isDMNFile;
import static com.gs.dmn.serialization.DMNConstants.isTCKFile;

public abstract class AbstractDMNInterpreterTest<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends AbstractTest {
    private static final BuildLogger LOGGER = new Slf4jBuildLogger(LoggerFactory.getLogger(AbstractDMNInterpreterTest.class));
    private static final boolean IGNORE_ERROR_FLAG = true;

    private final DMNSerializer dmnSerializer = this.getDialectDefinition().createDMNSerializer(LOGGER, this.inputParameters);
    private final TCKSerializer tckSerializer = new XMLTCKSerializer(LOGGER, this.inputParameters);

    protected DMNInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> interpreter;
    private DMNTransformer<TestCases> dmnTransformer;
    private BasicDMNToNativeTransformer<Type, DMNContext> basicTransformer;
    private FEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> lib;

    @SafeVarargs
    protected final void doFolderTest(String dmnVersion, String folderName, Pair<String, String>... extraInputParameters) {
        String errorMessage = String.format("Tested failed for '%s'", folderName);
        try {
            // Read DMN files
            List<TDefinitions> definitionsList = readModels(dmnVersion, folderName);
            DMNModelRepository repository = new DMNModelRepository(definitionsList);

            // Read TestCases files
            Pair<List<String>, List<TestCases>> pair = readTestCases(dmnVersion, folderName);

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

    private Pair<List<String>, List<TestCases>> readTestCases(String dmnVersion, String modelFolder) throws Exception {
        List<String> testFileNames = new ArrayList<>();
        List<TestCases> testCasesList = new ArrayList<>();
        URL testInputPathURL = tckResource(completePath(getTestCasesInputPath(), dmnVersion, modelFolder)).toURL();
        File testInputPathFolder = new File(testInputPathURL.getFile());
        for (File child : Objects.requireNonNull(testInputPathFolder.listFiles())) {
            if (isTCKFile(child, this.inputParameters.getTckFileExtension())) {
                TestCases testCases = this.tckSerializer.read(child);
                testFileNames.add(child.getName());
                testCasesList.add(testCases);
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
            for (TestCase testCase : testCases.getTestCase()) {
                doTest(testCaseFileName, testCases, testCase, interpreter);
            }
        }
    }

    private void doTest(String testCaseFileName, TestCases testCases, TestCase testCase, DMNInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> interpreter) {
        TCKUtil<NUMBER, DATE, TIME, DATE_TIME, DURATION> tckUtil = new TCKUtil<>(this.basicTransformer, this.lib);

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
                if (!IGNORE_ERROR_FLAG) {
                    String errorFlagMessage = String.format("%s ResultNode '%s' error flag mismatch", testLocation, res.getName());
                    Assert.assertEquals(errorFlagMessage, res.isErrorResult(), actualResult.hasErrors());
                }
            } catch (Exception e) {
                String stackTrace = ExceptionUtils.getStackTrace(e);
                LOGGER.error(stackTrace);
                String errorMessage = String.format("%s ResultNode '%s' output mismatch, expected '%s' actual '%s'", testLocation, res.getName(), expectedValue, actualValue);
                Assertions.fail(errorMessage + ". Exception thrown while testing");
            }
        }
    }

    private List<TDefinitions> readModels(String dmnVersion, String modelFolder) throws Exception {
        List<TDefinitions> definitionsList = new ArrayList<>();
        URL modelInputPathURL = tckResource(completePath(getDMNInputPath(), dmnVersion, modelFolder)).toURL();
        File modelInputPathFolder = new File(modelInputPathURL.getFile());
        for (File child : Objects.requireNonNull(modelInputPathFolder.listFiles())) {
            if (isDMNFile(child, this.inputParameters.getDmnFileExtension())) {
                TDefinitions definitions = this.dmnSerializer.readModel(child);
                definitionsList.add(definitions);
            }
        }
        return definitionsList;
    }

    private InputParameters makeInputParameters(Map<String, String> inputParameters) {
        return new InputParameters(inputParameters);
    }

    private Map<String, String> makeInputParametersMap(Pair<String, String>[] extraInputParameters) {
        Map<String, String> inputParameters = makeInputParametersMap();
        for (Pair<String, String> params : extraInputParameters) {
            inputParameters.put(params.getLeft(), params.getRight());
        }
        return inputParameters;
    }

    protected abstract DMNDialectDefinition<NUMBER, DATE, TIME, DATE_TIME, DURATION, TestCases> getDialectDefinition();

    protected abstract String getDMNInputPath();

    protected abstract String getTestCasesInputPath();
}
