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
package com.gs.dmn.feel.lib.type;

import java.util.function.Supplier;

public interface EqualityComparator<T> {
    Boolean equalTo(T first, T second);

    default Boolean notEqualTo(T first, T second) {
        Boolean equal = equalTo(first, second);
        return equal == null ? null : ! equal;
    }

    default Boolean applyOperator(T first, T second, Supplier<Boolean>[] result) {
        if (first == null && second == null) {
            return result[0].get();
        } else if (first == null) {
            return result[1].get();
        } else if (second == null) {
            return result[2].get();
        } else {
            return result[3].get();
        }
    }
}
