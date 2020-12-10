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
import com.gs.dmn.serialization.DefaultTypeDeserializationConfigurer;
import com.gs.dmn.serialization.TypeDeserializationConfigurer;
import com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision;
import com.gs.dmn.signavio.runtime.SignavioEnvironmentFactory;
import com.gs.dmn.signavio.testlab.TestLab;
import com.gs.dmn.signavio.transformation.template.SignavioTreeTemplateProvider;
import com.gs.dmn.transformation.AbstractDMNTransformerTest;
import com.gs.dmn.transformation.DMNTransformer;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;
import com.gs.dmn.transformation.template.TemplateProvider;
import com.gs.dmn.validation.DMNValidator;
import com.gs.dmn.validation.NopDMNValidator;

import java.net.URI;
import java.util.Map;

public abstract class AbstractSignavioDMNToNativeTest<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends AbstractDMNTransformerTest<NUMBER, DATE, TIME, DATE_TIME, DURATION, TestLab> {
    @Override
    protected URI resource(String path) {
        return signavioResource(path);
    }

    @Override
    protected DMNValidator makeDMNValidator(BuildLogger logger) {
        return new NopDMNValidator();
    }

    @Override
    protected DMNTransformer<TestLab> makeDMNTransformer(BuildLogger logger) {
        return new RuleDescriptionTransformer();
    }

    @Override
    protected TemplateProvider makeTemplateProvider() {
        return new SignavioTreeTemplateProvider();
    }

    @Override
    protected LazyEvaluationDetector makeLazyEvaluationDetector(InputParameters inputParameters, BuildLogger logger) {
        return new NopLazyEvaluationDetector();
    }

    @Override
    protected TypeDeserializationConfigurer makeTypeDeserializationConfigurer(BuildLogger logger) {
        return new DefaultTypeDeserializationConfigurer();
    }

    @Override
    protected Map<String, String> makeInputParametersMap() {
        Map<String, String> inputParams = super.makeInputParametersMap();
        inputParams.put("semanticValidation", "false");
        inputParams.put("signavioSchemaNamespace", "http://www.provider.com/schema/dmn/1.1/");
        inputParams.put("environmentFactoryClass", SignavioEnvironmentFactory.class.getName());
        inputParams.put("decisionBaseClass", DefaultSignavioBaseDecision.class.getName());
        return inputParams;
    }

    @Override
    protected abstract DMNDialectDefinition<NUMBER, DATE, TIME, DATE_TIME, DURATION, TestLab> makeDialectDefinition();
}
