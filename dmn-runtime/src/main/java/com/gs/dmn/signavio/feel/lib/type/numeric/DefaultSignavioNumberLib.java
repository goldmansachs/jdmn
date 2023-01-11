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
        if (literal == null) {
            return null;
        }

        return new BigDecimal(literal, MATH_CONTEXT);
    }

    @Override
    public BigDecimal number(String text, BigDecimal defaultValue) {
        if (text == null || defaultValue ==  null) {
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
        if (number == null) {
            return null;
        }

        return number.abs();
    }

    @Override
    public BigDecimal count(List<?> list) {
        if (list == null) {
            return null;
        }

        return BigDecimal.valueOf(list.size());
    }

    @Override
    public BigDecimal round(BigDecimal number, BigDecimal digits) {
        if (number == null || digits == null) {
            return null;
        }

        return number.setScale(digits.intValue(), RoundingMode.HALF_EVEN);
    }

    @Override
    public BigDecimal ceiling(BigDecimal number) {
        if (number == null) {
            return null;
        }

        return number.setScale(0, RoundingMode.CEILING);
    }

    @Override
    public BigDecimal floor(BigDecimal number) {
        if (number == null) {
            return null;
        }

        return number.setScale(0, RoundingMode.FLOOR);
    }

    @Override
    public BigDecimal integer(BigDecimal number) {
        if (number == null) {
            return null;
        }

        return BigDecimal.valueOf(number.intValue());
    }

    @Override
    public BigDecimal modulo(BigDecimal dividend, BigDecimal divisor) {
        if (dividend == null || divisor == null) {
            return null;
        }

        return dividend.remainder(divisor, MATH_CONTEXT);
    }

    @Override
    public BigDecimal power(BigDecimal base, BigDecimal exponent) {
        if (base == null || exponent == null) {
            return null;
        }

        return base.pow(exponent.intValue(), MATH_CONTEXT);
    }

    @Override
    public BigDecimal percent(BigDecimal number) {
        if (number == null) {
            return null;
        }

        return number.divide(BigDecimal.valueOf(100), MATH_CONTEXT);
    }

    @Override
    public BigDecimal product(List<?> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return null;
        }

        BigDecimal result = BigDecimal.valueOf(1);
        for (Object e : numbers) {
            BigDecimal number = (BigDecimal) e;
            result = result.multiply(number);
        }
        return result;
    }

    @Override
    public BigDecimal roundDown(BigDecimal number, BigDecimal digits) {
        if (number == null || digits == null) {
            return null;
        }

        return number.setScale(digits.intValue(), RoundingMode.DOWN);
    }

    @Override
    public BigDecimal roundUp(BigDecimal number, BigDecimal digits) {
        if (number == null || digits == null) {
            return null;
        }

        return number.setScale(digits.intValue(), RoundingMode.UP);
    }

    @Override
    public BigDecimal sum(List<?> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }

        BigDecimal result = BigDecimal.valueOf(0);
        for (Object e : list) {
            BigDecimal number = (BigDecimal) e;
            result = result.add(number);
        }
        return result;
    }

    @Override
    public BigDecimal avg(List<?> numbers) {
        if (numbers == null) {
            return null;
        }

        return this.sum(numbers).divide(BigDecimal.valueOf(numbers.size()), MATH_CONTEXT);
    }

    @Override
    public BigDecimal max(List<?> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }

        BigDecimal result = (BigDecimal) list.get(0);
        for (int i = 1; i < list.size(); i++) {
            BigDecimal x = (BigDecimal) list.get(i);
            if (result.compareTo(x) < 0) {
                result = x;
            }
        }
        return result;
    }

    @Override
    public BigDecimal median(List<?> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }

        Collections.sort((List<BigDecimal>)list);
        BigDecimal median;
        int size = list.size();
        if (size % 2 == 0) {
            BigDecimal first = (BigDecimal) list.get(size / 2);
            BigDecimal second = (BigDecimal) list.get(size / 2 - 1);
            median = first.add(second).divide(BigDecimal.valueOf(2), MATH_CONTEXT);
        } else {
            median = (BigDecimal) list.get(size / 2);
        }
        return median;
    }

    @Override
    public BigDecimal min(List<?> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }

        BigDecimal result = (BigDecimal) list.get(0);
        for (int i = 1; i < list.size(); i++) {
            BigDecimal x = (BigDecimal) list.get(i);
            if (result.compareTo(x) > 0) {
                result = x;
            }
        }
        return result;
    }

    @Override
    public BigDecimal mode(List<?> numbers) {
        Map<Object, Integer> map = new LinkedHashMap<>();
        for (Object n : numbers) {
            if (n == null) {
                return null;
            }
            Integer counter = map.get(n);
            if (counter == null) {
                counter = 1;
            } else {
                counter++;
            }
            map.put(n, counter);
        }
        Object resultKey = null;
        Integer resultCounter = null;
        for (Map.Entry<Object, Integer> entry : map.entrySet()) {
            Object key = entry.getKey();
            Integer counter = entry.getValue();
            if (resultCounter == null || counter > resultCounter) {
                resultKey = key;
                resultCounter = counter;
            }
        }
        return (BigDecimal) resultKey;
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
