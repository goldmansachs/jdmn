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
package com.gs.dmn.feel;

import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.dialect.JavaTimeDMNDialectDefinition;
import com.gs.dmn.tck.ast.TestCases;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JavaTimeFEELProcessorTest extends AbstractStandardFEELProcessorTest<Number, LocalDate, TemporalAccessor, TemporalAccessor, TemporalAmount> {
    @Override
    protected DMNDialectDefinition<Number, LocalDate, TemporalAccessor, TemporalAccessor, TemporalAmount, TestCases> makeDialect() {
        return new JavaTimeDMNDialectDefinition();
    }

    @Override
    @Test
    public void testConversionFunctions() {
        super.testConversionFunctions();

        List<EnvironmentEntry> entries = Collections.emptyList();

        doExpressionTest(entries, "", "date and time(date and time(\"2012-03-01T13:14:15Z\"), time(\"10:11:12Z\"))",
                "FunctionInvocation(Name(date and time) -> PositionalParameters(DateTimeLiteral(date and time, \"2012-03-01T13:14:15Z\"), DateTimeLiteral(time, \"10:11:12Z\")))",
                "date and time",
                "dateAndTime(dateAndTime(\"2012-03-01T13:14:15Z\"), time(\"10:11:12Z\"))",
                this.lib.dateAndTime(this.lib.dateAndTime("2012-03-01T13:14:15Z"), this.lib.time("10:11:12Z")),
                this.lib.dateAndTime("2012-03-01T10:11:12Z"));

        doExpressionTest(entries, "", "years and months duration(date and time(\"2012-03-01T10:12:00Z\"), date and time(\"2012-03-01T10:12:00Z\"))",
                "FunctionInvocation(Name(years and months duration) -> PositionalParameters(DateTimeLiteral(date and time, \"2012-03-01T10:12:00Z\"), DateTimeLiteral(date and time, \"2012-03-01T10:12:00Z\")))",
                "years and months duration",
                "yearsAndMonthsDuration(dateAndTime(\"2012-03-01T10:12:00Z\"), dateAndTime(\"2012-03-01T10:12:00Z\"))",
                this.lib.yearsAndMonthsDuration(this.lib.dateAndTime("2012-03-01T10:12:00Z"), this.lib.dateAndTime("2012-03-01T10:12:00Z")),
                this.lib.duration("P0Y0M"));
        doExpressionTest(entries, "", "years and months duration(date and time(\"2012-03-01T10:12:00Z\"), date(\"2013-05-01\"))",
                "FunctionInvocation(Name(years and months duration) -> PositionalParameters(DateTimeLiteral(date and time, \"2012-03-01T10:12:00Z\"), DateTimeLiteral(date, \"2013-05-01\")))",
                "years and months duration",
                "yearsAndMonthsDuration(dateAndTime(\"2012-03-01T10:12:00Z\"), date(\"2013-05-01\"))",
                this.lib.yearsAndMonthsDuration(this.lib.dateAndTime("2012-03-01T10:12:00Z"), this.lib.date("2013-05-01")),
                this.lib.duration("P1Y2M"));
        doExpressionTest(entries, "", "years and months duration(date(\"2013-05-01\"), date and time(\"2012-03-01T10:12:00Z\"))",
                "FunctionInvocation(Name(years and months duration) -> PositionalParameters(DateTimeLiteral(date, \"2013-05-01\"), DateTimeLiteral(date and time, \"2012-03-01T10:12:00Z\")))",
                "years and months duration",
                "yearsAndMonthsDuration(date(\"2013-05-01\"), dateAndTime(\"2012-03-01T10:12:00Z\"))",
                this.lib.yearsAndMonthsDuration(this.lib.date("2013-05-01"), this.lib.dateAndTime("2012-03-01T10:12:00Z")),
                this.lib.duration("-P1Y2M"));
    }

    @Test
    @Override
    public void testDateTimeFunctions() {
        super.testDateTimeFunctions();

        List<EnvironmentEntry> entries = new ArrayList<>();

        doExpressionTest(entries, "", "is(@\"23:00:50Z\", @\"23:00:50\")",
                "FunctionInvocation(Name(is) -> PositionalParameters(DateTimeLiteral(time, \"23:00:50Z\"), DateTimeLiteral(time, \"23:00:50\")))",
                "boolean",
                "is(time(\"23:00:50Z\"), time(\"23:00:50\"))",
                this.lib.is(this.lib.time("23:00:50Z"), this.lib.time("23:00:50")),
                false);
    }

    @Test
    @Override
    public void testDateAndTimeProperties() {
        List<EnvironmentEntry> entries = Collections.emptyList();

        doExpressionTest(entries, "", "date and time(\"2018-12-10T10:30:00\").time offset",
                "PathExpression(DateTimeLiteral(date and time, \"2018-12-10T10:30:00\"), time offset)",
                "days and time duration",
                "timeOffset(dateAndTime(\"2018-12-10T10:30:00\"))",
                this.lib.timeOffset(this.lib.dateAndTime("2018-12-10T10:30:00")),
                null
        );
        doExpressionTest(entries, "", "date and time(\"2018-12-10T10:30:00@Etc/UTC\").timezone",
                "PathExpression(DateTimeLiteral(date and time, \"2018-12-10T10:30:00@Etc/UTC\"), timezone)",
                "string",
                "timezone(dateAndTime(\"2018-12-10T10:30:00@Etc/UTC\"))",
                this.lib.timezone(this.lib.dateAndTime("2018-12-10T10:30:00@Etc/UTC")),
                "Etc/UTC"
        );
        doExpressionTest(entries, "", "date and time(\"2018-12-10T10:30:00\").timezone",
                "PathExpression(DateTimeLiteral(date and time, \"2018-12-10T10:30:00\"), timezone)",
                "string",
                "timezone(dateAndTime(\"2018-12-10T10:30:00\"))",
                this.lib.timezone(this.lib.dateAndTime("2018-12-10T10:30:00")),
                null
        );
        doExpressionTest(entries, "", "time(\"10:30:00\").time offset",
                "PathExpression(DateTimeLiteral(time, \"10:30:00\"), time offset)",
                "days and time duration",
                "timeOffset(time(\"10:30:00\"))",
                this.lib.timeOffset(this.lib.time("10:30:00")),
                null
        );
        doExpressionTest(entries, "", "time(\"10:30:00@Etc/UTC\").timezone",
                "PathExpression(DateTimeLiteral(time, \"10:30:00@Etc/UTC\"), timezone)",
                "string",
                "timezone(time(\"10:30:00@Etc/UTC\"))",
                this.lib.timezone(this.lib.time("10:30:00@Etc/UTC")),
                "Etc/UTC"
        );
        doExpressionTest(entries, "", "time(\"10:30:00\").timezone",
                "PathExpression(DateTimeLiteral(time, \"10:30:00\"), timezone)",
                "string",
                "timezone(time(\"10:30:00\"))",
                this.lib.timezone(this.lib.time("10:30:00")),
                null
        );
    }

    @Override
    protected String numberType() {
        return Number.class.getName();
    }
}