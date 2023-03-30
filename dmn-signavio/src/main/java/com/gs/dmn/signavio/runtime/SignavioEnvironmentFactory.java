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

import com.gs.dmn.context.DMNContext;
import com.gs.dmn.context.DMNContextKind;
import com.gs.dmn.context.environment.Declaration;
import com.gs.dmn.context.environment.Environment;
import com.gs.dmn.context.environment.EnvironmentFactory;
import com.gs.dmn.context.environment.RuntimeEnvironment;
import com.gs.dmn.feel.analysis.semantics.environment.StandardEnvironmentFactory;
import com.gs.dmn.feel.analysis.semantics.type.BuiltinFunctionType;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter;
import com.gs.dmn.runtime.function.BuiltinFunction;

import java.util.List;
import java.util.Map;

import static com.gs.dmn.el.analysis.semantics.type.AnyType.ANY;
import static com.gs.dmn.feel.analysis.semantics.type.BooleanType.BOOLEAN;
import static com.gs.dmn.feel.analysis.semantics.type.DateTimeType.DATE_AND_TIME;
import static com.gs.dmn.feel.analysis.semantics.type.DateType.DATE;
import static com.gs.dmn.feel.analysis.semantics.type.ListType.ANY_LIST;
import static com.gs.dmn.feel.analysis.semantics.type.NumberType.NUMBER;
import static com.gs.dmn.feel.analysis.semantics.type.StringType.STRING;
import static com.gs.dmn.feel.analysis.semantics.type.TemporalType.TEMPORAL;
import static com.gs.dmn.feel.analysis.semantics.type.TimeType.TIME;

public class SignavioEnvironmentFactory implements EnvironmentFactory {
    private static final EnvironmentFactory INSTANCE = new SignavioEnvironmentFactory();
    private static final DMNContext BUILT_IN_CONTEXT;

    static {
        Environment environment = INSTANCE.emptyEnvironment();
        RuntimeEnvironment runtimeEnvironment = RuntimeEnvironment.of();
        addSignavioFunctions(environment);
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

    @Override
    public DMNContext getBuiltInContext() {
        return SignavioEnvironmentFactory.BUILT_IN_CONTEXT;
    }

    private static void addSignavioFunctions(Environment environment) {
        // Data acceptance functions
        addDataAcceptanceFunctions(environment);
        // Conversion functions
        addConversionFunctions(environment);
        // Number functions
        addNumberFunctions(environment);
        // String functions
        addStringFunctions(environment);
        // Boolean functions
        addBooleanFunctions(environment);
        // Date time functions
        addDateTimeFunctions(environment);
        // List functions
        addListFunctions(environment);
        // Statistical operations
        addStatisticalFunctions(environment);
    }

    private static void addDataAcceptanceFunctions(Environment environment) {
        addFunctionDeclaration(environment, "isDefined", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("arg", ANY)));
        addFunctionDeclaration(environment, "isUndefined", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("arg", ANY)));
        addFunctionDeclaration(environment, "isValid", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("arg", ANY)));
        addFunctionDeclaration(environment, "isInvalid", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("arg", ANY)));
    }

    private static void addConversionFunctions(Environment environment) {
        addFunctionDeclaration(environment, "date", new BuiltinFunctionType(DATE, new FormalParameter<>("from", STRING)));
        addFunctionDeclaration(environment, "date and time", new BuiltinFunctionType(DATE_AND_TIME, new FormalParameter<>("from", STRING)));
        addFunctionDeclaration(environment, "time", new BuiltinFunctionType(TIME, new FormalParameter<>("from", STRING)));
        addFunctionDeclaration(environment, "string", new BuiltinFunctionType(STRING, new FormalParameter<>("from", ANY)));
    }

    private static void addNumberFunctions(Environment environment) {
        addFunctionDeclaration(environment, "abs", new BuiltinFunctionType(NUMBER, new FormalParameter<>("number", NUMBER)));
        addFunctionDeclaration(environment, "count", new BuiltinFunctionType(NUMBER, new FormalParameter<>("list", ANY_LIST)));
        addFunctionDeclaration(environment, "round", new BuiltinFunctionType(NUMBER, new FormalParameter<>("number", NUMBER), new FormalParameter<>("digits", NUMBER)));
        addFunctionDeclaration(environment, "ceiling", new BuiltinFunctionType(NUMBER, new FormalParameter<>("number", NUMBER)));
        addFunctionDeclaration(environment, "floor", new BuiltinFunctionType(NUMBER, new FormalParameter<>("number", NUMBER)));
        addFunctionDeclaration(environment, "integer", new BuiltinFunctionType(NUMBER, new FormalParameter<>("number", NUMBER)));
        addFunctionDeclaration(environment, "modulo", new BuiltinFunctionType(NUMBER, new FormalParameter<>("dividend", NUMBER), new FormalParameter<>("divisor", NUMBER)));
        addFunctionDeclaration(environment, "percent", new BuiltinFunctionType(NUMBER, new FormalParameter<>("number", NUMBER)));
        addFunctionDeclaration(environment, "power", new BuiltinFunctionType(NUMBER, new FormalParameter<>("base", NUMBER), new FormalParameter<>("exponent", NUMBER)));
        addFunctionDeclaration(environment, "product", new BuiltinFunctionType(NUMBER, new FormalParameter<>("list", ANY_LIST)));
        addFunctionDeclaration(environment, "roundDown", new BuiltinFunctionType(NUMBER, new FormalParameter<>("number", NUMBER), new FormalParameter<>("digits", NUMBER)));
        addFunctionDeclaration(environment, "roundUp", new BuiltinFunctionType(NUMBER, new FormalParameter<>("number", NUMBER), new FormalParameter<>("digits", NUMBER)));
        addFunctionDeclaration(environment, "sum", new BuiltinFunctionType(NUMBER, new FormalParameter<>("list", ANY_LIST)));
    }

    private static void addStringFunctions(Environment environment) {
        addFunctionDeclaration(environment, "concat", new BuiltinFunctionType(STRING, new FormalParameter<>("list", ANY_LIST)));
        addFunctionDeclaration(environment, "isAlpha", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("text", STRING)));
        addFunctionDeclaration(environment, "isAlphanumeric", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("text", STRING)));
        addFunctionDeclaration(environment, "isNumeric", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("text", STRING)));
        addFunctionDeclaration(environment, "isSpaces", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("text", STRING)));
        addFunctionDeclaration(environment, "len", new BuiltinFunctionType(NUMBER, new FormalParameter<>("text", STRING)));
        addFunctionDeclaration(environment, "lower", new BuiltinFunctionType(STRING, new FormalParameter<>("text", STRING)));
        addFunctionDeclaration(environment, "trim", new BuiltinFunctionType(STRING, new FormalParameter<>("text", STRING)));
        addFunctionDeclaration(environment, "upper", new BuiltinFunctionType(STRING, new FormalParameter<>("text", STRING)));
        addFunctionDeclaration(environment, "number", new BuiltinFunctionType(NUMBER, new FormalParameter<>("text", STRING), new FormalParameter<>("default_value", NUMBER, true, false)));
        addFunctionDeclaration(environment, "mid", new BuiltinFunctionType(STRING, new FormalParameter<>("text", STRING), new FormalParameter<>("start", NUMBER), new FormalParameter<>("num_chars", NUMBER)));
        addFunctionDeclaration(environment, "left", new BuiltinFunctionType(STRING, new FormalParameter<>("text", STRING), new FormalParameter<>("num_chars", NUMBER)));
        addFunctionDeclaration(environment, "right", new BuiltinFunctionType(STRING, new FormalParameter<>("text", STRING), new FormalParameter<>("num_chars", NUMBER)));
        addFunctionDeclaration(environment, "text", new BuiltinFunctionType(STRING, new FormalParameter<>("num", NUMBER), new FormalParameter<>("format_text", STRING)));
        addFunctionDeclaration(environment, "textOccurrences", new BuiltinFunctionType(NUMBER, new FormalParameter<>("find_text", STRING), new FormalParameter<>("within_text", STRING)));
        addFunctionDeclaration(environment, "contains", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("text", STRING), new FormalParameter<>("substring", STRING)));
        addFunctionDeclaration(environment, "startsWith", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("string", STRING), new FormalParameter<>("prefix", STRING)));
        addFunctionDeclaration(environment, "endsWith", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("string", STRING), new FormalParameter<>("suffix", STRING)));
    }

    private static void addBooleanFunctions(Environment environment) {
        addFunctionDeclaration(environment, "not", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("negand", BOOLEAN)));
    }

    private static void addDateTimeFunctions(Environment environment) {
        addFunctionDeclaration(environment, "day", new BuiltinFunctionType(NUMBER, new FormalParameter<>("datetime", DATE)));
        addFunctionDeclaration(environment, "day", new BuiltinFunctionType(NUMBER, new FormalParameter<>("datetime", DATE_AND_TIME)));
        addFunctionDeclaration(environment, "dayAdd", new BuiltinFunctionType(DATE, new FormalParameter<>("datetime", DATE), new FormalParameter<>("days_to_add", NUMBER)));
        addFunctionDeclaration(environment, "dayAdd", new BuiltinFunctionType(DATE_AND_TIME, new FormalParameter<>("datetime", DATE_AND_TIME), new FormalParameter<>("days_to_add", NUMBER)));
        addFunctionDeclaration(environment, "dayDiff", new BuiltinFunctionType(NUMBER, new FormalParameter<>("datetime1", DATE), new FormalParameter<>("datetime2", DATE)));
        addFunctionDeclaration(environment, "dayDiff", new BuiltinFunctionType(NUMBER, new FormalParameter<>("datetime1", DATE), new FormalParameter<>("datetime2", DATE_AND_TIME)));
        addFunctionDeclaration(environment, "dayDiff", new BuiltinFunctionType(NUMBER, new FormalParameter<>("datetime1", DATE_AND_TIME), new FormalParameter<>("datetime2", DATE)));
        addFunctionDeclaration(environment, "dayDiff", new BuiltinFunctionType(NUMBER, new FormalParameter<>("datetime1", DATE_AND_TIME), new FormalParameter<>("datetime2", DATE_AND_TIME)));
        addFunctionDeclaration(environment, "date", new BuiltinFunctionType(DATE, new FormalParameter<>("year", NUMBER), new FormalParameter<>("month", NUMBER), new FormalParameter<>("day", NUMBER)));
        addFunctionDeclaration(environment, "dateTime", new BuiltinFunctionType(DATE_AND_TIME, new FormalParameter<>("day", NUMBER), new FormalParameter<>("month", NUMBER), new FormalParameter<>("year", NUMBER), new FormalParameter<>("hour", NUMBER), new FormalParameter<>("minute", NUMBER), new FormalParameter<>("second", NUMBER), new FormalParameter<>("hourOffset", NUMBER, true, false)));
        addFunctionDeclaration(environment, "hour", new BuiltinFunctionType(NUMBER, new FormalParameter<>("datetime", TIME)));
        addFunctionDeclaration(environment, "hour", new BuiltinFunctionType(NUMBER, new FormalParameter<>("datetime", DATE_AND_TIME)));
        addFunctionDeclaration(environment, "hourDiff", new BuiltinFunctionType(NUMBER, new FormalParameter<>("datetime1", TEMPORAL), new FormalParameter<>("datetime2", TEMPORAL)));
        addFunctionDeclaration(environment, "minute", new BuiltinFunctionType(NUMBER, new FormalParameter<>("datetime", TIME)));
        addFunctionDeclaration(environment, "minute", new BuiltinFunctionType(NUMBER, new FormalParameter<>("datetime", DATE_AND_TIME)));
        addFunctionDeclaration(environment, "minutesDiff", new BuiltinFunctionType(NUMBER, new FormalParameter<>("datetime1", TEMPORAL), new FormalParameter<>("datetime2", TEMPORAL)));
        addFunctionDeclaration(environment, "month", new BuiltinFunctionType(NUMBER, new FormalParameter<>("datetime", DATE)));
        addFunctionDeclaration(environment, "month", new BuiltinFunctionType(NUMBER, new FormalParameter<>("datetime", DATE_AND_TIME)));
        addFunctionDeclaration(environment, "monthAdd", new BuiltinFunctionType(DATE, new FormalParameter<>("datetime", DATE), new FormalParameter<>("months_to_add", NUMBER)));
        addFunctionDeclaration(environment, "monthAdd", new BuiltinFunctionType(DATE_AND_TIME, new FormalParameter<>("datetime", DATE_AND_TIME), new FormalParameter<>("months_to_add", NUMBER)));
        addFunctionDeclaration(environment, "monthDiff", new BuiltinFunctionType(NUMBER, new FormalParameter<>("datetime1", DATE), new FormalParameter<>("datetime2", DATE)));
        addFunctionDeclaration(environment, "monthDiff", new BuiltinFunctionType(NUMBER, new FormalParameter<>("datetime1", DATE), new FormalParameter<>("datetime2", DATE_AND_TIME)));
        addFunctionDeclaration(environment, "monthDiff", new BuiltinFunctionType(NUMBER, new FormalParameter<>("datetime1", DATE_AND_TIME), new FormalParameter<>("datetime2", DATE)));
        addFunctionDeclaration(environment, "monthDiff", new BuiltinFunctionType(NUMBER, new FormalParameter<>("datetime1", DATE_AND_TIME), new FormalParameter<>("datetime2", DATE_AND_TIME)));
        addFunctionDeclaration(environment, "now", new BuiltinFunctionType(DATE_AND_TIME));
        addFunctionDeclaration(environment, "today", new BuiltinFunctionType(DATE));
        addFunctionDeclaration(environment, "weekday", new BuiltinFunctionType(NUMBER, new FormalParameter<>("datetime", DATE)));
        addFunctionDeclaration(environment, "weekday", new BuiltinFunctionType(NUMBER, new FormalParameter<>("datetime", DATE_AND_TIME)));
        addFunctionDeclaration(environment, "year", new BuiltinFunctionType(NUMBER, new FormalParameter<>("datetime", DATE)));
        addFunctionDeclaration(environment, "year", new BuiltinFunctionType(NUMBER, new FormalParameter<>("datetime", DATE_AND_TIME)));
        addFunctionDeclaration(environment, "yearAdd", new BuiltinFunctionType(DATE, new FormalParameter<>("datetime", DATE), new FormalParameter<>("years_to_add", NUMBER)));
        addFunctionDeclaration(environment, "yearAdd", new BuiltinFunctionType(DATE_AND_TIME, new FormalParameter<>("datetime", DATE_AND_TIME), new FormalParameter<>("years_to_add", NUMBER)));
        addFunctionDeclaration(environment, "yearDiff", new BuiltinFunctionType(NUMBER, new FormalParameter<>("datetime1", DATE), new FormalParameter<>("datetime2", DATE)));
        addFunctionDeclaration(environment, "yearDiff", new BuiltinFunctionType(NUMBER, new FormalParameter<>("datetime1", DATE), new FormalParameter<>("datetime2", DATE_AND_TIME)));
        addFunctionDeclaration(environment, "yearDiff", new BuiltinFunctionType(NUMBER, new FormalParameter<>("datetime1", DATE_AND_TIME), new FormalParameter<>("datetime2", DATE)));
        addFunctionDeclaration(environment, "yearDiff", new BuiltinFunctionType(NUMBER, new FormalParameter<>("datetime1", DATE_AND_TIME), new FormalParameter<>("datetime2", DATE_AND_TIME)));
    }

    private static void addListFunctions(Environment environment) {
        addFunctionDeclaration(environment, "append", StandardEnvironmentFactory.makeSignavioAppendBuiltinFunctionType(ANY_LIST, ANY));
        addFunctionDeclaration(environment, "appendAll", StandardEnvironmentFactory.makeSignavioAppendAllBuiltinFunctionType(ANY_LIST));
        addFunctionDeclaration(environment, "remove", StandardEnvironmentFactory.makeSignavioRemoveBuiltinFunctionType(ANY_LIST, ANY));
        addFunctionDeclaration(environment, "removeAll", StandardEnvironmentFactory.makeSignavioRemoveAllBuiltinFunctionType(ANY_LIST));
        addFunctionDeclaration(environment, "zip", StandardEnvironmentFactory.makeSignavioZipBuiltinFunctionType(ANY_LIST, ANY_LIST, ANY_LIST));
        addFunctionDeclaration(environment, "notContainsAny", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("list1", ANY_LIST), new FormalParameter<>("list2", ANY_LIST)));
        addFunctionDeclaration(environment, "containsOnly", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("list1", ANY_LIST), new FormalParameter<>("list2", ANY_LIST)));
        addFunctionDeclaration(environment, "areElementsOf", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("list1", ANY_LIST), new FormalParameter<>("list2", ANY_LIST)));
        addFunctionDeclaration(environment, "elementOf", new BuiltinFunctionType(BOOLEAN, new FormalParameter<>("list1", ANY), new FormalParameter<>("list2", ANY_LIST)));
    }

    private static void addStatisticalFunctions(Environment environment) {
        addFunctionDeclaration(environment, "avg", new BuiltinFunctionType(NUMBER, new FormalParameter<>("list", ANY_LIST)));
        addFunctionDeclaration(environment, "max", new BuiltinFunctionType(NUMBER, new FormalParameter<>("list", ANY_LIST)));
        addFunctionDeclaration(environment, "median", new BuiltinFunctionType(NUMBER, new FormalParameter<>("list", ANY_LIST)));
        addFunctionDeclaration(environment, "min", new BuiltinFunctionType(NUMBER, new FormalParameter<>("list", ANY_LIST)));
        addFunctionDeclaration(environment, "mode", new BuiltinFunctionType(NUMBER, new FormalParameter<>("list", ANY_LIST)));
    }

    private static void addFunctionDeclaration(Environment environment, String name, BuiltinFunctionType functionType) {
        environment.addDeclaration(INSTANCE.makeVariableDeclaration(name, functionType));
    }
}
