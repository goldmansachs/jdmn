package com.gs.dmn.signavio.testlab;

import com.gs.dmn.signavio.testlab.expression.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class NopTestLabVisitorTest {
    private final NopTestLabVisitor visitor = new NopTestLabVisitor();

    @Test
    public void testVisitTestLab() { 
        assertNull(visitor.visit((TestLab) null));
    }

    @Test
    public void testVisitInputParameterDefinition() {
        assertNull(visitor.visit((InputParameterDefinition) null));
    }

    @Test
    public void testVisitOutputParameterDefinition() {
        assertNull(visitor.visit((OutputParameterDefinition) null));
    }

    @Test
    public void testVisitTestCase() {
        assertNull(visitor.visit((TestCase) null));
    }

    @Test
    public void testVisitNumberLiteral() {
        assertNull(visitor.visit((NumberLiteral) null));
    }

    @Test
    public void testVisitStringLiteral() {
        assertNull(visitor.visit((StringLiteral) null));
    }

    @Test
    public void testVisitBooleanLiteral() {
        assertNull(visitor.visit((BooleanLiteral) null));
    }

    @Test
    public void testVisitDateLiteral() {
        assertNull(visitor.visit((DateLiteral) null));
    }

    @Test
    public void testVisitTimeLiteral() {
        assertNull(visitor.visit((TimeLiteral) null));
    }

    @Test
    public void testVisitDatetimeLiteral() {
        assertNull(visitor.visit((DatetimeLiteral) null));
    }

    @Test
    public void testVisitEnumerationLiteral() {
        assertNull(visitor.visit((EnumerationLiteral) null));
    }

    @Test
    public void testVisitListExpression() {
        assertNull(visitor.visit((ListExpression) null));
    }

    @Test
    public void testVisitComplexExpression() {
        assertNull(visitor.visit((ComplexExpression) null));
    }

    @Test
    public void testVisitSlot() {
        assertNull(visitor.visit((Slot) null));
    }
}