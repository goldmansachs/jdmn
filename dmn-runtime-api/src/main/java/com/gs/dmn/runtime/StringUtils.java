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
package com.gs.dmn.runtime;

public final class StringUtils {

    private StringUtils() {
    }

    public static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    public static boolean isEmpty(String value) {
        return value == null || value.isEmpty();
    }

    public static boolean isAlpha(String value) {
        return value != null && !value.isEmpty() && value.codePoints().allMatch(Character::isLetter);
    }

    public static boolean isAlphanumeric(String value) {
        return value != null && !value.isEmpty() && value.codePoints().allMatch(Character::isLetterOrDigit);
    }

    public static boolean isNumeric(String value) {
        return value != null && !value.isEmpty() && value.codePoints().allMatch(Character::isDigit);
    }
}
