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
import com.gs.dmn.ast.*;
import com.gs.dmn.serialization.jackson.JsonDMNSerializer;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonDMNSerializerTest extends AbstractTest {
    private final DMNSerializer dmnSerializer = new JsonDMNSerializer(LOGGER, makeInputParameters());

    @Test
    public void testRead() {
        File input = new File(resource("jackson/v1_3/0004-lending.dmn"));

        List<TDefinitions> definitionsList = this.dmnSerializer.readModels(Arrays.asList(input));
        assertEquals(1, definitionsList.size());
        List<TDRGElement> drgElementList = definitionsList.get(0).getDrgElement();
        assertEquals(24, drgElementList.size());

        TDecision decision = (TDecision) drgElementList.get(0);
        assertNamedElement(decision, "d_BureauCallType", "BureauCallType");
        assertVariable(decision, "BureauCallType", null, "tBureauCallType");
        assertEquals(1, decision.getInformationRequirement().size());
        assertEquals(1, decision.getKnowledgeRequirement().size());

        TExpression expression = decision.getExpression();
        assertTrue(expression instanceof TInvocation);
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
