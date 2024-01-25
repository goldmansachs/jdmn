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
package com.gs.dmn.generated.sd_primitive_type_inputs_feel_input_entries_single_output_first_hit_policy;

import com.gs.dmn.generated.AbstractHandwrittenDecisionTest;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HandwrittenDecisionTest extends AbstractHandwrittenDecisionTest {
    private final Decision decision = new Decision();

    @Test
    public void testApply() {
        String result = applyDecision("true", "2016-08-01T11:00:00Z", "2016-08-01", "e1", "-1", "abc", "12:00:00Z");
        assertEquals("r1", result);
    }

    @Override
    protected void applyDecision() {
        String dateInput = "2016-08-01";
        String timeInput = "12:00:00Z";
        String dateAndTimeInput = "2016-08-01T11:00:00Z";
        String numberInput = "-1";
        String textInput = "abc";
        String booleanInput = "true";
        String enumerationInput = "e1";

        applyDecision(booleanInput, dateAndTimeInput, dateInput, enumerationInput, numberInput, textInput, timeInput);
    }

    private String applyDecision(String booleanInput, String dateAndTimeInput, String dateInput, String enumerationInput, String numberInput, String textInput, String timeInput) {
        Map<String, String> input = new LinkedHashMap<>();
        input.put("BooleanInput", booleanInput);
        input.put("DateAndTimeInput", dateAndTimeInput);
        input.put("DateInput", dateInput);
        input.put("EnumerationInput", enumerationInput);
        input.put("NumberInput", numberInput);
        input.put("TextInput", textInput);
        input.put("TimeInput", timeInput);
        return decision.applyMap(input, context);
    }
}