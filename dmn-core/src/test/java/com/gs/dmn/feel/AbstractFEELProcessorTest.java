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

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.dialect.StandardDMNDialectDefinition;
import com.gs.dmn.feel.analysis.semantics.SemanticError;
import com.gs.dmn.feel.analysis.semantics.environment.Environment;
import com.gs.dmn.feel.analysis.semantics.environment.EnvironmentFactory;
import com.gs.dmn.feel.analysis.semantics.type.DateTimeType;
import com.gs.dmn.feel.analysis.semantics.type.DateType;
import com.gs.dmn.feel.analysis.semantics.type.TimeType;
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.test.UnaryTests;
import com.gs.dmn.feel.interpreter.FEELInterpreter;
import com.gs.dmn.feel.lib.StandardFEELLib;
import com.gs.dmn.feel.synthesis.FEELTranslator;
import com.gs.dmn.runtime.interpreter.DMNInterpreter;
import com.gs.dmn.runtime.interpreter.Result;
import com.gs.dmn.runtime.interpreter.environment.RuntimeEnvironment;
import com.gs.dmn.runtime.interpreter.environment.RuntimeEnvironmentFactory;
import com.gs.dmn.transformation.DMNToJavaTransformer;
import com.gs.dmn.transformation.basic.BasicDMN2JavaTransformer;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.omg.spec.dmn._20180521.model.TNamedElement;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import static com.gs.dmn.feel.analysis.semantics.type.BooleanType.BOOLEAN;
import static com.gs.dmn.feel.analysis.semantics.type.NumberType.NUMBER;
import static com.gs.dmn.feel.analysis.semantics.type.StringType.STRING;
import static org.junit.Assert.assertEquals;

public abstract class AbstractFEELProcessorTest {
    private final DMNDialectDefinition dialectDefinition = new StandardDMNDialectDefinition();

    private final RuntimeEnvironmentFactory runtimeEnvironmentFactory = RuntimeEnvironmentFactory.instance();

    protected final DMNInterpreter dmnInterpreter = this.dialectDefinition.createDMNInterpreter(new DMNModelRepository(), new LinkedHashMap<>());
    protected final BasicDMN2JavaTransformer dmnTransformer = this.dmnInterpreter.getBasicDMNTransformer();
    protected final EnvironmentFactory environmentFactory = this.dmnTransformer.getEnvironmentFactory();
    protected final StandardFEELLib lib = (StandardFEELLib) this.dmnInterpreter.getFeelLib();

    protected FEELTranslator feelTranslator;
    protected FEELInterpreter feelInterpreter;

    @Test
    public void testSimpleUnaryTests() {
        Object input = this.lib.number("1");
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
                "true",
                true,
                true);
    }

    @Test
    public void testSimplePositiveUnaryTests() {
        Object number = this.lib.number("1");
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
        Object number = this.lib.number("1");
        String string = "1";
        boolean boolean_ = true;
        Object date = this.lib.date("2017-01-03");
        Object time = this.lib.time("12:00:00Z");
        Object dateTime = this.lib.dateAndTime("2017-01-03T12:00:00Z");
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
        Object input = this.lib.number("1");
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
        boolean input = true;
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
    public void testSimpleExpressions() {
        List entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, this.lib.number("1")));

        doSimpleExpressionsTest(entries, "1, 2",
                "ExpressionList(NumericLiteral(1),NumericLiteral(2))",
                "TupleType(number, number)",
                null,
                null,
                null);
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
        doExpressionTest(entries, "", "true",
                "BooleanLiteral(true)",
                "boolean",
                "Boolean.TRUE",
                Boolean.TRUE,
                true);
        doExpressionTest(entries, "", "\"abc\"",
                "StringLiteral(\"abc\")",
                "string",
                "\"abc\"",
                "abc",
                "abc");
        doExpressionTest(entries, "", "date(\"2016-08-01\")",
                "DateTimeLiteral(date, \"2016-08-01\")",
                "date",
                "date(\"2016-08-01\")",
                this.lib.date("2016-08-01"),
                this.lib.date("2016-08-01"));
        doExpressionTest(entries, "", "time(\"12:00:00Z\")",
                "DateTimeLiteral(time, \"12:00:00Z\")",
                "time",
                "time(\"12:00:00Z\")",
                this.lib.time("12:00:00Z"),
                this.lib.time("12:00:00Z"));
        doExpressionTest(entries, "", "date and time(\"2016-08-01T11:00:00Z\")",
                "DateTimeLiteral(date and time, \"2016-08-01T11:00:00Z\")",
                "date and time",
                "dateAndTime(\"2016-08-01T11:00:00Z\")",
                this.lib.dateAndTime("2016-08-01T11:00:00Z"),
                this.lib.dateAndTime("2016-08-01T11:00:00Z"));
    }

    @Test
    public void testComparisonExpression() {
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
    public void testSimpleLiteral() {
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
        inputEntryContext.runtimeBind(DMNToJavaTransformer.INPUT_ENTRY_PLACE_HOLDER, inputExpressionValue);
        return this.feelInterpreter.evaluateUnaryTests(inputEntryTest, inputEntryContext);
    }

    private void doCodeGenerationTest(UnaryTests inputEntry, FEELContext inputEntryContext, String expectedJavaCode) {
        if (expectedJavaCode != null) {
            String javaCode = this.feelTranslator.expressionToJava(inputEntry, inputEntryContext);
            assertEquals("Generated code mismatch", expectedJavaCode, javaCode);
        }
    }

    private void doCodeGenerationTest(Expression expression, FEELContext expressionContext, String expectedJavaCode) {
        if (expectedJavaCode != null) {
            String javaCode = this.feelTranslator.expressionToJava(expression, expressionContext);
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
            environment.addDeclaration(DMNToJavaTransformer.INPUT_ENTRY_PLACE_HOLDER, this.environmentFactory.makeVariableDeclaration(DMNToJavaTransformer.INPUT_ENTRY_PLACE_HOLDER, inputExpression.getType()));
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
}
