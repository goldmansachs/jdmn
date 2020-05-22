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
package com.gs.dmn.signavio.dialect;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.dialect.AbstractDMNDialectDefinition;
import com.gs.dmn.feel.analysis.semantics.environment.EnvironmentFactory;
import com.gs.dmn.feel.lib.FEELLib;
import com.gs.dmn.feel.synthesis.type.NativeTypeFactory;
import com.gs.dmn.feel.synthesis.type.StandardNativeTypeToKotlinFactory;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.interpreter.DMNInterpreter;
import com.gs.dmn.serialization.TypeDeserializationConfigurer;
import com.gs.dmn.signavio.feel.lib.DefaultSignavioLib;
import com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision;
import com.gs.dmn.signavio.runtime.SignavioEnvironmentFactory;
import com.gs.dmn.signavio.runtime.interpreter.SignavioDMNInterpreter;
import com.gs.dmn.signavio.transformation.BasicSignavioDMN2KotlinTransformer;
import com.gs.dmn.signavio.transformation.SignavioDMNToKotlinTransformer;
import com.gs.dmn.transformation.AbstractDMNToNativeTransformer;
import com.gs.dmn.transformation.DMNTransformer;
import com.gs.dmn.transformation.basic.BasicDMN2JavaTransformer;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;
import com.gs.dmn.transformation.template.TemplateProvider;
import com.gs.dmn.validation.DMNValidator;

import java.util.LinkedHashMap;
import java.util.Map;

public class KotlinSignavioDMNDialectDefinition extends AbstractDMNDialectDefinition {
    //
    // DMN processors
    //
    @Override
    public DMNInterpreter createDMNInterpreter(DMNModelRepository repository, Map<String, String> inputParameters) {
        return new SignavioDMNInterpreter(createBasicTransformer(repository, new NopLazyEvaluationDetector(), new LinkedHashMap<>()), createFEELLib());
    }

    @Override
    public AbstractDMNToNativeTransformer createDMNToJavaTransformer(DMNValidator dmnValidator, DMNTransformer dmnTransformer, TemplateProvider templateProvider, LazyEvaluationDetector lazyEvaluationDetector, TypeDeserializationConfigurer typeDeserializationConfigurer, Map<String, String> inputParameters, BuildLogger logger) {
        return new SignavioDMNToKotlinTransformer(this, dmnValidator, dmnTransformer, templateProvider, lazyEvaluationDetector, typeDeserializationConfigurer, inputParameters, logger);
    }

    @Override
    public BasicDMN2JavaTransformer createBasicTransformer(DMNModelRepository repository, LazyEvaluationDetector lazyEvaluationDetector, Map<String, String> inputParameters) {
        EnvironmentFactory environmentFactory = createEnvironmentFactory();
        return new BasicSignavioDMN2KotlinTransformer(repository, environmentFactory, createTypeTranslator(), lazyEvaluationDetector, inputParameters);
    }

    //
    // Execution engine
    //
    @Override
    public NativeTypeFactory createTypeTranslator() {
        return new StandardNativeTypeToKotlinFactory();
    }

    @Override
    public FEELLib createFEELLib() {
        return new DefaultSignavioLib();
    }

    @Override
    public String getDecisionBaseClass() {
        return DefaultSignavioBaseDecision.class.getName();
    }

    private EnvironmentFactory createEnvironmentFactory() {
        return SignavioEnvironmentFactory.instance();
    }
}
