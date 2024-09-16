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
package com.gs.dmn.generated.sd_feel_complex_literal_expression;

import com.gs.dmn.generated.AbstractHandwrittenDecisionTest;
import com.gs.dmn.generated.sd_feel_complex_literal_expression.type.LoanImpl;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class HandwrittenMonthlyTest extends AbstractHandwrittenDecisionTest {
    private final Monthly decision = new Monthly();

    @Test
    public void testApply() {
        LoanImpl loan = new LoanImpl();
        loan.setPrincipal(new BigDecimal("100.00", MathContext.DECIMAL128));
        loan.setRate(new BigDecimal("5", MathContext.DECIMAL128));
        loan.setTerm(new BigDecimal("10", MathContext.DECIMAL128));

        Number output = applyDecision(toJson(loan));

        assertEquals("100.00", round(output, 2));
    }

    @Test
    public void testApplyWhenNull() {
        Number output = applyDecision(null);
        assertNull(output);
    }

    @Override
    protected void applyDecision() {
        LoanImpl loan = new LoanImpl();
        loan.setPrincipal(new BigDecimal("100.00", MathContext.DECIMAL128));
        loan.setRate(new BigDecimal("5", MathContext.DECIMAL128));
        loan.setTerm(new BigDecimal("10", MathContext.DECIMAL128));

        applyDecision(toJson(loan));
    }

    private Number applyDecision(String loan) {
        Map<String, String> input = new LinkedHashMap<>();
        input.put("Loan", loan);
        return decision.applyMap(input, context);
    }

}
