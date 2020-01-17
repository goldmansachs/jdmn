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

public class DefaultSignavioNumberLib {
    public BigDecimal count(List list) {
        if (list == null) {
            return null;
        }

        return BigDecimal.valueOf(list.size());
    }

    public BigDecimal abs(BigDecimal number) {
        if (number == null) {
            return null;
        }

        return number.abs();
    }

    public BigDecimal round(BigDecimal number, BigDecimal digits) {
        if (number == null || digits == null) {
            return null;
        }

        return number.setScale(digits.intValue(), BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal roundDown(BigDecimal number, BigDecimal digits) {
        if (number == null || digits == null) {
            return null;
        }

        return number.setScale(digits.intValue(), BigDecimal.ROUND_DOWN);
    }

    public BigDecimal roundUp(BigDecimal number, BigDecimal digits) {
        if (number == null || digits == null) {
            return null;
        }

        return number.setScale(digits.intValue(), BigDecimal.ROUND_UP);
    }

    public BigDecimal integer(BigDecimal number) {
        return BigDecimal.valueOf(number.intValue());
    }

    public BigDecimal modulo(BigDecimal divident, BigDecimal divisor) {
        if (divident == null || divisor == null) {
            return null;
        }

        return BigDecimal.valueOf(divident.intValue() % divisor.intValue());
    }
}
