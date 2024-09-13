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

import com.gs.dmn.runtime.ExecutionContext;
import com.gs.dmn.runtime.ExecutionContextBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractHandwrittenDecisionTest {
    protected ExecutionContext context;

    protected abstract void applyDecision();

    @Test
    public void testPerformance() {
        long before = System.currentTimeMillis();
        applyDecision();
        long after = System.currentTimeMillis();
        assertTrue(after - before < 500, "Takes longer than 500ms");
    }

    @BeforeEach
    public void setUp() {
        this.context = ExecutionContextBuilder.executionContext().build();
    }

    protected String round(Number number, int scale) {
        return new BigDecimal(number.toString()).setScale(scale, RoundingMode.FLOOR).toPlainString();
    }
}
