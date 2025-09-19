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
import com.gs.dmn.QualifiedName;
import com.gs.dmn.ast.TItemDefinition;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.signavio.SignavioDMNModelRepository;
import com.gs.dmn.signavio.testlab.TestLab;
import com.gs.dmn.transformation.DMNTransformer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

public class GenerateMissingItemDefinitionsTransformerTest extends AbstractSignavioFileTransformerTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();

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

    @Test
    public void testTransformerForExistingConflictingDefinition() {
        Assertions.assertThrows(DMNRuntimeException.class, () -> {
            executeTransformation(
                    signavioResource("dmn/complex/input/credit-decision-missing-definitions-other.dmn"),
                    signavioResource("dmn/dmn2java/configuration/credit-decision-existing-conflicting-definition-config.json")
            );

            Assertions.fail("Test is expected to fail; attempted to replace existing conflicting definition");
        });
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

    @Test
    public void testTransformerForDuplicateConflictingNewDefinitions() {
        Assertions.assertThrows(DMNRuntimeException.class, () -> {
            executeTransformation(
                    signavioResource("dmn/complex/input/credit-decision-missing-definitions-other.dmn"),
                    signavioResource("dmn/dmn2java/configuration/credit-decision-duplicate-conflicting-definition-config.json")
            );

            Assertions.fail("Test is expected to fail; attempted to insert duplicate, conflicting new definitions");
        });
    }

    @Test
    public void testSupportForAllTypeRefSyntax() throws Exception {
        RepositoryTransformResult transformResult = executeTransformation(
                signavioResource("dmn/complex/credit-decision-missing-definitions-other.dmn"),
                signavioResource("dmn/dmn2java/configuration/credit-decision-dmn11-12-definitions-config.json")
        );

        List<String> expectedNewDefinitions = Arrays.asList("assessIssue", "lendingThreshold", "currentRiskAppetite", "processPriorIssues");

        List<String> newDefinitions = identifyNewDefinitions(transformResult.getBeforeTransform(), transformResult.getAfterTransform());
        Assertions.assertEquals(expectedNewDefinitions.size(), newDefinitions.size(), "Unexpected new definition count");

        for (String definitionName : newDefinitions) {
            Assertions.assertTrue(expectedNewDefinitions.contains(definitionName), "Unexpected new definition");
            TItemDefinition definition = transformResult.getAfterTransform()
                    .stream().filter(x -> x.getName().equals(definitionName)).findFirst()
                    .orElseThrow(() -> new DMNRuntimeException("Cannot locate new definition"));

            Assertions.assertEquals("number", QualifiedName.toName(definition.getTypeRef()), "Definition type reference is not correct");
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
        DMNModelRepository repository = new SignavioDMNModelRepository(this.dmnSerializer.readModel(dmnFile));
        List<TItemDefinition> definitions = new ArrayList<>(repository.findTopLevelItemDefinitions(repository.getRootDefinitions()));
        DMNModelRepository transformed = transformer.transform(repository);

        List<TItemDefinition> transformedDefinitions = new ArrayList<>(transformed.findTopLevelItemDefinitions(transformed.getRootDefinitions()));
        return new RepositoryTransformResult(definitions, transformedDefinitions);
    }

    // Identify the name of any definitions present in comparisonRepository that are not present in baseRepository
    private List<String> identifyNewDefinitions(List<TItemDefinition> baseRepository, List<TItemDefinition> comparisonRepository) {
        List<String> baseDefinitions = baseRepository.stream().map(TItemDefinition::getName).toList();

        return comparisonRepository.stream()
                .map(TItemDefinition::getName)
                .filter(x -> !baseDefinitions.contains(x))
                .collect(Collectors.toList());
    }

    private void assertExpectedTransformResult(RepositoryTransformResult transformResult,
                                               List<String> expectedNewDefinitions, List<String> expectedRemovedDefinitions) {
        List<String> newDefinitions = identifyNewDefinitions(transformResult.getBeforeTransform(), transformResult.getAfterTransform());
        Assertions.assertTrue(newDefinitions.containsAll(expectedNewDefinitions), "Missing expected new definition");
        Assertions.assertEquals(expectedNewDefinitions.size(), newDefinitions.size(), "Incorrect number of new definitions");

        List<String> removedDefinitions = identifyNewDefinitions(transformResult.getAfterTransform(), transformResult.getBeforeTransform());
        Assertions.assertTrue(removedDefinitions.containsAll(expectedRemovedDefinitions), "Expected removed definition is still present");
        Assertions.assertEquals(expectedRemovedDefinitions.size(), removedDefinitions.size(), "Incorrect number of removed definitions");
    }

    private static class RepositoryTransformResult {
        private final List<TItemDefinition> beforeTransform;
        private final List<TItemDefinition> afterTransform;

        public RepositoryTransformResult(List<TItemDefinition> beforeTransform, List<TItemDefinition> afterTransform) {
            this.beforeTransform = beforeTransform;
            this.afterTransform = afterTransform;
        }

        public List<TItemDefinition> getBeforeTransform() {
            return this.beforeTransform;
        }

        public List<TItemDefinition> getAfterTransform() {
            return this.afterTransform;
        }
    }
}
