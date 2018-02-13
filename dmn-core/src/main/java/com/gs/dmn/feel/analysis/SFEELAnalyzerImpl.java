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
import com.gs.dmn.feel.analysis.syntax.antlrv4.SFEELLexer;
import com.gs.dmn.feel.analysis.syntax.antlrv4.SFEELParser;
import com.gs.dmn.feel.analysis.syntax.ast.ASTFactory;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.test.UnaryTests;
import com.gs.dmn.transformation.basic.BasicDMN2JavaTransformer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class SFEELAnalyzerImpl extends AbstractFEELAnalyzer {
    public SFEELAnalyzerImpl(BasicDMN2JavaTransformer dmnTransformer) {
        super(dmnTransformer);
    }

    @Override
    public UnaryTests parseUnaryTests(String text) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public UnaryTests parseSimpleUnaryTests(String text) {
        SFEELParser parser = makeParser(text);
        return parser.simpleUnaryTestsRoot().ast;
    }

    @Override
    public Expression parseExpression(String text) {
        SFEELParser parser = makeParser(text);
        return parser.expressionRoot().ast;
    }

    @Override
    public Expression parseSimpleExpressions(String text) {
        SFEELParser parser = makeParser(text);
        return parser.simpleExpressionsRoot().ast;
    }

    @Override
    public Expression parseTextualExpressions(String text) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public Expression parseBoxedExpression(String text) {
        throw new UnsupportedOperationException("Not supported");
    }

    private SFEELParser makeParser(String text) {
        CharStream cs = CharStreams.fromString(text);
        SFEELLexer lexer = new SFEELLexer(cs);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SFEELParser sfeelParser = new SFEELParser(tokens, new ASTFactory());
        sfeelParser.removeErrorListeners();
        sfeelParser.addErrorListener(new ErrorListener());
        return sfeelParser;
    }
}
