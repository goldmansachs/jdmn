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

//
// Reference to runtime types
//
// Native expression type is serialized as:
// Primitive types:
//     Null, Any
//     number, string, boolean,
//     date, time, date and time, years and months duration, days and time duration
// Composite types:
//     list<number>
//     range<number>
//     range<number>
//     context<a:number, b:string> for ContextType
//     p1.p2.Cls<a:number> for ItemDefinitionType
public class TypeReference {
    private final String typeExpression;
    private Type type;

    public TypeReference(String typeExpression) {
        this.typeExpression = typeExpression;
    }

    public String getTypeExpression() {
        return typeExpression;
    }

    public Type getType() {
        if (type == null) {
            this.type = parse(typeExpression);
        }
        return type;
    }

    protected Type parse(String type) {
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
            } else if (ch == '\'') {
                StringBuilder nameBuilder = new StringBuilder();
                ch = nextChar();
                while (ch != '\'' && ch != -1) {
                    nameBuilder.append((char) ch);
                    ch = nextChar();
                }
                if (ch == '\'') {
                    nextChar();
                    currentToken = new Token(NAME, nameBuilder.toString());
                } else {
                    currentToken = new Token(TokenKind.BAD, "'" + ch);
                }
            } else if (isNameStart(ch)) {
                StringBuilder nameBuilder = new StringBuilder();
                while (isNamePart(ch) && ch != -1) {
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

    private boolean isNameStart(int ch) {
        return Character.isLetter(ch);
    }

    private boolean isNamePart(int ch) {
        return Character.isLetter(ch) ||
                Character.isDigit(ch) ||
                ch == '_' ||
                ch == '-';
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
        if (matches(token, NAME)) {
            lexer.nextToken();
            if (matches(lexer.currentToken, COLON)) {
                return itemDefinitionType(token.lexeme, lexer);
            } else {
                return new SimpleType(token.lexeme);
            }
        } else if (matches(token, LIST)) {
            return listType(lexer);
        } else if (matches(token, RANGE)) {
            return rangeType(lexer);
        } else if (matches(token, CONTEXT)) {
            return contextType(lexer);
        } else if (matches(token, FUNCTION)) {
            return functionType(lexer);
        }
        throw new DMNRuntimeException(String.format("Illegal type expression '%s'", lexer.tape));
    }

    // list < type >
    private Type listType(Lexer lexer) {
        if (matches(lexer.currentToken, LIST)) {
            lexer.nextToken();
            if (matches(lexer.currentToken, LEFT_BRACKET)) {
                lexer.nextToken();
                Type elementType = type(lexer);
                if (matches(lexer.currentToken, RIGHT_BRACKET)) {
                    lexer.nextToken();
                    return new ListType(elementType);
                }
            }
        }
        throw new DMNRuntimeException(String.format("Incorrect list type in '%s'", lexer.tape));
    }

    // range < type >
    private Type rangeType(Lexer lexer) {
        if (matches(lexer.currentToken, RANGE)) {
            lexer.nextToken();
            if (matches(lexer.currentToken, LEFT_BRACKET)) {
                lexer.nextToken();
                Type elementType = type(lexer);
                if (matches(lexer.currentToken, RIGHT_BRACKET)) {
                    lexer.nextToken();
                    return new RangeType(elementType);
                }
            }
        }
        throw new DMNRuntimeException(String.format("Incorrect range type in '%s'", lexer.tape));
    }

    // context < contextEntry (, contextEntry )* >
    private Type contextType(Lexer lexer) {
        if (matches(lexer.currentToken, CONTEXT)) {
            lexer.nextToken();
            ContextType contextType = new ContextType();
            collectEntries(lexer, contextType);
            return contextType;
        }
        throw new DMNRuntimeException(String.format("Incorrect context type in '%s'", lexer.tape));
    }

    // qName < contextEntry (, contextEntry )* >
    private Type itemDefinitionType(String modelName, Lexer lexer) {
        if (matches(lexer.currentToken, COLON)) {
            lexer.nextToken();
            if (matches(lexer.currentToken, NAME)) {
                String itemDefinitionName = lexer.currentToken.lexeme;
                lexer.nextToken();
                ItemDefinitionType contextType = new ItemDefinitionType(modelName, itemDefinitionName);
                collectEntries(lexer, contextType);
                return contextType;
            }
        }
        throw new DMNRuntimeException(String.format("Incorrect named context type in '%s'", lexer.tape));
    }

    private void collectEntries(Lexer lexer, CompositeDataType contextType) {
        if (matches(lexer.currentToken, LEFT_BRACKET)) {
            lexer.nextToken();
            if (matches(lexer.currentToken, RIGHT_BRACKET)) {
                // empty context type
                lexer.nextToken();
            } else {
                ContextEntry entry = contextEntry(lexer);
                contextType.addMember(entry.name, entry.type);
                while (matches(lexer.currentToken, COMMA)) {
                    lexer.nextToken();
                    entry = contextEntry(lexer);
                    contextType.addMember(entry.name, entry.type);
                }
                if (matches(lexer.currentToken, RIGHT_BRACKET)) {
                    lexer.nextToken();
                }
            }
        }
    }

    // name : type
    private ContextEntry contextEntry(Lexer lexer) {
        if (matches(lexer.currentToken, NAME)) {
            String name = lexer.currentToken.lexeme;
            lexer.nextToken();
            if (matches(lexer.currentToken, COLON)) {
                lexer.nextToken();
                Type type = type(lexer);
                return new ContextEntry(name, type);
            }
        }
        throw new DMNRuntimeException(String.format("Incorrect context entry in '%s'", lexer.tape));
    }
    // function < argList > -> type
    private Type functionType(Lexer lexer) {
        if (matches(lexer.currentToken, FUNCTION)) {
            lexer.nextToken();
            if (matches(lexer.currentToken, LEFT_BRACKET)) {
                lexer.nextToken();
                List<Type> paramTypes = new ArrayList<>();
                if (SD_TYPE.contains(lexer.currentToken.kind)) {
                    Type type = type(lexer);
                    paramTypes.add(type);
                    while (matches(lexer.currentToken, COMMA)) {
                        lexer.nextToken();
                        type = type(lexer);
                        paramTypes.add(type);
                    }
                }
                if (matches(lexer.currentToken, RIGHT_BRACKET)) {
                    lexer.nextToken();
                    if (matches(lexer.currentToken, ARROW)) {
                        lexer.nextToken();
                        Type returnType = type(lexer);
                        return new FunctionType(paramTypes, returnType);
                    }
                }
            }
        }
        throw new DMNRuntimeException(String.format("Incorrect function type in '%s'", lexer.tape));
    }

    private boolean matches(Token token, TokenKind tokenKind) {
        return token != null && token.kind == tokenKind;
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