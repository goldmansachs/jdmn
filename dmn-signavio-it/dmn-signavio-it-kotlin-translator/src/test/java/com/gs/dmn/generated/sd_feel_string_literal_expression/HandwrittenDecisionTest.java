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
package com.gs.dmn.generated.sd_feel_string_literal_expression;

import com.gs.dmn.generated.AbstractHandwrittenDecisionTest;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class HandwrittenDecisionTest extends AbstractHandwrittenDecisionTest {
    private final Decision decision = new Decision();

    @Test
    public void apply() throws Exception {
        String output = applyDecision(null, null, null, null, "123");
        assertEquals("123abc", output);
    }

    @Test
    public void applyWhenNull() {
        String output = applyDecision(null, null, null, null, null);
        assertNull(output);
    }

    @Override
    protected void applyDecision() {
        applyDecision(null, null, null, null, "123");
    }

    private String applyDecision(String booleanInput, String dateInput, String enumerationInput, String numberInput, String stringInput) {
        Map<String, String> input = new LinkedHashMap<>();
        input.put("BooleanInput", booleanInput);
        input.put("DateInput", dateInput);
        input.put("EnumerationInput", enumerationInput);
        input.put("NumberInput", numberInput);
        input.put("StringInput", stringInput);
        return decision.applyMap(input, context);
    }
}