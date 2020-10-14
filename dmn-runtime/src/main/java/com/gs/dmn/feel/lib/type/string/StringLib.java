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
package com.gs.dmn.feel.lib.type.string;

import java.util.List;

public interface StringLib {
    String string(Object from);

    Boolean contains(String string, String match);

    Boolean startsWith(String string, String match);

    Boolean endsWith(String string, String match);

    Integer stringLength(String string);

    String substring(String string, Number startPosition);

    String substring(String string, Number startPosition, Number length);

    String upperCase(String string);

    String lowerCase(String string);

    String substringBefore(String string, String match);

    String substringAfter(String string, String match);

    String replace(String input, String pattern, String replacement, String flags) throws Exception;

    Boolean matches(String input, String pattern, String flags) throws Exception;

    List split(String string, String delimiter);
}
