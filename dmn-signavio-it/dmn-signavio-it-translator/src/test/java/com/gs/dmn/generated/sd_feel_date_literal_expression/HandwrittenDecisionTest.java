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
package com.gs.dmn.generated.sd_feel_date_literal_expression;

import com.gs.dmn.generated.AbstractHandwrittenDecisionTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HandwrittenDecisionTest extends AbstractHandwrittenDecisionTest {
    private final Decision decision = new Decision();

    @Test
    public void testApply() throws Exception {
        BigDecimal output = applyDecision(null, "1965-03-29", null, null, null);
        assertEquals("29", output.toString());
    }

    @Test
    public void testApplyWhenNull() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            Assertions.assertThrows(NullPointerException.class, () -> {
                BigDecimal output = applyDecision(null, null, null, null, null);
                assertEquals(29, output.intValue());
            });
        });
    }

    @Override
    protected void applyDecision() {
        applyDecision(null, "1965-03-29", null, null, null);
    }

    private BigDecimal applyDecision(String booleanInput, String dateInput, String enumerationInput, String numberInput, String stringInput) {
        Map<String, String> input = new LinkedHashMap<>();
        input.put("BooleanInput", booleanInput);
        input.put("DateInput", dateInput);
        input.put("EnumerationInput", enumerationInput);
        input.put("NumberInput", numberInput);
        input.put("StringInput", stringInput);
        return decision.applyMap(input, context);
    }
}