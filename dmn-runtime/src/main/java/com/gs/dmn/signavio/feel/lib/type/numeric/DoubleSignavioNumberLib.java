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

import com.gs.dmn.feel.lib.type.numeric.DoubleNumericLib;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DoubleSignavioNumberLib extends DoubleNumericLib implements SignavioNumberLib<Double> {
    @Override
    public Double number(String text, Double defaultValue) {
        if (text == null || defaultValue ==  null) {
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
    public Double count(List<?> list) {
        if (list == null) {
            return null;
        }

        return Double.valueOf(list.size());
    }

    @Override
    public Double round(Double number, Double digits) {
        if (number == null || digits == null) {
            return null;
        }

        return BigDecimal.valueOf(number).setScale(digits.intValue(), RoundingMode.HALF_EVEN).doubleValue();
    }

    @Override
    public Double ceiling(Double number) {
        return this.ceiling(number, valueOf(0));
    }

    @Override
    public Double floor(Double number) {
        return this.floor(number, valueOf(0));
    }

    @Override
    public Double integer(Double number) {
        return Double.valueOf(number.intValue());
    }

    @Override
    public Double modulo(Double dividend, Double divisor) {
        if (dividend == null || divisor == null || divisor == 0) {
            return null;
        }

        return dividend % divisor;
    }

    @Override
    public Double power(Double base, Double exponent) {
        return Math.pow(base, exponent.intValue());
    }

    @Override
    public Double percent(Double number) {
        if (number == null) {
            return null;
        }

        return number / 100;
    }

    @Override
    public Double roundDown(Double number, Double digits) {
        if (number == null || digits == null) {
            return null;
        }

        return BigDecimal.valueOf(number).setScale(digits.intValue(), RoundingMode.DOWN).doubleValue();
    }

    @Override
    public Double roundUp(Double number, Double digits) {
        if (number == null || digits == null) {
            return null;
        }

        return BigDecimal.valueOf(number).setScale(digits.intValue(), RoundingMode.UP).doubleValue();
    }

    @Override
    public Double avg(List<?> list) {
        return this.mean(list);
    }

    @Override
    public Object signavioMode(List numbers) {
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
        return resultKey;
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
