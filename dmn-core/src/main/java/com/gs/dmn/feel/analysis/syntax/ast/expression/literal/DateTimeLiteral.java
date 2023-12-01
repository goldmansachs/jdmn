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
package com.gs.dmn.feel.analysis.syntax.ast.expression.literal;

import com.gs.dmn.feel.analysis.syntax.ast.Visitor;

import java.util.regex.Pattern;

public class DateTimeLiteral<T> extends SimpleLiteral<T> {
    private static final Pattern DAYS_AND_TIME_DURATION_PATTERN = Pattern.compile("\"[-]?P([0-9]+D)?T?([0-9]+H)?([0-9]+M)?([0-9]+H)?([0-9]+(\\.[0-9]*)?S)?\"");
    private static final Pattern YEARS_AND_MONTHS_DURATION_PATTERN = Pattern.compile("\"[-]?P([0-9]+Y)?([0-9]+M)?\"");

    private final String conversionFunction;

    public DateTimeLiteral(String conversionFunction, String lexeme) {
        super(lexeme);
        this.conversionFunction = conversionFunction;
    }

    public String getConversionFunction() {
        return this.conversionFunction;
    }

    public boolean isDaysAndTimeDuration(String value) {
        return DAYS_AND_TIME_DURATION_PATTERN.matcher(value).matches();
    }

    public boolean isYearsAndMonthsDuration(String value) {
        return YEARS_AND_MONTHS_DURATION_PATTERN.matcher(value).matches();
    }

    @Override
    public <C, R> R accept(Visitor<T, C, R> visitor, C context) {
        return visitor.visit(this, context);
    }

    @Override
    public String toString() {
        return String.format("%s(%s, %s)", getClass().getSimpleName(), this.conversionFunction, getLexeme());
    }
}

