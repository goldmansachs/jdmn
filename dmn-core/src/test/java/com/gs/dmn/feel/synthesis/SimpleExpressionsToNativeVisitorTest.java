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
package com.gs.dmn.feel.synthesis;

import com.gs.dmn.AbstractTest;
import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.dialect.StandardDMNDialectDefinition;
import com.gs.dmn.feel.analysis.syntax.ast.expression.ExpressionIteratorDomain;
import com.gs.dmn.feel.analysis.syntax.ast.expression.ExpressionList;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Iterator;
import com.gs.dmn.feel.analysis.syntax.ast.expression.RangeIteratorDomain;
import com.gs.dmn.feel.analysis.syntax.ast.expression.comparison.BetweenExpression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.comparison.InExpression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FunctionDefinition;
import com.gs.dmn.feel.analysis.syntax.ast.expression.logic.Conjunction;
import com.gs.dmn.feel.analysis.syntax.ast.expression.logic.Disjunction;
import com.gs.dmn.feel.analysis.syntax.ast.expression.logic.LogicNegation;
import com.gs.dmn.feel.analysis.syntax.ast.expression.textual.*;
import com.gs.dmn.feel.analysis.syntax.ast.test.*;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;

import static org.junit.Assert.assertNull;

public class SimpleExpressionsToNativeVisitorTest extends AbstractTest {
    private final StandardDMNDialectDefinition dialect = new StandardDMNDialectDefinition();
    private final SimpleExpressionsToNativeVisitor visitor = new SimpleExpressionsToNativeVisitor(dialect.createBasicTransformer(new DMNModelRepository(), new NopLazyEvaluationDetector(), makeInputParameters()));

    //
    // Tests
    //
    @org.junit.Test(expected = UnsupportedOperationException.class)
    public void testVisitNegatedPositiveUnaryTests() {
        assertNull(visitor.visit((NegatedPositiveUnaryTests) null, null));
    }

    @org.junit.Test(expected = UnsupportedOperationException.class)
    public void testVisitSimplePositiveUnaryTests() {
        assertNull(visitor.visit((SimplePositiveUnaryTests) null, null));
    }

    @org.junit.Test(expected = UnsupportedOperationException.class)
    public void testVisitNegatedSimplePositiveUnaryTests() {
        assertNull(visitor.visit((NegatedSimplePositiveUnaryTests) null, null));
    }

    @org.junit.Test(expected = UnsupportedOperationException.class)
    public void testVisitNullTest() {
        assertNull(visitor.visit((NullTest) null, null));
    }

    @org.junit.Test(expected = UnsupportedOperationException.class)
    public void testVisitRangeTest() {
        assertNull(visitor.visit((RangeTest) null, null));
    }

    @org.junit.Test(expected = UnsupportedOperationException.class)
    public void testVisitListTest() {
        assertNull(visitor.visit((ListTest) null, null));
    }

    //
    // Textual expressions
    //
    @org.junit.Test(expected = UnsupportedOperationException.class)
    public void testVisitFunctionDefinition() {
        assertNull(visitor.visit((FunctionDefinition) null, null));
    }

    @org.junit.Test(expected = UnsupportedOperationException.class)
    public void testVisitFormalParameter() {
        assertNull(visitor.visit((FormalParameter) null, null));
    }

    @org.junit.Test(expected = UnsupportedOperationException.class)
    public void testVisitForExpression() {
        assertNull(visitor.visit((ForExpression) null, null));
    }

    @org.junit.Test(expected = UnsupportedOperationException.class)
    public void testVisitIterator() {
        assertNull(visitor.visit((Iterator) null, null));
    }

    @org.junit.Test(expected = UnsupportedOperationException.class)
    public void testVisitExpressionIteratorDomain() {
        assertNull(visitor.visit((ExpressionIteratorDomain) null, null));
    }

    @org.junit.Test(expected = UnsupportedOperationException.class)
    public void testVisitRangeIteratorDomain() {
        assertNull(visitor.visit((RangeIteratorDomain) null, null));
    }

    @org.junit.Test(expected = UnsupportedOperationException.class)
    public void testVisitIfExpression() {
        assertNull(visitor.visit((IfExpression) null, null));
    }

    @org.junit.Test(expected = UnsupportedOperationException.class)
    public void testVisitQuantifiedExpression() {
        assertNull(visitor.visit((QuantifiedExpression) null, null));
    }

    @org.junit.Test(expected = UnsupportedOperationException.class)
    public void testVisitFilterExpression() {
        assertNull(visitor.visit((FilterExpression) null, null));
    }

    @org.junit.Test(expected = UnsupportedOperationException.class)
    public void testVisitInstanceOfExpression() {
        assertNull(visitor.visit((InstanceOfExpression) null, null));
    }

    //
    // Expressions
    //
    @org.junit.Test(expected = UnsupportedOperationException.class)
    public void testVisitExpressionList() {
        assertNull(visitor.visit((ExpressionList) null, null));
    }

    //
    // Logic functions
    //
    @org.junit.Test(expected = UnsupportedOperationException.class)
    public void testVisitConjunction() {
        assertNull(visitor.visit((Conjunction) null, null));
    }

    @org.junit.Test(expected = UnsupportedOperationException.class)
    public void testVisitDisjunction() {
        assertNull(visitor.visit((Disjunction) null, null));
    }

    @org.junit.Test(expected = UnsupportedOperationException.class)
    public void testVisitLogicNegation() {
        assertNull(visitor.visit((LogicNegation) null, null));
    }

    //
    // Comparison expressions
    //
    @org.junit.Test(expected = UnsupportedOperationException.class)
    public void testVisitBetweenExpression() {
        assertNull(visitor.visit((BetweenExpression) null, null));
    }

    @org.junit.Test(expected = UnsupportedOperationException.class)
    public void testVisitInExpression() {
        assertNull(visitor.visit((InExpression) null, null));
    }
}