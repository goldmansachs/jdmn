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
package com.gs.dmn.tck;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.serialization.TypeDeserializationConfigurer;
import com.gs.dmn.tck.ast.TestCases;
import com.gs.dmn.tck.serialization.xstream.XMLTCKSerializer;
import com.gs.dmn.transformation.AbstractTestCasesToJUnitTransformer;
import com.gs.dmn.transformation.DMNTransformer;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import com.gs.dmn.transformation.template.TemplateProvider;
import com.gs.dmn.validation.DMNValidator;
import org.apache.commons.lang3.time.StopWatch;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.gs.dmn.serialization.DMNConstants.isTCKFile;

public class TCKTestCasesToJavaJUnitTransformer<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends AbstractTestCasesToJUnitTransformer<NUMBER, DATE, TIME, DATE_TIME, DURATION, TestCases> {
    private final TCKSerializer testCasesReader;
    private final Path inputModelPath;

    public TCKTestCasesToJavaJUnitTransformer(DMNDialectDefinition<NUMBER, DATE, TIME, DATE_TIME, DURATION, TestCases> dialectDefinition, DMNValidator dmnValidator, DMNTransformer<TestCases> dmnTransformer, TemplateProvider templateProvider, LazyEvaluationDetector lazyEvaluationDetector, TypeDeserializationConfigurer typeDeserializationConfigurer, Path inputModelPath, InputParameters inputParameters, BuildLogger logger) {
        super(dialectDefinition, dmnValidator, dmnTransformer, templateProvider, lazyEvaluationDetector, typeDeserializationConfigurer, inputParameters, logger);
        this.inputModelPath = inputModelPath;
        this.testCasesReader = new XMLTCKSerializer(logger, inputParameters);
    }

    protected void collectFiles(Path inputPath, List<File> files) {
        if (Files.isRegularFile(inputPath) && shouldTransformFile(inputPath.toFile())) {
            files.add(inputPath.toFile());
        } else if (Files.isDirectory(inputPath)) {
            // One single level
            try (Stream<Path> stream = Files.list(inputPath)) {
                files.addAll(
                    stream
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .filter(this::shouldTransformFile)
                    .toList()
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected boolean shouldTransformFile(File inputFile) {
        return isTCKFile(inputFile, inputParameters.getTckFileExtension());
    }

    @Override
    protected void transformFiles(List<File> files, File rootFile, Path outputPath) {
        try {
            StopWatch watch = new StopWatch();
            watch.start();

            // Read the models
            DMNModelRepository repository = readModels(inputModelPath.toFile());

            // Read the test cases
            List<TestCases> testCasesList = readTestCases(files);

            // Apply the DMN transformation
            Pair<DMNModelRepository, List<TestCases>> pair = dmnTransformer.transform(repository, testCasesList);
            repository = pair.getLeft();
            testCasesList = pair.getRight();

            // Validate the models
            handleErrors(this.dmnValidator.validate(repository));

            // Translate the test cases to the native platform
            BasicDMNToNativeTransformer<Type, DMNContext> basicTransformer = this.dialectDefinition.createBasicTransformer(repository, lazyEvaluationDetector, inputParameters);
            transformTestCases(testCasesList, basicTransformer, outputPath);

            watch.stop();
            logger.info("TCK processing time: " + watch);
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Error during transforming TCK tests in %s.", rootFile.getName()), e);
        }
    }

    private List<TestCases> readTestCases(List<File> files) {
        List<TestCases> testCasesList = new ArrayList<>();
        for (File file : files) {
            logger.info(String.format("Processing TCK files '%s'", file));
            TestCases testCases = testCasesReader.read(file);
            testCases.setTestCasesName(file.getName());
            testCasesList.add(testCases);
        }
        return testCasesList;
    }

    private void transformTestCases(List<TestCases> testCasesList, BasicDMNToNativeTransformer<Type, DMNContext> basicTransformer, Path outputPath) {
        TCKUtil<NUMBER, DATE, TIME, DATE_TIME, DURATION> tckUtil = new TCKUtil<>(basicTransformer, dialectDefinition.createFEELLib());
        TemplateProvider templateProvider = templateProcessor.getTemplateProvider();
        for (TestCases testCases: testCasesList) {
            String javaClassName = testClassName(testCases, basicTransformer);
            transformTestCase(testCases, templateProvider, basicTransformer, tckUtil, outputPath, javaClassName);
        }
        generateExtra(basicTransformer, basicTransformer.getDMNModelRepository(), outputPath);
    }

    private void transformTestCase(TestCases testCases, TemplateProvider templateProvider, BasicDMNToNativeTransformer<Type, DMNContext> basicTransformer, TCKUtil<NUMBER, DATE, TIME, DATE_TIME, DURATION> tckUtil, Path outputPath, String javaClassName) {
        processTemplate(testCases, templateProvider.testBaseTemplatePath(), templateProvider.testTemplateName(), basicTransformer, tckUtil, outputPath, javaClassName);
    }

    protected void generateExtra(BasicDMNToNativeTransformer<Type, DMNContext> basicTransformer, DMNModelRepository dmnModelRepository, Path outputPath) {
    }

    protected void processTemplate(TestCases testCases, String baseTemplatePath, String templateName, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, TCKUtil<NUMBER, DATE, TIME, DATE_TIME, DURATION> tckUtil, Path outputPath, String testClassName) {
        try {
            // Make output file
            String javaPackageName = dmnTransformer.nativeModelPackageName(testCases.getModelName());
            String relativeFilePath = javaPackageName.replace('.', '/');
            String fileExtension = getFileExtension();
            File outputFile = this.templateProcessor.makeOutputFile(outputPath, relativeFilePath, testClassName, fileExtension);

            // Make parameters
            Map<String, Object> params = makeTemplateParams(testCases, tckUtil);
            params.put("packageName", javaPackageName);
            params.put("testClassName", testClassName);
            params.put("decisionBaseClass", decisionBaseClass);

            // Process template
            this.templateProcessor.processTemplate(baseTemplatePath, templateName, params, outputFile, true);
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot process template '%s' for testCases of '%s'", templateName, testCases.getModelName()), e);
        }
    }

    protected String testClassName(TestCases testCases, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer) {
        String sourceName = testCases.getTestCasesName();
        if (sourceName != null && !sourceName.isEmpty()) {
            sourceName = removeExtension(sourceName, this.inputParameters.getTckFileExtension());
            return testClassName(sourceName, dmnTransformer);
        } else {
            throw new DMNRuntimeException(String.format("Mising TestCases name when testing model '%s'", testCases.getModelName()));
        }
    }

    private static String testClassName(String sourceName, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer) {
        String testName = dmnTransformer.upperCaseFirst(sourceName + "Test");
        if (!Character.isJavaIdentifierStart(sourceName.charAt(0))) {
            return "_" + testName;
        } else {
            return testName;
        }
    }

    private static String removeExtension(String sourceName, String extension) {
        if (sourceName.endsWith(extension)) {
            sourceName = sourceName.substring(0, sourceName.length() - extension.length());
        }
        return sourceName;
    }

    private Map<String, Object> makeTemplateParams(TestCases testCases, TCKUtil<NUMBER, DATE, TIME, DATE_TIME, DURATION> tckUtil) {
        Map<String, Object> params = new HashMap<>();
        params.put("testCases", testCases);
        params.put("tckUtil", tckUtil);
        return params;
    }

    @Override
    protected String getFileExtension() {
        return ".java";
    }
}
