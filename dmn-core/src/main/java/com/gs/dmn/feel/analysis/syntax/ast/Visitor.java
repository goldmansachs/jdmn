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
import com.gs.dmn.runtime.DMNContext;

public interface Visitor {
    //
    // Tests
    //
    Object visit(PositiveUnaryTests element, DMNContext context);

    Object visit(NegatedPositiveUnaryTests element, DMNContext context);

    Object visit(SimplePositiveUnaryTests element, DMNContext context);

    Object visit(NegatedSimplePositiveUnaryTests element, DMNContext context);

    Object visit(Any element, DMNContext context);

    Object visit(NullTest element, DMNContext context);

    Object visit(ExpressionTest element, DMNContext context);

    Object visit(OperatorTest element, DMNContext context);

    Object visit(RangeTest element, DMNContext context);

    Object visit(ListTest element, DMNContext context);

    //
    // Textual expressions
    //
    Object visit(FormalParameter element, DMNContext context);

    Object visit(FunctionDefinition element, DMNContext context);

    Object visit(Context element, DMNContext context);

    Object visit(ContextEntry element, DMNContext context);

    Object visit(ContextEntryKey element, DMNContext context);

    Object visit(ForExpression element, DMNContext context);

    Object visit(Iterator element, DMNContext context);

    Object visit(ExpressionIteratorDomain element, DMNContext context);

    Object visit(RangeIteratorDomain element, DMNContext context);

    Object visit(IfExpression element, DMNContext context);

    Object visit(QuantifiedExpression element, DMNContext context);

    Object visit(FilterExpression element, DMNContext context);

    Object visit(InstanceOfExpression element, DMNContext context);

    //
    // Expressions
    //
    Object visit(ExpressionList element, DMNContext context);

    //
    // Logic expressions
    //
    Object visit(Disjunction element, DMNContext context);

    Object visit(Conjunction element, DMNContext context);

    Object visit(LogicNegation element, DMNContext context);

    //
    // Comparison expressions
    //
    Object visit(Relational element, DMNContext context);

    Object visit(BetweenExpression element, DMNContext context);

    Object visit(InExpression element, DMNContext context);

    //
    // Arithmetic expressions
    //
    Object visit(Addition element, DMNContext context);

    Object visit(Multiplication element, DMNContext context);

    Object visit(Exponentiation element, DMNContext context);

    Object visit(ArithmeticNegation element, DMNContext context);

    //
    // Postfix expressions
    //
    Object visit(FunctionInvocation element, DMNContext context);

    Object visit(NamedParameters element, DMNContext context);

    Object visit(PositionalParameters element, DMNContext context);

    Object visit(PathExpression element, DMNContext context);

    //
    // Primary expressions
    //
    Object visit(BooleanLiteral element, DMNContext context);

    Object visit(DateTimeLiteral element, DMNContext context);

    Object visit(NullLiteral element, DMNContext context);

    Object visit(NumericLiteral element, DMNContext context);

    Object visit(StringLiteral element, DMNContext context);

    Object visit(ListLiteral element, DMNContext context);

    Object visit(QualifiedName element, DMNContext context);

    Object visit(Name element, DMNContext context);

    //
    // Type expressions
    //
    Object visit(NamedTypeExpression element, DMNContext context);

    Object visit(ListTypeExpression element, DMNContext context);

    Object visit(ContextTypeExpression element, DMNContext context);

    Object visit(FunctionTypeExpression element, DMNContext context);
}
