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
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.test.UnaryTests;
import com.gs.dmn.runtime.DMNContext;

public class AbstractFEELProcessor {
    private final FEELAnalyzer<Type, DMNContext> feelAnalyzer;

    public AbstractFEELProcessor(FEELAnalyzer<Type, DMNContext> feelAnalyzer) {
        this.feelAnalyzer = feelAnalyzer;
    }

    public UnaryTests<Type, DMNContext> parseUnaryTests(String text) {
        return this.feelAnalyzer.parseUnaryTests(text);
    }

    public UnaryTests<Type, DMNContext> analyzeUnaryTests(String text, DMNContext context) {
        return this.feelAnalyzer.analyzeUnaryTests(text, context);
    }

    public Expression<Type, DMNContext> parseExpression(String text) {
        return this.feelAnalyzer.parseExpression(text);
    }

    public Expression<Type, DMNContext> parseTextualExpressions(String text) {
        return this.feelAnalyzer.parseTextualExpressions(text);
    }

    public Expression<Type, DMNContext> parseBoxedExpression(String text) {
        return this.feelAnalyzer.parseBoxedExpression(text);
    }

    public Expression<Type, DMNContext> analyzeExpression(String text, DMNContext context) {
        return this.feelAnalyzer.analyzeExpression(text, context);
    }

    public Expression<Type, DMNContext> analyzeTextualExpressions(String text, DMNContext context) {
        return this.feelAnalyzer.analyzeTextualExpressions(text, context);
    }

    public Expression<Type, DMNContext> analyzeBoxedExpression(String text, DMNContext context) {
        return this.feelAnalyzer.analyzeBoxedExpression(text, context);
    }
}
