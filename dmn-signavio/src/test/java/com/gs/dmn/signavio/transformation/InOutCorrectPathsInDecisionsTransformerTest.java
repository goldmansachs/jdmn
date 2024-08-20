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
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.signavio.SignavioDMNModelRepository;
import com.gs.dmn.signavio.testlab.TestLab;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

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
        assertInstanceOf(TDecision.class, firstDecision);
        TExpression firstExpression = repository.expression(firstDecision);
        assertInstanceOf(TDecisionTable.class, firstExpression);
        TDecisionTable firstDecisionTable = (TDecisionTable) firstExpression;
        assertEquals("(count(applicant.priorIssues)*(-5))", firstDecisionTable.getRule().get(4).getOutputEntry().get(0).getText());

        // Check decision with in-out pattern for inputs
        TDRGElement secondDecision = repository.findDRGElementByName("compareAgainstLendingThreshold");
        assertInstanceOf(TDecision.class, secondDecision);
        TExpression secondExpression = repository.expression(secondDecision);
        assertInstanceOf(TDecisionTable.class, secondExpression);
        TDecisionTable secondDecisionTable = (TDecisionTable) secondExpression;
        assertEquals("lendingThreshold.lendingThreshold", secondDecisionTable.getRule().get(1).getOutputEntry().get(0).getText());

        // Check decision with in-out pattern for inputs
        TDRGElement thirdDecision = repository.findDRGElementByName("incorrectDecision");
        assertInstanceOf(TDecision.class, thirdDecision);
        TExpression thirdExpression = repository.expression(thirdDecision);
        assertInstanceOf(TDecisionTable.class, thirdExpression);
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
    public void testTransformationWhenEmptyConfig() {
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
