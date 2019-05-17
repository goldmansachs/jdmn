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

public class StringEscapeUtil {
    public static String escapeInString(String text) {
        if (StringUtils.isBlank(text)) {
            return text;
        }
        StringBuilder builder = new StringBuilder();
        int i = 0;
        while (i < text.length()) {
            char ch = text.charAt(i);
            if (ch == '\\') {
                int nextChar = nextChar(text, i);
                if (nextChar == 'd') {
                    // \d used in regular expressions (see replace)
                    builder.append("\\\\d");
                } else if (nextChar == '\"') {
                    // \"
                    builder.append("\\\"");
                } else {
                    builder.append('\\');
                    builder.append((char) nextChar);
                }
                i = i + 2;
            } else if (ch == '"') {
                if (0 < i && i < text.length() - 1) {
                    // Unescaped inner "
                    builder.append("\\\"");
                } else {
                    builder.append("\"");
                }
                i++;
            } else {
                builder.append(ch);
                i++;
            }
        }
        return builder.toString();
    }

    private static int nextChar(String text, int i) {
        if (i == text.length()) {
            return -1;
        } else {
            return text.charAt(i + 1);
        }
    }
}
