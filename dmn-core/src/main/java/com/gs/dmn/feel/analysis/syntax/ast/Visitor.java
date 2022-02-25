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

public interface Visitor<C> {
    //
    // Tests
    //
    Object visit(PositiveUnaryTests<C> element, C context);

    Object visit(NegatedPositiveUnaryTests<C> element, C context);

    Object visit(Any<C> element, C context);

    Object visit(NullTest<C> element, C context);

    Object visit(ExpressionTest<C> element, C context);

    Object visit(OperatorRange<C> element, C context);

    Object visit(EndpointsRange<C> element, C context);

    Object visit(ListTest<C> element, C context);

    //
    // Textual expressions
    //
    Object visit(FormalParameter<C> element, C context);

    Object visit(FunctionDefinition<C> element, C context);

    Object visit(Context<C> element, C context);

    Object visit(ContextEntry<C> element, C context);

    Object visit(ContextEntryKey<C> element, C context);

    Object visit(ForExpression<C> element, C context);

    Object visit(Iterator<C> element, C context);

    Object visit(ExpressionIteratorDomain<C> element, C context);

    Object visit(RangeIteratorDomain<C> element, C context);

    Object visit(IfExpression<C> element, C context);

    Object visit(QuantifiedExpression<C> element, C context);

    Object visit(FilterExpression<C> element, C context);

    Object visit(InstanceOfExpression<C> element, C context);

    //
    // Expressions
    //
    Object visit(ExpressionList<C> element, C context);

    //
    // Logic expressions
    //
    Object visit(Disjunction<C> element, C context);

    Object visit(Conjunction<C> element, C context);

    Object visit(LogicNegation<C> element, C context);

    //
    // Comparison expressions
    //
    Object visit(Relational<C> element, C context);

    Object visit(BetweenExpression<C> element, C context);

    Object visit(InExpression<C> element, C context);

    //
    // Arithmetic expressions
    //
    Object visit(Addition<C> element, C context);

    Object visit(Multiplication<C> element, C context);

    Object visit(Exponentiation<C> element, C context);

    Object visit(ArithmeticNegation<C> element, C context);

    //
    // Postfix expressions
    //
    Object visit(FunctionInvocation<C> element, C context);

    Object visit(NamedParameters<C> element, C context);

    Object visit(PositionalParameters<C> element, C context);

    Object visit(PathExpression<C> element, C context);

    //
    // Primary expressions
    //
    Object visit(BooleanLiteral<C> element, C context);

    Object visit(DateTimeLiteral<C> element, C context);

    Object visit(NullLiteral<C> element, C context);

    Object visit(NumericLiteral<C> element, C context);

    Object visit(StringLiteral<C> element, C context);

    Object visit(ListLiteral<C> element, C context);

    Object visit(QualifiedName<C> element, C context);

    Object visit(Name<C> element, C context);

    //
    // Type expressions
    //
    Object visit(NamedTypeExpression<C> element, C context);

    Object visit(ListTypeExpression<C> element, C context);

    Object visit(ContextTypeExpression<C> element, C context);

    Object visit(FunctionTypeExpression<C> element, C context);
}
