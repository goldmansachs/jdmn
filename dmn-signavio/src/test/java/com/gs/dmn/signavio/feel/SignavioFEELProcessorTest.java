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
package com.gs.dmn.signavio.feel;

import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.feel.EnvironmentEntry;
import com.gs.dmn.signavio.dialect.SignavioDMNDialectDefinition;
import com.gs.dmn.signavio.testlab.TestLab;
import org.junit.Test;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static com.gs.dmn.feel.analysis.semantics.type.NumberType.NUMBER;

public class SignavioFEELProcessorTest extends AbstractSignavioFEELProcessorTest<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar, Duration> {
    @Override
    protected DMNDialectDefinition<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar, Duration, TestLab> makeDialect() {
        return new SignavioDMNDialectDefinition();
    }

    @Test
    public void testInstanceOfExpression() {
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, this.lib.number("1")));

        doExpressionTest(entries, "", "3 instance of number",
                "InstanceOfExpression(NumericLiteral(3), NamedTypeExpression(number))",
                "boolean",
                "number(\"3\") instanceof java.math.BigDecimal",
                this.lib.number("3") instanceof java.math.BigDecimal,
                true);
        doExpressionTest(entries, "", "\"abc\" instance of string",
                "InstanceOfExpression(StringLiteral(\"abc\"), NamedTypeExpression(string))",
                "boolean",
                "\"abc\" instanceof String",
                "abc" instanceof String,
                true);
        doExpressionTest(entries, "", "true instance of boolean",
                "InstanceOfExpression(BooleanLiteral(true), NamedTypeExpression(boolean))",
                "boolean",
                "Boolean.TRUE instanceof Boolean",
                Boolean.TRUE instanceof Boolean,
                true);
        doExpressionTest(entries, "", "date(\"2011-01-03\") instance of date",
                "InstanceOfExpression(DateTimeLiteral(date, \"2011-01-03\"), NamedTypeExpression(date))",
                "boolean",
                "date(\"2011-01-03\") instanceof javax.xml.datatype.XMLGregorianCalendar",
                this.lib.date("2011-01-03") instanceof javax.xml.datatype.XMLGregorianCalendar,
                true);
        doExpressionTest(entries, "", "time(\"12:00:00Z\") instance of time",
                "InstanceOfExpression(DateTimeLiteral(time, \"12:00:00Z\"), NamedTypeExpression(time))",
                "boolean",
                "time(\"12:00:00Z\") instanceof javax.xml.datatype.XMLGregorianCalendar",
                this.lib.time("12:00:00Z") instanceof javax.xml.datatype.XMLGregorianCalendar,
                true);
        doExpressionTest(entries, "", "date and time(\"2016-03-01T12:00:00Z\") instance of date and time",
                "InstanceOfExpression(DateTimeLiteral(date and time, \"2016-03-01T12:00:00Z\"), NamedTypeExpression(date and time))",
                "boolean",
                "dateAndTime(\"2016-03-01T12:00:00Z\") instanceof javax.xml.datatype.XMLGregorianCalendar",
                this.lib.dateAndTime("2016-03-01T12:00:00Z") instanceof javax.xml.datatype.XMLGregorianCalendar,
                true);
        doExpressionTest(entries, "", "duration(\"P1Y1M\") instance of years and months duration",
                "InstanceOfExpression(DateTimeLiteral(duration, \"P1Y1M\"), NamedTypeExpression(years and months duration))",
                "boolean",
                "duration(\"P1Y1M\") instanceof javax.xml.datatype.Duration",
                this.lib.duration("P1Y1M") instanceof javax.xml.datatype.Duration,
                true);
        doExpressionTest(entries, "", "duration(\"P1DT1H\") instance of days and time duration",
                "InstanceOfExpression(DateTimeLiteral(duration, \"P1DT1H\"), NamedTypeExpression(days and time duration))",
                "boolean",
                "duration(\"P1DT1H\") instanceof javax.xml.datatype.Duration",
                this.lib.duration("P1Y1M") instanceof javax.xml.datatype.Duration,
                true);
        doExpressionTest(entries, "", "(function () 4) instance of function <> -> number",
                "InstanceOfExpression(FunctionDefinition(, NumericLiteral(4), false), FunctionTypeExpression( -> NamedTypeExpression(number)))",
                "boolean",
                null,
                null,
                null);
    }
}