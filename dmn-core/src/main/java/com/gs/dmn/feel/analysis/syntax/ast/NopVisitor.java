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

import com.gs.dmn.error.NopErrorHandler;
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
import com.gs.dmn.feel.analysis.syntax.ast.expression.type.*;
import com.gs.dmn.feel.analysis.syntax.ast.test.*;

public class NopVisitor<T, C> extends AbstractVisitor<T, C, Element<T>> {
    public NopVisitor() {
        super(NopErrorHandler.INSTANCE);
    }

    //
    // Tests
    //
    @Override
    public Element<T> visit(PositiveUnaryTests<T> element, C context) {
        return element;
    }

    @Override
    public Element<T> visit(NegatedPositiveUnaryTests<T> element, C context) {
        return element;
    }

    @Override
    public Element<T> visit(Any<T> element, C context) {
        return element;
    }

    @Override
    public Element<T> visit(NullTest<T> element, C context) {
        return element;
    }

    @Override
    public Element<T> visit(ExpressionTest<T> element, C context) {
        return element;
    }

    @Override
    public Element<T> visit(OperatorRange<T> element, C context) {
        return element;
    }

    @Override
    public Element<T> visit(EndpointsRange<T> element, C context) {
        return element;
    }

    @Override
    public Element<T> visit(ListTest<T> element, C context) {
        return element;
    }

    //
    // Textual expressions
    //
    @Override
    public Element<T> visit(FunctionDefinition<T> element, C context) {
        return element;
    }

    @Override
    public Element<T> visit(FormalParameter<T> element, C context) {
        return element;
    }

    @Override
    public Element<T> visit(Context<T> element, C context) {
        return element;
    }

    @Override
    public Element<T> visit(ContextEntry<T> element, C context) {
        return element;
    }

    @Override
    public Element<T> visit(ContextEntryKey<T> element, C context) {
        return element;
    }

    @Override
    public Element<T> visit(ForExpression<T> element, C context) {
        return element;
    }

    @Override
    public Element<T> visit(Iterator<T> element, C context) {
        return element;
    }

    @Override
    public Element<T> visit(ExpressionIteratorDomain<T> element, C context) {
        return element;
    }

    @Override
    public Element<T> visit(RangeIteratorDomain<T> element, C context) {
        return element;
    }

    @Override
    public Element<T> visit(IfExpression<T> element, C context) {
        return element;
    }

    @Override
    public Element<T> visit(QuantifiedExpression<T> element, C context) {
        return element;
    }

    @Override
    public Element<T> visit(FilterExpression<T> element, C context) {
        return element;
    }

    @Override
    public Element<T> visit(InstanceOfExpression<T> element, C context) {
        return element;
    }

    //
    // Expressions
    //
    @Override
    public Element<T> visit(ExpressionList<T> element, C context) {
        return element;
    }

    //
    // Logic expressions
    //
    @Override
    public Element<T> visit(Conjunction<T> element, C context) {
        return element;
    }

    @Override
    public Element<T> visit(Disjunction<T> element, C context) {
        return element;
    }

    @Override
    public Element<T> visit(LogicNegation<T> element, C context) {
        return element;
    }

    //
    // Comparison expressions
    //
    @Override
    public Element<T> visit(Relational<T> element, C context) {
        return element;
    }

    @Override
    public Element<T> visit(BetweenExpression<T> element, C context) {
        return element;
    }

    @Override
    public Element<T> visit(InExpression<T> element, C context) {
        return element;
    }

    //
    // Arithmetic expressions
    //
    @Override
    public Element<T> visit(Addition<T> element, C context) {
        return element;
    }

    @Override
    public Element<T> visit(Multiplication<T> element, C context) {
        return element;
    }

    @Override
    public Element<T> visit(Exponentiation<T> element, C context) {
        return element;
    }

    @Override
    public Element<T> visit(ArithmeticNegation<T> element, C context) {
        return element;
    }

    //
    // Postfix expressions
    //
    @Override
    public Element<T> visit(PathExpression<T> element, C context) {
        return element;
    }

    @Override
    public Element<T> visit(FunctionInvocation<T> element, C context) {
        return element;
    }

    @Override
    public Element<T> visit(NamedParameters<T> element, C context) {
        return element;
    }

    @Override
    public Element<T> visit(PositionalParameters<T> element, C context) {
        return element;
    }

    //
    // Primary expressions
    //
    @Override
    public Element<T> visit(BooleanLiteral<T> element, C context) {
        return element;
    }

    @Override
    public Element<T> visit(DateTimeLiteral<T> element, C context) {
        return element;
    }

    @Override
    public Element<T> visit(NullLiteral<T> element, C context) {
        return element;
    }

    @Override
    public Element<T> visit(NumericLiteral<T> element, C context) {
        return element;
    }

    @Override
    public Element<T> visit(StringLiteral<T> element, C context) {
        return element;
    }

    @Override
    public Element<T> visit(ListLiteral<T> element, C context) {
        return element;
    }

    @Override
    public Element<T> visit(QualifiedName<T> element, C context) {
        return element;
    }

    @Override
    public Element<T> visit(Name<T> element, C context) {
        return element;
    }

    //
    // Type expressions
    //
    @Override
    public Element<T> visit(NamedTypeExpression<T> element, C context) {
        return element;
    }

    @Override
    public Element<T> visit(ContextTypeExpression<T> element, C context) {
        return element;
    }

    @Override
    public Element<T> visit(RangeTypeExpression<T> element, C context) {
        return element;
    }

    @Override
    public Element<T> visit(FunctionTypeExpression<T> element, C context) {
        return element;
    }

    @Override
    public Element<T> visit(ListTypeExpression<T> element, C context) {
        return element;
    }
}
