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
package com.gs.dmn.signavio.feel;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.feel.AbstractFEELProcessorTest;
import com.gs.dmn.feel.EnvironmentEntry;
import com.gs.dmn.feel.analysis.semantics.type.ItemDefinitionType;
import com.gs.dmn.signavio.SignavioDMNModelRepository;
import com.gs.dmn.signavio.feel.lib.SignavioLib;
import com.gs.dmn.signavio.testlab.TestLab;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.gs.dmn.feel.analysis.semantics.type.NumberType.NUMBER;
import static com.gs.dmn.feel.analysis.semantics.type.StringType.STRING;

public abstract class AbstractSignavioFEELProcessorTest<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends AbstractFEELProcessorTest<NUMBER, DATE, TIME, DATE_TIME, DURATION, TestLab> {
    protected final SignavioLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> lib = (SignavioLib<NUMBER, DATE, TIME, DATE_TIME, DURATION>) this.dmnInterpreter.getFeelLib();

    @Override
    protected DMNModelRepository makeRepository() {
        return new SignavioDMNModelRepository();
    }

    @Override
    @Test
    public void testBetweenExpression() {
        super.testBetweenExpression();

        List<EnvironmentEntry> entries = Arrays.asList();

        // Certain relational operators are not supported in Signavio (return null)
        doExpressionTest(entries, "", "\"a\" between \"b\" and \"d\"",
                "BetweenExpression(StringLiteral(\"a\"), StringLiteral(\"b\"), StringLiteral(\"d\"))",
                "boolean",
                "booleanAnd(stringLessEqualThan(\"b\", \"a\"), stringLessEqualThan(\"a\", \"d\"))",
                lib.booleanAnd(lib.stringLessEqualThan("b", "a"), lib.stringLessEqualThan("a", "d")),
                null);
    }

    @Override
    @Test
    public void testInExpression() {
        super.testInExpression();

        // operator test
        List<EnvironmentEntry> entries = Arrays.asList();

        // Certain relational operators are not supported in Signavio (return null)
        doExpressionTest(entries, "", "\"b\" in [[\"f\"..\"h\"], [\"a\"..\"c\"]]",
                "InExpression(StringLiteral(\"b\"), ListTest(ListLiteral(IntervalTest(false,StringLiteral(\"f\"),false,StringLiteral(\"h\")),IntervalTest(false,StringLiteral(\"a\"),false,StringLiteral(\"c\")))))",
                "boolean",
                "(listContains(asList(booleanAnd(stringGreaterEqualThan(\"b\", \"f\"), stringLessEqualThan(\"b\", \"h\")), booleanAnd(stringGreaterEqualThan(\"b\", \"a\"), stringLessEqualThan(\"b\", \"c\"))), true))",
                (lib.listContains(lib.asList(lib.booleanAnd(lib.stringGreaterEqualThan("b", "f"), lib.stringLessEqualThan("b", "h")), lib.booleanAnd(lib.stringGreaterEqualThan("b", "a"), lib.stringLessEqualThan("b", "c"))), true)),
                false);
    }

    @Test
    public void testFunctionInvocation() {
        String input = "abc";
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", STRING, input));

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
        doExpressionTest(entries, "", "len(right(input, 1))",
                "FunctionInvocation(Name(len) -> PositionalParameters(FunctionInvocation(Name(right) -> PositionalParameters(Name(input), NumericLiteral(1)))))",
                "number",
                "len(right(input, number(\"1\")))",
                lib.len(lib.right(input, lib.number("1"))),
                lib.number("1"));

        doExpressionTest(entries, "", "contains(text : \"abc\", substring : \"a\")",
                "FunctionInvocation(Name(contains) -> NamedParameters(text : StringLiteral(\"abc\"), substring : StringLiteral(\"a\")))",
                "boolean",
                "contains(\"abc\", \"a\")",
                lib.contains("abc", "a"),
                true);
    }

    @Override
    @Test
    public void testPathExpression() {
        super.testPathExpression();

        ItemDefinitionType type = new ItemDefinitionType("PrivateFundRequirements").addMember("HierarchyNode", Arrays.asList(), STRING);
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("PrivateFundRequirements", type, null));

        doExpressionTest(entries, "", "len(PrivateFundRequirements.HierarchyNode)",
                "FunctionInvocation(Name(len) -> PositionalParameters(PathExpression(Name(PrivateFundRequirements), HierarchyNode)))",
                "number",
                "len(((String)(privateFundRequirements != null ? privateFundRequirements.getHierarchyNode() : null)))",
                null,
                null);
    }

    @Override
    @Test
    public void testConversionFunctions() {
        super.testConversionFunctions();
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, lib.number("1")));

        doExpressionTest(entries, "", "date(\"2000-10-10\")",
                "DateTimeLiteral(date, \"2000-10-10\")",
                "date",
                "date(\"2000-10-10\")",
                lib.date("2000-10-10"),
                lib.date("2000-10-10"));
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

        doExpressionTest(entries, "", "floor(100)",
                "FunctionInvocation(Name(floor) -> PositionalParameters(NumericLiteral(100)))",
                "number",
                "floor(number(\"100\"))",
                lib.floor(lib.number("100")),
                lib.number("100"));
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

        doExpressionTest(entries, "", "right(\"abc\", 1)",
                "FunctionInvocation(Name(right) -> PositionalParameters(StringLiteral(\"abc\"), NumericLiteral(1)))",
                "string",
                "right(\"abc\", number(\"1\"))",
                lib.right("abc", lib.number("1")),
                "c");
        doExpressionTest(entries, "", "len(\"abc\")",
                "FunctionInvocation(Name(len) -> PositionalParameters(StringLiteral(\"abc\")))",
                "number",
                "len(\"abc\")",
                lib.len("abc"),
                lib.number("3"));
        doExpressionTest(entries, "", "len(\"\\n\")",
                "FunctionInvocation(Name(len) -> PositionalParameters(StringLiteral(\"\\n\")))",
                "number",
                "len(\"\\n\")",
                lib.len("\n"),
                lib.number("1"));
        doExpressionTest(entries, "", "len(\"\\r\")",
                "FunctionInvocation(Name(len) -> PositionalParameters(StringLiteral(\"\\r\")))",
                "number",
                "len(\"\\r\")",
                lib.len("\r"),
                lib.number("1"));
        doExpressionTest(entries, "", "len(\"\\t\")",
                "FunctionInvocation(Name(len) -> PositionalParameters(StringLiteral(\"\\t\")))",
                "number",
                "len(\"\\t\")",
                lib.len("\t"),
                lib.number("1"));
        doExpressionTest(entries, "", "len(\"\\'\")",
                "FunctionInvocation(Name(len) -> PositionalParameters(StringLiteral(\"\\'\")))",
                "number",
                "len(\"'\")",
                lib.len("\'"),
                lib.number("1"));
        doExpressionTest(entries, "", "len(\"\\\"\")",
                "FunctionInvocation(Name(len) -> PositionalParameters(StringLiteral(\"\\\"\")))",
                "number",
                "len(\"\\\"\")",
                lib.len("\""),
                lib.number("1"));
        doExpressionTest(entries, "", "len(\"\\\\\")",
                "FunctionInvocation(Name(len) -> PositionalParameters(StringLiteral(\"\\\\\")))",
                "number",
                "len(\"\\\\\")",
                lib.len("\\"),
                lib.number("1"));
        doExpressionTest(entries, "", "len(\"\u0009\")",
                "FunctionInvocation(Name(len) -> PositionalParameters(StringLiteral(\"\t\")))",
                "number",
                "len(\"\\t\")",
                lib.len("\t"),
                lib.number("1"));
        doExpressionTest(entries, "", "len(\"\\\\u0009\")",
                "FunctionInvocation(Name(len) -> PositionalParameters(StringLiteral(\"\\\\u0009\")))",
                "number",
                "len(\"\\\\u0009\")",
                lib.len("\\u0009"),
                lib.number("6"));
        doExpressionTest(entries, "", "len(\"\\uD83D\\uDCA9\")",
                "FunctionInvocation(Name(len) -> PositionalParameters(StringLiteral(\"\\uD83D\\uDCA9\")))",
                "number",
                "len(\"\\uD83D\\uDCA9\")",
                lib.len("\uD83D\uDCA9"),
                lib.number("2"));
        doExpressionTest(entries, "", "upper(\"abc\")",
                "FunctionInvocation(Name(upper) -> PositionalParameters(StringLiteral(\"abc\")))",
                "string",
                "upper(\"abc\")",
                lib.upper("abc"),
                "ABC");
        doExpressionTest(entries, "", "lower(\"abc\")",
                "FunctionInvocation(Name(lower) -> PositionalParameters(StringLiteral(\"abc\")))",
                "string",
                "lower(\"abc\")",
                lib.lower("abc"),
                "abc");
        doExpressionTest(entries, "", "contains(\"abc\", \"a\")",
                "FunctionInvocation(Name(contains) -> PositionalParameters(StringLiteral(\"abc\"), StringLiteral(\"a\")))",
                "boolean",
                "contains(\"abc\", \"a\")",
                lib.contains("abc", "a"),
                true);
        doExpressionTest(entries, "", "startsWith(\"abc\", \"a\")",
                "FunctionInvocation(Name(startsWith) -> PositionalParameters(StringLiteral(\"abc\"), StringLiteral(\"a\")))",
                "boolean",
                "startsWith(\"abc\", \"a\")",
                lib.startsWith("abc", "a"),
                true);
        doExpressionTest(entries, "", "endsWith(\"abc\", \"c\")",
                "FunctionInvocation(Name(endsWith) -> PositionalParameters(StringLiteral(\"abc\"), StringLiteral(\"c\")))",
                "boolean",
                "endsWith(\"abc\", \"c\")",
                lib.endsWith("abc", "c"),
                true);
    }

    @Test
    public void testListFunctions() {
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, lib.number("1")));

        doExpressionTest(entries, "", "containsOnly([1, 2, 3], [1])",
                "FunctionInvocation(Name(containsOnly) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), ListLiteral(NumericLiteral(1))))",
                "boolean",
                "containsOnly(asList(number(\"1\"), number(\"2\"), number(\"3\")), asList(number(\"1\")))",
                lib.containsOnly(lib.asList(lib.number("1"), lib.number("2"), lib.number("3")), lib.asList(lib.number("1"))),
                false);
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
        doExpressionTest(entries, "", "avg([1, 2, 3])",
                "FunctionInvocation(Name(avg) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3))))",
                "number",
                "avg(asList(number(\"1\"), number(\"2\"), number(\"3\")))",
                lib.avg(Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3"))),
                lib.number("2"));
        doExpressionTest(entries, "", "append([1, 2, 3], 4)",
                "FunctionInvocation(Name(append) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), NumericLiteral(4)))",
                "ListType(Any)",
                "append(asList(number(\"1\"), number(\"2\"), number(\"3\")), number(\"4\"))",
                lib.append(Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3")), lib.number("4")),
                Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3"), lib.number("4")));

        doExpressionTest(entries, "", "appendAll([1, 2, 3], [4, 5, 6])",
                "FunctionInvocation(Name(appendAll) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), ListLiteral(NumericLiteral(4),NumericLiteral(5),NumericLiteral(6))))",
                "ListType(Any)",
                "appendAll(asList(number(\"1\"), number(\"2\"), number(\"3\")), asList(number(\"4\"), number(\"5\"), number(\"6\")))",
                lib.appendAll(Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3")), Arrays.asList(lib.number("4"), lib.number("5"), lib.number("6"))),
                Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3"), lib.number("4"), lib.number("5"), lib.number("6")));
        doExpressionTest(entries, "", "remove([1, 2, 3], 1)",
                "FunctionInvocation(Name(remove) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), NumericLiteral(1)))",
                "ListType(Any)",
                "remove(asList(number(\"1\"), number(\"2\"), number(\"3\")), number(\"1\"))",
                lib.remove(Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3")), lib.number("1")),
                Arrays.asList(lib.number("2"), lib.number("3")));
    }
}