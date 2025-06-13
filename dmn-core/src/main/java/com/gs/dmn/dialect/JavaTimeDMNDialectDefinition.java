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
import com.gs.dmn.feel.lib.FEELLib;
import com.gs.dmn.feel.lib.JavaTimeFEELLib;
import com.gs.dmn.feel.synthesis.type.JavaTimeNativeTypeFactory;
import com.gs.dmn.feel.synthesis.type.NativeTypeFactory;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.JavaTimeDMNBaseDecision;
import com.gs.dmn.serialization.TypeDeserializationConfigurer;
import com.gs.dmn.tck.ast.TestCases;
import com.gs.dmn.transformation.DMNToJavaTransformer;
import com.gs.dmn.transformation.DMNToNativeTransformer;
import com.gs.dmn.transformation.DMNTransformer;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.basic.BasicDMNToJavaTransformer;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import com.gs.dmn.transformation.template.TemplateProvider;
import com.gs.dmn.validation.DMNValidator;

import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;

public class JavaTimeDMNDialectDefinition extends AbstractStandardDMNDialectDefinition<Number, LocalDate, TemporalAccessor, TemporalAccessor, TemporalAmount> {
    //
    // DMN Processors
    //
    @Override
    public DMNToNativeTransformer createDMNToNativeTransformer(DMNValidator dmnValidator, DMNTransformer<TestCases> dmnTransformer, TemplateProvider templateProvider, LazyEvaluationDetector lazyEvaluationDetector, TypeDeserializationConfigurer typeDeserializationConfigurer, InputParameters inputParameters, BuildLogger logger) {
        return new DMNToJavaTransformer<>(this, dmnValidator, dmnTransformer, templateProvider, lazyEvaluationDetector, typeDeserializationConfigurer, inputParameters, logger);
    }

    @Override
    public BasicDMNToJavaTransformer createBasicTransformer(DMNModelRepository repository, LazyEvaluationDetector lazyEvaluationDetector, InputParameters inputParameters) {
        return new BasicDMNToJavaTransformer(this, repository, lazyEvaluationDetector, inputParameters);
    }

    //
    // DMN execution
    //
    @Override
    public NativeTypeFactory createNativeTypeFactory() {
        return new JavaTimeNativeTypeFactory();
    }

    @Override
    public FEELLib<Number, LocalDate, TemporalAccessor, TemporalAccessor, TemporalAmount> createFEELLib() {
        return new JavaTimeFEELLib();
    }

    @Override
    public String getDecisionBaseClass() {
        return JavaTimeDMNBaseDecision.class.getName();
    }
}
