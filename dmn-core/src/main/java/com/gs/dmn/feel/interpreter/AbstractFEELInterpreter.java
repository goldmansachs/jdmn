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

import com.gs.dmn.context.DMNContext;
import com.gs.dmn.el.analysis.ELAnalyzer;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.el.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.el.analysis.syntax.ast.test.UnaryTests;
import com.gs.dmn.el.interpreter.ELInterpreter;
import com.gs.dmn.feel.AbstractFEELProcessor;
import com.gs.dmn.runtime.interpreter.DMNInterpreter;
import com.gs.dmn.runtime.interpreter.Result;

public abstract class AbstractFEELInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends AbstractFEELProcessor<Type, DMNContext> implements ELInterpreter<Type, DMNContext> {
    private final AbstractFEELInterpreterVisitor<NUMBER, DATE, TIME, DATE_TIME, DURATION> visitor;

    protected AbstractFEELInterpreter(DMNInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> dmnInterpreter, ELAnalyzer<Type, DMNContext> feelAnalyzer) {
        super(feelAnalyzer);
        this.visitor = makeVisitor(dmnInterpreter);
    }

    @Override
    public Result evaluateUnaryTests(String text, DMNContext context) {
        UnaryTests<Type> expression = analyzeUnaryTests(text, context);
        return evaluateUnaryTests(expression, context);
    }

    @Override
    public Result evaluateUnaryTests(UnaryTests<Type> expression, DMNContext context) {
        Object value = ((com.gs.dmn.feel.analysis.syntax.ast.test.UnaryTests<Type, DMNContext>) expression).accept(visitor, context);
        return Result.of(value, expression.getType());
    }

    @Override
    public Result evaluateExpression(String text, DMNContext context) {
        Expression<Type> expression = analyzeExpression(text, context);
        return evaluateExpression(expression, context);
    }

    @Override
    public Result evaluateExpression(Expression<Type> expression, DMNContext context) {
        Object object = ((com.gs.dmn.feel.analysis.syntax.ast.expression.Expression<Type, DMNContext>) expression).accept(visitor, context);
        return Result.of(object, expression.getType());
    }

    protected abstract AbstractFEELInterpreterVisitor<NUMBER, DATE, TIME, DATE_TIME, DURATION> makeVisitor(DMNInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> dmnInterpreter);
}
