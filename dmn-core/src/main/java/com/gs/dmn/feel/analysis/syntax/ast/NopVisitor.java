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
import com.gs.dmn.runtime.DMNContext;

public class NopVisitor extends AbstractVisitor {
    public NopVisitor() {
        super(NopErrorHandler.INSTANCE);
    }

    //
    // Tests
    //
    @Override
    public Object visit(PositiveUnaryTests element, DMNContext context) {
        return element;
    }

    @Override
    public Object visit(NegatedPositiveUnaryTests element, DMNContext context) {
        return element;
    }

    @Override
    public Object visit(SimplePositiveUnaryTests element, DMNContext context) {
        return element;
    }

    @Override
    public Object visit(NegatedSimplePositiveUnaryTests element, DMNContext context) {
        return element;
    }

    @Override
    public Object visit(Any element, DMNContext context) {
        return element;
    }

    @Override
    public Object visit(NullTest element, DMNContext context) {
        return element;
    }

    @Override
    public Object visit(ExpressionTest element, DMNContext context) {
        return element;
    }

    @Override
    public Object visit(OperatorTest element, DMNContext context) {
        return element;
    }

    @Override
    public Object visit(RangeTest element, DMNContext context) {
        return element;
    }

    @Override
    public Object visit(ListTest element, DMNContext context) {
        return element;
    }

    //
    // Textual expressions
    //
    @Override
    public Object visit(FunctionDefinition element, DMNContext context) {
        return element;
    }

    @Override
    public Object visit(FormalParameter element, DMNContext context) {
        return element;
    }

    @Override
    public Object visit(Context element, DMNContext context) {
        return element;
    }

    @Override
    public Object visit(ContextEntry element, DMNContext context) {
        return element;
    }

    @Override
    public Object visit(ContextEntryKey element, DMNContext context) {
        return element;
    }

    @Override
    public Object visit(ForExpression element, DMNContext context) {
        return element;
    }

    @Override
    public Object visit(Iterator element, DMNContext context) {
        return element;
    }

    @Override
    public Object visit(ExpressionIteratorDomain element, DMNContext context) {
        return element;
    }

    @Override
    public Object visit(RangeIteratorDomain element, DMNContext context) {
        return element;
    }

    @Override
    public Object visit(IfExpression element, DMNContext context) {
        return element;
    }

    @Override
    public Object visit(QuantifiedExpression element, DMNContext context) {
        return element;
    }

    @Override
    public Object visit(FilterExpression element, DMNContext context) {
        return element;
    }

    @Override
    public Object visit(InstanceOfExpression element, DMNContext context) {
        return element;
    }

    //
    // Expressions
    //
    @Override
    public Object visit(ExpressionList element, DMNContext context) {
        return element;
    }

    //
    // Logic expressions
    //
    @Override
    public Object visit(Conjunction element, DMNContext context) {
        return element;
    }

    @Override
    public Object visit(Disjunction element, DMNContext context) {
        return element;
    }

    @Override
    public Object visit(LogicNegation element, DMNContext context) {
        return element;
    }

    //
    // Comparison expressions
    //
    @Override
    public Object visit(Relational element, DMNContext context) {
        return element;
    }

    @Override
    public Object visit(BetweenExpression element, DMNContext context) {
        return element;
    }

    @Override
    public Object visit(InExpression element, DMNContext context) {
        return element;
    }

    //
    // Arithmetic expressions
    //
    @Override
    public Object visit(Addition element, DMNContext context) {
        return element;
    }

    @Override
    public Object visit(Multiplication element, DMNContext context) {
        return element;
    }

    @Override
    public Object visit(Exponentiation element, DMNContext context) {
        return element;
    }

    @Override
    public Object visit(ArithmeticNegation element, DMNContext context) {
        return element;
    }

    //
    // Postfix expressions
    //
    @Override
    public Object visit(PathExpression element, DMNContext context) {
        return element;
    }

    @Override
    public Object visit(FunctionInvocation element, DMNContext context) {
        return element;
    }

    @Override
    public Object visit(NamedParameters element, DMNContext context) {
        return element;
    }

    @Override
    public Object visit(PositionalParameters element, DMNContext context) {
        return element;
    }

    //
    // Primary expressions
    //
    @Override
    public Object visit(BooleanLiteral element, DMNContext context) {
        return element;
    }

    @Override
    public Object visit(DateTimeLiteral element, DMNContext context) {
        return element;
    }

    @Override
    public Object visit(NullLiteral element, DMNContext context) {
        return element;
    }

    @Override
    public Object visit(NumericLiteral element, DMNContext context) {
        return element;
    }

    @Override
    public Object visit(StringLiteral element, DMNContext context) {
        return element;
    }

    @Override
    public Object visit(ListLiteral element, DMNContext context) {
        return element;
    }

    @Override
    public Object visit(QualifiedName element, DMNContext context) {
        return element;
    }

    @Override
    public Object visit(Name element, DMNContext context) {
        return element;
    }

    //
    // Type expressions
    //
    @Override
    public Object visit(NamedTypeExpression element, DMNContext params) {
        return element;
    }

    @Override
    public Object visit(ListTypeExpression element, DMNContext params) {
        return element;
    }

    @Override
    public Object visit(ContextTypeExpression element, DMNContext params) {
        return element;
    }

    @Override
    public Object visit(FunctionTypeExpression element, DMNContext params) {
        return element;
    }
}
