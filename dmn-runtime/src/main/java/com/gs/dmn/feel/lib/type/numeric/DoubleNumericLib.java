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
import java.math.RoundingMode;
import java.util.*;

public class DoubleNumericLib extends BaseNumericLib<Double> {
    @Override
    public Double number(String literal) {
        if (StringUtils.isBlank(literal)) {
            return null;
        }

        return Double.parseDouble(literal);
    }

    @Override
    public Double decimal(Double n, Double scale) {
        if (n == null || scale == null) {
            return null;
        }
        checkScale(scale);

        return BigDecimal.valueOf(n).setScale(scale.intValue(), RoundingMode.HALF_EVEN).doubleValue();
    }

    @Override
    public Double round(Double n, Double scale, RoundingMode mode) {
        if (n == null || scale == null || mode == null) {
            return null;
        }
        checkScale(scale);

        return BigDecimal.valueOf(n).setScale(scale.intValue(), mode).doubleValue();
    }

    @Override
    public Double floor(Double n, Double scale) {
        if (n == null || scale == null) {
            return null;
        }
        checkScale(scale);

        return BigDecimal.valueOf(n).setScale(scale.intValue(), RoundingMode.FLOOR).doubleValue();
    }

    @Override
    public Double ceiling(Double n, Double scale) {
        if (n == null || scale == null) {
            return null;
        }
        checkScale(scale);

        return BigDecimal.valueOf(n).setScale(scale.intValue(), RoundingMode.CEILING).doubleValue();
    }

    @Override
    public Double abs(Double n) {
        if (n == null) {
            return null;
        }

        return Math.abs(n);
    }

    @Override
    public Double intModulo(Double dividend, Double divisor) {
        if (dividend == null || divisor == null) {
            return null;
        }

        return (double) (dividend.intValue() % divisor.intValue());
    }

    @Override
    public Double modulo(Double dividend, Double divisor) {
        if (dividend == null || divisor == null) {
            return null;
        }

        return dividend - divisor * floor(dividend/divisor, 0.0);
    }

    @Override
    public Double sqrt(Double number) {
        if (number == null) {
            return null;
        }

        double result = Math.sqrt(number);
        if (Double.isNaN(result)) {
            throw new DMNRuntimeException("Illegal number '%s'".formatted(number));
        } else {
            return result;
        }
    }

    @Override
    public Double log(Double number) {
        if (number == null) {
            return null;
        }

        double result = Math.log(number);
        if (Double.isNaN(result)) {
            throw new DMNRuntimeException("Illegal number '%s'".formatted(number));
        } else {
            return result;
        }
    }

    @Override
    public Double exp(Double number) {
        if (number == null) {
            return null;
        }

        double result = Math.exp(number);
        if (Double.isNaN(result)) {
            throw new DMNRuntimeException("Illegal number '%s'".formatted(number));
        } else {
            return result;
        }
    }

    @Override
    public Boolean odd(Double number) {
        if (!isIntegerValue(number)) {
            return null;
        }

        return number.intValue() % 2 != 0;
    }

    @Override
    public Boolean even(Double number) {
        if (!isIntegerValue(number)) {
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
    @Override
    public Double count(List<?> list) {
        return list == null ? Double.valueOf(0) : Double.valueOf(list.size());
    }

    @Override
    public Double min(List<?> list) {
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

    @Override
    public Double max(List<?> list) {
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

    @Override
    public Double sum(List<?> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }

        double result = 0;
        for (Object e : list) {
            Double number = (Double) e;
            result = result + number;
        }
        return result;
    }

    @Override
    public Double mean(List<?> list) {
        if (list == null) {
            return null;
        }

        Double sum = sum(list);
        return DoubleNumericType.doubleNumericDivide(sum, (double) list.size());
    }

    @Override
    public Double product(List<?> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }

        double result = 1.0;
        for (Object e : list) {
            Double number = (Double) e;
            result = result * number;
        }
        return result;
    }

    @Override
    public Double median(List<?> list) {
        if (list == null || list.isEmpty()) {
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
    public Double stddev(List<?> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }

        Double mean = mean(list);
        int length = list.size();
        double variance = 0.0;
        for(Object e: list) {
            Double number = (Double) e;
            Double dm = number - mean;
            double dv = dm * dm;
            variance = variance + dv;
        }
        variance = variance / (length - 1);
        return sqrt(variance);
    }

    @Override
    public List mode(List<?> list) {
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

    @Override
    public Number toNumber(Double number) {
        return number;
    }

    private void checkScale(Double scale) {
        if (scale != null && (scale.intValue() < MIN_SCALE || scale.intValue() > MAX_SCALE)) {
            throw new DMNRuntimeException("Scale '%s' not in range [%s, %s]".formatted(scale, MIN_SCALE, MAX_SCALE));
        }
    }
}
