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
    protected final BasicDMNToNativeTransformer<Type, DMNContext> basicTransformer;

    protected final TCKSerializer testCasesReader;
    private final TCKUtil<NUMBER, DATE, TIME, DATE_TIME, DURATION> tckUtil;

    public TCKTestCasesToJavaJUnitTransformer(DMNDialectDefinition<NUMBER, DATE, TIME, DATE_TIME, DURATION, TestCases> dialectDefinition, DMNValidator dmnValidator, DMNTransformer<TestCases> dmnTransformer, TemplateProvider templateProvider, LazyEvaluationDetector lazyEvaluationDetector, TypeDeserializationConfigurer typeDeserializationConfigurer, Path inputModelPath, InputParameters inputParameters, BuildLogger logger) {
        super(dialectDefinition, dmnValidator, dmnTransformer, templateProvider, lazyEvaluationDetector, typeDeserializationConfigurer, inputParameters, logger);
        DMNModelRepository repository = readModels(inputModelPath.toFile());
        this.basicTransformer = this.dialectDefinition.createBasicTransformer(repository, lazyEvaluationDetector, inputParameters);
        handleValidationErrors(this.dmnValidator.validate(repository));
        this.testCasesReader = new XMLTCKSerializer(logger, inputParameters);
        this.tckUtil = new TCKUtil<>(basicTransformer, dialectDefinition.createFEELLib());
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

            List<TestCases> testCasesList = new ArrayList<>();
            for (File file : files) {
                logger.info(String.format("Processing TCK files '%s'", file));
                TestCases testCases = testCasesReader.read(file);
                testCasesList.add(testCases);
            }
            testCasesList = dmnTransformer.transform(basicTransformer.getDMNModelRepository(), testCasesList).getRight();

            TemplateProvider templateProvider = templateProcessor.getTemplateProvider();
            for (TestCases testCases: testCasesList) {
                String javaClassName = testClassName(testCases, basicTransformer);
                processTemplate(testCases, templateProvider.testBaseTemplatePath(), templateProvider.testTemplateName(), basicTransformer, outputPath, javaClassName);
            }

            generateExtra(basicTransformer, basicTransformer.getDMNModelRepository(), outputPath);

            watch.stop();
            logger.info("TCK processing time: " + watch);
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Error during transforming TCK tests in %s.", rootFile.getName()), e);
        }
    }

    protected void generateExtra(BasicDMNToNativeTransformer<Type, DMNContext> basicTransformer, DMNModelRepository dmnModelRepository, Path outputPath) {
    }

    protected void processTemplate(TestCases testCases, String baseTemplatePath, String templateName, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, Path outputPath, String testClassName) {
        try {
            // Make output file
            String javaPackageName = dmnTransformer.nativeModelPackageName(testCases.getModelName());
            String relativeFilePath = javaPackageName.replace('.', '/');
            String fileExtension = getFileExtension();
            File outputFile = this.templateProcessor.makeOutputFile(outputPath, relativeFilePath, testClassName, fileExtension);

            // Make parameters
            Map<String, Object> params = makeTemplateParams(testCases);
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
        String modelName = testCases.getModelName();
        if (modelName.endsWith(this.inputParameters.getDmnFileExtension())) {
            modelName = modelName.substring(0, modelName.length() - 4);
        }
        String testName = dmnTransformer.upperCaseFirst(modelName + "Test");
        if (!Character.isJavaIdentifierStart(modelName.charAt(0))) {
            return "_" + testName;
        } else {
            return testName;
        }
    }

    private Map<String, Object> makeTemplateParams(TestCases testCases) {
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
