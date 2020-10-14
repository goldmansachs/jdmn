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
package com.gs.dmn.signavio.feel.lib.type.string;

import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.util.List;

public class DefaultSignavioStringLib implements SignavioStringLib {
    @Override
    public String stringAdd(String first, String second) {
        if (first == null && second == null) {
            return "";
        } else if (first == null) {
            return second;
        } else if (second == null) {
            return first;
        } else {
            return first + second;
        }
    }

    @Override
    public String concat(List<String> texts) {
        if (texts == null || texts.isEmpty()) {
            return null;
        }

        StringBuilder result = new StringBuilder();
        for(String text: texts) {
            if (text == null) {
                return null;
            }
            if (text != null) {
                result.append(text);
            }
        }
        return result.toString();
    }

    @Override
    public String mid(String text, Number start, Number numChar) {
        if (text == null || start == null || numChar == null) {
            return null;
        }
        if (start.intValue() < 0 || numChar.intValue() < 0) {
            return null;
        }

        int endIndex = start.intValue() + numChar.intValue();
        if (endIndex > text.length()) {
            endIndex = text.length();
        }
        if (endIndex == start.intValue()) {
            return null;
        }
        return text.substring(start.intValue(), endIndex);
    }

    @Override
    public String left(String text, Number numChar) {
        if (text == null || numChar == null) {
            return null;
        }

        int length = numChar.intValue();
        if (length > text.length()) {
            length = text.length();
        }
        return text.substring(0, length);
    }

    @Override
    public String right(String text, Number numChar) {
        if (text == null || numChar == null) {
            return null;
        }

        int length = numChar.intValue();
        if (length > text.length()) {
            length = text.length();
        }
        return text.substring(text.length() - length);
    }

    @Override
    public String text(Number num, String formatText) {
        DecimalFormat df = new DecimalFormat(formatText);
        return df.format(num);
    }

    @Override
    public Integer textOccurrences(String findText, String withinText) {
        if (findText == null || withinText == null) {
            return null;
        }
        int count = 0;
        int i = 0;
        while (i < withinText.length()) {
            if (withinText.substring(i).startsWith(findText)) {
                count++;
                i += findText.length();
            } else {
                i++;
            }
        }
        return count;
    }

    @Override
    public Boolean isAlpha(String text) {
        if (text == null) {
            return null;
        }

        return StringUtils.isAlpha(text);
    }

    @Override
    public Boolean isAlphanumeric(String text) {
        if (text == null) {
            return null;
        }

        return StringUtils.isAlphanumeric(text);
    }

    @Override
    public Boolean isNumeric(String text) {
        if (text == null) {
            return null;
        }

        return StringUtils.isNumeric(text);
    }

    @Override
    public Boolean isSpaces(String text) {
        if (text == null) {
            return null;
        }
        if (text.isEmpty()) {
            return false;
        }

        return StringUtils.isBlank(text);
    }

    @Override
    public String lower(String text) {
        return text == null ? null : text.toLowerCase();
    }

    @Override
    public String trim(String text) {
        return text == null ? null : text.trim();
    }

    @Override
    public String upper(String text) {
        return text == null ? null : text.toUpperCase();
    }
}
