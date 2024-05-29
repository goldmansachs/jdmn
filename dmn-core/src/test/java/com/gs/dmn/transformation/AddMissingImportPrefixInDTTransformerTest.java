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
package com.gs.dmn.transformation;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.ast.*;
import com.gs.dmn.runtime.Pair;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddMissingImportPrefixInDTTransformerTest {
    private final AddMissingImportPrefixInDTTransformer transformer = new AddMissingImportPrefixInDTTransformer();
    private final Set<String> names = new LinkedHashSet<>(Arrays.asList("ident1", "ident2"));

    @Test
    public void testModel() {
        String literalExpressionText = "person.age";
        String unaryTestsText = "> person.age";
        String annotationText = "\"rule triggered\" + person.age";

        ObjectFactory objectFactory = new ObjectFactory();
        TDefinitions definitions = objectFactory.createTDefinitions();

        // Create input
        TInputData inputData = objectFactory.createTInputData();
        inputData.setName("person");
        inputData.setId("person");
        definitions.getDrgElement().add(inputData);

        // Create child decision
        TDecision child = objectFactory.createTDecision();
        child.setName("child");
        child.setId("child");
        definitions.getDrgElement().add(child);

        // Create decision
        TDecision decision = objectFactory.createTDecision();
        decision.setName("decision");
        decision.setId("decision");
        // Create IRs
        TInformationRequirement inputDataRequirement = objectFactory.createTInformationRequirement();
        TDMNElementReference inputDataReference = objectFactory.createTDMNElementReference();
        inputDataReference.setHref("#person");
        inputDataRequirement.setRequiredInput(inputDataReference);
        decision.getInformationRequirement().add(inputDataRequirement);
        TInformationRequirement childRequirement = objectFactory.createTInformationRequirement();
        TDMNElementReference decisionReference = objectFactory.createTDMNElementReference();
        decisionReference.setHref("#child");
        childRequirement.setRequiredDecision(decisionReference);
        decision.getInformationRequirement().add(childRequirement);
        // Create decision table
        TDecisionTable decisionTable = objectFactory.createTDecisionTable();
        // Create input clause
        TInputClause inputClause = objectFactory.createTInputClause();
        TLiteralExpression inputExpression = objectFactory.createTLiteralExpression();
        inputExpression.setText(literalExpressionText);
        inputClause.setInputExpression(inputExpression);
        decisionTable.getInput().add(inputClause);
        // Create output clause
        TOutputClause outputClause = objectFactory.createTOutputClause();
        TUnaryTests outputClauseTest = objectFactory.createTUnaryTests();
        outputClauseTest.setText(unaryTestsText);
        outputClause.setOutputValues(outputClauseTest);
        decisionTable.getOutput().add(outputClause);
        // Create rule
        TDecisionRule rule = objectFactory.createTDecisionRule();
        TUnaryTests inputEntryTests = objectFactory.createTUnaryTests();
        rule.getInputEntry().add(inputEntryTests);
        TLiteralExpression ruleOutputExpression = objectFactory.createTLiteralExpression();
        ruleOutputExpression.setText(literalExpressionText);
        rule.getOutputEntry().add(ruleOutputExpression);
        TRuleAnnotation ruleAnnotation = objectFactory.createTRuleAnnotation();
        ruleAnnotation.setText(annotationText);
        rule.getAnnotationEntry().add(ruleAnnotation);
        decisionTable.getRule().add(rule);
        decision.setExpression(decisionTable);
        definitions.getDrgElement().add(decision);

        transformer.transform(new DMNModelRepository(definitions));

        // Check replacements in
        assertEquals("person.person . age", inputClause.getInputExpression().getText());
        assertEquals("> person.person . age", outputClause.getOutputValues().getText());
        assertEquals("person.person . age", rule.getOutputEntry().get(0).getText());
        assertEquals("\"rule triggered\" + person.person . age", rule.getAnnotationEntry().get(0).getText());
    }

    @Test
    public void testAddMissingPrefix() {
        List<Pair<String, String>> testCases = Arrays.asList(
                // Empty
                new Pair<>(null, null),
                new Pair<>("", ""),
                // String literal
                new Pair<>("\"ident1\"", "\"ident1\""),
                new Pair<>("ident1+ident2", "ident1.ident1 + ident2.ident2"),
                // Unary tests
                new Pair<>("> ident1+ident2", "> ident1.ident1 + ident2.ident2"),
                new Pair<>("starts with(?, ident1)", "starts with ( ? , ident1.ident1 )"),
                // Function name same as argument
                new Pair<>("ident1(?, ident1)", "ident1 ( ? , ident1.ident1 )"),
                // Member name same as source
                new Pair<>("ident1.ident1+4", "ident1.ident1 . ident1 + 4")
        );
        for (Pair<String, String> pair: testCases) {
            String text = pair.getLeft();
            String newText = transformer.addMissingPrefix(text, names);
            assertEquals(pair.getRight(), newText);
        }
    }
}