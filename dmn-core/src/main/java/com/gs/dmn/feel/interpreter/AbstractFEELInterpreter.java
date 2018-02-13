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
package com.gs.dmn.feel.interpreter;

import com.gs.dmn.feel.analysis.FEELAnalyzer;
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.test.UnaryTests;
import com.gs.dmn.runtime.interpreter.DMNInterpreter;

abstract class AbstractFEELInterpreter implements FEELInterpreter {
    private final FEELAnalyzer feelAnalyzer;
    private final FEELInterpreterVisitor visitor;

    protected AbstractFEELInterpreter(DMNInterpreter dmnInterpreter, FEELAnalyzer feelAnalyzer) {
        this.feelAnalyzer = feelAnalyzer;
        this.visitor = new FEELInterpreterVisitor(dmnInterpreter);
    }

    @Override
    public UnaryTests parseUnaryTests(String text) {
        return feelAnalyzer.parseUnaryTests(text);
    }

    @Override
    public UnaryTests parseSimpleUnaryTests(String text) {
        return feelAnalyzer.parseSimpleUnaryTests(text);
    }

    @Override
    public UnaryTests analyzeUnaryTests(String text, FEELContext context) {
        return feelAnalyzer.analyzeUnaryTests(text, context);
    }

    @Override
    public UnaryTests analyzeSimpleUnaryTests(String text, FEELContext context) {
        return feelAnalyzer.analyzeSimpleUnaryTests(text, context);
    }

    @Override
    public Expression parseExpression(String text) {
        return feelAnalyzer.parseExpression(text);
    }

    @Override
    public Expression parseSimpleExpressions(String text) {
        return feelAnalyzer.parseSimpleExpressions(text);
    }

    @Override
    public Expression parseTextualExpressions(String text) {
        return feelAnalyzer.parseTextualExpressions(text);
    }

    @Override
    public Expression parseBoxedExpression(String text) {
        return feelAnalyzer.parseBoxedExpression(text);
    }

    @Override
    public Expression analyzeExpression(String text, FEELContext context) {
        return feelAnalyzer.analyzeExpression(text, context);
    }

    @Override
    public Expression analyzeSimpleExpressions(String text, FEELContext context) {
        return feelAnalyzer.analyzeSimpleExpressions(text, context);
    }

    @Override
    public Expression analyzeTextualExpressions(String text, FEELContext context) {
        return feelAnalyzer.analyzeTextualExpressions(text, context);
    }

    @Override
    public Expression analyzeBoxedExpression(String text, FEELContext context) {
        return feelAnalyzer.analyzeBoxedExpression(text, context);
    }

    @Override
    public Object evaluateUnaryTests(String text, FEELContext context) {
        UnaryTests expression = analyzeUnaryTests(text, context);
        return evaluateUnaryTests(expression, context);
    }

    @Override
    public Object evaluateUnaryTests(UnaryTests expression, FEELContext context) {
        return expression.accept(visitor, context);
    }

    @Override
    public Object evaluateSimpleUnaryTests(String text, FEELContext context) {
        UnaryTests expression = analyzeSimpleUnaryTests(text, context);
        return evaluateUnaryTests(expression, context);
    }

    @Override
    public Object evaluateSimpleUnaryTests(UnaryTests expression, FEELContext context) {
        return evaluateUnaryTests(expression, context);
    }

    @Override
    public Object evaluateExpression(String text, FEELContext context) {
        Expression expression = analyzeExpression(text, context);
        return evaluateExpression(expression, context);
    }

    @Override
    public Object evaluateExpression(Expression expression, FEELContext context) {
        return expression.accept(visitor, context);
    }
}
