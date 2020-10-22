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

import static org.junit.Assert.assertNull;

public abstract class BaseVisitorTest {
    //
    // Tests
    //
    @Test
    public void testVisitPositiveUnaryTests() {
        assertNull(getVisitor().visit((PositiveUnaryTests) null, null));
    }

    @Test
    public void testVisitNegatedPositiveUnaryTests() {
        assertNull(getVisitor().visit((NegatedPositiveUnaryTests) null, null));
    }

    @Test
    public void testVisitSimplePositiveUnaryTests() {
        assertNull(getVisitor().visit((SimplePositiveUnaryTests) null, null));
    }

    @Test
    public void testVisitNegatedSimplePositiveUnaryTests() {
        assertNull(getVisitor().visit((NegatedSimplePositiveUnaryTests) null, null));
    }

    @Test
    public void testVisitAny() {
        assertNull(getVisitor().visit((Any) null, null));
    }

    @Test
    public void testVisitNullTest() {
        assertNull(getVisitor().visit((NullTest) null, null));
    }

    @Test
    public void testVisitExpressionTest() {
        assertNull(getVisitor().visit((ExpressionTest) null, null));
    }

    @Test
    public void testVisitOperatorTest() {
        assertNull(getVisitor().visit((OperatorTest) null, null));
    }

    @Test
    public void testVisitRangeTest() {
        assertNull(getVisitor().visit((RangeTest) null, null));
    }

    @Test
    public void testVisitListTest() {
        assertNull(getVisitor().visit((ListTest) null, null));
    }

    //
    // Textual expressions
    //
    @Test
    public void testVisitFunctionDefinition() {
        assertNull(getVisitor().visit((FunctionDefinition) null, null));
    }

    @Test
    public void testVisitFormalParameter() {
        assertNull(getVisitor().visit((FormalParameter) null, null));
    }

    @Test
    public void testVisitContext() {
        assertNull(getVisitor().visit((Context) null, null));
    }

    @Test
    public void testVisitContextEntry() {
        assertNull(getVisitor().visit((ContextEntry) null, null));
    }

    @Test
    public void testVisitContextEntryKey() {
        assertNull(getVisitor().visit((ContextEntryKey) null, null));
    }

    @Test
    public void testVisitForExpression() {
        assertNull(getVisitor().visit((ForExpression) null, null));
    }

    @Test
    public void testVisitIterator() {
        assertNull(getVisitor().visit((Iterator) null, null));
    }

    @Test
    public void testVisitExpressionIteratorDomain() {
        assertNull(getVisitor().visit((ExpressionIteratorDomain) null, null));
    }

    @Test
    public void testVisitRangeIteratorDomain() {
        assertNull(getVisitor().visit((RangeIteratorDomain) null, null));
    }

    @Test
    public void testVisitIfExpression() {
        assertNull(getVisitor().visit((IfExpression) null, null));
    }

    @Test
    public void testVisitQuantifiedExpression() {
        assertNull(getVisitor().visit((QuantifiedExpression) null, null));
    }

    @Test
    public void testVisitFilterExpression() {
        assertNull(getVisitor().visit((FilterExpression) null, null));
    }

    @Test
    public void testVisitInstanceOfExpression() {
        assertNull(getVisitor().visit((InstanceOfExpression) null, null));
    }

    //
    // Expressions
    //
    @Test
    public void testVisitExpressionList() {
        assertNull(getVisitor().visit((ExpressionList) null, null));
    }

    //
    // Logic expressions
    //
    @Test
    public void testVisitConjunction() {
        assertNull(getVisitor().visit((Conjunction) null, null));
    }

    @Test
    public void testVisitDisjunction() {
        assertNull(getVisitor().visit((Disjunction) null, null));
    }

    @Test
    public void testVisitLogicNegation() {
        assertNull(getVisitor().visit((LogicNegation) null, null));
    }

    //
    // Comparison expressions
    //
    @Test
    public void testVisitRelational() {
        assertNull(getVisitor().visit((Relational) null, null));
    }

    @Test
    public void testVisitBetweenExpression() {
        assertNull(getVisitor().visit((BetweenExpression) null, null));
    }

    @Test
    public void testVisitInExpression() {
        assertNull(getVisitor().visit((InExpression) null, null));
    }

    //
    // Arithmetic expressions
    //
    @Test
    public void testVisitAddition() {
        assertNull(getVisitor().visit((Addition) null, null));
    }

    @Test
    public void testVisitMultiplication() {
        assertNull(getVisitor().visit((Multiplication) null, null));
    }

    @Test
    public void testVisitExponentiation() {
        assertNull(getVisitor().visit((Exponentiation) null, null));
    }

    @Test
    public void testVisitArithmeticNegation() {
        assertNull(getVisitor().visit((ArithmeticNegation) null, null));
    }

    //
    // Postfix expressions
    //
    @Test
    public void testVisitPathExpression() {
        assertNull(getVisitor().visit((PathExpression) null, null));
    }

    @Test
    public void testVisitFunctionInvocation() {
        assertNull(getVisitor().visit((FunctionInvocation) null, null));
    }

    @Test
    public void testVisitNamedParameters() {
        assertNull(getVisitor().visit((NamedParameters) null, null));
    }

    @Test
    public void testVisitPositionalParameters() {
        assertNull(getVisitor().visit((PositionalParameters) null, null));
    }

    //
    // Primary expressions
    //
    @Test
    public void testVisitBooleanLiteral() {
        assertNull(getVisitor().visit((BooleanLiteral) null, null));
    }

    @Test
    public void testVisitDateTimeLiteral() {
        assertNull(getVisitor().visit((DateTimeLiteral) null, null));
    }

    @Test
    public void testVisitNullLiteral() {
        assertNull(getVisitor().visit((NullLiteral) null, null));
    }

    @Test
    public void testVisitNumericLiteral() {
        assertNull(getVisitor().visit((NumericLiteral) null, null));
    }

    @Test
    public void testVisitStringLiteral() {
        assertNull(getVisitor().visit((StringLiteral) null, null));
    }

    @Test
    public void testVisitListLiteral() {
        assertNull(getVisitor().visit((ListLiteral) null, null));
    }

    @Test
    public void testVisitQualifiedName() {
        assertNull(getVisitor().visit((QualifiedName) null, null));
    }

    @Test
    public void testVisitName() {
        assertNull(getVisitor().visit((Name) null, null));
    }

    //
    // Type expressions
    //
    @Test
    public void testVisitNamedTypeExpression() {
        assertNull(getVisitor().visit((NamedTypeExpression) null, null));

    }

    @Test
    public void testVisitListTypeExpression() {
        assertNull(getVisitor().visit((ListTypeExpression) null, null));
    }

    @Test
    public void testVisitContextTypeExpression() {
        assertNull(getVisitor().visit((ContextTypeExpression) null, null));
    }

    @Test
    public void testVisitFunctionTypeExpression() {
        assertNull(getVisitor().visit((FunctionTypeExpression) null, null));
    }

    abstract protected Visitor getVisitor();
}