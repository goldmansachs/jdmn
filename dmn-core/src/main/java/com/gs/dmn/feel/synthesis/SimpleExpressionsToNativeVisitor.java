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
package com.gs.dmn.feel.synthesis;

import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.analysis.syntax.ast.expression.*;
import com.gs.dmn.feel.analysis.syntax.ast.expression.comparison.BetweenExpression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.comparison.InExpression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FunctionDefinition;
import com.gs.dmn.feel.analysis.syntax.ast.expression.logic.Conjunction;
import com.gs.dmn.feel.analysis.syntax.ast.expression.logic.Disjunction;
import com.gs.dmn.feel.analysis.syntax.ast.expression.logic.LogicNegation;
import com.gs.dmn.feel.analysis.syntax.ast.expression.textual.*;
import com.gs.dmn.feel.analysis.syntax.ast.test.*;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;

class SimpleExpressionsToNativeVisitor extends FEELToNativeVisitor {
    public SimpleExpressionsToNativeVisitor(BasicDMNToNativeTransformer dmnTransformer) {
        super(dmnTransformer);
    }

    //
    // Tests
    //
    @Override
    public Object visit(NegatedPositiveUnaryTests element, FEELContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(SimplePositiveUnaryTests element, FEELContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(NegatedSimplePositiveUnaryTests element, FEELContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(NullTest element, FEELContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(RangeTest element, FEELContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(ListTest element, FEELContext context) {
        return handleNotSupportedElement(element);
    }

    //
    // Textual expressions
    //
    @Override
    public Object visit(FunctionDefinition element, FEELContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(FormalParameter element, FEELContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(ForExpression element, FEELContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(Iterator element, FEELContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(ExpressionIteratorDomain element, FEELContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(RangeIteratorDomain element, FEELContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(IfExpression element, FEELContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(QuantifiedExpression element, FEELContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(FilterExpression element, FEELContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(InstanceOfExpression element, FEELContext context) {
        return handleNotSupportedElement(element);
    }

    //
    // Expressions
    //
    @Override
    public Object visit(ExpressionList element, FEELContext context) {
        return handleNotSupportedElement(element);
    }

    //
    // Logic functions
    //
    @Override
    public Object visit(Conjunction element, FEELContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(Disjunction element, FEELContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(LogicNegation element, FEELContext context) {
        return handleNotSupportedElement(element);
    }

    //
    // Comparison expressions
    //
    @Override
    public Object visit(BetweenExpression element, FEELContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(InExpression element, FEELContext context) {
        return handleNotSupportedElement(element);
    }
}
