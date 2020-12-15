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
import com.gs.dmn.dialect.StandardDMNDialectDefinition;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.serialization.DMNReader;
import com.gs.dmn.signavio.SignavioDMNModelRepository;
import com.gs.dmn.signavio.dialect.SignavioDMNDialectDefinition;
import com.gs.dmn.signavio.testlab.TestLab;
import com.gs.dmn.transformation.AbstractFileTransformerTest;
import com.gs.dmn.transformation.DMNTransformer;
import org.junit.Assert;
import org.junit.Test;
import org.omg.spec.dmn._20191111.model.TItemDefinition;

import java.io.File;
import java.math.BigDecimal;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static com.gs.dmn.signavio.transformation.InferMissingItemDefinitionsTransformer.DMN_DIALECT_NAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class InferMissingItemDefinitionsTransformerTest extends AbstractFileTransformerTest {

    private final DMNReader dmnReader = new DMNReader(LOGGER, false);

    @Test
    public void testWhenSignavioDialect() throws Exception {
        Map<String, Object> config = makeConfiguration(SignavioDMNDialectDefinition.class.getName());
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
    public void testWhenConfigurationIsNull() throws Exception {
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
    public void testWhenConfigurationIsEmpty() throws Exception {
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
            config.put(DMN_DIALECT_NAME, StandardDMNDialectDefinition.class.getName());

            RepositoryTransformResult transformResult = executeTransformation(
                    signavioResource("dmn/complex/credit-decision-missing-some-definitions.dmn"), config);

            fail("Test is expected to fail; incorrect dmnDialect");
        } catch (Exception e) {
            assertEquals("Invalid transformer configuration: Configuration does not have expected structure (expecting only 'dmnDialect' node)", e.getMessage());
        }
    }

    @Test
    public void testWhenConfigurationHasIncorrectType() throws Exception {
        try {
            Map<String, Object> config = new LinkedHashMap<>();
            config.put(DMN_DIALECT_NAME, 457);

            RepositoryTransformResult transformResult = executeTransformation(
                    signavioResource("dmn/complex/credit-decision-missing-some-definitions.dmn"), config);
            fail("Test is expected to fail; incorrect dmnDialect");
        } catch (Exception e) {
            assertEquals("Invalid transformer configuration: 'dmnDialect' should be a string", e.getMessage());
        }
    }

    @Test
    public void testWhenDialectHasIncorrectType() throws Exception {
        try {
            Map<String, Object> config = new LinkedHashMap<>();
            config.put(DMN_DIALECT_NAME, BigDecimal.class.getName());

            RepositoryTransformResult transformResult = executeTransformation(
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

    @SuppressWarnings("unchecked")
    private RepositoryTransformResult executeTransformation(URI dmnFileURI, Map<String, Object> configuration) throws Exception {
        DMNTransformer<TestLab> transformer = new InferMissingItemDefinitionsTransformer(LOGGER);
        transformer.configure(configuration);

        File dmnFile = new File(dmnFileURI);
        DMNModelRepository repository = new SignavioDMNModelRepository(dmnReader.read(dmnFile), "http://www.provider.com/schema/dmn/1.1/");
        List<TItemDefinition> definitions = new ArrayList<>(repository.findItemDefinitions(repository.getRootDefinitions()));
        DMNModelRepository transformed = transformer.transform(repository);

        List<TItemDefinition> transformedDefinitions = new ArrayList<>(transformed.findItemDefinitions(transformed.getRootDefinitions()));
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
        assertEquals("Incorrect number of new definitions", expectedNewDefinitions.size(), newDefinitions.size());
        for (int i=0; i<expectedNewDefinitions.size(); i++) {
            Pair<String, String> expectedPair = expectedNewDefinitions.get(i);
            TItemDefinition actualItemDefinition = newDefinitions.get(i);
            assertEquals(expectedPair.getLeft(), actualItemDefinition.getName());
            assertEquals(expectedPair.getRight(), toType(actualItemDefinition));
        }

        List<TItemDefinition> removedDefinitions = identifyNewDefinitions(transformResult.getAfterTransform(), transformResult.getBeforeTransform());
        Assert.assertTrue("Expected removed definition is still present", removedDefinitions.containsAll(expectedRemovedDefinitions));
        assertEquals("Incorrect number of removed definitions", expectedRemovedDefinitions.size(), removedDefinitions.size());
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
            return beforeTransform;
        }

        public List<TItemDefinition> getAfterTransform() {
            return afterTransform;
        }
    }
}
