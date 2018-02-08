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

import com.gs.dmn.feel.analysis.syntax.antlrv4.FEELLexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Token;
import org.junit.Test;

import static com.gs.dmn.feel.analysis.syntax.antlrv4.FEELLexer.*;

public class FEELLexerTest extends AbstractLexerTest {
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
    public void testQuotedNames() {
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
        checkToken(" duration ", NAME, "duration");
        checkToken(" null ", NULL, "null");
        checkToken(" function ", FUNCTION, "function");
        checkToken(" external ", EXTERNAL, "external");
        checkToken(" for ", FOR, "for");
        checkToken(" in ", IN, "in");
        checkToken(" return ", RETURN, "return");
        checkToken(" if ", IF, "if");
        checkToken(" then ", THEN, "then");
        checkToken(" else ", ELSE, "else");
        checkToken(" some ", SOME, "some");
        checkToken(" every ", EVERY, "every");
        checkToken(" satisfies ", SATISFIES, "satisfies");
        checkToken(" and ", AND, "and");
        checkToken(" or ", OR, "or");
        checkToken(" between ", BETWEEN, "between");
        checkToken(" instance of ", INSTANCE_OF, "instance of");
    }

    @Test
    public void testOperators() {
        String text = "not - ** * / + < <= > >= = == !=";
        checkTokenList(
                text, new Integer[] {NOT, MINUS, STAR_STAR, STAR, FORWARD_SLASH, PLUS, LT, LE, GT, GE, EQ, EQ, NE},
                new String[] {"not", "-", "**", "*", "/", "+", "<", "<=", ">", ">=", "=", "==", "!="}
        );
    }

    @Test
    public void testPunctuation() {
        String text = "( ) [ ] { } . .. , :";
        checkTokenList(
                text, new Integer[] {
                        PAREN_OPEN, PAREN_CLOSE, BRACKET_OPEN, BRACKET_CLOSE,
                        BRACE_OPEN, BRACE_CLOSE, DOT, DOT_DOT, COMMA, COLON},
                new String[] {"(", ")", "[", "]", "{", "}", ".", "..", ",", ":"}
        );
    }

    @Test
    public void testComplex() {
        checkTokenList(
                "<1", new Integer[] {LT, NUMBER},
                new String[] {"<", "1"}
        );

        checkTokenList(false,
                "booleanA and booleanB or booleanA", new Integer[] {NAME, AND, NAME, OR, NAME},
                new String[] {"booleanA", "and", "booleanB", "or", "booleanA", "1"}
        );
    }

    @Test
    public void testNameAndKeywords() {
        String text = "true and true";
        checkTokenList(true,
                text, new Integer[] {TRUE, AND, TRUE},
                new String[] {"true", "and", "true"}
        );

        text = "true and BooleanInput";
        checkTokenList(true,
                text, new Integer[] {TRUE, AND, NAME},
                new String[] {"true", "and", "BooleanInput"}
        );

        text = "BooleanInput and true";
        checkTokenList(true,
                text, new Integer[] {NAME, AND, TRUE},
                new String[] {"BooleanInput", "and", "true"}
        );

        text = "3 between 1 and 4";
        checkTokenList(true,
                text, new Integer[] {NUMBER, BETWEEN, NUMBER, AND, NUMBER},
                new String[] {"3", "between", "1", "and", "4"}
        );

        text = "(i) between 1 and 2";
        checkTokenList(true,
                text, new Integer[] {PAREN_OPEN, NAME, PAREN_CLOSE, BETWEEN, NUMBER, AND, NUMBER},
                new String[] {"(", "i", ")", "between", "1", "and", "2"}
        );

        text = "1 in 1";
        checkTokenList(true,
                text, new Integer[] {NUMBER, IN, NUMBER},
                new String[] {"1", "in", "1"}
        );

        text = "i instance of number";
        checkTokenList(true,
                text, new Integer[] {NAME, INSTANCE_OF, NAME},
                new String[] {"i", "instance of", "number"}
        );
    }

    @Test
    public void testFunctionNames() {
        checkToken("date and time", NAME, "date and time");
        checkToken("years and months duration", NAME, "years and months duration");
        checkToken("days and time duration", NAME, "days and time duration");
        checkToken("string length", NAME, "string length");
        checkToken("upper case", NAME, "upper case");
        checkToken("lower case", NAME, "lower case");
        checkToken("substring before", NAME, "substring before");
        checkToken("substring after", NAME, "substring after");
        checkToken("starts with", NAME, "starts with");
        checkToken("ends with", NAME, "ends with");
        checkToken("list contains", NAME, "list contains");
        checkToken("insert before", NAME, "insert before");
        checkToken("index of", NAME, "index of");
        checkToken("distinct values", NAME, "distinct values");
    }

    @Test
    public void testForIterator() {
        String text = "for i in ";
        checkTokenList(false,
                text, new Integer[] {FOR, NAME, IN},
                new String[] {"for", "i", "in"}
        );
    }

    @Override
    protected Lexer makeLexer(String text) {
        CharStream cs = CharStreams.fromString(text);
        return new FEELLexer(cs);
    }

}
