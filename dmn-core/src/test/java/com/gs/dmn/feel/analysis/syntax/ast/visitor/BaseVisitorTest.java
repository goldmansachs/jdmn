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
package com.gs.dmn.feel.analysis.syntax.ast.visitor;

import com.gs.dmn.context.DMNContext;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.Element;
import com.gs.dmn.feel.analysis.syntax.ast.Visitor;
import com.gs.dmn.feel.analysis.syntax.ast.expression.*;
import com.gs.dmn.feel.analysis.syntax.ast.expression.arithmetic.Addition;
import com.gs.dmn.feel.analysis.syntax.ast.expression.arithmetic.ArithmeticNegation;
import com.gs.dmn.feel.analysis.syntax.ast.expression.arithmetic.Exponentiation;
import com.gs.dmn.feel.analysis.syntax.ast.expression.arithmetic.Multiplication;
import com.gs.dmn.feel.analysis.syntax.ast.expression.comparison.BetweenExpression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.comparison.InExpression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.comparison.Relational;
import com.gs.dmn.feel.analysis.syntax.ast.expression.context.Context;
import com.gs.dmn.feel.analysis.syntax.ast.expression.context.ContextEntry;
import com.gs.dmn.feel.analysis.syntax.ast.expression.context.ContextEntryKey;
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
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNull;

public abstract class BaseVisitorTest {
    //
    // Tests
    //
    @Test
    public void testVisitPositiveUnaryTests() {
        assertNull(getVisitor().visit((PositiveUnaryTests<Type>) null, null));
    }

    @Test
    public void testVisitNegatedPositiveUnaryTests() {
        assertNull(getVisitor().visit((NegatedPositiveUnaryTests<Type>) null, null));
    }

    @Test
    public void testVisitAny() {
        assertNull(getVisitor().visit((Any<Type>) null, null));
    }

    @Test
    public void testVisitNullTest() {
        assertNull(getVisitor().visit((NullTest<Type>) null, null));
    }

    @Test
    public void testVisitExpressionTest() {
        assertNull(getVisitor().visit((ExpressionTest<Type>) null, null));
    }

    @Test
    public void testVisitOperatorTest() {
        assertNull(getVisitor().visit((OperatorRange<Type>) null, null));
    }

    @Test
    public void testVisitRangeTest() {
        assertNull(getVisitor().visit((EndpointsRange<Type>) null, null));
    }

    @Test
    public void testVisitListTest() {
        assertNull(getVisitor().visit((ListTest<Type>) null, null));
    }

    //
    // Textual expressions
    //
    @Test
    public void testVisitFunctionDefinition() {
        assertNull(getVisitor().visit((FunctionDefinition<Type>) null, null));
    }

    @Test
    public void testVisitFormalParameter() {
        assertNull(getVisitor().visit((FormalParameter<Type>) null, null));
    }

    @Test
    public void testVisitContext() {
        assertNull(getVisitor().visit((Context<Type>) null, null));
    }

    @Test
    public void testVisitContextEntry() {
        assertNull(getVisitor().visit((ContextEntry<Type>) null, null));
    }

    @Test
    public void testVisitContextEntryKey() {
        assertNull(getVisitor().visit((ContextEntryKey<Type>) null, null));
    }

    @Test
    public void testVisitForExpression() {
        assertNull(getVisitor().visit((ForExpression<Type>) null, null));
    }

    @Test
    public void testVisitIterator() {
        assertNull(getVisitor().visit((Iterator<Type>) null, null));
    }

    @Test
    public void testVisitExpressionIteratorDomain() {
        assertNull(getVisitor().visit((ExpressionIteratorDomain<Type>) null, null));
    }

    @Test
    public void testVisitRangeIteratorDomain() {
        assertNull(getVisitor().visit((RangeIteratorDomain<Type>) null, null));
    }

    @Test
    public void testVisitIfExpression() {
        assertNull(getVisitor().visit((IfExpression<Type>) null, null));
    }

    @Test
    public void testVisitQuantifiedExpression() {
        assertNull(getVisitor().visit((QuantifiedExpression<Type>) null, null));
    }

    @Test
    public void testVisitFilterExpression() {
        assertNull(getVisitor().visit((FilterExpression<Type>) null, null));
    }

    @Test
    public void testVisitInstanceOfExpression() {
        assertNull(getVisitor().visit((InstanceOfExpression<Type>) null, null));
    }

    //
    // Expressions
    //
    @Test
    public void testVisitExpressionList() {
        assertNull(getVisitor().visit((ExpressionList<Type>) null, null));
    }

    //
    // Logic expressions
    //
    @Test
    public void testVisitConjunction() {
        assertNull(getVisitor().visit((Conjunction<Type>) null, null));
    }

    @Test
    public void testVisitDisjunction() {
        assertNull(getVisitor().visit((Disjunction<Type>) null, null));
    }

    @Test
    public void testVisitLogicNegation() {
        assertNull(getVisitor().visit((LogicNegation<Type>) null, null));
    }

    //
    // Comparison expressions
    //
    @Test
    public void testVisitRelational() {
        assertNull(getVisitor().visit((Relational<Type>) null, null));
    }

    @Test
    public void testVisitBetweenExpression() {
        assertNull(getVisitor().visit((BetweenExpression<Type>) null, null));
    }

    @Test
    public void testVisitInExpression() {
        assertNull(getVisitor().visit((InExpression<Type>) null, null));
    }

    //
    // Arithmetic expressions
    //
    @Test
    public void testVisitAddition() {
        assertNull(getVisitor().visit((Addition<Type>) null, null));
    }

    @Test
    public void testVisitMultiplication() {
        assertNull(getVisitor().visit((Multiplication<Type>) null, null));
    }

    @Test
    public void testVisitExponentiation() {
        assertNull(getVisitor().visit((Exponentiation<Type>) null, null));
    }

    @Test
    public void testVisitArithmeticNegation() {
        assertNull(getVisitor().visit((ArithmeticNegation<Type>) null, null));
    }

    //
    // Postfix expressions
    //
    @Test
    public void testVisitPathExpression() {
        assertNull(getVisitor().visit((PathExpression<Type>) null, null));
    }

    @Test
    public void testVisitFunctionInvocation() {
        assertNull(getVisitor().visit((FunctionInvocation<Type>) null, null));
    }

    @Test
    public void testVisitNamedParameters() {
        assertNull(getVisitor().visit((NamedParameters<Type>) null, null));
    }

    @Test
    public void testVisitPositionalParameters() {
        assertNull(getVisitor().visit((PositionalParameters<Type>) null, null));
    }

    //
    // Primary expressions
    //
    @Test
    public void testVisitBooleanLiteral() {
        assertNull(getVisitor().visit((BooleanLiteral<Type>) null, null));
    }

    @Test
    public void testVisitDateTimeLiteral() {
        assertNull(getVisitor().visit((DateTimeLiteral<Type>) null, null));
    }

    @Test
    public void testVisitNullLiteral() {
        assertNull(getVisitor().visit((NullLiteral<Type>) null, null));
    }

    @Test
    public void testVisitNumericLiteral() {
        assertNull(getVisitor().visit((NumericLiteral<Type>) null, null));
    }

    @Test
    public void testVisitStringLiteral() {
        assertNull(getVisitor().visit((StringLiteral<Type>) null, null));
    }

    @Test
    public void testVisitListLiteral() {
        assertNull(getVisitor().visit((ListLiteral<Type>) null, null));
    }

    @Test
    public void testVisitQualifiedName() {
        assertNull(getVisitor().visit((QualifiedName<Type>) null, null));
    }

    @Test
    public void testVisitName() {
        assertNull(getVisitor().visit((Name<Type>) null, null));
    }

    //
    // Type expressions
    //
    @Test
    public void testVisitNamedTypeExpression() {
        assertNull(getVisitor().visit((NamedTypeExpression<Type>) null, null));

    }

    @Test
    public void testVisitListTypeExpression() {
        assertNull(getVisitor().visit((ListTypeExpression<Type>) null, null));
    }

    @Test
    public void testVisitContextTypeExpression() {
        assertNull(getVisitor().visit((ContextTypeExpression<Type>) null, null));
    }

    @Test
    public void testVisitFunctionTypeExpression() {
        assertNull(getVisitor().visit((FunctionTypeExpression<Type>) null, null));
    }

    abstract protected Visitor<Type, DMNContext, Element<Type>> getVisitor();
}