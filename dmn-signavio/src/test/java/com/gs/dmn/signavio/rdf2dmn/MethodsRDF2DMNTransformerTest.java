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
package com.gs.dmn.signavio.rdf2dmn;

import com.gs.dmn.AbstractTest;
import com.gs.dmn.transformation.InputParameters;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class MethodsRDF2DMNTransformerTest extends AbstractTest {
    private final RDFToDMNTransformer transformer = new RDFToDMNTransformer(new InputParameters(makeInputParametersMap()), null);

    @Test
    public void testDecisionNames() {
        String originalDecisionName = "Name other ? test";

        String itemDefinitionName = transformer.makeItemDefinitionName(originalDecisionName);
        String decisionName = transformer.makeDecisionName(originalDecisionName);
        String decisionVariableName = transformer.makeDecisionVariableName(originalDecisionName);

        assertEquals("nameOtherTest", itemDefinitionName);
        assertEquals("nameOtherTest", decisionName);
        assertEquals("nameOtherTest", decisionVariableName);
    }

    @Override
    protected Map<String, String> makeInputParametersMap() {
        Map<String, String> inputParams = super.makeInputParametersMap();
        inputParams.put("namespace", "http://www.gs.com/cip");
        inputParams.put("prefix", "cip");
        return inputParams;
    }
}
