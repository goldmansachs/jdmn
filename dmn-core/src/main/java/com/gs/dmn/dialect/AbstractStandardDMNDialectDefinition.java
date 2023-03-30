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
import com.gs.dmn.context.environment.EnvironmentFactory;
import com.gs.dmn.feel.analysis.semantics.environment.StandardEnvironmentFactory;
import com.gs.dmn.feel.interpreter.AbstractFEELInterpreter;
import com.gs.dmn.feel.interpreter.StandardFEELInterpreter;
import com.gs.dmn.feel.interpreter.TypeConverter;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.interpreter.DMNInterpreter;
import com.gs.dmn.runtime.interpreter.StandardDMNInterpreter;
import com.gs.dmn.serialization.DMNSerializer;
import com.gs.dmn.serialization.SerializationFormat;
import com.gs.dmn.serialization.jackson.JsonDMNSerializer;
import com.gs.dmn.serialization.xstream.XMLDMNSerializer;
import com.gs.dmn.tck.ast.TestCases;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;

import static com.gs.dmn.serialization.SerializationFormat.JSON;
import static com.gs.dmn.serialization.SerializationFormat.XML;

public abstract class AbstractStandardDMNDialectDefinition<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends AbstractDMNDialectDefinition<NUMBER, DATE, TIME, DATE_TIME, DURATION, TestCases> {
    //
    // Interpreter
    //
    @Override
    protected AbstractFEELInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> createFEELInterpreter(DMNInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> dmnInterpreter) {
        return new StandardFEELInterpreter<>(dmnInterpreter);
    }

    //
    // Serialization
    //
    @Override
    public DMNSerializer createDMNSerializer(BuildLogger logger, InputParameters inputParameters) {
        SerializationFormat format = inputParameters.getFormat();
        if (XML == format) {
            return new XMLDMNSerializer(logger, inputParameters.isXsdValidation());
        } else if (format == JSON) {
            return new JsonDMNSerializer(logger, inputParameters.isXsdValidation());
        } else {
            throw new IllegalArgumentException(String.format("Format '%s' is not supported yet", format));
        }
    }

    //
    // DMN Processors
    //
    @Override
    public DMNInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> createDMNInterpreter(DMNModelRepository repository, InputParameters inputParameters) {
        return new StandardDMNInterpreter<>(createBasicTransformer(repository, new NopLazyEvaluationDetector(), inputParameters), createFEELLib(), new TypeConverter());
    }

    @Override
    public EnvironmentFactory createEnvironmentFactory() {
        return StandardEnvironmentFactory.instance();
    }
}
