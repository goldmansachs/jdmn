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
import com.gs.dmn.feel.lib.FEELLib;
import com.gs.dmn.feel.lib.PureJavaTimeFEELLib;
import com.gs.dmn.feel.synthesis.type.NativeTypeFactory;
import com.gs.dmn.feel.synthesis.type.PureJavaTimeNativeTypeFactory;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.PureJavaTimeDMNBaseDecision;
import com.gs.dmn.serialization.TypeDeserializationConfigurer;
import com.gs.dmn.transformation.DMNToJavaTransformer;
import com.gs.dmn.transformation.DMNToNativeTransformer;
import com.gs.dmn.transformation.DMNTransformer;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.basic.BasicDMNToJavaTransformer;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import com.gs.dmn.transformation.template.TemplateProvider;
import com.gs.dmn.validation.DMNValidator;
import org.omg.dmn.tck.marshaller._20160719.TestCases;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;

public class PureJavaTimeDMNDialectDefinition extends AbstractStandardDMNDialectDefinition<BigDecimal, LocalDate, Temporal, Temporal, TemporalAmount> {
    //
    // DMN Processors
    //
    @Override
    public DMNToNativeTransformer createDMNToNativeTransformer(DMNValidator dmnValidator, DMNTransformer<TestCases> dmnTransformer, TemplateProvider templateProvider, LazyEvaluationDetector lazyEvaluationDetector, TypeDeserializationConfigurer typeDeserializationConfigurer, InputParameters inputParameters, BuildLogger logger) {
        return new DMNToJavaTransformer<>(this, dmnValidator, dmnTransformer, templateProvider, lazyEvaluationDetector, typeDeserializationConfigurer, inputParameters, logger);
    }

    @Override
    public BasicDMNToJavaTransformer createBasicTransformer(DMNModelRepository repository, LazyEvaluationDetector lazyEvaluationDetector, InputParameters inputParameters) {
        EnvironmentFactory environmentFactory = createEnvironmentFactory();
        return new BasicDMNToJavaTransformer(this, repository, environmentFactory, createNativeTypeFactory(), lazyEvaluationDetector, inputParameters);
    }

    //
    // DMN execution
    //
    @Override
    public NativeTypeFactory createNativeTypeFactory() {
        return new PureJavaTimeNativeTypeFactory();
    }

    @Override
    public FEELLib<BigDecimal, LocalDate, Temporal, Temporal, TemporalAmount> createFEELLib() {
        return new PureJavaTimeFEELLib();
    }

    @Override
    public String getDecisionBaseClass() {
        return PureJavaTimeDMNBaseDecision.class.getName();
    }
}
