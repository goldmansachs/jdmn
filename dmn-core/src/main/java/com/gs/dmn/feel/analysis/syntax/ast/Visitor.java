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
import com.gs.dmn.feel.analysis.syntax.ast.library.Library;
import com.gs.dmn.feel.analysis.syntax.ast.library.FunctionDeclaration;
import com.gs.dmn.feel.analysis.syntax.ast.test.*;

public interface Visitor<T, C, R> {
    //
    // Libraries
    //
    R visit(Library<T> element, C context);

    R visit(FunctionDeclaration<T> element, C context);

    //
    // Tests
    //
    R visit(PositiveUnaryTests<T> element, C context);

    R visit(NegatedPositiveUnaryTests<T> element, C context);

    R visit(Any<T> element, C context);

    R visit(ExpressionTest<T> element, C context);

    R visit(OperatorRange<T> element, C context);

    R visit(EndpointsRange<T> element, C context);

    R visit(ListTest<T> element, C context);

    //
    // Textual expressions
    //
    R visit(FormalParameter<T> element, C context);

    R visit(FunctionDefinition<T> element, C context);

    R visit(Context<T> element, C context);

    R visit(ContextEntry<T> element, C context);

    R visit(ContextEntryKey<T> element, C context);

    R visit(ForExpression<T> element, C context);

    R visit(Iterator<T> element, C context);

    R visit(ExpressionIteratorDomain<T> element, C context);

    R visit(RangeIteratorDomain<T> element, C context);

    R visit(IfExpression<T> element, C context);

    R visit(QuantifiedExpression<T> element, C context);

    R visit(FilterExpression<T> element, C context);

    R visit(InstanceOfExpression<T> element, C context);

    //
    // Expressions
    //
    R visit(ExpressionList<T> element, C context);

    //
    // Logic expressions
    //
    R visit(Disjunction<T> element, C context);

    R visit(Conjunction<T> element, C context);

    R visit(LogicNegation<T> element, C context);

    //
    // Comparison expressions
    //
    R visit(Relational<T> element, C context);

    R visit(BetweenExpression<T> element, C context);

    R visit(InExpression<T> element, C context);

    //
    // Arithmetic expressions
    //
    R visit(Addition<T> element, C context);

    R visit(Multiplication<T> element, C context);

    R visit(Exponentiation<T> element, C context);

    R visit(ArithmeticNegation<T> element, C context);

    //
    // Postfix expressions
    //
    R visit(FunctionInvocation<T> element, C context);

    R visit(NamedParameters<T> element, C context);

    R visit(PositionalParameters<T> element, C context);

    R visit(PathExpression<T> element, C context);

    //
    // Primary expressions
    //
    R visit(NumericLiteral<T> element, C context);

    R visit(StringLiteral<T> element, C context);

    R visit(BooleanLiteral<T> element, C context);

    R visit(DateTimeLiteral<T> element, C context);

    R visit(NullLiteral<T> element, C context);

    R visit(ListLiteral<T> element, C context);

    R visit(Name<T> element, C context);

    R visit(QualifiedName<T> element, C context);

    //
    // Type expressions
    //
    R visit(NamedTypeExpression<T> element, C context);

    R visit(ListTypeExpression<T> element, C context);

    R visit(RangeTypeExpression<T> element, C context);

    R visit(ContextTypeExpression<T> element, C context);

    R visit(FunctionTypeExpression<T> element, C context);
}
