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
package com.gs.dmn.signavio.testlab;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.ast.TDRGElement;
import com.gs.dmn.ast.TDecision;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.error.ValidationError;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.serialization.TypeDeserializationConfigurer;
import com.gs.dmn.signavio.testlab.visitor.TestLabEnhancer;
import com.gs.dmn.transformation.AbstractTestCasesToJUnitTransformer;
import com.gs.dmn.transformation.DMNTransformer;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import com.gs.dmn.transformation.repository.InputRepository;
import com.gs.dmn.transformation.repository.OutputRepository;
import com.gs.dmn.transformation.template.TemplateProvider;
import com.gs.dmn.validation.DMNValidator;
import com.gs.dmn.validation.TestValidator;
import org.apache.commons.lang3.time.StopWatch;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gs.dmn.signavio.testlab.TestLabSerializer.isTestLabFile;

public class TestLabToJavaJUnitTransformer<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends AbstractTestCasesToJUnitTransformer<NUMBER, DATE, TIME, DATE_TIME, DURATION, TestLab> {
    private final TestLabSerializer testLabReader = new TestLabSerializer();
    private final TestLabValidator testLabValidator;

    private final InputRepository inputModelRepository;

    public TestLabToJavaJUnitTransformer(DMNDialectDefinition<NUMBER, DATE, TIME, DATE_TIME, DURATION, TestLab> dialectDefinition, DMNValidator dmnValidator, TestValidator<TestLab> testCasesValidator, DMNTransformer<TestLab> dmnTransformer, TemplateProvider templateProvider, LazyEvaluationDetector lazyEvaluationDetector, TypeDeserializationConfigurer typeDeserializationConfigurer, InputRepository inputModelRepository, InputParameters inputParameters, BuildLogger logger) {
        super(dialectDefinition, dmnValidator, testCasesValidator, dmnTransformer, templateProvider, lazyEvaluationDetector, typeDeserializationConfigurer, inputParameters, logger);
        this.inputModelRepository = inputModelRepository;
        this.testLabValidator = (TestLabValidator) testCasesValidator;
    }

    @Override
    protected String getInputFileType() {
        return "TestLab";
    }

    @Override
    protected boolean shouldTransformFile(File inputFile) {
        return isTestLabFile(inputFile);
    }

    @Override
    protected void transformFiles(List<File> files, OutputRepository outputRepository) {
        StopWatch watch = new StopWatch();
        watch.start();

        // Read the models
        DMNModelRepository repository = readModels(inputModelRepository.getRootFile());

        // Read the test cases
        List<TestLab> testLabList = readTestCases(files);

        // Apply the DMN transformation
        Pair<DMNModelRepository, List<TestLab>> pair = this.dmnTransformer.transform(repository, testLabList);
        repository = pair.getLeft();
        testLabList = pair.getRight();

        // Validate the models and test cases
        handleModelErrors(this.dmnValidator.validate(repository));
        handleModelErrors(validateTestCases(testLabList));

        // Translate the test cases to the native platform
        BasicDMNToNativeTransformer<Type, DMNContext> basicTransformer = this.dialectDefinition.createBasicTransformer(repository, lazyEvaluationDetector, inputParameters);
        transformTestCases(testLabList, basicTransformer, outputRepository);

        watch.stop();
        this.logger.info("TestLab processing time: " + watch);
    }

    private List<TestLab> readTestCases(List<File> files) {
        List<TestLab> testLabList = new ArrayList<>();
        for (File child: files) {
            if (shouldTransformFile(child)) {
                TestLab testLab = this.testLabReader.read(child);
                testLabList.add(testLab);
            }
        }
        return testLabList;
    }

    private List<ValidationError> validateTestCases(List<TestLab> testLabList) {
        List<ValidationError> errors = new ArrayList<>();
        for (TestLab testLab : testLabList) {
            errors.addAll(this.testLabValidator.validate(testLab));
        }
        return errors;
    }

    private void transformTestCases(List<TestLab> testLabList, BasicDMNToNativeTransformer<Type, DMNContext> basicTransformer, OutputRepository outputRepository) {
        // Enhance test cases with item definition information
        TestLabUtil testLabUtil = new TestLabUtil(basicTransformer);
        TestLabEnhancer testLabEnhancer = new TestLabEnhancer(testLabUtil);
        for (TestLab testLab : testLabList) {
            testLabEnhancer.enhance(testLab);
        }

        // Translate the test cases to the native platform
        for (TestLab testLab: testLabList) {
            String nativeClassName = testClassName(testLab, basicTransformer, testLabUtil);
            transformTestLab(testLab, this.templateProcessor.getTemplateProvider().testBaseTemplatePath(), this.templateProcessor.getTemplateProvider().testTemplateName(), basicTransformer, testLabUtil, outputRepository, nativeClassName);
        }
    }

    private void transformTestLab(TestLab testLab, String baseTemplatePath, String templateName, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, TestLabUtil testLabUtil, OutputRepository outputRepository, String testClassName) {
        try {
            String nativePackageName = dmnTransformer.nativeModelPackageName(testLab.getModelName());

            // Make parameters
            Map<String, Object> params = makeTemplateParams(testLab, testLabUtil);
            params.put("packageName", nativePackageName);
            params.put("testClassName", testClassName);
            params.put("decisionBaseClass", this.decisionBaseClass);

            // Make output file
            String relativeFilePath = nativePackageName.replace('.', '/');
            String fileExtension = getFileExtension();
            File outputFile = outputRepository.makeOutputFile(relativeFilePath, testClassName, fileExtension);

            // Process template
            this.templateProcessor.processTemplate(baseTemplatePath, templateName, params, outputFile, true);
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot process TestLab template '%s' for '%s'", templateName, testLab.getRootDecisionId()), e);
        }
    }

    private String testClassName(TestLab testLab, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, TestLabUtil testLabUtil) {
        List<OutputParameterDefinition> outputParameterDefinitions = testLab.getOutputParameterDefinitions();
        OutputParameterDefinition outputParameterDefinition = outputParameterDefinitions.get(0);
        return testClassName(dmnTransformer, outputParameterDefinition, testLabUtil);
    }

    private String testClassName(BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, OutputParameterDefinition outputParameterDefinition, TestLabUtil testLabUtil) {
        TDRGElement decision = testLabUtil.findDRGElement(outputParameterDefinition);
        if (decision instanceof TDecision) {
            String requirementName = decision.getName();
            return dmnTransformer.upperCaseFirst(requirementName + "Test");
        } else {
            throw new IllegalArgumentException(String.format("The DRGElement '%s' should be a decision, is output of TestLab.", outputParameterDefinition.getId()));
        }
    }

    private Map<String, Object> makeTemplateParams(TestLab testLab, TestLabUtil testLabUtil) {
        Map<String, Object> params = new HashMap<>();
        params.put("testLab", testLab);
        params.put("testLabUtil", testLabUtil);
        return params;
    }

    @Override
    protected String getFileExtension() {
        return ".java";
    }
}
