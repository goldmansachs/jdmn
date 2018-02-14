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

public class ContextDependentFEELLexerTest {
    private ContextDependentFEELLexer scanner;

    @Test
    public void testNumber() {
        LexicalContext lexicalContext = new LexicalContext();

        check(lexicalContext, "123", NUMBER, "123");
        check(lexicalContext, "123.345", NUMBER, "123.345");
        check(lexicalContext, ".345", NUMBER, ".345");
    }

    @Test
    public void testString() {
        LexicalContext lexicalContext = new LexicalContext();

        check(lexicalContext, "\"\"", STRING, "\"\"");
        check(lexicalContext, "\"123.345\"", STRING, "\"123.345\"");
    }

    @Test
    public void testNames() {
        LexicalContext lexicalContext = new LexicalContext();

        check(lexicalContext, "?abc_ABC0123456789", NAME, "?abc_ABC0123456789");
        check(lexicalContext, "a+b", NAME, "a+b");
        check(lexicalContext, "a-b", NAME, "a-b");
        check(lexicalContext, "a/b", NAME, "a/b");
        check(lexicalContext, "a*b", NAME, "a*b");
        check(lexicalContext, "a.b", NAME, "a.b");
        check(lexicalContext, "a'b", NAME, "a'b");

        check(lexicalContext, "a b c", NAME, "a b c");
    }

    @Test
    public void testKeywords() {
        LexicalContext lexicalContext = new LexicalContext();

        check(lexicalContext, "not", NOT, "not");
        check(lexicalContext, "true", TRUE, "true");
        check(lexicalContext, "false", FALSE, "false");
        check(lexicalContext, "null", NULL, "null");
        check(lexicalContext, "function", FUNCTION, "function");
        check(lexicalContext, "external", EXTERNAL, "external");
        check(lexicalContext, "for", FOR, "for");
        check(lexicalContext, "in", IN, "in");
        check(lexicalContext, "return", RETURN, "return");
        check(lexicalContext, "if", IF, "if");
        check(lexicalContext, "then", THEN, "then");
        check(lexicalContext, "else", ELSE, "else");
        check(lexicalContext, "some", SOME, "some");
        check(lexicalContext, "every", EVERY, "every");
        check(lexicalContext, "satisfies", SATISFIES, "satisfies");
        check(lexicalContext, "and", AND, "and");
        check(lexicalContext, "or", OR, "or");
        check(lexicalContext, "between", BETWEEN, "between");
        check(lexicalContext, "instance of", INSTANCE_OF, "instance of");
    }

    @Test
    public void testOperators() {
        LexicalContext lexicalContext = new LexicalContext();

        check(lexicalContext, "+", PLUS, "+");
        check(lexicalContext, "-", MINUS, "-");
        check(lexicalContext, "*", STAR, "*");
        check(lexicalContext, "/", FORWARD_SLASH, "/");
        check(lexicalContext, "**", STAR_STAR, "**");

        check(lexicalContext, "=", EQ, "=");
        check(lexicalContext, "!=", NE, "!=");
        check(lexicalContext, "<", LT, "<");
        check(lexicalContext, ">", GT, ">");
        check(lexicalContext, "<=", LE, "<=");
        check(lexicalContext, ">=", GE, ">=");
    }

    @Test
    public void testPunctuation() {
        LexicalContext lexicalContext = new LexicalContext();

        check(lexicalContext, "..", DOT_DOT, "..");
        check(lexicalContext, ".", DOT, ".");
        check(lexicalContext, "(", PAREN_OPEN, "(");
        check(lexicalContext, ")", PAREN_CLOSE, ")");
        check(lexicalContext, "[", BRACKET_OPEN, "[");
        check(lexicalContext, "]", BRACKET_CLOSE, "]");
        check(lexicalContext, "{", BRACE_OPEN, "{");
        check(lexicalContext, "}", BRACE_CLOSE, "}");
        check(lexicalContext, ":", COLON, ":");
    }

    @Test
    public void testSequencesWithEmptyContext() {
        LexicalContext lexicalContext = new LexicalContext();

        check(lexicalContext, "a + b", Arrays.asList(
                new CommonToken(NAME, "a + b"),
                new CommonToken(EOF, "")
        ));

        check(lexicalContext, "// line comment \n123", Arrays.asList(
                new CommonToken(NUMBER, "123"),
                new CommonToken(EOF, "")
        ));

        check(lexicalContext, "// line comment \n123", Arrays.asList(
                new CommonToken(NUMBER, "123"),
                new CommonToken(EOF, "")
        ));

        check(lexicalContext, "for i in ", Arrays.asList(
                new CommonToken(FOR, "for"),
                new CommonToken(NAME, "i in"),
                new CommonToken(EOF, "")
        ));
    }

    @Test
    public void testSequencesWithContext() {
        LexicalContext lexicalContext = new LexicalContext("a + b", "a", "b");

        check(lexicalContext, "a + b", Arrays.asList(
                new CommonToken(NAME, "a + b"),
                new CommonToken(EOF, "")
        ));

        check(lexicalContext, "a * b", Arrays.asList(
                new CommonToken(NAME, "a"), new CommonToken(STAR, "*"), new CommonToken(NAME, "b"),
                new CommonToken(EOF, "")
        ));

        check(lexicalContext, "(a) * b", Arrays.asList(
                new CommonToken(PAREN_OPEN, "("), new CommonToken(NAME, "a"), new CommonToken(PAREN_CLOSE, ")"), new CommonToken(STAR, "*"), new CommonToken(NAME, "b"),
                new CommonToken(EOF, "")
        ));

        check(lexicalContext, "for i in x ", Arrays.asList(
                new CommonToken(FOR, "for"), new CommonToken(NAME, "i in x"),
                new CommonToken(EOF, "")
        ));

        check(lexicalContext, "for (i) in x ", Arrays.asList(
                new CommonToken(FOR, "for"), new CommonToken(PAREN_OPEN, "("), new CommonToken(NAME, "i"), new CommonToken(PAREN_CLOSE, ")"), new CommonToken(IN, "in"), new CommonToken(NAME, "x"),
                new CommonToken(EOF, "")
        ));
    }

    @Test
    public void testComments() {
        LexicalContext lexicalContext = new LexicalContext();

        check(lexicalContext, "// line comment \n123", Arrays.asList(
                new CommonToken(NUMBER, "123"),
                new CommonToken(EOF, "")
        ));

        check(lexicalContext, "/* block comment */ 123", Arrays.asList(
                new CommonToken(NUMBER, "123"),
                new CommonToken(EOF, "")
        ));
    }

    @Test
    public void testBad() {
        LexicalContext lexicalContext = new LexicalContext();

        check(lexicalContext, "123.", ContextDependentFEELLexer.BAD, "123.");
        check(lexicalContext, "\"123", ContextDependentFEELLexer.BAD, "\"123");
    }

    private void check(LexicalContext lexicalContext, String input, Integer code, String lexeme) {
        check(lexicalContext, input, new CommonToken(code, lexeme));
    }

    private void check(LexicalContext lexicalContext, String input, Token expectedToken) {
        scanner = new ContextDependentFEELLexer(CharStreams.fromString(input));
        Token actualToken = scanner.nextToken(lexicalContext);
        checkToken(expectedToken, actualToken);
    }

    private void checkToken(Token expectedToken, Token actualToken) {
        assertEquals(expectedToken.getText(), actualToken.getText());
        assertEquals(expectedToken.getType(), actualToken.getType());
    }

    private void check(LexicalContext lexicalContext, String input, List<Token> expectedTokens) {
        scanner = new ContextDependentFEELLexer(CharStreams.fromString(input));
        Token actualToken = null;
        int i = 0;
        do {
            actualToken = scanner.nextToken(lexicalContext);
            checkToken(expectedTokens.get(i++), actualToken);
        } while (actualToken.getType() != EOF);
    }
}