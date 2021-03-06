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

import com.gs.dmn.feel.lib.type.bool.TernaryBooleanLogicUtil;

public interface RelationalComparator<T> extends EqualityComparator<T> {
    Integer compare(T first, T second);

    Boolean lessThan(T first, T second);

    default Boolean greaterThan(T first, T second) {
        return lessThan(second, first);
    }

    default Boolean lessEqualThan(T first, T second) {
        return TernaryBooleanLogicUtil.getInstance().or(lessThan(first, second), equalTo(first, second));
    }

    default Boolean greaterEqualThan(T first, T second) {
        return TernaryBooleanLogicUtil.getInstance().or(greaterThan(first, second), equalTo(first, second));
    }
}
