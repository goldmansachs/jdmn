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
import org.junit.jupiter.api.Test;

import javax.xml.datatype.Duration;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import static com.gs.dmn.feel.analysis.semantics.type.DateTimeType.DATE_AND_TIME;
import static com.gs.dmn.feel.analysis.semantics.type.DateType.DATE;
import static com.gs.dmn.feel.analysis.semantics.type.TimeType.TIME;

public class MixedJavaTimeSignavioFEELProcessorTest extends AbstractSignavioFEELProcessorTest<BigDecimal, LocalDate, OffsetTime, ZonedDateTime, Duration> {
    @Override
    protected String numberType() {
        return BigDecimal.class.getName();
    }

    @Override
    protected DMNDialectDefinition<BigDecimal, LocalDate, OffsetTime, ZonedDateTime, Duration, TestLab> makeDialect() {
        return new MixedJavaTimeSignavioDMNDialectDefinition();
    }

    @Test
    @Override
    public void testFunctionInvocation() {
        super.testFunctionInvocation();

        LocalDate date = this.lib.date("2018-08-02");
        OffsetTime time = this.lib.time("21:02:03");
        ZonedDateTime dateTime = this.lib.dateAndTime("2019-10-05T20:00:00");

        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("date", DATE, date),
                new EnvironmentEntry("time", TIME, time),
                new EnvironmentEntry("dateTime", DATE_AND_TIME, dateTime)
        );

        // diff functions
        doExpressionTest(entries, "", "yearDiff(date, date)",
                "FunctionInvocation(Name(yearDiff) -> PositionalParameters(Name(date), Name(date)))",
                "number",
                "yearDiff(date, date)",
                this.lib.yearDiff(date, date),
                this.lib.number("0"));
        doExpressionTest(entries, "", "yearDiff(date, dateTime)",
                "FunctionInvocation(Name(yearDiff) -> PositionalParameters(Name(date), Name(dateTime)))",
                "number",
                "yearDiff(date, dateTime)",
                this.lib.yearDiff(date, dateTime),
                this.lib.number("1"));
        doExpressionTest(entries, "", "yearDiff(dateTime, date)",
                "FunctionInvocation(Name(yearDiff) -> PositionalParameters(Name(dateTime), Name(date)))",
                "number",
                "yearDiff(dateTime, date)",
                this.lib.yearDiff(dateTime, date),
                this.lib.number("-1"));
        doExpressionTest(entries, "", "yearDiff(dateTime, dateTime)",
                "FunctionInvocation(Name(yearDiff) -> PositionalParameters(Name(dateTime), Name(dateTime)))",
                "number",
                "yearDiff(dateTime, dateTime)",
                this.lib.yearDiff(dateTime, dateTime),
                this.lib.number("0"));

        doExpressionTest(entries, "", "monthDiff(date, date)",
                "FunctionInvocation(Name(monthDiff) -> PositionalParameters(Name(date), Name(date)))",
                "number",
                "monthDiff(date, date)",
                this.lib.monthDiff(date, date),
                this.lib.number("0"));
        doExpressionTest(entries, "", "monthDiff(date, dateTime)",
                "FunctionInvocation(Name(monthDiff) -> PositionalParameters(Name(date), Name(dateTime)))",
                "number",
                "monthDiff(date, dateTime)",
                this.lib.monthDiff(date, dateTime),
                this.lib.number("14"));
        doExpressionTest(entries, "", "monthDiff(dateTime, date)",
                "FunctionInvocation(Name(monthDiff) -> PositionalParameters(Name(dateTime), Name(date)))",
                "number",
                "monthDiff(dateTime, date)",
                this.lib.monthDiff(dateTime, date),
                this.lib.number("-14"));
        doExpressionTest(entries, "", "monthDiff(dateTime, dateTime)",
                "FunctionInvocation(Name(monthDiff) -> PositionalParameters(Name(dateTime), Name(dateTime)))",
                "number",
                "monthDiff(dateTime, dateTime)",
                this.lib.monthDiff(dateTime, dateTime),
                this.lib.number("0"));

        doExpressionTest(entries, "", "dayDiff(date, date)",
                "FunctionInvocation(Name(dayDiff) -> PositionalParameters(Name(date), Name(date)))",
                "number",
                "dayDiff(date, date)",
                this.lib.dayDiff(date, date),
                this.lib.number("0"));
        doExpressionTest(entries, "", "dayDiff(date, dateTime)",
                "FunctionInvocation(Name(dayDiff) -> PositionalParameters(Name(date), Name(dateTime)))",
                "number",
                "dayDiff(date, dateTime)",
                this.lib.dayDiff(date, dateTime),
                this.lib.number("429"));
        doExpressionTest(entries, "", "dayDiff(dateTime, date)",
                "FunctionInvocation(Name(dayDiff) -> PositionalParameters(Name(dateTime), Name(date)))",
                "number",
                "dayDiff(dateTime, date)",
                this.lib.dayDiff(dateTime, date),
                this.lib.number("-429"));
        doExpressionTest(entries, "", "dayDiff(dateTime, dateTime)",
                "FunctionInvocation(Name(dayDiff) -> PositionalParameters(Name(dateTime), Name(dateTime)))",
                "number",
                "dayDiff(dateTime, dateTime)",
                this.lib.dayDiff(dateTime, dateTime),
                this.lib.number("0"));

        doExpressionTest(entries, "", "hourDiff(time, time)",
                "FunctionInvocation(Name(hourDiff) -> PositionalParameters(Name(time), Name(time)))",
                "number",
                "hourDiff(time, time)",
                this.lib.hourDiff(time, time),
                this.lib.number("0"));
        doExpressionTest(entries, "", "hourDiff(time, dateTime)",
                "FunctionInvocation(Name(hourDiff) -> PositionalParameters(Name(time), Name(dateTime)))",
                "number",
                "hourDiff(time, dateTime)",
                this.lib.hourDiff(time, dateTime),
                this.lib.number("-1"));
        doExpressionTest(entries, "", "hourDiff(dateTime, time)",
                "FunctionInvocation(Name(hourDiff) -> PositionalParameters(Name(dateTime), Name(time)))",
                "number",
                "hourDiff(dateTime, time)",
                this.lib.hourDiff(dateTime, time),
                this.lib.number("1"));
        doExpressionTest(entries, "", "hourDiff(dateTime, dateTime)",
                "FunctionInvocation(Name(hourDiff) -> PositionalParameters(Name(dateTime), Name(dateTime)))",
                "number",
                "hourDiff(dateTime, dateTime)",
                this.lib.hourDiff(dateTime, dateTime),
                this.lib.number("0"));

        doExpressionTest(entries, "", "minutesDiff(time, time)",
                "FunctionInvocation(Name(minutesDiff) -> PositionalParameters(Name(time), Name(time)))",
                "number",
                "minutesDiff(time, time)",
                this.lib.minutesDiff(time, time),
                this.lib.number("0"));
        doExpressionTest(entries, "", "minutesDiff(time, dateTime)",
                "FunctionInvocation(Name(minutesDiff) -> PositionalParameters(Name(time), Name(dateTime)))",
                "number",
                "minutesDiff(time, dateTime)",
                this.lib.minutesDiff(time, dateTime),
                this.lib.number("-62"));
        doExpressionTest(entries, "", "minutesDiff(dateTime, time)",
                "FunctionInvocation(Name(minutesDiff) -> PositionalParameters(Name(dateTime), Name(time)))",
                "number",
                "minutesDiff(dateTime, time)",
                this.lib.minutesDiff(dateTime, time),
                this.lib.number("62"));
        doExpressionTest(entries, "", "minutesDiff(dateTime, dateTime)",
                "FunctionInvocation(Name(minutesDiff) -> PositionalParameters(Name(dateTime), Name(dateTime)))",
                "number",
                "minutesDiff(dateTime, dateTime)",
                this.lib.minutesDiff(dateTime, dateTime),
                this.lib.number("0"));
    }
}