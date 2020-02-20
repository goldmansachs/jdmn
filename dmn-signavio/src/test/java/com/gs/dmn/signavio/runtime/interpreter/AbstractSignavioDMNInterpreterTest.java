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
package com.gs.dmn.signavio.runtime.interpreter;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.feel.lib.FEELLib;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.runtime.interpreter.DMNInterpreter;
import com.gs.dmn.runtime.interpreter.Result;
import com.gs.dmn.runtime.interpreter.environment.RuntimeEnvironment;
import com.gs.dmn.runtime.interpreter.environment.RuntimeEnvironmentFactory;
import com.gs.dmn.serialization.DMNConstants;
import com.gs.dmn.serialization.DMNReader;
import com.gs.dmn.serialization.PrefixNamespaceMappings;
import com.gs.dmn.signavio.SignavioDMNModelRepository;
import com.gs.dmn.signavio.dialect.SignavioDMNDialectDefinition;
import org.omg.spec.dmn._20180521.model.TDRGElement;
import org.omg.spec.dmn._20180521.model.TDefinitions;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertEquals;

public abstract class AbstractSignavioDMNInterpreterTest {
    private static final BuildLogger LOGGER = new Slf4jBuildLogger(LoggerFactory.getLogger(AbstractSignavioDMNInterpreterTest.class));

    private final DMNReader reader = new DMNReader(LOGGER, false);
    private final DMNDialectDefinition dialectDefinition = new SignavioDMNDialectDefinition();

    protected void doTest(DecisionTestConfig config) throws Exception {
        doTest(config.getDecisionName(), config.getDiagramName(), config.getRuntimeContext(), config.getExpectedResult());
    }

    protected void doTest(String decisionName, String diagramName, RuntimeEnvironment runtimeEnvironment, Object expectedResult) throws Exception {
        String errorMessage = String.format("Tested failed for diagram '%s'", diagramName);
        try {
            String pathName = getInputPath() + "/" + diagramName + DMNConstants.DMN_FILE_EXTENSION;
            URL url = getClass().getClassLoader().getResource(pathName).toURI().toURL();
            Pair<TDefinitions, PrefixNamespaceMappings> pair = reader.read(url);
            DMNModelRepository repository = new SignavioDMNModelRepository(pair, "http://www.provider.com/schema/dmn/1.1/");
            DMNInterpreter interpreter = dialectDefinition.createDMNInterpreter(repository);

            TDRGElement decision = repository.findDRGElementByName(decisionName);
            Result actualResult = interpreter.evaluate(null, decision, null, runtimeEnvironment);
            Object actualValue = Result.value(actualResult);

            assertEquals(errorMessage, expectedResult, actualValue);
        } catch (Exception e) {
            throw e;
        }
    }

    protected RuntimeEnvironment makeRuntimeEnvironment(List<Pair<String, ?>> pairs) {
        RuntimeEnvironment environment = RuntimeEnvironmentFactory.instance().makeEnvironment();
        for (Pair<String, ?> pair : pairs) {
            environment.bind(pair.getLeft(), pair.getRight());
        }
        return environment;
    }

    protected abstract String getInputPath();

    protected abstract FEELLib getLib();
}
