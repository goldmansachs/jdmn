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
package com.gs.dmn.signavio.transformation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.serialization.DMNReader;
import com.gs.dmn.signavio.SignavioDMNModelRepository;
import com.gs.dmn.signavio.testlab.TestLab;
import com.gs.dmn.signavio.transformation.config.Correction;
import com.gs.dmn.signavio.transformation.config.DecisionTableCorrection;
import com.gs.dmn.transformation.AbstractFileTransformerTest;
import org.junit.Test;
import org.omg.spec.dmn._20180521.model.TDRGElement;
import org.omg.spec.dmn._20180521.model.TDecision;
import org.omg.spec.dmn._20180521.model.TDecisionTable;
import org.omg.spec.dmn._20180521.model.TExpression;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class CorrectPathsInDecisionsTransformerTest extends AbstractFileTransformerTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String BASE_PATH = "dmn2java/exported/complex";

    private final DMNReader dmnReader = new DMNReader(LOGGER, false);

    @Test
    public void testConfigWhenNoConfig() {
        try {
            configTransformer(null);
            fail("Should throw exception");
        } catch (Exception e) {
            assertTrue(e instanceof DMNRuntimeException);
            assertEquals("Invalid transformer configuration: Incorrect or missing 'corrections' node", e.getMessage());
        }
    }

    @Test
    public void testConfigWhenNullCorrections() {
        try {
            configTransformer(
                    signavioResource("configuration/credit-decision-with-null-corrections.json")
            );
            fail("Should throw exception");
        } catch (Exception e) {
            assertTrue(e instanceof DMNRuntimeException);
            assertEquals("Invalid transformer configuration: Incorrect or missing 'corrections' node", e.getMessage());
        }
    }

    @Test
    public void testConfigWhenEmptyCorrections() {
        try {
            configTransformer(
                    signavioResource("configuration/credit-decision-with-no-corrections.json")
            );
            fail("Should throw exception");
        } catch (Exception e) {
            assertTrue(e instanceof DMNRuntimeException);
            assertEquals("Invalid transformer configuration: Incorrect or missing 'correction' nodes", e.getMessage());
        }
    }

    @Test
    public void testConfigWhenCorrectionIsNotMap() {
        try {
            configTransformer(
                    signavioResource("configuration/credit-decision-with-string-correction.json")
            );
            fail("Should throw exception");
        } catch (Exception e) {
            assertTrue(e instanceof DMNRuntimeException);
            assertEquals("Invalid transformer configuration: Incorrect or missing 'correction' nodes", e.getMessage());
        }
    }

    @Test
    public void testConfigWhenCorrectionIsNotListOfMap() {
        try {
            configTransformer(
                    signavioResource("configuration/credit-decision-with-string-corrections.json")
            );
            fail("Should throw exception");
        } catch (Exception e) {
            assertTrue(e instanceof DMNRuntimeException);
            assertEquals("Invalid transformer configuration: Incorrect or missing 'correction' nodes", e.getMessage());
        }
    }

    @Test
    public void testConfigWhenCorrectionIsIncorrect() {
        try {
            configTransformer(
                    signavioResource("configuration/credit-decision-with-incorrect-correction.json")
            );
            fail("Should throw exception");
        } catch (Exception e) {
            assertTrue(e instanceof DMNRuntimeException);
            assertEquals("Invalid transformer configuration: Incorrect fields in 'correction' node", e.getMessage());
        }
    }

    @Test
    public void testConfigWhenCorrectionIndexesAreIncorrect() {
        try {
            configTransformer(
                    signavioResource("configuration/credit-decision-with-incorrect-indexes.json")
            );
            fail("Should throw exception");
        } catch (Exception e) {
            assertTrue(e instanceof DMNRuntimeException);
            assertEquals("Invalid transformer configuration: Unexpected comma separated list of indexes '{abc=abc}'", e.getMessage());
        }
    }

    @Test
    public void testConfigWhenOneCorrection() throws IOException {
        CorrectPathsInDecisionsTransformer transformer = configTransformer(
                signavioResource("configuration/credit-decision-with-one-correction.json")
        );
        List<Correction> expectedCorrections = new ArrayList<>();
        expectedCorrections.add(
                new DecisionTableCorrection("processPriorIssues", "priorIssues.priorIssues", "priorIssues", Collections.emptyList(), Collections.emptyList())
        );
        checkCorrections(expectedCorrections, transformer);
    }

    @Test
    public void testConfigWhenCorrect() throws IOException {
        CorrectPathsInDecisionsTransformer transformer = configTransformer(
                signavioResource("configuration/credit-decision-with-corrections.json")
        );
        List<Correction> expectedCorrections = new ArrayList<>();
        expectedCorrections.add(
                new DecisionTableCorrection("processPriorIssues", "priorIssues.priorIssues", "priorIssues", Collections.singletonList(1), Collections.singletonList(5))
        );
        checkCorrections(expectedCorrections, transformer);
    }

    @Test
    public void testTransformationNormalFlow() throws Exception {
        DMNModelRepository repository = executeTransformation(
                signavioResource("input/example-credit-decision-with-incorrect-paths.dmn"),
                signavioResource("configuration/credit-decision-with-corrections.json")
        );

        String decisionName = "processPriorIssues";
        TDRGElement drgElement = repository.findDRGElementByName(decisionName);
        assertTrue(drgElement instanceof TDecision);
        TDecision decision = (TDecision) drgElement;
        TExpression expression = repository.expression(decision);
        assertTrue(expression instanceof TDecisionTable);
        assertEquals("applicant.priorIssues", ((TDecisionTable) expression).getInput().get(0).getInputExpression().getText());
        assertEquals("(count(applicant.priorIssues)*(-5))", ((TDecisionTable) expression).getRule().get(4).getOutputEntry().get(0).getText());
    }

    @Test
    public void testTransformationWhenNoConfiguration() throws Exception {
        DMNModelRepository repository = executeTransformation(
                signavioResource("input/example-credit-decision-with-incorrect-paths.dmn"),
                signavioResource("configuration/credit-decision-with-one-correction.json")
        );

        String decisionName = "processPriorIssues";
        TDRGElement drgElement = repository.findDRGElementByName(decisionName);
        assertTrue(drgElement instanceof TDecision);
        TDecision decision = (TDecision) drgElement;
        TExpression expression = repository.expression(decision);
        assertTrue(expression instanceof TDecisionTable);
        assertEquals("applicant.priorIssues", ((TDecisionTable) expression).getInput().get(0).getInputExpression().getText());
        assertEquals("(count(applicant.priorIssues)*(-5))", ((TDecisionTable) expression).getRule().get(4).getOutputEntry().get(0).getText());
    }

    @Test
    public void testTransformationWhenEmptyRepo() {
        CorrectPathsInDecisionsTransformer transformer = new CorrectPathsInDecisionsTransformer();
        DMNModelRepository repository = null;
        transformer.transform(repository);
        Pair<DMNModelRepository, List<TestLab>> res = transformer.transform(repository, null);
        assertEquals(repository, res.getLeft());
    }

    @Test
    public void testTransformationWhenEmptyConfig() {
        CorrectPathsInDecisionsTransformer transformer = new CorrectPathsInDecisionsTransformer();
        DMNModelRepository repository = new DMNModelRepository();
        transformer.transform(repository);
        Pair<DMNModelRepository, List<TestLab>> res = transformer.transform(repository, null);
        assertEquals(repository, res.getLeft());
    }

    private DMNModelRepository executeTransformation(URI dmnFileURI, URI transformerConfigURI) throws Exception {
        CorrectPathsInDecisionsTransformer transformer = configTransformer(transformerConfigURI);
        File dmnFile = new File(dmnFileURI);
        DMNModelRepository repository = new SignavioDMNModelRepository(dmnReader.read(dmnFile));

        return transformer.transform(repository);
    }

    private CorrectPathsInDecisionsTransformer configTransformer(URI transformerConfigURI) throws IOException {
        CorrectPathsInDecisionsTransformer transformer = new CorrectPathsInDecisionsTransformer(LOGGER);
        Map<String, Object> configuration = null;
        if (transformerConfigURI != null) {
            File configFile = new File(transformerConfigURI);
            configuration = MAPPER.readValue(configFile, Map.class);
        }
        transformer.configure(configuration);
        return transformer;
    }

    private void checkCorrections(List<Correction> expectedCorrections, CorrectPathsInDecisionsTransformer transformer) {
        assertEquals(expectedCorrections, transformer.corrections);
    }

    private URI signavioResource(String relativePath) {
        return resource(String.format("%s/%s", BASE_PATH, relativePath));
    }
}
