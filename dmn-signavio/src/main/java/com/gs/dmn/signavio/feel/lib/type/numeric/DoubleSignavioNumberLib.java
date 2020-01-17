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
import java.util.List;

public class DoubleSignavioNumberLib {
    public Double count(List list) {
        if (list == null) {
            return null;
        }

        return Double.valueOf(list.size());
    }

    public Double abs(Double number) {
        if (number == null) {
            return null;
        }

        return Math.abs(number);
    }

    public Double round(Double number, Double digits) {
        if (number == null || digits == null) {
            return null;
        }

        return BigDecimal.valueOf(number).setScale(digits.intValue(), BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public Double roundDown(Double number, Double digits) {
        if (number == null || digits == null) {
            return null;
        }

        return BigDecimal.valueOf(number).setScale(digits.intValue(), BigDecimal.ROUND_DOWN).doubleValue();
    }

    public Double roundUp(Double number, Double digits) {
        if (number == null || digits == null) {
            return null;
        }

        return BigDecimal.valueOf(number).setScale(digits.intValue(), BigDecimal.ROUND_UP).doubleValue();
    }

    public Double integer(Double number) {
        return Double.valueOf(number.intValue());
    }

    public Double modulo(Double divident, Double divisor) {
        if (divident == null || divisor == null) {
            return null;
        }

        return Double.valueOf(divident.intValue() % divisor.intValue());
    }
}
