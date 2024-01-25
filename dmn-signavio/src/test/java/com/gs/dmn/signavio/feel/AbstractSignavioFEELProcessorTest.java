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
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
                this.lib.booleanAnd(this.lib.stringLessEqualThan("b", "a"), this.lib.stringLessEqualThan("a", "d")),
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
                "InExpression(StringLiteral(\"b\"), ListTest(ListLiteral(EndpointsRange(false,StringLiteral(\"f\"),false,StringLiteral(\"h\")),EndpointsRange(false,StringLiteral(\"a\"),false,StringLiteral(\"c\")))))",
                "boolean",
                "listContains(asList(booleanAnd(stringGreaterEqualThan(\"b\", \"f\"), stringLessEqualThan(\"b\", \"h\")), booleanAnd(stringGreaterEqualThan(\"b\", \"a\"), stringLessEqualThan(\"b\", \"c\"))), true)",
                this.lib.listContains(this.lib.asList(this.lib.booleanAnd(this.lib.stringGreaterEqualThan("b", "f"), this.lib.stringLessEqualThan("b", "h")), this.lib.booleanAnd(this.lib.stringGreaterEqualThan("b", "a"), this.lib.stringLessEqualThan("b", "c"))), true),
                false);
    }

    @Test
    public void testFunctionInvocation() {
        String input = "abc";
        String exchangeRegion = "region";
        String smtpInfoBarrier = "smtp";
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", STRING, input),
                new EnvironmentEntry("exchangeRegion", STRING, exchangeRegion),
                new EnvironmentEntry("smtpInfoBarrier", STRING, smtpInfoBarrier)
        );

        // nested functions
        doExpressionTest(entries, "", "len(right(input, 1))",
                "FunctionInvocation(Name(len) -> PositionalParameters(FunctionInvocation(Name(right) -> PositionalParameters(Name(input), NumericLiteral(1)))))",
                "number",
                "len(right(input, number(\"1\")))",
                this.lib.len(this.lib.right(input, this.lib.number("1"))),
                this.lib.number("1"));
        doExpressionTest(entries, "", "concat(appendAll([exchangeRegion, \".\", smtpInfoBarrier], [\".email.gs.com\"]))",
                "FunctionInvocation(Name(concat) -> PositionalParameters(FunctionInvocation(Name(appendAll) -> PositionalParameters(ListLiteral(Name(exchangeRegion),StringLiteral(\".\"),Name(smtpInfoBarrier)), ListLiteral(StringLiteral(\".email.gs.com\"))))))",
                "string",
                "concat(appendAll(asList(exchangeRegion, \".\", smtpInfoBarrier), asList(\".email.gs.com\")))",
                this.lib.concat(this.lib.appendAll(this.lib.asList(exchangeRegion, ".", smtpInfoBarrier), this.lib.asList(".email.gs.com"))),
                "region.smtp.email.gs.com");

        // invocation with positional arguments
        doExpressionTest(entries, "", "contains(\"abc\", \"a\")",
                "FunctionInvocation(Name(contains) -> PositionalParameters(StringLiteral(\"abc\"), StringLiteral(\"a\")))",
                "boolean",
                "contains(\"abc\", \"a\")",
                this.lib.contains("abc", "a"),
                true);

        // invocation with named arguments
        doExpressionTest(entries, "", "string(from : 1.1)",
                "FunctionInvocation(Name(string) -> NamedParameters(from : NumericLiteral(1.1)))",
                "string",
                null,
                null,
                null);
        doExpressionTest(entries, "", "contains(text : \"abc\", substring : \"a\")",
                "FunctionInvocation(Name(contains) -> NamedParameters(text : StringLiteral(\"abc\"), substring : StringLiteral(\"a\")))",
                "boolean",
                "contains(\"abc\", \"a\")",
                this.lib.contains("abc", "a"),
                true);
    }

    @Test
    @Override
    public void testFilterExpression() {
        super.testFilterExpression();

        List<EnvironmentEntry> entries = Arrays.asList(
        );
        doExpressionTest(entries, "", "[ { x: \"1\", y: \"2\" }, { x: null, y: \"3\" } ][ x < \"2\" ]",
                "FilterExpression(ListLiteral(Context(ContextEntry(ContextEntryKey(x) = StringLiteral(\"1\")),ContextEntry(ContextEntryKey(y) = StringLiteral(\"2\"))),Context(ContextEntry(ContextEntryKey(x) = NullLiteral()),ContextEntry(ContextEntryKey(y) = StringLiteral(\"3\")))), Relational(<,PathExpression(Name(item), x),StringLiteral(\"2\")))",
                "ListType(ContextType(x = string, y = string))",
                "asList(new com.gs.dmn.runtime.Context().add(\"x\", \"1\").add(\"y\", \"2\"), new com.gs.dmn.runtime.Context().add(\"x\", null).add(\"y\", \"3\")).stream().filter(item -> stringLessThan(((String)((com.gs.dmn.runtime.Context)item).get(\"x\")), \"2\") == Boolean.TRUE).collect(Collectors.toList())",
                this.lib.asList(new com.gs.dmn.runtime.Context().add("x", "1").add("y", "2"), new com.gs.dmn.runtime.Context().add("x", null).add("y", "3")).stream().filter(item -> this.lib.stringLessThan(((String)((com.gs.dmn.runtime.Context)item).get("x")), "2") == Boolean.TRUE).collect(Collectors.toList()),
                Arrays.asList());
        doExpressionTest(entries, "", "[ { x: null, y: \"2\" }, { x: \"1\", y: \"3\" } ][ x < \"2\" ]",
                "FilterExpression(ListLiteral(Context(ContextEntry(ContextEntryKey(x) = NullLiteral()),ContextEntry(ContextEntryKey(y) = StringLiteral(\"2\"))),Context(ContextEntry(ContextEntryKey(x) = StringLiteral(\"1\")),ContextEntry(ContextEntryKey(y) = StringLiteral(\"3\")))), Relational(<,PathExpression(Name(item), x),StringLiteral(\"2\")))",
                "ListType(ContextType(x = string, y = string))",
                "asList(new com.gs.dmn.runtime.Context().add(\"x\", null).add(\"y\", \"2\"), new com.gs.dmn.runtime.Context().add(\"x\", \"1\").add(\"y\", \"3\")).stream().filter(item -> stringLessThan(((String)((com.gs.dmn.runtime.Context)item).get(\"x\")), \"2\") == Boolean.TRUE).collect(Collectors.toList())",
                this.lib.asList(new com.gs.dmn.runtime.Context().add("x", null).add("y", "2"), new com.gs.dmn.runtime.Context().add("x", "1").add("y", "3")).stream().filter(item -> this.lib.stringLessThan(((String)((com.gs.dmn.runtime.Context)item).get("x")), "2") == Boolean.TRUE).collect(Collectors.toList()),
                Arrays.asList());
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
                new EnvironmentEntry("input", NUMBER, this.lib.number("1")));

        doExpressionTest(entries, "", "isDefined(\"2000-10-10\")",
                "FunctionInvocation(Name(isDefined) -> PositionalParameters(StringLiteral(\"2000-10-10\")))",
                "boolean",
                "isDefined(\"2000-10-10\")",
                this.lib.isDefined("2000-10-10"),
                true);
        doExpressionTest(entries, "", "isUndefined(\"2000-10-10\")",
                "FunctionInvocation(Name(isUndefined) -> PositionalParameters(StringLiteral(\"2000-10-10\")))",
                "boolean",
                "isUndefined(\"2000-10-10\")",
                this.lib.isUndefined("2000-10-10"),
                false);
        doExpressionTest(entries, "", "isValid(\"2000-10-10\")",
                "FunctionInvocation(Name(isValid) -> PositionalParameters(StringLiteral(\"2000-10-10\")))",
                "boolean",
                "isValid(\"2000-10-10\")",
                this.lib.isValid("2000-10-10"),
                true);
        doExpressionTest(entries, "", "isInvalid(\"2000-10-10\")",
                "FunctionInvocation(Name(isInvalid) -> PositionalParameters(StringLiteral(\"2000-10-10\")))",
                "boolean",
                "isInvalid(\"2000-10-10\")",
                this.lib.isInvalid("2000-10-10"),
                false);

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

        doExpressionTest(entries, "", "abs(100)",
                "FunctionInvocation(Name(abs) -> PositionalParameters(NumericLiteral(100)))",
                "number",
                "abs(number(\"100\"))",
                this.lib.abs(this.lib.number("100")),
                this.lib.number("100"));
        doExpressionTest(entries, "", "round(100, 2)",
                "FunctionInvocation(Name(round) -> PositionalParameters(NumericLiteral(100), NumericLiteral(2)))",
                "number",
                "round(number(\"100\"), number(\"2\"))",
                this.lib.round(this.lib.number("100"), this.lib.number("2")),
                this.lib.number("100.00"));
        doExpressionTest(entries, "", "ceiling(100)",
                "FunctionInvocation(Name(ceiling) -> PositionalParameters(NumericLiteral(100)))",
                "number",
                "ceiling(number(\"100\"))",
                this.lib.ceiling(this.lib.number("100")),
                this.lib.number("100"));
        doExpressionTest(entries, "", "floor(100)",
                "FunctionInvocation(Name(floor) -> PositionalParameters(NumericLiteral(100)))",
                "number",
                "floor(number(\"100\"))",
                this.lib.floor(this.lib.number("100")),
                this.lib.number("100"));
        doExpressionTest(entries, "", "integer(100)",
                "FunctionInvocation(Name(integer) -> PositionalParameters(NumericLiteral(100)))",
                "number",
                "integer(number(\"100\"))",
                this.lib.integer(this.lib.number("100")),
                this.lib.number("100"));
        doExpressionTest(entries, "", "modulo(100, 3)",
                "FunctionInvocation(Name(modulo) -> PositionalParameters(NumericLiteral(100), NumericLiteral(3)))",
                "number",
                "modulo(number(\"100\"), number(\"3\"))",
                this.lib.modulo(this.lib.number("100"), this.lib.number("3")),
                this.lib.number("1"));
        doExpressionTest(entries, "", "percent(100)",
                "FunctionInvocation(Name(percent) -> PositionalParameters(NumericLiteral(100)))",
                "number",
                "percent(number(\"100\"))",
                this.lib.percent(this.lib.number("100")),
                this.lib.number("1"));
        doExpressionTest(entries, "", "power(10, 2)",
                "FunctionInvocation(Name(power) -> PositionalParameters(NumericLiteral(10), NumericLiteral(2)))",
                "number",
                "power(number(\"10\"), number(\"2\"))",
                this.lib.power(this.lib.number("10"), this.lib.number("2")),
                this.lib.number("100"));
        doExpressionTest(entries, "", "product([100])",
                "FunctionInvocation(Name(product) -> PositionalParameters(ListLiteral(NumericLiteral(100))))",
                "number",
                "product(asList(number(\"100\")))",
                this.lib.product(this.lib.asList(this.lib.number("100"))),
                this.lib.number("100"));
        doExpressionTest(entries, "", "roundDown(100, 2)",
                "FunctionInvocation(Name(roundDown) -> PositionalParameters(NumericLiteral(100), NumericLiteral(2)))",
                "number",
                "roundDown(number(\"100\"), number(\"2\"))",
                this.lib.roundDown(this.lib.number("100"), this.lib.number("2")),
                this.lib.number("100.00"));
        doExpressionTest(entries, "", "roundUp(100, 2)",
                "FunctionInvocation(Name(roundUp) -> PositionalParameters(NumericLiteral(100), NumericLiteral(2)))",
                "number",
                "roundUp(number(\"100\"), number(\"2\"))",
                this.lib.roundUp(this.lib.number("100"), this.lib.number("2")),
                this.lib.number("100.00"));
        doExpressionTest(entries, "", "sum([100])",
                "FunctionInvocation(Name(sum) -> PositionalParameters(ListLiteral(NumericLiteral(100))))",
                "number",
                "sum(asList(number(\"100\")))",
                this.lib.sum(this.lib.asList(this.lib.number("100"))),
                this.lib.number("100"));
    }

    @Test
    public void testStringFunctions() {
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, this.lib.number("1")));

        doExpressionTest(entries, "", "right(\"abc\", 1)",
                "FunctionInvocation(Name(right) -> PositionalParameters(StringLiteral(\"abc\"), NumericLiteral(1)))",
                "string",
                "right(\"abc\", number(\"1\"))",
                this.lib.right("abc", this.lib.number("1")),
                "c");
        doExpressionTest(entries, "", "len(\"abc\")",
                "FunctionInvocation(Name(len) -> PositionalParameters(StringLiteral(\"abc\")))",
                "number",
                "len(\"abc\")",
                this.lib.len("abc"),
                this.lib.number("3"));
        doExpressionTest(entries, "", "len(\"\\n\")",
                "FunctionInvocation(Name(len) -> PositionalParameters(StringLiteral(\"\\n\")))",
                "number",
                "len(\"\\n\")",
                this.lib.len("\n"),
                this.lib.number("1"));
        doExpressionTest(entries, "", "len(\"\\r\")",
                "FunctionInvocation(Name(len) -> PositionalParameters(StringLiteral(\"\\r\")))",
                "number",
                "len(\"\\r\")",
                this.lib.len("\r"),
                this.lib.number("1"));
        doExpressionTest(entries, "", "len(\"\\t\")",
                "FunctionInvocation(Name(len) -> PositionalParameters(StringLiteral(\"\\t\")))",
                "number",
                "len(\"\\t\")",
                this.lib.len("\t"),
                this.lib.number("1"));
        doExpressionTest(entries, "", "len(\"\\'\")",
                "FunctionInvocation(Name(len) -> PositionalParameters(StringLiteral(\"\\'\")))",
                "number",
                "len(\"'\")",
                this.lib.len("\'"),
                this.lib.number("1"));
        doExpressionTest(entries, "", "len(\"\\\"\")",
                "FunctionInvocation(Name(len) -> PositionalParameters(StringLiteral(\"\\\"\")))",
                "number",
                "len(\"\\\"\")",
                this.lib.len("\""),
                this.lib.number("1"));
        doExpressionTest(entries, "", "len(\"\\\\\")",
                "FunctionInvocation(Name(len) -> PositionalParameters(StringLiteral(\"\\\\\")))",
                "number",
                "len(\"\\\\\")",
                this.lib.len("\\"),
                this.lib.number("1"));
        doExpressionTest(entries, "", "len(\"\u0009\")",
                "FunctionInvocation(Name(len) -> PositionalParameters(StringLiteral(\"\t\")))",
                "number",
                "len(\"\\t\")",
                this.lib.len("\t"),
                this.lib.number("1"));
        doExpressionTest(entries, "", "len(\"\\\\u0009\")",
                "FunctionInvocation(Name(len) -> PositionalParameters(StringLiteral(\"\\\\u0009\")))",
                "number",
                "len(\"\\\\u0009\")",
                this.lib.len("\\u0009"),
                this.lib.number("6"));
        doExpressionTest(entries, "", "len(\"\\uD83D\\uDCA9\")",
                "FunctionInvocation(Name(len) -> PositionalParameters(StringLiteral(\"\\uD83D\\uDCA9\")))",
                "number",
                "len(\"\\uD83D\\uDCA9\")",
                this.lib.len("\uD83D\uDCA9"),
                this.lib.number("2"));
        doExpressionTest(entries, "", "upper(\"abc\")",
                "FunctionInvocation(Name(upper) -> PositionalParameters(StringLiteral(\"abc\")))",
                "string",
                "upper(\"abc\")",
                this.lib.upper("abc"),
                "ABC");
        doExpressionTest(entries, "", "lower(\"abc\")",
                "FunctionInvocation(Name(lower) -> PositionalParameters(StringLiteral(\"abc\")))",
                "string",
                "lower(\"abc\")",
                this.lib.lower("abc"),
                "abc");
        doExpressionTest(entries, "", "contains(\"abc\", \"a\")",
                "FunctionInvocation(Name(contains) -> PositionalParameters(StringLiteral(\"abc\"), StringLiteral(\"a\")))",
                "boolean",
                "contains(\"abc\", \"a\")",
                this.lib.contains("abc", "a"),
                true);
        doExpressionTest(entries, "", "startsWith(\"abc\", \"a\")",
                "FunctionInvocation(Name(startsWith) -> PositionalParameters(StringLiteral(\"abc\"), StringLiteral(\"a\")))",
                "boolean",
                "startsWith(\"abc\", \"a\")",
                this.lib.startsWith("abc", "a"),
                true);
        doExpressionTest(entries, "", "endsWith(\"abc\", \"c\")",
                "FunctionInvocation(Name(endsWith) -> PositionalParameters(StringLiteral(\"abc\"), StringLiteral(\"c\")))",
                "boolean",
                "endsWith(\"abc\", \"c\")",
                this.lib.endsWith("abc", "c"),
                true);
    }

    @Test
    public void testListFunctions() {
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, this.lib.number("1")));

        doExpressionTest(entries, "", "containsOnly([1, 2, 3], [1])",
                "FunctionInvocation(Name(containsOnly) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), ListLiteral(NumericLiteral(1))))",
                "boolean",
                "containsOnly(asList(number(\"1\"), number(\"2\"), number(\"3\")), asList(number(\"1\")))",
                this.lib.containsOnly(this.lib.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3")), this.lib.asList(this.lib.number("1"))),
                false);
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
        doExpressionTest(entries, "", "avg([1, 2, 3])",
                "FunctionInvocation(Name(avg) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3))))",
                "number",
                "avg(asList(number(\"1\"), number(\"2\"), number(\"3\")))",
                this.lib.avg(Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3"))),
                this.lib.number("2"));

        // function with type refinement
        doExpressionTest(entries, "", "append([1, 2, 3], 4)",
                "FunctionInvocation(Name(append) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), NumericLiteral(4)))",
                "ListType(number)",
                "append(asList(number(\"1\"), number(\"2\"), number(\"3\")), number(\"4\"))",
                this.lib.append(Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3")), this.lib.number("4")),
                Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3"), this.lib.number("4")));
        doExpressionTest(entries, "", "appendAll([1, 2, 3], [4, 5, 6])",
                "FunctionInvocation(Name(appendAll) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), ListLiteral(NumericLiteral(4),NumericLiteral(5),NumericLiteral(6))))",
                "ListType(number)",
                "appendAll(asList(number(\"1\"), number(\"2\"), number(\"3\")), asList(number(\"4\"), number(\"5\"), number(\"6\")))",
                this.lib.appendAll(Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3")), Arrays.asList(this.lib.number("4"), this.lib.number("5"), this.lib.number("6"))),
                Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3"), this.lib.number("4"), this.lib.number("5"), this.lib.number("6")));
        doExpressionTest(entries, "", "remove([1, 2, 3], 1)",
                "FunctionInvocation(Name(remove) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), NumericLiteral(1)))",
                "ListType(number)",
                "remove(asList(number(\"1\"), number(\"2\"), number(\"3\")), number(\"1\"))",
                this.lib.remove(Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3")), this.lib.number("1")),
                Arrays.asList(this.lib.number("2"), this.lib.number("3")));
        doExpressionTest(entries, "", "removeAll([1, 2, 3], [4, 5, 6])",
                "FunctionInvocation(Name(removeAll) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), ListLiteral(NumericLiteral(4),NumericLiteral(5),NumericLiteral(6))))",
                "ListType(number)",
                "removeAll(asList(number(\"1\"), number(\"2\"), number(\"3\")), asList(number(\"4\"), number(\"5\"), number(\"6\")))",
                this.lib.removeAll(Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3")), Arrays.asList(this.lib.number("4"), this.lib.number("5"), this.lib.number("6"))),
                Arrays.asList(this.lib.number("1"), this.lib.number("2"), this.lib.number("3")));
    }
}