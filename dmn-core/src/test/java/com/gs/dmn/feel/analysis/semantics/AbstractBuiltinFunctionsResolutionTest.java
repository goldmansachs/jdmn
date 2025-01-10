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
package com.gs.dmn.feel.analysis.semantics;

import com.gs.dmn.context.DMNContext;
import com.gs.dmn.el.analysis.ELAnalyzer;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FunctionInvocation;
import com.gs.dmn.feel.analysis.syntax.ast.expression.literal.SimpleLiteral;
import com.gs.dmn.feel.analysis.syntax.ast.expression.logic.LogicNegation;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractBuiltinFunctionsResolutionTest {
    protected final String numberString = "\"123.00\"";
    protected final String number = "3";
    protected final String stringString = "\"string\"";
    protected final String dateString = "\"2000-01-01\"";
    protected final String date = "date(" + dateString + ")";
    protected final String timeString = "\"12:00:00\"";
    protected final String time = "time(" + timeString + ")";
    protected final String dateTimeString = "\"2000-01-01T12:00:00\"";
    protected final String dateTime = "date and time(\"2000-01-01T12:00:00\")";
    protected final String yearsAndMonthsDurationString = "\"P1Y1M\"";
    protected final String daysAndTimeDurationString = "\"P1DT2H\"";
    protected final String yearsAndMonthsDuration = "duration(" + yearsAndMonthsDurationString + ")";
    protected final String daysAndTimeDuration = "duration(" + daysAndTimeDurationString + ")";

    protected final String numberSequence = "1, 2, 3";
    protected final String numberList = "[" + numberSequence + "]";
    protected final String numberRange = "[1..2]";

    protected final String booleanSequence = "true, true, true";
    protected final String booleanList = "[" + booleanSequence + "]";

    protected final String stringSequence = stringString + "," + stringString;
    protected final String stringList = "[" + stringSequence + "]";

    protected final String dateSequence = date + "," + date + "," + date;
    protected final String dateList = "[" + dateSequence + "]";

    protected final String timeSequence = time + "," + time + "," + time;
    protected final String timeList = "[" + timeSequence + "]";

    protected final String dateTimeSequence = dateTime + "," + dateTime + "," + dateTime;
    protected final String dateTimeList = "[" + dateTimeSequence + "]";

    protected final String yearsAndMonthsDurationSequence = yearsAndMonthsDuration + "," + yearsAndMonthsDuration;
    protected final String yearsAndMonthsDurationList = "[" + yearsAndMonthsDurationSequence + "]";

    protected final String daysAndTimeDurationSequence = daysAndTimeDuration + "," + daysAndTimeDuration;
    protected final String daysAndTimeDurationList = "[" + daysAndTimeDurationSequence + "]";

    protected final String context = "{\"k\": 123}";
    protected final String contextValue = "\"123\"";

    protected void testFunctionInvocation(String text, String expectedType, boolean error) {
        try {
            Expression<Type> expression = (Expression<Type>) getFEELAnalyzer().analyzeExpression(text, getDMNContext());
            if (expression instanceof SimpleLiteral) {
                assertEquals(expectedType, expression.getClass().getSimpleName());
                assertFalse(error);
            } else if (expression instanceof LogicNegation) {
                assertEquals(expectedType, expression.getClass().getSimpleName());
                assertFalse(error);
            } else {
                FunctionInvocation<Type> functionInvocation = (FunctionInvocation<Type>) expression;
                Type actualType = functionInvocation.getFunction().getType();
                assertEquals(expectedType, actualType.toString());
            }
            assertFalse(error);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(error, text);
        }
    }

    protected abstract ELAnalyzer<Type, DMNContext> getFEELAnalyzer();

    protected abstract DMNContext getDMNContext();
}