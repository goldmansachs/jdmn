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
package com.gs.dmn.feel.synthesis;

import com.gs.dmn.feel.analysis.FEELAnalyzer;
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.test.UnaryTests;

class AbstractFEELTranslator implements FEELTranslator {
    private final FEELAnalyzer feelAnalyzer;
    private final FEELToJavaVisitor expressionVisitor;
    private final SimpleExpressionsToJavaVisitor simpleExpressionsVisitor;

    public AbstractFEELTranslator(FEELAnalyzer feelAnalyzer, FEELToJavaVisitor expressionVisitor, SimpleExpressionsToJavaVisitor simpleExpressionsVisitor) {
        this.feelAnalyzer = feelAnalyzer;
        this.expressionVisitor = expressionVisitor;
        this.simpleExpressionsVisitor = simpleExpressionsVisitor;
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
    public String expressionToJava(String text, FEELContext context) {
        Expression expression = analyzeExpression(text, context);
        return expressionToJava(expression, context);
    }

    @Override
    public String expressionToJava(Expression expression, FEELContext context) {
        this.expressionVisitor.init();
        return (String) expression.accept(this.expressionVisitor, context);
    }

    @Override
    public String simpleExpressionsToJava(Expression simpleExpressions, FEELContext context) {
        this.simpleExpressionsVisitor.init();
        String javaOutputEntryText = (String) simpleExpressions.accept(this.simpleExpressionsVisitor, context);
        return "-".equals(javaOutputEntryText) ? "null" : javaOutputEntryText;
    }
}
