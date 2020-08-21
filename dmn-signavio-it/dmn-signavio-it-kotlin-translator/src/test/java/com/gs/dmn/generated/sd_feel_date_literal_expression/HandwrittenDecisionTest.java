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
import com.gs.dmn.runtime.annotation.AnnotationSet;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class HandwrittenDecisionTest extends AbstractHandwrittenDecisionTest {
    private final Decision decision = new Decision();

    @Test
    public void testApply() throws Exception {
        AnnotationSet annotationSet = new AnnotationSet();
        BigDecimal output = decision.apply(null, "1965-03-29", null, null, null, annotationSet);
        assertEquals("29", output.toString());
    }

    @Test(expected = NullPointerException.class)
    public void testApplyWhenNull() throws Exception {
        AnnotationSet annotationSet = new AnnotationSet();
        BigDecimal output = decision.apply(null, (String)null, null, null, null, annotationSet);
        assertEquals(29, output.intValue());
    }

    @Override
    protected void applyDecision() {
        AnnotationSet annotationSet = new AnnotationSet();
        decision.apply(null, "1965-03-29", null, null, null, annotationSet);
    }
}