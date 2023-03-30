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
package com.gs.dmn.signavio.feel.lib.type.numeric;

import com.gs.dmn.signavio.feel.lib.SignavioUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.gs.dmn.feel.lib.type.numeric.DefaultNumericType.MATH_CONTEXT;

public class DefaultSignavioNumberLib implements SignavioNumberLib<BigDecimal> {
    @Override
    public BigDecimal number(String literal) {
        if (!SignavioUtil.areNullSafe(literal)) {
            return null;
        }

        return new BigDecimal(literal, MATH_CONTEXT);
    }

    @Override
    public BigDecimal number(String text, BigDecimal defaultValue) {
        if (!SignavioUtil.areNullSafe(text, defaultValue)) {
            return null;
        }

        BigDecimal number = null;
        try {
            number = number(text);
        } catch (Exception e) {
        }
        return number != null ? number : defaultValue;
    }

    @Override
    public BigDecimal abs(BigDecimal number) {
        if (!SignavioUtil.areNullSafe(number)) {
            return null;
        }

        return number.abs();
    }

    @Override
    public BigDecimal count(List<?> list) {
        if (!SignavioUtil.areNullSafe(list)) {
            return null;
        }

        return BigDecimal.valueOf(list.size());
    }

    @Override
    public BigDecimal round(BigDecimal number, BigDecimal digits) {
        if (!SignavioUtil.areNullSafe(number, digits)) {
            return null;
        }

        return number.setScale(digits.intValue(), RoundingMode.HALF_EVEN);
    }

    @Override
    public BigDecimal ceiling(BigDecimal number) {
        if (!SignavioUtil.areNullSafe(number)) {
            return null;
        }

        return number.setScale(0, RoundingMode.CEILING);
    }

    @Override
    public BigDecimal floor(BigDecimal number) {
        if (!SignavioUtil.areNullSafe(number)) {
            return null;
        }

        return number.setScale(0, RoundingMode.FLOOR);
    }

    @Override
    public BigDecimal integer(BigDecimal number) {
        if (!SignavioUtil.areNullSafe(number)) {
            return null;
        }

        return BigDecimal.valueOf(number.intValue());
    }

    @Override
    public BigDecimal modulo(BigDecimal dividend, BigDecimal divisor) {
        if (!SignavioUtil.areNullSafe(dividend, divisor) || divisor.compareTo(BigDecimal.ZERO) == 0) {
            return null;
        }

        return dividend.remainder(divisor, MATH_CONTEXT);
    }

    @Override
    public BigDecimal power(BigDecimal base, BigDecimal exponent) {
        if (!SignavioUtil.areNullSafe(base, exponent)) {
            return null;
        }

        return base.pow(exponent.intValue(), MATH_CONTEXT);
    }

    @Override
    public BigDecimal percent(BigDecimal number) {
        if (!SignavioUtil.areNullSafe(number)) {
            return null;
        }

        return number.divide(BigDecimal.valueOf(100), MATH_CONTEXT);
    }

    @Override
    public BigDecimal product(List<?> numbers) {
        if (!SignavioUtil.verifyValidityOfListArgument(numbers)) {
            return null;
        }

        BigDecimal result = BigDecimal.valueOf(1);
        for (BigDecimal number : SignavioUtil.asBigDecimals(numbers)) {
            result = result.multiply(number);
        }
        return result;
    }

    @Override
    public BigDecimal roundDown(BigDecimal number, BigDecimal digits) {
        if (!SignavioUtil.areNullSafe(number, digits)) {
            return null;
        }

        return number.setScale(digits.intValue(), RoundingMode.DOWN);
    }

    @Override
    public BigDecimal roundUp(BigDecimal number, BigDecimal digits) {
        if (!SignavioUtil.areNullSafe(number, digits)) {
            return null;
        }

        return number.setScale(digits.intValue(), RoundingMode.UP);
    }

    @Override
    public BigDecimal sum(List<?> numbers) {
        if (!SignavioUtil.verifyValidityOfListArgument(numbers)) {
            return null;
        }

        BigDecimal result = BigDecimal.ZERO;
        for (BigDecimal number : SignavioUtil.asBigDecimals(numbers)) {
            result = result.add(number);
        }
        return result;
    }

    @Override
    public BigDecimal avg(List<?> numbers) {
        if (!SignavioUtil.verifyValidityOfListArgument(numbers)) {
            return null;
        }

        return this.sum(numbers).divide(BigDecimal.valueOf(numbers.size()), MATH_CONTEXT);
    }

    @Override
    public BigDecimal max(List<?> numbers) {
        if (!SignavioUtil.verifyValidityOfListArgument(numbers)) {
            return null;
        }

        List<BigDecimal> decimals = SignavioUtil.asBigDecimals(numbers);
        BigDecimal result = decimals.get(0);
        for (BigDecimal number: decimals) {
            if (number.compareTo(result) > 0) {
                result = number;
            }
        }
        return result;
    }

    @Override
    public BigDecimal median(List<?> numbers) {
        if (!SignavioUtil.verifyValidityOfListArgument(numbers)) {
            return null;
        }

        List<BigDecimal> decimals = SignavioUtil.asBigDecimals(numbers);
        if (decimals.size() == 1) {
            return decimals.get(0);
        } else {
            Collections.sort(decimals);
            int size = decimals.size();
            int index = size / 2;
            if (size % 2 == 0) {
                BigDecimal lowerMedian = decimals.get(index - 1);
                BigDecimal upperMedian = decimals.get(index);
                return lowerMedian.add(upperMedian).divide(new BigDecimal(2), MATH_CONTEXT);
            } else {
                return decimals.get(index);
            }
        }
    }

    @Override
    public BigDecimal min(List<?> list) {
        if (!SignavioUtil.verifyValidityOfListArgument(list)) {
            return null;
        }

        List<BigDecimal> decimals = SignavioUtil.asBigDecimals(list);
        BigDecimal result = decimals.get(0);
        for (BigDecimal number: decimals) {
            if (number.compareTo(result) < 0) {
                result = number;
            }
        }
        return result;
    }

    @Override
    public BigDecimal mode(List<?> numbers) {
        if (!SignavioUtil.verifyValidityOfListArgument(numbers)) {
            return null;
        }

        Map<Object, Integer> map = new LinkedHashMap<>();
        int top = -1;
        List<BigDecimal> decimals = SignavioUtil.asBigDecimals(numbers);
        BigDecimal returnValue = decimals.get(0);
        for (BigDecimal value : decimals) {
            Integer count = map.get(value);
            int currentCount = count == null ? 1 : count + 1;
            map.put(value, currentCount);
            if (currentCount > top) {
                top = currentCount;
                returnValue = value;
            }
        }
        return returnValue;
    }

    @Override
    public BigDecimal valueOf(long number) {
        return BigDecimal.valueOf(number);
    }

    @Override
    public int intValue(BigDecimal number) {
        return number.intValue();
    }

    @Override
    public Number toNumber(BigDecimal number) {
        return number;
    }
}
