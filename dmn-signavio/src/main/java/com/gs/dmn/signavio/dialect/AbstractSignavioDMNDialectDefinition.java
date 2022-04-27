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
package com.gs.dmn.signavio.dialect;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.context.environment.EnvironmentFactory;
import com.gs.dmn.dialect.AbstractDMNDialectDefinition;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.interpreter.DMNInterpreter;
import com.gs.dmn.serialization.DMNReader;
import com.gs.dmn.serialization.DMNWriter;
import com.gs.dmn.signavio.runtime.SignavioEnvironmentFactory;
import com.gs.dmn.signavio.runtime.interpreter.SignavioDMNInterpreter;
import com.gs.dmn.signavio.runtime.interpreter.SignavioTypeConverter;
import com.gs.dmn.signavio.serialization.xstream.SignavioExtensionRegister;
import com.gs.dmn.signavio.testlab.TestLab;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;

import java.util.Arrays;

public abstract class AbstractSignavioDMNDialectDefinition<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends AbstractDMNDialectDefinition<NUMBER, DATE, TIME, DATE_TIME, DURATION, TestLab> {
    //
    // Serialization
    //
    @Override
    public DMNReader createDMNReader(BuildLogger logger, InputParameters inputParameters) {
        return new DMNReader(logger, inputParameters.isXsdValidation(), Arrays.asList(new SignavioExtensionRegister(inputParameters.getSchemaNamespace())));
    }

    @Override
    public DMNWriter createDMNWriter(BuildLogger logger, InputParameters inputParameters) {
        return new DMNWriter(logger, Arrays.asList(new SignavioExtensionRegister(inputParameters.getSchemaNamespace())));
    }

    //
    // DMN processors
    //
    @Override
    public DMNInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> createDMNInterpreter(DMNModelRepository repository, InputParameters inputParameters) {
        return new SignavioDMNInterpreter<>(createBasicTransformer(repository, new NopLazyEvaluationDetector(), inputParameters), createFEELLib(), new SignavioTypeConverter());
    }

    @Override
    public EnvironmentFactory createEnvironmentFactory() {
        return SignavioEnvironmentFactory.instance();
    }
}
