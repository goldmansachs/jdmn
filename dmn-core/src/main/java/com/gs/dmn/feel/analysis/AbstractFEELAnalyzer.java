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
package com.gs.dmn.feel.analysis;

import com.gs.dmn.feel.analysis.semantics.FEELSemanticVisitor;
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.test.UnaryTests;
import com.gs.dmn.transformation.basic.BasicDMN2JavaTransformer;

abstract class AbstractFEELAnalyzer implements FEELAnalyzer {
    private final BasicDMN2JavaTransformer dmnTransformer;

    protected AbstractFEELAnalyzer(BasicDMN2JavaTransformer dmnTransformer) {
        this.dmnTransformer = dmnTransformer;
    }

    @Override
    public UnaryTests analyzeUnaryTests(String text, FEELContext context) {
        UnaryTests unaryTests = parseUnaryTests(text);
        FEELSemanticVisitor visitor = new FEELSemanticVisitor(dmnTransformer);
        return (UnaryTests) unaryTests.accept(visitor, context);
    }

    @Override
    public UnaryTests analyzeSimpleUnaryTests(String text, FEELContext context) {
        UnaryTests unaryTests = parseSimpleUnaryTests(text);
        FEELSemanticVisitor visitor = new FEELSemanticVisitor(dmnTransformer);
        return (UnaryTests) unaryTests.accept(visitor, context);
    }

    @Override
    public Expression analyzeExpression(String text, FEELContext context) {
        Expression expression = parseExpression(text);
        FEELSemanticVisitor visitor = new FEELSemanticVisitor(dmnTransformer);
        return (Expression) expression.accept(visitor, context);
    }

    @Override
    public Expression analyzeSimpleExpressions(String text, FEELContext context) {
        Expression expression = parseSimpleExpressions(text);
        FEELSemanticVisitor visitor = new FEELSemanticVisitor(dmnTransformer);
        return (Expression) expression.accept(visitor, context);
    }

    @Override
    public Expression analyzeTextualExpressions(String text, FEELContext context) {
        Expression expression = parseTextualExpressions(text);
        FEELSemanticVisitor visitor = new FEELSemanticVisitor(dmnTransformer);
        return (Expression) expression.accept(visitor, context);
    }

    @Override
    public Expression analyzeBoxedExpression(String text, FEELContext context) {
        Expression expression = parseBoxedExpression(text);
        FEELSemanticVisitor visitor = new FEELSemanticVisitor(dmnTransformer);
        return (Expression) expression.accept(visitor, context);
    }

    @Override
    public abstract UnaryTests parseUnaryTests(String text);

    @Override
    public abstract UnaryTests parseSimpleUnaryTests(String text);

    @Override
    public abstract Expression parseExpression(String text);

    @Override
    public abstract Expression parseSimpleExpressions(String text);

    @Override
    public abstract Expression parseTextualExpressions(String text);

    @Override
    public abstract Expression parseBoxedExpression(String text);
}
