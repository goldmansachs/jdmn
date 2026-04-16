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

import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.serialization.DefaultTypeDeserializationConfigurer;
import com.gs.dmn.serialization.TypeDeserializationConfigurer;
import com.gs.dmn.tck.TestCasesToNativeTransformer;
import com.gs.dmn.transformation.DMNToNativeTransformer;
import com.gs.dmn.transformation.DMNTransformer;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.NopDMNTransformer;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;
import com.gs.dmn.transformation.repository.InputRepository;
import com.gs.dmn.transformation.template.TemplateProvider;
import com.gs.dmn.validation.DMNValidator;
import com.gs.dmn.validation.NopDMNValidator;
import com.gs.dmn.validation.NopTestValidator;
import com.gs.dmn.validation.TestValidator;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;
import java.util.Map;

public abstract class AbstractDMNToJavaTranslatorBuilder<TEST> {
    protected DMNDialectDefinition<Number, LocalDate, TemporalAccessor, TemporalAccessor, TemporalAmount, TEST> dialectDefinition;
    protected TemplateProvider templateProvider;

    protected BuildLogger buildLogger = new Slf4jBuildLogger(LoggerFactory.getLogger(DMNToJavaTranslator.class));
    protected InputParameters inputParameters = new InputParameters(makeDefaultConfiguration());

    protected DMNValidator dmnValidator = new NopDMNValidator();
    protected TestValidator<TEST> testCasesValidator = new NopTestValidator<>();
    protected DMNTransformer<TEST> dmnTransformer = new NopDMNTransformer<>();
    protected LazyEvaluationDetector lazyEvaluationDetector = new NopLazyEvaluationDetector();
    protected TypeDeserializationConfigurer deserializationConfigurer = new DefaultTypeDeserializationConfigurer();

    public AbstractDMNToJavaTranslatorBuilder(DMNDialectDefinition<Number, LocalDate, TemporalAccessor, TemporalAccessor, TemporalAmount, TEST> dialectDefinition) {
        this.dialectDefinition = dialectDefinition;
    }

    // Builder-style fluent setters for overriding defaults
    public AbstractDMNToJavaTranslatorBuilder<TEST> withBuildLogger(BuildLogger buildLogger) {
        this.buildLogger = buildLogger;
        return this;
    }

    public AbstractDMNToJavaTranslatorBuilder<TEST> withInputParameters(InputParameters inputParameters) {
        this.inputParameters = inputParameters;
        return this;
    }

    public AbstractDMNToJavaTranslatorBuilder<TEST> withDMNValidator(DMNValidator dmnValidator) {
        this.dmnValidator = dmnValidator;
        return this;
    }

    public AbstractDMNToJavaTranslatorBuilder<TEST> withTestValidator(TestValidator<TEST> testCasesValidator) {
        this.testCasesValidator = testCasesValidator;
        return this;
    }

    public AbstractDMNToJavaTranslatorBuilder<TEST> withDMNTransformer(DMNTransformer<TEST> dmnTransformer) {
        this.dmnTransformer = dmnTransformer;
        return this;
    }

    public AbstractDMNToJavaTranslatorBuilder<TEST> withTemplateProvider(TemplateProvider templateProvider) {
        this.templateProvider = templateProvider;
        return this;
    }

    public AbstractDMNToJavaTranslatorBuilder<TEST> withLazyEvaluationDetector(LazyEvaluationDetector lazyEvaluationDetector) {
        this.lazyEvaluationDetector = lazyEvaluationDetector;
        return this;
    }

    public AbstractDMNToJavaTranslatorBuilder<TEST> withDeserializationConfigurer(TypeDeserializationConfigurer deserializationConfigurer) {
        this.deserializationConfigurer = deserializationConfigurer;
        return this;
    }

    public DMNToNativeTransformer buildDMNTranslator() {
        return dialectDefinition.createDMNToNativeTransformer(
                dmnValidator, dmnTransformer, templateProvider, lazyEvaluationDetector,
                deserializationConfigurer, inputParameters, buildLogger);
    }

    public TestCasesToNativeTransformer buildTestCasesTranslator(InputRepository inputModelRepository) {
        return dialectDefinition.createTestCasesToNativeTransformer(
                dmnValidator, testCasesValidator, dmnTransformer, templateProvider, lazyEvaluationDetector, deserializationConfigurer,
                inputModelRepository,
                inputParameters,
                buildLogger
        );
    }

    protected abstract Map<String, String> makeDefaultConfiguration();
}


