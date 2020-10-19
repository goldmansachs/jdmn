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

import com.gs.dmn.feel.analysis.FEELAnalyzer;
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.test.UnaryTests;

public class AbstractFEELProcessor {
    private final FEELAnalyzer feelAnalyzer;

    public AbstractFEELProcessor(FEELAnalyzer feelAnalyzer) {
        this.feelAnalyzer = feelAnalyzer;
    }

    public UnaryTests parseUnaryTests(String text) {
        return this.feelAnalyzer.parseUnaryTests(text);
    }

    public UnaryTests parseSimpleUnaryTests(String text) {
        return this.feelAnalyzer.parseSimpleUnaryTests(text);
    }

    public UnaryTests analyzeUnaryTests(String text, FEELContext context) {
        return this.feelAnalyzer.analyzeUnaryTests(text, context);
    }

    public UnaryTests analyzeSimpleUnaryTests(String text, FEELContext context) {
        return this.feelAnalyzer.analyzeSimpleUnaryTests(text, context);
    }

    public Expression parseExpression(String text) {
        return this.feelAnalyzer.parseExpression(text);
    }

    public Expression parseSimpleExpressions(String text) {
        return this.feelAnalyzer.parseSimpleExpressions(text);
    }

    public Expression parseTextualExpressions(String text) {
        return this.feelAnalyzer.parseTextualExpressions(text);
    }

    public Expression parseBoxedExpression(String text) {
        return this.feelAnalyzer.parseBoxedExpression(text);
    }

    public Expression analyzeExpression(String text, FEELContext context) {
        return this.feelAnalyzer.analyzeExpression(text, context);
    }

    public Expression analyzeSimpleExpressions(String text, FEELContext context) {
        return this.feelAnalyzer.analyzeSimpleExpressions(text, context);
    }

    public Expression analyzeTextualExpressions(String text, FEELContext context) {
        return this.feelAnalyzer.analyzeTextualExpressions(text, context);
    }

    public Expression analyzeBoxedExpression(String text, FEELContext context) {
        return this.feelAnalyzer.analyzeBoxedExpression(text, context);
    }
}
