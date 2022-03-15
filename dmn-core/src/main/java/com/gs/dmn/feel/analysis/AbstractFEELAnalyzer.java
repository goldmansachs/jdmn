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
package com.gs.dmn.feel.analysis;

import com.gs.dmn.context.DMNContext;
import com.gs.dmn.el.analysis.ELAnalyzer;
import com.gs.dmn.feel.analysis.semantics.FEELSemanticVisitor;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.test.UnaryTests;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;

abstract class AbstractFEELAnalyzer implements ELAnalyzer<Type, DMNContext> {
    private final BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer;

    protected AbstractFEELAnalyzer(BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer) {
        this.dmnTransformer = dmnTransformer;
    }

    @Override
    public UnaryTests<Type, DMNContext> analyzeUnaryTests(String text, DMNContext context) {
        UnaryTests<Type, DMNContext> unaryTests = (UnaryTests<Type, DMNContext>) parseUnaryTests(text);
        FEELSemanticVisitor visitor = new FEELSemanticVisitor(dmnTransformer);
        return (UnaryTests<Type, DMNContext>) unaryTests.accept(visitor, context);
    }

    @Override
    public Expression<Type, DMNContext> analyzeExpression(String text, DMNContext context) {
        Expression<Type, DMNContext> expression = (Expression<Type, DMNContext>) parseExpression(text);
        FEELSemanticVisitor visitor = new FEELSemanticVisitor(dmnTransformer);
        return (Expression<Type, DMNContext>) expression.accept(visitor, context);
    }

    @Override
    public Expression<Type, DMNContext> analyzeTextualExpressions(String text, DMNContext context) {
        Expression<Type, DMNContext> expression = (Expression<Type, DMNContext>) parseTextualExpressions(text);
        FEELSemanticVisitor visitor = new FEELSemanticVisitor(dmnTransformer);
        return (Expression<Type, DMNContext>) expression.accept(visitor, context);
    }

    @Override
    public Expression<Type, DMNContext> analyzeBoxedExpression(String text, DMNContext context) {
        Expression<Type, DMNContext> expression = (Expression<Type, DMNContext>) parseBoxedExpression(text);
        FEELSemanticVisitor visitor = new FEELSemanticVisitor(dmnTransformer);
        return (Expression<Type, DMNContext>) expression.accept(visitor, context);
    }
}
