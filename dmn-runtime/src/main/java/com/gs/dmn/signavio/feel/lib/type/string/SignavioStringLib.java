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

import java.util.List;

public interface SignavioStringLib {
    String stringAdd(String first, String second);

    String concat(List<String> texts);

    String mid(String text, Number start, Number numChar);

    String left(String text, Number numChar);

    String right(String text, Number numChar);

    String text(Number num, String formatText);

    Integer textOccurrences(String findText, String withinText);

    Boolean isAlpha(String text);

    Boolean isAlphanumeric(String text);

    Boolean isNumeric(String text);

    Boolean isSpaces(String text);

    String lower(String text);

    String trim(String text);

    String upper(String text);
}
