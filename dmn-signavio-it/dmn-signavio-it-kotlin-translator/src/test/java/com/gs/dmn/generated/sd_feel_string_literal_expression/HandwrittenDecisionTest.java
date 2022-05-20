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
import com.gs.dmn.runtime.annotation.AnnotationSet;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HandwrittenDecisionTest extends AbstractHandwrittenDecisionTest {
    private final Decision decision = new Decision();

    @Test
    public void apply() throws Exception {
        String output = decision.apply(null, (String)null, null, null, "123", annotationSet, eventListener, externalFunctionExecutor, cache);
        assertEquals("123abc", output);
    }

    @Test
    public void applyWhenNull() throws Exception {
        String output = decision.apply(null, (String)null, null, null, null, annotationSet, eventListener, externalFunctionExecutor, cache);
        assertEquals(null, output);
    }

    @Override
    protected void applyDecision() {
        decision.apply(null, (String)null, null, null, "123", annotationSet, eventListener, externalFunctionExecutor, cache);
    }
}