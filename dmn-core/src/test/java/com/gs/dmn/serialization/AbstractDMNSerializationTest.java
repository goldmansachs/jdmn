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

import com.gs.dmn.ast.TDecision;
import com.gs.dmn.ast.TDecisionRule;
import com.gs.dmn.ast.TDecisionTable;
import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.serialization.xstream.XMLDMNSerializer;
import com.gs.dmn.serialization.xstream.extensions.test.*;
import com.gs.dmn.transformation.AbstractFileTransformerTest;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public abstract class AbstractDMNSerializationTest extends AbstractFileTransformerTest {
    public static Map<String, Class<?>> EXTENSION_MAPPER = new LinkedHashMap<String, Class<?>>() {{
        put(AllowedAnswer.class.getName(), AllowedAnswer.class);
        put(Decision.class.getName(), Decision.class);
        put(Definitions.class.getName(), Definitions.class);
        put(ElementCollection.class.getName(), ElementCollection.class);
        put(ItemDefinition.class.getName(), ItemDefinition.class);
        put(LiteralExpression.class.getName(), LiteralExpression.class);
        put(PerformanceIndicator.class.getName(), PerformanceIndicator.class);
        put(TextAnnotation.class.getName(), TextAnnotation.class);
    }};

    private final DMNSerializer dmnSerializer = makeSerializer();

    protected void doReadTest(String inputPath) {
        File input = new File(resource(inputPath));

        TDefinitions definitions = this.dmnSerializer.readModel(input);
        checkModel(definitions);
    }

    protected void doWriteTest() throws IOException {
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

    protected void doRoundTripTest(String inputPath, String expectedPath) throws Exception {
        doRoundTripTest(inputPath, expectedPath, this.dmnSerializer);
    }

    protected void doRoundTripTest(String inputPath, String expectedPath, DMNSerializer dmnSerializer) throws Exception {
        File inputFile = new File(resource(inputPath));
        TDefinitions definitions = dmnSerializer.readModel(inputFile);
        String extension = dmnSerializer instanceof XMLDMNSerializer ? ".dmn" : ".json";
        String outputFileName = String.format("test-" + inputFile.getName() + extension);
        File outputFile = new File("target", outputFileName);
        dmnSerializer.writeModel(definitions, outputFile);

        File expectedFile = new File(resource(expectedPath));
        compareFile(expectedFile, outputFile);
    }

    protected abstract DMNSerializer makeSerializer();

    protected abstract void checkModel(TDefinitions definitions);
}
