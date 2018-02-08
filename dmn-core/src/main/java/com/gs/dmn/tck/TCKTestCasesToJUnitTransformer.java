/**
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
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.serialization.DMNReader;
import com.gs.dmn.transformation.AbstractDMNTransformer;
import com.gs.dmn.transformation.DMNTransformer;
import com.gs.dmn.transformation.basic.BasicDMN2JavaTransformer;
import com.gs.dmn.transformation.template.POJOTemplateProvider;
import org.apache.commons.lang3.StringUtils;
import org.omg.dmn.tck.marshaller._20160719.TestCases;
import org.omg.spec.dmn._20151101.dmn.TDefinitions;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Octavian Patrascoiu on 03/11/2016.
 */
public class TCKTestCasesToJUnitTransformer extends AbstractDMNTransformer {
    protected final BasicDMN2JavaTransformer basicTransformer;

    protected final TestCasesReader testCasesReader;
    private final TCKUtil tckUtil;

    public TCKTestCasesToJUnitTransformer(DMNDialectDefinition dialectDefinition, DMNTransformer dmnTransformer, Path inputModelPath, Map<String, String> inputParameters, BuildLogger logger) {
        super(dialectDefinition, dmnTransformer, inputParameters, logger, new POJOTemplateProvider());
        TDefinitions definitions = readDMN(inputModelPath.toFile());
        this.basicTransformer = this.dialectDefinition.createBasicTransformer(definitions, javaRootPackage);
        DMNModelRepository dmnModelRepository = this.basicTransformer.getDMNModelRepository();
        this.dmnValidator.validateDefinitions(dmnModelRepository);
        this.testCasesReader = new TestCasesReader(logger);
        this.tckUtil = new TCKUtil(basicTransformer, dialectDefinition.createFEELLib());
    }

    @Override
    protected boolean shouldTransform(File inputFile) {
        String name = inputFile.getName();
        return name.endsWith(TestCasesReader.TEST_FILE_EXTENSION) && !name.endsWith(".svn");
    }

    @Override
    protected void transformFile(File child, File root, Path outputPath) {
        try {
            logger.info("Processing TCK TestCases ...");

            TestCases testCases = testCasesReader.read(child);
            testCases = (TestCases) dmnTransformer.transform(basicTransformer.getDMNModelRepository().getDefinitions(), testCases).getRight();

            String javaClassName = testClassName(testCases, basicTransformer);
            processTemplate(testCases, templateProvider.testBaseTemplatePath(), templateProvider.testTemplateName(), basicTransformer, outputPath, javaClassName);
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Error during transforming %s.", child.getName()), e);
        }
    }

    protected void processTemplate(TestCases testCases, String baseTemplatePath, String templateName, BasicDMN2JavaTransformer dmnTransformer, Path outputPath, String testClassName) {
        try {
            // Make parameters
            Map<String, Object> params = makeTemplateParams(testCases, dmnTransformer);
            params.put("packageName", javaRootPackage);
            params.put("testClassName", testClassName);
            params.put("decisionBaseClass", decisionBaseClass);

            // Make output file
            String javaPackageName = dmnTransformer.javaRootPackageName();
            String relativeFilePath = javaPackageName.replace('.', '/');
            String fileExtension = ".java";
            File outputFile = makeOutputFile(outputPath, relativeFilePath, testClassName, fileExtension);

            // Process template
            processTemplate(baseTemplatePath, templateName, params, outputFile, true);
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot process template '%s' for testCases '%s'", templateName, testCases.getModelName()), e);
        }
    }

    protected String testClassName(TestCases testCases, BasicDMN2JavaTransformer dmnTransformer) {
        String modelName = testCases.getModelName();
        if (modelName.endsWith(DMNReader.DMN_FILE_EXTENSION)) {
            modelName = modelName.substring(0, modelName.length() - 4);
        }
        String testClassName = StringUtils.capitalize(dmnTransformer.javaFriendlyName("Test" + modelName));
        return testClassName;
    }

    private Map<String, Object> makeTemplateParams(TestCases testCases, BasicDMN2JavaTransformer dmnTransformer) {
        Map<String, Object> params = new HashMap<>();
        params.put("testCases", testCases);
        params.put("tckUtil", tckUtil);
        return params;
    }
}
