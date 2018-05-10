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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class DoubleUtil {
    public static Double decimal(Double n, Double scale) {
        if (n == null || scale == null) {
            return null;
        }

        return BigDecimal.valueOf(n).setScale(scale.intValue(), RoundingMode.HALF_EVEN).doubleValue();
    }

    public static Double floor(Double number) {
        if (number == null) {
            return null;
        }

        return BigDecimal.valueOf(number).setScale(0, BigDecimal.ROUND_FLOOR).doubleValue();
    }

    public static Double ceiling(Double number) {
        if (number == null) {
            return null;
        }

        return BigDecimal.valueOf(number).setScale(0, BigDecimal.ROUND_CEILING).doubleValue();
    }

    public static Double abs(Double number) {
        if (number == null) {
            return null;
        }

        return Math.abs(number);
    }

    public static Double modulo(Double divident, Double divisor) {
        if (divident == null || divisor == null) {
            return null;
        }

        return Double.valueOf(divident.intValue() % divisor.intValue());
    }

    public static Double sqrt(Double number) {
        if (number == null) {
            return null;
        }

        return Math.sqrt(number);
    }

    public static Double log(Double number) {
        if (number == null) {
            return null;
        }

        return Math.log(number);
    }

    public static Double exp(Double number) {
        if (number == null) {
            return null;
        }

        return Math.exp(number);
    }

    public static Boolean odd(Double number) {
        if (number == null || !isIntegerValue(number)) {
            return null;
        }

        return number.intValue() % 2 != 0;
    }

    public static Boolean even(Double number) {
        if (number == null || !isIntegerValue(number)) {
            return null;
        }

        return number.intValue() % 2 == 0;
    }

    private static boolean isIntegerValue(Double d) {
        if (d == null) {
            return false;
        } else {
            return d == d.intValue();
        }
    }

    //
    // List functions
    //
    public static Double min(List list) {
        if (list == null || list.isEmpty()) {
            return null;
        }

        Double result = (Double) list.get(0);
        for (int i = 1; i < list.size(); i++) {
            Double x = (Double) list.get(i);
            if (result.compareTo(x) > 0) {
                result = x;
            }
        }
        return result;
    }

    public static Double max(List list) {
        Double result = (Double) list.get(0);
        for (int i = 1; i < list.size(); i++) {
            Double x = (Double) list.get(i);
            if (result.compareTo(x) < 0) {
                result = x;
            }
        }
        return result;
    }

    public static Double sum(List list) {
        if (list == null || list.isEmpty()) {
            return null;
        }

        Double result = Double.valueOf(0);
        for (Object e : list) {
            Double number = (Double) e;
            result = result + number;
        }
        return result;
    }

    public static Double mean(List list) {
        if (list == null) {
            return null;
        }

        Double sum = sum(list);
        return numericDivide(sum, Double.valueOf(list.size()));
    }

    public static Double product(List list) {
        if (list == null || list.isEmpty()) {
            return null;
        }

        Double result = 1.0;
        for (Object e : list) {
            Double number = (Double) e;
            result = result * number;
        }
        return result;
    }

    public static Double median(List list) {
        if (list == null || list.isEmpty()) {
            return null;
        }

        Collections.sort(list);
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

    public static Double stddev(List list) {
        if (list == null || list.isEmpty()) {
            return null;
        }

        Double mean = mean(list);
        int length = list.size();
        Double variance = 0.0;
        for(Object e: list) {
            Double number = (Double) e;
            Double dm = number - mean;
            Double dv = dm * dm;
            variance = variance + dv;
        }
        variance = variance / length;
        Double stddev = sqrt(variance);
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

    public static Double numericDivide(Double first, Double second) {
        if (first == null || second == null) {
            return null;
        }
        if (second == 0.0) {
            return null;
        }

        return first / second;
    }
}
