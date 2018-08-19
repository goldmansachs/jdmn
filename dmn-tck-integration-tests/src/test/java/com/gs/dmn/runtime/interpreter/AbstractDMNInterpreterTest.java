/**
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

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.feel.analysis.semantics.environment.Environment;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.lib.FEELLib;
import com.gs.dmn.feel.lib.StandardFEELLib;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.runtime.Assert;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.runtime.interpreter.environment.RuntimeEnvironment;
import com.gs.dmn.serialization.DMNConstants;
import com.gs.dmn.serialization.DMNNamespacePrefixMapper;
import com.gs.dmn.serialization.DMNReader;
import com.gs.dmn.serialization.DMNWriter;
import com.gs.dmn.tck.TCKUtil;
import com.gs.dmn.tck.TestCasesReader;
import com.gs.dmn.transformation.DMNTransformer;
import com.gs.dmn.transformation.ToSimpleNameTransformer;
import com.gs.dmn.transformation.basic.BasicDMN2JavaTransformer;
import org.omg.dmn.tck.marshaller._20160719.TestCases;
import org.omg.dmn.tck.marshaller._20160719.TestCases.TestCase;
import org.omg.dmn.tck.marshaller._20160719.TestCases.TestCase.ResultNode;
import org.omg.spec.dmn._20180521.model.TDecision;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertTrue;

public abstract class AbstractDMNInterpreterTest {
    private static final BuildLogger LOGGER = new Slf4jBuildLogger(LoggerFactory.getLogger(AbstractDMNInterpreterTest.class));

    private final DMNReader reader = new DMNReader(LOGGER, false);
    private final TestCasesReader testCasesReader = new TestCasesReader(LOGGER);

    protected DMNInterpreter interpreter;
    private DMNTransformer dmnTransformer;
    private BasicDMN2JavaTransformer basicTransformer;
    private FEELLib lib;

    protected void doTestDiagram(String dmnFileName, String... testSuffixes) {
        String errorMessage = String.format("Tested failed for diagram '%s'", dmnFileName);
        try {
            // Read DMN file
            String dmnPathName = getDMNInputPath() + "/" + dmnFileName + DMNConstants.DMN_FILE_EXTENSION;
            URL dmnFileURL = getClass().getClassLoader().getResource(dmnPathName).toURI().toURL();
            DMNModelRepository repository = reader.read(dmnFileURL);

            // Transform definitions
            dmnTransformer = new ToSimpleNameTransformer(LOGGER);
            repository = dmnTransformer.transform(repository);
            new DMNWriter(LOGGER).write(repository.getDefinitions(), new File("C:/Work/Projects/Temp.dmn"), new DMNNamespacePrefixMapper());

            // Set-up execution
            this.interpreter = getDialectDefinition().createDMNInterpreter(repository);
            this.basicTransformer = interpreter.getBasicDMNTransformer();
            this.lib = interpreter.getFeelLib();

            // Check test files
            if (testSuffixes == null || testSuffixes.length == 0) {
                URL inputPathURL = getClass().getClassLoader().getResource(getTestCasesInputPath()).toURI().toURL();
                File inputPathFolder = new File(inputPathURL.getFile());
                for (File child : inputPathFolder.listFiles()) {
                    if (child.getName().endsWith(TestCasesReader.TEST_FILE_EXTENSION) && child.getName().startsWith(dmnFileName)) {
                        TestCases testCases = testCasesReader.read(child);
                        doTest(child.getName(), interpreter, repository, testCases);
                    }
                }
            } else {
                for (String testSuffix : testSuffixes) {
                    String testPathName = getTestCasesInputPath() + "/" + dmnFileName + testSuffix + TestCasesReader.TEST_FILE_EXTENSION;
                    URL testURL = getClass().getClassLoader().getResource(testPathName).toURI().toURL();
                    TestCases testCases = testCasesReader.read(testURL);
                    doTest(new File(testURL.getFile()).getName(), interpreter, repository, testCases);
                }
            }
        } catch (Exception e) {
            throw new DMNRuntimeException(errorMessage, e);
        }
    }

    protected void doTest(String testCaseFileName, DMNInterpreter interpreter, DMNModelRepository repository, TestCases testCases) {
        // Check all TestCases
        Pair<DMNModelRepository, TestCases> result = dmnTransformer.transform(repository, testCases);
        for (TestCase testCase : result.getRight().getTestCase()) {
            doTest(testCaseFileName, interpreter, result.getLeft(), testCase);
        }
    }

    private void doTest(String testCaseFileName, DMNInterpreter interpreter, DMNModelRepository repository, TestCase testCase) {
        TCKUtil tckUtil = new TCKUtil(basicTransformer, (StandardFEELLib) lib);
        RuntimeEnvironment runtimeEnvironment = tckUtil.makeEnvironment(testCase);

        List<ResultNode> resultNode = testCase.getResultNode();
        for (ResultNode res : resultNode) {
            Object expectedValue = null;
            Object actualOutput = null;
            String message = String.format("Unexpected result in test case in file '%s' for result node '%s'", testCaseFileName, res.getName());
            try {
                String decisionName = res.getName();
                TDecision decision = (TDecision) basicTransformer.getDMNModelRepository().findDRGElementByName(decisionName);
                Environment environment = basicTransformer.makeEnvironment(decision);
                Type decisionType = basicTransformer.drgElementOutputFEELType(decision, environment);
                expectedValue = tckUtil.makeValue(res.getExpected(), decisionType);
                actualOutput = interpreter.evaluate(decisionName, runtimeEnvironment);
            } catch (Exception e) {
                if (!res.isErrorResult()) {
                    e.printStackTrace();
                    DMNRuntimeException dmnRuntimeException = new DMNRuntimeException(message, e);
                    assertTrue(dmnRuntimeException.getMessage() + ". " + e.getMessage() + String.format(".  Expected '%s' actual '%s'", expectedValue, actualOutput), res.isErrorResult());
                }
            }
            Assert.assertEquals(message, expectedValue, actualOutput);
        }
    }

    protected abstract DMNDialectDefinition getDialectDefinition();

    protected abstract String getDMNInputPath();

    protected abstract String getTestCasesInputPath();
}
