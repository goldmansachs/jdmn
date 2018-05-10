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
import com.gs.dmn.feel.interpreter.FEELInterpreter;
import com.gs.dmn.feel.lib.FEELLib;
import com.gs.dmn.feel.synthesis.FEELTranslator;
import com.gs.dmn.feel.synthesis.type.FEELTypeTranslator;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.interpreter.DMNInterpreter;
import com.gs.dmn.transformation.DMNToJavaTransformer;
import com.gs.dmn.transformation.DMNTransformer;
import com.gs.dmn.transformation.basic.BasicDMN2JavaTransformer;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import com.gs.dmn.transformation.template.TemplateProvider;
import com.gs.dmn.validation.DMNValidator;

import java.util.Map;

public interface DMNDialectDefinition {
    //
    // FEEL Processors
    //
    FEELInterpreter createFEELInterpreter(DMNModelRepository repository);

    FEELTranslator createFEELTranslator(DMNModelRepository repository, Map<String, String> inputParameters);

    FEELInterpreter createSFEELInterpreter(DMNModelRepository repository);

    FEELTranslator createSFEELTranslator(DMNModelRepository repository, Map<String, String> inputParameters);

    //
    // DMN Processors
    //
    DMNInterpreter createDMNInterpreter(DMNModelRepository repository);

    DMNToJavaTransformer createDMNToJavaTransformer(DMNValidator dmnValidator, DMNTransformer dmnTransformer, TemplateProvider templateProvider, LazyEvaluationDetector lazyEvaluationDetector, Map<String, String> inputParameters, BuildLogger logger);

    BasicDMN2JavaTransformer createBasicTransformer(DMNModelRepository repository, LazyEvaluationDetector lazyEvaluationDetector, Map<String, String> inputParameters);

    //
    // Execution engine
    //
    FEELTypeTranslator createTypeTranslator();

    FEELLib createFEELLib();

    String getDecisionBaseClass();
}
