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

import com.gs.dmn.AbstractTest;
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
import com.gs.dmn.signavio.testlab.TestLab;
import com.gs.dmn.transformation.InputParameters;
import org.omg.spec.dmn._20191111.model.TDRGElement;
import org.omg.spec.dmn._20191111.model.TDefinitions;
import org.slf4j.LoggerFactory;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

import static org.junit.Assert.assertEquals;

public abstract class AbstractSignavioDMNInterpreterTest extends AbstractTest {
    private static final BuildLogger LOGGER = new Slf4jBuildLogger(LoggerFactory.getLogger(AbstractSignavioDMNInterpreterTest.class));

    private final DMNReader reader = new DMNReader(LOGGER, false);
    private final DMNDialectDefinition<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar, Duration, TestLab> dialectDefinition = new SignavioDMNDialectDefinition();

    protected void doTest(DecisionTestConfig config) throws Exception {
        doTest(config.getDecisionName(), config.getDiagramName(), config.getRuntimeContext(), config.getExpectedResult());
    }

    protected void doTest(String decisionName, String diagramName, RuntimeEnvironment runtimeEnvironment, Object expectedResult) throws Exception {
        String errorMessage = String.format("Tested failed for diagram '%s'", diagramName);
        try {
            String pathName = getInputPath() + "/" + diagramName + DMNConstants.DMN_FILE_EXTENSION;
            URI uri = signavioResource(pathName);
            Pair<TDefinitions, PrefixNamespaceMappings> pair = reader.read(uri.toURL());
            DMNModelRepository repository = new SignavioDMNModelRepository(pair, "http://www.provider.com/schema/dmn/1.1/");
            DMNInterpreter<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar, Duration> interpreter = dialectDefinition.createDMNInterpreter(repository, makeInputParameters());

            TDRGElement decision = repository.findDRGElementByName(repository.getRootDefinitions(), decisionName);
            Result actualResult = interpreter.evaluate(repository.makeDRGElementReference(decision), null, runtimeEnvironment);
            Object actualValue = Result.value(actualResult);

            assertEquals(errorMessage, expectedResult, actualValue);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    protected InputParameters makeInputParameters() {
        return new InputParameters(makeInputParametersMap());
    }

    protected RuntimeEnvironment makeRuntimeEnvironment(List<Pair<String, ?>> pairs) {
        RuntimeEnvironment environment = RuntimeEnvironmentFactory.instance().makeEnvironment();
        for (Pair<String, ?> pair : pairs) {
            environment.bind(pair.getLeft(), pair.getRight());
        }
        return environment;
    }

    protected abstract String getInputPath();

    protected abstract FEELLib<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar, Duration> getLib();
}
