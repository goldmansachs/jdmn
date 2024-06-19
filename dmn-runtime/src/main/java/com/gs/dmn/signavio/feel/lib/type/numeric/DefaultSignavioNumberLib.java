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

import java.util.List;

import static com.gs.dmn.feel.lib.type.numeric.DefaultNumericType.toDecimal;

public class DefaultSignavioNumberLib implements SignavioNumberLib<Number> {
    private static final DecimalSignavioNumberLib DECIMAL_SIGNAVIO_NUMBER_LIB = new DecimalSignavioNumberLib();

    @Override
    public Number number(String literal) {
        return DECIMAL_SIGNAVIO_NUMBER_LIB.number(literal);
    }

    @Override
    public Number number(String text, Number defaultValue) {
        return DECIMAL_SIGNAVIO_NUMBER_LIB.number(text, toDecimal(defaultValue));
    }

    @Override
    public Number abs(Number number) {
        return DECIMAL_SIGNAVIO_NUMBER_LIB.abs(toDecimal(number));
    }

    @Override
    public Number count(List<?> list) {
        return DECIMAL_SIGNAVIO_NUMBER_LIB.count(list);
    }

    @Override
    public Number round(Number number, Number digits) {
        return DECIMAL_SIGNAVIO_NUMBER_LIB.round(toDecimal(number), toDecimal(digits));
    }

    @Override
    public Number ceiling(Number number) {
        return DECIMAL_SIGNAVIO_NUMBER_LIB.ceiling(toDecimal(number));
    }

    @Override
    public Number floor(Number number) {
        return DECIMAL_SIGNAVIO_NUMBER_LIB.floor(toDecimal(number));
    }

    @Override
    public Number integer(Number number) {
        return DECIMAL_SIGNAVIO_NUMBER_LIB.integer(toDecimal(number));
    }

    @Override
    public Number modulo(Number dividend, Number divisor) {
        return DECIMAL_SIGNAVIO_NUMBER_LIB.modulo(toDecimal(dividend), toDecimal(divisor));
    }

    @Override
    public Number power(Number base, Number exponent) {
        return DECIMAL_SIGNAVIO_NUMBER_LIB.power(toDecimal(base), toDecimal(exponent));
    }

    @Override
    public Number percent(Number number) {
        return DECIMAL_SIGNAVIO_NUMBER_LIB.percent(toDecimal(number));
    }

    @Override
    public Number product(List<?> numbers) {
        return DECIMAL_SIGNAVIO_NUMBER_LIB.product(numbers);
    }

    @Override
    public Number roundDown(Number number, Number digits) {
        return DECIMAL_SIGNAVIO_NUMBER_LIB.roundDown(toDecimal(number), toDecimal(digits));
    }

    @Override
    public Number roundUp(Number number, Number digits) {
        return DECIMAL_SIGNAVIO_NUMBER_LIB.roundUp(toDecimal(number), toDecimal(digits));
    }

    @Override
    public Number sum(List<?> numbers) {
        return DECIMAL_SIGNAVIO_NUMBER_LIB.sum(numbers);
    }

    @Override
    public Number avg(List<?> numbers) {
        return DECIMAL_SIGNAVIO_NUMBER_LIB.avg(numbers);
    }

    @Override
    public Number max(List<?> numbers) {
        return DECIMAL_SIGNAVIO_NUMBER_LIB.max(numbers);
    }

    @Override
    public Number median(List<?> numbers) {
        return DECIMAL_SIGNAVIO_NUMBER_LIB.median(numbers);
    }

    @Override
    public Number min(List<?> list) {
        return DECIMAL_SIGNAVIO_NUMBER_LIB.min(list);
    }

    @Override
    public Number mode(List<?> numbers) {
        return DECIMAL_SIGNAVIO_NUMBER_LIB.mode(numbers);
    }

    @Override
    public Number valueOf(long number) {
        return DECIMAL_SIGNAVIO_NUMBER_LIB.valueOf(number);
    }

    @Override
    public int intValue(Number number) {
        return DECIMAL_SIGNAVIO_NUMBER_LIB.intValue(toDecimal(number));
    }

    @Override
    public Number toNumber(Number number) {
        return DECIMAL_SIGNAVIO_NUMBER_LIB.toNumber(toDecimal(number));
    }
}
