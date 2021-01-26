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
import com.gs.dmn.feel.analysis.semantics.type.ItemDefinitionType;
import com.gs.dmn.feel.lib.StandardFEELLib;
import org.junit.Test;
import org.omg.dmn.tck.marshaller._20160719.TestCases;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        DATE date = this.lib.date("2015-01-01");
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("date", DATE, date));

//        doUnaryTestsTest(entries, "date", "<= date(date(\"2020-01-01\") - duration(\"P5Y\"))",
//                "PositiveUnaryTests(ExpressionTest(Relational(<=,Name(?),FunctionInvocation(Name(date) -> PositionalParameters(Addition(-,DateTimeLiteral(date, \"2020-01-01\"),DateTimeLiteral(duration, \"P5Y\")))))))",
//                "TupleType(boolean)",
//                "(dateLessEqualThan(date, date(dateSubtractDuration(date(\"2020-01-01\"), duration(\"P5Y\")))))",
//                (lib.dateLessEqualThan(date, lib.date((DATE_TIME) lib.dateSubtractDuration(lib.date("2020-01-01"), lib.duration("P5Y"))))),
//                true);
//        doUnaryTestsTest(entries, "date", "<= date(\"2020-01-01\") - duration(\"P5Y\")",
//                "PositiveUnaryTests(ExpressionTest(Relational(<=,Name(?),FunctionInvocation(Name(date) -> PositionalParameters(Addition(-,DateTimeLiteral(date, \"2020-01-01\"),DateTimeLiteral(duration, \"P5Y\")))))))",
//                "TupleType(boolean)",
//                "(dateLessEqualThan(date, date(dateSubtractDuration(date(\"2020-01-01\"), duration(\"P5Y\")))))",
//                (lib.dateLessEqualThan(date, lib.date((DATE_TIME) lib.dateSubtractDuration(lib.date("2020-01-01"), lib.duration("P5Y"))))),
//                true);
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
        doUnaryTestsTest(entries, "date", "<= date(\"2020-01-01\")",
                "PositiveUnaryTests(OperatorRange(<=,DateTimeLiteral(date, \"2020-01-01\")))",
                "TupleType(boolean)",
                "(dateLessEqualThan(date, date(\"2020-01-01\")))",
                (this.lib.dateLessEqualThan(date, this.lib.date("2020-01-01"))),
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
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", STRING, input));

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

        doExpressionTest(entries, "", "contains(string : \"abc\", match : \"a\")",
                "FunctionInvocation(Name(contains) -> NamedParameters(string : StringLiteral(\"abc\"), match : StringLiteral(\"a\")))",
                "boolean",
                null,
                null,
                null);
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
    }

    @Override
    @Test
    public void testConversionFunctions() {
        super.testConversionFunctions();
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, this.lib.number("1")));

        doExpressionTest(entries, "", "number(\"1 000\", \" \", \".\")",
                "FunctionInvocation(Name(number) -> PositionalParameters(StringLiteral(\"1 000\"), StringLiteral(\" \"), StringLiteral(\".\")))",
                "number",
                "number(\"1 000\", \" \", \".\")",
                this.lib.number("1 000", " ", "."),
                this.lib.number("1000"));
        doExpressionTest(entries, "", "number(from: \"1.000.000,01\", 'decimal separator':\",\", 'grouping separator':\".\")",
                "FunctionInvocation(Name(number) -> NamedParameters(from : StringLiteral(\"1.000.000,01\"), 'decimal separator' : StringLiteral(\",\"), 'grouping separator' : StringLiteral(\".\")))",
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
    }

    @Test
    public void testNumericFunctions() {
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, this.lib.number("1")));

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
        doExpressionTest(entries, "", "floor(100)",
                "FunctionInvocation(Name(floor) -> PositionalParameters(NumericLiteral(100)))",
                "number",
                "floor(number(\"100\"))",
                this.lib.floor(this.lib.number("100")),
                this.lib.number("100"));
        doExpressionTest(entries, "", "ceiling(100)",
                "FunctionInvocation(Name(ceiling) -> PositionalParameters(NumericLiteral(100)))",
                "number",
                "ceiling(number(\"100\"))",
                this.lib.ceiling(this.lib.number("100")),
                this.lib.number("100"));
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
    }

    @Test
    public void testStringFunctions() {
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, this.lib.number("1")));

        doExpressionTest(entries, "", "substring(\"abc\", 3)",
                "FunctionInvocation(Name(substring) -> PositionalParameters(StringLiteral(\"abc\"), NumericLiteral(3)))",
                "string",
                "substring(\"abc\", number(\"3\"))",
                this.lib.substring("abc", this.lib.number("3")),
                "c");
        doExpressionTest(entries, "", "split(\"John Doe\", \"\\s\")",
                "FunctionInvocation(Name(split) -> PositionalParameters(StringLiteral(\"John Doe\"), StringLiteral(\"\\s\")))",
                "ListType(string)",
                "split(\"John Doe\", \"\\\\s\")",
                this.lib.split("John Doe", "\\s"),
                this.lib.asList("John", "Doe"));
        doExpressionTest(entries, "", "substring(string: \"abc\", 'start position': 3)",
                "FunctionInvocation(Name(substring) -> NamedParameters(string : StringLiteral(\"abc\"), 'start position' : NumericLiteral(3)))",
                "string",
                "substring(\"abc\", number(\"3\"))",
                this.lib.substring("abc", this.lib.number("3")),
                "c");
        doExpressionTest(entries, "", "substring(string: \"abc\", startPosition: 3)",
                "FunctionInvocation(Name(substring) -> NamedParameters(string : StringLiteral(\"abc\"), startPosition : NumericLiteral(3)))",
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
        doExpressionTest(entries, "", "max([1, 2, 3])",
                "FunctionInvocation(Name(max) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3))))",
                "number",
                "max(asList(number(\"1\"), number(\"2\"), number(\"3\")))",
                this.lib.max(Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3"))),
                this.lib.number("3"));
        doExpressionTest(entries, "", "sum([1, 2, 3])",
                "FunctionInvocation(Name(sum) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3))))",
                "number",
                "sum(asList(number(\"1\"), number(\"2\"), number(\"3\")))",
                this.lib.sum(Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3"))),
                this.lib.number("6"));
        doExpressionTest(entries, "", "mean([1, 2, 3])",
                "FunctionInvocation(Name(mean) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3))))",
                "number",
                "mean(asList(number(\"1\"), number(\"2\"), number(\"3\")))",
                this.lib.mean(Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3"))),
                this.lib.number("2"));
        doExpressionTest(entries, "", "sublist([1, 2, 3], 2, 1)",
                "FunctionInvocation(Name(sublist) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), NumericLiteral(2), NumericLiteral(1)))",
                "ListType(Any)",
                "sublist(asList(number(\"1\"), number(\"2\"), number(\"3\")), number(\"2\"), number(\"1\"))",
                this.lib.sublist(Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3")), this.lib.number("2"), this.lib.number("1")),
                Arrays.asList(this.lib.number("2")));
        doExpressionTest(entries, "", "sublist([1, 2, 3], 2)",
                "FunctionInvocation(Name(sublist) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), NumericLiteral(2)))",
                "ListType(Any)",
                "sublist(asList(number(\"1\"), number(\"2\"), number(\"3\")), number(\"2\"))",
                this.lib.sublist(Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3")), this.lib.number("2")),
                Arrays.asList(this.lib.number("2"), this.lib.number("3")));
        doExpressionTest(entries, "", "append([1, 2, 3], 4)",
                "FunctionInvocation(Name(append) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), NumericLiteral(4)))",
                "ListType(Any)",
                "append(asList(number(\"1\"), number(\"2\"), number(\"3\")), number(\"4\"))",
                this.lib.append(Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3")), this.lib.number("4")),
                Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3"), this.lib.number("4")));

        doExpressionTest(entries, "", "concatenate([1, 2, 3], [4, 5, 6])",
                "FunctionInvocation(Name(concatenate) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), ListLiteral(NumericLiteral(4),NumericLiteral(5),NumericLiteral(6))))",
                "ListType(Any)",
                "concatenate(asList(number(\"1\"), number(\"2\"), number(\"3\")), asList(number(\"4\"), number(\"5\"), number(\"6\")))",
                this.lib.concatenate(Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3")), Arrays.asList(this.lib.number("4"), this.lib.number("5"), this.lib.number("6"))),
                Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3"), this.lib.number("4"), this.lib.number("5"), this.lib.number("6")));
        doExpressionTest(entries, "", "insert before([1, 2, 3], 1, 4)",
                "FunctionInvocation(Name(insert before) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), NumericLiteral(1), NumericLiteral(4)))",
                "ListType(Any)",
                "insertBefore(asList(number(\"1\"), number(\"2\"), number(\"3\")), number(\"1\"), number(\"4\"))",
                this.lib.insertBefore(Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3")), this.lib.number("1"), this.lib.number("4")),
                Arrays.asList(this.lib.number("4"), this.lib.number("1"), this.lib.number("2"), this.lib.number("3")));
        doExpressionTest(entries, "", "remove([1, 2, 3], 1)",
                "FunctionInvocation(Name(remove) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), NumericLiteral(1)))",
                "ListType(Any)",
                "remove(asList(number(\"1\"), number(\"2\"), number(\"3\")), number(\"1\"))",
                this.lib.remove(Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3")), this.lib.number("1")),
                Arrays.asList(this.lib.number("2"), this.lib.number("3")));
        doExpressionTest(entries, "", "reverse([1, 2, 3])",
                "FunctionInvocation(Name(reverse) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3))))",
                "ListType(Any)",
                "reverse(asList(number(\"1\"), number(\"2\"), number(\"3\")))",
                this.lib.reverse(Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3"))),
                Arrays.asList(this.lib.number("3"), this.lib.number("2"), this.lib.number("1")));
        doExpressionTest(entries, "", "index of([1, 2, 3], 3)",
                "FunctionInvocation(Name(index of) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), NumericLiteral(3)))",
                "ListType(Any)",
                "indexOf(asList(number(\"1\"), number(\"2\"), number(\"3\")), number(\"3\"))",
                this.lib.indexOf(Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3")), this.lib.number("3")),
                Arrays.asList(this.lib.number("3")));
        doExpressionTest(entries, "", "union([1, 2, 3], [4, 5, 6])",
                "FunctionInvocation(Name(union) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), ListLiteral(NumericLiteral(4),NumericLiteral(5),NumericLiteral(6))))",
                "ListType(Any)",
                "union(asList(number(\"1\"), number(\"2\"), number(\"3\")), asList(number(\"4\"), number(\"5\"), number(\"6\")))",
                this.lib.union(Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3")), Arrays.asList(this.lib.number("4"), this.lib.number("5"), this.lib.number("6"))),
                Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3"), this.lib.number("4"), this.lib.number("5"), this.lib.number("6")));
        doExpressionTest(entries, "", "distinct values([1, 2, 3])",
                "FunctionInvocation(Name(distinct values) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3))))",
                "ListType(Any)",
                "distinctValues(asList(number(\"1\"), number(\"2\"), number(\"3\")))",
                this.lib.distinctValues(Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3"))),
                Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3")));
        doExpressionTest(entries, "", "flatten([1, 2, 3])",
                "FunctionInvocation(Name(flatten) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3))))",
                "ListType(Any)",
                "flatten(asList(number(\"1\"), number(\"2\"), number(\"3\")))",
                this.lib.flatten(Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3"))),
                Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3")));
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
                this.lib.duration("P0Y0M0DT0H0M0.000S"));
    }

    @Test
    public void testDurationProperties() {
        List<EnvironmentEntry> entries = new ArrayList<>();

        doExpressionTest(entries, "", "duration(\"P10Y4M\").years",
                "PathExpression(DateTimeLiteral(duration, \"P10Y4M\"), years)",
                "number",
                "years(duration(\"P10Y4M\"))",
                this.lib.years(this.lib.duration("P10Y4M")),
                this.lib.number("10"));
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
        doExpressionTest(entries, "", "is(@\"23:00:50Z\", @\"23:00:50\")",
                "FunctionInvocation(Name(is) -> PositionalParameters(DateTimeLiteral(time, \"23:00:50Z\"), DateTimeLiteral(time, \"23:00:50\")))",
                "boolean",
                "is(time(\"23:00:50Z\"), time(\"23:00:50\"))",
                this.lib.is(this.lib.time("23:00:50Z"), this.lib.time("23:00:50")),
                false);
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
        DATE input = this.lib.date("2010-10-02");
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", DATE, input));

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
    }

    @Test
    public void testSortInvocation() {
        List<EnvironmentEntry> entries = Arrays.asList(
        );

        doExpressionTest(entries, "", "sort([3,1,4,5,2], function(x: feel.number, y: feel.number) x < y)",
                "FunctionInvocation(Name(sort) -> PositionalParameters(ListLiteral(NumericLiteral(3),NumericLiteral(1),NumericLiteral(4),NumericLiteral(5),NumericLiteral(2)), FunctionDefinition(FormalParameter(x, number),FormalParameter(y, number), Relational(<,Name(x),Name(y)), false)))",
                "ListType(Any)",
                "sort(asList(number(\"3\"), number(\"1\"), number(\"4\"), number(\"5\"), number(\"2\")), new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args) {java.math.BigDecimal x = (java.math.BigDecimal)args[0]; java.math.BigDecimal y = (java.math.BigDecimal)args[1];return numericLessThan(x, y);}})",
                null,
                null
        );
    }
}