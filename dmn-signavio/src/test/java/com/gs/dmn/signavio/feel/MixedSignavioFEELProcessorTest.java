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
import com.gs.dmn.signavio.dialect.MixedJavaTimeSignavioDMNDialectDefinition;
import com.gs.dmn.signavio.testlab.TestLab;
import org.junit.Test;

import javax.xml.datatype.Duration;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import static com.gs.dmn.feel.analysis.semantics.type.DateTimeType.DATE_AND_TIME;
import static com.gs.dmn.feel.analysis.semantics.type.NumberType.NUMBER;

public class MixedSignavioFEELProcessorTest extends AbstractSignavioFEELProcessorTest<BigDecimal, LocalDate, OffsetTime, ZonedDateTime, Duration> {
    @Override
    protected DMNDialectDefinition<BigDecimal, LocalDate, OffsetTime, ZonedDateTime, Duration, TestLab> makeDialect() {
        return new MixedJavaTimeSignavioDMNDialectDefinition();
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
                "date(\"2011-01-03\") instanceof java.time.LocalDate",
                this.lib.date("2011-01-03") instanceof java.time.LocalDate,
                true);
        doExpressionTest(entries, "", "time(\"12:00:00Z\") instance of time",
                "InstanceOfExpression(DateTimeLiteral(time, \"12:00:00Z\"), NamedTypeExpression(time))",
                "boolean",
                "time(\"12:00:00Z\") instanceof java.time.OffsetTime",
                this.lib.time("12:00:00Z") instanceof java.time.OffsetTime,
                true);
        doExpressionTest(entries, "", "date and time(\"2016-03-01T12:00:00Z\") instance of date and time",
                "InstanceOfExpression(DateTimeLiteral(date and time, \"2016-03-01T12:00:00Z\"), NamedTypeExpression(date and time))",
                "boolean",
                "dateAndTime(\"2016-03-01T12:00:00Z\") instanceof java.time.ZonedDateTime",
                this.lib.dateAndTime("2016-03-01T12:00:00Z") instanceof java.time.ZonedDateTime,
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

    @Test
    public void testFunctionInvocation() {
        super.testFunctionInvocation();

        LocalDate date = this.lib.date("2018-08-02");
        ZonedDateTime dateTime = this.lib.dateAndTime("2019-10-05T20:00:00");

        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("date", com.gs.dmn.feel.analysis.semantics.type.DateType.DATE, date),
                new EnvironmentEntry("dateTime", DATE_AND_TIME, dateTime)
        );

        // diff functions
        doExpressionTest(entries, "", "yearDiff(date, dateTime)",
                "FunctionInvocation(Name(yearDiff) -> PositionalParameters(Name(date), Name(dateTime)))",
                "number",
                "yearDiff(date, dateTime)",
                this.lib.yearDiff(date, dateTime),
                this.lib.number("1"));
    }

}