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
package com.gs.dmn;

import org.apache.commons.lang3.StringUtils;

public class NameUtils {
    public static boolean isSimpleNameStart(int codePoint) {
        return Character.isJavaIdentifierStart(codePoint);
    }

    public static boolean isSimpleNamePart(int codePoint) {
        return Character.isJavaIdentifierPart(codePoint);
    }

    public static boolean isSimpleName(String name) {
        if (!isSimpleNameStart(name.codePointAt(0))) {
            return false;
        }
        for (int cp : name.codePoints().toArray()) {
            if (!(isSimpleNamePart(cp))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isQuotedName(String name) {
        return
                !StringUtils.isBlank(name) &&
                name.startsWith("'") &&
                name.endsWith("'");
    }

    public static String removeSingleQuotes(String name) {
        if (StringUtils.isBlank(name)) {
            return name;
        } else {
            name = name.trim();
            if (isQuotedName(name)) {
                name = name.replaceAll("''", "'");
                name = name.substring(1, name.length() - 1);
            }
            return name;
        }
    }

    public static String removeDoubleQuotes(String key) {
        if (StringUtils.isBlank(key)) {
            return key;
        } else {
            key = key.trim();
            if (!StringUtils.isBlank(key) && key.startsWith("\"") && key.endsWith("\"")) {
                key = key.substring(1, key.length() - 1);
            }
            return key;
        }
    }

    public static String invocableName(String text) {
        if (StringUtils.isBlank(text)) {
            return text;
        } else {
            return removeSingleQuotes(text.trim());
        }
    }
}
