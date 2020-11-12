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
package com.gs.dmn.fitnesse.fixture.feel;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.feel.interpreter.FEELInterpreter;
import com.gs.dmn.fitnesse.fixture.AbstractFixture;
import com.gs.dmn.transformation.InputParameters;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class FEELFixture extends AbstractFixture {
    protected final FEELInterpreter feelInterpreter;
    protected final DMNModelRepository repository;

    public FEELFixture() {
        this.repository = new DMNModelRepository();
        InputParameters inputParameters = makeInputParameters();
        this.feelInterpreter = this.dialectDefinition.createFEELInterpreter(this.repository, inputParameters);
    }

    protected InputParameters makeInputParameters() {
        return new InputParameters(makeInputParametersMap());
    }

    protected Map<String, String> makeInputParametersMap() {
        Map<String, String> inputParams = new LinkedHashMap<>();
        inputParams.put("dmnVersion", "1.1");
        inputParams.put("modelVersion", "1.0");
        inputParams.put("platformVersion", "1.0");
        return inputParams;
    }
}
