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
package com.gs.dmn.dialect;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.feel.analysis.semantics.environment.DefaultDMNEnvironmentFactory;
import com.gs.dmn.feel.analysis.semantics.environment.EnvironmentFactory;
import com.gs.dmn.feel.lib.DefaultFEELLib;
import com.gs.dmn.feel.lib.FEELLib;
import com.gs.dmn.feel.synthesis.type.NativeTypeFactory;
import com.gs.dmn.feel.synthesis.type.StandardNativeTypeToKotlinFactory;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.DefaultDMNBaseDecision;
import com.gs.dmn.runtime.interpreter.DMNInterpreter;
import com.gs.dmn.runtime.interpreter.StandardDMNInterpreter;
import com.gs.dmn.serialization.TypeDeserializationConfigurer;
import com.gs.dmn.transformation.AbstractDMNToNativeTransformer;
import com.gs.dmn.transformation.DMNToKotlinTransformer;
import com.gs.dmn.transformation.DMNTransformer;
import com.gs.dmn.transformation.basic.BasicDMN2JavaTransformer;
import com.gs.dmn.transformation.basic.BasicDMN2KotlinTransformer;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;
import com.gs.dmn.transformation.template.TemplateProvider;
import com.gs.dmn.validation.DMNValidator;

import java.util.Map;

public class KotlinStandardDMNDialectDefinition extends AbstractDMNDialectDefinition {
    //
    // DMN Processors
    //
    @Override
    public DMNInterpreter createDMNInterpreter(DMNModelRepository repository, Map<String, String> inputParameters) {
        return new StandardDMNInterpreter(createBasicTransformer(repository, new NopLazyEvaluationDetector(), inputParameters), createFEELLib());
    }

    @Override
    public AbstractDMNToNativeTransformer createDMNToJavaTransformer(DMNValidator dmnValidator, DMNTransformer dmnTransformer, TemplateProvider templateProvider, LazyEvaluationDetector lazyEvaluationDetector, TypeDeserializationConfigurer typeDeserializationConfigurer, Map<String, String> inputParameters, BuildLogger logger) {
        return new DMNToKotlinTransformer(this, dmnValidator, dmnTransformer, templateProvider, lazyEvaluationDetector, typeDeserializationConfigurer, inputParameters, logger);
    }

    @Override
    public BasicDMN2JavaTransformer createBasicTransformer(DMNModelRepository repository, LazyEvaluationDetector lazyEvaluationDetector, Map<String, String> inputParameters) {
        EnvironmentFactory environmentFactory = createEnvironmentFactory();
        return new BasicDMN2KotlinTransformer(repository, environmentFactory, createTypeTranslator(), lazyEvaluationDetector, inputParameters);
    }

    private EnvironmentFactory createEnvironmentFactory() {
        return DefaultDMNEnvironmentFactory.instance();
    }

    //
    // DMN execution
    //
    @Override
    public NativeTypeFactory createTypeTranslator() {
        return new StandardNativeTypeToKotlinFactory();
    }

    @Override
    public FEELLib createFEELLib() {
        return new DefaultFEELLib();
    }

    @Override
    public String getDecisionBaseClass() {
        return DefaultDMNBaseDecision.class.getName();
    }
}
