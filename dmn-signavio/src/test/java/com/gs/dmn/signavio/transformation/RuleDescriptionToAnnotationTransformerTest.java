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

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.ast.TDecisionRule;
import com.gs.dmn.ast.TDecisionTable;
import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.error.NopErrorHandler;
import com.gs.dmn.log.NopBuildLogger;
import com.gs.dmn.signavio.testlab.TestLab;
import com.gs.dmn.transformation.DMNTransformer;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RuleDescriptionToAnnotationTransformerTest extends AbstractSignavioDMNTransformerTest {
    private final RuleDescriptionToAnnotationTransformer transformer = new RuleDescriptionToAnnotationTransformer(LOGGER);
    private final RuleDescriptionToAnnotationVisitor visitor = new RuleDescriptionToAnnotationVisitor(new NopBuildLogger(), new NopErrorHandler());

    @Test
    public void testTransformWithOneAnnotation() throws Exception {
        String path = "dmn/input/1.4/";

        // Transform DMN
        URI dmnFileURI = resource(path + "simpleMID.dmn");
        DMNModelRepository actualRepository = executeDMNTransformation(transformer, dmnFileURI);

        // Check output
        checkDefinitions(actualRepository, "simpleMID-with-annotations.dmn");
    }

    @Test
    public void testTransformWithMultipleAnnotations() throws Exception {
        String path = "dmn/input/1.1/";

        // Transform DMN
        URI dmnFileURI = resource(path + "dmn-link-issue-1.dmn");
        DMNModelRepository actualRepository = executeDMNTransformation(transformer, dmnFileURI);

        // Check output
        checkDefinitions(actualRepository, "dmn-link-issue-1.dmn");
    }

    @Test
    public void testVisitor() {
        List<String> descriptions = Arrays.asList("string(-)", null, "abc");
        List<String> annotations = Arrays.asList("\"\"", null, "abc");

        visitAndCheckDT(descriptions, annotations);
    }

    private void visitAndCheckDT(List<String> descriptions, List<String> annotations) {
        // Initialize DT
        TDecisionTable decisionTable = makeDecisionTable(descriptions);

        // Transform
        decisionTable = (TDecisionTable) visitor.visit(decisionTable, null);

        // Check
        assertEquals(1, decisionTable.getAnnotation().size());
        assertEquals("Annotation", decisionTable.getAnnotation().get(0).getName());
        for (int i=0; i<decisionTable.getRule().size(); i++) {
            TDecisionRule rule = decisionTable.getRule().get(i);
            assertNull(rule.getDescription());
            String expectedAnnotation = annotations.get(i);
            if (expectedAnnotation != null) {
                assertEquals(expectedAnnotation, rule.getAnnotationEntry().get(0).getText());
            }
        }
    }

    private void checkDefinitions(DMNModelRepository repository, String fileName) throws Exception {
        File actualDMNFile = new File("target/" + fileName);
        TDefinitions actualDefinitions = getDefinitions(repository);
        this.dmnSerializer.writeModel(actualDefinitions, actualDMNFile);

        String path = "dmn/expected/";
        File expectedDMNFile = new File(resource(path + fileName));

        compareFile(expectedDMNFile, actualDMNFile);
    }

    @Override
    protected DMNTransformer<TestLab> getTransformer() {
        return transformer;
    }

    private TDecisionTable makeDecisionTable(List<String> descriptions) {
        TDecisionTable decisionTable = new TDecisionTable();

        for(String d: descriptions) {
            decisionTable.getRule().add(makeRule(d));
        }
        return decisionTable;
    }

    private TDecisionRule makeRule(String description) {
        TDecisionRule rule1 = new TDecisionRule();
        rule1.setDescription(description);
        return rule1;
    }
}