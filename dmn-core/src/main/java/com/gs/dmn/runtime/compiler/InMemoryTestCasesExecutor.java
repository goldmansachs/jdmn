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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class InMemoryTestCasesExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryTestCasesExecutor.class);

    private final DMNToJavaTranslator translator;
    private final InMemoryJavaCompiler compiler = new InMemoryJavaCompiler();
    private final InMemoryJUnitRunner junit5Runner = new InMemoryJUnitRunner();

    public InMemoryTestCasesExecutor(DMNToJavaTranslator translator) {
        this.translator = translator;
    }

    public TestRunResult execute(File inputFile, File outputFolder) throws Exception {
        // DMN and TCK files are in the same folder
        // Output is generated in the same folder
        return execute(inputFile, inputFile, outputFolder, outputFolder);
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

        // Create a map with class name as key and source code as value for all sources and tests
        Map<String, String> allClassesMap = new HashMap<>();

        // Collect source code files
        collectJavaClasses(outputSourceFolder, allClassesMap);

        // Collect test code files
        if (outputSourceFolder != outputTestFolder) {
            collectJavaClasses(outputTestFolder, allClassesMap);
        }

        watch.stop();
        LOGGER.info("Translation executed in {}", watch);
        return allClassesMap;
    }

    // Collects source code from the provided files and puts it in the map with class name as key and source code as value
    private void collectJavaClasses(File outputFolder, Map<String, String> allClassesMap) throws IOException {
        if (outputFolder != null && outputFolder.listFiles() != null) {
            deepCollectClasses(Arrays.asList(outputFolder.listFiles()), "", this::isJavaFile, allClassesMap);
        }
    }

    private void deepCollectClasses(List<File> files, String currentPackage, java.util.function.Predicate<File> predicate, Map<String, String> allClassesMap) throws IOException {
        if (files != null) {
            for (File file : files) {
                deepCollectClasses(file, currentPackage, predicate, allClassesMap);
            }
        }
    }

    private void deepCollectClasses(File file, String currentPackage, java.util.function.Predicate<File> predicate, Map<String, String> allClassesMap) throws IOException {
        if (file.isDirectory()) {
            String childPackage = currentPackage.isEmpty() ? file.getName() : currentPackage + "." + file.getName();
            deepCollectClasses(Arrays.asList(file.listFiles()), childPackage, predicate, allClassesMap);
        } else if (predicate.test(file)) {
            String className = file.getName().replace(".java", "");
            String classQName = StringUtils.isEmpty(currentPackage) ? className : currentPackage + "." + className;
            String classSource = new String(Files.readAllBytes(file.toPath()));
            allClassesMap.put(classQName, classSource);
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
}
