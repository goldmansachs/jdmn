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
package com.gs.dmn.signavio.transformation;

import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.serialization.TypeDeserializationConfigurer;
import com.gs.dmn.signavio.testlab.TestLab;
import com.gs.dmn.transformation.DMNTransformer;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import com.gs.dmn.transformation.template.TemplateProvider;
import com.gs.dmn.validation.DMNValidator;

public class SignavioDMNToKotlinTransformer<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends SignavioDMNToJavaTransformer<NUMBER, DATE, TIME, DATE_TIME, DURATION> {
    public SignavioDMNToKotlinTransformer(DMNDialectDefinition<NUMBER, DATE, TIME, DATE_TIME, DURATION, TestLab> dialectDefinition, DMNValidator dmnValidator, DMNTransformer<TestLab> dmnTransformer, TemplateProvider templateProvider, LazyEvaluationDetector lazyEvaluationDetector, TypeDeserializationConfigurer typeDeserializationConfigurer, InputParameters inputParameters, BuildLogger logger) {
        super(dialectDefinition, dmnValidator, dmnTransformer, templateProvider, lazyEvaluationDetector, typeDeserializationConfigurer, inputParameters, logger);
    }

    @Override
    protected String getFileExtension() {
        return ".kt";
    }
}
