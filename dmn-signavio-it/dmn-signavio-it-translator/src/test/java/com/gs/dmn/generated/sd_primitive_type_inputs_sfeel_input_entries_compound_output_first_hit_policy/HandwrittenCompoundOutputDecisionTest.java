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
package com.gs.dmn.generated.sd_primitive_type_inputs_sfeel_input_entries_compound_output_first_hit_policy;

import com.gs.dmn.generated.AbstractHandwrittenDecisionTest;
import com.gs.dmn.runtime.annotation.AnnotationSet;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HandwrittenCompoundOutputDecisionTest extends AbstractHandwrittenDecisionTest {
    private final CompoundOutputDecision decision = new CompoundOutputDecision();

    @Test
    public void testApplyWhenRule0() {
        com.gs.dmn.generated.sd_primitive_type_inputs_sfeel_input_entries_compound_output_first_hit_policy.type.CompoundOutputDecision output = applyDecision("true", "2016-08-01T11:00:00Z", "2016-08-01", "e1", "-1", "abc", "12:00:00Z");
        assertEquals("r11", output.getFirstOutput());
        assertEquals("r12", output.getSecondOutput());
    }

    private com.gs.dmn.generated.sd_primitive_type_inputs_sfeel_input_entries_compound_output_first_hit_policy.type.CompoundOutputDecision applyDecision(
            String booleanString, String dateAndTimeString, String dateString, String enumerationString,
            String numberString, String text, String timeString) {
        AnnotationSet annotationSet = new AnnotationSet();

        return decision.apply(booleanString, dateAndTimeString, dateString, enumerationString, numberString, text, timeString, annotationSet);
    }

    @Override
    protected void applyDecision() {
        AnnotationSet annotationSet = new AnnotationSet();
        decision.apply(null, (String)null, null, null, null, null, null, annotationSet);
    }
}