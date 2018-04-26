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

public class NopVisitor extends AbstractVisitor {
    private ASTFactory astFactory = new ASTFactory();

    public NopVisitor() {
    }

    //
    // Tests
    //
    @Override
    public Object visit(PositiveUnaryTests element, FEELContext context) {
        return element;
    }

    @Override
    public Object visit(NegatedPositiveUnaryTests element, FEELContext context) {
        return element;
    }

    @Override
    public Object visit(SimplePositiveUnaryTests element, FEELContext context) {
        return element;
    }

    @Override
    public Object visit(NegatedSimplePositiveUnaryTests element, FEELContext context) {
        return element;
    }

    @Override
    public Object visit(Any element, FEELContext context) {
        return element;
    }

    @Override
    public Object visit(NullTest element, FEELContext context) {
        return element;
    }

    @Override
    public Object visit(ExpressionTest element, FEELContext context) {
        return element;
    }

    @Override
    public Object visit(OperatorTest element, FEELContext context) {
        return element;
    }

    @Override
    public Object visit(RangeTest element, FEELContext context) {
        return element;
    }

    @Override
    public Object visit(ListTest element, FEELContext context) {
        return element;
    }

    //
    // Textual expressions
    //
    @Override
    public Object visit(FunctionDefinition element, FEELContext context) {
        return element;
    }

    @Override
    public Object visit(FormalParameter element, FEELContext context) {
        return element;
    }

    @Override
    public Object visit(Context element, FEELContext context) {
        return element;
    }

    @Override
    public Object visit(ContextEntry element, FEELContext context) {
        return element;
    }

    @Override
    public Object visit(ContextEntryKey element, FEELContext context) {
        return element;
    }

    @Override
    public Object visit(ForExpression element, FEELContext context) {
        return element;
    }

    @Override
    public Object visit(Iterator element, FEELContext context) {
        return element;
    }

    @Override
    public Object visit(ExpressionIteratorDomain element, FEELContext context) {
        return element;
    }

    @Override
    public Object visit(RangeIteratorDomain element, FEELContext context) {
        return element;
    }

    @Override
    public Object visit(IfExpression element, FEELContext context) {
        return element;
    }

    @Override
    public Object visit(QuantifiedExpression element, FEELContext context) {
        return element;
    }

    @Override
    public Object visit(FilterExpression element, FEELContext context) {
        return element;
    }

    @Override
    public Object visit(InstanceOfExpression element, FEELContext context) {
        return element;
    }

    //
    // Expressions
    //
    @Override
    public Object visit(ExpressionList element, FEELContext context) {
        return element;
    }

    //
    // Logic expressions
    //
    @Override
    public Object visit(Conjunction element, FEELContext context) {
        return element;
    }

    @Override
    public Object visit(Disjunction element, FEELContext context) {
        return element;
    }

    @Override
    public Object visit(LogicNegation element, FEELContext context) {
        return element;
    }

    //
    // Comparison expressions
    //
    @Override
    public Object visit(Relational element, FEELContext context) {
        return element;
    }

    @Override
    public Object visit(BetweenExpression element, FEELContext context) {
        return element;
    }

    @Override
    public Object visit(InExpression element, FEELContext context) {
        return element;
    }

    //
    // Arithmetic expressions
    //
    @Override
    public Object visit(Addition element, FEELContext context) {
        return element;
    }

    @Override
    public Object visit(Multiplication element, FEELContext context) {
        return element;
    }

    @Override
    public Object visit(Exponentiation element, FEELContext context) {
        return element;
    }

    @Override
    public Object visit(ArithmeticNegation element, FEELContext context) {
        return element;
    }

    //
    // Postfix expressions
    //
    @Override
    public Object visit(PathExpression element, FEELContext context) {
        return element;
    }

    @Override
    public Object visit(FunctionInvocation element, FEELContext context) {
        return element;
    }

    @Override
    public Object visit(NamedParameters element, FEELContext context) {
        return element;
    }

    @Override
    public Object visit(PositionalParameters element, FEELContext context) {
        return element;
    }

    //
    // Primary expressions
    //
    @Override
    public Object visit(BooleanLiteral element, FEELContext context) {
        return element;
    }

    @Override
    public Object visit(DateTimeLiteral element, FEELContext context) {
        return element;
    }

    @Override
    public Object visit(NullLiteral element, FEELContext context) {
        return element;
    }

    @Override
    public Object visit(NumericLiteral element, FEELContext context) {
        return element;
    }

    @Override
    public Object visit(StringLiteral element, FEELContext context) {
        return element;
    }

    @Override
    public Object visit(ListLiteral element, FEELContext context) {
        return element;
    }

    @Override
    public Object visit(QualifiedName element, FEELContext context) {
        return element;
    }

    @Override
    public Object visit(Name element, FEELContext context) {
        return element;
    }
}
