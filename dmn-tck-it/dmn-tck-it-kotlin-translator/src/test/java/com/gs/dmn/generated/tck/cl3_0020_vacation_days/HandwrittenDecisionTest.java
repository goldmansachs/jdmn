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
package com.gs.dmn.generated.tck.cl3_0020_vacation_days;

import com.gs.dmn.generated.tck.AbstractHandwrittenDecisionTest;
import com.gs.dmn.runtime.annotation.AnnotationSet;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HandwrittenDecisionTest extends AbstractHandwrittenDecisionTest {
    private final TotalVacationDays decision = new TotalVacationDays();

    @Test
    public void applyCompiler() {
        AnnotationSet annotationSet = new AnnotationSet();
        assertEquals("27", decision.apply("16", "1", annotationSet).toPlainString());
        assertEquals("22", decision.apply("25", "5", annotationSet).toPlainString());
        assertEquals("24", decision.apply("25", "20", annotationSet).toPlainString());
        assertEquals("30", decision.apply("44", "30", annotationSet).toPlainString());
        assertEquals("24", decision.apply("50", "20", annotationSet).toPlainString());
        assertEquals("30", decision.apply("50", "30", annotationSet).toPlainString());
        assertEquals("30", decision.apply("60", "20", annotationSet).toPlainString());
    }

    @Override
    protected void applyDecision() {
        AnnotationSet annotationSet = new AnnotationSet();
        decision.apply((String) null, null, annotationSet);
    }
}