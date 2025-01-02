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
package com.gs.dmn.feel.lib.reference;

import com.gs.dmn.runtime.DMNRuntimeException;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

import static com.gs.dmn.feel.lib.reference.TokenKind.*;

public class TypeReference {
    private final String typeExpression;
    private final Type type;

    public TypeReference(String typeExpression) {
        if (StringUtils.isBlank(typeExpression)) {
            throw new DMNRuntimeException("Missing type expression");
        }
        this.typeExpression = typeExpression;
        this.type = parse(typeExpression);
    }

    public String getTypeExpression() {
        return typeExpression;
    }

    public Type getType() {
        return type;
    }

    private Type parse(String type) {
        if (StringUtils.isBlank(type)) {
            throw new DMNRuntimeException("Type expression cannot be blank");
        }

        Parser parser = new Parser();
        return parser.parse(type);
    }
}

enum TokenKind {
    NAME,
    LIST,
    RANGE,
    CONTEXT,
    FUNCTION,
    LEFT_BRACKET,
    RIGHT_BRACKET,
    COMMA,
    COLON,
    ARROW,
    EOF,
    BAD
}

class Token {
    TokenKind kind;
    String lexeme;

    public Token(TokenKind kind, String lexeme) {
        this.kind = kind;
        this.lexeme = lexeme;
    }

    @Override
    public String toString() {
        return String.format("%s:%s", kind, lexeme);
    }
}

class Lexer {
    final String tape;
    int index;
    Token currentToken;

    Lexer(String tape) {
        this.tape = tape;
        this.index = 0;
    }

    void nextToken() {
        if (index < tape.length()) {
            currentToken = null;
            int ch = tape.charAt(index);
            while (Character.isSpaceChar(ch)) {
                ch = nextChar();
            }
            if (ch == '<') {
                nextChar();
                currentToken = new Token(LEFT_BRACKET, "<");
            } else if (ch == '>') {
                nextChar();
                currentToken = new Token(RIGHT_BRACKET, ">");
            } else if (ch == ',') {
                nextChar();
                currentToken = new Token(TokenKind.COMMA, ",");
            } else if (ch == ':') {
                nextChar();
                currentToken = new Token(TokenKind.COLON, ".");
            } else if (ch == '-') {
                ch = nextChar();
                if (ch == '>') {
                    nextChar();
                    currentToken = new Token(TokenKind.ARROW, "->");
                } else {
                    currentToken = new Token(TokenKind.BAD, "-" + ch);
                }
            } else if (Character.isLetter(ch)) {
                StringBuilder nameBuilder = new StringBuilder();
                while ((Character.isLetter(ch) || Character.isSpaceChar(ch)) && ch != -1) {
                    nameBuilder.append((char) ch);
                    ch = nextChar();
                }
                String name = nameBuilder.toString().trim();
                switch (name) {
                    case "list":
                        currentToken = new Token(TokenKind.LIST, name);
                        break;
                    case "range":
                        currentToken = new Token(TokenKind.RANGE, name);
                        break;
                    case "context":
                        currentToken = new Token(TokenKind.CONTEXT, name);
                        break;
                    case "function":
                        currentToken = new Token(FUNCTION, name);
                        break;
                    default:
                        currentToken = new Token(TokenKind.NAME, name);
                        break;
                }
            }
        } else {
            currentToken = new Token(EOF, "");
        }
    }

    private int nextChar() {
        index++;
        if (index < tape.length()) {
            return tape.charAt(index);
        } else {
            return -1;
        }
    }

}

class Parser {
    private static final Set<TokenKind> SD_TYPE = new LinkedHashSet<>(Arrays.asList(NAME, LIST, RANGE, CONTEXT, FUNCTION));

    Parser() {
    }

    Type parse(String text) {
        Lexer lexer = new Lexer(text);
        lexer.nextToken();
        Type type = type(lexer);
        if (lexer.currentToken.kind != EOF) {
            throw new DMNRuntimeException(String.format("Illegal type expression '%s'", lexer.tape));
        }
        return type;
    }

    private Type type(Lexer lexer) {
        Token token = lexer.currentToken;
        if (token.kind == TokenKind.NAME) {
            lexer.nextToken();
            return new SimpleType(token.lexeme);
        } else if (token.kind == TokenKind.LIST) {
            return listType(lexer);
        } else if (token.kind == TokenKind.RANGE) {
            return rangeType(lexer);
        } else if (token.kind == TokenKind.CONTEXT) {
            return contextType(lexer);
        } else if (token.kind == FUNCTION) {
            return functionType(lexer);
        }
        throw new DMNRuntimeException(String.format("Illegal type expression '%s'", lexer.tape));
    }

    // list < type >
    private Type listType(Lexer lexer) {
        if (lexer.currentToken.kind == LIST) {
            lexer.nextToken();
            if (lexer.currentToken.kind == LEFT_BRACKET) {
                lexer.nextToken();
                Type elementType = type(lexer);
                if (lexer.currentToken.kind == RIGHT_BRACKET) {
                    lexer.nextToken();
                    return new ListType(elementType);
                }
            }
        }
        throw new DMNRuntimeException(String.format("Incorrect list type in '%s'", lexer.tape));
    }

    // range < type >
    private Type rangeType(Lexer lexer) {
        if (lexer.currentToken.kind == RANGE) {
            lexer.nextToken();
            if (lexer.currentToken.kind == LEFT_BRACKET) {
                lexer.nextToken();
                Type elementType = type(lexer);
                if (lexer.currentToken.kind == RIGHT_BRACKET) {
                    lexer.nextToken();
                    return new RangeType(elementType);
                }
            }
        }
        throw new DMNRuntimeException(String.format("Incorrect range type in '%s'", lexer.tape));
    }

    // context < contextEntry (, contextEntry )* >
    private Type contextType(Lexer lexer) {
        if (lexer.currentToken.kind == CONTEXT) {
            lexer.nextToken();
            if (lexer.currentToken.kind == LEFT_BRACKET) {
                lexer.nextToken();
                ContextType contextType = new ContextType();
                ContextEntry entry = contextEntry(lexer);
                contextType.addMember(entry.name, entry.type);
                while (lexer.currentToken.kind == COMMA) {
                    lexer.nextToken();
                    entry = contextEntry(lexer);
                    contextType.addMember(entry.name, entry.type);
                }
                if (lexer.currentToken.kind == RIGHT_BRACKET) {
                    lexer.nextToken();
                    return contextType;
                }
            }
        }
        throw new DMNRuntimeException(String.format("Incorrect context type in '%s'", lexer.tape));
    }

    // name : type
    private ContextEntry contextEntry(Lexer lexer) {
        if (lexer.currentToken.kind == NAME) {
            String name = lexer.currentToken.lexeme;
            lexer.nextToken();
            if (lexer.currentToken.kind == COLON) {
                lexer.nextToken();
                Type type = type(lexer);
                return new ContextEntry(name, type);
            }
        }
        throw new DMNRuntimeException(String.format("Incorrect context entry in '%s'", lexer.tape));
    }
    // function < argList > -> type
    private Type functionType(Lexer lexer) {
        if (lexer.currentToken.kind == FUNCTION) {
            lexer.nextToken();
            if (lexer.currentToken.kind == LEFT_BRACKET) {
                lexer.nextToken();
                List<Type> paramTypes = new ArrayList<>();
                if (SD_TYPE.contains(lexer.currentToken.kind)) {
                    Type type = type(lexer);
                    paramTypes.add(type);
                    while (lexer.currentToken.kind == COMMA) {
                        lexer.nextToken();
                        type = type(lexer);
                        paramTypes.add(type);
                    }
                }
                if (lexer.currentToken.kind == RIGHT_BRACKET) {
                    lexer.nextToken();
                    if (lexer.currentToken.kind == ARROW) {
                        lexer.nextToken();
                        Type returnType = type(lexer);
                        return new FunctionType(paramTypes, returnType);
                    }
                }
            }
        }
        throw new DMNRuntimeException(String.format("Incorrect function type in '%s'", lexer.tape));
    }
}

class ContextEntry {
    String name;
    Type type;

    public ContextEntry(String name, Type type) {
        this.name = name;
        this.type = type;
    }
}