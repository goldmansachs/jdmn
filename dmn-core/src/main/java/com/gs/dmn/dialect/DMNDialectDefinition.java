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
import com.gs.dmn.context.environment.EnvironmentFactory;
import com.gs.dmn.el.interpreter.ELInterpreter;
import com.gs.dmn.el.synthesis.ELTranslator;
import com.gs.dmn.feel.lib.FEELLib;
import com.gs.dmn.feel.synthesis.type.NativeTypeFactory;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.interpreter.DMNInterpreter;
import com.gs.dmn.serialization.DMNSerializer;
import com.gs.dmn.serialization.TypeDeserializationConfigurer;
import com.gs.dmn.transformation.DMNToNativeTransformer;
import com.gs.dmn.transformation.DMNTransformer;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.basic.BasicDMNToJavaTransformer;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import com.gs.dmn.transformation.template.TemplateProvider;
import com.gs.dmn.validation.DMNValidator;

public interface DMNDialectDefinition<NUMBER, DATE, TIME, DATE_TIME, DURATION, TEST> {
    //
    // Serialization
    //
    DMNSerializer createDMNSerializer(BuildLogger logger, InputParameters inputParameters);

    //
    // FEEL Processors
    //
    EnvironmentFactory createEnvironmentFactory();

    ELInterpreter createFEELInterpreter(DMNModelRepository repository, InputParameters inputParameters);

    ELTranslator createFEELTranslator(DMNModelRepository repository, InputParameters inputParameters);

    //
    // DMN Processors
    //
    DMNInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> createDMNInterpreter(DMNModelRepository repository, InputParameters inputParameters);

    DMNToNativeTransformer createDMNToNativeTransformer(DMNValidator dmnValidator, DMNTransformer<TEST> dmnTransformer, TemplateProvider templateProvider, LazyEvaluationDetector lazyEvaluationDetector, TypeDeserializationConfigurer typeDeserializationConfigurer, InputParameters inputParameters, BuildLogger logger);

    BasicDMNToJavaTransformer createBasicTransformer(DMNModelRepository repository, LazyEvaluationDetector lazyEvaluationDetector, InputParameters inputParameters);

    //
    // Execution engine
    //
    NativeTypeFactory createNativeTypeFactory();

    FEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> createFEELLib();

    String getDecisionBaseClass();
}
