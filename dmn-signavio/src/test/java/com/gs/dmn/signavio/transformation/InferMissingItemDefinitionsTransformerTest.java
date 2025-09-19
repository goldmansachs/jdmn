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
import com.gs.dmn.ast.TItemDefinition;
import com.gs.dmn.ast.TNamedElement;
import com.gs.dmn.dialect.JavaTimeDMNDialectDefinition;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.signavio.SignavioDMNModelRepository;
import com.gs.dmn.signavio.SignavioTestConstants;
import com.gs.dmn.signavio.dialect.JavaTimeSignavioDMNDialectDefinition;
import com.gs.dmn.signavio.testlab.TestLab;
import com.gs.dmn.transformation.DMNTransformer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.math.BigDecimal;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static com.gs.dmn.signavio.transformation.InferMissingItemDefinitionsTransformer.DMN_DIALECT_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class InferMissingItemDefinitionsTransformerTest extends AbstractSignavioFileTransformerTest {
    @Test
    public void testWhenSignavioDialect() {
        Map<String, Object> config = makeConfiguration(JavaTimeSignavioDMNDialectDefinition.class.getName());
        RepositoryTransformResult transformResult = executeTransformation(
                signavioResource("dmn/complex/credit-decision-missing-some-definitions.dmn"),
                config
        );

        List<Pair<String, String>> expectedNewDefinitions = Arrays.asList(
                new Pair<>("assessApplicantAge", "number, false"),
                new Pair<>("assessIssue", "number, false"),
                new Pair<>("makeCreditDecision", "string, false"),
                new Pair<>("processPriorIssues", "number, true"),
                new Pair<>("assessIssueRisk", "number, false")
        );
        assertExpectedTransformResult(transformResult, expectedNewDefinitions, Collections.emptyList());
    }

    @Test
    public void testWhenConfigurationIsNull() {
        Map<String, Object> config = null;
        RepositoryTransformResult transformResult = executeTransformation(
                signavioResource("dmn/complex/credit-decision-missing-some-definitions.dmn"), config);

        List<Pair<String, String>> expectedNewDefinitions = Arrays.asList(
                new Pair<>("assessApplicantAge", "number, false"),
                new Pair<>("assessIssue", "number, false"),
                new Pair<>("makeCreditDecision", "string, false"),
                new Pair<>("processPriorIssues", "number, true"),
                new Pair<>("assessIssueRisk", "number, false")
        );
        assertExpectedTransformResult(transformResult, expectedNewDefinitions, Collections.emptyList());
    }

    @Test
    public void testWhenConfigurationIsEmpty() {
        Map<String, Object> config = new LinkedHashMap<>();
        RepositoryTransformResult transformResult = executeTransformation(
                signavioResource("dmn/complex/credit-decision-missing-some-definitions.dmn"), config);

        List<Pair<String, String>> expectedNewDefinitions = Arrays.asList(
                new Pair<>("assessApplicantAge", "number, false"),
                new Pair<>("assessIssue", "number, false"),
                new Pair<>("makeCreditDecision", "string, false"),
                new Pair<>("processPriorIssues", "number, true"),
                new Pair<>("assessIssueRisk", "number, false")
        );
        assertExpectedTransformResult(transformResult, expectedNewDefinitions, Collections.emptyList());
    }

    @Test
    public void testWhenConfigurationHasIncorrectStructure() {
        try {
            Map<String, Object> config = new LinkedHashMap<>();
            config.put("a", 10);
            config.put(DMN_DIALECT_NAME, JavaTimeDMNDialectDefinition.class.getName());

            executeTransformation(
                    signavioResource("dmn/complex/credit-decision-missing-some-definitions.dmn"), config);

            fail("Test is expected to fail; incorrect dmnDialect");
        } catch (Exception e) {
            assertEquals("Invalid transformer configuration: Configuration does not have expected structure (expecting only 'dmnDialect' node)", e.getMessage());
        }
    }

    @Test
    public void testWhenConfigurationHasIncorrectType() {
        try {
            Map<String, Object> config = new LinkedHashMap<>();
            config.put(DMN_DIALECT_NAME, 457);

            executeTransformation(
                    signavioResource("dmn/complex/credit-decision-missing-some-definitions.dmn"), config);
            fail("Test is expected to fail; incorrect dmnDialect");
        } catch (Exception e) {
            assertEquals("Invalid transformer configuration: 'dmnDialect' should be a string", e.getMessage());
        }
    }

    @Test
    public void testWhenDialectHasIncorrectType() {
        try {
            Map<String, Object> config = new LinkedHashMap<>();
            config.put(DMN_DIALECT_NAME, BigDecimal.class.getName());

            executeTransformation(
                    signavioResource("dmn/complex/credit-decision-missing-some-definitions.dmn"), config);
            fail("Test is expected to fail; incorrect dmnDialect");
        } catch (Exception e) {
            assertEquals("Invalid transformer configuration: Incorrect DMN dialect name 'java.math.BigDecimal'", e.getMessage());
        }
    }

    private Map<String, Object> makeConfiguration(String dialectClassName) {
        Map<String, Object> config = new LinkedHashMap<>();
        config.put(DMN_DIALECT_NAME, dialectClassName);
        return config;
    }

    private RepositoryTransformResult executeTransformation(URI dmnFileURI, Map<String, Object> configuration) {
        DMNTransformer<TestLab> transformer = new InferMissingItemDefinitionsTransformer(LOGGER);
        transformer.configure(configuration);

        File dmnFile = new File(dmnFileURI);
        DMNModelRepository repository = new SignavioDMNModelRepository(this.dmnSerializer.readModel(dmnFile), SignavioTestConstants.SIG_EXT_NAMESPACE);
        List<TItemDefinition> definitions = new ArrayList<>(repository.findTopLevelItemDefinitions(repository.getRootDefinitions()));
        DMNModelRepository transformed = transformer.transform(repository);

        List<TItemDefinition> transformedDefinitions = new ArrayList<>(transformed.findTopLevelItemDefinitions(transformed.getRootDefinitions()));
        return new RepositoryTransformResult(definitions, transformedDefinitions);
    }

    // Identify the definitions present in comparisonRepository that are not present in baseRepository (name is unique)
    private List<TItemDefinition> identifyNewDefinitions(List<TItemDefinition> baseRepository, List<TItemDefinition> comparisonRepository) {
        List<String> baseRepositoryNames = baseRepository.stream().map(TItemDefinition::getName).collect(Collectors.toList());

        return comparisonRepository.stream()
                .filter(x -> !baseRepositoryNames.contains(x.getName()))
                .collect(Collectors.toList());
    }

    private void assertExpectedTransformResult(RepositoryTransformResult transformResult,
                                               List<Pair<String, String>> expectedNewDefinitions, List<String> expectedRemovedDefinitions) {
        List<TItemDefinition> newDefinitions = identifyNewDefinitions(transformResult.getBeforeTransform(), transformResult.getAfterTransform());
        assertEquals(expectedNewDefinitions.size(), newDefinitions.size(), "Incorrect number of new definitions");
        for (int i=0; i<expectedNewDefinitions.size(); i++) {
            Pair<String, String> expectedPair = expectedNewDefinitions.get(i);
            TItemDefinition actualItemDefinition = newDefinitions.get(i);
            assertEquals(expectedPair.getLeft(), actualItemDefinition.getName());
            assertEquals(expectedPair.getRight(), toType(actualItemDefinition));
        }

        List<TItemDefinition> removedItemDefinitions = identifyNewDefinitions(transformResult.getAfterTransform(), transformResult.getBeforeTransform());
        Set<String> removedItemDefinitionNames = removedItemDefinitions.stream().map(TNamedElement::getName).collect(Collectors.toSet());
        Assertions.assertTrue(removedItemDefinitionNames.containsAll(expectedRemovedDefinitions), "Expected removed definition is still present");
        assertEquals(expectedRemovedDefinitions.size(), removedItemDefinitions.size(), "Incorrect number of removed definitions");
    }

    private String toType(TItemDefinition itemDefinition) {
        return String.format("%s, %s", itemDefinition.getTypeRef(), itemDefinition.isIsCollection());
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
