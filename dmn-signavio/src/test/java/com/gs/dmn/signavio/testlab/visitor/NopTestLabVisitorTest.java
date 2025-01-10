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
package com.gs.dmn.signavio.testlab.visitor;

import com.gs.dmn.signavio.testlab.InputParameterDefinition;
import com.gs.dmn.signavio.testlab.OutputParameterDefinition;
import com.gs.dmn.signavio.testlab.TestCase;
import com.gs.dmn.signavio.testlab.TestLab;
import com.gs.dmn.signavio.testlab.expression.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

public class NopTestLabVisitorTest {
    private final NopTestLabVisitor<Object> visitor = new NopTestLabVisitor<>();

    @Test
    public void testVisitTestLab() { 
        assertNull(visitor.visit((TestLab) null, null));
    }

    @Test
    public void testVisitInputParameterDefinition() {
        assertNull(visitor.visit((InputParameterDefinition) null, null));
    }

    @Test
    public void testVisitOutputParameterDefinition() {
        assertNull(visitor.visit((OutputParameterDefinition) null, null));
    }

    @Test
    public void testVisitTestCase() {
        assertNull(visitor.visit((TestCase) null, null));
    }

    @Test
    public void testVisitNumberLiteral() {
        assertNull(visitor.visit((NumberLiteral) null, null));
    }

    @Test
    public void testVisitStringLiteral() {
        assertNull(visitor.visit((StringLiteral) null, null));
    }

    @Test
    public void testVisitBooleanLiteral() {
        assertNull(visitor.visit((BooleanLiteral) null, null));
    }

    @Test
    public void testVisitDateLiteral() {
        assertNull(visitor.visit((DateLiteral) null, null));
    }

    @Test
    public void testVisitTimeLiteral() {
        assertNull(visitor.visit((TimeLiteral) null, null));
    }

    @Test
    public void testVisitDatetimeLiteral() {
        assertNull(visitor.visit((DatetimeLiteral) null, null));
    }

    @Test
    public void testVisitEnumerationLiteral() {
        assertNull(visitor.visit((EnumerationLiteral) null, null));
    }

    @Test
    public void testVisitListExpression() {
        assertNull(visitor.visit((ListExpression) null, null));
    }

    @Test
    public void testVisitComplexExpression() {
        assertNull(visitor.visit((ComplexExpression) null, null));
    }

    @Test
    public void testVisitSlot() {
        assertNull(visitor.visit((Slot) null, null));
    }
}