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
package com.gs.dmn.runtime.compiler;

import com.gs.dmn.error.SemanticErrorException;
import com.gs.dmn.error.SyntaxErrorException;
import com.gs.dmn.runtime.compiler.listener.ExecutionListener;
import com.gs.dmn.runtime.compiler.listener.ExecutionTimer;
import com.gs.dmn.runtime.compiler.listener.NopExecutionListener;
import com.gs.dmn.serialization.DMNConstants;
import com.gs.dmn.serialization.SerializationFormat;
import com.gs.dmn.tck.TestCasesToNativeTransformer;
import com.gs.dmn.tck.ast.TestCases;
import com.gs.dmn.tck.validation.DefaultTCKValidator;
import com.gs.dmn.transformation.CompositeDMNTransformer;
import com.gs.dmn.transformation.DMNToNativeTransformer;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.ToQuotedNameTransformer;
import com.gs.dmn.transformation.lazy.SparseDecisionDetector;
import com.gs.dmn.transformation.repository.InputRepository;
import com.gs.dmn.transformation.repository.OutputRepository;
import com.gs.dmn.validation.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.gs.dmn.runtime.compiler.listener.ExecutionTimer.*;
import static org.junit.jupiter.api.Assertions.*;

public abstract class InMemoryTestCasesExecutorTest {
    // static configuration shared across all instances
    private static final Map<String, String> MAP;
    private static final Map<String, String> TCK_MAP;
    private static final InputParameters INPUT_PARAMETERS;
    private static final InputParameters TCK_INPUT_PARAMETERS;

    static {
        MAP = new LinkedHashMap<>();
        MAP.put("dmn.version", "1.5");
        MAP.put("model.version", "1.0");
        MAP.put("platform.version", "10.0.0");
        MAP.put("xsdValidation", "true");

        TCK_MAP = new LinkedHashMap<>(MAP);
        TCK_MAP.put(InputParameters.TCK_FORMAT_KEY, SerializationFormat.XML.name());
        TCK_MAP.put(InputParameters.TCK_FILE_EXTENSION_KEY, DMNConstants.TCK_FILE_EXTENSION);

        INPUT_PARAMETERS = new InputParameters(MAP);
        TCK_INPUT_PARAMETERS = new InputParameters(TCK_MAP);
    }

    @Test
    void testExecuteSeparateFolders() throws Exception {
        // Input path
        Path inputPath = Paths.get("../dmn-test-cases", "standard/tck/1.3/cl3/0020-vacation-days/translator");
        File inputModelFile = inputPath.toFile();
        File inputTestFile = inputPath.resolve("pure").toFile();

        // Output path
        Path outputPath = Paths.get("target", "in-memory", "separated");
        File outputSourceFolder = outputPath.resolve("dmn").toFile();
        File outputTestFolder = outputPath.resolve("tck").toFile();

        // Repositories
        InputRepository inputModelRepository = new InputRepository(inputModelFile);
        InputRepository inputTestRepository = new InputRepository(inputTestFile);
        OutputRepository outputSourceRepository = makeOutputRepository(outputSourceFolder);
        OutputRepository outputTestRepository = makeOutputRepository(outputTestFolder);

        // Run tests
        DMNToJavaTranslator translator = makeTranslator(inputModelRepository);
        TestRunResult testRunResult = runTests(translator, inputModelRepository, inputTestRepository, outputSourceRepository, outputTestRepository, new NopExecutionListener());

        // Check results
        checkTestResults(testRunResult);
    }

    @Test
    void testExecuteSharedFolders() throws Exception {
        // Repositories
        Path inputPath = Paths.get("../dmn-test-cases", "standard/tck/1.4/cl3/0020-vacation-days");
        File inputFile = inputPath.toFile();
        Path outputPath = Paths.get("target", "in-memory", "shared");
        File outputFolder = outputPath.resolve("java").toFile();
        InputRepository inputRepository = new InputRepository(inputFile);
        OutputRepository outputRepository = makeOutputRepository(outputFolder);

        // Run tests
        DMNToJavaTranslator translator = makeTranslator(inputRepository);
        TestRunResult testRunResult = runTests(translator, inputRepository, outputRepository, new NopExecutionListener());

        // Check results
        checkTestResults(testRunResult);
    }

    @Test
    void testExecuteWithMockedDecisions() throws Exception {
        // Repositories
        Path inputPath = Paths.get("../dmn-test-cases", "standard/tck/1.5/cl3/1162-import-same-name/translator/");
        File inputFile = inputPath.toFile();
        Path outputPath = Paths.get("target", "in-memory", "shared");
        File outputFolder = outputPath.resolve("java").toFile();
        InputRepository inputRepository = new InputRepository(inputFile);
        OutputRepository outputRepository = makeOutputRepository(outputFolder);

        // Run tests
        DMNToJavaTranslator translator = makeTranslator(inputRepository);
        TestRunResult testRunResult = runTests(translator, inputRepository, outputRepository, new NopExecutionListener());

        // Check results
        assertEquals(0, testRunResult.getFailures().size());
    }

    @Test
    void testExecuteSharedFoldersWithListener() throws Exception {
        // Repositories
        Path inputPath = Paths.get("../dmn-test-cases", "standard/tck/1.4/cl3/0020-vacation-days");
        File inputFile = inputPath.toFile();
        Path outputPath = Paths.get("target", "in-memory", "shared");
        File outputFolder = outputPath.resolve("java").toFile();
        InputRepository inputRepository = new InputRepository(inputFile);
        OutputRepository outputRepository = makeOutputRepository(outputFolder);

        // Run tests
        DMNToJavaTranslator translator = makeTranslator(inputRepository);
        ExecutionTimer listener = new ExecutionTimer();
        TestRunResult testRunResult = runTests(translator, inputRepository, outputRepository, listener);

        // Check results
        checkTestResults(testRunResult);

        // Check listener
        Duration dmnTranslationDuration = listener.getAccumulatedDuration(DMN_TRANSLATION);
        Duration testCasesTranslationDuration = listener.getAccumulatedDuration(TEST_CASES_TRANSLATION);
        Duration compilationDuration = listener.getAccumulatedDuration(COMPILATION);
        Duration testExecutionDuration = listener.getAccumulatedDuration(TEST_EXECUTION);
        assertTrue(dmnTranslationDuration.toNanos() > 0);
        assertTrue(testCasesTranslationDuration.toNanos() > 0);
        assertTrue(compilationDuration.toNanos() > 0);
        assertTrue(testExecutionDuration.toNanos() > 0);
    }

    @Test
    void testInvalidDMNFiles() {
        // Missing DMN file
        assertThrows(IllegalArgumentException.class, () -> {
            runTests("missing-dmn.dmn", "dmn-error-1");
        });

        // DMN file with schema error
        assertThrows(SyntaxErrorException.class, () -> {
            runTests("invalid-schema.dmn", "dmn-error-2");
        });

        // DMN file with FEEL syntax error
        assertThrows(SemanticErrorException.class, () -> {
            runTests("feel-syntax-error.dmn", "dmn-error-3");
        });

        // Correct
        assertDoesNotThrow(() -> {
            runTests("correct.dmn", "dmn-error-4");
        });
    }

    @Test
    void testInvalidTCKFiles() {
        Path inputPath = Paths.get("src/test/resources/compiler", "correct.dmn");
        File inputModelFile = inputPath.toFile();

        // TCK file with schema error
        assertThrows(SyntaxErrorException.class, () -> {
            runTests(inputModelFile, "invalid-schema.tck", "tck-error-1");
        });

        // TCK file with invalid model name
        assertThrows(SemanticErrorException.class, () -> {
            runTests(inputModelFile, "invalid-model-name.tck", "tck-error-2");
        });

        // TCK file with duplicate IDs
        assertThrows(SemanticErrorException.class, () -> {
            runTests(inputModelFile, "duplicate-ids.tck", "tck-error-3");
        });

        // Correct
        assertDoesNotThrow(() -> {
            runTests(inputModelFile, "correct.tck", "tck-error-4");
        });
    }

    private void runTests(String dmnFileName, String outputFileName) throws Exception {
        Path inputPath = Paths.get("src/test/resources/compiler", dmnFileName);
        File inputFile = inputPath.toFile();
        Path outputPath = Paths.get("target", "in-memory", outputFileName);
        InputRepository inputRepository = new InputRepository(inputFile);
        OutputRepository outputRepository = makeOutputRepository(outputPath.toFile());

        DMNToJavaTranslator translator = makeTranslator(inputRepository);
        runTests(translator, inputRepository, outputRepository, new NopExecutionListener());
    }

    private void runTests(File inputModelFile, String tckFileName, String outputFileName) throws Exception {
        // Repositories
        Path inputPath = Paths.get("src/test/resources/compiler", tckFileName);
        File inputTestFile = inputPath.toFile();
        Path outputPath = Paths.get("target", "in-memory", outputFileName);
        File outputFolder = outputPath.toFile();
        InputRepository inputModelRepository = new InputRepository(inputModelFile);
        InputRepository inputTestRepository = new InputRepository(inputTestFile);
        OutputRepository outputSourceRepository = makeOutputRepository(outputFolder);
        OutputRepository outputTestRepository = makeOutputRepository(outputFolder);

        // Run tests
        DMNToJavaTranslator translator = makeTranslator(inputModelRepository, TCK_INPUT_PARAMETERS);
        runTests(translator, inputModelRepository, inputTestRepository, outputSourceRepository, outputTestRepository, new NopExecutionListener());
    }

    private TestRunResult runTests(DMNToJavaTranslator translator, InputRepository inputRepository, OutputRepository outputRepository, ExecutionListener listener) throws Exception {
         // Translate DMN and TCK to Java, compile and run the tests
         return new InMemoryTestCasesExecutor(translator).execute(inputRepository, outputRepository, listener);
    }

    private TestRunResult runTests(DMNToJavaTranslator translator, InputRepository inputModelRepository, InputRepository inputTestRepository, OutputRepository outputSourceRepository, OutputRepository outputTestRepository, ExecutionListener listener) throws Exception {
        // Translate DMN and TCK to Java, compile and run the tests
        return new InMemoryTestCasesExecutor(translator).execute(inputModelRepository, inputTestRepository, outputSourceRepository, outputTestRepository, listener);
    }

    private DMNToJavaTranslator makeTranslator(InputRepository inputModelRepository) {
        return makeTranslator(inputModelRepository, INPUT_PARAMETERS);
    }

    private DMNToJavaTranslator makeTranslator(InputRepository inputModelRepository, InputParameters inputParameters) {
        // Build the translator for DMN and TCK
        DMNValidator dmnValidator = new CompositeDMNValidator(List.of(
                new DefaultDMNValidator(),
                new TypeRefValidator(),
                new UniqueNameValidator()
        ));
        CompositeDMNTransformer<TestCases> dmnTransformer = new CompositeDMNTransformer<>(List.of(
                new ToQuotedNameTransformer()
        ));
        AbstractDMNToJavaTranslatorBuilder<TestCases> dmnTranslatorBuilder = new DMNToJavaTranslatorBuilder()
                .withInputParameters(inputParameters)
                .withDMNValidator(dmnValidator)
                .withTestValidator(new DefaultTCKValidator())
                .withDMNTransformer(dmnTransformer)
                .withLazyEvaluationDetector(new SparseDecisionDetector());
        DMNToNativeTransformer dmnTranslator = dmnTranslatorBuilder.buildDMNTranslator();
        TestCasesToNativeTransformer tckTranslator = dmnTranslatorBuilder.buildTestCasesTranslator(inputModelRepository);
        return new DMNToJavaTranslator(dmnTranslator, tckTranslator);
    }

    private void checkTestResults(TestRunResult result) {
        // Expect 2 tests, 1 success, 1 failure
        assertEquals(7, result.getTestsFound(), "testsFound");
        assertEquals(7, result.getTestsSucceeded(), "testsSucceeded");
        assertEquals(0, result.getTestsFailed(), "testsFailed");
        assertEquals(0, result.getTestsAborted(), "testsAborted");
        assertEquals(0, result.getTestsSkipped(), "testsSkipped");
    }

    protected abstract OutputRepository makeOutputRepository(File outputTestFolder);
}