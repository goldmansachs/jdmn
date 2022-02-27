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

public class NopVisitor<T, C> extends AbstractVisitor<T, C> {
    public NopVisitor() {
        super(NopErrorHandler.INSTANCE);
    }

    //
    // Tests
    //
    @Override
    public Object visit(PositiveUnaryTests<T, C> element, C context) {
        return element;
    }

    @Override
    public Object visit(NegatedPositiveUnaryTests<T, C> element, C context) {
        return element;
    }

    @Override
    public Object visit(Any<T, C> element, C context) {
        return element;
    }

    @Override
    public Object visit(NullTest<T, C> element, C context) {
        return element;
    }

    @Override
    public Object visit(ExpressionTest<T, C> element, C context) {
        return element;
    }

    @Override
    public Object visit(OperatorRange<T, C> element, C context) {
        return element;
    }

    @Override
    public Object visit(EndpointsRange<T, C> element, C context) {
        return element;
    }

    @Override
    public Object visit(ListTest<T, C> element, C context) {
        return element;
    }

    //
    // Textual expressions
    //
    @Override
    public Object visit(FunctionDefinition<T, C> element, C context) {
        return element;
    }

    @Override
    public Object visit(FormalParameter<T, C> element, C context) {
        return element;
    }

    @Override
    public Object visit(Context<T, C> element, C context) {
        return element;
    }

    @Override
    public Object visit(ContextEntry<T, C> element, C context) {
        return element;
    }

    @Override
    public Object visit(ContextEntryKey<T, C> element, C context) {
        return element;
    }

    @Override
    public Object visit(ForExpression<T, C> element, C context) {
        return element;
    }

    @Override
    public Object visit(Iterator<T, C> element, C context) {
        return element;
    }

    @Override
    public Object visit(ExpressionIteratorDomain<T, C> element, C context) {
        return element;
    }

    @Override
    public Object visit(RangeIteratorDomain<T, C> element, C context) {
        return element;
    }

    @Override
    public Object visit(IfExpression<T, C> element, C context) {
        return element;
    }

    @Override
    public Object visit(QuantifiedExpression<T, C> element, C context) {
        return element;
    }

    @Override
    public Object visit(FilterExpression<T, C> element, C context) {
        return element;
    }

    @Override
    public Object visit(InstanceOfExpression<T, C> element, C context) {
        return element;
    }

    //
    // Expressions
    //
    @Override
    public Object visit(ExpressionList<T, C> element, C context) {
        return element;
    }

    //
    // Logic expressions
    //
    @Override
    public Object visit(Conjunction<T, C> element, C context) {
        return element;
    }

    @Override
    public Object visit(Disjunction<T, C> element, C context) {
        return element;
    }

    @Override
    public Object visit(LogicNegation<T, C> element, C context) {
        return element;
    }

    //
    // Comparison expressions
    //
    @Override
    public Object visit(Relational<T, C> element, C context) {
        return element;
    }

    @Override
    public Object visit(BetweenExpression<T, C> element, C context) {
        return element;
    }

    @Override
    public Object visit(InExpression<T, C> element, C context) {
        return element;
    }

    //
    // Arithmetic expressions
    //
    @Override
    public Object visit(Addition<T, C> element, C context) {
        return element;
    }

    @Override
    public Object visit(Multiplication<T, C> element, C context) {
        return element;
    }

    @Override
    public Object visit(Exponentiation<T, C> element, C context) {
        return element;
    }

    @Override
    public Object visit(ArithmeticNegation<T, C> element, C context) {
        return element;
    }

    //
    // Postfix expressions
    //
    @Override
    public Object visit(PathExpression<T, C> element, C context) {
        return element;
    }

    @Override
    public Object visit(FunctionInvocation<T, C> element, C context) {
        return element;
    }

    @Override
    public Object visit(NamedParameters<T, C> element, C context) {
        return element;
    }

    @Override
    public Object visit(PositionalParameters<T, C> element, C context) {
        return element;
    }

    //
    // Primary expressions
    //
    @Override
    public Object visit(BooleanLiteral<T, C> element, C context) {
        return element;
    }

    @Override
    public Object visit(DateTimeLiteral<T, C> element, C context) {
        return element;
    }

    @Override
    public Object visit(NullLiteral<T, C> element, C context) {
        return element;
    }

    @Override
    public Object visit(NumericLiteral<T, C> element, C context) {
        return element;
    }

    @Override
    public Object visit(StringLiteral<T, C> element, C context) {
        return element;
    }

    @Override
    public Object visit(ListLiteral<T, C> element, C context) {
        return element;
    }

    @Override
    public Object visit(QualifiedName<T, C> element, C context) {
        return element;
    }

    @Override
    public Object visit(Name<T, C> element, C context) {
        return element;
    }

    //
    // Type expressions
    //
    @Override
    public Object visit(NamedTypeExpression<T, C> element, C context) {
        return element;
    }

    @Override
    public Object visit(ListTypeExpression<T, C> element, C context) {
        return element;
    }

    @Override
    public Object visit(ContextTypeExpression<T, C> element, C context) {
        return element;
    }

    @Override
    public Object visit(FunctionTypeExpression<T, C> element, C context) {
        return element;
    }
}
