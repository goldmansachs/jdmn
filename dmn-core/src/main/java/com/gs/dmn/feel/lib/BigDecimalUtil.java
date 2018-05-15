/**
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
package com.gs.dmn.feel.lib;

import com.gs.dmn.feel.lib.type.numeric.DefaultNumericType;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;

public class BigDecimalUtil {
    public static BigDecimal decimal(BigDecimal n, BigDecimal scale) {
        if (n == null || scale == null) {
            return null;
        }

        return n.setScale(scale.intValue(), RoundingMode.HALF_EVEN);
    }

    public static BigDecimal floor(BigDecimal number) {
        if (number == null) {
            return null;
        }

        return number.setScale(0, BigDecimal.ROUND_FLOOR);
    }

    public static BigDecimal ceiling(BigDecimal number) {
        if (number == null) {
            return null;
        }

        return number.setScale(0, BigDecimal.ROUND_CEILING);
    }

    public static BigDecimal abs(BigDecimal number) {
        if (number == null) {
            return null;
        }

        return number.abs();
    }

    public static BigDecimal modulo(BigDecimal divident, BigDecimal divisor) {
        if (divident == null || divisor == null) {
            return null;
        }

        return new BigDecimal(divident.toBigInteger().mod(divisor.toBigInteger()));
    }

    public static BigDecimal sqrt(BigDecimal number) {
        if (number == null) {
            return null;
        }

        double sqrt = Math.sqrt(number.doubleValue());
        return new BigDecimal(sqrt);
    }

    public static BigDecimal log(BigDecimal number) {
        if (number == null) {
            return null;
        }

        double sqrt = Math.log(number.doubleValue());
        return new BigDecimal(sqrt, DefaultNumericType.MATH_CONTEXT);
    }

    public static BigDecimal exp(BigDecimal number) {
        if (number == null) {
            return null;
        }

        double sqrt = Math.exp(number.doubleValue());
        return new BigDecimal(sqrt, DefaultNumericType.MATH_CONTEXT);
    }

    public static Boolean odd(BigDecimal number) {
        if (number == null || !isIntegerValue(number)) {
            return null;
        }

        return number.intValue() % 2 != 0;
    }

    public static Boolean even(BigDecimal number) {
        if (number == null || !isIntegerValue(number)) {
            return null;
        }

        return number.intValue() % 2 == 0;
    }

    private static boolean isIntegerValue(BigDecimal bd) {
        boolean result;
        try {
            bd.toBigIntegerExact();
            result = true;
        } catch (ArithmeticException ex) {
            result = false;
        }
        return result;
    }

    //
    // List functions
    //
    public static BigDecimal min(List list) {
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

    public static BigDecimal max(List list) {
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
    public static BigDecimal sum(List list) {
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

    public static BigDecimal mean(List list) {
        if (list == null) {
            return null;
        }

        BigDecimal sum = sum(list);
        return numericDivide(sum, BigDecimal.valueOf(list.size()));
    }

    public static BigDecimal product(List list) {
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

    public static BigDecimal median(List list) {
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

    public static BigDecimal stddev(List list) {
        if (list == null || list.isEmpty()) {
            return null;
        }

        BigDecimal mean = mean(list);
        BigDecimal length = BigDecimal.valueOf(list.size());
        BigDecimal variance = BigDecimal.ZERO;
        for(Object e: list) {
            BigDecimal number = (BigDecimal) e;
            BigDecimal dm = number.subtract(mean);
            BigDecimal dv = dm.multiply(dm);
            variance = variance.add(dv);
        }
        variance = variance.divide(length);
        BigDecimal stddev = sqrt(variance);
        return stddev;
    }

    public static List mode(List list) {
        if (list == null || list.isEmpty()) {
            return null;
        }

        int max = -1;
        List modes = new ArrayList();
        Map<Object, Integer> countMap = new HashMap<Object, Integer>();
        for (Object n : list) {
            int count = 0;

            if (countMap.containsKey(n)) {
                count = countMap.get(n) + 1;
            } else {
                count = 1;
            }
            countMap.put(n, count);

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

    public static BigDecimal numericDivide(BigDecimal first, BigDecimal second) {
        if (first == null || second == null) {
            return null;
        }
        if (BigDecimal.ZERO.equals(second)) {
            return null;
        }

        return first.divide(second, MathContext.DECIMAL128);
    }

}
