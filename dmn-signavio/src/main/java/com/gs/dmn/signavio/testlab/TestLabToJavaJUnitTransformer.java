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
import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.serialization.TypeDeserializationConfigurer;
import com.gs.dmn.signavio.SignavioDMNModelRepository;
import com.gs.dmn.signavio.testlab.visitor.TestLabEnhancer;
import com.gs.dmn.transformation.AbstractTestCasesToJUnitTransformer;
import com.gs.dmn.transformation.DMNTransformer;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import com.gs.dmn.transformation.template.TemplateProvider;
import com.gs.dmn.validation.DMNValidator;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gs.dmn.serialization.DMNSerializer.isDMNFile;
import static com.gs.dmn.signavio.extension.SignavioExtension.SIG_EXT_NAMESPACE;
import static com.gs.dmn.signavio.testlab.TestLabSerializer.isTestLabFile;

public class TestLabToJavaJUnitTransformer<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends AbstractTestCasesToJUnitTransformer<NUMBER, DATE, TIME, DATE_TIME, DURATION, TestLab> {
    private final TestLabSerializer testLabReader = new TestLabSerializer();
    private final TestLabValidator testLabValidator = new TestLabValidator();

    private final String schemaNamespace;
    private final BasicDMNToNativeTransformer<Type, DMNContext> basicTransformer;
    private final TestLabUtil testLabUtil;
    private final TestLabEnhancer testLabEnhancer;

    public TestLabToJavaJUnitTransformer(DMNDialectDefinition<NUMBER, DATE, TIME, DATE_TIME, DURATION, TestLab> dialectDefinition, DMNValidator dmnValidator, DMNTransformer<TestLab> dmnTransformer, TemplateProvider templateProvider, LazyEvaluationDetector lazyEvaluationDetector, TypeDeserializationConfigurer typeDeserializationConfigurer, Path inputModelPath, InputParameters inputParameters, BuildLogger logger) {
        super(dialectDefinition, dmnValidator, dmnTransformer, templateProvider, lazyEvaluationDetector, typeDeserializationConfigurer, inputParameters, logger);
        String schemaNamespace = inputParameters.getSchemaNamespace();
        if (StringUtils.isBlank(schemaNamespace)) {
            this.schemaNamespace = SIG_EXT_NAMESPACE;
        } else {
            this.schemaNamespace = schemaNamespace;
        }
        DMNModelRepository repository = readModels(inputModelPath.toFile());
        this.basicTransformer = this.dialectDefinition.createBasicTransformer(repository, lazyEvaluationDetector, inputParameters);
        DMNModelRepository dmnModelRepository = this.basicTransformer.getDMNModelRepository();
        this.dmnValidator.validate(dmnModelRepository);

        this.testLabUtil = new TestLabUtil(this.basicTransformer);
        this.testLabEnhancer = new TestLabEnhancer(this.testLabUtil);
    }

    @Override
    protected boolean shouldTransformFile(File inputFile) {
        if (inputFile == null) {
            return false;
        } else if (inputFile.isDirectory()) {
            return !inputFile.getName().endsWith(".svn");
        } else {
            return isTestLabFile(inputFile);
        }
    }

    @Override
    protected void transformFile(File file, File root, Path outputPath) {
        try {
            this.logger.info("Processing TestLab file '%s'".formatted(file.getPath()));
            StopWatch watch = new StopWatch();
            watch.start();

            List<TestLab> testLabList = new ArrayList<>();
            if (file.isFile()) {
                TestLab testLab = this.testLabReader.read(file);
                this.testLabValidator.validate(testLab);
                this.testLabEnhancer.enhance(testLab);
                testLabList.add(testLab);
            } else {
                for (File child: file.listFiles()) {
                    if (shouldTransformFile(child)) {
                        TestLab testLab = this.testLabReader.read(child);
                        this.testLabValidator.validate(testLab);
                        this.testLabEnhancer.enhance(testLab);
                        testLabList.add(testLab);
                    }
                }
            }

            testLabList = this.dmnTransformer.transform(this.basicTransformer.getDMNModelRepository(), testLabList).getRight();

            for (TestLab testLab: testLabList) {
                String javaClassName = testClassName(testLab, this.basicTransformer);
                processTemplate(testLab, this.templateProvider.testBaseTemplatePath(), this.templateProvider.testTemplateName(), this.basicTransformer, outputPath, javaClassName);
            }

            watch.stop();
            this.logger.info("TestLab processing time: " + watch);
        } catch (IOException e) {
            throw new DMNRuntimeException("Error during transforming %s.".formatted(file.getName()), e);
        }
    }

    @Override
    protected DMNModelRepository readModels(File file) {
        if (isDMNFile(file)) {
            TDefinitions result = this.dmnSerializer.readModel(file);
            return new SignavioDMNModelRepository(result, this.schemaNamespace);
        } else {
            throw new DMNRuntimeException("Invalid DMN file %s".formatted(file.getAbsoluteFile()));
        }
    }

    private void processTemplate(TestLab testLab, String baseTemplatePath, String templateName, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, Path outputPath, String testClassName) {
        try {
            String javaPackageName = dmnTransformer.nativeModelPackageName(testLab.getModelName());

            // Make parameters
            Map<String, Object> params = makeTemplateParams(testLab, dmnTransformer);
            params.put("packageName", javaPackageName);
            params.put("testClassName", testClassName);
            params.put("decisionBaseClass", this.decisionBaseClass);

            // Make output file
            String relativeFilePath = javaPackageName.replace('.', '/');
            String fileExtension = getFileExtension();
            File outputFile = makeOutputFile(outputPath, relativeFilePath, testClassName, fileExtension);

            // Process template
            processTemplate(baseTemplatePath, templateName, params, outputFile, true);
        } catch (Exception e) {
            throw new DMNRuntimeException("Cannot process TestLab template '%s' for '%s'".formatted(templateName, testLab.getRootDecisionId()), e);
        }
    }

    private String testClassName(TestLab testLab, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer) {
        List<OutputParameterDefinition> outputParameterDefinitions = testLab.getOutputParameterDefinitions();
        OutputParameterDefinition outputParameterDefinition = outputParameterDefinitions.get(0);
        return testClassName(dmnTransformer, outputParameterDefinition);
    }

    private String testClassName(BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, OutputParameterDefinition outputParameterDefinition) {
        TDRGElement decision = this.testLabUtil.findDRGElement(outputParameterDefinition);
        if (decision instanceof TDecision) {
            String requirementName = decision.getName();
            return dmnTransformer.upperCaseFirst(requirementName + "Test");
        } else {
            throw new IllegalArgumentException("The DRGElement '%s' should be a decision, is output of TestLab.".formatted(outputParameterDefinition.getId()));
        }
    }

    private Map<String, Object> makeTemplateParams(TestLab testLab, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer) {
        Map<String, Object> params = new HashMap<>();
        params.put("testLab", testLab);
        params.put("testLabUtil", this.testLabUtil);
        return params;
    }

    @Override
    protected String getFileExtension() {
        return ".java";
    }
}
