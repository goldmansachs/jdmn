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

import com.gs.dmn.feel.analysis.syntax.ErrorListener;
import com.gs.dmn.feel.analysis.syntax.antlrv4.FEELLexer;
import com.gs.dmn.feel.analysis.syntax.antlrv4.FEELParser;
import com.gs.dmn.feel.analysis.syntax.ast.ASTFactory;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.test.UnaryTests;
import com.gs.dmn.transformation.basic.BasicDMN2JavaTransformer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class FEELAnalyzerImpl extends AbstractFEELAnalyzer {
    public FEELAnalyzerImpl(BasicDMN2JavaTransformer dmnTransformer) {
        super(dmnTransformer);
    }

    @Override
    public UnaryTests parseUnaryTests(String text) {
        FEELParser parser = makeParser(text);
        return parser.unaryTestsRoot().ast;
    }

    @Override
    public UnaryTests parseSimpleUnaryTests(String text) {
        FEELParser parser = makeParser(text);
        return parser.simpleUnaryTests().ast;
    }

    @Override
    public Expression parseExpression(String text) {
        FEELParser parser = makeParser(text);
        return parser.expressionRoot().ast;
    }

    @Override
    public Expression parseSimpleExpressions(String text) {
        FEELParser parser = makeParser(text);
        return parser.simpleExpressionsRoot().ast;
    }

    @Override
    public Expression parseTextualExpressions(String text) {
        FEELParser parser = makeParser(text);
        return parser.textualExpressionsRoot().ast;
    }

    @Override
    public Expression parseBoxedExpression(String text) {
        FEELParser parser = makeParser(text);
        return parser.boxedExpressionRoot().ast;
    }

    private FEELParser makeParser(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Input tape cannot be null.");
        }
        CharStream cs = CharStreams.fromString(text);
        FEELLexer lexer = new FEELLexer(cs);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        FEELParser feelParser = new FEELParser(tokens, new ASTFactory());
        feelParser.removeErrorListeners();
        feelParser.addErrorListener(new ErrorListener());
        return feelParser;
    }
}
