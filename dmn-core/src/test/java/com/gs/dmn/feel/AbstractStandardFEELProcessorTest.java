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

        DATE date = lib.date("2015-01-01");
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
                (lib.dateLessEqualThan(date, lib.date(lib.dateSubtractDuration(lib.date("2020-01-01"), lib.duration("P5Y"))))),
                true);
        doUnaryTestsTest(entries, "date", "? <= date(\"2020-01-01\") - duration(\"P5Y\")",
                "PositiveUnaryTests(ExpressionTest(Relational(<=,Name(?),Addition(-,DateTimeLiteral(date, \"2020-01-01\"),DateTimeLiteral(duration, \"P5Y\")))))",
                "TupleType(boolean)",
                "(dateLessEqualThan(date, dateSubtractDuration(date(\"2020-01-01\"), duration(\"P5Y\"))))",
                (lib.dateLessEqualThan(date, lib.dateSubtractDuration(lib.date("2020-01-01"), lib.duration("P5Y")))),
                true);
        doUnaryTestsTest(entries, "date", "<= date(\"2020-01-01\")",
                "PositiveUnaryTests(OperatorTest(<=,DateTimeLiteral(date, \"2020-01-01\")))",
                "TupleType(boolean)",
                "(dateLessEqualThan(date, date(\"2020-01-01\")))",
                (lib.dateLessEqualThan(date, lib.date("2020-01-01"))),
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
                lib.booleanAnd(lib.stringLessEqualThan("b", "a"), lib.stringLessEqualThan("a", "d")),
                false);
    }

    @Override
    @Test
    public void testInExpression() {
        super.testInExpression();

        // operator test
        List<EnvironmentEntry> entries = Arrays.asList();

        doExpressionTest(entries, "", "\"b\" in [[\"f\"..\"h\"], [\"a\"..\"c\"]]",
                "InExpression(StringLiteral(\"b\"), ListTest(ListLiteral(IntervalTest(false,StringLiteral(\"f\"),false,StringLiteral(\"h\")),IntervalTest(false,StringLiteral(\"a\"),false,StringLiteral(\"c\")))))",
                "boolean",
                "(listContains(asList(booleanAnd(stringGreaterEqualThan(\"b\", \"f\"), stringLessEqualThan(\"b\", \"h\")), booleanAnd(stringGreaterEqualThan(\"b\", \"a\"), stringLessEqualThan(\"b\", \"c\"))), true))",
                (lib.listContains(lib.asList(lib.booleanAnd(lib.stringGreaterEqualThan("b", "f"), lib.stringLessEqualThan("b", "h")), lib.booleanAnd(lib.stringGreaterEqualThan("b", "a"), lib.stringLessEqualThan("b", "c"))), true)),
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
                lib.and(lib.asList(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE)),
                false);
        doExpressionTest(entries, "", "or([true, false, true])",
                "FunctionInvocation(Name(or) -> PositionalParameters(ListLiteral(BooleanLiteral(true),BooleanLiteral(false),BooleanLiteral(true))))",
                "boolean",
                "or(asList(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE))",
                lib.or(lib.asList(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE)),
                true);
        doExpressionTest(entries, "", "contains(\"abc\", \"a\")",
                "FunctionInvocation(Name(contains) -> PositionalParameters(StringLiteral(\"abc\"), StringLiteral(\"a\")))",
                "boolean",
                "contains(\"abc\", \"a\")",
                lib.contains("abc", "a"),
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
                lib.stringLength(lib.substring(input, lib.number("1"))),
                lib.number("3"));

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
                new EnvironmentEntry("input", NUMBER, lib.number("1")));

        doExpressionTest(entries, "", "number(\"1 000\", \" \", \".\")",
                "FunctionInvocation(Name(number) -> PositionalParameters(StringLiteral(\"1 000\"), StringLiteral(\" \"), StringLiteral(\".\")))",
                "number",
                "number(\"1 000\", \" \", \".\")",
                lib.number("1 000", " ", "."),
                lib.number("1000"));
        doExpressionTest(entries, "", "number(from: \"1.000.000,01\", 'decimal separator':\",\", 'grouping separator':\".\")",
                "FunctionInvocation(Name(number) -> NamedParameters(from : StringLiteral(\"1.000.000,01\"), 'decimal separator' : StringLiteral(\",\"), 'grouping separator' : StringLiteral(\".\")))",
                "number",
                "number(\"1.000.000,01\", \".\", \",\")",
                lib.number("1.000.000,01", ".", ","),
                lib.number("1.000.000,01", ".", ","));
        doExpressionTest(entries, "", "number(from: \"1.000.000,01\", decimalSeparator:\",\", groupingSeparator:\".\")",
                "FunctionInvocation(Name(number) -> NamedParameters(from : StringLiteral(\"1.000.000,01\"), decimalSeparator : StringLiteral(\",\"), groupingSeparator : StringLiteral(\".\")))",
                "number",
                "number(\"1.000.000,01\", \".\", \",\")",
                lib.number("1.000.000,01", ".", ","),
                lib.number("1.000.000,01", ".", ","));
        doExpressionTest(entries, "", "string(1.1)",
                "FunctionInvocation(Name(string) -> PositionalParameters(NumericLiteral(1.1)))",
                "string",
                "string(number(\"1.1\"))",
                lib.string(lib.number("1.1")),
                "1.1");
    }

    @Test
    public void testNumericFunctions() {
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, lib.number("1")));

        doExpressionTest(entries, "", "decimal(100, 2)",
                "FunctionInvocation(Name(decimal) -> PositionalParameters(NumericLiteral(100), NumericLiteral(2)))",
                "number",
                "decimal(number(\"100\"), number(\"2\"))",
                lib.decimal(lib.number("100"), lib.number("2")),
                lib.decimal(lib.number("100"), lib.number("2")));
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
                lib.ceiling(lib.number("100")),
                lib.number("100"));
    }

    @Test
    public void testStringFunctions() {
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, lib.number("1")));

        doExpressionTest(entries, "", "substring(\"abc\", 3)",
                "FunctionInvocation(Name(substring) -> PositionalParameters(StringLiteral(\"abc\"), NumericLiteral(3)))",
                "string",
                "substring(\"abc\", number(\"3\"))",
                lib.substring("abc", lib.number("3")),
                "c");
        doExpressionTest(entries, "", "split(\"John Doe\", \"\\s\")",
                "FunctionInvocation(Name(split) -> PositionalParameters(StringLiteral(\"John Doe\"), StringLiteral(\"\\s\")))",
                "ListType(string)",
                "split(\"John Doe\", \"\\\\s\")",
                lib.split("John Doe", "\\s"),
                lib.asList("John", "Doe"));
        doExpressionTest(entries, "", "substring(string: \"abc\", 'start position': 3)",
                "FunctionInvocation(Name(substring) -> NamedParameters(string : StringLiteral(\"abc\"), 'start position' : NumericLiteral(3)))",
                "string",
                "substring(\"abc\", number(\"3\"))",
                lib.substring("abc", lib.number("3")),
                "c");
        doExpressionTest(entries, "", "substring(string: \"abc\", startPosition: 3)",
                "FunctionInvocation(Name(substring) -> NamedParameters(string : StringLiteral(\"abc\"), startPosition : NumericLiteral(3)))",
                "string",
                "substring(\"abc\", number(\"3\"))",
                lib.substring("abc", lib.number("3")),
                "c");
        doExpressionTest(entries, "", "string length(\"abc\")",
                "FunctionInvocation(Name(string length) -> PositionalParameters(StringLiteral(\"abc\")))",
                "number",
                "stringLength(\"abc\")",
                lib.stringLength("abc"),
                lib.number("3"));
        doExpressionTest(entries, "", "string length(\"\\n\")",
                "FunctionInvocation(Name(string length) -> PositionalParameters(StringLiteral(\"\\n\")))",
                "number",
                "stringLength(\"\\n\")",
                lib.stringLength("\n"),
                lib.number("1"));
        doExpressionTest(entries, "", "string length(\"\\r\")",
                "FunctionInvocation(Name(string length) -> PositionalParameters(StringLiteral(\"\\r\")))",
                "number",
                "stringLength(\"\\r\")",
                lib.stringLength("\r"),
                lib.number("1"));
        doExpressionTest(entries, "", "string length(\"\\t\")",
                "FunctionInvocation(Name(string length) -> PositionalParameters(StringLiteral(\"\\t\")))",
                "number",
                "stringLength(\"\\t\")",
                lib.stringLength("\t"),
                lib.number("1"));
        doExpressionTest(entries, "", "string length(\"\\'\")",
                "FunctionInvocation(Name(string length) -> PositionalParameters(StringLiteral(\"\\'\")))",
                "number",
                "stringLength(\"'\")",
                lib.stringLength("\'"),
                lib.number("1"));
        doExpressionTest(entries, "", "string length(\"\\\"\")",
                "FunctionInvocation(Name(string length) -> PositionalParameters(StringLiteral(\"\\\"\")))",
                "number",
                "stringLength(\"\\\"\")",
                lib.stringLength("\""),
                lib.number("1"));
        doExpressionTest(entries, "", "string length(\"\\\\\")",
                "FunctionInvocation(Name(string length) -> PositionalParameters(StringLiteral(\"\\\\\")))",
                "number",
                "stringLength(\"\\\\\")",
                lib.stringLength("\\"),
                lib.number("1"));
        doExpressionTest(entries, "", "string length(\"\u0009\")",
                "FunctionInvocation(Name(string length) -> PositionalParameters(StringLiteral(\"\t\")))",
                "number",
                "stringLength(\"\\t\")",
                lib.stringLength("\t"),
                lib.number("1"));
        doExpressionTest(entries, "", "string length(\"\\\\u0009\")",
                "FunctionInvocation(Name(string length) -> PositionalParameters(StringLiteral(\"\\\\u0009\")))",
                "number",
                "stringLength(\"\\\\u0009\")",
                lib.stringLength("\\u0009"),
                lib.number("6"));
        doExpressionTest(entries, "", "string length(\"\\uD83D\\uDCA9\")",
                "FunctionInvocation(Name(string length) -> PositionalParameters(StringLiteral(\"\\uD83D\\uDCA9\")))",
                "number",
                "stringLength(\"\\uD83D\\uDCA9\")",
                lib.stringLength("\uD83D\uDCA9"),
                lib.number("2"));
        doExpressionTest(entries, "", "upper case(\"abc\")",
                "FunctionInvocation(Name(upper case) -> PositionalParameters(StringLiteral(\"abc\")))",
                "string",
                "upperCase(\"abc\")",
                lib.upperCase("abc"),
                "ABC");
        doExpressionTest(entries, "", "lower case(\"abc\")",
                "FunctionInvocation(Name(lower case) -> PositionalParameters(StringLiteral(\"abc\")))",
                "string",
                "lowerCase(\"abc\")",
                lib.lowerCase("abc"),
                "abc");
        doExpressionTest(entries, "", "substring before(\"abc\", \"b\")",
                "FunctionInvocation(Name(substring before) -> PositionalParameters(StringLiteral(\"abc\"), StringLiteral(\"b\")))",
                "string",
                "substringBefore(\"abc\", \"b\")",
                lib.substringBefore("abc", "b"),
                "a");
        doExpressionTest(entries, "", "substring after(\"abc\", \"b\")",
                "FunctionInvocation(Name(substring after) -> PositionalParameters(StringLiteral(\"abc\"), StringLiteral(\"b\")))",
                "string",
                "substringAfter(\"abc\", \"b\")",
                lib.substringAfter("abc", "b"),
                "c");
        doExpressionTest(entries, "", "replace(\"abc\", \"b\", \"d\", \"i\")",
                "FunctionInvocation(Name(replace) -> PositionalParameters(StringLiteral(\"abc\"), StringLiteral(\"b\"), StringLiteral(\"d\"), StringLiteral(\"i\")))",
                "string",
                "replace(\"abc\", \"b\", \"d\", \"i\")",
                lib.replace("abc", "b", "d", "i"),
                "adc");
        doExpressionTest(entries, "", "replace(\"abc\", \"b\", \"d\")",
                "FunctionInvocation(Name(replace) -> PositionalParameters(StringLiteral(\"abc\"), StringLiteral(\"b\"), StringLiteral(\"d\")))",
                "string",
                "replace(\"abc\", \"b\", \"d\")",
                lib.replace("abc", "b", "d"),
                "adc");
        doExpressionTest(entries, "", "replace(\"0123456789\",\"(\\d{3})(\\d{3})(\\d{4})\",\"($1) $2-$3\")",
                "FunctionInvocation(Name(replace) -> PositionalParameters(StringLiteral(\"0123456789\"), StringLiteral(\"(\\d{3})(\\d{3})(\\d{4})\"), StringLiteral(\"($1) $2-$3\")))",
                "string",
                "replace(\"0123456789\", \"(\\\\d{3})(\\\\d{3})(\\\\d{4})\", \"($1) $2-$3\")",
                lib.replace("0123456789", "(\\d{3})(\\d{3})(\\d{4})", "($1) $2-$3"),
                "(012) 345-6789");
        doExpressionTest(entries, "", "contains(\"abc\", \"a\")",
                "FunctionInvocation(Name(contains) -> PositionalParameters(StringLiteral(\"abc\"), StringLiteral(\"a\")))",
                "boolean",
                "contains(\"abc\", \"a\")",
                lib.contains("abc", "a"),
                true);
        doExpressionTest(entries, "", "starts with(\"abc\", \"a\")",
                "FunctionInvocation(Name(starts with) -> PositionalParameters(StringLiteral(\"abc\"), StringLiteral(\"a\")))",
                "boolean",
                "startsWith(\"abc\", \"a\")",
                lib.startsWith("abc", "a"),
                true);
        doExpressionTest(entries, "", "ends with(\"abc\", \"c\")",
                "FunctionInvocation(Name(ends with) -> PositionalParameters(StringLiteral(\"abc\"), StringLiteral(\"c\")))",
                "boolean",
                "endsWith(\"abc\", \"c\")",
                lib.endsWith("abc", "c"),
                true);
        doExpressionTest(entries, "", "matches(\"abc\", \"abc\", \"i\")",
                "FunctionInvocation(Name(matches) -> PositionalParameters(StringLiteral(\"abc\"), StringLiteral(\"abc\"), StringLiteral(\"i\")))",
                "boolean",
                "matches(\"abc\", \"abc\", \"i\")",
                lib.matches("abc", "abc", "i"),
                true);
        doExpressionTest(entries, "", "matches(\"abc\", \"abc\")",
                "FunctionInvocation(Name(matches) -> PositionalParameters(StringLiteral(\"abc\"), StringLiteral(\"abc\")))",
                "boolean",
                "matches(\"abc\", \"abc\")",
                lib.matches("abc", "abc"),
                true);
        doExpressionTest(entries, "", "matches(\"?\", \"\\p{Nd}+\")",
                "FunctionInvocation(Name(matches) -> PositionalParameters(StringLiteral(\"?\"), StringLiteral(\"\\p{Nd}+\")))",
                "boolean",
                "matches(\"?\", \"\\\\p{Nd}+\")",
                lib.matches("?", "\\p{Nd}+"),
                false);
    }

    @Test
    public void testListFunctions() {
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, lib.number("1")));

        doExpressionTest(entries, "", "list contains([1, 2, 3], 1)",
                "FunctionInvocation(Name(list contains) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), NumericLiteral(1)))",
                "boolean",
                "listContains(asList(number(\"1\"), number(\"2\"), number(\"3\")), number(\"1\"))",
                lib.listContains(Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3")), lib.number("1")),
                true);
        doExpressionTest(entries, "", "count([1, 2, 3])",
                "FunctionInvocation(Name(count) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3))))",
                "number",
                "count(asList(number(\"1\"), number(\"2\"), number(\"3\")))",
                lib.count(Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3"))),
                lib.number("3"));
        doExpressionTest(entries, "", "min([1, 2, 3])",
                "FunctionInvocation(Name(min) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3))))",
                "number",
                "min(asList(number(\"1\"), number(\"2\"), number(\"3\")))",
                lib.min(Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3"))),
                lib.number("1"));
        doExpressionTest(entries, "", "max([1, 2, 3])",
                "FunctionInvocation(Name(max) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3))))",
                "number",
                "max(asList(number(\"1\"), number(\"2\"), number(\"3\")))",
                lib.max(Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3"))),
                lib.number("3"));
        doExpressionTest(entries, "", "sum([1, 2, 3])",
                "FunctionInvocation(Name(sum) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3))))",
                "number",
                "sum(asList(number(\"1\"), number(\"2\"), number(\"3\")))",
                lib.sum(Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3"))),
                lib.number("6"));
        doExpressionTest(entries, "", "mean([1, 2, 3])",
                "FunctionInvocation(Name(mean) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3))))",
                "number",
                "mean(asList(number(\"1\"), number(\"2\"), number(\"3\")))",
                lib.mean(Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3"))),
                lib.number("2"));
        doExpressionTest(entries, "", "sublist([1, 2, 3], 2, 1)",
                "FunctionInvocation(Name(sublist) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), NumericLiteral(2), NumericLiteral(1)))",
                "ListType(Any)",
                "sublist(asList(number(\"1\"), number(\"2\"), number(\"3\")), number(\"2\"), number(\"1\"))",
                lib.sublist(Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3")), lib.number("2"), lib.number("1")),
                Arrays.asList(lib.number("2")));
        doExpressionTest(entries, "", "sublist([1, 2, 3], 2)",
                "FunctionInvocation(Name(sublist) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), NumericLiteral(2)))",
                "ListType(Any)",
                "sublist(asList(number(\"1\"), number(\"2\"), number(\"3\")), number(\"2\"))",
                lib.sublist(Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3")), lib.number("2")),
                Arrays.asList(lib.number("2"), lib.number("3")));
        doExpressionTest(entries, "", "append([1, 2, 3], 4)",
                "FunctionInvocation(Name(append) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), NumericLiteral(4)))",
                "ListType(Any)",
                "append(asList(number(\"1\"), number(\"2\"), number(\"3\")), number(\"4\"))",
                lib.append(Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3")), lib.number("4")),
                Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3"), lib.number("4")));

        doExpressionTest(entries, "", "concatenate([1, 2, 3], [4, 5, 6])",
                "FunctionInvocation(Name(concatenate) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), ListLiteral(NumericLiteral(4),NumericLiteral(5),NumericLiteral(6))))",
                "ListType(Any)",
                "concatenate(asList(number(\"1\"), number(\"2\"), number(\"3\")), asList(number(\"4\"), number(\"5\"), number(\"6\")))",
                lib.concatenate(Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3")), Arrays.asList(lib.number("4"), lib.number("5"), lib.number("6"))),
                Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3"), lib.number("4"), lib.number("5"), lib.number("6")));
        doExpressionTest(entries, "", "insert before([1, 2, 3], 1, 4)",
                "FunctionInvocation(Name(insert before) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), NumericLiteral(1), NumericLiteral(4)))",
                "ListType(Any)",
                "insertBefore(asList(number(\"1\"), number(\"2\"), number(\"3\")), number(\"1\"), number(\"4\"))",
                lib.insertBefore(Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3")), lib.number("1"), lib.number("4")),
                Arrays.asList(lib.number("4"), lib.number("1"), lib.number("2"), lib.number("3")));
        doExpressionTest(entries, "", "remove([1, 2, 3], 1)",
                "FunctionInvocation(Name(remove) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), NumericLiteral(1)))",
                "ListType(Any)",
                "remove(asList(number(\"1\"), number(\"2\"), number(\"3\")), number(\"1\"))",
                lib.remove(Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3")), lib.number("1")),
                Arrays.asList(lib.number("2"), lib.number("3")));
        doExpressionTest(entries, "", "reverse([1, 2, 3])",
                "FunctionInvocation(Name(reverse) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3))))",
                "ListType(Any)",
                "reverse(asList(number(\"1\"), number(\"2\"), number(\"3\")))",
                lib.reverse(Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3"))),
                Arrays.asList(lib.number("3"), lib.number("2"), lib.number("1")));
        doExpressionTest(entries, "", "index of([1, 2, 3], 3)",
                "FunctionInvocation(Name(index of) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), NumericLiteral(3)))",
                "ListType(Any)",
                "indexOf(asList(number(\"1\"), number(\"2\"), number(\"3\")), number(\"3\"))",
                lib.indexOf(Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3")), lib.number("3")),
                Arrays.asList(lib.number("3")));
        doExpressionTest(entries, "", "union([1, 2, 3], [4, 5, 6])",
                "FunctionInvocation(Name(union) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), ListLiteral(NumericLiteral(4),NumericLiteral(5),NumericLiteral(6))))",
                "ListType(Any)",
                "union(asList(number(\"1\"), number(\"2\"), number(\"3\")), asList(number(\"4\"), number(\"5\"), number(\"6\")))",
                lib.union(Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3")), Arrays.asList(lib.number("4"), lib.number("5"), lib.number("6"))),
                Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3"), lib.number("4"), lib.number("5"), lib.number("6")));
        doExpressionTest(entries, "", "distinct values([1, 2, 3])",
                "FunctionInvocation(Name(distinct values) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3))))",
                "ListType(Any)",
                "distinctValues(asList(number(\"1\"), number(\"2\"), number(\"3\")))",
                lib.distinctValues(Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3"))),
                Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3")));
        doExpressionTest(entries, "", "flatten([1, 2, 3])",
                "FunctionInvocation(Name(flatten) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3))))",
                "ListType(Any)",
                "flatten(asList(number(\"1\"), number(\"2\"), number(\"3\")))",
                lib.flatten(Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3"))),
                Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3")));
    }

    @Test
    public void testContextFunctions() {
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, lib.number("1")));

        doExpressionTest(entries, "", "get entries({a: \"foo\", b: \"bar\"})",
                "FunctionInvocation(Name(get entries) -> PositionalParameters(Context(ContextEntry(ContextEntryKey(a) = StringLiteral(\"foo\")),ContextEntry(ContextEntryKey(b) = StringLiteral(\"bar\")))))",
                "ListType(ContextType())",
                "getEntries(new com.gs.dmn.runtime.Context().add(\"a\", \"foo\").add(\"b\", \"bar\"))",
                lib.getEntries(new com.gs.dmn.runtime.Context().add("a", "foo").add("b", "bar")),
                lib.asList(new com.gs.dmn.runtime.Context().add("key", "a").add("value", "foo"), new com.gs.dmn.runtime.Context().add("key", "b").add("value", "bar")));
        doExpressionTest(entries, "", "get entries({})",
                "FunctionInvocation(Name(get entries) -> PositionalParameters(Context()))",
                "ListType(ContextType())",
                "getEntries(new com.gs.dmn.runtime.Context())",
                lib.getEntries(new com.gs.dmn.runtime.Context()),
                lib.asList());

        doExpressionTest(entries, "", "get value({a: \"foo\"}, \"a\")",
                "FunctionInvocation(Name(get value) -> PositionalParameters(Context(ContextEntry(ContextEntryKey(a) = StringLiteral(\"foo\"))), StringLiteral(\"a\")))",
                "Any",
                "getValue(new com.gs.dmn.runtime.Context().add(\"a\", \"foo\"), \"a\")",
                lib.getValue(new com.gs.dmn.runtime.Context().add("a", "foo"), "a"),
                "foo");
        doExpressionTest(entries, "", "get value(null, null)",
                "FunctionInvocation(Name(get value) -> PositionalParameters(NullLiteral(), NullLiteral()))",
                "Any",
                "getValue(null, null)",
                lib.getValue(null, null),
                null);
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