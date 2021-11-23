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

import com.gs.dmn.feel.analysis.semantics.SemanticError;
import com.gs.dmn.feel.analysis.semantics.type.DateTimeType;
import com.gs.dmn.feel.analysis.semantics.type.DateType;
import com.gs.dmn.feel.analysis.semantics.type.DurationType;
import com.gs.dmn.feel.analysis.semantics.type.TimeType;
import com.gs.dmn.feel.analysis.syntax.ast.Visitor;
import com.gs.dmn.runtime.DMNContext;

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
        return this.conversionFunction;
    }

    @Override
    public void deriveType(DMNContext context) {
        if (DateType.DATE.hasConversionFunction(this.conversionFunction)) {
            this.setType(DateType.DATE);
        } else if (TimeType.TIME.hasConversionFunction(this.conversionFunction)) {
            this.setType(TimeType.TIME);
        } else if (DateTimeType.DATE_AND_TIME.hasConversionFunction(this.conversionFunction)) {
            this.setType(DateTimeType.DATE_AND_TIME);
        } else if (DurationType.CONVERSION_FUNCTION.equals(this.conversionFunction)) {
            if (isYearsAndMonthsDuration(getLexeme())) {
                this.setType(DurationType.YEARS_AND_MONTHS_DURATION);
            } else if (isDaysAndTimeDuration(getLexeme())) {
                this.setType(DurationType.DAYS_AND_TIME_DURATION);
            } else {
                throw new SemanticError(this, String.format("Date time literal '%s(%s) is not supported", this.conversionFunction, getLexeme()));
            }
        } else {
            throw new SemanticError(this, String.format("Date time literal '%s(%s)' is not supported", this.conversionFunction, getLexeme()));
        }
    }

    private boolean isDaysAndTimeDuration(String value) {
        return DAYS_AND_TIME_DURATION_PATTERN.matcher(value).matches();
    }

    private boolean isYearsAndMonthsDuration(String value) {
        return YEARS_AND_MONTHS_DURATION_PATTERN.matcher(value).matches();
    }

    @Override
    public Object accept(Visitor visitor, DMNContext context) {
        return visitor.visit(this, context);
    }

    @Override
    public String toString() {
        return String.format("%s(%s, %s)", getClass().getSimpleName(), this.conversionFunction, getLexeme());
    }
}

