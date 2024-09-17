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
import com.gs.dmn.DRGElementReference;
import com.gs.dmn.ast.TDecision;
import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.feel.lib.FEELLib;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.runtime.interpreter.DMNInterpreter;
import com.gs.dmn.runtime.interpreter.EvaluationContext;
import com.gs.dmn.runtime.interpreter.Result;
import com.gs.dmn.serialization.DMNConstants;
import com.gs.dmn.serialization.DMNSerializer;
import com.gs.dmn.signavio.SignavioDMNModelRepository;
import com.gs.dmn.signavio.dialect.JavaTimeSignavioDMNDialectDefinition;
import com.gs.dmn.signavio.testlab.TestLab;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URI;
import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.gs.dmn.signavio.SignavioTestConstants.SIG_EXT_NAMESPACE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractSignavioDMNInterpreterTest extends AbstractTest {
    private static final BuildLogger LOGGER = new Slf4jBuildLogger(LoggerFactory.getLogger(AbstractSignavioDMNInterpreterTest.class));

    private final DMNDialectDefinition<Number, LocalDate, TemporalAccessor, TemporalAccessor, TemporalAmount, TestLab> dialectDefinition = new JavaTimeSignavioDMNDialectDefinition();
    private final DMNSerializer serializer = this.dialectDefinition.createDMNSerializer(LOGGER, this.inputParameters);

    protected void doTest(DecisionTestConfig config) throws Exception {
        doTest(config.getDecisionName(), config.getDiagramName(), config.getRuntimeContext(), config.getExpectedResult());
    }

    protected void doTest(String decisionName, String diagramName, Map<String, Object> inputRequirements, Object expectedResult) throws Exception {
        String errorMessage = String.format("Tested failed for diagram '%s'", diagramName);
        try {
            String pathName = getInputPath() + "/" + diagramName + DMNConstants.DMN_FILE_EXTENSION;
            URI uri = signavioResource(pathName);
            File dmnFile = new File(uri.getPath());
            TDefinitions definitions = this.serializer.readModel(dmnFile);
            DMNModelRepository repository = new SignavioDMNModelRepository(definitions, SIG_EXT_NAMESPACE);
            DMNInterpreter<Number, LocalDate, TemporalAccessor, TemporalAccessor, TemporalAmount> interpreter = this.dialectDefinition.createDMNInterpreter(repository, this.inputParameters);

            TDecision decision = (TDecision) repository.findDRGElementByName(repository.getRootDefinitions(), decisionName);
            DRGElementReference<TDecision> reference = repository.makeDRGElementReference(decision);
            Result actualResult = interpreter.evaluateDecision(reference.getNamespace(), reference.getElementName(), EvaluationContext.makeDecisionEvaluationContext(decision, inputRequirements));
            Object actualValue = Result.value(actualResult);

            assertEquals(expectedResult, actualValue, errorMessage);
        } catch (Exception e) {
            throw e;
        }
    }

    protected Map<String, Object> makeInformationRequirements(List<Pair<String, ?>> pairs) {
        Map<String, Object> environment = new LinkedHashMap<>();
        for (Pair<String, ?> pair : pairs) {
            environment.put(pair.getLeft(), pair.getRight());
        }
        return environment;
    }

    @Override
    protected Map<String, String> makeInputParametersMap() {
        Map<String, String> inputParams = new LinkedHashMap<>();
        inputParams.put("dmnVersion", "1.1");
        inputParams.put("modelVersion", "1.0");
        inputParams.put("platformVersion", "1.0");
        inputParams.put("signavioSchemaNamespace", SIG_EXT_NAMESPACE);
        return inputParams;
    }

    protected abstract String getInputPath();

    protected abstract FEELLib<Number, LocalDate, TemporalAccessor, TemporalAccessor, TemporalAmount> getLib();
}
