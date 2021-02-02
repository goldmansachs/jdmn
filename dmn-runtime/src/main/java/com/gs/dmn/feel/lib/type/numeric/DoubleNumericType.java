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
package com.gs.dmn.feel.lib.type.numeric;

import com.gs.dmn.feel.lib.type.ComparableComparator;

public class DoubleNumericType extends BaseNumericType implements NumericType<Double> {
    public static Double doubleNumericDivide(Double first, Double second) {
        if (first == null || second == null) {
            return null;
        }
        if (second == 0.0) {
            return null;
        }

        return first / second;
    }

    private final ComparableComparator<Double> comparator;

    @Deprecated
    public DoubleNumericType() {
        this(new ComparableComparator<>());
    }

    public DoubleNumericType(ComparableComparator<Double> comparator) {
        this.comparator = comparator;
    }

    @Override
    public boolean isNumber(Object value) {
        return value instanceof Double;
    }

    @Override
    public Double numericAdd(Double first, Double second) {
        if (first == null || second == null) {
            return null;
        }

        return first + second;
    }

    @Override
    public Double numericSubtract(Double first, Double second) {
        if (first == null || second == null) {
            return null;
        }

        return first - second;
    }

    @Override
    public Double numericMultiply(Double first, Double second) {
        if (first == null || second == null) {
            return null;
        }

        return first * second;
    }

    @Override
    public Double numericDivide(Double first, Double second) {
        return doubleNumericDivide(first, second);
    }

    @Override
    public Double numericUnaryMinus(Double first) {
        if (first == null) {
            return null;
        }

        return - first;
    }

    @Override
    public Double numericExponentiation(Double first, Double second) {
        if (first == null || second == null) {
            return null;
        }

        return Math.pow(first, second);
    }

    @Override
    public Boolean numericIs(Double first, Double second) {
        if (first == null || second == null) {
            return first == second;
        }

        return first.equals(second);
    }

    @Override
    public Boolean numericEqual(Double first, Double second) {
        return this.comparator.equalTo(first, second);
    }

    @Override
    public Boolean numericNotEqual(Double first, Double second) {
        return this.comparator.notEqualTo(first, second);
    }

    @Override
    public Boolean numericLessThan(Double first, Double second) {
        return this.comparator.lessThan(first, second);
    }

    @Override
    public Boolean numericGreaterThan(Double first, Double second) {
        return this.comparator.greaterThan(first, second);
    }

    @Override
    public Boolean numericLessEqualThan(Double first, Double second) {
        return this.comparator.lessEqualThan(first, second);
    }

    @Override
    public Boolean numericGreaterEqualThan(Double first, Double second) {
        return this.comparator.greaterEqualThan(first, second);
    }
}
