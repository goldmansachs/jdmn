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
package com.gs.dmn.generated.sd_feel_boolean_literal_expression;

import com.gs.dmn.generated.AbstractHandwrittenDecisionTest;
import com.gs.dmn.runtime.annotation.AnnotationSet;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HandwrittenDecisionTest extends AbstractHandwrittenDecisionTest {
    private final Decision decision = new Decision();

    @Test
    public void testApply() {
        AnnotationSet annotationSet = new AnnotationSet();
        Boolean output = decision.apply("true", null, null, null, null, annotationSet);
        assertEquals(true, output);

        output = decision.apply("false", null, null, null, null, annotationSet);
        assertEquals(false, output);
    }

    @Test
    public void testApplyWhenNull() {
        AnnotationSet annotationSet = new AnnotationSet();
        Boolean output = decision.apply((String)null, null, null, null, null, annotationSet);
        assertEquals(null, output);
    }

    @Override
    protected void applyDecision() {
        AnnotationSet annotationSet = new AnnotationSet();
        decision.apply("false", null, null, null, null, annotationSet);
    }
}
