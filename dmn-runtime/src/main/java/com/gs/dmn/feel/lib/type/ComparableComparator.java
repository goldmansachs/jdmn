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

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class ComparableComparator<T> implements RelationalComparator<Comparable<T>> {
    @Override
    public Integer compare(Comparable<T> first, Comparable<T> second) {
        return first.compareTo((T) second);
    }

    @Override
    public Boolean equal(Comparable<T> first, Comparable<T> second) {
        return applyOperator(first, second, new Supplier[] {
                () -> TRUE,
                () -> FALSE,
                () -> FALSE,
                () -> first.compareTo((T) second) == 0
        });
    }

    @Override
    public Boolean lessThan(Comparable<T> first, Comparable<T> second) {
        if (first == null && second == null) {
            return null;
        } else if (first == null) {
            return null;
        } else if (second == null) {
            return null;
        } else {
            int result = first.compareTo((T) second);
            return result < 0;
        }
    }

    @Override
    public Boolean greaterThan(Comparable<T> first, Comparable<T> second) {
        if (first == null && second == null) {
            return null;
        } else if (first == null) {
            return null;
        } else if (second == null) {
            return null;
        } else {
            int result = first.compareTo((T) second);
            return result > 0;
        }
    }

    @Override
    public Boolean lessEqualThan(Comparable<T> first, Comparable<T> second) {
        if (first == null && second == null) {
            return true;
        } else if (first == null) {
            return null;
        } else if (second == null) {
            return null;
        } else {
            int result = first.compareTo((T) second);
            return result <= 0;
        }
    }

    @Override
    public Boolean greaterEqualThan(Comparable<T> first, Comparable<T> second) {
        if (first == null && second == null) {
            return true;
        } else if (first == null) {
            return null;
        } else if (second == null) {
            return null;
        } else {
            int result = first.compareTo((T) second);
            return result >= 0;
        }
    }
}
