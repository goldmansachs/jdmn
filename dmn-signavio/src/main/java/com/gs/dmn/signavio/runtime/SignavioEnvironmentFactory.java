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
package com.gs.dmn.signavio.runtime;

import com.gs.dmn.feel.analysis.semantics.environment.Environment;
import com.gs.dmn.feel.analysis.semantics.environment.EnvironmentFactory;
import com.gs.dmn.feel.analysis.semantics.environment.Parameter;
import com.gs.dmn.feel.analysis.semantics.type.BuiltinFunctionType;

import static com.gs.dmn.feel.analysis.semantics.type.AnyType.ANY;
import static com.gs.dmn.feel.analysis.semantics.type.BooleanType.BOOLEAN;
import static com.gs.dmn.feel.analysis.semantics.type.DateTimeType.DATE_AND_TIME;
import static com.gs.dmn.feel.analysis.semantics.type.DateType.DATE;
import static com.gs.dmn.feel.analysis.semantics.type.ListType.ANY_LIST;
import static com.gs.dmn.feel.analysis.semantics.type.NumberType.NUMBER;
import static com.gs.dmn.feel.analysis.semantics.type.StringType.STRING;
import static com.gs.dmn.feel.analysis.semantics.type.TimeType.TIME;

public class SignavioEnvironmentFactory implements EnvironmentFactory {
    private static final EnvironmentFactory INSTANCE = new SignavioEnvironmentFactory();
    private static final Environment ROOT_ENVIRONMENT;

    static {
        ROOT_ENVIRONMENT = INSTANCE.emptyEnvironment();
        addSignavioFunctions(ROOT_ENVIRONMENT);
    }

    public static EnvironmentFactory instance() {
        return INSTANCE;
    }

    @Override
    public Environment getRootEnvironment() {
        return SignavioEnvironmentFactory.ROOT_ENVIRONMENT;
    }

    private static void addSignavioFunctions(Environment environment) {
        // Data acceptance functions
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("isDefined", new BuiltinFunctionType(BOOLEAN, new Parameter("", ANY))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("isUndefined", new BuiltinFunctionType(BOOLEAN, new Parameter("", ANY))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("isValid", new BuiltinFunctionType(BOOLEAN, new Parameter("", ANY))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("isInvalid", new BuiltinFunctionType(BOOLEAN, new Parameter("", ANY))));

        // Conversion functions
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("date", new BuiltinFunctionType(DATE, new Parameter("from", STRING))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("date and time", new BuiltinFunctionType(DATE_AND_TIME, new Parameter("from", STRING))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("time", new BuiltinFunctionType(TIME, new Parameter("from", STRING))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("string", new BuiltinFunctionType(STRING, new Parameter("from", ANY))));

        // Number functions
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("abs", new BuiltinFunctionType(NUMBER, new Parameter("number", NUMBER))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("count", new BuiltinFunctionType(NUMBER, new Parameter("list", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("round", new BuiltinFunctionType(NUMBER, new Parameter("number", NUMBER), new Parameter("digits", NUMBER))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("ceiling", new BuiltinFunctionType(NUMBER, new Parameter("n", NUMBER))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("floor", new BuiltinFunctionType(NUMBER, new Parameter("n", NUMBER))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("integer", new BuiltinFunctionType(NUMBER, new Parameter("number", NUMBER))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("modulo", new BuiltinFunctionType(NUMBER, new Parameter("divident", NUMBER), new Parameter("divisor", NUMBER))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("percent", new BuiltinFunctionType(NUMBER, new Parameter("number", NUMBER))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("power", new BuiltinFunctionType(NUMBER, new Parameter("base", NUMBER), new Parameter("exponent", NUMBER))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("product", new BuiltinFunctionType(NUMBER, new Parameter("list", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("roundDown", new BuiltinFunctionType(NUMBER, new Parameter("number", NUMBER), new Parameter("digits", NUMBER))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("roundUp", new BuiltinFunctionType(NUMBER, new Parameter("number", NUMBER), new Parameter("digits", NUMBER))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("sum", new BuiltinFunctionType(NUMBER, new Parameter("list", ANY_LIST))));

        // Date time functions
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("day", new BuiltinFunctionType(NUMBER, new Parameter("datetime", DATE))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("day", new BuiltinFunctionType(NUMBER, new Parameter("datetime", DATE_AND_TIME))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("dayAdd", new BuiltinFunctionType(DATE, new Parameter("datetime", DATE), new Parameter("days_to_add", NUMBER))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("dayAdd", new BuiltinFunctionType(DATE, new Parameter("datetime", DATE_AND_TIME), new Parameter("days_to_add", NUMBER))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("dayDiff", new BuiltinFunctionType(NUMBER, new Parameter("datetime1", DATE), new Parameter("datetime2", DATE))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("dayDiff", new BuiltinFunctionType(NUMBER, new Parameter("datetime1", DATE_AND_TIME), new Parameter("datetime2", DATE_AND_TIME))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("date", new BuiltinFunctionType(DATE, new Parameter("year", NUMBER), new Parameter("month", NUMBER), new Parameter("day", NUMBER))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("dateTime", new BuiltinFunctionType(DATE_AND_TIME, new Parameter("day", NUMBER), new Parameter("month", NUMBER), new Parameter("year", NUMBER), new Parameter("hour", NUMBER), new Parameter("minute", NUMBER), new Parameter("second", NUMBER), new Parameter("hourOffset", NUMBER, true, false))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("hour", new BuiltinFunctionType(NUMBER, new Parameter("datetime", TIME))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("hour", new BuiltinFunctionType(NUMBER, new Parameter("datetime", DATE_AND_TIME))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("hourDiff", new BuiltinFunctionType(NUMBER, new Parameter("datetime1", TIME), new Parameter("datetime2", TIME))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("hourDiff", new BuiltinFunctionType(NUMBER, new Parameter("datetime1", DATE_AND_TIME), new Parameter("datetime2", DATE_AND_TIME))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("minute", new BuiltinFunctionType(NUMBER, new Parameter("datetime", TIME))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("minute", new BuiltinFunctionType(NUMBER, new Parameter("datetime", DATE_AND_TIME))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("minutesDiff", new BuiltinFunctionType(NUMBER, new Parameter("datetime1", TIME), new Parameter("datetime2", TIME))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("minutesDiff", new BuiltinFunctionType(NUMBER, new Parameter("datetime1", DATE_AND_TIME), new Parameter("datetime2", DATE_AND_TIME))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("month", new BuiltinFunctionType(NUMBER, new Parameter("datetime", DATE))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("month", new BuiltinFunctionType(NUMBER, new Parameter("datetime", DATE_AND_TIME))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("monthAdd", new BuiltinFunctionType(DATE, new Parameter("datetime", DATE), new Parameter("months_to_add", NUMBER))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("monthAdd", new BuiltinFunctionType(DATE, new Parameter("datetime", DATE_AND_TIME), new Parameter("months_to_add", NUMBER))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("monthDiff", new BuiltinFunctionType(NUMBER, new Parameter("datetime1", DATE), new Parameter("datetime2", DATE))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("monthDiff", new BuiltinFunctionType(NUMBER, new Parameter("datetime1", DATE_AND_TIME), new Parameter("datetime2", DATE_AND_TIME))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("now", new BuiltinFunctionType(DATE_AND_TIME)));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("today", new BuiltinFunctionType(DATE)));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("weekday", new BuiltinFunctionType(NUMBER, new Parameter("datetime", DATE))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("weekday", new BuiltinFunctionType(NUMBER, new Parameter("datetime", DATE_AND_TIME))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("year", new BuiltinFunctionType(NUMBER, new Parameter("datetime", DATE))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("year", new BuiltinFunctionType(NUMBER, new Parameter("datetime", DATE_AND_TIME))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("yearAdd", new BuiltinFunctionType(DATE_AND_TIME, new Parameter("datetime", DATE), new Parameter("years_to_add", NUMBER))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("yearAdd", new BuiltinFunctionType(DATE_AND_TIME, new Parameter("datetime", DATE_AND_TIME), new Parameter("years_to_add", NUMBER))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("yearDiff", new BuiltinFunctionType(NUMBER, new Parameter("datetime1", DATE), new Parameter("datetime2", DATE))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("yearDiff", new BuiltinFunctionType(NUMBER, new Parameter("datetime1", DATE_AND_TIME), new Parameter("datetime2", DATE_AND_TIME))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("yearDiff", new BuiltinFunctionType(NUMBER, new Parameter("datetime1", DATE), new Parameter("datetime2", DATE_AND_TIME))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("yearDiff", new BuiltinFunctionType(NUMBER, new Parameter("datetime1", DATE_AND_TIME), new Parameter("datetime2", DATE))));

        // List functions
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("append", new BuiltinFunctionType(ANY_LIST, new Parameter("list", ANY_LIST), new Parameter("element", ANY))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("appendAll", new BuiltinFunctionType(ANY_LIST, new Parameter("list1", ANY_LIST), new Parameter("list2", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("remove", new BuiltinFunctionType(ANY_LIST, new Parameter("list", ANY_LIST), new Parameter("element", ANY))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("removeAll", new BuiltinFunctionType(ANY_LIST, new Parameter("list1", ANY_LIST), new Parameter("list2", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("zip", new BuiltinFunctionType(ANY_LIST, new Parameter("attributes", ANY_LIST), new Parameter("values", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("notContainsAny", new BuiltinFunctionType(BOOLEAN, new Parameter("list1", ANY_LIST), new Parameter("list2", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("containsOnly", new BuiltinFunctionType(BOOLEAN, new Parameter("list1", ANY_LIST), new Parameter("list2", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("areElementsOf", new BuiltinFunctionType(BOOLEAN, new Parameter("list1", ANY_LIST), new Parameter("list2", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("elementOf", new BuiltinFunctionType(BOOLEAN, new Parameter("list1", ANY), new Parameter("list2", ANY_LIST))));

        // Statistical operations
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("avg", new BuiltinFunctionType(NUMBER, new Parameter("list", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("max", new BuiltinFunctionType(NUMBER, new Parameter("list", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("median", new BuiltinFunctionType(NUMBER, new Parameter("list", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("min", new BuiltinFunctionType(NUMBER, new Parameter("list", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("mode", new BuiltinFunctionType(NUMBER, new Parameter("list", ANY_LIST))));

        // String functions
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("concat", new BuiltinFunctionType(STRING, new Parameter("list", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("isAlpha", new BuiltinFunctionType(BOOLEAN, new Parameter("text", STRING))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("isAlphanumeric", new BuiltinFunctionType(BOOLEAN, new Parameter("text", STRING))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("isNumeric", new BuiltinFunctionType(BOOLEAN, new Parameter("text", STRING))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("isSpaces", new BuiltinFunctionType(BOOLEAN, new Parameter("text", STRING))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("len", new BuiltinFunctionType(NUMBER, new Parameter("text", STRING))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("lower", new BuiltinFunctionType(STRING, new Parameter("text", STRING))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("trim", new BuiltinFunctionType(STRING, new Parameter("text", STRING))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("upper", new BuiltinFunctionType(STRING, new Parameter("text", STRING))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("number", new BuiltinFunctionType(NUMBER, new Parameter("text", STRING), new Parameter("default_value", NUMBER, true, false))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("mid", new BuiltinFunctionType(STRING, new Parameter("text", STRING), new Parameter("start", NUMBER), new Parameter("num_chars", NUMBER))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("left", new BuiltinFunctionType(STRING, new Parameter("text", STRING), new Parameter("num_chars", NUMBER))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("right", new BuiltinFunctionType(STRING, new Parameter("text", STRING), new Parameter("num_chars", NUMBER))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("text", new BuiltinFunctionType(STRING, new Parameter("num", NUMBER), new Parameter("format_text", STRING))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("textOccurrences", new BuiltinFunctionType(NUMBER, new Parameter("find_text", STRING), new Parameter("within_text", STRING))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("contains", new BuiltinFunctionType(BOOLEAN, new Parameter("text", STRING), new Parameter("substring", STRING))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("startsWith", new BuiltinFunctionType(BOOLEAN, new Parameter("string", STRING), new Parameter("prefix", STRING))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("endsWith", new BuiltinFunctionType(BOOLEAN, new Parameter("string", STRING), new Parameter("suffix", STRING))));

        // Boolean functions
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("not", new BuiltinFunctionType(BOOLEAN, new Parameter("boolean", BOOLEAN))));
    }
}
