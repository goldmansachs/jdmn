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
package com.gs.dmn.serialization.jackson;

import com.gs.dmn.ast.*;
import com.gs.dmn.serialization.AbstractDMNSerializationTest;
import com.gs.dmn.serialization.DMNSerializer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonDMNSerializerTest extends AbstractDMNSerializationTest {

    @Test
    public void testRead() {
        doReadTest("jackson/v1_3/0004-lending.json");
    }

    @Test
    public void testWrite() throws IOException {
        doWriteTest();
    }

    @Test
    public void testRoundTripNoExtension() throws Exception {
        String inputPath = "jackson/v1_3/0004-lending.json";
        String expectedPath = "dmn/expected/1.1/1.4/0004-lending.json";

        doRoundTripTest(inputPath, expectedPath);
    }

    @Test
    public void testRoundTripWithExtensions() throws Exception {
        String inputPath = "jackson/v1_4/test20161014.json";
        String expectedPath = "dmn/expected/1.1/1.4/test20161014.json";

        doRoundTripTest(inputPath, expectedPath);
    }

    @Override
    protected DMNSerializer makeSerializer() {
        return new JsonDMNSerializer(LOGGER, EXTENSION_MAPPER, this.inputParameters);
    }

    @Override
    protected void checkModel(TDefinitions definitions) {
        List<TDRGElement> drgElementList = definitions.getDrgElement();
        assertEquals(24, drgElementList.size());

        TDecision decision = (TDecision) drgElementList.get(0);
        assertNamedElement(decision, "d_BureauCallType", "BureauCallType");
        assertVariable(decision, "BureauCallType", null, "tBureauCallType");
        assertEquals(1, decision.getInformationRequirement().size());
        assertEquals(1, decision.getKnowledgeRequirement().size());

        TExpression expression = decision.getExpression();
        assertInstanceOf(TInvocation.class, expression);
        TInvocation invocation = (TInvocation) expression;
        assertNotNull(invocation.getExpression(), "Missing expression");
    }

    private void assertNamedElement(TNamedElement decision, String id, String name) {
        assertEquals(id, decision.getId(), "Incorrect ID");
        assertEquals(name, decision.getName(), "Incorrect name");
    }

    private void assertVariable(TDecision decision, String id, String name, String typeRef) {
        TInformationItem variable = decision.getVariable();
        assertEquals(id, variable.getName(), "Incorrect name");
        assertEquals(name, variable.getLabel(), "Incorrect label");
        assertEquals(typeRef, variable.getTypeRef().getLocalPart(), "Incorrect typeRef");
    }
}
