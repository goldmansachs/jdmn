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
package com.gs.dmn.feel.lib;

import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class StringEscapeUtil {
    private static final Map<Character, String> FEEL_UNESCAPE_MAP = new LinkedHashMap<>();
    static {
        // control and basic escapes
        FEEL_UNESCAPE_MAP.put('n', "\n");
        FEEL_UNESCAPE_MAP.put('r', "\r");
        FEEL_UNESCAPE_MAP.put('t', "\t");
        FEEL_UNESCAPE_MAP.put('\'', "\'");
        FEEL_UNESCAPE_MAP.put('\"', "\"");
        FEEL_UNESCAPE_MAP.put('\\', "\\");
    }

    private static final Map<Character, String> REGEXP_UNESCAPE_MAP = new LinkedHashMap<>();
    static {
        // reg exp escapes
        FEEL_UNESCAPE_MAP.put('d', "\\d");
        FEEL_UNESCAPE_MAP.put('D', "\\D");
        FEEL_UNESCAPE_MAP.put('s', "\\s");
        FEEL_UNESCAPE_MAP.put('S', "\\S");
        FEEL_UNESCAPE_MAP.put('p', "\\p");
        FEEL_UNESCAPE_MAP.put('P', "\\P");
        FEEL_UNESCAPE_MAP.put('x', "\\x");
        FEEL_UNESCAPE_MAP.put('X', "\\X");
    }

    private static final Map<Character, String> FEEL_ESCAPE_MAP = new LinkedHashMap<>();
    static {
        // control and basic escapes
        FEEL_ESCAPE_MAP.put('\n', "\\n");
        FEEL_ESCAPE_MAP.put('\r', "\\r");
        FEEL_ESCAPE_MAP.put('\t', "\\t");
        FEEL_ESCAPE_MAP.put('\'', "\'");
        FEEL_ESCAPE_MAP.put('\"', "\\\"");
        FEEL_ESCAPE_MAP.put('\\', "\\\\");
    }

    private static final Map<Character, String> REGEXP_ESCAPE_MAP = new LinkedHashMap<>();
    static {
        // reg exp escapes
        REGEXP_ESCAPE_MAP.put('d', "\\\\d");
        REGEXP_ESCAPE_MAP.put('D', "\\\\D");
        REGEXP_ESCAPE_MAP.put('s', "\\\\s");
        REGEXP_ESCAPE_MAP.put('S', "\\\\S");
        REGEXP_ESCAPE_MAP.put('p', "\\\\p");
        REGEXP_ESCAPE_MAP.put('P', "\\\\P");
        REGEXP_ESCAPE_MAP.put('x', "\\\\x");
        REGEXP_ESCAPE_MAP.put('X', "\\\\X");
    }

    public static String stripQuotes(String value) {
        if (StringUtils.isEmpty(value) || !value.startsWith("\"")) {
            return value;
        }
        return value.substring(1, value.length() - 1);
    }

    public static String feelLiteralToJavaLiteral(String lexeme) {
        String value = unescapeFEEL(lexeme);
        value = escapeFEEL(value);
        return String.format("\"%s\"", value);
    }

    // Replace the FEEL escape sequences in lexeme with their values
    public static String unescapeFEEL(String lexeme) {
        if (StringUtils.isEmpty(lexeme)) {
            return lexeme;
        }

        String value = stripQuotes(lexeme);
        StringBuilder builder = new StringBuilder();
        int index = 0;
        while (index < value.length()) {
            char ch = value.charAt(index);
            if (ch == '\\') {
                if (contains(FEEL_UNESCAPE_MAP.keySet(), value, index + 1)) {
                    // \n \r \t \' \" \\
                    // patterns (e.g. \d) used in regular expressions (see replace)
                    builder.append(FEEL_UNESCAPE_MAP.get(value.charAt(index + 1)));
                    index += 2;
                } else if (contains(REGEXP_UNESCAPE_MAP.keySet(), value, index + 1)) {
                        // \n \r \t \' \" \\
                        // patterns (e.g. \d) used in regular expressions (see replace)
                        builder.append(REGEXP_UNESCAPE_MAP.get(value.charAt(index + 1)));
                        index += 2;
                } else if (isUnicodeEscape(value, index + 1)) {
                    // unicode escapes
                    String hexCode = value.substring(index + 2, index + 6);
                    builder.appendCodePoint(Integer.parseInt(hexCode, 16));
                    index += 6;
                } else {
                    builder.append('\\');
                    index++;
                }
            } else {
                builder.append(ch);
                index++;
            }
        }
        return builder.toString();
    }

    // Translates a FEEL String to a Java String
    public static String escapeFEEL(String value) {
        if (StringUtils.isEmpty(value)) {
            return value;
        }

        StringBuilder builder = new StringBuilder();
        int index = 0;
        while (index < value.length()) {
            char ch = value.charAt(index);
            if (ch == '\\') {
                if (contains(REGEXP_ESCAPE_MAP.keySet(), value, index + 1)) {
                    // patterns (e.g. \d) used in regular expressions (see replace)
                    builder.append(REGEXP_ESCAPE_MAP.get(value.charAt(index + 1)));
                    index += 2;
                } else {
                    builder.append('\\');
                    builder.append('\\');
                    index++;
                }
            } else if (FEEL_ESCAPE_MAP.containsKey(ch)) {
                // \n \r \t \' \" \\
                builder.append(FEEL_ESCAPE_MAP.get(ch));
                index++;
            } else if (Character.isSurrogate(ch)) {
                builder.append("\\u").append(hex(ch));
                index++;
            } else {
                builder.append(ch);
                index++;
            }
        }
        return builder.toString();
    }

    public static String escapeInString(String text) {
        if (StringUtils.isBlank(text)) {
            return text;
        }

        StringBuilder builder = new StringBuilder();
        int index = 0;
        while (index < text.length()) {
            char ch = text.charAt(index);
            if (ch == '\\') {
                if (contains(FEEL_ESCAPE_MAP.keySet(), text, index + 1)) {
                    // \n \r \t \' \" \\
                    builder.append("\\");
                    builder.append(text.charAt(index + 1));
                    index += 2;
                } else if (contains(REGEXP_ESCAPE_MAP.keySet(), text, index + 1)) {
                    // patterns (e.g. \d) used in regular expressions (see replace)
                    builder.append("\\");
                    builder.append("\\");
                    builder.append(text.charAt(index + 1));
                    index = index + 2;
                } else if (isUnicodeEscape(text, index + 1)) {
                    // unicode escapes
                    builder.append("\\");
                    for(int i = 0; i < 5; i++) {
                        builder.append(text.charAt(index + 1 + i));
                    }
                    index = index + 6;
                } else {
                    builder.append('\\');
                    builder.append('\\');
                    index = index + 1;
                }
            } else if (ch == '"') {
                // unescaped "
                builder.append("\\\"");
                index++;
            } else {
                builder.append(ch);
                index++;
            }
        }
        return builder.toString();
    }

    private static boolean isUnicodeEscape(String text, int index) {
        return isChar(text, index, 'u') &&
                isHexChar(text, index + 1) &&
                isHexChar(text, index + 2) &&
                isHexChar(text, index + 3) &&
                isHexChar(text, index + 4);
    }

    private static boolean isChar(String text, int index, char ch) {
        return index < text.length() && text.charAt(index) == ch;
    }

    private static boolean isHexChar(String text, int index) {
        if (index >= text.length()) {
            return false;
        }
        char ch = text.charAt(index);
        return '0' <= ch && ch <= '9' || 'a' <= ch && ch <= 'f' || 'A' <= ch && ch <= 'F';
    }

    private static boolean contains(Set<Character> set, String value, int index) {
        return index < value.length() && set.contains(value.charAt(index));
    }

    private static String hex(int codepoint) {
        return Integer.toHexString(codepoint).toUpperCase(Locale.ENGLISH);
    }
}
