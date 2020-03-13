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
package com.gs.dmn.fitnesse.fixture.feel;

import com.gs.dmn.feel.analysis.semantics.environment.Environment;
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.test.UnaryTests;
import com.gs.dmn.runtime.interpreter.Result;
import com.gs.dmn.runtime.interpreter.environment.RuntimeEnvironment;
import com.gs.dmn.transformation.DMNToJavaTransformer;
import org.omg.spec.dmn._20180521.model.TNamedElement;

public class FEELSimpleUnaryTestsFixture extends FEELFixture {
    private String inputExpression;
    private String inputEntry;

    public FEELSimpleUnaryTestsFixture() {
        super();
    }

    public void setInputExpression(String inputExpression) {
        this.inputExpression = inputExpression;
    }

    public void setInputEntry(String inputEntry) {
        this.inputEntry = inputEntry;
    }

    public Object output() {
        // Analyze input expression
        Environment inputExpressionEnvironment = makeEnvironment(this.scope);
        RuntimeEnvironment runtimeEnvironment = makeRuntimeEnvironment(this.scope);
        FEELContext inputExpressionContext = FEELContext.makeContext(getElement(), inputExpressionEnvironment, runtimeEnvironment);
        Expression inputExpression = this.feelInterpreter.analyzeSimpleExpressions(this.inputExpression, inputExpressionContext);

        // Analyze input entry
        Environment inputEntryEnvironment = makeInputEntryEnvironment(inputExpressionEnvironment, inputExpression);
        FEELContext inputEntryContext = FEELContext.makeContext(getElement(), inputEntryEnvironment, runtimeEnvironment);
        UnaryTests inputEntryTest = this.feelInterpreter.analyzeSimpleUnaryTests(this.inputEntry, inputEntryContext);

        // Evaluate input expression
        Result inputExpressionResult = this.feelInterpreter.evaluateExpression(inputExpression, inputExpressionContext);
        Object inputExpressionValue = Result.value(inputExpressionResult);

        // Evaluate input entry
        inputEntryContext.runtimeBind(DMNToJavaTransformer.INPUT_ENTRY_PLACE_HOLDER, inputExpressionValue);
        Result result = this.feelInterpreter.evaluateUnaryTests(inputEntryTest, inputEntryContext);
        return Result.value(result);
    }

    private TNamedElement getElement() {
        return null;
    }
}
