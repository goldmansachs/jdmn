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
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.feel.lib.StandardFEELLib;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.serialization.DMNConstants;
import com.gs.dmn.serialization.TypeDeserializationConfigurer;
import com.gs.dmn.transformation.AbstractTestCasesToJUnitTransformer;
import com.gs.dmn.transformation.DMNTransformer;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import com.gs.dmn.transformation.template.TemplateProvider;
import com.gs.dmn.validation.DMNValidator;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.omg.dmn.tck.marshaller._20160719.TestCases;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gs.dmn.tck.TestCasesReader.isTCKFile;

public class TCKTestCasesToJavaJUnitTransformer<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends AbstractTestCasesToJUnitTransformer<NUMBER, DATE, TIME, DATE_TIME, DURATION, TestCases> {
    protected final BasicDMNToNativeTransformer basicTransformer;

    protected final TestCasesReader testCasesReader;
    private final TCKUtil<NUMBER, DATE, TIME, DATE_TIME, DURATION> tckUtil;

    public TCKTestCasesToJavaJUnitTransformer(DMNDialectDefinition<NUMBER, DATE, TIME, DATE_TIME, DURATION, TestCases> dialectDefinition, DMNValidator dmnValidator, DMNTransformer<TestCases> dmnTransformer, TemplateProvider templateProvider, LazyEvaluationDetector lazyEvaluationDetector, TypeDeserializationConfigurer typeDeserializationConfigurer, Path inputModelPath, InputParameters inputParameters, BuildLogger logger) {
        super(dialectDefinition, dmnValidator, dmnTransformer, templateProvider, lazyEvaluationDetector, typeDeserializationConfigurer, inputParameters, logger);
        DMNModelRepository repository = readModels(inputModelPath.toFile());
        this.basicTransformer = this.dialectDefinition.createBasicTransformer(repository, lazyEvaluationDetector, inputParameters);
        handleValidationErrors(this.dmnValidator.validate(repository));
        this.testCasesReader = new TestCasesReader(logger);
        this.tckUtil = new TCKUtil<>(basicTransformer, (StandardFEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION>) dialectDefinition.createFEELLib());
    }

    @Override
    protected boolean shouldTransformFile(File inputFile) {
        if (inputFile == null) {
            return false;
        } else if (inputFile.isDirectory()) {
            return !inputFile.getName().endsWith(".svn");
        } else {
            return isTCKFile(inputFile);
        }
    }

    @Override
    protected void transformFile(File file, File root, Path outputPath) {
        try {
            logger.info(String.format("Processing TCK file '%s'", file.getPath()));
            StopWatch watch = new StopWatch();
            watch.start();

            List<TestCases> testCasesList = new ArrayList<>();
            if (file.isFile()) {
                TestCases testCases = testCasesReader.read(file);
                testCasesList.add(testCases);
            } else if (file.isDirectory() && file.listFiles() != null) {
                for (File child: file.listFiles()) {
                    if (isTCKFile(child)) {
                        TestCases testCases = testCasesReader.read(child);
                        testCasesList.add(testCases);
                    }
                }
            }
            testCasesList = dmnTransformer.transform(basicTransformer.getDMNModelRepository(), testCasesList).getRight();

            for (TestCases testCases: testCasesList) {
                String javaClassName = testClassName(testCases, basicTransformer);
                processTemplate(testCases, templateProvider.testBaseTemplatePath(), templateProvider.testTemplateName(), basicTransformer, outputPath, javaClassName);
            }

            watch.stop();
            logger.info("TCK processing time: " + watch.toString());
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Error during transforming %s.", file.getName()), e);
        }
    }

    protected void processTemplate(TestCases testCases, String baseTemplatePath, String templateName, BasicDMNToNativeTransformer dmnTransformer, Path outputPath, String testClassName) {
        try {
            // Make output file
            String javaPackageName = dmnTransformer.nativeModelPackageName(testCases.getModelName());
            String relativeFilePath = javaPackageName.replace('.', '/');
            String fileExtension = getFileExtension();
            File outputFile = makeOutputFile(outputPath, relativeFilePath, testClassName, fileExtension);

            // Make parameters
            Map<String, Object> params = makeTemplateParams(testCases, dmnTransformer);
            params.put("packageName", javaPackageName);
            params.put("testClassName", testClassName);
            params.put("decisionBaseClass", decisionBaseClass);

            // Process template
            processTemplate(baseTemplatePath, templateName, params, outputFile, true);
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot process template '%s' for testCases of '%s'", templateName, testCases.getModelName()), e);
        }
    }

    protected String testClassName(TestCases testCases, BasicDMNToNativeTransformer dmnTransformer) {
        String modelName = testCases.getModelName();
        if (modelName.endsWith(DMNConstants.DMN_FILE_EXTENSION)) {
            modelName = modelName.substring(0, modelName.length() - 4);
        }
        return StringUtils.capitalize(dmnTransformer.nativeFriendlyName("Test" + modelName));
    }

    private Map<String, Object> makeTemplateParams(TestCases testCases, BasicDMNToNativeTransformer dmnTransformer) {
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
