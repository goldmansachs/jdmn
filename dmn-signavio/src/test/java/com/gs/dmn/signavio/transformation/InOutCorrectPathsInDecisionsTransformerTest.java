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
import com.gs.dmn.ast.TDRGElement;
import com.gs.dmn.ast.TDecision;
import com.gs.dmn.ast.TDecisionTable;
import com.gs.dmn.ast.TExpression;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.signavio.SignavioDMNModelRepository;
import com.gs.dmn.signavio.testlab.TestLab;
import com.gs.dmn.signavio.transformation.config.Correction;
import com.gs.dmn.signavio.transformation.config.DecisionTableCorrection;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class InOutCorrectPathsInDecisionsTransformerTest extends AbstractSignavioFileTransformerTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final InOutCorrectPathsInDecisionsTransformer transformer = new InOutCorrectPathsInDecisionsTransformer();

    @Test
    public void testTransformationNormalFlow() throws Exception {
        DMNModelRepository repository = executeTransformation(
                signavioResource("dmn/complex/example-in-out-credit-decision-with-incorrect-paths.dmn")
        );

        // Check decision with no in-out pattern
        TDRGElement firstDecision = repository.findDRGElementByName("processPriorIssues");
        assertTrue(firstDecision instanceof TDecision);
        TExpression firstExpression = repository.expression(firstDecision);
        assertTrue(firstExpression instanceof TDecisionTable);
        TDecisionTable firstDecisionTable = (TDecisionTable) firstExpression;
        assertEquals("(count(applicant.priorIssues)*(-5))", firstDecisionTable.getRule().get(4).getOutputEntry().get(0).getText());

        // Check decision with in-out pattern for inputs
        TDRGElement secondDecision = repository.findDRGElementByName("compareAgainstLendingThreshold");
        assertTrue(secondDecision instanceof TDecision);
        TExpression secondExpression = repository.expression(secondDecision);
        assertTrue(secondExpression instanceof TDecisionTable);
        TDecisionTable secondDecisionTable = (TDecisionTable) secondExpression;
        assertEquals("lendingThreshold.lendingThreshold", secondDecisionTable.getRule().get(1).getOutputEntry().get(0).getText());

        // Check decision with in-out pattern for inputs
        TDRGElement thirdDecision = repository.findDRGElementByName("incorrectDecision");
        assertTrue(thirdDecision instanceof TDecision);
        TExpression thirdExpression = repository.expression(thirdDecision);
        assertTrue(thirdExpression instanceof TDecisionTable);
        TDecisionTable thirdDecisionTable = (TDecisionTable) thirdExpression;
        assertEquals("assessIssueRisk", thirdDecisionTable.getRule().get(1).getOutputEntry().get(0).getText());
    }

    @Test
    public void testTransformationWhenEmptyRepo() {
        DMNModelRepository repository = null;
        transformer.transform(repository);
        Pair<DMNModelRepository, List<TestLab>> res = transformer.transform(repository, null);
        assertEquals(repository, res.getLeft());
    }

    @Test
    public void testTransformationWhenEmptyConfig() {;
        DMNModelRepository repository = new DMNModelRepository();
        transformer.transform(repository);
        Pair<DMNModelRepository, List<TestLab>> res = transformer.transform(repository, null);
        assertEquals(repository, res.getLeft());
    }

    private DMNModelRepository executeTransformation(URI dmnFileURI) throws Exception {
        File dmnFile = new File(dmnFileURI);
        DMNModelRepository repository = new SignavioDMNModelRepository(this.dmnSerializer.readModel(dmnFile));

        return transformer.transform(repository);
    }
}

