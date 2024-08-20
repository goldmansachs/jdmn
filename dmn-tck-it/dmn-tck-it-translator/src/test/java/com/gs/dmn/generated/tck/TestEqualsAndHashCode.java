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
package com.gs.dmn.generated.tck;

import com.gs.dmn.generated.tck.cl2_0009_invocation_arithmetic.type.TLoan;
import com.gs.dmn.generated.tck.cl2_0009_invocation_arithmetic.type.TLoanImpl;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestEqualsAndHashCode {
    @Test
    public void testEquals() {
        TLoan loan1 = new TLoanImpl();
        TLoan loan2 = new TLoanImpl(BigDecimal.ONE, BigDecimal.TEN, BigDecimal.ZERO);
        assertEquals(loan1, loan1);
        assertEquals(loan2, loan2);
        assertNotEquals(null, loan1);
        assertNotEquals(loan1, loan2);
        assertNotEquals(loan2, loan1);
    }

    @Test
    public void testHashCode() {
        TLoan loan1 = new TLoanImpl();
        TLoan loan2 = new TLoanImpl(BigDecimal.ONE, BigDecimal.TEN, BigDecimal.ZERO);
        assertEquals(loan1.hashCode(), loan1.hashCode());
        assertEquals(loan2.hashCode(), loan2.hashCode());
    }
}
