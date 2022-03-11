package com.gs.dmn.feel.analysis.semantics;

import com.gs.dmn.context.DMNContext;
import com.gs.dmn.error.NopErrorHandler;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ErrorListener;
import com.gs.dmn.feel.analysis.syntax.antlrv4.FEELLexer;
import com.gs.dmn.feel.analysis.syntax.antlrv4.FEELParser;
import com.gs.dmn.feel.analysis.syntax.ast.ASTFactory;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.test.UnaryTests;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class NamesVisitorTest {
    @Test
    public void getNames() {
        testExpression("function (a, b) a + b", Arrays.asList("a", "b"));
        testExpression("{ \"a\": a, \"b\": b}", Arrays.asList("a", "b"));
        testExpression("for i in 4..2 return i", Arrays.asList("i"));
        testExpression("if a then b else c", Arrays.asList("a", "b", "c"));
        testExpression("if a then b else c", Arrays.asList("a", "b", "c"));
        testExpression("every i in [1..2] j in [2..3] satisfies i + j > 1", Arrays.asList("i", "j"));
        testExpression("some i in [1..2] j in [2..3] satisfies i + j > 1", Arrays.asList("i", "j"));
        testExpression("DeptTable[number = EmployeeTable[name=LastName].deptNum[1]].manager[1]", Arrays.asList("DeptTable", "number", "EmployeeTable", "name", "LastName"));
        testExpression("a instance of number", Arrays.asList("a"));
        testExpression("[a, b, c]", Arrays.asList("a", "b", "c"));
        testExpression("not a or b and c", Arrays.asList("a", "b", "c"));
        testExpression("(a < b) or (b <= c) or (b > c) or (b >= c)", Arrays.asList("a", "b", "c"));
        testExpression("a between b and c", Arrays.asList("a", "b", "c"));
        testExpression("a in [b, c]", Arrays.asList("a", "b", "c"));
        testExpression(" a + b - c * d / e ** f", Arrays.asList("a", "b", "c", "d", "e", "f"));
        testExpression(" - a", Arrays.asList("a"));
        testExpression("a.b.c", Arrays.asList("a"));
        testExpression("a(b, c)", Arrays.asList("a", "b", "c"));
        testExpression("a(\"first\" = b, \"second\" = c)", Arrays.asList("a", "b", "c"));

        testUnaryTests("a, b", Arrays.asList("a", "b"));
        testUnaryTests("not (a)", Arrays.asList("a"));
        testUnaryTests("-", Arrays.asList());
        testUnaryTests("< a", Arrays.asList("a"));
        testUnaryTests("<= a", Arrays.asList("a"));
        testUnaryTests("> a", Arrays.asList("a"));
        testUnaryTests(">= a", Arrays.asList("a"));
    }

    private void testExpression(String text, List<String> expectedNames) {
        FEELParser parser = makeParser(text);
        Expression<Type, DMNContext> ast = parser.expressionRoot().ast;
        NamesVisitor<Type, DMNContext> visitor = new NamesVisitor<>(new NopErrorHandler());
        ast.accept(visitor, null);
        Set<String> actualNames = visitor.getNames();

        assertEquals(expectedNames, new ArrayList<>(actualNames));
    }

    private void testUnaryTests(String text, List<String> expectedNames) {
        FEELParser parser = makeParser(text);
        UnaryTests<Type, DMNContext> ast = parser.unaryTestsRoot().ast;
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