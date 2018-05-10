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
package com.gs.dmn.feel.analysis.semantics.environment;

import com.gs.dmn.feel.analysis.semantics.type.BuiltinFunctionType;

import static com.gs.dmn.feel.analysis.semantics.type.AnyType.ANY;
import static com.gs.dmn.feel.analysis.semantics.type.BooleanType.BOOLEAN;
import static com.gs.dmn.feel.analysis.semantics.type.DateTimeType.DATE_AND_TIME;
import static com.gs.dmn.feel.analysis.semantics.type.DateType.DATE;
import static com.gs.dmn.feel.analysis.semantics.type.DurationType.DAYS_AND_TIME_DURATION;
import static com.gs.dmn.feel.analysis.semantics.type.DurationType.YEARS_AND_MONTHS_DURATION;
import static com.gs.dmn.feel.analysis.semantics.type.ListType.ANY_LIST;
import static com.gs.dmn.feel.analysis.semantics.type.ListType.STRING_LIST;
import static com.gs.dmn.feel.analysis.semantics.type.NumberType.NUMBER;
import static com.gs.dmn.feel.analysis.semantics.type.StringType.STRING;
import static com.gs.dmn.feel.analysis.semantics.type.TimeType.TIME;

public class DefaultDMNEnvironmentFactory extends EnvironmentFactory {
    private static final EnvironmentFactory INSTANCE = new DefaultDMNEnvironmentFactory();
    private static final Environment ROOT_ENVIRONMENT;

    static {
        ROOT_ENVIRONMENT = INSTANCE.emptyEnvironment();
        addFEELFunctions(ROOT_ENVIRONMENT);
    }

    public static EnvironmentFactory instance() {
        return INSTANCE;
    }

    private DefaultDMNEnvironmentFactory() {
    }

    @Override
    public Environment getRootEnvironment() {
        return DefaultDMNEnvironmentFactory.ROOT_ENVIRONMENT;
    }

    private static void addFEELFunctions(Environment environment) {
        addConversionFunctions(environment);
        addBooleanFunctions(environment);
        addNumberFunctions(environment);
        addStringFunctions(environment);
        addListFunctions(environment);
    }

    private static void addConversionFunctions(Environment environment) {
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("date", new BuiltinFunctionType(DATE, new Parameter("from", STRING))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("date", new BuiltinFunctionType(DATE, new Parameter("from", DATE))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("date", new BuiltinFunctionType(DATE, new Parameter("from", DATE_AND_TIME))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("date", new BuiltinFunctionType(DATE, new Parameter("year", NUMBER), new Parameter("month", NUMBER), new Parameter("day", NUMBER))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("date and time", new BuiltinFunctionType(DATE_AND_TIME, new Parameter("date", DATE), new Parameter("time", TIME))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("date and time", new BuiltinFunctionType(DATE_AND_TIME, new Parameter("date", DATE_AND_TIME), new Parameter("time", TIME))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("date and time", new BuiltinFunctionType(DATE_AND_TIME, new Parameter("from", STRING))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("time", new BuiltinFunctionType(TIME, new Parameter("from", STRING))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("time", new BuiltinFunctionType(TIME, new Parameter("from", DATE))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("time", new BuiltinFunctionType(TIME, new Parameter("from", DATE_AND_TIME))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("time", new BuiltinFunctionType(TIME, new Parameter("hour", NUMBER), new Parameter("minute", NUMBER), new Parameter("second", NUMBER), new Parameter("offset", DAYS_AND_TIME_DURATION, true, false))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("number", new BuiltinFunctionType(NUMBER, new Parameter("from", STRING), new Parameter("grouping separator", STRING), new Parameter("decimal separator", STRING))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("string", new BuiltinFunctionType(STRING, new Parameter("from", ANY))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("duration", new BuiltinFunctionType(ANY, new Parameter("from", STRING))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("years and months duration", new BuiltinFunctionType(YEARS_AND_MONTHS_DURATION, new Parameter("from", DATE), new Parameter("to", DATE))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("years and months duration", new BuiltinFunctionType(YEARS_AND_MONTHS_DURATION, new Parameter("from", DATE_AND_TIME), new Parameter("to", DATE_AND_TIME))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("years and months duration", new BuiltinFunctionType(YEARS_AND_MONTHS_DURATION, new Parameter("from", DATE), new Parameter("to", DATE_AND_TIME))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("years and months duration", new BuiltinFunctionType(YEARS_AND_MONTHS_DURATION, new Parameter("from", DATE_AND_TIME), new Parameter("to", DATE))));
    }

    private static void addBooleanFunctions(Environment environment) {
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("not", new BuiltinFunctionType(BOOLEAN, new Parameter("negand", BOOLEAN))));
    }

    private static void addStringFunctions(Environment environment) {
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("substring", new BuiltinFunctionType(STRING, new Parameter("string", STRING), new Parameter("start position", NUMBER), new Parameter("length", NUMBER, true, false))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("string length", new BuiltinFunctionType(NUMBER, new Parameter("string", STRING))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("upper case", new BuiltinFunctionType(STRING, new Parameter("string", STRING))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("lower case", new BuiltinFunctionType(STRING, new Parameter("string", STRING))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("substring before", new BuiltinFunctionType(STRING, new Parameter("string", STRING), new Parameter("match", STRING))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("substring after", new BuiltinFunctionType(STRING, new Parameter("string", STRING), new Parameter("match", STRING))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("replace", new BuiltinFunctionType(STRING, new Parameter("input", STRING), new Parameter("pattern", STRING), new Parameter("replacement", STRING), new Parameter("flags", STRING, true, false))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("contains", new BuiltinFunctionType(BOOLEAN, new Parameter("string", STRING), new Parameter("match", STRING))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("starts with", new BuiltinFunctionType(BOOLEAN, new Parameter("string", STRING), new Parameter("match", STRING))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("ends with", new BuiltinFunctionType(BOOLEAN, new Parameter("string", STRING), new Parameter("match", STRING))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("matches", new BuiltinFunctionType(BOOLEAN, new Parameter("input", STRING), new Parameter("pattern", STRING), new Parameter("flags", STRING, true, false))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("split", new BuiltinFunctionType(STRING_LIST, new Parameter("string", STRING), new Parameter("delimiter", STRING))));
    }

    private static void addListFunctions(Environment environment) {
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("list contains", new BuiltinFunctionType(BOOLEAN, new Parameter("list", ANY_LIST), new Parameter("element", ANY))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("count", new BuiltinFunctionType(NUMBER, new Parameter("list", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("min", new BuiltinFunctionType(NUMBER, new Parameter("list", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("min", new BuiltinFunctionType(NUMBER, new Parameter("c1", ANY), new Parameter("cs", ANY, false, true))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("max", new BuiltinFunctionType(NUMBER, new Parameter("list", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("max", new BuiltinFunctionType(NUMBER, new Parameter("c1", ANY), new Parameter("cs", ANY, false, true))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("sum", new BuiltinFunctionType(NUMBER, new Parameter("list", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("sum", new BuiltinFunctionType(NUMBER, new Parameter("n1", ANY), new Parameter("ns", ANY, false, true))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("mean", new BuiltinFunctionType(NUMBER, new Parameter("list", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("mean", new BuiltinFunctionType(NUMBER, new Parameter("n1", ANY), new Parameter("ns", ANY, false, true))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("and", new BuiltinFunctionType(BOOLEAN, new Parameter("list", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("and", new BuiltinFunctionType(NUMBER, new Parameter("b1", ANY), new Parameter("bs", ANY, false, true))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("all", new BuiltinFunctionType(BOOLEAN, new Parameter("list", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("all", new BuiltinFunctionType(NUMBER, new Parameter("b1", ANY), new Parameter("bs", ANY, false, true))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("or", new BuiltinFunctionType(BOOLEAN, new Parameter("list", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("or", new BuiltinFunctionType(NUMBER, new Parameter("b1", ANY), new Parameter("bs", ANY, false, true))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("any", new BuiltinFunctionType(BOOLEAN, new Parameter("list", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("any", new BuiltinFunctionType(NUMBER, new Parameter("b1", ANY), new Parameter("bs", ANY, false, true))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("sublist", new BuiltinFunctionType(ANY_LIST, new Parameter("list", ANY_LIST), new Parameter("start position", NUMBER), new Parameter("length", NUMBER, true, false))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("append", new BuiltinFunctionType(ANY_LIST, new Parameter("list", ANY_LIST), new Parameter("item", ANY))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("append", new BuiltinFunctionType(ANY_LIST, new Parameter("list", ANY_LIST), new Parameter("item", ANY, false, true))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("concatenate", new BuiltinFunctionType(ANY_LIST, new Parameter("list1", ANY_LIST, false, true))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("insert before", new BuiltinFunctionType(ANY_LIST, new Parameter("list", ANY_LIST), new Parameter("position", NUMBER), new Parameter("new item", ANY))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("remove", new BuiltinFunctionType(ANY_LIST, new Parameter("list", ANY_LIST), new Parameter("position", NUMBER))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("reverse", new BuiltinFunctionType(ANY_LIST, new Parameter("list", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("index of", new BuiltinFunctionType(ANY_LIST, new Parameter("list", ANY_LIST), new Parameter("match", ANY))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("distinct values", new BuiltinFunctionType(ANY_LIST, new Parameter("list", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("union", new BuiltinFunctionType(ANY_LIST, new Parameter("list1", ANY_LIST), new Parameter("list2", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("flatten", new BuiltinFunctionType(ANY_LIST, new Parameter("list", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("product", new BuiltinFunctionType(NUMBER, new Parameter("list", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("product", new BuiltinFunctionType(NUMBER, new Parameter("n1", ANY), new Parameter("ns", ANY, false, true))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("median", new BuiltinFunctionType(NUMBER, new Parameter("list", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("median", new BuiltinFunctionType(NUMBER, new Parameter("n1", ANY), new Parameter("ns", ANY, false, true))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("stddev", new BuiltinFunctionType(NUMBER, new Parameter("list", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("stddev", new BuiltinFunctionType(NUMBER, new Parameter("n1", ANY), new Parameter("ns", ANY, false, true))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("mode", new BuiltinFunctionType(NUMBER, new Parameter("list", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("mode", new BuiltinFunctionType(NUMBER, new Parameter("n1", ANY), new Parameter("ns", ANY, false, true))));

        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("sort", new BuiltinFunctionType(ANY_LIST, new Parameter("list", ANY_LIST), new Parameter("function", ANY))));
    }

    private static void addNumberFunctions(Environment environment) {
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("decimal", new BuiltinFunctionType(NUMBER, new Parameter("n", NUMBER), new Parameter("scale", NUMBER))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("floor", new BuiltinFunctionType(NUMBER, new Parameter("n", NUMBER))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("ceiling", new BuiltinFunctionType(NUMBER, new Parameter("n", NUMBER))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("abs", new BuiltinFunctionType(NUMBER, new Parameter("number", NUMBER))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("modulo", new BuiltinFunctionType(NUMBER, new Parameter("number", NUMBER))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("sqrt", new BuiltinFunctionType(NUMBER, new Parameter("number", NUMBER))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("log", new BuiltinFunctionType(NUMBER, new Parameter("number", NUMBER))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("exp", new BuiltinFunctionType(NUMBER, new Parameter("number", NUMBER))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("odd", new BuiltinFunctionType(BOOLEAN, new Parameter("number", NUMBER))));
        environment.addDeclaration(INSTANCE.makeFunctionDeclaration("even", new BuiltinFunctionType(BOOLEAN, new Parameter("number", NUMBER))));
    }
}
