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
import com.gs.dmn.feel.analysis.semantics.environment.StandardEnvironmentFactory;
import com.gs.dmn.runtime.interpreter.DMNInterpreter;
import com.gs.dmn.runtime.interpreter.StandardDMNInterpreter;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;
import org.omg.dmn.tck.marshaller._20160719.TestCases;

public abstract class AbstractStandardDMNDialectDefinition<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends AbstractDMNDialectDefinition<NUMBER, DATE, TIME, DATE_TIME, DURATION, TestCases> {
    //
    // DMN Processors
    //
    @Override
    public DMNInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> createDMNInterpreter(DMNModelRepository repository, InputParameters inputParameters) {
        return new StandardDMNInterpreter<>(createBasicTransformer(repository, new NopLazyEvaluationDetector(), inputParameters), createFEELLib());
    }

    @Override
    public EnvironmentFactory createEnvironmentFactory() {
        return StandardEnvironmentFactory.instance();
    }
}
