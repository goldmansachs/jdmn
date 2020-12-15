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
import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.feel.analysis.semantics.SemanticError;
import com.gs.dmn.feel.analysis.semantics.environment.Environment;
import com.gs.dmn.feel.analysis.semantics.environment.EnvironmentFactory;
import com.gs.dmn.feel.analysis.semantics.type.*;
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.test.UnaryTests;
import com.gs.dmn.feel.interpreter.FEELInterpreter;
import com.gs.dmn.feel.lib.FEELLib;
import com.gs.dmn.feel.synthesis.FEELTranslator;
import com.gs.dmn.runtime.Context;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.interpreter.DMNInterpreter;
import com.gs.dmn.runtime.interpreter.Result;
import com.gs.dmn.runtime.interpreter.environment.RuntimeEnvironment;
import com.gs.dmn.runtime.interpreter.environment.RuntimeEnvironmentFactory;
import com.gs.dmn.transformation.AbstractDMNToNativeTransformer;
import com.gs.dmn.transformation.InputParameters;
import org.apache.commons.lang3.StringUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.omg.spec.dmn._20191111.model.TNamedElement;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.gs.dmn.feel.analysis.semantics.type.BooleanType.BOOLEAN;
import static com.gs.dmn.feel.analysis.semantics.type.DateType.DATE;
import static com.gs.dmn.feel.analysis.semantics.type.NumberType.NUMBER;
import static com.gs.dmn.feel.analysis.semantics.type.StringType.STRING;
import static org.junit.Assert.assertEquals;

public abstract class AbstractFEELProcessorTest<NUMBER, DATE, TIME, DATE_TIME, DURATION, TEST> extends AbstractTest {
    protected final FEELTranslator feelTranslator;
    protected final DMNInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> dmnInterpreter;
    protected final FEELInterpreter feelInterpreter;

    protected final EnvironmentFactory environmentFactory;
    private final FEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> lib;
    private final RuntimeEnvironmentFactory runtimeEnvironmentFactory;

    protected AbstractFEELProcessorTest() {
        DMNDialectDefinition<NUMBER, DATE, TIME, DATE_TIME, DURATION, TEST> dialectDefinition = makeDialect();
        DMNModelRepository repository = makeRepository();
        InputParameters inputParameters = makeInputParameters();

        this.environmentFactory = dialectDefinition.createEnvironmentFactory();
        this.runtimeEnvironmentFactory = RuntimeEnvironmentFactory.instance();
        this.lib = dialectDefinition.createFEELLib();
        this.feelTranslator = dialectDefinition.createFEELTranslator(repository, inputParameters);
        this.dmnInterpreter = dialectDefinition.createDMNInterpreter(repository, inputParameters);
        this.feelInterpreter = dialectDefinition.createFEELInterpreter(repository, inputParameters);
    }

    @Test
    public void testSimpleUnaryTests() {
        NUMBER input = this.lib.number("1");
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, input));

        doSimpleUnaryTestsTest(entries, "input", "not (-1)",
                "NegatedSimpleUnaryTests(SimplePositiveUnaryTests(OperatorTest(null,ArithmeticNegation(NumericLiteral(1)))))",
                "TupleType(boolean)",
                "booleanNot((numericEqual(input, numericUnaryMinus(number(\"1\")))))",
                this.lib.booleanNot((this.lib.numericEqual(input, this.lib.numericUnaryMinus(this.lib.number("1"))))),
                true);
        doSimpleUnaryTestsTest(entries, "input", "not(1, 2)",
                "NegatedSimpleUnaryTests(SimplePositiveUnaryTests(OperatorTest(null,NumericLiteral(1)),OperatorTest(null,NumericLiteral(2))))",
                "TupleType(boolean, boolean)",
                "booleanNot(booleanOr((numericEqual(input, number(\"1\"))), (numericEqual(input, number(\"2\")))))",
                this.lib.booleanNot(this.lib.booleanOr((this.lib.numericEqual(input, this.lib.number("1"))), (this.lib.numericEqual(input, this.lib.number("2"))))),
                false);
        doSimpleUnaryTestsTest(entries, "input", "-",
                "Any()",
                "boolean",
                "Boolean.TRUE",
                true,
                true);
    }

    @Test
    public void testSimplePositiveUnaryTests() {
        NUMBER number = this.lib.number("1");
        String string = "e1";
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("number", NUMBER, number),
                new EnvironmentEntry("string", STRING, string));

        doSimpleUnaryTestsTest(entries, "number", "1, 2",
                "SimplePositiveUnaryTests(OperatorTest(null,NumericLiteral(1)),OperatorTest(null,NumericLiteral(2)))",
                "TupleType(boolean, boolean)",
                "booleanOr((numericEqual(number, number(\"1\"))), (numericEqual(number, number(\"2\"))))",
                this.lib.booleanOr((this.lib.numericEqual(number, this.lib.number("1"))), (this.lib.numericEqual(number, this.lib.number("2")))),
                true);
        doSimpleUnaryTestsTest(entries, "string", "\"e1\", \"e2\"",
                "SimplePositiveUnaryTests(OperatorTest(null,StringLiteral(\"e1\")),OperatorTest(null,StringLiteral(\"e2\")))",
                "TupleType(boolean, boolean)",
                "booleanOr((stringEqual(string, \"e1\")), (stringEqual(string, \"e2\")))",
                this.lib.booleanOr((this.lib.stringEqual(string, "e1")), (this.lib.stringEqual(string, "e2"))),
                true);
    }

    @Test
    public void testSimplePositiveUnaryTest() {
        NUMBER number = this.lib.number("1");
        String string = "1";
        boolean boolean_ = true;
        DATE date = this.lib.date("2017-01-03");
        TIME time = this.lib.time("12:00:00Z");
        DATE_TIME dateTime = this.lib.dateAndTime("2017-01-03T12:00:00Z");
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("number", NUMBER, number),
                new EnvironmentEntry("string", STRING, string),
                new EnvironmentEntry("boolean", BOOLEAN, boolean_),
                new EnvironmentEntry("date", DateType.DATE, date),
                new EnvironmentEntry("time", TimeType.TIME, time),
                new EnvironmentEntry("dateTime", DateTimeType.DATE_AND_TIME, dateTime)
        );

        doSimpleUnaryTestsTest(entries, "number", "< 123.45",
                "SimplePositiveUnaryTests(OperatorTest(<,NumericLiteral(123.45)))",
                "TupleType(boolean)",
                "(numericLessThan(number, number(\"123.45\")))",
                (this.lib.numericLessThan(number, this.lib.number("123.45"))),
                true);
        doSimpleUnaryTestsTest(entries, "number", "<= 123.45",
                "SimplePositiveUnaryTests(OperatorTest(<=,NumericLiteral(123.45)))",
                "TupleType(boolean)",
                "(numericLessEqualThan(number, number(\"123.45\")))",
                (this.lib.numericLessEqualThan(number, this.lib.number("123.45"))),
                true);
        doSimpleUnaryTestsTest(entries, "number", "> 123.45",
                "SimplePositiveUnaryTests(OperatorTest(>,NumericLiteral(123.45)))",
                "TupleType(boolean)",
                "(numericGreaterThan(number, number(\"123.45\")))",
                (this.lib.numericGreaterThan(number, this.lib.number("123.45"))),
                false);
        doSimpleUnaryTestsTest(entries, "number", ">= 123.45",
                "SimplePositiveUnaryTests(OperatorTest(>=,NumericLiteral(123.45)))",
                "TupleType(boolean)",
                "(numericGreaterEqualThan(number, number(\"123.45\")))",
                (this.lib.numericGreaterEqualThan(number, this.lib.number("123.45"))),
                false);
        doSimpleUnaryTestsTest(entries, "date", "< date(\"2016-08-01\")",
                "SimplePositiveUnaryTests(OperatorTest(<,DateTimeLiteral(date, \"2016-08-01\")))",
                "TupleType(boolean)",
                "(dateLessThan(date, date(\"2016-08-01\")))",
                (this.lib.numericGreaterEqualThan(number, this.lib.number("123.45"))),
                false);
        doSimpleUnaryTestsTest(entries, "date", "<= date(\"2016-08-01\")",
                "SimplePositiveUnaryTests(OperatorTest(<=,DateTimeLiteral(date, \"2016-08-01\")))",
                "TupleType(boolean)",
                "(dateLessEqualThan(date, date(\"2016-08-01\")))",
                (this.lib.dateLessEqualThan(date, this.lib.date("2016-08-01"))),
                false);
        doSimpleUnaryTestsTest(entries, "date", "> date(\"2016-08-01\")",
                "SimplePositiveUnaryTests(OperatorTest(>,DateTimeLiteral(date, \"2016-08-01\")))",
                "TupleType(boolean)",
                "(dateGreaterThan(date, date(\"2016-08-01\")))",
                (this.lib.dateGreaterThan(date, this.lib.date("2016-08-01"))),
                true);
        doSimpleUnaryTestsTest(entries, "date", ">= date(\"2016-08-01\")",
                "SimplePositiveUnaryTests(OperatorTest(>=,DateTimeLiteral(date, \"2016-08-01\")))",
                "TupleType(boolean)",
                "(dateGreaterEqualThan(date, date(\"2016-08-01\")))",
                (this.lib.dateGreaterEqualThan(date, this.lib.date("2016-08-01"))),
                true);
        doSimpleUnaryTestsTest(entries, "time", "< time(\"12:00:00Z\")",
                "SimplePositiveUnaryTests(OperatorTest(<,DateTimeLiteral(time, \"12:00:00Z\")))",
                "TupleType(boolean)",
                "(timeLessThan(time, time(\"12:00:00Z\")))",
                (this.lib.timeLessThan(time, this.lib.time("12:00:00Z"))),
                false);
        doSimpleUnaryTestsTest(entries, "time", "<= time(\"12:00:00Z\")",
                "SimplePositiveUnaryTests(OperatorTest(<=,DateTimeLiteral(time, \"12:00:00Z\")))",
                "TupleType(boolean)",
                "(timeLessEqualThan(time, time(\"12:00:00Z\")))",
                (this.lib.timeLessEqualThan(time, this.lib.time("12:00:00Z"))),
                true);
        doSimpleUnaryTestsTest(entries, "time", "> time(\"12:00:00Z\")",
                "SimplePositiveUnaryTests(OperatorTest(>,DateTimeLiteral(time, \"12:00:00Z\")))",
                "TupleType(boolean)",
                "(timeGreaterThan(time, time(\"12:00:00Z\")))",
                (this.lib.timeGreaterThan(time, this.lib.time("12:00:00Z"))),
                false);
        doSimpleUnaryTestsTest(entries, "time", ">= time(\"12:00:00Z\")",
                "SimplePositiveUnaryTests(OperatorTest(>=,DateTimeLiteral(time, \"12:00:00Z\")))",
                "TupleType(boolean)",
                "(timeGreaterEqualThan(time, time(\"12:00:00Z\")))",
                (this.lib.timeGreaterEqualThan(time, this.lib.time("12:00:00Z"))),
                true);
        doSimpleUnaryTestsTest(entries, "dateTime", "< date and time(\"2016-08-01T11:00:00Z\")",
                "SimplePositiveUnaryTests(OperatorTest(<,DateTimeLiteral(date and time, \"2016-08-01T11:00:00Z\")))",
                "TupleType(boolean)",
                "(dateTimeLessThan(dateTime, dateAndTime(\"2016-08-01T11:00:00Z\")))",
                (this.lib.dateTimeLessThan(dateTime, this.lib.dateAndTime("2016-08-01T11:00:00Z"))),
                false);
        doSimpleUnaryTestsTest(entries, "dateTime", "<= date and time(\"2016-08-01T11:00:00Z\")",
                "SimplePositiveUnaryTests(OperatorTest(<=,DateTimeLiteral(date and time, \"2016-08-01T11:00:00Z\")))",
                "TupleType(boolean)",
                "(dateTimeLessEqualThan(dateTime, dateAndTime(\"2016-08-01T11:00:00Z\")))",
                (this.lib.dateTimeLessEqualThan(dateTime, this.lib.dateAndTime("2016-08-01T11:00:00Z"))),
                false);
        doSimpleUnaryTestsTest(entries, "dateTime", "> date and time(\"2016-08-01T11:00:00Z\")",
                "SimplePositiveUnaryTests(OperatorTest(>,DateTimeLiteral(date and time, \"2016-08-01T11:00:00Z\")))",
                "TupleType(boolean)",
                "(dateTimeGreaterThan(dateTime, dateAndTime(\"2016-08-01T11:00:00Z\")))",
                (this.lib.dateTimeGreaterThan(dateTime, this.lib.dateAndTime("2016-08-01T11:00:00Z"))),
                true);
        doSimpleUnaryTestsTest(entries, "dateTime", ">= date and time(\"2016-08-01T11:00:00Z\")",
                "SimplePositiveUnaryTests(OperatorTest(>=,DateTimeLiteral(date and time, \"2016-08-01T11:00:00Z\")))",
                "TupleType(boolean)",
                "(dateTimeGreaterEqualThan(dateTime, dateAndTime(\"2016-08-01T11:00:00Z\")))",
                (this.lib.dateTimeGreaterEqualThan(dateTime, this.lib.dateAndTime("2016-08-01T11:00:00Z"))),
                true);
        doSimpleUnaryTestsTest(entries, "number", "123.56",
                "SimplePositiveUnaryTests(OperatorTest(null,NumericLiteral(123.56)))",
                "TupleType(boolean)",
                "(numericEqual(number, number(\"123.56\")))",
                (this.lib.numericEqual(number, this.lib.number("123.56"))),
                false);
        doSimpleUnaryTestsTest(entries, "string", "\"abc\"",
                "SimplePositiveUnaryTests(OperatorTest(null,StringLiteral(\"abc\")))",
                "TupleType(boolean)",
                "(stringEqual(string, \"abc\"))",
                (this.lib.stringEqual(string, "abc")),
                false);
        doSimpleUnaryTestsTest(entries, "boolean", "true",
                "SimplePositiveUnaryTests(OperatorTest(null,BooleanLiteral(true)))",
                "TupleType(boolean)",
                "(booleanEqual(boolean, Boolean.TRUE))",
                (this.lib.booleanEqual(boolean_, Boolean.TRUE)),
                true);
        doSimpleUnaryTestsTest(entries, "date", "date(\"2016-08-01\")",
                "SimplePositiveUnaryTests(OperatorTest(null,DateTimeLiteral(date, \"2016-08-01\")))",
                "TupleType(boolean)",
                "(dateEqual(date, date(\"2016-08-01\")))",
                (this.lib.dateEqual(date, this.lib.date("2016-08-01"))),
                false);
        doSimpleUnaryTestsTest(entries, "time", "time(\"12:00:00Z\")",
                "SimplePositiveUnaryTests(OperatorTest(null,DateTimeLiteral(time, \"12:00:00Z\")))",
                "TupleType(boolean)",
                "(timeEqual(time, time(\"12:00:00Z\")))",
                (this.lib.timeEqual(time, this.lib.time("12:00:00Z"))),
                true);
        doSimpleUnaryTestsTest(entries, "dateTime", "date and time(\"2016-08-01T11:00:00Z\")",
                "SimplePositiveUnaryTests(OperatorTest(null,DateTimeLiteral(date and time, \"2016-08-01T11:00:00Z\")))",
                "TupleType(boolean)",
                "(dateTimeEqual(dateTime, dateAndTime(\"2016-08-01T11:00:00Z\")))",
                (this.lib.dateTimeEqual(dateTime, this.lib.dateAndTime("2016-08-01T11:00:00Z"))),
                false);
    }

    @Test
    public void testIntervalTest() {
        NUMBER input = this.lib.number("1");
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, input));

        doSimpleUnaryTestsTest(entries, "input", "(1..2)",
                "SimplePositiveUnaryTests(IntervalTest(true,NumericLiteral(1),true,NumericLiteral(2)))",
                "TupleType(RangeType(number))",
                "(booleanAnd(numericGreaterThan(input, number(\"1\")), numericLessThan(input, number(\"2\"))))",
                (this.lib.booleanAnd(this.lib.numericGreaterThan(input, this.lib.number("1")), this.lib.numericLessThan(input, this.lib.number("2")))),
                false);
        doSimpleUnaryTestsTest(entries, "input", "]1..2[",
                "SimplePositiveUnaryTests(IntervalTest(true,NumericLiteral(1),true,NumericLiteral(2)))",
                "TupleType(RangeType(number))",
                "(booleanAnd(numericGreaterThan(input, number(\"1\")), numericLessThan(input, number(\"2\"))))",
                (this.lib.booleanAnd(this.lib.numericGreaterThan(input, this.lib.number("1")), this.lib.numericLessThan(input, this.lib.number("2")))),
                false);
        doSimpleUnaryTestsTest(entries, "input", "[1..2]",
                "SimplePositiveUnaryTests(IntervalTest(false,NumericLiteral(1),false,NumericLiteral(2)))",
                "TupleType(RangeType(number))",
                "(booleanAnd(numericGreaterEqualThan(input, number(\"1\")), numericLessEqualThan(input, number(\"2\"))))",
                (this.lib.booleanAnd(this.lib.numericGreaterEqualThan(input, this.lib.number("1")), this.lib.numericLessEqualThan(input, this.lib.number("2")))),
                true);
    }

    @Test(expected = SemanticError.class)
    public void testEqualityWhenTypeMismatch() {
        Boolean input = true;
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", BOOLEAN, input));

        doSimpleUnaryTestsTest(entries, "input", "123.56", "", "TupleType(boolean)", "", null, "");
    }

    @Test(expected = SemanticError.class)
    public void testOperatorTestWhenTypeMismatch() {
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", BOOLEAN, true));

        doSimpleUnaryTestsTest(entries, "input", "< 123.56", "", "TupleType(boolean)", "", null, "");
    }

    @Test
    public void testUnaryTests() {
        NUMBER input = lib.number("1");
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, input));

        doUnaryTestsTest(entries, "input", "1, 2",
                "PositiveUnaryTests(OperatorTest(null,NumericLiteral(1)),OperatorTest(null,NumericLiteral(2)))",
                "TupleType(boolean, boolean)",
                "booleanOr((numericEqual(input, number(\"1\"))), (numericEqual(input, number(\"2\"))))",
                lib.booleanOr((lib.numericEqual(input, lib.number("1"))), (lib.numericEqual(input, lib.number("2")))),
                true);
        doUnaryTestsTest(entries, "input", "not (-1)",
                "NegatedUnaryTests(PositiveUnaryTests(OperatorTest(null,ArithmeticNegation(NumericLiteral(1)))))",
                "TupleType(boolean)",
                "booleanNot((numericEqual(input, numericUnaryMinus(number(\"1\")))))",
                lib.booleanNot((lib.numericEqual(input, lib.numericUnaryMinus(lib.number("1"))))),
                true);
        doUnaryTestsTest(entries, "input", "not(1, 2)",
                "NegatedUnaryTests(PositiveUnaryTests(OperatorTest(null,NumericLiteral(1)),OperatorTest(null,NumericLiteral(2))))",
                "TupleType(boolean, boolean)",
                "booleanNot(booleanOr((numericEqual(input, number(\"1\"))), (numericEqual(input, number(\"2\")))))",
                lib.booleanNot(lib.booleanOr((lib.numericEqual(input, lib.number("1"))), (lib.numericEqual(input, lib.number("2"))))),
                false);
        doUnaryTestsTest(entries, "input", "-",
                "Any()",
                "boolean",
                "Boolean.TRUE",
                true,
                true);
    }

    @Test
    public void testPositiveUnaryTest() {
        NUMBER inputNumber = lib.number("1");
        DATE inputDate = lib.date("2017-01-01");
        String inputString = "abc";
        List<String> inputList = lib.asList("a", "b", "c");
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("inputString", STRING, inputString),
                new EnvironmentEntry("inputNumber", NUMBER, inputNumber),
                new EnvironmentEntry("inputDate", DATE, inputDate),
                new EnvironmentEntry("inputList", ListType.STRING_LIST, inputList)
        );

        //
        // OperatorTest
        //
        doUnaryTestsTest(entries, "inputNumber", "1",
                "PositiveUnaryTests(OperatorTest(null,NumericLiteral(1)))",
                "TupleType(boolean)",
                "(numericEqual(inputNumber, number(\"1\")))",
                (lib.numericEqual(inputNumber, lib.number("1"))),
                true);
        doUnaryTestsTest(entries, "inputNumber", "-1",
                "PositiveUnaryTests(OperatorTest(null,ArithmeticNegation(NumericLiteral(1))))",
                "TupleType(boolean)",
                "(numericEqual(inputNumber, numericUnaryMinus(number(\"1\"))))",
                (lib.numericEqual(inputNumber, lib.numericUnaryMinus(lib.number("1")))),
                false);

        doUnaryTestsTest(entries, "inputNumber", "<1",
                "PositiveUnaryTests(OperatorTest(<,NumericLiteral(1)))",
                "TupleType(boolean)",
                "(numericLessThan(inputNumber, number(\"1\")))",
                (lib.numericLessThan(inputNumber, lib.number("1"))),
                false);
        doUnaryTestsTest(entries, "inputNumber", "<=1",
                "PositiveUnaryTests(OperatorTest(<=,NumericLiteral(1)))",
                "TupleType(boolean)",
                "(numericLessEqualThan(inputNumber, number(\"1\")))",
                (lib.numericLessEqualThan(inputNumber, lib.number("1"))),
                true);
        doUnaryTestsTest(entries, "inputNumber", ">1",
                "PositiveUnaryTests(OperatorTest(>,NumericLiteral(1)))",
                "TupleType(boolean)",
                "(numericGreaterThan(inputNumber, number(\"1\")))",
                (lib.numericGreaterThan(inputNumber, lib.number("1"))),
                false);
        doUnaryTestsTest(entries, "inputNumber", ">=1",
                "PositiveUnaryTests(OperatorTest(>=,NumericLiteral(1)))",
                "TupleType(boolean)",
                "(numericGreaterEqualThan(inputNumber, number(\"1\")))",
                (lib.numericGreaterEqualThan(inputNumber, lib.number("1"))),
                true);

        doUnaryTestsTest(entries, "inputDate", "< date(\"2016-08-01\")",
                "PositiveUnaryTests(OperatorTest(<,DateTimeLiteral(date, \"2016-08-01\")))",
                "TupleType(boolean)",
                "(dateLessThan(inputDate, date(\"2016-08-01\")))",
                (lib.dateLessThan(inputDate, lib.date("2016-08-01"))),
                false);

        //
        // IntervalTest
        //
        doUnaryTestsTest(entries, "inputNumber", "(1..2)",
                "PositiveUnaryTests(IntervalTest(true,NumericLiteral(1),true,NumericLiteral(2)))",
                "TupleType(RangeType(number))",
                "(booleanAnd(numericGreaterThan(inputNumber, number(\"1\")), numericLessThan(inputNumber, number(\"2\"))))",
                (lib.booleanAnd(lib.numericGreaterThan(inputNumber, lib.number("1")), lib.numericLessThan(inputNumber, lib.number("2")))),
                false);
        doUnaryTestsTest(entries, "inputNumber", "]1..2[",
                "PositiveUnaryTests(IntervalTest(true,NumericLiteral(1),true,NumericLiteral(2)))",
                "TupleType(RangeType(number))",
                "(booleanAnd(numericGreaterThan(inputNumber, number(\"1\")), numericLessThan(inputNumber, number(\"2\"))))",
                (lib.booleanAnd(lib.numericGreaterThan(inputNumber, lib.number("1")), lib.numericLessThan(inputNumber, lib.number("2")))),
                false);
        doUnaryTestsTest(entries, "inputNumber", "[1..2]",
                "PositiveUnaryTests(IntervalTest(false,NumericLiteral(1),false,NumericLiteral(2)))",
                "TupleType(RangeType(number))",
                "(booleanAnd(numericGreaterEqualThan(inputNumber, number(\"1\")), numericLessEqualThan(inputNumber, number(\"2\"))))",
                (lib.booleanAnd(lib.numericGreaterEqualThan(inputNumber, lib.number("1")), lib.numericLessEqualThan(inputNumber, lib.number("2")))),
                true);

        //
        //  NullTest
        //
        doUnaryTestsTest(entries, "inputNumber", "null",
                "PositiveUnaryTests(NullTest())",
                "TupleType(boolean)",
                "(inputNumber == null)",
                (inputNumber == null),
                false);

        //
        // ExpressionTest
        //
        doUnaryTestsTest(entries, "inputList", "count(?) > 2",
                "PositiveUnaryTests(ExpressionTest(Relational(>,FunctionInvocation(Name(count) -> PositionalParameters(Name(?))),NumericLiteral(2))))",
                "TupleType(boolean)",
                "(numericGreaterThan(count(inputList), number(\"2\")))",
                (lib.numericGreaterThan(lib.count(inputList), lib.number("2"))),
                true);

        doUnaryTestsTest(entries, "inputNumber", "? < 2 + 3",
                "PositiveUnaryTests(ExpressionTest(Relational(<,Name(?),Addition(+,NumericLiteral(2),NumericLiteral(3)))))",
                "TupleType(boolean)",
                "(numericLessThan(inputNumber, numericAdd(number(\"2\"), number(\"3\"))))",
                (lib.numericLessThan(inputNumber, lib.numericAdd(lib.number("2"), lib.number("3")))),
                true);
    }

    @Test
    public void testTextualExpressions() {
        String input = "abc";
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, input));

        doTextualExpressionsTest(entries, "1 + 2",
                "Addition(+,NumericLiteral(1),NumericLiteral(2))",
                "number",
                "numericAdd(number(\"1\"), number(\"2\"))",
                lib.numericAdd(lib.number("1"), lib.number("2")),
                lib.number("3"));
    }

    @Test
    public void testSimpleExpressions() {
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, this.lib.number("1")));

        doSimpleExpressionsTest(entries, "1, 2",
                "ExpressionList(NumericLiteral(1),NumericLiteral(2))",
                "TupleType(number, number)",
                null,
                null,
                null);
    }

    @Test
    public void testForExpression() {
        List<EnvironmentEntry> entries = Arrays.asList();

        doExpressionTest(entries, "", "for i in 0..4 return if i = 0 then 1 else i * partial[-1]",
                "ForExpression(Iterator(i in RangeIteratorDomain(NumericLiteral(0), NumericLiteral(4))) -> IfExpression(Relational(=,Name(i),NumericLiteral(0)), NumericLiteral(1), Multiplication(*,Name(i),FilterExpression(Name(partial), ArithmeticNegation(NumericLiteral(1))))))",
                "ListType(number)",
                "rangeToList(number(\"0\"), number(\"4\")).stream().map(i -> (booleanEqual(numericEqual(i, number(\"0\")), Boolean.TRUE)) ? number(\"1\") : numericMultiply(i, (java.math.BigDecimal)(elementAt(partial, numericUnaryMinus(number(\"1\")))))).collect(Collectors.toList())",
                null,
                null
        );
        doExpressionTest(entries, "", "for i in 4..2 return i",
                "ForExpression(Iterator(i in RangeIteratorDomain(NumericLiteral(4), NumericLiteral(2))) -> Name(i))",
                "ListType(number)",
                "rangeToList(number(\"4\"), number(\"2\")).stream().map(i -> i).collect(Collectors.toList())",
                lib.rangeToList(lib.number("4"), lib.number("2")).stream().map(i -> i).collect(Collectors.toList()),
                lib.asList(lib.number("4"), lib.number("3"), lib.number("2"))
        );
        doExpressionTest(entries, "", "for i in 1..-1 return i",
                "ForExpression(Iterator(i in RangeIteratorDomain(NumericLiteral(1), ArithmeticNegation(NumericLiteral(1)))) -> Name(i))",
                "ListType(number)",
                "rangeToList(number(\"1\"), numericUnaryMinus(number(\"1\"))).stream().map(i -> i).collect(Collectors.toList())",
                lib.rangeToList(lib.number("1"), lib.numericUnaryMinus(lib.number("1"))).stream().map(i -> i).collect(Collectors.toList()),
                lib.asList(lib.number("1"), lib.number("0"), lib.number("-1"))
        );

        doExpressionTest(entries, "", "for i in 1..2 return for j in [2, 3] return i+j",
                "ForExpression(Iterator(i in RangeIteratorDomain(NumericLiteral(1), NumericLiteral(2))) -> ForExpression(Iterator(j in ExpressionIteratorDomain(ListLiteral(NumericLiteral(2),NumericLiteral(3)))) -> Addition(+,Name(i),Name(j))))",
                "ListType(ListType(number))",
                "rangeToList(number(\"1\"), number(\"2\")).stream().map(i -> asList(number(\"2\"), number(\"3\")).stream().map(j -> numericAdd(i, j)).collect(Collectors.toList())).collect(Collectors.toList())",
                lib.rangeToList(lib.number("1"), lib.number("2")).stream().map(i -> lib.asList(lib.number("2"), lib.number("3")).stream().map(j -> lib.numericAdd(i, j)).collect(Collectors.toList())).collect(Collectors.toList()),
                Arrays.asList(Arrays.asList(lib.number("3"), lib.number("4")), Arrays.asList(lib.number("4"), lib.number("5"))));
        doExpressionTest(entries, "", "for i in [1..2] return i",
                "ForExpression(Iterator(i in ExpressionIteratorDomain(IntervalTest(false,NumericLiteral(1),false,NumericLiteral(2)))) -> Name(i))",
                "ListType(number)",
                "rangeToList(false, number(\"1\"), false, number(\"2\")).stream().map(i -> i).collect(Collectors.toList())",
                lib.rangeToList(false, lib.number("1"), false, lib.number("2")).stream().map(i -> i).collect(Collectors.toList()),
                Arrays.asList(lib.number("1"), lib.number("2")));
        doExpressionTest(entries, "", "for i in [1..2], j in [2..3] return i+j",
                "ForExpression(Iterator(i in ExpressionIteratorDomain(IntervalTest(false,NumericLiteral(1),false,NumericLiteral(2)))),Iterator(j in ExpressionIteratorDomain(IntervalTest(false,NumericLiteral(2),false,NumericLiteral(3)))) -> Addition(+,Name(i),Name(j)))",
                "ListType(number)",
                "rangeToList(false, number(\"1\"), false, number(\"2\")).stream().map(i -> rangeToList(false, number(\"2\"), false, number(\"3\")).stream().map(j -> numericAdd(i, j))).flatMap(x -> x).collect(Collectors.toList())",
                lib.rangeToList(false, lib.number("1"), false, lib.number("2")).stream().map(i ->
                        lib.rangeToList(false, lib.number("2"), false, lib.number("3")).stream().map(j ->
                                lib.numericAdd(i, j)))
                        .flatMap(x -> x)
                        .collect(Collectors.toList()),
                Arrays.asList(lib.number("3"), lib.number("4"), lib.number("4"), lib.number("5")));
        doExpressionTest(entries, "", "for i in [1..2] return for j in [2..3] return i+j",
                "ForExpression(Iterator(i in ExpressionIteratorDomain(IntervalTest(false,NumericLiteral(1),false,NumericLiteral(2)))) -> ForExpression(Iterator(j in ExpressionIteratorDomain(IntervalTest(false,NumericLiteral(2),false,NumericLiteral(3)))) -> Addition(+,Name(i),Name(j))))",
                "ListType(ListType(number))",
                "rangeToList(false, number(\"1\"), false, number(\"2\")).stream().map(i -> rangeToList(false, number(\"2\"), false, number(\"3\")).stream().map(j -> numericAdd(i, j)).collect(Collectors.toList())).collect(Collectors.toList())",
                Arrays.asList(Arrays.asList(lib.number("3"), lib.number("4")), Arrays.asList(lib.number("4"), lib.number("5"))),
                Arrays.asList(Arrays.asList(lib.number("3"), lib.number("4")), Arrays.asList(lib.number("4"), lib.number("5"))));

        doExpressionTest(entries, "", "for i in [1, 2] return i",
                "ForExpression(Iterator(i in ExpressionIteratorDomain(ListLiteral(NumericLiteral(1),NumericLiteral(2)))) -> Name(i))",
                "ListType(number)",
                "asList(number(\"1\"), number(\"2\")).stream().map(i -> i).collect(Collectors.toList())",
                Arrays.asList(lib.number("1"), lib.number("2")).stream().map(i -> i).collect(Collectors.toList()),
                Arrays.asList(lib.number("1"), lib.number("2")));
        doExpressionTest(entries, "", "for i in [1, 2], j in [2, 3] return i+j",
                "ForExpression(Iterator(i in ExpressionIteratorDomain(ListLiteral(NumericLiteral(1),NumericLiteral(2)))),Iterator(j in ExpressionIteratorDomain(ListLiteral(NumericLiteral(2),NumericLiteral(3)))) -> Addition(+,Name(i),Name(j)))",
                "ListType(number)",
                "asList(number(\"1\"), number(\"2\")).stream().map(i -> asList(number(\"2\"), number(\"3\")).stream().map(j -> numericAdd(i, j))).flatMap(x -> x).collect(Collectors.toList())",
                Arrays.asList(lib.number("1"), lib.number("2")).stream().map(i ->
                        Arrays.asList(lib.number("2"), lib.number("3")).stream().map(j ->
                                lib.numericAdd(i, j)))
                        .flatMap(x -> x)
                        .collect(Collectors.toList()),
                Arrays.asList(lib.number("3"), lib.number("4"), lib.number("4"), lib.number("5")));
        doExpressionTest(entries, "", "for i in [1, 2] return for j in [2, 3] return i+j",
                "ForExpression(Iterator(i in ExpressionIteratorDomain(ListLiteral(NumericLiteral(1),NumericLiteral(2)))) -> ForExpression(Iterator(j in ExpressionIteratorDomain(ListLiteral(NumericLiteral(2),NumericLiteral(3)))) -> Addition(+,Name(i),Name(j))))",
                "ListType(ListType(number))",
                "asList(number(\"1\"), number(\"2\")).stream().map(i -> asList(number(\"2\"), number(\"3\")).stream().map(j -> numericAdd(i, j)).collect(Collectors.toList())).collect(Collectors.toList())",
                Arrays.asList(lib.number("1"), lib.number("2")).stream().map(i ->
                        Arrays.asList(lib.number("2"), lib.number("3")).stream().map(j ->
                                lib.numericAdd(i, j))
                                .collect(Collectors.toList()))
                        .collect(Collectors.toList()),
                Arrays.asList(Arrays.asList(lib.number("3"), lib.number("4")), Arrays.asList(lib.number("4"), lib.number("5"))));
    }

    @Test
    public void testIfExpression() {
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, lib.number("1")));

        doExpressionTest(entries, "", "if true then 1 else 2",
                "IfExpression(BooleanLiteral(true), NumericLiteral(1), NumericLiteral(2))",
                "number",
                "(booleanEqual(Boolean.TRUE, Boolean.TRUE)) ? number(\"1\") : number(\"2\")",
                (lib.booleanEqual(Boolean.TRUE, Boolean.TRUE)) ? lib.number("1") : lib.number("2"),
                lib.number("1"));
        doExpressionTest(entries, "", "if true then \"b\" else \"a\"",
                "IfExpression(BooleanLiteral(true), StringLiteral(\"b\"), StringLiteral(\"a\"))",
                "string",
                "(booleanEqual(Boolean.TRUE, Boolean.TRUE)) ? \"b\" : \"a\"",
                (lib.booleanEqual(Boolean.TRUE, Boolean.TRUE)) ? "b" : "a",
                "b");
        doExpressionTest(entries, "", "if false then null else \"a\"",
                "IfExpression(BooleanLiteral(false), NullLiteral(), StringLiteral(\"a\"))",
                "string",
                "(booleanEqual(Boolean.FALSE, Boolean.TRUE)) ? null : \"a\"",
                (lib.booleanEqual(Boolean.FALSE, Boolean.TRUE)) ? null : "a",
                "a");
        doExpressionTest(entries, "", "if true then 1 else null",
                "IfExpression(BooleanLiteral(true), NumericLiteral(1), NullLiteral())",
                "number",
                "(booleanEqual(Boolean.TRUE, Boolean.TRUE)) ? number(\"1\") : null",
                (lib.booleanEqual(Boolean.TRUE, Boolean.TRUE)) ? lib.number("1") : null,
                lib.number("1"));
    }

    @Test(expected = SemanticError.class)
    public void testIfExpressionWhenConditionIsNotBoolean() {
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, lib.number("1")));

        doExpressionTest(entries, "", "if 5 then 1 else 2",
                "",
                "number",
                "",
                "",
                "");
    }

    @Test(expected = SemanticError.class)
    public void testIfExpressionWhenTypesDontMatch() {
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, lib.number("1")));

        doExpressionTest(entries, "", "if true then true else 2",
                "",
                "number",
                "",
                "",
                "");
    }

    @Test
    public void testQuantifiedExpression() {
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, lib.number("1")));

        doExpressionTest(entries, "", "some i in [1..2] j in [2..3] satisfies i + j > 1",
                "QuantifiedExpression(some, Iterator(i in ExpressionIteratorDomain(IntervalTest(false,NumericLiteral(1),false,NumericLiteral(2)))),Iterator(j in ExpressionIteratorDomain(IntervalTest(false,NumericLiteral(2),false,NumericLiteral(3)))) -> Relational(>,Addition(+,Name(i),Name(j)),NumericLiteral(1)))",
                "boolean",
                "booleanOr((List)rangeToList(false, number(\"1\"), false, number(\"2\")).stream().map(i -> rangeToList(false, number(\"2\"), false, number(\"3\")).stream().map(j -> numericGreaterThan(numericAdd(i, j), number(\"1\")))).flatMap(x -> x).collect(Collectors.toList()))",
                lib.booleanOr((List)
                        lib.rangeToList(false, lib.number("1"), false, lib.number("2")).stream().map(i ->
                                lib.rangeToList(false, lib.number("2"), false, lib.number("3")).stream().map(j ->
                                        lib.numericGreaterThan(lib.numericAdd(i, j), lib.number("1"))))
                                .flatMap(x -> x)
                                .collect(Collectors.toList())),
                true);
        doExpressionTest(entries, "", "every i in [1..2] j in [2..3] satisfies i + j > 1",
                "QuantifiedExpression(every, Iterator(i in ExpressionIteratorDomain(IntervalTest(false,NumericLiteral(1),false,NumericLiteral(2)))),Iterator(j in ExpressionIteratorDomain(IntervalTest(false,NumericLiteral(2),false,NumericLiteral(3)))) -> Relational(>,Addition(+,Name(i),Name(j)),NumericLiteral(1)))",
                "boolean",
                "booleanAnd((List)rangeToList(false, number(\"1\"), false, number(\"2\")).stream().map(i -> rangeToList(false, number(\"2\"), false, number(\"3\")).stream().map(j -> numericGreaterThan(numericAdd(i, j), number(\"1\")))).flatMap(x -> x).collect(Collectors.toList()))",
                lib.booleanAnd((List)
                        lib.rangeToList(false, lib.number("1"), false, lib.number("2")).stream().map(i ->
                                lib.rangeToList(false, lib.number("2"), false, lib.number("3")).stream().map(j ->
                                        lib.numericGreaterThan(lib.numericAdd(i, j), lib.number("1"))))
                                .flatMap(x -> x)
                                .collect(Collectors.toList())),
                true);

        doExpressionTest(entries, "", "some i in [1, 2] j in [2, 3] satisfies i + j > 1",
                "QuantifiedExpression(some, Iterator(i in ExpressionIteratorDomain(ListLiteral(NumericLiteral(1),NumericLiteral(2)))),Iterator(j in ExpressionIteratorDomain(ListLiteral(NumericLiteral(2),NumericLiteral(3)))) -> Relational(>,Addition(+,Name(i),Name(j)),NumericLiteral(1)))",
                "boolean",
                "booleanOr((List)asList(number(\"1\"), number(\"2\")).stream().map(i -> asList(number(\"2\"), number(\"3\")).stream().map(j -> numericGreaterThan(numericAdd(i, j), number(\"1\")))).flatMap(x -> x).collect(Collectors.toList()))",
                lib.booleanOr(Arrays.asList(lib.number("1"), lib.number("2")).stream().map(i ->
                        Arrays.asList(lib.number("2"), lib.number("3")).stream().map(j ->
                                lib.numericGreaterThan(lib.numericAdd(i, j), lib.number("1"))))
                        .flatMap(x -> x)
                        .collect(Collectors.toList())),
                true);
        doExpressionTest(entries, "", "every i in [1, 2] j in [2, 3] satisfies i + j > 1",
                "QuantifiedExpression(every, Iterator(i in ExpressionIteratorDomain(ListLiteral(NumericLiteral(1),NumericLiteral(2)))),Iterator(j in ExpressionIteratorDomain(ListLiteral(NumericLiteral(2),NumericLiteral(3)))) -> Relational(>,Addition(+,Name(i),Name(j)),NumericLiteral(1)))",
                "boolean",
                "booleanAnd((List)asList(number(\"1\"), number(\"2\")).stream().map(i -> asList(number(\"2\"), number(\"3\")).stream().map(j -> numericGreaterThan(numericAdd(i, j), number(\"1\")))).flatMap(x -> x).collect(Collectors.toList()))",
                lib.booleanAnd(Arrays.asList(lib.number("1"), lib.number("2")).stream().map(i ->
                        Arrays.asList(lib.number("2"), lib.number("3")).stream().map(j ->
                                lib.numericGreaterThan(lib.numericAdd(i, j), lib.number("1"))))
                        .flatMap(x -> x)
                        .collect(Collectors.toList())),
                true);
    }

    @Test
    public void testRelationalExpression() {
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, this.lib.number("1")));

        // number
        doExpressionTest(entries, "", "1 = 2",
                "Relational(=,NumericLiteral(1),NumericLiteral(2))",
                "boolean",
                "numericEqual(number(\"1\"), number(\"2\"))",
                this.lib.numericEqual(this.lib.number("1"), this.lib.number("2")),
                false);
        doExpressionTest(entries, "", "1 == 2",
                "Relational(==,NumericLiteral(1),NumericLiteral(2))",
                "boolean",
                "numericEqual(number(\"1\"), number(\"2\"))",
                this.lib.numericEqual(this.lib.number("1"), this.lib.number("2")),
                false);
        doExpressionTest(entries, "", "1 != 2",
                "Relational(!=,NumericLiteral(1),NumericLiteral(2))",
                "boolean",
                "numericNotEqual(number(\"1\"), number(\"2\"))",
                this.lib.numericNotEqual(this.lib.number("1"), this.lib.number("2")),
                true);
        doExpressionTest(entries, "", "1 < 2",
                "Relational(<,NumericLiteral(1),NumericLiteral(2))",
                "boolean",
                "numericLessThan(number(\"1\"), number(\"2\"))",
                this.lib.numericLessThan(this.lib.number("1"), this.lib.number("2")),
                true);
        doExpressionTest(entries, "", "1 <= 2",
                "Relational(<=,NumericLiteral(1),NumericLiteral(2))",
                "boolean",
                "numericLessEqualThan(number(\"1\"), number(\"2\"))",
                this.lib.numericLessEqualThan(this.lib.number("1"), this.lib.number("2")),
                true);
        doExpressionTest(entries, "", "1 > 2",
                "Relational(>,NumericLiteral(1),NumericLiteral(2))",
                "boolean",
                "numericGreaterThan(number(\"1\"), number(\"2\"))",
                this.lib.numericGreaterThan(this.lib.number("1"), this.lib.number("2")),
                false);
        doExpressionTest(entries, "", "1 >= 2",
                "Relational(>=,NumericLiteral(1),NumericLiteral(2))",
                "boolean",
                "numericGreaterEqualThan(number(\"1\"), number(\"2\"))",
                this.lib.numericGreaterEqualThan(this.lib.number("1"), this.lib.number("2")),
                false);

        // date
        doExpressionTest(entries, "", "date(\"2016-03-01\") = date(\"2016-03-02\")",
                "Relational(=,DateTimeLiteral(date, \"2016-03-01\"),DateTimeLiteral(date, \"2016-03-02\"))",
                "boolean",
                "dateEqual(date(\"2016-03-01\"), date(\"2016-03-02\"))",
                this.lib.dateEqual(this.lib.date("2016-03-01"), this.lib.date("2016-03-02")),
                false);
        doExpressionTest(entries, "", "date(\"2016-03-01\") == date(\"2016-03-02\")",
                "Relational(==,DateTimeLiteral(date, \"2016-03-01\"),DateTimeLiteral(date, \"2016-03-02\"))",
                "boolean",
                "dateEqual(date(\"2016-03-01\"), date(\"2016-03-02\"))",
                this.lib.dateEqual(this.lib.date("2016-03-01"), this.lib.date("2016-03-02")),
                false);
        doExpressionTest(entries, "", "date(\"2016-03-01\") != date(\"2016-03-01\")",
                "Relational(!=,DateTimeLiteral(date, \"2016-03-01\"),DateTimeLiteral(date, \"2016-03-01\"))",
                "boolean",
                "dateNotEqual(date(\"2016-03-01\"), date(\"2016-03-01\"))",
                this.lib.dateNotEqual(this.lib.date("2016-03-01"), this.lib.date("2016-03-01")),
                false);
        doExpressionTest(entries, "", "date(\"2016-03-01\") < date(\"2016-03-01\")",
                "Relational(<,DateTimeLiteral(date, \"2016-03-01\"),DateTimeLiteral(date, \"2016-03-01\"))",
                "boolean",
                "dateLessThan(date(\"2016-03-01\"), date(\"2016-03-01\"))",
                this.lib.dateLessThan(this.lib.date("2016-03-01"), this.lib.date("2016-03-01")),
                false);
        doExpressionTest(entries, "", "date(\"2016-03-01\") <= date(\"2016-03-02\")",
                "Relational(<=,DateTimeLiteral(date, \"2016-03-01\"),DateTimeLiteral(date, \"2016-03-02\"))",
                "boolean",
                "dateLessEqualThan(date(\"2016-03-01\"), date(\"2016-03-02\"))",
                this.lib.dateLessEqualThan(this.lib.date("2016-03-01"), this.lib.date("2016-03-02")),
                true);
        doExpressionTest(entries, "", "date(\"2016-03-01\") > date(\"2016-03-02\")",
                "Relational(>,DateTimeLiteral(date, \"2016-03-01\"),DateTimeLiteral(date, \"2016-03-02\"))",
                "boolean",
                "dateGreaterThan(date(\"2016-03-01\"), date(\"2016-03-02\"))",
                this.lib.dateGreaterThan(this.lib.date("2016-03-01"), this.lib.date("2016-03-02")),
                false);
        doExpressionTest(entries, "", "date(\"2016-03-01\") >= date(\"2016-03-02\")",
                "Relational(>=,DateTimeLiteral(date, \"2016-03-01\"),DateTimeLiteral(date, \"2016-03-02\"))",
                "boolean",
                "dateGreaterEqualThan(date(\"2016-03-01\"), date(\"2016-03-02\"))",
                this.lib.dateGreaterEqualThan(this.lib.date("2016-03-01"), this.lib.date("2016-03-02")),
                false);

        // date and time
        doExpressionTest(entries, "", "date and time(\"2016-03-01T12:00:00Z\") = date and time(\"2016-03-02T12:00:00Z\")",
                "Relational(=,DateTimeLiteral(date and time, \"2016-03-01T12:00:00Z\"),DateTimeLiteral(date and time, \"2016-03-02T12:00:00Z\"))",
                "boolean",
                "dateTimeEqual(dateAndTime(\"2016-03-01T12:00:00Z\"), dateAndTime(\"2016-03-02T12:00:00Z\"))",
                this.lib.dateTimeEqual(this.lib.dateAndTime("2016-03-01T12:00:00Z"), this.lib.dateAndTime("2016-03-02T12:00:00Z")),
                false);
        doExpressionTest(entries, "", "date and time(\"2016-03-01T12:00:00Z\") == date and time(\"2016-03-02T12:00:00Z\")",
                "Relational(==,DateTimeLiteral(date and time, \"2016-03-01T12:00:00Z\"),DateTimeLiteral(date and time, \"2016-03-02T12:00:00Z\"))",
                "boolean",
                "dateTimeEqual(dateAndTime(\"2016-03-01T12:00:00Z\"), dateAndTime(\"2016-03-02T12:00:00Z\"))",
                this.lib.dateTimeEqual(this.lib.dateAndTime("2016-03-01T12:00:00Z"), this.lib.dateAndTime("2016-03-02T12:00:00Z")),
                false);
        doExpressionTest(entries, "", "date and time(\"2016-03-01T12:00:00Z\") != date and time(\"2016-03-02T12:00:00Z\")",
                "Relational(!=,DateTimeLiteral(date and time, \"2016-03-01T12:00:00Z\"),DateTimeLiteral(date and time, \"2016-03-02T12:00:00Z\"))",
                "boolean",
                "dateTimeNotEqual(dateAndTime(\"2016-03-01T12:00:00Z\"), dateAndTime(\"2016-03-02T12:00:00Z\"))",
                this.lib.dateTimeNotEqual(this.lib.dateAndTime("2016-03-01T12:00:00Z"), this.lib.dateAndTime("2016-03-02T12:00:00Z")),
                true);
        doExpressionTest(entries, "", "date and time(\"2016-03-01T12:00:00Z\") < date and time(\"2016-03-02T12:00:00Z\")",
                "Relational(<,DateTimeLiteral(date and time, \"2016-03-01T12:00:00Z\"),DateTimeLiteral(date and time, \"2016-03-02T12:00:00Z\"))",
                "boolean",
                "dateTimeLessThan(dateAndTime(\"2016-03-01T12:00:00Z\"), dateAndTime(\"2016-03-02T12:00:00Z\"))",
                this.lib.dateTimeLessThan(this.lib.dateAndTime("2016-03-01T12:00:00Z"), this.lib.dateAndTime("2016-03-02T12:00:00Z")),
                true);
        doExpressionTest(entries, "", "date and time(\"2016-03-01T12:00:00Z\") <= date and time(\"2016-03-02T12:00:00Z\")",
                "Relational(<=,DateTimeLiteral(date and time, \"2016-03-01T12:00:00Z\"),DateTimeLiteral(date and time, \"2016-03-02T12:00:00Z\"))",
                "boolean",
                "dateTimeLessEqualThan(dateAndTime(\"2016-03-01T12:00:00Z\"), dateAndTime(\"2016-03-02T12:00:00Z\"))",
                this.lib.dateTimeLessEqualThan(this.lib.dateAndTime("2016-03-01T12:00:00Z"), this.lib.dateAndTime("2016-03-02T12:00:00Z")),
                true);
        doExpressionTest(entries, "", "date and time(\"2016-03-01T12:00:00Z\") > date and time(\"2016-03-02T12:00:00Z\")",
                "Relational(>,DateTimeLiteral(date and time, \"2016-03-01T12:00:00Z\"),DateTimeLiteral(date and time, \"2016-03-02T12:00:00Z\"))",
                "boolean",
                "dateTimeGreaterThan(dateAndTime(\"2016-03-01T12:00:00Z\"), dateAndTime(\"2016-03-02T12:00:00Z\"))",
                this.lib.dateTimeGreaterThan(this.lib.dateAndTime("2016-03-01T12:00:00Z"), this.lib.dateAndTime("2016-03-02T12:00:00Z")),
                false);
        doExpressionTest(entries, "", "date and time(\"2016-03-01T12:00:00Z\") >= date and time(\"2016-03-02T12:00:00Z\")",
                "Relational(>=,DateTimeLiteral(date and time, \"2016-03-01T12:00:00Z\"),DateTimeLiteral(date and time, \"2016-03-02T12:00:00Z\"))",
                "boolean",
                "dateTimeGreaterEqualThan(dateAndTime(\"2016-03-01T12:00:00Z\"), dateAndTime(\"2016-03-02T12:00:00Z\"))",
                this.lib.dateTimeGreaterEqualThan(this.lib.dateAndTime("2016-03-01T12:00:00Z"), this.lib.dateAndTime("2016-03-02T12:00:00Z")),
                false);

        // time
        doExpressionTest(entries, "", "time(\"12:00:00Z\") = time(\"12:01:00Z\")",
                "Relational(=,DateTimeLiteral(time, \"12:00:00Z\"),DateTimeLiteral(time, \"12:01:00Z\"))",
                "boolean",
                "timeEqual(time(\"12:00:00Z\"), time(\"12:01:00Z\"))",
                this.lib.timeEqual(this.lib.time("12:00:00Z"), this.lib.time("12:01:00Z")),
                false);
        doExpressionTest(entries, "", "time(\"12:00:00Z\") == time(\"12:01:00Z\")",
                "Relational(==,DateTimeLiteral(time, \"12:00:00Z\"),DateTimeLiteral(time, \"12:01:00Z\"))",
                "boolean",
                "timeEqual(time(\"12:00:00Z\"), time(\"12:01:00Z\"))",
                this.lib.timeEqual(this.lib.time("12:00:00Z"), this.lib.time("12:01:00Z")),
                false);
        doExpressionTest(entries, "", "time(\"12:00:00Z\") != time(\"12:01:00Z\")",
                "Relational(!=,DateTimeLiteral(time, \"12:00:00Z\"),DateTimeLiteral(time, \"12:01:00Z\"))",
                "boolean",
                "timeNotEqual(time(\"12:00:00Z\"), time(\"12:01:00Z\"))",
                this.lib.timeNotEqual(this.lib.time("12:00:00Z"), this.lib.time("12:01:00Z")),
                true);
        doExpressionTest(entries, "", "time(\"12:00:00Z\") < time(\"12:01:00Z\")",
                "Relational(<,DateTimeLiteral(time, \"12:00:00Z\"),DateTimeLiteral(time, \"12:01:00Z\"))",
                "boolean",
                "timeLessThan(time(\"12:00:00Z\"), time(\"12:01:00Z\"))",
                this.lib.timeLessThan(this.lib.time("12:00:00Z"), this.lib.time("12:01:00Z")),
                true);
        doExpressionTest(entries, "", "time(\"12:00:00Z\") <= time(\"12:01:00Z\")",
                "Relational(<=,DateTimeLiteral(time, \"12:00:00Z\"),DateTimeLiteral(time, \"12:01:00Z\"))",
                "boolean",
                "timeLessEqualThan(time(\"12:00:00Z\"), time(\"12:01:00Z\"))",
                this.lib.timeLessEqualThan(this.lib.time("12:00:00Z"), this.lib.time("12:01:00Z")),
                true);
        doExpressionTest(entries, "", "time(\"12:00:00Z\") > time(\"12:01:00Z\")",
                "Relational(>,DateTimeLiteral(time, \"12:00:00Z\"),DateTimeLiteral(time, \"12:01:00Z\"))",
                "boolean",
                "timeGreaterThan(time(\"12:00:00Z\"), time(\"12:01:00Z\"))",
                this.lib.timeGreaterThan(this.lib.time("12:00:00Z"), this.lib.time("12:01:00Z")),
                false);
        doExpressionTest(entries, "", "time(\"12:00:00Z\") >= time(\"12:01:00Z\")",
                "Relational(>=,DateTimeLiteral(time, \"12:00:00Z\"),DateTimeLiteral(time, \"12:01:00Z\"))",
                "boolean",
                "timeGreaterEqualThan(time(\"12:00:00Z\"), time(\"12:01:00Z\"))",
                this.lib.timeGreaterEqualThan(this.lib.time("12:00:00Z"), this.lib.time("12:01:00Z")),
                false);

        // duration
        doExpressionTest(entries, "", "duration(\"P1Y1M\") = duration(\"P1Y2M\")",
                "Relational(=,DateTimeLiteral(duration, \"P1Y1M\"),DateTimeLiteral(duration, \"P1Y2M\"))",
                "boolean",
                "durationEqual(duration(\"P1Y1M\"), duration(\"P1Y2M\"))",
                this.lib.durationEqual(this.lib.duration("P1Y1M"), this.lib.duration("P1Y2M")),
                false);
        doExpressionTest(entries, "", "duration(\"P1Y1M\") == duration(\"P1Y2M\")",
                "Relational(==,DateTimeLiteral(duration, \"P1Y1M\"),DateTimeLiteral(duration, \"P1Y2M\"))",
                "boolean",
                "durationEqual(duration(\"P1Y1M\"), duration(\"P1Y2M\"))",
                this.lib.durationEqual(this.lib.duration("P1Y1M"), this.lib.duration("P1Y2M")),
                false);
        doExpressionTest(entries, "", "duration(\"P1Y1M\") != duration(\"P1Y2M\")",
                "Relational(!=,DateTimeLiteral(duration, \"P1Y1M\"),DateTimeLiteral(duration, \"P1Y2M\"))",
                "boolean",
                "durationNotEqual(duration(\"P1Y1M\"), duration(\"P1Y2M\"))",
                this.lib.durationNotEqual(this.lib.duration("P1Y1M"), this.lib.duration("P1Y2M")),
                true);
        doExpressionTest(entries, "", "duration(\"P1Y1M\") < duration(\"P1Y2M\")",
                "Relational(<,DateTimeLiteral(duration, \"P1Y1M\"),DateTimeLiteral(duration, \"P1Y2M\"))",
                "boolean",
                "durationLessThan(duration(\"P1Y1M\"), duration(\"P1Y2M\"))",
                this.lib.durationLessThan(this.lib.duration("P1Y1M"), this.lib.duration("P1Y2M")),
                true);
        doExpressionTest(entries, "", "duration(\"P1Y1M\") <= duration(\"P1Y2M\")",
                "Relational(<=,DateTimeLiteral(duration, \"P1Y1M\"),DateTimeLiteral(duration, \"P1Y2M\"))",
                "boolean",
                "durationLessEqualThan(duration(\"P1Y1M\"), duration(\"P1Y2M\"))",
                this.lib.durationLessEqualThan(this.lib.duration("P1Y1M"), this.lib.duration("P1Y2M")),
                true);
        doExpressionTest(entries, "", "duration(\"P1Y1M\") > duration(\"P1Y2M\")",
                "Relational(>,DateTimeLiteral(duration, \"P1Y1M\"),DateTimeLiteral(duration, \"P1Y2M\"))",
                "boolean",
                "durationGreaterThan(duration(\"P1Y1M\"), duration(\"P1Y2M\"))",
                this.lib.durationGreaterThan(this.lib.duration("P1Y1M"), this.lib.duration("P1Y2M")),
                false);
        doExpressionTest(entries, "", "duration(\"P1Y1M\") >= duration(\"P1Y2M\")",
                "Relational(>=,DateTimeLiteral(duration, \"P1Y1M\"),DateTimeLiteral(duration, \"P1Y2M\"))",
                "boolean",
                "durationGreaterEqualThan(duration(\"P1Y1M\"), duration(\"P1Y2M\"))",
                this.lib.durationGreaterEqualThan(this.lib.duration("P1Y1M"), this.lib.duration("P1Y2M")),
                false);
    }

    @Test
    public void testNullRelationalExpression() {
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, lib.number("1")));

        // null
        doExpressionTest(entries, "", "1 = null",
                "Relational(=,NumericLiteral(1),NullLiteral())",
                "boolean",
                "numericEqual(number(\"1\"), null)",
                lib.numericEqual(lib.number("1"), null),
                false);
        doExpressionTest(entries, "", "null = 2",
                "Relational(=,NullLiteral(),NumericLiteral(2))",
                "boolean",
                "numericEqual(null, number(\"2\"))",
                lib.numericEqual(null, lib.number("2")),
                false);
        doExpressionTest(entries, "", "1 != null",
                "Relational(!=,NumericLiteral(1),NullLiteral())",
                "boolean",
                "numericNotEqual(number(\"1\"), null)",
                lib.numericNotEqual(lib.number("1"), null),
                true);
        doExpressionTest(entries, "", "null != 2",
                "Relational(!=,NullLiteral(),NumericLiteral(2))",
                "boolean",
                "numericNotEqual(null, number(\"2\"))",
                lib.numericNotEqual(null, lib.number("2")),
                true);

        doExpressionTest(entries, "", "true = null",
                "Relational(=,BooleanLiteral(true),NullLiteral())",
                "boolean",
                "booleanEqual(Boolean.TRUE, null)",
                lib.booleanEqual(Boolean.TRUE, null),
                false);
        doExpressionTest(entries, "", "null = true",
                "Relational(=,NullLiteral(),BooleanLiteral(true))",
                "boolean",
                "booleanEqual(null, Boolean.TRUE)",
                lib.booleanEqual(null, Boolean.TRUE),
                false);
        doExpressionTest(entries, "", "true != null",
                "Relational(!=,BooleanLiteral(true),NullLiteral())",
                "boolean",
                "booleanNotEqual(Boolean.TRUE, null)",
                lib.booleanNotEqual(Boolean.TRUE, null),
                true);
        doExpressionTest(entries, "", "null != true",
                "Relational(!=,NullLiteral(),BooleanLiteral(true))",
                "boolean",
                "booleanNotEqual(null, Boolean.TRUE)",
                lib.booleanNotEqual(null, Boolean.TRUE),
                true);

        doExpressionTest(entries, "", "\"abc\" = null",
                "Relational(=,StringLiteral(\"abc\"),NullLiteral())",
                "boolean",
                "stringEqual(\"abc\", null)",
                lib.stringEqual("abc", null),
                false);
        doExpressionTest(entries, "", "null = \"abc\"",
                "Relational(=,NullLiteral(),StringLiteral(\"abc\"))",
                "boolean",
                "stringEqual(null, \"abc\")",
                lib.stringEqual(null, "abc"),
                false);
        doExpressionTest(entries, "", "\"abc\" != null",
                "Relational(!=,StringLiteral(\"abc\"),NullLiteral())",
                "boolean",
                "stringNotEqual(\"abc\", null)",
                lib.stringNotEqual("abc", null),
                true);
        doExpressionTest(entries, "", "null != \"abc\"",
                "Relational(!=,NullLiteral(),StringLiteral(\"abc\"))",
                "boolean",
                "stringNotEqual(null, \"abc\")",
                lib.stringNotEqual(null, "abc"),
                true);

        doExpressionTest(entries, "", "date(\"2016-03-01\") = null",
                "Relational(=,DateTimeLiteral(date, \"2016-03-01\"),NullLiteral())",
                "boolean",
                "dateEqual(date(\"2016-03-01\"), null)",
                lib.dateEqual(lib.date("2016-03-01"), null),
                false);
        doExpressionTest(entries, "", "null = date(\"2016-03-02\")",
                "Relational(=,NullLiteral(),DateTimeLiteral(date, \"2016-03-02\"))",
                "boolean",
                "dateEqual(null, date(\"2016-03-02\"))",
                lib.dateEqual(null, lib.date("2016-03-02")),
                false);
        doExpressionTest(entries, "", "date(\"2016-03-01\") != null",
                "Relational(!=,DateTimeLiteral(date, \"2016-03-01\"),NullLiteral())",
                "boolean",
                "dateNotEqual(date(\"2016-03-01\"), null)",
                lib.dateNotEqual(lib.date("2016-03-01"), null),
                true);
        doExpressionTest(entries, "", "null != date(\"2016-03-02\")",
                "Relational(!=,NullLiteral(),DateTimeLiteral(date, \"2016-03-02\"))",
                "boolean",
                "dateNotEqual(null, date(\"2016-03-02\"))",
                lib.dateNotEqual(null, lib.date("2016-03-02")),
                true);

        doExpressionTest(entries, "", "date and time(\"2016-03-01T12:00:00Z\") = null",
                "Relational(=,DateTimeLiteral(date and time, \"2016-03-01T12:00:00Z\"),NullLiteral())",
                "boolean",
                "dateTimeEqual(dateAndTime(\"2016-03-01T12:00:00Z\"), null)",
                lib.dateTimeEqual(lib.dateAndTime("2016-03-01T12:00:00Z"), null),
                false);
        doExpressionTest(entries, "", "null = date and time(\"2016-03-02T12:00:00Z\")",
                "Relational(=,NullLiteral(),DateTimeLiteral(date and time, \"2016-03-02T12:00:00Z\"))",
                "boolean",
                "dateTimeEqual(null, dateAndTime(\"2016-03-02T12:00:00Z\"))",
                lib.dateTimeEqual(null, lib.dateAndTime("2016-03-02T12:00:00Z")),
                false);
        doExpressionTest(entries, "", "date and time(\"2016-03-01T12:00:00Z\") != null",
                "Relational(!=,DateTimeLiteral(date and time, \"2016-03-01T12:00:00Z\"),NullLiteral())",
                "boolean",
                "dateTimeNotEqual(dateAndTime(\"2016-03-01T12:00:00Z\"), null)",
                lib.dateTimeNotEqual(lib.dateAndTime("2016-03-01T12:00:00Z"), null),
                true);
        doExpressionTest(entries, "", "null != date and time(\"2016-03-02T12:00:00Z\")",
                "Relational(!=,NullLiteral(),DateTimeLiteral(date and time, \"2016-03-02T12:00:00Z\"))",
                "boolean",
                "dateTimeNotEqual(null, dateAndTime(\"2016-03-02T12:00:00Z\"))",
                lib.dateTimeNotEqual(null, lib.dateAndTime("2016-03-02T12:00:00Z")),
                true);

        doExpressionTest(entries, "", "time(\"12:00:00Z\") = null",
                "Relational(=,DateTimeLiteral(time, \"12:00:00Z\"),NullLiteral())",
                "boolean",
                "timeEqual(time(\"12:00:00Z\"), null)",
                lib.timeEqual(lib.time("12:00:00Z"), null),
                false);
        doExpressionTest(entries, "", "null = time(\"12:01:00Z\")",
                "Relational(=,NullLiteral(),DateTimeLiteral(time, \"12:01:00Z\"))",
                "boolean",
                "timeEqual(null, time(\"12:01:00Z\"))",
                lib.timeEqual(null, lib.time("12:01:00Z")),
                false);
        doExpressionTest(entries, "", "time(\"12:00:00Z\") != null",
                "Relational(!=,DateTimeLiteral(time, \"12:00:00Z\"),NullLiteral())",
                "boolean",
                "timeNotEqual(time(\"12:00:00Z\"), null)",
                lib.timeNotEqual(lib.time("12:00:00Z"), null),
                true);
        doExpressionTest(entries, "", "null != time(\"12:01:00Z\")",
                "Relational(!=,NullLiteral(),DateTimeLiteral(time, \"12:01:00Z\"))",
                "boolean",
                "timeNotEqual(null, time(\"12:01:00Z\"))",
                lib.timeNotEqual(null, lib.time("12:01:00Z")),
                true);

        doExpressionTest(entries, "", "duration(\"P1Y1M\") = null",
                "Relational(=,DateTimeLiteral(duration, \"P1Y1M\"),NullLiteral())",
                "boolean",
                "durationEqual(duration(\"P1Y1M\"), null)",
                lib.durationEqual(lib.duration("P1Y1M"), null),
                false);
        doExpressionTest(entries, "", "null = duration(\"P1Y2M\")",
                "Relational(=,NullLiteral(),DateTimeLiteral(duration, \"P1Y2M\"))",
                "boolean",
                "durationEqual(null, duration(\"P1Y2M\"))",
                lib.durationEqual(null, lib.duration("P1Y2M")),
                false);
        doExpressionTest(entries, "", "duration(\"P1Y1M\") != null",
                "Relational(!=,DateTimeLiteral(duration, \"P1Y1M\"),NullLiteral())",
                "boolean",
                "durationNotEqual(duration(\"P1Y1M\"), null)",
                lib.durationNotEqual(lib.duration("P1Y1M"), null),
                true);
        doExpressionTest(entries, "", "null != duration(\"P1Y2M\")",
                "Relational(!=,NullLiteral(),DateTimeLiteral(duration, \"P1Y2M\"))",
                "boolean",
                "durationNotEqual(null, duration(\"P1Y2M\"))",
                lib.durationNotEqual(null, lib.duration("P1Y2M")),
                true);

        doExpressionTest(entries, "", "null = null",
                "Relational(=,NullLiteral(),NullLiteral())",
                "boolean",
                "(null) == (null)",
                null == null,
                true);
        doExpressionTest(entries, "", "null != null",
                "Relational(!=,NullLiteral(),NullLiteral())",
                "boolean",
                "(null) != (null)",
                null != null,
                false);

        // list
        doExpressionTest(entries, "", "[] = []",
                "Relational(=,ListLiteral(),ListLiteral())",
                "boolean",
                "listEqual(asList(), asList())",
                lib.listEqual(lib.asList(), lib.asList()),
                true);
        doExpressionTest(entries, "", "[] = null",
                "Relational(=,ListLiteral(),NullLiteral())",
                "boolean",
                "listEqual(asList(), null)",
                lib.listEqual(lib.asList(), null),
                false);

        doExpressionTest(entries, "", "[] != []",
                "Relational(!=,ListLiteral(),ListLiteral())",
                "boolean",
                "listNotEqual(asList(), asList())",
                lib.listNotEqual(lib.asList(), lib.asList()),
                false);
        doExpressionTest(entries, "", "[] != null",
                "Relational(!=,ListLiteral(),NullLiteral())",
                "boolean",
                "listNotEqual(asList(), null)",
                lib.listNotEqual(lib.asList(), null),
                true);

        // context
        doExpressionTest(entries, "", "{} = {}",
                "Relational(=,Context(),Context())",
                "boolean",
                "contextEqual(new com.gs.dmn.runtime.Context(), new com.gs.dmn.runtime.Context())",
                lib.contextEqual(new com.gs.dmn.runtime.Context(), new com.gs.dmn.runtime.Context()),
                true);
        doExpressionTest(entries, "", "{} = null",
                "Relational(=,Context(),NullLiteral())",
                "boolean",
                "contextEqual(new com.gs.dmn.runtime.Context(), null)",
                lib.contextEqual(new com.gs.dmn.runtime.Context(), null),
                false);

        doExpressionTest(entries, "", "{} != {}",
                "Relational(!=,Context(),Context())",
                "boolean",
                "contextNotEqual(new com.gs.dmn.runtime.Context(), new com.gs.dmn.runtime.Context())",
                lib.contextNotEqual(new com.gs.dmn.runtime.Context(), new com.gs.dmn.runtime.Context()),
                false);
        doExpressionTest(entries, "", "{} != null",
                "Relational(!=,Context(),NullLiteral())",
                "boolean",
                "contextNotEqual(new com.gs.dmn.runtime.Context(), null)",
                lib.contextNotEqual(new com.gs.dmn.runtime.Context(), null),
                true);

        // complex
        doExpressionTest(entries, "", "[1,2,{a: [3,4]}] = [1,2,{a: [3,4]}]",
                "Relational(=,ListLiteral(NumericLiteral(1),NumericLiteral(2),Context(ContextEntry(ContextEntryKey(a) = ListLiteral(NumericLiteral(3),NumericLiteral(4))))),ListLiteral(NumericLiteral(1),NumericLiteral(2),Context(ContextEntry(ContextEntryKey(a) = ListLiteral(NumericLiteral(3),NumericLiteral(4))))))",
                "boolean",
                "listEqual(asList(number(\"1\"), number(\"2\"), new com.gs.dmn.runtime.Context().add(\"a\", asList(number(\"3\"), number(\"4\")))), asList(number(\"1\"), number(\"2\"), new com.gs.dmn.runtime.Context().add(\"a\", asList(number(\"3\"), number(\"4\")))))",
                lib.listEqual(lib.asList(lib.number("1"), lib.number("2"), new com.gs.dmn.runtime.Context().add("a", lib.asList(lib.number("3"), lib.number("4")))), lib.asList(lib.number("1"), lib.number("2"), new com.gs.dmn.runtime.Context().add("a", lib.asList(lib.number("3"), lib.number("4"))))),
                true);

        // complex
        doExpressionTest(entries, "", "[1,2,{a: [3,4]}] = [1,2,{a: [3,4], b: \"foo\"}]",
                "Relational(=,ListLiteral(NumericLiteral(1),NumericLiteral(2),Context(ContextEntry(ContextEntryKey(a) = ListLiteral(NumericLiteral(3),NumericLiteral(4))))),ListLiteral(NumericLiteral(1),NumericLiteral(2),Context(ContextEntry(ContextEntryKey(a) = ListLiteral(NumericLiteral(3),NumericLiteral(4))),ContextEntry(ContextEntryKey(b) = StringLiteral(\"foo\")))))",
                "boolean",
                "listEqual(asList(number(\"1\"), number(\"2\"), new com.gs.dmn.runtime.Context().add(\"a\", asList(number(\"3\"), number(\"4\")))), asList(number(\"1\"), number(\"2\"), new com.gs.dmn.runtime.Context().add(\"a\", asList(number(\"3\"), number(\"4\"))).add(\"b\", \"foo\")))",
                lib.listEqual(lib.asList(lib.number("1"), lib.number("2"), new com.gs.dmn.runtime.Context().add("a", lib.asList(lib.number("3"), lib.number("4")))), lib.asList(lib.number("1"), lib.number("2"), new com.gs.dmn.runtime.Context().add("a", lib.asList(lib.number("3"), lib.number("4"))).add("b", "foo"))),
                false);

    }

    @Test
    public void testBetweenExpression() {
        NUMBER i = lib.number("1");
        NUMBER a = lib.number("1");
        NUMBER b = lib.number("1");
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("i", NUMBER, i),
                new EnvironmentEntry("a", NUMBER, a),
                new EnvironmentEntry("b", NUMBER, b));

        doExpressionTest(entries, "", "3 between 1 and 4",
                "BetweenExpression(NumericLiteral(3), NumericLiteral(1), NumericLiteral(4))",
                "boolean",
                "booleanAnd(numericLessEqualThan(number(\"1\"), number(\"3\")), numericLessEqualThan(number(\"3\"), number(\"4\")))",
                lib.booleanAnd(lib.numericLessEqualThan(lib.number("1"), lib.number("3")), lib.numericLessEqualThan(lib.number("3"), lib.number("4"))),
                true);
        doExpressionTest(entries, "", "(i) between (a) and (b)",
                "BetweenExpression(Name(i), Name(a), Name(b))",
                "boolean",
                "booleanAnd(numericLessEqualThan(a, i), numericLessEqualThan(i, b))",
                lib.booleanAnd(lib.numericLessEqualThan(a, i), lib.numericLessEqualThan(i, b)),
                true);
        doExpressionTest(entries, "", "(i) between 1 and 2",
                "BetweenExpression(Name(i), NumericLiteral(1), NumericLiteral(2))",
                "boolean",
                "booleanAnd(numericLessEqualThan(number(\"1\"), i), numericLessEqualThan(i, number(\"2\")))",
                lib.booleanAnd(lib.numericLessEqualThan(lib.number("1"), i), lib.numericLessEqualThan(i, lib.number("2"))),
                true);

        doExpressionTest(entries, "", "date(\"2018-12-01\") between date(\"2018-12-02\") and date(\"2018-12-04\")",
                "BetweenExpression(DateTimeLiteral(date, \"2018-12-01\"), DateTimeLiteral(date, \"2018-12-02\"), DateTimeLiteral(date, \"2018-12-04\"))",
                "boolean",
                "booleanAnd(dateLessEqualThan(date(\"2018-12-02\"), date(\"2018-12-01\")), dateLessEqualThan(date(\"2018-12-01\"), date(\"2018-12-04\")))",
                lib.booleanAnd(lib.dateLessEqualThan(lib.date("2018-12-02"), lib.date("2018-12-01")), lib.dateLessEqualThan(lib.date("2018-12-01"), lib.date("2018-12-04"))),
                false);
        doExpressionTest(entries, "", "time(\"10:31:00\") between time(\"10:32:00\") and time(\"10:34:00\")",
                "BetweenExpression(DateTimeLiteral(time, \"10:31:00\"), DateTimeLiteral(time, \"10:32:00\"), DateTimeLiteral(time, \"10:34:00\"))",
                "boolean",
                "booleanAnd(timeLessEqualThan(time(\"10:32:00\"), time(\"10:31:00\")), timeLessEqualThan(time(\"10:31:00\"), time(\"10:34:00\")))",
                lib.booleanAnd(lib.timeLessEqualThan(lib.time("10:32:00"), lib.time("10:31:00")), lib.timeLessEqualThan(lib.time("10:31:00"), lib.time("10:34:00"))),
                false);
        doExpressionTest(entries, "", "date and time(\"2018-12-01T10:30:00\") between date and time(\"2018-12-02T10:30:00\") and date and time(\"2018-12-04T10:30:00\")",
                "BetweenExpression(DateTimeLiteral(date and time, \"2018-12-01T10:30:00\"), DateTimeLiteral(date and time, \"2018-12-02T10:30:00\"), DateTimeLiteral(date and time, \"2018-12-04T10:30:00\"))",
                "boolean",
                "booleanAnd(dateTimeLessEqualThan(dateAndTime(\"2018-12-02T10:30:00\"), dateAndTime(\"2018-12-01T10:30:00\")), dateTimeLessEqualThan(dateAndTime(\"2018-12-01T10:30:00\"), dateAndTime(\"2018-12-04T10:30:00\")))",
                lib.booleanAnd(lib.dateTimeLessEqualThan(lib.dateAndTime("2018-12-02T10:30:00"), lib.dateAndTime("2018-12-01T10:30:00")), lib.dateTimeLessEqualThan(lib.dateAndTime("2018-12-01T10:30:00"), lib.dateAndTime("2018-12-04T10:30:00"))),
                false);
        doExpressionTest(entries, "", "duration(\"P1Y\") between duration(\"P2Y\") and duration(\"P4Y\")",
                "BetweenExpression(DateTimeLiteral(duration, \"P1Y\"), DateTimeLiteral(duration, \"P2Y\"), DateTimeLiteral(duration, \"P4Y\"))",
                "boolean",
                "booleanAnd(durationLessEqualThan(duration(\"P2Y\"), duration(\"P1Y\")), durationLessEqualThan(duration(\"P1Y\"), duration(\"P4Y\")))",
                lib.booleanAnd(lib.durationLessEqualThan(lib.duration("P2Y"), lib.duration("P1Y")), lib.durationLessEqualThan(lib.duration("P1Y"), lib.duration("P4Y"))),
                false);
        doExpressionTest(entries, "", "duration(\"P1D\") between duration(\"P2D\") and duration(\"P4D\")",
                "BetweenExpression(DateTimeLiteral(duration, \"P1D\"), DateTimeLiteral(duration, \"P2D\"), DateTimeLiteral(duration, \"P4D\"))",
                "boolean",
                "booleanAnd(durationLessEqualThan(duration(\"P2D\"), duration(\"P1D\")), durationLessEqualThan(duration(\"P1D\"), duration(\"P4D\")))",
                lib.booleanAnd(lib.durationLessEqualThan(lib.duration("P2D"), lib.duration("P1D")), lib.durationLessEqualThan(lib.duration("P1D"), lib.duration("P4D"))),
                false);
    }

    @Test
    public void testInExpression() {
        // operator test
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, lib.number("1")));

        doExpressionTest(entries, "", "1 in 1",
                "InExpression(NumericLiteral(1), OperatorTest(null,NumericLiteral(1)))",
                "boolean",
                "(numericEqual(number(\"1\"), number(\"1\")))",
                (lib.numericEqual(lib.number("1"), lib.number("1"))),
                true);
        doExpressionTest(entries, "", "1 in <1",
                "InExpression(NumericLiteral(1), OperatorTest(<,NumericLiteral(1)))",
                "boolean",
                "(numericLessThan(number(\"1\"), number(\"1\")))",
                (lib.numericLessThan(lib.number("1"), lib.number("1"))),
                false);
        doExpressionTest(entries, "", "1 in <=1",
                "InExpression(NumericLiteral(1), OperatorTest(<=,NumericLiteral(1)))",
                "boolean",
                "(numericLessEqualThan(number(\"1\"), number(\"1\")))",
                (lib.numericLessEqualThan(lib.number("1"), lib.number("1"))),
                true);
        doExpressionTest(entries, "", "1 in >1",
                "InExpression(NumericLiteral(1), OperatorTest(>,NumericLiteral(1)))",
                "boolean",
                "(numericGreaterThan(number(\"1\"), number(\"1\")))",
                (lib.numericGreaterThan(lib.number("1"), lib.number("1"))),
                false);
        doExpressionTest(entries, "", "1 in >=1",
                "InExpression(NumericLiteral(1), OperatorTest(>=,NumericLiteral(1)))",
                "boolean",
                "(numericGreaterEqualThan(number(\"1\"), number(\"1\")))",
                (lib.numericGreaterEqualThan(lib.number("1"), lib.number("1"))),
                true);

        // interval test
        doExpressionTest(entries, "", "1 in (1..2)",
                "InExpression(NumericLiteral(1), IntervalTest(true,NumericLiteral(1),true,NumericLiteral(2)))",
                "boolean",
                "(booleanAnd(numericGreaterThan(number(\"1\"), number(\"1\")), numericLessThan(number(\"1\"), number(\"2\"))))",
                (lib.booleanAnd(lib.numericGreaterThan(lib.number("1"), lib.number("1")), lib.numericLessThan(lib.number("1"), lib.number("2")))),
                false);
        doExpressionTest(entries, "", "1 in (1..2]",
                "InExpression(NumericLiteral(1), IntervalTest(true,NumericLiteral(1),false,NumericLiteral(2)))",
                "boolean",
                "(booleanAnd(numericGreaterThan(number(\"1\"), number(\"1\")), numericLessEqualThan(number(\"1\"), number(\"2\"))))",
                (lib.booleanAnd(lib.numericGreaterThan(lib.number("1"), lib.number("1")), lib.numericLessEqualThan(lib.number("1"), lib.number("2")))),
                false);
        doExpressionTest(entries, "", "1 in [1..2)",
                "InExpression(NumericLiteral(1), IntervalTest(false,NumericLiteral(1),true,NumericLiteral(2)))",
                "boolean",
                "(booleanAnd(numericGreaterEqualThan(number(\"1\"), number(\"1\")), numericLessThan(number(\"1\"), number(\"2\"))))",
                (lib.booleanAnd(lib.numericGreaterEqualThan(lib.number("1"), lib.number("1")), lib.numericLessThan(lib.number("1"), lib.number("2")))),
                true);
        doExpressionTest(entries, "", "1 in [1..2]",
                "InExpression(NumericLiteral(1), IntervalTest(false,NumericLiteral(1),false,NumericLiteral(2)))",
                "boolean",
                "(booleanAnd(numericGreaterEqualThan(number(\"1\"), number(\"1\")), numericLessEqualThan(number(\"1\"), number(\"2\"))))",
                (lib.booleanAnd(lib.numericGreaterEqualThan(lib.number("1"), lib.number("1")), lib.numericLessEqualThan(lib.number("1"), lib.number("2")))),
                true);

        // list test
        doExpressionTest(entries, "", "1 in [1, 2]",
                "InExpression(NumericLiteral(1), ListTest(ListLiteral(NumericLiteral(1),NumericLiteral(2))))",
                "boolean",
                "(listContains(asList(number(\"1\"), number(\"2\")), number(\"1\")))",
                (lib.listContains(Arrays.asList(lib.number("1"), lib.number("2")), lib.number("1"))),
                true);
        doExpressionTest(entries, "", "[1, 2, 3] in [1, 2, 3]",
                "InExpression(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), ListTest(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3))))",
                "boolean",
                "(listEqual(asList(number(\"1\"), number(\"2\"), number(\"3\")), asList(number(\"1\"), number(\"2\"), number(\"3\"))))",
                (lib.listEqual(lib.asList(lib.number("1"), lib.number("2"), lib.number("3")), lib.asList(lib.number("1"), lib.number("2"), lib.number("3")))),
                true);
        doExpressionTest(entries, "", "[1,2,3] in ([1,2,3,4], [1,2,3])",
                "InExpression(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), ListTest(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3),NumericLiteral(4))), ListTest(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3))))",
                "boolean",
                "booleanOr(listEqual(asList(number(\"1\"), number(\"2\"), number(\"3\")), asList(number(\"1\"), number(\"2\"), number(\"3\"), number(\"4\"))), listEqual(asList(number(\"1\"), number(\"2\"), number(\"3\")), asList(number(\"1\"), number(\"2\"), number(\"3\"))))",
                lib.booleanOr(lib.listEqual(lib.asList(lib.number("1"), lib.number("2"), lib.number("3")), lib.asList(lib.number("1"), lib.number("2"), lib.number("3"), lib.number("4"))), lib.listEqual(lib.asList(lib.number("1"), lib.number("2"), lib.number("3")), lib.asList(lib.number("1"), lib.number("2"), lib.number("3")))),
                true);
        doExpressionTest(entries, "", "[1,2,3] in ([[1,2,3,4]], [[1,2,3]])",
                "InExpression(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), ListTest(ListLiteral(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3),NumericLiteral(4)))), ListTest(ListLiteral(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)))))",
                "boolean",
                "booleanOr(listContains(asList(asList(number(\"1\"), number(\"2\"), number(\"3\"), number(\"4\"))), asList(number(\"1\"), number(\"2\"), number(\"3\"))), listContains(asList(asList(number(\"1\"), number(\"2\"), number(\"3\"))), asList(number(\"1\"), number(\"2\"), number(\"3\"))))",
                lib.booleanOr(lib.listContains(lib.asList(lib.asList(lib.number("1"), lib.number("2"), lib.number("3"), lib.number("4"))), lib.asList(lib.number("1"), lib.number("2"), lib.number("3"))), lib.listContains(lib.asList(lib.asList(lib.number("1"), lib.number("2"), lib.number("3"))), lib.asList(lib.number("1"), lib.number("2"), lib.number("3")))),
                true);
        doExpressionTest(entries, "", "[1,2,3] in ([[1,2,3,4]], [[1,2,3]])",
                "InExpression(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), ListTest(ListLiteral(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3),NumericLiteral(4)))), ListTest(ListLiteral(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)))))",
                "boolean",
                "booleanOr(listContains(asList(asList(number(\"1\"), number(\"2\"), number(\"3\"), number(\"4\"))), asList(number(\"1\"), number(\"2\"), number(\"3\"))), listContains(asList(asList(number(\"1\"), number(\"2\"), number(\"3\"))), asList(number(\"1\"), number(\"2\"), number(\"3\"))))",
                lib.booleanOr(lib.listContains(lib.asList(lib.asList(lib.number("1"), lib.number("2"), lib.number("3"), lib.number("4"))), lib.asList(lib.number("1"), lib.number("2"), lib.number("3"))), lib.listContains(lib.asList(lib.asList(lib.number("1"), lib.number("2"), lib.number("3"))), lib.asList(lib.number("1"), lib.number("2"), lib.number("3")))),
                true);
        doExpressionTest(entries, "", "[1,2,3] in [[1,2,3,4], [1,2,3]]",
                "InExpression(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), ListTest(ListLiteral(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3),NumericLiteral(4)),ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)))))",
                "boolean",
                "(listContains(asList(asList(number(\"1\"), number(\"2\"), number(\"3\"), number(\"4\")), asList(number(\"1\"), number(\"2\"), number(\"3\"))), asList(number(\"1\"), number(\"2\"), number(\"3\"))))",
                (lib.listContains(lib.asList(lib.asList(lib.number("1"), lib.number("2"), lib.number("3"), lib.number("4")), lib.asList(lib.number("1"), lib.number("2"), lib.number("3"))), lib.asList(lib.number("1"), lib.number("2"), lib.number("3")))),
                true);

        // list test containing IntervalTest
        doExpressionTest(entries, "", "1 in [[2..4], [1..3]]",
                "InExpression(NumericLiteral(1), ListTest(ListLiteral(IntervalTest(false,NumericLiteral(2),false,NumericLiteral(4)),IntervalTest(false,NumericLiteral(1),false,NumericLiteral(3)))))",
                "boolean",
                "(listContains(asList(booleanAnd(numericGreaterEqualThan(number(\"1\"), number(\"2\")), numericLessEqualThan(number(\"1\"), number(\"4\"))), booleanAnd(numericGreaterEqualThan(number(\"1\"), number(\"1\")), numericLessEqualThan(number(\"1\"), number(\"3\")))), true))",
                (lib.listContains(lib.asList(lib.booleanAnd(lib.numericGreaterEqualThan(lib.number("1"), lib.number("2")), lib.numericLessEqualThan(lib.number("1"), lib.number("4"))), lib.booleanAnd(lib.numericGreaterEqualThan(lib.number("1"), lib.number("1")), lib.numericLessEqualThan(lib.number("1"), lib.number("3")))), true)),
                true);
        doExpressionTest(entries, "", "date(\"2018-12-11\") in [[date(\"2018-12-05\") .. date(\"2018-12-07\")], [date(\"2018-12-10\") .. date(\"2018-12-12\")]]",
                "InExpression(DateTimeLiteral(date, \"2018-12-11\"), ListTest(ListLiteral(IntervalTest(false,DateTimeLiteral(date, \"2018-12-05\"),false,DateTimeLiteral(date, \"2018-12-07\")),IntervalTest(false,DateTimeLiteral(date, \"2018-12-10\"),false,DateTimeLiteral(date, \"2018-12-12\")))))",
                "boolean",
                "(listContains(asList(booleanAnd(dateGreaterEqualThan(date(\"2018-12-11\"), date(\"2018-12-05\")), dateLessEqualThan(date(\"2018-12-11\"), date(\"2018-12-07\"))), booleanAnd(dateGreaterEqualThan(date(\"2018-12-11\"), date(\"2018-12-10\")), dateLessEqualThan(date(\"2018-12-11\"), date(\"2018-12-12\")))), true))",
                (lib.listContains(lib.asList(lib.booleanAnd(lib.dateGreaterEqualThan(lib.date("2018-12-11"), lib.date("2018-12-05")), lib.dateLessEqualThan(lib.date("2018-12-11"), lib.date("2018-12-07"))), lib.booleanAnd(lib.dateGreaterEqualThan(lib.date("2018-12-11"), lib.date("2018-12-10")), lib.dateLessEqualThan(lib.date("2018-12-11"), lib.date("2018-12-12")))), true)),
                true);
        doExpressionTest(entries, "", "time(\"10:30:11\") in [[time(\"10:30:05\") .. time(\"10:30:07\")], [time(\"10:30:10\") .. time(\"10:30:12\")]]",
                "InExpression(DateTimeLiteral(time, \"10:30:11\"), ListTest(ListLiteral(IntervalTest(false,DateTimeLiteral(time, \"10:30:05\"),false,DateTimeLiteral(time, \"10:30:07\")),IntervalTest(false,DateTimeLiteral(time, \"10:30:10\"),false,DateTimeLiteral(time, \"10:30:12\")))))",
                "boolean",
                "(listContains(asList(booleanAnd(timeGreaterEqualThan(time(\"10:30:11\"), time(\"10:30:05\")), timeLessEqualThan(time(\"10:30:11\"), time(\"10:30:07\"))), booleanAnd(timeGreaterEqualThan(time(\"10:30:11\"), time(\"10:30:10\")), timeLessEqualThan(time(\"10:30:11\"), time(\"10:30:12\")))), true))",
                (lib.listContains(lib.asList(lib.booleanAnd(lib.timeGreaterEqualThan(lib.time("10:30:11"), lib.time("10:30:05")), lib.timeLessEqualThan(lib.time("10:30:11"), lib.time("10:30:07"))), lib.booleanAnd(lib.timeGreaterEqualThan(lib.time("10:30:11"), lib.time("10:30:10")), lib.timeLessEqualThan(lib.time("10:30:11"), lib.time("10:30:12")))), true)),
                true);
        doExpressionTest(entries, "", "date and time(\"2018-12-08T10:30:11\") in [[date and time(\"2018-12-08T10:30:05\") .. date and time(\"2018-12-08T10:30:07\")], [date and time(\"2018-12-08T10:30:10\") .. date and time(\"2018-12-08T10:30:12\")]]",
                "InExpression(DateTimeLiteral(date and time, \"2018-12-08T10:30:11\"), ListTest(ListLiteral(IntervalTest(false,DateTimeLiteral(date and time, \"2018-12-08T10:30:05\"),false,DateTimeLiteral(date and time, \"2018-12-08T10:30:07\")),IntervalTest(false,DateTimeLiteral(date and time, \"2018-12-08T10:30:10\"),false,DateTimeLiteral(date and time, \"2018-12-08T10:30:12\")))))",
                "boolean",
                "(listContains(asList(booleanAnd(dateTimeGreaterEqualThan(dateAndTime(\"2018-12-08T10:30:11\"), dateAndTime(\"2018-12-08T10:30:05\")), dateTimeLessEqualThan(dateAndTime(\"2018-12-08T10:30:11\"), dateAndTime(\"2018-12-08T10:30:07\"))), booleanAnd(dateTimeGreaterEqualThan(dateAndTime(\"2018-12-08T10:30:11\"), dateAndTime(\"2018-12-08T10:30:10\")), dateTimeLessEqualThan(dateAndTime(\"2018-12-08T10:30:11\"), dateAndTime(\"2018-12-08T10:30:12\")))), true))",
                (lib.listContains(lib.asList(lib.booleanAnd(lib.dateTimeGreaterEqualThan(lib.dateAndTime("2018-12-08T10:30:11"), lib.dateAndTime("2018-12-08T10:30:05")), lib.dateTimeLessEqualThan(lib.dateAndTime("2018-12-08T10:30:11"), lib.dateAndTime("2018-12-08T10:30:07"))), lib.booleanAnd(lib.dateTimeGreaterEqualThan(lib.dateAndTime("2018-12-08T10:30:11"), lib.dateAndTime("2018-12-08T10:30:10")), lib.dateTimeLessEqualThan(lib.dateAndTime("2018-12-08T10:30:11"), lib.dateAndTime("2018-12-08T10:30:12")))), true)),
                true);
        doExpressionTest(entries, "", "{a: \"foo\"} in [{b: \"bar\"}, {a: \"foo\"}]",
                "InExpression(Context(ContextEntry(ContextEntryKey(a) = StringLiteral(\"foo\"))), ListTest(ListLiteral(Context(ContextEntry(ContextEntryKey(b) = StringLiteral(\"bar\"))),Context(ContextEntry(ContextEntryKey(a) = StringLiteral(\"foo\"))))))",
                "boolean",
                "(listContains(asList(new com.gs.dmn.runtime.Context().add(\"b\", \"bar\"), new com.gs.dmn.runtime.Context().add(\"a\", \"foo\")), new com.gs.dmn.runtime.Context().add(\"a\", \"foo\")))",
                (lib.listContains(lib.asList(new com.gs.dmn.runtime.Context().add("b", "bar"), new com.gs.dmn.runtime.Context().add("a", "foo")), new com.gs.dmn.runtime.Context().add("a", "foo"))),
                true);

        doExpressionTest(entries, "", "{a: \"foo\"} in ({a: \"bar\"}, {a: \"foo\"})",
                "InExpression(Context(ContextEntry(ContextEntryKey(a) = StringLiteral(\"foo\"))), OperatorTest(null,Context(ContextEntry(ContextEntryKey(a) = StringLiteral(\"bar\")))), OperatorTest(null,Context(ContextEntry(ContextEntryKey(a) = StringLiteral(\"foo\")))))",
                "boolean",
                "booleanOr(contextEqual(new com.gs.dmn.runtime.Context().add(\"a\", \"foo\"), new com.gs.dmn.runtime.Context().add(\"a\", \"bar\")), contextEqual(new com.gs.dmn.runtime.Context().add(\"a\", \"foo\"), new com.gs.dmn.runtime.Context().add(\"a\", \"foo\")))",
                lib.booleanOr(lib.contextEqual(new com.gs.dmn.runtime.Context().add("a", "foo"), new com.gs.dmn.runtime.Context().add("a", "bar")), lib.contextEqual(new com.gs.dmn.runtime.Context().add("a", "foo"), new com.gs.dmn.runtime.Context().add("a", "foo"))),
                true);
        doExpressionTest(entries, "", "{a: \"foo\"} in ({a: \"bar\"}, {a: \"baz\"})",
                "InExpression(Context(ContextEntry(ContextEntryKey(a) = StringLiteral(\"foo\"))), OperatorTest(null,Context(ContextEntry(ContextEntryKey(a) = StringLiteral(\"bar\")))), OperatorTest(null,Context(ContextEntry(ContextEntryKey(a) = StringLiteral(\"baz\")))))",
                "boolean",
                "booleanOr(contextEqual(new com.gs.dmn.runtime.Context().add(\"a\", \"foo\"), new com.gs.dmn.runtime.Context().add(\"a\", \"bar\")), contextEqual(new com.gs.dmn.runtime.Context().add(\"a\", \"foo\"), new com.gs.dmn.runtime.Context().add(\"a\", \"baz\")))",
                lib.booleanOr(lib.contextEqual(new com.gs.dmn.runtime.Context().add("a", "foo"), new com.gs.dmn.runtime.Context().add("a", "bar")), lib.contextEqual(new com.gs.dmn.runtime.Context().add("a", "foo"), new com.gs.dmn.runtime.Context().add("a", "baz"))),
                false);

        // compound test
        doExpressionTest(entries, "", "1 in (1)",
                "InExpression(NumericLiteral(1), OperatorTest(null,NumericLiteral(1)))",
                "boolean",
                "(numericEqual(number(\"1\"), number(\"1\")))",
                (lib.numericEqual(lib.number("1"), lib.number("1"))),
                true);
        doExpressionTest(entries, "", "1 in (1, 2)",
                "InExpression(NumericLiteral(1), OperatorTest(null,NumericLiteral(1)), OperatorTest(null,NumericLiteral(2)))",
                "boolean",
                "booleanOr(numericEqual(number(\"1\"), number(\"1\")), numericEqual(number(\"1\"), number(\"2\")))",
                lib.booleanOr(lib.numericEqual(lib.number("1"), lib.number("1")), lib.numericEqual(lib.number("1"), lib.number("2"))),
                true);
        doExpressionTest(entries, "", "1 in (<1, [1..2], [1, 2])",
                "InExpression(NumericLiteral(1), OperatorTest(<,NumericLiteral(1)), IntervalTest(false,NumericLiteral(1),false,NumericLiteral(2)), ListTest(ListLiteral(NumericLiteral(1),NumericLiteral(2))))",
                "boolean",
                "booleanOr(numericLessThan(number(\"1\"), number(\"1\")), booleanAnd(numericGreaterEqualThan(number(\"1\"), number(\"1\")), numericLessEqualThan(number(\"1\"), number(\"2\"))), listContains(asList(number(\"1\"), number(\"2\")), number(\"1\")))",
                lib.booleanOr(lib.numericLessThan(lib.number("1"), lib.number("1")), lib.booleanAnd(lib.numericGreaterEqualThan(lib.number("1"), lib.number("1")), lib.numericLessEqualThan(lib.number("1"), lib.number("2"))), lib.listContains(lib.asList(lib.number("1"), lib.number("2")), lib.number("1"))),
                true);
        doExpressionTest(entries, "", "1 in (<1, [1..2], 3)",
                "InExpression(NumericLiteral(1), OperatorTest(<,NumericLiteral(1)), IntervalTest(false,NumericLiteral(1),false,NumericLiteral(2)), OperatorTest(null,NumericLiteral(3)))",
                "boolean",
                "booleanOr(numericLessThan(number(\"1\"), number(\"1\")), booleanAnd(numericGreaterEqualThan(number(\"1\"), number(\"1\")), numericLessEqualThan(number(\"1\"), number(\"2\"))), numericEqual(number(\"1\"), number(\"3\")))",
                lib.booleanOr(lib.numericLessThan(lib.number("1"), lib.number("1")), lib.booleanAnd(lib.numericGreaterEqualThan(lib.number("1"), lib.number("1")), lib.numericLessEqualThan(lib.number("1"), lib.number("2"))), lib.numericEqual(lib.number("1"), lib.number("3"))),
                true);
    }

    @Test(expected = SemanticError.class)
    public void testInExpressionWhenOperatorTestAndTypeMismatch() {
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, lib.number("1")));

        doExpressionTest(entries, "", "1 in (true)",
                "",
                "boolean",
                "",
                "",
                "");
    }

    @Test
    public void testLogicExpression() {
        Boolean booleanA = true;
        Boolean booleanB = false;
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, lib.number("1")),
                new EnvironmentEntry("booleanA", BOOLEAN, booleanA),
                new EnvironmentEntry("booleanB", BOOLEAN, booleanB));

        doExpressionTest(entries, "", "(true) or (true)",
                "Disjunction(BooleanLiteral(true),BooleanLiteral(true))",
                "boolean",
                "booleanOr(Boolean.TRUE, Boolean.TRUE)",
                lib.booleanOr(Boolean.TRUE, Boolean.TRUE),
                true);
        doExpressionTest(entries, "", "true or true",
                "Disjunction(BooleanLiteral(true),BooleanLiteral(true))",
                "boolean",
                "booleanOr(Boolean.TRUE, Boolean.TRUE)",
                lib.booleanOr(Boolean.TRUE, Boolean.TRUE),
                true);
        doExpressionTest(entries, "", "true or 123",
                "Disjunction(BooleanLiteral(true),NumericLiteral(123))",
                "boolean",
                "booleanOr(Boolean.TRUE, number(\"123\"))",
                lib.booleanOr(Boolean.TRUE, lib.number("123")),
                true);
        doExpressionTest(entries, "", "false or 123",
                "Disjunction(BooleanLiteral(false),NumericLiteral(123))",
                "boolean",
                "booleanOr(Boolean.FALSE, number(\"123\"))",
                lib.booleanOr(Boolean.FALSE, lib.number("123")),
                null);

        doExpressionTest(entries, "", "(true) and (true)",
                "Conjunction(BooleanLiteral(true),BooleanLiteral(true))",
                "boolean",
                "booleanAnd(Boolean.TRUE, Boolean.TRUE)",
                lib.booleanAnd(Boolean.TRUE, Boolean.TRUE),
                true);
        doExpressionTest(entries, "", "true and true",
                "Conjunction(BooleanLiteral(true),BooleanLiteral(true))",
                "boolean",
                "booleanAnd(Boolean.TRUE, Boolean.TRUE)",
                lib.booleanAnd(Boolean.TRUE, Boolean.TRUE),
                true);
        doExpressionTest(entries, "", "false and 123",
                "Conjunction(BooleanLiteral(false),NumericLiteral(123))",
                "boolean",
                "booleanAnd(Boolean.FALSE, number(\"123\"))",
                lib.booleanAnd(Boolean.FALSE, lib.number("123")),
                false);
        doExpressionTest(entries, "", "true and 123",
                "Conjunction(BooleanLiteral(true),NumericLiteral(123))",
                "boolean",
                "booleanAnd(Boolean.TRUE, number(\"123\"))",
                lib.booleanAnd(Boolean.TRUE, lib.number("123")),
                null);

        doExpressionTest(entries, "", "not (true)",
                "LogicNegation(BooleanLiteral(true))",
                "boolean",
                "booleanNot(Boolean.TRUE)",
                lib.booleanNot(Boolean.TRUE),
                false);

        doExpressionTest(entries, "", "not not true",
                "BooleanLiteral(true)",
                "boolean",
                "Boolean.TRUE",
                Boolean.TRUE,
                true);

        doExpressionTest(entries, "", "not not not true",
                "LogicNegation(BooleanLiteral(true))",
                "boolean",
                "booleanNot(Boolean.TRUE)",
                lib.booleanNot(Boolean.TRUE),
                false);

        doExpressionTest(entries, "", "booleanA and booleanB or booleanA",
                "Disjunction(Conjunction(Name(booleanA),Name(booleanB)),Name(booleanA))",
                "boolean",
                "booleanOr(booleanAnd(booleanA, booleanB), booleanA)",
                lib.booleanOr(lib.booleanAnd(booleanA, booleanB), booleanA),
                true);
    }

    @Test
    public void testAddition() {
        String number = "1";
        String time = "time(\"12:00:00Z\")";
        String dateAndTime = "date and time(\"2016-08-01T11:00:00Z\")";
        String date = "date(\"2016-08-01\")";
        String yearsAndMonthsDuration = "duration(\"P1Y1M\")";
        String daysAndTimeDuration = "duration(\"P1DT1H\")";
        String string = "\"abc\"";

        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, this.lib.number("1")));

        doExpressionTest(entries, "", String.format("%s %s %s", number, "+", number),
                "Addition(+,NumericLiteral(1),NumericLiteral(1))",
                "number",
                "numericAdd(number(\"1\"), number(\"1\"))",
                this.lib.numericAdd(this.lib.number("1"), this.lib.number("1")),
                this.lib.number("2"));
        doExpressionTest(entries, "", String.format("%s %s %s", number, "-", number),
                "Addition(-,NumericLiteral(1),NumericLiteral(1))",
                "number",
                "numericSubtract(number(\"1\"), number(\"1\"))",
                this.lib.numericSubtract(this.lib.number("1"), this.lib.number("1")),
                this.lib.number("0"));

        doExpressionTest(entries, "", String.format("%s %s %s", dateAndTime, "-", dateAndTime),
                "Addition(-,DateTimeLiteral(date and time, \"2016-08-01T11:00:00Z\"),DateTimeLiteral(date and time, \"2016-08-01T11:00:00Z\"))",
                "days and time duration",
                "dateTimeSubtract(dateAndTime(\"2016-08-01T11:00:00Z\"), dateAndTime(\"2016-08-01T11:00:00Z\"))",
                this.lib.dateTimeSubtract(this.lib.dateAndTime("2016-08-01T11:00:00Z"), this.lib.dateAndTime("2016-08-01T11:00:00Z")),
                this.lib.duration("P0Y0M0DT0H0M0.000S"));

        doExpressionTest(entries, "", String.format("%s %s %s", date, "-", date),
                "Addition(-,DateTimeLiteral(date, \"2016-08-01\"),DateTimeLiteral(date, \"2016-08-01\"))",
                "years and months duration",
                "dateSubtract(date(\"2016-08-01\"), date(\"2016-08-01\"))",
                this.lib.dateSubtract(this.lib.date("2016-08-01"), this.lib.date("2016-08-01")),
                this.lib.duration("P0Y0M0DT0H0M0.000S"));

        doExpressionTest(entries, "", String.format("%s %s %s", time, "-", time),
                "Addition(-,DateTimeLiteral(time, \"12:00:00Z\"),DateTimeLiteral(time, \"12:00:00Z\"))",
                "days and time duration",
                "timeSubtract(time(\"12:00:00Z\"), time(\"12:00:00Z\"))",
                this.lib.timeSubtract(this.lib.time("12:00:00Z"), this.lib.time("12:00:00Z")),
                this.lib.duration("P0Y0M0DT0H0M0.000S"));

        doExpressionTest(entries, "", String.format("%s %s %s", yearsAndMonthsDuration, "+", yearsAndMonthsDuration),
                "Addition(+,DateTimeLiteral(duration, \"P1Y1M\"),DateTimeLiteral(duration, \"P1Y1M\"))",
                "years and months duration",
                "durationAdd(duration(\"P1Y1M\"), duration(\"P1Y1M\"))",
                this.lib.durationAdd(this.lib.duration("P1Y1M"), this.lib.duration("P1Y1M")),
                this.lib.duration("P2Y2M"));
        doExpressionTest(entries, "", String.format("%s %s %s", yearsAndMonthsDuration, "-", yearsAndMonthsDuration),
                "Addition(-,DateTimeLiteral(duration, \"P1Y1M\"),DateTimeLiteral(duration, \"P1Y1M\"))",
                "years and months duration",
                "durationSubtract(duration(\"P1Y1M\"), duration(\"P1Y1M\"))",
                this.lib.durationSubtract(this.lib.duration("P1Y1M"), this.lib.duration("P1Y1M")),
                this.lib.duration("P0Y0M"));

        doExpressionTest(entries, "", String.format("%s %s %s", daysAndTimeDuration, "+", daysAndTimeDuration),
                "Addition(+,DateTimeLiteral(duration, \"P1DT1H\"),DateTimeLiteral(duration, \"P1DT1H\"))",
                "days and time duration",
                "durationAdd(duration(\"P1DT1H\"), duration(\"P1DT1H\"))",
                this.lib.durationAdd(this.lib.duration("P1DT1H"), this.lib.duration("P1DT1H")),
                this.lib.duration("P2DT2H"));
        doExpressionTest(entries, "", String.format("%s %s %s", daysAndTimeDuration, "-", daysAndTimeDuration),
                "Addition(-,DateTimeLiteral(duration, \"P1DT1H\"),DateTimeLiteral(duration, \"P1DT1H\"))",
                "days and time duration",
                "durationSubtract(duration(\"P1DT1H\"), duration(\"P1DT1H\"))",
                this.lib.durationSubtract(this.lib.duration("P1DT1H"), this.lib.duration("P1DT1H")),
                this.lib.duration("P0DT0H"));

        doExpressionTest(entries, "", String.format("%s %s %s", dateAndTime, "+", yearsAndMonthsDuration),
                "Addition(+,DateTimeLiteral(date and time, \"2016-08-01T11:00:00Z\"),DateTimeLiteral(duration, \"P1Y1M\"))",
                "date and time",
                "dateTimeAddDuration(dateAndTime(\"2016-08-01T11:00:00Z\"), duration(\"P1Y1M\"))",
                this.lib.dateTimeAddDuration(this.lib.dateAndTime("2016-08-01T11:00:00Z"), this.lib.duration("P1Y1M")),
                this.lib.dateAndTime("2017-09-01T11:00:00Z"));
        doExpressionTest(entries, "", String.format("%s %s %s", dateAndTime, "-", yearsAndMonthsDuration),
                "Addition(-,DateTimeLiteral(date and time, \"2016-08-01T11:00:00Z\"),DateTimeLiteral(duration, \"P1Y1M\"))",
                "date and time",
                "dateTimeSubtractDuration(dateAndTime(\"2016-08-01T11:00:00Z\"), duration(\"P1Y1M\"))",
                this.lib.dateTimeSubtractDuration(this.lib.dateAndTime("2016-08-01T11:00:00Z"), this.lib.duration("P1Y1M")),
                this.lib.dateAndTime("2015-07-01T11:00:00Z"));

        // Not in standard
        doExpressionTest(entries, "", String.format("%s %s %s", date, "+", yearsAndMonthsDuration),
                "Addition(+,DateTimeLiteral(date, \"2016-08-01\"),DateTimeLiteral(duration, \"P1Y1M\"))",
                "date",
                "dateAddDuration(date(\"2016-08-01\"), duration(\"P1Y1M\"))",
                this.lib.dateAddDuration(this.lib.date("2016-08-01"), this.lib.duration("P1Y1M")),
                this.lib.date("2017-09-01"));
        // Not in standard
        doExpressionTest(entries, "", String.format("%s %s %s", date, "-", yearsAndMonthsDuration),
                "Addition(-,DateTimeLiteral(date, \"2016-08-01\"),DateTimeLiteral(duration, \"P1Y1M\"))",
                "date",
                "dateSubtractDuration(date(\"2016-08-01\"), duration(\"P1Y1M\"))",
                this.lib.dateSubtractDuration(this.lib.date("2016-08-01"), this.lib.duration("P1Y1M")),
                this.lib.date("2015-07-01"));

        doExpressionTest(entries, "", String.format("%s %s %s", yearsAndMonthsDuration, "+", dateAndTime),
                "Addition(+,DateTimeLiteral(duration, \"P1Y1M\"),DateTimeLiteral(date and time, \"2016-08-01T11:00:00Z\"))",
                "date and time",
                "dateTimeAddDuration(dateAndTime(\"2016-08-01T11:00:00Z\"), duration(\"P1Y1M\"))",
                this.lib.dateTimeAddDuration(this.lib.dateAndTime("2016-08-01T11:00:00Z"), this.lib.duration("P1Y1M")),
                this.lib.dateAndTime("2017-09-01T11:00:00Z"));
        // Not in standard
        doExpressionTest(entries, "", String.format("%s %s %s", yearsAndMonthsDuration, "+", date),
                "Addition(+,DateTimeLiteral(duration, \"P1Y1M\"),DateTimeLiteral(date, \"2016-08-01\"))",
                "date",
                "dateAddDuration(date(\"2016-08-01\"), duration(\"P1Y1M\"))",
                this.lib.dateAddDuration(this.lib.date("2016-08-01"), this.lib.duration("P1Y1M")),
                this.lib.date("2017-09-01"));

        doExpressionTest(entries, "", String.format("%s %s %s", dateAndTime, "+", daysAndTimeDuration),
                "Addition(+,DateTimeLiteral(date and time, \"2016-08-01T11:00:00Z\"),DateTimeLiteral(duration, \"P1DT1H\"))",
                "date and time",
                "dateTimeAddDuration(dateAndTime(\"2016-08-01T11:00:00Z\"), duration(\"P1DT1H\"))",
                this.lib.dateTimeAddDuration(this.lib.dateAndTime("2016-08-01T11:00:00Z"), this.lib.duration("P1DT1H")),
                this.lib.dateAndTime("2016-08-02T12:00:00Z"));
        doExpressionTest(entries, "", String.format("%s %s %s", dateAndTime, "-", daysAndTimeDuration),
                "Addition(-,DateTimeLiteral(date and time, \"2016-08-01T11:00:00Z\"),DateTimeLiteral(duration, \"P1DT1H\"))",
                "date and time",
                "dateTimeSubtractDuration(dateAndTime(\"2016-08-01T11:00:00Z\"), duration(\"P1DT1H\"))",
                this.lib.dateTimeSubtractDuration(this.lib.dateAndTime("2016-08-01T11:00:00Z"), this.lib.duration("P1DT1H")),
                this.lib.dateAndTime("2016-07-31T10:00:00Z"));

        doExpressionTest(entries, "", String.format("%s %s %s", daysAndTimeDuration, "+", dateAndTime),
                "Addition(+,DateTimeLiteral(duration, \"P1DT1H\"),DateTimeLiteral(date and time, \"2016-08-01T11:00:00Z\"))",
                "date and time",
                "dateTimeAddDuration(dateAndTime(\"2016-08-01T11:00:00Z\"), duration(\"P1DT1H\"))",
                this.lib.dateTimeAddDuration(this.lib.dateAndTime("2016-08-01T11:00:00Z"), this.lib.duration("P1DT1H")),
                this.lib.dateAndTime("2016-08-02T12:00:00Z"));

        doExpressionTest(entries, "", String.format("%s %s %s", time, "+", daysAndTimeDuration),
                "Addition(+,DateTimeLiteral(time, \"12:00:00Z\"),DateTimeLiteral(duration, \"P1DT1H\"))",
                "time",
                "timeAddDuration(time(\"12:00:00Z\"), duration(\"P1DT1H\"))",
                this.lib.timeAddDuration(this.lib.time("12:00:00Z"), this.lib.duration("P1DT1H")),
                this.lib.time("13:00:00Z"));
        doExpressionTest(entries, "", String.format("%s %s %s", time, "-", daysAndTimeDuration),
                "Addition(-,DateTimeLiteral(time, \"12:00:00Z\"),DateTimeLiteral(duration, \"P1DT1H\"))",
                "time",
                "timeSubtractDuration(time(\"12:00:00Z\"), duration(\"P1DT1H\"))",
                this.lib.timeSubtractDuration(this.lib.time("12:00:00Z"), this.lib.duration("P1DT1H")),
                this.lib.time("11:00:00Z"));

        doExpressionTest(entries, "", String.format("%s %s %s", daysAndTimeDuration, "+", time),
                "Addition(+,DateTimeLiteral(duration, \"P1DT1H\"),DateTimeLiteral(time, \"12:00:00Z\"))",
                "time",
                "timeAddDuration(time(\"12:00:00Z\"), duration(\"P1DT1H\"))",
                this.lib.timeAddDuration(this.lib.time("12:00:00Z"), this.lib.duration("P1DT1H")),
                this.lib.time("13:00:00Z"));

        doExpressionTest(entries, "", String.format("%s %s %s", string, "+", string),
                "Addition(+,StringLiteral(\"abc\"),StringLiteral(\"abc\"))",
                "string",
                "stringAdd(\"abc\", \"abc\")",
                this.lib.stringAdd("abc", "abc"),
                "abcabc");

        doExpressionTest(entries, "", "string(\"Today is the \") + string(\"day\") + string(\".\") + string(\"month\") + string(\".\") + string(\"year\") + string(\"!\")",
                "Addition(+,Addition(+,Addition(+,Addition(+,Addition(+,Addition(+,FunctionInvocation(Name(string) -> PositionalParameters(StringLiteral(\"Today is the \"))),FunctionInvocation(Name(string) -> PositionalParameters(StringLiteral(\"day\")))),FunctionInvocation(Name(string) -> PositionalParameters(StringLiteral(\".\")))),FunctionInvocation(Name(string) -> PositionalParameters(StringLiteral(\"month\")))),FunctionInvocation(Name(string) -> PositionalParameters(StringLiteral(\".\")))),FunctionInvocation(Name(string) -> PositionalParameters(StringLiteral(\"year\")))),FunctionInvocation(Name(string) -> PositionalParameters(StringLiteral(\"!\"))))",
                "string",
                "stringAdd(stringAdd(stringAdd(stringAdd(stringAdd(stringAdd(string(\"Today is the \"), string(\"day\")), string(\".\")), string(\"month\")), string(\".\")), string(\"year\")), string(\"!\"))",
                this.lib.stringAdd(this.lib.stringAdd(this.lib.stringAdd(this.lib.stringAdd(this.lib.stringAdd(this.lib.stringAdd(
                        this.lib.string("Today is the "), this.lib.string("day")), this.lib.string(".")), this.lib.string("month")), this.lib.string(".")), this.lib.string("year")), this.lib.string("!")),
                "Today is the day.month.year!");
    }

    @Test
    public void testMultiplication() {
        String number = "1";
        String yearsAndMonthsDuration = "duration(\"P1Y1M\")";
        String daysAndTimeDuration = "duration(\"P1DT1H\")";

        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, this.lib.number("1")));

        // number
        doExpressionTest(entries, "", String.format("%s %s %s", number, "*", number),
                "Multiplication(*,NumericLiteral(1),NumericLiteral(1))",
                "number",
                "numericMultiply(number(\"1\"), number(\"1\"))",
                this.lib.numericMultiply(this.lib.number("1"), this.lib.number("1")),
                this.lib.number("1"));
        doExpressionTest(entries, "", String.format("%s %s %s", number, "/", number),
                "Multiplication(/,NumericLiteral(1),NumericLiteral(1))",
                "number",
                "numericDivide(number(\"1\"), number(\"1\"))",
                this.lib.numericDivide(this.lib.number("1"), this.lib.number("1")),
                this.lib.number("1"));

        // years and months duration
        doExpressionTest(entries, "", String.format("%s %s %s", yearsAndMonthsDuration, "*", number),
                "Multiplication(*,DateTimeLiteral(duration, \"P1Y1M\"),NumericLiteral(1))",
                "years and months duration",
                "durationMultiply(duration(\"P1Y1M\"), number(\"1\"))",
                this.lib.durationMultiply(this.lib.duration("P1Y1M"), this.lib.number("1")),
                this.lib.duration("P1Y1M"));
        doExpressionTest(entries, "", String.format("%s %s %s", yearsAndMonthsDuration, "/", number),
                "Multiplication(/,DateTimeLiteral(duration, \"P1Y1M\"),NumericLiteral(1))",
                "years and months duration",
                "durationDivide(duration(\"P1Y1M\"), number(\"1\"))",
                this.lib.durationDivide(this.lib.duration("P1Y1M"), this.lib.number("1")),
                this.lib.duration("P1Y1M"));

        doExpressionTest(entries, "", String.format("%s %s %s", number, "*", yearsAndMonthsDuration),
                "Multiplication(*,NumericLiteral(1),DateTimeLiteral(duration, \"P1Y1M\"))",
                "years and months duration",
                "durationMultiply(duration(\"P1Y1M\"), number(\"1\"))",
                this.lib.durationMultiply(this.lib.duration("P1Y1M"), this.lib.number("1")),
                this.lib.duration("P1Y1M"));
/*
        // should not be in standard
        doExpressionTest(entries, "", String.format("%s %s %s", number, "/", yearsAndMonthsDuration),
                "Multiplication(/,NumericLiteral(1),DateTimeLiteral(duration, \"P1Y1M\"))",
                "years and months duration",
                "durationDivide(duration(\"P1Y1M\"), number(\"1\"))",
                lib.durationDivide(lib.duration("P1Y1M"), lib.number("1")),
                "");
*/

        // days and time duration
        doExpressionTest(entries, "", String.format("%s %s %s", daysAndTimeDuration, "*", number),
                "Multiplication(*,DateTimeLiteral(duration, \"P1DT1H\"),NumericLiteral(1))",
                "days and time duration",
                "durationMultiply(duration(\"P1DT1H\"), number(\"1\"))",
                this.lib.durationMultiply(this.lib.duration("P1DT1H"), this.lib.number("1")),
                this.lib.duration("P1DT1H"));
        doExpressionTest(entries, "", String.format("%s %s %s", daysAndTimeDuration, "/", number),
                "Multiplication(/,DateTimeLiteral(duration, \"P1DT1H\"),NumericLiteral(1))",
                "days and time duration",
                "durationDivide(duration(\"P1DT1H\"), number(\"1\"))",
                this.lib.durationDivide(this.lib.duration("P1DT1H"), this.lib.number("1")),
                this.lib.duration("P1DT1H"));

        doExpressionTest(entries, "", String.format("%s %s %s", number, "*", daysAndTimeDuration),
                "Multiplication(*,NumericLiteral(1),DateTimeLiteral(duration, \"P1DT1H\"))",
                "days and time duration",
                "durationMultiply(duration(\"P1DT1H\"), number(\"1\"))",
                this.lib.durationMultiply(this.lib.duration("P1DT1H"), this.lib.number("1")),
                this.lib.duration("P1DT1H"));
/*
        // should not be in standard
        doExpressionTest(entries, "", String.format("%s %s %s", number, "/", daysAndTimeDuration),
                "Multiplication(/,NumericLiteral(1),DateTimeLiteral(duration, \"P1DT1H\"))",
                "days and time duration",
                "durationDivide(duration(\"P1DT1H\"), number(\"1\"))",
                lib.durationDivide(lib.duration("P1DT1H"), lib.number("1")),
                "");
*/
    }

    @Test
    public void testExponentiation() {
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, this.lib.number("1")));

        doExpressionTest(entries, "", "2 ** 2",
                "Exponentiation(NumericLiteral(2),NumericLiteral(2))",
                "number",
                "numericExponentiation(number(\"2\"), number(\"2\"))",
                this.lib.numericExponentiation(this.lib.number("2"), this.lib.number("2")),
                this.lib.number("4"));
    }

    @Test
    public void testArithmeticNegation() {
        String number = "1";
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, this.lib.number("1")));

        doExpressionTest(entries, "", String.format("- %s", number),
                "ArithmeticNegation(NumericLiteral(1))",
                "number",
                "numericUnaryMinus(number(\"1\"))",
                this.lib.numericUnaryMinus(this.lib.number("1")),
                this.lib.number("-1"));

        doExpressionTest(entries, "", String.format("-- %s", number),
                "NumericLiteral(1)",
                "number",
                "number(\"1\")",
                this.lib.number("1"),
                this.lib.number("1"));

        doExpressionTest(entries, "", String.format("--- %s", number),
                "ArithmeticNegation(NumericLiteral(1))",
                "number",
                "numericUnaryMinus(number(\"1\"))",
                this.lib.numericUnaryMinus(this.lib.number("1")),
                this.lib.number("-1"));
    }

    @Test(expected = SemanticError.class)
    public void testArithmeticNegationOnIncorrectOperands() {
        String yearsAndMonthsDuration = "duration(\"P1Y1M\")";
        String daysAndTimeDuration = "duration(\"P1DT1H\")";

        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, this.lib.number("1")));
        doExpressionTest(entries, "", String.format("- %s", yearsAndMonthsDuration),
                "ArithmeticNegation(DateTimeLiteral(duration, \"P1Y1M\"))",
                "years and months duration",
                "numericUnaryMinus(duration(\"P1Y1M\"))",
                null,
                null);
        doExpressionTest(entries, "", String.format("- %s", daysAndTimeDuration),
                "ArithmeticNegation(DateTimeLiteral(duration, \"P1DT1H\"))",
                "days and time duration",
                "numericUnaryMinus(duration(\"P1DT1H\"))",
                null,
                null);
    }

    @Test
    public void testComplexArithmeticExpression() {
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, this.lib.number("1")));

        doExpressionTest(entries, "", "-1",
                "ArithmeticNegation(NumericLiteral(1))",
                "number",
                "numericUnaryMinus(number(\"1\"))",
                this.lib.numericUnaryMinus(this.lib.number("1")),
                this.lib.number("-1"));
        doExpressionTest(entries, "", "-(1)",
                "ArithmeticNegation(NumericLiteral(1))",
                "number",
                "numericUnaryMinus(number(\"1\"))",
                this.lib.numericUnaryMinus(this.lib.number("1")),
                this.lib.number("-1"));
        doExpressionTest(entries, "", "1 ** 2",
                "Exponentiation(NumericLiteral(1),NumericLiteral(2))",
                "number",
                "numericExponentiation(number(\"1\"), number(\"2\"))",
                this.lib.numericExponentiation(this.lib.number("1"), this.lib.number("2")),
                this.lib.number("1"));
        doExpressionTest(entries, "", "1 * 2 * 3",
                "Multiplication(*,Multiplication(*,NumericLiteral(1),NumericLiteral(2)),NumericLiteral(3))",
                "number",
                "numericMultiply(numericMultiply(number(\"1\"), number(\"2\")), number(\"3\"))",
                this.lib.numericMultiply(this.lib.numericMultiply(this.lib.number("1"), this.lib.number("2")), this.lib.number("3")),
                this.lib.number("6"));
        doExpressionTest(entries, "", "1 + 2 + 3",
                "Addition(+,Addition(+,NumericLiteral(1),NumericLiteral(2)),NumericLiteral(3))",
                "number",
                "numericAdd(numericAdd(number(\"1\"), number(\"2\")), number(\"3\"))",
                this.lib.numericAdd(this.lib.numericAdd(this.lib.number("1"), this.lib.number("2")), this.lib.number("3")),
                this.lib.number("6"));
        doExpressionTest(entries, "", "(1 + 2)",
                "Addition(+,NumericLiteral(1),NumericLiteral(2))",
                "number",
                "numericAdd(number(\"1\"), number(\"2\"))",
                this.lib.numericAdd(this.lib.number("1"), this.lib.number("2")),
                this.lib.number("3"));
        doExpressionTest(entries, "", "1 + (2 + 3)",
                "Addition(+,NumericLiteral(1),Addition(+,NumericLiteral(2),NumericLiteral(3)))",
                "number",
                "numericAdd(number(\"1\"), numericAdd(number(\"2\"), number(\"3\")))",
                this.lib.numericAdd(this.lib.number("1"), this.lib.numericAdd(this.lib.number("2"), this.lib.number("3"))),
                this.lib.number("6"));
        doExpressionTest(entries, "", "(1 + 2) + 3",
                "Addition(+,Addition(+,NumericLiteral(1),NumericLiteral(2)),NumericLiteral(3))",
                "number",
                "numericAdd(numericAdd(number(\"1\"), number(\"2\")), number(\"3\"))",
                this.lib.numericAdd(this.lib.numericAdd(this.lib.number("1"), this.lib.number("2")), this.lib.number("3")),
                this.lib.number("6"));
        doExpressionTest(entries, "", "(1 + 2) * 3",
                "Multiplication(*,Addition(+,NumericLiteral(1),NumericLiteral(2)),NumericLiteral(3))",
                "number",
                "numericMultiply(numericAdd(number(\"1\"), number(\"2\")), number(\"3\"))",
                this.lib.numericMultiply(this.lib.numericAdd(this.lib.number("1"), this.lib.number("2")), this.lib.number("3")),
                this.lib.number("9"));
    }

    @Test
    public void testInstanceOfExpression() {
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, lib.number("1")));

        doExpressionTest(entries, "", "3 instance of number",
                "InstanceOfExpression(NumericLiteral(3), NamedTypeExpression(number))",
                "boolean",
                "number(\"3\") instanceof java.math.BigDecimal",
                lib.number("3") instanceof java.math.BigDecimal,
                true);
        doExpressionTest(entries, "", "\"abc\" instance of string",
                "InstanceOfExpression(StringLiteral(\"abc\"), NamedTypeExpression(string))",
                "boolean",
                "\"abc\" instanceof String",
                "abc" instanceof String,
                true);
        doExpressionTest(entries, "", "true instance of boolean",
                "InstanceOfExpression(BooleanLiteral(true), NamedTypeExpression(boolean))",
                "boolean",
                "Boolean.TRUE instanceof Boolean",
                Boolean.TRUE instanceof Boolean,
                true);
        doExpressionTest(entries, "", "date(\"2011-01-03\") instance of date",
                "InstanceOfExpression(DateTimeLiteral(date, \"2011-01-03\"), NamedTypeExpression(date))",
                "boolean",
                "date(\"2011-01-03\") instanceof javax.xml.datatype.XMLGregorianCalendar",
                lib.date("2011-01-03") instanceof javax.xml.datatype.XMLGregorianCalendar,
                true);
        doExpressionTest(entries, "", "time(\"12:00:00Z\") instance of time",
                "InstanceOfExpression(DateTimeLiteral(time, \"12:00:00Z\"), NamedTypeExpression(time))",
                "boolean",
                "time(\"12:00:00Z\") instanceof javax.xml.datatype.XMLGregorianCalendar",
                lib.time("12:00:00Z") instanceof javax.xml.datatype.XMLGregorianCalendar,
                true);
        doExpressionTest(entries, "", "date and time(\"2016-03-01T12:00:00Z\") instance of date and time",
                "InstanceOfExpression(DateTimeLiteral(date and time, \"2016-03-01T12:00:00Z\"), NamedTypeExpression(date and time))",
                "boolean",
                "dateAndTime(\"2016-03-01T12:00:00Z\") instanceof javax.xml.datatype.XMLGregorianCalendar",
                lib.dateAndTime("2016-03-01T12:00:00Z") instanceof javax.xml.datatype.XMLGregorianCalendar,
                true);
        doExpressionTest(entries, "", "duration(\"P1Y1M\") instance of years and months duration",
                "InstanceOfExpression(DateTimeLiteral(duration, \"P1Y1M\"), NamedTypeExpression(years and months duration))",
                "boolean",
                "duration(\"P1Y1M\") instanceof javax.xml.datatype.Duration",
                lib.duration("P1Y1M") instanceof javax.xml.datatype.Duration,
                true);
        doExpressionTest(entries, "", "duration(\"P1DT1H\") instance of days and time duration",
                "InstanceOfExpression(DateTimeLiteral(duration, \"P1DT1H\"), NamedTypeExpression(days and time duration))",
                "boolean",
                "duration(\"P1DT1H\") instanceof javax.xml.datatype.Duration",
                lib.duration("P1Y1M") instanceof javax.xml.datatype.Duration,
                true);
        doExpressionTest(entries, "", "(function () 4) instance of function <> -> number",
                "InstanceOfExpression(FunctionDefinition(, NumericLiteral(4), false), FunctionTypeExpression( -> NamedTypeExpression(number)))",
                "boolean",
                null,
                null,
                null);
    }

    @Test
    public void testPostfixExpression() {
        ItemDefinitionType employeeTableType = new ItemDefinitionType("tEmployeeTable")
                .addMember("id", Arrays.asList(), STRING)
                .addMember("name", Arrays.asList(), STRING)
                .addMember("deptNum", Arrays.asList(), NUMBER)
                ;
        ItemDefinitionType deptTableType = new ItemDefinitionType("tDeptTable")
                .addMember("number", Arrays.asList(), NUMBER)
                .addMember("name", Arrays.asList(), STRING)
                .addMember("manager", Arrays.asList(), STRING)
                ;
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("EmployeeTable", new ListType(employeeTableType), null),
                new EnvironmentEntry("DeptTable", new ListType(deptTableType), null),
                new EnvironmentEntry("LastName", STRING, null)
        );
        doExpressionTest(entries, "", "DeptTable[number = EmployeeTable[name=LastName].deptNum[1]].manager[1]",
                "FilterExpression(PathExpression(FilterExpression(Name(DeptTable), Relational(=,PathExpression(Name(item), number),FilterExpression(PathExpression(FilterExpression(Name(EmployeeTable), Relational(=,PathExpression(Name(item), name),Name(LastName))), deptNum), NumericLiteral(1)))), manager), NumericLiteral(1))",
                "string",
                "(String)(elementAt(deptTable.stream().filter(item -> numericEqual(((java.math.BigDecimal)(item != null ? item.getNumber() : null)), " +
                        "(java.math.BigDecimal)(elementAt(employeeTable.stream().filter(item_1_ -> stringEqual(((String)(item_1_ != null ? item_1_.getName() : null)), lastName)).collect(Collectors.toList()).stream().map(x -> ((java.math.BigDecimal)(x != null ? x.getDeptNum() : null))).collect(Collectors.toList()), number(\"1\"))))).collect(Collectors.toList()).stream()" +
                        ".map(x -> ((String)(x != null ? x.getManager() : null))).collect(Collectors.toList()), number(\"1\")))",
                null,
                null
        );
    }

    @Test
    public void testFilterExpression() {
        List<NUMBER> source = Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3"));
        ContextType employeeType = new ContextType();
        employeeType.addMember("id", Arrays.asList(), NumberType.NUMBER);
        employeeType.addMember("dept", Arrays.asList(), NumberType.NUMBER);
        employeeType.addMember("name", Arrays.asList(), StringType.STRING);

        Type employeeListType = new ListType(employeeType);
        List<Context> employeeValue = Arrays.asList(
                new Context().add("id", lib.number("7792")).add("dept", lib.number("10")).add("name", "Clark"),
                new Context().add("id", lib.number("7973")).add("dept", lib.number("20")).add("name", "Adams"),
                new Context().add("id", lib.number("7973")).add("dept", lib.number("20")).add("name", "Ford")
        );
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("source", ListType.NUMBER_LIST, source),
                new EnvironmentEntry("employee", employeeListType, employeeValue)
        );

        // boolean filter
        doExpressionTest(entries, "", "[{item: 1}, {item: 2}, {item: 3}][item >= 2]",
                "FilterExpression(ListLiteral(Context(ContextEntry(ContextEntryKey(item) = NumericLiteral(1))),Context(ContextEntry(ContextEntryKey(item) = NumericLiteral(2))),Context(ContextEntry(ContextEntryKey(item) = NumericLiteral(3)))), Relational(>=,PathExpression(Name(item), item),NumericLiteral(2)))",
                "ListType(ContextType(item = number))",
                "asList(new com.gs.dmn.runtime.Context().add(\"item\", number(\"1\")), new com.gs.dmn.runtime.Context().add(\"item\", number(\"2\")), new com.gs.dmn.runtime.Context().add(\"item\", number(\"3\"))).stream().filter(item -> numericGreaterEqualThan(((java.math.BigDecimal)((com.gs.dmn.runtime.Context)item).get(\"item\")), number(\"2\"))).collect(Collectors.toList())",
                lib.asList(new com.gs.dmn.runtime.Context().add("item", lib.number("1")), new com.gs.dmn.runtime.Context().add("item", lib.number("2")), new com.gs.dmn.runtime.Context().add("item", lib.number("3"))).stream().filter(item -> lib.numericGreaterEqualThan((NUMBER)((Context)item).get("item"), lib.number("2"))).collect(Collectors.toList()),
                lib.asList(new Context().add("item", lib.number("2")), new Context().add("item", lib.number("3"))));
        doExpressionTest(entries, "", "source[true]",
                "FilterExpression(Name(source), BooleanLiteral(true))",
                "ListType(number)",
                "source.stream().filter(item -> Boolean.TRUE).collect(Collectors.toList())",
                source.stream().filter(item -> Boolean.TRUE).collect(Collectors.toList()),
                source);
        doExpressionTest(entries, "", "[1, 2][true]",
                "FilterExpression(ListLiteral(NumericLiteral(1),NumericLiteral(2)), BooleanLiteral(true))",
                "ListType(number)",
                "asList(number(\"1\"), number(\"2\")).stream().filter(item -> Boolean.TRUE).collect(Collectors.toList())",
                Arrays.asList(lib.number("1"), lib.number("2")).stream().filter(item -> true).collect(Collectors.toList()),
                Arrays.asList(lib.number("1"), lib.number("2")));
        doExpressionTest(entries, "", "1[true]",
                "FilterExpression(NumericLiteral(1), BooleanLiteral(true))",
                "ListType(number)",
                "asList(number(\"1\")).stream().filter(item -> Boolean.TRUE).collect(Collectors.toList())",
                Arrays.asList(lib.number("1")).stream().filter(item -> true).collect(Collectors.toList()),
                Arrays.asList(lib.number("1")));
        doExpressionTest(entries, "", "[1, 2, 3, 4][item > 2]",
                "FilterExpression(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3),NumericLiteral(4)), Relational(>,Name(item),NumericLiteral(2)))",
                "ListType(number)",
                "asList(number(\"1\"), number(\"2\"), number(\"3\"), number(\"4\")).stream().filter(item -> numericGreaterThan(item, number(\"2\"))).collect(Collectors.toList())",
                Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3"), lib.number("4")).stream().filter(item -> lib.numericGreaterThan(item, lib.number("2"))).collect(Collectors.toList()),
                Arrays.asList(lib.number("3"), lib.number("4")));
        doExpressionTest(entries, "", "employee[item.dept = 20]",
                "FilterExpression(Name(employee), Relational(=,PathExpression(Name(item), dept),NumericLiteral(20)))",
                "ListType(ContextType(id = number, dept = number, name = string))",
                "employee.stream().filter(item -> numericEqual(((java.math.BigDecimal)((com.gs.dmn.runtime.Context)item).get(\"dept\")), number(\"20\"))).collect(Collectors.toList())",
                employeeValue.stream().filter(item -> lib.numericEqual((NUMBER)((Context)item).get("dept"), lib.number("20"))).collect(Collectors.toList()),
                Arrays.asList(employeeValue.get(1), employeeValue.get(2)));
        doExpressionTest(entries, "", "employee[item.dept = 20].name",
                "PathExpression(FilterExpression(Name(employee), Relational(=,PathExpression(Name(item), dept),NumericLiteral(20))), name)",
                "ListType(string)",
                "employee.stream().filter(item -> numericEqual(((java.math.BigDecimal)((com.gs.dmn.runtime.Context)item).get(\"dept\")), number(\"20\"))).collect(Collectors.toList()).stream().map(x -> ((String)((com.gs.dmn.runtime.Context)x).get(\"name\"))).collect(Collectors.toList())",
                employeeValue.stream().filter(item -> lib.numericEqual((NUMBER)((Context)item).get("dept"), lib.number("20"))).collect(Collectors.toList()).stream().map(x -> x.get("name")).collect(Collectors.toList()),
                Arrays.asList(employeeValue.get(1).get("name"), employeeValue.get(2).get("name")));
        doExpressionTest(entries, "", "employee[dept = 20].name",
                "PathExpression(FilterExpression(Name(employee), Relational(=,PathExpression(Name(item), dept),NumericLiteral(20))), name)",
                "ListType(string)",
                "employee.stream().filter(item -> numericEqual(((java.math.BigDecimal)((com.gs.dmn.runtime.Context)item).get(\"dept\")), number(\"20\"))).collect(Collectors.toList()).stream().map(x -> ((String)((com.gs.dmn.runtime.Context)x).get(\"name\"))).collect(Collectors.toList())",
                employeeValue.stream().filter(item -> lib.numericEqual((NUMBER)((Context)item).get("dept"), lib.number("20"))).collect(Collectors.toList()).stream().map(x -> (String) x.get("name")).collect(Collectors.toList()),
                Arrays.asList(employeeValue.get(1).get("name"), employeeValue.get(2).get("name")));

        // numeric filter
        doExpressionTest(entries, "", "[1, 2][0]",
                "FilterExpression(ListLiteral(NumericLiteral(1),NumericLiteral(2)), NumericLiteral(0))",
                "number",
                "(java.math.BigDecimal)(elementAt(asList(number(\"1\"), number(\"2\")), number(\"0\")))",
                lib.elementAt(lib.asList(lib.number("1"), lib.number("2")), lib.number("0")),
                null);
        doExpressionTest(entries, "", "[1, 2][-1]",
                "FilterExpression(ListLiteral(NumericLiteral(1),NumericLiteral(2)), ArithmeticNegation(NumericLiteral(1)))",
                "number",
                "(java.math.BigDecimal)(elementAt(asList(number(\"1\"), number(\"2\")), numericUnaryMinus(number(\"1\"))))",
                lib.elementAt(lib.asList(lib.number("1"), lib.number("2")), lib.numericUnaryMinus(lib.number("1"))),
                lib.number("2"));
        doExpressionTest(entries, "", "[1, 2][-2]",
                "FilterExpression(ListLiteral(NumericLiteral(1),NumericLiteral(2)), ArithmeticNegation(NumericLiteral(2)))",
                "number",
                "(java.math.BigDecimal)(elementAt(asList(number(\"1\"), number(\"2\")), numericUnaryMinus(number(\"2\"))))",
                lib.elementAt(lib.asList(lib.number("1"), lib.number("2")), lib.numericUnaryMinus(lib.number("2"))),
                lib.number("1"));
        doExpressionTest(entries, "", "1[1]",
                "FilterExpression(NumericLiteral(1), NumericLiteral(1))",
                "number",
                "(java.math.BigDecimal)(elementAt(asList(number(\"1\")), number(\"1\")))",
                lib.elementAt(lib.asList(lib.number("1")), lib.number("1")),
                lib.number("1"));

        // context filter
        doExpressionTest(entries, "", "[{x:1, y:2}, {x:2, y:3}] [item.x = 1]",
                "FilterExpression(ListLiteral(Context(ContextEntry(ContextEntryKey(x) = NumericLiteral(1)),ContextEntry(ContextEntryKey(y) = NumericLiteral(2))),Context(ContextEntry(ContextEntryKey(x) = NumericLiteral(2)),ContextEntry(ContextEntryKey(y) = NumericLiteral(3)))), Relational(=,PathExpression(Name(item), x),NumericLiteral(1)))",
                "ListType(ContextType(x = number, y = number))",
                "asList(new com.gs.dmn.runtime.Context().add(\"x\", number(\"1\")).add(\"y\", number(\"2\")), new com.gs.dmn.runtime.Context().add(\"x\", number(\"2\")).add(\"y\", number(\"3\"))).stream().filter(item -> numericEqual(((java.math.BigDecimal)((com.gs.dmn.runtime.Context)item).get(\"x\")), number(\"1\"))).collect(Collectors.toList())",
                Arrays.asList(new com.gs.dmn.runtime.Context().add("x", lib.number("1")).add("y", lib.number("2")), new com.gs.dmn.runtime.Context().add("x", lib.number("2")).add("y", lib.number("3"))).stream().filter(item -> lib.numericEqual((NUMBER)((Context)item).get("x"), lib.number("1"))).collect(Collectors.toList()),
                Arrays.asList(new com.gs.dmn.runtime.Context().add("x", lib.number("1")).add("y", lib.number("2"))));
    }

    @Test
    public void testConversionFunctions() {
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, this.lib.number("1")));

        doExpressionTest(entries, "", "date(\"2016-03-01\")",
                "DateTimeLiteral(date, \"2016-03-01\")",
                "date",
                "date(\"2016-03-01\")",
                this.lib.date("2016-03-01"),
                this.lib.date("2016-03-01"));
        doExpressionTest(entries, "", "date(\"2016-03-01T12:00:00Z\")",
                "DateTimeLiteral(date, \"2016-03-01T12:00:00Z\")",
                "date",
                "date(\"2016-03-01T12:00:00Z\")",
                this.lib.date("2016-03-01T12:00:00Z"),
                null);
        doExpressionTest(entries, "", "time(\"12:00:00Z\")",
                "DateTimeLiteral(time, \"12:00:00Z\")",
                "time",
                "time(\"12:00:00Z\")",
                this.lib.time("12:00:00Z"),
                this.lib.time("12:00:00Z"));
        doExpressionTest(entries, "", "time(\"2016-03-01T12:00:00Z\")",
                "DateTimeLiteral(time, \"2016-03-01T12:00:00Z\")",
                "time",
                "time(\"2016-03-01T12:00:00Z\")",
                this.lib.time("2016-03-01T12:00:00Z"),
                null);
        doExpressionTest(entries, "", "date and time(\"2016-03-01T12:00:00Z\")",
                "DateTimeLiteral(date and time, \"2016-03-01T12:00:00Z\")",
                "date and time",
                "dateAndTime(\"2016-03-01T12:00:00Z\")",
                this.lib.dateAndTime("2016-03-01T12:00:00Z"),
                this.lib.dateAndTime("2016-03-01T12:00:00Z"));
        doExpressionTest(entries, "", "duration(\"P1Y1M\")",
                "DateTimeLiteral(duration, \"P1Y1M\")",
                "years and months duration",
                "duration(\"P1Y1M\")",
                this.lib.duration("P1Y1M"),
                this.lib.duration("P1Y1M"));
        doExpressionTest(entries, "", "duration(\"P1DT1H\")",
                "DateTimeLiteral(duration, \"P1DT1H\")",
                "days and time duration",
                "duration(\"P1DT1H\")",
                this.lib.duration("P1DT1H"),
                this.lib.duration("P1DT1H"));
    }

    @Ignore
    @Test(expected = DMNRuntimeException.class)
    public void testFunctionInvocationWhenMultipleMatch() {
        List<EnvironmentEntry> entries = Arrays.asList();

        // Multiple matches for date(null)
        doExpressionTest(entries, "", "date(null)",
                "FunctionInvocation(Name(date) -> PositionalParameters(NullLiteral()))",
                "date",
                "contains(null)",
                lib.date((DATE) null),
                true);
    }

    @Test
    public void testPathExpression() {
        ItemDefinitionType type = new ItemDefinitionType("PrivateFundRequirements").addMember("HierarchyNode", Arrays.asList(), STRING);
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("PrivateFundRequirements", type, null));

        doExpressionTest(entries, "", "[{b: 1}, {b: [2.1, 2.2]}, {b: 3}, {b: 4}, {b: 5}].b = [1, [2.1, 2.2], 3, 4, 5]",
                "Relational(=,PathExpression(ListLiteral(Context(ContextEntry(ContextEntryKey(b) = NumericLiteral(1))),Context(ContextEntry(ContextEntryKey(b) = ListLiteral(NumericLiteral(2.1),NumericLiteral(2.2)))),Context(ContextEntry(ContextEntryKey(b) = NumericLiteral(3))),Context(ContextEntry(ContextEntryKey(b) = NumericLiteral(4))),Context(ContextEntry(ContextEntryKey(b) = NumericLiteral(5)))), b),ListLiteral(NumericLiteral(1),ListLiteral(NumericLiteral(2.1),NumericLiteral(2.2)),NumericLiteral(3),NumericLiteral(4),NumericLiteral(5)))",
                "boolean",
                "listEqual(asList(new com.gs.dmn.runtime.Context().add(\"b\", number(\"1\")), new com.gs.dmn.runtime.Context().add(\"b\", asList(number(\"2.1\"), number(\"2.2\"))), new com.gs.dmn.runtime.Context().add(\"b\", number(\"3\")), new com.gs.dmn.runtime.Context().add(\"b\", number(\"4\")), new com.gs.dmn.runtime.Context().add(\"b\", number(\"5\"))).stream().map(x -> ((com.gs.dmn.runtime.Context)(x)).get(\"b\", asList())).collect(Collectors.toList()), asList(number(\"1\"), asList(number(\"2.1\"), number(\"2.2\")), number(\"3\"), number(\"4\"), number(\"5\")))",
                null,
                null);

        doExpressionTest(entries, "", "date(\"2018-12-10\").weekday",
                "PathExpression(DateTimeLiteral(date, \"2018-12-10\"), weekday)",
                "number",
                "weekday(date(\"2018-12-10\"))",
                lib.weekday(lib.date("2018-12-10")),
                lib.number("1"));
        doExpressionTest(entries, "", "date and time(\"2018-12-10T10:30:01\").weekday",
                "PathExpression(DateTimeLiteral(date and time, \"2018-12-10T10:30:01\"), weekday)",
                "number",
                "weekday(dateAndTime(\"2018-12-10T10:30:01\"))",
                lib.weekday((DATE) lib.dateAndTime("2018-12-10T10:30:01")),
                lib.number("1"));

        doExpressionTest(entries, "", "time(\"10:30:01\").hour",
                "PathExpression(DateTimeLiteral(time, \"10:30:01\"), hour)",
                "number",
                "hour(time(\"10:30:01\"))",
                lib.hour(lib.time("10:30:01")),
                lib.number("10"));
        doExpressionTest(entries, "", "date and time(\"2018-12-10T10:30:01\").hour",
                "PathExpression(DateTimeLiteral(date and time, \"2018-12-10T10:30:01\"), hour)",
                "number",
                "hour(dateAndTime(\"2018-12-10T10:30:01\"))",
                lib.hour((TIME) lib.dateAndTime("2018-12-10T10:30:01")),
                lib.number("10"));
    }

    @Test
    public void testQualifiedName() {
        Type bType = new ItemDefinitionType("b").addMember("c", Arrays.asList("C"), STRING);
        Type aType = new ItemDefinitionType("a").addMember("b", Arrays.asList("B"), bType);
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("a", aType, null));

        doSimpleExpressionsTest(entries, "a.b.c",
                "PathExpression(PathExpression(Name(a), b), c)",
                "string",
                "((String)(((type.B)(a != null ? a.getB() : null)) != null ? ((type.B)(a != null ? a.getB() : null)).getC() : null))",
                null,
                null);
    }

    @Test
    public void testDateAndTimeProperties() {
        List<EnvironmentEntry> entries = Arrays.asList(
        );

        doExpressionTest(entries, "", "date and time(\"2018-12-10T10:30:00\").time offset",
                "PathExpression(DateTimeLiteral(date and time, \"2018-12-10T10:30:00\"), time offset)",
                "days and time duration",
                "timeOffset(dateAndTime(\"2018-12-10T10:30:00\"))",
                lib.timeOffset((TIME) lib.dateAndTime("2018-12-10T10:30:00")),
                null
        );
        doExpressionTest(entries, "", "date and time(\"2018-12-10T10:30:00@Etc/UTC\").timezone",
                "PathExpression(DateTimeLiteral(date and time, \"2018-12-10T10:30:00@Etc/UTC\"), timezone)",
                "string",
                "timezone(dateAndTime(\"2018-12-10T10:30:00@Etc/UTC\"))",
                lib.timezone((TIME) lib.dateAndTime("2018-12-10T10:30:00@Etc/UTC")),
                "Etc/UTC"
        );
        doExpressionTest(entries, "", "date and time(\"2018-12-10T10:30:00\").timezone",
                "PathExpression(DateTimeLiteral(date and time, \"2018-12-10T10:30:00\"), timezone)",
                "string",
                "timezone(dateAndTime(\"2018-12-10T10:30:00\"))",
                lib.timezone((TIME) lib.dateAndTime("2018-12-10T10:30:00")),
                null
        );
        doExpressionTest(entries, "", "time(\"10:30:00\").time offset",
                "PathExpression(DateTimeLiteral(time, \"10:30:00\"), time offset)",
                "days and time duration",
                "timeOffset(time(\"10:30:00\"))",
                lib.timeOffset(lib.time("10:30:00")),
                null
        );
        doExpressionTest(entries, "", "time(\"10:30:00@Etc/UTC\").timezone",
                "PathExpression(DateTimeLiteral(time, \"10:30:00@Etc/UTC\"), timezone)",
                "string",
                "timezone(time(\"10:30:00@Etc/UTC\"))",
                lib.timezone(lib.time("10:30:00@Etc/UTC")),
                "Etc/UTC"
        );
        doExpressionTest(entries, "", "time(\"10:30:00\").timezone",
                "PathExpression(DateTimeLiteral(time, \"10:30:00\"), timezone)",
                "string",
                "timezone(time(\"10:30:00\"))",
                lib.timezone(lib.time("10:30:00")),
                null
        );
    }

    @Test
    public void testPrimaryExpression() {
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, this.lib.number("1")));

        doExpressionTest(entries, "", "(123.45)",
                "NumericLiteral(123.45)",
                "number",
                "number(\"123.45\")",
                this.lib.number("123.45"),
                this.lib.number("123.45"));
        doExpressionTest(entries, "", "(1 + 2)",
                "Addition(+,NumericLiteral(1),NumericLiteral(2))",
                "number",
                "numericAdd(number(\"1\"), number(\"2\"))",
                this.lib.numericAdd(this.lib.number("1"), this.lib.number("2")),
                this.lib.number("3"));
    }

    @Test
    public void testNull() {
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, lib.number("1")));

        doExpressionTest(entries, "", "null",
                "NullLiteral()",
                "NullType",
                "null",
                null,
                null);
    }

    @Test
    public void testFunctionDefinition() {
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, lib.number("1")));

        doExpressionTest(entries, "", "function (x : feel.string, y : feel.string) x + y",
                "FunctionDefinition(FormalParameter(x, string),FormalParameter(y, string), Addition(+,Name(x),Name(y)), false)",
                "FEELFunctionType(FormalParameter(x, string), FormalParameter(y, string), string, false)",
                null,
                null,
                null);
        doExpressionTest(entries, "", "function (x , y) x + y",
                "FunctionDefinition(FormalParameter(x, null),FormalParameter(y, null), Addition(+,Name(x),Name(y)), false)",
                "FEELFunctionType(FormalParameter(x, null), FormalParameter(y, null), Any, false)",
                null,
                null,
                null);

        doExpressionTest(entries, "", "function (x : feel.string, y : feel.string) external { " +
                        "java: {class : \"name\", methodSignature: \"signature\" } }",
                "FunctionDefinition(FormalParameter(x, string),FormalParameter(y, string), Context(ContextEntry(ContextEntryKey(java) = Context(ContextEntry(ContextEntryKey(class) = StringLiteral(\"name\")),ContextEntry(ContextEntryKey(methodSignature) = StringLiteral(\"signature\"))))), true)",
                "FEELFunctionType(FormalParameter(x, string), FormalParameter(y, string), Any, true)",
                null,
                null,
                null);
        doExpressionTest(entries, "", "function (x , y) external { " +
                        "java: {class : \"name\", methodSignature: \"signature\" } }",
                "FunctionDefinition(FormalParameter(x, null),FormalParameter(y, null), Context(ContextEntry(ContextEntryKey(java) = Context(ContextEntry(ContextEntryKey(class) = StringLiteral(\"name\")),ContextEntry(ContextEntryKey(methodSignature) = StringLiteral(\"signature\"))))), true)",
                "FEELFunctionType(FormalParameter(x, null), FormalParameter(y, null), Any, true)",
                null,
                null,
                null);
        doExpressionTest(entries, "", "function(a: feel.number, b: feel.number) external {" +
                        "java: {class: \"com.gs.dmn.simple_decision_with_user_function.Sum\", methodSignature: \"add(a, b)\", returnType : \"number\"}" +
                        "}",
                "FunctionDefinition(FormalParameter(a, number),FormalParameter(b, number), Context(ContextEntry(ContextEntryKey(java) = Context(ContextEntry(ContextEntryKey(class) = StringLiteral(\"com.gs.dmn.simple_decision_with_user_function.Sum\")),ContextEntry(ContextEntryKey(methodSignature) = StringLiteral(\"add(a, b)\")),ContextEntry(ContextEntryKey(returnType) = StringLiteral(\"number\"))))), true)",
                "FEELFunctionType(FormalParameter(a, number), FormalParameter(b, number), Any, true)",
                null,
                null,
                null
        );
    }

    @Test
    public void testList() {
        NUMBER number = lib.number("1");
        List<NUMBER> list = Arrays.asList(lib.number("1"));

        List<EnvironmentEntry> expressionPairs = Arrays.asList(
                new EnvironmentEntry("number", NUMBER, number),
                new EnvironmentEntry("list", ListType.NUMBER_LIST, list));
        List<EnvironmentEntry> testPairs = Arrays.asList(
                new EnvironmentEntry("number", NUMBER, number),
                new EnvironmentEntry("list", ListType.NUMBER_LIST, list));

        doExpressionTest(expressionPairs, "", "[1, 2, 3]",
                "ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3))",
                "ListType(number)",
                "asList(number(\"1\"), number(\"2\"), number(\"3\"))",
                Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3")),
                Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3")));
        doExpressionTest(expressionPairs, "", "[]",
                "ListLiteral()",
                "ListType(Any)",
                "asList()",
                Arrays.asList(),
                Arrays.asList());

        doExpressionTest(expressionPairs, "", "[1]",
                "ListLiteral(NumericLiteral(1))",
                "ListType(number)",
                "asList(number(\"1\"))",
                Arrays.asList(lib.number("1")),
                Arrays.asList(lib.number("1")));

        doExpressionTest(expressionPairs, "", "[1, 2, 3]",
                "ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3))",
                "ListType(number)",
                "asList(number(\"1\"), number(\"2\"), number(\"3\"))",
                Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3")),
                Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3")));

        doExpressionTest(expressionPairs, "number", "[1, <2, [3..4]]",
                "ListLiteral(NumericLiteral(1),OperatorTest(<,NumericLiteral(2)),IntervalTest(false,NumericLiteral(3),false,NumericLiteral(4)))",
                "ListType(Any)",
                "asList(number(\"1\"), numericLessThan(number, number(\"2\")), booleanAnd(numericGreaterEqualThan(number, number(\"3\")), numericLessEqualThan(number, number(\"4\"))))",
                null,
                null);

        doExpressionTest(expressionPairs, "", "true[0]",
                "FilterExpression(BooleanLiteral(true), NumericLiteral(0))",
                "boolean",
                "(Boolean)(elementAt(asList(Boolean.TRUE), number(\"0\")))",
                lib.elementAt(lib.asList(Boolean.TRUE), lib.number("0")),
                null);

        doExpressionTest(expressionPairs, "", "100[0]",
                "FilterExpression(NumericLiteral(100), NumericLiteral(0))",
                "number",
                "(java.math.BigDecimal)(elementAt(asList(number(\"100\")), number(\"0\")))",
                lib.elementAt(lib.asList(lib.number("100")), lib.number("0")),
                null);

        doExpressionTest(expressionPairs, "", "\"foo\"[0]",
                "FilterExpression(StringLiteral(\"foo\"), NumericLiteral(0))",
                "string",
                "(String)(elementAt(asList(\"foo\"), number(\"0\")))",
                lib.elementAt(lib.asList("foo"), lib.number("0")),
                null);

        doUnaryTestsTest(testPairs, "list", "[]",
                "PositiveUnaryTests(ListTest(ListLiteral()))",
                "TupleType(boolean)",
                "(listEqual(list, asList()))",
                (lib.listEqual(list, Arrays.asList())),
                false);

        doUnaryTestsTest(testPairs, "list", "[1]",
                "PositiveUnaryTests(ListTest(ListLiteral(NumericLiteral(1))))",
                "TupleType(boolean)",
                "(listEqual(list, asList(number(\"1\"))))",
                (lib.listEqual(list, Arrays.asList(lib.number("1")))),
                true);

        doUnaryTestsTest(testPairs, "list", "[1, 2, 3]",
                "PositiveUnaryTests(ListTest(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3))))",
                "TupleType(boolean)",
                "(listEqual(list, asList(number(\"1\"), number(\"2\"), number(\"3\"))))",
                (lib.listEqual(list, Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3")))),
                false);

//        doUnaryTestsTest(testPairs, "number", "[<1, <2]",
//                "PositiveUnaryTests(OperatorTest(<,NumericLiteral(1)),OperatorTest(<,NumericLiteral(2)),IntervalTest(false,NumericLiteral(3),false,NumericLiteral(4)))",
//                "",
//                null,
//                null
//        );
    }

    @Test
    public void testContext() {
        DATE dateInput = lib.date("2017-01-03");
        String enumerationInput = "e1";
        NUMBER number = lib.number("1");
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("x", NUMBER, number),
                new EnvironmentEntry("dateInput", DATE, dateInput),
                new EnvironmentEntry("enumerationInput", STRING, enumerationInput));

        doExpressionTest(entries, "", "{a: \"foo\", b: {c: \"bar\", d: {e: \"baz\"}}}",
                "Context(ContextEntry(ContextEntryKey(a) = StringLiteral(\"foo\")),ContextEntry(ContextEntryKey(b) = Context(ContextEntry(ContextEntryKey(c) = StringLiteral(\"bar\")),ContextEntry(ContextEntryKey(d) = Context(ContextEntry(ContextEntryKey(e) = StringLiteral(\"baz\")))))))",
                "ContextType(a = string, b = ContextType(c = string, d = ContextType(e = string)))",
                "new com.gs.dmn.runtime.Context().add(\"a\", \"foo\").add(\"b\", new com.gs.dmn.runtime.Context().add(\"c\", \"bar\").add(\"d\", new com.gs.dmn.runtime.Context().add(\"e\", \"baz\")))",
                new com.gs.dmn.runtime.Context().add("a", "foo").add("b", new com.gs.dmn.runtime.Context().add("c", "bar").add("d", new com.gs.dmn.runtime.Context().add("e", "baz"))),
                new Context().add("a", "foo").add("b", new com.gs.dmn.runtime.Context().add("c", "bar").add("d", new com.gs.dmn.runtime.Context().add("e", "baz"))));
        doExpressionTest(entries, "", "{a: 1 + 2, b: 3, c: {d: a + b}}",
                "Context(ContextEntry(ContextEntryKey(a) = Addition(+,NumericLiteral(1),NumericLiteral(2))),ContextEntry(ContextEntryKey(b) = NumericLiteral(3)),ContextEntry(ContextEntryKey(c) = Context(ContextEntry(ContextEntryKey(d) = Addition(+,Name(a),Name(b))))))",
                "ContextType(a = number, b = number, c = ContextType(d = number))",
                null,
                null,
                null);
        doExpressionTest(entries, "", "{\"\": \"foo\"}",
                "Context(ContextEntry(ContextEntryKey() = StringLiteral(\"foo\")))",
                "ContextType( = string)",
                "new com.gs.dmn.runtime.Context().add(\"\", \"foo\")",
                new com.gs.dmn.runtime.Context().add("", "foo"),
                new Context().add("", "foo"));
        doExpressionTest(entries, "", "{\"foo+bar((!!],foo\": \"foo\"}",
                "Context(ContextEntry(ContextEntryKey(foo+bar((!!],foo) = StringLiteral(\"foo\")))",
                "ContextType(foo+bar((!!],foo = string)",
                "new com.gs.dmn.runtime.Context().add(\"foo+bar((!!],foo\", \"foo\")",
                new com.gs.dmn.runtime.Context().add("foo+bar((!!],foo", "foo"),
                new Context().add("foo+bar((!!],foo", "foo"));

        doExpressionTest(entries, "", "{ k1 : 1, k2 : 2 }",
                "Context(ContextEntry(ContextEntryKey(k1) = NumericLiteral(1)),ContextEntry(ContextEntryKey(k2) = NumericLiteral(2)))",
                "ContextType(k1 = number, k2 = number)",
                "new com.gs.dmn.runtime.Context().add(\"k1\", number(\"1\")).add(\"k2\", number(\"2\"))",
                new com.gs.dmn.runtime.Context().add("k1", lib.number("1")).add("k2", lib.number("2")),
                new Context().add("k1", lib.number("1")).add("k2", lib.number("2")));
        doExpressionTest(entries, "", "{ isPositive : function(x : feel.number) x > 1 }",
                "Context(ContextEntry(ContextEntryKey(isPositive) = FunctionDefinition(FormalParameter(x, number), Relational(>,Name(x),NumericLiteral(1)), false)))",
                "ContextType(isPositive = FEELFunctionType(FormalParameter(x, number), boolean, false))",
                null,
                null,
                null);
        doExpressionTest(entries, "", "{type : \"string\", value : string(dateInput)}",
                "Context(ContextEntry(ContextEntryKey(type) = StringLiteral(\"string\")),ContextEntry(ContextEntryKey(value) = FunctionInvocation(Name(string) -> PositionalParameters(Name(dateInput)))))",
                "ContextType(type = string, value = string)",
                "new com.gs.dmn.runtime.Context().add(\"type\", \"string\").add(\"value\", string(dateInput))",
                new com.gs.dmn.runtime.Context().add("type", "string").add("value", lib.string(dateInput)),
                new com.gs.dmn.runtime.Context().add("type", "string").add("value", lib.string(lib.date("2017-01-03"))));
        doExpressionTest(entries, "", "{type : \"string\", value : enumerationInput = \"E1\"}",
                "Context(ContextEntry(ContextEntryKey(type) = StringLiteral(\"string\")),ContextEntry(ContextEntryKey(value) = Relational(=,Name(enumerationInput),StringLiteral(\"E1\"))))",
                "ContextType(type = string, value = boolean)",
                "new com.gs.dmn.runtime.Context().add(\"type\", \"string\").add(\"value\", stringEqual(enumerationInput, \"E1\"))",
                new com.gs.dmn.runtime.Context().add("type", "string").add("value", lib.stringEqual(enumerationInput, "E1")),
                new com.gs.dmn.runtime.Context().add("type", "string").add("value", lib.stringEqual("e1", "E1")));
    }

    @Test
    public void testSimpleLiterals() {
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, this.lib.number("1")));

        doExpressionTest(entries, "", "123.45",
                "NumericLiteral(123.45)",
                "number",
                "number(\"123.45\")",
                this.lib.number("123.45"),
                this.lib.number("123.45"));
        doExpressionTest(entries, "", "\"123\"",
                "StringLiteral(\"123\")",
                "string",
                "\"123\"",
                "123",
                "123");
        doExpressionTest(entries, "", "true",
                "BooleanLiteral(true)",
                "boolean",
                "Boolean.TRUE",
                Boolean.TRUE,
                true);
        doExpressionTest(entries, "", "false",
                "BooleanLiteral(false)",
                "boolean",
                "Boolean.FALSE",
                Boolean.FALSE,
                false);
        doExpressionTest(entries, "", "date(\"2016-03-01\")",
                "DateTimeLiteral(date, \"2016-03-01\")",
                "date",
                "date(\"2016-03-01\")",
                this.lib.date("2016-03-01"),
                this.lib.date("2016-03-01"));
        doExpressionTest(entries, "",
                "date and time(\"2016-03-01T12:00:00Z\")",
                "DateTimeLiteral(date and time, \"2016-03-01T12:00:00Z\")",
                "date and time",
                "dateAndTime(\"2016-03-01T12:00:00Z\")",
                this.lib.dateAndTime("2016-03-01T12:00:00Z"),
                this.lib.dateAndTime("2016-03-01T12:00:00Z"));
        doExpressionTest(entries, "",
                "time(\"12:00:00Z\")",
                "DateTimeLiteral(time, \"12:00:00Z\")",
                "time",
                "time(\"12:00:00Z\")",
                this.lib.time("12:00:00Z"),
                this.lib.time("12:00:00Z"));
        doExpressionTest(entries, "",
                "duration(\"P1Y1M\")",
                "DateTimeLiteral(duration, \"P1Y1M\")",
                "years and months duration",
                "duration(\"P1Y1M\")",
                this.lib.duration("P1Y1M"),
                this.lib.duration("P1Y1M"));
        doExpressionTest(entries, "", "duration(\"P1DT1H\")",
                "DateTimeLiteral(duration, \"P1DT1H\")",
                "days and time duration",
                "duration(\"P1DT1H\")",
                this.lib.duration("P1DT1H"),
                this.lib.duration("P1DT1H"));
    }

    protected void doUnaryTestsTest(List<EnvironmentEntry> entries, String inputExpressionText, String inputEntryText,
                                    String expectedAST, String expectedType, String expectedJavaCode, Object expectedGeneratedValue, Object expectedEvaluatedValue) {
        // Analyze input expression
        Environment inputExpressionEnvironment = makeEnvironment(entries);
        RuntimeEnvironment runtimeEnvironment = makeRuntimeEnvironment(entries);
        FEELContext inputExpressionContext = FEELContext.makeContext(getElement(), inputExpressionEnvironment, runtimeEnvironment);
        Expression inputExpression = this.feelTranslator.analyzeSimpleExpressions(inputExpressionText, inputExpressionContext);

        // Analyze input entry
        Environment inputEntryEnvironment = makeInputEntryEnvironment(inputExpressionEnvironment, inputExpression);
        FEELContext inputEntryContext = FEELContext.makeContext(getElement(), inputEntryEnvironment, runtimeEnvironment);
        UnaryTests inputEntryTest = this.feelTranslator.analyzeUnaryTests(inputEntryText, inputEntryContext);

        // Check input entry
        assertEquals(expectedAST, inputEntryTest.toString());
        assertEquals(expectedType, inputEntryTest.getType().toString());

        // Generate code and check
        doCodeGenerationTest(inputEntryTest, inputEntryContext, expectedJavaCode);

        // Evaluate expression and check
        doEvaluationTest(inputExpression, inputExpressionContext, inputEntryTest, inputEntryContext, expectedEvaluatedValue);

        // Check generated and evaluated value
        checkGeneratedAndEvaluatedValue(expectedGeneratedValue, expectedEvaluatedValue);
    }

    protected void doSimpleUnaryTestsTest(List<EnvironmentEntry> entries, String inputExpressionText, String inputEntryText,
                                          String expectedAST, String expectedType, String expectedJavaCode, Object expectedGeneratedValue, Object expectedEvaluatedValue) {
        // Analyze input expression
        Environment inputExpressionEnvironment = makeEnvironment(entries);
        RuntimeEnvironment runtimeEnvironment = makeRuntimeEnvironment(entries);
        FEELContext inputExpressionContext = FEELContext.makeContext(getElement(), inputExpressionEnvironment, runtimeEnvironment);
        Expression inputExpression = this.feelTranslator.analyzeSimpleExpressions(inputExpressionText, inputExpressionContext);

        // Analyze input entry
        Environment inputEntryEnvironment = makeInputEntryEnvironment(inputExpressionEnvironment, inputExpression);
        FEELContext inputEntryContext = FEELContext.makeContext(getElement(), inputEntryEnvironment, runtimeEnvironment);
        UnaryTests inputEntry = this.feelTranslator.analyzeSimpleUnaryTests(inputEntryText, inputEntryContext);

        // Check input entry
        assertEquals(expectedAST, inputEntry.toString());
        assertEquals(expectedType, inputEntry.getType().toString());

        // Generate code and check
        doCodeGenerationTest(inputEntry, inputEntryContext, expectedJavaCode);

        // Evaluate expression and check
        doEvaluationTest(inputExpression, inputExpressionContext, inputEntry, inputEntryContext, expectedEvaluatedValue);

        // Check generated and evaluated value
        checkGeneratedAndEvaluatedValue(expectedGeneratedValue, expectedEvaluatedValue);
    }

    protected void doExpressionTest(List<EnvironmentEntry> entries, String inputExpressionText, String expressionText,
                                    String expectedAST, String expectedType, String expectedJavaCode, Object expectedGeneratedValue, Object expectedEvaluatedValue) {
        Expression inputExpression = null;
        Environment environment = makeEnvironment(entries);
        RuntimeEnvironment runtimeEnvironment = makeRuntimeEnvironment(entries);
        if (!StringUtils.isEmpty(inputExpressionText)) {
            // Analyze input expression
            FEELContext inputExpressionContext = FEELContext.makeContext(getElement(), environment, runtimeEnvironment);
            inputExpression = this.feelTranslator.analyzeSimpleExpressions(inputExpressionText, inputExpressionContext);
        }

        // Analyse expression
        FEELContext expressionContext = FEELContext.makeContext(getElement(), makeInputEntryEnvironment(environment, inputExpression), runtimeEnvironment);
        Expression actual = this.feelTranslator.analyzeExpression(expressionText, expressionContext);

        // Check expression
        assertEquals("Augmented AST mismatch", expectedAST, actual.toString());
        assertEquals("Type mismatch", expectedType, (actual.getType() == null ? null : actual.getType().toString()));

        // Generate code and check
        doCodeGenerationTest(actual, expressionContext, expectedJavaCode);

        // Evaluate expression and check
        doEvaluationTest(actual, expressionContext, expectedEvaluatedValue);

        // Check generated and evaluated value
        checkGeneratedAndEvaluatedValue(expectedGeneratedValue, expectedEvaluatedValue);
    }

    protected void doSimpleExpressionsTest(List<EnvironmentEntry> entries, String expressionText,
                                           String expectedAST, String expectedType, String expectedJavaCode, Object expectedGeneratedValue, Object expectedEvaluatedValue) {
        // Analyze expression
        Environment expressionEnvironment = makeEnvironment(entries);
        RuntimeEnvironment runtimeEnvironment = makeRuntimeEnvironment(entries);
        FEELContext context = FEELContext.makeContext(getElement(), expressionEnvironment, runtimeEnvironment);
        Expression expression = this.feelTranslator.analyzeSimpleExpressions(expressionText, context);

        // Check analysis result
        assertEquals("AST mismatch", expectedAST, expression.toString());
        assertEquals("Type mismatch", expectedType, expression.getType().toString());

        // Generate code and check
        doCodeGenerationTest(expression, context, expectedJavaCode);

        // Evaluate expression and check
        doEvaluationTest(expression, context, expectedEvaluatedValue);

        // Check generated and evaluated value
        checkGeneratedAndEvaluatedValue(expectedGeneratedValue, expectedEvaluatedValue);
    }

    protected void doTextualExpressionsTest(List<EnvironmentEntry> entries, String expressionText,
                                            String expectedAST, String expectedType, String expectedJavaCode, Object expectedGeneratedValue, Object expectedEvaluatedValue) {
        // Analyze expression
        Environment expressionEnvironment = makeEnvironment(entries);
        RuntimeEnvironment runtimeEnvironment = makeRuntimeEnvironment(entries);
        FEELContext context = FEELContext.makeContext(getElement(), expressionEnvironment, runtimeEnvironment);
        Expression expression = this.feelTranslator.analyzeTextualExpressions(expressionText, context);

        // Check analysis result
        assertEquals(expectedAST, expression.toString());
        assertEquals(expectedType, expression.getType().toString());

        // Generate code and check
        doCodeGenerationTest(expression, context, expectedJavaCode);

        // Evaluate expression and check
        doEvaluationTest(expression, context, expectedEvaluatedValue);

        // Check generated and evaluated value
        checkGeneratedAndEvaluatedValue(expectedGeneratedValue, expectedEvaluatedValue);
    }

    protected void doBoxedExpressionTest(List<EnvironmentEntry> entries, String expressionText,
                                         String expectedAST, String expectedType, String expectedJavaCode, Object expectedGeneratedValue, Object expectedEvaluatedValue) {
        // Analyze expression
        Environment expressionEnvironment = makeEnvironment(entries);
        RuntimeEnvironment runtimeEnvironment = makeRuntimeEnvironment(entries);
        FEELContext context = FEELContext.makeContext(getElement(), expressionEnvironment, runtimeEnvironment);
        Expression actual = this.feelTranslator.analyzeBoxedExpression(expressionText, context);

        // Check analysis result
        assertEquals(expectedAST, actual.toString());
        assertEquals(expectedType, actual.getType().toString());

        // Generate code and check
        doCodeGenerationTest(actual, context, expectedJavaCode);

        // Evaluate expression and check
        doEvaluationTest(actual, context, expectedEvaluatedValue);

        // Check generated and evaluated value
        checkGeneratedAndEvaluatedValue(expectedGeneratedValue, expectedEvaluatedValue);
    }

    private void checkGeneratedAndEvaluatedValue(Object expectedGeneratedValue, Object expectedEvaluatedValue) {
        assertEquals("Evaluated and generated value mismatch", expectedEvaluatedValue, expectedGeneratedValue);
    }

    private Result evaluateInputEntry(Expression inputExpression, FEELContext inputExpressionContext, UnaryTests inputEntryTest, FEELContext inputEntryContext) {
        // Evaluate input expression
        Result inputExpressionResult = this.feelInterpreter.evaluateExpression(inputExpression, inputExpressionContext);
        Object inputExpressionValue = Result.value(inputExpressionResult);
        // Evaluate input entry
        inputEntryContext.runtimeBind(AbstractDMNToNativeTransformer.INPUT_ENTRY_PLACE_HOLDER, inputExpressionValue);
        return this.feelInterpreter.evaluateUnaryTests(inputEntryTest, inputEntryContext);
    }

    private void doCodeGenerationTest(UnaryTests inputEntry, FEELContext inputEntryContext, String expectedJavaCode) {
        if (expectedJavaCode != null) {
            String javaCode = this.feelTranslator.expressionToNative(inputEntry, inputEntryContext);
            assertEquals("Generated code mismatch", expectedJavaCode, javaCode);
        }
    }

    private void doCodeGenerationTest(Expression expression, FEELContext expressionContext, String expectedJavaCode) {
        if (expectedJavaCode != null) {
            String javaCode = this.feelTranslator.expressionToNative(expression, expressionContext);
            assertEquals("Generated code mismatch", expectedJavaCode, javaCode);
        }
    }

    private void doEvaluationTest(Expression inputExpression, FEELContext inputExpressionContext, UnaryTests inputEntry, FEELContext inputEntryContext, Object expectedEvaluatedValue) {
        if (expectedEvaluatedValue != null) {
            Result actualResult = evaluateInputEntry(inputExpression, inputExpressionContext, inputEntry, inputEntryContext);
            Object actualValue = Result.value(actualResult);
            assertEquals("Evaluated value mismatch", expectedEvaluatedValue, actualValue);
        }
    }

    private void doEvaluationTest(Expression expression, FEELContext context, Object expectedEvaluatedValue) {
        if (expectedEvaluatedValue != null) {
            Result actualResult = this.feelInterpreter.evaluateExpression(expression, context);
            Object actualValue = Result.value(actualResult);
            assertEquals(expectedEvaluatedValue, actualValue);
        }
    }

    private Environment makeEnvironment(List<EnvironmentEntry> entries) {
        Environment environment = this.environmentFactory.makeEnvironment();
        for (EnvironmentEntry pair : entries) {
            environment.addDeclaration(pair.getName(), this.environmentFactory.makeVariableDeclaration(pair.getName(), pair.getType()));
        }
        return environment;
    }

    private Environment makeInputEntryEnvironment(Environment parent, Expression inputExpression) {
        Environment environment = this.environmentFactory.makeEnvironment(parent, inputExpression);
        if (inputExpression != null) {
            environment.addDeclaration(AbstractDMNToNativeTransformer.INPUT_ENTRY_PLACE_HOLDER, this.environmentFactory.makeVariableDeclaration(AbstractDMNToNativeTransformer.INPUT_ENTRY_PLACE_HOLDER, inputExpression.getType()));
        }
        return environment;
    }

    private RuntimeEnvironment makeRuntimeEnvironment(List<EnvironmentEntry> entries) {
        RuntimeEnvironment runtimeEnvironment = this.runtimeEnvironmentFactory.makeEnvironment();
        for (EnvironmentEntry e : entries) {
            runtimeEnvironment.bind(e.getName(), e.getValue());
        }
        return runtimeEnvironment;
    }

    protected TNamedElement getElement() {
        return null;
    }

    protected abstract DMNModelRepository makeRepository();

    protected abstract DMNDialectDefinition<NUMBER, DATE, TIME, DATE_TIME, DURATION, TEST> makeDialect();
}
