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
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;

public class DefaultNumericLib extends BaseNumericLib<BigDecimal> implements NumericLib<BigDecimal> {
    @Override
    public BigDecimal number(String literal) {
        if (StringUtils.isBlank(literal)) {
            return null;
        }

        return new BigDecimal(literal, DefaultNumericType.MATH_CONTEXT);
    }

    @Override
    public BigDecimal decimal(BigDecimal n, BigDecimal scale) {
        if (n == null || scale == null) {
            return null;
        }

        return n.setScale(scale.intValue(), RoundingMode.HALF_EVEN);
    }

    @Override
    public BigDecimal round(BigDecimal n, BigDecimal scale, String mode) {
        if (n == null || scale == null || mode == null) {
            return null;
        }

        RoundingMode roundingMode = NumericRoundingMode.fromValue(mode);
        if (roundingMode == null) {
            throw new DMNRuntimeException(String.format("Unknown rounding mode '%s'. Expected one of '%s'", mode, NumericRoundingMode.ALLOWED_VALUES));
        } else {
            return n.setScale(scale.intValue(), roundingMode);
        }
    }

    @Override
    public BigDecimal floor(BigDecimal number) {
        if (number == null) {
            return null;
        }

        return number.setScale(0, RoundingMode.FLOOR);
    }

    @Override
    public BigDecimal ceiling(BigDecimal number) {
        if (number == null) {
            return null;
        }

        return number.setScale(0, RoundingMode.CEILING);
    }

    @Override
    public BigDecimal abs(BigDecimal number) {
        if (number == null) {
            return null;
        }

        return number.abs();
    }

    @Override
    public BigDecimal intModulo(BigDecimal dividend, BigDecimal divisor) {
        if (dividend == null || divisor == null) {
            return null;
        }

        return new BigDecimal(dividend.toBigInteger().remainder(divisor.toBigInteger()));
    }

    @Override
    public BigDecimal modulo(BigDecimal dividend, BigDecimal divisor) {
        if (dividend == null || divisor == null) {
            return null;
        }

        // dividend - divisor*floor(dividend/divisor)
        return dividend.subtract(divisor.multiply(floor(DefaultNumericType.decimalNumericDivide(dividend, divisor))));
    }

    @Override
    public BigDecimal sqrt(BigDecimal number) {
        if (number == null) {
            return null;
        }

        double sqrt = Math.sqrt(number.doubleValue());
        return BigDecimal.valueOf(sqrt);
    }

    @Override
    public BigDecimal log(BigDecimal number) {
        if (number == null) {
            return null;
        }

        double sqrt = Math.log(number.doubleValue());
        return BigDecimal.valueOf(sqrt);
    }

    @Override
    public BigDecimal exp(BigDecimal number) {
        if (number == null) {
            return null;
        }

        double sqrt = Math.exp(number.doubleValue());
        return BigDecimal.valueOf(sqrt);
    }

    @Override
    public Boolean odd(BigDecimal number) {
        if (!isIntegerValue(number)) {
            return null;
        }

        return number.intValue() % 2 != 0;
    }

    @Override
    public Boolean even(BigDecimal number) {
        if (!isIntegerValue(number)) {
            return null;
        }

        return number.intValue() % 2 == 0;
    }

    private boolean isIntegerValue(BigDecimal bd) {
        return bd != null && bd.stripTrailingZeros().scale() <= 0;
    }

    //
    // List functions
    //
    @Override
    public BigDecimal count(List list) {
        return list == null ? BigDecimal.valueOf(0) : BigDecimal.valueOf(list.size());
    }

    @Override
    public BigDecimal min(List list) {
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
    public BigDecimal max(List list) {
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
    public BigDecimal sum(List list) {
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
    public BigDecimal mean(List list) {
        if (list == null) {
            return null;
        }

        BigDecimal sum = sum(list);
        return DefaultNumericType.decimalNumericDivide(sum, BigDecimal.valueOf(list.size()));
    }

    @Override
    public BigDecimal product(List list) {
        if (list == null || list.isEmpty()) {
            return null;
        }

        BigDecimal result = BigDecimal.valueOf(1);
        for (Object e : list) {
            BigDecimal number = (BigDecimal) e;
            result = result.multiply(number);
        }
        return result;
    }

    @Override
    public BigDecimal median(List list) {
        if (list == null || list.isEmpty()) {
            return null;
        }

        Collections.sort(list);
        BigDecimal median;
        int size = list.size();
        if (size % 2 == 0) {
            BigDecimal first = (BigDecimal) list.get(size / 2);
            BigDecimal second = (BigDecimal) list.get(size / 2 - 1);
            median = first.add(second).divide(BigDecimal.valueOf(2));
        } else {
            median = (BigDecimal) list.get(size / 2);
        }
        return median;
    }

    @Override
    public BigDecimal stddev(List list) {
        if (list == null || list.isEmpty()) {
            return null;
        }

        BigDecimal mean = mean(list);
        BigDecimal length = BigDecimal.valueOf((long) list.size() - 1);
        BigDecimal variance = BigDecimal.ZERO;
        for(Object e: list) {
            BigDecimal number = (BigDecimal) e;
            BigDecimal dm = number.subtract(mean);
            BigDecimal dv = dm.multiply(dm);
            variance = variance.add(dv);
        }
        variance = variance.divide(length, MathContext.DECIMAL128);
        BigDecimal stddev = sqrt(variance);
        return stddev;
    }

    @Override
    public List mode(List list) {
        if (list == null) {
            return null;
        }
        if (list.isEmpty()) {
            return new ArrayList();
        }

        int max = -1;
        List modes = new ArrayList();
        Map<Object, Integer> countMap = new HashMap<>();
        for (Object n : list) {
            if (! (n instanceof Number)) {
                return null;
            }
            int count = countMap.compute(n, (k, v) -> v == null ? 1 : v + 1);
            if (count > max) {
                max = count;
            }
        }

        for (Map.Entry<Object, Integer> tuple : countMap.entrySet()) {
            if (tuple.getValue() == max) {
                modes.add(tuple.getKey());
            }
        }

        Collections.sort(modes);
        return modes;
    }

    @Override
    public Number toNumber(BigDecimal number) {
        return number;
    }
}
