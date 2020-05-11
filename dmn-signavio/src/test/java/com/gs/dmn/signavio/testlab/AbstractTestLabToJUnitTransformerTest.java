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

import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.serialization.DMNConstants;
import com.gs.dmn.serialization.DefaultTypeDeserializationConfigurer;
import com.gs.dmn.serialization.TypeDeserializationConfigurer;
import com.gs.dmn.signavio.dialect.SignavioDMNDialectDefinition;
import com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision;
import com.gs.dmn.signavio.runtime.SignavioEnvironmentFactory;
import com.gs.dmn.signavio.transformation.template.SignavioTreeTemplateProvider;
import com.gs.dmn.transformation.AbstractTestCasesTransformerTest;
import com.gs.dmn.transformation.DMNTransformer;
import com.gs.dmn.transformation.FileTransformer;
import com.gs.dmn.transformation.NopDMNTransformer;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;
import com.gs.dmn.transformation.template.TemplateProvider;
import com.gs.dmn.validation.DMNValidator;
import com.gs.dmn.validation.NopDMNValidator;

import java.net.URLDecoder;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AbstractTestLabToJUnitTransformerTest extends AbstractTestCasesTransformerTest {
    @Override
    protected DMNValidator makeDMNValidator(BuildLogger logger) {
        return new NopDMNValidator();
    }

    @Override
    protected DMNTransformer makeDMNTransformer(BuildLogger logger) {
        return new NopDMNTransformer();
    }

    @Override
    protected LazyEvaluationDetector makeLazyEvaluationDetector(Map<String, String> inputParameters, BuildLogger logger) {
        return new NopLazyEvaluationDetector();
    }

    @Override
    protected TypeDeserializationConfigurer makeTypeDeserializationConfigurer(BuildLogger logger) {
        return new DefaultTypeDeserializationConfigurer();
    }

    @Override
    protected Map<String, String> makeInputParameters() {
        Map<String, String> inputParams = new LinkedHashMap<>();
        inputParams.put("environmentFactoryClass", SignavioEnvironmentFactory.class.getName());
        inputParams.put("decisionBaseClass", DefaultSignavioBaseDecision.class.getName());
        inputParams.put("signavioSchemaNamespace", "http://www.provider.com/schema/dmn/1.1/");
        return inputParams;
   }

    public void doTest(String name) throws Exception {
        String path = getInputPath() + "/";
        String expectedPath = getExpectedPath() + "/" + friendlyFolderName(name);
        String inputTestFilePath = path + name + TestLabReader.TEST_LAB_FILE_EXTENSION;
        String inputModelFilePath = path + name + DMNConstants.DMN_FILE_EXTENSION;
        String decodedInputTestFilePath = URLDecoder.decode(resource(inputTestFilePath).getPath(), "UTF-8");
        String decodedInputModelFilePath = URLDecoder.decode(resource(inputModelFilePath).getPath(), "UTF-8");
        super.doTest(decodedInputTestFilePath, decodedInputModelFilePath, expectedPath);
    }

    protected abstract String getInputPath();

    protected abstract String getExpectedPath();
}
