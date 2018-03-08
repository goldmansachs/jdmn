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

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Token;

import static org.junit.Assert.assertEquals;

public abstract class AbstractLexerTest {
    protected void checkPosition(Token token, int beginLine, int beginColumn, int endLine, int endColumn, int beginOffset, int endOffset) {
        assertEquals(beginLine, getBeginLine(token));
        assertEquals(beginColumn, getBeginColumn(token));
        assertEquals(endLine, getEndLine(token));
        assertEquals(endColumn, getEndColumn(token));
        assertEquals(beginOffset, getBeginOffset(token));
        assertEquals(endOffset, getEndOffset(token));
    }

    private int getBeginLine(Token token) {
        return token.getLine();
    }

    private int getBeginColumn(Token token) {
        return token.getCharPositionInLine() + 1;
    }

    public int getBeginOffset(Token token) {
        return token.getStartIndex();
    }

    private int getEndLine(Token token) {
        int lineNo = countLines(token.getText());
        return token.getLine() + lineNo - 1;
    }

    private int getEndColumn(Token token) {
        int i = token.getText().lastIndexOf('\n');
        if (i == -1) {
            return token.getCharPositionInLine() + token.getText().length();
        } else {
            return token.getText().length() - i;
        }
    }

    private int getEndOffset(Token token) {
        return token.getStopIndex() + 1;
    }

    private int countLines(String text) {
        int lineNo = 1;
        for(int i=0; i<text.length(); i++) {
            if (text.charAt(i) == '\n') {
                lineNo++;
            }
        }
        return lineNo;
    }

    protected Token checkToken(String inputTape, int expectedTokenCode, String expectedLexeme) {
        Lexer tokenStream = makeLexer(inputTape);
        Token token = tokenStream.nextToken();
        assertEquals(expectedLexeme, token.getText());
        assertEquals(expectedTokenCode, token.getType());
        return token;
    }

    protected void checkTokenList(String text, Integer[] expectedTokenCodes, String[] expectedLexemes) {
        checkTokenList(true, text, expectedTokenCodes, expectedLexemes);
    }

    public void checkTokenList(boolean allowSpaceInNames, String text, Integer[] expectedTokenCodes, String[] expectedLexemes) {
        Lexer lexer = makeLexer(text);
        for(int i=0; i<expectedTokenCodes.length; i++) {
            Token token = lexer.nextToken();
            assertEquals(expectedLexemes[i], token.getText());
            assertEquals((long)expectedTokenCodes[i], token.getType());
        }
    }

    protected abstract Lexer makeLexer(String ab_ab);
}
