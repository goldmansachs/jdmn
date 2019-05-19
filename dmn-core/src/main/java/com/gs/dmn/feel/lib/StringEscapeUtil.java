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
package com.gs.dmn.feel.lib;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.translate.*;

import java.util.LinkedHashMap;
import java.util.Map;

public class StringEscapeUtil {
    private static final Map<Character, String> FEEL_ESCAPED_CHARS = new LinkedHashMap<>();
    static {
        // control and basic escapes
        FEEL_ESCAPED_CHARS.put('n', "\n");
        FEEL_ESCAPED_CHARS.put('r', "\r");
        FEEL_ESCAPED_CHARS.put('t', "\t");
        FEEL_ESCAPED_CHARS.put('\'', "\'");
        FEEL_ESCAPED_CHARS.put('\"', "\"");
        FEEL_ESCAPED_CHARS.put('\\', "\\");
    }

    private static final Map<Character, String> REGEXP_ESCAPED_CHARS = new LinkedHashMap<>();
    static {
        // reg exp escapes
        REGEXP_ESCAPED_CHARS.put('d', "\\d");
        REGEXP_ESCAPED_CHARS.put('D', "\\D");
        REGEXP_ESCAPED_CHARS.put('s', "\\s");
        REGEXP_ESCAPED_CHARS.put('S', "\\S");
        REGEXP_ESCAPED_CHARS.put('p', "\\p");
        REGEXP_ESCAPED_CHARS.put('P', "\\P");
        REGEXP_ESCAPED_CHARS.put('x', "\\x");
        REGEXP_ESCAPED_CHARS.put('X', "\\X");
    }

    // Translator for unescaping.
    private static final CharSequenceTranslator UNESCAPE_FEEL =
            new AggregateTranslator(
                    new UnicodeUnescaper(),
                    new LookupTranslator(
                            new String[][] {
                                    {"\\n", "\n"},
                                    {"\\r", "\r"},
                                    {"\\t", "\t"},
                                    {"\\\'", "\'"},
                                    {"\\\"", "\""},
                                    {"\\s", "\\s"},
                                    {"\\S", "\\S"},
                                    {"\\d", "\\d"},
                                    {"\\D", "\\D"},
                                    {"\\x", "\\x"},
                                    {"\\X", "\\X"},
                                    {"\\p", "\\p"},
                                    {"\\P", "\\P"},
                                    {"\\\\", "\\"},
                            })
            );

    // Translator for replacing escape sequences with character codes
    private static final CharSequenceTranslator ESCAPE_FEEL =
            new AggregateTranslator(
                    new LookupTranslator(
                        new String[][] {
                                {"\n", "\\n"},
                                {"\r", "\\r"},
                                {"\t", "\\t"},
                                {"\'", "\\\'"},
                                {"\"", "\\\""},
                                {"\\s", "\\\\s"},
                                {"\\S", "\\\\S"},
                                {"\\d", "\\\\d"},
                                {"\\D", "\\\\D"},
                                {"\\x", "\\\\x"},
                                {"\\X", "\\\\X"},
                                {"\\p", "\\\\p"},
                                {"\\P", "\\\\P"},
                                {"\\", "\\\\"},
                        }
                ),
                FEELUnicodeEscaper.outsideOf(32, 0x7f)
            );


    // Replace the FEEL escape sequences in lexeme with their values
    public static final String unescapeFEEL(String lexeme) {
        if (StringUtils.isEmpty(lexeme)) {
            return lexeme;
        }

        String value = StringUtil.stripQuotes(lexeme);
        return UNESCAPE_FEEL.translate(value);
    }

    // Translates a FEEL String to a Java String
    public static final String escapeFEEL(String value) {
        return ESCAPE_FEEL.translate(value);
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
                if (isEscapedChar(text, index + 1)) {
                    // \n \r \t \' \" \\
                    builder.append("\\");
                    builder.append(text.charAt(index + 1));
                    index += 2;
                } else if (isRegExpChar(text, index + 1)) {
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

    private static boolean isRegExpChar(String text, int index) {
        return index < text.length() && REGEXP_ESCAPED_CHARS.keySet().contains(text.charAt(index));
    }

    private static boolean isEscapedChar(String text, int index) {
        return index < text.length() && FEEL_ESCAPED_CHARS.keySet().contains(text.charAt(index));
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
}
