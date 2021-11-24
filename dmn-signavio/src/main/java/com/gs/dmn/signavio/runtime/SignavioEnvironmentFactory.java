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

import com.gs.dmn.feel.analysis.semantics.environment.Declaration;
import com.gs.dmn.feel.analysis.semantics.environment.Environment;
import com.gs.dmn.feel.analysis.semantics.environment.EnvironmentFactory;
import com.gs.dmn.feel.analysis.semantics.environment.StandardEnvironmentFactory;
import com.gs.dmn.feel.analysis.semantics.type.BuiltinFunctionType;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter;
import com.gs.dmn.runtime.DMNContext;
import com.gs.dmn.runtime.DMNContextKind;
import com.gs.dmn.runtime.function.BuiltinFunction;
import com.gs.dmn.runtime.interpreter.environment.RuntimeEnvironment;

import java.util.List;
import java.util.Map;

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
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("isDefined", new BuiltinFunctionType(BOOLEAN, new FormalParameter("", ANY))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("isUndefined", new BuiltinFunctionType(BOOLEAN, new FormalParameter("", ANY))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("isValid", new BuiltinFunctionType(BOOLEAN, new FormalParameter("", ANY))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("isInvalid", new BuiltinFunctionType(BOOLEAN, new FormalParameter("", ANY))));

        // Conversion functions
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("date", new BuiltinFunctionType(DATE, new FormalParameter("from", STRING))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("date and time", new BuiltinFunctionType(DATE_AND_TIME, new FormalParameter("from", STRING))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("time", new BuiltinFunctionType(TIME, new FormalParameter("from", STRING))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("string", new BuiltinFunctionType(STRING, new FormalParameter("from", ANY))));

        // Number functions
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("abs", new BuiltinFunctionType(NUMBER, new FormalParameter("number", NUMBER))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("count", new BuiltinFunctionType(NUMBER, new FormalParameter("list", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("round", new BuiltinFunctionType(NUMBER, new FormalParameter("number", NUMBER), new FormalParameter("digits", NUMBER))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("ceiling", new BuiltinFunctionType(NUMBER, new FormalParameter("n", NUMBER))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("floor", new BuiltinFunctionType(NUMBER, new FormalParameter("n", NUMBER))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("integer", new BuiltinFunctionType(NUMBER, new FormalParameter("number", NUMBER))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("modulo", new BuiltinFunctionType(NUMBER, new FormalParameter("divident", NUMBER), new FormalParameter("divisor", NUMBER))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("percent", new BuiltinFunctionType(NUMBER, new FormalParameter("number", NUMBER))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("power", new BuiltinFunctionType(NUMBER, new FormalParameter("base", NUMBER), new FormalParameter("exponent", NUMBER))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("product", new BuiltinFunctionType(NUMBER, new FormalParameter("list", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("roundDown", new BuiltinFunctionType(NUMBER, new FormalParameter("number", NUMBER), new FormalParameter("digits", NUMBER))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("roundUp", new BuiltinFunctionType(NUMBER, new FormalParameter("number", NUMBER), new FormalParameter("digits", NUMBER))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("sum", new BuiltinFunctionType(NUMBER, new FormalParameter("list", ANY_LIST))));

        // Date time functions
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("day", new BuiltinFunctionType(NUMBER, new FormalParameter("datetime", DATE))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("day", new BuiltinFunctionType(NUMBER, new FormalParameter("datetime", DATE_AND_TIME))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("dayAdd", new BuiltinFunctionType(DATE, new FormalParameter("datetime", DATE), new FormalParameter("days_to_add", NUMBER))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("dayAdd", new BuiltinFunctionType(DATE, new FormalParameter("datetime", DATE_AND_TIME), new FormalParameter("days_to_add", NUMBER))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("dayDiff", new BuiltinFunctionType(NUMBER, new FormalParameter("datetime1", DATE), new FormalParameter("datetime2", DATE))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("dayDiff", new BuiltinFunctionType(NUMBER, new FormalParameter("datetime1", DATE_AND_TIME), new FormalParameter("datetime2", DATE_AND_TIME))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("date", new BuiltinFunctionType(DATE, new FormalParameter("year", NUMBER), new FormalParameter("month", NUMBER), new FormalParameter("day", NUMBER))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("dateTime", new BuiltinFunctionType(DATE_AND_TIME, new FormalParameter("day", NUMBER), new FormalParameter("month", NUMBER), new FormalParameter("year", NUMBER), new FormalParameter("hour", NUMBER), new FormalParameter("minute", NUMBER), new FormalParameter("second", NUMBER), new FormalParameter("hourOffset", NUMBER, true, false))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("hour", new BuiltinFunctionType(NUMBER, new FormalParameter("datetime", TIME))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("hour", new BuiltinFunctionType(NUMBER, new FormalParameter("datetime", DATE_AND_TIME))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("hourDiff", new BuiltinFunctionType(NUMBER, new FormalParameter("datetime1", TIME), new FormalParameter("datetime2", TIME))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("hourDiff", new BuiltinFunctionType(NUMBER, new FormalParameter("datetime1", DATE_AND_TIME), new FormalParameter("datetime2", DATE_AND_TIME))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("minute", new BuiltinFunctionType(NUMBER, new FormalParameter("datetime", TIME))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("minute", new BuiltinFunctionType(NUMBER, new FormalParameter("datetime", DATE_AND_TIME))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("minutesDiff", new BuiltinFunctionType(NUMBER, new FormalParameter("datetime1", TIME), new FormalParameter("datetime2", TIME))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("minutesDiff", new BuiltinFunctionType(NUMBER, new FormalParameter("datetime1", DATE_AND_TIME), new FormalParameter("datetime2", DATE_AND_TIME))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("month", new BuiltinFunctionType(NUMBER, new FormalParameter("datetime", DATE))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("month", new BuiltinFunctionType(NUMBER, new FormalParameter("datetime", DATE_AND_TIME))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("monthAdd", new BuiltinFunctionType(DATE, new FormalParameter("datetime", DATE), new FormalParameter("months_to_add", NUMBER))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("monthAdd", new BuiltinFunctionType(DATE, new FormalParameter("datetime", DATE_AND_TIME), new FormalParameter("months_to_add", NUMBER))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("monthDiff", new BuiltinFunctionType(NUMBER, new FormalParameter("datetime1", DATE), new FormalParameter("datetime2", DATE))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("monthDiff", new BuiltinFunctionType(NUMBER, new FormalParameter("datetime1", DATE_AND_TIME), new FormalParameter("datetime2", DATE_AND_TIME))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("now", new BuiltinFunctionType(DATE_AND_TIME)));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("today", new BuiltinFunctionType(DATE)));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("weekday", new BuiltinFunctionType(NUMBER, new FormalParameter("datetime", DATE))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("weekday", new BuiltinFunctionType(NUMBER, new FormalParameter("datetime", DATE_AND_TIME))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("year", new BuiltinFunctionType(NUMBER, new FormalParameter("datetime", DATE))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("year", new BuiltinFunctionType(NUMBER, new FormalParameter("datetime", DATE_AND_TIME))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("yearAdd", new BuiltinFunctionType(DATE_AND_TIME, new FormalParameter("datetime", DATE), new FormalParameter("years_to_add", NUMBER))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("yearAdd", new BuiltinFunctionType(DATE_AND_TIME, new FormalParameter("datetime", DATE_AND_TIME), new FormalParameter("years_to_add", NUMBER))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("yearDiff", new BuiltinFunctionType(NUMBER, new FormalParameter("datetime1", DATE), new FormalParameter("datetime2", DATE))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("yearDiff", new BuiltinFunctionType(NUMBER, new FormalParameter("datetime1", DATE_AND_TIME), new FormalParameter("datetime2", DATE_AND_TIME))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("yearDiff", new BuiltinFunctionType(NUMBER, new FormalParameter("datetime1", DATE), new FormalParameter("datetime2", DATE_AND_TIME))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("yearDiff", new BuiltinFunctionType(NUMBER, new FormalParameter("datetime1", DATE_AND_TIME), new FormalParameter("datetime2", DATE))));

        // List functions
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("append", StandardEnvironmentFactory.makeSignavioAppendBuiltinFunctionType(ANY_LIST, ANY)));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("appendAll", StandardEnvironmentFactory.makeSignavioAppendAllBuiltinFunctionType(ANY_LIST)));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("remove", StandardEnvironmentFactory.makeSignavioRemoveBuiltinFunctionType(ANY_LIST, ANY)));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("removeAll", StandardEnvironmentFactory.makeSignavioRemoveAllBuiltinFunctionType(ANY_LIST)));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("zip", StandardEnvironmentFactory.makeSignavioZipBuiltinFunctionType(ANY_LIST, ANY_LIST, ANY_LIST)));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("notContainsAny", new BuiltinFunctionType(BOOLEAN, new FormalParameter("list1", ANY_LIST), new FormalParameter("list2", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("containsOnly", new BuiltinFunctionType(BOOLEAN, new FormalParameter("list1", ANY_LIST), new FormalParameter("list2", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("areElementsOf", new BuiltinFunctionType(BOOLEAN, new FormalParameter("list1", ANY_LIST), new FormalParameter("list2", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("elementOf", new BuiltinFunctionType(BOOLEAN, new FormalParameter("list1", ANY), new FormalParameter("list2", ANY_LIST))));

        // Statistical operations
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("avg", new BuiltinFunctionType(NUMBER, new FormalParameter("list", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("max", new BuiltinFunctionType(NUMBER, new FormalParameter("list", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("median", new BuiltinFunctionType(NUMBER, new FormalParameter("list", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("min", new BuiltinFunctionType(NUMBER, new FormalParameter("list", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("mode", new BuiltinFunctionType(NUMBER, new FormalParameter("list", ANY_LIST))));

        // String functions
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("concat", new BuiltinFunctionType(STRING, new FormalParameter("list", ANY_LIST))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("isAlpha", new BuiltinFunctionType(BOOLEAN, new FormalParameter("text", STRING))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("isAlphanumeric", new BuiltinFunctionType(BOOLEAN, new FormalParameter("text", STRING))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("isNumeric", new BuiltinFunctionType(BOOLEAN, new FormalParameter("text", STRING))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("isSpaces", new BuiltinFunctionType(BOOLEAN, new FormalParameter("text", STRING))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("len", new BuiltinFunctionType(NUMBER, new FormalParameter("text", STRING))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("lower", new BuiltinFunctionType(STRING, new FormalParameter("text", STRING))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("trim", new BuiltinFunctionType(STRING, new FormalParameter("text", STRING))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("upper", new BuiltinFunctionType(STRING, new FormalParameter("text", STRING))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("number", new BuiltinFunctionType(NUMBER, new FormalParameter("text", STRING), new FormalParameter("default_value", NUMBER, true, false))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("mid", new BuiltinFunctionType(STRING, new FormalParameter("text", STRING), new FormalParameter("start", NUMBER), new FormalParameter("num_chars", NUMBER))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("left", new BuiltinFunctionType(STRING, new FormalParameter("text", STRING), new FormalParameter("num_chars", NUMBER))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("right", new BuiltinFunctionType(STRING, new FormalParameter("text", STRING), new FormalParameter("num_chars", NUMBER))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("text", new BuiltinFunctionType(STRING, new FormalParameter("num", NUMBER), new FormalParameter("format_text", STRING))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("textOccurrences", new BuiltinFunctionType(NUMBER, new FormalParameter("find_text", STRING), new FormalParameter("within_text", STRING))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("contains", new BuiltinFunctionType(BOOLEAN, new FormalParameter("text", STRING), new FormalParameter("substring", STRING))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("startsWith", new BuiltinFunctionType(BOOLEAN, new FormalParameter("string", STRING), new FormalParameter("prefix", STRING))));
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("endsWith", new BuiltinFunctionType(BOOLEAN, new FormalParameter("string", STRING), new FormalParameter("suffix", STRING))));

        // Boolean functions
        environment.addDeclaration(INSTANCE.makeVariableDeclaration("not", new BuiltinFunctionType(BOOLEAN, new FormalParameter("boolean", BOOLEAN))));
    }
}
