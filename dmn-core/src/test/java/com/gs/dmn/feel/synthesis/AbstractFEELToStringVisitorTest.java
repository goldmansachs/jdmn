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
package com.gs.dmn.feel.synthesis;

import com.gs.dmn.AbstractTest;
import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.dialect.JavaTimeDMNDialectDefinition;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.el.synthesis.ELTranslator;
import com.gs.dmn.error.NopErrorHandler;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.test.UnaryTests;
import com.gs.dmn.feel.analysis.syntax.ast.visitor.ToStringVisitor;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractFEELToStringVisitorTest extends AbstractTest {
    protected final ELTranslator<Type, DMNContext> feelTranslator;

    private final ToStringVisitor<Type, DMNContext> visitor;

    protected AbstractFEELToStringVisitorTest() {
        JavaTimeDMNDialectDefinition dialectDefinition = new JavaTimeDMNDialectDefinition();
        DMNModelRepository repository = makeRepository();

        this.feelTranslator = dialectDefinition.createFEELTranslator(repository, this.inputParameters);
        this.visitor = new ToStringVisitor<>(new NopErrorHandler());
    }

    //
    // Test UnaryTests
    //
    @ParameterizedTest
    @CsvFileSource(resources = "/feel/serialization/common-unary-tests.csv", numLinesToSkip = 1, delimiter = '|')
    public void testCommonUnaryTests(String input, String expected, String ast) {
        doUnaryTestsTest(input, ast, expected);
    }

    //
    // Test Textual Expressions
    //
    @ParameterizedTest
    @CsvFileSource(resources = "/feel/serialization/common-textual-expressions.csv", numLinesToSkip = 1, delimiter = '|')
    public void testCommonTextualExpressions(String input, String expected, String ast) {
        doTextualExpressionsTest(input, ast, expected);
    }

    //
    // Test Boxed Expressions
    //
    @ParameterizedTest
    @CsvFileSource(resources = "/feel/serialization/common-boxed-expressions.csv", numLinesToSkip = 1, delimiter = '|')
    public void testCommonBoxedExpressions(String input, String expected, String ast) {
        doBoxedExpressionTest(input, ast, expected);
    }

    //
    // Test Expressions
    //
    @ParameterizedTest
    @CsvFileSource(resources = "/feel/serialization/common-expressions.csv", numLinesToSkip = 1, delimiter = '|')
    public void testCommonExpressions(String input, String expected, String ast) {
        doExpressionTest(input, ast, expected);
    }

    protected void doUnaryTestsTest(String actualText, String expectedAST, String expectedText) {
        // Parse input entry
        UnaryTests<Type> actualTest = (UnaryTests<Type>) this.feelTranslator.parseUnaryTests(actualText);

        // Check AST
        assertEquals(expectedAST, actualTest.toString());

        // Check serialization
        checkSerialization(expectedText, actualTest);

        // Check if tests are equivalent
        UnaryTests<Type> expectedTest = (UnaryTests<Type>) this.feelTranslator.parseUnaryTests(expectedText);
        assertEquals(expectedAST, expectedTest.toString(), "Augmented AST mismatch");
    }

    protected void doExpressionTest(String actualText, String expectedAST, String expectedText) {
        // Parse expression
        Expression<Type> actualExpression = (Expression<Type>) this.feelTranslator.parseExpression(actualText);

        // Check AST
        assertEquals(expectedAST, actualExpression.toString(), "Augmented AST mismatch");

        // Check serialization
        checkSerialization(expectedText, actualExpression);

        // Check if expressions are equivalent
        Expression<Type> expectecExpression = (Expression<Type>) this.feelTranslator.parseExpression(expectedText);
        assertEquals(expectedAST, expectecExpression.toString(), "Augmented AST mismatch");
    }

    protected void doTextualExpressionsTest(String actualText, String expectedAST, String expectedText) {
        // Parse expression
        Expression<Type> actualExpression = (Expression<Type>) this.feelTranslator.parseTextualExpressions(actualText);

        // Check AST
        assertEquals(expectedAST, actualExpression.toString());

        // Check serialization
        checkSerialization(expectedText, actualExpression);

        // Check if expressions are equivalent
        Expression<Type> expectecExpression = (Expression<Type>) this.feelTranslator.parseTextualExpressions(expectedText);
        assertEquals(expectedAST, expectecExpression.toString(), "Augmented AST mismatch");
    }

    protected void doBoxedExpressionTest(String actualText, String expectedAST, String expectedText) {
        // Parse expression
        Expression<Type> actualExpression = (Expression<Type>) this.feelTranslator.parseBoxedExpression(actualText);

        // Check AST
        assertEquals(expectedAST, actualExpression.toString());

        // Check serialization
        checkSerialization(expectedText, actualExpression);

        // Check if expressions are equivalent
        Expression<Type> expectecExpression = (Expression<Type>) this.feelTranslator.parseBoxedExpression(expectedText);
        assertEquals(expectedAST, expectecExpression.toString(), "Augmented AST mismatch");
    }

    private void checkSerialization(String expectedText, Expression<Type> expression) {
        String actualText = expression.accept(this.visitor, null);
        assertEquals(expectedText, actualText);
    }

    protected DMNModelRepository makeRepository() {
        return new DMNModelRepository();
    }
}
