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
package com.gs.dmn.signavio.feel.lib;

import java.util.List;

public class SignavioStringUtil {
    public static String stringAdd(String first, String second) {
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

    public static String concat(List<String> texts) {
        StringBuilder result = new StringBuilder();
        for(String text: texts) {
            if (text != null) {
                result.append(text);
            }
        }
        return result.toString();
    }
}
