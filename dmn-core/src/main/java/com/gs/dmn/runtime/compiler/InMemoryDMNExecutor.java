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

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class InMemoryDMNExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryDMNExecutor.class);

    private final DMNToJavaTranslator translator;
    private final InMemoryJavaCompiler compiler = new InMemoryJavaCompiler();
    private final InMemoryJUnitRunner junit5Runner = new InMemoryJUnitRunner();

    public InMemoryDMNExecutor(DMNToJavaTranslator translator) {
        this.translator = translator;
    }

    public TestRunResult execute(File inputModelFile, File inputTestFile, File outputSourceFolder, File outputTestFolder) throws Exception {
        // Translate DMN and TCK to Java, compile and run the tests
        Map<String, String> allSources = translate(inputModelFile, inputTestFile, outputSourceFolder, outputTestFolder);

        // Compile the generated code and load it in memory
        Map<String, byte[]> classBytes = compile(allSources);

        // Run unit tests and display results
        return runTests(allSources.keySet(), classBytes);
    }

    private Map<String, String> translate(File inputModelFile, File inputTestFile, File outputSourceFolder, File outputTestFolder) throws IOException {
        // Generate code for DMN and TCK
        StopWatch watch = new StopWatch();
        watch.start();

        translator.translateDMN(inputModelFile, outputSourceFolder);
        translator.translateTCK(inputTestFile, outputTestFolder);

        // Collect source code files
        List<File> sources = new ArrayList<>();
        deepCollectFiles(outputSourceFolder, this::isJavaFile, sources);

        // Collect test code files
        List<File> tests = new ArrayList<>();
        deepCollectFiles(outputTestFolder, this::isJavaFile, tests);

        // Create a map with class name as key and source code as value for all sources and tests
        Map<String, String> allClassesMap = new HashMap<>();
        collectCode(sources, allClassesMap);
        collectCode(tests, allClassesMap);

        watch.stop();
        LOGGER.info("Translation executed in {}", watch);
        return allClassesMap;
    }

    private void deepCollectFiles(File file, java.util.function.Predicate<File> predicate, List<File> sources) {
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                deepCollectFiles(child, predicate, sources);
            }
        } else {
            if (predicate.test(file)) {
                sources.add(file);
            }
        }
    }

    private boolean isJavaFile(File file) {
        return file.isFile() && file.getName().endsWith(".java");
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

    // Collects source code from the provided files and puts it in the map with class name as key and source code as value
    private void collectCode(List<File> sources, Map<String, String> allSources) throws IOException {
        for (File file : sources) {
            String className = file.getName().replace(".java", "");
            String classSource = new String(Files.readAllBytes(file.toPath()));
            allSources.put(className, classSource);
        }
    }
}
