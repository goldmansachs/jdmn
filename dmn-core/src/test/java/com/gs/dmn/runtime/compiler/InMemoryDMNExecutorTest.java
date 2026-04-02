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

import com.gs.dmn.tck.TestCasesToNativeTransformer;
import com.gs.dmn.tck.ast.TestCases;
import com.gs.dmn.transformation.CompositeDMNTransformer;
import com.gs.dmn.transformation.DMNToNativeTransformer;
import com.gs.dmn.transformation.ToQuotedNameTransformer;
import com.gs.dmn.transformation.lazy.SparseDecisionDetector;
import com.gs.dmn.validation.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryDMNExecutorTest {
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

        DMNToJavaTranslator translator = makeTranslator(inputModelFile);
        runTests(translator, inputModelFile, inputTestFile, outputSourceFolder, outputTestFolder);
    }

    @Test
    void testExecuteSharedFolders() throws Exception {
        // Input path
        Path inputPath = Paths.get("../dmn-test-cases", "standard/tck/1.4/cl3/0020-vacation-days");
        File inputFile = inputPath.toFile();

        // Output path
        Path outputPath = Paths.get("target", "in-memory", "shared");
        File outputFolder = outputPath.resolve("java").toFile();

        DMNToJavaTranslator translator = makeTranslator(inputFile);
        runTests(translator, inputFile, outputFolder);
    }

     private void runTests(DMNToJavaTranslator translator, File inputFile, File outputFolder) throws Exception {
         // Translate DMN and TCK to Java, compile and run the tests
         TestRunResult result = new InMemoryDMNExecutor(translator).execute(inputFile, outputFolder);

         // Check results
         checkTestResults(result);
     }

    private void runTests(DMNToJavaTranslator translator, File inputModelFile, File inputTestFile, File outputSourceFolder, File outputTestFolder) throws Exception {
        // Translate DMN and TCK to Java, compile and run the tests
        TestRunResult result = new InMemoryDMNExecutor(translator).execute(inputModelFile, inputTestFile, outputSourceFolder, outputTestFolder);

        // Check results
        checkTestResults(result);
    }

    private DMNToJavaTranslator makeTranslator(File inputModelFile) {
        // Build the translator for DMN and TCK
        DMNValidator dmnValidator = new CompositeDMNValidator(List.of(
                new DefaultDMNValidator(),
                new TypeRefValidator(),
                new UniqueNameValidator()
        ));
        CompositeDMNTransformer<TestCases> dmnTransformer = new CompositeDMNTransformer<>(List.of(
                new ToQuotedNameTransformer()
        ));
        DMNToJavaTranslatorBuilder dmnTranslatorBuilder = new DMNToJavaTranslatorBuilder()
                .withDMNValidator(dmnValidator)
                .withDMNTransformer(dmnTransformer)
                .withLazyEvaluationDetector(new SparseDecisionDetector());
        DMNToNativeTransformer dmnTranslator = dmnTranslatorBuilder.buildDMNTranslator();
        TestCasesToNativeTransformer tckTranslator = dmnTranslatorBuilder.buildTCKTranslator(inputModelFile);
        DMNToJavaTranslator translator = new DMNToJavaTranslator(dmnTranslator, tckTranslator);
        return translator;
    }

    private void checkTestResults(TestRunResult result) {
        // Expect 2 tests, 1 success, 1 failure
        assertEquals(7, result.getTestsFound(), "testsFound");
        assertEquals(7, result.getTestsSucceeded(), "testsSucceeded");
        assertEquals(0, result.getTestsFailed(), "testsFailed");
    }
}