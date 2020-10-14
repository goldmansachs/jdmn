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

import com.gs.dmn.feel.lib.type.BaseType;
import com.gs.dmn.feel.lib.type.ComparableComparator;
import com.gs.dmn.feel.lib.type.NumericType;
import org.slf4j.Logger;

public class DoubleNumericType extends BaseType implements NumericType<Double> {
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
    public DoubleNumericType(Logger logger) {
        this(logger, new ComparableComparator<>());
    }

    public DoubleNumericType(Logger logger, ComparableComparator<Double> comparator) {
        super(logger);
        this.comparator = comparator;
    }

    @Override
    public Double numericAdd(Double first, Double second) {
        if (first == null || second == null) {
            return null;
        }

        try {
            return first + second;
        } catch (Exception e) {
            String message = String.format("numericAdd(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double numericSubtract(Double first, Double second) {
        if (first == null || second == null) {
            return null;
        }

        try {
            return first - second;
        } catch (Exception e) {
            String message = String.format("numericSubtract(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double numericMultiply(Double first, Double second) {
        if (first == null || second == null) {
            return null;
        }

        try {
            return first * second;
        } catch (Exception e) {
            String message = String.format("numericMultiply(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double numericDivide(Double first, Double second) {
        try {
            return doubleNumericDivide(first, second);
        } catch (Exception e) {
            String message = String.format("numericDivide(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double numericUnaryMinus(Double first) {
        if (first == null) {
            return null;
        }

        try {
            return - first;
        } catch (Exception e) {
            String message = String.format("numericUnaryMinus(%s)", first);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double numericExponentiation(Double first, Double second) {
        if (first == null || second == null) {
            return null;
        }

        try {
            return Math.pow(first, second);
        } catch (Exception e) {
            String message = String.format("numericExponentiation(%s, %s)", first, second);
            logError(message, e);
            return null;
        }
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
