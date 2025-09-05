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

import com.gs.dmn.AbstractTest;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.semantics.type.ContextType;
import com.gs.dmn.feel.analysis.semantics.type.ItemDefinitionType;
import com.gs.dmn.feel.analysis.semantics.type.RangeType;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static com.gs.dmn.el.analysis.semantics.type.AnyType.ANY;
import static com.gs.dmn.el.analysis.semantics.type.NullType.NULL;
import static com.gs.dmn.feel.analysis.semantics.type.BooleanType.BOOLEAN;
import static com.gs.dmn.feel.analysis.semantics.type.ContextType.CONTEXT;
import static com.gs.dmn.feel.analysis.semantics.type.DateTimeType.DATE_AND_TIME;
import static com.gs.dmn.feel.analysis.semantics.type.DateType.DATE;
import static com.gs.dmn.feel.analysis.semantics.type.DaysAndTimeDurationType.DAYS_AND_TIME_DURATION;
import static com.gs.dmn.feel.analysis.semantics.type.DurationType.DURATION;
import static com.gs.dmn.feel.analysis.semantics.type.ItemDefinitionType.ANY_ITEM_DEFINITION;
import static com.gs.dmn.feel.analysis.semantics.type.ListType.ANY_LIST;
import static com.gs.dmn.feel.analysis.semantics.type.ListType.NUMBER_LIST;
import static com.gs.dmn.feel.analysis.semantics.type.NumberType.NUMBER;
import static com.gs.dmn.feel.analysis.semantics.type.RangeType.*;
import static com.gs.dmn.feel.analysis.semantics.type.StringType.STRING;
import static com.gs.dmn.feel.analysis.semantics.type.TimeType.TIME;
import static com.gs.dmn.feel.analysis.semantics.type.YearsAndMonthsDurationType.YEARS_AND_MONTHS_DURATION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class OperatorDecisionTableTest extends AbstractTest {
    @Test
    public void testEquality() {
        // Exact types
        check("=", NUMBER, NUMBER, BOOLEAN, "numericEqual");
        check("=", BOOLEAN, BOOLEAN, BOOLEAN, "booleanEqual");
        check("=", STRING, STRING, BOOLEAN, "stringEqual");
        check("=", DATE, DATE, BOOLEAN, "dateEqual");
        check("=", TIME, TIME, BOOLEAN, "timeEqual");
        check("=", DATE_AND_TIME, DATE_AND_TIME, BOOLEAN, "dateTimeEqual");
        check("=", YEARS_AND_MONTHS_DURATION, YEARS_AND_MONTHS_DURATION, BOOLEAN, "durationEqual");
        check("=", DAYS_AND_TIME_DURATION, DAYS_AND_TIME_DURATION, BOOLEAN, "durationEqual");
        check("=", ANY_LIST, ANY_LIST, BOOLEAN, "listEqual");
        check("=", CONTEXT, CONTEXT, BOOLEAN, "contextEqual");
        check("=", ANY_ITEM_DEFINITION, ANY_ITEM_DEFINITION, BOOLEAN, "contextEqual");
        check("=", ANY_RANGE, ANY_RANGE, BOOLEAN, "rangeEqual");

        check("=", NUMBER_LIST, ANY_LIST, BOOLEAN, "listEqual");
        ContextType contextType = new ContextType();
        contextType.addMember("a", Collections.emptyList(), NUMBER);
        check("=", contextType, CONTEXT, BOOLEAN, "contextEqual");
        ItemDefinitionType itemDefinitionType = makeItemDefinitionType("test");
        itemDefinitionType.addMember("a", Collections.emptyList(), NUMBER);
        check("=", itemDefinitionType, ANY_ITEM_DEFINITION, BOOLEAN, "contextEqual");
        Type rangeType = new RangeType(NUMBER);
        check("=", rangeType, ANY_RANGE, BOOLEAN, "rangeEqual");

        check("=", NUMBER, null, BOOLEAN, "numericEqual");
        check("=", NUMBER, NULL, BOOLEAN, "numericEqual");
        check("=", NUMBER, ANY, BOOLEAN, "numericEqual");
        check("==", DURATION, DURATION, BOOLEAN, "durationEqual");

        check("==", DAYS_AND_TIME_DURATION, DURATION, BOOLEAN, "durationEqual");
        check("==", DURATION, DAYS_AND_TIME_DURATION, BOOLEAN, "durationEqual");
        check("==", YEARS_AND_MONTHS_DURATION, DURATION, BOOLEAN, "durationEqual");
        check("==", DURATION, YEARS_AND_MONTHS_DURATION, BOOLEAN, "durationEqual");
        check("==", DURATION, DURATION, BOOLEAN, "durationEqual");

        check("!=", DAYS_AND_TIME_DURATION, DURATION, BOOLEAN, "durationNotEqual");
        check("!=", DURATION, DAYS_AND_TIME_DURATION, BOOLEAN, "durationNotEqual");
        check("!=", YEARS_AND_MONTHS_DURATION, DURATION, BOOLEAN, "durationNotEqual");
        check("!=", DURATION, YEARS_AND_MONTHS_DURATION, BOOLEAN, "durationNotEqual");
        check("!=", DURATION, DURATION, BOOLEAN, "durationNotEqual");
    }

    @Test
    public void testAddition() {
        check("+", NUMBER, NUMBER, NUMBER, "numericAdd");
        check("-", NUMBER, NUMBER, NUMBER, "numericSubtract");

        check("-", DATE_AND_TIME, DATE_AND_TIME, DAYS_AND_TIME_DURATION, "dateTimeSubtract");
        check("-", DATE_AND_TIME, DATE, DAYS_AND_TIME_DURATION, "dateTimeSubtract");
        check("-", DATE, DATE, DAYS_AND_TIME_DURATION, "dateSubtract");
        check("-", DATE, DATE_AND_TIME, DAYS_AND_TIME_DURATION, "dateSubtract");

        check("-", TIME, TIME, DAYS_AND_TIME_DURATION, "timeSubtract");

        check("+", YEARS_AND_MONTHS_DURATION, YEARS_AND_MONTHS_DURATION, YEARS_AND_MONTHS_DURATION, "durationAdd");
        check("+", YEARS_AND_MONTHS_DURATION, DURATION, YEARS_AND_MONTHS_DURATION, "durationAdd");
        check("+", DURATION, YEARS_AND_MONTHS_DURATION, YEARS_AND_MONTHS_DURATION, "durationAdd");
        check("-", YEARS_AND_MONTHS_DURATION, YEARS_AND_MONTHS_DURATION, YEARS_AND_MONTHS_DURATION, "durationSubtract");
        check("-", YEARS_AND_MONTHS_DURATION, DURATION, YEARS_AND_MONTHS_DURATION, "durationSubtract");
        check("-", DURATION, YEARS_AND_MONTHS_DURATION, YEARS_AND_MONTHS_DURATION, "durationSubtract");

        check("+", DAYS_AND_TIME_DURATION, DAYS_AND_TIME_DURATION, DAYS_AND_TIME_DURATION, "durationAdd");
        check("+", DAYS_AND_TIME_DURATION, DURATION, DAYS_AND_TIME_DURATION, "durationAdd");
        check("+", DURATION, DAYS_AND_TIME_DURATION, DAYS_AND_TIME_DURATION, "durationAdd");
        check("+", DURATION, DURATION, DURATION, "durationAdd");
        check("-", DAYS_AND_TIME_DURATION, DAYS_AND_TIME_DURATION, DAYS_AND_TIME_DURATION, "durationSubtract");
        check("-", DAYS_AND_TIME_DURATION, DURATION, DAYS_AND_TIME_DURATION, "durationSubtract");
        check("-", DURATION, DAYS_AND_TIME_DURATION, DAYS_AND_TIME_DURATION, "durationSubtract");
        check("-", DURATION, DURATION, DURATION, "durationSubtract");

        check("+", DATE_AND_TIME, YEARS_AND_MONTHS_DURATION, DATE_AND_TIME, "dateTimeAddDuration");
        check("-", DATE_AND_TIME, YEARS_AND_MONTHS_DURATION, DATE_AND_TIME, "dateTimeSubtractDuration");

        check("+", YEARS_AND_MONTHS_DURATION, DATE_AND_TIME, DATE_AND_TIME, "dateTimeAddDuration");

        check("+", DATE_AND_TIME, DAYS_AND_TIME_DURATION, DATE_AND_TIME, "dateTimeAddDuration");
        check("+", DATE_AND_TIME, DURATION, DATE_AND_TIME, "dateTimeAddDuration");
        check("-", DATE_AND_TIME, DAYS_AND_TIME_DURATION, DATE_AND_TIME, "dateTimeSubtractDuration");
        check("-", DATE_AND_TIME, DURATION, DATE_AND_TIME, "dateTimeSubtractDuration");

        check("+", DAYS_AND_TIME_DURATION, DATE_AND_TIME, DATE_AND_TIME, "dateTimeAddDuration");
        check("+", DURATION, DATE_AND_TIME, DATE_AND_TIME, "dateTimeAddDuration");

        check("+", TIME, DAYS_AND_TIME_DURATION, TIME, "timeAddDuration");
        check("+", TIME, DURATION, TIME, "timeAddDuration");
        check("-", TIME, DAYS_AND_TIME_DURATION, TIME, "timeSubtractDuration");
        check("-", TIME, DURATION, TIME, "timeSubtractDuration");

        check("+", DAYS_AND_TIME_DURATION, TIME, TIME, "timeAddDuration");
        check("+", DURATION, TIME, TIME, "timeAddDuration");

        check("+", STRING, STRING, STRING, "stringAdd");

        check("+", DATE, YEARS_AND_MONTHS_DURATION, DATE, "dateAddDuration");
        check("-", DATE, YEARS_AND_MONTHS_DURATION, DATE, "dateSubtractDuration");

        check("+", YEARS_AND_MONTHS_DURATION, DATE, DATE, "dateAddDuration");

        check("+", DATE, DAYS_AND_TIME_DURATION, DATE, "dateAddDuration");
        check("+", DATE, DURATION, DATE, "dateAddDuration");
        check("-", DATE, DAYS_AND_TIME_DURATION, DATE, "dateSubtractDuration");
        check("-", DATE, DURATION, DATE, "dateSubtractDuration");

        check("+", DAYS_AND_TIME_DURATION, DATE, DATE, "dateAddDuration");
        check("+", DURATION, DATE, DATE, "dateAddDuration");
    }

    @Test
    public void testMultiplication() {
        check("*", NUMBER, NUMBER, NUMBER, "numericMultiply");
        check("/", NUMBER, NUMBER, NUMBER, "numericDivide");

        check("*", YEARS_AND_MONTHS_DURATION, NUMBER, YEARS_AND_MONTHS_DURATION, "durationMultiplyNumber");
        check("/", YEARS_AND_MONTHS_DURATION, NUMBER, YEARS_AND_MONTHS_DURATION, "durationDivideNumber");

        check("*", NUMBER, YEARS_AND_MONTHS_DURATION, YEARS_AND_MONTHS_DURATION, "durationMultiplyNumber");

        check("/", YEARS_AND_MONTHS_DURATION, YEARS_AND_MONTHS_DURATION, NUMBER, "durationDivide");
        check("/", YEARS_AND_MONTHS_DURATION, DURATION, NUMBER, "durationDivide");
        check("/", DURATION, YEARS_AND_MONTHS_DURATION, NUMBER, "durationDivide");

        check("*", DAYS_AND_TIME_DURATION, NUMBER, DAYS_AND_TIME_DURATION, "durationMultiplyNumber");
        check("/", DAYS_AND_TIME_DURATION, NUMBER, DAYS_AND_TIME_DURATION, "durationDivideNumber");

        check("*", NUMBER, DAYS_AND_TIME_DURATION, DAYS_AND_TIME_DURATION, "durationMultiplyNumber");

        check("/", DAYS_AND_TIME_DURATION, DAYS_AND_TIME_DURATION, NUMBER, "durationDivide");
        check("/", DAYS_AND_TIME_DURATION, DURATION, NUMBER, "durationDivide");
        check("/", DURATION, DAYS_AND_TIME_DURATION, NUMBER, "durationDivide");

        check("*", DURATION, NUMBER, DURATION, "durationMultiplyNumber");
        check("/", DURATION, NUMBER, DURATION, "durationDivideNumber");

        check("*", NUMBER, DURATION, DURATION, "durationMultiplyNumber");

        check("/", DURATION, DURATION, NUMBER, "durationDivide");
    }

    @Test
    public void testRelational() {
        check("<", NUMBER, NUMBER, BOOLEAN, "numericLessThan");
        check("<", STRING, STRING, BOOLEAN, "stringLessThan");
        check("<", DATE, DATE, BOOLEAN, "dateLessThan");
        check("<", TIME, TIME, BOOLEAN, "timeLessThan");
        check("<", DATE_AND_TIME, DATE_AND_TIME, BOOLEAN, "dateTimeLessThan");
        check("<", YEARS_AND_MONTHS_DURATION, YEARS_AND_MONTHS_DURATION, BOOLEAN, "durationLessThan");
        check("<", YEARS_AND_MONTHS_DURATION, DURATION, BOOLEAN, "durationLessThan");
        check("<", DURATION, YEARS_AND_MONTHS_DURATION, BOOLEAN, "durationLessThan");
        check("<", DAYS_AND_TIME_DURATION, DAYS_AND_TIME_DURATION, BOOLEAN, "durationLessThan");
        check("<", DAYS_AND_TIME_DURATION, DURATION, BOOLEAN, "durationLessThan");
        check("<", DURATION, DAYS_AND_TIME_DURATION, BOOLEAN, "durationLessThan");
        check("<", DURATION, DURATION, BOOLEAN, "durationLessThan");

        check(">", NUMBER, NUMBER, BOOLEAN, "numericGreaterThan");
        check(">", STRING, STRING, BOOLEAN, "stringGreaterThan");
        check(">", DATE, DATE, BOOLEAN, "dateGreaterThan");
        check(">", TIME, TIME, BOOLEAN, "timeGreaterThan");
        check(">", DATE_AND_TIME, DATE_AND_TIME, BOOLEAN, "dateTimeGreaterThan");
        check(">", YEARS_AND_MONTHS_DURATION, YEARS_AND_MONTHS_DURATION, BOOLEAN, "durationGreaterThan");
        check(">", YEARS_AND_MONTHS_DURATION, DURATION, BOOLEAN, "durationGreaterThan");
        check(">", DURATION, YEARS_AND_MONTHS_DURATION, BOOLEAN, "durationGreaterThan");
        check(">", DAYS_AND_TIME_DURATION, DAYS_AND_TIME_DURATION, BOOLEAN, "durationGreaterThan");
        check(">", DAYS_AND_TIME_DURATION, DURATION, BOOLEAN, "durationGreaterThan");
        check(">", DURATION, DAYS_AND_TIME_DURATION, BOOLEAN, "durationGreaterThan");
        check(">", DURATION, DURATION, BOOLEAN, "durationGreaterThan");

        check("<=", NUMBER, NUMBER, BOOLEAN, "numericLessEqualThan");
        check("<=", STRING, STRING, BOOLEAN, "stringLessEqualThan");
        check("<=", DATE, DATE, BOOLEAN, "dateLessEqualThan");
        check("<=", TIME, TIME, BOOLEAN, "timeLessEqualThan");
        check("<=", DATE_AND_TIME, DATE_AND_TIME, BOOLEAN, "dateTimeLessEqualThan");
        check("<=", YEARS_AND_MONTHS_DURATION, YEARS_AND_MONTHS_DURATION, BOOLEAN, "durationLessEqualThan");
        check("<=", YEARS_AND_MONTHS_DURATION, DURATION, BOOLEAN, "durationLessEqualThan");
        check("<=", DURATION, YEARS_AND_MONTHS_DURATION, BOOLEAN, "durationLessEqualThan");
        check("<=", DAYS_AND_TIME_DURATION, DAYS_AND_TIME_DURATION, BOOLEAN, "durationLessEqualThan");
        check("<=", DAYS_AND_TIME_DURATION, DURATION, BOOLEAN, "durationLessEqualThan");
        check("<=", DURATION, DAYS_AND_TIME_DURATION, BOOLEAN, "durationLessEqualThan");
        check("<=", DURATION, DURATION, BOOLEAN, "durationLessEqualThan");

        check(">=", NUMBER, NUMBER, BOOLEAN, "numericGreaterEqualThan");
        check(">=", STRING, STRING, BOOLEAN, "stringGreaterEqualThan");
        check(">=", DATE, DATE, BOOLEAN, "dateGreaterEqualThan");
        check(">=", TIME, TIME, BOOLEAN, "timeGreaterEqualThan");
        check(">=", DATE_AND_TIME, DATE_AND_TIME, BOOLEAN, "dateTimeGreaterEqualThan");
        check(">=", YEARS_AND_MONTHS_DURATION, YEARS_AND_MONTHS_DURATION, BOOLEAN, "durationGreaterEqualThan");
        check(">=", YEARS_AND_MONTHS_DURATION, DURATION, BOOLEAN, "durationGreaterEqualThan");
        check(">=", DURATION, YEARS_AND_MONTHS_DURATION, BOOLEAN, "durationGreaterEqualThan");
        check(">=", DAYS_AND_TIME_DURATION, DAYS_AND_TIME_DURATION, BOOLEAN, "durationGreaterEqualThan");
        check(">=", DAYS_AND_TIME_DURATION, DURATION, BOOLEAN, "durationGreaterEqualThan");
        check(">=", DURATION, DAYS_AND_TIME_DURATION, BOOLEAN, "durationGreaterEqualThan");
        check(">=", DURATION, DURATION, BOOLEAN, "durationGreaterEqualThan");
    }

    @Test
    public void testBoolean() {
        // boolean
        check("or", ANY, ANY, BOOLEAN, "booleanOr");
        check("and", ANY, ANY, BOOLEAN, "booleanAnd");
        check("not", ANY, null, BOOLEAN, "booleanNot");
    }
    
    @Test
    public void testRange() {
        // Range
        check("..", NUMBER, NUMBER, NUMBER_RANGE, null);
        check("..", STRING, STRING, STRING_RANGE, null);
        check("..", DATE, DATE, DATE_RANGE, null);
        check("..", TIME, TIME, TIME_RANGE, null);
        check("..", DATE_AND_TIME, DATE_AND_TIME, DATE_AND_TIME_RANGE, null);
        check("..", YEARS_AND_MONTHS_DURATION, YEARS_AND_MONTHS_DURATION, YEARS_AND_MONTHS_DURATION_RANGE, null);
        check("..", DAYS_AND_TIME_DURATION, DAYS_AND_TIME_DURATION, DAYS_AND_TIME_DURATION_RANGE, null);
    }
    
    private void check(String operator, Type leftType, Type rightType, Type resultType, String nativeOperator) {
        assertEquals(resultType, OperatorDecisionTable.resultType(operator, leftType, rightType));
        if (nativeOperator == null) {
            assertNull(OperatorDecisionTable.nativeOperator(operator, leftType, rightType));
        } else {
            assertEquals(nativeOperator, OperatorDecisionTable.nativeOperator(operator, leftType, rightType).getName());
        }
    }
}