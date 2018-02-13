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
package com.gs.dmn.feel;

import com.gs.dmn.feel.analysis.semantics.type.ListType;
import com.gs.dmn.feel.analysis.semantics.type.NullType;
import com.gs.dmn.feel.analysis.semantics.type.NumberType;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.synthesis.JavaOperator;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.gs.dmn.feel.analysis.semantics.type.BooleanType.BOOLEAN;
import static com.gs.dmn.feel.analysis.semantics.type.DateTimeType.DATE_AND_TIME;
import static com.gs.dmn.feel.analysis.semantics.type.DateType.DATE;
import static com.gs.dmn.feel.analysis.semantics.type.DurationType.DAYS_AND_TIME_DURATION;
import static com.gs.dmn.feel.analysis.semantics.type.DurationType.YEARS_AND_MONTHS_DURATION;
import static com.gs.dmn.feel.analysis.semantics.type.ListType.ANY_LIST;
import static com.gs.dmn.feel.analysis.semantics.type.NullType.NULL;
import static com.gs.dmn.feel.analysis.semantics.type.NumberType.NUMBER;
import static com.gs.dmn.feel.analysis.semantics.type.RangeType.NUMBER_RANGE_TYPE;
import static com.gs.dmn.feel.analysis.semantics.type.StringType.STRING;
import static com.gs.dmn.feel.analysis.semantics.type.TimeType.TIME;
import static com.gs.dmn.feel.synthesis.JavaOperator.Associativity.LEFT_RIGHT;
import static com.gs.dmn.feel.synthesis.JavaOperator.Associativity.RIGHT_LEFT;
import static com.gs.dmn.feel.synthesis.JavaOperator.Notation.FUNCTIONAL;
import static com.gs.dmn.feel.synthesis.JavaOperator.Notation.INFIX;

public class OperatorDecisionTable {
    private static final Map<OperatorTableInputEntry, Pair<Type, JavaOperator>> MAPPINGS = new LinkedHashMap() {{
        // boolean
        put(new OperatorTableInputEntry("or", BOOLEAN, BOOLEAN), new Pair(BOOLEAN, new JavaOperator("booleanOr", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("or", BOOLEAN, NULL), new Pair(BOOLEAN, new JavaOperator("booleanOr", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("or", NULL, BOOLEAN), new Pair(BOOLEAN, new JavaOperator("booleanOr", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("or", NULL, NULL), new Pair(BOOLEAN, new JavaOperator("booleanOr", 2, true, LEFT_RIGHT, FUNCTIONAL)));

        put(new OperatorTableInputEntry("and", BOOLEAN, BOOLEAN), new Pair(BOOLEAN, new JavaOperator("booleanAnd", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("and", BOOLEAN, NULL), new Pair(BOOLEAN, new JavaOperator("booleanAnd", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("and", NULL, BOOLEAN), new Pair(BOOLEAN, new JavaOperator("booleanAnd", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("and", NULL, NULL), new Pair(BOOLEAN, new JavaOperator("booleanAnd", 2, true, LEFT_RIGHT, FUNCTIONAL)));

        put(new OperatorTableInputEntry("not", BOOLEAN, null), new Pair(BOOLEAN, new JavaOperator("booleanNot", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("not", NULL, null), new Pair(BOOLEAN, new JavaOperator("booleanNot", 2, true, LEFT_RIGHT, FUNCTIONAL)));

        // equality
        put(new OperatorTableInputEntry("=", NUMBER, NUMBER), new Pair(BOOLEAN, new JavaOperator("numericEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("=", NUMBER, NULL), new Pair(BOOLEAN, new JavaOperator("numericEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("=", NULL, NUMBER), new Pair(BOOLEAN, new JavaOperator("numericEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));

        put(new OperatorTableInputEntry("=", BOOLEAN, BOOLEAN), new Pair(BOOLEAN, new JavaOperator("booleanEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("=", BOOLEAN, NULL), new Pair(BOOLEAN, new JavaOperator("booleanEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("=", NULL, BOOLEAN), new Pair(BOOLEAN, new JavaOperator("booleanEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));

        put(new OperatorTableInputEntry("=", STRING, STRING), new Pair(BOOLEAN, new JavaOperator("stringEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("=", STRING, NULL), new Pair(BOOLEAN, new JavaOperator("stringEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("=", NULL, STRING), new Pair(BOOLEAN, new JavaOperator("stringEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));

        put(new OperatorTableInputEntry("=", DATE, DATE), new Pair(BOOLEAN, new JavaOperator("dateEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("=", DATE, NULL), new Pair(BOOLEAN, new JavaOperator("dateEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("=", NULL, DATE), new Pair(BOOLEAN, new JavaOperator("dateEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));

        put(new OperatorTableInputEntry("=", TIME, TIME), new Pair(BOOLEAN, new JavaOperator("timeEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("=", TIME, NULL), new Pair(BOOLEAN, new JavaOperator("timeEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("=", NULL, TIME), new Pair(BOOLEAN, new JavaOperator("timeEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));

        put(new OperatorTableInputEntry("=", DATE_AND_TIME, DATE_AND_TIME), new Pair(BOOLEAN, new JavaOperator("dateTimeEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("=", DATE_AND_TIME, NULL), new Pair(BOOLEAN, new JavaOperator("dateTimeEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("=", NULL, DATE_AND_TIME), new Pair(BOOLEAN, new JavaOperator("dateTimeEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));

        put(new OperatorTableInputEntry("=", YEARS_AND_MONTHS_DURATION, YEARS_AND_MONTHS_DURATION), new Pair(BOOLEAN, new JavaOperator("durationEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("=", YEARS_AND_MONTHS_DURATION, NULL), new Pair(BOOLEAN, new JavaOperator("durationEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("=", NULL, YEARS_AND_MONTHS_DURATION), new Pair(BOOLEAN, new JavaOperator("durationEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));

        put(new OperatorTableInputEntry("=", NULL, NULL), new Pair(BOOLEAN, new JavaOperator("==", 2, true, LEFT_RIGHT, INFIX)));

        put(new OperatorTableInputEntry("=", DAYS_AND_TIME_DURATION, DAYS_AND_TIME_DURATION), new Pair(BOOLEAN, new JavaOperator("durationEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("=", DAYS_AND_TIME_DURATION, NULL), new Pair(BOOLEAN, new JavaOperator("durationEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("=", NULL, DAYS_AND_TIME_DURATION), new Pair(BOOLEAN, new JavaOperator("durationEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));

        put(new OperatorTableInputEntry("=", ANY_LIST, ANY_LIST), new Pair(BOOLEAN, new JavaOperator("listEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("=", ANY_LIST, NULL), new Pair(BOOLEAN, new JavaOperator("listEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("=", NULL, ANY_LIST), new Pair(BOOLEAN, new JavaOperator("listEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));

        put(new OperatorTableInputEntry("!=", NUMBER, NUMBER), new Pair(BOOLEAN, new JavaOperator("numericNotEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("!=", NUMBER, NULL), new Pair(BOOLEAN, new JavaOperator("numericNotEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("!=", NULL, NUMBER), new Pair(BOOLEAN, new JavaOperator("numericNotEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));

        put(new OperatorTableInputEntry("!=", BOOLEAN, BOOLEAN), new Pair(BOOLEAN, new JavaOperator("booleanNotEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("!=", BOOLEAN, NULL), new Pair(BOOLEAN, new JavaOperator("booleanNotEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("!=", NULL, BOOLEAN), new Pair(BOOLEAN, new JavaOperator("booleanNotEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));

        put(new OperatorTableInputEntry("!=", STRING, STRING), new Pair(BOOLEAN, new JavaOperator("stringNotEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("!=", STRING, NULL), new Pair(BOOLEAN, new JavaOperator("stringNotEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("!=", NULL, STRING), new Pair(BOOLEAN, new JavaOperator("stringNotEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));

        put(new OperatorTableInputEntry("!=", DATE, DATE), new Pair(BOOLEAN, new JavaOperator("dateNotEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("!=", DATE, NULL), new Pair(BOOLEAN, new JavaOperator("dateNotEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("!=", NULL, DATE), new Pair(BOOLEAN, new JavaOperator("dateNotEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));

        put(new OperatorTableInputEntry("!=", TIME, TIME), new Pair(BOOLEAN, new JavaOperator("timeNotEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("!=", TIME, NULL), new Pair(BOOLEAN, new JavaOperator("timeNotEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("!=", NULL, TIME), new Pair(BOOLEAN, new JavaOperator("timeNotEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));

        put(new OperatorTableInputEntry("!=", DATE_AND_TIME, DATE_AND_TIME), new Pair(BOOLEAN, new JavaOperator("dateTimeNotEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("!=", DATE_AND_TIME, NULL), new Pair(BOOLEAN, new JavaOperator("dateTimeNotEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("!=", NULL, DATE_AND_TIME), new Pair(BOOLEAN, new JavaOperator("dateTimeNotEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));

        put(new OperatorTableInputEntry("!=", YEARS_AND_MONTHS_DURATION, YEARS_AND_MONTHS_DURATION), new Pair(BOOLEAN, new JavaOperator("durationNotEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("!=", YEARS_AND_MONTHS_DURATION, NULL), new Pair(BOOLEAN, new JavaOperator("durationNotEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("!=", NULL, YEARS_AND_MONTHS_DURATION), new Pair(BOOLEAN, new JavaOperator("durationNotEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));

        put(new OperatorTableInputEntry("!=", DAYS_AND_TIME_DURATION, DAYS_AND_TIME_DURATION), new Pair(BOOLEAN, new JavaOperator("durationNotEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("!=", DAYS_AND_TIME_DURATION, NULL), new Pair(BOOLEAN, new JavaOperator("durationNotEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("!=", NULL, DAYS_AND_TIME_DURATION), new Pair(BOOLEAN, new JavaOperator("durationNotEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));

        put(new OperatorTableInputEntry("!=", ANY_LIST, ANY_LIST), new Pair(BOOLEAN, new JavaOperator("listNotEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("!=", ANY_LIST, NULL), new Pair(BOOLEAN, new JavaOperator("listNotEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("!=", NULL, ANY_LIST), new Pair(BOOLEAN, new JavaOperator("listNotEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));

        put(new OperatorTableInputEntry("!=", NULL, NULL), new Pair(BOOLEAN, new JavaOperator("!=", 2, true, LEFT_RIGHT, INFIX)));

        // Relational
        put(new OperatorTableInputEntry("<", NUMBER, NUMBER), new Pair(BOOLEAN, new JavaOperator("numericLessThan", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("<", STRING, STRING), new Pair(BOOLEAN, new JavaOperator("stringLessThan", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("<", DATE, DATE), new Pair(BOOLEAN, new JavaOperator("dateLessThan", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("<", TIME, TIME), new Pair(BOOLEAN, new JavaOperator("timeLessThan", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("<", DATE_AND_TIME, DATE_AND_TIME), new Pair(BOOLEAN, new JavaOperator("dateTimeLessThan", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("<", YEARS_AND_MONTHS_DURATION, YEARS_AND_MONTHS_DURATION), new Pair(BOOLEAN, new JavaOperator("durationLessThan", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("<", DAYS_AND_TIME_DURATION, DAYS_AND_TIME_DURATION), new Pair(BOOLEAN, new JavaOperator("durationLessThan", 2, true, LEFT_RIGHT, FUNCTIONAL)));

        put(new OperatorTableInputEntry(">", NUMBER, NUMBER), new Pair(BOOLEAN, new JavaOperator("numericGreaterThan", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry(">", STRING, STRING), new Pair(BOOLEAN, new JavaOperator("stringGreaterThan", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry(">", DATE, DATE), new Pair(BOOLEAN, new JavaOperator("dateGreaterThan", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry(">", TIME, TIME), new Pair(BOOLEAN, new JavaOperator("timeGreaterThan", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry(">", DATE_AND_TIME, DATE_AND_TIME), new Pair(BOOLEAN, new JavaOperator("dateTimeGreaterThan", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry(">", YEARS_AND_MONTHS_DURATION, YEARS_AND_MONTHS_DURATION), new Pair(BOOLEAN, new JavaOperator("durationGreaterThan", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry(">", DAYS_AND_TIME_DURATION, DAYS_AND_TIME_DURATION), new Pair(BOOLEAN, new JavaOperator("durationGreaterThan", 2, true, LEFT_RIGHT, FUNCTIONAL)));

        put(new OperatorTableInputEntry("<=", NUMBER, NUMBER), new Pair(BOOLEAN, new JavaOperator("numericLessEqualThan", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("<=", STRING, STRING), new Pair(BOOLEAN, new JavaOperator("stringLessEqualThan", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("<=", DATE, DATE), new Pair(BOOLEAN, new JavaOperator("dateLessEqualThan", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("<=", TIME, TIME), new Pair(BOOLEAN, new JavaOperator("timeLessEqualThan", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("<=", DATE_AND_TIME, DATE_AND_TIME), new Pair(BOOLEAN, new JavaOperator("dateTimeLessEqualThan", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("<=", YEARS_AND_MONTHS_DURATION, YEARS_AND_MONTHS_DURATION), new Pair(BOOLEAN, new JavaOperator("durationLessEqualThan", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("<=", DAYS_AND_TIME_DURATION, DAYS_AND_TIME_DURATION), new Pair(BOOLEAN, new JavaOperator("durationLessEqualThan", 2, true, LEFT_RIGHT, FUNCTIONAL)));

        put(new OperatorTableInputEntry(">=", NUMBER, NUMBER), new Pair(BOOLEAN, new JavaOperator("numericGreaterEqualThan", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry(">=", STRING, STRING), new Pair(BOOLEAN, new JavaOperator("stringGreaterEqualThan", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry(">=", DATE, DATE), new Pair(BOOLEAN, new JavaOperator("dateGreaterEqualThan", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry(">=", TIME, TIME), new Pair(BOOLEAN, new JavaOperator("timeGreaterEqualThan", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry(">=", DATE_AND_TIME, DATE_AND_TIME), new Pair(BOOLEAN, new JavaOperator("dateTimeGreaterEqualThan", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry(">=", YEARS_AND_MONTHS_DURATION, YEARS_AND_MONTHS_DURATION), new Pair(BOOLEAN, new JavaOperator("durationGreaterEqualThan", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry(">=", DAYS_AND_TIME_DURATION, DAYS_AND_TIME_DURATION), new Pair(BOOLEAN, new JavaOperator("durationGreaterEqualThan", 2, true, LEFT_RIGHT, FUNCTIONAL)));

        // Addition
        put(new OperatorTableInputEntry("+", NUMBER, NUMBER), new Pair(NUMBER, new JavaOperator("numericAdd", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("-", NUMBER, NUMBER), new Pair(NUMBER, new JavaOperator("numericSubtract", 2, false, LEFT_RIGHT, FUNCTIONAL)));

        put(new OperatorTableInputEntry("-", DATE_AND_TIME, DATE_AND_TIME), new Pair(DAYS_AND_TIME_DURATION, new JavaOperator("dateTimeSubtract", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        // not in standard
        put(new OperatorTableInputEntry("-", DATE, DATE), new Pair(YEARS_AND_MONTHS_DURATION, new JavaOperator("dateSubtract", 2, false, LEFT_RIGHT, FUNCTIONAL)));

        put(new OperatorTableInputEntry("-", TIME, TIME), new Pair(DAYS_AND_TIME_DURATION, new JavaOperator("timeSubtract", 2, false, LEFT_RIGHT, FUNCTIONAL)));

        put(new OperatorTableInputEntry("+", YEARS_AND_MONTHS_DURATION, YEARS_AND_MONTHS_DURATION), new Pair(YEARS_AND_MONTHS_DURATION, new JavaOperator("durationAdd", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("-", YEARS_AND_MONTHS_DURATION, YEARS_AND_MONTHS_DURATION), new Pair(YEARS_AND_MONTHS_DURATION, new JavaOperator("durationSubtract", 2, true, LEFT_RIGHT, FUNCTIONAL)));

        put(new OperatorTableInputEntry("+", DAYS_AND_TIME_DURATION, DAYS_AND_TIME_DURATION), new Pair(DAYS_AND_TIME_DURATION, new JavaOperator("durationAdd", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("-", DAYS_AND_TIME_DURATION, DAYS_AND_TIME_DURATION), new Pair(DAYS_AND_TIME_DURATION, new JavaOperator("durationSubtract", 2, true, LEFT_RIGHT, FUNCTIONAL)));

        put(new OperatorTableInputEntry("+", DATE_AND_TIME, YEARS_AND_MONTHS_DURATION), new Pair(DATE_AND_TIME, new JavaOperator("dateTimeAddDuration", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("-", DATE_AND_TIME, YEARS_AND_MONTHS_DURATION), new Pair(DATE_AND_TIME, new JavaOperator("dateTimeSubtractDuration", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        // not in standard
        put(new OperatorTableInputEntry("+", DATE, YEARS_AND_MONTHS_DURATION), new Pair(DATE, new JavaOperator("dateAddDuration", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        // not in standard
        put(new OperatorTableInputEntry("-", DATE, YEARS_AND_MONTHS_DURATION), new Pair(DATE, new JavaOperator("dateSubtractDuration", 2, false, LEFT_RIGHT, FUNCTIONAL)));

        put(new OperatorTableInputEntry("+", YEARS_AND_MONTHS_DURATION, DATE_AND_TIME), new Pair(DATE_AND_TIME, new JavaOperator("dateTimeAddDuration", 2, true, RIGHT_LEFT, FUNCTIONAL)));
        // not in standard
        put(new OperatorTableInputEntry("+", YEARS_AND_MONTHS_DURATION, DATE), new Pair(DATE, new JavaOperator("dateAddDuration", 2, true, RIGHT_LEFT, FUNCTIONAL)));

        put(new OperatorTableInputEntry("+", DATE_AND_TIME, DAYS_AND_TIME_DURATION), new Pair(DATE_AND_TIME, new JavaOperator("dateTimeAddDuration", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("-", DATE_AND_TIME, DAYS_AND_TIME_DURATION), new Pair(DATE_AND_TIME, new JavaOperator("dateTimeSubtractDuration", 2, false, LEFT_RIGHT, FUNCTIONAL)));

        put(new OperatorTableInputEntry("+", DAYS_AND_TIME_DURATION, DATE_AND_TIME), new Pair(DATE_AND_TIME, new JavaOperator("dateTimeAddDuration", 2, true, RIGHT_LEFT, FUNCTIONAL)));

        put(new OperatorTableInputEntry("+", TIME, DAYS_AND_TIME_DURATION), new Pair(TIME, new JavaOperator("timeAddDuration", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("-", TIME, DAYS_AND_TIME_DURATION), new Pair(TIME, new JavaOperator("timeSubtractDuration", 2, false, LEFT_RIGHT, FUNCTIONAL)));

        put(new OperatorTableInputEntry("+", DAYS_AND_TIME_DURATION, TIME), new Pair(TIME, new JavaOperator("timeAddDuration", 2, true, RIGHT_LEFT, FUNCTIONAL)));

        put(new OperatorTableInputEntry("+", STRING, STRING), new Pair(STRING, new JavaOperator("stringAdd", 2, false, LEFT_RIGHT, FUNCTIONAL)));

        // Multiplication
        put(new OperatorTableInputEntry("*", NUMBER, NUMBER), new Pair(NUMBER, new JavaOperator("numericMultiply", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("/", NUMBER, NUMBER), new Pair(NUMBER, new JavaOperator("numericDivide", 2, false, LEFT_RIGHT, FUNCTIONAL)));

        put(new OperatorTableInputEntry("*", YEARS_AND_MONTHS_DURATION, NUMBER), new Pair(YEARS_AND_MONTHS_DURATION, new JavaOperator("durationMultiply", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("/", YEARS_AND_MONTHS_DURATION, NUMBER), new Pair(YEARS_AND_MONTHS_DURATION, new JavaOperator("durationDivide", 2, false, LEFT_RIGHT, FUNCTIONAL)));

        put(new OperatorTableInputEntry("*", NUMBER, YEARS_AND_MONTHS_DURATION), new Pair(YEARS_AND_MONTHS_DURATION, new JavaOperator("durationMultiply", 2, true, RIGHT_LEFT, FUNCTIONAL)));
        // should not be in standard
//        put(new OperatorTableInputEntry("/", NUMBER, YEARS_AND_MONTHS_DURATION), new Pair(YEARS_AND_MONTHS_DURATION, new JavaOperator("durationDivide", 2, false, RIGHT_LEFT, FUNCTIONAL)));

        put(new OperatorTableInputEntry("*", DAYS_AND_TIME_DURATION, NUMBER), new Pair(DAYS_AND_TIME_DURATION, new JavaOperator("durationMultiply", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        put(new OperatorTableInputEntry("/", DAYS_AND_TIME_DURATION, NUMBER), new Pair(DAYS_AND_TIME_DURATION, new JavaOperator("durationDivide", 2, true, LEFT_RIGHT, FUNCTIONAL)));

        put(new OperatorTableInputEntry("*", NUMBER, DAYS_AND_TIME_DURATION), new Pair(DAYS_AND_TIME_DURATION, new JavaOperator("durationMultiply", 2, true, RIGHT_LEFT, FUNCTIONAL)));
        // should not be in standard
//        put(new OperatorTableInputEntry("/", NUMBER, DAYS_AND_TIME_DURATION), new Pair(DAYS_AND_TIME_DURATION, new JavaOperator("durationDivide", 2, false, RIGHT_LEFT, FUNCTIONAL)));

        // Exponentiation
        put(new OperatorTableInputEntry("**", NUMBER, NUMBER), new Pair(NUMBER, new JavaOperator("numericExponentiation", 2, false, LEFT_RIGHT, FUNCTIONAL)));

        // Range
        put(new OperatorTableInputEntry("..", NumberType.NUMBER, NumberType.NUMBER), new Pair(NUMBER_RANGE_TYPE, null));

    }};

    public static JavaOperator javaOperator(String name, Type leftType, Type rightType) {
        OperatorTableInputEntry operatorTableEntry = makeOperatorTableEntry(name, leftType, rightType);
        Pair<Type, JavaOperator> pair = MAPPINGS.get(operatorTableEntry);
        if (pair == null) {
            throw new DMNRuntimeException(String.format("Cannot infer java operator for '%s'", operatorTableEntry));
        }
        return pair.getRight();
    }

    public static Type resultType(String name, Type leftType, Type rightType) {
        OperatorTableInputEntry operatorTableEntry = makeOperatorTableEntry(name, leftType, rightType);
        Pair<Type, JavaOperator> pair = MAPPINGS.get(operatorTableEntry);
        if (pair == null) {
            throw new DMNRuntimeException(String.format("Cannot infer result type for '%s'", operatorTableEntry));
        }
        return pair.getLeft();
    }

    private static String normalizeJavaOperator(String name) {
        if ("==".equals(name)) {
            name = "=";
        }
        return name;
    }

    private static OperatorTableInputEntry makeOperatorTableEntry(String name, Type leftType, Type rightType) {
        name = normalizeJavaOperator(name);
        Pair<Type, Type> pair = normalizeTypes(leftType, rightType);
        return new OperatorTableInputEntry(name, pair.getLeft(), pair.getRight());
    }

    private static Pair<Type, Type> normalizeTypes(Type leftType, Type rightType) {
        if (leftType instanceof ListType) {
            leftType = ListType.ANY_LIST;
        }
        if (rightType instanceof ListType) {
            rightType = ListType.ANY_LIST;
        }
        if (leftType instanceof NullType && rightType != null) {
            leftType = rightType;
        }
        if (rightType instanceof NullType && leftType != null) {
            rightType = leftType;
        }
        return new Pair<>(leftType, rightType);
    }

}
