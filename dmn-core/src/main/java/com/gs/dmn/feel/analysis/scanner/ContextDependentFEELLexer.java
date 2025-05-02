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
package com.gs.dmn.feel.analysis.scanner;

import com.gs.dmn.runtime.Pair;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.IntStream;
import org.antlr.v4.runtime.Token;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.gs.dmn.feel.analysis.syntax.antlrv4.FEELLexer.*;

public class ContextDependentFEELLexer {
    public static final int BAD = 0;
    public static final Map<String, Integer> KEYWORDS = new LinkedHashMap<>();
    static {
        KEYWORDS.put("instance of", INSTANCE_OF);
        KEYWORDS.put("not", NOT);
        KEYWORDS.put("true", TRUE);
        KEYWORDS.put("false", FALSE);
        KEYWORDS.put("null", NULL);
        KEYWORDS.put("function", FUNCTION);
        KEYWORDS.put("external", EXTERNAL);
        KEYWORDS.put("for", FOR);
        KEYWORDS.put("in", IN);
        KEYWORDS.put("return", RETURN);
        KEYWORDS.put("if", IF);
        KEYWORDS.put("then", THEN);
        KEYWORDS.put("else", ELSE);
        KEYWORDS.put("some", SOME);
        KEYWORDS.put("every", EVERY);
        KEYWORDS.put("satisfies", SATISFIES);
        KEYWORDS.put("and", AND);
        KEYWORDS.put("or", OR);
        KEYWORDS.put("between", BETWEEN);
    }

    private final CharStream inputTape;

    public ContextDependentFEELLexer(CharStream inputTape) {
        this.inputTape = inputTape;
    }

    public Pair<String, Token> nextToken(LexicalContext lexicalContext) {
        int code = BAD;
        StringBuilder lexeme = new StringBuilder();

        int ch = currentChar(inputTape);
        if (ch == IntStream.EOF) {
            code = EOF;
        }

        // Skip spaces and comments
        StringBuilder spacesBeforeBuilder = new StringBuilder();
        while (isWhiteSpace(ch) || isBeginLineComment(ch) || isBeginBlockComment(ch)) {
            if (isWhiteSpace(ch)) {
                while (isWhiteSpace(ch)) {
                    spacesBeforeBuilder.appendCodePoint(ch);
                    ch = nextChar(inputTape);
                }
            } else if (isBeginLineComment(ch)) {
                do {
                    spacesBeforeBuilder.appendCodePoint(ch);
                    ch = nextChar(inputTape);
                } while (!isEndLineComment(ch));
            } else if (isBeginBlockComment(ch)) {
                do {
                    spacesBeforeBuilder.appendCodePoint(ch);
                    ch = nextChar(inputTape);
                } while (!isEndBlockComment(ch));
                spacesBeforeBuilder.appendCodePoint(ch);
                ch = nextChar(inputTape);
                spacesBeforeBuilder.appendCodePoint(ch);
                ch = nextChar(inputTape);
            }
        }
        String spacesBefore = spacesBeforeBuilder.toString();

        // Simple names
        if (isNameStartChar(ch)) {
            List<String> nameSegments = new ArrayList<>();

            // Scan name start
            StringBuilder nameSegment = new StringBuilder();
            do {
                nameSegment.appendCodePoint(ch);
                ch = nextChar(inputTape);
            } while (isNamePartChar(ch) || isAdditionalNameSymbols(ch));
            nameSegments.add(nameSegment.toString());

            while (isWhiteSpace(ch)) {
                // Scan white spaces
                nameSegment = new StringBuilder();
                while (isWhiteSpace(ch)) {
                    nameSegment.appendCodePoint(ch);
                    ch = nextChar(inputTape);
                }

                if (isNamePartChar(ch) || isAdditionalNameSymbols(ch)) {
                    nameSegments.add(nameSegment.toString());

                    // Scan name part
                    nameSegment = new StringBuilder();
                    do {
                        nameSegment.appendCodePoint(ch);
                        ch = nextChar(inputTape);
                    } while (isNamePartChar(ch) || isAdditionalNameSymbols(ch));
                    nameSegments.add(nameSegment.toString());
                }
            }
            String lastSegmentText = nameSegment.toString();
            if (StringUtils.isBlank(lastSegmentText)) {
                // Rewind to avoid missing spaces at end of segments
                rewind(inputTape, lastSegmentText.length());
            }

            // Possible name
            String nameLexeme = makeLexeme(nameSegments);

            // Check if it starts with a keyword
            for(Map.Entry<String, Integer> entry: KEYWORDS.entrySet()) {
                String keyword = entry.getKey();
                Integer value = entry.getValue();
                if (nameLexeme.startsWith(keyword)) {
                    rewind(inputTape, nameLexeme.length() - keyword.length());
                    return new Pair<>(spacesBefore, new CommonToken(value, keyword));
                }
            }

            // Lookup for the longest name
            for(String name: lexicalContext.getNames()) {
                if (nameLexeme.startsWith(name)) {
                    rewind(inputTape, nameLexeme.length() - name.length());
                    return new Pair<>(spacesBefore, new CommonToken(NAME, name));
                }
            }

            return new Pair<>(spacesBefore, new CommonToken(NAME, nameLexeme));
        }

        // Qualified names
        if (ch == '\'') {
            code = NAME;
            do {
                lexeme.appendCodePoint(ch);
                ch = nextChar(inputTape);
            } while (ch != '\'' && ch != '\r' && ch != '\n' && ch != -1);
            if (ch == '\'') {
                lexeme.appendCodePoint(ch);
                nextChar(inputTape);
            } else {
                code = BAD;
            }
            return new Pair<>(spacesBefore, new CommonToken(code, com.gs.dmn.NameUtils.removeSingleQuotes(lexeme.toString())));
        }

        // Numbers
        if (isDigit(ch)) {
            code = NUMBER;
            do {
                lexeme.appendCodePoint(ch);
                ch = nextChar(inputTape);
            } while (isDigit(ch));
            if (ch == '.') {
                lexeme.appendCodePoint(ch);
                ch = nextChar(inputTape);
                if (isDigit(ch)) {
                    do {
                        lexeme.appendCodePoint(ch);
                        ch = nextChar(inputTape);
                    } while (isDigit(ch));
                } else {
                    code = BAD;
                }
            }
            return new Pair<>(spacesBefore, new CommonToken(code, lexeme.toString()));
        }

        // Strings
        if (ch == '"') {
            code = STRING;
            do {
                lexeme.appendCodePoint(ch);
                ch = nextChar(inputTape);
            } while (ch != '"' && ch != '\r' && ch != '\n' && ch != -1);
            if (ch == '"') {
                lexeme.appendCodePoint(ch);
                nextChar(inputTape);
            } else {
                code = BAD;
            }
            return new Pair<>(spacesBefore, new CommonToken(code, lexeme.toString()));
        }

        // Operators
        if (ch == '+') {
            code = PLUS;
            lexeme.appendCodePoint(ch);
            nextChar(inputTape);
        } else if (ch == '-') {
            code = MINUS;
            lexeme.appendCodePoint(ch);
            nextChar(inputTape);
        } else if (ch == '*') {
            code = STAR;
            lexeme.appendCodePoint(ch);
            ch = nextChar(inputTape);
            if (ch == '*') {
                code = STAR_STAR;
                lexeme.appendCodePoint(ch);
                nextChar(inputTape);
            }
        } else if (ch == '/') {
            code = FORWARD_SLASH;
            lexeme.appendCodePoint(ch);
            nextChar(inputTape);
        } else if (ch == '=') {
            code = EQ;
            lexeme.appendCodePoint(ch);
            nextChar(inputTape);
        } else if (ch == '!') {
            code = EQ;
            lexeme.appendCodePoint(ch);
            ch = nextChar(inputTape);
            if (ch == '=') {
                code = NE;
                lexeme.appendCodePoint(ch);
                nextChar(inputTape);
            }
        } else if (ch == '<') {
            code = LT;
            lexeme.appendCodePoint(ch);
            ch = nextChar(inputTape);
            if (currentChar(inputTape) == '=') {
                code = LE;
                lexeme.appendCodePoint(ch);
                nextChar(inputTape);
            }
        } else if (ch == '>') {
            code = GT;
            lexeme.appendCodePoint(ch);
            ch = nextChar(inputTape);
            if (currentChar(inputTape) == '=') {
                code = GE;
                lexeme.appendCodePoint(ch);
                nextChar(inputTape);
            }
        // Punctuation
        } else if (ch == '.') {
            code = DOT;
            lexeme.appendCodePoint(ch);
            ch = nextChar(inputTape);
            if (ch == '.') {
                code = DOT_DOT;
                lexeme.appendCodePoint(ch);
                nextChar(inputTape);
            } else if (isDigit(ch)) {
                // Number
                code = NUMBER;
                do {
                    lexeme.appendCodePoint(ch);
                    ch = nextChar(inputTape);
                } while (isDigit(ch));
            }
        } else if (ch == '(') {
            code = PAREN_OPEN;
            lexeme.appendCodePoint(ch);
            nextChar(inputTape);
        } else if (ch == ')') {
            code = PAREN_CLOSE;
            lexeme.appendCodePoint(ch);
            nextChar(inputTape);
        } else if (ch == '[') {
            code = BRACKET_OPEN;
            lexeme.appendCodePoint(ch);
            nextChar(inputTape);
        } else if (ch == ']') {
            code = BRACKET_CLOSE;
            lexeme.appendCodePoint(ch);
            nextChar(inputTape);
        } else if (ch == '{') {
            code = BRACE_OPEN;
            lexeme.appendCodePoint(ch);
            nextChar(inputTape);
        } else if (ch == '}') {
            code = BRACE_CLOSE;
            lexeme.appendCodePoint(ch);
            nextChar(inputTape);
        } else if (ch == ':') {
            code = COLON;
            lexeme.appendCodePoint(ch);
            nextChar(inputTape);
        } else if (ch == ',') {
            code = COMMA;
            lexeme.appendCodePoint(ch);
            nextChar(inputTape);
        } else if (ch == IntStream.EOF) {
            code = EOF;
        } else {
            // BAD
            lexeme.appendCodePoint(ch);
            nextChar(inputTape);
        }

        return new Pair<>(spacesBefore, new CommonToken(code, lexeme.toString()));
    }

    private int currentChar(CharStream inputTape) {
        int index = inputTape.index();
        int ch;
        if (index < inputTape.size()) {
            ch = inputTape.LA(1);
        } else {
            ch = IntStream.EOF;
        }
        return ch;
    }

    private int nextChar(CharStream inputTape) {
        int ch = currentChar(inputTape);
        if (ch != IntStream.EOF) {
            inputTape.consume();
            ch = currentChar(inputTape);
        }
        return ch;
    }

    private void rewind(CharStream inputTape, int offset) {
        if (offset < 0) {
            throw new IllegalArgumentException(String.format("Offset should be positive. Got %d", offset));
        } else if (offset > 0) {
            int rewindIndex = inputTape.index() - offset;
            inputTape.seek(rewindIndex);
        }
    }

    private String makeLexeme(List<String> nameSegments) {
        return String.join("", nameSegments);
    }

    private boolean isBeginLineComment(int ch) {
        return ch == '/' && inputTape.LA(+2) == '/';
    }

    private boolean isEndLineComment(int ch) {
        return ch == '\n'
                || ch == IntStream.EOF;
    }

    private boolean isBeginBlockComment(int ch) {
        return ch == '/' && inputTape.LA(+2) == '*';
    }

    private boolean isEndBlockComment(int ch) {
        return ch == '*' && inputTape.LA(+2) == '/'
                || ch == IntStream.EOF;
    }

    private boolean isWhiteSpace(int ch) {
        return Character.isWhitespace(ch);
    }

    private boolean isDigit(int ch) {
        return in(ch, '0', '9');
    }

    // name start char =
    //      "?" | [A-Z] | "_" | [a-z] |
    //      [\u00C0-\u00D6] | [\u00D8-\u00F6] |
    //      [\u00F8-\u02FF] | [\u0370-\u037D] |
    //      [\u037F-\u1FFF] | [\u200C-\u200D] |
    //      [\u2070-\u218F] | [\u2C00-\u2FEF] |
    //      [\u3001-\uD7FF] | [\uF900-\uFDCF] |
    //      [\uFDF0-\uFFFD] | [\u10000-\uEFFFF] ;
    public static boolean isNameStartChar(int ch) {
        return
                ch == '?' || in(ch, 'A', 'Z') || ch == '_' || in(ch, 'a', 'z') ||
                        in(ch, '\u00C0', '\u00D6') || in(ch, '\u00D8', '\u00F6') ||
                        in(ch, '\u00F8', '\u02FF') || in(ch, '\u0370', '\u037D') ||
                        in(ch, '\u037F', '\u1FFF') || in(ch, '\u200C', '\u200D') ||
                        in(ch, '\u2070', '\u218F') || in(ch, '\u2C00', '\u2FEF') ||
                        in(ch, '\u3001', '\uD7FF') || in(ch, '\uF900', '\uFDCF') ||
                        in(ch, '\uFDF0', '\uFFFD') || in (ch, 0x10000, 0xEFFFF);
    }

    // name part char =
    //      name start char | digit | \u00B7 |
    //      [\u0300-\u036F] | [\u203F-\u2040]
    public static boolean isNamePartChar(int ch) {
        return isNameStartChar(ch) || in(ch, '0', '9') || ch == '\u00b7' ||
                in(ch,'\u0300', '\u036F') || in(ch, '\u203F', '\u2040');
    }

    // additional name symbols = "." | "/" | "-" | "’" | "+" | "*"
    private static boolean isAdditionalNameSymbols(int ch) {
        return ch == '.' || ch == '/' || ch == '-' || ch == '\'' || ch == '+' || ch == '*';
    }

    private static boolean in(int ch, int left, int right) {
        return left <= ch && ch <= right;
    }
}
