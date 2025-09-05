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

import com.gs.dmn.AbstractTest;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.ASTFactory;
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
import com.gs.dmn.feel.analysis.syntax.ast.library.FunctionDeclaration;
import com.gs.dmn.feel.analysis.syntax.ast.library.Library;
import com.gs.dmn.feel.analysis.syntax.ast.test.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public abstract class BaseVisitorTest extends AbstractTest {
    private final ASTFactory<Type, ?> factory = new ASTFactory<>();

    //
    // Libraries
    //
    @Test
    public void testVisitLibrary() {
        assertNull(getVisitor().visit((Library<Type>) null, null));
        assertNotNull(getVisitor().visit(factory.toLibrary(factory.toPathExpression(null, null), null, Collections.emptyList()), null));
    }

    @Test
    public void testVisitFunctionDeclaration() {
        assertNull(getVisitor().visit((FunctionDeclaration<Type>) null, null));
        assertNotNull(getVisitor().visit(factory.toFunctionDeclaration(null, null, null), null));
    }

    //
    // Tests
    //
    @Test
    public void testVisitPositiveUnaryTests() {
        assertNull(getVisitor().visit((PositiveUnaryTests<Type>) null, null));
        assertNotNull(getVisitor().visit(factory.toPositiveUnaryTests(null), null));
    }

    @Test
    public void testVisitNegatedPositiveUnaryTests() {
        assertNull(getVisitor().visit((NegatedPositiveUnaryTests<Type>) null, null));
        assertNotNull(getVisitor().visit((NegatedPositiveUnaryTests<Type>) factory.toNegatedUnaryTests(null), null));
    }

    @Test
    public void testVisitAny() {
        assertNull(getVisitor().visit((Any<Type>) null, null));
        assertNotNull(getVisitor().visit((Any<Type>) factory.toAny(), null));
    }

    @Test
    public void testVisitExpressionTest() {
        assertNull(getVisitor().visit((ExpressionTest<Type>) null, null));
        assertNotNull(getVisitor().visit(factory.toExpressionTest(null), null));
    }

    @Test
    public void testVisitOperatorRangeTest() {
        assertNull(getVisitor().visit((OperatorRange<Type>) null, null));
        assertNotNull(getVisitor().visit(factory.toOperatorRange(null, null), null));
    }

    @Test
    public void testVisitRangeTest() {
        assertNull(getVisitor().visit((EndpointsRange<Type>) null, null));
        assertNotNull(getVisitor().visit(factory.toEndpointsRange(null, null, null, null), null));
    }

    @Test
    public void testVisitListTest() {
        assertNull(getVisitor().visit((ListTest<Type>) null, null));
        assertNotNull(getVisitor().visit(factory.toListTest((ListLiteral<Type>) null), null));
    }

    //
    // Textual expressions
    //
    @Test
    public void testVisitFunctionDefinition() {
        assertNull(getVisitor().visit((FunctionDefinition<Type>) null, null));
        assertNotNull(getVisitor().visit((FunctionDefinition<Type>) factory.toFunctionDefinition(null, null, null, false), null));
    }

    @Test
    public void testVisitFormalParameter() {
        assertNull(getVisitor().visit((FormalParameter<Type>) null, null));
        assertNotNull(getVisitor().visit(factory.toFormalParameter(null, null, null), null));
    }

    @Test
    public void testVisitContext() {
        assertNull(getVisitor().visit((Context<Type>) null, null));
        assertNotNull(getVisitor().visit((Context<Type>) factory.toContext(null), null));
    }

    @Test
    public void testVisitContextEntry() {
        assertNull(getVisitor().visit((ContextEntry<Type>) null, null));
        assertNotNull(getVisitor().visit(factory.toContextEntry(null, null), null));
    }

    @Test
    public void testVisitContextEntryKey() {
        assertNull(getVisitor().visit((ContextEntryKey<Type>) null, null));
        assertNotNull(getVisitor().visit(factory.toContextEntryKey(null), null));
    }

    @Test
    public void testVisitForExpression() {
        assertNull(getVisitor().visit((ForExpression<Type>) null, null));
        assertNotNull(getVisitor().visit((ForExpression<Type>) factory.toForExpression(null, null), null));
    }

    @Test
    public void testVisitIterator() {
        assertNull(getVisitor().visit((Iterator<Type>) null, null));
        assertNotNull(getVisitor().visit(factory.toIterator(null, (IteratorDomain<Type>) null), null));
    }

    @Test
    public void testVisitExpressionIteratorDomain() {
        assertNull(getVisitor().visit((ExpressionIteratorDomain<Type>) null, null));
        assertNotNull(getVisitor().visit((ExpressionIteratorDomain<Type>)factory.toIteratorDomain(null, null), null));
    }

    @Test
    public void testVisitRangeIteratorDomain() {
        assertNull(getVisitor().visit((RangeIteratorDomain<Type>) null, null));
        assertNotNull(getVisitor().visit((RangeIteratorDomain<Type>) factory.toIteratorDomain(null, factory.toNumericLiteral("123")), null));
    }

    @Test
    public void testVisitIfExpression() {
        assertNull(getVisitor().visit((IfExpression<Type>) null, null));
        assertNotNull(getVisitor().visit(factory.toIfExpression(null, null, null), null));
    }

    @Test
    public void testVisitQuantifiedExpression() {
        assertNull(getVisitor().visit((QuantifiedExpression<Type>) null, null));
        assertNotNull(getVisitor().visit((QuantifiedExpression<Type>) factory.toQuantifiedExpression(null, null, null), null));
    }

    @Test
    public void testVisitFilterExpression() {
        assertNull(getVisitor().visit((FilterExpression<Type>) null, null));
        assertNotNull(getVisitor().visit((FilterExpression<Type>) factory.toFilterExpression(null, null), null));
    }

    @Test
    public void testVisitInstanceOfExpression() {
        assertNull(getVisitor().visit((InstanceOfExpression<Type>) null, null));
        assertNotNull(getVisitor().visit((InstanceOfExpression<Type>) factory.toInstanceOf(null, null), null));
    }

    //
    // Expressions
    //
    @Test
    public void testVisitExpressionList() {
        assertNull(getVisitor().visit((ExpressionList<Type>) null, null));
        assertNotNull(getVisitor().visit((ExpressionList<Type>) factory.toExpressionList(Collections.emptyList()), null));
    }

    //
    // Logic expressions
    //
    @Test
    public void testVisitConjunction() {
        assertNull(getVisitor().visit((Conjunction<Type>) null, null));
        assertNotNull(getVisitor().visit((Conjunction<Type>) factory.toConjunction(null, null), null));
    }

    @Test
    public void testVisitDisjunction() {
        assertNull(getVisitor().visit((Disjunction<Type>) null, null));
        assertNotNull(getVisitor().visit((Disjunction<Type>) factory.toDisjunction(null, null), null));
    }

    @Test
    public void testVisitLogicNegation() {
        assertNull(getVisitor().visit((LogicNegation<Type>) null, null));
        assertNotNull(getVisitor().visit((LogicNegation<Type>) factory.toNegation("not", null), null));
    }

    //
    // Comparison expressions
    //
    @Test
    public void testVisitRelational() {
        assertNull(getVisitor().visit((Relational<Type>) null, null));
        assertNotNull(getVisitor().visit((Relational<Type>) factory.toComparison(null, null, null), null));
    }

    @Test
    public void testVisitBetweenExpression() {
        assertNull(getVisitor().visit((BetweenExpression<Type>) null, null));
        assertNotNull(getVisitor().visit((BetweenExpression<Type>) factory.toBetweenExpression(null, null, null), null));
    }

    @Test
    public void testVisitInExpression() {
        assertNull(getVisitor().visit((InExpression<Type>) null, null));
        assertNotNull(getVisitor().visit((InExpression<Type>)factory.toInExpression(null, null), null));
    }

    //
    // Arithmetic expressions
    //
    @Test
    public void testVisitAddition() {
        assertNull(getVisitor().visit((Addition<Type>) null, null));
        assertNotNull(getVisitor().visit((Addition<Type>) factory.toAddition(null, null, null), null));
    }

    @Test
    public void testVisitMultiplication() {
        assertNull(getVisitor().visit((Multiplication<Type>) null, null));
        assertNotNull(getVisitor().visit((Multiplication<Type>) factory.toMultiplication(null, null, null), null));
    }

    @Test
    public void testVisitExponentiation() {
        assertNull(getVisitor().visit((Exponentiation<Type>) null, null));
        assertNotNull(getVisitor().visit((Exponentiation<Type>) factory.toExponentiation(null, null), null));
    }

    @Test
    public void testVisitArithmeticNegation() {
        assertNull(getVisitor().visit((ArithmeticNegation<Type>) null, null));
        assertNotNull(getVisitor().visit((ArithmeticNegation<Type>) factory.toNegation("-", null), null));
    }

    //
    // Postfix expressions
    //
    @Test
    public void testVisitPathExpression() {
        assertNull(getVisitor().visit((PathExpression<Type>) null, null));
        assertNotNull(getVisitor().visit((PathExpression<Type>) factory.toPathExpression(null, null), null));
    }

    @Test
    public void testVisitFunctionInvocation() {
        assertNull(getVisitor().visit((FunctionInvocation<Type>) null, null));
        assertNotNull(getVisitor().visit((FunctionInvocation<Type>) factory.toFunctionInvocation(null, null), null));
    }

    @Test
    public void testVisitNamedParameters() {
        assertNull(getVisitor().visit((NamedParameters<Type>) null, null));
        assertNotNull(getVisitor().visit(factory.toNamedParameters(null), null));
    }

    @Test
    public void testVisitPositionalParameters() {
        assertNull(getVisitor().visit((PositionalParameters<Type>) null, null));
        assertNotNull(getVisitor().visit(factory.toPositionalParameters(null), null));
    }

    //
    // Primary expressions
    //
    @Test
    public void testVisitBooleanLiteral() {
        assertNull(getVisitor().visit((BooleanLiteral<Type>) null, null));
        assertNotNull(getVisitor().visit(factory.toBooleanLiteral(null), null));
    }

    @Test
    public void testVisitDateTimeLiteral() {
        assertNull(getVisitor().visit((DateTimeLiteral<Type>) null, null));
        assertNotNull(getVisitor().visit((DateTimeLiteral<Type>)factory.toDateTimeLiteral(null), null));
    }

    @Test
    public void testVisitNullLiteral() {
        assertNull(getVisitor().visit((NullLiteral<Type>) null, null));
        assertNotNull(getVisitor().visit((NullLiteral<Type>) factory.toNullLiteral(), null));
    }

    @Test
    public void testVisitNumericLiteral() {
        assertNull(getVisitor().visit((NumericLiteral<Type>) null, null));
        assertNotNull(getVisitor().visit(factory.toNumericLiteral(null), null));
    }

    @Test
    public void testVisitStringLiteral() {
        assertNull(getVisitor().visit((StringLiteral<Type>) null, null));
        assertNotNull(getVisitor().visit(factory.toStringLiteral(null), null));
    }

    @Test
    public void testVisitListLiteral() {
        assertNull(getVisitor().visit((ListLiteral<Type>) null, null));
        assertNotNull(getVisitor().visit((ListLiteral<Type>) factory.toListLiteral(null), null));
    }

    @Test
    public void testVisitQualifiedName() {
        assertNull(getVisitor().visit((QualifiedName<Type>) null, null));
        assertNotNull(getVisitor().visit((PathExpression<Type>) factory.toQualifiedName(Arrays.asList("a", "b")), null));
    }

    @Test
    public void testVisitName() {
        assertNull(getVisitor().visit((Name<Type>) null, null));
        assertNotNull(getVisitor().visit((Name<Type>) factory.toName(null), null));
    }

    //
    // Type expressions
    //
    @Test
    public void testVisitNamedTypeExpression() {
        assertNull(getVisitor().visit((NamedTypeExpression<Type>) null, null));
        assertNotNull(getVisitor().visit((NamedTypeExpression<Type>) factory.toNamedTypeExpression((String) null), null));
    }

    @Test
    public void testVisitListTypeExpression() {
        assertNull(getVisitor().visit((ListTypeExpression<Type>) null, null));
        assertNotNull(getVisitor().visit((ListTypeExpression<Type>) factory.toListTypeExpression(null), null));
    }

    @Test
    public void testVisitContextTypeExpression() {
        assertNull(getVisitor().visit((ContextTypeExpression<Type>) null, null));
        assertNotNull(getVisitor().visit((ContextTypeExpression<Type>) factory.toContextTypeExpression(null), null));
    }

    @Test
    public void testVisitFunctionTypeExpression() {
        assertNull(getVisitor().visit((FunctionTypeExpression<Type>) null, null));
        assertNotNull(getVisitor().visit((FunctionTypeExpression<Type>) factory.toFunctionTypeExpression(null, null), null));
    }

    abstract protected Visitor<Type, DMNContext, Element<Type>> getVisitor();
}