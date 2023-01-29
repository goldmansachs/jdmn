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
import com.gs.dmn.feel.analysis.semantics.type.*;
import com.gs.dmn.feel.lib.StandardFEELLib;
import com.gs.dmn.tck.ast.TestCases;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.gs.dmn.feel.analysis.semantics.type.BooleanType.BOOLEAN;
import static com.gs.dmn.feel.analysis.semantics.type.DateType.DATE;
import static com.gs.dmn.feel.analysis.semantics.type.NumberType.NUMBER;
import static com.gs.dmn.feel.analysis.semantics.type.StringType.STRING;

public abstract class AbstractStandardFEELProcessorTest<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends AbstractFEELProcessorTest<NUMBER, DATE, TIME, DATE_TIME, DURATION, TestCases> {
    protected final StandardFEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> lib = (StandardFEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION>) this.dmnInterpreter.getFeelLib();

    @Override
    protected DMNModelRepository makeRepository() {
        return new DMNModelRepository();
    }

    @Override
    @Test
    public void testUnaryTests() {
        super.testUnaryTests();

        NUMBER number = this.lib.number("15");
        DATE date = this.lib.date("2015-01-01");
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("number", NUMBER, number),
                new EnvironmentEntry("date", DATE, date)
        );

        doUnaryTestsTest(entries, "number", "and(> 10, < 20)",
                "PositiveUnaryTests(ExpressionTest(FunctionInvocation(Name(and) -> PositionalParameters(OperatorRange(>,NumericLiteral(10)), OperatorRange(<,NumericLiteral(20))))))",
                "TupleType(boolean)",
                "(and(numericGreaterThan(number, number(\"10\")), numericLessThan(number, number(\"20\"))))",
                (this.lib.and(this.lib.numericGreaterThan(number, this.lib.number("10")), this.lib.numericLessThan(number, this.lib.number("20")))),
                true);
        doUnaryTestsTest(entries, "number", "or((1..2), [3..4])",
                "PositiveUnaryTests(ExpressionTest(FunctionInvocation(Name(or) -> PositionalParameters(EndpointsRange(true,NumericLiteral(1),true,NumericLiteral(2)), EndpointsRange(false,NumericLiteral(3),false,NumericLiteral(4))))))",
                "TupleType(boolean)",
                "(or(booleanAnd(numericGreaterThan(number, number(\"1\")), numericLessThan(number, number(\"2\"))), booleanAnd(numericGreaterEqualThan(number, number(\"3\")), numericLessEqualThan(number, number(\"4\")))))",
                (this.lib.or(this.lib.booleanAnd(this.lib.numericGreaterThan(number, this.lib.number("1")), this.lib.numericLessThan(number, this.lib.number("2"))), this.lib.booleanAnd(this.lib.numericGreaterEqualThan(number, this.lib.number("3")), this.lib.numericLessEqualThan(number, this.lib.number("4"))))),
                false);
        doUnaryTestsTest(entries, "date", "<= date(\"2020-01-01\")",
                "PositiveUnaryTests(OperatorRange(<=,DateTimeLiteral(date, \"2020-01-01\")))",
                "TupleType(boolean)",
                "(dateLessEqualThan(date, date(\"2020-01-01\")))",
                (this.lib.dateLessEqualThan(date, this.lib.date("2020-01-01"))),
                true);
        // Extension for simple positive unary test / endpoint / simple value
        doUnaryTestsTest(entries, "date", "<= date(date(\"2020-01-01\") - duration(\"P5Y\"))",
                "PositiveUnaryTests(OperatorRange(<=,FunctionInvocation(Name(date) -> PositionalParameters(Addition(-,DateTimeLiteral(date, \"2020-01-01\"),DateTimeLiteral(duration, \"P5Y\"))))))",
                "TupleType(boolean)",
                "(dateLessEqualThan(date, date(dateSubtractDuration(date(\"2020-01-01\"), duration(\"P5Y\")))))",
                (lib.dateLessEqualThan(date, lib.date(lib.dateSubtractDuration(lib.date("2020-01-01"), lib.duration("P5Y"))))),
                true);
        doUnaryTestsTest(entries, "date", "date(\"2020-01-01\") - duration(\"P5Y\")",
                "PositiveUnaryTests(OperatorRange(null,Addition(-,DateTimeLiteral(date, \"2020-01-01\"),DateTimeLiteral(duration, \"P5Y\"))))",
                "TupleType(boolean)",
                "(dateEqual(date, dateSubtractDuration(date(\"2020-01-01\"), duration(\"P5Y\"))))",
                (lib.dateEqual(date, lib.dateSubtractDuration(lib.date("2020-01-01"), lib.duration("P5Y")))),
                true);
        doUnaryTestsTest(entries, "date", "? <= date(date(\"2020-01-01\") - duration(\"P5Y\"))",
                "PositiveUnaryTests(ExpressionTest(Relational(<=,Name(?),FunctionInvocation(Name(date) -> PositionalParameters(Addition(-,DateTimeLiteral(date, \"2020-01-01\"),DateTimeLiteral(duration, \"P5Y\")))))))",
                "TupleType(boolean)",
                "(dateLessEqualThan(date, date(dateSubtractDuration(date(\"2020-01-01\"), duration(\"P5Y\")))))",
                (this.lib.dateLessEqualThan(date, this.lib.date(this.lib.dateSubtractDuration(this.lib.date("2020-01-01"), this.lib.duration("P5Y"))))),
                true);
        doUnaryTestsTest(entries, "date", "? <= date(\"2020-01-01\") - duration(\"P5Y\")",
                "PositiveUnaryTests(ExpressionTest(Relational(<=,Name(?),Addition(-,DateTimeLiteral(date, \"2020-01-01\"),DateTimeLiteral(duration, \"P5Y\")))))",
                "TupleType(boolean)",
                "(dateLessEqualThan(date, dateSubtractDuration(date(\"2020-01-01\"), duration(\"P5Y\"))))",
                (this.lib.dateLessEqualThan(date, this.lib.dateSubtractDuration(this.lib.date("2020-01-01"), this.lib.duration("P5Y")))),
                true);
    }

    @Override
    @Test
    public void testPositiveUnaryTest() {
        super.testPositiveUnaryTest();

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
        // ExpressionTest
        //
        doUnaryTestsTest(entries, "list", "count(?) > 2",
                "PositiveUnaryTests(ExpressionTest(Relational(>,FunctionInvocation(Name(count) -> PositionalParameters(Name(?))),NumericLiteral(2))))",
                "TupleType(boolean)",
                "(numericGreaterThan(count(list), number(\"2\")))",
                (this.lib.numericGreaterThan(this.lib.count(list), this.lib.number("2"))),
                true);
    }

    @Override
    @Test
    public void testBetweenExpression() {
        super.testBetweenExpression();

        List<EnvironmentEntry> entries = Arrays.asList();

        doExpressionTest(entries, "", "\"a\" between \"b\" and \"d\"",
                "BetweenExpression(StringLiteral(\"a\"), StringLiteral(\"b\"), StringLiteral(\"d\"))",
                "boolean",
                "booleanAnd(stringLessEqualThan(\"b\", \"a\"), stringLessEqualThan(\"a\", \"d\"))",
                this.lib.booleanAnd(this.lib.stringLessEqualThan("b", "a"), this.lib.stringLessEqualThan("a", "d")),
                false);
    }

    @Override
    @Test
    public void testInExpression() {
        super.testInExpression();

        // operator test
        List<EnvironmentEntry> entries = Arrays.asList();

        doExpressionTest(entries, "", "\"b\" in [[\"f\"..\"h\"], [\"a\"..\"c\"]]",
                "InExpression(StringLiteral(\"b\"), ListTest(ListLiteral(EndpointsRange(false,StringLiteral(\"f\"),false,StringLiteral(\"h\")),EndpointsRange(false,StringLiteral(\"a\"),false,StringLiteral(\"c\")))))",
                "boolean",
                "(listContains(asList(booleanAnd(stringGreaterEqualThan(\"b\", \"f\"), stringLessEqualThan(\"b\", \"h\")), booleanAnd(stringGreaterEqualThan(\"b\", \"a\"), stringLessEqualThan(\"b\", \"c\"))), true))",
                (this.lib.listContains(this.lib.asList(this.lib.booleanAnd(this.lib.stringGreaterEqualThan("b", "f"), this.lib.stringLessEqualThan("b", "h")), this.lib.booleanAnd(this.lib.stringGreaterEqualThan("b", "a"), this.lib.stringLessEqualThan("b", "c"))), true)),
                true);
    }

    @Test
    public void testFunctionInvocation() {
        String input = "abc";
        NUMBER score1 = this.lib.number("1");
        NUMBER score2 = this.lib.number("2");
        NUMBER score3 = this.lib.number("3");
        NUMBER score4 = this.lib.number("4");
        NUMBER score5 = this.lib.number("5");
        NUMBER score6 = this.lib.number("6");
        NUMBER score7 = this.lib.number("7");
        NUMBER score8 = this.lib.number("8");
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", STRING, input),
                new EnvironmentEntry("score1", NUMBER, score1),
                new EnvironmentEntry("score2", NUMBER, score2),
                new EnvironmentEntry("score3", NUMBER, score3),
                new EnvironmentEntry("score4", NUMBER, score4),
                new EnvironmentEntry("score5", NUMBER, score5),
                new EnvironmentEntry("score6", NUMBER, score6),
                new EnvironmentEntry("score7", NUMBER, score7),
                new EnvironmentEntry("score8", NUMBER, score8)
        );

        // nested functions invocation
        String list1 = "[score1, score2, score3, score4]";
        String list2 = "[score5, score6, score7, score8]";
        String sort2 = "sort(" + list2 + ", function(x: number, y: number) y < x)";
        String sublist2 = "sublist(" + sort2 + ", 1, 2)";
        String concatenate = "concatenate(" + list1 + ", " + sublist2 + ")";
        String sort1 = "sort(" + concatenate + ", function(x: number, y:number) y < x)";
        String sublist1 = "sublist(" + sort1 + ", 1, 3)";
        String forExp = "for i in " + sublist1 + "return i*i";
        String sum = "sum(" + forExp + ")";

        doExpressionTest(entries, "", sort2,
                "FunctionInvocation(Name(sort) -> PositionalParameters(ListLiteral(Name(score5),Name(score6),Name(score7),Name(score8)), FunctionDefinition(FormalParameter(x, number, false, false),FormalParameter(y, number, false, false), Relational(<,Name(y),Name(x)), false)))",
                "ListType(number)",
                "sort(asList(score5, score6, score7, score8), new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args_) {java.math.BigDecimal x = (java.math.BigDecimal)args_[0]; java.math.BigDecimal y = (java.math.BigDecimal)args_[1];return numericLessThan(y, x);}})",
                this.lib.sort(this.lib.asList(score5, score6, score7, score8), new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args_) {NUMBER x = (NUMBER)args_[0]; NUMBER y = (NUMBER)args_[1];return lib.numericLessThan(y, x);}}),
                this.lib.asList(this.lib.number("8"), this.lib.number("7"), this.lib.number("6"), this.lib.number("5")));
        doExpressionTest(entries, "", sublist2,
                "FunctionInvocation(Name(sublist) -> PositionalParameters(FunctionInvocation(Name(sort) -> PositionalParameters(ListLiteral(Name(score5),Name(score6),Name(score7),Name(score8)), FunctionDefinition(FormalParameter(x, number, false, false),FormalParameter(y, number, false, false), Relational(<,Name(y),Name(x)), false))), NumericLiteral(1), NumericLiteral(2)))",
                "ListType(number)",
                "sublist(sort(asList(score5, score6, score7, score8), new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args_) {java.math.BigDecimal x = (java.math.BigDecimal)args_[0]; java.math.BigDecimal y = (java.math.BigDecimal)args_[1];return numericLessThan(y, x);}}), number(\"1\"), number(\"2\"))",
                this.lib.sublist(this.lib.sort(this.lib.asList(score5, score6, score7, score8), new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args_) {NUMBER x = (NUMBER)args_[0]; NUMBER y = (NUMBER)args_[1];return lib.numericLessThan(y, x);}}), this.lib.number("1"), this.lib.number("2")),
                this.lib.asList(this.lib.number("8"), this.lib.number("7")));
        doExpressionTest(entries, "", sublist1,
                "FunctionInvocation(Name(sublist) -> PositionalParameters(FunctionInvocation(Name(sort) -> PositionalParameters(FunctionInvocation(Name(concatenate) -> PositionalParameters(ListLiteral(Name(score1),Name(score2),Name(score3),Name(score4)), FunctionInvocation(Name(sublist) -> PositionalParameters(FunctionInvocation(Name(sort) -> PositionalParameters(ListLiteral(Name(score5),Name(score6),Name(score7),Name(score8)), FunctionDefinition(FormalParameter(x, number, false, false),FormalParameter(y, number, false, false), Relational(<,Name(y),Name(x)), false))), NumericLiteral(1), NumericLiteral(2))))), FunctionDefinition(FormalParameter(x, number, false, false),FormalParameter(y, number, false, false), Relational(<,Name(y),Name(x)), false))), NumericLiteral(1), NumericLiteral(3)))",
                "ListType(number)",
                "sublist(sort(concatenate(asList(score1, score2, score3, score4), sublist(sort(asList(score5, score6, score7, score8), new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args_) {java.math.BigDecimal x = (java.math.BigDecimal)args_[0]; java.math.BigDecimal y = (java.math.BigDecimal)args_[1];return numericLessThan(y, x);}}), number(\"1\"), number(\"2\"))), new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args_) {java.math.BigDecimal x = (java.math.BigDecimal)args_[0]; java.math.BigDecimal y = (java.math.BigDecimal)args_[1];return numericLessThan(y, x);}}), number(\"1\"), number(\"3\"))",
                this.lib.sublist(this.lib.sort(this.lib.concatenate(this.lib.asList(score1, score2, score3, score4), this.lib.sublist(this.lib.sort(this.lib.asList(score5, score6, score7, score8), new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args_) {NUMBER x = (NUMBER)args_[0]; NUMBER y = (NUMBER)args_[1];return lib.numericLessThan(y, x);}}), this.lib.number("1"), this.lib.number("2"))), new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args_) {NUMBER x = (NUMBER)args_[0]; NUMBER y = (NUMBER)args_[1];return lib.numericLessThan(y, x);}}), this.lib.number("1"), this.lib.number("3")),
                this.lib.asList(this.lib.number("8"), this.lib.number("7"), this.lib.number("4")));
        doExpressionTest(entries, "", forExp,
                "ForExpression(Iterator(i in ExpressionIteratorDomain(FunctionInvocation(Name(sublist) -> PositionalParameters(FunctionInvocation(Name(sort) -> PositionalParameters(FunctionInvocation(Name(concatenate) -> PositionalParameters(ListLiteral(Name(score1),Name(score2),Name(score3),Name(score4)), FunctionInvocation(Name(sublist) -> PositionalParameters(FunctionInvocation(Name(sort) -> PositionalParameters(ListLiteral(Name(score5),Name(score6),Name(score7),Name(score8)), FunctionDefinition(FormalParameter(x, number, false, false),FormalParameter(y, number, false, false), Relational(<,Name(y),Name(x)), false))), NumericLiteral(1), NumericLiteral(2))))), FunctionDefinition(FormalParameter(x, number, false, false),FormalParameter(y, number, false, false), Relational(<,Name(y),Name(x)), false))), NumericLiteral(1), NumericLiteral(3))))) -> Multiplication(*,Name(i),Name(i)))",
                "ListType(number)",
                "sublist(sort(concatenate(asList(score1, score2, score3, score4), sublist(sort(asList(score5, score6, score7, score8), new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args_) {java.math.BigDecimal x = (java.math.BigDecimal)args_[0]; java.math.BigDecimal y = (java.math.BigDecimal)args_[1];return numericLessThan(y, x);}}), number(\"1\"), number(\"2\"))), new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args_) {java.math.BigDecimal x = (java.math.BigDecimal)args_[0]; java.math.BigDecimal y = (java.math.BigDecimal)args_[1];return numericLessThan(y, x);}}), number(\"1\"), number(\"3\")).stream().map(i -> numericMultiply(i, i)).collect(Collectors.toList())",
                this.lib.sublist(this.lib.sort(this.lib.concatenate(this.lib.asList(score1, score2, score3, score4), this.lib.sublist(this.lib.sort(this.lib.asList(score5, score6, score7, score8), new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args_) {NUMBER x = (NUMBER)args_[0]; NUMBER y = (NUMBER)args_[1];return lib.numericLessThan(y, x);}}), this.lib.number("1"), this.lib.number("2"))), new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args_) {NUMBER x = (NUMBER)args_[0]; NUMBER y = (NUMBER)args_[1];return lib.numericLessThan(y, x);}}), this.lib.number("1"), this.lib.number("3")).stream().map(i -> lib.numericMultiply(i, i)).collect(Collectors.toList()),
                this.lib.asList(this.lib.number("64"), this.lib.number("49"), this.lib.number("16")));
        doExpressionTest(entries, "", forExp,
                "ForExpression(Iterator(i in ExpressionIteratorDomain(FunctionInvocation(Name(sublist) -> PositionalParameters(FunctionInvocation(Name(sort) -> PositionalParameters(FunctionInvocation(Name(concatenate) -> PositionalParameters(ListLiteral(Name(score1),Name(score2),Name(score3),Name(score4)), FunctionInvocation(Name(sublist) -> PositionalParameters(FunctionInvocation(Name(sort) -> PositionalParameters(ListLiteral(Name(score5),Name(score6),Name(score7),Name(score8)), FunctionDefinition(FormalParameter(x, number, false, false),FormalParameter(y, number, false, false), Relational(<,Name(y),Name(x)), false))), NumericLiteral(1), NumericLiteral(2))))), FunctionDefinition(FormalParameter(x, number, false, false),FormalParameter(y, number, false, false), Relational(<,Name(y),Name(x)), false))), NumericLiteral(1), NumericLiteral(3))))) -> Multiplication(*,Name(i),Name(i)))",
                "ListType(number)",
                "sublist(sort(concatenate(asList(score1, score2, score3, score4), sublist(sort(asList(score5, score6, score7, score8), new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args_) {java.math.BigDecimal x = (java.math.BigDecimal)args_[0]; java.math.BigDecimal y = (java.math.BigDecimal)args_[1];return numericLessThan(y, x);}}), number(\"1\"), number(\"2\"))), new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args_) {java.math.BigDecimal x = (java.math.BigDecimal)args_[0]; java.math.BigDecimal y = (java.math.BigDecimal)args_[1];return numericLessThan(y, x);}}), number(\"1\"), number(\"3\")).stream().map(i -> numericMultiply(i, i)).collect(Collectors.toList())",
                this.lib.sublist(this.lib.sort(this.lib.concatenate(this.lib.asList(score1, score2, score3, score4), this.lib.sublist(this.lib.sort(this.lib.asList(score5, score6, score7, score8), new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args_) {NUMBER x = (NUMBER)args_[0]; NUMBER y = (NUMBER)args_[1];return lib.numericLessThan(y, x);}}), this.lib.number("1"), this.lib.number("2"))), new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args_) {NUMBER x = (NUMBER)args_[0]; NUMBER y = (NUMBER)args_[1];return lib.numericLessThan(y, x);}}), this.lib.number("1"), this.lib.number("3")).stream().map(i -> lib.numericMultiply(i, i)).collect(Collectors.toList()),
                this.lib.asList(this.lib.number("64"), this.lib.number("49"), this.lib.number("16")));
        doExpressionTest(entries, "", sum,
                "FunctionInvocation(Name(sum) -> PositionalParameters(ForExpression(Iterator(i in ExpressionIteratorDomain(FunctionInvocation(Name(sublist) -> PositionalParameters(FunctionInvocation(Name(sort) -> PositionalParameters(FunctionInvocation(Name(concatenate) -> PositionalParameters(ListLiteral(Name(score1),Name(score2),Name(score3),Name(score4)), FunctionInvocation(Name(sublist) -> PositionalParameters(FunctionInvocation(Name(sort) -> PositionalParameters(ListLiteral(Name(score5),Name(score6),Name(score7),Name(score8)), FunctionDefinition(FormalParameter(x, number, false, false),FormalParameter(y, number, false, false), Relational(<,Name(y),Name(x)), false))), NumericLiteral(1), NumericLiteral(2))))), FunctionDefinition(FormalParameter(x, number, false, false),FormalParameter(y, number, false, false), Relational(<,Name(y),Name(x)), false))), NumericLiteral(1), NumericLiteral(3))))) -> Multiplication(*,Name(i),Name(i)))))",
                "number",
                "sum(sublist(sort(concatenate(asList(score1, score2, score3, score4), sublist(sort(asList(score5, score6, score7, score8), new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args_) {java.math.BigDecimal x = (java.math.BigDecimal)args_[0]; java.math.BigDecimal y = (java.math.BigDecimal)args_[1];return numericLessThan(y, x);}}), number(\"1\"), number(\"2\"))), new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args_) {java.math.BigDecimal x = (java.math.BigDecimal)args_[0]; java.math.BigDecimal y = (java.math.BigDecimal)args_[1];return numericLessThan(y, x);}}), number(\"1\"), number(\"3\")).stream().map(i -> numericMultiply(i, i)).collect(Collectors.toList()))",
                this.lib.sum(this.lib.sublist(this.lib.sort(this.lib.concatenate(this.lib.asList(score1, score2, score3, score4), this.lib.sublist(this.lib.sort(this.lib.asList(score5, score6, score7, score8), new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args_) {NUMBER x = (NUMBER)args_[0]; NUMBER y = (NUMBER)args_[1];return lib.numericLessThan(y, x);}}), this.lib.number("1"), this.lib.number("2"))), new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args_) {NUMBER x = (NUMBER)args_[0]; NUMBER y = (NUMBER)args_[1];return lib.numericLessThan(y, x);}}), this.lib.number("1"), this.lib.number("3")).stream().map(i -> lib.numericMultiply(i, i)).collect(Collectors.toList())),
                this.lib.number("129"));

        // invocation with positional arguments
        doExpressionTest(entries, "", "and([true, false, true])",
                "FunctionInvocation(Name(and) -> PositionalParameters(ListLiteral(BooleanLiteral(true),BooleanLiteral(false),BooleanLiteral(true))))",
                "boolean",
                "and(asList(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE))",
                this.lib.and(this.lib.asList(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE)),
                false);
        doExpressionTest(entries, "", "or([true, false, true])",
                "FunctionInvocation(Name(or) -> PositionalParameters(ListLiteral(BooleanLiteral(true),BooleanLiteral(false),BooleanLiteral(true))))",
                "boolean",
                "or(asList(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE))",
                this.lib.or(this.lib.asList(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE)),
                true);
        doExpressionTest(entries, "", "contains(\"abc\", \"a\")",
                "FunctionInvocation(Name(contains) -> PositionalParameters(StringLiteral(\"abc\"), StringLiteral(\"a\")))",
                "boolean",
                "contains(\"abc\", \"a\")",
                this.lib.contains("abc", "a"),
                true);
        doExpressionTest(entries, "", "string(from : 1.1)",
                "FunctionInvocation(Name(string) -> NamedParameters(from : NumericLiteral(1.1)))",
                "string",
                null,
                null,
                null);
        doExpressionTest(entries, "", "string length(substring(input, 1))",
                "FunctionInvocation(Name(string length) -> PositionalParameters(FunctionInvocation(Name(substring) -> PositionalParameters(Name(input), NumericLiteral(1)))))",
                "number",
                "stringLength(substring(input, number(\"1\")))",
                this.lib.stringLength(this.lib.substring(input, this.lib.number("1"))),
                this.lib.number("3"));

        // invocation with named arguments
        doExpressionTest(entries, "", "contains(string : \"abc\", match : \"a\")",
                "FunctionInvocation(Name(contains) -> NamedParameters(string : StringLiteral(\"abc\"), match : StringLiteral(\"a\")))",
                "boolean",
                "contains(\"abc\", \"a\")",
                this.lib.contains("abc", "a"),
                true);

    }

    @Override
    @Test
    public void testPathExpression() {
        super.testPathExpression();

        ItemDefinitionType type = new ItemDefinitionType("PrivateFundRequirements").addMember("HierarchyNode", Arrays.asList(), STRING);
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("PrivateFundRequirements", type, null));

        doExpressionTest(entries, "", "string length(PrivateFundRequirements.HierarchyNode)",
                "FunctionInvocation(Name(string length) -> PositionalParameters(PathExpression(Name(PrivateFundRequirements), HierarchyNode)))",
                "number",
                "stringLength(((String)(privateFundRequirements != null ? privateFundRequirements.getHierarchyNode() : null)))",
                null,
                null);

        doExpressionTest(entries, "", "date(\"2018-12-10\").weekday",
                "PathExpression(DateTimeLiteral(date, \"2018-12-10\"), weekday)",
                "number",
                "weekday(date(\"2018-12-10\"))",
                this.lib.weekday(this.lib.date("2018-12-10")),
                this.lib.number("1"));
        doExpressionTest(entries, "", "date and time(\"2018-12-10T10:30:01\").weekday",
                "PathExpression(DateTimeLiteral(date and time, \"2018-12-10T10:30:01\"), weekday)",
                "number",
                "weekday(dateAndTime(\"2018-12-10T10:30:01\"))",
                this.lib.weekday(this.lib.dateAndTime("2018-12-10T10:30:01")),
                this.lib.number("1"));

        doExpressionTest(entries, "", "time(\"10:30:01\").hour",
                "PathExpression(DateTimeLiteral(time, \"10:30:01\"), hour)",
                "number",
                "hour(time(\"10:30:01\"))",
                this.lib.hour(this.lib.time("10:30:01")),
                this.lib.number("10"));
        doExpressionTest(entries, "", "date and time(\"2018-12-10T10:30:01\").hour",
                "PathExpression(DateTimeLiteral(date and time, \"2018-12-10T10:30:01\"), hour)",
                "number",
                "hour(dateAndTime(\"2018-12-10T10:30:01\"))",
                this.lib.hour(this.lib.dateAndTime("2018-12-10T10:30:01")),
                this.lib.number("10"));
    }

    @Override
    @Test
    public void testConversionFunctions() {
        super.testConversionFunctions();

        List<EnvironmentEntry> entries = Arrays.asList();

        doExpressionTest(entries, "", "date(\"2012-03-01\")",
                "DateTimeLiteral(date, \"2012-03-01\")",
                "date",
                "date(\"2012-03-01\")",
                this.lib.date("2012-03-01"),
                this.lib.date("2012-03-01"));
        doExpressionTest(entries, "", "date(date(\"2012-03-01\"))",
                "FunctionInvocation(Name(date) -> PositionalParameters(DateTimeLiteral(date, \"2012-03-01\")))",
                "date",
                "date(date(\"2012-03-01\"))",
                this.lib.date(this.lib.date("2012-03-01")),
                this.lib.date("2012-03-01"));
        doExpressionTest(entries, "", "date(date and time(\"2012-03-01\"))",
                "FunctionInvocation(Name(date) -> PositionalParameters(DateTimeLiteral(date and time, \"2012-03-01\")))",
                "date",
                "date(dateAndTime(\"2012-03-01\"))",
                null,
                null);
        doExpressionTest(entries, "", "date(2012, 3, 1)",
                "FunctionInvocation(Name(date) -> PositionalParameters(NumericLiteral(2012), NumericLiteral(3), NumericLiteral(1)))",
                "date",
                "date(number(\"2012\"), number(\"3\"), number(\"1\"))",
                this.lib.date(this.lib.number("2012"), this.lib.number("3"), this.lib.number("1")),
                this.lib.date("2012-03-01"));
        doExpressionTest(entries, "", "date and time(date(\"2012-03-01\"), time(\"10:11:12Z\"))",
                "FunctionInvocation(Name(date and time) -> PositionalParameters(DateTimeLiteral(date, \"2012-03-01\"), DateTimeLiteral(time, \"10:11:12Z\")))",
                "date and time",
                "dateAndTime(date(\"2012-03-01\"), time(\"10:11:12Z\"))",
                this.lib.dateAndTime(this.lib.date("2012-03-01"), this.lib.time("10:11:12Z")),
                this.lib.dateAndTime("2012-03-01T10:11:12Z"));
        doExpressionTest(entries, "", "date and time(\"2012-03-01T10:11:12Z\")",
                "DateTimeLiteral(date and time, \"2012-03-01T10:11:12Z\")",
                "date and time",
                "dateAndTime(\"2012-03-01T10:11:12Z\")",
                this.lib.dateAndTime("2012-03-01T10:11:12Z"),
                this.lib.dateAndTime("2012-03-01T10:11:12Z"));
        doExpressionTest(entries, "", "time(\"10:11:12Z\")",
                "DateTimeLiteral(time, \"10:11:12Z\")",
                "time",
                "time(\"10:11:12Z\")",
                this.lib.time("10:11:12Z"),
                this.lib.time("10:11:12Z"));
        doExpressionTest(entries, "", "time(date(\"2012-03-01\"))",
                "FunctionInvocation(Name(time) -> PositionalParameters(DateTimeLiteral(date, \"2012-03-01\")))",
                "time",
                "time(date(\"2012-03-01\"))",
                null,
                null);
        doExpressionTest(entries, "", "time(date and time(\"2012-03-01T10:11:12Z\"))",
                "FunctionInvocation(Name(time) -> PositionalParameters(DateTimeLiteral(date and time, \"2012-03-01T10:11:12Z\")))",
                "time",
                "time(dateAndTime(\"2012-03-01T10:11:12Z\"))",
                null,
                null);
        doExpressionTest(entries, "", "number(\"1 000\", \" \", \".\")",
                "FunctionInvocation(Name(number) -> PositionalParameters(StringLiteral(\"1 000\"), StringLiteral(\" \"), StringLiteral(\".\")))",
                "number",
                "number(\"1 000\", \" \", \".\")",
                this.lib.number("1 000", " ", "."),
                this.lib.number("1000"));
        doExpressionTest(entries, "", "number(from: \"1.000.000,01\", 'decimal separator':\",\", 'grouping separator':\".\")",
                "FunctionInvocation(Name(number) -> NamedParameters(from : StringLiteral(\"1.000.000,01\"), decimal separator : StringLiteral(\",\"), grouping separator : StringLiteral(\".\")))",
                "number",
                "number(\"1.000.000,01\", \".\", \",\")",
                this.lib.number("1.000.000,01", ".", ","),
                this.lib.number("1.000.000,01", ".", ","));
        doExpressionTest(entries, "", "number(from: \"1.000.000,01\", decimalSeparator:\",\", groupingSeparator:\".\")",
                "FunctionInvocation(Name(number) -> NamedParameters(from : StringLiteral(\"1.000.000,01\"), decimalSeparator : StringLiteral(\",\"), groupingSeparator : StringLiteral(\".\")))",
                "number",
                "number(\"1.000.000,01\", \".\", \",\")",
                this.lib.number("1.000.000,01", ".", ","),
                this.lib.number("1.000.000,01", ".", ","));
        doExpressionTest(entries, "", "string(1.1)",
                "FunctionInvocation(Name(string) -> PositionalParameters(NumericLiteral(1.1)))",
                "string",
                "string(number(\"1.1\"))",
                this.lib.string(this.lib.number("1.1")),
                "1.1");
        doExpressionTest(entries, "", "duration(\"P1Y2M\")",
                "DateTimeLiteral(duration, \"P1Y2M\")",
                "years and months duration",
                "duration(\"P1Y2M\")",
                this.lib.duration("P1Y2M"),
                this.lib.duration("P1Y2M"));
        doExpressionTest(entries, "", "duration(\"P1DT2H3M3S\")",
                "DateTimeLiteral(duration, \"P1DT2H3M3S\")",
                "days and time duration",
                "duration(\"P1DT2H3M3S\")",
                this.lib.duration("P1DT2H3M3S"),
                this.lib.duration("P1DT2H3M3S"));
//        doExpressionTest(entries, "", "duration(\"P1Y1M2DT3H4M5S\")",
//                "DateTimeLiteral(duration, \"P1DT2H3M3S\")",
//                "days and time duration",
//                "duration(\"P1DT2H3M3S\")",
//                this.lib.duration("P1DT2H3M3S"),
//                this.lib.duration("P1DT2H3M3S"));
        doExpressionTest(entries, "", "years and months duration(date(\"2012-03-01\"), date(\"2013-05-01\"))",
                "FunctionInvocation(Name(years and months duration) -> PositionalParameters(DateTimeLiteral(date, \"2012-03-01\"), DateTimeLiteral(date, \"2013-05-01\")))",
                "years and months duration",
                "yearsAndMonthsDuration(date(\"2012-03-01\"), date(\"2013-05-01\"))",
                this.lib.yearsAndMonthsDuration(this.lib.date("2012-03-01"), this.lib.date("2013-05-01")),
                this.lib.duration("P1Y2M"));
    }

    @Test
    public void testNumericFunctions() {
        List<EnvironmentEntry> entries = Arrays.asList();

        doExpressionTest(entries, "", "decimal(100, 2)",
                "FunctionInvocation(Name(decimal) -> PositionalParameters(NumericLiteral(100), NumericLiteral(2)))",
                "number",
                "decimal(number(\"100\"), number(\"2\"))",
                this.lib.decimal(this.lib.number("100"), this.lib.number("2")),
                this.lib.decimal(this.lib.number("100"), this.lib.number("2")));
        doExpressionTest(entries, "", "round(5.125, 2, \"up\")",
                "FunctionInvocation(Name(round) -> PositionalParameters(NumericLiteral(5.125), NumericLiteral(2), StringLiteral(\"up\")))",
                "number",
                "round(number(\"5.125\"), number(\"2\"), \"up\")",
                this.lib.round(this.lib.number("5.125"), this.lib.number("2"), "up"),
                this.lib.number("5.13"));
        doExpressionTest(entries, "", "round up(5.125, 2)",
                "FunctionInvocation(Name(round up) -> PositionalParameters(NumericLiteral(5.125), NumericLiteral(2)))",
                "number",
                "roundUp(number(\"5.125\"), number(\"2\"))",
                this.lib.roundUp(this.lib.number("5.125"), this.lib.number("2")),
                this.lib.number("5.13"));
        doExpressionTest(entries, "", "round down(5.125, 2)",
                "FunctionInvocation(Name(round down) -> PositionalParameters(NumericLiteral(5.125), NumericLiteral(2)))",
                "number",
                "roundDown(number(\"5.125\"), number(\"2\"))",
                this.lib.roundDown(this.lib.number("5.125"), this.lib.number("2")),
                this.lib.number("5.12"));
        doExpressionTest(entries, "", "round half up(5.125, 2)",
                "FunctionInvocation(Name(round half up) -> PositionalParameters(NumericLiteral(5.125), NumericLiteral(2)))",
                "number",
                "roundHalfUp(number(\"5.125\"), number(\"2\"))",
                this.lib.roundHalfUp(this.lib.number("5.125"), this.lib.number("2")),
                this.lib.number("5.13"));
        doExpressionTest(entries, "", "round half down(5.125, 2)",
                "FunctionInvocation(Name(round half down) -> PositionalParameters(NumericLiteral(5.125), NumericLiteral(2)))",
                "number",
                "roundHalfDown(number(\"5.125\"), number(\"2\"))",
                this.lib.roundHalfDown(this.lib.number("5.125"), this.lib.number("2")),
                this.lib.number("5.12"));
        doExpressionTest(entries, "", "floor(100)",
                "FunctionInvocation(Name(floor) -> PositionalParameters(NumericLiteral(100)))",
                "number",
                "floor(number(\"100\"))",
                this.lib.floor(this.lib.number("100")),
                this.lib.number("100"));
        doExpressionTest(entries, "", "floor(100.123, 2)",
                "FunctionInvocation(Name(floor) -> PositionalParameters(NumericLiteral(100.123), NumericLiteral(2)))",
                "number",
                "floor(number(\"100.123\"), number(\"2\"))",
                this.lib.floor(this.lib.number("100.123"), this.lib.number("2")),
                this.lib.number("100.12"));
        doExpressionTest(entries, "", "ceiling(100)",
                "FunctionInvocation(Name(ceiling) -> PositionalParameters(NumericLiteral(100)))",
                "number",
                "ceiling(number(\"100\"))",
                this.lib.ceiling(this.lib.number("100")),
                this.lib.number("100"));
        doExpressionTest(entries, "", "ceiling(100.123, 2)",
                "FunctionInvocation(Name(ceiling) -> PositionalParameters(NumericLiteral(100.123), NumericLiteral(2)))",
                "number",
                "ceiling(number(\"100.123\"), number(\"2\"))",
                this.lib.ceiling(this.lib.number("100.123"), this.lib.number("2")),
                this.lib.number("100.13"));
        doExpressionTest(entries, "", "abs(100)",
                "FunctionInvocation(Name(abs) -> PositionalParameters(NumericLiteral(100)))",
                "number",
                "abs(number(\"100\"))",
                this.lib.abs(this.lib.number("100")),
                this.lib.number("100"));
        doExpressionTest(entries, "", "abs(-100)",
                "FunctionInvocation(Name(abs) -> PositionalParameters(ArithmeticNegation(NumericLiteral(100))))",
                "number",
                "abs(numericUnaryMinus(number(\"100\")))",
                this.lib.abs(this.lib.numericUnaryMinus(this.lib.number("100"))),
                this.lib.number("100"));
        doExpressionTest(entries, "", "abs(@\"PT5H\")",
                "FunctionInvocation(Name(abs) -> PositionalParameters(DateTimeLiteral(duration, \"PT5H\")))",
                "days and time duration",
                "abs(duration(\"PT5H\"))",
                this.lib.abs(this.lib.duration("PT5H")),
                this.lib.duration("PT5H"));
        doExpressionTest(entries, "", "abs(@\"-PT5H\")",
                "FunctionInvocation(Name(abs) -> PositionalParameters(DateTimeLiteral(duration, \"-PT5H\")))",
                "days and time duration",
                "abs(duration(\"-PT5H\"))",
                this.lib.abs(this.lib.duration("-PT5H")),
                this.lib.duration("PT5H"));
        doExpressionTest(entries, "", "modulo(100, 3)",
                "FunctionInvocation(Name(modulo) -> PositionalParameters(NumericLiteral(100), NumericLiteral(3)))",
                "number",
                "modulo(number(\"100\"), number(\"3\"))",
                this.lib.modulo(this.lib.number("100"), this.lib.number("3")),
                this.lib.number("1"));
        doExpressionTest(entries, "", "sqrt(101)",
                "FunctionInvocation(Name(sqrt) -> PositionalParameters(NumericLiteral(101)))",
                "number",
                "sqrt(number(\"101\"))",
                this.lib.sqrt(this.lib.number("101")),
                this.lib.number("10.04987562112089"));
        doExpressionTest(entries, "", "log(100)",
                "FunctionInvocation(Name(log) -> PositionalParameters(NumericLiteral(100)))",
                "number",
                "log(number(\"100\"))",
                this.lib.log(this.lib.number("100")),
                this.lib.number("4.605170185988092"));
        doExpressionTest(entries, "", "exp(100)",
                "FunctionInvocation(Name(exp) -> PositionalParameters(NumericLiteral(100)))",
                "number",
                "exp(number(\"100\"))",
                this.lib.exp(this.lib.number("100")),
                this.lib.number("2.6881171418161356E+43"));
        doExpressionTest(entries, "", "odd(100)",
                "FunctionInvocation(Name(odd) -> PositionalParameters(NumericLiteral(100)))",
                "boolean",
                "odd(number(\"100\"))",
                this.lib.odd(this.lib.number("100")),
                false);
        doExpressionTest(entries, "", "even(100)",
                "FunctionInvocation(Name(even) -> PositionalParameters(NumericLiteral(100)))",
                "boolean",
                "even(number(\"100\"))",
                this.lib.even(this.lib.number("100")),
                true);
    }

    @Test
    public void testBooleanFunctions() {
        List<EnvironmentEntry> entries = Arrays.asList();

        doExpressionTest(entries, "", "not(true)",
                "LogicNegation(BooleanLiteral(true))",
                "boolean",
                "booleanNot(Boolean.TRUE)",
                this.lib.booleanNot(Boolean.TRUE),
                false);
    }

    @Test
    public void testStringFunctions() {
        List<EnvironmentEntry> entries = Arrays.asList();

        doExpressionTest(entries, "", "substring(\"abc\", 3)",
                "FunctionInvocation(Name(substring) -> PositionalParameters(StringLiteral(\"abc\"), NumericLiteral(3)))",
                "string",
                "substring(\"abc\", number(\"3\"))",
                this.lib.substring("abc", this.lib.number("3")),
                "c");
        doExpressionTest(entries, "", "substring(string: \"abc\", 'start position': 3)",
                "FunctionInvocation(Name(substring) -> NamedParameters(string : StringLiteral(\"abc\"), start position : NumericLiteral(3)))",
                "string",
                "substring(\"abc\", number(\"3\"))",
                this.lib.substring("abc", this.lib.number("3")),
                "c");
        doExpressionTest(entries, "", "string length(\"abc\")",
                "FunctionInvocation(Name(string length) -> PositionalParameters(StringLiteral(\"abc\")))",
                "number",
                "stringLength(\"abc\")",
                this.lib.stringLength("abc"),
                this.lib.number("3"));
        doExpressionTest(entries, "", "string length(\"\\n\")",
                "FunctionInvocation(Name(string length) -> PositionalParameters(StringLiteral(\"\\n\")))",
                "number",
                "stringLength(\"\\n\")",
                this.lib.stringLength("\n"),
                this.lib.number("1"));
        doExpressionTest(entries, "", "string length(\"\\r\")",
                "FunctionInvocation(Name(string length) -> PositionalParameters(StringLiteral(\"\\r\")))",
                "number",
                "stringLength(\"\\r\")",
                this.lib.stringLength("\r"),
                this.lib.number("1"));
        doExpressionTest(entries, "", "string length(\"\\t\")",
                "FunctionInvocation(Name(string length) -> PositionalParameters(StringLiteral(\"\\t\")))",
                "number",
                "stringLength(\"\\t\")",
                this.lib.stringLength("\t"),
                this.lib.number("1"));
        doExpressionTest(entries, "", "string length(\"\\'\")",
                "FunctionInvocation(Name(string length) -> PositionalParameters(StringLiteral(\"\\'\")))",
                "number",
                "stringLength(\"'\")",
                this.lib.stringLength("\'"),
                this.lib.number("1"));
        doExpressionTest(entries, "", "string length(\"\\\"\")",
                "FunctionInvocation(Name(string length) -> PositionalParameters(StringLiteral(\"\\\"\")))",
                "number",
                "stringLength(\"\\\"\")",
                this.lib.stringLength("\""),
                this.lib.number("1"));
        doExpressionTest(entries, "", "string length(\"\\\\\")",
                "FunctionInvocation(Name(string length) -> PositionalParameters(StringLiteral(\"\\\\\")))",
                "number",
                "stringLength(\"\\\\\")",
                this.lib.stringLength("\\"),
                this.lib.number("1"));
        doExpressionTest(entries, "", "string length(\"\u0009\")",
                "FunctionInvocation(Name(string length) -> PositionalParameters(StringLiteral(\"\t\")))",
                "number",
                "stringLength(\"\\t\")",
                this.lib.stringLength("\t"),
                this.lib.number("1"));
        doExpressionTest(entries, "", "string length(\"\\\\u0009\")",
                "FunctionInvocation(Name(string length) -> PositionalParameters(StringLiteral(\"\\\\u0009\")))",
                "number",
                "stringLength(\"\\\\u0009\")",
                this.lib.stringLength("\\u0009"),
                this.lib.number("6"));
        doExpressionTest(entries, "", "string length(\"\\uD83D\\uDCA9\")",
                "FunctionInvocation(Name(string length) -> PositionalParameters(StringLiteral(\"\\uD83D\\uDCA9\")))",
                "number",
                "stringLength(\"\\uD83D\\uDCA9\")",
                this.lib.stringLength("\uD83D\uDCA9"),
                this.lib.number("1"));
        doExpressionTest(entries, "", "upper case(\"abc\")",
                "FunctionInvocation(Name(upper case) -> PositionalParameters(StringLiteral(\"abc\")))",
                "string",
                "upperCase(\"abc\")",
                this.lib.upperCase("abc"),
                "ABC");
        doExpressionTest(entries, "", "lower case(\"abc\")",
                "FunctionInvocation(Name(lower case) -> PositionalParameters(StringLiteral(\"abc\")))",
                "string",
                "lowerCase(\"abc\")",
                this.lib.lowerCase("abc"),
                "abc");
        doExpressionTest(entries, "", "substring before(\"abc\", \"b\")",
                "FunctionInvocation(Name(substring before) -> PositionalParameters(StringLiteral(\"abc\"), StringLiteral(\"b\")))",
                "string",
                "substringBefore(\"abc\", \"b\")",
                this.lib.substringBefore("abc", "b"),
                "a");
        doExpressionTest(entries, "", "substring after(\"abc\", \"b\")",
                "FunctionInvocation(Name(substring after) -> PositionalParameters(StringLiteral(\"abc\"), StringLiteral(\"b\")))",
                "string",
                "substringAfter(\"abc\", \"b\")",
                this.lib.substringAfter("abc", "b"),
                "c");
        doExpressionTest(entries, "", "replace(\"abc\", \"b\", \"d\", \"i\")",
                "FunctionInvocation(Name(replace) -> PositionalParameters(StringLiteral(\"abc\"), StringLiteral(\"b\"), StringLiteral(\"d\"), StringLiteral(\"i\")))",
                "string",
                "replace(\"abc\", \"b\", \"d\", \"i\")",
                this.lib.replace("abc", "b", "d", "i"),
                "adc");
        doExpressionTest(entries, "", "replace(\"abc\", \"b\", \"d\")",
                "FunctionInvocation(Name(replace) -> PositionalParameters(StringLiteral(\"abc\"), StringLiteral(\"b\"), StringLiteral(\"d\")))",
                "string",
                "replace(\"abc\", \"b\", \"d\")",
                this.lib.replace("abc", "b", "d"),
                "adc");
        doExpressionTest(entries, "", "replace(\"0123456789\",\"(\\d{3})(\\d{3})(\\d{4})\",\"($1) $2-$3\")",
                "FunctionInvocation(Name(replace) -> PositionalParameters(StringLiteral(\"0123456789\"), StringLiteral(\"(\\d{3})(\\d{3})(\\d{4})\"), StringLiteral(\"($1) $2-$3\")))",
                "string",
                "replace(\"0123456789\", \"(\\\\d{3})(\\\\d{3})(\\\\d{4})\", \"($1) $2-$3\")",
                this.lib.replace("0123456789", "(\\d{3})(\\d{3})(\\d{4})", "($1) $2-$3"),
                "(012) 345-6789");
        doExpressionTest(entries, "", "contains(\"abc\", \"a\")",
                "FunctionInvocation(Name(contains) -> PositionalParameters(StringLiteral(\"abc\"), StringLiteral(\"a\")))",
                "boolean",
                "contains(\"abc\", \"a\")",
                this.lib.contains("abc", "a"),
                true);
        doExpressionTest(entries, "", "starts with(\"abc\", \"a\")",
                "FunctionInvocation(Name(starts with) -> PositionalParameters(StringLiteral(\"abc\"), StringLiteral(\"a\")))",
                "boolean",
                "startsWith(\"abc\", \"a\")",
                this.lib.startsWith("abc", "a"),
                true);
        doExpressionTest(entries, "", "ends with(\"abc\", \"c\")",
                "FunctionInvocation(Name(ends with) -> PositionalParameters(StringLiteral(\"abc\"), StringLiteral(\"c\")))",
                "boolean",
                "endsWith(\"abc\", \"c\")",
                this.lib.endsWith("abc", "c"),
                true);
        doExpressionTest(entries, "", "matches(\"abc\", \"abc\", \"i\")",
                "FunctionInvocation(Name(matches) -> PositionalParameters(StringLiteral(\"abc\"), StringLiteral(\"abc\"), StringLiteral(\"i\")))",
                "boolean",
                "matches(\"abc\", \"abc\", \"i\")",
                this.lib.matches("abc", "abc", "i"),
                true);
        doExpressionTest(entries, "", "matches(\"abc\", \"abc\")",
                "FunctionInvocation(Name(matches) -> PositionalParameters(StringLiteral(\"abc\"), StringLiteral(\"abc\")))",
                "boolean",
                "matches(\"abc\", \"abc\")",
                this.lib.matches("abc", "abc"),
                true);
        doExpressionTest(entries, "", "matches(\"?\", \"\\p{Nd}+\")",
                "FunctionInvocation(Name(matches) -> PositionalParameters(StringLiteral(\"?\"), StringLiteral(\"\\p{Nd}+\")))",
                "boolean",
                "matches(\"?\", \"\\\\p{Nd}+\")",
                this.lib.matches("?", "\\p{Nd}+"),
                false);
        doExpressionTest(entries, "", "split(\"John Doe\", \"\\s\")",
                "FunctionInvocation(Name(split) -> PositionalParameters(StringLiteral(\"John Doe\"), StringLiteral(\"\\s\")))",
                "ListType(string)",
                "split(\"John Doe\", \"\\\\s\")",
                this.lib.split("John Doe", "\\s"),
                this.lib.asList("John", "Doe"));
    }

    @Test
    public void testListFunctions() {
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, this.lib.number("1")));

        doExpressionTest(entries, "", "list contains([1, 2, 3], 1)",
                "FunctionInvocation(Name(list contains) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), NumericLiteral(1)))",
                "boolean",
                "listContains(asList(number(\"1\"), number(\"2\"), number(\"3\")), number(\"1\"))",
                this.lib.listContains(Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3")), this.lib.number("1")),
                true);
        doExpressionTest(entries, "", "count([1, 2, 3])",
                "FunctionInvocation(Name(count) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3))))",
                "number",
                "count(asList(number(\"1\"), number(\"2\"), number(\"3\")))",
                this.lib.count(Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3"))),
                this.lib.number("3"));
        doExpressionTest(entries, "", "min([1, 2, 3])",
                "FunctionInvocation(Name(min) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3))))",
                "number",
                "min(asList(number(\"1\"), number(\"2\"), number(\"3\")))",
                this.lib.min(Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3"))),
                this.lib.number("1"));
        doExpressionTest(entries, "", "min(1, 2, 3)",
                "FunctionInvocation(Name(min) -> PositionalParameters(NumericLiteral(1), NumericLiteral(2), NumericLiteral(3)))",
                "number",
                "min(number(\"1\"), number(\"2\"), number(\"3\"))",
                this.lib.min(this.lib.number("1"), this.lib.number("2"), this.lib.number("3")),
                this.lib.number("1"));
        doExpressionTest(entries, "", "max([1, 2, 3])",
                "FunctionInvocation(Name(max) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3))))",
                "number",
                "max(asList(number(\"1\"), number(\"2\"), number(\"3\")))",
                this.lib.max(Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3"))),
                this.lib.number("3"));
        doExpressionTest(entries, "", "max(1, 2, 3)",
                "FunctionInvocation(Name(max) -> PositionalParameters(NumericLiteral(1), NumericLiteral(2), NumericLiteral(3)))",
                "number",
                "max(number(\"1\"), number(\"2\"), number(\"3\"))",
                this.lib.max(this.lib.number("1"), this.lib.number("2"), this.lib.number("3")),
                this.lib.number("3"));
        doExpressionTest(entries, "", "sum([1, 2, 3])",
                "FunctionInvocation(Name(sum) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3))))",
                "number",
                "sum(asList(number(\"1\"), number(\"2\"), number(\"3\")))",
                this.lib.sum(Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3"))),
                this.lib.number("6"));
        doExpressionTest(entries, "", "sum(1, 2, 3)",
                "FunctionInvocation(Name(sum) -> PositionalParameters(NumericLiteral(1), NumericLiteral(2), NumericLiteral(3)))",
                "number",
                "sum(number(\"1\"), number(\"2\"), number(\"3\"))",
                this.lib.sum(this.lib.number("1"), this.lib.number("2"), this.lib.number("3")),
                this.lib.number("6"));
        doExpressionTest(entries, "", "mean([1, 2, 3])",
                "FunctionInvocation(Name(mean) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3))))",
                "number",
                "mean(asList(number(\"1\"), number(\"2\"), number(\"3\")))",
                this.lib.mean(Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3"))),
                this.lib.number("2"));
        doExpressionTest(entries, "", "mean(1, 2, 3)",
                "FunctionInvocation(Name(mean) -> PositionalParameters(NumericLiteral(1), NumericLiteral(2), NumericLiteral(3)))",
                "number",
                "mean(number(\"1\"), number(\"2\"), number(\"3\"))",
                this.lib.mean(this.lib.number("1"), this.lib.number("2"), this.lib.number("3")),
                this.lib.number("2"));
        doExpressionTest(entries, "", "and([true, true, false])",
                "FunctionInvocation(Name(and) -> PositionalParameters(ListLiteral(BooleanLiteral(true),BooleanLiteral(true),BooleanLiteral(false))))",
                "boolean",
                "and(asList(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE))",
                this.lib.and(this.lib.asList(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE)),
                false);
        doExpressionTest(entries, "", "and(true, true, false)",
                "FunctionInvocation(Name(and) -> PositionalParameters(BooleanLiteral(true), BooleanLiteral(true), BooleanLiteral(false)))",
                "boolean",
                "and(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE)",
                this.lib.and(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE),
                false);
        doExpressionTest(entries, "", "all([true, true, false])",
                "FunctionInvocation(Name(all) -> PositionalParameters(ListLiteral(BooleanLiteral(true),BooleanLiteral(true),BooleanLiteral(false))))",
                "boolean",
                "all(asList(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE))",
                this.lib.all(this.lib.asList(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE)),
                false);
        doExpressionTest(entries, "", "all(true, true, false)",
                "FunctionInvocation(Name(all) -> PositionalParameters(BooleanLiteral(true), BooleanLiteral(true), BooleanLiteral(false)))",
                "boolean",
                "all(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE)",
                this.lib.all(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE),
                false);
        doExpressionTest(entries, "", "or([true, true, false])",
                "FunctionInvocation(Name(or) -> PositionalParameters(ListLiteral(BooleanLiteral(true),BooleanLiteral(true),BooleanLiteral(false))))",
                "boolean",
                "or(asList(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE))",
                this.lib.or(this.lib.asList(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE)),
                true);
        doExpressionTest(entries, "", "or([true, true, false])",
                "FunctionInvocation(Name(or) -> PositionalParameters(ListLiteral(BooleanLiteral(true),BooleanLiteral(true),BooleanLiteral(false))))",
                "boolean",
                "or(asList(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE))",
                this.lib.or(this.lib.asList(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE)),
                true);
        doExpressionTest(entries, "", "any([true, true, false])",
                "FunctionInvocation(Name(any) -> PositionalParameters(ListLiteral(BooleanLiteral(true),BooleanLiteral(true),BooleanLiteral(false))))",
                "boolean",
                "any(asList(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE))",
                this.lib.any(this.lib.asList(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE)),
                true);
        doExpressionTest(entries, "", "any(true, true, false)",
                "FunctionInvocation(Name(any) -> PositionalParameters(BooleanLiteral(true), BooleanLiteral(true), BooleanLiteral(false)))",
                "boolean",
                "any(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE)",
                this.lib.any(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE),
                true);

        // function with refined type
        doExpressionTest(entries, "", "sublist([1, 2, 3], 2, 1)",
                "FunctionInvocation(Name(sublist) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), NumericLiteral(2), NumericLiteral(1)))",
                "ListType(number)",
                "sublist(asList(number(\"1\"), number(\"2\"), number(\"3\")), number(\"2\"), number(\"1\"))",
                this.lib.sublist(Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3")), this.lib.number("2"), this.lib.number("1")),
                Arrays.asList(this.lib.number("2")));
        doExpressionTest(entries, "", "sublist([1, 2, 3], 2)",
                "FunctionInvocation(Name(sublist) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), NumericLiteral(2)))",
                "ListType(number)",
                "sublist(asList(number(\"1\"), number(\"2\"), number(\"3\")), number(\"2\"))",
                this.lib.sublist(Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3")), this.lib.number("2")),
                Arrays.asList(this.lib.number("2"), this.lib.number("3")));
        doExpressionTest(entries, "", "append([1, 2, 3], 4)",
                "FunctionInvocation(Name(append) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), NumericLiteral(4)))",
                "ListType(number)",
                "append(asList(number(\"1\"), number(\"2\"), number(\"3\")), number(\"4\"))",
                this.lib.append(Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3")), this.lib.number("4")),
                Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3"), this.lib.number("4")));
        doExpressionTest(entries, "", "append([1, 2, 3], 4, 5)",
                "FunctionInvocation(Name(append) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), NumericLiteral(4), NumericLiteral(5)))",
                "ListType(number)",
                "append(asList(number(\"1\"), number(\"2\"), number(\"3\")), number(\"4\"), number(\"5\"))",
                this.lib.append(Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3")), this.lib.number("4"), this.lib.number("5")),
                Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3"), this.lib.number("4"), this.lib.number("5")));
        doExpressionTest(entries, "", "concatenate([1, 2, 3], [4, 5, 6])",
                "FunctionInvocation(Name(concatenate) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), ListLiteral(NumericLiteral(4),NumericLiteral(5),NumericLiteral(6))))",
                "ListType(number)",
                "concatenate(asList(number(\"1\"), number(\"2\"), number(\"3\")), asList(number(\"4\"), number(\"5\"), number(\"6\")))",
                this.lib.concatenate(Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3")), Arrays.asList(this.lib.number("4"), this.lib.number("5"), this.lib.number("6"))),
                Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3"), this.lib.number("4"), this.lib.number("5"), this.lib.number("6")));
        doExpressionTest(entries, "", "insert before([\"1\", \"2\", \"3\"], 3, \"5\")",
                "FunctionInvocation(Name(insert before) -> PositionalParameters(ListLiteral(StringLiteral(\"1\"),StringLiteral(\"2\"),StringLiteral(\"3\")), NumericLiteral(3), StringLiteral(\"5\")))",
                "ListType(string)",
                "insertBefore(asList(\"1\", \"2\", \"3\"), number(\"3\"), \"5\")",
                this.lib.insertBefore(this.lib.asList("1", "2", "3"), this.lib.number("3"), "5"),
                this.lib.asList("1", "2", "5", "3"));
        doExpressionTest(entries, "", "remove([1, 2, 3], 1)",
                "FunctionInvocation(Name(remove) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), NumericLiteral(1)))",
                "ListType(number)",
                "remove(asList(number(\"1\"), number(\"2\"), number(\"3\")), number(\"1\"))",
                this.lib.remove(Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3")), this.lib.number("1")),
                Arrays.asList(this.lib.number("2"), this.lib.number("3")));
        doExpressionTest(entries, "", "reverse([1, 2, 3])",
                "FunctionInvocation(Name(reverse) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3))))",
                "ListType(number)",
                "reverse(asList(number(\"1\"), number(\"2\"), number(\"3\")))",
                this.lib.reverse(Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3"))),
                Arrays.asList(this.lib.number("3"), this.lib.number("2"), this.lib.number("1")));
        doExpressionTest(entries, "", "index of([1, 2, 3], 3)",
                "FunctionInvocation(Name(index of) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), NumericLiteral(3)))",
                "ListType(number)",
                "indexOf(asList(number(\"1\"), number(\"2\"), number(\"3\")), number(\"3\"))",
                this.lib.indexOf(Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3")), this.lib.number("3")),
                Arrays.asList(this.lib.number("3")));
        doExpressionTest(entries, "", "distinct values([1, 2, 3])",
                "FunctionInvocation(Name(distinct values) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3))))",
                "ListType(number)",
                "distinctValues(asList(number(\"1\"), number(\"2\"), number(\"3\")))",
                this.lib.distinctValues(Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3"))),
                Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3")));
        doExpressionTest(entries, "", "index of([\"1\", \"2\", \"2\"], \"2\")",
                "FunctionInvocation(Name(index of) -> PositionalParameters(ListLiteral(StringLiteral(\"1\"),StringLiteral(\"2\"),StringLiteral(\"2\")), StringLiteral(\"2\")))",
                "ListType(number)",
                "indexOf(asList(\"1\", \"2\", \"2\"), \"2\")",
                this.lib.indexOf(this.lib.asList("1", "2", "2"), "2"),
                this.lib.asList(this.lib.number("2"), this.lib.number("3")));
        doExpressionTest(entries, "", "union([1, 2, 3], [4, 5, 6])",
                "FunctionInvocation(Name(union) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), ListLiteral(NumericLiteral(4),NumericLiteral(5),NumericLiteral(6))))",
                "ListType(number)",
                "union(asList(number(\"1\"), number(\"2\"), number(\"3\")), asList(number(\"4\"), number(\"5\"), number(\"6\")))",
                this.lib.union(Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3")), Arrays.asList(this.lib.number("4"), this.lib.number("5"), this.lib.number("6"))),
                Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3"), this.lib.number("4"), this.lib.number("5"), this.lib.number("6")));
        doExpressionTest(entries, "", "flatten([])",
                "FunctionInvocation(Name(flatten) -> PositionalParameters(ListLiteral()))",
                "ListType(Any)",
                "flatten(asList())",
                this.lib.flatten(this.lib.asList()),
                this.lib.asList());
        doExpressionTest(entries, "", "flatten([1, 2, 3])",
                "FunctionInvocation(Name(flatten) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3))))",
                "ListType(number)",
                "flatten(asList(number(\"1\"), number(\"2\"), number(\"3\")))",
                this.lib.flatten(Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3"))),
                Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3")));
        doExpressionTest(entries, "", "flatten([1, 2, [3, [4]], [5, 6]])",
                "FunctionInvocation(Name(flatten) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),ListLiteral(NumericLiteral(3),ListLiteral(NumericLiteral(4))),ListLiteral(NumericLiteral(5),NumericLiteral(6)))))",
                "ListType(number)",
                "flatten(asList(number(\"1\"), number(\"2\"), asList(number(\"3\"), asList(number(\"4\"))), asList(number(\"5\"), number(\"6\"))))",
                this.lib.flatten(this.lib.asList(this.lib.number("1"), this.lib.number("2"), this.lib.asList(this.lib.number("3"), this.lib.asList(this.lib.number("4"))), this.lib.asList(this.lib.number("5"), this.lib.number("6")))),
                this.lib.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3"), this.lib.number("4"), this.lib.number("5"), this.lib.number("6")));
        doExpressionTest(entries, "", "flatten([1, 2, \"3\"])",
                "FunctionInvocation(Name(flatten) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),StringLiteral(\"3\"))))",
                "ListType(Any)",
                "flatten(asList(number(\"1\"), number(\"2\"), \"3\"))",
                this.lib.flatten(Arrays.asList(this.lib.number("1"), this.lib.number("2"), "3")),
                Arrays.asList(this.lib.number("1"), this.lib.number("2"), "3"));
        doExpressionTest(entries, "", "sort([1, 2, 3, 4], function(x: number, y: number) y < x)",
                "FunctionInvocation(Name(sort) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3),NumericLiteral(4)), FunctionDefinition(FormalParameter(x, number, false, false),FormalParameter(y, number, false, false), Relational(<,Name(y),Name(x)), false)))",
                "ListType(number)",
                "sort(asList(number(\"1\"), number(\"2\"), number(\"3\"), number(\"4\")), new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args_) {java.math.BigDecimal x = (java.math.BigDecimal)args_[0]; java.math.BigDecimal y = (java.math.BigDecimal)args_[1];return numericLessThan(y, x);}})",
                this.lib.sort(this.lib.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3"), this.lib.number("4")), new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args_) {NUMBER x = (NUMBER)args_[0]; NUMBER y = (NUMBER)args_[1];return lib.numericLessThan(y, x);}}),
                this.lib.asList(this.lib.number("4"), this.lib.number("3"), this.lib.number("2"), this.lib.number("1")));
        doExpressionTest(entries, "", "sort([\"1\", \"2\", \"3\", \"4\"], function(x: number, y: number) y < x)",
                "FunctionInvocation(Name(sort) -> PositionalParameters(ListLiteral(StringLiteral(\"1\"),StringLiteral(\"2\"),StringLiteral(\"3\"),StringLiteral(\"4\")), FunctionDefinition(FormalParameter(x, string, false, false),FormalParameter(y, string, false, false), Relational(<,Name(y),Name(x)), false)))",
                "ListType(string)",
                "sort(asList(\"1\", \"2\", \"3\", \"4\"), new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args_) {String x = (String)args_[0]; String y = (String)args_[1];return stringLessThan(y, x);}})",
                this.lib.sort(this.lib.asList("1", "2", "3", "4"), new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args_) {String x = (String)args_[0]; String y = (String)args_[1];return lib.stringLessThan(y, x);}}),
                this.lib.asList("4", "3", "2", "1"));
    }

    @Test
    public void testContextFunctions() {
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, this.lib.number("1")));

        doExpressionTest(entries, "", "get entries({a: \"foo\", b: \"bar\"})",
                "FunctionInvocation(Name(get entries) -> PositionalParameters(Context(ContextEntry(ContextEntryKey(a) = StringLiteral(\"foo\")),ContextEntry(ContextEntryKey(b) = StringLiteral(\"bar\")))))",
                "ListType(ContextType())",
                "getEntries(new com.gs.dmn.runtime.Context().add(\"a\", \"foo\").add(\"b\", \"bar\"))",
                this.lib.getEntries(new com.gs.dmn.runtime.Context().add("a", "foo").add("b", "bar")),
                this.lib.asList(new com.gs.dmn.runtime.Context().add("key", "a").add("value", "foo"), new com.gs.dmn.runtime.Context().add("key", "b").add("value", "bar")));
        doExpressionTest(entries, "", "get entries({})",
                "FunctionInvocation(Name(get entries) -> PositionalParameters(Context()))",
                "ListType(ContextType())",
                "getEntries(new com.gs.dmn.runtime.Context())",
                this.lib.getEntries(new com.gs.dmn.runtime.Context()),
                this.lib.asList());

        doExpressionTest(entries, "", "get value({a: \"foo\"}, \"a\")",
                "FunctionInvocation(Name(get value) -> PositionalParameters(Context(ContextEntry(ContextEntryKey(a) = StringLiteral(\"foo\"))), StringLiteral(\"a\")))",
                "Any",
                "getValue(new com.gs.dmn.runtime.Context().add(\"a\", \"foo\"), \"a\")",
                this.lib.getValue(new com.gs.dmn.runtime.Context().add("a", "foo"), "a"),
                "foo");
        doExpressionTest(entries, "", "get value(null, null)",
                "FunctionInvocation(Name(get value) -> PositionalParameters(NullLiteral(), NullLiteral()))",
                "Any",
                "getValue(null, null)",
                this.lib.getValue(null, null),
                null);
    }

    @Test
    public void testDateProperties() {
        List<EnvironmentEntry> entries = new ArrayList<>();

        doExpressionTest(entries, "", "date(\"2010-01-01\").year",
                "PathExpression(DateTimeLiteral(date, \"2010-01-01\"), year)",
                "number",
                "year(date(\"2010-01-01\"))",
                this.lib.year(this.lib.date("2010-01-01")),
                this.lib.number("2010"));
    }

    @Test
    public void testTimeProperties() {
        List<EnvironmentEntry> entries = new ArrayList<>();

        doExpressionTest(entries, "", "time(\"12:00:00\").hour",
                "PathExpression(DateTimeLiteral(time, \"12:00:00\"), hour)",
                "number",
                "hour(time(\"12:00:00\"))",
                this.lib.hour(this.lib.time("12:00:00")),
                this.lib.number("12"));
        doExpressionTest(entries, "", "time(\"12:00:00Z\").time offset",
                "PathExpression(DateTimeLiteral(time, \"12:00:00Z\"), time offset)",
                "days and time duration",
                "timeOffset(time(\"12:00:00Z\"))",
                this.lib.timeOffset(this.lib.time("12:00:00Z")),
                this.lib.duration("P0DT0H0M0.000S"));
    }

    @Test
    public void testDateAndTimeProperties() {
        List<EnvironmentEntry> entries = Arrays.asList(
        );

        doExpressionTest(entries, "", "date and time(\"2018-12-10T10:30:00\").time offset",
                "PathExpression(DateTimeLiteral(date and time, \"2018-12-10T10:30:00\"), time offset)",
                "days and time duration",
                "timeOffset(dateAndTime(\"2018-12-10T10:30:00\"))",
                this.lib.timeOffset((TIME) this.lib.dateAndTime("2018-12-10T10:30:00")),
                null
        );
        doExpressionTest(entries, "", "date and time(\"2018-12-10T10:30:00@Etc/UTC\").timezone",
                "PathExpression(DateTimeLiteral(date and time, \"2018-12-10T10:30:00@Etc/UTC\"), timezone)",
                "string",
                "timezone(dateAndTime(\"2018-12-10T10:30:00@Etc/UTC\"))",
                this.lib.timezone((TIME) this.lib.dateAndTime("2018-12-10T10:30:00@Etc/UTC")),
                "Etc/UTC"
        );
        doExpressionTest(entries, "", "date and time(\"2018-12-10T10:30:00\").timezone",
                "PathExpression(DateTimeLiteral(date and time, \"2018-12-10T10:30:00\"), timezone)",
                "string",
                "timezone(dateAndTime(\"2018-12-10T10:30:00\"))",
                this.lib.timezone((TIME) this.lib.dateAndTime("2018-12-10T10:30:00")),
                null
        );
        doExpressionTest(entries, "", "time(\"10:30:00\").time offset",
                "PathExpression(DateTimeLiteral(time, \"10:30:00\"), time offset)",
                "days and time duration",
                "timeOffset(time(\"10:30:00\"))",
                this.lib.timeOffset(this.lib.time("10:30:00")),
                null
        );
        doExpressionTest(entries, "", "time(\"10:30:00@Etc/UTC\").timezone",
                "PathExpression(DateTimeLiteral(time, \"10:30:00@Etc/UTC\"), timezone)",
                "string",
                "timezone(time(\"10:30:00@Etc/UTC\"))",
                this.lib.timezone(this.lib.time("10:30:00@Etc/UTC")),
                "Etc/UTC"
        );
        doExpressionTest(entries, "", "time(\"10:30:00\").timezone",
                "PathExpression(DateTimeLiteral(time, \"10:30:00\"), timezone)",
                "string",
                "timezone(time(\"10:30:00\"))",
                this.lib.timezone(this.lib.time("10:30:00")),
                null
        );
    }

    @Test
    public void testDurationProperties() {
        List<EnvironmentEntry> entries = new ArrayList<>();

        // years and months duration
        doExpressionTest(entries, "", "duration(\"P10Y4M\").years",
                "PathExpression(DateTimeLiteral(duration, \"P10Y4M\"), years)",
                "number",
                "years(duration(\"P10Y4M\"))",
                this.lib.years(this.lib.duration("P10Y4M")),
                this.lib.number("10"));
        doExpressionTest(entries, "", "duration(\"P10Y4M\").months",
                "PathExpression(DateTimeLiteral(duration, \"P10Y4M\"), months)",
                "number",
                "months(duration(\"P10Y4M\"))",
                this.lib.months(this.lib.duration("P10Y4M")),
                this.lib.number("4"));

        // days and time duration
        doExpressionTest(entries, "", "duration(\"P1DT2H3M4S\").days",
                "PathExpression(DateTimeLiteral(duration, \"P1DT2H3M4S\"), days)",
                "number",
                "days(duration(\"P1DT2H3M4S\"))",
                this.lib.days(this.lib.duration("P1DT2H3M4S")),
                this.lib.number("1"));
        doExpressionTest(entries, "", "duration(\"P1DT2H3M4S\").hours",
                "PathExpression(DateTimeLiteral(duration, \"P1DT2H3M4S\"), hours)",
                "number",
                "hours(duration(\"P1DT2H3M4S\"))",
                this.lib.hours(this.lib.duration("P1DT2H3M4S")),
                this.lib.number("2"));
        doExpressionTest(entries, "", "duration(\"P1DT2H3M4S\").minutes",
                "PathExpression(DateTimeLiteral(duration, \"P1DT2H3M4S\"), minutes)",
                "number",
                "minutes(duration(\"P1DT2H3M4S\"))",
                this.lib.minutes(this.lib.duration("P1DT2H3M4S")),
                this.lib.number("3"));
        doExpressionTest(entries, "", "duration(\"P1DT2H3M4S\").seconds",
                "PathExpression(DateTimeLiteral(duration, \"P1DT2H3M4S\"), seconds)",
                "number",
                "seconds(duration(\"P1DT2H3M4S\"))",
                this.lib.seconds(this.lib.duration("P1DT2H3M4S")),
                this.lib.number("4"));

        // complex expressions
        doExpressionTest(entries, "", "(date(\"2012-03-01\") - date(\"2012-04-01\")).days",
                "PathExpression(Addition(-,DateTimeLiteral(date, \"2012-03-01\"),DateTimeLiteral(date, \"2012-04-01\")), days)",
                "number",
                "days(dateSubtract(date(\"2012-03-01\"), date(\"2012-04-01\")))",
                this.lib.days(this.lib.dateSubtract(this.lib.date("2012-03-01"), this.lib.date("2012-04-01"))),
                this.lib.number("-31"));
        doExpressionTest(entries, "", "(date(\"2012-03-01\") - date(\"2012-04-01\")).hours",
                "PathExpression(Addition(-,DateTimeLiteral(date, \"2012-03-01\"),DateTimeLiteral(date, \"2012-04-01\")), hours)",
                "number",
                "hours(dateSubtract(date(\"2012-03-01\"), date(\"2012-04-01\")))",
                this.lib.hours(this.lib.dateSubtract(this.lib.date("2012-03-01"), this.lib.date("2012-04-01"))),
                this.lib.number("0"));
        doExpressionTest(entries, "", "(date(\"2012-03-01\") - date(\"2012-04-01\")).minutes",
                "PathExpression(Addition(-,DateTimeLiteral(date, \"2012-03-01\"),DateTimeLiteral(date, \"2012-04-01\")), minutes)",
                "number",
                "minutes(dateSubtract(date(\"2012-03-01\"), date(\"2012-04-01\")))",
                this.lib.minutes(this.lib.dateSubtract(this.lib.date("2012-03-01"), this.lib.date("2012-04-01"))),
                this.lib.number("0"));
        doExpressionTest(entries, "", "(date(\"2012-03-01\") - date(\"2012-04-01\")).seconds",
                "PathExpression(Addition(-,DateTimeLiteral(date, \"2012-03-01\"),DateTimeLiteral(date, \"2012-04-01\")), seconds)",
                "number",
                "seconds(dateSubtract(date(\"2012-03-01\"), date(\"2012-04-01\")))",
                this.lib.seconds(this.lib.dateSubtract(this.lib.date("2012-03-01"), this.lib.date("2012-04-01"))),
                this.lib.number("0"));
    }

    @Test
    public void testDateTimeFunctions() {
        List<EnvironmentEntry> entries = new ArrayList<>();

        doExpressionTest(entries, "", "is(1, 1)",
                "FunctionInvocation(Name(is) -> PositionalParameters(NumericLiteral(1), NumericLiteral(1)))",
                "boolean",
                "is(number(\"1\"), number(\"1\"))",
                this.lib.is(this.lib.number("1"), this.lib.number("1")),
                true);
        doExpressionTest(entries, "", "is(@\"2012-12-25\", @\"23:00:50\")",
                "FunctionInvocation(Name(is) -> PositionalParameters(DateTimeLiteral(date, \"2012-12-25\"), DateTimeLiteral(time, \"23:00:50\")))",
                "boolean",
                "is(date(\"2012-12-25\"), time(\"23:00:50\"))",
                this.lib.is(this.lib.date("2012-12-25"), this.lib.time("23:00:50")),
                false);
        doExpressionTest(entries, "", "is(@\"2012-12-25\", @\"2012-12-25\")",
                "FunctionInvocation(Name(is) -> PositionalParameters(DateTimeLiteral(date, \"2012-12-25\"), DateTimeLiteral(date, \"2012-12-25\")))",
                "boolean",
                "is(date(\"2012-12-25\"), date(\"2012-12-25\"))",
                this.lib.is(this.lib.date("2012-12-25"), this.lib.date("2012-12-25")),
                true);
    }

    @Test
    public void testTemporalFunctions() {
        List<EnvironmentEntry> entries = new ArrayList<>();

        doExpressionTest(entries, "", "day of year(date(2019, 9, 17))",
                "FunctionInvocation(Name(day of year) -> PositionalParameters(FunctionInvocation(Name(date) -> PositionalParameters(NumericLiteral(2019), NumericLiteral(9), NumericLiteral(17)))))",
                "number",
                "dayOfYear(date(number(\"2019\"), number(\"9\"), number(\"17\")))",
                this.lib.dayOfYear(this.lib.date(this.lib.number("2019"), this.lib.number("9"), this.lib.number("17"))),
                this.lib.number("260"));
        doExpressionTest(entries, "", "day of week(date(2019, 9, 17))",
                "FunctionInvocation(Name(day of week) -> PositionalParameters(FunctionInvocation(Name(date) -> PositionalParameters(NumericLiteral(2019), NumericLiteral(9), NumericLiteral(17)))))",
                "string",
                "dayOfWeek(date(number(\"2019\"), number(\"9\"), number(\"17\")))",
                this.lib.dayOfWeek(this.lib.date(this.lib.number("2019"), this.lib.number("9"), this.lib.number("17"))),
                "Tuesday");
        doExpressionTest(entries, "", "month of year(date(2019, 9, 17))",
                "FunctionInvocation(Name(month of year) -> PositionalParameters(FunctionInvocation(Name(date) -> PositionalParameters(NumericLiteral(2019), NumericLiteral(9), NumericLiteral(17)))))",
                "string",
                "monthOfYear(date(number(\"2019\"), number(\"9\"), number(\"17\")))",
                this.lib.monthOfYear(this.lib.date(this.lib.number("2019"), this.lib.number("9"), this.lib.number("17"))),
                "September");
        doExpressionTest(entries, "", "week of year(date(2019, 9, 17))",
                "FunctionInvocation(Name(week of year) -> PositionalParameters(FunctionInvocation(Name(date) -> PositionalParameters(NumericLiteral(2019), NumericLiteral(9), NumericLiteral(17)))))",
                "number",
                "weekOfYear(date(number(\"2019\"), number(\"9\"), number(\"17\")))",
                this.lib.weekOfYear(this.lib.date(this.lib.number("2019"), this.lib.number("9"), this.lib.number("17"))),
                this.lib.number("38"));
    }

    @Test
    public void testRangeLiterals() {
        DATE input = this.lib.date("2010-10-02");
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", DATE, input));

        doExpressionTest(entries, "", "@\"2010-10-01\"",
                "DateTimeLiteral(date, \"2010-10-01\")",
                "date",
                "date(\"2010-10-01\")",
                this.lib.date("2010-10-01"),
                this.lib.date("2010-10-01"));

        doExpressionTest(entries, "", "@\"12:00:00\"",
                "DateTimeLiteral(time, \"12:00:00\")",
                "time",
                "time(\"12:00:00\")",
                this.lib.time("12:00:00"),
                this.lib.time("12:00:00"));

        doExpressionTest(entries, "", "@\"2010-10-01T12:00:00\"",
                "DateTimeLiteral(date and time, \"2010-10-01T12:00:00\")",
                "date and time",
                "dateAndTime(\"2010-10-01T12:00:00\")",
                this.lib.dateAndTime("2010-10-01T12:00:00"),
                this.lib.dateAndTime("2010-10-01T12:00:00"));

        doExpressionTest(entries, "", "@\"P10Y\"",
                "DateTimeLiteral(duration, \"P10Y\")",
                "years and months duration",
                "duration(\"P10Y\")",
                this.lib.duration("P10Y"),
                this.lib.duration("P10Y"));

        doExpressionTest(entries, "", "@\"\"",
                "DateTimeLiteral(date and time, \"\")",
                "date and time",
                "dateAndTime(\"\")",
                this.lib.dateAndTime(""),
                null);

        doUnaryTestsTest(entries, "input", "@\"2010-10-01\"",
                "PositiveUnaryTests(OperatorRange(null,DateTimeLiteral(date, \"2010-10-01\")))",
                "TupleType(boolean)",
                "(dateEqual(input, date(\"2010-10-01\")))",
                (this.lib.dateEqual(input, this.lib.date("2010-10-01"))),
                false);

        doUnaryTestsTest(entries, "input", "< @\"2010-10-01\"",
                "PositiveUnaryTests(OperatorRange(<,DateTimeLiteral(date, \"2010-10-01\")))",
                "TupleType(boolean)",
                "(dateLessThan(input, date(\"2010-10-01\")))",
                (this.lib.dateLessThan(input, this.lib.date("2010-10-01"))),
                false);
    }

    @Test
    public void testRangeFunctions() {
        List<EnvironmentEntry> entries = Arrays.asList(
        );

        doExpressionTest(entries, "", "before(1, [5..8])",
                "FunctionInvocation(Name(before) -> PositionalParameters(NumericLiteral(1), EndpointsRange(false,NumericLiteral(5),false,NumericLiteral(8))))",
                "boolean",
                "before(number(\"1\"), new com.gs.dmn.runtime.Range(true, number(\"5\"), true, number(\"8\")))",
                this.lib.before(this.lib.number("1"), new com.gs.dmn.runtime.Range(true, this.lib.number("5"), true, this.lib.number("8"))),
                true);
        doExpressionTest(entries, "", "before(\"1\", [\"5\"..\"8\"])",
                "FunctionInvocation(Name(before) -> PositionalParameters(StringLiteral(\"1\"), EndpointsRange(false,StringLiteral(\"5\"),false,StringLiteral(\"8\"))))",
                "boolean",
                "before(\"1\", new com.gs.dmn.runtime.Range(true, \"5\", true, \"8\"))",
                this.lib.before("1", new com.gs.dmn.runtime.Range(true, "5", true, "8")),
                true);
        doExpressionTest(entries, "", "before(@\"2010-10-01\", [@\"2010-10-02\"..@\"2010-10-03\"])",
                "FunctionInvocation(Name(before) -> PositionalParameters(DateTimeLiteral(date, \"2010-10-01\"), EndpointsRange(false,DateTimeLiteral(date, \"2010-10-02\"),false,DateTimeLiteral(date, \"2010-10-03\"))))",
                "boolean",
                "before(date(\"2010-10-01\"), new com.gs.dmn.runtime.Range(true, date(\"2010-10-02\"), true, date(\"2010-10-03\")))",
                this.lib.before(this.lib.date("2010-10-01"), new com.gs.dmn.runtime.Range(true, this.lib.date("2010-10-02"), true, this.lib.date("2010-10-03"))),
                true);
        doExpressionTest(entries, "", "before(@\"12:00:00\", [@\"12:00:02\"..@\"12:00:03\"])",
                "FunctionInvocation(Name(before) -> PositionalParameters(DateTimeLiteral(time, \"12:00:00\"), EndpointsRange(false,DateTimeLiteral(time, \"12:00:02\"),false,DateTimeLiteral(time, \"12:00:03\"))))",
                "boolean",
                "before(time(\"12:00:00\"), new com.gs.dmn.runtime.Range(true, time(\"12:00:02\"), true, time(\"12:00:03\")))",
                this.lib.before(this.lib.time("12:00:00"), new com.gs.dmn.runtime.Range(true, this.lib.time("12:00:02"), true, this.lib.time("12:00:03"))),
                true);
        doExpressionTest(entries, "", "before(@\"2010-10-01T12:00:00\", [@\"2010-10-02T12:00:00\"..@\"2010-10-03T12:00:00\"])",
                "FunctionInvocation(Name(before) -> PositionalParameters(DateTimeLiteral(date and time, \"2010-10-01T12:00:00\"), EndpointsRange(false,DateTimeLiteral(date and time, \"2010-10-02T12:00:00\"),false,DateTimeLiteral(date and time, \"2010-10-03T12:00:00\"))))",
                "boolean",
                "before(dateAndTime(\"2010-10-01T12:00:00\"), new com.gs.dmn.runtime.Range(true, dateAndTime(\"2010-10-02T12:00:00\"), true, dateAndTime(\"2010-10-03T12:00:00\")))",
                this.lib.before(this.lib.dateAndTime("2010-10-01T12:00:00"), new com.gs.dmn.runtime.Range(true, this.lib.dateAndTime("2010-10-02T12:00:00"), true, this.lib.dateAndTime("2010-10-03T12:00:00"))),
                true);
        doExpressionTest(entries, "", "before(@\"P10Y\", [@\"P12Y\"..@\"P13Y\"])",
                "FunctionInvocation(Name(before) -> PositionalParameters(DateTimeLiteral(duration, \"P10Y\"), EndpointsRange(false,DateTimeLiteral(duration, \"P12Y\"),false,DateTimeLiteral(duration, \"P13Y\"))))",
                "boolean",
                "before(duration(\"P10Y\"), new com.gs.dmn.runtime.Range(true, duration(\"P12Y\"), true, duration(\"P13Y\")))",
                this.lib.before(this.lib.duration("P10Y"), new com.gs.dmn.runtime.Range(true, this.lib.duration("P12Y"), true, this.lib.duration("P13Y"))),
                true);

        doExpressionTest(entries, "", "after(1, 5)",
                "FunctionInvocation(Name(after) -> PositionalParameters(NumericLiteral(1), NumericLiteral(5)))",
                "boolean",
                "after(number(\"1\"), number(\"5\"))",
                this.lib.after(this.lib.number("1"), this.lib.number("5")),
                false);
        doExpressionTest(entries, "", "after(1, [5..8])",
                "FunctionInvocation(Name(after) -> PositionalParameters(NumericLiteral(1), EndpointsRange(false,NumericLiteral(5),false,NumericLiteral(8))))",
                "boolean",
                "after(number(\"1\"), new com.gs.dmn.runtime.Range(true, number(\"5\"), true, number(\"8\")))",
                this.lib.after(this.lib.number("1"), new com.gs.dmn.runtime.Range(true, this.lib.number("5"), true, this.lib.number("8"))),
                false);
        doExpressionTest(entries, "", "after([5..8], 1)",
                "FunctionInvocation(Name(after) -> PositionalParameters(EndpointsRange(false,NumericLiteral(5),false,NumericLiteral(8)), NumericLiteral(1)))",
                "boolean",
                "after(new com.gs.dmn.runtime.Range(true, number(\"5\"), true, number(\"8\")), number(\"1\"))",
                this.lib.after(new com.gs.dmn.runtime.Range(true, this.lib.number("5"), true, this.lib.number("8")), this.lib.number("1")),
                true);
        doExpressionTest(entries, "", "after([1..2], [5..8])",
                "FunctionInvocation(Name(after) -> PositionalParameters(EndpointsRange(false,NumericLiteral(1),false,NumericLiteral(2)), EndpointsRange(false,NumericLiteral(5),false,NumericLiteral(8))))",
                "boolean",
                "after(new com.gs.dmn.runtime.Range(true, number(\"1\"), true, number(\"2\")), new com.gs.dmn.runtime.Range(true, number(\"5\"), true, number(\"8\")))",
                this.lib.after(new com.gs.dmn.runtime.Range(true, this.lib.number("1"), true, this.lib.number("2")), new com.gs.dmn.runtime.Range(true, this.lib.number("5"), true, this.lib.number("8"))),
                false);

        doExpressionTest(entries, "", "meets([1..2], [5..8])",
                "FunctionInvocation(Name(meets) -> PositionalParameters(EndpointsRange(false,NumericLiteral(1),false,NumericLiteral(2)), EndpointsRange(false,NumericLiteral(5),false,NumericLiteral(8))))",
                "boolean",
                "meets(new com.gs.dmn.runtime.Range(true, number(\"1\"), true, number(\"2\")), new com.gs.dmn.runtime.Range(true, number(\"5\"), true, number(\"8\")))",
                this.lib.meets(new com.gs.dmn.runtime.Range(true, this.lib.number("1"), true, this.lib.number("2")), new com.gs.dmn.runtime.Range(true, this.lib.number("5"), true, this.lib.number("8"))),
                false);

        doExpressionTest(entries, "", "met by([1..2], [5..8])",
                "FunctionInvocation(Name(met by) -> PositionalParameters(EndpointsRange(false,NumericLiteral(1),false,NumericLiteral(2)), EndpointsRange(false,NumericLiteral(5),false,NumericLiteral(8))))",
                "boolean",
                "metBy(new com.gs.dmn.runtime.Range(true, number(\"1\"), true, number(\"2\")), new com.gs.dmn.runtime.Range(true, number(\"5\"), true, number(\"8\")))",
                this.lib.metBy(new com.gs.dmn.runtime.Range(true, this.lib.number("1"), true, this.lib.number("2")), new com.gs.dmn.runtime.Range(true, this.lib.number("5"), true, this.lib.number("8"))),
                false);

        doExpressionTest(entries, "", "overlaps([1..2], [5..8])",
                "FunctionInvocation(Name(overlaps) -> PositionalParameters(EndpointsRange(false,NumericLiteral(1),false,NumericLiteral(2)), EndpointsRange(false,NumericLiteral(5),false,NumericLiteral(8))))",
                "boolean",
                "overlaps(new com.gs.dmn.runtime.Range(true, number(\"1\"), true, number(\"2\")), new com.gs.dmn.runtime.Range(true, number(\"5\"), true, number(\"8\")))",
                this.lib.overlaps(new com.gs.dmn.runtime.Range(true, this.lib.number("1"), true, this.lib.number("2")), new com.gs.dmn.runtime.Range(true, this.lib.number("5"), true, this.lib.number("8"))),
                false);
        doExpressionTest(entries, "", "overlaps before([1..2], [5..8])",
                "FunctionInvocation(Name(overlaps before) -> PositionalParameters(EndpointsRange(false,NumericLiteral(1),false,NumericLiteral(2)), EndpointsRange(false,NumericLiteral(5),false,NumericLiteral(8))))",
                "boolean",
                "overlapsBefore(new com.gs.dmn.runtime.Range(true, number(\"1\"), true, number(\"2\")), new com.gs.dmn.runtime.Range(true, number(\"5\"), true, number(\"8\")))",
                this.lib.overlapsBefore(new com.gs.dmn.runtime.Range(true, this.lib.number("1"), true, this.lib.number("2")), new com.gs.dmn.runtime.Range(true, this.lib.number("5"), true, this.lib.number("8"))),
                false);
        doExpressionTest(entries, "", "overlaps after([1..2], [5..8])",
                "FunctionInvocation(Name(overlaps after) -> PositionalParameters(EndpointsRange(false,NumericLiteral(1),false,NumericLiteral(2)), EndpointsRange(false,NumericLiteral(5),false,NumericLiteral(8))))",
                "boolean",
                "overlapsAfter(new com.gs.dmn.runtime.Range(true, number(\"1\"), true, number(\"2\")), new com.gs.dmn.runtime.Range(true, number(\"5\"), true, number(\"8\")))",
                this.lib.overlapsAfter(new com.gs.dmn.runtime.Range(true, this.lib.number("1"), true, this.lib.number("2")), new com.gs.dmn.runtime.Range(true, this.lib.number("5"), true, this.lib.number("8"))),
                false);

        doExpressionTest(entries, "", "finishes(1, [5..8])",
                "FunctionInvocation(Name(finishes) -> PositionalParameters(NumericLiteral(1), EndpointsRange(false,NumericLiteral(5),false,NumericLiteral(8))))",
                "boolean",
                "finishes(number(\"1\"), new com.gs.dmn.runtime.Range(true, number(\"5\"), true, number(\"8\")))",
                this.lib.finishes(this.lib.number("1"), new com.gs.dmn.runtime.Range(true, this.lib.number("5"), true, this.lib.number("8"))),
                false);
        doExpressionTest(entries, "", "finishes([1..2], [5..8])",
                "FunctionInvocation(Name(finishes) -> PositionalParameters(EndpointsRange(false,NumericLiteral(1),false,NumericLiteral(2)), EndpointsRange(false,NumericLiteral(5),false,NumericLiteral(8))))",
                "boolean",
                "finishes(new com.gs.dmn.runtime.Range(true, number(\"1\"), true, number(\"2\")), new com.gs.dmn.runtime.Range(true, number(\"5\"), true, number(\"8\")))",
                this.lib.finishes(new com.gs.dmn.runtime.Range(true, this.lib.number("1"), true, this.lib.number("2")), new com.gs.dmn.runtime.Range(true, this.lib.number("5"), true, this.lib.number("8"))),
                false);

        doExpressionTest(entries, "", "finished by([5..8], 1)",
                "FunctionInvocation(Name(finished by) -> PositionalParameters(EndpointsRange(false,NumericLiteral(5),false,NumericLiteral(8)), NumericLiteral(1)))",
                "boolean",
                "finishedBy(new com.gs.dmn.runtime.Range(true, number(\"5\"), true, number(\"8\")), number(\"1\"))",
                this.lib.finishedBy(new com.gs.dmn.runtime.Range(true, this.lib.number("5"), true, this.lib.number("8")), this.lib.number("1")),
                false);
        doExpressionTest(entries, "", "finished by([1..2], [5..8])",
                "FunctionInvocation(Name(finished by) -> PositionalParameters(EndpointsRange(false,NumericLiteral(1),false,NumericLiteral(2)), EndpointsRange(false,NumericLiteral(5),false,NumericLiteral(8))))",
                "boolean",
                "finishedBy(new com.gs.dmn.runtime.Range(true, number(\"1\"), true, number(\"2\")), new com.gs.dmn.runtime.Range(true, number(\"5\"), true, number(\"8\")))",
                this.lib.finishedBy(new com.gs.dmn.runtime.Range(true, this.lib.number("1"), true, this.lib.number("2")), new com.gs.dmn.runtime.Range(true, this.lib.number("5"), true, this.lib.number("8"))),
                false);

        doExpressionTest(entries, "", "includes([5..8], 1)",
                "FunctionInvocation(Name(includes) -> PositionalParameters(EndpointsRange(false,NumericLiteral(5),false,NumericLiteral(8)), NumericLiteral(1)))",
                "boolean",
                "includes(new com.gs.dmn.runtime.Range(true, number(\"5\"), true, number(\"8\")), number(\"1\"))",
                this.lib.includes(new com.gs.dmn.runtime.Range(true, this.lib.number("5"), true, this.lib.number("8")), this.lib.number("1")),
                false);
        doExpressionTest(entries, "", "includes([1..2], [5..8])",
                "FunctionInvocation(Name(includes) -> PositionalParameters(EndpointsRange(false,NumericLiteral(1),false,NumericLiteral(2)), EndpointsRange(false,NumericLiteral(5),false,NumericLiteral(8))))",
                "boolean",
                "includes(new com.gs.dmn.runtime.Range(true, number(\"1\"), true, number(\"2\")), new com.gs.dmn.runtime.Range(true, number(\"5\"), true, number(\"8\")))",
                this.lib.includes(new com.gs.dmn.runtime.Range(true, this.lib.number("1"), true, this.lib.number("2")), new com.gs.dmn.runtime.Range(true, this.lib.number("5"), true, this.lib.number("8"))),
                false);

        doExpressionTest(entries, "", "during(1, [5..8])",
                "FunctionInvocation(Name(during) -> PositionalParameters(NumericLiteral(1), EndpointsRange(false,NumericLiteral(5),false,NumericLiteral(8))))",
                "boolean",
                "during(number(\"1\"), new com.gs.dmn.runtime.Range(true, number(\"5\"), true, number(\"8\")))",
                this.lib.during(this.lib.number("1"), new com.gs.dmn.runtime.Range(true, this.lib.number("5"), true, this.lib.number("8"))),
                false);
        doExpressionTest(entries, "", "during([1..2], [5..8])",
                "FunctionInvocation(Name(during) -> PositionalParameters(EndpointsRange(false,NumericLiteral(1),false,NumericLiteral(2)), EndpointsRange(false,NumericLiteral(5),false,NumericLiteral(8))))",
                "boolean",
                "during(new com.gs.dmn.runtime.Range(true, number(\"1\"), true, number(\"2\")), new com.gs.dmn.runtime.Range(true, number(\"5\"), true, number(\"8\")))",
                this.lib.during(new com.gs.dmn.runtime.Range(true, this.lib.number("1"), true, this.lib.number("2")), new com.gs.dmn.runtime.Range(true, this.lib.number("5"), true, this.lib.number("8"))),
                false);

        doExpressionTest(entries, "", "starts(1, [5..8])",
                "FunctionInvocation(Name(starts) -> PositionalParameters(NumericLiteral(1), EndpointsRange(false,NumericLiteral(5),false,NumericLiteral(8))))",
                "boolean",
                "starts(number(\"1\"), new com.gs.dmn.runtime.Range(true, number(\"5\"), true, number(\"8\")))",
                this.lib.starts(this.lib.number("1"), new com.gs.dmn.runtime.Range(true, this.lib.number("5"), true, this.lib.number("8"))),
                false);
        doExpressionTest(entries, "", "starts([1..2], [5..8])",
                "FunctionInvocation(Name(starts) -> PositionalParameters(EndpointsRange(false,NumericLiteral(1),false,NumericLiteral(2)), EndpointsRange(false,NumericLiteral(5),false,NumericLiteral(8))))",
                "boolean",
                "starts(new com.gs.dmn.runtime.Range(true, number(\"1\"), true, number(\"2\")), new com.gs.dmn.runtime.Range(true, number(\"5\"), true, number(\"8\")))",
                this.lib.starts(new com.gs.dmn.runtime.Range(true, this.lib.number("1"), true, this.lib.number("2")), new com.gs.dmn.runtime.Range(true, this.lib.number("5"), true, this.lib.number("8"))),
                false);

        doExpressionTest(entries, "", "started by([5..8], 1)",
                "FunctionInvocation(Name(started by) -> PositionalParameters(EndpointsRange(false,NumericLiteral(5),false,NumericLiteral(8)), NumericLiteral(1)))",
                "boolean",
                "startedBy(new com.gs.dmn.runtime.Range(true, number(\"5\"), true, number(\"8\")), number(\"1\"))",
                this.lib.startedBy(new com.gs.dmn.runtime.Range(true, this.lib.number("5"), true, this.lib.number("8")), this.lib.number("1")),
                false);
        doExpressionTest(entries, "", "started by([1..2], [5..8])",
                "FunctionInvocation(Name(started by) -> PositionalParameters(EndpointsRange(false,NumericLiteral(1),false,NumericLiteral(2)), EndpointsRange(false,NumericLiteral(5),false,NumericLiteral(8))))",
                "boolean",
                "startedBy(new com.gs.dmn.runtime.Range(true, number(\"1\"), true, number(\"2\")), new com.gs.dmn.runtime.Range(true, number(\"5\"), true, number(\"8\")))",
                this.lib.startedBy(new com.gs.dmn.runtime.Range(true, this.lib.number("1"), true, this.lib.number("2")), new com.gs.dmn.runtime.Range(true, this.lib.number("5"), true, this.lib.number("8"))),
                false);

        doExpressionTest(entries, "", "coincides(1, 5)",
                "FunctionInvocation(Name(coincides) -> PositionalParameters(NumericLiteral(1), NumericLiteral(5)))",
                "boolean",
                "coincides(number(\"1\"), number(\"5\"))",
                this.lib.coincides(this.lib.number("1"), this.lib.number("5")),
                false);
        doExpressionTest(entries, "", "coincides([1..2], [5..8])",
                "FunctionInvocation(Name(coincides) -> PositionalParameters(EndpointsRange(false,NumericLiteral(1),false,NumericLiteral(2)), EndpointsRange(false,NumericLiteral(5),false,NumericLiteral(8))))",
                "boolean",
                "coincides(new com.gs.dmn.runtime.Range(true, number(\"1\"), true, number(\"2\")), new com.gs.dmn.runtime.Range(true, number(\"5\"), true, number(\"8\")))",
                this.lib.coincides(new com.gs.dmn.runtime.Range(true, this.lib.number("1"), true, this.lib.number("2")), new com.gs.dmn.runtime.Range(true, this.lib.number("5"), true, this.lib.number("8"))),
                false);
    }

    @Test
    public void testRangeProperties() {
        List<EnvironmentEntry> entries = new ArrayList<>();

        //
        // EndpointsRange
        //
        doExpressionTest(entries, "", "[5..8].start",
                "PathExpression(EndpointsRange(false,NumericLiteral(5),false,NumericLiteral(8)), start)",
                "number",
                "new com.gs.dmn.runtime.Range(true, number(\"5\"), true, number(\"8\")).getStart()",
                (new com.gs.dmn.runtime.Range(true, this.lib.number("5"), true, this.lib.number("8"))).getStart(),
                this.lib.number("5"));

        doExpressionTest(entries, "", "[5..8].end",
                "PathExpression(EndpointsRange(false,NumericLiteral(5),false,NumericLiteral(8)), end)",
                "number",
                "new com.gs.dmn.runtime.Range(true, number(\"5\"), true, number(\"8\")).getEnd()",
                new com.gs.dmn.runtime.Range(true, this.lib.number("5"), true, this.lib.number("8")).getEnd(),
                this.lib.number("8"));

        doExpressionTest(entries, "", "[5..8].start included",
                "PathExpression(EndpointsRange(false,NumericLiteral(5),false,NumericLiteral(8)), start included)",
                "boolean",
                "new com.gs.dmn.runtime.Range(true, number(\"5\"), true, number(\"8\")).isStartIncluded()",
                new com.gs.dmn.runtime.Range(true, this.lib.number("5"), true, this.lib.number("8")).isStartIncluded(),
                true);

        doExpressionTest(entries, "", "[5..8).end included",
                "PathExpression(EndpointsRange(false,NumericLiteral(5),true,NumericLiteral(8)), end included)",
                "boolean",
                "new com.gs.dmn.runtime.Range(true, number(\"5\"), false, number(\"8\")).isEndIncluded()",
                new com.gs.dmn.runtime.Range(true, this.lib.number("5"), false, this.lib.number("8")).isEndIncluded(),
                false);

        //
        // OperatorRange
        //
        doExpressionTest(entries, "", "(< 10).start",
                "PathExpression(OperatorRange(<,NumericLiteral(10)), start)",
                "number",
                "new com.gs.dmn.runtime.Range(false, null, false, number(\"10\")).getStart()",
                new com.gs.dmn.runtime.Range(false, null, false, lib.number("10")).getStart(),
                null);

        doExpressionTest(entries, "", "(<= 10).end",
                "PathExpression(OperatorRange(<=,NumericLiteral(10)), end)",
                "number",
                "new com.gs.dmn.runtime.Range(false, null, true, number(\"10\")).getEnd()",
                new com.gs.dmn.runtime.Range(false, null, true, lib.number("10")).getEnd(),
                this.lib.number("10"));

        doExpressionTest(entries, "", "(< 10).start included",
                "PathExpression(OperatorRange(<,NumericLiteral(10)), start included)",
                "boolean",
                "new com.gs.dmn.runtime.Range(false, null, false, number(\"10\")).isStartIncluded()",
                new com.gs.dmn.runtime.Range(false, null, false, lib.number("10")).isStartIncluded(),
                false);

        doExpressionTest(entries, "", "(> 10).end included",
                "PathExpression(OperatorRange(>,NumericLiteral(10)), end included)",
                "boolean",
                "new com.gs.dmn.runtime.Range(false, number(\"10\"), false, null).isEndIncluded()",
                new com.gs.dmn.runtime.Range(false, lib.number("10"), false, null).isEndIncluded(),
                false);
    }
}