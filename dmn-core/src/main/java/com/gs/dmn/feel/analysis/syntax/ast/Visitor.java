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
    Object visit(PositiveUnaryTests element, DMNContext params);

    Object visit(NegatedPositiveUnaryTests element, DMNContext params);

    Object visit(SimplePositiveUnaryTests element, DMNContext params);

    Object visit(NegatedSimplePositiveUnaryTests element, DMNContext params);

    Object visit(Any element, DMNContext params);

    Object visit(NullTest element, DMNContext params);

    Object visit(ExpressionTest element, DMNContext params);

    Object visit(OperatorTest element, DMNContext params);

    Object visit(RangeTest element, DMNContext params);

    Object visit(ListTest element, DMNContext params);

    //
    // Textual expressions
    //
    Object visit(FormalParameter element, DMNContext params);

    Object visit(FunctionDefinition element, DMNContext params);

    Object visit(Context element, DMNContext params);

    Object visit(ContextEntry element, DMNContext params);

    Object visit(ContextEntryKey element, DMNContext params);

    Object visit(ForExpression element, DMNContext params);

    Object visit(Iterator element, DMNContext params);

    Object visit(ExpressionIteratorDomain element, DMNContext params);

    Object visit(RangeIteratorDomain element, DMNContext params);

    Object visit(IfExpression element, DMNContext params);

    Object visit(QuantifiedExpression element, DMNContext params);

    Object visit(FilterExpression element, DMNContext params);

    Object visit(InstanceOfExpression element, DMNContext params);

    //
    // Expressions
    //
    Object visit(ExpressionList element, DMNContext params);

    //
    // Logic expressions
    //
    Object visit(Disjunction element, DMNContext params);

    Object visit(Conjunction element, DMNContext params);

    Object visit(LogicNegation element, DMNContext params);

    //
    // Comparison expressions
    //
    Object visit(Relational element, DMNContext params);

    Object visit(BetweenExpression element, DMNContext params);

    Object visit(InExpression element, DMNContext params);

    //
    // Arithmetic expressions
    //
    Object visit(Addition element, DMNContext params);

    Object visit(Multiplication element, DMNContext params);

    Object visit(Exponentiation element, DMNContext params);

    Object visit(ArithmeticNegation element, DMNContext params);

    //
    // Postfix expressions
    //
    Object visit(FunctionInvocation element, DMNContext params);

    Object visit(NamedParameters element, DMNContext params);

    Object visit(PositionalParameters element, DMNContext params);

    Object visit(PathExpression element, DMNContext params);

    //
    // Primary expressions
    //
    Object visit(BooleanLiteral element, DMNContext params);

    Object visit(DateTimeLiteral element, DMNContext params);

    Object visit(NullLiteral element, DMNContext params);

    Object visit(NumericLiteral element, DMNContext params);

    Object visit(StringLiteral element, DMNContext params);

    Object visit(ListLiteral element, DMNContext params);

    Object visit(QualifiedName element, DMNContext params);

    Object visit(Name element, DMNContext params);

    //
    // Type expressions
    //
    Object visit(NamedTypeExpression element, DMNContext params);

    Object visit(ListTypeExpression element, DMNContext params);

    Object visit(ContextTypeExpression element, DMNContext params);

    Object visit(FunctionTypeExpression element, DMNContext params);
}
