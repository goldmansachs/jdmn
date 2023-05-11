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
import com.gs.dmn.ast.TNamedElement;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.context.DMNContextKind;
import com.gs.dmn.context.environment.EnvironmentFactory;
import com.gs.dmn.context.environment.RuntimeEnvironment;
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.el.interpreter.ELInterpreter;
import com.gs.dmn.el.synthesis.ELTranslator;
import com.gs.dmn.feel.analysis.semantics.SemanticError;
import com.gs.dmn.feel.analysis.semantics.type.*;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.test.UnaryTests;
import com.gs.dmn.feel.lib.FEELLib;
import com.gs.dmn.runtime.Context;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.interpreter.DMNInterpreter;
import com.gs.dmn.runtime.interpreter.Result;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;
import org.apache.commons.lang3.StringUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.gs.dmn.context.DMNContext.INPUT_ENTRY_PLACE_HOLDER;
import static com.gs.dmn.el.analysis.semantics.type.AnyType.ANY;
import static com.gs.dmn.feel.analysis.semantics.type.BooleanType.BOOLEAN;
import static com.gs.dmn.feel.analysis.semantics.type.DateType.DATE;
import static com.gs.dmn.feel.analysis.semantics.type.NumberType.NUMBER;
import static com.gs.dmn.feel.analysis.semantics.type.StringType.STRING;
import static org.junit.Assert.assertEquals;

public abstract class AbstractFEELProcessorTest<NUMBER, DATE, TIME, DATE_TIME, DURATION, TEST> extends AbstractTest {
    protected final ELTranslator<Type, DMNContext> feelTranslator;
    protected final DMNInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> dmnInterpreter;
    protected final ELInterpreter<Type, DMNContext> feelInterpreter;

    protected final EnvironmentFactory environmentFactory;
    private final FEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> lib;
    private final BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer;

    protected AbstractFEELProcessorTest() {
        DMNDialectDefinition<NUMBER, DATE, TIME, DATE_TIME, DURATION, TEST> dialectDefinition = makeDialect();
        DMNModelRepository repository = makeRepository();
        InputParameters inputParameters = makeInputParameters();

        this.environmentFactory = dialectDefinition.createEnvironmentFactory();
        this.lib = dialectDefinition.createFEELLib();
        this.feelTranslator = dialectDefinition.createFEELTranslator(repository, inputParameters);
        this.dmnInterpreter = dialectDefinition.createDMNInterpreter(repository, inputParameters);
        this.feelInterpreter = dialectDefinition.createELInterpreter(repository, inputParameters);
        this.dmnTransformer = dialectDefinition.createBasicTransformer(repository, new NopLazyEvaluationDetector(), inputParameters);
    }

    @Test
    public void testPositiveUnaryTests() {
        NUMBER number = this.lib.number("1");
        String string = "e1";
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("number", NUMBER, number),
                new EnvironmentEntry("string", STRING, string));

        doUnaryTestsTest(entries, "number", "1, 2",
                "PositiveUnaryTests(OperatorRange(null,NumericLiteral(1)),OperatorRange(null,NumericLiteral(2)))",
                "TupleType(boolean, boolean)",
                "booleanOr(numericEqual(number, number(\"1\")), numericEqual(number, number(\"2\")))",
                this.lib.booleanOr(this.lib.numericEqual(number, this.lib.number("1")), this.lib.numericEqual(number, this.lib.number("2"))),
                true);
        doUnaryTestsTest(entries, "string", "\"e1\", \"e2\"",
                "PositiveUnaryTests(OperatorRange(null,StringLiteral(\"e1\")),OperatorRange(null,StringLiteral(\"e2\")))",
                "TupleType(boolean, boolean)",
                "booleanOr(stringEqual(string, \"e1\"), stringEqual(string, \"e2\"))",
                this.lib.booleanOr(this.lib.stringEqual(string, "e1"), this.lib.stringEqual(string, "e2")),
                true);
    }

    @Test
    public void testNegatedPositiveUnaryTests() {
        NUMBER input = this.lib.number("1");
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, input));

        doUnaryTestsTest(entries, "input", "not (-1)",
                "NegatedPositiveUnaryTests(PositiveUnaryTests(OperatorRange(null,ArithmeticNegation(NumericLiteral(1)))))",
                "TupleType(boolean)",
                "booleanNot(numericEqual(input, numericUnaryMinus(number(\"1\"))))",
                this.lib.booleanNot(this.lib.numericEqual(input, this.lib.numericUnaryMinus(this.lib.number("1")))),
                true);
        doUnaryTestsTest(entries, "input", "not(1, 2)",
                "NegatedPositiveUnaryTests(PositiveUnaryTests(OperatorRange(null,NumericLiteral(1)),OperatorRange(null,NumericLiteral(2))))",
                "TupleType(boolean, boolean)",
                "booleanNot(booleanOr(numericEqual(input, number(\"1\")), numericEqual(input, number(\"2\"))))",
                this.lib.booleanNot(this.lib.booleanOr(this.lib.numericEqual(input, this.lib.number("1")), this.lib.numericEqual(input, this.lib.number("2")))),
                false);
    }

    @Test
    public void testAnyUnaryTests() {
        NUMBER input = this.lib.number("1");
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, input));

        doUnaryTestsTest(entries, "input", "-",
                "Any()",
                "boolean",
                "Boolean.TRUE",
                true,
                true);
    }

    @Test
    public void testUnaryTests() {
        NUMBER input = this.lib.number("1");
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, input));

        doUnaryTestsTest(entries, "input", "-",
                "Any()",
                "boolean",
                "Boolean.TRUE",
                true,
                true);
    }

    @Test
    public void testPositiveUnaryTest() {
        NUMBER number = this.lib.number("1");
        String string = "abc";
        boolean boolean_ = true;
        DATE date = this.lib.date("2017-01-03");
        TIME time = this.lib.time("12:00:00Z");
        DATE_TIME dateTime = this.lib.dateAndTime("2017-01-03T12:00:00Z");
        List<String> list = this.lib.asList("a", "b", "c");

        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("number", NUMBER, number),
                new EnvironmentEntry("string", STRING, string),
                new EnvironmentEntry("boolean", BOOLEAN, boolean_),
                new EnvironmentEntry("date", DateType.DATE, date),
                new EnvironmentEntry("time", TimeType.TIME, time),
                new EnvironmentEntry("dateTime", DateTimeType.DATE_AND_TIME, dateTime),
                new EnvironmentEntry("list", ListType.STRING_LIST, list)
        );

        //
        // OperatorRange
        //
        doUnaryTestsTest(entries, "number", "1",
                "PositiveUnaryTests(OperatorRange(null,NumericLiteral(1)))",
                "TupleType(boolean)",
                "numericEqual(number, number(\"1\"))",
                this.lib.numericEqual(number, this.lib.number("1")),
                true);
        doUnaryTestsTest(entries, "number", "-1",
                "PositiveUnaryTests(OperatorRange(null,ArithmeticNegation(NumericLiteral(1))))",
                "TupleType(boolean)",
                "numericEqual(number, numericUnaryMinus(number(\"1\")))",
                this.lib.numericEqual(number, this.lib.numericUnaryMinus(this.lib.number("1"))),
                false);

        doUnaryTestsTest(entries, "number", "<1",
                "PositiveUnaryTests(OperatorRange(<,NumericLiteral(1)))",
                "TupleType(boolean)",
                "numericLessThan(number, number(\"1\"))",
                this.lib.numericLessThan(number, this.lib.number("1")),
                false);
        doUnaryTestsTest(entries, "number", "<=1",
                "PositiveUnaryTests(OperatorRange(<=,NumericLiteral(1)))",
                "TupleType(boolean)",
                "numericLessEqualThan(number, number(\"1\"))",
                this.lib.numericLessEqualThan(number, this.lib.number("1")),
                true);
        doUnaryTestsTest(entries, "number", ">1",
                "PositiveUnaryTests(OperatorRange(>,NumericLiteral(1)))",
                "TupleType(boolean)",
                "numericGreaterThan(number, number(\"1\"))",
                this.lib.numericGreaterThan(number, this.lib.number("1")),
                false);
        doUnaryTestsTest(entries, "number", ">=1",
                "PositiveUnaryTests(OperatorRange(>=,NumericLiteral(1)))",
                "TupleType(boolean)",
                "numericGreaterEqualThan(number, number(\"1\"))",
                this.lib.numericGreaterEqualThan(number, this.lib.number("1")),
                true);

        doUnaryTestsTest(entries, "date", "< date(\"2016-08-01\")",
                "PositiveUnaryTests(OperatorRange(<,DateTimeLiteral(date, \"2016-08-01\")))",
                "TupleType(boolean)",
                "dateLessThan(date, date(\"2016-08-01\"))",
                this.lib.dateLessThan(date, this.lib.date("2016-08-01")),
                false);

        //
        // EndpointsRange
        //
        doUnaryTestsTest(entries, "number", "(1..2)",
                "PositiveUnaryTests(EndpointsRange(true,NumericLiteral(1),true,NumericLiteral(2)))",
                "TupleType(boolean)",
                "booleanAnd(numericGreaterThan(number, number(\"1\")), numericLessThan(number, number(\"2\")))",
                this.lib.booleanAnd(this.lib.numericGreaterThan(number, this.lib.number("1")), this.lib.numericLessThan(number, this.lib.number("2"))),
                false);
        doUnaryTestsTest(entries, "number", "]1..2[",
                "PositiveUnaryTests(EndpointsRange(true,NumericLiteral(1),true,NumericLiteral(2)))",
                "TupleType(boolean)",
                "booleanAnd(numericGreaterThan(number, number(\"1\")), numericLessThan(number, number(\"2\")))",
                this.lib.booleanAnd(this.lib.numericGreaterThan(number, this.lib.number("1")), this.lib.numericLessThan(number, this.lib.number("2"))),
                false);
        doUnaryTestsTest(entries, "number", "[1..2]",
                "PositiveUnaryTests(EndpointsRange(false,NumericLiteral(1),false,NumericLiteral(2)))",
                "TupleType(boolean)",
                "booleanAnd(numericGreaterEqualThan(number, number(\"1\")), numericLessEqualThan(number, number(\"2\")))",
                this.lib.booleanAnd(this.lib.numericGreaterEqualThan(number, this.lib.number("1")), this.lib.numericLessEqualThan(number, this.lib.number("2"))),
                true);

        //
        //  NullTest
        //
        doUnaryTestsTest(entries, "number", "null",
                "PositiveUnaryTests(NullTest())",
                "TupleType(boolean)",
                "number == null",
                number == null,
                false);

        //
        // ExpressionTest
        //
        doUnaryTestsTest(entries, "list", "count(?) > 2",
                "PositiveUnaryTests(ExpressionTest(Relational(>,FunctionInvocation(Name(count) -> PositionalParameters(Name(?))),NumericLiteral(2))))",
                "TupleType(boolean)",
                "numericGreaterThan(count(list), number(\"2\"))",
                this.lib.numericGreaterThan(this.lib.count(list), this.lib.number("2")),
                true);

        doUnaryTestsTest(entries, "number", "? < 2 + 3",
                "PositiveUnaryTests(ExpressionTest(Relational(<,Name(?),Addition(+,NumericLiteral(2),NumericLiteral(3)))))",
                "TupleType(boolean)",
                "numericLessThan(number, numericAdd(number(\"2\"), number(\"3\")))",
                this.lib.numericLessThan(number, this.lib.numericAdd(this.lib.number("2"), this.lib.number("3"))),
                true);

        doUnaryTestsTest(entries, "number", "< 123.45",
                "PositiveUnaryTests(OperatorRange(<,NumericLiteral(123.45)))",
                "TupleType(boolean)",
                "numericLessThan(number, number(\"123.45\"))",
                this.lib.numericLessThan(number, this.lib.number("123.45")),
                true);
        doUnaryTestsTest(entries, "number", "<= 123.45",
                "PositiveUnaryTests(OperatorRange(<=,NumericLiteral(123.45)))",
                "TupleType(boolean)",
                "numericLessEqualThan(number, number(\"123.45\"))",
                this.lib.numericLessEqualThan(number, this.lib.number("123.45")),
                true);
        doUnaryTestsTest(entries, "number", "> 123.45",
                "PositiveUnaryTests(OperatorRange(>,NumericLiteral(123.45)))",
                "TupleType(boolean)",
                "numericGreaterThan(number, number(\"123.45\"))",
                this.lib.numericGreaterThan(number, this.lib.number("123.45")),
                false);
        doUnaryTestsTest(entries, "number", ">= 123.45",
                "PositiveUnaryTests(OperatorRange(>=,NumericLiteral(123.45)))",
                "TupleType(boolean)",
                "numericGreaterEqualThan(number, number(\"123.45\"))",
                this.lib.numericGreaterEqualThan(number, this.lib.number("123.45")),
                false);
        doUnaryTestsTest(entries, "date", "< date(\"2016-08-01\")",
                "PositiveUnaryTests(OperatorRange(<,DateTimeLiteral(date, \"2016-08-01\")))",
                "TupleType(boolean)",
                "dateLessThan(date, date(\"2016-08-01\"))",
                this.lib.numericGreaterEqualThan(number, this.lib.number("123.45")),
                false);
        doUnaryTestsTest(entries, "date", "<= date(\"2016-08-01\")",
                "PositiveUnaryTests(OperatorRange(<=,DateTimeLiteral(date, \"2016-08-01\")))",
                "TupleType(boolean)",
                "dateLessEqualThan(date, date(\"2016-08-01\"))",
                this.lib.dateLessEqualThan(date, this.lib.date("2016-08-01")),
                false);
        doUnaryTestsTest(entries, "date", "> date(\"2016-08-01\")",
                "PositiveUnaryTests(OperatorRange(>,DateTimeLiteral(date, \"2016-08-01\")))",
                "TupleType(boolean)",
                "dateGreaterThan(date, date(\"2016-08-01\"))",
                this.lib.dateGreaterThan(date, this.lib.date("2016-08-01")),
                true);
        doUnaryTestsTest(entries, "date", ">= date(\"2016-08-01\")",
                "PositiveUnaryTests(OperatorRange(>=,DateTimeLiteral(date, \"2016-08-01\")))",
                "TupleType(boolean)",
                "dateGreaterEqualThan(date, date(\"2016-08-01\"))",
                this.lib.dateGreaterEqualThan(date, this.lib.date("2016-08-01")),
                true);
        doUnaryTestsTest(entries, "time", "< time(\"12:00:00Z\")",
                "PositiveUnaryTests(OperatorRange(<,DateTimeLiteral(time, \"12:00:00Z\")))",
                "TupleType(boolean)",
                "timeLessThan(time, time(\"12:00:00Z\"))",
                this.lib.timeLessThan(time, this.lib.time("12:00:00Z")),
                false);
        doUnaryTestsTest(entries, "time", "<= time(\"12:00:00Z\")",
                "PositiveUnaryTests(OperatorRange(<=,DateTimeLiteral(time, \"12:00:00Z\")))",
                "TupleType(boolean)",
                "timeLessEqualThan(time, time(\"12:00:00Z\"))",
                this.lib.timeLessEqualThan(time, this.lib.time("12:00:00Z")),
                true);
        doUnaryTestsTest(entries, "time", "> time(\"12:00:00Z\")",
                "PositiveUnaryTests(OperatorRange(>,DateTimeLiteral(time, \"12:00:00Z\")))",
                "TupleType(boolean)",
                "timeGreaterThan(time, time(\"12:00:00Z\"))",
                this.lib.timeGreaterThan(time, this.lib.time("12:00:00Z")),
                false);
        doUnaryTestsTest(entries, "time", ">= time(\"12:00:00Z\")",
                "PositiveUnaryTests(OperatorRange(>=,DateTimeLiteral(time, \"12:00:00Z\")))",
                "TupleType(boolean)",
                "timeGreaterEqualThan(time, time(\"12:00:00Z\"))",
                this.lib.timeGreaterEqualThan(time, this.lib.time("12:00:00Z")),
                true);
        doUnaryTestsTest(entries, "dateTime", "< date and time(\"2016-08-01T11:00:00Z\")",
                "PositiveUnaryTests(OperatorRange(<,DateTimeLiteral(date and time, \"2016-08-01T11:00:00Z\")))",
                "TupleType(boolean)",
                "dateTimeLessThan(dateTime, dateAndTime(\"2016-08-01T11:00:00Z\"))",
                this.lib.dateTimeLessThan(dateTime, this.lib.dateAndTime("2016-08-01T11:00:00Z")),
                false);
        doUnaryTestsTest(entries, "dateTime", "<= date and time(\"2016-08-01T11:00:00Z\")",
                "PositiveUnaryTests(OperatorRange(<=,DateTimeLiteral(date and time, \"2016-08-01T11:00:00Z\")))",
                "TupleType(boolean)",
                "dateTimeLessEqualThan(dateTime, dateAndTime(\"2016-08-01T11:00:00Z\"))",
                this.lib.dateTimeLessEqualThan(dateTime, this.lib.dateAndTime("2016-08-01T11:00:00Z")),
                false);
        doUnaryTestsTest(entries, "dateTime", "> date and time(\"2016-08-01T11:00:00Z\")",
                "PositiveUnaryTests(OperatorRange(>,DateTimeLiteral(date and time, \"2016-08-01T11:00:00Z\")))",
                "TupleType(boolean)",
                "dateTimeGreaterThan(dateTime, dateAndTime(\"2016-08-01T11:00:00Z\"))",
                this.lib.dateTimeGreaterThan(dateTime, this.lib.dateAndTime("2016-08-01T11:00:00Z")),
                true);
        doUnaryTestsTest(entries, "dateTime", ">= date and time(\"2016-08-01T11:00:00Z\")",
                "PositiveUnaryTests(OperatorRange(>=,DateTimeLiteral(date and time, \"2016-08-01T11:00:00Z\")))",
                "TupleType(boolean)",
                "dateTimeGreaterEqualThan(dateTime, dateAndTime(\"2016-08-01T11:00:00Z\"))",
                this.lib.dateTimeGreaterEqualThan(dateTime, this.lib.dateAndTime("2016-08-01T11:00:00Z")),
                true);
        doUnaryTestsTest(entries, "number", "123.56",
                "PositiveUnaryTests(OperatorRange(null,NumericLiteral(123.56)))",
                "TupleType(boolean)",
                "numericEqual(number, number(\"123.56\"))",
                this.lib.numericEqual(number, this.lib.number("123.56")),
                false);
        doUnaryTestsTest(entries, "string", "\"abc\"",
                "PositiveUnaryTests(OperatorRange(null,StringLiteral(\"abc\")))",
                "TupleType(boolean)",
                "stringEqual(string, \"abc\")",
                this.lib.stringEqual(string, "abc"),
                true);
        doUnaryTestsTest(entries, "boolean", "true",
                "PositiveUnaryTests(OperatorRange(null,BooleanLiteral(true)))",
                "TupleType(boolean)",
                "booleanEqual(boolean, Boolean.TRUE)",
                this.lib.booleanEqual(boolean_, Boolean.TRUE),
                true);
        doUnaryTestsTest(entries, "date", "date(\"2016-08-01\")",
                "PositiveUnaryTests(OperatorRange(null,DateTimeLiteral(date, \"2016-08-01\")))",
                "TupleType(boolean)",
                "dateEqual(date, date(\"2016-08-01\"))",
                this.lib.dateEqual(date, this.lib.date("2016-08-01")),
                false);
        doUnaryTestsTest(entries, "time", "time(\"12:00:00Z\")",
                "PositiveUnaryTests(OperatorRange(null,DateTimeLiteral(time, \"12:00:00Z\")))",
                "TupleType(boolean)",
                "timeEqual(time, time(\"12:00:00Z\"))",
                this.lib.timeEqual(time, this.lib.time("12:00:00Z")),
                true);
        doUnaryTestsTest(entries, "dateTime", "date and time(\"2016-08-01T11:00:00Z\")",
                "PositiveUnaryTests(OperatorRange(null,DateTimeLiteral(date and time, \"2016-08-01T11:00:00Z\")))",
                "TupleType(boolean)",
                "dateTimeEqual(dateTime, dateAndTime(\"2016-08-01T11:00:00Z\"))",
                this.lib.dateTimeEqual(dateTime, this.lib.dateAndTime("2016-08-01T11:00:00Z")),
                false);
    }

    @Test
    public void testEndpointsRange() {
        NUMBER input = this.lib.number("1");
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, input));

        doUnaryTestsTest(entries, "input", "(1..2)",
                "PositiveUnaryTests(EndpointsRange(true,NumericLiteral(1),true,NumericLiteral(2)))",
                "TupleType(boolean)",
                "booleanAnd(numericGreaterThan(input, number(\"1\")), numericLessThan(input, number(\"2\")))",
                this.lib.booleanAnd(this.lib.numericGreaterThan(input, this.lib.number("1")), this.lib.numericLessThan(input, this.lib.number("2"))),
                false);
        doUnaryTestsTest(entries, "input", "]1..2[",
                "PositiveUnaryTests(EndpointsRange(true,NumericLiteral(1),true,NumericLiteral(2)))",
                "TupleType(boolean)",
                "booleanAnd(numericGreaterThan(input, number(\"1\")), numericLessThan(input, number(\"2\")))",
                this.lib.booleanAnd(this.lib.numericGreaterThan(input, this.lib.number("1")), this.lib.numericLessThan(input, this.lib.number("2"))),
                false);
        doUnaryTestsTest(entries, "input", "[1..2]",
                "PositiveUnaryTests(EndpointsRange(false,NumericLiteral(1),false,NumericLiteral(2)))",
                "TupleType(boolean)",
                "booleanAnd(numericGreaterEqualThan(input, number(\"1\")), numericLessEqualThan(input, number(\"2\")))",
                this.lib.booleanAnd(this.lib.numericGreaterEqualThan(input, this.lib.number("1")), this.lib.numericLessEqualThan(input, this.lib.number("2"))),
                true);
    }

    @Test(expected = SemanticError.class)
    public void testEqualityWhenTypeMismatch() {
        Boolean input = true;
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", BOOLEAN, input));

        doUnaryTestsTest(entries, "input", "123.56", "", "TupleType(boolean)", "", null, "");
    }

    @Test(expected = SemanticError.class)
    public void testOperatorRangeWhenTypeMismatch() {
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", BOOLEAN, true));

        doUnaryTestsTest(entries, "input", "< 123.56", "", "TupleType(boolean)", "", null, "");
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
                this.lib.numericAdd(this.lib.number("1"), this.lib.number("2")),
                this.lib.number("3"));
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
                this.lib.rangeToList(this.lib.number("4"), this.lib.number("2")).stream().map(i -> i).collect(Collectors.toList()),
                this.lib.asList(this.lib.number("4"), this.lib.number("3"), this.lib.number("2"))
        );
        doExpressionTest(entries, "", "for i in 1..-1 return i",
                "ForExpression(Iterator(i in RangeIteratorDomain(NumericLiteral(1), ArithmeticNegation(NumericLiteral(1)))) -> Name(i))",
                "ListType(number)",
                "rangeToList(number(\"1\"), numericUnaryMinus(number(\"1\"))).stream().map(i -> i).collect(Collectors.toList())",
                this.lib.rangeToList(this.lib.number("1"), this.lib.numericUnaryMinus(this.lib.number("1"))).stream().map(i -> i).collect(Collectors.toList()),
                this.lib.asList(this.lib.number("1"), this.lib.number("0"), this.lib.number("-1"))
        );

        doExpressionTest(entries, "", "for i in 1..2 return for j in [2, 3] return i+j",
                "ForExpression(Iterator(i in RangeIteratorDomain(NumericLiteral(1), NumericLiteral(2))) -> ForExpression(Iterator(j in ExpressionIteratorDomain(ListLiteral(NumericLiteral(2),NumericLiteral(3)))) -> Addition(+,Name(i),Name(j))))",
                "ListType(ListType(number))",
                "rangeToList(number(\"1\"), number(\"2\")).stream().map(i -> asList(number(\"2\"), number(\"3\")).stream().map(j -> numericAdd(i, j)).collect(Collectors.toList())).collect(Collectors.toList())",
                this.lib.rangeToList(this.lib.number("1"), this.lib.number("2")).stream().map(i -> this.lib.asList(this.lib.number("2"), this.lib.number("3")).stream().map(j -> this.lib.numericAdd(i, j)).collect(Collectors.toList())).collect(Collectors.toList()),
                Arrays.asList(Arrays.asList(this.lib.number("3"), this.lib.number("4")), Arrays.asList(this.lib.number("4"), this.lib.number("5"))));
        doExpressionTest(entries, "", "for i in [1..2] return i",
                "ForExpression(Iterator(i in ExpressionIteratorDomain(EndpointsRange(false,NumericLiteral(1),false,NumericLiteral(2)))) -> Name(i))",
                "ListType(number)",
                "rangeToList(false, number(\"1\"), false, number(\"2\")).stream().map(i -> i).collect(Collectors.toList())",
                this.lib.rangeToList(false, this.lib.number("1"), false, this.lib.number("2")).stream().map(i -> i).collect(Collectors.toList()),
                Arrays.asList(this.lib.number("1"), this.lib.number("2")));
        doExpressionTest(entries, "", "for i in [1..2], j in [2..3] return i+j",
                "ForExpression(Iterator(i in ExpressionIteratorDomain(EndpointsRange(false,NumericLiteral(1),false,NumericLiteral(2)))),Iterator(j in ExpressionIteratorDomain(EndpointsRange(false,NumericLiteral(2),false,NumericLiteral(3)))) -> Addition(+,Name(i),Name(j)))",
                "ListType(number)",
                "rangeToList(false, number(\"1\"), false, number(\"2\")).stream().map(i -> rangeToList(false, number(\"2\"), false, number(\"3\")).stream().map(j -> numericAdd(i, j))).flatMap(x -> x).collect(Collectors.toList())",
                this.lib.rangeToList(false, this.lib.number("1"), false, this.lib.number("2")).stream().map(i ->
                        this.lib.rangeToList(false, this.lib.number("2"), false, this.lib.number("3")).stream().map(j ->
                                this.lib.numericAdd(i, j)))
                        .flatMap(x -> x)
                        .collect(Collectors.toList()),
                Arrays.asList(this.lib.number("3"), this.lib.number("4"), this.lib.number("4"), this.lib.number("5")));
        doExpressionTest(entries, "", "for i in [1..2] return for j in [2..3] return i+j",
                "ForExpression(Iterator(i in ExpressionIteratorDomain(EndpointsRange(false,NumericLiteral(1),false,NumericLiteral(2)))) -> ForExpression(Iterator(j in ExpressionIteratorDomain(EndpointsRange(false,NumericLiteral(2),false,NumericLiteral(3)))) -> Addition(+,Name(i),Name(j))))",
                "ListType(ListType(number))",
                "rangeToList(false, number(\"1\"), false, number(\"2\")).stream().map(i -> rangeToList(false, number(\"2\"), false, number(\"3\")).stream().map(j -> numericAdd(i, j)).collect(Collectors.toList())).collect(Collectors.toList())",
                Arrays.asList(Arrays.asList(this.lib.number("3"), this.lib.number("4")), Arrays.asList(this.lib.number("4"), this.lib.number("5"))),
                Arrays.asList(Arrays.asList(this.lib.number("3"), this.lib.number("4")), Arrays.asList(this.lib.number("4"), this.lib.number("5"))));

        doExpressionTest(entries, "", "for i in [1, 2] return i",
                "ForExpression(Iterator(i in ExpressionIteratorDomain(ListLiteral(NumericLiteral(1),NumericLiteral(2)))) -> Name(i))",
                "ListType(number)",
                "asList(number(\"1\"), number(\"2\")).stream().map(i -> i).collect(Collectors.toList())",
                Arrays.asList(this.lib.number("1"), this.lib.number("2")).stream().map(i -> i).collect(Collectors.toList()),
                Arrays.asList(this.lib.number("1"), this.lib.number("2")));
        doExpressionTest(entries, "", "for i in [1, 2], j in [2, 3] return i+j",
                "ForExpression(Iterator(i in ExpressionIteratorDomain(ListLiteral(NumericLiteral(1),NumericLiteral(2)))),Iterator(j in ExpressionIteratorDomain(ListLiteral(NumericLiteral(2),NumericLiteral(3)))) -> Addition(+,Name(i),Name(j)))",
                "ListType(number)",
                "asList(number(\"1\"), number(\"2\")).stream().map(i -> asList(number(\"2\"), number(\"3\")).stream().map(j -> numericAdd(i, j))).flatMap(x -> x).collect(Collectors.toList())",
                Arrays.asList(this.lib.number("1"), this.lib.number("2")).stream().map(i ->
                        Arrays.asList(this.lib.number("2"), this.lib.number("3")).stream().map(j ->
                                this.lib.numericAdd(i, j)))
                        .flatMap(x -> x)
                        .collect(Collectors.toList()),
                Arrays.asList(this.lib.number("3"), this.lib.number("4"), this.lib.number("4"), this.lib.number("5")));
        doExpressionTest(entries, "", "for i in [1, 2] return for j in [2, 3] return i+j",
                "ForExpression(Iterator(i in ExpressionIteratorDomain(ListLiteral(NumericLiteral(1),NumericLiteral(2)))) -> ForExpression(Iterator(j in ExpressionIteratorDomain(ListLiteral(NumericLiteral(2),NumericLiteral(3)))) -> Addition(+,Name(i),Name(j))))",
                "ListType(ListType(number))",
                "asList(number(\"1\"), number(\"2\")).stream().map(i -> asList(number(\"2\"), number(\"3\")).stream().map(j -> numericAdd(i, j)).collect(Collectors.toList())).collect(Collectors.toList())",
                Arrays.asList(this.lib.number("1"), this.lib.number("2")).stream().map(i ->
                        Arrays.asList(this.lib.number("2"), this.lib.number("3")).stream().map(j ->
                                this.lib.numericAdd(i, j))
                                .collect(Collectors.toList()))
                        .collect(Collectors.toList()),
                Arrays.asList(Arrays.asList(this.lib.number("3"), this.lib.number("4")), Arrays.asList(this.lib.number("4"), this.lib.number("5"))));
    }

    @Test
    public void testIfExpression() {
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, this.lib.number("1")));

        doExpressionTest(entries, "", "if true then 1 else 2",
                "IfExpression(BooleanLiteral(true), NumericLiteral(1), NumericLiteral(2))",
                "number",
                "(booleanEqual(Boolean.TRUE, Boolean.TRUE)) ? number(\"1\") : number(\"2\")",
                (this.lib.booleanEqual(Boolean.TRUE, Boolean.TRUE)) ? this.lib.number("1") : this.lib.number("2"),
                this.lib.number("1"));
        doExpressionTest(entries, "", "if true then \"b\" else \"a\"",
                "IfExpression(BooleanLiteral(true), StringLiteral(\"b\"), StringLiteral(\"a\"))",
                "string",
                "(booleanEqual(Boolean.TRUE, Boolean.TRUE)) ? \"b\" : \"a\"",
                (this.lib.booleanEqual(Boolean.TRUE, Boolean.TRUE)) ? "b" : "a",
                "b");
        doExpressionTest(entries, "", "if false then null else \"a\"",
                "IfExpression(BooleanLiteral(false), NullLiteral(), StringLiteral(\"a\"))",
                "string",
                "(booleanEqual(Boolean.FALSE, Boolean.TRUE)) ? null : \"a\"",
                (this.lib.booleanEqual(Boolean.FALSE, Boolean.TRUE)) ? null : "a",
                "a");
        doExpressionTest(entries, "", "if true then 1 else null",
                "IfExpression(BooleanLiteral(true), NumericLiteral(1), NullLiteral())",
                "number",
                "(booleanEqual(Boolean.TRUE, Boolean.TRUE)) ? number(\"1\") : null",
                (this.lib.booleanEqual(Boolean.TRUE, Boolean.TRUE)) ? this.lib.number("1") : null,
                this.lib.number("1"));
    }

    @Test(expected = SemanticError.class)
    public void testIfExpressionWhenConditionIsNotBoolean() {
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, this.lib.number("1")));

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
                new EnvironmentEntry("input", NUMBER, this.lib.number("1")));

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
                new EnvironmentEntry("input", NUMBER, this.lib.number("1")));

        doExpressionTest(entries, "", "some i in [1..2] j in [2..3] satisfies i + j > 1",
                "QuantifiedExpression(some, Iterator(i in ExpressionIteratorDomain(EndpointsRange(false,NumericLiteral(1),false,NumericLiteral(2)))),Iterator(j in ExpressionIteratorDomain(EndpointsRange(false,NumericLiteral(2),false,NumericLiteral(3)))) -> Relational(>,Addition(+,Name(i),Name(j)),NumericLiteral(1)))",
                "boolean",
                "booleanOr((List)rangeToList(false, number(\"1\"), false, number(\"2\")).stream().map(i -> rangeToList(false, number(\"2\"), false, number(\"3\")).stream().map(j -> numericGreaterThan(numericAdd(i, j), number(\"1\")))).flatMap(x -> x).collect(Collectors.toList()))",
                this.lib.booleanOr((List)
                        this.lib.rangeToList(false, this.lib.number("1"), false, this.lib.number("2")).stream().map(i ->
                                this.lib.rangeToList(false, this.lib.number("2"), false, this.lib.number("3")).stream().map(j ->
                                        this.lib.numericGreaterThan(this.lib.numericAdd(i, j), this.lib.number("1"))))
                                .flatMap(x -> x)
                                .collect(Collectors.toList())),
                true);
        doExpressionTest(entries, "", "every i in [1..2] j in [2..3] satisfies i + j > 1",
                "QuantifiedExpression(every, Iterator(i in ExpressionIteratorDomain(EndpointsRange(false,NumericLiteral(1),false,NumericLiteral(2)))),Iterator(j in ExpressionIteratorDomain(EndpointsRange(false,NumericLiteral(2),false,NumericLiteral(3)))) -> Relational(>,Addition(+,Name(i),Name(j)),NumericLiteral(1)))",
                "boolean",
                "booleanAnd((List)rangeToList(false, number(\"1\"), false, number(\"2\")).stream().map(i -> rangeToList(false, number(\"2\"), false, number(\"3\")).stream().map(j -> numericGreaterThan(numericAdd(i, j), number(\"1\")))).flatMap(x -> x).collect(Collectors.toList()))",
                this.lib.booleanAnd((List)
                        this.lib.rangeToList(false, this.lib.number("1"), false, this.lib.number("2")).stream().map(i ->
                                this.lib.rangeToList(false, this.lib.number("2"), false, this.lib.number("3")).stream().map(j ->
                                        this.lib.numericGreaterThan(this.lib.numericAdd(i, j), this.lib.number("1"))))
                                .flatMap(x -> x)
                                .collect(Collectors.toList())),
                true);

        doExpressionTest(entries, "", "some i in [1, 2] j in [2, 3] satisfies i + j > 1",
                "QuantifiedExpression(some, Iterator(i in ExpressionIteratorDomain(ListLiteral(NumericLiteral(1),NumericLiteral(2)))),Iterator(j in ExpressionIteratorDomain(ListLiteral(NumericLiteral(2),NumericLiteral(3)))) -> Relational(>,Addition(+,Name(i),Name(j)),NumericLiteral(1)))",
                "boolean",
                "booleanOr((List)asList(number(\"1\"), number(\"2\")).stream().map(i -> asList(number(\"2\"), number(\"3\")).stream().map(j -> numericGreaterThan(numericAdd(i, j), number(\"1\")))).flatMap(x -> x).collect(Collectors.toList()))",
                this.lib.booleanOr(Arrays.asList(this.lib.number("1"), this.lib.number("2")).stream().map(i ->
                        Arrays.asList(this.lib.number("2"), this.lib.number("3")).stream().map(j ->
                                this.lib.numericGreaterThan(this.lib.numericAdd(i, j), this.lib.number("1"))))
                        .flatMap(x -> x)
                        .collect(Collectors.toList())),
                true);
        doExpressionTest(entries, "", "every i in [1, 2] j in [2, 3] satisfies i + j > 1",
                "QuantifiedExpression(every, Iterator(i in ExpressionIteratorDomain(ListLiteral(NumericLiteral(1),NumericLiteral(2)))),Iterator(j in ExpressionIteratorDomain(ListLiteral(NumericLiteral(2),NumericLiteral(3)))) -> Relational(>,Addition(+,Name(i),Name(j)),NumericLiteral(1)))",
                "boolean",
                "booleanAnd((List)asList(number(\"1\"), number(\"2\")).stream().map(i -> asList(number(\"2\"), number(\"3\")).stream().map(j -> numericGreaterThan(numericAdd(i, j), number(\"1\")))).flatMap(x -> x).collect(Collectors.toList()))",
                this.lib.booleanAnd(Arrays.asList(this.lib.number("1"), this.lib.number("2")).stream().map(i ->
                        Arrays.asList(this.lib.number("2"), this.lib.number("3")).stream().map(j ->
                                this.lib.numericGreaterThan(this.lib.numericAdd(i, j), this.lib.number("1"))))
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

        // range
        doExpressionTest(entries, "", "[1..10] = [1..10]",
                "Relational(=,EndpointsRange(false,NumericLiteral(1),false,NumericLiteral(10)),EndpointsRange(false,NumericLiteral(1),false,NumericLiteral(10)))",
                "boolean",
                "rangeEqual(new com.gs.dmn.runtime.Range(true, number(\"1\"), true, number(\"10\")), new com.gs.dmn.runtime.Range(true, number(\"1\"), true, number(\"10\")))",
                this.lib.rangeEqual(new com.gs.dmn.runtime.Range(true, this.lib.number("1"), true, this.lib.number("10")), new com.gs.dmn.runtime.Range(true, this.lib.number("1"), true, this.lib.number("10"))),
                true);
        doExpressionTest(entries, "", "[1..10] = [1..11]",
                "Relational(=,EndpointsRange(false,NumericLiteral(1),false,NumericLiteral(10)),EndpointsRange(false,NumericLiteral(1),false,NumericLiteral(11)))",
                "boolean",
                "rangeEqual(new com.gs.dmn.runtime.Range(true, number(\"1\"), true, number(\"10\")), new com.gs.dmn.runtime.Range(true, number(\"1\"), true, number(\"11\")))",
                this.lib.rangeEqual(new com.gs.dmn.runtime.Range(true, this.lib.number("1"), true, this.lib.number("10")), new com.gs.dmn.runtime.Range(true, this.lib.number("1"), true, this.lib.number("11"))),
                false);
        doExpressionTest(entries, "", "[1..10] = [1..10)",
                "Relational(=,EndpointsRange(false,NumericLiteral(1),false,NumericLiteral(10)),EndpointsRange(false,NumericLiteral(1),true,NumericLiteral(10)))",
                "boolean",
                "rangeEqual(new com.gs.dmn.runtime.Range(true, number(\"1\"), true, number(\"10\")), new com.gs.dmn.runtime.Range(true, number(\"1\"), false, number(\"10\")))",
                this.lib.rangeEqual(new com.gs.dmn.runtime.Range(true, this.lib.number("1"), true, this.lib.number("10")), new com.gs.dmn.runtime.Range(true, this.lib.number("1"), false, this.lib.number("10"))),
                false);
    }

    @Test
    public void testNullRelationalExpression() {
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, this.lib.number("1")));

        // number
        doExpressionTest(entries, "", "1 = null",
                "Relational(=,NumericLiteral(1),NullLiteral())",
                "boolean",
                "numericEqual(number(\"1\"), null)",
                this.lib.numericEqual(this.lib.number("1"), null),
                false);
        doExpressionTest(entries, "", "null = 2",
                "Relational(=,NullLiteral(),NumericLiteral(2))",
                "boolean",
                "numericEqual(null, number(\"2\"))",
                this.lib.numericEqual(null, this.lib.number("2")),
                false);
        doExpressionTest(entries, "", "1 != null",
                "Relational(!=,NumericLiteral(1),NullLiteral())",
                "boolean",
                "numericNotEqual(number(\"1\"), null)",
                this.lib.numericNotEqual(this.lib.number("1"), null),
                true);
        doExpressionTest(entries, "", "null != 2",
                "Relational(!=,NullLiteral(),NumericLiteral(2))",
                "boolean",
                "numericNotEqual(null, number(\"2\"))",
                this.lib.numericNotEqual(null, this.lib.number("2")),
                true);

        // boolean
        doExpressionTest(entries, "", "true = null",
                "Relational(=,BooleanLiteral(true),NullLiteral())",
                "boolean",
                "booleanEqual(Boolean.TRUE, null)",
                this.lib.booleanEqual(Boolean.TRUE, null),
                false);
        doExpressionTest(entries, "", "null = true",
                "Relational(=,NullLiteral(),BooleanLiteral(true))",
                "boolean",
                "booleanEqual(null, Boolean.TRUE)",
                this.lib.booleanEqual(null, Boolean.TRUE),
                false);
        doExpressionTest(entries, "", "true != null",
                "Relational(!=,BooleanLiteral(true),NullLiteral())",
                "boolean",
                "booleanNotEqual(Boolean.TRUE, null)",
                this.lib.booleanNotEqual(Boolean.TRUE, null),
                true);
        doExpressionTest(entries, "", "null != true",
                "Relational(!=,NullLiteral(),BooleanLiteral(true))",
                "boolean",
                "booleanNotEqual(null, Boolean.TRUE)",
                this.lib.booleanNotEqual(null, Boolean.TRUE),
                true);

        // string
        doExpressionTest(entries, "", "\"abc\" = null",
                "Relational(=,StringLiteral(\"abc\"),NullLiteral())",
                "boolean",
                "stringEqual(\"abc\", null)",
                this.lib.stringEqual("abc", null),
                false);
        doExpressionTest(entries, "", "null = \"abc\"",
                "Relational(=,NullLiteral(),StringLiteral(\"abc\"))",
                "boolean",
                "stringEqual(null, \"abc\")",
                this.lib.stringEqual(null, "abc"),
                false);
        doExpressionTest(entries, "", "\"abc\" != null",
                "Relational(!=,StringLiteral(\"abc\"),NullLiteral())",
                "boolean",
                "stringNotEqual(\"abc\", null)",
                this.lib.stringNotEqual("abc", null),
                true);
        doExpressionTest(entries, "", "null != \"abc\"",
                "Relational(!=,NullLiteral(),StringLiteral(\"abc\"))",
                "boolean",
                "stringNotEqual(null, \"abc\")",
                this.lib.stringNotEqual(null, "abc"),
                true);

        // date
        doExpressionTest(entries, "", "date(\"2016-03-01\") = null",
                "Relational(=,DateTimeLiteral(date, \"2016-03-01\"),NullLiteral())",
                "boolean",
                "dateEqual(date(\"2016-03-01\"), null)",
                this.lib.dateEqual(this.lib.date("2016-03-01"), null),
                false);
        doExpressionTest(entries, "", "null = date(\"2016-03-02\")",
                "Relational(=,NullLiteral(),DateTimeLiteral(date, \"2016-03-02\"))",
                "boolean",
                "dateEqual(null, date(\"2016-03-02\"))",
                this.lib.dateEqual(null, this.lib.date("2016-03-02")),
                false);
        doExpressionTest(entries, "", "date(\"2016-03-01\") != null",
                "Relational(!=,DateTimeLiteral(date, \"2016-03-01\"),NullLiteral())",
                "boolean",
                "dateNotEqual(date(\"2016-03-01\"), null)",
                this.lib.dateNotEqual(this.lib.date("2016-03-01"), null),
                true);
        doExpressionTest(entries, "", "null != date(\"2016-03-02\")",
                "Relational(!=,NullLiteral(),DateTimeLiteral(date, \"2016-03-02\"))",
                "boolean",
                "dateNotEqual(null, date(\"2016-03-02\"))",
                this.lib.dateNotEqual(null, this.lib.date("2016-03-02")),
                true);

        // date and time
        doExpressionTest(entries, "", "date and time(\"2016-03-01T12:00:00Z\") = null",
                "Relational(=,DateTimeLiteral(date and time, \"2016-03-01T12:00:00Z\"),NullLiteral())",
                "boolean",
                "dateTimeEqual(dateAndTime(\"2016-03-01T12:00:00Z\"), null)",
                this.lib.dateTimeEqual(this.lib.dateAndTime("2016-03-01T12:00:00Z"), null),
                false);
        doExpressionTest(entries, "", "null = date and time(\"2016-03-02T12:00:00Z\")",
                "Relational(=,NullLiteral(),DateTimeLiteral(date and time, \"2016-03-02T12:00:00Z\"))",
                "boolean",
                "dateTimeEqual(null, dateAndTime(\"2016-03-02T12:00:00Z\"))",
                this.lib.dateTimeEqual(null, this.lib.dateAndTime("2016-03-02T12:00:00Z")),
                false);
        doExpressionTest(entries, "", "date and time(\"2016-03-01T12:00:00Z\") != null",
                "Relational(!=,DateTimeLiteral(date and time, \"2016-03-01T12:00:00Z\"),NullLiteral())",
                "boolean",
                "dateTimeNotEqual(dateAndTime(\"2016-03-01T12:00:00Z\"), null)",
                this.lib.dateTimeNotEqual(this.lib.dateAndTime("2016-03-01T12:00:00Z"), null),
                true);
        doExpressionTest(entries, "", "null != date and time(\"2016-03-02T12:00:00Z\")",
                "Relational(!=,NullLiteral(),DateTimeLiteral(date and time, \"2016-03-02T12:00:00Z\"))",
                "boolean",
                "dateTimeNotEqual(null, dateAndTime(\"2016-03-02T12:00:00Z\"))",
                this.lib.dateTimeNotEqual(null, this.lib.dateAndTime("2016-03-02T12:00:00Z")),
                true);

        // time
        doExpressionTest(entries, "", "time(\"12:00:00Z\") = null",
                "Relational(=,DateTimeLiteral(time, \"12:00:00Z\"),NullLiteral())",
                "boolean",
                "timeEqual(time(\"12:00:00Z\"), null)",
                this.lib.timeEqual(this.lib.time("12:00:00Z"), null),
                false);
        doExpressionTest(entries, "", "null = time(\"12:01:00Z\")",
                "Relational(=,NullLiteral(),DateTimeLiteral(time, \"12:01:00Z\"))",
                "boolean",
                "timeEqual(null, time(\"12:01:00Z\"))",
                this.lib.timeEqual(null, this.lib.time("12:01:00Z")),
                false);
        doExpressionTest(entries, "", "time(\"12:00:00Z\") != null",
                "Relational(!=,DateTimeLiteral(time, \"12:00:00Z\"),NullLiteral())",
                "boolean",
                "timeNotEqual(time(\"12:00:00Z\"), null)",
                this.lib.timeNotEqual(this.lib.time("12:00:00Z"), null),
                true);
        doExpressionTest(entries, "", "null != time(\"12:01:00Z\")",
                "Relational(!=,NullLiteral(),DateTimeLiteral(time, \"12:01:00Z\"))",
                "boolean",
                "timeNotEqual(null, time(\"12:01:00Z\"))",
                this.lib.timeNotEqual(null, this.lib.time("12:01:00Z")),
                true);

        // duration
        doExpressionTest(entries, "", "duration(\"P1Y1M\") = null",
                "Relational(=,DateTimeLiteral(duration, \"P1Y1M\"),NullLiteral())",
                "boolean",
                "durationEqual(duration(\"P1Y1M\"), null)",
                this.lib.durationEqual(this.lib.duration("P1Y1M"), null),
                false);
        doExpressionTest(entries, "", "null = duration(\"P1Y2M\")",
                "Relational(=,NullLiteral(),DateTimeLiteral(duration, \"P1Y2M\"))",
                "boolean",
                "durationEqual(null, duration(\"P1Y2M\"))",
                this.lib.durationEqual(null, this.lib.duration("P1Y2M")),
                false);
        doExpressionTest(entries, "", "duration(\"P1Y1M\") != null",
                "Relational(!=,DateTimeLiteral(duration, \"P1Y1M\"),NullLiteral())",
                "boolean",
                "durationNotEqual(duration(\"P1Y1M\"), null)",
                this.lib.durationNotEqual(this.lib.duration("P1Y1M"), null),
                true);
        doExpressionTest(entries, "", "null != duration(\"P1Y2M\")",
                "Relational(!=,NullLiteral(),DateTimeLiteral(duration, \"P1Y2M\"))",
                "boolean",
                "durationNotEqual(null, duration(\"P1Y2M\"))",
                this.lib.durationNotEqual(null, this.lib.duration("P1Y2M")),
                true);

        // range
        doExpressionTest(entries, "", "[1..10] = null",
                "Relational(=,EndpointsRange(false,NumericLiteral(1),false,NumericLiteral(10)),NullLiteral())",
                "boolean",
                "rangeEqual(new com.gs.dmn.runtime.Range(true, number(\"1\"), true, number(\"10\")), null)",
                this.lib.rangeEqual(new com.gs.dmn.runtime.Range(true, this.lib.number("1"), true, this.lib.number("10")), null),
                false);
        doExpressionTest(entries, "", "null = [1..10]",
                "Relational(=,NullLiteral(),EndpointsRange(false,NumericLiteral(1),false,NumericLiteral(10)))",
                "boolean",
                "rangeEqual(null, new com.gs.dmn.runtime.Range(true, number(\"1\"), true, number(\"10\")))",
                this.lib.rangeEqual(null, new com.gs.dmn.runtime.Range(true, this.lib.number("1"), true, this.lib.number("10"))),
                false);

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
                this.lib.listEqual(this.lib.asList(), this.lib.asList()),
                true);
        doExpressionTest(entries, "", "[] = null",
                "Relational(=,ListLiteral(),NullLiteral())",
                "boolean",
                "listEqual(asList(), null)",
                this.lib.listEqual(this.lib.asList(), null),
                false);

        doExpressionTest(entries, "", "[] != []",
                "Relational(!=,ListLiteral(),ListLiteral())",
                "boolean",
                "listNotEqual(asList(), asList())",
                this.lib.listNotEqual(this.lib.asList(), this.lib.asList()),
                false);
        doExpressionTest(entries, "", "[] != null",
                "Relational(!=,ListLiteral(),NullLiteral())",
                "boolean",
                "listNotEqual(asList(), null)",
                this.lib.listNotEqual(this.lib.asList(), null),
                true);

        // context
        doExpressionTest(entries, "", "{} = {}",
                "Relational(=,Context(),Context())",
                "boolean",
                "contextEqual(new com.gs.dmn.runtime.Context(), new com.gs.dmn.runtime.Context())",
                this.lib.contextEqual(new com.gs.dmn.runtime.Context(), new com.gs.dmn.runtime.Context()),
                true);
        doExpressionTest(entries, "", "{} = null",
                "Relational(=,Context(),NullLiteral())",
                "boolean",
                "contextEqual(new com.gs.dmn.runtime.Context(), null)",
                this.lib.contextEqual(new com.gs.dmn.runtime.Context(), null),
                false);

        doExpressionTest(entries, "", "{} != {}",
                "Relational(!=,Context(),Context())",
                "boolean",
                "contextNotEqual(new com.gs.dmn.runtime.Context(), new com.gs.dmn.runtime.Context())",
                this.lib.contextNotEqual(new com.gs.dmn.runtime.Context(), new com.gs.dmn.runtime.Context()),
                false);
        doExpressionTest(entries, "", "{} != null",
                "Relational(!=,Context(),NullLiteral())",
                "boolean",
                "contextNotEqual(new com.gs.dmn.runtime.Context(), null)",
                this.lib.contextNotEqual(new com.gs.dmn.runtime.Context(), null),
                true);

        // complex
        doExpressionTest(entries, "", "[1,2,{a: [3,4]}] = [1,2,{a: [3,4]}]",
                "Relational(=,ListLiteral(NumericLiteral(1),NumericLiteral(2),Context(ContextEntry(ContextEntryKey(a) = ListLiteral(NumericLiteral(3),NumericLiteral(4))))),ListLiteral(NumericLiteral(1),NumericLiteral(2),Context(ContextEntry(ContextEntryKey(a) = ListLiteral(NumericLiteral(3),NumericLiteral(4))))))",
                "boolean",
                "listEqual(asList(number(\"1\"), number(\"2\"), new com.gs.dmn.runtime.Context().add(\"a\", asList(number(\"3\"), number(\"4\")))), asList(number(\"1\"), number(\"2\"), new com.gs.dmn.runtime.Context().add(\"a\", asList(number(\"3\"), number(\"4\")))))",
                this.lib.listEqual(this.lib.asList(this.lib.number("1"), this.lib.number("2"), new com.gs.dmn.runtime.Context().add("a", this.lib.asList(this.lib.number("3"), this.lib.number("4")))), this.lib.asList(this.lib.number("1"), this.lib.number("2"), new com.gs.dmn.runtime.Context().add("a", this.lib.asList(this.lib.number("3"), this.lib.number("4"))))),
                true);

        // complex
        doExpressionTest(entries, "", "[1,2,{a: [3,4]}] = [1,2,{a: [3,4], b: \"foo\"}]",
                "Relational(=,ListLiteral(NumericLiteral(1),NumericLiteral(2),Context(ContextEntry(ContextEntryKey(a) = ListLiteral(NumericLiteral(3),NumericLiteral(4))))),ListLiteral(NumericLiteral(1),NumericLiteral(2),Context(ContextEntry(ContextEntryKey(a) = ListLiteral(NumericLiteral(3),NumericLiteral(4))),ContextEntry(ContextEntryKey(b) = StringLiteral(\"foo\")))))",
                "boolean",
                "listEqual(asList(number(\"1\"), number(\"2\"), new com.gs.dmn.runtime.Context().add(\"a\", asList(number(\"3\"), number(\"4\")))), asList(number(\"1\"), number(\"2\"), new com.gs.dmn.runtime.Context().add(\"a\", asList(number(\"3\"), number(\"4\"))).add(\"b\", \"foo\")))",
                this.lib.listEqual(this.lib.asList(this.lib.number("1"), this.lib.number("2"), new com.gs.dmn.runtime.Context().add("a", this.lib.asList(this.lib.number("3"), this.lib.number("4")))), this.lib.asList(this.lib.number("1"), this.lib.number("2"), new com.gs.dmn.runtime.Context().add("a", this.lib.asList(this.lib.number("3"), this.lib.number("4"))).add("b", "foo"))),
                false);

    }

    @Test
    public void testBetweenExpression() {
        NUMBER i = this.lib.number("1");
        NUMBER a = this.lib.number("1");
        NUMBER b = this.lib.number("1");
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("i", NUMBER, i),
                new EnvironmentEntry("a", NUMBER, a),
                new EnvironmentEntry("b", NUMBER, b));

        doExpressionTest(entries, "", "3 between 1 and 4",
                "BetweenExpression(NumericLiteral(3), NumericLiteral(1), NumericLiteral(4))",
                "boolean",
                "booleanAnd(numericLessEqualThan(number(\"1\"), number(\"3\")), numericLessEqualThan(number(\"3\"), number(\"4\")))",
                this.lib.booleanAnd(this.lib.numericLessEqualThan(this.lib.number("1"), this.lib.number("3")), this.lib.numericLessEqualThan(this.lib.number("3"), this.lib.number("4"))),
                true);
        doExpressionTest(entries, "", "(i) between (a) and (b)",
                "BetweenExpression(Name(i), Name(a), Name(b))",
                "boolean",
                "booleanAnd(numericLessEqualThan(a, i), numericLessEqualThan(i, b))",
                this.lib.booleanAnd(this.lib.numericLessEqualThan(a, i), this.lib.numericLessEqualThan(i, b)),
                true);
        doExpressionTest(entries, "", "(i) between 1 and 2",
                "BetweenExpression(Name(i), NumericLiteral(1), NumericLiteral(2))",
                "boolean",
                "booleanAnd(numericLessEqualThan(number(\"1\"), i), numericLessEqualThan(i, number(\"2\")))",
                this.lib.booleanAnd(this.lib.numericLessEqualThan(this.lib.number("1"), i), this.lib.numericLessEqualThan(i, this.lib.number("2"))),
                true);

        doExpressionTest(entries, "", "date(\"2018-12-01\") between date(\"2018-12-02\") and date(\"2018-12-04\")",
                "BetweenExpression(DateTimeLiteral(date, \"2018-12-01\"), DateTimeLiteral(date, \"2018-12-02\"), DateTimeLiteral(date, \"2018-12-04\"))",
                "boolean",
                "booleanAnd(dateLessEqualThan(date(\"2018-12-02\"), date(\"2018-12-01\")), dateLessEqualThan(date(\"2018-12-01\"), date(\"2018-12-04\")))",
                this.lib.booleanAnd(this.lib.dateLessEqualThan(this.lib.date("2018-12-02"), this.lib.date("2018-12-01")), this.lib.dateLessEqualThan(this.lib.date("2018-12-01"), this.lib.date("2018-12-04"))),
                false);
        doExpressionTest(entries, "", "time(\"10:31:00\") between time(\"10:32:00\") and time(\"10:34:00\")",
                "BetweenExpression(DateTimeLiteral(time, \"10:31:00\"), DateTimeLiteral(time, \"10:32:00\"), DateTimeLiteral(time, \"10:34:00\"))",
                "boolean",
                "booleanAnd(timeLessEqualThan(time(\"10:32:00\"), time(\"10:31:00\")), timeLessEqualThan(time(\"10:31:00\"), time(\"10:34:00\")))",
                this.lib.booleanAnd(this.lib.timeLessEqualThan(this.lib.time("10:32:00"), this.lib.time("10:31:00")), this.lib.timeLessEqualThan(this.lib.time("10:31:00"), this.lib.time("10:34:00"))),
                false);
        doExpressionTest(entries, "", "date and time(\"2018-12-01T10:30:00\") between date and time(\"2018-12-02T10:30:00\") and date and time(\"2018-12-04T10:30:00\")",
                "BetweenExpression(DateTimeLiteral(date and time, \"2018-12-01T10:30:00\"), DateTimeLiteral(date and time, \"2018-12-02T10:30:00\"), DateTimeLiteral(date and time, \"2018-12-04T10:30:00\"))",
                "boolean",
                "booleanAnd(dateTimeLessEqualThan(dateAndTime(\"2018-12-02T10:30:00\"), dateAndTime(\"2018-12-01T10:30:00\")), dateTimeLessEqualThan(dateAndTime(\"2018-12-01T10:30:00\"), dateAndTime(\"2018-12-04T10:30:00\")))",
                this.lib.booleanAnd(this.lib.dateTimeLessEqualThan(this.lib.dateAndTime("2018-12-02T10:30:00"), this.lib.dateAndTime("2018-12-01T10:30:00")), this.lib.dateTimeLessEqualThan(this.lib.dateAndTime("2018-12-01T10:30:00"), this.lib.dateAndTime("2018-12-04T10:30:00"))),
                false);
        doExpressionTest(entries, "", "duration(\"P1Y\") between duration(\"P2Y\") and duration(\"P4Y\")",
                "BetweenExpression(DateTimeLiteral(duration, \"P1Y\"), DateTimeLiteral(duration, \"P2Y\"), DateTimeLiteral(duration, \"P4Y\"))",
                "boolean",
                "booleanAnd(durationLessEqualThan(duration(\"P2Y\"), duration(\"P1Y\")), durationLessEqualThan(duration(\"P1Y\"), duration(\"P4Y\")))",
                this.lib.booleanAnd(this.lib.durationLessEqualThan(this.lib.duration("P2Y"), this.lib.duration("P1Y")), this.lib.durationLessEqualThan(this.lib.duration("P1Y"), this.lib.duration("P4Y"))),
                false);
        doExpressionTest(entries, "", "duration(\"P1D\") between duration(\"P2D\") and duration(\"P4D\")",
                "BetweenExpression(DateTimeLiteral(duration, \"P1D\"), DateTimeLiteral(duration, \"P2D\"), DateTimeLiteral(duration, \"P4D\"))",
                "boolean",
                "booleanAnd(durationLessEqualThan(duration(\"P2D\"), duration(\"P1D\")), durationLessEqualThan(duration(\"P1D\"), duration(\"P4D\")))",
                this.lib.booleanAnd(this.lib.durationLessEqualThan(this.lib.duration("P2D"), this.lib.duration("P1D")), this.lib.durationLessEqualThan(this.lib.duration("P1D"), this.lib.duration("P4D"))),
                false);
    }

    @Test
    public void testInExpression() {
        // operator test
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, this.lib.number("1")));

        doExpressionTest(entries, "", "1 in 1",
                "InExpression(NumericLiteral(1), OperatorRange(null,NumericLiteral(1)))",
                "boolean",
                "(numericEqual(number(\"1\"), number(\"1\")))",
                (this.lib.numericEqual(this.lib.number("1"), this.lib.number("1"))),
                true);
        doExpressionTest(entries, "", "1 in <1",
                "InExpression(NumericLiteral(1), OperatorRange(<,NumericLiteral(1)))",
                "boolean",
                "(numericLessThan(number(\"1\"), number(\"1\")))",
                (this.lib.numericLessThan(this.lib.number("1"), this.lib.number("1"))),
                false);
        doExpressionTest(entries, "", "1 in <=1",
                "InExpression(NumericLiteral(1), OperatorRange(<=,NumericLiteral(1)))",
                "boolean",
                "(numericLessEqualThan(number(\"1\"), number(\"1\")))",
                (this.lib.numericLessEqualThan(this.lib.number("1"), this.lib.number("1"))),
                true);
        doExpressionTest(entries, "", "1 in >1",
                "InExpression(NumericLiteral(1), OperatorRange(>,NumericLiteral(1)))",
                "boolean",
                "(numericGreaterThan(number(\"1\"), number(\"1\")))",
                (this.lib.numericGreaterThan(this.lib.number("1"), this.lib.number("1"))),
                false);
        doExpressionTest(entries, "", "1 in >=1",
                "InExpression(NumericLiteral(1), OperatorRange(>=,NumericLiteral(1)))",
                "boolean",
                "(numericGreaterEqualThan(number(\"1\"), number(\"1\")))",
                (this.lib.numericGreaterEqualThan(this.lib.number("1"), this.lib.number("1"))),
                true);

        // interval test
        doExpressionTest(entries, "", "1 in (1..2)",
                "InExpression(NumericLiteral(1), EndpointsRange(true,NumericLiteral(1),true,NumericLiteral(2)))",
                "boolean",
                "(booleanAnd(numericGreaterThan(number(\"1\"), number(\"1\")), numericLessThan(number(\"1\"), number(\"2\"))))",
                (this.lib.booleanAnd(this.lib.numericGreaterThan(this.lib.number("1"), this.lib.number("1")), this.lib.numericLessThan(this.lib.number("1"), this.lib.number("2")))),
                false);
        doExpressionTest(entries, "", "1 in (1..2]",
                "InExpression(NumericLiteral(1), EndpointsRange(true,NumericLiteral(1),false,NumericLiteral(2)))",
                "boolean",
                "(booleanAnd(numericGreaterThan(number(\"1\"), number(\"1\")), numericLessEqualThan(number(\"1\"), number(\"2\"))))",
                (this.lib.booleanAnd(this.lib.numericGreaterThan(this.lib.number("1"), this.lib.number("1")), this.lib.numericLessEqualThan(this.lib.number("1"), this.lib.number("2")))),
                false);
        doExpressionTest(entries, "", "1 in [1..2)",
                "InExpression(NumericLiteral(1), EndpointsRange(false,NumericLiteral(1),true,NumericLiteral(2)))",
                "boolean",
                "(booleanAnd(numericGreaterEqualThan(number(\"1\"), number(\"1\")), numericLessThan(number(\"1\"), number(\"2\"))))",
                (this.lib.booleanAnd(this.lib.numericGreaterEqualThan(this.lib.number("1"), this.lib.number("1")), this.lib.numericLessThan(this.lib.number("1"), this.lib.number("2")))),
                true);
        doExpressionTest(entries, "", "1 in [1..2]",
                "InExpression(NumericLiteral(1), EndpointsRange(false,NumericLiteral(1),false,NumericLiteral(2)))",
                "boolean",
                "(booleanAnd(numericGreaterEqualThan(number(\"1\"), number(\"1\")), numericLessEqualThan(number(\"1\"), number(\"2\"))))",
                (this.lib.booleanAnd(this.lib.numericGreaterEqualThan(this.lib.number("1"), this.lib.number("1")), this.lib.numericLessEqualThan(this.lib.number("1"), this.lib.number("2")))),
                true);

        // list test
        doExpressionTest(entries, "", "1 in [1, 2]",
                "InExpression(NumericLiteral(1), ListTest(ListLiteral(OperatorRange(null,NumericLiteral(1)),OperatorRange(null,NumericLiteral(2)))))",
                "boolean",
                "(listContains(asList(number(\"1\"), number(\"2\")), number(\"1\")))",
                (this.lib.listContains(Arrays.asList(this.lib.number("1"), this.lib.number("2")), this.lib.number("1"))),
                true);
        doExpressionTest(entries, "", "[1, 2, 3] in [1, 2, 3]",
                "InExpression(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), ListTest(ListLiteral(OperatorRange(null,NumericLiteral(1)),OperatorRange(null,NumericLiteral(2)),OperatorRange(null,NumericLiteral(3)))))",
                "boolean",
                "(listEqual(asList(number(\"1\"), number(\"2\"), number(\"3\")), asList(number(\"1\"), number(\"2\"), number(\"3\"))))",
                (this.lib.listEqual(this.lib.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3")), this.lib.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3")))),
                true);
        doExpressionTest(entries, "", "[1,2,3] in ([1,2,3,4], [1,2,3])",
                "InExpression(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), ListTest(ListLiteral(OperatorRange(null,NumericLiteral(1)),OperatorRange(null,NumericLiteral(2)),OperatorRange(null,NumericLiteral(3)),OperatorRange(null,NumericLiteral(4)))), ListTest(ListLiteral(OperatorRange(null,NumericLiteral(1)),OperatorRange(null,NumericLiteral(2)),OperatorRange(null,NumericLiteral(3)))))",
                "boolean",
                "booleanOr(listEqual(asList(number(\"1\"), number(\"2\"), number(\"3\")), asList(number(\"1\"), number(\"2\"), number(\"3\"), number(\"4\"))), listEqual(asList(number(\"1\"), number(\"2\"), number(\"3\")), asList(number(\"1\"), number(\"2\"), number(\"3\"))))",
                this.lib.booleanOr(this.lib.listEqual(this.lib.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3")), this.lib.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3"), this.lib.number("4"))), this.lib.listEqual(this.lib.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3")), this.lib.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3")))),
                true);
        doExpressionTest(entries, "", "[1,2,3] in ([[1,2,3,4]], [[1,2,3]])",
                "InExpression(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), ListTest(ListLiteral(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3),NumericLiteral(4)))), ListTest(ListLiteral(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)))))",
                "boolean",
                "booleanOr(listContains(asList(asList(number(\"1\"), number(\"2\"), number(\"3\"), number(\"4\"))), asList(number(\"1\"), number(\"2\"), number(\"3\"))), listContains(asList(asList(number(\"1\"), number(\"2\"), number(\"3\"))), asList(number(\"1\"), number(\"2\"), number(\"3\"))))",
                this.lib.booleanOr(this.lib.listContains(this.lib.asList(this.lib.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3"), this.lib.number("4"))), this.lib.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3"))), this.lib.listContains(this.lib.asList(this.lib.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3"))), this.lib.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3")))),
                true);
        doExpressionTest(entries, "", "[1,2,3] in ([[1,2,3,4]], [[1,2,3]])",
                "InExpression(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), ListTest(ListLiteral(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3),NumericLiteral(4)))), ListTest(ListLiteral(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)))))",
                "boolean",
                "booleanOr(listContains(asList(asList(number(\"1\"), number(\"2\"), number(\"3\"), number(\"4\"))), asList(number(\"1\"), number(\"2\"), number(\"3\"))), listContains(asList(asList(number(\"1\"), number(\"2\"), number(\"3\"))), asList(number(\"1\"), number(\"2\"), number(\"3\"))))",
                this.lib.booleanOr(this.lib.listContains(this.lib.asList(this.lib.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3"), this.lib.number("4"))), this.lib.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3"))), this.lib.listContains(this.lib.asList(this.lib.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3"))), this.lib.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3")))),
                true);
        doExpressionTest(entries, "", "[1,2,3] in [[1,2,3,4], [1,2,3]]",
                "InExpression(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), ListTest(ListLiteral(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3),NumericLiteral(4)),ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)))))",
                "boolean",
                "(listContains(asList(asList(number(\"1\"), number(\"2\"), number(\"3\"), number(\"4\")), asList(number(\"1\"), number(\"2\"), number(\"3\"))), asList(number(\"1\"), number(\"2\"), number(\"3\"))))",
                (this.lib.listContains(this.lib.asList(this.lib.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3"), this.lib.number("4")), this.lib.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3"))), this.lib.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3")))),
                true);

        // list test containing EndpointsRange
        doExpressionTest(entries, "", "1 in [[2..4], [1..3]]",
                "InExpression(NumericLiteral(1), ListTest(ListLiteral(EndpointsRange(false,NumericLiteral(2),false,NumericLiteral(4)),EndpointsRange(false,NumericLiteral(1),false,NumericLiteral(3)))))",
                "boolean",
                "(listContains(asList(booleanAnd(numericGreaterEqualThan(number(\"1\"), number(\"2\")), numericLessEqualThan(number(\"1\"), number(\"4\"))), booleanAnd(numericGreaterEqualThan(number(\"1\"), number(\"1\")), numericLessEqualThan(number(\"1\"), number(\"3\")))), true))",
                (this.lib.listContains(this.lib.asList(this.lib.booleanAnd(this.lib.numericGreaterEqualThan(this.lib.number("1"), this.lib.number("2")), this.lib.numericLessEqualThan(this.lib.number("1"), this.lib.number("4"))), this.lib.booleanAnd(this.lib.numericGreaterEqualThan(this.lib.number("1"), this.lib.number("1")), this.lib.numericLessEqualThan(this.lib.number("1"), this.lib.number("3")))), true)),
                true);
        doExpressionTest(entries, "", "date(\"2018-12-11\") in [[date(\"2018-12-05\") .. date(\"2018-12-07\")], [date(\"2018-12-10\") .. date(\"2018-12-12\")]]",
                "InExpression(DateTimeLiteral(date, \"2018-12-11\"), ListTest(ListLiteral(EndpointsRange(false,DateTimeLiteral(date, \"2018-12-05\"),false,DateTimeLiteral(date, \"2018-12-07\")),EndpointsRange(false,DateTimeLiteral(date, \"2018-12-10\"),false,DateTimeLiteral(date, \"2018-12-12\")))))",
                "boolean",
                "(listContains(asList(booleanAnd(dateGreaterEqualThan(date(\"2018-12-11\"), date(\"2018-12-05\")), dateLessEqualThan(date(\"2018-12-11\"), date(\"2018-12-07\"))), booleanAnd(dateGreaterEqualThan(date(\"2018-12-11\"), date(\"2018-12-10\")), dateLessEqualThan(date(\"2018-12-11\"), date(\"2018-12-12\")))), true))",
                (this.lib.listContains(this.lib.asList(this.lib.booleanAnd(this.lib.dateGreaterEqualThan(this.lib.date("2018-12-11"), this.lib.date("2018-12-05")), this.lib.dateLessEqualThan(this.lib.date("2018-12-11"), this.lib.date("2018-12-07"))), this.lib.booleanAnd(this.lib.dateGreaterEqualThan(this.lib.date("2018-12-11"), this.lib.date("2018-12-10")), this.lib.dateLessEqualThan(this.lib.date("2018-12-11"), this.lib.date("2018-12-12")))), true)),
                true);
        doExpressionTest(entries, "", "time(\"10:30:11\") in [[time(\"10:30:05\") .. time(\"10:30:07\")], [time(\"10:30:10\") .. time(\"10:30:12\")]]",
                "InExpression(DateTimeLiteral(time, \"10:30:11\"), ListTest(ListLiteral(EndpointsRange(false,DateTimeLiteral(time, \"10:30:05\"),false,DateTimeLiteral(time, \"10:30:07\")),EndpointsRange(false,DateTimeLiteral(time, \"10:30:10\"),false,DateTimeLiteral(time, \"10:30:12\")))))",
                "boolean",
                "(listContains(asList(booleanAnd(timeGreaterEqualThan(time(\"10:30:11\"), time(\"10:30:05\")), timeLessEqualThan(time(\"10:30:11\"), time(\"10:30:07\"))), booleanAnd(timeGreaterEqualThan(time(\"10:30:11\"), time(\"10:30:10\")), timeLessEqualThan(time(\"10:30:11\"), time(\"10:30:12\")))), true))",
                (this.lib.listContains(this.lib.asList(this.lib.booleanAnd(this.lib.timeGreaterEqualThan(this.lib.time("10:30:11"), this.lib.time("10:30:05")), this.lib.timeLessEqualThan(this.lib.time("10:30:11"), this.lib.time("10:30:07"))), this.lib.booleanAnd(this.lib.timeGreaterEqualThan(this.lib.time("10:30:11"), this.lib.time("10:30:10")), this.lib.timeLessEqualThan(this.lib.time("10:30:11"), this.lib.time("10:30:12")))), true)),
                true);
        doExpressionTest(entries, "", "date and time(\"2018-12-08T10:30:11\") in [[date and time(\"2018-12-08T10:30:05\") .. date and time(\"2018-12-08T10:30:07\")], [date and time(\"2018-12-08T10:30:10\") .. date and time(\"2018-12-08T10:30:12\")]]",
                "InExpression(DateTimeLiteral(date and time, \"2018-12-08T10:30:11\"), ListTest(ListLiteral(EndpointsRange(false,DateTimeLiteral(date and time, \"2018-12-08T10:30:05\"),false,DateTimeLiteral(date and time, \"2018-12-08T10:30:07\")),EndpointsRange(false,DateTimeLiteral(date and time, \"2018-12-08T10:30:10\"),false,DateTimeLiteral(date and time, \"2018-12-08T10:30:12\")))))",
                "boolean",
                "(listContains(asList(booleanAnd(dateTimeGreaterEqualThan(dateAndTime(\"2018-12-08T10:30:11\"), dateAndTime(\"2018-12-08T10:30:05\")), dateTimeLessEqualThan(dateAndTime(\"2018-12-08T10:30:11\"), dateAndTime(\"2018-12-08T10:30:07\"))), booleanAnd(dateTimeGreaterEqualThan(dateAndTime(\"2018-12-08T10:30:11\"), dateAndTime(\"2018-12-08T10:30:10\")), dateTimeLessEqualThan(dateAndTime(\"2018-12-08T10:30:11\"), dateAndTime(\"2018-12-08T10:30:12\")))), true))",
                (this.lib.listContains(this.lib.asList(this.lib.booleanAnd(this.lib.dateTimeGreaterEqualThan(this.lib.dateAndTime("2018-12-08T10:30:11"), this.lib.dateAndTime("2018-12-08T10:30:05")), this.lib.dateTimeLessEqualThan(this.lib.dateAndTime("2018-12-08T10:30:11"), this.lib.dateAndTime("2018-12-08T10:30:07"))), this.lib.booleanAnd(this.lib.dateTimeGreaterEqualThan(this.lib.dateAndTime("2018-12-08T10:30:11"), this.lib.dateAndTime("2018-12-08T10:30:10")), this.lib.dateTimeLessEqualThan(this.lib.dateAndTime("2018-12-08T10:30:11"), this.lib.dateAndTime("2018-12-08T10:30:12")))), true)),
                true);
        doExpressionTest(entries, "", "{a: \"foo\"} in [{b: \"bar\"}, {a: \"foo\"}]",
                "InExpression(Context(ContextEntry(ContextEntryKey(a) = StringLiteral(\"foo\"))), ListTest(ListLiteral(OperatorRange(null,Context(ContextEntry(ContextEntryKey(b) = StringLiteral(\"bar\")))),OperatorRange(null,Context(ContextEntry(ContextEntryKey(a) = StringLiteral(\"foo\")))))))",
                "boolean",
                "(listContains(asList(new com.gs.dmn.runtime.Context().add(\"b\", \"bar\"), new com.gs.dmn.runtime.Context().add(\"a\", \"foo\")), new com.gs.dmn.runtime.Context().add(\"a\", \"foo\")))",
                (this.lib.listContains(this.lib.asList(new com.gs.dmn.runtime.Context().add("b", "bar"), new com.gs.dmn.runtime.Context().add("a", "foo")), new com.gs.dmn.runtime.Context().add("a", "foo"))),
                true);

        doExpressionTest(entries, "", "{a: \"foo\"} in ({a: \"bar\"}, {a: \"foo\"})",
                "InExpression(Context(ContextEntry(ContextEntryKey(a) = StringLiteral(\"foo\"))), OperatorRange(null,Context(ContextEntry(ContextEntryKey(a) = StringLiteral(\"bar\")))), OperatorRange(null,Context(ContextEntry(ContextEntryKey(a) = StringLiteral(\"foo\")))))",
                "boolean",
                "booleanOr(contextEqual(new com.gs.dmn.runtime.Context().add(\"a\", \"foo\"), new com.gs.dmn.runtime.Context().add(\"a\", \"bar\")), contextEqual(new com.gs.dmn.runtime.Context().add(\"a\", \"foo\"), new com.gs.dmn.runtime.Context().add(\"a\", \"foo\")))",
                this.lib.booleanOr(this.lib.contextEqual(new com.gs.dmn.runtime.Context().add("a", "foo"), new com.gs.dmn.runtime.Context().add("a", "bar")), this.lib.contextEqual(new com.gs.dmn.runtime.Context().add("a", "foo"), new com.gs.dmn.runtime.Context().add("a", "foo"))),
                true);
        doExpressionTest(entries, "", "{a: \"foo\"} in ({a: \"bar\"}, {a: \"baz\"})",
                "InExpression(Context(ContextEntry(ContextEntryKey(a) = StringLiteral(\"foo\"))), OperatorRange(null,Context(ContextEntry(ContextEntryKey(a) = StringLiteral(\"bar\")))), OperatorRange(null,Context(ContextEntry(ContextEntryKey(a) = StringLiteral(\"baz\")))))",
                "boolean",
                "booleanOr(contextEqual(new com.gs.dmn.runtime.Context().add(\"a\", \"foo\"), new com.gs.dmn.runtime.Context().add(\"a\", \"bar\")), contextEqual(new com.gs.dmn.runtime.Context().add(\"a\", \"foo\"), new com.gs.dmn.runtime.Context().add(\"a\", \"baz\")))",
                this.lib.booleanOr(this.lib.contextEqual(new com.gs.dmn.runtime.Context().add("a", "foo"), new com.gs.dmn.runtime.Context().add("a", "bar")), this.lib.contextEqual(new com.gs.dmn.runtime.Context().add("a", "foo"), new com.gs.dmn.runtime.Context().add("a", "baz"))),
                false);

        // compound test
        doExpressionTest(entries, "", "1 in (1)",
                "InExpression(NumericLiteral(1), OperatorRange(null,NumericLiteral(1)))",
                "boolean",
                "(numericEqual(number(\"1\"), number(\"1\")))",
                (this.lib.numericEqual(this.lib.number("1"), this.lib.number("1"))),
                true);
        doExpressionTest(entries, "", "1 in (1, 2)",
                "InExpression(NumericLiteral(1), OperatorRange(null,NumericLiteral(1)), OperatorRange(null,NumericLiteral(2)))",
                "boolean",
                "booleanOr(numericEqual(number(\"1\"), number(\"1\")), numericEqual(number(\"1\"), number(\"2\")))",
                this.lib.booleanOr(this.lib.numericEqual(this.lib.number("1"), this.lib.number("1")), this.lib.numericEqual(this.lib.number("1"), this.lib.number("2"))),
                true);
        doExpressionTest(entries, "", "1 in (<1, [1..2], [1, 2])",
                "InExpression(NumericLiteral(1), OperatorRange(<,NumericLiteral(1)), EndpointsRange(false,NumericLiteral(1),false,NumericLiteral(2)), ListTest(ListLiteral(OperatorRange(null,NumericLiteral(1)),OperatorRange(null,NumericLiteral(2)))))",
                "boolean",
                "booleanOr(numericLessThan(number(\"1\"), number(\"1\")), booleanAnd(numericGreaterEqualThan(number(\"1\"), number(\"1\")), numericLessEqualThan(number(\"1\"), number(\"2\"))), listContains(asList(number(\"1\"), number(\"2\")), number(\"1\")))",
                this.lib.booleanOr(this.lib.numericLessThan(this.lib.number("1"), this.lib.number("1")), this.lib.booleanAnd(this.lib.numericGreaterEqualThan(this.lib.number("1"), this.lib.number("1")), this.lib.numericLessEqualThan(this.lib.number("1"), this.lib.number("2"))), this.lib.listContains(this.lib.asList(this.lib.number("1"), this.lib.number("2")), this.lib.number("1"))),
                true);
        doExpressionTest(entries, "", "1 in (<1, [1..2], 3)",
                "InExpression(NumericLiteral(1), OperatorRange(<,NumericLiteral(1)), EndpointsRange(false,NumericLiteral(1),false,NumericLiteral(2)), OperatorRange(null,NumericLiteral(3)))",
                "boolean",
                "booleanOr(numericLessThan(number(\"1\"), number(\"1\")), booleanAnd(numericGreaterEqualThan(number(\"1\"), number(\"1\")), numericLessEqualThan(number(\"1\"), number(\"2\"))), numericEqual(number(\"1\"), number(\"3\")))",
                this.lib.booleanOr(this.lib.numericLessThan(this.lib.number("1"), this.lib.number("1")), this.lib.booleanAnd(this.lib.numericGreaterEqualThan(this.lib.number("1"), this.lib.number("1")), this.lib.numericLessEqualThan(this.lib.number("1"), this.lib.number("2"))), this.lib.numericEqual(this.lib.number("1"), this.lib.number("3"))),
                true);
    }

    @Test(expected = SemanticError.class)
    public void testInExpressionWhenOperatorRangeAndTypeMismatch() {
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, this.lib.number("1")));

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
                new EnvironmentEntry("input", NUMBER, this.lib.number("1")),
                new EnvironmentEntry("booleanA", BOOLEAN, booleanA),
                new EnvironmentEntry("booleanB", BOOLEAN, booleanB));

        doExpressionTest(entries, "", "(true) or (true)",
                "Disjunction(BooleanLiteral(true),BooleanLiteral(true))",
                "boolean",
                "booleanOr(Boolean.TRUE, Boolean.TRUE)",
                this.lib.booleanOr(Boolean.TRUE, Boolean.TRUE),
                true);
        doExpressionTest(entries, "", "true or true",
                "Disjunction(BooleanLiteral(true),BooleanLiteral(true))",
                "boolean",
                "booleanOr(Boolean.TRUE, Boolean.TRUE)",
                this.lib.booleanOr(Boolean.TRUE, Boolean.TRUE),
                true);
        doExpressionTest(entries, "", "true or 123",
                "Disjunction(BooleanLiteral(true),NumericLiteral(123))",
                "boolean",
                "booleanOr(Boolean.TRUE, number(\"123\"))",
                this.lib.booleanOr(Boolean.TRUE, this.lib.number("123")),
                true);
        doExpressionTest(entries, "", "false or 123",
                "Disjunction(BooleanLiteral(false),NumericLiteral(123))",
                "boolean",
                "booleanOr(Boolean.FALSE, number(\"123\"))",
                this.lib.booleanOr(Boolean.FALSE, this.lib.number("123")),
                null);

        doExpressionTest(entries, "", "(true) and (true)",
                "Conjunction(BooleanLiteral(true),BooleanLiteral(true))",
                "boolean",
                "booleanAnd(Boolean.TRUE, Boolean.TRUE)",
                this.lib.booleanAnd(Boolean.TRUE, Boolean.TRUE),
                true);
        doExpressionTest(entries, "", "true and true",
                "Conjunction(BooleanLiteral(true),BooleanLiteral(true))",
                "boolean",
                "booleanAnd(Boolean.TRUE, Boolean.TRUE)",
                this.lib.booleanAnd(Boolean.TRUE, Boolean.TRUE),
                true);
        doExpressionTest(entries, "", "false and 123",
                "Conjunction(BooleanLiteral(false),NumericLiteral(123))",
                "boolean",
                "booleanAnd(Boolean.FALSE, number(\"123\"))",
                this.lib.booleanAnd(Boolean.FALSE, this.lib.number("123")),
                false);
        doExpressionTest(entries, "", "true and 123",
                "Conjunction(BooleanLiteral(true),NumericLiteral(123))",
                "boolean",
                "booleanAnd(Boolean.TRUE, number(\"123\"))",
                this.lib.booleanAnd(Boolean.TRUE, this.lib.number("123")),
                null);

        doExpressionTest(entries, "", "not (true)",
                "LogicNegation(BooleanLiteral(true))",
                "boolean",
                "booleanNot(Boolean.TRUE)",
                this.lib.booleanNot(Boolean.TRUE),
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
                this.lib.booleanNot(Boolean.TRUE),
                false);

        doExpressionTest(entries, "", "booleanA and booleanB or booleanA",
                "Disjunction(Conjunction(Name(booleanA),Name(booleanB)),Name(booleanA))",
                "boolean",
                "booleanOr(booleanAnd(booleanA, booleanB), booleanA)",
                this.lib.booleanOr(this.lib.booleanAnd(booleanA, booleanB), booleanA),
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

        // number, number
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

        // date and time, date and time
        doExpressionTest(entries, "", String.format("%s %s %s", dateAndTime, "-", dateAndTime),
                "Addition(-,DateTimeLiteral(date and time, \"2016-08-01T11:00:00Z\"),DateTimeLiteral(date and time, \"2016-08-01T11:00:00Z\"))",
                "days and time duration",
                "dateTimeSubtract(dateAndTime(\"2016-08-01T11:00:00Z\"), dateAndTime(\"2016-08-01T11:00:00Z\"))",
                this.lib.dateTimeSubtract(this.lib.dateAndTime("2016-08-01T11:00:00Z"), this.lib.dateAndTime("2016-08-01T11:00:00Z")),
                this.lib.duration("P0DT0H0M0.000S"));

        // date, date
        doExpressionTest(entries, "", String.format("%s %s %s", date, "-", date),
                "Addition(-,DateTimeLiteral(date, \"2016-08-01\"),DateTimeLiteral(date, \"2016-08-01\"))",
                "days and time duration",
                "dateSubtract(date(\"2016-08-01\"), date(\"2016-08-01\"))",
                this.lib.dateSubtract(this.lib.date("2016-08-01"), this.lib.date("2016-08-01")),
                this.lib.duration("P0DT0H0M0.000S"));

        // date and time, date
        doExpressionTest(entries, "", String.format("%s %s %s", dateAndTime, "-", date),
                "Addition(-,DateTimeLiteral(date and time, \"2016-08-01T11:00:00Z\"),DateTimeLiteral(date, \"2016-08-01\"))",
                "days and time duration",
                "dateTimeSubtract(dateAndTime(\"2016-08-01T11:00:00Z\"), date(\"2016-08-01\"))",
                this.lib.dateTimeSubtract(this.lib.dateAndTime("2016-08-01T11:00:00Z"), this.lib.date("2016-08-01")),
                this.lib.duration("P0DT11H0M0S"));

        // date, date and time
        doExpressionTest(entries, "", String.format("%s %s %s", date, "-", dateAndTime),
                "Addition(-,DateTimeLiteral(date, \"2016-08-01\"),DateTimeLiteral(date and time, \"2016-08-01T11:00:00Z\"))",
                "days and time duration",
                "dateSubtract(date(\"2016-08-01\"), dateAndTime(\"2016-08-01T11:00:00Z\"))",
                this.lib.dateSubtract(this.lib.date("2016-08-01"), this.lib.dateAndTime("2016-08-01T11:00:00Z")),
                this.lib.duration("-P0DT11H0M0S"));

        // time, time
        doExpressionTest(entries, "", String.format("%s %s %s", time, "-", time),
                "Addition(-,DateTimeLiteral(time, \"12:00:00Z\"),DateTimeLiteral(time, \"12:00:00Z\"))",
                "days and time duration",
                "timeSubtract(time(\"12:00:00Z\"), time(\"12:00:00Z\"))",
                this.lib.timeSubtract(this.lib.time("12:00:00Z"), this.lib.time("12:00:00Z")),
                this.lib.duration("P0DT0H0M0.000S"));

        // years and months duration, years and months duration
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

        // days and time duration, days and time duration
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

        // date and time, years and months duration
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
        // date, years and months duration
        doExpressionTest(entries, "", String.format("%s %s %s", date, "+", yearsAndMonthsDuration),
                "Addition(+,DateTimeLiteral(date, \"2016-08-01\"),DateTimeLiteral(duration, \"P1Y1M\"))",
                "date",
                "dateAddDuration(date(\"2016-08-01\"), duration(\"P1Y1M\"))",
                this.lib.dateAddDuration(this.lib.date("2016-08-01"), this.lib.duration("P1Y1M")),
                this.lib.date("2017-09-01"));
        // Not in standard
        // date, years and months duration
        doExpressionTest(entries, "", String.format("%s %s %s", date, "-", yearsAndMonthsDuration),
                "Addition(-,DateTimeLiteral(date, \"2016-08-01\"),DateTimeLiteral(duration, \"P1Y1M\"))",
                "date",
                "dateSubtractDuration(date(\"2016-08-01\"), duration(\"P1Y1M\"))",
                this.lib.dateSubtractDuration(this.lib.date("2016-08-01"), this.lib.duration("P1Y1M")),
                this.lib.date("2015-07-01"));

        // years and months duration, date and time
        doExpressionTest(entries, "", String.format("%s %s %s", yearsAndMonthsDuration, "+", dateAndTime),
                "Addition(+,DateTimeLiteral(duration, \"P1Y1M\"),DateTimeLiteral(date and time, \"2016-08-01T11:00:00Z\"))",
                "date and time",
                "dateTimeAddDuration(dateAndTime(\"2016-08-01T11:00:00Z\"), duration(\"P1Y1M\"))",
                this.lib.dateTimeAddDuration(this.lib.dateAndTime("2016-08-01T11:00:00Z"), this.lib.duration("P1Y1M")),
                this.lib.dateAndTime("2017-09-01T11:00:00Z"));
        // Not in standard
        // years and months duration, date
        doExpressionTest(entries, "", String.format("%s %s %s", yearsAndMonthsDuration, "+", date),
                "Addition(+,DateTimeLiteral(duration, \"P1Y1M\"),DateTimeLiteral(date, \"2016-08-01\"))",
                "date",
                "dateAddDuration(date(\"2016-08-01\"), duration(\"P1Y1M\"))",
                this.lib.dateAddDuration(this.lib.date("2016-08-01"), this.lib.duration("P1Y1M")),
                this.lib.date("2017-09-01"));

        // date and time, days and time duration
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

        // days and time duration, date and time
        doExpressionTest(entries, "", String.format("%s %s %s", daysAndTimeDuration, "+", dateAndTime),
                "Addition(+,DateTimeLiteral(duration, \"P1DT1H\"),DateTimeLiteral(date and time, \"2016-08-01T11:00:00Z\"))",
                "date and time",
                "dateTimeAddDuration(dateAndTime(\"2016-08-01T11:00:00Z\"), duration(\"P1DT1H\"))",
                this.lib.dateTimeAddDuration(this.lib.dateAndTime("2016-08-01T11:00:00Z"), this.lib.duration("P1DT1H")),
                this.lib.dateAndTime("2016-08-02T12:00:00Z"));

        // time, days and time duration
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

        // days and time duration, time
        doExpressionTest(entries, "", String.format("%s %s %s", daysAndTimeDuration, "+", time),
                "Addition(+,DateTimeLiteral(duration, \"P1DT1H\"),DateTimeLiteral(time, \"12:00:00Z\"))",
                "time",
                "timeAddDuration(time(\"12:00:00Z\"), duration(\"P1DT1H\"))",
                this.lib.timeAddDuration(this.lib.time("12:00:00Z"), this.lib.duration("P1DT1H")),
                this.lib.time("13:00:00Z"));

        // string, string
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

        // number, number
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

        // years and months duration, number
        doExpressionTest(entries, "", String.format("%s %s %s", yearsAndMonthsDuration, "*", number),
                "Multiplication(*,DateTimeLiteral(duration, \"P1Y1M\"),NumericLiteral(1))",
                "years and months duration",
                "durationMultiplyNumber(duration(\"P1Y1M\"), number(\"1\"))",
                this.lib.durationMultiplyNumber(this.lib.duration("P1Y1M"), this.lib.number("1")),
                this.lib.duration("P1Y1M"));
        doExpressionTest(entries, "", String.format("%s %s %s", yearsAndMonthsDuration, "/", number),
                "Multiplication(/,DateTimeLiteral(duration, \"P1Y1M\"),NumericLiteral(1))",
                "years and months duration",
                "durationDivideNumber(duration(\"P1Y1M\"), number(\"1\"))",
                this.lib.durationDivideNumber(this.lib.duration("P1Y1M"), this.lib.number("1")),
                this.lib.duration("P1Y1M"));
        // number, years and months duration
        doExpressionTest(entries, "", String.format("%s %s %s", number, "*", yearsAndMonthsDuration),
                "Multiplication(*,NumericLiteral(1),DateTimeLiteral(duration, \"P1Y1M\"))",
                "years and months duration",
                "durationMultiplyNumber(duration(\"P1Y1M\"), number(\"1\"))",
                this.lib.durationMultiplyNumber(this.lib.duration("P1Y1M"), this.lib.number("1")),
                this.lib.duration("P1Y1M"));

        // years and months duration, years and months duration
        doExpressionTest(entries, "", String.format("%s %s %s", yearsAndMonthsDuration, "/", yearsAndMonthsDuration),
                "Multiplication(/,DateTimeLiteral(duration, \"P1Y1M\"),DateTimeLiteral(duration, \"P1Y1M\"))",
                "number",
                "durationDivide(duration(\"P1Y1M\"), duration(\"P1Y1M\"))",
                this.lib.durationDivide(this.lib.duration("P1Y1M"), this.lib.duration("P1Y1M")),
                this.lib.number("1"));

        // days and time duration, number
        doExpressionTest(entries, "", String.format("%s %s %s", daysAndTimeDuration, "*", number),
                "Multiplication(*,DateTimeLiteral(duration, \"P1DT1H\"),NumericLiteral(1))",
                "days and time duration",
                "durationMultiplyNumber(duration(\"P1DT1H\"), number(\"1\"))",
                this.lib.durationMultiplyNumber(this.lib.duration("P1DT1H"), this.lib.number("1")),
                this.lib.duration("P1DT1H"));
        doExpressionTest(entries, "", String.format("%s %s %s", daysAndTimeDuration, "/", number),
                "Multiplication(/,DateTimeLiteral(duration, \"P1DT1H\"),NumericLiteral(1))",
                "days and time duration",
                "durationDivideNumber(duration(\"P1DT1H\"), number(\"1\"))",
                this.lib.durationDivideNumber(this.lib.duration("P1DT1H"), this.lib.number("1")),
                this.lib.duration("P1DT1H"));

        // number, days and time duration
        doExpressionTest(entries, "", String.format("%s %s %s", number, "*", daysAndTimeDuration),
                "Multiplication(*,NumericLiteral(1),DateTimeLiteral(duration, \"P1DT1H\"))",
                "days and time duration",
                "durationMultiplyNumber(duration(\"P1DT1H\"), number(\"1\"))",
                this.lib.durationMultiplyNumber(this.lib.duration("P1DT1H"), this.lib.number("1")),
                this.lib.duration("P1DT1H"));

        // days and time duration, days and time duration
        doExpressionTest(entries, "", String.format("%s %s %s", daysAndTimeDuration, "/", daysAndTimeDuration),
                "Multiplication(/,DateTimeLiteral(duration, \"P1DT1H\"),DateTimeLiteral(duration, \"P1DT1H\"))",
                "number",
                "durationDivide(duration(\"P1DT1H\"), duration(\"P1DT1H\"))",
                this.lib.durationDivide(this.lib.duration("P1DT1H"), this.lib.duration("P1DT1H")),
                this.lib.number("1"));

        // complex divisions
        doExpressionTest(entries, "", "(date and time(\"2012-04-01T00:00:00Z\") - date and time(\"2012-03-01T00:00:00Z\")) / duration(\"P1D\")",
                "Multiplication(/,Addition(-,DateTimeLiteral(date and time, \"2012-04-01T00:00:00Z\"),DateTimeLiteral(date and time, \"2012-03-01T00:00:00Z\")),DateTimeLiteral(duration, \"P1D\"))",
                "number",
                "durationDivide(dateTimeSubtract(dateAndTime(\"2012-04-01T00:00:00Z\"), dateAndTime(\"2012-03-01T00:00:00Z\")), duration(\"P1D\"))",
                this.lib.durationDivide(this.lib.dateTimeSubtract(this.lib.dateAndTime("2012-04-01T00:00:00Z"), this.lib.dateAndTime("2012-03-01T00:00:00Z")), this.lib.duration("P1D")),
                this.lib.number("31"));
        doExpressionTest(entries, "", "(date(\"2012-04-01\") - date(\"2012-03-01\")) / duration(\"P1D\")",
                "Multiplication(/,Addition(-,DateTimeLiteral(date, \"2012-04-01\"),DateTimeLiteral(date, \"2012-03-01\")),DateTimeLiteral(duration, \"P1D\"))",
                "number",
                "durationDivide(dateSubtract(date(\"2012-04-01\"), date(\"2012-03-01\")), duration(\"P1D\"))",
                this.lib.durationDivide(this.lib.dateSubtract(this.lib.date("2012-04-01"), this.lib.date("2012-03-01")), this.lib.duration("P1D")),
                this.lib.number("31"));
        doExpressionTest(entries, "", "(date(\"2012-03-01\") - date(\"2012-04-01\")) / duration(\"P1D\")",
                "Multiplication(/,Addition(-,DateTimeLiteral(date, \"2012-03-01\"),DateTimeLiteral(date, \"2012-04-01\")),DateTimeLiteral(duration, \"P1D\"))",
                "number",
                "durationDivide(dateSubtract(date(\"2012-03-01\"), date(\"2012-04-01\")), duration(\"P1D\"))",
                this.lib.durationDivide(this.lib.dateSubtract(this.lib.date("2012-03-01"), this.lib.date("2012-04-01")), this.lib.duration("P1D")),
                this.lib.number("-31"));
        doExpressionTest(entries, "", "(date and time(\"2012-04-01T00:00:00Z\") - date and time(\"2012-03-01T00:00:00Z\")) / 2",
                "Multiplication(/,Addition(-,DateTimeLiteral(date and time, \"2012-04-01T00:00:00Z\"),DateTimeLiteral(date and time, \"2012-03-01T00:00:00Z\")),NumericLiteral(2))",
                "days and time duration",
                "durationDivideNumber(dateTimeSubtract(dateAndTime(\"2012-04-01T00:00:00Z\"), dateAndTime(\"2012-03-01T00:00:00Z\")), number(\"2\"))",
                this.lib.durationDivideNumber(this.lib.dateTimeSubtract(this.lib.dateAndTime("2012-04-01T00:00:00Z"), this.lib.dateAndTime("2012-03-01T00:00:00Z")), this.lib.number("2")),
                this.lib.duration("P15DT12H0M0.000S"));
        doExpressionTest(entries, "", "(date and time(\"2012-03-01T00:00:00Z\") - date and time(\"2012-04-01T00:00:00Z\")) / 2",
                "Multiplication(/,Addition(-,DateTimeLiteral(date and time, \"2012-03-01T00:00:00Z\"),DateTimeLiteral(date and time, \"2012-04-01T00:00:00Z\")),NumericLiteral(2))",
                "days and time duration",
                "durationDivideNumber(dateTimeSubtract(dateAndTime(\"2012-03-01T00:00:00Z\"), dateAndTime(\"2012-04-01T00:00:00Z\")), number(\"2\"))",
                this.lib.durationDivideNumber(this.lib.dateTimeSubtract(this.lib.dateAndTime("2012-03-01T00:00:00Z"), this.lib.dateAndTime("2012-04-01T00:00:00Z")), this.lib.number("2")),
                this.lib.duration("-P15DT12H0M0.000S"));
        doExpressionTest(entries, "", "(date(\"2012-04-01\") - date(\"2012-03-01\")) / 2",
                "Multiplication(/,Addition(-,DateTimeLiteral(date, \"2012-04-01\"),DateTimeLiteral(date, \"2012-03-01\")),NumericLiteral(2))",
                "days and time duration",
                "durationDivideNumber(dateSubtract(date(\"2012-04-01\"), date(\"2012-03-01\")), number(\"2\"))",
                this.lib.durationDivideNumber(this.lib.dateSubtract(this.lib.date("2012-04-01"), this.lib.date("2012-03-01")), this.lib.number("2")),
                this.lib.duration("P15DT12H0M0.000S"));
        doExpressionTest(entries, "", "(date(\"2012-02-01\") - date(\"2012-03-01\")) / 2",
                "Multiplication(/,Addition(-,DateTimeLiteral(date, \"2012-02-01\"),DateTimeLiteral(date, \"2012-03-01\")),NumericLiteral(2))",
                "days and time duration",
                "durationDivideNumber(dateSubtract(date(\"2012-02-01\"), date(\"2012-03-01\")), number(\"2\"))",
                this.lib.durationDivideNumber(this.lib.dateSubtract(this.lib.date("2012-02-01"), this.lib.date("2012-03-01")), this.lib.number("2")),
                this.lib.duration("-P14DT12H0M0.000S"));
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
                "(String)(elementAt(deptTable.stream().filter(item -> numericEqual(((java.math.BigDecimal)(item != null ? item.getNumber() : null)), (java.math.BigDecimal)(elementAt(employeeTable.stream().filter(item_1_ -> stringEqual(((String)(item_1_ != null ? item_1_.getName() : null)), lastName) == Boolean.TRUE).collect(Collectors.toList()).stream().map(x -> ((java.math.BigDecimal)(x != null ? x.getDeptNum() : null))).collect(Collectors.toList()), number(\"1\")))) == Boolean.TRUE).collect(Collectors.toList()).stream().map(x -> ((String)(x != null ? x.getManager() : null))).collect(Collectors.toList()), number(\"1\")))",
                null,
                null
        );
    }

    @Test
    public void testFilterExpression() {
        List<NUMBER> source = Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3"));
        ContextType employeeType = new ContextType();
        employeeType.addMember("id", Arrays.asList(), NumberType.NUMBER);
        employeeType.addMember("dept", Arrays.asList(), NumberType.NUMBER);
        employeeType.addMember("name", Arrays.asList(), StringType.STRING);

        Type employeeListType = new ListType(employeeType);
        List<Context> employeeValue = Arrays.asList(
                new Context().add("id", this.lib.number("7792")).add("dept", this.lib.number("10")).add("name", "Clark"),
                new Context().add("id", this.lib.number("7973")).add("dept", this.lib.number("20")).add("name", "Adams"),
                new Context().add("id", this.lib.number("7973")).add("dept", this.lib.number("20")).add("name", "Ford")
        );
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("source", ListType.NUMBER_LIST, source),
                new EnvironmentEntry("employee", employeeListType, employeeValue)
        );

        // boolean filter
        doExpressionTest(entries, "", "[{item: 1}, {item: 2}, {item: 3}][item >= 2]",
                "FilterExpression(ListLiteral(Context(ContextEntry(ContextEntryKey(item) = NumericLiteral(1))),Context(ContextEntry(ContextEntryKey(item) = NumericLiteral(2))),Context(ContextEntry(ContextEntryKey(item) = NumericLiteral(3)))), Relational(>=,PathExpression(Name(item), item),NumericLiteral(2)))",
                "ListType(ContextType(item = number))",
                "asList(new com.gs.dmn.runtime.Context().add(\"item\", number(\"1\")), new com.gs.dmn.runtime.Context().add(\"item\", number(\"2\")), new com.gs.dmn.runtime.Context().add(\"item\", number(\"3\"))).stream().filter(item -> numericGreaterEqualThan(((java.math.BigDecimal)((com.gs.dmn.runtime.Context)item).get(\"item\")), number(\"2\")) == Boolean.TRUE).collect(Collectors.toList())",
                this.lib.asList(new com.gs.dmn.runtime.Context().add("item", this.lib.number("1")), new com.gs.dmn.runtime.Context().add("item", this.lib.number("2")), new com.gs.dmn.runtime.Context().add("item", this.lib.number("3"))).stream().filter(item -> this.lib.numericGreaterEqualThan(((NUMBER)((com.gs.dmn.runtime.Context)item).get("item")), this.lib.number("2")) == Boolean.TRUE).collect(Collectors.toList()),
                this.lib.asList(new Context().add("item", this.lib.number("2")), new Context().add("item", this.lib.number("3"))));
        doExpressionTest(entries, "", "source[true]",
                "FilterExpression(Name(source), BooleanLiteral(true))",
                "ListType(number)",
                "source.stream().filter(item -> Boolean.TRUE == Boolean.TRUE).collect(Collectors.toList())",
                source.stream().filter(item -> Boolean.TRUE == Boolean.TRUE).collect(Collectors.toList()),
                source);
        doExpressionTest(entries, "", "[1, 2][true]",
                "FilterExpression(ListLiteral(NumericLiteral(1),NumericLiteral(2)), BooleanLiteral(true))",
                "ListType(number)",
                "asList(number(\"1\"), number(\"2\")).stream().filter(item -> Boolean.TRUE == Boolean.TRUE).collect(Collectors.toList())",
                this.lib.asList(this.lib.number("1"), this.lib.number("2")).stream().filter(item -> Boolean.TRUE == Boolean.TRUE).collect(Collectors.toList()),
                Arrays.asList(this.lib.number("1"), this.lib.number("2")));
        doExpressionTest(entries, "", "1[true]",
                "FilterExpression(NumericLiteral(1), BooleanLiteral(true))",
                "ListType(number)",
                "asList(number(\"1\")).stream().filter(item -> Boolean.TRUE == Boolean.TRUE).collect(Collectors.toList())",
                this.lib.asList(this.lib.number("1")).stream().filter(item -> Boolean.TRUE == Boolean.TRUE).collect(Collectors.toList()),
                Arrays.asList(this.lib.number("1")));
        doExpressionTest(entries, "", "[1, 2, 3, 4][item > 2]",
                "FilterExpression(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3),NumericLiteral(4)), Relational(>,Name(item),NumericLiteral(2)))",
                "ListType(number)",
                "asList(number(\"1\"), number(\"2\"), number(\"3\"), number(\"4\")).stream().filter(item -> numericGreaterThan(item, number(\"2\")) == Boolean.TRUE).collect(Collectors.toList())",
                this.lib.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3"), this.lib.number("4")).stream().filter(item -> this.lib.numericGreaterThan(item, this.lib.number("2")) == Boolean.TRUE).collect(Collectors.toList()),
                Arrays.asList(this.lib.number("3"), this.lib.number("4")));
        doExpressionTest(entries, "", "employee[item.dept = 20]",
                "FilterExpression(Name(employee), Relational(=,PathExpression(Name(item), dept),NumericLiteral(20)))",
                "ListType(ContextType(id = number, dept = number, name = string))",
                "employee.stream().filter(item -> numericEqual(((java.math.BigDecimal)((com.gs.dmn.runtime.Context)item).get(\"dept\")), number(\"20\")) == Boolean.TRUE).collect(Collectors.toList())",
                employeeValue.stream().filter(item -> this.lib.numericEqual(((NUMBER)((com.gs.dmn.runtime.Context)item).get("dept")), this.lib.number("20")) == Boolean.TRUE).collect(Collectors.toList()),
                Arrays.asList(employeeValue.get(1), employeeValue.get(2)));
        doExpressionTest(entries, "", "employee[item.dept = 20].name",
                "PathExpression(FilterExpression(Name(employee), Relational(=,PathExpression(Name(item), dept),NumericLiteral(20))), name)",
                "ListType(string)",
                "employee.stream().filter(item -> numericEqual(((java.math.BigDecimal)((com.gs.dmn.runtime.Context)item).get(\"dept\")), number(\"20\")) == Boolean.TRUE).collect(Collectors.toList()).stream().map(x -> ((String)((com.gs.dmn.runtime.Context)x).get(\"name\"))).collect(Collectors.toList())",
                employeeValue.stream().filter(item -> this.lib.numericEqual(((NUMBER)((com.gs.dmn.runtime.Context)item).get("dept")), this.lib.number("20")) == Boolean.TRUE).collect(Collectors.toList()).stream().map(x -> ((String)((com.gs.dmn.runtime.Context)x).get("name"))).collect(Collectors.toList()),
                Arrays.asList(employeeValue.get(1).get("name"), employeeValue.get(2).get("name")));
        doExpressionTest(entries, "", "employee[dept = 20].name",
                "PathExpression(FilterExpression(Name(employee), Relational(=,PathExpression(Name(item), dept),NumericLiteral(20))), name)",
                "ListType(string)",
                "employee.stream().filter(item -> numericEqual(((java.math.BigDecimal)((com.gs.dmn.runtime.Context)item).get(\"dept\")), number(\"20\")) == Boolean.TRUE).collect(Collectors.toList()).stream().map(x -> ((String)((com.gs.dmn.runtime.Context)x).get(\"name\"))).collect(Collectors.toList())",
                employeeValue.stream().filter(item -> this.lib.numericEqual((NUMBER)((Context)item).get("dept"), this.lib.number("20"))).collect(Collectors.toList()).stream().map(x -> (String) x.get("name")).collect(Collectors.toList()),
                Arrays.asList(employeeValue.get(1).get("name"), employeeValue.get(2).get("name")));

        // numeric filter
        doExpressionTest(entries, "", "[1, 2][0]",
                "FilterExpression(ListLiteral(NumericLiteral(1),NumericLiteral(2)), NumericLiteral(0))",
                "number",
                "(java.math.BigDecimal)(elementAt(asList(number(\"1\"), number(\"2\")), number(\"0\")))",
                this.lib.elementAt(this.lib.asList(this.lib.number("1"), this.lib.number("2")), this.lib.number("0")),
                null);
        doExpressionTest(entries, "", "[1, 2][-1]",
                "FilterExpression(ListLiteral(NumericLiteral(1),NumericLiteral(2)), ArithmeticNegation(NumericLiteral(1)))",
                "number",
                "(java.math.BigDecimal)(elementAt(asList(number(\"1\"), number(\"2\")), numericUnaryMinus(number(\"1\"))))",
                this.lib.elementAt(this.lib.asList(this.lib.number("1"), this.lib.number("2")), this.lib.numericUnaryMinus(this.lib.number("1"))),
                this.lib.number("2"));
        doExpressionTest(entries, "", "[1, 2][-2]",
                "FilterExpression(ListLiteral(NumericLiteral(1),NumericLiteral(2)), ArithmeticNegation(NumericLiteral(2)))",
                "number",
                "(java.math.BigDecimal)(elementAt(asList(number(\"1\"), number(\"2\")), numericUnaryMinus(number(\"2\"))))",
                this.lib.elementAt(this.lib.asList(this.lib.number("1"), this.lib.number("2")), this.lib.numericUnaryMinus(this.lib.number("2"))),
                this.lib.number("1"));
        doExpressionTest(entries, "", "1[1]",
                "FilterExpression(NumericLiteral(1), NumericLiteral(1))",
                "number",
                "(java.math.BigDecimal)(elementAt(asList(number(\"1\")), number(\"1\")))",
                this.lib.elementAt(this.lib.asList(this.lib.number("1")), this.lib.number("1")),
                this.lib.number("1"));

        // context filter
        doExpressionTest(entries, "", "[{x:1, y:2}, {x:2, y:3}] [item.x = 1]",
                "FilterExpression(ListLiteral(Context(ContextEntry(ContextEntryKey(x) = NumericLiteral(1)),ContextEntry(ContextEntryKey(y) = NumericLiteral(2))),Context(ContextEntry(ContextEntryKey(x) = NumericLiteral(2)),ContextEntry(ContextEntryKey(y) = NumericLiteral(3)))), Relational(=,PathExpression(Name(item), x),NumericLiteral(1)))",
                "ListType(ContextType(x = number, y = number))",
                "asList(new com.gs.dmn.runtime.Context().add(\"x\", number(\"1\")).add(\"y\", number(\"2\")), new com.gs.dmn.runtime.Context().add(\"x\", number(\"2\")).add(\"y\", number(\"3\"))).stream().filter(item -> numericEqual(((java.math.BigDecimal)((com.gs.dmn.runtime.Context)item).get(\"x\")), number(\"1\")) == Boolean.TRUE).collect(Collectors.toList())",
                this.lib.asList(new com.gs.dmn.runtime.Context().add("x", this.lib.number("1")).add("y", this.lib.number("2")), new com.gs.dmn.runtime.Context().add("x", this.lib.number("2")).add("y", this.lib.number("3"))).stream().filter(item -> this.lib.numericEqual(((NUMBER)((com.gs.dmn.runtime.Context)item).get("x")), this.lib.number("1")) == Boolean.TRUE).collect(Collectors.toList()),
                Arrays.asList(new com.gs.dmn.runtime.Context().add("x", this.lib.number("1")).add("y", this.lib.number("2"))));
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
                this.lib.date(null),
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
    }

    @Test
    public void testQualifiedName() {
        Type bType = new ItemDefinitionType("b").addMember("c", Arrays.asList("C"), STRING);
        Type aType = new ItemDefinitionType("a").addMember("b", Arrays.asList("B"), bType);
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("a", aType, null));

        doExpressionTest(entries, "", "a.b.c",
                "PathExpression(PathExpression(Name(a), b), c)",
                "string",
                "((String)(((type.B)(a != null ? a.getB() : null)) != null ? ((type.B)(a != null ? a.getB() : null)).getC() : null))",
                null,
                null);
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
                new EnvironmentEntry("input", NUMBER, this.lib.number("1")));

        doExpressionTest(entries, "", "null",
                "NullLiteral()",
                "Null",
                "null",
                null,
                null);
    }

    @Test
    public void testFunctionDefinition() {
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, this.lib.number("1")));

        doExpressionTest(entries, "", "function (x : feel.string, y : feel.string) x + y",
                "FunctionDefinition(FormalParameter(x, string, false, false),FormalParameter(y, string, false, false), Addition(+,Name(x),Name(y)), false)",
                "FEELFunctionType(FormalParameter(x, string, false, false), FormalParameter(y, string, false, false), string, false)",
                null,
                null,
                null);
        doExpressionTest(entries, "", "function (x , y) x + y",
                "FunctionDefinition(FormalParameter(x, null, false, false),FormalParameter(y, null, false, false), Addition(+,Name(x),Name(y)), false)",
                "FEELFunctionType(FormalParameter(x, null, false, false), FormalParameter(y, null, false, false), null, false)",
                null,
                null,
                null);

        doExpressionTest(entries, "", "function (x : feel.string, y : feel.string) external { " +
                        "java: {class : \"name\", methodSignature: \"signature\" } }",
                "FunctionDefinition(FormalParameter(x, string, false, false),FormalParameter(y, string, false, false), Context(ContextEntry(ContextEntryKey(java) = Context(ContextEntry(ContextEntryKey(class) = StringLiteral(\"name\")),ContextEntry(ContextEntryKey(methodSignature) = StringLiteral(\"signature\"))))), true)",
                "FEELFunctionType(FormalParameter(x, string, false, false), FormalParameter(y, string, false, false), Any, true)",
                null,
                null,
                null);
        doExpressionTest(entries, "", "function (x , y) external { " +
                        "java: {class : \"name\", methodSignature: \"signature\" } }",
                "FunctionDefinition(FormalParameter(x, null, false, false),FormalParameter(y, null, false, false), Context(ContextEntry(ContextEntryKey(java) = Context(ContextEntry(ContextEntryKey(class) = StringLiteral(\"name\")),ContextEntry(ContextEntryKey(methodSignature) = StringLiteral(\"signature\"))))), true)",
                "FEELFunctionType(FormalParameter(x, null, false, false), FormalParameter(y, null, false, false), Any, true)",
                null,
                null,
                null);
        doExpressionTest(entries, "", "function(a: feel.number, b: feel.number) external {" +
                        "java: {class: \"com.gs.dmn.simple_decision_with_user_function.Sum\", methodSignature: \"add(a, b)\", returnType : \"number\"}" +
                        "}",
                "FunctionDefinition(FormalParameter(a, number, false, false),FormalParameter(b, number, false, false), Context(ContextEntry(ContextEntryKey(java) = Context(ContextEntry(ContextEntryKey(class) = StringLiteral(\"com.gs.dmn.simple_decision_with_user_function.Sum\")),ContextEntry(ContextEntryKey(methodSignature) = StringLiteral(\"add(a, b)\")),ContextEntry(ContextEntryKey(returnType) = StringLiteral(\"number\"))))), true)",
                "FEELFunctionType(FormalParameter(a, number, false, false), FormalParameter(b, number, false, false), Any, true)",
                null,
                null,
                null
        );
    }

    @Test
    public void testList() {
        NUMBER number = this.lib.number("1");
        List<NUMBER> list = Arrays.asList(this.lib.number("1"));

        List<EnvironmentEntry> expressionPairs = Arrays.asList(
                new EnvironmentEntry("number", NUMBER, number),
                new EnvironmentEntry("list", ListType.NUMBER_LIST, list));
        List<EnvironmentEntry> testPairs = Arrays.asList(
                new EnvironmentEntry("number", NUMBER, number),
                new EnvironmentEntry("list", ListType.NUMBER_LIST, list));

        // simple lists
        doExpressionTest(expressionPairs, "", "[]",
                "ListLiteral()",
                "ListType(Null)",
                "asList()",
                Arrays.asList(),
                Arrays.asList());
        doExpressionTest(expressionPairs, "", "[1]",
                "ListLiteral(NumericLiteral(1))",
                "ListType(number)",
                "asList(number(\"1\"))",
                Arrays.asList(this.lib.number("1")),
                Arrays.asList(this.lib.number("1")));
        doExpressionTest(expressionPairs, "", "[1, 2, 3]",
                "ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3))",
                "ListType(number)",
                "asList(number(\"1\"), number(\"2\"), number(\"3\"))",
                Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3")),
                Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3")));

        // list of expressions
        doExpressionTest(expressionPairs, "", "[number >= 1, number > 2, number > 3]",
                "ListLiteral(Relational(>=,Name(number),NumericLiteral(1)),Relational(>,Name(number),NumericLiteral(2)),Relational(>,Name(number),NumericLiteral(3)))",
                "ListType(boolean)",
                "asList(numericGreaterEqualThan(number, number(\"1\")), numericGreaterThan(number, number(\"2\")), numericGreaterThan(number, number(\"3\")))",
                this.lib.asList(this.lib.numericGreaterEqualThan(number, this.lib.number("1")), this.lib.numericGreaterThan(number, this.lib.number("2")), this.lib.numericGreaterThan(number, this.lib.number("3"))),
                Arrays.asList(true, false, false));
        doExpressionTest(expressionPairs, "", "[1, <2, [3..4]]",
                "ListLiteral(NumericLiteral(1),OperatorRange(<,NumericLiteral(2)),EndpointsRange(false,NumericLiteral(3),false,NumericLiteral(4)))",
                "ListType(Any)",
                "asList(number(\"1\"), new com.gs.dmn.runtime.Range(false, null, false, number(\"2\")), new com.gs.dmn.runtime.Range(true, number(\"3\"), true, number(\"4\")))",
                null,
                null);

        // list filters
        doExpressionTest(expressionPairs, "", "true[0]",
                "FilterExpression(BooleanLiteral(true), NumericLiteral(0))",
                "boolean",
                "(Boolean)(elementAt(asList(Boolean.TRUE), number(\"0\")))",
                this.lib.elementAt(this.lib.asList(Boolean.TRUE), this.lib.number("0")),
                null);
        doExpressionTest(expressionPairs, "", "100[0]",
                "FilterExpression(NumericLiteral(100), NumericLiteral(0))",
                "number",
                "(java.math.BigDecimal)(elementAt(asList(number(\"100\")), number(\"0\")))",
                this.lib.elementAt(this.lib.asList(this.lib.number("100")), this.lib.number("0")),
                null);
        doExpressionTest(expressionPairs, "", "\"foo\"[0]",
                "FilterExpression(StringLiteral(\"foo\"), NumericLiteral(0))",
                "string",
                "(String)(elementAt(asList(\"foo\"), number(\"0\")))",
                this.lib.elementAt(this.lib.asList("foo"), this.lib.number("0")),
                null);

        // unary tests with lists
        doUnaryTestsTest(testPairs, "number", "[]",
                "PositiveUnaryTests(ListTest(ListLiteral()))",
                "TupleType(boolean)",
                "listContains(asList(), number)",
                this.lib.listContains(Arrays.asList(), number),
                false);
        doUnaryTestsTest(testPairs, "list", "[]",
                "PositiveUnaryTests(ListTest(ListLiteral()))",
                "TupleType(boolean)",
                "listEqual(list, asList())",
                this.lib.listEqual(list, Arrays.asList()),
                false);
        doUnaryTestsTest(testPairs, "list", "[1]",
                "PositiveUnaryTests(ListTest(ListLiteral(OperatorRange(null,NumericLiteral(1)))))",
                "TupleType(boolean)",
                "listEqual(list, asList(number(\"1\")))",
                this.lib.listEqual(list, Arrays.asList(this.lib.number("1"))),
                true);
        doUnaryTestsTest(testPairs, "list", "[1, 2, 3]",
                "PositiveUnaryTests(ListTest(ListLiteral(OperatorRange(null,NumericLiteral(1)),OperatorRange(null,NumericLiteral(2)),OperatorRange(null,NumericLiteral(3)))))",
                "TupleType(boolean)",
                "listEqual(list, asList(number(\"1\"), number(\"2\"), number(\"3\")))",
                this.lib.listEqual(list, Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3"))),
                false);
        doUnaryTestsTest(testPairs, "number", "[1, <2, [3..4]]",
                "PositiveUnaryTests(ListTest(ListLiteral(OperatorRange(null,NumericLiteral(1)),OperatorRange(<,NumericLiteral(2)),EndpointsRange(false,NumericLiteral(3),false,NumericLiteral(4)))))",
                "TupleType(boolean)",
                "listContains(asList(numericEqual(number, number(\"1\")), numericLessThan(number, number(\"2\")), booleanAnd(numericGreaterEqualThan(number, number(\"3\")), numericLessEqualThan(number, number(\"4\")))), true)",
                this.lib.listContains(this.lib.asList(this.lib.numericEqual(number, this.lib.number("1")), this.lib.numericLessThan(number, this.lib.number("2")), this.lib.booleanAnd(this.lib.numericGreaterEqualThan(number, this.lib.number("3")), this.lib.numericLessEqualThan(number, this.lib.number("4")))), true),
                true);
        doUnaryTestsTest(testPairs, "number", "[<1, <2]",
                "PositiveUnaryTests(ListTest(ListLiteral(OperatorRange(<,NumericLiteral(1)),OperatorRange(<,NumericLiteral(2)))))",
                "TupleType(boolean)",
                "listContains(asList(numericLessThan(number, number(\"1\")), numericLessThan(number, number(\"2\"))), true)",
                this.lib.listContains(this.lib.asList(this.lib.numericLessThan(number, this.lib.number("1")), this.lib.numericLessThan(number, this.lib.number("2"))), true),
                true);
    }

    @Test
    public void testContext() {
        DATE dateInput = this.lib.date("2017-01-03");
        String enumerationInput = "e1";
        NUMBER number = this.lib.number("1");
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
                new com.gs.dmn.runtime.Context().add("k1", this.lib.number("1")).add("k2", this.lib.number("2")),
                new Context().add("k1", this.lib.number("1")).add("k2", this.lib.number("2")));
        doExpressionTest(entries, "", "{ isPositive : function(x : feel.number) x > 1 }",
                "Context(ContextEntry(ContextEntryKey(isPositive) = FunctionDefinition(FormalParameter(x, number, false, false), Relational(>,Name(x),NumericLiteral(1)), false)))",
                "ContextType(isPositive = FEELFunctionType(FormalParameter(x, number, false, false), boolean, false))",
                null,
                null,
                null);
        doExpressionTest(entries, "", "{type : \"string\", value : string(dateInput)}",
                "Context(ContextEntry(ContextEntryKey(type) = StringLiteral(\"string\")),ContextEntry(ContextEntryKey(value) = FunctionInvocation(Name(string) -> PositionalParameters(Name(dateInput)))))",
                "ContextType(type = string, value = string)",
                "new com.gs.dmn.runtime.Context().add(\"type\", \"string\").add(\"value\", string(dateInput))",
                new com.gs.dmn.runtime.Context().add("type", "string").add("value", this.lib.string(dateInput)),
                new com.gs.dmn.runtime.Context().add("type", "string").add("value", this.lib.string(this.lib.date("2017-01-03"))));
        doExpressionTest(entries, "", "{type : \"string\", value : enumerationInput = \"E1\"}",
                "Context(ContextEntry(ContextEntryKey(type) = StringLiteral(\"string\")),ContextEntry(ContextEntryKey(value) = Relational(=,Name(enumerationInput),StringLiteral(\"E1\"))))",
                "ContextType(type = string, value = boolean)",
                "new com.gs.dmn.runtime.Context().add(\"type\", \"string\").add(\"value\", stringEqual(enumerationInput, \"E1\"))",
                new com.gs.dmn.runtime.Context().add("type", "string").add("value", this.lib.stringEqual(enumerationInput, "E1")),
                new com.gs.dmn.runtime.Context().add("type", "string").add("value", this.lib.stringEqual("e1", "E1")));
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

    @Test
    public void testTypeIsMissing() {
        Object null_input = null;
        Object number = lib.number("1");
        Object string = "a";
        Object date = lib.date("1970-01-01");
        Object dateTime = lib.dateAndTime("1970-01-01T10:10:10");
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("null_input", null, null_input),
                new EnvironmentEntry("number", null, number),
                new EnvironmentEntry("string", null, string),
                new EnvironmentEntry("date", null, date),
                new EnvironmentEntry("dateTime", null, dateTime)
        );

        // 0068-feel-equality
        doExpressionTest(entries, "", "(< 10) = (null_input..10)",
                "Relational(=,OperatorRange(<,NumericLiteral(10)),EndpointsRange(true,Name(null_input),true,NumericLiteral(10)))",
                "boolean",
                "rangeEqual(new com.gs.dmn.runtime.Range(false, null, false, number(\"10\")), new com.gs.dmn.runtime.Range(false, null_input, false, number(\"10\")))",
                this.lib.rangeEqual(new com.gs.dmn.runtime.Range(false, null, false, this.lib.number("10")), new com.gs.dmn.runtime.Range(false, null_input, false, this.lib.number("10"))),
                true);
        doExpressionTest(entries, "", "(>=; 10) = [10..null_input)",
                "Relational(=,OperatorRange(>=,NumericLiteral(10)),EndpointsRange(false,NumericLiteral(10),true,Name(null_input)))",
                "boolean",
                "rangeEqual(new com.gs.dmn.runtime.Range(true, number(\"10\"), false, null), new com.gs.dmn.runtime.Range(true, number(\"10\"), false, null_input))",
                this.lib.rangeEqual(new com.gs.dmn.runtime.Range(true, this.lib.number("10"), false, null), new com.gs.dmn.runtime.Range(true, this.lib.number("10"), false, null_input)),
                true);
    }

    @Test
    public void testTypeIsAny() {
        Object null_input = null;
        Object number = lib.number("1");
        Object string = "a";
        Object date = lib.date("1970-01-01");
        Object dateTime = lib.dateAndTime("1970-01-01T10:10:10");
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("null_input", ANY, null_input),
                new EnvironmentEntry("number", ANY, number),
                new EnvironmentEntry("string", ANY, string),
                new EnvironmentEntry("date", ANY, date),
                new EnvironmentEntry("dateTime", ANY, dateTime)
        );

        // 0068-feel-equality
        doExpressionTest(entries, "", "(< 10) = (null_input..10)",
                "Relational(=,OperatorRange(<,NumericLiteral(10)),EndpointsRange(true,Name(null_input),true,NumericLiteral(10)))",
                "boolean",
                "rangeEqual(new com.gs.dmn.runtime.Range(false, null, false, number(\"10\")), new com.gs.dmn.runtime.Range(false, null_input, false, number(\"10\")))",
                this.lib.rangeEqual(new com.gs.dmn.runtime.Range(false, null, false, this.lib.number("10")), new com.gs.dmn.runtime.Range(false, null_input, false, this.lib.number("10"))),
                true);
        doExpressionTest(entries, "", "(>=; 10) = [10..null_input)",
                "Relational(=,OperatorRange(>=,NumericLiteral(10)),EndpointsRange(false,NumericLiteral(10),true,Name(null_input)))",
                "boolean",
                "rangeEqual(new com.gs.dmn.runtime.Range(true, number(\"10\"), false, null), new com.gs.dmn.runtime.Range(true, number(\"10\"), false, null_input))",
                this.lib.rangeEqual(new com.gs.dmn.runtime.Range(true, this.lib.number("10"), false, null), new com.gs.dmn.runtime.Range(true, this.lib.number("10"), false, null_input)),
                true);
    }

    protected void doUnaryTestsTest(List<EnvironmentEntry> entries, String inputExpressionText, String inputEntryText,
                                    String expectedAST, String expectedType, String expectedJavaCode, Object expectedGeneratedValue, Object expectedEvaluatedValue) {
        // Analyze input expression
        DMNContext globalContext = makeContext(entries);
        Expression<Type, DMNContext> inputExpression = (Expression<Type, DMNContext>) this.feelTranslator.analyzeExpression(inputExpressionText, globalContext);

        // Analyze input entry
        DMNContext inputEntryContext = this.dmnTransformer.makeUnaryTestContext(inputExpression, globalContext);
        UnaryTests<Type, DMNContext> inputEntryTest = (UnaryTests<Type, DMNContext>) this.feelTranslator.analyzeUnaryTests(inputEntryText, inputEntryContext);

        // Check input entry
        assertEquals(expectedAST, inputEntryTest.toString());
        assertEquals(expectedType, inputEntryTest.getType().toString());

        // Generate code and check
        doCodeGenerationTest(inputEntryTest, inputEntryContext, expectedJavaCode);

        // Evaluate expression and check
        doEvaluationTest(inputExpression, globalContext, inputEntryTest, inputEntryContext, expectedEvaluatedValue);

        // Check generated and evaluated value
        checkGeneratedAndEvaluatedValue(expectedGeneratedValue, expectedEvaluatedValue);
    }

    protected void doExpressionTest(List<EnvironmentEntry> entries, String inputExpressionText, String expressionText,
                                    String expectedAST, String expectedType, String expectedJavaCode, Object expectedGeneratedValue, Object expectedEvaluatedValue) {
        Expression<Type, DMNContext> inputExpression = null;
        DMNContext globalContext = makeContext(entries);
        if (!StringUtils.isEmpty(inputExpressionText)) {
            // Analyze input expression
            inputExpression = (Expression<Type, DMNContext>) this.feelTranslator.analyzeExpression(inputExpressionText, globalContext);
        }

        // Analyse expression
        DMNContext expressionContext = this.dmnTransformer.makeUnaryTestContext(inputExpression, globalContext);
        Expression<Type, DMNContext> actual = (Expression<Type, DMNContext>) this.feelTranslator.analyzeExpression(expressionText, expressionContext);

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

    protected void doTextualExpressionsTest(List<EnvironmentEntry> entries, String expressionText,
                                            String expectedAST, String expectedType, String expectedJavaCode, Object expectedGeneratedValue, Object expectedEvaluatedValue) {
        // Analyze expression
        DMNContext context = makeContext(entries);
        Expression<Type, DMNContext> expression = (Expression<Type, DMNContext>) this.feelTranslator.analyzeTextualExpressions(expressionText, context);

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
        DMNContext context = makeContext(entries);
        Expression<Type, DMNContext> actual = (Expression<Type, DMNContext>) this.feelTranslator.analyzeBoxedExpression(expressionText, context);

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

    private Result evaluateInputEntry(Expression<Type, DMNContext> inputExpression, DMNContext inputExpressionContext, UnaryTests<Type, DMNContext> inputEntryTest, DMNContext inputEntryContext) {
        // Evaluate input expression
        Result inputExpressionResult = this.feelInterpreter.evaluateExpression(inputExpression, inputExpressionContext);
        Object inputExpressionValue = Result.value(inputExpressionResult);
        // Evaluate input entry
        inputEntryContext.bind(INPUT_ENTRY_PLACE_HOLDER, inputExpressionValue);
        return this.feelInterpreter.evaluateUnaryTests(inputEntryTest, inputEntryContext);
    }

    private void doCodeGenerationTest(UnaryTests<Type, DMNContext> inputEntry, DMNContext inputEntryContext, String expectedJavaCode) {
        if (expectedJavaCode != null) {
            String javaCode = this.feelTranslator.expressionToNative(inputEntry, inputEntryContext);
            assertEquals("Generated code mismatch", expectedJavaCode, javaCode);
        }
    }

    private void doCodeGenerationTest(Expression<Type, DMNContext> expression, DMNContext expressionContext, String expectedJavaCode) {
        if (expectedJavaCode != null) {
            String javaCode = this.feelTranslator.expressionToNative(expression, expressionContext);
            assertEquals("Generated code mismatch", expectedJavaCode, javaCode);
        }
    }

    private void doEvaluationTest(Expression<Type, DMNContext> inputExpression, DMNContext inputExpressionContext, UnaryTests<Type, DMNContext> inputEntry, DMNContext inputEntryContext, Object expectedEvaluatedValue) {
        if (expectedEvaluatedValue != null) {
            Result actualResult = evaluateInputEntry(inputExpression, inputExpressionContext, inputEntry, inputEntryContext);
            Object actualValue = Result.value(actualResult);
            assertEquals("Evaluated value mismatch", expectedEvaluatedValue, actualValue);
        }
    }

    private void doEvaluationTest(Expression<Type, DMNContext> expression, DMNContext context, Object expectedEvaluatedValue) {
        if (expectedEvaluatedValue != null) {
            Result actualResult = this.feelInterpreter.evaluateExpression(expression, context);
            Object actualValue = Result.value(actualResult);
            assertEquals(expectedEvaluatedValue, actualValue);
        }
    }

    private DMNContext makeContext(List<EnvironmentEntry> entries) {
        DMNContext context = DMNContext.of(
                this.dmnTransformer.makeBuiltInContext(),
                DMNContextKind.LOCAL,
                getElement(),
                this.environmentFactory.emptyEnvironment(),
                RuntimeEnvironment.of());
        for (EnvironmentEntry entry : entries) {
            context.addDeclaration(this.environmentFactory.makeVariableDeclaration(entry.getName(), entry.getType()));
            context.bind(entry.getName(), entry.getValue());
        }
        return context;
    }

    protected TNamedElement getElement() {
        return null;
    }

    protected abstract DMNModelRepository makeRepository();

    protected abstract DMNDialectDefinition<NUMBER, DATE, TIME, DATE_TIME, DURATION, TEST> makeDialect();
}
