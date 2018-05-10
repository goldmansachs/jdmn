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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    public static List split(String string, String delimiter) {
        if (string == null || delimiter == null) {
            return null;
        }
        if (string.isEmpty() || delimiter.isEmpty()) {
            return null;
        }

        Pattern p = Pattern.compile(delimiter);
        Matcher m = p.matcher(string);
        List<String> result = new ArrayList<>();
        int start = 0;
        while (m.find(start)) {
            int delimiterStart = m.start();
            int delimiterEnd = m.end();
            String token = string.substring(start, delimiterStart);
            result.add(token);
            start = delimiterEnd;
        }
        if (start <= string.length()) {
            String token = string.substring(start);
            result.add(token);
        }
        return result;

    }
}
