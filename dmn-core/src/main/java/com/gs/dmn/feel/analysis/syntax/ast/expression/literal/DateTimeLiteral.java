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
package com.gs.dmn.feel.analysis.syntax.ast.expression.literal;

import com.gs.dmn.feel.analysis.semantics.SemanticError;
import com.gs.dmn.feel.analysis.semantics.environment.Environment;
import com.gs.dmn.feel.analysis.semantics.type.DateTimeType;
import com.gs.dmn.feel.analysis.semantics.type.DateType;
import com.gs.dmn.feel.analysis.semantics.type.DurationType;
import com.gs.dmn.feel.analysis.semantics.type.TimeType;
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.analysis.syntax.ast.Visitor;

import java.util.regex.Pattern;

public class DateTimeLiteral extends SimpleLiteral {
    private static final Pattern DAYS_AND_TIME_DURATION_PATTERN = Pattern.compile("\"[-]?P([0-9]+D)?T?([0-9]+H)?([0-9]+M)?([0-9]+H)?([0-9]+(\\.[0-9]*)?S)?\"");
    private static final Pattern YEARS_AND_MONTHS_DURATION_PATTERN = Pattern.compile("\"[-]?P([0-9]+Y)?([0-9]+M)?\"");

    private final String conversionFunction;

    public DateTimeLiteral(String conversionFunction, String lexeme) {
        super(lexeme);
        this.conversionFunction = conversionFunction;
    }

    public String getConversionFunction() {
        return conversionFunction;
    }

    @Override
    public void deriveType(Environment environment) {
        if (DateType.DATE.hasConversionFunction(conversionFunction)) {
            this.setType(DateType.DATE);
        } else if (TimeType.TIME.hasConversionFunction(conversionFunction)) {
            this.setType(TimeType.TIME);
        } else if (DateTimeType.DATE_AND_TIME.hasConversionFunction(conversionFunction)) {
            this.setType(DateTimeType.DATE_AND_TIME);
        } else if (DurationType.CONVERSION_FUNCTION.equals(conversionFunction)) {
            if (isYearsAndMonthsDuration(getValue())) {
                this.setType(DurationType.YEARS_AND_MONTHS_DURATION);
            } else if (isDaysAndTimeDuration(getValue())) {
                this.setType(DurationType.DAYS_AND_TIME_DURATION);
            } else {
                throw new SemanticError(this, String.format("Cannot convert duration '%s(%s)'", conversionFunction, getValue()));
            }
        } else {
            throw new SemanticError(this, String.format("Cannot convert date time literal '%s(%s)'", conversionFunction, getValue()));
        }
    }

    private boolean isDaysAndTimeDuration(String value) {
        return DAYS_AND_TIME_DURATION_PATTERN.matcher(value).matches();
    }

    private boolean isYearsAndMonthsDuration(String value) {
        return YEARS_AND_MONTHS_DURATION_PATTERN.matcher(value).matches();
    }

    @Override
    public Object accept(Visitor visitor, FEELContext params) {
        return visitor.visit(this, params);
    }

    @Override
    public String toString() {
        return String.format("DateTimeLiteral(%s, %s)", conversionFunction, getValue());
    }

}

