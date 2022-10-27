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
package com.gs.dmn.generated.sd_primitive_type_inputs_compound_output_priority_hit_policy;

import com.gs.dmn.generated.AbstractHandwrittenDecisionTest;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class HandwrittenDecisionTest extends AbstractHandwrittenDecisionTest {
    private final Decision decision = new Decision();

    @Test
    public void testApply() {
        com.gs.dmn.generated.sd_primitive_type_inputs_compound_output_priority_hit_policy.type.Decision output = applyDecision("1", "1");
        assertEquals("r2", output.getOutput1());
        assertEquals("r6", output.getOutput2());

        output = applyDecision("1", null);
        assertEquals("r2", output.getOutput1());
        assertEquals("r7", output.getOutput2());

        output = applyDecision(null, "1");
        assertEquals("r2", output.getOutput1());
        assertEquals("r8", output.getOutput2());

        output = applyDecision(null, null);
        assertEquals("r2", output.getOutput1());
        assertEquals("r9", output.getOutput2());
    }

    @Override
    protected void applyDecision() {
        applyDecision("1", "1");
    }

    private com.gs.dmn.generated.sd_primitive_type_inputs_compound_output_priority_hit_policy.type.Decision applyDecision(String numberInput, String textInput) {
        Map<String, String> input = new LinkedHashMap<>();
        input.put("NumberInput", numberInput);
        input.put("TextInput", textInput);
        return decision.apply(input, context);
    }
}