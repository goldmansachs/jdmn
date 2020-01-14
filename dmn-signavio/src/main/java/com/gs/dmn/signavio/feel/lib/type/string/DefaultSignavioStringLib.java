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

import java.text.DecimalFormat;
import java.util.List;

public class DefaultSignavioStringLib {
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

    public String concat(List<String> texts) {
        StringBuilder result = new StringBuilder();
        for(String text: texts) {
            if (text != null) {
                result.append(text);
            }
        }
        return result.toString();
    }

    public String mid(String text, Number start, Number numChar) {
        int endIndex = start.intValue() + numChar.intValue();
        if (endIndex > text.length()) {
            endIndex = text.length();
        }
        return text.substring(start.intValue(), endIndex);
    }

    public String left(String text, Number numChar) {
        if (text == null) {
            return null;
        }

        return text.substring(0, numChar.intValue());
    }

    public String right(String text, Number numChar) {
        return text.substring(text.length() - numChar.intValue());
    }

    public String text(Number num, String formatText) {
        DecimalFormat df = new DecimalFormat(formatText);
        return df.format(num);
    }

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
}
