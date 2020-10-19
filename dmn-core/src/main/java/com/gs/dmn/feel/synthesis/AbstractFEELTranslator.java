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

import com.gs.dmn.feel.AbstractFEELProcessor;
import com.gs.dmn.feel.analysis.FEELAnalyzer;
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.test.UnaryTests;

class AbstractFEELTranslator extends AbstractFEELProcessor implements FEELTranslator {
    private final FEELToNativeVisitor expressionVisitor;
    private final SimpleExpressionsToNativeVisitor simpleExpressionsVisitor;

    public AbstractFEELTranslator(FEELAnalyzer feelAnalyzer, FEELToNativeVisitor expressionVisitor, SimpleExpressionsToNativeVisitor simpleExpressionsVisitor) {
        super(feelAnalyzer);
        this.expressionVisitor = expressionVisitor;
        this.simpleExpressionsVisitor = simpleExpressionsVisitor;
    }

    @Override
    public String unaryTestsToJava(String text, FEELContext context) {
        UnaryTests unaryTests = analyzeUnaryTests(text, context);
        return unaryTestsToJava(unaryTests, context);
    }

    @Override
    public String simpleUnaryTestsToJava(String text, FEELContext context) {
        UnaryTests unaryTests = analyzeSimpleUnaryTests(text, context);
        return simpleUnaryTestsToJava(unaryTests, context);
    }

    @Override
    public String unaryTestsToJava(UnaryTests expression, FEELContext context) {
        this.expressionVisitor.init();
        return (String) expression.accept(this.expressionVisitor, context);
    }

    @Override
    public String simpleUnaryTestsToJava(UnaryTests expression, FEELContext context) {
        return unaryTestsToJava(expression, context);
    }

    @Override
    public String expressionToNative(String text, FEELContext context) {
        Expression expression = analyzeExpression(text, context);
        return expressionToNative(expression, context);
    }

    @Override
    public String expressionToNative(Expression expression, FEELContext context) {
        this.expressionVisitor.init();
        return (String) expression.accept(this.expressionVisitor, context);
    }

    @Override
    public String simpleExpressionsToNative(Expression simpleExpressions, FEELContext context) {
        this.simpleExpressionsVisitor.init();
        String javaOutputEntryText = (String) simpleExpressions.accept(this.simpleExpressionsVisitor, context);
        return "-".equals(javaOutputEntryText) ? "null" : javaOutputEntryText;
    }
}
