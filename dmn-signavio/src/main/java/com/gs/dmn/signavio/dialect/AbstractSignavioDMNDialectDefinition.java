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
import com.gs.dmn.feel.interpreter.AbstractFEELInterpreter;
import com.gs.dmn.feel.interpreter.SignavioFEELInterpreter;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.interpreter.DMNInterpreter;
import com.gs.dmn.serialization.DMNSerializer;
import com.gs.dmn.serialization.SerializationFormat;
import com.gs.dmn.serialization.jackson.JsonDMNSerializer;
import com.gs.dmn.serialization.xstream.XMLDMNSerializer;
import com.gs.dmn.signavio.runtime.SignavioEnvironmentFactory;
import com.gs.dmn.signavio.runtime.interpreter.SignavioDMNInterpreter;
import com.gs.dmn.signavio.serialization.xstream.SignavioExtensionRegister;
import com.gs.dmn.signavio.testlab.TestLab;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;

import java.util.Collections;

import static com.gs.dmn.serialization.SerializationFormat.JSON;
import static com.gs.dmn.serialization.SerializationFormat.XML;

public abstract class AbstractSignavioDMNDialectDefinition<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends AbstractDMNDialectDefinition<NUMBER, DATE, TIME, DATE_TIME, DURATION, TestLab> {
    //
    // Interpreter
    //
    @Override
    protected AbstractFEELInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> createFEELInterpreter(DMNInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> dmnInterpreter) {
        return new SignavioFEELInterpreter<>(dmnInterpreter);
    }

    //
    // Serialization
    //
    @Override
    public DMNSerializer createDMNSerializer(BuildLogger logger, InputParameters inputParameters) {
        SerializationFormat format = inputParameters.getFormat();
        if (format == XML) {
            return new XMLDMNSerializer(logger, Collections.singletonList(new SignavioExtensionRegister(inputParameters.getSchemaNamespace())), inputParameters);
        } else if (format == JSON) {
            return new JsonDMNSerializer(logger, inputParameters);
        } else {
            throw new IllegalArgumentException(String.format("Format '%s' is not supported yet", format));
        }
    }

    //
    // DMN processors
    //
    @Override
    public DMNInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> createDMNInterpreter(DMNModelRepository repository, InputParameters inputParameters) {
        return new SignavioDMNInterpreter<>(createBasicTransformer(repository, new NopLazyEvaluationDetector(), inputParameters), createFEELLib());
    }

    @Override
    public EnvironmentFactory createEnvironmentFactory() {
        return SignavioEnvironmentFactory.instance();
    }
}
