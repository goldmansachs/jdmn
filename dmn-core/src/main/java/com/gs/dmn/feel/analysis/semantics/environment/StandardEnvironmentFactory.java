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
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter;
import com.gs.dmn.runtime.DMNContext;
import com.gs.dmn.runtime.DMNContextKind;
import com.gs.dmn.runtime.function.BuiltinFunction;
import com.gs.dmn.runtime.interpreter.environment.RuntimeEnvironment;

import java.util.List;
import java.util.Map;

import static com.gs.dmn.feel.analysis.semantics.type.AnyType.ANY;
import static com.gs.dmn.feel.analysis.semantics.type.BooleanType.BOOLEAN;
import static com.gs.dmn.feel.analysis.semantics.type.ComparableDataType.COMPARABLE;
import static com.gs.dmn.feel.analysis.semantics.type.DateTimeType.DATE_AND_TIME;
import static com.gs.dmn.feel.analysis.semantics.type.DateType.DATE;
import static com.gs.dmn.feel.analysis.semantics.type.DurationType.DAYS_AND_TIME_DURATION;
import static com.gs.dmn.feel.analysis.semantics.type.DurationType.YEARS_AND_MONTHS_DURATION;
import static com.gs.dmn.feel.analysis.semantics.type.ListType.*;
import static com.gs.dmn.feel.analysis.semantics.type.NumberType.NUMBER;
import static com.gs.dmn.feel.analysis.semantics.type.RangeType.COMPARABLE_RANGE;
import static com.gs.dmn.feel.analysis.semantics.type.StringType.STRING;
import static com.gs.dmn.feel.analysis.semantics.type.TimeType.TIME;

public class StandardEnvironmentFactory implements EnvironmentFactory {
    private static final EnvironmentFactory INSTANCE = new StandardEnvironmentFactory();
    private static final DMNContext BUILT_IN_CONTEXT;

    static {
        Environment environment = INSTANCE.emptyEnvironment();
        RuntimeEnvironment runtimeEnvironment = RuntimeEnvironment.of();
        addFEELFunctions(environment);
        for (Map.Entry<String, List<Declaration>> entry: environment.variablesTable.entrySet()) {
            runtimeEnvironment.bind(entry.getKey(), BuiltinFunction.of(entry.getValue()));
        }
        BUILT_IN_CONTEXT = DMNContext.of(
                null,
                DMNContextKind.BUILT_IN,
                null,
                environment,
                runtimeEnvironment
        );
    }

    public static EnvironmentFactory instance() {
        return INSTANCE;
    }

    public static BuiltinFunctionType makeSublistBuiltInFunctionType(Type listType) {
        return new BuiltinFunctionType(listType, new FormalParameter("list", listType), new FormalParameter("start position", NUMBER), new FormalParameter("length", NUMBER, true, false));
    }

    public static BuiltinFunctionType makeAppendBuiltinFunctionType(Type listType, Type itemType) {
        return new BuiltinFunctionType(listType, new FormalParameter("list", listType), new FormalParameter("item", itemType, false, true));
    }

    public static BuiltinFunctionType makeConcatenateBuiltinFunctionType(Type listType) {
        return new BuiltinFunctionType(listType, new FormalParameter("list1", listType, false, true));
    }

    public static BuiltinFunctionType makeInsertBeforeBuiltinFunctionType(Type listType, Type itemType) {
        return new BuiltinFunctionType(listType, new FormalParameter("list", listType), new FormalParameter("position", NUMBER), new FormalParameter("new item", itemType));
    }

    public static BuiltinFunctionType makeRemoveBuiltinFunctionType(Type listType) {
        return new BuiltinFunctionType(listType, new FormalParameter("list", listType), new FormalParameter("position", NUMBER));
    }

    public static BuiltinFunctionType makeReverseBuiltinFunctionType(Type listType) {
        return new BuiltinFunctionType(listType, new FormalParameter("list", listType));
    }

    public static BuiltinFunctionType makeIndexOfBuiltinFunctionType(Type listType, Type matchType) {
        return new BuiltinFunctionType(NUMBER_LIST, new FormalParameter("list", listType), new FormalParameter("match", matchType));
    }

    public static BuiltinFunctionType makeDistinctValuesBuiltinFunctionType(Type listType) {
        return new BuiltinFunctionType(listType, new FormalParameter("list", listType));
    }

    public static BuiltinFunctionType makeUnionBuiltinFunctionType(Type listType) {
        return new BuiltinFunctionType(listType, new FormalParameter("list1", listType), new FormalParameter("list2", listType));
    }

    public static BuiltinFunctionType makeFlattenBuiltinFunctionType(Type listType) {
        return new BuiltinFunctionType(listType, new FormalParameter("list", ANY_LIST));
    }

    public static BuiltinFunctionType makeSortBuiltinFunctionType(Type listType, Type functionType) {
        return new BuiltinFunctionType(listType, new FormalParameter("list", listType), new FormalParameter("function", functionType));
    }

    // Signavio
    public static BuiltinFunctionType makeSignavioAppendBuiltinFunctionType(Type listType, Type elementType) {
        return new BuiltinFunctionType(listType, new FormalParameter("list", listType), new FormalParameter("element", elementType));
    }

    public static BuiltinFunctionType makeSignavioAppendAllBuiltinFunctionType(Type listType) {
        return new BuiltinFunctionType(listType, new FormalParameter("list1", listType), new FormalParameter("list2", listType));
    }

    public static BuiltinFunctionType makeSignavioRemoveBuiltinFunctionType(Type listType, Type elementType) {
        return new BuiltinFunctionType(listType, new FormalParameter("list", listType), new FormalParameter("element", elementType));
    }

    public static BuiltinFunctionType makeSignavioRemoveAllBuiltinFunctionType(Type listType) {
        return new BuiltinFunctionType(listType, new FormalParameter("list1", listType), new FormalParameter("list2", listType));
    }

    public static BuiltinFunctionType makeSignavioZipBuiltinFunctionType(Type resultType, Type attributesType, Type valuesType) {
        return new BuiltinFunctionType(resultType, new FormalParameter("attributes", attributesType), new FormalParameter("values", valuesType));
    }

    private StandardEnvironmentFactory() {
    }

    @Override
    public DMNContext getBuiltInContext() {
        return StandardEnvironmentFactory.BUILT_IN_CONTEXT;
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
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("date", new BuiltinFunctionType(DATE, new FormalParameter("from", STRING))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("date", new BuiltinFunctionType(DATE, new FormalParameter("from", DATE))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("date", new BuiltinFunctionType(DATE, new FormalParameter("from", DATE_AND_TIME))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("date", new BuiltinFunctionType(DATE, new FormalParameter("year", NUMBER), new FormalParameter("month", NUMBER), new FormalParameter("day", NUMBER))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("date and time", new BuiltinFunctionType(DATE_AND_TIME, new FormalParameter("date", DATE), new FormalParameter("time", TIME))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("date and time", new BuiltinFunctionType(DATE_AND_TIME, new FormalParameter("date", DATE_AND_TIME), new FormalParameter("time", TIME))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("date and time", new BuiltinFunctionType(DATE_AND_TIME, new FormalParameter("from", STRING))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("time", new BuiltinFunctionType(TIME, new FormalParameter("from", STRING))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("time", new BuiltinFunctionType(TIME, new FormalParameter("from", DATE))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("time", new BuiltinFunctionType(TIME, new FormalParameter("from", DATE_AND_TIME))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("time", new BuiltinFunctionType(TIME, new FormalParameter("hour", NUMBER), new FormalParameter("minute", NUMBER), new FormalParameter("second", NUMBER), new FormalParameter("offset", DAYS_AND_TIME_DURATION, true, false))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("number", new BuiltinFunctionType(NUMBER, new FormalParameter("from", STRING), new FormalParameter("'grouping separator'", STRING), new FormalParameter("'decimal separator'", STRING))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("number", new BuiltinFunctionType(NUMBER, new FormalParameter("from", STRING), new FormalParameter("groupingSeparator", STRING), new FormalParameter("decimalSeparator", STRING))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("string", new BuiltinFunctionType(STRING, new FormalParameter("from", ANY))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("duration", new BuiltinFunctionType(ANY, new FormalParameter("from", STRING))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("years and months duration", new BuiltinFunctionType(YEARS_AND_MONTHS_DURATION, new FormalParameter("from", DATE), new FormalParameter("to", DATE))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("years and months duration", new BuiltinFunctionType(YEARS_AND_MONTHS_DURATION, new FormalParameter("from", DATE_AND_TIME), new FormalParameter("to", DATE_AND_TIME))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("years and months duration", new BuiltinFunctionType(YEARS_AND_MONTHS_DURATION, new FormalParameter("from", DATE), new FormalParameter("to", DATE_AND_TIME))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("years and months duration", new BuiltinFunctionType(YEARS_AND_MONTHS_DURATION, new FormalParameter("from", DATE_AND_TIME), new FormalParameter("to", DATE))));
    }

    private static void addNumberFunctions(Environment environment) {
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("decimal", new BuiltinFunctionType(NUMBER, new FormalParameter("n", NUMBER), new FormalParameter("scale", NUMBER))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("round", new BuiltinFunctionType(NUMBER, new FormalParameter("n", NUMBER), new FormalParameter("scale", NUMBER), new FormalParameter("mode", STRING))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("round up", new BuiltinFunctionType(NUMBER, new FormalParameter("n", NUMBER), new FormalParameter("scale", NUMBER))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("round down", new BuiltinFunctionType(NUMBER, new FormalParameter("n", NUMBER), new FormalParameter("scale", NUMBER))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("round half up", new BuiltinFunctionType(NUMBER, new FormalParameter("n", NUMBER), new FormalParameter("scale", NUMBER))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("round half down", new BuiltinFunctionType(NUMBER, new FormalParameter("n", NUMBER), new FormalParameter("scale", NUMBER))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("floor", new BuiltinFunctionType(NUMBER, new FormalParameter("n", NUMBER))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("floor", new BuiltinFunctionType(NUMBER, new FormalParameter("n", NUMBER), new FormalParameter("scale", NUMBER))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("ceiling", new BuiltinFunctionType(NUMBER, new FormalParameter("n", NUMBER))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("ceiling", new BuiltinFunctionType(NUMBER, new FormalParameter("n", NUMBER), new FormalParameter("scale", NUMBER))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("abs", new BuiltinFunctionType(NUMBER, new FormalParameter("n", NUMBER))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("abs", new BuiltinFunctionType(YEARS_AND_MONTHS_DURATION, new FormalParameter("n", YEARS_AND_MONTHS_DURATION))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("abs", new BuiltinFunctionType(DAYS_AND_TIME_DURATION, new FormalParameter("n", DAYS_AND_TIME_DURATION))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("modulo", new BuiltinFunctionType(NUMBER, new FormalParameter("dividend", NUMBER), new FormalParameter("divisor", NUMBER))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("sqrt", new BuiltinFunctionType(NUMBER, new FormalParameter("number", NUMBER))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("log", new BuiltinFunctionType(NUMBER, new FormalParameter("number", NUMBER))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("exp", new BuiltinFunctionType(NUMBER, new FormalParameter("number", NUMBER))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("odd", new BuiltinFunctionType(BOOLEAN, new FormalParameter("number", NUMBER))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("even", new BuiltinFunctionType(BOOLEAN, new FormalParameter("number", NUMBER))));
    }

    private static void addBooleanFunctions(Environment environment) {
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("not", new BuiltinFunctionType(BOOLEAN, new FormalParameter("negand", BOOLEAN))));
    }

    private static void addStringFunctions(Environment environment) {
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("substring", new BuiltinFunctionType(STRING, new FormalParameter("string", STRING), new FormalParameter("'start position'", NUMBER), new FormalParameter("length", NUMBER, true, false))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("substring", new BuiltinFunctionType(STRING, new FormalParameter("string", STRING), new FormalParameter("startPosition", NUMBER), new FormalParameter("length", NUMBER, true, false))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("string length", new BuiltinFunctionType(NUMBER, new FormalParameter("string", STRING))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("upper case", new BuiltinFunctionType(STRING, new FormalParameter("string", STRING))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("lower case", new BuiltinFunctionType(STRING, new FormalParameter("string", STRING))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("substring before", new BuiltinFunctionType(STRING, new FormalParameter("string", STRING), new FormalParameter("match", STRING))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("substring after", new BuiltinFunctionType(STRING, new FormalParameter("string", STRING), new FormalParameter("match", STRING))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("replace", new BuiltinFunctionType(STRING, new FormalParameter("input", STRING), new FormalParameter("pattern", STRING), new FormalParameter("replacement", STRING), new FormalParameter("flags", STRING, true, false))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("contains", new BuiltinFunctionType(BOOLEAN, new FormalParameter("string", STRING), new FormalParameter("match", STRING))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("starts with", new BuiltinFunctionType(BOOLEAN, new FormalParameter("string", STRING), new FormalParameter("match", STRING))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("ends with", new BuiltinFunctionType(BOOLEAN, new FormalParameter("string", STRING), new FormalParameter("match", STRING))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("matches", new BuiltinFunctionType(BOOLEAN, new FormalParameter("input", STRING), new FormalParameter("pattern", STRING), new FormalParameter("flags", STRING, true, false))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("split", new BuiltinFunctionType(STRING_LIST, new FormalParameter("string", STRING), new FormalParameter("delimiter", STRING))));
    }

    private static void addListFunctions(Environment environment) {
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("list contains", new BuiltinFunctionType(BOOLEAN, new FormalParameter("list", ANY_LIST), new FormalParameter("element", ANY))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("count", new BuiltinFunctionType(NUMBER, new FormalParameter("list", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("min", new BuiltinFunctionType(NUMBER, new FormalParameter("list", COMPARABLE_LIST))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("min", new BuiltinFunctionType(NUMBER, new FormalParameter("c1", COMPARABLE), new FormalParameter("cs", COMPARABLE, false, true))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("max", new BuiltinFunctionType(NUMBER, new FormalParameter("list", COMPARABLE_LIST))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("max", new BuiltinFunctionType(NUMBER, new FormalParameter("c1", COMPARABLE), new FormalParameter("cs", COMPARABLE, false, true))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("sum", new BuiltinFunctionType(NUMBER, new FormalParameter("list", NUMBER_LIST))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("sum", new BuiltinFunctionType(NUMBER, new FormalParameter("n1", NUMBER), new FormalParameter("ns", NUMBER, false, true))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("mean", new BuiltinFunctionType(NUMBER, new FormalParameter("list", NUMBER_LIST))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("mean", new BuiltinFunctionType(NUMBER, new FormalParameter("n1", NUMBER), new FormalParameter("ns", NUMBER, false, true))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("and", new BuiltinFunctionType(BOOLEAN, new FormalParameter("list", BOOLEAN_LIST))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("and", new BuiltinFunctionType(BOOLEAN, new FormalParameter("b1", BOOLEAN), new FormalParameter("bs", BOOLEAN, false, true))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("all", new BuiltinFunctionType(BOOLEAN, new FormalParameter("list", BOOLEAN_LIST))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("all", new BuiltinFunctionType(BOOLEAN, new FormalParameter("b1", BOOLEAN), new FormalParameter("bs", BOOLEAN, false, true))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("or", new BuiltinFunctionType(BOOLEAN, new FormalParameter("list", BOOLEAN_LIST))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("or", new BuiltinFunctionType(BOOLEAN, new FormalParameter("b1", BOOLEAN), new FormalParameter("bs", BOOLEAN, false, true))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("any", new BuiltinFunctionType(BOOLEAN, new FormalParameter("list", BOOLEAN_LIST))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("any", new BuiltinFunctionType(BOOLEAN, new FormalParameter("b1", BOOLEAN), new FormalParameter("bs", BOOLEAN, false, true))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("sublist", new BuiltinFunctionType(ANY_LIST, new FormalParameter("list", ANY_LIST), new FormalParameter("start position", NUMBER), new FormalParameter("length", NUMBER, true, false))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("append", new BuiltinFunctionType(ANY_LIST, new FormalParameter("list", ANY_LIST), new FormalParameter("item", ANY, false, true))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("concatenate", new BuiltinFunctionType(ANY_LIST, new FormalParameter("list1", ANY_LIST, false, true))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("insert before", new BuiltinFunctionType(ANY_LIST, new FormalParameter("list", ANY_LIST), new FormalParameter("position", NUMBER), new FormalParameter("new item", ANY))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("remove", new BuiltinFunctionType(ANY_LIST, new FormalParameter("list", ANY_LIST), new FormalParameter("position", NUMBER))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("reverse", new BuiltinFunctionType(ANY_LIST, new FormalParameter("list", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("index of", new BuiltinFunctionType(ANY_LIST, new FormalParameter("list", ANY_LIST), new FormalParameter("match", ANY))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("distinct values", new BuiltinFunctionType(ANY_LIST, new FormalParameter("list", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("union", new BuiltinFunctionType(ANY_LIST, new FormalParameter("list1", ANY_LIST), new FormalParameter("list2", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("flatten", new BuiltinFunctionType(ANY_LIST, new FormalParameter("list", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("product", new BuiltinFunctionType(NUMBER, new FormalParameter("list", NUMBER_LIST))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("product", new BuiltinFunctionType(NUMBER, new FormalParameter("n1", NUMBER), new FormalParameter("ns", NUMBER, false, true))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("median", new BuiltinFunctionType(NUMBER, new FormalParameter("list", NUMBER_LIST))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("median", new BuiltinFunctionType(NUMBER, new FormalParameter("n1", NUMBER), new FormalParameter("ns", NUMBER, false, true))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("stddev", new BuiltinFunctionType(NUMBER, new FormalParameter("list", NUMBER_LIST))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("stddev", new BuiltinFunctionType(NUMBER, new FormalParameter("n1", NUMBER), new FormalParameter("ns", NUMBER, false, true))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("mode", new BuiltinFunctionType(NUMBER, new FormalParameter("list", NUMBER_LIST))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("mode", new BuiltinFunctionType(NUMBER, new FormalParameter("n1", NUMBER), new FormalParameter("ns", NUMBER, false, true))));

        environment.addDeclaration(INSTANCE.makeVariableDeclaration("sort", new BuiltinFunctionType(ANY_LIST, new FormalParameter("list", ANY_LIST), new FormalParameter("function", ANY))));
    }

    private static void addContextFunctions(Environment environment) {
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("get entries", new BuiltinFunctionType(CONTEXT_LIST, new FormalParameter("m", ContextType.ANY_CONTEXT))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("get value", new BuiltinFunctionType(ANY, new FormalParameter("m", ContextType.ANY_CONTEXT), new FormalParameter("key", STRING))));
    }

    private static void addDateTimeFunctions(Environment environment) {
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("is", new BuiltinFunctionType(BOOLEAN, new FormalParameter("value1", ANY), new FormalParameter("value2", ANY))));
    }

    private static void addTemporalFunctions(Environment environment) {
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("day of year", new BuiltinFunctionType(NUMBER, new FormalParameter("date", DATE))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("day of year", new BuiltinFunctionType(NUMBER, new FormalParameter("date", DATE_AND_TIME))));

        environment.addDeclaration(INSTANCE.makeVariableDeclaration("day of week", new BuiltinFunctionType(STRING, new FormalParameter("date", DATE))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("day of week", new BuiltinFunctionType(STRING, new FormalParameter("date", DATE_AND_TIME))));

        environment.addDeclaration(INSTANCE.makeVariableDeclaration("month of year", new BuiltinFunctionType(STRING, new FormalParameter("date", DATE))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("month of year", new BuiltinFunctionType(STRING, new FormalParameter("date", DATE_AND_TIME))));

        environment.addDeclaration(INSTANCE.makeVariableDeclaration("week of year", new BuiltinFunctionType(NUMBER, new FormalParameter("date", DATE))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("week of year", new BuiltinFunctionType(NUMBER, new FormalParameter("date", DATE_AND_TIME))));
    }

    private static void addRangeFunctions(Environment environment) {
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("before", new BuiltinFunctionType(BOOLEAN, new FormalParameter("point1", COMPARABLE), new FormalParameter("point2", COMPARABLE))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("before", new BuiltinFunctionType(BOOLEAN, new FormalParameter("point", COMPARABLE), new FormalParameter("range", COMPARABLE_RANGE))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("before", new BuiltinFunctionType(BOOLEAN, new FormalParameter("range", COMPARABLE_RANGE), new FormalParameter("point", COMPARABLE))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("before", new BuiltinFunctionType(BOOLEAN, new FormalParameter("range1", COMPARABLE_RANGE), new FormalParameter("range2", COMPARABLE_RANGE))));

        environment.addDeclaration(INSTANCE.makeVariableDeclaration("after", new BuiltinFunctionType(BOOLEAN, new FormalParameter("point1", COMPARABLE), new FormalParameter("point2", COMPARABLE))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("after", new BuiltinFunctionType(BOOLEAN, new FormalParameter("point", COMPARABLE), new FormalParameter("range", COMPARABLE_RANGE))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("after", new BuiltinFunctionType(BOOLEAN, new FormalParameter("range", COMPARABLE_RANGE), new FormalParameter("point", COMPARABLE))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("after", new BuiltinFunctionType(BOOLEAN, new FormalParameter("range1", COMPARABLE_RANGE), new FormalParameter("range2", COMPARABLE_RANGE))));

        environment.addDeclaration(INSTANCE.makeVariableDeclaration("meets", new BuiltinFunctionType(BOOLEAN, new FormalParameter("range1", COMPARABLE_RANGE), new FormalParameter("range2", COMPARABLE_RANGE))));

        environment.addDeclaration(INSTANCE.makeVariableDeclaration("met by", new BuiltinFunctionType(BOOLEAN, new FormalParameter("range1", COMPARABLE_RANGE), new FormalParameter("range2", COMPARABLE_RANGE))));

        environment.addDeclaration(INSTANCE.makeVariableDeclaration("overlaps", new BuiltinFunctionType(BOOLEAN, new FormalParameter("range1", COMPARABLE_RANGE), new FormalParameter("range2", COMPARABLE_RANGE))));

        environment.addDeclaration(INSTANCE.makeVariableDeclaration("overlaps before", new BuiltinFunctionType(BOOLEAN, new FormalParameter("range1", COMPARABLE_RANGE), new FormalParameter("range2", COMPARABLE_RANGE))));

        environment.addDeclaration(INSTANCE.makeVariableDeclaration("overlaps after", new BuiltinFunctionType(BOOLEAN, new FormalParameter("range1", COMPARABLE_RANGE), new FormalParameter("range2", COMPARABLE_RANGE))));

        environment.addDeclaration(INSTANCE.makeVariableDeclaration("finishes", new BuiltinFunctionType(BOOLEAN, new FormalParameter("point", COMPARABLE), new FormalParameter("range", COMPARABLE_RANGE))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("finishes", new BuiltinFunctionType(BOOLEAN, new FormalParameter("range1", COMPARABLE_RANGE), new FormalParameter("range2", COMPARABLE_RANGE))));

        environment.addDeclaration(INSTANCE.makeVariableDeclaration("finished by", new BuiltinFunctionType(BOOLEAN, new FormalParameter("range", COMPARABLE_RANGE), new FormalParameter("point", COMPARABLE))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("finished by", new BuiltinFunctionType(BOOLEAN, new FormalParameter("range1", COMPARABLE_RANGE), new FormalParameter("range2", COMPARABLE_RANGE))));

        environment.addDeclaration(INSTANCE.makeVariableDeclaration("includes", new BuiltinFunctionType(BOOLEAN, new FormalParameter("range", COMPARABLE_RANGE), new FormalParameter("point", COMPARABLE))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("includes", new BuiltinFunctionType(BOOLEAN, new FormalParameter("range1", COMPARABLE_RANGE), new FormalParameter("range2", COMPARABLE_RANGE))));

        environment.addDeclaration(INSTANCE.makeVariableDeclaration("during", new BuiltinFunctionType(BOOLEAN, new FormalParameter("point", COMPARABLE), new FormalParameter("range", COMPARABLE_RANGE))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("during", new BuiltinFunctionType(BOOLEAN, new FormalParameter("range1", COMPARABLE_RANGE), new FormalParameter("range2", COMPARABLE_RANGE))));

        environment.addDeclaration(INSTANCE.makeVariableDeclaration("starts", new BuiltinFunctionType(BOOLEAN, new FormalParameter("point", COMPARABLE), new FormalParameter("range", COMPARABLE_RANGE))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("starts", new BuiltinFunctionType(BOOLEAN, new FormalParameter("range1", COMPARABLE_RANGE), new FormalParameter("range2", COMPARABLE_RANGE))));

        environment.addDeclaration(INSTANCE.makeVariableDeclaration("started by", new BuiltinFunctionType(BOOLEAN, new FormalParameter("range", COMPARABLE_RANGE), new FormalParameter("point", COMPARABLE))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("started by", new BuiltinFunctionType(BOOLEAN, new FormalParameter("range1", COMPARABLE_RANGE), new FormalParameter("range2", COMPARABLE_RANGE))));

        environment.addDeclaration(INSTANCE.makeVariableDeclaration("coincides", new BuiltinFunctionType(BOOLEAN, new FormalParameter("point1", COMPARABLE), new FormalParameter("point2", COMPARABLE))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("coincides", new BuiltinFunctionType(BOOLEAN, new FormalParameter("range1", COMPARABLE_RANGE), new FormalParameter("range2", COMPARABLE_RANGE))));
    }
}
