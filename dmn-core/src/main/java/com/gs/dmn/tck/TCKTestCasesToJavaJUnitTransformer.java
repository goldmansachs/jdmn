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
import com.gs.dmn.error.SemanticErrorException;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.serialization.TypeDeserializationConfigurer;
import com.gs.dmn.tck.ast.TestCases;
import com.gs.dmn.tck.serialization.xstream.XMLTCKSerializer;
import com.gs.dmn.transformation.AbstractTestCasesToJUnitTransformer;
import com.gs.dmn.transformation.DMNTransformer;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import com.gs.dmn.transformation.repository.InputRepository;
import com.gs.dmn.transformation.repository.OutputElement;
import com.gs.dmn.transformation.repository.OutputRepository;
import com.gs.dmn.transformation.template.TemplateProvider;
import com.gs.dmn.validation.DMNValidator;
import com.gs.dmn.validation.TestValidator;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gs.dmn.error.DMNErrorHandler.handleError;
import static com.gs.dmn.serialization.DMNConstants.isTCKFile;

public class TCKTestCasesToJavaJUnitTransformer<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends AbstractTestCasesToJUnitTransformer<NUMBER, DATE, TIME, DATE_TIME, DURATION, TestCases> {
    private final TCKSerializer tckSerializer;
    private final InputRepository inputModelRepository;

    public TCKTestCasesToJavaJUnitTransformer(DMNDialectDefinition<NUMBER, DATE, TIME, DATE_TIME, DURATION, TestCases> dialectDefinition, DMNValidator dmnValidator, TestValidator<TestCases> testCasesValidator, DMNTransformer<TestCases> dmnTransformer, TemplateProvider templateProvider, LazyEvaluationDetector lazyEvaluationDetector, TypeDeserializationConfigurer typeDeserializationConfigurer, InputRepository inputModelRepository, InputParameters inputParameters, BuildLogger logger) {
        super(dialectDefinition, dmnValidator, testCasesValidator, dmnTransformer, templateProvider, lazyEvaluationDetector, typeDeserializationConfigurer, inputParameters, logger);
        this.inputModelRepository = inputModelRepository;
        this.tckSerializer = new XMLTCKSerializer(logger, inputParameters);
    }

    @Override
    protected void collectFiles(InputRepository inputRepository, List<File> files) {
        inputRepository.shallowCollectFiles(this::shouldTransformFile, files);
    }

    @Override
    protected String getInputFileType() {
        return "TCK";
    }

    @Override
    protected boolean shouldTransformFile(File inputFile) {
        return isTCKFile(inputFile, inputParameters.getTckFileExtension());
    }

    @Override
    protected void transformFiles(List<File> files, OutputRepository outputRepository) {
        StopWatch watch = new StopWatch();
        watch.start();

        // Read the models
        DMNModelRepository repository = readModels(inputModelRepository.getRootFile());

        // Read the test cases
        List<TestCases> testCasesList = readTestCases(files);

        // Apply the DMN transformation
        Pair<DMNModelRepository, List<TestCases>> pair = dmnTransformer.transform(repository, testCasesList);
        repository = pair.getLeft();
        testCasesList = pair.getRight();

        // Validate the models and test cases
        handleValidationErrors(this.dmnValidator.validate(repository));
        handleValidationErrors(this.testCasesValidator.validate(testCasesList));

        // Translate the test cases to the native platform
        BasicDMNToNativeTransformer<Type, DMNContext> basicTransformer = this.dialectDefinition.createBasicTransformer(repository, lazyEvaluationDetector, inputParameters);
        transformTestCases(testCasesList, basicTransformer, outputRepository);

        watch.stop();
        logger.info("TCK processing time: " + watch);
    }

    private List<TestCases> readTestCases(List<File> files) {
        List<TestCases> testCasesList = new ArrayList<>();
        for (File file : files) {
            logger.info(String.format("Processing TCK files '%s'", file));
            TestCases testCases = tckSerializer.read(file);
            testCases.setTestCasesName(file.getName());
            testCasesList.add(testCases);
        }
        return testCasesList;
    }

    private void transformTestCases(List<TestCases> testCasesList, BasicDMNToNativeTransformer<Type, DMNContext> basicTransformer, OutputRepository outputRepository) {
        TCKUtil<NUMBER, DATE, TIME, DATE_TIME, DURATION> tckUtil = new TCKUtil<>(basicTransformer, dialectDefinition.createFEELLib());
        TemplateProvider templateProvider = templateProcessor.getTemplateProvider();
        for (TestCases testCases: testCasesList) {
            String nativeClassName = testClassName(testCases, basicTransformer);
            transformTestCase(testCases, templateProvider, basicTransformer, tckUtil, outputRepository, nativeClassName);
        }
        generateExtra(basicTransformer, basicTransformer.getDMNModelRepository(), outputRepository);
    }

    private void transformTestCase(TestCases testCases, TemplateProvider templateProvider, BasicDMNToNativeTransformer<Type, DMNContext> basicTransformer, TCKUtil<NUMBER, DATE, TIME, DATE_TIME, DURATION> tckUtil, OutputRepository outputRepository, String nativeClassName) {
        processTemplate(testCases, templateProvider.testBaseTemplatePath(), templateProvider.testTemplateName(), basicTransformer, tckUtil, outputRepository, nativeClassName);
    }

    protected void generateExtra(BasicDMNToNativeTransformer<Type, DMNContext> basicTransformer, DMNModelRepository dmnModelRepository, OutputRepository outputRepository) {
    }

    protected void processTemplate(TestCases testCases, String baseTemplatePath, String templateName, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, TCKUtil<NUMBER, DATE, TIME, DATE_TIME, DURATION> tckUtil, OutputRepository outputRepository, String testClassName) {
        try {
            // Make output file
            String nativePackageName = dmnTransformer.nativeModelPackageName(testCases.getModelName());
            String fileExtension = getFileExtension();
            OutputElement outputElement = outputRepository.makeOutputElement(nativePackageName, testClassName, fileExtension);

            // Make parameters
            Map<String, Object> params = makeTemplateParams(testCases, tckUtil);
            params.put("packageName", nativePackageName);
            params.put("testClassName", testClassName);
            params.put("decisionBaseClass", decisionBaseClass);

            // Process template
            this.templateProcessor.processTemplate(baseTemplatePath, templateName, params, outputElement, true);
        } catch (Exception e) {
            throw handleError(String.format("Cannot process template '%s' for testCases of '%s'", templateName, testCases.getModelName()), e);
        }
    }

    protected String testClassName(TestCases testCases, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer) {
        String testCasesName = TCKUtil.getTestCasesName(testCases.getTestCasesName());
        if (!StringUtils.isBlank(testCasesName)) {
            return testClassName(testCasesName, dmnTransformer);
        } else {
            throw new SemanticErrorException(String.format("Missing TestCases name when testing model '%s'", testCases.getModelName()));
        }
    }

    private static String testClassName(String testCasesName, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer) {
        String testName = dmnTransformer.upperCaseFirst(testCasesName + "Test");
        if (!Character.isJavaIdentifierStart(testCasesName.charAt(0))) {
            return "_" + testName;
        } else {
            return testName;
        }
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
