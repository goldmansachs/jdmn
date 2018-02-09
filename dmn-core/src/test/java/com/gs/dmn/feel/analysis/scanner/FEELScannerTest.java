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
package com.gs.dmn.feel.analysis.scanner;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Token;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.gs.dmn.feel.analysis.syntax.antlrv4.FEELLexer.*;
import static org.junit.Assert.assertEquals;

public class FEELScannerTest {
    private FEELScanner scanner;

    @Test
    public void testNumber() {
        check("123", NUMBER, "123");
        check("123.345", NUMBER, "123.345");
        check(".345", NUMBER, ".345");
    }

    @Test
    public void testString() {
        check("\"\"", STRING, "\"\"");
        check("\"123.345\"", STRING, "\"123.345\"");
    }

    @Test
    public void testNames() {
        check("?abc_ABC0123456789", NAME, "?abc_ABC0123456789");
        check("a+b", NAME, "a+b");
        check("a-b", NAME, "a-b");
        check("a/b", NAME, "a/b");
        check("a*b", NAME, "a*b");
        check("a.b", NAME, "a.b");
        check("a'b", NAME, "a'b");

        check("a b c", NAME, "a b c");
    }

    @Test
    public void testKeywords() {
        check("not", NOT, "not");
        check("true", TRUE, "true");
        check("false", FALSE, "false");
        check("null", NULL, "null");
        check("function", FUNCTION, "function");
        check("external", EXTERNAL, "external");
        check("for", FOR, "for");
        check("in", IN, "in");
        check("return", RETURN, "return");
        check("if", IF, "if");
        check("then", THEN, "then");
        check("else", ELSE, "else");
        check("some", SOME, "some");
        check("every", EVERY, "every");
        check("satisfies", SATISFIES, "satisfies");
        check("and", AND, "and");
        check("or", OR, "or");
        check("between", BETWEEN, "between");
        check("instance of", INSTANCE_OF, "instance of");
    }

    @Test
    public void testOperators() {
        check("+", PLUS, "+");
        check("-", MINUS, "-");
        check("*", STAR, "*");
        check("/", FORWARD_SLASH, "/");
        check("**", STAR_STAR, "**");

        check("=", EQ, "=");
        check("!=", NE, "!=");
        check("<", LT, "<");
        check(">", GT, ">");
        check("<=", LE, "<=");
        check(">=", GE, ">=");
    }

    @Test
    public void testPunctuation() {
        check("..", DOT_DOT, "..");
        check(".", DOT, ".");
        check("(", PAREN_OPEN, "(");
        check(")", PAREN_CLOSE, ")");
        check("[", BRACKET_OPEN, "[");
        check("]", BRACKET_CLOSE, "]");
        check("{", BRACE_OPEN, "{");
        check("}", BRACE_CLOSE, "}");
        check(":", COLON, ":");
    }

    @Test
    public void testSequences() {
        check("a + b", Arrays.asList(
                new CommonToken(NAME, "a"), new CommonToken(PLUS, "+"), new CommonToken(NAME, "b"),
                new CommonToken(EOF, "")
        ));

        check("// line comment \n123", Arrays.asList(
                new CommonToken(NUMBER, "123"),
                new CommonToken(EOF, "")
        ));

        check("// line comment \n123", Arrays.asList(
                new CommonToken(NUMBER, "123"),
                new CommonToken(EOF, "")
        ));

        check("for i in ", Arrays.asList(
                new CommonToken(FOR, "for"),
                new CommonToken(NAME, "i"),
                new CommonToken(IN, "in"),
                new CommonToken(EOF, "")
        ));
    }

    @Test
    public void testComments() {
        check("// line comment \n123", Arrays.asList(
                new CommonToken(NUMBER, "123"),
                new CommonToken(EOF, "")
        ));

        check("/* block comment */ 123", Arrays.asList(
                new CommonToken(NUMBER, "123"),
                new CommonToken(EOF, "")
        ));
    }

    @Test
    public void testBad() {
        check("123.", FEELScanner.BAD, "123.");
        check("\"123", FEELScanner.BAD, "\"123");
    }

    private void check(String input, Integer code, String lexeme) {
        check(input, new CommonToken(code, lexeme));
    }

    private void check(String input, Token expectedToken) {
        scanner = new FEELScanner(CharStreams.fromString(input));
        Token actualToken = scanner.nextToken();
        checkToken(expectedToken, actualToken);
    }

    private void checkToken(Token expectedToken, Token actualToken) {
        assertEquals(expectedToken.getText(), actualToken.getText());
        assertEquals(expectedToken.getType(), actualToken.getType());
    }

    private void check(String input, List<Token> expectedTokens) {
        scanner = new FEELScanner(CharStreams.fromString(input));
        Token actualToken = null;
        int i = 0;
        do {
            actualToken = scanner.nextToken();
            checkToken(expectedTokens.get(i++), actualToken);
        } while (actualToken.getType() != EOF);
    }
}