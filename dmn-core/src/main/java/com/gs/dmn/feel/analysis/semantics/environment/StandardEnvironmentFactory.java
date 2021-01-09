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
package com.gs.dmn.feel.analysis.semantics.environment;

import com.gs.dmn.feel.analysis.semantics.type.BuiltinFunctionType;
import com.gs.dmn.feel.analysis.semantics.type.ContextType;

import static com.gs.dmn.feel.analysis.semantics.type.AnyType.ANY;
import static com.gs.dmn.feel.analysis.semantics.type.BooleanType.BOOLEAN;
import static com.gs.dmn.feel.analysis.semantics.type.ComparableDataType.COMPARABLE;
import static com.gs.dmn.feel.analysis.semantics.type.DateTimeType.DATE_AND_TIME;
import static com.gs.dmn.feel.analysis.semantics.type.DateType.DATE;
import static com.gs.dmn.feel.analysis.semantics.type.DurationType.DAYS_AND_TIME_DURATION;
import static com.gs.dmn.feel.analysis.semantics.type.DurationType.YEARS_AND_MONTHS_DURATION;
import static com.gs.dmn.feel.analysis.semantics.type.ListType.*;
import static com.gs.dmn.feel.analysis.semantics.type.NumberType.NUMBER;
import static com.gs.dmn.feel.analysis.semantics.type.RangeType.COMPARABLE_RANGE_TYPE;
import static com.gs.dmn.feel.analysis.semantics.type.StringType.STRING;
import static com.gs.dmn.feel.analysis.semantics.type.TimeType.TIME;

public class StandardEnvironmentFactory implements EnvironmentFactory {
    private static final EnvironmentFactory INSTANCE = new StandardEnvironmentFactory();
    private static final Environment BUILT_IN_ENVIRONMENT;

    static {
        BUILT_IN_ENVIRONMENT = INSTANCE.emptyEnvironment();
        addFEELFunctions(BUILT_IN_ENVIRONMENT);
    }

    public static EnvironmentFactory instance() {
        return INSTANCE;
    }

    private StandardEnvironmentFactory() {
    }

    @Override
    public Environment getBuiltInEnvironment() {
        return StandardEnvironmentFactory.BUILT_IN_ENVIRONMENT;
    }

    private static void addFEELFunctions(Environment environment) {
        addConversionFunctions(environment);
        addNumberFunctions(environment);
        addBooleanFunctions(environment);
        addStringFunctions(environment);
        addListFunctions(environment);
        addContextFunctions(environment);
        addDateTimeFunctions(environment);
        addTemporalFunctions(environment);
        addRangeFunctions(environment);
    }

    private static void addConversionFunctions(Environment environment) {
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("date", new BuiltinFunctionType(DATE, new Parameter("from", STRING))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("date", new BuiltinFunctionType(DATE, new Parameter("from", DATE))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("date", new BuiltinFunctionType(DATE, new Parameter("from", DATE_AND_TIME))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("date", new BuiltinFunctionType(DATE, new Parameter("year", NUMBER), new Parameter("month", NUMBER), new Parameter("day", NUMBER))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("date and time", new BuiltinFunctionType(DATE_AND_TIME, new Parameter("date", DATE), new Parameter("time", TIME))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("date and time", new BuiltinFunctionType(DATE_AND_TIME, new Parameter("date", DATE_AND_TIME), new Parameter("time", TIME))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("date and time", new BuiltinFunctionType(DATE_AND_TIME, new Parameter("from", STRING))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("time", new BuiltinFunctionType(TIME, new Parameter("from", STRING))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("time", new BuiltinFunctionType(TIME, new Parameter("from", DATE))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("time", new BuiltinFunctionType(TIME, new Parameter("from", DATE_AND_TIME))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("time", new BuiltinFunctionType(TIME, new Parameter("hour", NUMBER), new Parameter("minute", NUMBER), new Parameter("second", NUMBER), new Parameter("offset", DAYS_AND_TIME_DURATION, true, false))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("number", new BuiltinFunctionType(NUMBER, new Parameter("from", STRING), new Parameter("'grouping separator'", STRING), new Parameter("'decimal separator'", STRING))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("number", new BuiltinFunctionType(NUMBER, new Parameter("from", STRING), new Parameter("groupingSeparator", STRING), new Parameter("decimalSeparator", STRING))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("string", new BuiltinFunctionType(STRING, new Parameter("from", ANY))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("duration", new BuiltinFunctionType(ANY, new Parameter("from", STRING))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("years and months duration", new BuiltinFunctionType(YEARS_AND_MONTHS_DURATION, new Parameter("from", DATE), new Parameter("to", DATE))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("years and months duration", new BuiltinFunctionType(YEARS_AND_MONTHS_DURATION, new Parameter("from", DATE_AND_TIME), new Parameter("to", DATE_AND_TIME))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("years and months duration", new BuiltinFunctionType(YEARS_AND_MONTHS_DURATION, new Parameter("from", DATE), new Parameter("to", DATE_AND_TIME))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("years and months duration", new BuiltinFunctionType(YEARS_AND_MONTHS_DURATION, new Parameter("from", DATE_AND_TIME), new Parameter("to", DATE))));
    }

    private static void addNumberFunctions(Environment environment) {
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("decimal", new BuiltinFunctionType(NUMBER, new Parameter("n", NUMBER), new Parameter("scale", NUMBER))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("floor", new BuiltinFunctionType(NUMBER, new Parameter("n", NUMBER))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("ceiling", new BuiltinFunctionType(NUMBER, new Parameter("n", NUMBER))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("abs", new BuiltinFunctionType(NUMBER, new Parameter("n", NUMBER))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("abs", new BuiltinFunctionType(YEARS_AND_MONTHS_DURATION, new Parameter("n", YEARS_AND_MONTHS_DURATION))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("abs", new BuiltinFunctionType(DAYS_AND_TIME_DURATION, new Parameter("n", DAYS_AND_TIME_DURATION))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("modulo", new BuiltinFunctionType(NUMBER, new Parameter("dividend", NUMBER), new Parameter("divisor", NUMBER))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("sqrt", new BuiltinFunctionType(NUMBER, new Parameter("number", NUMBER))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("log", new BuiltinFunctionType(NUMBER, new Parameter("number", NUMBER))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("exp", new BuiltinFunctionType(NUMBER, new Parameter("number", NUMBER))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("odd", new BuiltinFunctionType(BOOLEAN, new Parameter("number", NUMBER))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("even", new BuiltinFunctionType(BOOLEAN, new Parameter("number", NUMBER))));
    }

    private static void addBooleanFunctions(Environment environment) {
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("not", new BuiltinFunctionType(BOOLEAN, new Parameter("negand", BOOLEAN))));
    }

    private static void addStringFunctions(Environment environment) {
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("substring", new BuiltinFunctionType(STRING, new Parameter("string", STRING), new Parameter("'start position'", NUMBER), new Parameter("length", NUMBER, true, false))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("substring", new BuiltinFunctionType(STRING, new Parameter("string", STRING), new Parameter("startPosition", NUMBER), new Parameter("length", NUMBER, true, false))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("string length", new BuiltinFunctionType(NUMBER, new Parameter("string", STRING))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("upper case", new BuiltinFunctionType(STRING, new Parameter("string", STRING))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("lower case", new BuiltinFunctionType(STRING, new Parameter("string", STRING))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("substring before", new BuiltinFunctionType(STRING, new Parameter("string", STRING), new Parameter("match", STRING))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("substring after", new BuiltinFunctionType(STRING, new Parameter("string", STRING), new Parameter("match", STRING))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("replace", new BuiltinFunctionType(STRING, new Parameter("input", STRING), new Parameter("pattern", STRING), new Parameter("replacement", STRING), new Parameter("flags", STRING, true, false))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("contains", new BuiltinFunctionType(BOOLEAN, new Parameter("string", STRING), new Parameter("match", STRING))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("starts with", new BuiltinFunctionType(BOOLEAN, new Parameter("string", STRING), new Parameter("match", STRING))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("ends with", new BuiltinFunctionType(BOOLEAN, new Parameter("string", STRING), new Parameter("match", STRING))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("matches", new BuiltinFunctionType(BOOLEAN, new Parameter("input", STRING), new Parameter("pattern", STRING), new Parameter("flags", STRING, true, false))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("split", new BuiltinFunctionType(STRING_LIST, new Parameter("string", STRING), new Parameter("delimiter", STRING))));
    }

    private static void addListFunctions(Environment environment) {
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("list contains", new BuiltinFunctionType(BOOLEAN, new Parameter("list", ANY_LIST), new Parameter("element", ANY))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("count", new BuiltinFunctionType(NUMBER, new Parameter("list", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("min", new BuiltinFunctionType(NUMBER, new Parameter("list", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("min", new BuiltinFunctionType(NUMBER, new Parameter("c1", ANY), new Parameter("cs", ANY, false, true))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("max", new BuiltinFunctionType(NUMBER, new Parameter("list", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("max", new BuiltinFunctionType(NUMBER, new Parameter("c1", ANY), new Parameter("cs", ANY, false, true))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("sum", new BuiltinFunctionType(NUMBER, new Parameter("list", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("sum", new BuiltinFunctionType(NUMBER, new Parameter("n1", ANY), new Parameter("ns", ANY, false, true))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("mean", new BuiltinFunctionType(NUMBER, new Parameter("list", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("mean", new BuiltinFunctionType(NUMBER, new Parameter("n1", ANY), new Parameter("ns", ANY, false, true))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("and", new BuiltinFunctionType(BOOLEAN, new Parameter("list", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("and", new BuiltinFunctionType(NUMBER, new Parameter("b1", ANY), new Parameter("bs", ANY, false, true))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("all", new BuiltinFunctionType(BOOLEAN, new Parameter("list", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("all", new BuiltinFunctionType(NUMBER, new Parameter("b1", ANY), new Parameter("bs", ANY, false, true))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("or", new BuiltinFunctionType(BOOLEAN, new Parameter("list", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("or", new BuiltinFunctionType(NUMBER, new Parameter("b1", ANY), new Parameter("bs", ANY, false, true))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("any", new BuiltinFunctionType(BOOLEAN, new Parameter("list", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("any", new BuiltinFunctionType(NUMBER, new Parameter("b1", ANY), new Parameter("bs", ANY, false, true))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("sublist", new BuiltinFunctionType(ANY_LIST, new Parameter("list", ANY_LIST), new Parameter("start position", NUMBER), new Parameter("length", NUMBER, true, false))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("append", new BuiltinFunctionType(ANY_LIST, new Parameter("list", ANY_LIST), new Parameter("item", ANY))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("append", new BuiltinFunctionType(ANY_LIST, new Parameter("list", ANY_LIST), new Parameter("item", ANY, false, true))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("concatenate", new BuiltinFunctionType(ANY_LIST, new Parameter("list1", ANY_LIST, false, true))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("insert before", new BuiltinFunctionType(ANY_LIST, new Parameter("list", ANY_LIST), new Parameter("position", NUMBER), new Parameter("new item", ANY))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("remove", new BuiltinFunctionType(ANY_LIST, new Parameter("list", ANY_LIST), new Parameter("position", NUMBER))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("reverse", new BuiltinFunctionType(ANY_LIST, new Parameter("list", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("index of", new BuiltinFunctionType(ANY_LIST, new Parameter("list", ANY_LIST), new Parameter("match", ANY))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("distinct values", new BuiltinFunctionType(ANY_LIST, new Parameter("list", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("union", new BuiltinFunctionType(ANY_LIST, new Parameter("list1", ANY_LIST), new Parameter("list2", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("flatten", new BuiltinFunctionType(ANY_LIST, new Parameter("list", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("product", new BuiltinFunctionType(NUMBER, new Parameter("list", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("product", new BuiltinFunctionType(NUMBER, new Parameter("n1", ANY), new Parameter("ns", ANY, false, true))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("median", new BuiltinFunctionType(NUMBER, new Parameter("list", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("median", new BuiltinFunctionType(NUMBER, new Parameter("n1", ANY), new Parameter("ns", ANY, false, true))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("stddev", new BuiltinFunctionType(NUMBER, new Parameter("list", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("stddev", new BuiltinFunctionType(NUMBER, new Parameter("n1", ANY), new Parameter("ns", ANY, false, true))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("mode", new BuiltinFunctionType(NUMBER, new Parameter("list", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("mode", new BuiltinFunctionType(NUMBER, new Parameter("n1", ANY), new Parameter("ns", ANY, false, true))));

        environment.addDeclaration(INSTANCE.makeVariableDeclaration("sort", new BuiltinFunctionType(ANY_LIST, new Parameter("list", ANY_LIST), new Parameter("function", ANY))));
    }

    private static void addContextFunctions(Environment environment) {
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("get entries", new BuiltinFunctionType(CONTEXT_LIST, new Parameter("m", ContextType.ANY_CONTEXT))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("get value", new BuiltinFunctionType(ANY, new Parameter("m", ContextType.ANY_CONTEXT), new Parameter("key", STRING))));
    }

    private static void addDateTimeFunctions(Environment environment) {
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("is", new BuiltinFunctionType(BOOLEAN, new Parameter("value1", ANY), new Parameter("value2", ANY))));
    }

    private static void addTemporalFunctions(Environment environment) {
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("day of year", new BuiltinFunctionType(NUMBER, new Parameter("date", DATE))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("day of year", new BuiltinFunctionType(NUMBER, new Parameter("date", DATE_AND_TIME))));

        environment.addDeclaration(INSTANCE.makeVariableDeclaration("day of week", new BuiltinFunctionType(STRING, new Parameter("date", DATE))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("day of week", new BuiltinFunctionType(STRING, new Parameter("date", DATE_AND_TIME))));

        environment.addDeclaration(INSTANCE.makeVariableDeclaration("month of year", new BuiltinFunctionType(STRING, new Parameter("date", DATE))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("month of year", new BuiltinFunctionType(STRING, new Parameter("date", DATE_AND_TIME))));

        environment.addDeclaration(INSTANCE.makeVariableDeclaration("week of year", new BuiltinFunctionType(NUMBER, new Parameter("date", DATE))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("week of year", new BuiltinFunctionType(NUMBER, new Parameter("date", DATE_AND_TIME))));
    }

    private static void addRangeFunctions(Environment environment) {
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("before", new BuiltinFunctionType(BOOLEAN, new Parameter("point1", COMPARABLE), new Parameter("point2", COMPARABLE))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("before", new BuiltinFunctionType(BOOLEAN, new Parameter("point", COMPARABLE), new Parameter("range", COMPARABLE_RANGE_TYPE))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("before", new BuiltinFunctionType(BOOLEAN, new Parameter("range", COMPARABLE_RANGE_TYPE), new Parameter("point", COMPARABLE))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("before", new BuiltinFunctionType(BOOLEAN, new Parameter("range1", COMPARABLE_RANGE_TYPE), new Parameter("range2", COMPARABLE_RANGE_TYPE))));

        environment.addDeclaration(INSTANCE.makeVariableDeclaration("after", new BuiltinFunctionType(BOOLEAN, new Parameter("point1", COMPARABLE), new Parameter("point2", COMPARABLE))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("after", new BuiltinFunctionType(BOOLEAN, new Parameter("point", COMPARABLE), new Parameter("range", COMPARABLE_RANGE_TYPE))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("after", new BuiltinFunctionType(BOOLEAN, new Parameter("range", COMPARABLE_RANGE_TYPE), new Parameter("point", COMPARABLE))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("after", new BuiltinFunctionType(BOOLEAN, new Parameter("range1", COMPARABLE_RANGE_TYPE), new Parameter("range2", COMPARABLE_RANGE_TYPE))));

        environment.addDeclaration(INSTANCE.makeVariableDeclaration("meets", new BuiltinFunctionType(BOOLEAN, new Parameter("range1", COMPARABLE_RANGE_TYPE), new Parameter("range2", COMPARABLE_RANGE_TYPE))));

        environment.addDeclaration(INSTANCE.makeVariableDeclaration("met by", new BuiltinFunctionType(BOOLEAN, new Parameter("range1", COMPARABLE_RANGE_TYPE), new Parameter("range2", COMPARABLE_RANGE_TYPE))));

        environment.addDeclaration(INSTANCE.makeVariableDeclaration("overlaps", new BuiltinFunctionType(BOOLEAN, new Parameter("range1", COMPARABLE_RANGE_TYPE), new Parameter("range2", COMPARABLE_RANGE_TYPE))));

        environment.addDeclaration(INSTANCE.makeVariableDeclaration("overlaps before", new BuiltinFunctionType(BOOLEAN, new Parameter("range1", COMPARABLE_RANGE_TYPE), new Parameter("range2", COMPARABLE_RANGE_TYPE))));

        environment.addDeclaration(INSTANCE.makeVariableDeclaration("overlaps after", new BuiltinFunctionType(BOOLEAN, new Parameter("range1", COMPARABLE_RANGE_TYPE), new Parameter("range2", COMPARABLE_RANGE_TYPE))));

        environment.addDeclaration(INSTANCE.makeVariableDeclaration("finishes", new BuiltinFunctionType(BOOLEAN, new Parameter("point", COMPARABLE), new Parameter("range", COMPARABLE_RANGE_TYPE))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("finishes", new BuiltinFunctionType(BOOLEAN, new Parameter("range1", COMPARABLE_RANGE_TYPE), new Parameter("range2", COMPARABLE_RANGE_TYPE))));

        environment.addDeclaration(INSTANCE.makeVariableDeclaration("finished by", new BuiltinFunctionType(BOOLEAN, new Parameter("range", COMPARABLE_RANGE_TYPE), new Parameter("point", COMPARABLE))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("finished by", new BuiltinFunctionType(BOOLEAN, new Parameter("range1", COMPARABLE_RANGE_TYPE), new Parameter("range2", COMPARABLE_RANGE_TYPE))));

        environment.addDeclaration(INSTANCE.makeVariableDeclaration("includes", new BuiltinFunctionType(BOOLEAN, new Parameter("range", COMPARABLE_RANGE_TYPE), new Parameter("point", COMPARABLE))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("includes", new BuiltinFunctionType(BOOLEAN, new Parameter("range1", COMPARABLE_RANGE_TYPE), new Parameter("range2", COMPARABLE_RANGE_TYPE))));

        environment.addDeclaration(INSTANCE.makeVariableDeclaration("during", new BuiltinFunctionType(BOOLEAN, new Parameter("point", COMPARABLE), new Parameter("range", COMPARABLE_RANGE_TYPE))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("during", new BuiltinFunctionType(BOOLEAN, new Parameter("range1", COMPARABLE_RANGE_TYPE), new Parameter("range2", COMPARABLE_RANGE_TYPE))));

        environment.addDeclaration(INSTANCE.makeVariableDeclaration("starts", new BuiltinFunctionType(BOOLEAN, new Parameter("point", COMPARABLE), new Parameter("range", COMPARABLE_RANGE_TYPE))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("starts", new BuiltinFunctionType(BOOLEAN, new Parameter("range1", COMPARABLE_RANGE_TYPE), new Parameter("range2", COMPARABLE_RANGE_TYPE))));

        environment.addDeclaration(INSTANCE.makeVariableDeclaration("started by", new BuiltinFunctionType(BOOLEAN, new Parameter("range", COMPARABLE_RANGE_TYPE), new Parameter("point", COMPARABLE))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("started by", new BuiltinFunctionType(BOOLEAN, new Parameter("range1", COMPARABLE_RANGE_TYPE), new Parameter("range2", COMPARABLE_RANGE_TYPE))));

        environment.addDeclaration(INSTANCE.makeVariableDeclaration("coincides", new BuiltinFunctionType(BOOLEAN, new Parameter("point1", COMPARABLE), new Parameter("point2", COMPARABLE))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("coincides", new BuiltinFunctionType(BOOLEAN, new Parameter("range1", COMPARABLE_RANGE_TYPE), new Parameter("range2", COMPARABLE_RANGE_TYPE))));
    }
}
