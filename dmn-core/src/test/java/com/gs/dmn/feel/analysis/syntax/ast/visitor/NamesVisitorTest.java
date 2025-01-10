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
package com.gs.dmn.feel.analysis.syntax.ast.visitor;

import com.gs.dmn.context.DMNContext;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.error.NopErrorHandler;
import com.gs.dmn.feel.analysis.syntax.ErrorListener;
import com.gs.dmn.feel.analysis.syntax.antlrv4.FEELLexer;
import com.gs.dmn.feel.analysis.syntax.antlrv4.FEELParser;
import com.gs.dmn.feel.analysis.syntax.ast.ASTFactory;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.test.UnaryTests;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NamesVisitorTest {
    @Test
    public void getNames() {
        testExpression("function (a, b) a + b", Arrays.asList("a", "b"));
        testExpression("{ \"a\": a, \"b\": b}", Arrays.asList("a", "b"));
        testExpression("for i in 4..2 return i", Collections.singletonList("i"));
        testExpression("if a then b else c", Arrays.asList("a", "b", "c"));
        testExpression("if a then b else c", Arrays.asList("a", "b", "c"));
        testExpression("every i in [1..2] j in [2..3] satisfies i + j > 1", Arrays.asList("i", "j"));
        testExpression("some i in [1..2] j in [2..3] satisfies i + j > 1", Arrays.asList("i", "j"));
        testExpression("DeptTable[number = EmployeeTable[name=LastName].deptNum[1]].manager[1]", Arrays.asList("DeptTable", "number", "EmployeeTable", "name", "LastName"));
        testExpression("a instance of number", Collections.singletonList("a"));
        testExpression("[a, b, c]", Arrays.asList("a", "b", "c"));
        testExpression("not a or b and c", Arrays.asList("a", "b", "c"));
        testExpression("(a < b) or (b <= c) or (b > c) or (b >= c)", Arrays.asList("a", "b", "c"));
        testExpression("a between b and c", Arrays.asList("a", "b", "c"));
        testExpression("a in [b, c]", Arrays.asList("a", "b", "c"));
        testExpression(" a + b - c * d / e ** f", Arrays.asList("a", "b", "c", "d", "e", "f"));
        testExpression(" - a", Collections.singletonList("a"));
        testExpression("a.b.c", Collections.singletonList("a"));
        testExpression("a(b, c)", Arrays.asList("a", "b", "c"));
        testExpression("a(\"first\" = b, \"second\" = c)", Arrays.asList("a", "b", "c"));

        testUnaryTests("a, b", Arrays.asList("a", "b"));
        testUnaryTests("not (a)", Collections.singletonList("a"));
        testUnaryTests("-", Collections.emptyList());
        testUnaryTests("< a", Collections.singletonList("a"));
        testUnaryTests("<= a", Collections.singletonList("a"));
        testUnaryTests("> a", Collections.singletonList("a"));
        testUnaryTests(">= a", Collections.singletonList("a"));
    }

    private void testExpression(String text, List<String> expectedNames) {
        FEELParser parser = makeParser(text);
        Expression<Type> ast = parser.expressionRoot().ast;
        NamesVisitor<Type, DMNContext> visitor = new NamesVisitor<>(new NopErrorHandler());
        ast.accept(visitor, null);
        Set<String> actualNames = visitor.getNames();

        assertEquals(expectedNames, new ArrayList<>(actualNames));
    }

    private void testUnaryTests(String text, List<String> expectedNames) {
        FEELParser parser = makeParser(text);
        UnaryTests<Type> ast = parser.unaryTestsRoot().ast;
        NamesVisitor<Type, DMNContext> visitor = new NamesVisitor<>(new NopErrorHandler());
        ast.accept(visitor, null);
        Set<String> actualNames = visitor.getNames();

        assertEquals(expectedNames, new ArrayList<>(actualNames));
    }

    private FEELParser makeParser(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Input tape cannot be null.");
        }
        CharStream cs = CharStreams.fromString(text);
        FEELLexer lexer = new FEELLexer(cs);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        FEELParser feelParser = new FEELParser(tokens, new ASTFactory<Type, DMNContext>());
        feelParser.removeErrorListeners();
        feelParser.addErrorListener(new ErrorListener());
        return feelParser;
    }
}