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

import com.gs.dmn.feel.analysis.syntax.ast.expression.ExpressionIteratorDomain;
import com.gs.dmn.feel.analysis.syntax.ast.expression.ExpressionList;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Iterator;
import com.gs.dmn.feel.analysis.syntax.ast.expression.RangeIteratorDomain;
import com.gs.dmn.feel.analysis.syntax.ast.expression.comparison.BetweenExpression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.comparison.InExpression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FunctionDefinition;
import com.gs.dmn.feel.analysis.syntax.ast.expression.logic.Conjunction;
import com.gs.dmn.feel.analysis.syntax.ast.expression.logic.Disjunction;
import com.gs.dmn.feel.analysis.syntax.ast.expression.logic.LogicNegation;
import com.gs.dmn.feel.analysis.syntax.ast.expression.textual.*;
import com.gs.dmn.feel.analysis.syntax.ast.test.*;
import com.gs.dmn.runtime.DMNContext;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;

class SimpleExpressionsToNativeVisitor extends FEELToNativeVisitor {
    public SimpleExpressionsToNativeVisitor(BasicDMNToNativeTransformer dmnTransformer) {
        super(dmnTransformer);
    }

    //
    // Tests
    //
    @Override
    public Object visit(NegatedPositiveUnaryTests element, DMNContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(SimplePositiveUnaryTests element, DMNContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(NegatedSimplePositiveUnaryTests element, DMNContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(NullTest element, DMNContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(RangeTest element, DMNContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(ListTest element, DMNContext context) {
        return handleNotSupportedElement(element);
    }

    //
    // Textual expressions
    //
    @Override
    public Object visit(FunctionDefinition element, DMNContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(FormalParameter element, DMNContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(ForExpression element, DMNContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(Iterator element, DMNContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(ExpressionIteratorDomain element, DMNContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(RangeIteratorDomain element, DMNContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(IfExpression element, DMNContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(QuantifiedExpression element, DMNContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(FilterExpression element, DMNContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(InstanceOfExpression element, DMNContext context) {
        return handleNotSupportedElement(element);
    }

    //
    // Expressions
    //
    @Override
    public Object visit(ExpressionList element, DMNContext context) {
        return handleNotSupportedElement(element);
    }

    //
    // Logic functions
    //
    @Override
    public Object visit(Conjunction element, DMNContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(Disjunction element, DMNContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(LogicNegation element, DMNContext context) {
        return handleNotSupportedElement(element);
    }

    //
    // Comparison expressions
    //
    @Override
    public Object visit(BetweenExpression element, DMNContext context) {
        return handleNotSupportedElement(element);
    }

    @Override
    public Object visit(InExpression element, DMNContext context) {
        return handleNotSupportedElement(element);
    }
}
