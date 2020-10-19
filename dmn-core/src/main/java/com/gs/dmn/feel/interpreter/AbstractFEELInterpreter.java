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
package com.gs.dmn.feel.interpreter;

import com.gs.dmn.feel.AbstractFEELProcessor;
import com.gs.dmn.feel.analysis.FEELAnalyzer;
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.test.UnaryTests;
import com.gs.dmn.runtime.interpreter.DMNInterpreter;
import com.gs.dmn.runtime.interpreter.Result;

abstract class AbstractFEELInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends AbstractFEELProcessor implements FEELInterpreter {
    private final FEELInterpreterVisitor<NUMBER, DATE, TIME, DATE_TIME, DURATION> visitor;

    protected AbstractFEELInterpreter(DMNInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> dmnInterpreter, FEELAnalyzer feelAnalyzer) {
        super(feelAnalyzer);
        this.visitor = new FEELInterpreterVisitor<>(dmnInterpreter);
    }

    @Override
    public Result evaluateUnaryTests(String text, FEELContext context) {
        UnaryTests expression = analyzeUnaryTests(text, context);
        return evaluateUnaryTests(expression, context);
    }

    @Override
    public Result evaluateUnaryTests(UnaryTests expression, FEELContext context) {
        Object value = expression.accept(visitor, context);
        return new Result(value, expression.getType());
    }

    @Override
    public Result evaluateSimpleUnaryTests(String text, FEELContext context) {
        UnaryTests expression = analyzeSimpleUnaryTests(text, context);
        return evaluateUnaryTests(expression, context);
    }

    @Override
    public Result evaluateSimpleUnaryTests(UnaryTests expression, FEELContext context) {
        return evaluateUnaryTests(expression, context);
    }

    @Override
    public Result evaluateExpression(String text, FEELContext context) {
        Expression expression = analyzeExpression(text, context);
        return evaluateExpression(expression, context);
    }

    @Override
    public Result evaluateExpression(Expression expression, FEELContext context) {
        Object object = expression.accept(visitor, context);
        return new Result(object, expression.getType());
    }
}
