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
import com.gs.dmn.feel.analysis.semantics.environment.EnvironmentFactory;
import com.gs.dmn.feel.interpreter.FEELInterpreter;
import com.gs.dmn.feel.lib.FEELLib;
import com.gs.dmn.feel.synthesis.FEELTranslator;
import com.gs.dmn.feel.synthesis.type.NativeTypeFactory;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.interpreter.DMNInterpreter;
import com.gs.dmn.serialization.TypeDeserializationConfigurer;
import com.gs.dmn.transformation.DMNToNativeTransformer;
import com.gs.dmn.transformation.DMNTransformer;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.basic.BasicDMNToJavaTransformer;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import com.gs.dmn.transformation.template.TemplateProvider;
import com.gs.dmn.validation.DMNValidator;

import java.util.Map;

public interface DMNDialectDefinition<NUMBER, DATE, TIME, DATE_TIME, DURATION, TEST> {
    //
    // FEEL Processors
    //
    EnvironmentFactory createEnvironmentFactory();

    /**
     * @deprecated  Replaced by {@link #createFEELInterpreter(DMNModelRepository, InputParameters)}
     */
    @Deprecated
    default FEELInterpreter createFEELInterpreter(DMNModelRepository repository, Map<String, String> inputParameters) {
        return createFEELInterpreter(repository, new InputParameters(inputParameters));
    }
    FEELInterpreter createFEELInterpreter(DMNModelRepository repository, InputParameters inputParameters);

    /**
     * @deprecated  Replaced by {@link #createFEELTranslator(DMNModelRepository, InputParameters)}
     */
    @Deprecated
    default FEELTranslator createFEELTranslator(DMNModelRepository repository, Map<String, String> inputParameters) {
        return createFEELTranslator(repository, new InputParameters(inputParameters));
    }
    FEELTranslator createFEELTranslator(DMNModelRepository repository, InputParameters inputParameters);

    //
    // DMN Processors
    //
    /**
     * @deprecated  Replaced by {@link #createDMNInterpreter(DMNModelRepository, InputParameters)}
     */
    @Deprecated
    default DMNInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> createDMNInterpreter(DMNModelRepository repository, Map<String, String> inputParameters) {
        return createDMNInterpreter(repository, new InputParameters(inputParameters));
    }
    DMNInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> createDMNInterpreter(DMNModelRepository repository, InputParameters inputParameters);

    /**
     * @deprecated  Replaced by {@link #createDMNToNativeTransformer(DMNValidator, DMNTransformer, TemplateProvider, LazyEvaluationDetector, TypeDeserializationConfigurer, InputParameters, BuildLogger)}
     */
    @Deprecated
    default DMNToNativeTransformer createDMNToNativeTransformer(DMNValidator dmnValidator, DMNTransformer<TEST> dmnTransformer, TemplateProvider templateProvider, LazyEvaluationDetector lazyEvaluationDetector, TypeDeserializationConfigurer typeDeserializationConfigurer, Map<String, String> inputParameters, BuildLogger logger) {
        return createDMNToNativeTransformer(dmnValidator, dmnTransformer, templateProvider, lazyEvaluationDetector, typeDeserializationConfigurer, new InputParameters(inputParameters), logger);
    }
    DMNToNativeTransformer createDMNToNativeTransformer(DMNValidator dmnValidator, DMNTransformer<TEST> dmnTransformer, TemplateProvider templateProvider, LazyEvaluationDetector lazyEvaluationDetector, TypeDeserializationConfigurer typeDeserializationConfigurer, InputParameters inputParameters, BuildLogger logger);

    /**
     * @deprecated  Replaced by {@link #createBasicTransformer(DMNModelRepository, LazyEvaluationDetector, InputParameters)}
     */
    @Deprecated
    default BasicDMNToJavaTransformer createBasicTransformer(DMNModelRepository repository, LazyEvaluationDetector lazyEvaluationDetector, Map<String, String> inputParameters) {
        return createBasicTransformer(repository, lazyEvaluationDetector, new InputParameters(inputParameters));
    }
    BasicDMNToJavaTransformer createBasicTransformer(DMNModelRepository repository, LazyEvaluationDetector lazyEvaluationDetector, InputParameters inputParameters);

    //
    // Execution engine
    //
    NativeTypeFactory createNativeTypeFactory();

    FEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> createFEELLib();

    String getDecisionBaseClass();
}
