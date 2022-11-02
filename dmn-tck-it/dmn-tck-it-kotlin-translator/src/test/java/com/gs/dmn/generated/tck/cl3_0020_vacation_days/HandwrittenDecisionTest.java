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
import com.gs.dmn.runtime.ExecutionContext;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class HandwrittenDecisionTest extends AbstractHandwrittenDecisionTest {
    private final TotalVacationDays decision = new TotalVacationDays();

    @Test
    public void applyCompiler() {
        com.gs.dmn.runtime.ExecutionContext context = new com.gs.dmn.runtime.ExecutionContext();

        assertEquals("27", applyDecision("16", "1", context).toPlainString());
        assertEquals("22", applyDecision("25", "5", context).toPlainString());
        assertEquals("24", applyDecision("25", "20", context).toPlainString());
        assertEquals("30", applyDecision("44", "30", context).toPlainString());
        assertEquals("24", applyDecision("50", "20", context).toPlainString());
        assertEquals("30", applyDecision("50", "30", context).toPlainString());
        assertEquals("30", applyDecision("60", "20", context).toPlainString());
    }

    @Override
    protected void applyDecision() {
        com.gs.dmn.runtime.ExecutionContext context = new com.gs.dmn.runtime.ExecutionContext();

        applyDecision(null, null, context);
    }

    private BigDecimal applyDecision(String age, String yearsOfService, ExecutionContext context) {
        Map<String, String> result = new LinkedHashMap<>();
        result.put("Age", age);
        result.put("Years of Service", yearsOfService);
        return decision.applyMap(result, context);
    }
}