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
package com.gs.dmn.validation;

import java.util.List;

import static com.gs.dmn.NameUtils.toHyphenSeparated;

public interface TestValidator<TEST> {
    default String ruleName() {
        String clsName = this.getClass().getSimpleName();
        return toHyphenSeparated(clsName);
    }

    default <E> List<E> validate(List<TEST> testCasesList) {
        List<E> errors = new java.util.ArrayList<>();
        for (TEST testCases : testCasesList) {
            if (!isEmpty(testCases)) {
                errors.addAll(validate(testCases));
            }
        }
        return errors;
    }

    default boolean isEmpty(TEST testCases) {
        return testCases == null;
    }

    <E> List<E> validate(TEST test);
}
