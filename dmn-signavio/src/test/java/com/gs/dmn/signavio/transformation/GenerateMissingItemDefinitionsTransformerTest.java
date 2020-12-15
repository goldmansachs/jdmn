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
import com.gs.dmn.serialization.DMNReader;
import com.gs.dmn.signavio.SignavioDMNModelRepository;
import com.gs.dmn.signavio.testlab.TestLab;
import com.gs.dmn.transformation.AbstractFileTransformerTest;
import com.gs.dmn.transformation.DMNTransformer;
import org.junit.Assert;
import org.junit.Test;
import org.omg.spec.dmn._20191111.model.TItemDefinition;

import java.io.File;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

public class GenerateMissingItemDefinitionsTransformerTest extends AbstractFileTransformerTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final DMNReader dmnReader = new DMNReader(LOGGER, false);

    @Test
    public void testMissingDefinitionsWithoutTransformation() throws Exception {
        RepositoryTransformResult transformResult = executeTransformation(
                signavioResource("dmn/complex/credit-decision-missing-definitions-other.dmn"), null);

        // Repository should not be modified
        assertExpectedTransformResult(transformResult, Collections.emptyList(), Collections.emptyList());
    }

    @Test
    public void testTransformerForMissingDefinitions() throws Exception {
        RepositoryTransformResult transformResult = executeTransformation(
                signavioResource("dmn/complex/credit-decision-missing-definitions-other.dmn"),
                signavioResource("dmn/dmn2java/configuration/credit-decision-missing-definitions-config.json")
        );

        // Post-transform repository should include all missing definitions
        assertExpectedTransformResult(transformResult, Arrays.asList("assessIssue", "lendingThreshold", "currentRiskAppetite", "processPriorIssues",
                "creditIssueType", "assessApplicantAge", "priorIssue", "makeCreditDecision", "assessIssueRisk"), Collections.emptyList());
    }

    @Test
    public void testTransformerForExistingEquivalentDefinition() throws Exception {
        RepositoryTransformResult transformResult = executeTransformation(
                signavioResource("dmn/complex/credit-decision-missing-definitions-other.dmn"),
                signavioResource("dmn/dmn2java/configuration/credit-decision-existing-definition-config.json")
        );

        // Should transform correctly, and disregard definition that already exists since it is equivalent
        assertExpectedTransformResult(transformResult, Arrays.asList("assessIssue", "lendingThreshold", "currentRiskAppetite", "processPriorIssues",
                "creditIssueType", "assessApplicantAge", "priorIssue", "makeCreditDecision", "assessIssueRisk"), Collections.emptyList());
    }

    @Test(expected = DMNRuntimeException.class)
    public void testTransformerForExistingConflictingDefinition() throws Exception {
        executeTransformation(
                signavioResource("dmn/complex/input/credit-decision-missing-definitions-other.dmn"),
                signavioResource("dmn/dmn2java/configuration/credit-decision-existing-conflicting-definition-config.json")
        );

        Assert.fail("Test is expected to fail; attempted to replace existing conflicting definition");
    }

    @Test
    public void testTransformerForDuplicateEquivalentNewDefinitions() throws Exception {
        RepositoryTransformResult transformResult = executeTransformation(
                signavioResource("dmn/complex/credit-decision-missing-definitions-other.dmn"),
                signavioResource("dmn/dmn2java/configuration/credit-decision-duplicate-definition-config.json")
        );

        // Should transform correctly, and disregard the duplicate definition config since it is equivalent
        assertExpectedTransformResult(transformResult, Arrays.asList("assessIssue", "lendingThreshold",
                "currentRiskAppetite", "processPriorIssues"), Collections.emptyList());
    }

    @Test(expected = DMNRuntimeException.class)
    public void testTransformerForDuplicateConflictingNewDefinitions() throws Exception {
        executeTransformation(
                signavioResource("dmn/complex/input/credit-decision-missing-definitions-other.dmn"),
                signavioResource("dmn/dmn2java/configuration/credit-decision-duplicate-conflicting-definition-config.json")
        );

        Assert.fail("Test is expected to fail; attempted to insert duplicate, conflicting new definitions");
    }

    @Test
    public void testSupportForAllTypeRefSyntax() throws Exception {
        RepositoryTransformResult transformResult = executeTransformation(
                signavioResource("dmn/complex/credit-decision-missing-definitions-other.dmn"),
                signavioResource("dmn/dmn2java/configuration/credit-decision-dmn11-12-definitions-config.json")
        );

        List<String> expectedNewDefinitions = Arrays.asList("assessIssue", "lendingThreshold", "currentRiskAppetite", "processPriorIssues");

        List<String> newDefinitions = identifyNewDefinitions(transformResult.getBeforeTransform(), transformResult.getAfterTransform());
        Assert.assertEquals("Unexpected new definition count", expectedNewDefinitions.size(), newDefinitions.size());

        for (String definitionName : newDefinitions) {
            Assert.assertTrue("Unexpected new definition", expectedNewDefinitions.contains(definitionName));
            TItemDefinition definition = transformResult.getAfterTransform()
                    .stream().filter(x -> x.getName().equals(definitionName)).findFirst()
                    .orElseThrow(() -> new DMNRuntimeException("Cannot locate new definition"));

            Assert.assertEquals("Definition type reference is not correct", "feel.number", definition.getTypeRef());
        }
    }


    @SuppressWarnings("unchecked")
    private RepositoryTransformResult executeTransformation(URI dmnFileURI, URI transformerConfigURI) throws Exception {
        DMNTransformer<TestLab> transformer = new GenerateMissingItemDefinitionsTransformer(LOGGER);
        if (transformerConfigURI != null) {
            File configFile = new File(transformerConfigURI);
            Map<String, Object> configuration = MAPPER.readValue(configFile, Map.class);

            transformer.configure(configuration);
        }

        File dmnFile = new File(dmnFileURI);
        DMNModelRepository repository = new SignavioDMNModelRepository(dmnReader.read(dmnFile));
        List<TItemDefinition> definitions = new ArrayList<>(repository.findItemDefinitions(repository.getRootDefinitions()));
        DMNModelRepository transformed = transformer.transform(repository);

        List<TItemDefinition> transformedDefinitions = new ArrayList<>(transformed.findItemDefinitions(transformed.getRootDefinitions()));
        return new RepositoryTransformResult(definitions, transformedDefinitions);
    }

    // Identify the name of any definitions present in comparisonRepository that are not present in baseRepository
    private List<String> identifyNewDefinitions(List<TItemDefinition> baseRepository, List<TItemDefinition> comparisonRepository) {
        List<String> baseDefinitions = baseRepository.stream().map(TItemDefinition::getName).collect(Collectors.toList());

        return comparisonRepository.stream()
                .map(TItemDefinition::getName)
                .filter(x -> !baseDefinitions.contains(x))
                .collect(Collectors.toList());
    }

    private void assertExpectedTransformResult(RepositoryTransformResult transformResult,
                                               List<String> expectedNewDefinitions, List<String> expectedRemovedDefinitions) {
        List<String> newDefinitions = identifyNewDefinitions(transformResult.getBeforeTransform(), transformResult.getAfterTransform());
        Assert.assertTrue("Missing expected new definition", newDefinitions.containsAll(expectedNewDefinitions));
        Assert.assertEquals("Incorrect number of new definitions", expectedNewDefinitions.size(), newDefinitions.size());

        List<String> removedDefinitions = identifyNewDefinitions(transformResult.getAfterTransform(), transformResult.getBeforeTransform());
        Assert.assertTrue("Expected removed definition is still present", removedDefinitions.containsAll(expectedRemovedDefinitions));
        Assert.assertEquals("Incorrect number of removed definitions", expectedRemovedDefinitions.size(), removedDefinitions.size());
    }

    private static class RepositoryTransformResult {
        private final List<TItemDefinition> beforeTransform;
        private final List<TItemDefinition> afterTransform;

        public RepositoryTransformResult(List<TItemDefinition> beforeTransform, List<TItemDefinition> afterTransform) {
            this.beforeTransform = beforeTransform;
            this.afterTransform = afterTransform;
        }

        public List<TItemDefinition> getBeforeTransform() {
            return beforeTransform;
        }

        public List<TItemDefinition> getAfterTransform() {
            return afterTransform;
        }
    }
}
