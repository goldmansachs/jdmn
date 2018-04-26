/**
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
import com.gs.dmn.feel.analysis.syntax.ast.test.*;

public interface Visitor {
    //
    // Tests
    //
    Object visit(PositiveUnaryTests element, FEELContext params);

    Object visit(NegatedPositiveUnaryTests element, FEELContext params);

    Object visit(SimplePositiveUnaryTests element, FEELContext params);

    Object visit(NegatedSimplePositiveUnaryTests element, FEELContext params);

    Object visit(Any element, FEELContext params);

    Object visit(NullTest element, FEELContext params);

    Object visit(ExpressionTest element, FEELContext params);

    Object visit(OperatorTest element, FEELContext params);

    Object visit(RangeTest element, FEELContext params);

    Object visit(ListTest element, FEELContext params);

    //
    // Textual expressions
    //
    Object visit(FormalParameter element, FEELContext params);

    Object visit(FunctionDefinition element, FEELContext params);

    Object visit(Context element, FEELContext params);

    Object visit(ContextEntry element, FEELContext params);

    Object visit(ContextEntryKey element, FEELContext params);

    Object visit(ForExpression element, FEELContext params);

    Object visit(Iterator element, FEELContext params);

    Object visit(ExpressionIteratorDomain element, FEELContext params);

    Object visit(RangeIteratorDomain element, FEELContext params);

    Object visit(IfExpression element, FEELContext params);

    Object visit(QuantifiedExpression element, FEELContext params);

    Object visit(FilterExpression element, FEELContext params);

    Object visit(InstanceOfExpression element, FEELContext params);

    //
    // Expressions
    //
    Object visit(ExpressionList element, FEELContext params);

    //
    // Logic expressions
    //
    Object visit(Disjunction element, FEELContext params);

    Object visit(Conjunction element, FEELContext params);

    Object visit(LogicNegation element, FEELContext params);

    //
    // Comparison expressions
    //
    Object visit(Relational element, FEELContext params);

    Object visit(BetweenExpression element, FEELContext params);

    Object visit(InExpression element, FEELContext params);

    //
    // Arithmetic expressions
    //
    Object visit(Addition element, FEELContext params);

    Object visit(Multiplication element, FEELContext params);

    Object visit(Exponentiation element, FEELContext params);

    Object visit(ArithmeticNegation element, FEELContext params);

    //
    // Postfix expressions
    //
    Object visit(FunctionInvocation element, FEELContext params);

    Object visit(NamedParameters element, FEELContext params);

    Object visit(PositionalParameters element, FEELContext params);

    Object visit(PathExpression element, FEELContext params);

    //
    // Primary expressions
    //
    Object visit(BooleanLiteral element, FEELContext params);

    Object visit(DateTimeLiteral element, FEELContext params);

    Object visit(NullLiteral element, FEELContext params);

    Object visit(NumericLiteral element, FEELContext params);

    Object visit(StringLiteral element, FEELContext params);

    Object visit(ListLiteral element, FEELContext params);

    Object visit(QualifiedName element, FEELContext params);

    Object visit(Name element, FEELContext params);
}
