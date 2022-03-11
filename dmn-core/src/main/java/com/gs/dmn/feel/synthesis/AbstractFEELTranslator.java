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

import com.gs.dmn.context.DMNContext;
import com.gs.dmn.feel.AbstractFEELProcessor;
import com.gs.dmn.feel.analysis.FEELAnalyzer;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.test.UnaryTests;

public abstract class AbstractFEELTranslator extends AbstractFEELProcessor<Type, DMNContext> implements FEELTranslator<Type, DMNContext> {
    private final FEELToNativeVisitor expressionVisitor;

    public AbstractFEELTranslator(FEELAnalyzer<Type, DMNContext> feelAnalyzer, FEELToNativeVisitor expressionVisitor) {
        super(feelAnalyzer);
        this.expressionVisitor = expressionVisitor;
    }

    @Override
    public String unaryTestsToJava(String text, DMNContext context) {
        UnaryTests<Type, DMNContext> unaryTests = analyzeUnaryTests(text, context);
        return unaryTestsToJava(unaryTests, context);
    }

    @Override
    public String unaryTestsToJava(UnaryTests<Type, DMNContext> expression, DMNContext context) {
        this.expressionVisitor.init();
        return (String) expression.accept(this.expressionVisitor, context);
    }

    @Override
    public String expressionToNative(String text, DMNContext context) {
        Expression<Type, DMNContext> expression = analyzeExpression(text, context);
        return expressionToNative(expression, context);
    }

    @Override
    public String expressionToNative(Expression<Type, DMNContext> expression, DMNContext context) {
        this.expressionVisitor.init();
        return (String) expression.accept(this.expressionVisitor, context);
    }
}
