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

import com.gs.dmn.dialect.JavaTimeDMNDialectDefinition;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.serialization.DefaultTypeDeserializationConfigurer;
import com.gs.dmn.serialization.TypeDeserializationConfigurer;
import com.gs.dmn.tck.TestCasesToNativeTransformer;
import com.gs.dmn.tck.ast.TestCases;
import com.gs.dmn.transformation.DMNToNativeTransformer;
import com.gs.dmn.transformation.DMNTransformer;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.NopDMNTransformer;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;
import com.gs.dmn.transformation.template.TemplateProvider;
import com.gs.dmn.transformation.template.TreeTemplateProvider;
import com.gs.dmn.validation.DMNValidator;
import com.gs.dmn.validation.NopDMNValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

public class DMNToJavaTranslatorBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(DMNToJavaTranslatorBuilder.class);

    // static configuration shared across all instances
    private static final Map<String, String> MAP;

    static {
        MAP = new LinkedHashMap<>();
        MAP.put("dmn.version", "1.5");
        MAP.put("model.version", "1.0");
        MAP.put("platform.version", "10.0.0");
    }

    private final JavaTimeDMNDialectDefinition dialectDefinition = new JavaTimeDMNDialectDefinition();
    private InputParameters inputParameters = new InputParameters(MAP);
    private BuildLogger buildLogger = new Slf4jBuildLogger(LOGGER);
    private DMNValidator dmnValidator = new NopDMNValidator();
    private DMNTransformer<TestCases> dmnTransformer = new NopDMNTransformer<>();
    private TemplateProvider templateProvider = new TreeTemplateProvider();
    private LazyEvaluationDetector lazyEvaluationDetector = new NopLazyEvaluationDetector();
    private TypeDeserializationConfigurer deserializationConfigurer = new DefaultTypeDeserializationConfigurer();

    // Builder-style fluent setters for overriding defaults
    public DMNToJavaTranslatorBuilder withBuildLogger(BuildLogger buildLogger) {
        this.buildLogger = buildLogger;
        return this;
    }

    public DMNToJavaTranslatorBuilder withInputParameters(InputParameters inputParameters) {
        this.inputParameters = inputParameters;
        return this;
    }

    public DMNToJavaTranslatorBuilder withDMNValidator(DMNValidator dmnValidator) {
        this.dmnValidator = dmnValidator;
        return this;
    }

    public DMNToJavaTranslatorBuilder withDMNTransformer(DMNTransformer<TestCases> dmnTransformer) {
        this.dmnTransformer = dmnTransformer;
        return this;
    }

    public DMNToJavaTranslatorBuilder withTemplateProvider(TemplateProvider templateProvider) {
        this.templateProvider = templateProvider;
        return this;
    }

    public DMNToJavaTranslatorBuilder withLazyEvaluationDetector(LazyEvaluationDetector lazyEvaluationDetector) {
        this.lazyEvaluationDetector = lazyEvaluationDetector;
        return this;
    }

    public DMNToJavaTranslatorBuilder withDeserializationConfigurer(TypeDeserializationConfigurer deserializationConfigurer) {
        this.deserializationConfigurer = deserializationConfigurer;
        return this;
    }

    public DMNToNativeTransformer buildDMNTranslator() {
        return dialectDefinition.createDMNToNativeTransformer(
                dmnValidator, dmnTransformer, templateProvider, lazyEvaluationDetector,
                deserializationConfigurer, inputParameters, buildLogger);
    }

    public TestCasesToNativeTransformer buildTCKTranslator(File inputModelFile) {
        return dialectDefinition.createTestCasesToNativeTransformer(
                dmnValidator, dmnTransformer, templateProvider, lazyEvaluationDetector, deserializationConfigurer,
                inputModelFile.toPath(),
                inputParameters,
                buildLogger
        );
    }
}


