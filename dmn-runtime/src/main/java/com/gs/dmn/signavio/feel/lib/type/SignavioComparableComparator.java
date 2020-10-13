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
package com.gs.dmn.signavio.feel.lib.type;

import com.gs.dmn.feel.lib.type.ComparableComparator;

public class SignavioComparableComparator<T> extends ComparableComparator<T> {
    @Override
    public Boolean lessEqualThan(Comparable<T> first, Comparable<T> second) {
        if (first == null && second == null) {
            return null;
        } else {
            return super.lessEqualThan(first, second);
        }
    }

    @Override
    public Boolean greaterEqualThan(Comparable<T> first, Comparable<T> second) {
        if (first == null && second == null) {
            return null;
        } else {
            return super.greaterEqualThan(first, second);
        }
    }
}
