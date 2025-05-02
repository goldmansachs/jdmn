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
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ErrorListener;
import com.gs.dmn.feel.analysis.syntax.antlrv4.FEELLexer;
import com.gs.dmn.feel.analysis.syntax.antlrv4.FEELParser;
import com.gs.dmn.feel.analysis.syntax.ast.ASTFactory;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.test.UnaryTests;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.LinkedHashMap;
import java.util.Map;

public class FEELAnalyzerImpl extends AbstractFEELAnalyzer {
    private final Map<String, Expression<Type>> expressionMap = new LinkedHashMap<>();
    private final Map<String, UnaryTests<Type>> testsMap = new LinkedHashMap<>();

    public FEELAnalyzerImpl(BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer) {
        super(dmnTransformer);
    }

    @Override
    public UnaryTests<Type> parseUnaryTests(String text) {
        if (testsMap.get(text) == null) {
            FEELParser parser = makeParser(text);
            UnaryTests<Type> ast = parser.unaryTestsRoot().ast;

            this.testsMap.put(text, ast);
        }

        return testsMap.get(text);
    }

    @Override
    public Expression<Type> parseExpression(String text) {
        if (expressionMap.get(text) == null) {
            FEELParser parser = makeParser(text);
            Expression<Type> ast = parser.expressionRoot().ast;

            this.expressionMap.put(text, ast);
        }
        return expressionMap.get(text);
    }

    @Override
    public Expression<Type> parseTextualExpressions(String text) {
        if (expressionMap.get(text) == null) {
            FEELParser parser = makeParser(text);
            Expression<Type> ast = parser.textualExpressionsRoot().ast;

            this.expressionMap.put(text, ast);
        }
        return expressionMap.get(text);
    }

    @Override
    public Expression<Type> parseBoxedExpression(String text) {
        if (expressionMap.get(text) == null) {
            FEELParser parser = makeParser(text);
            Expression<Type> ast = parser.boxedExpressionRoot().ast;

            this.expressionMap.put(text, ast);
        }
        return expressionMap.get(text);
    }

    private FEELParser makeParser(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Input tape cannot be null.");
        }
        CharStream cs = CharStreams.fromString(text);
        FEELLexer lexer = new FEELLexer(cs);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        FEELParser feelParser = FEELParser.makeParser(tokens, new ASTFactory<Type, DMNContext>());
        feelParser.removeErrorListeners();
        feelParser.addErrorListener(new ErrorListener());
        return feelParser;
    }
}
