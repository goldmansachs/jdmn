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
package com.gs.dmn.feel.analysis.syntax;

import com.gs.dmn.feel.analysis.syntax.antlrv4.SFEELLexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Token;
import org.junit.Test;

import static com.gs.dmn.feel.analysis.syntax.antlrv4.FEELLexer.NUMBER;
import static com.gs.dmn.feel.analysis.syntax.antlrv4.SFEELLexer.*;

public class SFEELLexerTest extends AbstractLexerTest {
    @Test
    public void testSpaces() {
        String verticalSpaces = "\n\u000B\u000C\r";
        String spaces = verticalSpaces + "\u0009\u0020\u0085\u00A0\u1680\u180E\u2000\u200B\u2028\u2029\u202F\u205F\u3000\uFEFF";
        Token token = checkToken(spaces + "1234", NUMBER, "1234");
        checkPosition(token, 2, 18, 2, 21, 18, 22);
    }

    @Test
    public void testComment() {
        Token token = checkToken("// abc\n1234", NUMBER, "1234");
        checkPosition(token, 2, 1, 2, 4, 7, 11);

        token = checkToken("/* comment */\n 1234", NUMBER, "1234");
        checkPosition(token, 2, 2, 2, 5, 15, 19);
    }

    @Test
    public void testNumber() {
        Token token = checkToken("1234", NUMBER, "1234");
        checkPosition(token, 1, 1, 1, 4, 0, 4);

        token = checkToken("1234.56", NUMBER, "1234.56");
        checkPosition(token, 1, 1, 1, 7, 0, 7);

        token = checkToken(".56", NUMBER, ".56");
        checkPosition(token, 1, 1, 1, 3, 0, 3);
    }

    @Test
    public void testString() {
        Token token = checkToken("\"abc\"", STRING, "\"abc\"");
        checkPosition(token, 1, 1, 1, 5, 0, 5);

        token = checkToken("\"a\u0030b\"", STRING, "\"a0b\"");
        checkPosition(token, 1, 1, 1, 5, 0, 5);

        token = checkToken("\".\"", STRING, "\".\"");
        checkPosition(token, 1, 1, 1, 3, 0, 3);

        token = checkToken("\".\"", STRING, "\".\"");
        checkPosition(token, 1, 1, 1, 3, 0, 3);

        token = checkToken("\"\\b\\t\\n\\r\\f\\'\\\"\"", STRING, "\"\\b\\t\\n\\r\\f\\'\\\"\"");
        checkPosition(token, 1, 1, 1, 16, 0, 16);
    }

    @Test
    public void testBoolean() {
        Token token = checkToken("true", TRUE, "true");
        checkPosition(token, 1, 1, 1, 4, 0, 4);

        token = checkToken("false", FALSE, "false");
        checkPosition(token, 1, 1, 1, 5, 0, 5);
    }

    @Test
    public void testSimpleName() {
        Token token = checkToken("ab_AB", NAME, "ab_AB");
        checkPosition(token, 1, 1, 1, 5, 0, 5);

        token = checkToken("ab12", NAME, "ab12");
        checkPosition(token, 1, 1, 1, 4, 0, 4);

        token = checkToken("ab_12_3", NAME, "ab_12_3");
        checkPosition(token, 1, 1, 1, 7, 0, 7);

        token = checkToken("?ab_12_", NAME, "?ab_12_");
        checkPosition(token, 1, 1, 1, 7, 0, 7);

        // Test with all the chars
        char[] sc1 = Character.toChars(0x10000);
        char[] sc2 = Character.toChars(0xEFFFF);
        String supplementaryChars = "" + sc1[0] + sc1[1] + sc2[0] + sc2[1];
        String name = "?AZ_az\u00C0\u00D6\u00D8\u00F6\u00F8\u02FF\u0370\u037D\u037F\u1FFF\u200C\u200D\u2070\u218F\u2C00\u2FEF\u3001\uD7FF\uF900\uFDCF\uFDF0\uFFFD" + supplementaryChars;
        token = checkToken(name, NAME, name);
        checkPosition(token, 1, 1, 1, 32, 0, 30);

        token = checkToken("date and time", NAME, "date and time");
        checkPosition(token, 1, 1, 1, 13, 0, 13);

        token = checkToken("years and months duration", NAME, "years and months duration");
        checkPosition(token, 1, 1, 1, 25, 0, 25);

        token = checkToken("days and time duration", NAME, "days and time duration");
        checkPosition(token, 1, 1, 1, 22, 0, 22);

        token = checkToken("string length", NAME, "string length");
        checkPosition(token, 1, 1, 1, 13, 0, 13);

        token = checkToken("upper case", NAME, "upper case");
        checkPosition(token, 1, 1, 1, 10, 0, 10);

        token = checkToken("lower case", NAME, "lower case");
        checkPosition(token, 1, 1, 1, 10, 0, 10);

        token = checkToken("substring before", NAME, "substring before");
        checkPosition(token, 1, 1, 1, 16, 0, 16);

        token = checkToken("substring after", NAME, "substring after");
        checkPosition(token, 1, 1, 1, 15, 0, 15);

        token = checkToken("starts with", NAME, "starts with");
        checkPosition(token, 1, 1, 1, 11, 0, 11);

        token = checkToken("ends with", NAME, "ends with");
        checkPosition(token, 1, 1, 1, 9, 0, 9);

        token = checkToken("list contains", NAME, "list contains");
        checkPosition(token, 1, 1, 1, 13, 0, 13);

        token = checkToken("insert before", NAME, "insert before");
        checkPosition(token, 1, 1, 1, 13, 0, 13);

        token = checkToken("index of", NAME, "index of");
        checkPosition(token, 1, 1, 1, 8, 0, 8);

        token = checkToken("distinct values", NAME, "distinct values");
        checkPosition(token, 1, 1, 1, 15, 0, 15);
    }

    @Test
    public void testQuotedName() {
        checkToken(" 'not' ", NAME, "'not'");
    }

    @Test
    public void testKeywords() {
        checkToken(" not ", NOT, "not");
        checkToken(" true ", TRUE, "true");
        checkToken(" false ", FALSE, "false");
        checkToken(" date ", NAME, "date");
        checkToken(" time ", NAME, "time");
        checkToken(" date and time ", NAME, "date and time");
    }

    @Test
    public void testOperators() {
        String text = "not - ** * / + < <= > >= = !=";
        checkTokenList(
                text, new Integer[] {NOT, MINUS, STAR_STAR, STAR, FORWARD_SLASH, PLUS, LT, LE, GT, GE, EQ, NE},
                new String[] {"not", "-", "**", "*", "/", "+", "<", "<=", ">", ">=", "=", "!="}
        );
    }

    @Test
    public void testPunctuation() {
        String text = "( ) [ ] . .. ,";
        checkTokenList(
                text, new Integer[] {PAREN_OPEN, PAREN_CLOSE, BRACKET_OPEN, BRACKET_CLOSE, DOT, DOT_DOT, COMMA},
                new String[] {"(", ")", "[", "]", ".", "..", ","}
        );
    }

    @Test
    public void testComplex() {
        String text = "<1";
        checkTokenList(
                text, new Integer[] {LT, NUMBER},
                new String[] {"<", "1"}
        );
    }

    @Test
    public void testNameAndKeywords() {
        String text = "true true";
        checkTokenList(
                text, new Integer[] {TRUE, TRUE},
                new String[] {"true", "true"}
        );
    }

    @Override
    protected Lexer makeLexer(String text) {
        CharStream cs = CharStreams.fromString(text);
        return new SFEELLexer(cs);
    }

}
