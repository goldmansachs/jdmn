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

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class DoubleNumericLib {
    public Double number(String literal) {
        if (StringUtils.isBlank(literal)) {
            return null;
        }

        return Double.parseDouble(literal);
    }

    public Double number(String from, String groupingSeparator, String decimalSeparator) {
        if (StringUtils.isBlank(from)) {
            return null;
        }
        if (! (" ".equals(groupingSeparator) || ".".equals(groupingSeparator) || ",".equals(groupingSeparator) || null == groupingSeparator)) {
            return null;
        }
        if (! (".".equals(decimalSeparator) || ",".equals(decimalSeparator) || null == decimalSeparator)) {
            return null;
        }
        if (groupingSeparator != null && groupingSeparator.equals(decimalSeparator)) {
            return null;
        }

        if (groupingSeparator != null) {
            if (groupingSeparator.equals(".")) {
                groupingSeparator = "\\" + groupingSeparator;
            }
            from = from.replaceAll(groupingSeparator, "");
        }
        if (decimalSeparator != null && !decimalSeparator.equals(".")) {
            from = from.replaceAll(decimalSeparator, ".");
        }
        return number(from);
    }

    public Double decimal(Double n, Double scale) {
        if (n == null || scale == null) {
            return null;
        }

        return BigDecimal.valueOf(n).setScale(scale.intValue(), RoundingMode.HALF_EVEN).doubleValue();
    }

    public Double floor(Double number) {
        if (number == null) {
            return null;
        }

        return BigDecimal.valueOf(number).setScale(0, BigDecimal.ROUND_FLOOR).doubleValue();
    }

    public Double ceiling(Double number) {
        if (number == null) {
            return null;
        }

        return BigDecimal.valueOf(number).setScale(0, BigDecimal.ROUND_CEILING).doubleValue();
    }

    public Double abs(Double number) {
        if (number == null) {
            return null;
        }

        return Math.abs(number);
    }

    public Double intModulo(Double dividend, Double divisor) {
        if (dividend == null || divisor == null) {
            return null;
        }

        return Double.valueOf(dividend.intValue() % divisor.intValue());
    }

    public Double modulo(Double dividend, Double divisor) {
        if (dividend == null || divisor == null) {
            return null;
        }

        return dividend - divisor * floor(dividend/divisor);
    }

    public Double sqrt(Double number) {
        if (number == null) {
            return null;
        }

        return Math.sqrt(number);
    }

    public Double log(Double number) {
        if (number == null) {
            return null;
        }

        return Math.log(number);
    }

    public Double exp(Double number) {
        if (number == null) {
            return null;
        }

        return Math.exp(number);
    }

    public Boolean odd(Double number) {
        if (number == null || !isIntegerValue(number)) {
            return null;
        }

        return number.intValue() % 2 != 0;
    }

    public Boolean even(Double number) {
        if (number == null || !isIntegerValue(number)) {
            return null;
        }

        return number.intValue() % 2 == 0;
    }

    private boolean isIntegerValue(Double d) {
        if (d == null) {
            return false;
        } else {
            return d == d.intValue();
        }
    }

    //
    // List functions
    //
    public Double count(List list) {
        return list == null ? Double.valueOf(0) : Double.valueOf(list.size());
    }

    public Double min(List list) {
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

    public Double min(Object... args) {
        if (args == null || args.length < 1) {
            return null;
        }

        return min(Arrays.asList(args));
    }

    public Double max(List list) {
        if (list == null || list.isEmpty()) {
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

    public Double max(Object... args) {
        if (args == null || args.length < 1) {
            return null;
        }

        return max(Arrays.asList(args));
    }

    public Double sum(List list) {
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

    public Double sum(Object... args) {
        if (args == null || args.length < 1) {
            return null;
        }

        return sum(Arrays.asList(args));
    }

    public Double mean(List list) {
        if (list == null) {
            return null;
        }

        Double sum = sum(list);
        return DoubleNumericType.doubleNumericDivide(sum, Double.valueOf(list.size()));
    }

    public Double mean(Object... args) {
        if (args == null || args.length < 1) {
            return null;
        }

        return mean(Arrays.asList(args));
    }

    public Double product(List list) {
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

    public Double product(Object... numbers) {
        if (numbers == null || numbers.length < 1) {
            return null;
        }

        return product(Arrays.asList(numbers));
    }

    public Double median(List list) {
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

    public Double median(Object... numbers) {
        if (numbers == null || numbers.length < 1) {
            return null;
        }

        return median(Arrays.asList(numbers));
    }

    public Double stddev(List list) {
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
        variance = variance / (length - 1);
        Double stddev = sqrt(variance);
        return stddev;
    }

    public Double stddev(Object... numbers) {
        if (numbers == null || numbers.length < 1) {
            return null;
        }

        return stddev(Arrays.asList(numbers));
    }

    public List mode(List list) {
        if (list == null) {
            return null;
        }
        if (list.isEmpty()) {
            return new ArrayList();
        }

        int max = -1;
        List modes = new ArrayList();
        Map<Object, Integer> countMap = new HashMap<Object, Integer>();
        for (Object n : list) {
            if (! (n instanceof Number)) {
                return null;
            }
            int count;

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

    public List mode(Object... numbers) {
        if (numbers == null) {
            return null;
        }

        return mode(Arrays.asList(numbers));
    }
}
