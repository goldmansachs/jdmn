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
package com.gs.dmn.feel.analysis.syntax;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.dialect.JavaTimeDMNDialectDefinition;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.el.synthesis.ELTranslator;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.test.UnaryTests;
import com.gs.dmn.transformation.InputParameters;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FEELParserTest {
    private final ELTranslator<Type, DMNContext> feelTranslator;

    public FEELParserTest() {
        JavaTimeDMNDialectDefinition dialectDefinition = new JavaTimeDMNDialectDefinition();
        DMNModelRepository repository = new DMNModelRepository();

        this.feelTranslator = dialectDefinition.createFEELTranslator(repository, new InputParameters());
    }

    //
    // Test UnaryTests
    //
    @ParameterizedTest
    @CsvFileSource(resources = "/feel/parsing/test/UnaryTestsTest.csv", numLinesToSkip = 1, delimiter = '|')
    public void testUnaryTests(String input, String ast) {
        doUnaryTestsTest(input, ast);
    }

    //
    // Test Textual Expressions
    //
    @ParameterizedTest
    @CsvFileSource(resources = "/feel/parsing/expression/textual/TextualExpressionsTest.csv", numLinesToSkip = 1, delimiter = '|')
    public void tesTextualExpressions(String input, String ast) {
        doTextualExpressionsTest(input, ast);
    }

    //
    // Test Boxed Expressions
    //
    @ParameterizedTest
    @CsvFileSource(resources = "/feel/parsing/expression/boxed/BoxedExpressionTest.csv", numLinesToSkip = 1, delimiter = '|')
    public void testBoxedExpressions(String input, String ast) {
        doBoxedExpressionTest(input, ast);
    }

    //
    // Test Expressions
    //
    @ParameterizedTest
    @CsvFileSource(resources = "/feel/parsing/expression/textual/ArithmeticExpressionTest.csv", numLinesToSkip = 1, delimiter = '|')
    public void testArithmeticExpressions(String input, String ast) {
        doExpressionTest(input, ast);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/feel/parsing/expression/textual/ComparisonExpressionTest.csv", numLinesToSkip = 1, delimiter = '|')
    public void tesComparisonExpression(String input, String ast) {
        doExpressionTest(input, ast);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/feel/parsing/expression/textual/ConversionFunctionsTest.csv", numLinesToSkip = 1, delimiter = '|')
    public void testConversionFunctions(String input, String ast) {
        doExpressionTest(input, ast);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/feel/parsing/expression/textual/ForExpressionTest.csv", numLinesToSkip = 1, delimiter = '|')
    public void testForExpressions(String input, String ast) {
        doExpressionTest(input, ast);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/feel/parsing/expression/textual/IfExpressionTest.csv", numLinesToSkip = 1, delimiter = '|')
    public void testIfExpressions(String input, String ast) {
        doExpressionTest(input, ast);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/feel/parsing/expression/textual/InstanceOfExpressionTest.csv", numLinesToSkip = 1, delimiter = '|')
    public void testInstanceOfExpressions(String input, String ast) {
        doExpressionTest(input, ast);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/feel/parsing/expression/textual/ListFunctionsTest.csv", numLinesToSkip = 1, delimiter = '|')
    public void testListFunctions(String input, String ast) {
        doExpressionTest(input, ast);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/feel/parsing/expression/textual/LogicExpressionTest.csv", numLinesToSkip = 1, delimiter = '|')
    public void testLogicExpressions(String input, String ast) {
        doExpressionTest(input, ast);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/feel/parsing/expression/textual/NumericFunctionsTest.csv", numLinesToSkip = 1, delimiter = '|')
    public void testNumericFunctions(String input, String ast) {
        doExpressionTest(input, ast);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/feel/parsing/expression/textual/PostfixExpressionTest.csv", numLinesToSkip = 1, delimiter = '|')
    public void testPostfixExpressions(String input, String ast) {
        doExpressionTest(input, ast);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/feel/parsing/expression/textual/PrimaryExpressionTest.csv", numLinesToSkip = 1, delimiter = '|')
    public void testPrimaryExpressions(String input, String ast) {
        doExpressionTest(input, ast);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/feel/parsing/expression/textual/QuantifiedExpressionTest.csv", numLinesToSkip = 1, delimiter = '|')
    public void testQuantifiedExpressions(String input, String ast) {
        doExpressionTest(input, ast);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/feel/parsing/expression/textual/StringFunctionsTest.csv", numLinesToSkip = 1, delimiter = '|')
    public void testStringFunctions(String input, String ast) {
        doExpressionTest(input, ast);
    }

    protected void doUnaryTestsTest(String actualText, String expectedAST) {
        // Parse input entry
        UnaryTests<Type> actualTest = (UnaryTests<Type>) this.feelTranslator.parseUnaryTests(actualText);

        // Check AST
        assertEquals(expectedAST, actualTest.toString());
    }

    protected void doExpressionTest(String actualText, String expectedAST) {
        // Parse expression
        Expression<Type> actualExpression = (Expression<Type>) this.feelTranslator.parseExpression(actualText);

        // Check AST
        assertEquals(expectedAST, actualExpression.toString(), "Augmented AST mismatch");
    }

    protected void doTextualExpressionsTest(String actualText, String expectedAST) {
        // Parse expression
        Expression<Type> actualExpression = (Expression<Type>) this.feelTranslator.parseTextualExpressions(actualText);

        // Check AST
        assertEquals(expectedAST, actualExpression.toString());
    }

    protected void doBoxedExpressionTest(String actualText, String expectedAST) {
        // Parse expression
        Expression<Type> actualExpression = (Expression<Type>) this.feelTranslator.parseBoxedExpression(actualText);

        // Check AST
        assertEquals(expectedAST, actualExpression.toString());
    }
}
