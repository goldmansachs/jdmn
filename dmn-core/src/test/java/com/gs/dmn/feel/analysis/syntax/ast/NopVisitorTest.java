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
package com.gs.dmn.feel.analysis.syntax.ast;

import com.gs.dmn.feel.analysis.syntax.ast.expression.*;
import com.gs.dmn.feel.analysis.syntax.ast.expression.arithmetic.Addition;
import com.gs.dmn.feel.analysis.syntax.ast.expression.arithmetic.ArithmeticNegation;
import com.gs.dmn.feel.analysis.syntax.ast.expression.arithmetic.Exponentiation;
import com.gs.dmn.feel.analysis.syntax.ast.expression.arithmetic.Multiplication;
import com.gs.dmn.feel.analysis.syntax.ast.expression.comparison.BetweenExpression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.comparison.InExpression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.comparison.Relational;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.*;
import com.gs.dmn.feel.analysis.syntax.ast.expression.literal.*;
import com.gs.dmn.feel.analysis.syntax.ast.expression.logic.Conjunction;
import com.gs.dmn.feel.analysis.syntax.ast.expression.logic.Disjunction;
import com.gs.dmn.feel.analysis.syntax.ast.expression.logic.LogicNegation;
import com.gs.dmn.feel.analysis.syntax.ast.expression.textual.*;
import com.gs.dmn.feel.analysis.syntax.ast.expression.type.ContextTypeExpression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.type.FunctionTypeExpression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.type.ListTypeExpression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.type.NamedTypeExpression;
import com.gs.dmn.feel.analysis.syntax.ast.test.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class NopVisitorTest {
    private final NopVisitor visitor = new NopVisitor();

    //
    // Tests
    //
    @Test
    public void testVisitPositiveUnaryTests() {
        assertNull(visitor.visit((PositiveUnaryTests) null, null));
    }

    @Test
    public void testVisitNegatedPositiveUnaryTests() {
        assertNull(visitor.visit((NegatedPositiveUnaryTests) null, null));
    }

    @Test
    public void testVisitSimplePositiveUnaryTests() {
        assertNull(visitor.visit((SimplePositiveUnaryTests) null, null));
    }

    @Test
    public void testVisitNegatedSimplePositiveUnaryTests() {
        assertNull(visitor.visit((NegatedSimplePositiveUnaryTests) null, null));
    }

    @Test
    public void testVisitAny() {
        assertNull(visitor.visit((Any) null, null));
    }

    @Test
    public void testVisitNullTest() {
        assertNull(visitor.visit((NullTest) null, null));
    }

    @Test
    public void testVisitExpressionTest() {
        assertNull(visitor.visit((ExpressionTest) null, null));
    }

    @Test
    public void testVisitOperatorTest() {
        assertNull(visitor.visit((OperatorTest) null, null));
    }

    @Test
    public void testVisitRangeTest() {
        assertNull(visitor.visit((RangeTest) null, null));
    }

    @Test
    public void testVisitListTest() {
        assertNull(visitor.visit((ListTest) null, null));
    }

    //
    // Textual expressions
    //
    @Test
    public void testVisitFunctionDefinition() {
        assertNull(visitor.visit((FunctionDefinition) null, null));
    }

    @Test
    public void testVisitFormalParameter() {
        assertNull(visitor.visit((FormalParameter) null, null));
    }

    @Test
    public void testVisitContext() {
        assertNull(visitor.visit((Context) null, null));
    }

    @Test
    public void testVisitContextEntry() {
        assertNull(visitor.visit((ContextEntry) null, null));
    }

    @Test
    public void testVisitContextEntryKey() {
        assertNull(visitor.visit((ContextEntryKey) null, null));
    }

    @Test
    public void testVisitForExpression() {
        assertNull(visitor.visit((ForExpression) null, null));
    }

    @Test
    public void testVisitIterator() {
        assertNull(visitor.visit((Iterator) null, null));
    }

    @Test
    public void testVisitExpressionIteratorDomain() {
        assertNull(visitor.visit((ExpressionIteratorDomain) null, null));
    }

    @Test
    public void testVisitRangeIteratorDomain() {
        assertNull(visitor.visit((RangeIteratorDomain) null, null));
    }

    @Test
    public void testVisitIfExpression() {
        assertNull(visitor.visit((IfExpression) null, null));
    }

    @Test
    public void testVisitQuantifiedExpression() {
        assertNull(visitor.visit((QuantifiedExpression) null, null));
    }

    @Test
    public void testVisitFilterExpression() {
        assertNull(visitor.visit((FilterExpression) null, null));
    }

    @Test
    public void testVisitInstanceOfExpression() {
        assertNull(visitor.visit((InstanceOfExpression) null, null));
    }

    //
    // Expressions
    //
    @Test
    public void testVisitExpressionList() {
        assertNull(visitor.visit((ExpressionList) null, null));
    }

    //
    // Logic expressions
    //
    @Test
    public void testVisitConjunction() {
        assertNull(visitor.visit((Conjunction) null, null));
    }

    @Test
    public void testVisitDisjunction() {
        assertNull(visitor.visit((Disjunction) null, null));
    }

    @Test
    public void testVisitLogicNegation() {
        assertNull(visitor.visit((LogicNegation) null, null));
    }

    //
    // Comparison expressions
    //
    @Test
    public void testVisitRelational() {
        assertNull(visitor.visit((Relational) null, null));
    }

    @Test
    public void testVisitBetweenExpression() {
        assertNull(visitor.visit((BetweenExpression) null, null));
    }

    @Test
    public void testVisitInExpression() {
        assertNull(visitor.visit((InExpression) null, null));
    }

    //
    // Arithmetic expressions
    //
    @Test
    public void testVisitAddition() {
        assertNull(visitor.visit((Addition) null, null));
    }

    @Test
    public void testVisitMultiplication() {
        assertNull(visitor.visit((Multiplication) null, null));
    }

    @Test
    public void testVisitExponentiation() {
        assertNull(visitor.visit((Exponentiation) null, null));
    }

    @Test
    public void testVisitArithmeticNegation() {
        assertNull(visitor.visit((ArithmeticNegation) null, null));
    }

    //
    // Postfix expressions
    //
    @Test
    public void testVisitPathExpression() {
        assertNull(visitor.visit((PathExpression) null, null));
    }

    @Test
    public void testVisitFunctionInvocation() {
        assertNull(visitor.visit((FunctionInvocation) null, null));
    }

    @Test
    public void testVisitNamedParameters() {
        assertNull(visitor.visit((NamedParameters) null, null));
    }

    @Test
    public void testVisitPositionalParameters() {
        assertNull(visitor.visit((PositionalParameters) null, null));
    }

    //
    // Primary expressions
    //
    @Test
    public void testVisitBooleanLiteral() {
        assertNull(visitor.visit((BooleanLiteral) null, null));
    }

    @Test
    public void testVisitDateTimeLiteral() {
        assertNull(visitor.visit((DateTimeLiteral) null, null));
    }

    @Test
    public void testVisitNullLiteral() {
        assertNull(visitor.visit((NullLiteral) null, null));
    }

    @Test
    public void testVisitNumericLiteral() {
        assertNull(visitor.visit((NumericLiteral) null, null));
    }

    @Test
    public void testVisitStringLiteral() {
        assertNull(visitor.visit((StringLiteral) null, null));
    }

    @Test
    public void testVisitListLiteral() {
        assertNull(visitor.visit((ListLiteral) null, null));
    }

    @Test
    public void testVisitQualifiedName() {
        assertNull(visitor.visit((QualifiedName) null, null));
    }

    @Test
    public void testVisitName() {
        assertNull(visitor.visit((Name) null, null));
    }

    //
    // Type expressions
    //
    @Test
    public void testVisitNamedTypeExpression() {
        assertNull(visitor.visit((NamedTypeExpression) null, null));

    }

    @Test
    public void testVisitListTypeExpression() {
        assertNull(visitor.visit((ListTypeExpression) null, null));
    }

    @Test
    public void testVisitContextTypeExpression() {
        assertNull(visitor.visit((ContextTypeExpression) null, null));
    }

    @Test
    public void testVisitFunctionTypeExpression() {
        assertNull(visitor.visit((FunctionTypeExpression) null, null));
    }
}