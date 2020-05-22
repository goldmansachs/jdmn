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
package com.gs.dmn.generated.sd_primitive_type_inputs_single_output_collect_min_hit_policy;

import com.gs.dmn.generated.AbstractHandwrittenDecisionTest;
import com.gs.dmn.runtime.annotation.AnnotationSet;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HandwrittenDecisionTest extends AbstractHandwrittenDecisionTest {
    private final Decision decision = new Decision();

    @Test
    public void testApply() {
        AnnotationSet annotationSet = new AnnotationSet();
        assertEquals(1, decision.apply("1", "1", annotationSet).intValue());
        assertEquals(2, decision.apply("1", null, annotationSet).intValue());
        assertEquals(3, decision.apply((String)null, "1", annotationSet).intValue());
        assertEquals(4, decision.apply((String)null, null, annotationSet).intValue());
    }

    @Override
    protected void applyDecision() {
        AnnotationSet annotationSet = new AnnotationSet();
        decision.apply((String)null, null, annotationSet);
    }
}