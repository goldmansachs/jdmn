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

import java.math.BigDecimal;
import java.math.MathContext;

public class DefaultNumericType extends BaseNumericType implements NumericType<BigDecimal> {
    public static final MathContext MATH_CONTEXT = MathContext.DECIMAL128;

    public static BigDecimal decimalNumericDivide(BigDecimal first, BigDecimal second) {
        if (first == null || second == null) {
            return null;
        }
        if (BigDecimal.ZERO.equals(second)) {
            return null;
        }

        return first.divide(second, MathContext.DECIMAL128);
    }

    private final ComparableComparator<BigDecimal> comparator;

    @Deprecated
    public DefaultNumericType() {
        this(new ComparableComparator<>());
    }

    public DefaultNumericType(ComparableComparator<BigDecimal> comparator) {
        this.comparator = comparator;
    }

    @Override
    public boolean isNumber(Object value) {
        return value instanceof BigDecimal;
    }

    @Override
    public BigDecimal numericAdd(BigDecimal first, BigDecimal second) {
        if (first == null || second == null) {
            return null;
        }

        return first.add(second, MATH_CONTEXT);
    }

    @Override
    public BigDecimal numericSubtract(BigDecimal first, BigDecimal second) {
        if (first == null || second == null) {
            return null;
        }

        return first.subtract(second, MATH_CONTEXT);
    }

    @Override
    public BigDecimal numericMultiply(BigDecimal first, BigDecimal second) {
        if (first == null || second == null) {
            return null;
        }

        return first.multiply(second, MATH_CONTEXT);
    }

    @Override
    public BigDecimal numericDivide(BigDecimal first, BigDecimal second) {
        return decimalNumericDivide(first, second);
    }

    @Override
    public BigDecimal numericUnaryMinus(BigDecimal first) {
        if (first == null) {
            return null;
        }

        return first.negate(MATH_CONTEXT);
    }

    @Override
    public BigDecimal numericExponentiation(BigDecimal first, BigDecimal second) {
        if (first == null || second == null) {
            return null;
        }

        if (second.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0) {
            return numericExponentiation(first, second.intValue());
        } else {
            return BigDecimal.valueOf(Math.pow(first.doubleValue(), second.doubleValue()));
        }
    }

    public BigDecimal numericExponentiation(BigDecimal first, int second) {
        if (first == null) {
            return null;
        }

        if (second < 0) {
            return first.pow(second, MATH_CONTEXT);
        } else if (second == 0) {
            return BigDecimal.ONE;
        } else {
            BigDecimal temp = first.pow(-second, MATH_CONTEXT);
            return BigDecimal.ONE.divide(temp, MATH_CONTEXT);
        }
    }

    @Override
    public Boolean numericIs(BigDecimal first, BigDecimal second) {
        if (first == null || second == null) {
            return first == second;
        }

        return first.unscaledValue().equals(second.unscaledValue())
                && first.scale() == second.scale();
    }

    @Override
    public Boolean numericEqual(BigDecimal first, BigDecimal second) {
        return this.comparator.equalTo(first, second);
    }

    @Override
    public Boolean numericNotEqual(BigDecimal first, BigDecimal second) {
        return this.comparator.notEqualTo(first, second);
    }

    @Override
    public Boolean numericLessThan(BigDecimal first, BigDecimal second) {
        return this.comparator.lessThan(first, second);
    }

    @Override
    public Boolean numericGreaterThan(BigDecimal first, BigDecimal second) {
        return this.comparator.greaterThan(first, second);
    }

    @Override
    public Boolean numericLessEqualThan(BigDecimal first, BigDecimal second) {
        return this.comparator.lessEqualThan(first, second);
    }

    @Override
    public Boolean numericGreaterEqualThan(BigDecimal first, BigDecimal second) {
        return this.comparator.greaterEqualThan(first, second);
    }
}
