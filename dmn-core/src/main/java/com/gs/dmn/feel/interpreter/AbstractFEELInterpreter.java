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
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.test.UnaryTests;
import com.gs.dmn.runtime.DMNContext;
import com.gs.dmn.runtime.interpreter.DMNInterpreter;
import com.gs.dmn.runtime.interpreter.Result;

abstract class AbstractFEELInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends AbstractFEELProcessor implements FEELInterpreter {
    private final FEELInterpreterVisitor<NUMBER, DATE, TIME, DATE_TIME, DURATION> visitor;

    protected AbstractFEELInterpreter(DMNInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> dmnInterpreter, FEELAnalyzer feelAnalyzer) {
        super(feelAnalyzer);
        this.visitor = new FEELInterpreterVisitor<>(dmnInterpreter);
    }

    @Override
    public Result evaluateUnaryTests(String text, DMNContext context) {
        UnaryTests expression = analyzeUnaryTests(text, context);
        return evaluateUnaryTests(expression, context);
    }

    @Override
    public Result evaluateUnaryTests(UnaryTests expression, DMNContext context) {
        Object value = expression.accept(visitor, context);
        return Result.of(value, expression.getType());
    }

    @Override
    public Result evaluateExpression(String text, DMNContext context) {
        Expression expression = analyzeExpression(text, context);
        return evaluateExpression(expression, context);
    }

    @Override
    public Result evaluateExpression(Expression expression, DMNContext context) {
        Object object = expression.accept(visitor, context);
        return Result.of(object, expression.getType());
    }
}
