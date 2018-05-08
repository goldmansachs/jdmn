/**
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
import com.gs.dmn.feel.synthesis.type.FEELTypeTranslator;
import com.gs.dmn.feel.synthesis.type.StandardFEELTypeTranslator;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.DefaultDMNBaseDecision;
import com.gs.dmn.runtime.interpreter.DMNInterpreter;
import com.gs.dmn.transformation.DMNToJavaTransformer;
import com.gs.dmn.transformation.DMNTransformer;
import com.gs.dmn.transformation.basic.BasicDMN2JavaTransformer;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;
import com.gs.dmn.transformation.template.TemplateProvider;
import com.gs.dmn.validation.DMNValidator;

import java.util.LinkedHashMap;
import java.util.Map;

public class StandardDMNDialectDefinition extends AbstractDMNDialectDefinition {
    //
    // DMN Processors
    //
    @Override
    public DMNInterpreter createDMNInterpreter(DMNModelRepository repository) {
        return new DMNInterpreter(createBasicTransformer(repository, new NopLazyEvaluationDetector(), new LinkedHashMap<>()), createFEELLib());
    }

    @Override
    public DMNToJavaTransformer createDMNToJavaTransformer(DMNValidator dmnValidator, DMNTransformer dmnTransformer, TemplateProvider templateProvider, LazyEvaluationDetector lazyEvaluationDetector, Map<String, String> inputParameters, BuildLogger logger) {
        return new DMNToJavaTransformer(this, dmnValidator, dmnTransformer, templateProvider, lazyEvaluationDetector, inputParameters, logger);
    }

    @Override
    public BasicDMN2JavaTransformer createBasicTransformer(DMNModelRepository repository, LazyEvaluationDetector lazyEvaluationDetector, Map<String, String> inputParameters) {
        EnvironmentFactory environmentFactory = createEnvironmentFactory();
        return new BasicDMN2JavaTransformer(repository, environmentFactory, createTypeTranslator(), lazyEvaluationDetector, inputParameters);
    }

    private EnvironmentFactory createEnvironmentFactory() {
        return DefaultDMNEnvironmentFactory.instance();
    }

    //
    // DMN execution
    //
    @Override
    public FEELTypeTranslator createTypeTranslator() {
        return new StandardFEELTypeTranslator();
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
