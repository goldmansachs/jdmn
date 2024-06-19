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

import java.math.RoundingMode;
import java.util.List;

import static com.gs.dmn.feel.lib.type.numeric.DefaultNumericType.toDecimal;

public class DefaultNumericLib extends BaseNumericLib<Number> {
    private static final DecimalNumericLib DECIMAL_NUMERIC_LIB = new DecimalNumericLib();

    @Override
    public Number number(String literal) {
        return DECIMAL_NUMERIC_LIB.number(literal);
    }

    @Override
    public Number decimal(Number n, Number scale) {
        return DECIMAL_NUMERIC_LIB.decimal(toDecimal(n), toDecimal(scale));
    }

    @Override
    public Number round(Number n, Number scale, RoundingMode mode) {
        return DECIMAL_NUMERIC_LIB.round(toDecimal(n), toDecimal(scale), mode);
    }

    @Override
    public Number floor(Number n, Number scale) {
        return DECIMAL_NUMERIC_LIB.floor(toDecimal(n), toDecimal(scale));
    }

    @Override
    public Number ceiling(Number n, Number scale) {
        return DECIMAL_NUMERIC_LIB.ceiling(toDecimal(n), toDecimal(scale));
    }

    @Override
    public Number abs(Number n) {
        return DECIMAL_NUMERIC_LIB.abs(toDecimal(n));
    }

    @Override
    public Number intModulo(Number dividend, Number divisor) {
        return DECIMAL_NUMERIC_LIB.intModulo(toDecimal(dividend), toDecimal(divisor));
    }

    @Override
    public Number modulo(Number dividend, Number divisor) {
        return DECIMAL_NUMERIC_LIB.modulo(toDecimal(dividend), toDecimal(divisor));
    }

    @Override
    public Number sqrt(Number number) {
        return DECIMAL_NUMERIC_LIB.sqrt(toDecimal(number));
    }

    @Override
    public Number log(Number number) {
        return DECIMAL_NUMERIC_LIB.log(toDecimal(number));
    }

    @Override
    public Number exp(Number number) {
        return DECIMAL_NUMERIC_LIB.exp(toDecimal(number));
    }

    @Override
    public Boolean odd(Number number) {
        return DECIMAL_NUMERIC_LIB.odd(toDecimal(number));
    }

    @Override
    public Boolean even(Number number) {
        return DECIMAL_NUMERIC_LIB.even(toDecimal(number));
    }

    //
    // List functions
    //
    @Override
    public Number count(List<?> list) {
        return DECIMAL_NUMERIC_LIB.count(list);
    }

    @Override
    public Number min(List<?> list) {
        return DECIMAL_NUMERIC_LIB.min(list);
    }

    @Override
    public Number max(List<?> list) {
        return DECIMAL_NUMERIC_LIB.max(list);
    }

    @Override
    public Number sum(List<?> list) {
        return DECIMAL_NUMERIC_LIB.sum(list);
    }

    @Override
    public Number mean(List<?> list) {
        return DECIMAL_NUMERIC_LIB.mean(list);
    }

    @Override
    public Number product(List<?> list) {
        return DECIMAL_NUMERIC_LIB.product(list);
    }

    @Override
    public Number median(List<?> list) {
        return DECIMAL_NUMERIC_LIB.median(list);
    }

    @Override
    public Number stddev(List<?> list) {
        return DECIMAL_NUMERIC_LIB.stddev(list);
    }

    @Override
    public List mode(List<?> list) {
        return DECIMAL_NUMERIC_LIB.mode(list);
    }

    @Override
    public Number toNumber(Number number) {
        return DECIMAL_NUMERIC_LIB.toNumber(toDecimal(number));
    }
}
