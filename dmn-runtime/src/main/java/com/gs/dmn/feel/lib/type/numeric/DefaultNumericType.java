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

import com.gs.dmn.runtime.DMNRuntimeException;

import java.math.BigDecimal;

public class DefaultNumericType extends BaseNumericType implements NumericType<Number> {
    private static final DecimalNumericType DECIMAL_NUMERIC_TYPE = new DecimalNumericType();

    public static BigDecimal toDecimal(Number number) {
        if (number == null) {
            return null;
        } else if (number instanceof BigDecimal) {
            return (BigDecimal) number;
        } else if (number instanceof Short || number instanceof Integer) {
            return BigDecimal.valueOf(number.intValue());
        } else if (number instanceof Long) {
            return BigDecimal.valueOf(number.longValue());
        } else if (number instanceof Double) {
            return BigDecimal.valueOf(number.doubleValue());
        }
        throw new DMNRuntimeException(String.format("Not supported conversion of '%s' to decimal", number));
    }

    @Override
    public boolean isNumber(Object value) {
        return DECIMAL_NUMERIC_TYPE.isNumber(value);
    }

    @Override
    public Number numericValue(Number value) {
        return DECIMAL_NUMERIC_TYPE.numericValue(toDecimal(value));
    }

    @Override
    public Boolean numericIs(Number first, Number second) {
        return DECIMAL_NUMERIC_TYPE.numericIs(toDecimal(first), toDecimal(second));
    }

    @Override
    public Boolean numericEqual(Number first, Number second) {
        return DECIMAL_NUMERIC_TYPE.numericEqual(toDecimal(first), toDecimal(second));
    }

    @Override
    public Boolean numericNotEqual(Number first, Number second) {
        return DECIMAL_NUMERIC_TYPE.numericNotEqual(toDecimal(first), toDecimal(second));
    }

    @Override
    public Boolean numericLessThan(Number first, Number second) {
        return DECIMAL_NUMERIC_TYPE.numericLessThan(toDecimal(first), toDecimal(second));
    }

    @Override
    public Boolean numericGreaterThan(Number first, Number second) {
        return DECIMAL_NUMERIC_TYPE.numericGreaterThan(toDecimal(first), toDecimal(second));
    }

    @Override
    public Boolean numericLessEqualThan(Number first, Number second) {
        return DECIMAL_NUMERIC_TYPE.numericLessEqualThan(toDecimal(first), toDecimal(second));
    }

    @Override
    public Boolean numericGreaterEqualThan(Number first, Number second) {
        return DECIMAL_NUMERIC_TYPE.numericGreaterEqualThan(toDecimal(first), toDecimal(second));
    }

    @Override
    public Number numericAdd(Number first, Number second) {
        return DECIMAL_NUMERIC_TYPE.numericAdd(toDecimal(first), toDecimal(second));
    }

    @Override
    public Number numericSubtract(Number first, Number second) {
        return DECIMAL_NUMERIC_TYPE.numericSubtract(toDecimal(first), toDecimal(second));
    }

    @Override
    public Number numericMultiply(Number first, Number second) {
        return DECIMAL_NUMERIC_TYPE.numericMultiply(toDecimal(first), toDecimal(second));
    }

    @Override
    public Number numericDivide(Number first, Number second) {
        return DECIMAL_NUMERIC_TYPE.numericDivide(toDecimal(first), toDecimal(second));
    }

    @Override
    public Number numericUnaryMinus(Number first) {
        return DECIMAL_NUMERIC_TYPE.numericUnaryMinus(toDecimal(first));
    }

    @Override
    public Number numericExponentiation(Number first, Number second) {
        return DECIMAL_NUMERIC_TYPE.numericExponentiation(toDecimal(first), toDecimal(second));
    }
}
