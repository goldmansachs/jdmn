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

import com.gs.dmn.feel.lib.type.numeric.DefaultNumericLib;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class DefaultSignavioNumberLib extends DefaultNumericLib implements SignavioNumberLib<BigDecimal> {
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
    public BigDecimal count(List list) {
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
    public BigDecimal integer(BigDecimal number) {
        return BigDecimal.valueOf(number.intValue());
    }

    @Override
    public BigDecimal modulo(BigDecimal dividend, BigDecimal divisor) {
        if (dividend == null || divisor == null) {
            return null;
        }

        return dividend.remainder(divisor);
    }

    @Override
    public BigDecimal power(BigDecimal base, BigDecimal exponent) {
        return BigDecimal.valueOf(Math.pow(base.doubleValue(), exponent.intValue()));
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
