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
package com.gs.dmn.feel.analysis.semantics.type;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FEELTypeTest {
    @Test
    public void testTypeExpression() {
        assertEquals("number", NumberType.NUMBER.typeExpression());
        assertEquals("string", StringType.STRING.typeExpression());
        assertEquals("boolean", BooleanType.BOOLEAN.typeExpression());
        assertEquals("date", DateType.DATE.typeExpression());
        assertEquals("time", TimeType.TIME.typeExpression());
        assertEquals("date and time", DateTimeType.DATE_AND_TIME.typeExpression());
        assertEquals("duration", DurationType.DURATION.typeExpression());
        assertEquals("days and time duration", DaysAndTimeDurationType.DAYS_AND_TIME_DURATION.typeExpression());
        assertEquals("years and months duration", YearsAndMonthsDurationType.YEARS_AND_MONTHS_DURATION.typeExpression());

        assertEquals("list<Any>", ListType.ANY_LIST.typeExpression());
        assertEquals("list<number>", ListType.NUMBER_LIST.typeExpression());
        assertEquals("list<boolean>", ListType.BOOLEAN_LIST.typeExpression());
        assertEquals("list<string>", ListType.STRING_LIST.typeExpression());

        assertEquals("range<Any>", RangeType.ANY_RANGE.typeExpression());
        assertEquals("range<comparable>", RangeType.COMPARABLE_RANGE.typeExpression());
        assertEquals("range<number>", RangeType.NUMBER_RANGE.typeExpression());
        assertEquals("range<string>", RangeType.STRING_RANGE.typeExpression());

        assertEquals("context<>", ContextType.CONTEXT.typeExpression());
        assertEquals("context<a: number, b: string>", new ContextType().addMember("a", Arrays.asList(), NumberType.NUMBER).addMember("b", Arrays.asList(), StringType.STRING).typeExpression());

        assertEquals("function<Null, Null> -> boolean", FunctionType.PREDICATE_FUNCTION.typeExpression());
    }

    @Test
    public void testToString() {
        assertEquals("number", NumberType.NUMBER.toString());
        assertEquals("string", StringType.STRING.toString());
        assertEquals("boolean", BooleanType.BOOLEAN.toString());
        assertEquals("date", DateType.DATE.toString());
        assertEquals("time", TimeType.TIME.toString());
        assertEquals("date and time", DateTimeType.DATE_AND_TIME.toString());
        assertEquals("duration", DurationType.DURATION.toString());
        assertEquals("days and time duration", DaysAndTimeDurationType.DAYS_AND_TIME_DURATION.toString());
        assertEquals("years and months duration", YearsAndMonthsDurationType.YEARS_AND_MONTHS_DURATION.toString());

        assertEquals("ListType(Any)", ListType.ANY_LIST.toString());
        assertEquals("ListType(number)", ListType.NUMBER_LIST.toString());
        assertEquals("ListType(boolean)", ListType.BOOLEAN_LIST.toString());
        assertEquals("ListType(string)", ListType.STRING_LIST.toString());

        assertEquals("RangeType(Any)", RangeType.ANY_RANGE.toString());
        assertEquals("RangeType(comparable)", RangeType.COMPARABLE_RANGE.toString());
        assertEquals("RangeType(number)", RangeType.NUMBER_RANGE.toString());
        assertEquals("RangeType(string)", RangeType.STRING_RANGE.toString());

        assertEquals("ContextType()", ContextType.CONTEXT.toString());
        assertEquals("ContextType(a = number, b = string)", new ContextType().addMember("a", Arrays.asList(), NumberType.NUMBER).addMember("b", Arrays.asList(), StringType.STRING).toString());

        assertEquals("PredicateFunctionType(FormalParameter(first, Null, false, false), FormalParameter(second, Null, false, false), boolean, false)", FunctionType.PREDICATE_FUNCTION.toString());
    }
}
