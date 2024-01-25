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
package com.gs.dmn.generated.tck.cl2_0009_invocation_arithmetic;

import com.gs.dmn.generated.tck.AbstractHandwrittenDecisionTest;
import com.gs.dmn.generated.tck.cl2_0009_invocation_arithmetic.type.TLoanImpl;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HandwrittenMonthlyPaymentTest extends AbstractHandwrittenDecisionTest {
    private final MonthlyPayment decision = new MonthlyPayment();

    @Test
    public void testApply1() {
        TLoanImpl loan = new TLoanImpl();
        loan.setAmount(decision.number("600000"));
        loan.setRate(decision.number("0.0375"));
        loan.setTerm(decision.number("360"));
        BigDecimal fee = decision.number("100");
        BigDecimal output = applyDecision(loan, fee);
        assertEquals("2878.69", output.setScale(2, RoundingMode.FLOOR).toPlainString());
    }

    @Test
    public void testApply2() {
        TLoanImpl loan = new TLoanImpl();
        loan.setAmount(decision.number("30000"));
        loan.setRate(decision.number("0.0475"));
        loan.setTerm(decision.number("60"));
        BigDecimal fee = decision.number("100");
        BigDecimal output = applyDecision(loan, fee);
        assertEquals("662.70", output.setScale(2, RoundingMode.FLOOR).toPlainString());
    }

    @Override
    protected void applyDecision() {
        applyDecision(null, null);
    }

    private BigDecimal applyDecision(TLoanImpl loan, BigDecimal fee) {
        return decision.apply(loan, fee, context);
    }
}