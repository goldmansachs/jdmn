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

import com.gs.dmn.transformation.repository.InputRepository;
import com.gs.dmn.transformation.repository.OutputRepository;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class InMemoryTestCasesExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryTestCasesExecutor.class);

    private final DMNToJavaTranslator translator;
    private final InMemoryJavaCompiler compiler = new InMemoryJavaCompiler();
    private final InMemoryJUnitRunner junit5Runner = new InMemoryJUnitRunner();

    public InMemoryTestCasesExecutor(DMNToJavaTranslator translator) {
        this.translator = translator;
    }
    public TestRunResult execute(InputRepository inputRepository, OutputRepository outputRepository) throws Exception {
        // DMN and TCK files are in the same folder
        // Output is generated in the same folder
        return execute(inputRepository, inputRepository, outputRepository, outputRepository);
    }

    public TestRunResult execute(InputRepository inputModelRepository, InputRepository inputTestRepository, OutputRepository outputSourceRepository, OutputRepository outputTestRepository) throws Exception {
        // Translate DMN and TCK to Java, compile and run the tests
        Map<String, String> allSources = translate(inputModelRepository, inputTestRepository, outputSourceRepository, outputTestRepository);

        // Compile the generated code and load it in memory
        Map<String, byte[]> classBytes = compile(allSources);

        // Run unit tests and display results
        return runTests(allSources.keySet(), classBytes);
    }

    private Map<String, String> translate(InputRepository inputModelRepository, InputRepository inputTestRepository, OutputRepository outputSourceRepository, OutputRepository outputTestRepository) throws IOException {
        // Generate code for DMN and TCK
        StopWatch watch = new StopWatch();
        watch.start();

        translator.translateDMN(inputModelRepository, outputSourceRepository);
        translator.translateTCK(inputTestRepository, outputTestRepository);

        // Create a map with class name as key and source code as value for all sources and tests
        Map<String, String> allClassesMap = new HashMap<>();

        // Collect source code files
        outputSourceRepository.collectJavaClasses(allClassesMap);

        // Collect test code files
        if (outputSourceRepository != outputTestRepository) {
            outputTestRepository.collectJavaClasses(allClassesMap);
        }

        watch.stop();
        LOGGER.info("Translation executed in {}", watch);
        return allClassesMap;
    }

    private Map<String, byte[]> compile(Map<String, String> sources) {
        StopWatch watch = new StopWatch();
        watch.start();

        Map<String, byte[]> classBytes = this.compiler.compile(sources);

        watch.stop();
        LOGGER.info("Compilation executed in {}", watch);
        return classBytes;
    }

    private TestRunResult runTests(Collection<String> qualifiedClassNames, Map<String, byte[]> classBytes) throws Exception {
        StopWatch watch = new StopWatch();
        watch.start();

        TestRunResult result = junit5Runner.run(qualifiedClassNames, classBytes);

        watch.stop();
        LOGGER.info("Test execution in {}", watch);
        return result;
    }
}
