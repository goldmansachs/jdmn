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
package com.gs.dmn.generated.sd_primitive_type_inputs_single_output_collect_sum_hit_policy;

import com.gs.dmn.generated.AbstractHandwrittenDecisionTest;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class HandwrittenDecisionTest extends AbstractHandwrittenDecisionTest {
    private final Decision decision = new Decision();

    @Test
    public void testApply() {
        assertEquals(10, applyDecision("1", "1").intValue());
        assertEquals(6, applyDecision("1", null).intValue());
        assertEquals(7, applyDecision(null, "1").intValue());
        assertEquals(4, applyDecision(null, null).intValue());
    }

    @Override
    protected void applyDecision() {
        applyDecision(null, null);
    }

    private BigDecimal applyDecision(String numberInput, String textInput) {
        Map<String, String> input = new LinkedHashMap<>();
        input.put("NumberInput", numberInput);
        input.put("TextInput", textInput);
        return decision.applyMap(input, context);
    }
}