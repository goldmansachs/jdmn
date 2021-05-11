/**
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

import com.gs.dmn.feel.analysis.semantics.SemanticError;
import com.gs.dmn.feel.analysis.semantics.type.*;
import com.gs.dmn.feel.interpreter.FEELInterpreterImpl;
import com.gs.dmn.feel.synthesis.FEELTranslatorImpl;
import com.gs.dmn.runtime.Context;
import com.gs.dmn.runtime.DMNRuntimeException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.gs.dmn.feel.analysis.semantics.type.BooleanType.BOOLEAN;
import static com.gs.dmn.feel.analysis.semantics.type.DateType.DATE;
import static com.gs.dmn.feel.analysis.semantics.type.NumberType.NUMBER;
import static com.gs.dmn.feel.analysis.semantics.type.StringType.STRING;
import static org.junit.Assert.assertEquals;

public class FEELProcessorTest extends AbstractFEELProcessorTest {
    @Before
    public void setUp() {
        this.feelTranslator = new FEELTranslatorImpl(dmnTransformer);
        this.feelInterpreter = new FEELInterpreterImpl(dmnInterpreter);
    }

    @Test
    public void testUnaryTests() {
        BigDecimal input = lib.number("1");
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
                "true",
                true,
                true);
    }

    @Test
    public void testPositiveUnaryTest() {
        BigDecimal inputNumber = lib.number("1");
        XMLGregorianCalendar inputDate = lib.date("2017-01-01");
        String inputString = "abc";
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("inputString", STRING, inputString),
                new EnvironmentEntry("inputNumber", NUMBER, inputNumber),
                new EnvironmentEntry("inputDate", DATE, inputDate)
        );

        //
        // OperatoTest
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
        doUnaryTestsTest(entries, "inputString", "contains(?, \"abc\")",
                "PositiveUnaryTests(OperatorTest(null,FunctionInvocation(Name(contains) -> PositionalParameters(Name(?), StringLiteral(\"abc\")))))",
                "TupleType(boolean)",
                "(contains(inputString, \"abc\"))",
                (lib.contains(inputString, "abc")),
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
    public void testForExpression() {
        testForExpressionTest("for i in 0..4 return if i = 0 then 1 else i * partial[-1]",
                "ForExpression(Iterator(i in RangeIteratorDomain(NumericLiteral(0), NumericLiteral(4))) -> IfExpression(Relational(=,Name(i),NumericLiteral(0)), NumericLiteral(1), Multiplication(*,Name(i),FilterExpression(Name(partial), ArithmeticNegation(NumericLiteral(1))))))",
                "ListType(number)",
                "rangeToList(number(\"0\"), number(\"4\")).stream().map(i -> (booleanEqual(numericEqual(i, number(\"0\")), Boolean.TRUE)) ? number(\"1\") : numericMultiply(i, (java.math.BigDecimal)(elementAt(partial, numericUnaryMinus(number(\"1\")))))).collect(Collectors.toList())",
                null,
                null
        );
        testForExpressionTest("for i in 4..2 return i",
                "ForExpression(Iterator(i in RangeIteratorDomain(NumericLiteral(4), NumericLiteral(2))) -> Name(i))",
                "ListType(number)",
                "rangeToList(number(\"4\"), number(\"2\")).stream().map(i -> i).collect(Collectors.toList())",
                lib.rangeToList(lib.number("4"), lib.number("2")).stream().map(i -> i).collect(Collectors.toList()),
                lib.asList(lib.number("4"), lib.number("3"), lib.number("2"))
        );
        testForExpressionTest("for i in 1..-1 return i",
                "ForExpression(Iterator(i in RangeIteratorDomain(NumericLiteral(1), ArithmeticNegation(NumericLiteral(1)))) -> Name(i))",
                "ListType(number)",
                "rangeToList(number(\"1\"), numericUnaryMinus(number(\"1\"))).stream().map(i -> i).collect(Collectors.toList())",
                lib.rangeToList(lib.number("1"), lib.numericUnaryMinus(lib.number("1"))).stream().map(i -> i).collect(Collectors.toList()),
                lib.asList(lib.number("1"), lib.number("0"), lib.number("-1"))
        );

        testForExpressionTest("for i in 1..2 return for j in [2, 3] return i+j",
                "ForExpression(Iterator(i in RangeIteratorDomain(NumericLiteral(1), NumericLiteral(2))) -> ForExpression(Iterator(j in ExpressionIteratorDomain(ListLiteral(NumericLiteral(2),NumericLiteral(3)))) -> Addition(+,Name(i),Name(j))))",
                "ListType(ListType(number))",
                "rangeToList(number(\"1\"), number(\"2\")).stream().map(i -> asList(number(\"2\"), number(\"3\")).stream().map(j -> numericAdd(i, j)).collect(Collectors.toList())).collect(Collectors.toList())",
                lib.rangeToList(lib.number("1"), lib.number("2")).stream().map(i -> lib.asList(lib.number("2"), lib.number("3")).stream().map(j -> lib.numericAdd(i, j)).collect(Collectors.toList())).collect(Collectors.toList()),
                Arrays.asList(Arrays.asList(lib.number("3"), lib.number("4")), Arrays.asList(lib.number("4"), lib.number("5"))));
        testForExpressionTest("for i in [1..2] return i",
                "ForExpression(Iterator(i in ExpressionIteratorDomain(IntervalTest(false,NumericLiteral(1),false,NumericLiteral(2)))) -> Name(i))",
                "ListType(number)",
                "rangeToList(false, number(\"1\"), false, number(\"2\")).stream().map(i -> i).collect(Collectors.toList())",
                lib.rangeToList(false, lib.number("1"), false, lib.number("2")).stream().map(i -> i).collect(Collectors.toList()),
                Arrays.asList(lib.number("1"), lib.number("2")));
        testForExpressionTest("for i in [1..2], j in [2..3] return i+j",
                "ForExpression(Iterator(i in ExpressionIteratorDomain(IntervalTest(false,NumericLiteral(1),false,NumericLiteral(2)))),Iterator(j in ExpressionIteratorDomain(IntervalTest(false,NumericLiteral(2),false,NumericLiteral(3)))) -> Addition(+,Name(i),Name(j)))",
                "ListType(number)",
                "rangeToList(false, number(\"1\"), false, number(\"2\")).stream().map(i -> rangeToList(false, number(\"2\"), false, number(\"3\")).stream().map(j -> numericAdd(i, j))).flatMap(x -> x).collect(Collectors.toList())",
                lib.rangeToList(false, lib.number("1"), false, lib.number("2")).stream().map(i ->
                        lib.rangeToList(false, lib.number("2"), false, lib.number("3")).stream().map(j ->
                                lib.numericAdd(i, j)))
                        .flatMap(x -> x)
                        .collect(Collectors.toList()),
                Arrays.asList(lib.number("3"), lib.number("4"), lib.number("4"), lib.number("5")));
        testForExpressionTest("for i in [1..2] return for j in [2..3] return i+j",
                "ForExpression(Iterator(i in ExpressionIteratorDomain(IntervalTest(false,NumericLiteral(1),false,NumericLiteral(2)))) -> ForExpression(Iterator(j in ExpressionIteratorDomain(IntervalTest(false,NumericLiteral(2),false,NumericLiteral(3)))) -> Addition(+,Name(i),Name(j))))",
                "ListType(ListType(number))",
                "rangeToList(false, number(\"1\"), false, number(\"2\")).stream().map(i -> rangeToList(false, number(\"2\"), false, number(\"3\")).stream().map(j -> numericAdd(i, j)).collect(Collectors.toList())).collect(Collectors.toList())",
                Arrays.asList(Arrays.asList(lib.number("3"), lib.number("4")), Arrays.asList(lib.number("4"), lib.number("5"))),
                Arrays.asList(Arrays.asList(lib.number("3"), lib.number("4")), Arrays.asList(lib.number("4"), lib.number("5"))));

        testForExpressionTest("for i in [1, 2] return i",
                "ForExpression(Iterator(i in ExpressionIteratorDomain(ListLiteral(NumericLiteral(1),NumericLiteral(2)))) -> Name(i))",
                "ListType(number)",
                "asList(number(\"1\"), number(\"2\")).stream().map(i -> i).collect(Collectors.toList())",
                Arrays.asList(lib.number("1"), lib.number("2")).stream().map(i -> i).collect(Collectors.toList()),
                Arrays.asList(lib.number("1"), lib.number("2")));
        testForExpressionTest("for i in [1, 2], j in [2, 3] return i+j",
                "ForExpression(Iterator(i in ExpressionIteratorDomain(ListLiteral(NumericLiteral(1),NumericLiteral(2)))),Iterator(j in ExpressionIteratorDomain(ListLiteral(NumericLiteral(2),NumericLiteral(3)))) -> Addition(+,Name(i),Name(j)))",
                "ListType(number)",
                "asList(number(\"1\"), number(\"2\")).stream().map(i -> asList(number(\"2\"), number(\"3\")).stream().map(j -> numericAdd(i, j))).flatMap(x -> x).collect(Collectors.toList())",
                Arrays.asList(lib.number("1"), lib.number("2")).stream().map(i ->
                        Arrays.asList(lib.number("2"), lib.number("3")).stream().map(j ->
                                lib.numericAdd(i, j)))
                        .flatMap(x -> x)
                        .collect(Collectors.toList()),
                Arrays.asList(lib.number("3"), lib.number("4"), lib.number("4"), lib.number("5")));
        testForExpressionTest("for i in [1, 2] return for j in [2, 3] return i+j",
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

    private void testForExpressionTest(String expressionText, String expectedAST, String expectedType, String expectedGeneratedCode, Object generatedCodeValue, Object expectedValue) {
        List<EnvironmentEntry> entries = Arrays.asList();
        doExpressionTest(entries, "", expressionText, expectedAST, expectedType, expectedGeneratedCode, generatedCodeValue, expectedValue);
        assertEquals("Value computed with generated code doesn't match the expected one", expectedValue, generatedCodeValue);
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
                "FunctionDefinition(FormalParameter(x, AnyType),FormalParameter(y, AnyType), Addition(+,Name(x),Name(y)), false)",
                "FEELFunctionType(FormalParameter(x, AnyType), FormalParameter(y, AnyType), AnyType, false)",
                null,
                null,
                null);

        doExpressionTest(entries, "", "function (x : feel.string, y : feel.string) external { " +
                        "java: {class : \"name\", methodSignature: \"signature\" } }",
                "FunctionDefinition(FormalParameter(x, string),FormalParameter(y, string), Context(ContextEntry(ContextEntryKey(java) = Context(ContextEntry(ContextEntryKey(class) = StringLiteral(\"name\")),ContextEntry(ContextEntryKey(methodSignature) = StringLiteral(\"signature\"))))), true)",
                "FEELFunctionType(FormalParameter(x, string), FormalParameter(y, string), ContextType(java = ContextType(class = string, methodSignature = string)), true)",
                null,
                null,
                null);
        doExpressionTest(entries, "", "function (x , y) external { " +
                        "java: {class : \"name\", methodSignature: \"signature\" } }",
                "FunctionDefinition(FormalParameter(x, AnyType),FormalParameter(y, AnyType), Context(ContextEntry(ContextEntryKey(java) = Context(ContextEntry(ContextEntryKey(class) = StringLiteral(\"name\")),ContextEntry(ContextEntryKey(methodSignature) = StringLiteral(\"signature\"))))), true)",
                "FEELFunctionType(FormalParameter(x, AnyType), FormalParameter(y, AnyType), AnyType, true)",
                null,
                null,
                null);
        doExpressionTest(entries, "", "function(a: feel.number, b: feel.number) external {" +
                        "java: {class: \"com.gs.dmn.simple_decision_with_user_function.Sum\", methodSignature: \"add(a, b)\", returnType : \"number\"}" +
                        "}",
                "FunctionDefinition(FormalParameter(a, number),FormalParameter(b, number), Context(ContextEntry(ContextEntryKey(java) = Context(ContextEntry(ContextEntryKey(class) = StringLiteral(\"com.gs.dmn.simple_decision_with_user_function.Sum\")),ContextEntry(ContextEntryKey(methodSignature) = StringLiteral(\"add(a, b)\")),ContextEntry(ContextEntryKey(returnType) = StringLiteral(\"number\"))))), true)",
                "FEELFunctionType(FormalParameter(a, number), FormalParameter(b, number), ContextType(java = ContextType(class = string, methodSignature = string, returnType = string)), true)",
                null,
                null,
                null
        );
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
    public void testInstanceOfExpression() {
        List<EnvironmentEntry> entries = Arrays.asList(
                new EnvironmentEntry("input", NUMBER, lib.number("1")));

        doExpressionTest(entries, "", "3 instance of number",
                "InstanceOfExpression(NumericLiteral(3), QualifiedName(number, 1))",
                "boolean",
                "number(\"3\") instanceof java.math.BigDecimal",
                lib.number("3") instanceof java.math.BigDecimal,
                true);
        doExpressionTest(entries, "", "\"abc\" instance of string",
                "InstanceOfExpression(StringLiteral(\"abc\"), QualifiedName(string, 1))",
                "boolean",
                "\"abc\" instanceof String",
                "abc" instanceof String,
                true);
        doExpressionTest(entries, "", "true instance of boolean",
                "InstanceOfExpression(BooleanLiteral(true), QualifiedName(boolean, 1))",
                "boolean",
                "Boolean.TRUE instanceof Boolean",
                Boolean.TRUE instanceof Boolean,
                true);
        doExpressionTest(entries, "", "date(\"2011-01-03\") instance of date",
                "InstanceOfExpression(DateTimeLiteral(date, \"2011-01-03\"), QualifiedName(date, 1))",
                "boolean",
                "date(\"2011-01-03\") instanceof javax.xml.datatype.XMLGregorianCalendar",
                lib.date("2011-01-03") instanceof javax.xml.datatype.XMLGregorianCalendar,
                true);
        doExpressionTest(entries, "", "time(\"12:00:00Z\") instance of time",
                "InstanceOfExpression(DateTimeLiteral(time, \"12:00:00Z\"), QualifiedName(time, 1))",
                "boolean",
                "time(\"12:00:00Z\") instanceof javax.xml.datatype.XMLGregorianCalendar",
                lib.time("12:00:00Z") instanceof javax.xml.datatype.XMLGregorianCalendar,
                true);
        doExpressionTest(entries, "", "date and time(\"2016-03-01T12:00:00Z\") instance of date and time",
                "InstanceOfExpression(DateTimeLiteral(date and time, \"2016-03-01T12:00:00Z\"), QualifiedName(date and time, 1))",
                "boolean",
                "dateAndTime(\"2016-03-01T12:00:00Z\") instanceof javax.xml.datatype.XMLGregorianCalendar",
                lib.dateAndTime("2016-03-01T12:00:00Z") instanceof javax.xml.datatype.XMLGregorianCalendar,
                true);
        doExpressionTest(entries, "", "duration(\"P1Y1M\") instance of years and months duration",
                "InstanceOfExpression(DateTimeLiteral(duration, \"P1Y1M\"), QualifiedName(years and months duration, 1))",
                "boolean",
                "duration(\"P1Y1M\") instanceof javax.xml.datatype.Duration",
                lib.duration("P1Y1M") instanceof javax.xml.datatype.Duration,
                true);
        doExpressionTest(entries, "", "duration(\"P1DT1H\") instance of days and time duration",
                "InstanceOfExpression(DateTimeLiteral(duration, \"P1DT1H\"), QualifiedName(days and time duration, 1))",
                "boolean",
                "duration(\"P1DT1H\") instanceof javax.xml.datatype.Duration",
                lib.duration("P1Y1M") instanceof javax.xml.datatype.Duration,
                true);
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

    @Override
    @Test
    public void testComparisonExpression() {
        super.testComparisonExpression();

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
    }

    @Test
    public void testList() {
        Object number = lib.number("1");
        List<Object> list = Arrays.asList(lib.number("1"));

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
                "ListType(AnyType)",
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
                "ListType(AnyType)",
                "asList(number(\"1\"), numericLessThan(number, number(\"2\")), booleanAnd(numericGreaterEqualThan(number, number(\"3\")), numericLessEqualThan(number, number(\"4\"))))",
                null,
                null);

        doExpressionTest(expressionPairs, "", "true[0]",
                "FilterExpression(BooleanLiteral(true), NumericLiteral(0))",
                "boolean",
                "(Boolean)(elementAt(asList(Boolean.TRUE), number(\"0\")))",
                (Boolean)(lib.elementAt(lib.asList(Boolean.TRUE), lib.number("0"))),
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
        Object dateInput = lib.date("2017-01-03");
        String enumerationInput = "e1";
        Object number = lib.number("1");
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
    public void testLogicExpression() {
        boolean booleanA = true;
        boolean booleanB = false;
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
    public void testBetweenExpression() {
        BigDecimal i = lib.number("1");
        BigDecimal a = lib.number("1");
        BigDecimal b = lib.number("1");
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

        doExpressionTest(entries, "", "\"a\" between \"b\" and \"d\"",
                "BetweenExpression(StringLiteral(\"a\"), StringLiteral(\"b\"), StringLiteral(\"d\"))",
                "boolean",
                "booleanAnd(stringLessEqualThan(\"b\", \"a\"), stringLessEqualThan(\"a\", \"d\"))",
                lib.booleanAnd(lib.stringLessEqualThan("b", "a"), lib.stringLessEqualThan("a", "d")),
                false);
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
        doExpressionTest(entries, "", "\"b\" in [[\"f\"..\"h\"], [\"a\"..\"c\"]]",
                "InExpression(StringLiteral(\"b\"), ListTest(ListLiteral(IntervalTest(false,StringLiteral(\"f\"),false,StringLiteral(\"h\")),IntervalTest(false,StringLiteral(\"a\"),false,StringLiteral(\"c\")))))",
                "boolean",
                "(listContains(asList(booleanAnd(stringGreaterEqualThan(\"b\", \"f\"), stringLessEqualThan(\"b\", \"h\")), booleanAnd(stringGreaterEqualThan(\"b\", \"a\"), stringLessEqualThan(\"b\", \"c\"))), true))",
                (lib.listContains(lib.asList(lib.booleanAnd(lib.stringGreaterEqualThan("b", "f"), lib.stringLessEqualThan("b", "h")), lib.booleanAnd(lib.stringGreaterEqualThan("b", "a"), lib.stringLessEqualThan("b", "c"))), true)),
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
        List<BigDecimal> source = Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3"));
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
                lib.asList(new com.gs.dmn.runtime.Context().add("item", lib.number("1")), new com.gs.dmn.runtime.Context().add("item", lib.number("2")), new com.gs.dmn.runtime.Context().add("item", lib.number("3"))).stream().filter(item -> lib.numericGreaterEqualThan(((java.math.BigDecimal)((com.gs.dmn.runtime.Context)item).get("item")), lib.number("2"))).collect(Collectors.toList()),
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
                employeeValue.stream().filter(item -> lib.numericEqual(((java.math.BigDecimal)((com.gs.dmn.runtime.Context)item).get("dept")), lib.number("20"))).collect(Collectors.toList()),
                Arrays.asList(employeeValue.get(1), employeeValue.get(2)));
        doExpressionTest(entries, "", "employee[item.dept = 20].name",
                "PathExpression(FilterExpression(Name(employee), Relational(=,PathExpression(Name(item), dept),NumericLiteral(20))), name)",
                "ListType(string)",
                "employee.stream().filter(item -> numericEqual(((java.math.BigDecimal)((com.gs.dmn.runtime.Context)item).get(\"dept\")), number(\"20\"))).collect(Collectors.toList()).stream().map(x -> ((String)((com.gs.dmn.runtime.Context)x).get(\"name\"))).collect(Collectors.toList())",
                employeeValue.stream().filter(item -> lib.numericEqual(((java.math.BigDecimal)((com.gs.dmn.runtime.Context)item).get("dept")), lib.number("20"))).collect(Collectors.toList()).stream().map(x -> ((String)((com.gs.dmn.runtime.Context)x).get("name"))).collect(Collectors.toList()),
                Arrays.asList(employeeValue.get(1).get("name"), employeeValue.get(2).get("name")));
        doExpressionTest(entries, "", "employee[dept = 20].name",
                "PathExpression(FilterExpression(Name(employee), Relational(=,PathExpression(Name(item), dept),NumericLiteral(20))), name)",
                "ListType(string)",
                "employee.stream().filter(item -> numericEqual(((java.math.BigDecimal)((com.gs.dmn.runtime.Context)item).get(\"dept\")), number(\"20\"))).collect(Collectors.toList()).stream().map(x -> ((String)((com.gs.dmn.runtime.Context)x).get(\"name\"))).collect(Collectors.toList())",
                employeeValue.stream().filter(item -> lib.numericEqual(((java.math.BigDecimal)((com.gs.dmn.runtime.Context)item).get("dept")), lib.number("20"))).collect(Collectors.toList()).stream().map(x -> ((String)((com.gs.dmn.runtime.Context)x).get("name"))).collect(Collectors.toList()),
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
                lib.asList(new com.gs.dmn.runtime.Context().add("x", lib.number("1")).add("y", lib.number("2")), new com.gs.dmn.runtime.Context().add("x", lib.number("2")).add("y", lib.number("3"))).stream().filter(item -> lib.numericEqual(((java.math.BigDecimal)((com.gs.dmn.runtime.Context)item).get("x")), lib.number("1"))).collect(Collectors.toList()),
                Arrays.asList(new com.gs.dmn.runtime.Context().add("x", lib.number("1")).add("y", lib.number("2"))));
    }

    @Test
    public void testFunctionInvocation() {
        String input = "abc";
        BigDecimal score1 = this.lib.number("1");
        BigDecimal score2 = this.lib.number("2");
        BigDecimal score3 = this.lib.number("3");
        BigDecimal score4 = this.lib.number("4");
        BigDecimal score5 = this.lib.number("5");
        BigDecimal score6 = this.lib.number("6");
        BigDecimal score7 = this.lib.number("7");
        BigDecimal score8 = this.lib.number("8");
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
                "FunctionInvocation(Name(sort) -> PositionalParameters(ListLiteral(Name(score5),Name(score6),Name(score7),Name(score8)), FunctionDefinition(FormalParameter(x, number),FormalParameter(y, number), Relational(<,Name(y),Name(x)), false)))",
                "ListType(number)",
                "sort(asList(score5, score6, score7, score8), new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args) {java.math.BigDecimal x = (java.math.BigDecimal)args[0]; java.math.BigDecimal y = (java.math.BigDecimal)args[1];return numericLessThan(y, x);}})",
                this.lib.sort(this.lib.asList(score5, score6, score7, score8), new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args) {java.math.BigDecimal x = (java.math.BigDecimal)args[0]; java.math.BigDecimal y = (java.math.BigDecimal)args[1];return lib.numericLessThan(y, x);}}),
                this.lib.asList(this.lib.number("8"), this.lib.number("7"), this.lib.number("6"), this.lib.number("5")));
        doExpressionTest(entries, "", sublist2,
                "FunctionInvocation(Name(sublist) -> PositionalParameters(FunctionInvocation(Name(sort) -> PositionalParameters(ListLiteral(Name(score5),Name(score6),Name(score7),Name(score8)), FunctionDefinition(FormalParameter(x, number),FormalParameter(y, number), Relational(<,Name(y),Name(x)), false))), NumericLiteral(1), NumericLiteral(2)))",
                "ListType(number)",
                "sublist(sort(asList(score5, score6, score7, score8), new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args) {java.math.BigDecimal x = (java.math.BigDecimal)args[0]; java.math.BigDecimal y = (java.math.BigDecimal)args[1];return numericLessThan(y, x);}}), number(\"1\"), number(\"2\"))",
                this.lib.sublist(this.lib.sort(this.lib.asList(score5, score6, score7, score8), new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args) {java.math.BigDecimal x = (java.math.BigDecimal)args[0]; java.math.BigDecimal y = (java.math.BigDecimal)args[1];return lib.numericLessThan(y, x);}}), this.lib.number("1"), this.lib.number("2")),
                this.lib.asList(this.lib.number("8"), this.lib.number("7")));
        doExpressionTest(entries, "", sublist1,
                "FunctionInvocation(Name(sublist) -> PositionalParameters(FunctionInvocation(Name(sort) -> PositionalParameters(FunctionInvocation(Name(concatenate) -> PositionalParameters(ListLiteral(Name(score1),Name(score2),Name(score3),Name(score4)), FunctionInvocation(Name(sublist) -> PositionalParameters(FunctionInvocation(Name(sort) -> PositionalParameters(ListLiteral(Name(score5),Name(score6),Name(score7),Name(score8)), FunctionDefinition(FormalParameter(x, number),FormalParameter(y, number), Relational(<,Name(y),Name(x)), false))), NumericLiteral(1), NumericLiteral(2))))), FunctionDefinition(FormalParameter(x, number),FormalParameter(y, number), Relational(<,Name(y),Name(x)), false))), NumericLiteral(1), NumericLiteral(3)))",
                "ListType(number)",
                "sublist(sort(concatenate(asList(score1, score2, score3, score4), sublist(sort(asList(score5, score6, score7, score8), new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args) {java.math.BigDecimal x = (java.math.BigDecimal)args[0]; java.math.BigDecimal y = (java.math.BigDecimal)args[1];return numericLessThan(y, x);}}), number(\"1\"), number(\"2\"))), new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args) {java.math.BigDecimal x = (java.math.BigDecimal)args[0]; java.math.BigDecimal y = (java.math.BigDecimal)args[1];return numericLessThan(y, x);}}), number(\"1\"), number(\"3\"))",
                this.lib.sublist(this.lib.sort(this.lib.concatenate(this.lib.asList(score1, score2, score3, score4), this.lib.sublist(this.lib.sort(this.lib.asList(score5, score6, score7, score8), new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args) {java.math.BigDecimal x = (java.math.BigDecimal)args[0]; java.math.BigDecimal y = (java.math.BigDecimal)args[1];return lib.numericLessThan(y, x);}}), this.lib.number("1"), this.lib.number("2"))), new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args) {java.math.BigDecimal x = (java.math.BigDecimal)args[0]; java.math.BigDecimal y = (java.math.BigDecimal)args[1];return lib.numericLessThan(y, x);}}), this.lib.number("1"), this.lib.number("3")),
                this.lib.asList(this.lib.number("8"), this.lib.number("7"), this.lib.number("4")));
        doExpressionTest(entries, "", forExp,
                "ForExpression(Iterator(i in ExpressionIteratorDomain(FunctionInvocation(Name(sublist) -> PositionalParameters(FunctionInvocation(Name(sort) -> PositionalParameters(FunctionInvocation(Name(concatenate) -> PositionalParameters(ListLiteral(Name(score1),Name(score2),Name(score3),Name(score4)), FunctionInvocation(Name(sublist) -> PositionalParameters(FunctionInvocation(Name(sort) -> PositionalParameters(ListLiteral(Name(score5),Name(score6),Name(score7),Name(score8)), FunctionDefinition(FormalParameter(x, number),FormalParameter(y, number), Relational(<,Name(y),Name(x)), false))), NumericLiteral(1), NumericLiteral(2))))), FunctionDefinition(FormalParameter(x, number),FormalParameter(y, number), Relational(<,Name(y),Name(x)), false))), NumericLiteral(1), NumericLiteral(3))))) -> Multiplication(*,Name(i),Name(i)))",
                "ListType(number)",
                "sublist(sort(concatenate(asList(score1, score2, score3, score4), sublist(sort(asList(score5, score6, score7, score8), new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args) {java.math.BigDecimal x = (java.math.BigDecimal)args[0]; java.math.BigDecimal y = (java.math.BigDecimal)args[1];return numericLessThan(y, x);}}), number(\"1\"), number(\"2\"))), new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args) {java.math.BigDecimal x = (java.math.BigDecimal)args[0]; java.math.BigDecimal y = (java.math.BigDecimal)args[1];return numericLessThan(y, x);}}), number(\"1\"), number(\"3\")).stream().map(i -> numericMultiply(i, i)).collect(Collectors.toList())",
                this.lib.sublist(this.lib.sort(this.lib.concatenate(this.lib.asList(score1, score2, score3, score4), this.lib.sublist(this.lib.sort(this.lib.asList(score5, score6, score7, score8), new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args) {java.math.BigDecimal x = (java.math.BigDecimal)args[0]; java.math.BigDecimal y = (java.math.BigDecimal)args[1];return lib.numericLessThan(y, x);}}), this.lib.number("1"), this.lib.number("2"))), new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args) {java.math.BigDecimal x = (java.math.BigDecimal)args[0]; java.math.BigDecimal y = (java.math.BigDecimal)args[1];return lib.numericLessThan(y, x);}}), this.lib.number("1"), this.lib.number("3")).stream().map(i -> lib.numericMultiply(i, i)).collect(Collectors.toList()),
                this.lib.asList(this.lib.number("64"), this.lib.number("49"), this.lib.number("16")));
        doExpressionTest(entries, "", forExp,
                "ForExpression(Iterator(i in ExpressionIteratorDomain(FunctionInvocation(Name(sublist) -> PositionalParameters(FunctionInvocation(Name(sort) -> PositionalParameters(FunctionInvocation(Name(concatenate) -> PositionalParameters(ListLiteral(Name(score1),Name(score2),Name(score3),Name(score4)), FunctionInvocation(Name(sublist) -> PositionalParameters(FunctionInvocation(Name(sort) -> PositionalParameters(ListLiteral(Name(score5),Name(score6),Name(score7),Name(score8)), FunctionDefinition(FormalParameter(x, number),FormalParameter(y, number), Relational(<,Name(y),Name(x)), false))), NumericLiteral(1), NumericLiteral(2))))), FunctionDefinition(FormalParameter(x, number),FormalParameter(y, number), Relational(<,Name(y),Name(x)), false))), NumericLiteral(1), NumericLiteral(3))))) -> Multiplication(*,Name(i),Name(i)))",
                "ListType(number)",
                "sublist(sort(concatenate(asList(score1, score2, score3, score4), sublist(sort(asList(score5, score6, score7, score8), new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args) {java.math.BigDecimal x = (java.math.BigDecimal)args[0]; java.math.BigDecimal y = (java.math.BigDecimal)args[1];return numericLessThan(y, x);}}), number(\"1\"), number(\"2\"))), new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args) {java.math.BigDecimal x = (java.math.BigDecimal)args[0]; java.math.BigDecimal y = (java.math.BigDecimal)args[1];return numericLessThan(y, x);}}), number(\"1\"), number(\"3\")).stream().map(i -> numericMultiply(i, i)).collect(Collectors.toList())",
                this.lib.sublist(this.lib.sort(this.lib.concatenate(this.lib.asList(score1, score2, score3, score4), this.lib.sublist(this.lib.sort(this.lib.asList(score5, score6, score7, score8), new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args) {java.math.BigDecimal x = (java.math.BigDecimal)args[0]; java.math.BigDecimal y = (java.math.BigDecimal)args[1];return lib.numericLessThan(y, x);}}), this.lib.number("1"), this.lib.number("2"))), new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args) {java.math.BigDecimal x = (java.math.BigDecimal)args[0]; java.math.BigDecimal y = (java.math.BigDecimal)args[1];return lib.numericLessThan(y, x);}}), this.lib.number("1"), this.lib.number("3")).stream().map(i -> lib.numericMultiply(i, i)).collect(Collectors.toList()),
                this.lib.asList(this.lib.number("64"), this.lib.number("49"), this.lib.number("16")));
        doExpressionTest(entries, "", sum,
                "FunctionInvocation(Name(sum) -> PositionalParameters(ForExpression(Iterator(i in ExpressionIteratorDomain(FunctionInvocation(Name(sublist) -> PositionalParameters(FunctionInvocation(Name(sort) -> PositionalParameters(FunctionInvocation(Name(concatenate) -> PositionalParameters(ListLiteral(Name(score1),Name(score2),Name(score3),Name(score4)), FunctionInvocation(Name(sublist) -> PositionalParameters(FunctionInvocation(Name(sort) -> PositionalParameters(ListLiteral(Name(score5),Name(score6),Name(score7),Name(score8)), FunctionDefinition(FormalParameter(x, number),FormalParameter(y, number), Relational(<,Name(y),Name(x)), false))), NumericLiteral(1), NumericLiteral(2))))), FunctionDefinition(FormalParameter(x, number),FormalParameter(y, number), Relational(<,Name(y),Name(x)), false))), NumericLiteral(1), NumericLiteral(3))))) -> Multiplication(*,Name(i),Name(i)))))",
                "number",
                "sum(sublist(sort(concatenate(asList(score1, score2, score3, score4), sublist(sort(asList(score5, score6, score7, score8), new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args) {java.math.BigDecimal x = (java.math.BigDecimal)args[0]; java.math.BigDecimal y = (java.math.BigDecimal)args[1];return numericLessThan(y, x);}}), number(\"1\"), number(\"2\"))), new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args) {java.math.BigDecimal x = (java.math.BigDecimal)args[0]; java.math.BigDecimal y = (java.math.BigDecimal)args[1];return numericLessThan(y, x);}}), number(\"1\"), number(\"3\")).stream().map(i -> numericMultiply(i, i)).collect(Collectors.toList()))",
                this.lib.sum(this.lib.sublist(this.lib.sort(this.lib.concatenate(this.lib.asList(score1, score2, score3, score4), this.lib.sublist(this.lib.sort(this.lib.asList(score5, score6, score7, score8), new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args) {java.math.BigDecimal x = (java.math.BigDecimal)args[0]; java.math.BigDecimal y = (java.math.BigDecimal)args[1];return lib.numericLessThan(y, x);}}), this.lib.number("1"), this.lib.number("2"))), new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args) {java.math.BigDecimal x = (java.math.BigDecimal)args[0]; java.math.BigDecimal y = (java.math.BigDecimal)args[1];return lib.numericLessThan(y, x);}}), this.lib.number("1"), this.lib.number("3")).stream().map(i -> lib.numericMultiply(i, i)).collect(Collectors.toList())),
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

    @Ignore
    @Test(expected = DMNRuntimeException.class)
    public void testFunctionInvocationWhenMultipleMatch() {
        List<EnvironmentEntry> entries = Arrays.asList();

        // Multiple matches for date(null)
        doExpressionTest(entries, "", "date(null)",
                "FunctionInvocation(Name(date) -> PositionalParameters(NullLiteral()))",
                "date",
                "contains(null)",
                lib.date((XMLGregorianCalendar) null),
                true);
    }

    @Test
    public void testSortInvocation() {
        List<EnvironmentEntry> entries = Arrays.asList(
        );

        doExpressionTest(entries, "", "sort([3,1,4,5,2], function(x: feel.number, y: feel.number) x < y)",
                "FunctionInvocation(Name(sort) -> PositionalParameters(ListLiteral(NumericLiteral(3),NumericLiteral(1),NumericLiteral(4),NumericLiteral(5),NumericLiteral(2)), FunctionDefinition(FormalParameter(x, number),FormalParameter(y, number), Relational(<,Name(x),Name(y)), false)))",
                "ListType(number)",
                "sort(asList(number(\"3\"), number(\"1\"), number(\"4\"), number(\"5\"), number(\"2\")), new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args) {java.math.BigDecimal x = (java.math.BigDecimal)args[0]; java.math.BigDecimal y = (java.math.BigDecimal)args[1];return numericLessThan(x, y);}})",
                null,
                null
        );
    }

    @Test
    public void testDateAndTimeProperties() {
        List<EnvironmentEntry> entries = Arrays.asList(
        );

        doExpressionTest(entries, "", "date and time(\"2018-12-10T10:30:00\").time offset",
                "PathExpression(DateTimeLiteral(date and time, \"2018-12-10T10:30:00\"), time offset)",
                "days and time duration",
                "timeOffset(dateAndTime(\"2018-12-10T10:30:00\"))",
                lib.timeOffset(lib.dateAndTime("2018-12-10T10:30:00")),
                null
        );
        doExpressionTest(entries, "", "date and time(\"2018-12-10T10:30:00@Etc/UTC\").timezone",
                "PathExpression(DateTimeLiteral(date and time, \"2018-12-10T10:30:00@Etc/UTC\"), timezone)",
                "string",
                "timezone(dateAndTime(\"2018-12-10T10:30:00@Etc/UTC\"))",
                lib.timezone(lib.dateAndTime("2018-12-10T10:30:00@Etc/UTC")),
                "Etc/UTC"
        );
        doExpressionTest(entries, "", "date and time(\"2018-12-10T10:30:00\").timezone",
                "PathExpression(DateTimeLiteral(date and time, \"2018-12-10T10:30:00\"), timezone)",
                "string",
                "timezone(dateAndTime(\"2018-12-10T10:30:00\"))",
                lib.timezone(lib.dateAndTime("2018-12-10T10:30:00")),
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
    public void testPathExpression() {
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
                lib.weekday(lib.date("2018-12-10")),
                lib.number("1"));
        doExpressionTest(entries, "", "date and time(\"2018-12-10T10:30:01\").weekday",
                "PathExpression(DateTimeLiteral(date and time, \"2018-12-10T10:30:01\"), weekday)",
                "number",
                "weekday(dateAndTime(\"2018-12-10T10:30:01\"))",
                lib.weekday(lib.dateAndTime("2018-12-10T10:30:01")),
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
                lib.hour(lib.dateAndTime("2018-12-10T10:30:01")),
                lib.number("10"));
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
                lib.round(lib.number("5.125"), lib.number("2"), "up"),
                lib.number("5.13"));
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

        doExpressionTest(entries, "", "substring(\"abc\", 3)",
                "FunctionInvocation(Name(substring) -> PositionalParameters(StringLiteral(\"abc\"), NumericLiteral(3)))",
                "string",
                "substring(\"abc\", number(\"3\"))",
                lib.substring("abc", lib.number("3")),
                "c");
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
                "ListType(number)",
                "sublist(asList(number(\"1\"), number(\"2\"), number(\"3\")), number(\"2\"), number(\"1\"))",
                lib.sublist(Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3")), lib.number("2"), lib.number("1")),
                Arrays.asList(lib.number("2")));
        doExpressionTest(entries, "", "sublist([1, 2, 3], 2)",
                "FunctionInvocation(Name(sublist) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), NumericLiteral(2)))",
                "ListType(number)",
                "sublist(asList(number(\"1\"), number(\"2\"), number(\"3\")), number(\"2\"))",
                lib.sublist(Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3")), lib.number("2")),
                Arrays.asList(lib.number("2"), lib.number("3")));
        doExpressionTest(entries, "", "append([1, 2, 3], 4)",
                "FunctionInvocation(Name(append) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), NumericLiteral(4)))",
                "ListType(number)",
                "append(asList(number(\"1\"), number(\"2\"), number(\"3\")), number(\"4\"))",
                lib.append(Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3")), lib.number("4")),
                Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3"), lib.number("4")));

        doExpressionTest(entries, "", "concatenate([1, 2, 3], [4, 5, 6])",
                "FunctionInvocation(Name(concatenate) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), ListLiteral(NumericLiteral(4),NumericLiteral(5),NumericLiteral(6))))",
                "ListType(number)",
                "concatenate(asList(number(\"1\"), number(\"2\"), number(\"3\")), asList(number(\"4\"), number(\"5\"), number(\"6\")))",
                lib.concatenate(Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3")), Arrays.asList(lib.number("4"), lib.number("5"), lib.number("6"))),
                Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3"), lib.number("4"), lib.number("5"), lib.number("6")));
        doExpressionTest(entries, "", "insert before([1, 2, 3], 1, 4)",
                "FunctionInvocation(Name(insert before) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), NumericLiteral(1), NumericLiteral(4)))",
                "ListType(number)",
                "insertBefore(asList(number(\"1\"), number(\"2\"), number(\"3\")), number(\"1\"), number(\"4\"))",
                lib.insertBefore(Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3")), lib.number("1"), lib.number("4")),
                Arrays.asList(lib.number("4"), lib.number("1"), lib.number("2"), lib.number("3")));
        doExpressionTest(entries, "", "remove([1, 2, 3], 1)",
                "FunctionInvocation(Name(remove) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), NumericLiteral(1)))",
                "ListType(number)",
                "remove(asList(number(\"1\"), number(\"2\"), number(\"3\")), number(\"1\"))",
                lib.remove(Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3")), lib.number("1")),
                Arrays.asList(lib.number("2"), lib.number("3")));
        doExpressionTest(entries, "", "reverse([1, 2, 3])",
                "FunctionInvocation(Name(reverse) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3))))",
                "ListType(number)",
                "reverse(asList(number(\"1\"), number(\"2\"), number(\"3\")))",
                lib.reverse(Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3"))),
                Arrays.asList(lib.number("3"), lib.number("2"), lib.number("1")));
        doExpressionTest(entries, "", "index of([1, 2, 3], 3)",
                "FunctionInvocation(Name(index of) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), NumericLiteral(3)))",
                "ListType(number)",
                "indexOf(asList(number(\"1\"), number(\"2\"), number(\"3\")), number(\"3\"))",
                lib.indexOf(Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3")), lib.number("3")),
                Arrays.asList(lib.number("3")));
        doExpressionTest(entries, "", "union([1, 2, 3], [4, 5, 6])",
                "FunctionInvocation(Name(union) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3)), ListLiteral(NumericLiteral(4),NumericLiteral(5),NumericLiteral(6))))",
                "ListType(number)",
                "union(asList(number(\"1\"), number(\"2\"), number(\"3\")), asList(number(\"4\"), number(\"5\"), number(\"6\")))",
                lib.union(Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3")), Arrays.asList(lib.number("4"), lib.number("5"), lib.number("6"))),
                Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3"), lib.number("4"), lib.number("5"), lib.number("6")));
        doExpressionTest(entries, "", "distinct values([1, 2, 3])",
                "FunctionInvocation(Name(distinct values) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3))))",
                "ListType(number)",
                "distinctValues(asList(number(\"1\"), number(\"2\"), number(\"3\")))",
                lib.distinctValues(Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3"))),
                Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3")));
        doExpressionTest(entries, "", "flatten([1, 2, 3])",
                "FunctionInvocation(Name(flatten) -> PositionalParameters(ListLiteral(NumericLiteral(1),NumericLiteral(2),NumericLiteral(3))))",
                "ListType(number)",
                "flatten(asList(number(\"1\"), number(\"2\"), number(\"3\")))",
                lib.flatten(Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3"))),
                Arrays.asList(lib.number("1"), lib.number("2"), lib.number("3")));
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
                null,
                null,
                null);
    }
}