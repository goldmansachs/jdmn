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

import com.gs.dmn.feel.analysis.semantics.type.*;
import com.gs.dmn.feel.synthesis.JavaOperator;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.gs.dmn.feel.analysis.semantics.type.AnyType.ANY;
import static com.gs.dmn.feel.analysis.semantics.type.BooleanType.BOOLEAN;
import static com.gs.dmn.feel.analysis.semantics.type.DateTimeType.DATE_AND_TIME;
import static com.gs.dmn.feel.analysis.semantics.type.DateType.DATE;
import static com.gs.dmn.feel.analysis.semantics.type.DurationType.DAYS_AND_TIME_DURATION;
import static com.gs.dmn.feel.analysis.semantics.type.DurationType.YEARS_AND_MONTHS_DURATION;
import static com.gs.dmn.feel.analysis.semantics.type.ItemDefinitionType.ANY_ITEM_DEFINITION;
import static com.gs.dmn.feel.analysis.semantics.type.ListType.ANY_LIST;
import static com.gs.dmn.feel.analysis.semantics.type.ContextType.ANY_CONTEXT;
import static com.gs.dmn.feel.analysis.semantics.type.NullType.NULL;
import static com.gs.dmn.feel.analysis.semantics.type.NumberType.NUMBER;
import static com.gs.dmn.feel.analysis.semantics.type.RangeType.*;
import static com.gs.dmn.feel.analysis.semantics.type.StringType.STRING;
import static com.gs.dmn.feel.analysis.semantics.type.TimeType.TIME;
import static com.gs.dmn.feel.synthesis.JavaOperator.Associativity.LEFT_RIGHT;
import static com.gs.dmn.feel.synthesis.JavaOperator.Associativity.RIGHT_LEFT;
import static com.gs.dmn.feel.synthesis.JavaOperator.Notation.FUNCTIONAL;
import static com.gs.dmn.feel.synthesis.JavaOperator.Notation.INFIX;

public class OperatorDecisionTable {
    private static final Map<OperatorTableInputEntry, Pair<Type, JavaOperator>> MAPPINGS = new LinkedHashMap<>();
    static {
        // boolean
        MAPPINGS.put(new OperatorTableInputEntry("or", ANY, ANY), new Pair(BOOLEAN, new JavaOperator("booleanOr", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry("and", ANY, ANY), new Pair(BOOLEAN, new JavaOperator("booleanAnd", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry("not", ANY, null), new Pair(BOOLEAN, new JavaOperator("booleanNot", 2, true, LEFT_RIGHT, FUNCTIONAL)));

        // equality
        MAPPINGS.put(new OperatorTableInputEntry("=", NUMBER, NUMBER), new Pair(BOOLEAN, new JavaOperator("numericEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry("=", BOOLEAN, BOOLEAN), new Pair(BOOLEAN, new JavaOperator("booleanEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry("=", STRING, STRING), new Pair(BOOLEAN, new JavaOperator("stringEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry("=", DATE, DATE), new Pair(BOOLEAN, new JavaOperator("dateEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry("=", TIME, TIME), new Pair(BOOLEAN, new JavaOperator("timeEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry("=", DATE_AND_TIME, DATE_AND_TIME), new Pair(BOOLEAN, new JavaOperator("dateTimeEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry("=", YEARS_AND_MONTHS_DURATION, YEARS_AND_MONTHS_DURATION), new Pair(BOOLEAN, new JavaOperator("durationEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry("=", DAYS_AND_TIME_DURATION, DAYS_AND_TIME_DURATION), new Pair(BOOLEAN, new JavaOperator("durationEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry("=", ANY_LIST, ANY_LIST), new Pair(BOOLEAN, new JavaOperator("listEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry("=", ANY_CONTEXT, ANY_CONTEXT), new Pair(BOOLEAN, new JavaOperator("contextEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry("=", ANY_ITEM_DEFINITION, ANY_ITEM_DEFINITION), new Pair(BOOLEAN, new JavaOperator("contextEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));

        MAPPINGS.put(new OperatorTableInputEntry("=", NULL, NULL), new Pair(BOOLEAN, new JavaOperator("==", 2, true, LEFT_RIGHT, INFIX)));
        MAPPINGS.put(new OperatorTableInputEntry("=", ANY, ANY), new Pair(BOOLEAN, new JavaOperator("==", 2, true, LEFT_RIGHT, INFIX)));

        MAPPINGS.put(new OperatorTableInputEntry("!=", NUMBER, NUMBER), new Pair(BOOLEAN, new JavaOperator("numericNotEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry("!=", BOOLEAN, BOOLEAN), new Pair(BOOLEAN, new JavaOperator("booleanNotEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry("!=", STRING, STRING), new Pair(BOOLEAN, new JavaOperator("stringNotEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry("!=", DATE, DATE), new Pair(BOOLEAN, new JavaOperator("dateNotEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry("!=", TIME, TIME), new Pair(BOOLEAN, new JavaOperator("timeNotEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry("!=", DATE_AND_TIME, DATE_AND_TIME), new Pair(BOOLEAN, new JavaOperator("dateTimeNotEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry("!=", YEARS_AND_MONTHS_DURATION, YEARS_AND_MONTHS_DURATION), new Pair(BOOLEAN, new JavaOperator("durationNotEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry("!=", DAYS_AND_TIME_DURATION, DAYS_AND_TIME_DURATION), new Pair(BOOLEAN, new JavaOperator("durationNotEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry("!=", ANY_LIST, ANY_LIST), new Pair(BOOLEAN, new JavaOperator("listNotEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry("!=", ANY_CONTEXT, ANY_CONTEXT), new Pair(BOOLEAN, new JavaOperator("contextNotEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry("!=", ANY_ITEM_DEFINITION, ANY_ITEM_DEFINITION), new Pair(BOOLEAN, new JavaOperator("contextNotEqual", 2, true, LEFT_RIGHT, FUNCTIONAL)));

        MAPPINGS.put(new OperatorTableInputEntry("!=", NULL, NULL), new Pair(BOOLEAN, new JavaOperator("!=", 2, true, LEFT_RIGHT, INFIX)));
        MAPPINGS.put(new OperatorTableInputEntry("!=", ANY, ANY), new Pair(BOOLEAN, new JavaOperator("!=", 2, true, LEFT_RIGHT, INFIX)));

        // Relational
        MAPPINGS.put(new OperatorTableInputEntry("<", NUMBER, NUMBER), new Pair(BOOLEAN, new JavaOperator("numericLessThan", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry("<", STRING, STRING), new Pair(BOOLEAN, new JavaOperator("stringLessThan", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry("<", DATE, DATE), new Pair(BOOLEAN, new JavaOperator("dateLessThan", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry("<", TIME, TIME), new Pair(BOOLEAN, new JavaOperator("timeLessThan", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry("<", DATE_AND_TIME, DATE_AND_TIME), new Pair(BOOLEAN, new JavaOperator("dateTimeLessThan", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry("<", YEARS_AND_MONTHS_DURATION, YEARS_AND_MONTHS_DURATION), new Pair(BOOLEAN, new JavaOperator("durationLessThan", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry("<", DAYS_AND_TIME_DURATION, DAYS_AND_TIME_DURATION), new Pair(BOOLEAN, new JavaOperator("durationLessThan", 2, true, LEFT_RIGHT, FUNCTIONAL)));

        MAPPINGS.put(new OperatorTableInputEntry(">", NUMBER, NUMBER), new Pair(BOOLEAN, new JavaOperator("numericGreaterThan", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry(">", STRING, STRING), new Pair(BOOLEAN, new JavaOperator("stringGreaterThan", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry(">", DATE, DATE), new Pair(BOOLEAN, new JavaOperator("dateGreaterThan", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry(">", TIME, TIME), new Pair(BOOLEAN, new JavaOperator("timeGreaterThan", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry(">", DATE_AND_TIME, DATE_AND_TIME), new Pair(BOOLEAN, new JavaOperator("dateTimeGreaterThan", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry(">", YEARS_AND_MONTHS_DURATION, YEARS_AND_MONTHS_DURATION), new Pair(BOOLEAN, new JavaOperator("durationGreaterThan", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry(">", DAYS_AND_TIME_DURATION, DAYS_AND_TIME_DURATION), new Pair(BOOLEAN, new JavaOperator("durationGreaterThan", 2, true, LEFT_RIGHT, FUNCTIONAL)));

        MAPPINGS.put(new OperatorTableInputEntry("<=", NUMBER, NUMBER), new Pair(BOOLEAN, new JavaOperator("numericLessEqualThan", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry("<=", STRING, STRING), new Pair(BOOLEAN, new JavaOperator("stringLessEqualThan", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry("<=", DATE, DATE), new Pair(BOOLEAN, new JavaOperator("dateLessEqualThan", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry("<=", TIME, TIME), new Pair(BOOLEAN, new JavaOperator("timeLessEqualThan", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry("<=", DATE_AND_TIME, DATE_AND_TIME), new Pair(BOOLEAN, new JavaOperator("dateTimeLessEqualThan", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry("<=", YEARS_AND_MONTHS_DURATION, YEARS_AND_MONTHS_DURATION), new Pair(BOOLEAN, new JavaOperator("durationLessEqualThan", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry("<=", DAYS_AND_TIME_DURATION, DAYS_AND_TIME_DURATION), new Pair(BOOLEAN, new JavaOperator("durationLessEqualThan", 2, true, LEFT_RIGHT, FUNCTIONAL)));

        MAPPINGS.put(new OperatorTableInputEntry(">=", NUMBER, NUMBER), new Pair(BOOLEAN, new JavaOperator("numericGreaterEqualThan", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry(">=", STRING, STRING), new Pair(BOOLEAN, new JavaOperator("stringGreaterEqualThan", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry(">=", DATE, DATE), new Pair(BOOLEAN, new JavaOperator("dateGreaterEqualThan", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry(">=", TIME, TIME), new Pair(BOOLEAN, new JavaOperator("timeGreaterEqualThan", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry(">=", DATE_AND_TIME, DATE_AND_TIME), new Pair(BOOLEAN, new JavaOperator("dateTimeGreaterEqualThan", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry(">=", YEARS_AND_MONTHS_DURATION, YEARS_AND_MONTHS_DURATION), new Pair(BOOLEAN, new JavaOperator("durationGreaterEqualThan", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry(">=", DAYS_AND_TIME_DURATION, DAYS_AND_TIME_DURATION), new Pair(BOOLEAN, new JavaOperator("durationGreaterEqualThan", 2, true, LEFT_RIGHT, FUNCTIONAL)));

        // Addition
        MAPPINGS.put(new OperatorTableInputEntry("+", NUMBER, NUMBER), new Pair(NUMBER, new JavaOperator("numericAdd", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry("-", NUMBER, NUMBER), new Pair(NUMBER, new JavaOperator("numericSubtract", 2, false, LEFT_RIGHT, FUNCTIONAL)));

        MAPPINGS.put(new OperatorTableInputEntry("-", DATE_AND_TIME, DATE_AND_TIME), new Pair(DAYS_AND_TIME_DURATION, new JavaOperator("dateTimeSubtract", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        // not in standard
        MAPPINGS.put(new OperatorTableInputEntry("-", DATE, DATE), new Pair(YEARS_AND_MONTHS_DURATION, new JavaOperator("dateSubtract", 2, false, LEFT_RIGHT, FUNCTIONAL)));

        MAPPINGS.put(new OperatorTableInputEntry("-", TIME, TIME), new Pair(DAYS_AND_TIME_DURATION, new JavaOperator("timeSubtract", 2, false, LEFT_RIGHT, FUNCTIONAL)));

        MAPPINGS.put(new OperatorTableInputEntry("+", YEARS_AND_MONTHS_DURATION, YEARS_AND_MONTHS_DURATION), new Pair(YEARS_AND_MONTHS_DURATION, new JavaOperator("durationAdd", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry("-", YEARS_AND_MONTHS_DURATION, YEARS_AND_MONTHS_DURATION), new Pair(YEARS_AND_MONTHS_DURATION, new JavaOperator("durationSubtract", 2, true, LEFT_RIGHT, FUNCTIONAL)));

        MAPPINGS.put(new OperatorTableInputEntry("+", DAYS_AND_TIME_DURATION, DAYS_AND_TIME_DURATION), new Pair(DAYS_AND_TIME_DURATION, new JavaOperator("durationAdd", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry("-", DAYS_AND_TIME_DURATION, DAYS_AND_TIME_DURATION), new Pair(DAYS_AND_TIME_DURATION, new JavaOperator("durationSubtract", 2, true, LEFT_RIGHT, FUNCTIONAL)));

        MAPPINGS.put(new OperatorTableInputEntry("+", DATE_AND_TIME, YEARS_AND_MONTHS_DURATION), new Pair(DATE_AND_TIME, new JavaOperator("dateTimeAddDuration", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry("-", DATE_AND_TIME, YEARS_AND_MONTHS_DURATION), new Pair(DATE_AND_TIME, new JavaOperator("dateTimeSubtractDuration", 2, false, LEFT_RIGHT, FUNCTIONAL)));
        // not in standard
        MAPPINGS.put(new OperatorTableInputEntry("+", DATE, YEARS_AND_MONTHS_DURATION), new Pair(DATE, new JavaOperator("dateAddDuration", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        // not in standard
        MAPPINGS.put(new OperatorTableInputEntry("-", DATE, YEARS_AND_MONTHS_DURATION), new Pair(DATE, new JavaOperator("dateSubtractDuration", 2, false, LEFT_RIGHT, FUNCTIONAL)));

        MAPPINGS.put(new OperatorTableInputEntry("+", YEARS_AND_MONTHS_DURATION, DATE_AND_TIME), new Pair(DATE_AND_TIME, new JavaOperator("dateTimeAddDuration", 2, true, RIGHT_LEFT, FUNCTIONAL)));
        // not in standard
        MAPPINGS.put(new OperatorTableInputEntry("+", YEARS_AND_MONTHS_DURATION, DATE), new Pair(DATE, new JavaOperator("dateAddDuration", 2, true, RIGHT_LEFT, FUNCTIONAL)));

        MAPPINGS.put(new OperatorTableInputEntry("+", DATE_AND_TIME, DAYS_AND_TIME_DURATION), new Pair(DATE_AND_TIME, new JavaOperator("dateTimeAddDuration", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry("-", DATE_AND_TIME, DAYS_AND_TIME_DURATION), new Pair(DATE_AND_TIME, new JavaOperator("dateTimeSubtractDuration", 2, false, LEFT_RIGHT, FUNCTIONAL)));

        MAPPINGS.put(new OperatorTableInputEntry("+", DAYS_AND_TIME_DURATION, DATE_AND_TIME), new Pair(DATE_AND_TIME, new JavaOperator("dateTimeAddDuration", 2, true, RIGHT_LEFT, FUNCTIONAL)));

        MAPPINGS.put(new OperatorTableInputEntry("+", TIME, DAYS_AND_TIME_DURATION), new Pair(TIME, new JavaOperator("timeAddDuration", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry("-", TIME, DAYS_AND_TIME_DURATION), new Pair(TIME, new JavaOperator("timeSubtractDuration", 2, false, LEFT_RIGHT, FUNCTIONAL)));

        MAPPINGS.put(new OperatorTableInputEntry("+", DAYS_AND_TIME_DURATION, TIME), new Pair(TIME, new JavaOperator("timeAddDuration", 2, true, RIGHT_LEFT, FUNCTIONAL)));

        MAPPINGS.put(new OperatorTableInputEntry("+", STRING, STRING), new Pair(STRING, new JavaOperator("stringAdd", 2, false, LEFT_RIGHT, FUNCTIONAL)));

        // Multiplication
        MAPPINGS.put(new OperatorTableInputEntry("*", NUMBER, NUMBER), new Pair(NUMBER, new JavaOperator("numericMultiply", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry("/", NUMBER, NUMBER), new Pair(NUMBER, new JavaOperator("numericDivide", 2, false, LEFT_RIGHT, FUNCTIONAL)));

        MAPPINGS.put(new OperatorTableInputEntry("*", YEARS_AND_MONTHS_DURATION, NUMBER), new Pair(YEARS_AND_MONTHS_DURATION, new JavaOperator("durationMultiply", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry("/", YEARS_AND_MONTHS_DURATION, NUMBER), new Pair(YEARS_AND_MONTHS_DURATION, new JavaOperator("durationDivide", 2, false, LEFT_RIGHT, FUNCTIONAL)));

        MAPPINGS.put(new OperatorTableInputEntry("*", NUMBER, YEARS_AND_MONTHS_DURATION), new Pair(YEARS_AND_MONTHS_DURATION, new JavaOperator("durationMultiply", 2, true, RIGHT_LEFT, FUNCTIONAL)));
        // should not be in standard
//        MAPPINGS.put(new OperatorTableInputEntry("/", NUMBER, YEARS_AND_MONTHS_DURATION), new Pair(YEARS_AND_MONTHS_DURATION, new JavaOperator("durationDivide", 2, false, RIGHT_LEFT, FUNCTIONAL)));

        MAPPINGS.put(new OperatorTableInputEntry("*", DAYS_AND_TIME_DURATION, NUMBER), new Pair(DAYS_AND_TIME_DURATION, new JavaOperator("durationMultiply", 2, true, LEFT_RIGHT, FUNCTIONAL)));
        MAPPINGS.put(new OperatorTableInputEntry("/", DAYS_AND_TIME_DURATION, NUMBER), new Pair(DAYS_AND_TIME_DURATION, new JavaOperator("durationDivide", 2, true, LEFT_RIGHT, FUNCTIONAL)));

        MAPPINGS.put(new OperatorTableInputEntry("*", NUMBER, DAYS_AND_TIME_DURATION), new Pair(DAYS_AND_TIME_DURATION, new JavaOperator("durationMultiply", 2, true, RIGHT_LEFT, FUNCTIONAL)));
        // should not be in standard
//        MAPPINGS.put(new OperatorTableInputEntry("/", NUMBER, DAYS_AND_TIME_DURATION), new Pair(DAYS_AND_TIME_DURATION, new JavaOperator("durationDivide", 2, false, RIGHT_LEFT, FUNCTIONAL)));

        // Exponentiation
        MAPPINGS.put(new OperatorTableInputEntry("**", NUMBER, NUMBER), new Pair(NUMBER, new JavaOperator("numericExponentiation", 2, false, LEFT_RIGHT, FUNCTIONAL)));

        // Range
        MAPPINGS.put(new OperatorTableInputEntry("..", NUMBER, NUMBER), new Pair(NUMBER_RANGE_TYPE, null));
        MAPPINGS.put(new OperatorTableInputEntry("..", STRING, STRING), new Pair(STRING_RANGE_TYPE, null));
        MAPPINGS.put(new OperatorTableInputEntry("..", DATE, DATE), new Pair(DATE_RANGE_TYPE, null));
        MAPPINGS.put(new OperatorTableInputEntry("..", TIME, TIME), new Pair(TIME_RANGE_TYPE, null));
        MAPPINGS.put(new OperatorTableInputEntry("..", DATE_AND_TIME, DATE_AND_TIME), new Pair(DATE_AND_TIME_RANGE_TYPE, null));
        MAPPINGS.put(new OperatorTableInputEntry("..", YEARS_AND_MONTHS_DURATION, YEARS_AND_MONTHS_DURATION), new Pair(YEARS_AND_MONTHS_DURATION_RANGE_TYPE, null));
        MAPPINGS.put(new OperatorTableInputEntry("..", DAYS_AND_TIME_DURATION, DAYS_AND_TIME_DURATION), new Pair(DAYS_AND_TIME_DURATION_RANGE_TYPE, null));
    };

    public static JavaOperator javaOperator(String name, Type leftType, Type rightType) {
        OperatorTableInputEntry entry = resolveOperator(name, leftType, rightType);
        if (entry == null) {
            throw new DMNRuntimeException(String.format("Cannot infer java operator for '(%s, %s, %s)'", name, leftType, rightType));
        } else {
            return MAPPINGS.get(entry).getRight();
        }
    }

    public static Type resultType(String name, Type leftType, Type rightType) {
        OperatorTableInputEntry entry = resolveOperator(name, leftType, rightType);
        if (entry == null) {
            throw new DMNRuntimeException(String.format("Cannot infer result type for '(%s, %s, %s)'", name, leftType, rightType));
        } else {
            Pair<Type, JavaOperator> pair = MAPPINGS.get(entry);
            return pair.getLeft();
        }
    }

    private static OperatorTableInputEntry resolveOperator(String name, Type leftType, Type rightType) {
        // Normalize operator and operands
        String operator = normalizeJavaOperator(name);
        Pair<Type, Type> pair = normalizeTypes(leftType, rightType);

        // Check if operator can be applied
        if (!validOperator(name, pair.getLeft(), pair.getRight())) {
            throw new DMNRuntimeException(String.format("Operator '%s' cannot be applied to '%s', '%s'", name, leftType, rightType));
        }

        // Resolve operator
        OperatorTableInputEntry operatorTableEntry = new OperatorTableInputEntry(operator, pair.getLeft(), pair.getRight());
        OperatorTableInputEntry exactMatch = null;
        List<OperatorTableInputEntry> candidates = new ArrayList<>();
        for (OperatorTableInputEntry key: MAPPINGS.keySet()) {
            if (operatorTableEntry.equivalentTo(key)) {
                exactMatch = key;
                break;
            } else if (operatorTableEntry.conformsTo(key)) {
                candidates.add(key);
            }
        }
        if (exactMatch != null) {
            return exactMatch;
        } else if (candidates.size() == 1) {
            return candidates.get(0);
        }
        return null;
    }

    private static String normalizeJavaOperator(String name) {
        if ("==".equals(name)) {
            name = "=";
        }
        return name;
    }

    private static Pair<Type, Type> normalizeTypes(Type leftType, Type rightType) {
        // Normalize lists & contexts
        if (leftType instanceof ListType) {
            leftType = ANY_LIST;
        }
        if (rightType instanceof ListType) {
            rightType = ANY_LIST;
        }
        if (leftType instanceof ContextType) {
            leftType = ANY_CONTEXT;
        }
        if (rightType instanceof ContextType) {
            rightType = ANY_CONTEXT;
        }
        if (leftType instanceof ItemDefinitionType) {
            leftType = ANY_ITEM_DEFINITION;
        }
        if (rightType instanceof ItemDefinitionType) {
            rightType = ANY_ITEM_DEFINITION;
        }

        // Normalize data types
        if (leftType instanceof DataType && (rightType == NULL || rightType == ANY)) {
            rightType = leftType;
        } else if (rightType instanceof DataType && (leftType == NULL || leftType == ANY)) {
            leftType = rightType;
        } else if (leftType instanceof ListType && (rightType == NULL || rightType == ANY)) {
            rightType = leftType;
        } else if (rightType instanceof ListType && (leftType == NULL || leftType == ANY)) {
            leftType = rightType;
        } else if (leftType instanceof ContextType && (rightType == NULL || rightType == ANY)) {
            rightType = leftType;
        } else if (rightType instanceof ContextType && (leftType == NULL || leftType == ANY)) {
            leftType = rightType;
        } else if (leftType instanceof ItemDefinitionType && (rightType == NULL || rightType == ANY)) {
            rightType = leftType;
        } else if (rightType instanceof ItemDefinitionType && (leftType == NULL || leftType == ANY)) {
            leftType = rightType;
        }
        return new Pair<>(leftType, rightType);
    }

    private static boolean validOperator(String operator, Type leftType, Type rightType) {
        if (operator.equals("=") || operator.equals("!=")) {
            if (leftType instanceof DataType && rightType instanceof DataType && leftType != rightType) {
                return false;
            }
            if (leftType instanceof ListType && ! (rightType instanceof ListType)) {
                return false;
            }
            if (leftType instanceof ContextType && ! (rightType instanceof ContextType)) {
                return false;
            }
        }
        return true;
    }

}
