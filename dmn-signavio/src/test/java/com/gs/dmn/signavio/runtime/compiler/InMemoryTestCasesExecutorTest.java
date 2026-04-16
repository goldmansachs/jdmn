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
package com.gs.dmn.signavio.runtime.compiler;

import com.gs.dmn.runtime.compiler.AbstractDMNToJavaTranslatorBuilder;
import com.gs.dmn.runtime.compiler.DMNToJavaTranslator;
import com.gs.dmn.runtime.compiler.InMemoryTestCasesExecutor;
import com.gs.dmn.runtime.compiler.TestRunResult;
import com.gs.dmn.signavio.SignavioTestConstants;
import com.gs.dmn.signavio.testlab.TestLab;
import com.gs.dmn.signavio.testlab.TestLabValidator;
import com.gs.dmn.signavio.transformation.RuleDescriptionTransformer;
import com.gs.dmn.tck.TestCasesToNativeTransformer;
import com.gs.dmn.transformation.CompositeDMNTransformer;
import com.gs.dmn.transformation.DMNToNativeTransformer;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.lazy.SparseDecisionDetector;
import com.gs.dmn.transformation.repository.InputRepository;
import com.gs.dmn.transformation.repository.OutputRepository;
import com.gs.dmn.validation.CompositeDMNValidator;
import com.gs.dmn.validation.DMNValidator;
import com.gs.dmn.validation.TypeRefValidator;
import com.gs.dmn.validation.UniqueNameValidator;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class InMemoryTestCasesExecutorTest {
    // static configuration shared across all instances
    private static final Map<String, String> MAP;
    private static final InputParameters INPUT_PARAMETERS;

    static {
        MAP = new LinkedHashMap<>();
        MAP.put("dmn.version", "1.5");
        MAP.put("model.version", "1.0");
        MAP.put("platform.version", "10.0.0");
        MAP.put("xsdValidation", "false");
        MAP.put("signavioSchemaNamespace", SignavioTestConstants.SIG_EXT_NAMESPACE);
        // Format and TestLab extension is json
        INPUT_PARAMETERS = new InputParameters(MAP);
    }

    @Test
    void testExecuteSharedFolders() throws Exception {
        // Repositories
        Path inputPath = Paths.get("../dmn-test-cases", "signavio/dmn/singleton");
        File inputFile = inputPath.toFile();
        Path outputPath = Paths.get("target", "in-memory", "shared");
        File outputFolder = outputPath.resolve("java").toFile();
        InputRepository inputRepository = new InputRepository(inputFile);
        OutputRepository outputRepository = makeOutputRepository(outputFolder);

        // Run tests
        DMNToJavaTranslator translator = makeTranslator(inputRepository);
        TestRunResult testRunResult = runTests(translator, inputRepository, outputRepository);

        // Check results
        checkTestResults(testRunResult);
    }

    private TestRunResult runTests(DMNToJavaTranslator translator, InputRepository inputRepository, OutputRepository outputRepository) throws Exception {
         // Translate DMN and TCK to Java, compile and run the tests
         return new InMemoryTestCasesExecutor(translator).execute(inputRepository, outputRepository);
    }

    private DMNToJavaTranslator makeTranslator(InputRepository inputModelRepository) {
        return makeTranslator(inputModelRepository, INPUT_PARAMETERS);
    }

    private DMNToJavaTranslator makeTranslator(InputRepository inputModelRepository, InputParameters inputParameters) {
        // Build the translator for DMN and TCK
        DMNValidator dmnValidator = new CompositeDMNValidator(List.of(
//                new DefaultDMNValidator(),
                new TypeRefValidator(),
                new UniqueNameValidator()
        ));
        CompositeDMNTransformer<TestLab> dmnTransformer = new CompositeDMNTransformer<>(List.of(
                new RuleDescriptionTransformer()
        ));
        AbstractDMNToJavaTranslatorBuilder<TestLab> dmnTranslatorBuilder = new SignavioToJavaTranslatorBuilder()
                .withInputParameters(inputParameters)
                .withDMNValidator(dmnValidator)
                .withTestValidator(new TestLabValidator())
                .withDMNTransformer(dmnTransformer)
                .withLazyEvaluationDetector(new SparseDecisionDetector());
        DMNToNativeTransformer dmnTranslator = dmnTranslatorBuilder.buildDMNTranslator();
        TestCasesToNativeTransformer tckTranslator = dmnTranslatorBuilder.buildTestCasesTranslator(inputModelRepository);
        return new DMNToJavaTranslator(dmnTranslator, tckTranslator);
    }

    private void checkTestResults(TestRunResult result) {
        // Expect 2 tests, 1 success, 1 failure
        assertEquals(3, result.getTestsFound(), "testsFound");
        assertEquals(3, result.getTestsSucceeded(), "testsSucceeded");
        assertEquals(0, result.getTestsFailed(), "testsFailed");
        assertEquals(0, result.getTestsAborted(), "testsAborted");
        assertEquals(0, result.getTestsSkipped(), "testsSkipped");
    }

    protected abstract OutputRepository makeOutputRepository(File outputTestFolder);
}