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
import com.gs.dmn.feel.lib.MixedJavaTimeFEELLib;
import com.gs.dmn.feel.synthesis.type.MixedJavaTimeKotlinNativeTypeFactory;
import com.gs.dmn.feel.synthesis.type.NativeTypeFactory;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.MixedJavaTimeDMNBaseDecision;
import com.gs.dmn.serialization.TypeDeserializationConfigurer;
import com.gs.dmn.transformation.DMNToKotlinTransformer;
import com.gs.dmn.transformation.DMNToNativeTransformer;
import com.gs.dmn.transformation.DMNTransformer;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.basic.BasicDMNToJavaTransformer;
import com.gs.dmn.transformation.basic.BasicDMNToKotlinTransformer;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import com.gs.dmn.transformation.template.TemplateProvider;
import com.gs.dmn.validation.DMNValidator;
import org.omg.dmn.tck.marshaller._20160719.TestCases;

import javax.xml.datatype.Duration;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZonedDateTime;

public class MixedJavaTimeKotlinStandardDMNDialectDefinition extends AbstractStandardDMNDialectDefinition<BigDecimal, LocalDate, OffsetTime, ZonedDateTime, Duration> {
    //
    // DMN Processors
    //
    @Override
    public DMNToNativeTransformer createDMNToNativeTransformer(DMNValidator dmnValidator, DMNTransformer<TestCases> dmnTransformer, TemplateProvider templateProvider, LazyEvaluationDetector lazyEvaluationDetector, TypeDeserializationConfigurer typeDeserializationConfigurer, InputParameters inputParameters, BuildLogger logger) {
        return new DMNToKotlinTransformer<>(this, dmnValidator, dmnTransformer, templateProvider, lazyEvaluationDetector, typeDeserializationConfigurer, inputParameters, logger);
    }

    @Override
    public BasicDMNToJavaTransformer createBasicTransformer(DMNModelRepository repository, LazyEvaluationDetector lazyEvaluationDetector, InputParameters inputParameters) {
        EnvironmentFactory environmentFactory = createEnvironmentFactory();
        return new BasicDMNToKotlinTransformer(this, repository, environmentFactory, createNativeTypeFactory(), lazyEvaluationDetector, inputParameters);
    }

    //
    // DMN execution
    //
    @Override
    public NativeTypeFactory createNativeTypeFactory() {
        return new MixedJavaTimeKotlinNativeTypeFactory();
    }

    @Override
    public FEELLib<BigDecimal, LocalDate, OffsetTime, ZonedDateTime, Duration> createFEELLib() {
        return new MixedJavaTimeFEELLib();
    }

    @Override
    public String getDecisionBaseClass() {
        return MixedJavaTimeDMNBaseDecision.class.getName();
    }
}
