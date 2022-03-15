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
package com.gs.dmn.feel;

import com.gs.dmn.el.analysis.ELAnalyzer;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.test.UnaryTests;

public class AbstractFEELProcessor<T, C> {
    private final ELAnalyzer<T, C> feelAnalyzer;

    public AbstractFEELProcessor(ELAnalyzer<T, C> feelAnalyzer) {
        this.feelAnalyzer = feelAnalyzer;
    }

    public UnaryTests<T, C> parseUnaryTests(String text) {
        return (UnaryTests<T, C>) this.feelAnalyzer.parseUnaryTests(text);
    }

    public UnaryTests<T, C> analyzeUnaryTests(String text, C context) {
        return (UnaryTests<T, C>) this.feelAnalyzer.analyzeUnaryTests(text, context);
    }

    public Expression<T, C> parseExpression(String text) {
        return (Expression<T, C>) this.feelAnalyzer.parseExpression(text);
    }

    public Expression<T, C> parseTextualExpressions(String text) {
        return (Expression<T, C>) this.feelAnalyzer.parseTextualExpressions(text);
    }

    public Expression<T, C> parseBoxedExpression(String text) {
        return (Expression<T, C>) this.feelAnalyzer.parseBoxedExpression(text);
    }

    public Expression<T, C> analyzeExpression(String text, C context) {
        return (Expression<T, C>) this.feelAnalyzer.analyzeExpression(text, context);
    }

    public Expression<T, C> analyzeTextualExpressions(String text, C context) {
        return (Expression<T, C>) this.feelAnalyzer.analyzeTextualExpressions(text, context);
    }

    public Expression<T, C> analyzeBoxedExpression(String text, C context) {
        return (Expression<T, C>) this.feelAnalyzer.analyzeBoxedExpression(text, context);
    }
}
