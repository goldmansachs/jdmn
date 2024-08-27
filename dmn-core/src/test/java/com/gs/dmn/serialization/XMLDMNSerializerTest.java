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
package com.gs.dmn.serialization;

import com.gs.dmn.AbstractTest;
import com.gs.dmn.QualifiedName;
import com.gs.dmn.ast.*;
import com.gs.dmn.serialization.xstream.XMLDMNSerializer;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class XMLDMNSerializerTest extends AbstractTest {
    private final DMNSerializer dmnSerializer = new XMLDMNSerializer(LOGGER, this.inputParameters);

    @Test
    public void testRead() {
        File input = new File(resource("dmn/input/1.1/test-dmn.dmn"));

        TDefinitions definitions = this.dmnSerializer.readModel(input);
        checkModel(definitions);
    }

    @Test
    public void testWrite() throws IOException {
        // Test partial objects
        File outputFile = File.createTempFile("jdmn-", "-dmn");
        outputFile.deleteOnExit();

        assertDoesNotThrow(() -> this.dmnSerializer.writeModel(null, outputFile));

        assertDoesNotThrow(() -> {
            TDefinitions definitions = new TDefinitions();
            definitions.getElementInfo().getNsContext().putAll(DMNVersion.LATEST.getPrefixToNamespaceMap());
            this.dmnSerializer.writeModel(definitions, outputFile);
        });

        assertDoesNotThrow(() -> {
            TDefinitions definitions = new TDefinitions();
            definitions.getElementInfo().getNsContext().putAll(DMNVersion.LATEST.getPrefixToNamespaceMap());
            TDecision decision = new TDecision();
            TDecisionTable value = new TDecisionTable();
            TDecisionRule rule = null;
            value.getRule().add(rule);
            decision.setExpression(value);
            definitions.getDrgElement().add(decision);
            this.dmnSerializer.writeModel(definitions, outputFile);
        });
    }

    @Test
    public void testRoundTrip() {
        File input = new File(resource("dmn/input/1.1/test-dmn.dmn"));

        TDefinitions definitions = this.dmnSerializer.readModel(input);
        File outputFile = new File("target", "test-dmn.dmn");
        this.dmnSerializer.writeModel(definitions, outputFile);

        definitions = this.dmnSerializer.readModel(outputFile);

        checkModel(definitions);
    }

    private void checkModel(TDefinitions definitions) {
        List<TDRGElement> drgElementList = definitions.getDrgElement();
        assertEquals(1, drgElementList.size());

        TDRGElement decision = drgElementList.get(0);
        assertNamedElement(decision, "cip-assessments", "CIP Assessments");

        TExpression expression = ((TDecision) decision).getExpression();
        assertInstanceOf(TDecisionTable.class, expression);
        TDecisionTable table = (TDecisionTable) expression;
        assertEquals("decisionTable", table.getId());

        List<TInputClause> inputList = table.getInput();
        assertEquals(5, inputList.size());

        TInputClause firstClause = inputList.get(0);
        assertDMNElement(firstClause, "input1", "Gender");
        TLiteralExpression inputExpression = firstClause.getInputExpression();
        String stringType = "string";
        String id = "inputExpression1";
        String text = "gender";
        assertLiteralExpression(inputExpression, stringType, id, text);

        List<TOutputClause> outputList = table.getOutput();
        assertEquals(1, outputList.size());
        TOutputClause output = outputList.get(0);
        assertEquals(stringType, QualifiedName.toName(output.getTypeRef()));
        assertEquals("output1", output.getId());
        assertEquals("", output.getName());

        List<TDecisionRule> ruleList = table.getRule();
        assertEquals(2, ruleList.size());

        TDecisionRule firstRule = ruleList.get(0);
        assertEquals("row-168857152-1", firstRule.getId());
        assertNull(firstRule.getLabel());
        List<TUnaryTests> inputEntryList = firstRule.getInputEntry();
        assertEquals(5, inputEntryList.size());
        TUnaryTests firstUnaryTest = inputEntryList.get(0);
        assertEquals("= \"Female\"", firstUnaryTest.getText());
        TUnaryTests secondUnaryTest = inputEntryList.get(1);
        assertEquals("\"£ [ ] & < $  \\u20ac\"", secondUnaryTest.getText());
    }

    private void assertLiteralExpression(TLiteralExpression inputExpression, String stringType, String id, String text) {
        assertEquals(id, inputExpression.getId());
        assertEquals(stringType, QualifiedName.toName(inputExpression.getTypeRef()));
        assertEquals(text, inputExpression.getText());
    }

    private void assertNamedElement(TNamedElement decision, String id, String name) {
        assertEquals(id, decision.getId());
        assertEquals(name, decision.getName());
    }

    private void assertDMNElement(TDMNElement decision, String id, String label) {
        assertEquals(id, decision.getId());
        assertEquals(label, decision.getLabel());
    }
}
