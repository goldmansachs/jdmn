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

public class DoubleSignavioNumberLib implements SignavioNumberLib<Double> {
    @Override
    public Double number(String literal) {
        if (!SignavioUtil.areNullSafe(literal)) {
            return null;
        }

        return Double.parseDouble(literal);
    }

    @Override
    public Double number(String text, Double defaultValue) {
        if (!SignavioUtil.areNullSafe(text, defaultValue)) {
            return null;
        }

        Double number = null;
        try {
            number = number(text);
        } catch (Exception e) {
        }
        return number != null ? number : defaultValue;
    }

    @Override
    public Double abs(Double number) {
        if (!SignavioUtil.areNullSafe(number)) {
            return null;
        }

        return Math.abs(number);
    }

    @Override
    public Double count(List<?> list) {
        if (!SignavioUtil.areNullSafe(list)) {
            return null;
        }

        return (double) list.size();
    }

    @Override
    public Double round(Double number, Double digits) {
        if (!SignavioUtil.areNullSafe(number, digits)) {
            return null;
        }

        return BigDecimal.valueOf(number).setScale(digits.intValue(), RoundingMode.HALF_EVEN).doubleValue();
    }

    @Override
    public Double ceiling(Double number) {
        if (!SignavioUtil.areNullSafe(number)) {
            return null;
        }

        return BigDecimal.valueOf(number).setScale(0, RoundingMode.CEILING).doubleValue();
    }

    @Override
    public Double floor(Double number) {
        if (!SignavioUtil.areNullSafe(number)) {
            return null;
        }

        return BigDecimal.valueOf(number).setScale(0, RoundingMode.FLOOR).doubleValue();
    }

    @Override
    public Double integer(Double number) {
        if (!SignavioUtil.areNullSafe(number)) {
            return null;
        }

        return (double) number.intValue();
    }

    @Override
    public Double modulo(Double dividend, Double divisor) {
        if (!SignavioUtil.areNullSafe(dividend, divisor) || divisor == 0) {
            return null;
        }

        return dividend % divisor;
    }

    @Override
    public Double power(Double base, Double exponent) {
        if (!SignavioUtil.areNullSafe(base, exponent)) {
            return null;
        }

        return Math.pow(base, exponent.intValue());
    }

    @Override
    public Double percent(Double number) {
        if (!SignavioUtil.areNullSafe(number)) {
            return null;
        }

        return number / 100;
    }

    @Override
    public Double product(List<?> numbers) {
        if (!SignavioUtil.verifyValidityOfListArgument(numbers)) {
            return null;
        }

        Double result = 1.0;
        for (Double number : SignavioUtil.asDoubles(numbers)) {
            result *= number;
        }
        return result;
    }

    @Override
    public Double roundDown(Double number, Double digits) {
        if (!SignavioUtil.areNullSafe(number)) {
            return null;
        }

        return BigDecimal.valueOf(number).setScale(digits.intValue(), RoundingMode.DOWN).doubleValue();
    }

    @Override
    public Double roundUp(Double number, Double digits) {
        if (!SignavioUtil.areNullSafe(number)) {
            return null;
        }

        return BigDecimal.valueOf(number).setScale(digits.intValue(), RoundingMode.UP).doubleValue();
    }

    @Override
    public Double sum(List<?> list) {
        if (!SignavioUtil.verifyValidityOfListArgument(list)) {
            return null;
        }

        Double result = 0.0;
        for (Double e : SignavioUtil.asDoubles(list)) {
            result += e;
        }
        return result;
    }

    @Override
    public Double avg(List<?> numbers) {
        if (numbers == null) {
            return null;
        }

        return this.sum(numbers) / numbers.size();
    }

    @Override
    public Double max(List<?> list) {
        if (!SignavioUtil.verifyValidityOfListArgument(list)) {
            return null;
        }

        Double result = (Double) list.get(0);
        for (int i = 1; i < list.size(); i++) {
            Double x = (Double) list.get(i);
            if (result.compareTo(x) < 0) {
                result = x;
            }
        }
        return result;
    }

    @Override
    public Double median(List<?> list) {
        if (!SignavioUtil.verifyValidityOfListArgument(list)) {
            return null;
        }

        Collections.sort((List<Double>)list);
        Double median;
        int size = list.size();
        if (size % 2 == 0) {
            Double first = (Double) list.get(size / 2);
            Double second = (Double) list.get(size / 2 - 1);
            median = (first + second) / 2;
        } else {
            median = (Double) list.get(size / 2);
        }
        return median;
    }

    @Override
    public Double min(List<?> list) {
        if (!SignavioUtil.verifyValidityOfListArgument(list)) {
            return null;
        }

        List<Double> numbers = SignavioUtil.asDoubles(list);
        Double result = numbers.get(0);
        for (Double number: numbers) {
            if (number < result) {
                result = number;
            }
        }
        return result;
    }

    public Double mode(List<?> list) {
        if (!SignavioUtil.verifyValidityOfListArgument(list)) {
            return null;
        }

        Map<Object, Integer> map = new LinkedHashMap<>();
        int top = -1;
        List<Double> numbers = SignavioUtil.asDoubles(list);
        Double returnValue = numbers.get(0);
        for (Double value : numbers) {
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
    public Double valueOf(long number) {
        return (double) number;
    }

    @Override
    public int intValue(Double number) {
        return number.intValue();
    }

    @Override
    public Number toNumber(Double number) {
        return number;
    }
}
