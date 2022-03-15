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

import com.gs.dmn.context.DMNContext;
import com.gs.dmn.context.DMNContextKind;
import com.gs.dmn.context.environment.Declaration;
import com.gs.dmn.context.environment.Environment;
import com.gs.dmn.context.environment.EnvironmentFactory;
import com.gs.dmn.context.environment.RuntimeEnvironment;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.semantics.type.BuiltinFunctionType;
import com.gs.dmn.feel.analysis.semantics.type.ContextType;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter;
import com.gs.dmn.runtime.function.BuiltinFunction;

import java.util.List;
import java.util.Map;

import static com.gs.dmn.el.analysis.semantics.type.AnyType.ANY;
import static com.gs.dmn.feel.analysis.semantics.type.BooleanType.BOOLEAN;
import static com.gs.dmn.feel.analysis.semantics.type.ComparableDataType.COMPARABLE;
import static com.gs.dmn.feel.analysis.semantics.type.DateTimeType.DATE_AND_TIME;
import static com.gs.dmn.feel.analysis.semantics.type.DateType.DATE;
import static com.gs.dmn.feel.analysis.semantics.type.DurationType.*;
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
        for (Map.Entry<String, List<Declaration>> entry: environment.getVariablesTable().entrySet()) {
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
        return new BuiltinFunctionType(listType, new FormalParameter<>("list", listType), new FormalParameter<>("'start position'", NUMBER), new FormalParameter<>("length", NUMBER, true, false));
    }

    public static BuiltinFunctionType makeAppendBuiltinFunctionType(Type listType, Type itemType) {
        return new BuiltinFunctionType(listType, new FormalParameter<>("list", listType), new FormalParameter<>("item", itemType, false, true));
    }

    public static BuiltinFunctionType makeConcatenateBuiltinFunctionType(Type listType) {
        return new BuiltinFunctionType(listType, new FormalParameter<>("list1", listType, false, true));
    }

    public static BuiltinFunctionType makeInsertBeforeBuiltinFunctionType(Type listType, Type itemType) {
        return new BuiltinFunctionType(listType, new FormalParameter<>("list", listType), new FormalParameter<>("position", NUMBER), new FormalParameter<>("'new item'", itemType));
    }

    public static BuiltinFunctionType makeRemoveBuiltinFunctionType(Type listType) {
        return new BuiltinFunctionType(listType, new FormalParameter<>("list", listType), new FormalParameter<>("position", NUMBER));
    }

    public static BuiltinFunctionType makeReverseBuiltinFunctionType(Type listType) {
        return new BuiltinFunctionType(listType, new FormalParameter<>("list", listType));
    }

    public static BuiltinFunctionType makeIndexOfBuiltinFunctionType(Type listType, Type matchType) {
        return new BuiltinFunctionType(NUMBER_LIST, new FormalParameter<>("list", listType), new FormalParameter<>("match", matchType));
    }

    public static BuiltinFunctionType makeDistinctValuesBuiltinFunctionType(Type listType) {
        return new BuiltinFunctionType(listType, new FormalParameter<>("list", listType));
    }

    public static BuiltinFunctionType makeUnionBuiltinFunctionType(Type listType) {
        return new BuiltinFunctionType(listType, new FormalParameter<>("list1", listType), new FormalParameter<>("list2", listType));
    }

    public static BuiltinFunctionType makeFlattenBuiltinFunctionType(Type listType) {
        return new BuiltinFunctionType(listType, new FormalParameter<>("list", ANY_LIST));
    }

    public static BuiltinFunctionType makeSortBuiltinFunctionType(Type listType, Type functionType) {
        return new BuiltinFunctionType(listType, new FormalParameter<>("list", listType), new FormalParameter<>("function", functionType));
    }

    // Signavio
    public static BuiltinFunctionType makeSignavioAppendBuiltinFunctionType(Type listType, Type elementType) {
        return new BuiltinFunctionType(listType, new FormalParameter<>("list", listType), new FormalParameter<>("element", elementType));
    }

    public static BuiltinFunctionType makeSignavioAppendAllBuiltinFunctionType(Type listType) {
        return new BuiltinFunctionType(listType, new FormalParameter<>("list1", listType), new FormalParameter<>("list2", listType));
    }

    public static BuiltinFunctionType makeSignavioRemoveBuiltinFunctionType(Type listType, Type elementType) {
        return new BuiltinFunctionType(listType, new FormalParameter<>("list", listType), new FormalParameter<>("element", elementType));
    }

    public static BuiltinFunctionType makeSignavioRemoveAllBuiltinFunctionType(Type listType) {
        return new BuiltinFunctionType(listType, new FormalParameter<>("list1", listType), new FormalParameter<>("list2", listType));
    }

    public static BuiltinFunctionType makeSignavioZipBuiltinFunctionType(Type resultType, Type attributesType, Type valuesType) {
        return new BuiltinFunctionType(resultType, new FormalParameter<>("attributes", attributesType), new FormalParameter<>("values", valuesType));
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
        addFunctionDeclaration(environment, "date", new BuiltinFunctionType(DATE, new FormalParameter<>("from", STRING)));
        addFunctionDeclaration(environment, "date", new BuiltinFunctionType(DATE, new FormalParameter<>("from", DATE)));
        addFunctionDeclaration(environment, "date", new BuiltinFunctionType(DATE, new FormalParameter<>("from", DATE_AND_TIME)));
        addFunctionDeclaration(environment, "date", new BuiltinFunctionType(DATE, new FormalParameter<>("year", NUMBER), new FormalParameter<>("month", NUMBER), new FormalParameter<>("day", NUMBER)));
        addFunctionDeclaration(environment, "date and time", new BuiltinFunctionType(DATE_AND_TIME, new FormalParameter<>("date", DATE), new FormalParameter<>("time", TIME)));
        addFunctionDeclaration(environment, "date and time", new BuiltinFunctionType(DATE_AND_TIME, new FormalParameter<>("date", DATE_AND_TIME), new FormalParameter<>("time", TIME)));
        addFunctionDeclaration(environment, "date and time", new BuiltinFunctionType(DATE_AND_TIME, new FormalParameter<>("from", STRING)));
        addFunctionDeclaration(environment, "time", new BuiltinFunctionType(TIME, new FormalParameter<>("from", STRING)));
        addFunctionDeclaration(environment, "time", new BuiltinFunctionType(TIME, new FormalParameter<>("from", DATE)));
        addFunctionDeclaration(environment, "time", new BuiltinFunctionType(TIME, new FormalParameter<>("from", DATE_AND_TIME)));
        addFunctionDeclaration(environment, "time", new BuiltinFunctionType(TIME, new FormalParameter<>("hour", NUMBER), new FormalParameter<>("minute", NUMBER), new FormalParameter<>("second", NUMBER), new FormalParameter<>("offset", DAYS_AND_TIME_DURATION, true, false)));
        addFunctionDeclaration(environment, "number", new BuiltinFunctionType(NUMBER, new FormalParameter<>("from", STRING), new FormalParameter<>("'grouping separator'", STRING), new FormalParameter<>("'decimal separator'", STRING)));
        addFunctionDeclaration(environment, "number", new BuiltinFunctionType(NUMBER, new FormalParameter<>("from", STRING), new FormalParameter<>("groupingSeparator", STRING), new FormalParameter<>("decimalSeparator", STRING)));
        addFunctionDeclaration(environment, "string", new BuiltinFunctionType(STRING, new FormalParameter<>("from", ANY)));
        addFunctionDeclaration(environment, "duration", new BuiltinFunctionType(ANY_DURATION, new FormalParameter<>("from", STRING)));
        addFunctionDeclaration(environment, "years and months duration", new BuiltinFunctionType(YEARS_AND_MONTHS_DURATION, new FormalParameter<>("from", DATE), new FormalParameter<>("to", DATE)));
        addFunctionDeclaration(environment, "years and months duration", new BuiltinFunctionType(YEARS_AND_MONTHS_DURATION, new FormalParameter<>("from", DATE_AND_TIME), new FormalParameter<>("to", DATE_AND_TIME)));
        addFunctionDeclaration(environment, "years and months duration", new BuiltinFunctionType(YEARS_AND_MONTHS_DURATION, new FormalParameter<>("from", DATE), new FormalParameter<>("to", DATE_AND_TIME)));
        addFunctionDeclaration(environment, "years and months duration", new BuiltinFunctionType(YEARS_AND_MONTHS_DURATION, new FormalParameter<>("from", DATE_AND_TIME), new FormalParameter<>("to", DATE)));
    }

    private static void addNumberFunctions(Environment environment) {
        addFunctionDeclaration(environment, "decimal", new BuiltinFunctionType(NUMBER, new FormalParameter<>("n", NUMBER), new FormalParameter<>("scale", NUMBER)));
        addFunctionDeclaration(environment, "round", new BuiltinFunctionType(NUMBER, new FormalParameter<>("n", NUMBER), new FormalParameter<>("scale", NUMBER), new FormalParameter<>("mode", STRING)));
        addFunctionDeclaration(environment, "round up", new BuiltinFunctionType(NUMBER, new FormalParameter<>("n", NUMBER), new FormalParameter<>("scale", NUMBER)));
        addFunctionDeclaration(environment, "round down", new BuiltinFunctionType(NUMBER, new FormalParameter<>("n", NUMBER), new FormalParameter<>("scale", NUMBER)));
        addFunctionDeclaration(environment, "round half up", new BuiltinFunctionType(NUMBER, new FormalParameter<>("n", NUMBER), new FormalParameter<>("scale", NUMBER)));
        addFunctionDeclaration(environment, "round half down", new BuiltinFunctionType(NUMBER, new FormalParameter<>("n", NUMBER), new FormalParameter<>("scale", NUMBER)));
        addFunctionDeclaration(environment, "floor", new BuiltinFunctionType(NUMBER, new FormalParameter<>("n", NUMBER)));
        addFunctionDeclaration(environment, "floor", new BuiltinFunctionType(NUMBER, new FormalParameter<>("n", NUMBER), new FormalParameter<>("scale", NUMBER)));
        addFunctionDeclaration(environment, "ceiling", new BuiltinFunctionType(NUMBER, new FormalParameter<>("n", NUMBER)));
        addFunctionDeclaration(environment, "ceiling", new BuiltinFunctionType(NUMBER, new FormalParameter<>("n", NUMBER), new FormalParameter<>("scale", NUMBER)));
        addFunctionDeclaration(environment, "abs", new BuiltinFunctionType(NUMBER, new FormalParameter<>("n", NUMBER)));
        addFunctionDeclaration(environment, "abs", new BuiltinFunctionType(YEARS_AND_MONTHS_DURATION, new FormalParameter<>("n", YEARS_AND_MONTHS_DURATION)));
        addFunctionDeclaration(environment, "abs", new BuiltinFunctionType(DAYS_AND_TIME_DURATION, new FormalParameter<>("n", DAYS_AND_TIME_DURATION)));
        addFunctionDeclaration(environment, "modulo", new BuiltinFunctionType(NUMBER, new FormalParameter<>("dividend", NUMBER), new FormalParameter<>("divisor", NUMBER)));
        addFunctionDeclaration(environment, "sqrt", new BuiltinFunctionType(NUMBER, new FormalParameter<>("number", NUMBER)));
        addFunctionDeclaration(environment, "log", new BuiltinFunctionType(NUMBER, new FormalParameter<>("number", NUMBER)));
        addFunctionDeclaration(environment, "exp", new BuiltinFunctionType(NUMBER, new FormalParameter<>("number", NUMBER)));
        addFunctionDeclaration(environment, "odd", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("number", NUMBER)));
        addFunctionDeclaration(environment, "even", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("number", NUMBER)));
    }

    private static void addBooleanFunctions(Environment environment) {
        addFunctionDeclaration(environment, "not", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("negand", BOOLEAN)));
    }

    private static void addStringFunctions(Environment environment) {
        addFunctionDeclaration(environment, "substring", new BuiltinFunctionType(STRING, new FormalParameter<>("string", STRING), new FormalParameter<>("'start position'", NUMBER), new FormalParameter<>("length", NUMBER, true, false)));
        addFunctionDeclaration(environment, "substring", new BuiltinFunctionType(STRING, new FormalParameter<>("string", STRING), new FormalParameter<>("startPosition", NUMBER), new FormalParameter<>("length", NUMBER, true, false)));
        addFunctionDeclaration(environment, "string length", new BuiltinFunctionType(NUMBER, new FormalParameter<>("string", STRING)));
        addFunctionDeclaration(environment, "upper case", new BuiltinFunctionType(STRING, new FormalParameter<>("string", STRING)));
        addFunctionDeclaration(environment, "lower case", new BuiltinFunctionType(STRING, new FormalParameter<>("string", STRING)));
        addFunctionDeclaration(environment, "substring before", new BuiltinFunctionType(STRING, new FormalParameter<>("string", STRING), new FormalParameter<>("match", STRING)));
        addFunctionDeclaration(environment, "substring after", new BuiltinFunctionType(STRING, new FormalParameter<>("string", STRING), new FormalParameter<>("match", STRING)));
        addFunctionDeclaration(environment, "replace", new BuiltinFunctionType(STRING, new FormalParameter<>("input", STRING), new FormalParameter<>("pattern", STRING), new FormalParameter<>("replacement", STRING), new FormalParameter<>("flags", STRING, true, false)));
        addFunctionDeclaration(environment, "contains", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("string", STRING), new FormalParameter<>("match", STRING)));
        addFunctionDeclaration(environment, "starts with", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("string", STRING), new FormalParameter<>("match", STRING)));
        addFunctionDeclaration(environment, "ends with", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("string", STRING), new FormalParameter<>("match", STRING)));
        addFunctionDeclaration(environment, "matches", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("input", STRING), new FormalParameter<>("pattern", STRING), new FormalParameter<>("flags", STRING, true, false)));
        addFunctionDeclaration(environment, "split", new BuiltinFunctionType(STRING_LIST, new FormalParameter<>("string", STRING), new FormalParameter<>("delimiter", STRING)));
    }

    private static void addListFunctions(Environment environment) {
        addFunctionDeclaration(environment, "list contains", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("list", ANY_LIST), new FormalParameter<>("element", ANY)));
        addFunctionDeclaration(environment, "count", new BuiltinFunctionType(NUMBER, new FormalParameter<>("list", ANY_LIST)));
        addFunctionDeclaration(environment, "min", new BuiltinFunctionType(NUMBER, new FormalParameter<>("list", COMPARABLE_LIST)));
        addFunctionDeclaration(environment, "min", new BuiltinFunctionType(NUMBER, new FormalParameter<>("c1", COMPARABLE), new FormalParameter<>("cs", COMPARABLE, false, true)));
        addFunctionDeclaration(environment, "max", new BuiltinFunctionType(NUMBER, new FormalParameter<>("list", COMPARABLE_LIST)));
        addFunctionDeclaration(environment, "max", new BuiltinFunctionType(NUMBER, new FormalParameter<>("c1", COMPARABLE), new FormalParameter<>("cs", COMPARABLE, false, true)));
        addFunctionDeclaration(environment, "sum", new BuiltinFunctionType(NUMBER, new FormalParameter<>("list", NUMBER_LIST)));
        addFunctionDeclaration(environment, "sum", new BuiltinFunctionType(NUMBER, new FormalParameter<>("n1", NUMBER), new FormalParameter<>("ns", NUMBER, false, true)));
        addFunctionDeclaration(environment, "mean", new BuiltinFunctionType(NUMBER, new FormalParameter<>("list", NUMBER_LIST)));
        addFunctionDeclaration(environment, "mean", new BuiltinFunctionType(NUMBER, new FormalParameter<>("n1", NUMBER), new FormalParameter<>("ns", NUMBER, false, true)));
        addFunctionDeclaration(environment, "and", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("list", BOOLEAN_LIST)));
        addFunctionDeclaration(environment, "and", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("b1", BOOLEAN), new FormalParameter<>("bs", BOOLEAN, false, true)));
        addFunctionDeclaration(environment, "all", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("list", BOOLEAN_LIST)));
        addFunctionDeclaration(environment, "all", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("b1", BOOLEAN), new FormalParameter<>("bs", BOOLEAN, false, true)));
        addFunctionDeclaration(environment, "or", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("list", BOOLEAN_LIST)));
        addFunctionDeclaration(environment, "or", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("b1", BOOLEAN), new FormalParameter<>("bs", BOOLEAN, false, true)));
        addFunctionDeclaration(environment, "any", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("list", BOOLEAN_LIST)));
        addFunctionDeclaration(environment, "any", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("b1", BOOLEAN), new FormalParameter<>("bs", BOOLEAN, false, true)));
        addFunctionDeclaration(environment, "sublist", makeSublistBuiltInFunctionType(ANY_LIST));
        addFunctionDeclaration(environment, "append", makeAppendBuiltinFunctionType(ANY_LIST, ANY));
        addFunctionDeclaration(environment, "concatenate", makeConcatenateBuiltinFunctionType(ANY_LIST));
        addFunctionDeclaration(environment, "insert before", makeInsertBeforeBuiltinFunctionType(ANY_LIST, ANY));
        addFunctionDeclaration(environment, "remove", makeRemoveBuiltinFunctionType(ANY_LIST));
        addFunctionDeclaration(environment, "reverse", makeReverseBuiltinFunctionType(ANY_LIST));
        addFunctionDeclaration(environment, "index of", makeIndexOfBuiltinFunctionType(ANY_LIST, ANY));
        addFunctionDeclaration(environment, "distinct values", makeDistinctValuesBuiltinFunctionType(ANY_LIST));
        addFunctionDeclaration(environment, "union", makeUnionBuiltinFunctionType(ANY_LIST));
        addFunctionDeclaration(environment, "flatten", makeFlattenBuiltinFunctionType(ANY_LIST));
        addFunctionDeclaration(environment, "product", new BuiltinFunctionType(NUMBER, new FormalParameter<>("list", NUMBER_LIST)));
        addFunctionDeclaration(environment, "product", new BuiltinFunctionType(NUMBER, new FormalParameter<>("n1", NUMBER), new FormalParameter<>("ns", NUMBER, false, true)));
        addFunctionDeclaration(environment, "median", new BuiltinFunctionType(NUMBER, new FormalParameter<>("list", NUMBER_LIST)));
        addFunctionDeclaration(environment, "median", new BuiltinFunctionType(NUMBER, new FormalParameter<>("n1", NUMBER), new FormalParameter<>("ns", NUMBER, false, true)));
        addFunctionDeclaration(environment, "stddev", new BuiltinFunctionType(NUMBER, new FormalParameter<>("list", NUMBER_LIST)));
        addFunctionDeclaration(environment, "stddev", new BuiltinFunctionType(NUMBER, new FormalParameter<>("n1", NUMBER), new FormalParameter<>("ns", NUMBER, false, true)));
        addFunctionDeclaration(environment, "mode", new BuiltinFunctionType(NUMBER, new FormalParameter<>("list", NUMBER_LIST)));
        addFunctionDeclaration(environment, "mode", new BuiltinFunctionType(NUMBER, new FormalParameter<>("n1", NUMBER), new FormalParameter<>("ns", NUMBER, false, true)));

        addFunctionDeclaration(environment, "sort", makeSortBuiltinFunctionType(ANY_LIST, ANY));
    }

    private static void addContextFunctions(Environment environment) {
        addFunctionDeclaration(environment, "get entries", new BuiltinFunctionType(CONTEXT_LIST, new FormalParameter<>("m", ContextType.ANY_CONTEXT)));
        addFunctionDeclaration(environment, "get value", new BuiltinFunctionType(ANY, new FormalParameter<>("m", ContextType.ANY_CONTEXT), new FormalParameter<>("key", STRING)));
    }

    private static void addDateTimeFunctions(Environment environment) {
        addFunctionDeclaration(environment, "is", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("value1", ANY), new FormalParameter<>("value2", ANY)));
    }

    private static void addTemporalFunctions(Environment environment) {
        addFunctionDeclaration(environment, "day of year", new BuiltinFunctionType(NUMBER, new FormalParameter<>("date", DATE)));
        addFunctionDeclaration(environment, "day of year", new BuiltinFunctionType(NUMBER, new FormalParameter<>("date", DATE_AND_TIME)));

        addFunctionDeclaration(environment, "day of week", new BuiltinFunctionType(STRING, new FormalParameter<>("date", DATE)));
        addFunctionDeclaration(environment, "day of week", new BuiltinFunctionType(STRING, new FormalParameter<>("date", DATE_AND_TIME)));

        addFunctionDeclaration(environment, "month of year", new BuiltinFunctionType(STRING, new FormalParameter<>("date", DATE)));
        addFunctionDeclaration(environment, "month of year", new BuiltinFunctionType(STRING, new FormalParameter<>("date", DATE_AND_TIME)));

        addFunctionDeclaration(environment, "week of year", new BuiltinFunctionType(NUMBER, new FormalParameter<>("date", DATE)));
        addFunctionDeclaration(environment, "week of year", new BuiltinFunctionType(NUMBER, new FormalParameter<>("date", DATE_AND_TIME)));
    }

    private static void addRangeFunctions(Environment environment) {
        addFunctionDeclaration(environment, "before", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("point1", COMPARABLE), new FormalParameter<>("point2", COMPARABLE)));
        addFunctionDeclaration(environment, "before", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("point", COMPARABLE), new FormalParameter<>("range", COMPARABLE_RANGE)));
        addFunctionDeclaration(environment, "before", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("range", COMPARABLE_RANGE), new FormalParameter<>("point", COMPARABLE)));
        addFunctionDeclaration(environment, "before", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("range1", COMPARABLE_RANGE), new FormalParameter<>("range2", COMPARABLE_RANGE)));

        addFunctionDeclaration(environment, "after", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("point1", COMPARABLE), new FormalParameter<>("point2", COMPARABLE)));
        addFunctionDeclaration(environment, "after", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("point", COMPARABLE), new FormalParameter<>("range", COMPARABLE_RANGE)));
        addFunctionDeclaration(environment, "after", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("range", COMPARABLE_RANGE), new FormalParameter<>("point", COMPARABLE)));
        addFunctionDeclaration(environment, "after", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("range1", COMPARABLE_RANGE), new FormalParameter<>("range2", COMPARABLE_RANGE)));

        addFunctionDeclaration(environment, "meets", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("range1", COMPARABLE_RANGE), new FormalParameter<>("range2", COMPARABLE_RANGE)));

        addFunctionDeclaration(environment, "met by", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("range1", COMPARABLE_RANGE), new FormalParameter<>("range2", COMPARABLE_RANGE)));

        addFunctionDeclaration(environment, "overlaps", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("range1", COMPARABLE_RANGE), new FormalParameter<>("range2", COMPARABLE_RANGE)));

        addFunctionDeclaration(environment, "overlaps before", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("range1", COMPARABLE_RANGE), new FormalParameter<>("range2", COMPARABLE_RANGE)));

        addFunctionDeclaration(environment, "overlaps after", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("range1", COMPARABLE_RANGE), new FormalParameter<>("range2", COMPARABLE_RANGE)));

        addFunctionDeclaration(environment, "finishes", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("point", COMPARABLE), new FormalParameter<>("range", COMPARABLE_RANGE)));
        addFunctionDeclaration(environment, "finishes", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("range1", COMPARABLE_RANGE), new FormalParameter<>("range2", COMPARABLE_RANGE)));

        addFunctionDeclaration(environment, "finished by", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("range", COMPARABLE_RANGE), new FormalParameter<>("point", COMPARABLE)));
        addFunctionDeclaration(environment, "finished by", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("range1", COMPARABLE_RANGE), new FormalParameter<>("range2", COMPARABLE_RANGE)));

        addFunctionDeclaration(environment, "includes", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("range", COMPARABLE_RANGE), new FormalParameter<>("point", COMPARABLE)));
        addFunctionDeclaration(environment, "includes", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("range1", COMPARABLE_RANGE), new FormalParameter<>("range2", COMPARABLE_RANGE)));

        addFunctionDeclaration(environment, "during", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("point", COMPARABLE), new FormalParameter<>("range", COMPARABLE_RANGE)));
        addFunctionDeclaration(environment, "during", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("range1", COMPARABLE_RANGE), new FormalParameter<>("range2", COMPARABLE_RANGE)));

        addFunctionDeclaration(environment, "starts", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("point", COMPARABLE), new FormalParameter<>("range", COMPARABLE_RANGE)));
        addFunctionDeclaration(environment, "starts", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("range1", COMPARABLE_RANGE), new FormalParameter<>("range2", COMPARABLE_RANGE)));

        addFunctionDeclaration(environment, "started by", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("range", COMPARABLE_RANGE), new FormalParameter<>("point", COMPARABLE)));
        addFunctionDeclaration(environment, "started by", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("range1", COMPARABLE_RANGE), new FormalParameter<>("range2", COMPARABLE_RANGE)));

        addFunctionDeclaration(environment, "coincides", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("point1", COMPARABLE), new FormalParameter<>("point2", COMPARABLE)));
        addFunctionDeclaration(environment, "coincides", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("range1", COMPARABLE_RANGE), new FormalParameter<>("range2", COMPARABLE_RANGE)));
    }

    private static void addFunctionDeclaration(Environment environment, String name, BuiltinFunctionType functionType) {
        environment.addDeclaration(INSTANCE.makeVariableDeclaration(name, functionType));
    }
}
